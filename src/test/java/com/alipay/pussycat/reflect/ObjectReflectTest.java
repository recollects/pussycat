package com.alipay.pussycat.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import com.alipay.pussycat.reflect.model.HelloService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.expression.spel.support.ReflectiveMethodResolver;
import org.springframework.util.ReflectionUtils;

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
