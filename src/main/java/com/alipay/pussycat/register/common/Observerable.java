package com.alipay.pussycat.register.common;

/**
 * 被观察者
 * 通知观察者方法
 * @author recollects
 * @version V1.0
 * @date 2018年03月19日 下午9:54
 */
public interface Observerable {

    /**
     *
     * @param o
     */
    void registerObserver(Observer o);

    /**
     *
     * @param o
     */
    void removeObserver(Observer o);

    /**
     *
     */
    void notifyObserver(Observer o);
}
