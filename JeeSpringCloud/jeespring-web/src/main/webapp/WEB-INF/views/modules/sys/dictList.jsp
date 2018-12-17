<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
		    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox">
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>类型：</span>
				<form:select id="type" path="type" class="form-control m-b"><form:option value="" label=""/><form:options items="${typeList}" htmlEscape="false"/></form:select>
			<span>描述 ：</span>
				<form:input path="description" htmlEscape="false" maxlength="50" class="form-control"/>
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
			<shiro:hasPermission name="sys:dict:add">
				<table:addRow url="${ctx}/sys/dict/form" title="字典"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:dict:edit">
			    <table:editRow url="${ctx}/sys/dict/form" id="contentTable"  title="字典"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:dict:del">
				<table:delRow url="${ctx}/sys/dict/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
	       <button  class="btn btn-white btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		<!-- <div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div> -->
	</div>
	</div>
	
	<table id="contentTable" class="table table-hover table-condensed  dataTables-example dataTable no-footer">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column value">键值</th>
				<th >标签</th>
				<th  class="sort-column type">类型</th>
				<th  class="sort-column picture">图片</th>
				<th  class="sort-column description">描述</th>
				<th  class="sort-column sort">排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td> <input type="checkbox" id="${dict.id}" class="i-checks"></td>
				<td>${dict.value}</td>
				<td><a  href="#" onclick="openDialogView('查看字典', '${ctx}/sys/dict/form?id=${dict.id}','800px', '500px')">${dict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
				<td><img src="${dict.picture}" width="100px" height="50px"/></td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<td>
					<shiro:hasPermission name="sys:dict:view">
						<a href="#" onclick="openDialogView('查看字典', '${ctx}/sys/dict/form?id=${dict.id}','800px', '500px')" class="btn btn-success btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:dict:edit">
    					<a href="#" onclick="openDialog('修改字典', '${ctx}/sys/dict/form?id=${dict.id}','800px', '500px')" class="btn btn-white btn-sm" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:dict:del">
						<a href="${ctx}/sys/dict/delete?id=${dict.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)"   class="btn btn-white btn-sm"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:dict:add">
						<a href="#" onclick="openDialog('添加键值', '<c:url value='${fns:getAdminPath()}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>','800px', '500px')" class="btn btn-white btn-sm" ><i class="fa fa-plus"></i> 添加键值</a>
					</shiro:hasPermission>
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