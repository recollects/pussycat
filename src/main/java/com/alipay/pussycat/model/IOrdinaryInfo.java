package com.alipay.pussycat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alipay.pussycat.constants.ImportanceEnum;
import com.alipay.pussycat.result.BaseResult;
import com.alipay.pussycat.support.RootInfoService;
import com.alipay.pussycat.vo.OrdinaryVO;
import com.alipay.pussycat.vo.ProDataVO;

/**
 * @author wb-smj330392
 * @create 2018-03-10 13:23
 */
public class IOrdinaryInfo implements RootInfoService<OrdinaryVO>{

    public List<OrdinaryVO> MIDDLEIMP_LIST = new ArrayList<OrdinaryVO>();
    public List<OrdinaryVO>  WEAKIMP_LIST = new ArrayList<OrdinaryVO>();
    public List<OrdinaryVO> STRONGIMP_LIST = new ArrayList<OrdinaryVO>();

    @Override
    public OrdinaryVO create() {
        OrdinaryVO ordinaryVO = new OrdinaryVO();
        ordinaryVO.setContext("aaaaaaaaaaaaaaaaa");
        ordinaryVO.setEmail("XXXX@163.com");
        ordinaryVO.setReceiver("smj");
        ordinaryVO.setSender("su");
        Random random = new Random();
        ImportanceEnum[] impArr = ImportanceEnum.values();
        int nextInt = random.nextInt(impArr.length);
        ordinaryVO.setImportanceEnum(impArr[nextInt]);
        return ordinaryVO;
    }


    public void batchCreate() {
        for(int i=0;i<1000;i++){
            OrdinaryVO ordinaryVO = create();
            if (ordinaryVO.getImportanceEnum()==ImportanceEnum.COMMON_IMP){
                //add(ordinaryVO)   存到数据库
            }else if(ordinaryVO.getImportanceEnum()==ImportanceEnum.WEAK_IMP){
                WEAKIMP_LIST.add(ordinaryVO);
            }else if(ordinaryVO.getImportanceEnum()==ImportanceEnum.MIDDLE_IMP){
                MIDDLEIMP_LIST.add(ordinaryVO);
            }else if(ordinaryVO.getImportanceEnum()==ImportanceEnum.STRONG_IMP) {
                STRONGIMP_LIST.add(ordinaryVO);
            }
        }
    }

    @Override
    public OrdinaryVO getProData() {
        return null;
    }

    public BaseResult sendText(){ return new BaseResult();}

    public BaseResult sendAudio(){return new BaseResult();}

    public BaseResult sendEmial(OrdinaryVO ordinaryVO) throws InterruptedException {
        Thread.sleep(1000);
        return new BaseResult();
    }
}
