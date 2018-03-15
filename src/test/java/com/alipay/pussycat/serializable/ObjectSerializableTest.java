package com.alipay.pussycat.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alipay.pussycat.serializable.model.TransportModel;

/**
 *
 * TODO 这里只是模拟序列操作,但不是真正的网络直连来交互的.
 *
 *
 * Created by recollects on 18/3/13.
 */
public class ObjectSerializableTest {

    public static void main(String[] args) throws  Exception{
        HelloService helloService = new HelloServiceImpl();

        TransportModel model = new TransportModel();
        model.setResult(helloService.sayHello());


        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("./obj.out")));
        oos.writeObject(model);
        oos.flush();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("./obj.out")));
        TransportModel resultObject = (TransportModel)ois.readObject();

        Object result = resultObject.getResult();

        System.out.println("返回结果--->"+result);

    }

}
