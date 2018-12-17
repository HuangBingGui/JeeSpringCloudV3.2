<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
	双数值轴折线:
	<div id="line_doubleNum"  class="main000"></div>
	<echarts:lineDoubleNum 
	    id="line_doubleNum"
		title="双数值轴折线" 
		subtitle="短期预测数据对比曲线"
		xAxisName="预测时间"
		yAxisName="实际电量(MW)" 
		axisDataArr="${axisDataArr}"/>