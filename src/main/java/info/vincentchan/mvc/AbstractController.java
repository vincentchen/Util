package info.vincentchan.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 抽象Controller，提供异常处理，所有的Controller都继承于此
 *
 * @author Vincent.Chan
 * @since 2012.04.13 19:21
 */
@ControllerAdvice
public class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected MessageSource messageSource;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Model handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少必须的请求参数，请检查参数大小写！", e);
        ExtendedModelMap model = new ExtendedModelMap();
        /*Result error = new Result();
        error.setCode(ResultCode.MISSING_PARAMS);
        error.setMessage("缺少必须的请求参数，请检查参数大小写！详细信息：" + e.getMessage());
        //model.addAttribute(e);
        model.addAttribute("result", error);*/
        model.addAttribute("exception", e);
        return model;
    }

    @ExceptionHandler(TypeMismatchException.class)
    public Model handleTypeMismatchException(TypeMismatchException e) {
        logger.error("类型不匹配", e);
        ExtendedModelMap model = new ExtendedModelMap();
       /* Result error = new Result();
        error.setCode(ResultCode.TYPE_MISMATCH);
        error.setMessage("出错啦");
        //model.addAttribute(error);
        model.addAttribute("result", e);*/
        model.addAttribute("exception", e);
        return model;
    }

    /*@ExceptionHandler(AuthException.class)
    public ModelAndView handleAuthException(AuthException e) {
        logger.error("身份校验失败!", e);
        //ExtendedModelMap model = new ExtendedModelMap();
        ModelAndView modelAndView = new ModelAndView("login");

        return modelAndView;
    }*/

    /**
     * 数据库操作异常，统一处理，返回Model即可根据请求类型返回view
     *
     * @param dae
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public Model handleDataAccessException(DataAccessException dae) {
        logger.error("数据库操作异常！", dae);
        ExtendedModelMap model = new ExtendedModelMap();
        model.addAttribute("exception", dae);
        return model;
    }

    /**
     * 这个ExceptionHandler无效，因为MaxUploadSizeExceededException发生在binding阶段。。。
     * 解决方法见下方的resolveException(implement HandlerExceptionResolver interface)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Model handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        logger.error("上传文件大小超过系统最大限制！", e);
        ExtendedModelMap model = new ExtendedModelMap();
        /*Result error = new Result();
        error.setCode(ResultCode.WRONG_PARAMS);
        error.setMessage("上传文件大小超过系统最大限制！系统最大允许" + e.getMaxUploadSize() / 1024 / 1024 + "MB！");
        //model.addAttribute(e);
        model.addAttribute("result", error);*/
        model.addAttribute("exception", e);
        return model;
    }

    /**
     * 所有未被捕获的异常将在这里获得处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        logger.error("系统异常！", e);
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.getModelMap().put("exception", e);
        return modelAndView;
    }

    //@Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            logger.error("上传文件大小超过系统最大限制！", ex);
            ExtendedModelMap model = new ExtendedModelMap();
            /*Result error = new Result();
            error.setCode(ResultCode.WRONG_PARAMS);
            error.setMessage("上传文件大小超过系统最大限制！系统最大允许" + ((MaxUploadSizeExceededException) ex).getMaxUploadSize() / 1024 / 1024 + "MB！");
            //model.addAttribute(e);
            model.addAttribute("result", error);*/
            return new ModelAndView(request.getRequestURI(), model);
        }
        return null;
    }
}