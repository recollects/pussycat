package com.alipay.pussycat.serializable;

/**
 * Created by recollects on 18/3/13.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "sayHello  无参方法";
    }

    @Override
    public String sayHello(String a) {
        return "sayHello: " + a;
    }

    @Override
    public String sayHello(Integer a) {
        return "sayHello: " + a;
    }

    @Override
    public String sayHello(String a, Integer b) {
        return "sayHello:" + a + "," + b;
    }
}
