package com.alipay.pussycat.demoServer;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerReflect {
    private static final ExecutorService executorService  = Executors.newCachedThreadPool();
    /**
     * 服务的发布
     */

    public static void server(final Object service , int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream ois = null;
                    ObjectOutputStream oos = null;
                    try{

                         ois = new ObjectInputStream(socket.getInputStream());
                        String methodName = ois.readUTF();
                        Object[] args = (Object[]) ois.readObject();

                         oos = new ObjectOutputStream(socket.getOutputStream());

                        //利用发射调用服务方法
                        Object result = MethodUtils.invokeExactMethod(service, methodName, args);
                        //将结果返回给服务消费端
                        oos.writeObject(result);
                    }catch (Exception e ){
                        e.printStackTrace();
                    }finally {
                        try {
                            ois.close();
                            oos.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }

    }
}
