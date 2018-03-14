package com.alipay.pussycat.reflect.model;

/**
 * Created by recollects on 18/3/14.
 */
public class HelloService {

    public void sayHello() {
        System.out.println("sayHello");
    }

    public void sayHello(String str) {
        System.out.println("sayHello String=" + str);
    }

    public void sayHello(Integer i) {
        System.out.println("sayHello Integer=" + i);
    }

}
