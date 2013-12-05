package jaxb;

import info.vincentchan.ibaby.dto.CollectionWrapper;
import info.vincentchan.ibaby.dto.Result;
import info.vincentchan.ibaby.entities.Homework;
import info.vincentchan.ibaby.entities.Notify;
import info.vincentchan.ibaby.entities.User;
import info.vincentchan.marshaller.ClasspathScanningJaxb2Marshaller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Vincent.Chan
 * @since 2012.04.18 19:33
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JaxbTest {
    ClasspathScanningJaxb2Marshaller marshaller;

    @Before
    public void setUp() {
        marshaller = new ClasspathScanningJaxb2Marshaller();
        marshaller.setBasePackages(Arrays.asList(new String[]{"com.qztc.ibaby.dto", "com.qztc.ibaby.entities"}));
    }

    @Test
    public void testJaxb() throws JAXBException {
        /*JAXBContext context = JAXBContext.newInstance(
                Result.class,ArrayList.class,StringWrapper.class, Wrapper.class
        ,User.class);*/

        Result result = new Result();
        /*StringWrapper wrapper=new StringWrapper("test");
        List testList=new ArrayList();
        testList.add(wrapper);
        wrapper = new StringWrapper("test2");
        testList.add(wrapper);
        Wrapper wrapper1=new Wrapper();
        wrapper1.setTest(testList);
        result.setData(wrapper1);*/
        /*User user = new User();
        user.setName("Vincent.Chan");
        result.setData(user);*/
        Homework homework = new Homework();
        homework.setClassNames("Test");
        result.setData(homework);
        marshaller.marshal(result, new StreamResult(System.out));
        //marshaller.marshal(result, System.out);
    }

    @Test
    public void testJaxb2() {
        //Jaxb2Marshaller marshaller=new Jaxb2Marshaller();
        //marshaller.setClassesToBeBound(Result.class, User.class);
        //marshaller.setJaxbContextProperties();
        Result result = new Result();
        CollectionWrapper yr = new CollectionWrapper();

        Notify notify = new Notify();
        notify.setContent("测试");
        User user = new User();
        user.setName("Vincent.Chan");
        /*list.add(user);
        result.setData(list);*/
        List notifiesList = new ArrayList();
        notifiesList.add(notify);
        yr.data = notifiesList;
        result.setData(yr);
        marshaller.marshal(result, new StreamResult(System.out));
    }

    @Test
    public void testSLF4J() {
        Logger logger = LoggerFactory.getLogger(JaxbTest.class);
        logger.error("{}", "测试1", new Exception("测试"));
    }
}

