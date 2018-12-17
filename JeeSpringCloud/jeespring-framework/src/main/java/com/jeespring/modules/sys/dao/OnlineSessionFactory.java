package com.jeespring.modules.sys.dao;

import com.jeespring.modules.monitor.entity.OnlineSession;
import com.jeespring.modules.sys.entity.SysUserOnline;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.ServletUtils;
import com.jeespring.common.utils.IpUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义sessionFactory会话
 * 
 * @author JeeSpring
 */
@Service
public class OnlineSessionFactory implements SessionFactory
{
    public Session createSession(SysUserOnline userOnline)
    {
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null)
        {
            onlineSession.setId(userOnline.getId());
        }
        return userOnline.getSession();
    }

    @Override
    public Session createSession(SessionContext initData)
    {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext)
        {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null)
            {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
            }
        }
        return session;
    }
}
