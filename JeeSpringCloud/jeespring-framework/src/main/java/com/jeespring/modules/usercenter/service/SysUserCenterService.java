/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.usercenter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.usercenter.entity.SysUserCenter;
import com.jeespring.modules.usercenter.dao.SysUserCenterDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;

/**
 * 用户中心Service
 * @author 黄炳桂
 * @version 2017-12-12
 */
@Service
@Transactional(readOnly = true)
public class SysUserCenterService extends AbstractBaseService<SysUserCenterDao, SysUserCenter> implements ISysUserCenterService {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public SysUserCenter get(String id) {
		//获取缓存数据
		SysUserCenter sysUserCenter=(SysUserCenter)redisUtils.get(RedisUtils.getIdKey(SysUserCenterService.class.getName(),id));
		if( sysUserCenter!=null) {
			return sysUserCenter;
		}
		//获取数据库数据
		sysUserCenter=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysUserCenterService.class.getName(),id),sysUserCenter);
		return sysUserCenter;
	}

	@Override
	public SysUserCenter getCache(String id) {
		return null;
	}

	@Override
	public List<SysUserCenter> totalCache(SysUserCenter entity) {
		return null;
	}

	@Override
	public List<SysUserCenter> findList(SysUserCenter sysUserCenter) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysUserCenterService.class.getName(),JSON.toJSONString(sysUserCenter));
		List<SysUserCenter> sysUserCenterList=(List<SysUserCenter>)redisUtils.get(findListKey);
		if(sysUserCenterList!=null) {
			return sysUserCenterList;
		}
		//获取数据库数据
		sysUserCenterList=super.findList(sysUserCenter);
		//设置缓存数据
		redisUtils.set(findListKey,sysUserCenterList);
		return sysUserCenterList;
	}

	@Override
	public List<SysUserCenter> findListCache(SysUserCenter entity) {
		return null;
	}

	@Override
	public Page<SysUserCenter> findPage(Page<SysUserCenter> page, SysUserCenter sysUserCenter) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysUserCenterService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysUserCenter));
		Page<SysUserCenter> pageReuslt=(Page<SysUserCenter>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
			return pageReuslt;
		}
		//获取数据库数据
		pageReuslt=super.findPage(page, sysUserCenter);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
	public Page<SysUserCenter> findPageCache(Page<SysUserCenter> page, SysUserCenter entity) {
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SysUserCenter sysUserCenter) {
		//保存数据库记录
		super.save(sysUserCenter);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserCenterService.class.getName(),sysUserCenter.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserCenterService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserCenterService.class.getName()));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(SysUserCenter sysUserCenter) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserCenterService.class.getName(),sysUserCenter.getId()));
		//删除数据库记录
		super.delete(sysUserCenter);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserCenterService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserCenterService.class.getName()));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByLogic(SysUserCenter sysUserCenter) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysUserCenterService.class.getName(),sysUserCenter.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysUserCenter);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysUserCenterService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysUserCenterService.class.getName()));
	}

}