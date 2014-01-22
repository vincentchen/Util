package info.vincentchan.interceptor;

import info.vincentchan.util.DigestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 拦截器用来检查用户是否已登录
 *
 * @author Vincent.Chan
 * @since 12-4-6
 *        Time:   下午4:55
 */
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private static List<String> exceptedUrls;

    @Inject
    private UserManager userManager;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        //Do nothing
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String context = request.getContextPath();
        if (exceptedUrls != null) {
            for (String url : exceptedUrls) {
                if (request.getRequestURI().startsWith(context + url)) {
                    return true;
                }
            }
        }

        //检测用户是否已登录
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return true;
        } else if (validCookie(request)) {
            return true;
        } /*else {

            //response.sendError(HttpServletResponse.SC_FORBIDDEN, "用户尚未登录！");
        }*/
        throw new AuthException("用户未登录！");
        //return false;
    }

    private boolean validCookie(HttpServletRequest request) {
        try {
            String uid = null, token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("uid")) {
                        uid = cookie.getValue();
                    }
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }

            if (StringUtils.hasText(uid) && StringUtils.hasText(token)) {
                User user = userManager.findUserById(Long.valueOf(uid));
                if (user != null && token.equals(DigestHelper.md5(user.getUserId() + user.getPasswd()))) {
                    userManager.findUserInfo(user);
                    request.getSession(true).setAttribute("user", user);
                    return true;
                }
            }
        } catch (Exception ex) {
            logger.error("cookie校验出错！", ex);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //Do nothing
    }

    public List<String> getExceptedUrls() {
        return exceptedUrls;
    }

    public void setExceptedUrls(List<String> exceptedUrls) {
        this.exceptedUrls = exceptedUrls;
    }
}
