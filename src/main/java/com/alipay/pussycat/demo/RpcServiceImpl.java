package com.alipay.pussycat.demo;

public class RpcServiceImpl implements RpcService {
    @Override
    public String sayHi(String name) {
        return "hello , " + name;
    }
}
