<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假管理</title>
	<%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
	<script src="/staticViews/modules/test/one/formLeaveList.js" type="text/javascript"></script>
	<link href="/staticViews/modules/test/one/formLeaveList.css" rel="stylesheet" />
</head>
<body>
<!-- 内容-->
<div class="wrapper" id="rrapp">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>请假管理</div>
            <div class="box-tools pull-right">
				<button  class="btn btn-success btn-sm " onclick="$('#searchForm').toggle();$('.fa-chevron').toggle();"  title="筛选">
					<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 筛选
				</button>
				<shiro:hasPermission name="test:one:formLeave:add">
				      <a id="btnAdd" href="${ctx}/test/one/formLeave/form" title="增加" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加</a>
                     <a id="btnAdd" href="${ctx}/test/one/formLeave/form?ViewFormType=FormTwo" title="增加2" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加2</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="test:one:formLeave:del">
					<a id="btnDeleteAll" href="${ctx}/test/one/formLeave/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="test:one:formLeave:import">
					<table:importExcel url="${ctx}/test/one/formLeave/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="test:one:formLeave:export">
					<table:exportExcel url="${ctx}/test/one/formLeave/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>
                 <a href="${ctx}/test/one/formLeave/list" title="Vue" class="btn btn-default btn-sm"><i
                        class="glyphicon glyphicon-repeat"></i>list</a>
                <shiro:hasPermission name="test:one:formLeave:total">
                <a href="${ctx}/test/one/formLeave/total" title="统计图表" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计图表</a>
                 <a href="${ctx}/test/one/formLeave/totalMap" title="统计地图" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计地图</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
			<!--查询条件-->
			<form:form id="searchForm" modelAttribute="formLeave" action="${ctx}/../rest/test/one/formLeave/list" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
					<div class="form-group">
					<span>员工：</span>
						<sys:treeselect id="user" name="user.id" value="${formLeave.user.id}" labelName="user.name" labelValue="${formLeave.user.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
					 </div>
			   <div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
				</div>
			</form:form>

			<!-- 表格 -->
			<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
				<thead>
					<tr>
						<th> <input type="checkbox" class="i-checks"></th>
						<th  class="sort-column user.name ">员工</th>
						<th  class="sort-column office.name ">归属部门</th>
						<th  class="sort-column area.name ">归属区域</th>
						<th  class="sort-column beginDate hidden-xs">请假开始日期</th>
						<th  class="sort-column endDate hidden-xs">请假结束日期</th>
						<th  class="sort-column remarks hidden-xs">备注信息</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr  v-for="item in page" >
						<td>
						<input type="checkbox" :id="item.id"
							user.id="${formLeave.user.id}"
							office.id="${formLeave.office.id}"
							area.id="${formLeave.area.id}"
							remarks="${formLeave.remarks}"
						class="i-checks"></td>
						<td  class=""><a  href="#" v-on:click="openDialogView('查看请假', '${ctx}/test/one/formLeave/form?id='+item.id,'800px', '500px')">
						{{item.user.name}}
						</a></td>
						<td  class="">
						{{item.office.name}}
						</td>
						<td  class="">
						{{item.area.name}}
						</td>
						<td  class="hidden-xs">
						{{item.beginDate}}
						</td>
						<td  class="hidden-xs">
						{{item.endDate}}
						</td>
						<td  class="hidden-xs">
						{{item.remarks}}
						</td>
                        <td>
                            <shiro:hasPermission name="test:one:formLeave:view">
                                <a href="${ctx}/test/one/formLeave/form?id=${formLeave.id}&action=view" title="查看"><i class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                             <shiro:hasPermission name="test:one:formLeave:edit">
                                <a href="${ctx}/test/one/formLeave/form?id=${formLeave.id}" title="修改"  title="修改"><i class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="test:one:formLeave:del">
                                <a href="${ctx}/test/one/formLeave/delete?id=${formLeave.id}" onclick="return confirmx('确认要删除该请假吗？', this.href)" title="删除"><i class="fa fa-trash-o"></i></a>
                            </shiro:hasPermission>
                        </td>
					</tr>
				</tbody>
			</table>
			<!-- 分页代码 -->
			<div v-html="result.html">
				{{result.html}}
			</div>
		</div>
	</div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<script src="/staticViews/viewBase.js"></script>
<script src="/static/vue/vue.min.js"></script>
<script src="/static/common/SpringUI.js"></script>
</body>
</html>