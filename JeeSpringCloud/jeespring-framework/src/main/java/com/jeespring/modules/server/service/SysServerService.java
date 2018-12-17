/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.server.service;

import java.util.List;

//import org.springframework.stereotype.Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.server.entity.SysServer;
import com.jeespring.modules.server.dao.SysServerDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
//import com.alibaba.dubbo.config.annotation.Service;

/**
 * 服务器监控Service
 * @author JeeSpring
 * @version 2018-08-20
 */

//启用dubbo服务器时，要去掉下面注解
//com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysServerService.class,version = "1.0.0", timeout = 60000)
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class SysServerService extends AbstractBaseService<SysServerDao, SysServer> implements ISysServerService{

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public SysServer get(String id) {
		//获取数据库数据
		SysServer  sysServer=super.get(id);
		return sysServer;
	}

	@Override
	public SysServer getCache(String id) {
		//获取缓存数据
		SysServer sysServer=(SysServer)redisUtils.get(RedisUtils.getIdKey(SysServerService.class.getName(),id));
		if( sysServer!=null) {
            return sysServer;
        }
		//获取数据库数据
		sysServer=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysServerService.class.getName(),id),sysServer);
		return sysServer;
	}

	@Override
	public List<SysServer> total(SysServer sysServer) {
		//获取数据库数据
		List<SysServer> sysServerList=super.total(sysServer);
		return sysServerList;
	}

	@Override
	public List<SysServer> totalCache(SysServer sysServer) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(SysServerService.class.getName(),JSON.toJSONString(sysServer));
		List<SysServer> sysServerList=(List<SysServer>)redisUtils.get(totalKey);
		if(sysServerList!=null) {
			return sysServerList;
		}
		//获取数据库数据
		sysServerList=super.total(sysServer);
		//设置缓存数据
		redisUtils.set(totalKey,sysServerList);
		return sysServerList;
	}

	@Override
	public List<SysServer> findList(SysServer sysServer) {
		//获取数据库数据
		List<SysServer> sysServerList=super.findList(sysServer);
		//设置缓存数据
		return sysServerList;
	}

	@Override
	public List<SysServer> findListCache(SysServer sysServer) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysServerService.class.getName(),JSON.toJSONString(sysServer));
		List<SysServer> sysServerList=(List<SysServer>)redisUtils.get(findListKey);
		if(sysServerList!=null) {
			return sysServerList;
		}
		//获取数据库数据
		sysServerList=super.findList(sysServer);
		//设置缓存数据
		redisUtils.set(findListKey,sysServerList);
		return sysServerList;
	}

	public SysServer findListFirst(SysServer sysServer) {;
		//获取数据库数据
		List<SysServer> sysServerList=super.findList(sysServer);
		if(sysServerList.size()>0) {
			sysServer = sysServerList.get(0);
		}
		return sysServer;
	}

	public SysServer findListFirstCache(SysServer sysServer) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(SysServerService.class.getName(),JSON.toJSONString(sysServer));
		SysServer sysServerRedis=(SysServer)redisUtils.get(findListFirstKey);
		if(sysServerRedis!=null) {
			return sysServerRedis;
		}
		//获取数据库数据
		List<SysServer> sysServerList=super.findList(sysServer);
		if(sysServerList.size()>0) {
			sysServer = sysServerList.get(0);
		} else {
			sysServer = new SysServer();
		}
		//设置缓存数据
		redisUtils.set(findListFirstKey,sysServer);
		return sysServer;
	}

	@Override
	public Page<SysServer> findPage(Page<SysServer> page, SysServer sysServer) {
		//获取数据库数据
		Page<SysServer> pageReuslt=super.findPage(page, sysServer);
		return pageReuslt;
	}

	@Override
	public Page<SysServer> findPageCache(Page<SysServer> page, SysServer sysServer) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysServerService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysServer));
		Page<SysServer> pageReuslt=(Page<SysServer>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
			return pageReuslt;
		}
		//获取数据库数据
		pageReuslt=super.findPage(page, sysServer);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SysServer sysServer) {
		//保存数据库记录
		super.save(sysServer);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysServerService.class.getName(),sysServer.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysServerService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysServerService.class.getName()));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(SysServer sysServer) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysServerService.class.getName(),sysServer.getId()));
		//删除数据库记录
		super.delete(sysServer);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysServerService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysServerService.class.getName()));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByLogic(SysServer sysServer) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysServerService.class.getName(),sysServer.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysServer);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysServerService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysServerService.class.getName()));
	}

}