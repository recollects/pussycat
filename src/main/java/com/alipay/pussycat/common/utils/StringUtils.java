package com.alipay.pussycat.common.utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月07日 下午1:03
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     *
     * @param parameterTypes
     * @return
     */
    public static String parameterTypesToStr(Class<?>[] parameterTypes) {
        if (ArrayUtils.isEmpty(parameterTypes)) {
            return EMPTY;
        }
        List<Class<?>> classes = Arrays.asList(parameterTypes);

        List<String> strList = Lists.transform(classes, new Function<Class<?>, String>() {
            @Override
            public String apply(Class<?> aClass) {

                return aClass.getName();
            }
        });
        String join = Joiner.on(",").join(strList);
        return join;
    }
}
