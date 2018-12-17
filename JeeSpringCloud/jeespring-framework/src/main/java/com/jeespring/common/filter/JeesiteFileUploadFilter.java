package com.jeespring.common.filter;

import com.ckfinder.connector.FileUploadFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Created by zhao.weiwei
 * create on 2017/1/10 12:23
 * the email is zhao.weiwei@jyall.com.
 */
@WebFilter(urlPatterns = "/static/ckfinder/core/connector/java/connector.java", initParams = {
        @WebInitParam(name = "sessionCookieName", value = "JSESSIONID"),
        @WebInitParam(name = "sessionParameterName", value = "jsessionid")
})
public class JeesiteFileUploadFilter extends FileUploadFilter {
}
