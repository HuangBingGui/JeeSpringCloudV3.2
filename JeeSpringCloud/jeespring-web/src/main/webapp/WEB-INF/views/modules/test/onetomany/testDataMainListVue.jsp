<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订票管理</title>
	<%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
	<script src="/staticViews/modules/test/onetomany/testDataMainList.js" type="text/javascript"></script>
	<link href="/staticViews/modules/test/onetomany/testDataMainList.css" rel="stylesheet" />
</head>
<body>
<!-- 内容-->
<div class="wrapper" id="rrapp">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>订票管理</div>
            <div class="box-tools pull-right">
				<button  class="btn btn-success btn-sm " onclick="$('#searchForm').toggle();$('.fa-chevron').toggle();"  title="筛选">
					<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 筛选
				</button>
				<shiro:hasPermission name="test:onetomany:testDataMain:add">
				      <a id="btnAdd" href="${ctx}/test/onetomany/testDataMain/form" title="增加" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加</a>
                     <a id="btnAdd" href="${ctx}/test/onetomany/testDataMain/form?ViewFormType=FormTwo" title="增加2" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加2</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="test:onetomany:testDataMain:del">
					<a id="btnDeleteAll" href="${ctx}/test/onetomany/testDataMain/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="test:onetomany:testDataMain:import">
					<table:importExcel url="${ctx}/test/onetomany/testDataMain/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="test:onetomany:testDataMain:export">
					<table:exportExcel url="${ctx}/test/onetomany/testDataMain/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>
                 <a href="${ctx}/test/onetomany/testDataMain/list" title="Vue" class="btn btn-default btn-sm"><i
                        class="glyphicon glyphicon-repeat"></i>list</a>
                <shiro:hasPermission name="test:onetomany:testDataMain:total">
                <a href="${ctx}/test/onetomany/testDataMain/total" title="统计图表" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计图表</a>
                 <a href="${ctx}/test/onetomany/testDataMain/totalMap" title="统计地图" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计地图</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
			<!--查询条件-->
			<form:form id="searchForm" modelAttribute="testDataMain" action="${ctx}/../rest/test/onetomany/testDataMain/list" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
					<div class="form-group">
					<span>名称：</span>
						<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
					 </div>
					<div class="form-group">
					<span>性别：</span>
						<form:radiobuttons class="i-checks" path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </div>
					<div class="form-group">
					<span>加入日期：</span>
						<input id="beginInDate" name="beginInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
							value="<fmt:formatDate value="${testDataMain.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
						<input id="endInDate" name="endInDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
							value="<fmt:formatDate value="${testDataMain.endInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					 </div>
					<div class="form-group">
					<span>更新时间：</span>
						<input id="updateDate" name="updateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
							value="<fmt:formatDate value="${testDataMain.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
						<th  class="sort-column office.name ">归属部门</th>
						<th  class="sort-column area.name ">归属区域</th>
						<th  class="sort-column name hidden-xs">名称</th>
						<th  class="sort-column sex hidden-xs">性别</th>
						<th  class="sort-column inDate hidden-xs">加入日期</th>
						<th  class="sort-column createDate hidden-xs">创建时间</th>
						<th  class="sort-column updateDate hidden-xs">更新时间</th>
						<th  class="sort-column remarks hidden-xs">备注信息</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr  v-for="item in page" >
						<td>
						<input type="checkbox" :id="item.id"
							office.id="${testDataMain.office.id}"
							area.id="${testDataMain.area.id}"
							name="${testDataMain.name}"
							sex="${testDataMain.sex}"
							remarks="${testDataMain.remarks}"
						class="i-checks"></td>
						<td  class=""><a  href="#" v-on:click="openDialogView('查看订票', '${ctx}/test/onetomany/testDataMain/form?id='+item.id,'800px', '500px')">
						{{item.office.name}}
						</a></td>
						<td  class="">
						{{item.area.name}}
						</td>
						<td  class="hidden-xs">
						{{item.name}}
						</td>
						<td  class="hidden-xs">
						{{item.sexLabel}}
						</td>
						<td  class="hidden-xs">
						{{item.inDate}}
						</td>
						<td  class="hidden-xs">
						{{item.createDate}}
						</td>
						<td  class="hidden-xs">
						{{item.updateDate}}
						</td>
						<td  class="hidden-xs">
						{{item.remarks}}
						</td>
                        <td>
                            <shiro:hasPermission name="test:onetomany:testDataMain:view">
                                <a href="${ctx}/test/onetomany/testDataMain/form?id=${testDataMain.id}&action=view" title="查看"><i class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                             <shiro:hasPermission name="test:onetomany:testDataMain:edit">
                                <a href="${ctx}/test/onetomany/testDataMain/form?id=${testDataMain.id}" title="修改"  title="修改"><i class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="test:onetomany:testDataMain:del">
                                <a href="${ctx}/test/onetomany/testDataMain/delete?id=${testDataMain.id}" onclick="return confirmx('确认要删除该订票吗？', this.href)" title="删除"><i class="fa fa-trash-o"></i></a>
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