package com.jeespring.common.tag.echarts;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

public class EChartsPieTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String subtitle;
	private Map<String, Object> orientData;

	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("require([ 'echarts','echarts/chart/pie'], function(ec) {");
		sb.append("var myChart= ec.init(document.getElementById('" + id+ "'));");
		// 创建GsonOption对象，即为json字符串
		GsonOption option = new GsonOption();
		option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
		option.title(title, subtitle);
		// 工具栏
		option.toolbox().show(true).feature(
		Tool.mark,
		Tool.dataView,
				Tool.saveAsImage,
				//Tool.magicType,
				//new MagicType(Magic.line, Magic.bar,Magic.pie,Magic.stack,Magic.tiled),
				Tool.dataZoom, Tool.restore
				);
		option.calculable(true);
		
		// 数据轴封装并解析
		for(String xdata : orientData.keySet()) {
			//option.legend().orient(Orient.horizontal).x(X.left).y(Y.bottom).data(xdata);
			option.legend().orient(Orient.vertical).x(X.left).y(Y.bottom).data(xdata);

		}
		
		if (orientData != null) {
			Line line = new Line();
			line.name(title).type(SeriesType.pie);
			for (String title : orientData.keySet()) {
				Object value = orientData.get(title);		
				Data data = new Data().name(title);
				data.value(value);
				line.data(data);
			}
			option.series(line);
		}
		sb.append("var option=" + option.toString() + ";\n");
		/*sb.append("option.series[0].label={\nnormal: { formatter: ' {b|{b}：}{c}  {per|{d}%}  ',");
		sb.append("backgroundColor: '#eee',borderColor: '#aaa',borderWidth: 1,borderRadius: 4,");
		sb.append("rich: {a: {color: '#999',lineHeight: 22,align: 'center'},");
		sb.append("hr: {borderColor: '#aaa',width: '100%',borderWidth: 0.5,height: 0},");
		sb.append("b: {fontSize: 16,lineHeight: 33},");
		sb.append("per: {color: '#eee',backgroundColor: '#334455',padding: [2, 4],borderRadius: 2}");
		sb.append("}}\n};\n");*/
		sb.append("myChart.setOption(option);\n");
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

	public Map<String, Object> getOrientData() {
		return orientData;
	}

	public void setOrientData(Map<String, Object> orientData) {
		this.orientData = orientData;
	}
	
}
