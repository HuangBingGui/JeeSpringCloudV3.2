/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.echarts.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.utils.MyBeanUtils;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.echarts.entity.ChinaWeatherDataBean;
import com.jeespring.modules.echarts.service.ChinaWeatherDataBeanService;

/**
 * 城市气温Controller
 * @author JeeSpring
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/chinaWeatherDataBean")
public class ChinaWeatherDataBeanController extends AbstractBaseController {

	@Autowired
	private ChinaWeatherDataBeanService chinaWeatherDataBeanService;
	
	@ModelAttribute
	public ChinaWeatherDataBean get(@RequestParam(required=false) String id) {
		ChinaWeatherDataBean entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = chinaWeatherDataBeanService.get(id);
		}
		if (entity == null){
			entity = new ChinaWeatherDataBean();
		}
		return entity;
	}
	
	/**
	 * 城市气温列表页面
	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:list")
	@RequestMapping(value = {"list", ""})
	public String list(ChinaWeatherDataBean chinaWeatherDataBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ChinaWeatherDataBean> page = chinaWeatherDataBeanService.findPage(new Page<ChinaWeatherDataBean>(request, response), chinaWeatherDataBean); 
		model.addAttribute("page", page);
		
		
		//折线图列表数据
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		//Y轴双轴情况下的位置定位
		Map<String,Integer> yAxisIndex = new HashMap<String,Integer>();
		
		List<ChinaWeatherDataBean> weatherDataList= chinaWeatherDataBeanService.findList(chinaWeatherDataBean);
		
		List<Double> beijingMaxTemp = new ArrayList<Double>();
		List<Double> beijingMinTemp = new ArrayList<Double>();
		List<Double> changchunMaxTemp = new ArrayList<Double>();
		List<Double> changchunMinTemp = new ArrayList<Double>();
		List<Double> shenyangMaxTemp = new ArrayList<Double>();
		List<Double> shenyangMinTemp = new ArrayList<Double>();
		List<Double> haerbinMaxTemp = new ArrayList<Double>();
		List<Double> haerbinMinTemp = new ArrayList<Double>();
		
		for(ChinaWeatherDataBean chinaWeatherDataBeanTemp:weatherDataList){
			//x轴数据
			xAxisData.add(chinaWeatherDataBeanTemp.getDatestr().toLocaleString());
			//北京最高温度
			beijingMaxTemp.add(chinaWeatherDataBeanTemp.getBeijingMaxTemp());
			//北京最低温度
			beijingMinTemp.add(chinaWeatherDataBeanTemp.getBeijingMinTemp());
			//长春最高温度
			changchunMaxTemp.add(chinaWeatherDataBeanTemp.getChangchunMaxTemp());
			//长春最高温度
			changchunMinTemp.add(chinaWeatherDataBeanTemp.getChangchunMinTemp());
			//沈阳最高温度
			shenyangMaxTemp.add(chinaWeatherDataBeanTemp.getShenyangMaxTemp());
			//沈阳最高温度
			shenyangMinTemp.add(chinaWeatherDataBeanTemp.getShenyangMinTemp());
			//哈尔滨最高温度
			haerbinMaxTemp.add(chinaWeatherDataBeanTemp.getHaerbinMaxTemp());
			//哈尔滨最高温度
			haerbinMinTemp.add(chinaWeatherDataBeanTemp.getHaerbinMinTemp());
			
		}
		
		//y轴数据
		yAxisData.put("北京 最高温度", beijingMaxTemp);
		yAxisData.put("北京 最低温度", beijingMinTemp);
		yAxisData.put("长春 最高温度", changchunMaxTemp);
		yAxisData.put("长春 最低温度", changchunMinTemp);
		yAxisData.put("沈阳 最高温度", shenyangMaxTemp);
		yAxisData.put("沈阳 最低温度", shenyangMinTemp);
		yAxisData.put("哈尔滨 最高温度", haerbinMinTemp);
		yAxisData.put("哈尔滨 最低温度", haerbinMinTemp);
		
		//Y轴双轴情况下的位置定位
		yAxisIndex.put("北京 最高温度", 0);//0表示Y轴左轴
		yAxisIndex.put("长春 最高温度", 0);//0表示Y轴左轴
		yAxisIndex.put("沈阳 最高温度", 0);//0表示Y轴左轴
		yAxisIndex.put("哈尔滨 最高温度", 0);//0表示Y轴左轴
		yAxisIndex.put("北京 最低温度", 1);//1表示Y轴右轴
		yAxisIndex.put("长春 最低温度", 1);//1表示Y轴右轴
		yAxisIndex.put("沈阳 最低温度", 1);//1表示Y轴右轴
		yAxisIndex.put("哈尔滨 最低温度", 1);//1表示Y轴右轴
		
		request.setAttribute("yAxisIndex", yAxisIndex);
		request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		
		
		
		return "modules/echarts/chinaWeatherDataBeanList";
	}

	/**
	 * 查看，增加，编辑城市气温表单页面
	 */
	@RequiresPermissions(value={"echarts:chinaWeatherDataBean:view","echarts:chinaWeatherDataBean:add","echarts:chinaWeatherDataBean:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ChinaWeatherDataBean chinaWeatherDataBean, Model model) {
		model.addAttribute("chinaWeatherDataBean", chinaWeatherDataBean);
		return "modules/echarts/chinaWeatherDataBeanForm";
	}

	/**
	 * 保存城市气温
	 */
	@RequiresPermissions(value={"echarts:chinaWeatherDataBean:add","echarts:chinaWeatherDataBean:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ChinaWeatherDataBean chinaWeatherDataBean, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, chinaWeatherDataBean)){
			return form(chinaWeatherDataBean, model);
		}
		if(!chinaWeatherDataBean.getIsNewRecord()){//编辑表单保存
			ChinaWeatherDataBean t = chinaWeatherDataBeanService.get(chinaWeatherDataBean.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(chinaWeatherDataBean, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			chinaWeatherDataBeanService.save(t);//保存
		}else{//新增表单保存
			chinaWeatherDataBeanService.save(chinaWeatherDataBean);//保存
		}
		addMessage(redirectAttributes, "保存城市气温成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
	}
	
	/**
	 * 删除城市气温
	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:del")
	@RequestMapping(value = "delete")
	public String delete(ChinaWeatherDataBean chinaWeatherDataBean, RedirectAttributes redirectAttributes) {
		chinaWeatherDataBeanService.delete(chinaWeatherDataBean);
		addMessage(redirectAttributes, "删除城市气温成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
	}
	
	/**
	 * 批量删除城市气温
	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
		for(String id : idArray){
			chinaWeatherDataBeanService.delete(chinaWeatherDataBeanService.get(id));
		}
		addMessage(redirectAttributes, "删除城市气温成功");
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ChinaWeatherDataBean chinaWeatherDataBean, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "城市气温"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ChinaWeatherDataBean> page = chinaWeatherDataBeanService.findPage(new Page<ChinaWeatherDataBean>(request, response, -1), chinaWeatherDataBean);
    		new ExportExcel("城市气温", ChinaWeatherDataBean.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出城市气温记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ChinaWeatherDataBean> list = ei.getDataList(ChinaWeatherDataBean.class);
			for (ChinaWeatherDataBean chinaWeatherDataBean : list){
				try{
					chinaWeatherDataBeanService.save(chinaWeatherDataBean);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条城市气温记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条城市气温记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入城市气温失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
    }
	
	/**
	 * 下载导入城市气温数据模板
	 */
	@RequiresPermissions("echarts:chinaWeatherDataBean:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "城市气温数据导入模板.xlsx";
    		List<ChinaWeatherDataBean> list = Lists.newArrayList(); 
    		new ExportExcel("城市气温数据", ChinaWeatherDataBean.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/chinaWeatherDataBean/?repage";
    }
	
	
	

}