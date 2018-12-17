package com.jeespring.modules.echarts.web;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeespring.common.web.AbstractBaseController;
@Controller
@RequestMapping(value = "${adminPath}/echarts/pie")
public class PieController extends AbstractBaseController {
	
	private static final long serialVersionUID = 7375363226310112119L;
	
	private Map<String,Object> orientData;
	@RequestMapping(value = {"index", ""})
	public String index( HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("orientData", getorientData());
		return "modules/echarts/pie";
	}
	public Map<String,Object> getorientData(){
		orientData = new HashMap<String,Object>();
		orientData.put("直接访问", 335);
		orientData.put("邮件营销", 310);
		orientData.put("联盟广告", 234);
		orientData.put("视频广告", 135);
		orientData.put("搜索引擎", 1548);
		return orientData;
	}
}
