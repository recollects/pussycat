package com.alipay.pussycat.constants;

/**
 * @author wb-smj330392
 * @create 2018-03-10 14:22
 */
public enum ImportanceEnum {
    COMMON_IMP(0,"正常"),
    WEAK_IMP(1,"不太重要"),
    MIDDLE_IMP(5,"普通重要"),
    STRONG_IMP(10,"非常重要");

    private Integer impId;
    private String impName;

    ImportanceEnum(Integer impId, String impName) {
        this.impId = impId;
        this.impName = impName;
    }

    public static String getNameById(String id) {
        ImportanceEnum arrEnum[] = ImportanceEnum.values();
        for (ImportanceEnum app : arrEnum) {
            if (app.getImpId().equals(id)) {
                return app.getImpName();

            }
        }
        return "";
    }

    public Integer getImpId() {
        return impId;
    }

    public void setImpId(Integer impId) {
        this.impId = impId;
    }

    public String getImpName() {
        return impName;
    }

    public void setImpName(String impName) {
        this.impName = impName;
    }
}