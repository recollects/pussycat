package com.alipay.pussycat.serializable.socket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;

import com.alipay.pussycat.serializable.HelloService;
import com.alipay.pussycat.serializable.HelloServiceImpl;
import com.alipay.pussycat.serializable.model.TransportModel;

/**
 * 客户端
 * 
 * @author jiadong
 *
 */
public class ObjectClientSerializ {

	public static void main(String[] args)  {

		try {
			Socket socket = new Socket("localhost", 8081);

			HelloService helloService = new HelloServiceImpl();

			TransportModel model = new TransportModel();
			model.setMethodName("sayHello");
			Class<? extends HelloService> class1 = helloService.getClass();
			Method method = class1.getMethod("sayHello",String.class);
			model.setParameterTypes(method.getParameterTypes());

            String transformStr = ObjectServerSerializ.parameterTransformStr(method.getParameters());
            model.setParameters(transformStr);
			model.setInputParameters(new Object[]{"The first step of RPC"});
            model.setInterfaceName(HelloService.class.getName());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(model);

			oos.flush();

			byte[] byteArray = bos.toByteArray();

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(byteArray);
			outputStream.flush();
			InputStream inputStream = socket.getInputStream();

			ObjectInputStream ois = new ObjectInputStream(inputStream);
			TransportModel readObject = (TransportModel)ois.readObject();
			System.out.println("调用返回结果="+readObject.getResult());
			socket.close();

			System.out.println("客户端调用结束");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
