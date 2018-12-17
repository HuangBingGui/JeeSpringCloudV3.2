<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
		    <%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/infoEdit"  method="post" class="form-horizontal form-group">
		<div class="control-group">
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50"  class="form-control  max-width-250 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="form-control  max-width-250 email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" class="form-control  max-width-250 " maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" class="form-control  max-width-250 required" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control  max-width-250 "/>
			</div>
		</div>
	</form:form>
</body>
</html>