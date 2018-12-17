package com.jeespring.common.tag.echarts;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

public class EChartsRadarTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String subtitle;
	private Integer polarType;
	private List<Map<String, Object>> orientData;

	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("require([ 'echarts', 'echarts/chart/radar'], function(ec) {");
		sb.append("var myChart= ec.init(document.getElementById('" + id+ "'));");
		// 创建GsonOption对象，即为json字符串
		GsonOption option = new GsonOption();
		/**
		 * title: { text: '实时风向玫瑰图', subtext: '预测时间:' },
		 */
		option.title(title, subtitle);
		/**
		 * tooltip: { trigger: 'axis' },
		 */
		option.tooltip().trigger(Trigger.axis);
		/**
		 * polar: [ { indicator: [ { text: '正北（N）', max: 100 }, { text:
		 * '西北（NW）', max: 100 }, { text: '正西（W）', max: 100 }, { text: '西南（SW）',
		 * max: 100 }, { text: '正南（S）', max: 100 }, { text: '东南（SE）', max: 100
		 * }, { text: '正东（E）', max: 100 }, { text: '东北（NE）', max: 100 } ] } ]
		 */
		// 工具栏
		option.toolbox().show(true).feature(
		Tool.mark,
		Tool.dataView,
				Tool.saveAsImage,
		//new MagicType(Magic.line, Magic.bar,Magic.stack,Magic.tiled),
		Tool.dataZoom,
		Tool.restore
				);
		Polar polar = new Polar();
		if (polarType == 8) {
			polar.indicator(new Data().text("正北（N）").max(100))
					.indicator(new Data().text("西北（NW）").max(100))
					.indicator(new Data().text("正西（W）").max(100))
					.indicator(new Data().text("西南（SW）").max(100))
					.indicator(new Data().text("正南（S）").max(100))
					.indicator(new Data().text("东南（SE）").max(100))
					.indicator(new Data().text("正东（E）").max(100))
					.indicator(new Data().text("东北（NE)").max(100));

		} else if (polarType == 16) {
			polar.indicator(new Data().text("正北（N）").max(100))
					.indicator(new Data().text("北西北（NNW）").max(100))
					.indicator(new Data().text("西北（NW）").max(100))
					.indicator(new Data().text("西北西（WNW）").max(100))
					.indicator(new Data().text("正西（W）").max(100))
					.indicator(new Data().text("西南西（WSW）").max(100))
					.indicator(new Data().text("西南（SW）").max(100))
					.indicator(new Data().text("南西南（SSW）").max(100))
					.indicator(new Data().text("正南（S）").max(100))
					.indicator(new Data().text("南东南（SSE）").max(100))
					.indicator(new Data().text("东南（SE）").max(100))
					.indicator(new Data().text("东南东（ESE）").max(100))
					.indicator(new Data().text("正东（E）").max(100))
					.indicator(new Data().text("东北东（ENE）").max(100))
					.indicator(new Data().text("东北（NE)").max(100))
					.indicator(new Data().text("北东北（NNE）").max(100));
		}
		option.polar(polar);
		option.calculable(true);

		/**
		 * legend: { orient: 'horizontal', x: 'left', y: 'bottom', data: [
		 * <c:forEach var="item" items="${towerList}" varStatus="status">
		 * '${item.tower_mater}米风向', </c:forEach> ] },
		 */
		
		if (orientData != null) {
			for (Map<String, Object> legendMap : orientData) {
				String title = legendMap.get("title").toString();
				option.legend().orient(Orient.horizontal).x(X.left).y(Y.bottom).data(title);
				Line line = new Line();
				Data data = new Data().name(title);
				Object[] dataArr = (Double[]) legendMap.get("dataArr");
				data.value(dataArr);
				line.type(SeriesType.radar).data(data);
				option.series(line);
			}
		}
		sb.append("var option="+option.toString()+";");
		sb.append("myChart.setOption(option);");
		sb.append("});");
		sb.append("</script>");
		try {
			this.pageContext.getOut().write(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
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

	public Integer getPolarType() {
		return polarType;
	}

	public void setPolarType(Integer polarType) {
		this.polarType = polarType;
	}

	public List<Map<String, Object>> getOrientData() {
		return orientData;
	}

	public void setOrientData(List<Map<String, Object>> orientData) {
		this.orientData = orientData;
	}
	

}
