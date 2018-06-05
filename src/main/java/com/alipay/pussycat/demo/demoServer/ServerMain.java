package com.alipay.pussycat.demo.demoServer;

import com.alipay.pussycat.demo.RpcServiceImpl;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        RpcServiceImpl rpcService = new RpcServiceImpl();
        ServerReflect.server(rpcService, 8089);
    }
}
