package info.vincentchan.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author Vincent.Chan
 * @since 2012.04.13 16:27
 */
public class Servlets {
    public static String extractErrors(BindingResult bindingResult) {
        String result = "错误信息:";
        for (ObjectError error : bindingResult.getAllErrors()) {
            result += error.getDefaultMessage() + ";";
        }
        return result;
    }

    public static String extractErrors(BindingResult bindingResult, MessageSource messageSource) {
        String result = "错误信息:";
        for (ObjectError error : bindingResult.getAllErrors()) {
            result += extractErrorMessage(error, messageSource) + ";";
        }
        return result;
    }

    private static String extractErrorMessage(ObjectError error, MessageSource messageSource) {
        String result = null;
        if (error.getCodes() != null) {
            for (String code : error.getCodes()) {
                try {
                    result = messageSource.getMessage(code, error.getArguments(), Locale.SIMPLIFIED_CHINESE);
                } catch (NoSuchMessageException e) {
                    continue;
                }
                if (result != null) {
                    return result;
                }
            }
        }
        return error.getDefaultMessage();
    }

    public static String extractIp(HttpServletRequest request) {
        //从request提取客户端ip地址
        // We look if the request is forwarded
        // If it is not call the older function.
        String ip = request.getHeader("X-Pounded-For");

        if (ip != null) {
            return ip;
        }

        ip = request.getHeader("x-forwarded-for");

        if (ip == null) {
            return request.getRemoteAddr();
        } else {
            // Process the IP to keep the last IP (real ip of the computer on the net)
            StringTokenizer tokenizer = new StringTokenizer(ip, ",");

            // Ignore all tokens, except the last one
            for (int i = 0; i < tokenizer.countTokens() - 1; i++) {
                tokenizer.nextElement();
            }

            ip = tokenizer.nextToken().trim();

            if (ip.equals("")) {
                ip = null;
            }
        }

        // If the ip is still null, we put 0.0.0.0 to avoid null values
        /*if (ip == null) {
            ip = "0.0.0.0";
        }*/

        return ip;
    }
}
