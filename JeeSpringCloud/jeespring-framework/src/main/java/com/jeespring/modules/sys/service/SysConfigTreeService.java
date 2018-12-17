/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.service.TreeService;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.entity.SysConfigTree;
import com.jeespring.modules.sys.dao.SysConfigTreeDao;

/**
 * 系统配置Service
 * @author JeeSpring
 * @version 2018-08-22
 */
@Service
@Transactional(readOnly = true)
public class SysConfigTreeService extends TreeService<SysConfigTreeDao, SysConfigTree> {

	@Override
    public SysConfigTree get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<SysConfigTree> findList(SysConfigTree sysConfig) {
		if (StringUtils.isNotBlank(sysConfig.getParentIds())){
			sysConfig.setParentIds(","+sysConfig.getParentIds()+",");
		}
		return super.findList(sysConfig);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(SysConfigTree sysConfig) {
		super.save(sysConfig);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(SysConfigTree sysConfig) {
		super.delete(sysConfig);
	}
	
}