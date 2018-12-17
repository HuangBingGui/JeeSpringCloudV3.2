<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假单管理</title>
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
	<div class="ibox-title">
		<h5>请假单列表 </h5>
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
	<form:form id="searchForm" modelAttribute="formLeavem" action="${ctx}/mvvmoa/formLeavem/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>员工：</span>
				<sys:treeselect id="user" name="user.id" value="${formLeavem.user.id}" labelName="user.name" labelValue="${formLeavem.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<!--shiro:hasPermission name="mvvmoa:formLeavem:add"-->
				<table:addRow url="${ctx}/mvvmoa/formLeavem/form" title="请假单"></table:addRow><!-- 增加按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="mvvmoa:formLeavem:edit"-->
			    <table:editRow url="${ctx}/mvvmoa/formLeavem/form" title="请假单" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="mvvmoa:formLeavem:del"-->
				<table:delRow url="${ctx}/mvvmoa/formLeavem/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="mvvmoa:formLeavem:import"-->
				<table:importExcel url="${ctx}/mvvmoa/formLeavem/import"></table:importExcel><!-- 导入按钮 -->
			<!--/shiro:hasPermission-->
			<!--shiro:hasPermission name="mvvmoa:formLeavem:export"-->
	       		<table:exportExcel url="${ctx}/mvvmoa/formLeavem/export"></table:exportExcel><!-- 导出按钮 -->
	       	<!--/shiro:hasPermission-->
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
				<th  class="sort-column user.name">员工</th>
				<th  class="sort-column office.name">归属部门</th>
				<th  class="sort-column area.name">归属区域</th>
				<th  class="sort-column beginDate">请假开始日期</th>
				<th  class="sort-column endDate">请假结束日期</th>
				<th  class="sort-column remarks">备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="formLeavem">
			<tr>
				<td> <input type="checkbox" id="${formLeavem.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看请假单', '${ctx}/mvvmoa/formLeavem/form?id=${formLeavem.id}','800px', '500px')">
					${formLeavem.user.name}
				</a></td>
				<td>
					${formLeavem.office.name}
				</td>
				<td>
					${formLeavem.area.name}
				</td>
				<td>
					<fmt:formatDate value="${formLeavem.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${formLeavem.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${formLeavem.remarks}
				</td>
				<td>
					<!--shiro:hasPermission name="mvvmoa:formLeavem:view"-->
						<a href="#" onclick="openDialogView('查看请假单', '${ctx}/mvvmoa/formLeavem/form?id=${formLeavem.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<!--/shiro:hasPermission-->
					<!--shiro:hasPermission name="mvvmoa:formLeavem:edit"-->
    					<a href="#" onclick="openDialog('修改请假单', '${ctx}/mvvmoa/formLeavem/form?id=${formLeavem.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				<!--/shiro:hasPermission-->
    				<!--shiro:hasPermission name="mvvmoa:formLeavem:del"-->
						<a href="${ctx}/mvvmoa/formLeavem/delete?id=${formLeavem.id}" onclick="return confirmx('确认要删除该请假单吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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