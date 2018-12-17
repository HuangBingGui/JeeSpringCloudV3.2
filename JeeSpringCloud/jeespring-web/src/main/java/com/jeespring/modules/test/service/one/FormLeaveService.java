/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.jeespring.modules.test.service.one;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.test.entity.one.FormLeave;
import com.jeespring.modules.test.dao.one.FormLeaveDao;
import com.jeespring.modules.test.service.one.IFormLeaveService;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;
//import com.alibaba.dubbo.config.annotation.Service;
import com.jeespring.common.config.Global;

/**
 * 请假Service
 * @author JeeSpring
 * @version 2018-10-12
 */
 //启用dubbo服务器时，要去掉下面注解
 //com.alibaba.dubbo.config.annotation.Service(interfaceClass = ISysServerService.class,version = "1.0.0", timeout = 60000)
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class FormLeaveService extends AbstractBaseService<FormLeaveDao, FormLeave> implements IFormLeaveService{

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public FormLeave get(String id) {
		//获取数据库数据
		FormLeave  formLeave=super.get(id);
		return formLeave;
	}

	@Override
	public FormLeave getCache(String id) {
		//获取缓存数据
		FormLeave formLeave=(FormLeave)redisUtils.get(RedisUtils.getIdKey(FormLeaveService.class.getName(),id));
		if( formLeave!=null) {
            return formLeave;
        }
		//获取数据库数据
		formLeave=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(FormLeaveService.class.getName(),id),formLeave);
		return formLeave;
	}

	@Override
	public List<FormLeave> total(FormLeave formLeave) {
		//获取数据库数据
		List<FormLeave> formLeaveList=super.total(formLeave);
		return formLeaveList;
	}

	@Override
	public List<FormLeave> totalCache(FormLeave formLeave) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(FormLeaveService.class.getName(),JSON.toJSONString(formLeave));
		List<FormLeave> formLeaveList=(List<FormLeave>)redisUtils.get(totalKey);
		if(formLeaveList!=null) {
            return formLeaveList;
        }
		//获取数据库数据
		formLeaveList=super.total(formLeave);
		//设置缓存数据
		redisUtils.set(totalKey,formLeaveList);
		return formLeaveList;
	}

	@Override
	public List<FormLeave> findList(FormLeave formLeave) {
		//获取数据库数据
		List<FormLeave> formLeaveList=super.findList(formLeave);
		//设置缓存数据
		return formLeaveList;
	}

	@Override
	public List<FormLeave> findListCache(FormLeave formLeave) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(FormLeaveService.class.getName(),JSON.toJSONString(formLeave));
		List<FormLeave> formLeaveList=(List<FormLeave>)redisUtils.get(findListKey);
		if(formLeaveList!=null) {
            return formLeaveList;
        }
		//获取数据库数据
		formLeaveList=super.findList(formLeave);
		//设置缓存数据
		redisUtils.set(findListKey,formLeaveList);
		return formLeaveList;
	}

	public FormLeave findListFirst(FormLeave formLeave) {;
		//获取数据库数据
		List<FormLeave> formLeaveList=super.findList(formLeave);
		if(formLeaveList.size()>0) {
            formLeave = formLeaveList.get(0);
        }
		return formLeave;
	}

	public FormLeave findListFirstCache(FormLeave formLeave) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(FormLeaveService.class.getName(),JSON.toJSONString(formLeave));
		FormLeave formLeaveRedis=(FormLeave)redisUtils.get(findListFirstKey);
		if(formLeaveRedis!=null) {
            return formLeaveRedis;
        }
		//获取数据库数据
		List<FormLeave> formLeaveList=super.findList(formLeave);
		if(formLeaveList.size()>0) {
            formLeave = formLeaveList.get(0);
        } else {
            formLeave = new FormLeave();
        }
		//设置缓存数据
		redisUtils.set(findListFirstKey,formLeave);
		return formLeave;
	}

	@Override
	public Page<FormLeave> findPage(Page<FormLeave> page, FormLeave formLeave) {
		//获取数据库数据
		Page<FormLeave> pageReuslt=super.findPage(page, formLeave);
		return pageReuslt;
	}

	@Override
	public Page<FormLeave> findPageCache(Page<FormLeave> page, FormLeave formLeave) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(FormLeaveService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(formLeave));
		Page<FormLeave> pageReuslt=(Page<FormLeave>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) {
            return pageReuslt;
        }
		//获取数据库数据
		pageReuslt=super.findPage(page, formLeave);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(FormLeave formLeave) {
		//保存数据库记录
		super.save(formLeave);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(FormLeaveService.class.getName(),formLeave.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(FormLeaveService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(FormLeaveService.class.getName()));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(FormLeave formLeave) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(FormLeaveService.class.getName(),formLeave.getId()));
		//删除数据库记录
		super.delete(formLeave);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(FormLeaveService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(FormLeaveService.class.getName()));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByLogic(FormLeave formLeave) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(FormLeaveService.class.getName(),formLeave.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(formLeave);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(FormLeaveService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(FormLeaveService.class.getName()));
	}

}