package com.jeespring.modules.echarts.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeespring.common.web.AbstractBaseController;
@Controller
@RequestMapping(value = "${adminPath}/echarts/radar")
public class RadarController extends AbstractBaseController {
	
	private static final long serialVersionUID = 7375363226310112119L;
	
	private List<Map<String,Object>> orientData;
	@RequestMapping(value = {"index", ""})
	public String index( HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("orientData", getorientData8());
		return "modules/echarts/radar";
		
	}
	public List<Map<String,Object>> getorientData8(){
		orientData = new ArrayList<Map<String,Object>>();
		
		Double[] dataArr1 = new Double[]{0.1*100,0.2*100,0.3*100,0.1*100,0.05*100,0.05*100,0.1*100,0.1*100};
		Map<String,Object> mapData1 = new HashMap<String,Object>();
		mapData1.put("dataArr", dataArr1);
		mapData1.put("title", "玫瑰图1");
		orientData.add(mapData1);
		
		Double[] dataArr2 = new Double[]{0.05*100,0.05*100,0.1*100,0.1*100,0.1*100,0.2*100,0.3*100,0.1*100};
		Map<String,Object> mapData2 = new HashMap<String,Object>();
		mapData2.put("dataArr", dataArr2);
		mapData2.put("title", "玫瑰图2");
		orientData.add(mapData2);
		
		return orientData;
		
	}
}
