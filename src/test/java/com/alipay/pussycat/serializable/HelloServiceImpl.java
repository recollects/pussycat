package com.alipay.pussycat.serializable;

/**
 * Created by recoll on 18/3/13.
 */
public class HelloServiceImpl implements HelloService {

    public static final long serialVersionUID = 1L;

    @Override
    public String sayHello() {
        return "sayHello  无参方法";
    }

    @Override
    public String sayHello(String a) {
        return "sayHello: "+a;
    }

    @Override
    public String sayHello(Integer a) {
        return "sayHello: "+a;
    }
}
