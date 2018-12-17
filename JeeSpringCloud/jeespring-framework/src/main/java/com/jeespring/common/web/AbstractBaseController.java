/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.common.web;

import com.jeespring.common.mapper.JsonMapper;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.validator.BeanValidators;
import com.jeespring.modules.oauth.service.OauthService;
import com.jeespring.modules.utils.service.EmailService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 控制器支持类
 *
 * @author 黄炳桂 516821420@qq.com
 * @version 2013-3-23
 */
public abstract class AbstractBaseController {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;

    @Autowired
    private EmailService mailService;

    @Autowired
    private OauthService oauthService;

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(model, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(redirectAttributes, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组，不传入此参数时，同@Valid注解验证
     * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
     */
    protected void beanValidator(Object object, Class<?>... groups) {
        BeanValidators.validateWithException(validator, object, groups);
    }

    /**
     * 添加Model消息
     *
     * @param model
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JsonMapper.toJsonString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler({Exception.class,BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException(Exception ex,HttpServletRequest request) {
        //if(ex instanceof  IOException){
        //    return "error/400";
        //}
        try {
            mailService.sendMailException("Java后台异常", "URL:" + request.getRequestURL() + "<br>QueryString:" + request.getQueryString() + "<br>Exception:" + ex.getMessage() + "<br>");
        }catch (Exception exception){}
        return "error/400";
    }
    /**
     * 授权登录异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {
        return "error/403";
    }

    /**
     * 拦截API调用次数控制
     */
    @ModelAttribute
    protected void APIHandler(HttpServletRequest request, HttpServletResponse response) {
        try{
            if(!RedisUtils.isShireRedis()){ return;}
            if(!oauthService.isOauthOpen()){ return;}
            //if(request.getRequestURI().indexOf("/rest/")<0) return;
            if(request.getRequestURI().indexOf("/rest/oauth/apiTimeLimiFaild")>=0) {
                return;
            }
            if(request.getRequestURI().indexOf("/admin?login")>=0) {
                return;
            }
            if(request.getRequestURI().indexOf("/admin/login")>=0) {
                return;
            }
            if("/admin".equals(request.getRequestURI())){
                Result result=oauthService.userOnlineAmount();
                if("-1".equals(result.getResultCode())) {
                    response.sendRedirect("/rest/oauth/userOnlineAmountFaild");
                }
                return;
            }
            oauthService.setApiTime();
            Result result = oauthService.ApiTimeLimi(request.getRemoteAddr());
            if(result.getResultCoe().toString()=="-1"){
                //response.sendRedirect("../error/403");
                if(request.getRequestURI().indexOf("/rest/")>0) {
                    response.sendRedirect("/rest/oauth/apiTimeLimiFaild?apiTimeLimi=" + result.getResultObject());
                } else {
                    response.sendRedirect("/rest/oauth/apiTimeLimiFaild?apiTimeLimi=" + result.getResultObject());
                }
                //response.sendRedirect("/rest/oauth/apiTimeLimifaild");
            }
        }catch (Exception e){
        }
    }

    /**
     * Rest云接口安全拦截
     */
    @ModelAttribute
    protected void RestHandler(HttpServletRequest request, HttpServletResponse response) {
        try{
            if(!RedisUtils.isShireRedis()){ return;}
            if(!oauthService.isOauthOpen()){ return;}
            if(request.getRequestURI().indexOf("/rest/")<0) {
                return;
            }
            if(request.getRequestURI().indexOf("/rest/oauth/token")>=0) {
                return;
            }
            if(request.getRequestURI().indexOf("/rest/oauth/faild")>=0) {
                return;
            }
            if(request.getRequestURI().indexOf("/rest/oauth/checkToken")>=0) {
                return;
            }
            Result result = oauthService.checkToken(request.getParameter("token"),request.getRemoteAddr());
            if(result.getResultCoe().toString()=="-1"){
                //response.sendRedirect("../error/403");
                response.sendRedirect("/rest/oauth/faild");
            }
        }catch (Exception e){
        }
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                //过滤，进行html转码
                setValue(text == null ? null : StringEscapeUtils.unescapeHtml4(StringEscapeUtils.escapeHtml4(text.trim())));
                //不过滤，不进行html转码
                //setValue(text == null ? null : text.trim());
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? StringEscapeUtils.unescapeHtml4(value.toString()) : "";
                //return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
        });
    }

}
