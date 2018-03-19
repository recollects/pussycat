package com.alipay.pussycat.demo.demoClient;

import com.alipay.pussycat.demo.RpcService;

public class ConsumerMain {
    public static void main(String[] args) throws Exception {
        RpcService rpcService = ConsumerProxy.consume(RpcService.class, "127.0.0.1", 8089);
        for (int i = 0;i<100;i++){
            String hi = rpcService.sayHi("suMingjin" + i);
            System.out.println(hi);
            Thread.sleep(1000);
        }
    }
}
