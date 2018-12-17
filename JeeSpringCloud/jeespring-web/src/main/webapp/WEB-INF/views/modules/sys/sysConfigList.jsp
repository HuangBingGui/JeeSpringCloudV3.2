<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统配置管理</title>
	<meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">

    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysConfig" action="${ctx}/sys/sysConfig/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>类型：</span>
				<form:input path="type" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>数据值：</span>
				<form:input path="value" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>标签名：</span>
				<form:input path="label" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>描述：</span>
				<form:input path="description" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>排序（升序）：</span>
				<form:input path="sort" htmlEscape="false"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<button  class="btn btn-success btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<!--shiro:hasPermission name="sys:sysConfig:add"-->
				<table:addRow url="${ctx}/sys/sysConfig/form" title="系统配置"></table:addRow><!-- 增加按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="sys:sysConfig:edit"-->
			    <table:editRow url="${ctx}/sys/sysConfig/form" title="系统配置" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="sys:sysConfig:del"-->
				<table:delRow url="${ctx}/sys/sysConfig/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="sys:sysConfig:import"-->
				<table:importExcel url="${ctx}/sys/sysConfig/import"></table:importExcel><!-- 导入按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="sys:sysConfig:export"-->
	       		<table:exportExcel url="${ctx}/sys/sysConfig/export"></table:exportExcel><!-- 导出按钮 -->
	       	<!--/shiro:hasPermission-->
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			<button  class="btn btn-white btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		<div class="pull-right">
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column type">类型</th>
				<th  class="sort-column value">数据值</th>
				<th  class="sort-column label">标签名</th>
				<th  class="sort-column picture">图片</th>
				<th  class="sort-column description">描述</th>
				<th  class="sort-column sort">排序（升序）</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysConfig">
			<tr>
				<td>
				<input type="checkbox" id="${sysConfig.id}"
					type="${sysConfig.type}"
					value="${sysConfig.value}"
					label="${sysConfig.label}"
					sort="${sysConfig.sort}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看系统配置', '${ctx}/sys/sysConfig/form?id=${sysConfig.id}','800px', '500px')">
					${sysConfig.type}
				</a></td>
				<td>
					${sysConfig.value}
				</td>
				<td>
					${sysConfig.label}
				</td>
				<td>
				<img src="${sysConfig.picture}" width="100px" height="50px"/>
				</td>
				<td>
					<c:if test="${fn:length(sysConfig.description)>20}">${fn:substring(sysConfig.description,0,8)}...</c:if>
					<c:if test="${fn:length(sysConfig.description)<=20}">${sysConfig.description}</c:if>
				</td>
				<td>
					${sysConfig.sort}
				</td>
				<td>
					<!--shiro:hasPermission name="sys:sysConfig:view"-->
						<a href="#" onclick="openDialogView('查看系统配置', '${ctx}/sys/sysConfig/form?id=${sysConfig.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
					<!--/shiro:hasPermission-->
					<!--shiro:hasPermission name="sys:sysConfig:edit"-->
    					<a href="#" onclick="openDialog('修改系统配置', '${ctx}/sys/sysConfig/form?id=${sysConfig.id}','800px', '500px')" class="btn btn-success btn-sm" ><i class="fa fa-edit"></i> 修改</a>
    				<!--/shiro:hasPermission-->
    				<!--shiro:hasPermission name="sys:sysConfig:del"-->
						<a href="${ctx}/sys/sysConfig/delete?id=${sysConfig.id}" onclick="return confirmx('确认要删除该系统配置吗？', this.href)"   class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
					<!--/shiro:hasPermission-->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>