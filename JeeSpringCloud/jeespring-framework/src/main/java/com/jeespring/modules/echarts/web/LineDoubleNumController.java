package com.jeespring.modules.echarts.web;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeespring.common.web.AbstractBaseController;
@Controller
@RequestMapping(value = "${adminPath}/echarts/linedoublenum")
public class LineDoubleNumController extends AbstractBaseController {
	private static final long serialVersionUID = -6886697421555222670L;
	
	private Map<String,Double[][]> axisDataArr;

	@RequestMapping(value = {"index", ""})
	public String index( HttpServletRequest request, HttpServletResponse response, Model model) {

		//x+y轴数据Double[x轴数据][y轴数据]
		request.setAttribute("axisDataArr", getaxisDataArr());
		return "modules/echarts/lineDoubleNum";
	}
	
	public Map<String,Double[][]> getaxisDataArr(){
		
		Random random = new Random();
		axisDataArr = new HashMap<String,Double[][]>();
		
		Double[][] data1 = new Double[10][2];
		for(int i=0;i<10;i++){
			data1[i][0]=i+0.0;
			data1[i][1]=random.nextInt(10)+0.0;
		}
		axisDataArr.put("曲线一", data1);
		
		Double[][] data2 = new Double[10][2];
		for(int i=0;i<10;i++){
			data2[i][0]=i+1.0;
			data2[i][1]=random.nextInt(10)+0.0;
		}
		axisDataArr.put("曲线二", data2);
		
		return axisDataArr;
	}	

}
