package com.alipay.pussycat.consumer.invoker;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午4:15 
 * @version V1.0
 *
 */
public class ConsumerInvoker implements Invoker{

    @Override
    public PussycatResponse invoker(PussycatRequest request) throws PussycatException {

        //TODO 判断调用类型，或者在下层判断
        //负载方式
        //

        return null;
    }
}
