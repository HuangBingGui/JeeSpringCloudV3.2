/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.modules.sys.interceptor;

import com.jeespring.common.service.AbstractService;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.modules.sys.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 *
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-8-19
 */
@Component("controllerLogInterceptor")
public class LogInterceptor extends AbstractService implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger("controller-log");
    private static final ThreadLocal<Long> startTimeThreadLocal =new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        startTimeThreadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if(!"/error".equals(request.getRequestURI())) {
            logger.info("URI: {},耗时：{}   ", request.getRequestURI() + "-" + request.getMethod()
                    , DateUtils.formatDateTime(System.currentTimeMillis() - startTimeThreadLocal.get()));
        }
        //删除线程变量中的数据，防止内存泄漏
        startTimeThreadLocal.remove();
        // 保存日志
        LogUtils.saveLog(request, handler, ex, null);
    }
}
