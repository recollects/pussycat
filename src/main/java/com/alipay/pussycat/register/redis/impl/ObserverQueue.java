package com.alipay.pussycat.register.redis.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alipay.pussycat.register.common.Observer;
import com.alipay.pussycat.register.common.Observerable;

/**
 * 所有观察者队列
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月19日 下午9:58
 */
public class ObserverQueue implements Observerable {

    /**
     * 观察者队列[调用方]
     */
    private Queue<Observer> queue = new ConcurrentLinkedQueue<Observer>();

    @Override
    public void registerObserver(Observer o) {
        queue.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        queue.remove(o);
    }

    @Override
    public void notifyObserver(Observer o) {
        //TODO 通知观察者获取新信息或者直接将信息直接推送给观察者
    }
}
