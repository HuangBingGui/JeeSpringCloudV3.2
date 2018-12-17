<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>

	<div id="pie"  class="main000"></div>
	<echarts:pie
	    id="pie"
		title="某站点用户访问来源" 
		subtitle="纯属虚构"
		orientData="${orientData}"/>