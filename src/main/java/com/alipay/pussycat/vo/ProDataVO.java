package com.alipay.pussycat.vo;

/**
 * @author wb-smj330392
 * @create 2018-03-10 13:08
 * 记录生产数据源信息
 */
public class ProDataVO {
    private int appNO;  //应用编号
    private int appName;  //应用名称
    private int groupId;  //分组id

    public int getAppNO() {
        return appNO;
    }

    public void setAppNO(int appNO) {
        this.appNO = appNO;
    }

    public int getAppName() {
        return appName;
    }

    public void setAppName(int appName) {
        this.appName = appName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
