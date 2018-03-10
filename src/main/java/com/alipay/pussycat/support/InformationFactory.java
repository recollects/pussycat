package com.alipay.pussycat.support;

import java.util.HashSet;
import java.util.Set;

import com.alipay.pussycat.model.IOrdinaryInfo;
import com.alipay.pussycat.model.IPushInfo;
import com.alipay.pussycat.model.ISystemInfo;
import com.alipay.pussycat.vo.ProDataVO;

/**
 * @author wb-smj330392
 * @create 2018-03-10 13:01
 */
public class InformationFactory {

    private int infoNO;
    private Set<RootInfoService> set = new HashSet<RootInfoService>();

    public RootInfoService getInfoServiceBean(int i){
        if (i==1){
            return new IOrdinaryInfo();
        }else if(i==2){
            return new IPushInfo();
        }
        return new ISystemInfo();
    }

}
