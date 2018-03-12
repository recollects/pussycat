package com.alipay.pussycat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.pussycat.base.BaseJunit4Test;


/**
 * Unit test for simple App.
 */
public class AppTest extends BaseJunit4Test{

    @Autowired
    private DemoService demoService;

    @Test
    public void testGetService() {
        System.out.println(demoService);
    }

}
