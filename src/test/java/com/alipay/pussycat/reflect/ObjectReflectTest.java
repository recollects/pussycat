package com.alipay.pussycat.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * TODO 方法重截反射执行示例，待重写
 *
 * @author recollects
 *
 */
public class ObjectReflectTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void sayHello() {
		System.out.println("hello world");
	}

	public void sayHello(Integer a) {
		System.out.println("hello world integer");
	}
	
	public void sayHello(String a) {
		System.out.println("hello world string");
	}

	public void sayHello(String a, String b) {
		System.out.println("hello world ->a , ->b");
	}

	public static void main(String[] args)throws Exception {
		ObjectReflectTest hello = new ObjectReflectTest();

		Method[] methods = hello.getClass().getMethods();

		List<Method> asList = Arrays.asList(methods);

		for (Method method : asList) {
			if (StringUtils.equals(method.getName(), "sayHello")) {
				
				int parameterCount = method.getParameterCount();
				Class<?>[] parameterTypes = method.getParameterTypes();

				if (parameterCount==1&&parameterTypes[0].newInstance() instanceof String) {
					method.invoke(hello, "sss");
				}
			}
		}
		
	}
	
}
