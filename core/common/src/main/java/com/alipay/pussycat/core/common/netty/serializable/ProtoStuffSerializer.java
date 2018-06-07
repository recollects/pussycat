package com.alipay.pussycat.core.common.netty.serializable;

/**
 * @author wb-smj330392
 * @create 2018-05-25 17:20
 */
//public class ProtoStuffSerializer implements Serializer {
//
//    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
//
//    private static <T> Schema<T> getSchema(Class<T> clazz) {
//        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
//        if (schema == null) {
//            schema = RuntimeSchema.getSchema(clazz);
//            if (schema != null) {
//                cachedSchema.put(clazz, schema);
//            }
//        }
//        return schema;
//    }
//
//    @Override
//    public <T> byte[] writeObject(T obj) {
//        Class<T> clazz = (Class<T>) obj.getClass();
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            Schema<T> schema = getSchema(clazz);
//            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        } finally {
//            buffer.clear();
//        }
//    }
//
//    @Override
//    public <T> T readObject(byte[] bytes, Class<T> clazz) {
//        try {
//            T obj = clazz.newInstance();
//            Schema<T> schema = getSchema(clazz);
//            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
//            return obj;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
//    }
//}

