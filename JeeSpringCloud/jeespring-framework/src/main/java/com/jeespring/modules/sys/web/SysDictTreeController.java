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
import com.jeespring.modules.sys.entity.SysDictTree;
import com.jeespring.modules.sys.service.SysDictTreeService;

/**
 * 数据字典Controller
 * @author JeeSpring
 * @version 2018-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysDictTree")
public class SysDictTreeController extends AbstractBaseController {
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SysDictTreeService sysDictTreeService;
	
	@ModelAttribute
	public SysDictTree get(@RequestParam(required=false) String id) {
		SysDictTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysDictTreeService.get(id);
		}
		if (entity == null){
			entity = new SysDictTree();
		}
		return entity;
	}
	
	/**
	 * 数据字典列表页面
	 */
	//@RequiresPermissions("sys:sysDict:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysDictTree sysDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysDictTree> list = sysDictTreeService.findList(sysDict);
		model.addAttribute("list", list);
		return "modules/sys/sysDictTreeList";
	}

	/**
	 * 查看，增加，编辑数据字典表单页面
	 */
	//@RequiresPermissions(value={"sys:sysDict:view","sys:sysDict:add","sys:sysDict:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysDictTree sysDict, Model model) {
		if (sysDict.getParent()!=null && StringUtils.isNotBlank(sysDict.getParent().getId())){
			sysDict.setParent(sysDictTreeService.get(sysDict.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(sysDict.getId())){
				SysDictTree sysDictChild = new SysDictTree();
				sysDictChild.setParent(new SysDictTree(sysDict.getParent().getId()));
				List<SysDictTree> list = sysDictTreeService.findList(sysDict);
				if (list.size() > 0){
					sysDict.setSort(list.get(list.size()-1).getSort());
					if (sysDict.getSort() != null){
						sysDict.setSort(sysDict.getSort() + 30);
					}
				}
			}
		}
		if (sysDict.getSort() == null){
			sysDict.setSort(30);
		}
		model.addAttribute("sysDict", sysDict);
		return "modules/sys/sysDictTreeForm";
	}

	/**
	 * 保存数据字典
	 */
	//@RequiresPermissions(value={"sys:sysDict:add","sys:sysDict:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysDictTree sysDict, Model model, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfigTree/?repage";
		}

		if (!beanValidator(model, sysDict)){
			return form(sysDict, model);
		}
		sysDictTreeService.save(sysDict);
		addMessage(redirectAttributes, "保存数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysDictTree/?repage";
	}
	
	/**
	 * 删除数据字典
	 */
	//@RequiresPermissions("sys:sysDict:del")
	@RequestMapping(value = "delete")
	public String delete(SysDictTree sysDict, RedirectAttributes redirectAttributes) {
		if(sysConfigService.isDemoMode()){
			addMessage(redirectAttributes, sysConfigService.isDemoModeDescription());
			return "redirect:" + adminPath + "/sys/sysConfigTree/?repage";
	 	}

		sysDictTreeService.delete(sysDict);
		addMessage(redirectAttributes, "删除数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysDictTree/?repage";
	}

	//@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysDictTree> list = sysDictTreeService.findList(new SysDictTree());
		for (int i=0; i<list.size(); i++){
			SysDictTree e = list.get(i);
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