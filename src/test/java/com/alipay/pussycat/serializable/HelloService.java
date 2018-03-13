package com.alipay.pussycat.serializable;

import java.io.Serializable;

/**
 * Created by recollects on 18/3/13.
 */
public interface HelloService extends Serializable{

    /**
     *  无参方法
     *
     * @return
     */
    String sayHello();


    //-------------方法重载---------------------
    /**
     *
     * @param a
     * @return
     */
    String sayHello(String a);

    /**
     *
     * @param a
     * @return
     */
    String sayHello(Integer a);


}
