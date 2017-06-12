<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>${functionNameSimple}管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		<#list table.columnList as c>
		<#if c.isQuery?? && c.isQuery == "1">
		<#if c.showType == "dateselect" && c.queryType == "between">
	        laydate({
	            elem: '#begin${c.simpleJavaField?cap_first}', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#end${c.simpleJavaField?cap_first}', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		<#elseif c.showType == "dateselect">
			laydate({
	            elem: '#${c.javaFieldId}', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		</#if>
		</#if>
		</#list>	
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${r"${ctx}"}/${urlPrefix}/">${functionNameSimple}列表</a></li>
		<shiro:hasPermission name="${permissionPrefix}:edit"><li><a href="${r"${ctx}"}/${urlPrefix}/form">${functionNameSimple}添加</a></li></shiro:hasPermission>
	</ul>
	<div class="wrapper wrapper-content">
	<sys:message content="${r"${message}"}"/>
	<div class="row">
	<div class="col-sm-12">
	<div style="float:right">
	<form id="searchForm" modelAttribute="${className}" action="${r"${ctx}"}/${urlPrefix}/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${r"${page.pageNo}"}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${r"${page.pageSize}"}"/>
		<div class="form-group">
		<#list table.columnList as c>
			<#if c.isQuery?? && c.isQuery == "1">
			<span>${c.comments}：</span>
			<#if c.showType == "input" || c.showType == "textarea">
				<input id="${c.javaFieldId}" name="${c.javaFieldId}" type="text" <#if c.dataLength != "0"> maxlength="${c.dataLength}"</#if>  class=" form-control input-sm"/>
			<#elseif c.showType == "select">
				<form:select path="${c.javaFieldId}"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${"$"}{fns:getDictList('${c.dictType}')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<#elseif c.showType == "checkbox">
				<form:checkboxes path="${c.javaFieldId}" items="${"$"}{fns:getDictList('${c.dictType}')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			<#elseif c.showType == "radiobox">
				<form:radiobuttons class="i-checks" path="${c.javaFieldId}" items="${"$"}{fns:getDictList('${c.dictType}')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			<#elseif c.showType == "dateselect" && c.queryType == "between">
				<input id="begin${c.simpleJavaField?cap_first}" name="begin${c.simpleJavaField?cap_first}" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${"$"}{${className}.begin${c.simpleJavaField?cap_first}}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="end${c.simpleJavaField?cap_first}" name="end${c.simpleJavaField?cap_first}" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${"$"}{${className}.end${c.simpleJavaField?cap_first}}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<#elseif c.showType == "dateselect">
				<input id="${c.javaFieldId}" name="${c.javaFieldId}" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${"$"}{${className}.${c.javaFieldId}}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<#elseif c.showType == "userselect">
				<sys:treeselect id="${c.simpleJavaField}" name="${c.javaFieldId}" value="${"$"}{${className}.${c.javaFieldId}}" labelName="${c.javaFieldName}" labelValue="${"$"}{${className}.${c.javaFieldName}}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<#elseif c.showType == "officeselect">
				<sys:treeselect id="${c.simpleJavaField}" name="${c.javaFieldId}" value="${"$"}{${className}.${c.javaFieldId}}" labelName="${c.javaFieldName}" labelValue="${"$"}{${className}.${c.javaFieldName}}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<#elseif c.showType == "areaselect">
				<sys:treeselect id="${c.simpleJavaField}" name="${c.javaFieldId}" value="${"$"}{${className}.${c.javaFieldId}}" labelName="${c.javaFieldName}" labelValue="${"$"}{${className}.${c.javaFieldName}}"
					title="区域" url="/sys/area/treeData" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			</#if>
			</#if>
		</#list>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		 </div>	
	</form>
	</div>
	</div>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<#list table.columnList as c>
					<#if c.isList?? && c.isList == "1">
				<th>${c.comments}</th>
					</#if>
				</#list>
				<shiro:hasPermission name="${permissionPrefix}:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${r"${page.list}"}" var="${className}">
			<tr>
				<#assign firstListField = true>
				<#list table.columnList as c>
					<#if c.isList?? && c.isList == "1">
				<td><#if firstListField><a href="${r"${ctx}"}/${urlPrefix}/form?id=${"${"+className+".id}"}"></#if>
				<#if c.simpleJavaType == "Date">
					<fmt:formatDate value="${"$"}{${className}.${c.javaFieldId}}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<#elseif c.showType == "select" || c.showType == "checkbox" || c.showType == "radiobox">
					${"$"}{fns:getDictLabel(${className}.${c.javaFieldId}, '${c.dictType}', '')}
				<#elseif c.showType == "userselect" || c.showType == "officeselect" || c.showType == "areaselect">
					${"$"}{${className}.${c.javaFieldName}}
				<#else>
					${"$"}{${className}.${c.javaFieldId}}
				</#if>
				<#if firstListField></a></#if></td>
						<#assign firstListField = false>
					</#if>
				</#list>
				<shiro:hasPermission name="${permissionPrefix}:edit"><td>
    				<a href="${r"${ctx}"}/${urlPrefix}/form?id=${"${"+className+".id}"}" class="btn btn-info btn-xs" ><i class="fa fa-file-text-o"></i> 修改</a>
					<a href="${r"${ctx}"}/${urlPrefix}/delete?id=${"${"+className+".id}"}" onclick="return confirmx('确认要删除该${functionNameSimple}吗？', this.href)"   class="btn btn-info btn-xs btn-danger"><i class="fa fa-trash"></i> 删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	${r"${page}"}
	</div>
</body>
</html>