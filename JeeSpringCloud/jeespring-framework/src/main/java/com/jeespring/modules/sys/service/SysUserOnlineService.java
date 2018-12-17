/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.service;

import java.util.Date;
import java.util.List;

import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.monitor.entity.OnlineSession;
import com.jeespring.modules.sys.entity.User;
import com.jeespring.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.sys.entity.SysUserOnline;
import com.jeespring.modules.sys.dao.SysUserOnlineDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;

/**
 * 在线用户记录Service
 * @author JeeSpring
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class SysUserOnlineService extends AbstractBaseService<SysUserOnlineDao, SysUserOnline> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Value("${spring.redis.run}")
	String redisRun;
	/**
	 * 同步session到数据库的周期 单位为毫秒（默认1分钟）
	 */
	@Value("${shiro.session.dbSyncPeriod}")
	private int dbSyncPeriod=1;
	/**
	 * 上次同步数据库的时间戳
	 */
	private static final String LAST_SYNC_DB_TIMESTAMP = SysUserOnlineService.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

	@Override
    public SysUserOnline get(String id) {
		//获取数据库数据
		SysUserOnline  sysUserOnline=super.get(id);
		return sysUserOnline;
	}

	public SysUserOnline getCache(String id) {
		//获取缓存数据
		SysUserOnline sysUserOnline=(SysUserOnline)redisUtils.get(RedisUtils.getIdKey(SysUserOnlineService.class.getName(),id));
		if( sysUserOnline!=null) {
            return sysUserOnline;
        }
		//获取数据库数据
		sysUserOnline=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysUserOnlineService.class.getName(),id),sysUserOnline);
		return sysUserOnline;
	}

	@Override
    public List<SysUserOnline> total(SysUserOnline sysUserOnline) {
		//获取数据库数据
		List<SysUserOnline> sysUserOnlineList=super.total(sysUserOnline);
		return sysUserOnlineList;
	}

	public List<SysUserOnline> totalCache(SysUserOnline sysUserOnline) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(SysUserOnlineService.class.getName(),JSON.toJSONString(sysUserOnline));
		List<SysUserOnline> sysUserOnlineList=(List<SysUserOnline>)redisUtils.get(totalKey);
		if(sysUserOnlineList!=null) {
            return sysUserOnlineList;
        }
		//获取数据库数据
		sysUserOnlineList=super.total(sysUserOnline);
		//设置缓存数据
		redisUtils.set(totalKey,sysUserOnlineList);
		return sysUserOnlineList;
	}

	@Override
    public List<SysUserOnline> findList(SysUserOnline sysUserOnline) {
		//获取数据库数据
		List<SysUserOnline> sysUserOnlineList=super.findList(sysUserOnline);
		//设置缓存数据
		return sysUserOnlineList;
	}

	public List<SysUserOnline> findListCache(SysUserOnline sysUserOnline) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysUserOnlineService.class.getName(),JSON.toJSONString(sysUserOnline));
		List<SysUserOnline> sysUserOnlineList=(List<SysUserOnline>)redisUtils.get(findListKey);
		if(sysUserOnlineList!=null) {
            return sysUserOnlineList;
        }
		//获取数据库数据
		sysUserOnlineList=super.findList(sysUserOnline);
		//设置缓存数据
		redisUtils.set(findListKey,sysUserOnlineList);
		return sysUserOnlineList;
	}

	public SysUserOnline findListFirst(SysUserOnline sysUserOnline) {;
		//获取数据库数据
		List<SysUserOnline> sysUserOnlineList=super.findList(sysUserOnline);
		if(sysUserOnlineList.size()>0) {
            sysUserOnline = sysUserOnlineList.get(0);
        }
		return sysUserOnline;
	}

	public SysUserOnline findListFirstCache(SysUserOnline sysUserOnline) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(SysUserOnlineService.class.getName(),JSON.toJSONString(sysUserOnline));
		SysUserOnline sysUserOnlineRedis=(SysUserOnline)redisUtils.get(findListFirstKey);
		if(sysUserOnlineRedis!=null) {
            return sysUserOnlineRedis;
        }
		//获取数据库数据
		List<SysUserOnline> sysUserOnlineList=super.findList(sysUserOnline);
		if(sysUserOnlineList.size()>0) {
            sysUserOnline = sysUserOnlineList.get(0);
        } else {
            sysUserOnline = new SysUserOnline();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,sysUserOnline);
		return sysUserOnline;
	}

	@Override
    public Page<SysUserOnline> findPage(Page<SysUserOnline> page, SysUserOnline sysUserOnline) {
		//获取数据库数据
		Page<SysUserOnline> pageReuslt=super.findPage(page, sysUserOnline);
		return pageReuslt;
	}

	@Transactional(readOnly = false)
	public Page<SysUserOnline> findPageCache(Page<SysUserOnline> page, SysUserOnline sysUserOnline) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysUserOnlineService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysUserOnline));
		Page<SysUserOnline> pageReuslt=(Page<SysUserOnline>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, sysUserOnline);

		for (SysUserOnline item: pageReuslt.getList()) {
			if("true".equals(redisRun)){
				try{
					if(!redisUtils.exists(RedisUtils.SHIRO_REDIS+":"+item.getId())){
						item.setStatus(OnlineSession.OnlineStatus.off_line.toString());
						super.save(item);
					}
				}catch (Exception e){}
			}
		}
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
    @Transactional(readOnly = false)
	public void save(SysUserOnline sysUserOnline) {
		//保存数据库记录
		super.save(sysUserOnline);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserOnlineService.class.getName(),sysUserOnline.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserOnlineService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserOnlineService.class.getName()));
	}

	@Override
    @Transactional(readOnly = false)
	public void delete(SysUserOnline sysUserOnline) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserOnlineService.class.getName(),sysUserOnline.getId()));
		//删除数据库记录
		super.delete(sysUserOnline);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserOnlineService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserOnlineService.class.getName()));
	}

	@Override
    @Transactional(readOnly = false)
	public void deleteByLogic(SysUserOnline sysUserOnline) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserOnlineService.class.getName(),sysUserOnline.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysUserOnline);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserOnlineService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserOnlineService.class.getName()));
	}

	/**
	 * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
	 */
	@Transactional(readOnly = false)
	public void syncToDb(OnlineSession onlineSession)
	{
		Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
		if (lastSyncTimestamp != null)
		{
			boolean needSync = true;
			long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
			if (deltaTime < dbSyncPeriod * 60 * 1000)
			{
				// 时间差不足 无需同步
				needSync = false;
			}
			boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == "";

			// session 数据变更了 同步
			if (isGuest == false && onlineSession.isAttributeChanged())
			{
				needSync = true;
			}

			if (needSync == false)
			{
				return;
			}
		}
		onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
		// 更新完后 重置标识
		if (onlineSession.isAttributeChanged())
		{
			onlineSession.resetAttributeChanged();
		}
		try{
			SysUserOnline sysUserOnline=SysUserOnline.fromOnlineSession(onlineSession);
			SysUserOnline sysUserOnlineDb=this.get(sysUserOnline.getId());
			if(sysUserOnlineDb==null){
				sysUserOnline.setIsNewRecord(true);
			}else{
				sysUserOnline.setStartTimestsamp(sysUserOnlineDb.getStartTimestsamp());
			}
			if(StringUtils.isEmpty(sysUserOnline.getDeptName())){
				User user=UserUtils.getByLoginName(sysUserOnline.getLoginName());
				if(user.getCompany()!=null && user.getOffice()!=null) {
                    sysUserOnline.setDeptName(user.getCompany().getName() + "-" + user.getOffice().getName());
                } else if(user.getCompany()!=null) {
                    sysUserOnline.setDeptName(user.getCompany().getName());
                } else if(user.getOffice()!=null) {
                    sysUserOnline.setDeptName(user.getOffice().getName());
                }
			}
			this.save(sysUserOnline);
		} catch (Exception e){
			//System.out.println(e.getMessage());
		}
	}
}