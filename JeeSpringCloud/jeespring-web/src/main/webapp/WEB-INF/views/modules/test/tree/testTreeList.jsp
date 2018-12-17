<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>树管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>树管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="筛选"><i
                        class="fa fa-filter"></i>筛选</a>
                <shiro:hasPermission name="test:tree:testTree:add">
                    <a id="btnAdd" href="${ctx}/test/tree/testTree/form" class="btn btn-default btn-sm" title="新增"><i
                            class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <button data-placement="left" onclick="refresh()"
                        class="btn btn-default btn-sm" title="刷新"><i class="glyphicon glyphicon-repeat"></i>刷新
                </button>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!--查询条件-->
			<form:form id="searchForm" modelAttribute="testTree" action="${ctx}/test/tree/testTree/" method="post" class="form-inline">
					<div class="form-group">
						<label>名称：</label>
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control input-sm"/>
					</div>
				<div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                </div>
			</form:form>

			<table id="treeTable" class="table table-hover table-condensed dataTables-example dataTable">
				<thead>
					<tr>
						<th>名称</th>
						<th>备注信息</th>
						<shiro:hasPermission name="test:tree:testTree:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody id="treeTableList"></tbody>
			</table>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data = ${fns:toJson(list)}, ids = [], rootIds = [];
		for (var i=0; i<data.length; i++){
			ids.push(data[i].id);
		}
		ids = ',' + ids.join(',') + ',';
		for (var i=0; i<data.length; i++){
			if (ids.indexOf(','+data[i].parentId+',') == -1){
				if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
					rootIds.push(data[i].parentId);
				}
			}
		}
		for (var i=0; i<rootIds.length; i++){
			addRow("#treeTableList", tpl, data, rootIds[i], true);
		}
		$("#treeTable").treeTable({expandLevel : 5});
	});
	function addRow(list, tpl, data, pid, root){
		for (var i=0; i<data.length; i++){
			var row = data[i];
			if ((${fns:jsGetVal('row.parentId')}) == pid){
				$(list).append(Mustache.render(tpl, {
					dict: {
					blank123:0}, pid: (root?0:pid), row: row
				}));
				addRow(list, tpl, data, row.id);
			}
		}
	}

	function refresh(){//刷新

		window.location="${ctx}/test/tree/testTree/";
	}
</script>
<script type="text/template" id="treeTableTpl">
	<tr id="{{row.id}}" pId="{{pid}}">
		<td><a  href="#" onclick="openDialogView('查看树', '${ctx}/test/tree/testTree/form?id={{row.id}}','800px', '500px')">
			{{row.name}}
		</a></td>
		<td>
			{{row.remarks}}
		</td>
		<td>
		<shiro:hasPermission name="test:tree:testTree:view">
			<a href="${ctx}/test/tree/testTree/form?id={{row.id}}" title="查看"><i class="fa fa-search-plus"></i></a>
			</shiro:hasPermission>
		<shiro:hasPermission name="test:tree:testTree:edit">
			<a href="${ctx}/test/tree/testTree/form?id={{row.id}}" title="修改"><i class="fa fa-pencil"></i></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="test:tree:testTree:del">
			<a href="${ctx}/test/tree/testTree/delete?id={{row.id}}" onclick="return confirmx('确认要删除该树及所有子树吗？', this.href)" title="删除"><i class="fa fa-trash-o"></i></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="test:tree:testTree:add">
			<a href="${ctx}/test/tree/testTree/form?parent.id={{row.id}}"><i class="fa fa-plus"></i> 添加下级树</a>
		</shiro:hasPermission>
		</td>
	</tr>
</script>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
</body>
</html>