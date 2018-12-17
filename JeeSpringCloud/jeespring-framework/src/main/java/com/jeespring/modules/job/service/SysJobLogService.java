/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.job.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.job.entity.SysJobLog;
import com.jeespring.modules.job.dao.SysJobLogDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;

/**
 * 定时任务调度日志表Service
 * @author JeeSpring
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class SysJobLogService extends AbstractBaseService<SysJobLogDao, SysJobLog> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
    public SysJobLog get(String id) {
		//获取数据库数据
		SysJobLog  sysJobLog=super.get(id);
		return sysJobLog;
	}

	public SysJobLog getCache(String id) {
		//获取缓存数据
		SysJobLog sysJobLog=(SysJobLog)redisUtils.get(RedisUtils.getIdKey(SysJobLogService.class.getName(),id));
		if( sysJobLog!=null) {
            return sysJobLog;
        }
		//获取数据库数据
		sysJobLog=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysJobLogService.class.getName(),id),sysJobLog);
		return sysJobLog;
	}

	@Override
    public List<SysJobLog> total(SysJobLog sysJobLog) {
		//获取数据库数据
		List<SysJobLog> sysJobLogList=super.total(sysJobLog);
		return sysJobLogList;
	}

	public List<SysJobLog> totalCache(SysJobLog sysJobLog) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(SysJobLogService.class.getName(),JSON.toJSONString(sysJobLog));
		List<SysJobLog> sysJobLogList=(List<SysJobLog>)redisUtils.get(totalKey);
		if(sysJobLogList!=null) {
            return sysJobLogList;
        }
		//获取数据库数据
		sysJobLogList=super.total(sysJobLog);
		//设置缓存数据
		redisUtils.set(totalKey,sysJobLogList);
		return sysJobLogList;
	}

	@Override
    public List<SysJobLog> findList(SysJobLog sysJobLog) {
		//获取数据库数据
		List<SysJobLog> sysJobLogList=super.findList(sysJobLog);
		//设置缓存数据
		return sysJobLogList;
	}

	public List<SysJobLog> findListCache(SysJobLog sysJobLog) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysJobLogService.class.getName(),JSON.toJSONString(sysJobLog));
		List<SysJobLog> sysJobLogList=(List<SysJobLog>)redisUtils.get(findListKey);
		if(sysJobLogList!=null) {
            return sysJobLogList;
        }
		//获取数据库数据
		sysJobLogList=super.findList(sysJobLog);
		//设置缓存数据
		redisUtils.set(findListKey,sysJobLogList);
		return sysJobLogList;
	}

	public SysJobLog findListFirst(SysJobLog sysJobLog) {;
		//获取数据库数据
		List<SysJobLog> sysJobLogList=super.findList(sysJobLog);
		if(sysJobLogList.size()>0) {
            sysJobLog = sysJobLogList.get(0);
        }
		return sysJobLog;
	}

	public SysJobLog findListFirstCache(SysJobLog sysJobLog) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(SysJobLogService.class.getName(),JSON.toJSONString(sysJobLog));
		SysJobLog sysJobLogRedis=(SysJobLog)redisUtils.get(findListFirstKey);
		if(sysJobLogRedis!=null) {
            return sysJobLogRedis;
        }
		//获取数据库数据
		List<SysJobLog> sysJobLogList=super.findList(sysJobLog);
		if(sysJobLogList.size()>0) {
            sysJobLog = sysJobLogList.get(0);
        } else {
            sysJobLog = new SysJobLog();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,sysJobLog);
		return sysJobLog;
	}

	@Override
    public Page<SysJobLog> findPage(Page<SysJobLog> page, SysJobLog sysJobLog) {
		//获取数据库数据
		Page<SysJobLog> pageReuslt=super.findPage(page, sysJobLog);
		return pageReuslt;
	}

	public Page<SysJobLog> findPageCache(Page<SysJobLog> page, SysJobLog sysJobLog) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysJobLogService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysJobLog));
		Page<SysJobLog> pageReuslt=(Page<SysJobLog>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, sysJobLog);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
    @Transactional(readOnly = false)
	public void save(SysJobLog sysJobLog) {
		//保存数据库记录
		super.save(sysJobLog);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobLogService.class.getName(),sysJobLog.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobLogService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobLogService.class.getName()));
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(SysJobLog sysJobLog) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobLogService.class.getName(),sysJobLog.getId()));
		//删除数据库记录
		super.delete(sysJobLog);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobLogService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobLogService.class.getName()));
	}

	@Override
    @Transactional(readOnly = false)
	public void deleteByLogic(SysJobLog sysJobLog) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysJobLogService.class.getName(),sysJobLog.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysJobLog);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysJobLogService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysJobLogService.class.getName()));
	}

}