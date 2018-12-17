<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="com.jeespring.common.web.Servlets"%>
<%
	response.setStatus(404);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print("页面不存在.");
}

//输出异常信息页面
else {
%>
<!DOCTYPE html>
<html>

<head>
  <title>404 页面</title>
  <link href="${ctxStatic}/common/css/style.css?v=3.2.0" type="text/css" rel="stylesheet" />
</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到！</h3>

        <div class="error-desc">
            抱歉，页面好像去火星了~
        </div>
    </div>

</body>

</html>

<%}%>