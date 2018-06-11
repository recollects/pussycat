package com.alipay.pussycat.insidetest.model;

import com.alipay.pussycat.core.api.annotation.Service;
import com.alipay.pussycat.core.common.utils.StringUtils;

import java.util.Random;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年05月30日 下午7:19
 */
@Service(timeout = 3000,version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Override
    public String login(String userName, String password) {
        if (StringUtils.equals("yjd", userName) && StringUtils.equals("smj", password)) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String login(String phone) {
        Random random = new Random();
        return random.nextInt(1000000) + "";
    }

}
