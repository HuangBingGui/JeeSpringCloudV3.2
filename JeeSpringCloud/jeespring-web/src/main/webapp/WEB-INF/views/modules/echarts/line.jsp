<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
	单轴：
	<div id="line_normal"  class="main000"></div>
    <echarts:line 
        id="line_normal"
		title="2011年温度对比曲线" 
		subtitle="主要城市的温度对比曲线"
		xAxisData="${xAxisData}" 
		yAxisData="${yAxisData}" 
		xAxisName="预测时间"
		yAxisName="温度(℃)" />
		
	双轴：
	<div id="line_yAxisIndex"  class="main000"></div>
	<echarts:line 
	    id="line_yAxisIndex"
		title="2011年温度对比曲线" 
		subtitle="主要城市的温度对比曲线"
		xAxisData="${xAxisData}" 
		yAxisData="${yAxisData}" 
		xAxisName="预测时间"
		yAxisName="最高温度(℃),最低温度(℃)" 
		yAxisIndex="${yAxisIndex}"/>
