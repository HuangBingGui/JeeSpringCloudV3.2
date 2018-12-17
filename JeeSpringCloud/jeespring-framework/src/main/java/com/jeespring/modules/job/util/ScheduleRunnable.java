package com.jeespring.modules.job.util;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.spring.SpringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 * 
 * @author JeeSpring
 *
 */
public class ScheduleRunnable implements Runnable
{
    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params)
            throws NoSuchMethodException, SecurityException
    {
        this.target = SpringUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotEmpty(params))
        {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        }
        else
        {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run()
    {
        try
        {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params))
            {
                method.invoke(target, params);
            }
            else
            {
                method.invoke(target);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
