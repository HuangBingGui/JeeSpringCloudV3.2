package com.jeespring.common.tag.echarts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

public class EChartsLineTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id ;
	private String title;
	private String subtitle;
	private String xAxisName;
	private String yAxisName;
	private List<String> xAxisData;	
	private Map<String, Integer> yAxisIndex;
	private Map<String, List<Double>> yAxisData;
	
	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("require([ 'echarts', 'echarts/chart/line','echarts/chart/bar'], function(ec) {");
		sb.append("var myChart= ec.init(document.getElementById('"+id+"'));");
		// 创建GsonOption对象，即为json字符串
		GsonOption option = new GsonOption();
		option.tooltip().trigger(Trigger.axis);
		option.title(title, subtitle);
		// 工具栏
		option.toolbox().show(true).feature(
		 Tool.mark,
		 Tool.dataView,
				Tool.saveAsImage,
				Tool.magicType,
				new MagicType(Magic.line, Magic.bar,Magic.stack,Magic.tiled),
				Tool.dataZoom, Tool.restore);
		option.calculable(true);
		option.dataZoom().show(true).realtime(true).start(0).end(100);

		// X轴数据封装并解析
		ValueAxis valueAxis = new ValueAxis();
		for (String s : xAxisData) {
			valueAxis.type(AxisType.category).data(s);
		}
		// X轴单位
		valueAxis.name(xAxisName);
		option.xAxis(valueAxis);
		for (String key : yAxisData.keySet()) {
			option.legend().data(key);
		}
		// Y轴数据封装并解析
		String[] unitNameArray = yAxisName.split(",");
		for (String s : unitNameArray) {
			CategoryAxis categoryAxis = new CategoryAxis();
			categoryAxis.type(AxisType.value);
			option.yAxis(categoryAxis.name(s));
		}
		int i = 0;
		for (String key : yAxisData.keySet()) {
			// 遍历list得到数据
			List<Double> list = yAxisData.get(key);
			Line line = new Line().name(key);
			for (Double d : list) {
				// KW与MW单位的转换
				// if(settingGlobal!=null&&settingGlobal.getIskw()==0){
				// d = d/1000;
				// }
				// 数据为空的话会报错，为空则为零
				if (d != null) {
					line.type(SeriesType.line).data(d);
				} else {
					line.type(SeriesType.line).data(0);
				}

				if (yAxisIndex != null && yAxisIndex.get(key) != null) {
					line.type(SeriesType.line).yAxisIndex(yAxisIndex.get(key));
					line.symbol(Symbol.none);
				} else {
					line.type(SeriesType.line).yAxisIndex(0);
					//显示直线，而不是密密麻麻的点，一点都不好看
					line.symbol(Symbol.none);
				}
			}
			option.series(line);
			i++;
		}
		sb.append("var option="+option.toString()+";");
		sb.append("myChart.setOption(option);");
		sb.append("});");
		sb.append("</script>");
		try {
			this.pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			System.err.print(e);
		}
		return Tag.EVAL_PAGE;// 继续处理页面
	}

	@Override
    public String getId() {
		return id;
	}

	@Override
    public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getxAxisName() {
		return xAxisName;
	}

	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}

	public String getyAxisName() {
		return yAxisName;
	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	public List<String> getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(List<String> xAxisData) {
		this.xAxisData = xAxisData;
	}

	public Map<String, Integer> getyAxisIndex() {
		return yAxisIndex;
	}

	public void setyAxisIndex(Map<String, Integer> yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
	}

	public Map<String, List<Double>> getyAxisData() {
		return yAxisData;
	}

	public void setyAxisData(Map<String, List<Double>> yAxisData) {
		this.yAxisData = yAxisData;
	}
	
}
