package com.alipay.pussycat.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import com.alipay.pussycat.register.ServerRegisterService;

/**
 * 加载类，避免过多依赖spring实现
 */
public class PussycatServiceContainer {

    private final static ConcurrentHashMap<Class<?>, Object> INSTANCE_CACHE = new ConcurrentHashMap<Class<?>, Object>();

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> classType) {
        T instance = (T) INSTANCE_CACHE.get(classType);
        if (instance == null) {
            try {
                instance = ServiceLoader.load(classType, PussycatServiceContainer.class.getClassLoader()).iterator().next();
                INSTANCE_CACHE.putIfAbsent(classType, instance);
                return (T) INSTANCE_CACHE.get(classType);
            } catch (RuntimeException e) {
                    throw e;
            }
        } else {
            if (classType.isAssignableFrom(instance.getClass())) {
                return instance;
            } else {
                throw new RuntimeException("[Init service Container Error]" + classType);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getInstances(Class<T> classType) {
        List<T> list = (List<T>) INSTANCE_CACHE.get(classType);
        if (list == null) {
            try {
                list = new ArrayList<T>();
                for (T instance : ServiceLoader.load(classType, PussycatServiceContainer.class.getClassLoader())) {
                    list.add(instance);
                }
                INSTANCE_CACHE.putIfAbsent(classType, list);
                return (List<T>) INSTANCE_CACHE.get(classType);
            } catch (RuntimeException e) {
                    throw e;

            }
        } else {
            if (List.class.isAssignableFrom(list.getClass())) {
                return list;
            } else {
                throw new RuntimeException("[Init service Container Error(List)]" + classType);
            }
        }
    }

    public static void main(String[] args) {
        ServerRegisterService instance = PussycatServiceContainer.getInstance(ServerRegisterService.class);
        System.out.println(instance);
    }
}
