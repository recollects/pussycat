package com.alipay.pussycat.register;

/**
 * 被观察者
 *
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
