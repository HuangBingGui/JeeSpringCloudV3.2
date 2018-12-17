package com.jeespring.modules.echarts.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeespring.common.web.AbstractBaseController;

@Controller
@RequestMapping(value = "${adminPath}/echarts/bar")
public class BarController extends AbstractBaseController {
	private static final long serialVersionUID = -6886697421555222670L;
	
	private List<String> xAxisData;
	private Map<String,List<Double>> yAxisData;
	private Map<String,Integer> yAxisIndex;
	
	@RequestMapping(value = {"index", ""})
	public String index( HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//x轴数据
		request.setAttribute("xAxisData", getxAxisData());
		//y轴数据
		request.setAttribute("yAxisData", getyAxisData());
		//Y轴双轴情况下的位置定位
		request.setAttribute("yAxisIndex", getyAxisIndex());
		
		return "modules/echarts/bar";
	}
	
	public List<String> getxAxisData(){
		xAxisData = new ArrayList<String>();
		xAxisData.add("2015-10-10");
		xAxisData.add("2015-10-11");
		xAxisData.add("2015-10-12");
		xAxisData.add("2015-10-13");
		xAxisData.add("2015-10-14");
		return xAxisData;
	}
	
	public Map<String,List<Double>> getyAxisData(){
		Random random = new Random();
		yAxisData = new HashMap<String,List<Double>>();
		
		List<Double> data1 = new ArrayList<Double>();
		data1.add(random.nextDouble());
		data1.add(random.nextDouble());
		data1.add(random.nextDouble());
		data1.add(random.nextDouble());
		data1.add(random.nextDouble());
		yAxisData.put("柱状一", data1);
		
		List<Double> data2 = new ArrayList<Double>();
		data2.add(random.nextDouble());
		data2.add(random.nextDouble());
		data2.add(random.nextDouble());
		data2.add(random.nextDouble());
		data2.add(random.nextDouble());
		yAxisData.put("柱状二", data2);
		
		return yAxisData;
	}
	
	public Map<String,Integer> getyAxisIndex(){
		yAxisIndex = new HashMap<String,Integer>();
		yAxisIndex.put("柱状一", 0);
		yAxisIndex.put("柱状二", 1);
		return yAxisIndex;
	}
	
	

}
