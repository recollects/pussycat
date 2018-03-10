package com.alipay.pussycat.support;

import java.util.List;

import com.alipay.pussycat.vo.ProDataVO;

/**
 * @author wb-smj330392
 * @create 2018-03-10 13:50
 */
public interface RootInfoService<T extends ProDataVO> {

    T getProData();

    T create();

   void batchCreate();
    //void handle(ProDataVO proDataVO);

}