package com.jeespring.modules.echarts.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.modules.echarts.entity.ChinaWeatherDataBean;
import com.jeespring.modules.echarts.service.ChinaWeatherDataBeanService;
@Controller
@RequestMapping(value = "${adminPath}/echarts/line")
public class LineController extends AbstractBaseController {
	
	private static final long serialVersionUID = -6886697421555222670L;
	
	@Autowired
	private ChinaWeatherDataBeanService chinaWeatherDataBeanService;
	
	@RequestMapping(value = {"index", ""})
	public String index(ChinaWeatherDataBean chinaWeatherDataBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		
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
		return "modules/echarts/line";
	}
	
}
