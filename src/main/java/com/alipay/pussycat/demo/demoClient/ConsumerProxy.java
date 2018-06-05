package com.alipay.pussycat.demo.demoClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * created by sumj on 3/18/2018
 */
public class ConsumerProxy {

    /**
     * 服务消费代理接口
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public static <T> T consume(final Class<T> interfaceClass, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        ObjectOutputStream oos = null;
                        ObjectInputStream ois = null;
                        Socket socket = null;
                        try {
                            socket = new Socket(host, port);
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeUTF(method.getName());
                            oos.writeObject(args);
                            try {
                                ois = new ObjectInputStream(socket.getInputStream());
                                Object result = ois.readObject();
                                if (result instanceof Throwable) {
                                    throw (Throwable) result;
                                }
                                return result;
                            } finally {
                                ois.close();
                            }
                        } finally {

                            oos.close();
                            socket.close();
                        }

                    }
                });
    }
}
