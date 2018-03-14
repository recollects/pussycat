package com.alipay.pussycat.serializable.socket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

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

			TransportModel model = new TransportModel();
			model.setResult("OK");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(model);

			oos.flush();

			byte[] byteArray = bos.toByteArray();

			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(byteArray);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
