package info.vincentchan.mvc;

import info.vincentchan.util.DigestHelper;
import info.vincentchan.util.Servlets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.Random;

/**
 * @author Vincent.Chan
 * @since 2012.04.05 16:11
 */
@Controller
public class HomeController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testGet(@RequestParam String test) {
        logger.debug(test);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void testPost(@RequestParam String test) {
        logger.debug(test);
    }

    @RequestMapping(value = "/testMultiPart", method = RequestMethod.POST)
    public void testMultipart(@RequestParam String test, MultipartFile file) {
        logger.debug(test);
        if (file != null) {
            logger.debug(file.getContentType());
            logger.debug("文件名:" + file.getOriginalFilename());
        }
    }

    @RequestMapping("/index")
    public void index2() {
    }

    /**
     * 用户登录
     *
     * @param input              登录用户的信息(@see LoginUser)
     * @param bindingResult      用户信息是否有误
     * @param session
     * @param request
     * @param response
     * @param model
     * @param redirectAttributes 登录成功重定向时带登录结果
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute("loginUser") LoginUser input,
                        BindingResult bindingResult,
                        HttpSession session, HttpServletRequest request,
                        HttpServletResponse response,
                        Model model, RedirectAttributes redirectAttributes) {
        Result<User> result = new Result<User>();
        if (bindingResult.hasErrors()) {
            logger.error("参数不合法！登录信息：{}", input);
            result.setCode(ResultCode.INVALID_PARAMS);
            result.setMessage("参数不合法！" + Servlets.extractErrors(bindingResult, messageSource));
            model.addAttribute("result", result);
            return "/login";
        }

        User user = userManager.findUserByUsernameAndRole(input.getUsername(), input.getRoleType());
        if (user == null) {
            logger.error("查无该用户！登录信息：{}", input);
            result.setCode(ResultCode.WRONG_PARAMS);
            result.setMessage("查无该用户！");
            model.addAttribute("result", result);
            return "/login";
        }
        if (Utils.valid(user, input.getPasswd(), input.isGraphicPasswd())) {
            userManager.findUserInfo(user);
            session.setAttribute("user", user);
            Utils.writeCookie(user, response);
            String ip = Servlets.extractIp(request);
            logger.info("用户{}成功登录,登录IP为{}", input.getUsername(), ip);
            try {
                userManager.logUserLogin(user.getUserId(), input.getMacInfo(), input.getUimInfo(), ip, input.getLoginType());
            } catch (Exception e) {
                logger.warn("记录用户登录日志失败！登录信息：{}", input, e);
            }
            User user1 = new User();
            BeanUtils.copyProperties(user, user1);
            User statics = userManager.statics(user);
            user1.setCommentCnt(statics.getCommentCnt());
            user1.setNotifyCnt(statics.getNotifyCnt());
            user1.setHomeworkCnt(statics.getHomeworkCnt());
            user1.setMessageCnt(statics.getMessageCnt());
            result.setCode(ResultCode.OK);
            result.setData(user1);
            result.setMessage("登录成功！");
        } else {
            logger.error("鉴权失败！登录信息：{}", input);
            result.setCode(ResultCode.UNAUTHORIZED);
            result.setMessage("鉴权失败，请检查输入密码是否正确！");
            result.setCode(ResultCode.UNAUTHORIZED);
            model.addAttribute("result", result);
            return "/login";
        }
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(Model model) {
        model.addAttribute("loginUser", new LoginUser());
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.addCookie(new Cookie("uid", null));
        response.addCookie(new Cookie("token", null));
        Result result = new Result();
        result.setMessage("登出成功！");
        result.setCode(ResultCode.OK);
        model.addAttribute("result", result);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("registerUser") User user, BindingResult bindingResult,
                           HttpServletResponse response, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        logger.debug("入参：{}", user);
        Result result = new Result();
        /*if (StringUtils.hasText(user.getAuthCode())) {
            bindingResult.addError(new FieldError("registerUser", "authCode", "验证码不能为空！"));
        }*/

        if (bindingResult.hasErrors()) {
            result.setMessage("参数有误！" + Servlets.extractErrors(bindingResult, messageSource));
            result.setCode(ResultCode.INVALID_PARAMS);
            model.addAttribute("result", result);
            return "/register";
        }

        String expected = userManager.findAuthCode(user.getUsername(), user.getRoleType());
        if (expected == null || !expected.equals(user.getAuthCode())) {
            result.setMessage("验证码错误获已失效！");
            result.setCode(ResultCode.WRONG_PARAMS);
            model.addAttribute("result", result);
            return "/register";
        }

        User existsUser = userManager.findUserByUsernameAndRole(user.getUsername(), user.getRoleType());
        if (existsUser != null) {
            result.setCode(ResultCode.CONFLICT);
            result.setMessage("该帐号及角色已注册！");
            model.addAttribute("result", result);
            return "/register";
        }

        if (user.getSex() == null) {
            user.setSex("M");
        }

