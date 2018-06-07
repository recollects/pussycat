package com.alipay.pussycat.insidetest;

import com.alipay.pussycat.common.model.PussycatContants;
import com.alipay.pussycat.common.model.ServiceMetadata;
import com.alipay.pussycat.consumer.proxy.PYCServiceProxy;
import com.alipay.pussycat.insidetest.model.UserService;

/**
 *
 * @author recollects
 * @date 2018年06月05日 下午6:24 
 * @version V1.0
 *
 */
public class PYCServiceProxyTest {
    public static void main(String[] args) {
        ServiceMetadata metadata = new ServiceMetadata();
        metadata.setHost("127.0.0.1");
        metadata.setPort(PussycatContants.DEFAULT_PORT);
        metadata.setTimeout(3000);
        metadata.setInterfaceName("com.alipay.pussycat.insidetest.model.UserService");
        metadata.setItfClass(UserService.class);
        PYCServiceProxy proxy = new PYCServiceProxy(metadata);
        UserService serviceProxy = (UserService) proxy.getServiceProxy();
        String login = serviceProxy.login("yjd", "smj");
        System.out.println("返回结果:" + login);

    }
}
