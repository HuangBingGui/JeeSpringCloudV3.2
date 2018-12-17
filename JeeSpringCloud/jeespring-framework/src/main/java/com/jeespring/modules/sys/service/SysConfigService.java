/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.service;

import java.util.List;

import com.jeespring.common.config.Global;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.sys.entity.SysConfig;
import com.jeespring.modules.sys.dao.SysConfigDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;

/**
 * 系统配置Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2017-11-17
 */
@Service
@Transactional(readOnly = true)
public class SysConfigService extends AbstractBaseService<SysConfigDao, SysConfig> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public SysConfig get(String id) {
		//获取缓存数据
		SysConfig sysConfig=(SysConfig)redisUtils.get(RedisUtils.getIdKey(SysConfigService.class.getName(),id));
		if( sysConfig!=null) {
            return sysConfig;
        }
		//获取数据库数据
		sysConfig=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(SysConfigService.class.getName(),id),sysConfig);
		return sysConfig;
	}

	public SysConfig findListFirst(SysConfig sysConfig) {;
		//获取数据库数据
		List<SysConfig> sysConfigList=super.findList(sysConfig);
		if(sysConfigList.size()>0) {
            sysConfig = sysConfigList.get(0);
        }
		return sysConfig;
	}

	public SysConfig findListFirstCache(SysConfig sysConfig) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(SysConfigService.class.getName(),JSON.toJSONString(sysConfig));
		SysConfig sysConfigRedis=(SysConfig)redisUtils.get(findListFirstKey);
		if(sysConfigRedis!=null) {
            return sysConfigRedis;
        }
		//获取数据库数据
		List<SysConfig> tfTicketList=super.findList(sysConfig);
		if(tfTicketList.size()>0) {
            sysConfig = tfTicketList.get(0);
        } else {
            sysConfig = new SysConfig();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,sysConfig);
		return sysConfig;
	}

	@Override
	public List<SysConfig> findList(SysConfig sysConfig) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(SysConfigService.class.getName(),JSON.toJSONString(sysConfig));
		List<SysConfig> sysConfigList=(List<SysConfig>)redisUtils.get(findListKey);
		if(sysConfigList!=null) {
            return sysConfigList;
        }
		//获取数据库数据
		sysConfigList=super.findList(sysConfig);
		//设置缓存数据
		redisUtils.set(findListKey,sysConfigList);
		return sysConfigList;
	}
	
	@Override
	public Page<SysConfig> findPage(Page<SysConfig> page, SysConfig sysConfig) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(SysConfigService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(sysConfig));
		Page<SysConfig> pageReuslt=(Page<SysConfig>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, sysConfig);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(SysConfig sysConfig) {
		//保存数据库记录
		super.save(sysConfig);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysConfigService.class.getName(),sysConfig.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysConfigService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysConfigService.class.getName()));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(SysConfig sysConfig) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysConfigService.class.getName(),sysConfig.getId()));
		//删除数据库记录
		super.delete(sysConfig);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysConfigService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysConfigService.class.getName()));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByLogic(SysConfig sysConfig) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(SysConfigService.class.getName(),sysConfig.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(sysConfig);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(SysConfigService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(SysConfigService.class.getName()));
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public Boolean isDemoMode() {
		SysConfig sysConfig=new SysConfig();
		sysConfig.setType("demoMode");
		sysConfig=this.findListFirstCache(sysConfig);
		if("true".equals(sysConfig.getValue()) && Global.isDemoMode()) {
            return true;
        }
		return false;
	}

	public  String isDemoModeDescription() {
		SysConfig sysConfig=new SysConfig();
		sysConfig.setType("demoMode");
		sysConfig=this.findListFirstCache(sysConfig);
		if(sysConfig==null) {
            return Global.isDemoModeDescription();
        }
		return sysConfig.getDescription();
	}
	public  String systemMode(){
		if(this.isDemoMode()) {
            return "演示版";
        }
		return "正式版";
	}
}