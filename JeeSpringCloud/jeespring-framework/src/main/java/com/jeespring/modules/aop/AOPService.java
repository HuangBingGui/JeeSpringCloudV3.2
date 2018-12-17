package com.jeespring.modules.aop;

import com.jeespring.common.utils.SendMailUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JeeSpring AOP
 * @author 黄炳桂 516821420@qq.com
 * @version v.0.1
 * @date 2016年8月23日
 */
@Aspect
@Component
public class AOPService {
    private static Logger logger = LoggerFactory.getLogger(AOPService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // defined aop pointcut
    //@Pointcut("execution(* com.company.project.modules.*.*(..))")
    //@Pointcut("execution(* com.company.project.modules..*.*(..))")
    /*任意公共方法的执行：
    execution(public * *(..))
    任何一个以“set”开始的方法的执行：
    execution(* set*(..))
    AccountService 接口的任意方法的执行：
    execution(* com.xyz.service.AccountService.*(..))
    定义在service包里的任意方法的执行：
    execution(* com.xyz.service.*.*(..))
    定义在service包和所有子包里的任意类的任意方法的执行：
    execution(* com.xyz.service..*.*(..))
    定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：
    execution(* com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))
    最靠近(..)的为方法名,靠近.*(..))的为类名或者接口名,如上例的JoinPointObjP2.*(..))*/
    @Pointcut("execution(* com.company.project.modules.*.*.*.*(..))")
    public void controllerLog() {
    }

    // log all of controller
    @Before("controllerLog()")
    public void before(JoinPoint joinPoint) {
        //System.out.println("AOP Before:"+joinPoint.getSignature().getDeclaringType() + ",method:" + joinPoint.getSignature().getName()
                //+ ",params:" + Arrays.asList(joinPoint.getArgs()));
        logger.debug(dateFormat.format(new Date()) + " | " +"AOP Before:"+joinPoint.getSignature().getDeclaringType() + ",method:" + joinPoint.getSignature().getName());
        /*Email email=new Email();
        email.setToMailAddr("516821420@qq.com");
        email.setSubject("JeeSpring");
        email.setMessage("JeeSpring AOP!");
        SendMailUtil.sendCommonMail(email.getToMailAddr(),email.getSubject(),email.getMessage());*/
    }

    /*@Around("controllerLog()")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
        pjp.proceed();
    }*/

    // result of return
    @AfterReturning(pointcut = "controllerLog()", returning = "retVal")
    public void after(JoinPoint joinPoint, Object retVal) {
        //System.out.println("AOP AfterReturning:"+retVal);
        logger.debug(dateFormat.format(new Date()) + " | " +"AOP AfterReturning:Object");
    }

}
