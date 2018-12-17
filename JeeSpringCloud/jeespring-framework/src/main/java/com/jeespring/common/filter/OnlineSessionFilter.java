package com.jeespring.common.filter;

import com.jeespring.common.constant.ShiroConstants;
import com.jeespring.common.security.ShiroUtils;
import com.jeespring.common.utils.IpUtils;
import com.jeespring.common.utils.ServletUtils;
import com.jeespring.modules.sys.dao.OnlineSessionDAO;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import  com.jeespring.modules.monitor.entity.OnlineSession;

/**
 * 自定义访问控制
 * 
 * @author JeeSpring
 */
public class OnlineSessionFilter //extends AccessControlFilter
{

    /**
     * 强制退出后重定向的地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl="/admin/login";

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    //@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null)
        {
            return true;
        }
        //Session session = onlineSessionDAO.readSession(subject.getSession().getId());
        Session session =subject.getSession();
        //&& session instanceof OnlineSession
        if (session != null)
        {
            //OnlineSession onlineSession = (OnlineSession) session;
            OnlineSession onlineSession = new OnlineSession();
            onlineSession.setId(subject.getSession().getId().toString());
            request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
            // 把user对象设置进去
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == "";
            if (isGuest == true)
            {
                User user = ShiroUtils.getUser();
                if (user != null)
                {
                    onlineSession.setUserId(user.getId());
                    onlineSession.setLoginName(user.getLoginName());
                    if(user.getOffice()!=null) {
                        onlineSession.setDeptName(user.getOffice().getName());
                    }
                    onlineSession.markAttributeChanged();
                    UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                    // 获取客户端操作系统
                    String os = userAgent.getOperatingSystem().getName();
                    // 获取客户端浏览器
                    String browser = userAgent.getBrowser().getName();
                    onlineSession.setHost(IpUtils.getIpAddr((HttpServletRequest)request));
                    onlineSession.setBrowser(browser);
                    onlineSession.setOs(os);
                }
            }

            if (onlineSession.getStatus() == OnlineSession.OnlineStatus.off_line)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    //@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject != null)
        {
            subject.logout();
        }
        //saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    // 跳转到登录页
    //@Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException
    {
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */

    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }
}
