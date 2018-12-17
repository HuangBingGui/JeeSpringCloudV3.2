/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.utils.CacheUtils;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.sys.dao.DictDao;
import com.jeespring.modules.sys.entity.Dict;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends AbstractBaseService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Override
    @Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Override
    @Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
