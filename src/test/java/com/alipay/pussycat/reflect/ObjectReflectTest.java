package com.alipay.pussycat.reflect;

import com.alipay.pussycat.reflect.model.HelloService;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 *
 * TODO 方法重截反射执行示例，待重写
 *u
 *
 * @author recollects
 *
 */
public class ObjectReflectTest  {


	public static void main(String[] args)throws Exception {

		HelloService helloService = new HelloService();

		Class aClass = helloService.getClass();

		Method sayHello = aClass.getMethod("sayHello", String.class);

		sayHello.invoke(helloService,"ss");

		Method sayHello1 = aClass.getMethod("sayHello", Integer.class);

		sayHello1.invoke(helloService,22);



		//---------工具--------------

		Method sayHello2 = ReflectionUtils.findMethod(HelloService.class, "sayHello", String.class);

		sayHello2.invoke(helloService,"ss");




		//---------------
		//ReflectiveMethodInvocation

//		ReflectiveMethodResolver


	}
	
}
