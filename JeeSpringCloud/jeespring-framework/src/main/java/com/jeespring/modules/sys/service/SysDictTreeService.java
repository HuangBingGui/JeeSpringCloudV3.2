/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.service.TreeService;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.entity.SysDictTree;
import com.jeespring.modules.sys.dao.SysDictTreeDao;

/**
 * 数据字典Service
 * @author JeeSpring
 * @version 2018-08-22
 */
@Service
@Transactional(readOnly = true)
public class SysDictTreeService extends TreeService<SysDictTreeDao, SysDictTree> {

	@Override
    public SysDictTree get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<SysDictTree> findList(SysDictTree sysDict) {
		if (StringUtils.isNotBlank(sysDict.getParentIds())){
			sysDict.setParentIds(","+sysDict.getParentIds()+",");
		}
		return super.findList(sysDict);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(SysDictTree sysDict) {
		super.save(sysDict);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(SysDictTree sysDict) {
		super.delete(sysDict);
	}
	
}