package com.alipay.pussycat.serializable.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.alipay.pussycat.serializable.model.TransportModel;

/**
 * @author jiadong
 * @date 2018/3/14 19:12
 */
public class ObjectServerSerializ {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            Socket accept = serverSocket.accept();

            InputStream inputStream = accept.getInputStream();

            ObjectInputStream ois = new ObjectInputStream(inputStream);

            TransportModel readObject = (TransportModel) ois.readObject();

            Object result = readObject.getResult();
            System.out.println(result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
