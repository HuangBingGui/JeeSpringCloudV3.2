package com.jeespring.modules.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect     // 切面
@Component      // 加入spring的依赖
public class HttpAspect {
    // spring内置是使用logback，引入的是 import org.slf4j.Logger; 这个log的jar包
    private static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //如果拦截所有该类中的方法public * com.imooc.ManController.*(..)
    //如果拦截所有类 * public * com.imooc.*.*(..)，但是多类有requestMapping的话会算两次

    //@Pointcut("execution(public * com.jeespring.*.*(..))")
    //@Pointcut("execution(* com.jeespring.modules.*.*(..))")
    @Pointcut("execution(* com.jeespring.modules.*.web.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinpoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // url
        logger.info("url="+ request.getRequestURL());
        //System.out.println("url="+ request.getRequestURL());
        // method
        logger.info("method="+request.getMethod());
        //System.out.println("method="+request.getMethod());
        // ip
        logger.info("ip="+request.getRemoteAddr());
        //System.out.println("ip="+request.getRemoteAddr());
        // 类方法
        logger.info("class_method="+joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName());
        //System.out.println("class_method="+joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName());
        // 参数
        logger.info("args="+joinpoint.getArgs());
        //System.out.println("args="+joinpoint.getArgs());
    }

    @After("log()")
    public void doAfter(){
        logger.info("doAfter");
    }

    /**
     * 返回信息展示，注意实体类需要一个toString方法
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object){
        logger.info("respone="+object);
        //System.out.println("respone="+object);
    }
}
