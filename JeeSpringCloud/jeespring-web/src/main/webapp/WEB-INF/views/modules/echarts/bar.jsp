<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	单轴：
	<div id="line_normal"  class="main000"></div>
	<echarts:bar 
	  	id="line_normal"
		title="短期预测数据对比曲线" 
		subtitle="短期预测数据对比曲线"
		xAxisData="${xAxisData}" 
		yAxisData="${yAxisData}" 
		xAxisName="预测时间"
		yAxisName="实际电量(MW)" 
		/>
	双轴：
	<div id="line_yAxisIndex"  class="main000"></div>
	<echarts:bar
		id="line_yAxisIndex"
		title="短期预测数据对比曲线" 
		subtitle="短期预测数据对比曲线"
		xAxisData="${xAxisData}" 
		yAxisData="${yAxisData}" 
		xAxisName="预测时间"
		yAxisName="实际电量(MW),实际总辐射(w/㎡)" 
		yAxisIndex="${yAxisIndex}"/>
