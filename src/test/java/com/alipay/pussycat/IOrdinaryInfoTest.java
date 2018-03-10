package com.alipay.pussycat;

/**
 * @author wb-smj330392
 * @create 2018-03-10 15:55
 */

import com.alipay.pussycat.base.BaseJunit4Test;
import com.alipay.pussycat.model.IOrdinaryInfo;
import com.alipay.pussycat.support.InformationFactory;
import com.alipay.pussycat.support.RootInfoService;
import com.alipay.pussycat.vo.ProDataVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IOrdinaryInfoTest extends BaseJunit4Test{


    private IOrdinaryInfo iOrdinaryInfo;

    @Autowired
    private InformationFactory informationFactory;

    @Test
    public void testCreate(){
        RootInfoService infoServiceBean = informationFactory.getInfoServiceBean(1);
        infoServiceBean.batchCreate();
        System.out.println(((IOrdinaryInfo)infoServiceBean).MIDDLEIMP_LIST);
        System.out.println(((IOrdinaryInfo)infoServiceBean).WEAKIMP_LIST);
        System.out.println(((IOrdinaryInfo)infoServiceBean).STRONGIMP_LIST);
    }
}
