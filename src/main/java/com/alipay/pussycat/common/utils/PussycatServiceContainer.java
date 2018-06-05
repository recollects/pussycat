package com.alipay.pussycat.common.utils;

import com.alipay.pussycat.server.ProviderServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

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
                ServiceLoader<T> serviceLoader = ServiceLoader.load(classType);
                Iterator<T> iterator = serviceLoader.iterator();

                while (iterator.hasNext()) {
                    T next = iterator.next();
                    INSTANCE_CACHE.putIfAbsent(classType, next);
                    return (T) INSTANCE_CACHE.get(classType);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
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
        ServiceLoader<ProviderServer> consumes = ServiceLoader.load(ProviderServer.class);
        Iterator<ProviderServer> iterator = consumes.iterator();

        while (iterator.hasNext()) {
            ProviderServer next = iterator.next();
            System.out.println(next);
        }
    }

}
