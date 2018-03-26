package com.alipay.pussycat.serializable.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alipay.pussycat.serializable.HelloService;
import com.alipay.pussycat.serializable.HelloServiceImpl;
import com.alipay.pussycat.serializable.model.TransportModel;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

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

//            List<String> resolveClassMethods = resolveClassMethods(HelloService.class);

//            ImmutableMap<Object, String> immutableMap = Maps.uniqueIndex(resolveClassMethods.iterator(), new Function<String, Object>() {
//                @Override
//                public Object apply(String s) {
//                    return helloService;
//                }
//            });


            InputStream inputStream = accept.getInputStream();

            ObjectInputStream ois = new ObjectInputStream(inputStream);

            TransportModel transportModel = (TransportModel) ois.readObject();

            String methodName = transportModel.getMethodName();
            Class<?>[] parameterTypes = transportModel.getParameterTypes();
            String transformStr = transportModel.getParameters();
            Object[] inputParameters = transportModel.getInputParameters();

            //com.alipay.reflect.HelloService#sayHello:java.lang.String,java.lang.Integer

            String key = transportModel.getInterfaceName()+"#"+transportModel.getMethodName()+":"+transformStr;

            //模拟将信息添加注册中心
            ServerRegisterCenterTest.SERVER_REGISTER_CENTER.put(key,helloService);
            //然后取出服务
            Object object = ServerRegisterCenterTest.SERVER_REGISTER_CENTER.get(key);

            Method method = object.getClass().getMethod(methodName,parameterTypes);

            Object invoke = method.invoke(object, inputParameters);

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

    /**
     *
     * 提出来接口里方法列表
     * [这种拼接方式太low,后期转化成对象来存储,增加可读性]
     *
     * @param clazz
     * @return
     */
    public static List<String> resolveClassMethods(Class clazz){
        Method[] methods = clazz.getMethods();

        List<String> list = Lists.newArrayList();
        for (Method method : methods) {

            Parameter[] parameters = method.getParameters();

            String parameterStr=parameterTransformStr(parameters);
            list.add(HelloService.class.getName() + "#" + method.getName() + ":" + parameterStr);
        }
        return list;
    }

    /**
     *
     * @param parameters
     * @return
     */
    public static String parameterTransformStr(Parameter[] parameters){
        String parameterStr = null;
        if (parameters != null && parameters.length > 0) {
            List<Parameter> asList = Arrays.asList(parameters);

            List<Class<?>> transform = Lists.transform(asList, new Function<Parameter, Class<?>>() {

                @Override
                public Class<?> apply(Parameter input) {
                    return input.getType();
                }
            });
            parameterStr = Joiner.on(",").join(transform.iterator());
        }
        if (StringUtils.contains(parameterStr, "class ")) {
            parameterStr = StringUtils.remove(parameterStr, "class ");
        }
        return parameterStr;
    }


}
