package com.alipay.pussycat.insidetest.model;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午7:19
 */
public interface UserService {

    String login(String user, String password);

    String login(String phone);

}
