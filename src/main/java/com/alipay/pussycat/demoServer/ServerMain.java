package com.alipay.pussycat.demoServer;

import com.alipay.pussycat.RpcServiceImpl;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        RpcServiceImpl rpcService = new RpcServiceImpl();
        ServerReflect.server(rpcService,8089);
    }
}
