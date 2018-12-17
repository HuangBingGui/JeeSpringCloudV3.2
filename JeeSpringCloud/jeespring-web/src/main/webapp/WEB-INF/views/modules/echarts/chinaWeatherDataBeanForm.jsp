<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>城市气温管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#datestr', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="chinaWeatherDataBean" action="${ctx}/echarts/chinaWeatherDataBean/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">日期：</label></td>
					<td class="width-35">
						<input id="datestr" name="datestr" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${chinaWeatherDataBean.datestr}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">北京最高气温：</label></td>
					<td class="width-35">
						<form:input path="beijingMaxTemp" htmlEscape="false" class="form-control  number"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right">北京最低气温：</label></td>
					<td class="width-35">
						<form:input path="beijingMinTemp" htmlEscape="false" class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">长春最高气温：</label></td>
					<td class="width-35">
						<form:input path="changchunMaxTemp" htmlEscape="false" class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">长春最低气温：</label></td>
					<td class="width-35">
						<form:input path="changchunMinTemp" htmlEscape="false" class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">沈阳最高气温：</label></td>
					<td class="width-35">
						<form:input path="shenyangMaxTemp" htmlEscape="false" class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">沈阳最低气温：</label></td>
					<td class="width-35">
						<form:input path="shenyangMinTemp" htmlEscape="false" class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">哈尔滨最高气温：</label></td>
					<td class="width-35">
						<form:input path="haerbinMaxTemp" htmlEscape="false" class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">哈尔滨最低气温：</label></td>
					<td class="width-35">
						<form:input path="haerbinMinTemp" htmlEscape="false" class="form-control  number"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>