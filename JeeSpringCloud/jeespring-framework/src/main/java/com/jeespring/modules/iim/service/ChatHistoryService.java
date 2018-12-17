/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.jeespring.modules.iim.dao.ChatHistoryDao;
import com.jeespring.modules.iim.entity.ChatHistory;

/**
 * 聊天记录Service
 * @author 黄炳桂 516821420@qq.com
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class ChatHistoryService extends AbstractBaseService<ChatHistoryDao, ChatHistory> {

	@Override
    public ChatHistory get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<ChatHistory> findList(ChatHistory chatHistory) {
		return super.findList(chatHistory);
	}
	
	
	@Override
    public Page<ChatHistory> findPage(Page<ChatHistory> page, ChatHistory entity) {
		entity.setPage(page);
		page.setList(dao.findLogList(entity));
		return page;
	}
	
	
	@Override
    @Transactional(readOnly = false)
	public void save(ChatHistory chatHistory) {
		super.save(chatHistory);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(ChatHistory chatHistory) {
		super.delete(chatHistory);
	}
	
	public int findUnReadCount(ChatHistory chatHistory){
		return dao.findUnReadCount(chatHistory);
	}
	
}