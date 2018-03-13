package com.alipay.pussycat.serializable;

import com.alipay.pussycat.serializable.model.TransportModel;

import java.io.*;

/**
 *
 * TODO 这里只是模拟序列操作,但不是真正的网络直连来交互的.
 *
 * 可以用socket来模拟一下通信,现在是返回结果不好转成具体哪个类型.[这里需要如果处理???]
 *
 * Created by recollects on 18/3/13.
 */
public class ObjectSerializableTest {

    public static void main(String[] args) throws  Exception{
        HelloService helloService = new HelloServiceImpl();

        TransportModel model = new TransportModel();
        model.setObjectType(HelloServiceImpl.class);
        model.setServiceName(HelloServiceImpl.class.getName());
        model.setResult(helloService.sayHello());


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("./obj.out")));
        oos.writeObject(model);
        oos.flush();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("./obj.out")));
        TransportModel resultObject = (TransportModel)ois.readObject();

        String serviceName = resultObject.getServiceName();
        Object result = resultObject.getResult();


        System.out.println(serviceName);
        System.out.println("返回结果--->"+result);






    }


}
