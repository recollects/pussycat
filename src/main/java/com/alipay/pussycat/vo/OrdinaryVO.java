package com.alipay.pussycat.vo;

import java.util.List;

import com.alipay.pussycat.constants.ImportanceEnum;

/**
 * @author wb-smj330392
 * @create 2018-03-10 14:17
 */
public class OrdinaryVO extends ProDataVO {
    //以邮件为例
    private String sender;
    private String receiver;
    private List<String> receivers;

    private String email;
    private String title;
    private String context;
    private ImportanceEnum importanceEnum;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ImportanceEnum getImportanceEnum() {
        return importanceEnum;
    }

    public void setImportanceEnum(ImportanceEnum importanceEnum) {
        this.importanceEnum = importanceEnum;
    }

    @Override
    public String toString() {
        return "OrdinaryVO{" +
            "sender='" + sender + '\'' +
            ", receiver='" + receiver + '\'' +
            '}';
    }
}
