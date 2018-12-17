/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.company.project.modules.ylttrip.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.company.project.modules.ylttrip.entity.TfTicket;
import com.company.project.modules.ylttrip.service.TfTicketService;
import com.company.project.modules.ylttrip.service.ITfTicketService;
//import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 订单Controller
 * @author JeeSpring
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/ylttrip/tfTicket")
public class TfTicketController extends AbstractBaseController {

	//调用dubbo服务器是，要去Reference注解,注解Autowired
	//@Reference(version = "1.0.0")
	@Autowired
	private ITfTicketService tfTicketService;

	@ModelAttribute
	public TfTicket get(@RequestParam(required=false) String id) {
		TfTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tfTicketService.getCache(id);
			//entity = tfTicketService.get(id);
		}
		if (entity == null){
			entity = new TfTicket();
		}
		return entity;
	}

	/**
	 * 订单统计页面
	 */
	@RequiresPermissions("ylttrip:tfTicket:total")
	@RequestMapping(value = {"total"})
	public String totalView(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(tfTicket,request,response,model);
		return "modules/ylttrip/tfTicketTotal";
	}
	private void total(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(tfTicket.getTotalType())){
			tfTicket.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		List<Double> sumGoodsNumList = new ArrayList<Double>();
		List<Double> sumPriceList = new ArrayList<Double>();
		List<Double> sumSalePriceList = new ArrayList<Double>();
		if(tfTicket.getOrderBy()==""){
			tfTicket.setOrderBy("totalDate");
		}
		List<TfTicket> list = tfTicketService.totalCache(tfTicket);
		//List<TfTicket> list = tfTicketService.total(tfTicket);
		model.addAttribute("list", list);
		for(TfTicket tfTicketItem:list){
			//x轴数据
			xAxisData.add( tfTicketItem.getTotalDate());
			countList.add(Double.valueOf(tfTicketItem.getTotalCount()));
			if(tfTicketItem.getSumGoodsNum()!=null)
				sumGoodsNumList.add(Double.valueOf(tfTicketItem.getSumGoodsNum()));
			else
				tfTicketItem.setSumGoodsNum(0D);
			if(tfTicketItem.getSumPrice()!=null)
				sumPriceList.add(Double.valueOf(tfTicketItem.getSumPrice()));
			else
				tfTicketItem.setSumPrice(0D);
			if(tfTicketItem.getSumSalePrice()!=null)
				sumSalePriceList.add(Double.valueOf(tfTicketItem.getSumSalePrice()));
			else
				tfTicketItem.setSumSalePrice(0D);
		}
		yAxisData.put("数量", countList);
		yAxisData.put("商品数量", sumGoodsNumList);
		yAxisData.put("商品单价", sumPriceList);
		yAxisData.put("订单金额", sumSalePriceList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(TfTicket::getTotalCount).sum());
		model.addAttribute("sumGoodsNum", list.stream().mapToDouble(TfTicket::getSumGoodsNum).sum());
		model.addAttribute("sumPrice", list.stream().mapToDouble(TfTicket::getSumPrice).sum());
		model.addAttribute("sumSalePrice", list.stream().mapToDouble(TfTicket::getSumSalePrice).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		Map<String,Object> orientDataSumGoodsNum= new HashMap<String,Object>();
		Map<String,Object> orientDataSumPrice= new HashMap<String,Object>();
		Map<String,Object> orientDataSumSalePrice= new HashMap<String,Object>();
		for(TfTicket tfTicketItem:list){
			orientData.put(tfTicketItem.getTotalDate(), tfTicketItem.getTotalCount());
			orientDataSumGoodsNum.put(tfTicketItem.getTotalDate(), Double.valueOf(tfTicketItem.getSumGoodsNum()));
			orientDataSumPrice.put(tfTicketItem.getTotalDate(), Double.valueOf(tfTicketItem.getSumPrice()));
			orientDataSumSalePrice.put(tfTicketItem.getTotalDate(), Double.valueOf(tfTicketItem.getSumSalePrice()));
		}
		model.addAttribute("orientData", orientData);
		model.addAttribute("orientDataSumGoodsNum", orientDataSumGoodsNum);
		model.addAttribute("orientDataSumPrice", orientDataSumPrice);
		model.addAttribute("orientDataSumSalePrice", orientDataSumSalePrice);
	}
	@RequiresPermissions("ylttrip:tfTicket:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(tfTicket.getTotalType())){
			tfTicket.setTotalType("%Y-%m-%d");
		}
		List<TfTicket> list = tfTicketService.totalCache(tfTicket);
		//List<TfTicket> list = tfTicketService.total(tfTicket);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(TfTicket::getTotalCount).sum());
		model.addAttribute("sumGoodsNum", list.stream().mapToDouble(TfTicket::getSumGoodsNum).sum());
		model.addAttribute("sumPrice", list.stream().mapToDouble(TfTicket::getSumPrice).sum());
		model.addAttribute("sumSalePrice", list.stream().mapToDouble(TfTicket::getSumSalePrice).sum());
		model.addAttribute("list", list);
		return "modules/ylttrip/tfTicketTotalMap";
	}

	/**
	 * 订单列表页面
	 */
	@RequiresPermissions("ylttrip:tfTicket:list")
	@RequestMapping(value = {"list", ""})
	public String list(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TfTicket> page = tfTicketService.findPageCache(new Page<TfTicket>(request, response), tfTicket);
		//Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(request, response), tfTicket);
		model.addAttribute("page", page);
		tfTicket.setOrderBy("totalDate");
		total(tfTicket,request,response,model);
		return "modules/ylttrip/tfTicketList";
	}

	/**
	 * 订单列表页面
	 */
	@RequiresPermissions("ylttrip:tfTicket:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TfTicket> page = tfTicketService.findPageCache(new Page<TfTicket>(request, response), tfTicket);
		//Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(request, response), tfTicket);
		model.addAttribute("page", page);
		return "modules/ylttrip/tfTicketListVue";
	}

	/**
	 * 订单列表页面
	 */
	//RequiresPermissions("ylttrip:tfTicket:select")
	@RequestMapping(value = {"select"})
	public String select(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TfTicket> page = tfTicketService.findPageCache(new Page<TfTicket>(request, response), tfTicket);
		//Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(request, response), tfTicket);
		model.addAttribute("page", page);
		return "modules/ylttrip/tfTicketSelect";
	}

	/**
	 * 查看，增加，编辑订单表单页面
	 */
	@RequiresPermissions(value={"ylttrip:tfTicket:view","ylttrip:tfTicket:add","ylttrip:tfTicket:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TfTicket tfTicket, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("tfTicket", tfTicket);
		if(request.getParameter("ViewFormType")!=null && request.getParameter("ViewFormType").equals("FormTwo"))
			return "modules/ylttrip/tfTicketFormTwo";
		return "modules/ylttrip/tfTicketForm";
	}

	/**
	 * 保存订单
	 */
	@RequiresPermissions(value={"ylttrip:tfTicket:add","ylttrip:tfTicket:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TfTicket tfTicket, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, tfTicket)){
			return form(tfTicket, model,request,response);
		}
		tfTicketService.save(tfTicket);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
	}

	/**
	 * 删除订单
	 */
	@RequiresPermissions("ylttrip:tfTicket:del")
	@RequestMapping(value = "delete")
	public String delete(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		tfTicketService.delete(tfTicket);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
	}

	/**
	 * 删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"ylttrip:tfTicket:del","ylttrip:tfTicket:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(TfTicket tfTicket, RedirectAttributes redirectAttributes) {
		tfTicketService.deleteByLogic(tfTicket);
		addMessage(redirectAttributes, "逻辑删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
	}

	/**
	 * 批量删除订单
	 */
	@RequiresPermissions("ylttrip:tfTicket:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tfTicketService.delete(tfTicketService.get(id));
		}
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
	}

	/**
	 * 批量删除订单（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"ylttrip:tfTicket:del","ylttrip:tfTicket:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tfTicketService.deleteByLogic(tfTicketService.get(id));
		}
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ylttrip:tfTicket:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TfTicket tfTicket, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TfTicket> page = tfTicketService.findPage(new Page<TfTicket>(request, response, -1), tfTicket);
    		new ExportExcel("订单", TfTicket.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ylttrip:tfTicket:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TfTicket> list = ei.getDataList(TfTicket.class);
			for (TfTicket tfTicket : list){
				tfTicketService.save(tfTicket);
			}
			successNum=list.size();
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
    }
	
	/**
	 * 下载导入订单数据模板
	 */
	@RequiresPermissions("ylttrip:tfTicket:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单数据导入模板.xlsx";
    		List<TfTicket> list = Lists.newArrayList(); 
    		new ExportExcel("订单数据", TfTicket.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ylttrip/tfTicket/?repage";
    }
	

}