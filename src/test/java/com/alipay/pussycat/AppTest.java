package com.alipay.pussycat;

import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.pussycat.base.BaseJunit4Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseJunit4Test{

    @Autowired
    private RpcServiceImpl demoService;

    public void testGetService(){
        System.out.println(demoService);
    }
}
