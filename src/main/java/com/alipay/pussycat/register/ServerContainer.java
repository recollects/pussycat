package com.alipay.pussycat.register;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 *
 * @author recollects
 * @version V1.0
 * @date 2018年03月19日 下午9:58
 */
public class ServerContainer implements Observerable{

    private List<Observer> list = Lists.newArrayList();

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        list.remove(o);
    }

    @Override
    public void notifyObserver(Observer o) {
        Iterators.any(list.iterator(), new Predicate<Observer>() {

            @Override
            public boolean apply(Observer input) {
                return false;
            }
        });

    }
}
