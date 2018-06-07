package com.alipay.pussycat.core.common.netty.serializable;

/**
 *
 *
 * @description 序列化接口
 * @author wb-smj330392
 * @create 2018-05-24 15:02
 */
public interface Serializer {

    /**
     * 将对象序列化成byte[]
     * @param obj
     * @return
     */
    <T> byte[] writeObject(T obj);

    /**
     * 将byte数组反序列成对象
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T readObject(byte[] bytes, Class<T> clazz);
}
