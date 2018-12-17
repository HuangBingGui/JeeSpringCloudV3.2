package com.jeespring.modules.sys.dao;

import com.jeespring.modules.monitor.entity.OnlineSession;
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.service.SysUserOnlineService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * 针对自定义的ShiroSession的db操作
 * 
 * @author JeeSpring
 */
@Service
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO
{
    /**
     * 同步session到数据库的周期 单位为毫秒（默认1分钟）
     */
    @Value("${shiro.session.dbSyncPeriod}")
    private int dbSyncPeriod=1;

    /**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

    @Autowired
    private SysUserOnlineService sysUserOnlineService;

    @Autowired
    private OnlineSessionFactory onlineSessionFactory;

    public OnlineSessionDAO()
    {
        super();
    }

    public OnlineSessionDAO(long expireTime)
    {
        super();
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId)
    {
        SysUserOnline sysUserOnline=new  SysUserOnline();
        if(sysUserOnlineService==null ){
            sysUserOnline.setId(String.valueOf(sessionId));
        }else{
            sysUserOnline = sysUserOnlineService.get(String.valueOf(sessionId));
        }
        if (sysUserOnline == null)
        {
            return null;
        }
        return onlineSessionFactory.createSession(sysUserOnline);
    }
    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    protected void doDelete(Session session)
    {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession)
        {
            return;
        }
        onlineSession.setStatus(OnlineSession.OnlineStatus.off_line);
        SysUserOnline sysUserOnline=new SysUserOnline();
        sysUserOnline.setId(String.valueOf(onlineSession.getId()));
        sysUserOnlineService.delete(sysUserOnline);
    }
}
