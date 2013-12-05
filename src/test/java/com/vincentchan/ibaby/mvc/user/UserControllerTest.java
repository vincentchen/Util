package com.vincentchan.ibaby.mvc.user;

import info.vincentchan.ibaby.dao.UserDao;
import info.vincentchan.mvc.HomeController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.inject.Inject;

/**
 * @author Vincent.Chan
 * @since 2012.04.06 17:33
 */
//指定测试用例的运行器 这里是指定了Junit4
@RunWith(SpringJUnit4ClassRunner.class)
//指定Spring的配置文件 /为classpath下
@ContextConfiguration({"/spring/root-context.xml", "/spring/ibabyServlet/ibaby-context.xml"})
//对所有的测试方法都使用事务，并在测试完成后回滚事务
@Transactional
public class UserControllerTest {
    //会自动注入 default by type
    @Inject
    private UserDao userDao;

    //在每个测试用例方法之前都会执行
    @Before
    public void init() {
    }

    //在每个测试用例执行完之后执行
    @After
    public void destory() {
    }

    @Test
    public void testLogin() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
//      LoginCommand command = new LoginCommand();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        Model model = new ExtendedModelMap();
        RedirectAttributes redirectAttibutes = new RedirectAttributesModelMap();

        HomeController controller = new HomeController();
        /*controller.login(null, null, null, null, null, null, false,
                session, request, response, model, redirectAttibutes);*/
    }
}