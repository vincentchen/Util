package info.vincentchan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试跨域ajax
 *
 * @author Vincent.Chan
 * @since 2012.06.21 21:11
 */
public class XSSRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        // 设置 withCredentials 为 true 时，该字段不能为通配符
        // 切记不要写成request.headers.referer，ff下就会出错
        resp.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
        resp.getWriter().write("您的跨域请求成功了！");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type, theme");
        resp.addHeader("Access-Control-Max-Age", "30");
        String type = req.getParameter("type");
        if (type != null && type.equals("with-credentials")) {
            // 允许附带认证信息
            resp.addHeader("Access-Control-Allow-Credentials", "true");
            // 设置 cookie 以确认效果
            resp.addHeader("Set-Cookie", "time=' + new Date()");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (req.getHeader("Origin").equals("http://127.0.0.1:8080")) {
            resp.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
            byte[] data = new byte[req.getInputStream().available()];
            req.getInputStream().read(data);
            resp.getWriter().write("您提交的数据是：<br/><br/>" + new String(data, "UTF-8"));
        } else {
            resp.getWriter().write("不允许你的网站请求。");
        }
    }
}
