package com.alipay.pussycat.serializable.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import com.alipay.pussycat.serializable.HelloService;
import com.alipay.pussycat.serializable.HelloServiceImpl;
import com.alipay.pussycat.serializable.model.TransportModel;

/**
 *
 * 服务端
 *
 * @author jiadong
 * @date 2018/3/14 19:12
 */
public class ObjectServerSerializ {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            Socket accept = serverSocket.accept();

            HelloService helloService = new HelloServiceImpl();
            //TODO 模拟将服务放在map里去,这样客户端只用提交一个接口名,方法名,入参类型即可.我们服务在map里找到这样的服务,将服务取出来,执行返回结果给客户端!

            InputStream inputStream = accept.getInputStream();

            ObjectInputStream ois = new ObjectInputStream(inputStream);

            TransportModel transportModel = (TransportModel) ois.readObject();

            Object object = transportModel.getObject();
            String methodName = transportModel.getMethodName();
            Class<?>[] parameterTypes = transportModel.getParameterTypes();
            Object[] parameters = transportModel.getParameters();

            Method method = object.getClass().getMethod(methodName,parameterTypes);

            Object invoke = method.invoke(object, parameters);

            System.out.println("提供服务端执行方法返回结果："+invoke);

            OutputStream outputStream = accept.getOutputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            TransportModel resultModel = new TransportModel();
            resultModel.setResult(invoke);
            oos.writeObject(resultModel);

            outputStream.write(bos.toByteArray());
            outputStream.flush();
            bos.close();
            outputStream.close();
            serverSocket.close();
            System.out.println("服务端关闭");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
