package com.alipay.pussycat.publish.interfaceclazz;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 下午10:36
 */
public interface UserService {

    String register(String userName);

    boolean login(String userName, String password);

    boolean login(Integer mobile, String code);

    boolean logout(String userName);


}
