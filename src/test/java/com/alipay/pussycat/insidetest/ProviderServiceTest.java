package com.alipay.pussycat.insidetest;

import com.alipay.pussycat.server.ProviderServer;
import com.alipay.pussycat.server.impl.ProviderServerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午7:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", "/spring/spring-provider.xml" })
public class ProviderServiceTest {

    @Test
    public void testProviderService() throws Exception {

        ProviderServer providerServer = new ProviderServerImpl();
        //        providerServer.start();

        System.in.read();

    }

}
