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

public class EChartsLineTimeLineTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String subtitle;
	private String xAxisName;
	private String yAxisName;
	private List<String> xAxisData;
	private Map<String, Integer> yAxisIndex;
	private Map<String, List<Double>> yAxisData;
	private List<String> timelineData;
	private List<Map<String, List<Double>>> timelineAxisData;

	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("require([ 'echarts', 'echarts/chart/line'], function(ec) {");
		sb.append("var myChart= ec.init(document.getElementById('" + id
				+ "'));");
		GsonOption option = new GsonOption();

		GsonOption options = new GsonOption();
		/**
		 * timeline:{ data:[
		 * '2002-01-01','2003-01-01','2004-01-01','2005-01-01','2006-01-01',
		 * '2007-01-01','2008-01-01','2009-01-01','2010-01-01','2011-01-01' ],
		 * label : { formatter : function(s) { return s.slice(0, 4); } },
		 * autoPlay : true, playInterval : 1000 },
		 */
		option.timeline().autoPlay(true).playInterval(1000).label()
				.formatter("function(s){return s.slice(0, 4);}");
		for (String key : timelineData) {
			option.timeline().data(key);
		}
		/**
		 * title : { 'text':'2002全国宏观经济指标', 'subtext':'数据来自国家统计局' },
		 */
		options.title(title, subtitle);
		/**
		 * tooltip : {'trigger':'axis'},
		 */
		options.tooltip().trigger(Trigger.axis);
		/**
		 * legend : { x:'right', 'data':['GDP','金融','房地产','第一产业','第二产业','第三产业'],
		 * 'selected':{ 'GDP':true, '金融':false, '房地产':true, '第一产业':false,
		 * '第二产业':false, '第三产业':false } },
		 */
		options.legend().x(X.right);
		for (String key : yAxisData.keySet()) {
			options.legend().data(key);
		}
		/**
		 * toolbox : { 'show':true, orient : 'vertical', x: 'right', y:
		 * 'center', 'feature':{ 'mark':{'show':true},
		 * 'dataView':{'show':true,'readOnly':false},
		 * 'magicType':{'show':true,'type':['line','bar','stack','tiled']},
		 * 'restore':{'show':true}, 'saveAsImage':{'show':true} } }, calculable
		 * : true,
		 */
		// 工具栏
		options.toolbox().orient(Orient.vertical).x(X.right).y(Y.center)
				.show(true).feature(
				Tool.mark,
				Tool.dataView,
						Tool.saveAsImage,
						//new MagicType(Magic.line, Magic.bar,Magic.stack,Magic.tiled),
						Tool.dataZoom, Tool.restore);
		options.calculable(true);
		options.dataZoom().show(true).realtime(true).start(0).end(100);
		/**
		 * xAxis : [{ 'type':'category', 'axisLabel':{'interval':0}, 'data':[
		 * '北京','\n天津','河北','\n山西','内蒙古','\n辽宁','吉林','\n黑龙江',
		 * '上海','\n江苏','浙江','\n安徽','福建','\n江西','山东','\n河南',
		 * '湖北','\n湖南','广东','\n广西','海南','\n重庆','四川','\n贵州',
		 * '云南','\n西藏','陕西','\n甘肃','青海','\n宁夏','新疆' ] }],
		 */
		// X轴数据封装并解析
		ValueAxis valueAxis = new ValueAxis();
		for (String s : xAxisData) {
			valueAxis.type(AxisType.category).data(s);
		}
		// X轴单位
		valueAxis.name(xAxisName);
		options.xAxis(valueAxis);
		/**
		 * yAxis : [ { 'type':'value', 'name':'GDP（亿元）', 'max':53500 }, {
		 * 'type':'value', 'name':'其他（亿元）' } ],
		 */
		// Y轴数据封装并解析
		String[] unitNameArray = yAxisName.split(",");
		for (String s : unitNameArray) {
			CategoryAxis categoryAxis = new CategoryAxis();
			categoryAxis.type(AxisType.value);
			options.yAxis(categoryAxis.name(s));
		}

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
					// 显示直线，而不是密密麻麻的点，一点都不好看
					line.symbol(Symbol.none);
				} else {
					line.type(SeriesType.line).yAxisIndex(0);
					line.symbol(Symbol.none);
				}

			}
			options.series(line);
		}
		option.options(options);
		for (int ii = 1; ii < timelineData.size(); ii++) {
			Map<String, List<Double>> timelineAxisDataMap = timelineAxisData.get(ii - 1);
			GsonOption timeLineOption = new GsonOption();

			timeLineOption.title(timelineData.get(ii) + title.substring(4, title.length()),subtitle);
			for (String key : timelineAxisDataMap.keySet()) {
				// 遍历list得到数据
				List<Double> list = timelineAxisDataMap.get(key);
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
						line.type(SeriesType.line).yAxisIndex(
								yAxisIndex.get(key));
						// 显示直线，而不是密密麻麻的点，一点都不好看
						line.symbol(Symbol.none);
					} else {
						line.type(SeriesType.line).yAxisIndex(0);
						line.symbol(Symbol.none);
					}

				}
				timeLineOption.series(line);
			}
			option.options(timeLineOption);
		}
		sb.append("var option=" + option.toString() + ";");
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

	public List<String> getTimelineData() {
		return timelineData;
	}

	public void setTimelineData(List<String> timelineData) {
		this.timelineData = timelineData;
	}

	public List<Map<String, List<Double>>> getTimelineAxisData() {
		return timelineAxisData;
	}

	public void setTimelineAxisData(
			List<Map<String, List<Double>>> timelineAxisData) {
		this.timelineAxisData = timelineAxisData;
	}

}
