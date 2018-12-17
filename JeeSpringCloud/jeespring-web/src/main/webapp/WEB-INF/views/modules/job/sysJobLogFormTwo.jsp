<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务调度日志管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/modules/job//sysJobLogForm.js" type="text/javascript"></script>
	<link href="/staticViews/modules/job//sysJobLogForm.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<form:form id="inputForm" modelAttribute="sysJobLog" action="${ctx}/job/sysJobLog/save" method="post" class="form-horizontal  content-background">
		<div class="content">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="width-100 table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active" valign="top"><label class="pull-left"><font color="red">*</font>任务名称：</label></td>
					<td class="width-35">
							<form:input path="jobName" htmlEscape="false" maxlength="64" class="form-control required"/>
						<div class="help-block">请填写任务名称</div>
						</td>
					<td class="width-15 active" valign="top"><label class="pull-left"><font color="red">*</font>任务组名：</label></td>
					<td class="width-35">
							<form:input path="jobGroup" htmlEscape="false" maxlength="64" class="form-control required"/>
						<div class="help-block">请填写任务组名</div>
						</td>
					</tr>
				<tr>
					<td class="width-15 active" valign="top"><label class="pull-left">任务方法：</label></td>
					<td class="width-35">
							<form:input path="methodName" htmlEscape="false" maxlength="500" class="form-control "/>
						<div class="help-block">请填写任务方法</div>
						</td>
					<td class="width-15 active" valign="top"><label class="pull-left">方法参数：</label></td>
					<td class="width-35">
							<form:input path="methodParams" htmlEscape="false" maxlength="200" class="form-control "/>
						<div class="help-block">请填写方法参数</div>
						</td>
					</tr>
				<tr>
					<td class="width-15 active" valign="top"><label class="pull-left">日志信息：</label></td>
					<td class="width-35">
							<form:input path="jobMessage" htmlEscape="false" maxlength="500" class="form-control "/>
						<div class="help-block">请填写日志信息</div>
						</td>
					<td class="width-15 active" valign="top"><label class="pull-left">执行状态（0正常 1失败）：</label></td>
					<td class="width-35">
							<form:radiobuttons path="status" items="${fns:getDictList('job_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
							<div class="help-block">请选择执行状态（0正常 1失败）</div>
						</td>
					</tr>
				<tr>
					<td></td></tr><tr>
					<td class="width-15 active" valign="top"><label class="pull-left">异常信息：</label></td>
					<td class="width-35" colspan="3">
							<form:textarea path="exceptionInfo" htmlEscape="false" rows="4" class="form-control "/>
							<sys:ckeditor replace="exceptionInfo" height="400" uploadPath="/job/sysJobLog" />
						</td>
					</tr>
			</tbody>
		</table>
			<div id="iframeSave" class="form-group  ${action}">
				<a class="btn btn-success" onclick="doSubmit();">保存</a>
				<a class="btn btn-primary" onclick="javascript:history.back(-1);">返回</a>
				<!--a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a-->
			</div>
		</div>
	</form:form>
</body>
</html>