/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeespring.common.mapper.JsonMapper;
import com.jeespring.common.utils.CacheUtils;
import com.jeespring.common.utils.SpringContextHolder;
import com.jeespring.modules.sys.dao.DictDao;
import com.jeespring.modules.sys.entity.Dict;

/**
 * 字典工具类
 * @author 黄炳桂 516821420@qq.com
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictLabel(Long value, String type, String defaultValue){
		if(value==null) {
            return "";
        }
		return getDictLabel(value.toString(),type,defaultValue);
	}

	public static String getDictLabel(Integer value, String type, String defaultValue){
		if(value==null) {
            return "";
        }
		return getDictLabel(value.toString(),type,defaultValue);
	}

	public static String getDictPicture(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getPicture();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictPicture(Long value, String type, String defaultValue){
		if(value==null) {
            return "";
        }
		return getDictPicture(value.toString(),type,defaultValue);
	}

	public static String getDictPicture(Integer value, String type, String defaultValue){
		if(value==null) {
            return "";
        }
		return getDictPicture(value.toString(),type,defaultValue);
	}

	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		//if(dictList!=null && dictList.size()!=0 && "".equals(dictList.get(dictList.size()-1).getValue())){
		//	dictList.remove(dictList.size()-1);
		//}
		return dictList;
	}

	//增加全部项
	public static List<Dict> getDictListAddAll(String type){
		List<Dict> dictListResult=new ArrayList<Dict>();
		List<Dict> dictList =getDictList(type);
		dictListResult.addAll(dictList);
		//if(dictList!=null && "".equals(dictList.get(dictList.size()-1).getValue())){
		//	return dictList;
		//}
		Dict dict=new Dict();
		dict.setValue("");
		dict.setLabel("全部");
		dictListResult.add(dict);
		return dictListResult;
	}
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
	
}
