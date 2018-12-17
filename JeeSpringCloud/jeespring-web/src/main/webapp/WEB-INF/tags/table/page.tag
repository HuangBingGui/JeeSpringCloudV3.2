<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="page" type="com.jeespring.common.persistence.Page" required="true"%>
<%-- 表格分页工具栏，使用方法： 原样输出page --%>
${page.toStringPage()}