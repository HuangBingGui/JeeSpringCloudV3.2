package com.jeespring.common.filter;

import com.jeespring.common.security.ShiroUtils;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.monitor.entity.OnlineSession;
import com.jeespring.modules.sys.dao.OnlineSessionDAO;
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 退出过滤器
 * 
 * @author JeeSpring
 */

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter
{
    private SysUserOnlineService sysUserOnlineService;

    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);
    
    /**
     * 退出后重定向的地址
     */
    private String loginUrl="/admin/login";

    public String getLoginUrl()
    {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        try
        {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try
            {
                User user = ShiroUtils.getUser();
                if (StringUtils.isNotNull(user))
                {
                    String loginName = user.getLoginName();
                    SysUserOnline sysUserOnline=new SysUserOnline();
                    sysUserOnline.setLoginName(user.getName());
                    if(sysUserOnlineService==null){
                        ServletContext context = request.getServletContext();
                        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
                        sysUserOnlineService = ctx.getBean(SysUserOnlineService.class);
                    }
                    if(sysUserOnlineService!=null){
                        sysUserOnline= sysUserOnlineService.get(subject.getSession().getId().toString());
                        if(sysUserOnline!=null){
                            sysUserOnline.setStatus(OnlineSession.OnlineStatus.off_line.toString());
                            sysUserOnlineService.save(sysUserOnline);
                        }
                    }
                    // 记录用户退出日志
                    //SystemLogUtils.log(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success"));
                }
                // 退出登录
                subject.logout();
            }
            catch (SessionException ise)
            {
                log.error("logout fail.", ise);
            }
            issueRedirect(request, response, redirectUrl);
        }
        catch (Exception e)
        {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }
        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject)
    {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url))
        {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }

}
