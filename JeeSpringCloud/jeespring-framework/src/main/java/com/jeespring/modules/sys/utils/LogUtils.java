/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.modules.sys.utils;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.entity.Log;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.interceptor.InterceptorLogEntity;
import com.jeespring.modules.sys.interceptor.LogThread;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Date;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-11-7
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title) {
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
        User user = UserUtils.getUser();
        if (user != null && user.getId() != null) {
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(getParameterString(request));
            log.setMethod(request.getMethod());
            log.setCreateBy(user);
            log.setUpdateBy(user);
            log.setUpdateDate(new Date());
            log.setCreateDate(new Date());
            // 异步保存日志
            try {
                InterceptorLogEntity entiry = new InterceptorLogEntity(log, handler, ex);
                LogThread.interceptorLogQueue.put(entiry);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
    public static String getParameterString(HttpServletRequest request){
        if(request.getQueryString()!=null){
            if(request.getQueryString().length()>0){
                return request.getQueryString();
            }
        }
        Map map = request.getParameterMap();
        java.util.Enumeration enumx = request.getParameterNames();
        String result="";
        while(enumx.hasMoreElements()){
            String  paramName=(String)enumx.nextElement();
            String[]  values=request.getParameterValues(paramName);
            for(int  i=0;i<values.length;i++){
                result+=paramName+"="+values[i]+"&";
            }
        }
        if(result=="") {
            try{
                BufferedReader br = request.getReader();
                String str= "";
                while ((str = br.readLine()) != null) {
                    result += str;
                }
            }catch (Exception e){}
        }
        return result;
    }
}
