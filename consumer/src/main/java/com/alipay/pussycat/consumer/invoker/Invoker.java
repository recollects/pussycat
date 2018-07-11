package com.alipay.pussycat.consumer.invoker;

import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.core.common.model.PussycatRequest;
import com.alipay.pussycat.core.common.model.PussycatResponse;

/**
 *
 * @author recollects
 * @date 2018年06月29日 下午4:13 
 * @version V1.0
 *
 */
public interface Invoker {

    /**
     *
     * @param request
     * @return
     */
    PussycatResponse invoker(PussycatRequest request) throws PussycatException;
}
