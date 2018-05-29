package com.alipay.pussycat.register.common;

import com.alipay.pussycat.register.model.ObserverModel;

/**
 *
 * 抽象观察者[调用方]
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月19日 下午9:38
 */
public interface Observer {

    /**
     *
     *
     * @param obj
     */
    void update(ObserverModel obj);

}
