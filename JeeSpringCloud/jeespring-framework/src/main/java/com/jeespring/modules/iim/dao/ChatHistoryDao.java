/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.dao;

import java.util.List;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.iim.entity.ChatHistory;

/**
 * 聊天记录DAO接口
 * * * * @author 黄炳桂 516821420@qq.com
 * @version 2015-12-29
 */
@Mapper
public interface ChatHistoryDao extends InterfaceBaseDao<ChatHistory> {
	
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
    List<ChatHistory> findLogList(ChatHistory entity);
	
	
	int findUnReadCount(ChatHistory chatHistory);
	
}