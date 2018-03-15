package com.alipay.pussycat.common.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe工具类
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月15日 下午9:25
 */
public class UnsafeHelper {

    private static Unsafe unsafe = null;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe"); // Internal reference
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for property <tt>unsafe</tt>.
     *
     * @return property value of unsafe
     */
    public static Unsafe getUnsafe() {
        return unsafe;
    }

}
