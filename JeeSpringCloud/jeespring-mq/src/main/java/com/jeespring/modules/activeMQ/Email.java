package com.jeespring.modules.activeMQ;

import com.jeespring.common.persistence.AbstractBaseEntity;

/**
 * 消息生产者.
 * @author 黄炳桂 516821420@qq.com
 * @version v.0.1
 * @date 2016年8月23日
 */
public class Email extends AbstractBaseEntity<Email> {
    private String toMailAddr;
    private String subject;
    private String message;

    public String getToMailAddr() {
        return toMailAddr;
    }

    public void setToMailAddr(String toMailAddr) {
        this.toMailAddr = toMailAddr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
