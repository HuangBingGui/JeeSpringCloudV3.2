/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.company.project.modules.ylttrip.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.company.project.modules.ylttrip.entity.TfTicket;
import com.company.project.modules.ylttrip.dao.TfTicketDao;
import com.company.project.modules.ylttrip.service.ITfTicketService;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;
//import com.alibaba.dubbo.config.annotation.Service;
import com.jeespring.common.config.Global;

/**
 * 订单Service
 * @author JeeSpring
 * @version 2018-11-07
 */
 //启用dubbo服务器时，要去掉下面注解
 //com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysServerService.class,version = "1.0.0", timeout = 60000)
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class TfTicketService extends AbstractBaseService<TfTicketDao, TfTicket> implements ITfTicketService{

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	public TfTicket get(String id) {
		//获取数据库数据
		TfTicket  tfTicket=super.get(id);
		return tfTicket;
	}

	public TfTicket getCache(String id) {
		//获取缓存数据
		TfTicket tfTicket=(TfTicket)redisUtils.get(RedisUtils.getIdKey(TfTicketService.class.getName(),id));
		if( tfTicket!=null) return  tfTicket;
		//获取数据库数据
		tfTicket=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(TfTicketService.class.getName(),id),tfTicket);
		return tfTicket;
	}

	public List<TfTicket> total(TfTicket tfTicket) {
		//获取数据库数据
		List<TfTicket> tfTicketList=super.total(tfTicket);
		return tfTicketList;
	}

	public List<TfTicket> totalCache(TfTicket tfTicket) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(TfTicketService.class.getName(),JSON.toJSONString(tfTicket));
		List<TfTicket> tfTicketList=(List<TfTicket>)redisUtils.get(totalKey);
		if(tfTicketList!=null) return tfTicketList;
		//获取数据库数据
		tfTicketList=super.total(tfTicket);
		//设置缓存数据
		redisUtils.set(totalKey,tfTicketList);
		return tfTicketList;
	}

	public List<TfTicket> findList(TfTicket tfTicket) {
		//获取数据库数据
		List<TfTicket> tfTicketList=super.findList(tfTicket);
		//设置缓存数据
		return tfTicketList;
	}

	public List<TfTicket> findListCache(TfTicket tfTicket) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(TfTicketService.class.getName(),JSON.toJSONString(tfTicket));
		List<TfTicket> tfTicketList=(List<TfTicket>)redisUtils.get(findListKey);
		if(tfTicketList!=null) return tfTicketList;
		//获取数据库数据
		tfTicketList=super.findList(tfTicket);
		//设置缓存数据
		redisUtils.set(findListKey,tfTicketList);
		return tfTicketList;
	}

	public TfTicket findListFirst(TfTicket tfTicket) {;
		//获取数据库数据
		List<TfTicket> tfTicketList=super.findList(tfTicket);
		if(tfTicketList.size()>0) tfTicket=tfTicketList.get(0);
		return tfTicket;
	}

	public TfTicket findListFirstCache(TfTicket tfTicket) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(TfTicketService.class.getName(),JSON.toJSONString(tfTicket));
		TfTicket tfTicketRedis=(TfTicket)redisUtils.get(findListFirstKey);
		if(tfTicketRedis!=null) return tfTicketRedis;
		//获取数据库数据
		List<TfTicket> tfTicketList=super.findList(tfTicket);
		if(tfTicketList.size()>0) tfTicket=tfTicketList.get(0);
		else tfTicket=new TfTicket();
		//设置缓存数据
		redisUtils.set(findListFirstKey,tfTicket);
		return tfTicket;
	}

	public Page<TfTicket> findPage(Page<TfTicket> page, TfTicket tfTicket) {
		//获取数据库数据
		Page<TfTicket> pageReuslt=super.findPage(page, tfTicket);
		return pageReuslt;
	}

	public Page<TfTicket> findPageCache(Page<TfTicket> page, TfTicket tfTicket) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(TfTicketService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(tfTicket));
		Page<TfTicket> pageReuslt=(Page<TfTicket>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) return pageReuslt;
		//获取数据库数据
		pageReuslt=super.findPage(page, tfTicket);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Transactional(readOnly = false)
	public void save(TfTicket tfTicket) {
		//保存数据库记录
		super.save(tfTicket);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TfTicketService.class.getName(),tfTicket.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TfTicketService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TfTicketService.class.getName()));
	}
	
	@Transactional(readOnly = false)
	public void delete(TfTicket tfTicket) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TfTicketService.class.getName(),tfTicket.getId()));
		//删除数据库记录
		super.delete(tfTicket);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TfTicketService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TfTicketService.class.getName()));
	}

	@Transactional(readOnly = false)
	public void deleteByLogic(TfTicket tfTicket) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(TfTicketService.class.getName(),tfTicket.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(tfTicket);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(TfTicketService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(TfTicketService.class.getName()));
	}

}