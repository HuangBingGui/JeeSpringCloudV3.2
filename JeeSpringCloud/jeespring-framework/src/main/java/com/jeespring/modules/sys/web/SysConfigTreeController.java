/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 */
package com.jeespring.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeespring.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeespring.common.config.Global;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.modules.sys.entity.SysConfigTree;
import com.jeespring.modules.sys.service.SysConfigTreeService;

/**
 * 系统配置Controller
 * @author JeeSpring
 * @version 2018-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysConfigTree")
public class SysConfigTreeController extends AbstractBaseController {
	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private SysConfigTreeService sysConfigTreeService;
	
	@ModelAttribute
	public SysConfigTree get(@RequestParam(required=false) String id) {
		SysConfigTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysConfigTreeService.get(id);
		}
		if (entity == null){
			entity = new SysConfigTree();
		}
		return entity;
	}
	
	/**
	 * 系统配置列表页面
	 */
	//@RequiresPermissions("sys:sysConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysConfigTree sysConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysConfigTree> list = sysConfigTreeService.findList(sysConfig);
		for(SysConfigTree item:list){
			if(item.getDescription()==null) {
                item.setDescription("");
            }
			if(item.getDescription().length()>20) {
                item.setDescription(item.getDescription().substring(0, 20));
            }
		}
		model.addAttribute("list", list);
		return "modules/sys/sysConfigTreeList";
	}

	/**
	 * 查看，增加，编辑系统配置表单页面
	 */
	//@RequiresPermissions(value={"sys:sysConfig:view","sys:sysConfig:add","sys:sysConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysConfigTree sysConfig, Model model) {
		if (sysConfig.getParent()!=null && StringUtils.isNotBlank(sysConfig.getParent().getId())){
			sysConfig.setParent(sysConfigTreeService.get(sysConfig.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(sysConfig.getId())){
				SysConfigTree sysConfigChild = new SysConfigTree();
				sysConfigChild.setParent(new SysConfigTree(sysConfig.getParent().getId()));
				List<SysConfigTree> list = sysConfigTreeService.findList(sysConfig);
				if (list.size() > 0){
					sysConfig.setSort(list.get(list.size()-1).getSort());
					if (sysConfig.getSort() != null){
						sysConfig.setSort(sysConfig.getSort() + 30);
					}
				}
			}
		}
		if (sysConfig.getSort() == null){
			sysConfig.setSort(30);
		}
		model.addAttribute("sysConfig", sysConfig);
		return "modules/sys/sysConfigTreeForm";
	}

	/**
	 * 保存系统配置
	 */
	//@RequiresPermissions(value={"sys:sysConfig:add","sys:sysConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysConfigTree sysConfig, Model model, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfigTree/?repage";
		}
		if (!beanValidator(model, sysConfig)){
			return form(sysConfig, model);
		}
		sysConfigTreeService.save(sysConfig);
		addMessage(redirectAttributes, "保存系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfigTree/?repage";
	}
	
	/**
	 * 删除系统配置
	 */
	//@RequiresPermissions("sys:sysConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SysConfigTree sysConfig, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfigTree/?repage";
		}

		sysConfigTreeService.delete(sysConfig);
		addMessage(redirectAttributes, "删除系统配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysConfigTree/?repage";
	}

	//@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysConfigTree> list = sysConfigTreeService.findList(new SysConfigTree());
		for (int i=0; i<list.size(); i++){
			SysConfigTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}