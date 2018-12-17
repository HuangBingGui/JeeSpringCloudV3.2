<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>班级管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	<div class="wrapper wrapper-content">
	<div id="pie"  class="main000"></div>
	<echarts:pie
	    id="pie"
		title="班级统计" 
		subtitle="人数统计"
		orientData="${orientData}"/>
	<div class="ibox">
	<div class="ibox-title">
		<h5>班级列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="pieClass" action="${ctx}/echarts/pieClass/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>班级：</span>
				<form:input path="className" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="echarts:pieClass:add">
				<table:addRow url="${ctx}/echarts/pieClass/form" title="班级"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="echarts:pieClass:edit">
			    <table:editRow url="${ctx}/echarts/pieClass/form" title="班级" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="echarts:pieClass:del">
				<table:delRow url="${ctx}/echarts/pieClass/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="echarts:pieClass:import">
				<table:importExcel url="${ctx}/echarts/pieClass/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="echarts:pieClass:export">
	       		<table:exportExcel url="${ctx}/echarts/pieClass/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column className">班级</th>
				<th  class="sort-column num">人数</th>
				<th  class="sort-column remarks">备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pieClass">
			<tr>
				<td> <input type="checkbox" id="${pieClass.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看班级', '${ctx}/echarts/pieClass/form?id=${pieClass.id}','800px', '500px')">
					${pieClass.className}
				</a></td>
				<td>
					${pieClass.num}
				</td>
				<td>
					${pieClass.remarks}
				</td>
				<td>
					<shiro:hasPermission name="echarts:pieClass:view">
						<a href="#" onclick="openDialogView('查看班级', '${ctx}/echarts/pieClass/form?id=${pieClass.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="echarts:pieClass:edit">
    					<a href="#" onclick="openDialog('修改班级', '${ctx}/echarts/pieClass/form?id=${pieClass.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="echarts:pieClass:del">
						<a href="${ctx}/echarts/pieClass/delete?id=${pieClass.id}" onclick="return confirmx('确认要删除该班级吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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