        if (user.getName() == null) {
            user.setName(user.getUsername());
        }
        user.setIconId(new Long(0));
        userManager.save(user);
        logger.info("注册成功！自动登录：username:{},roleType:{}", user.getUsername(), user.getRoleType());
        userManager.findUserInfo(user);
        session.setAttribute("user", user);
        Utils.writeCookie(user, response);
        result.setMessage("注册成功！");
        result.setCode(ResultCode.OK);
        User userData = new User();
        userData.setUserId(user.getUserId());
        result.setData(userData);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/";
    }

    @RequestMapping(value = "/register/sms")
    public void preRegister(@RequestParam String username, @RequestParam String roleType, Model model) {
        Result result = new Result();
        User existsUser = userManager.findUserByUsernameAndRole(username, roleType);
        if (existsUser != null) {
            result.setCode(ResultCode.CONFLICT);
            result.setMessage("该帐号及角色已注册！");
            model.addAttribute("result", result);
            return;
        }
        userManager.sms(username, roleType, generateAuthCode());
        result.setCode(ResultCode.OK);
        result.setMessage("短信验证码已发送，该验证码在" + config.minutesBeforeSmsAuthCodeExpired + "分钟内有效！");
        model.addAttribute("result", result);
    }

    @RequestMapping(value = "/password/reset")
    public void passwordReset(@RequestParam String username, @RequestParam String roleType, Model model) {
        Result result = new Result();
        /*if (bindingResult.hasErrors()) {
            result.setMessage("参数有误！" + Servlets.extractErrors(bindingResult, messageSource));
            result.setCode(ErrorCode.INVALID_PARAMS);
            model.addAttribute("result", result);
            return;
        }*/
        /*String expected = userManager.findAuthCode(user.getUsername(), user.getRoleType());
        if (expected == null || !expected.equals(user.getAuthCode())) {
            result.setMessage("验证码错误！");
            result.setCode(ErrorCode.WRONG_PARAMS);
            model.addAttribute("result", result);
            return;
        }*/
        User realUser = userManager.findUserByUsernameAndRole(username, roleType);
        if (realUser != null) {
            realUser.setPassword(generateAuthCode());
            realUser.setAuthCode(realUser.getPassword());
            realUser.setPasswd(DigestHelper.password(realUser.getUserId() + realUser.getPassword()));
            userManager.resetPassword(realUser);
        } else {
            result.setCode(ResultCode.NOT_FOUND);
            result.setMessage("密码重置失败！");
            model.addAttribute("result", result);
            return;
        }

        //userManager.resetPassword(realUser);
        result.setCode(ResultCode.OK);
        result.setMessage("密码重置成功！");
        model.addAttribute("result", result);
    }

    public String generateAuthCode() {
        Random random = new Random();
        String result = "";

        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 根据文档ID提取文档
     *
     * @param docId
     * @param thumb 图片文档的话取原图还是缩略图 (0：原图；1：缩略图)
     * @return
     */
    @RequestMapping("/doc")
    public void getDoc(@RequestParam Long docId,
                       @RequestParam(required = false, defaultValue = "0") boolean thumb,
                       HttpSession session, HttpServletResponse response/*,Model model*/) throws IOException {
        long start = System.currentTimeMillis();
        Result result = new Result();
        DocLocate doc = docManager.find(docId);
        if (doc == null) {
            /*result.setCode(ErrorCode.NOT_FOUND);
            result.setMessage("无该文档("+ docId+")信息");
            model.addAttribute("result",result);*/
            throw new FileNotFoundException("系统无该文档信息！");
        }
        String filePath = null;
        if (doc.isImage() && thumb) {
            filePath = config.getAttachPath(doc.getDocDir()) + "s_" + doc.getDocName();
        } else {
            filePath = config.getAttachPath(doc.getDocDir()) + doc.getDocName();
        }
        logger.debug("文档ID{}路径{}", docId, filePath);
        File file = new File(filePath);
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        /*try {

        } catch (Exception e) {
            response.sendError(404, "找不到文档或目录！");
            return;
        }*/
        String inlineType = doc.isImage() ? "inline" : "attachment"; //  是否内联附件
        String encodedFileName = new String(doc.getDocTitle().getBytes(), "ISO8859-1");
        response.setHeader("Content-Disposition", inlineType + "; filename=\"" + encodedFileName + "\"");

        response.setContentLength((int) file.length());
        //logger.debug("file.getTotalSpace:{}", file.getTotalSpace());
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(fis, outputStream);
        outputStream.flush();
        outputStream.close();
        fis.close();
        long end = System.currentTimeMillis();
        logger.debug("transfer file {} using {}ms:", doc.getDocId(), end - start);
    }

    //FixMe:这个怎么不行
    @ResponseBody
    @RequestMapping(value = "/doc1", method = RequestMethod.GET)
    public ResponseEntity<File> testGetDoc(@RequestParam Long docId,
                                           @RequestParam(required = false, defaultValue = "0") boolean thumb,
                                           HttpSession session, HttpServletResponse response) throws IOException {
        DocLocate doc = docManager.find(docId);
        if (doc == null) {
            return null;
        }
        String filePath = null;
        if (doc.isImage() && thumb) {
            filePath = config.getAttachPath(doc.getDocDir()) + "s_" + doc.getDocName();
        } else {
            filePath = config.getAttachPath(doc.getDocDir()) + doc.getDocName();
        }
        logger.debug("文档ID{}路径{}", docId, filePath);
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(file.length());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        logger.debug("file.getTotalSpace:{}", file.getTotalSpace());
        return new ResponseEntity<File>(file, headers, HttpStatus.OK);
    }
}
