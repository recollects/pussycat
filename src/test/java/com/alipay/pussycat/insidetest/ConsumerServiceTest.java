package com.alipay.pussycat.insidetest;

import com.alipay.pussycat.insidetest.model.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午7:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", "/spring/spring-consumer.xml" })
public class ConsumerServiceTest {

    @Autowired
    private UserService userServiceConsumer;

    @Test
    public void testConsumer() throws Exception {
        String result = userServiceConsumer.login("yjd", "smj");
        String code = userServiceConsumer.login("123");

        System.out.println("login-1:" + result);
        System.out.println("login-2:" + code);

        System.in.read();

    }

}
