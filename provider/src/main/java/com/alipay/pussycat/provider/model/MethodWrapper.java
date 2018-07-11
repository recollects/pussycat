package com.alipay.pussycat.provider.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wb-smj330392
 * @create 2018-06-13 15:23
 */
public class MethodWrapper {

    /******* 该类中的方法名 *******/
    private String methodName;
    /****** 该方法的入参 ******/
    private Class<?>[] paramters;
    /******该方法是否可以降级********/
    private boolean isSupportDegradeService;
    /*****降级方法的路径*******/
    private String degradeServicePath;
    /*****该方法是否支持限流*******/
    private boolean isSupportCallLimit;
    /*****该方法的单位时间内（一分钟）调用的最大次数*******/
    private int maxCallCount = 300;
    /*****降级时的最低调用成功率*******/
    private int degradePercent = 90;


    private Object degradeServiceMock;

    public MethodWrapper(String methodName, Class<?>[] paramters, boolean isSupportDegradeService, String degradeServicePath, boolean isSupportCallLimit,
                         int maxCallCount, int degradePercent) {
        this.methodName = methodName;
        this.paramters = paramters;
        this.isSupportDegradeService = isSupportDegradeService;
        this.degradeServicePath = degradeServicePath;
        this.isSupportCallLimit = isSupportCallLimit;
        this.maxCallCount = maxCallCount;
        this.degradePercent = degradePercent;
        getDegradeMockProvider();
    }

    /**
     * 如果需要降级，设置降级的目标mock服务
     * @return
     */
    private void getDegradeMockProvider()  {
        try {
            if (this.isSupportDegradeService && StringUtils.isNotEmpty(this.degradeServicePath)){
                Class<?> mockClass = Class.forName(this.degradeServicePath);
                Object mockProvider = mockClass.newInstance();
                this.degradeServiceMock = mockProvider;
            }
        }catch (ClassNotFoundException e){

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public boolean isSupportDegradeService() {
        return isSupportDegradeService;
    }

    public void setSupportDegradeService(boolean supportDegradeService) {
        isSupportDegradeService = supportDegradeService;
    }

    public String getDegradeServicePath() {
        return degradeServicePath;
    }

    public void setDegradeServicePath(String degradeServicePath) {
        this.degradeServicePath = degradeServicePath;
    }

    public boolean isSupportCallLimit() {
        return isSupportCallLimit;
    }

    public void setSupportCallLimit(boolean supportCallLimit) {
        isSupportCallLimit = supportCallLimit;
    }

    public int getMaxCallCount() {
        return maxCallCount;
    }

    public void setMaxCallCount(int maxCallCount) {
        this.maxCallCount = maxCallCount;
    }

    public int getDegradePercent() {
        return degradePercent;
    }

    public void setDegradePercent(int degradePercent) {
        this.degradePercent = degradePercent;
    }


    public Class<?>[] getParamters() {
        return paramters;
    }

    public void setParamters(Class<?>[] paramters) {
        this.paramters = paramters;
    }

    public Object getDegradeServiceMock() {
        return degradeServiceMock;
    }

    public void setDegradeServiceMock(Object degradeServiceMock) {
        this.degradeServiceMock = degradeServiceMock;
    }
}
