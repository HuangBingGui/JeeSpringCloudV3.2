/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.sys.entity.Log;
import com.jeespring.modules.sys.service.LogService;

/**
 * 日志Controller
 * @author 黄炳桂 516821420@qq.com
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends AbstractBaseController {

	@Autowired
	private LogService logService;
	
	//RequiresPermissions("sys:log:list")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}

	
	/**
	 * 批量删除
	 */
	//RequiresPermissions("sys:log:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			logService.delete(logService.get(id));
		}
		addMessage(redirectAttributes, "删除日志成功");
		return "redirect:"+Global.getAdminPath()+"/sys/log/?repage";
	}
	
	/**
	 * 批量删除
	 */
	//RequiresPermissions("sys:log:del")
	@RequestMapping(value = "empty")
	public String empty(RedirectAttributes redirectAttributes) {
		logService.empty();
		addMessage(redirectAttributes, "清空日志成功");
		return "redirect:"+Global.getAdminPath()+"/sys/log/?repage";
	}
}
