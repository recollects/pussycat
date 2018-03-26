package com.alipay.pussycat.register;

/**
 *
 * 抽象观察者[调用方]
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月19日 下午9:38
 */
public interface Observer<T> {

    /**
     *
     * @param obj
     */
    void update(T obj);

}
