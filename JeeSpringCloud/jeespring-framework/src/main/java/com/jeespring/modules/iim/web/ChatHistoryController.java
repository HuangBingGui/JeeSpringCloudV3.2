/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.iim.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeespring.common.config.Global;
import com.jeespring.common.json.AjaxJson;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.iim.entity.ChatHistory;
import com.jeespring.modules.iim.service.ChatHistoryService;
import com.jeespring.modules.sys.utils.UserUtils;

/**
 * 聊天记录Controller
 * * * * @author 黄炳桂 516821420@qq.com
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/chatHistory")
public class ChatHistoryController extends AbstractBaseController {

	@Autowired
	private ChatHistoryService chatHistoryService;
	
	@ModelAttribute
	public ChatHistory get(@RequestParam(required=false) String id) {
		ChatHistory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = chatHistoryService.get(id);
		}
		if (entity == null){
			entity = new ChatHistory();
		}
		return entity;
	}
	
	/**
	 * 聊天列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(ChatHistory chatHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page pg = new Page<ChatHistory>(request, response);
		Page<ChatHistory> page = chatHistoryService.findPage(pg, chatHistory); 
		model.addAttribute("chatHistory", chatHistory);
		model.addAttribute("page", page);
		return "modules/iim/chatHistoryList";
	}

	/**
	 * 查看，增加，编辑聊天表单页面
	 */
	@RequestMapping(value = "form")
	public String form(ChatHistory chatHistory, Model model) {
		model.addAttribute("chatHistory", chatHistory);
		return "modules/iim/chatHistoryForm";
	}

	/**
	 * 保存聊天
	 */
	@RequestMapping(value = "save")
	public String save(ChatHistory chatHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, chatHistory)){
			return form(chatHistory, model);
		}
		chatHistoryService.save(chatHistory);
		addMessage(redirectAttributes, "保存聊天成功");
		return "redirect:"+Global.getAdminPath()+"/iim/chatHistory/?repage";
	}
	
	/**
	 * 删除聊天
	 */
	@RequestMapping(value = "delete")
	public String delete(ChatHistory chatHistory, RedirectAttributes redirectAttributes) {
		chatHistoryService.delete(chatHistory);
		addMessage(redirectAttributes, "删除聊天成功");
		return "redirect:"+Global.getAdminPath()+"/iim/chatHistory/?repage";
	}
	
	/**
	 * 批量删除聊天
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			chatHistoryService.delete(chatHistoryService.get(id));
		}
		addMessage(redirectAttributes, "删除聊天成功");
		return "redirect:"+Global.getAdminPath()+"/iim/chatHistory/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("iim:chatHistory:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ChatHistory chatHistory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "聊天"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ChatHistory> page = chatHistoryService.findPage(new Page<ChatHistory>(request, response, -1), chatHistory);
    		new ExportExcel("聊天", ChatHistory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出聊天记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/iim/chatHistory/?repage";
    }

	/**
	 * 获取聊天记录
	 */
	@ResponseBody
	@RequestMapping(value = "getChats")
	public AjaxJson getChats(ChatHistory chatHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ChatHistory> page = chatHistoryService.findPage(new Page<ChatHistory>(request, response), chatHistory); 
		List<ChatHistory> list = page.getList();
		for(ChatHistory c : list){
			if("0".equals(c.getStatus())){
				if(c.getUserid2().equals(UserUtils.getUser().getLoginName())){//把发送给我的信息标记为已读
					c.setStatus("1");//标记为已读
					chatHistoryService.save(c);
				}
				
			}
		}
		AjaxJson j = new AjaxJson();
		j.setMsg("获取聊天记录成功!");
		j.put("data", page.getList());
		return j;
	}
	
	/**
	 * 获取未读条数
	 */
	@ResponseBody
	@RequestMapping(value = "findUnReadCount")
	public AjaxJson  findUnReadCount(ChatHistory chatHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		int size = chatHistoryService.findUnReadCount(chatHistory); 
		j.setMsg("获取未读条数成功!");
		j.put("num", size);
		
		return j;
		
	}

	
	/**
	 * 发送聊天内容（手机端)
	 */
	
	@ResponseBody
	@RequestMapping(value = "sendChats")
	public  AjaxJson  sendChats(ChatHistory chatHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		j.setMsg("消息发送成功!");
		chatHistory.setStatus("0");//标记未读
		chatHistoryService.save(chatHistory);
		
		return j;
	}
	
	


}