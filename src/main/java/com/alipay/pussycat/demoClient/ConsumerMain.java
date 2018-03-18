package com.alipay.pussycat.demoClient;

import com.alipay.pussycat.RpcService;
import com.alipay.pussycat.RpcServiceImpl;
import com.alipay.pussycat.demoServer.ServerReflect;

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
