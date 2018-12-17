package com.jeespring.modules.oauth.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private Date tokenDate;
    private String oauthId;
    private String oauthSecret;
    private String ip;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public String getOauthSecret() {
        return oauthSecret;
    }

    public void setOauthSecret(String oauthSecret) {
        this.oauthSecret = oauthSecret;
    }

    @JsonIgnore
    @JSONField(serialize=false)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
