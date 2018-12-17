<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户中心管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/formSubmit.js" type="text/javascript"></script>
</head>
<body class="gray-bg">
		<form:form id="inputForm" modelAttribute="sysUserCenter" action="${ctx}/usercenter/sysUserCenter/save" method="post" class="form-horizontal  content-background">
		<div class="content">
			<form:hidden path="id"/>
			<sys:message content="${message}"/>
				<div class="form-group">
					<label class="pull-left">用户编号：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
						<form:input path="userId" htmlEscape="false" maxlength="64" class="form-control "/>
						<div class="help-block">请填写用户编号</div>
					</div>
				</div>
				<div class="form-group">
					<label class="pull-left">用户名称：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
						<form:input path="userName" htmlEscape="false" maxlength="64" class="form-control "/>
						<div class="help-block">请填写用户名称</div>
					</div>
				</div>
				<div class="form-group">
					<label class="pull-left">用户手机号：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
						<form:input path="userPhone" htmlEscape="false" maxlength="64" class="form-control "/>
						<div class="help-block">请填写用户手机号</div>
					</div>
				</div>
				<div class="form-group">
					<label class="pull-left">纬度：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
						<form:input path="lat" htmlEscape="false" maxlength="30" class="form-control "/>
						<div class="help-block">请填写纬度</div>
					</div>
				</div>
				<div class="form-group">
					<label class="pull-left">经度：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
						<form:input path="lng" htmlEscape="false" maxlength="30" class="form-control "/>
						<div class="help-block">请填写经度</div>
					</div>
				</div>
				<div class="form-group">
					<label class="pull-left">城市/地址/IP：</label>
					<div class="col-sm-9 col-lg-10 col-xs-12">
							<form:input path="city" htmlEscape="false" maxlength="30" class="form-control "/>
							<form:input path="address" htmlEscape="false" maxlength="30" class="form-control "/>
							<form:input path="ip" htmlEscape="false" maxlength="30" class="form-control "/>
							<div class="help-block">请填写城市/地址/IP</div>
					</div>
				</div>
		</form:form>
			<div id="iframeSave" class="form-group ${action}">
				<a class="btn btn-success" onclick="doSubmit();">保存</a>
				<a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a>
			</div>
		</div>
</body>
</html>