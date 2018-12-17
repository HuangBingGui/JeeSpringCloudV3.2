<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>用户管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="筛选"><i
                        class="fa fa-filter"></i>筛选</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i>刷新
                </button>
                <shiro:hasPermission name="sys:user:add">
                    <a id="btnAdd" href="${ctx}/sys/user/form" data-addTab="true" title="新增"  class="btn btn-default btn-sm" ><i class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user:del">
                    <a id="btnDeleteAll" href="${ctx}/sys/user/deleteAll" title="删除"  class="btn btn-default btn-sm" ><i class="fa fa-trash-o"></i>删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user:import">
                    <table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 导入按钮 -->
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user:export">
                    <table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 导出按钮 -->
                </shiro:hasPermission>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <span>归属公司：</span>
                    <sys:treeselect id="company" name="company.id" value="${user.company.id}"
                                    labelName="company.name" labelValue="${user.company.name}"
                                    title="公司" url="/sys/office/treeData?type=1"
                                    cssClass=" form-control input-sm" allowClear="true"/>
                </div>
                <div class="form-group">
                    <span>登录名：</span>
                    <form:input path="loginName" htmlEscape="false" maxlength="50"
                                class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>归属部门：</span>
                    <sys:treeselect id="office" name="office.id" value="${user.office.id}"
                                    labelName="office.name" labelValue="${user.office.name}"
                                    title="部门" url="/sys/office/treeData?type=2"
                                    cssClass=" form-control input-sm" allowClear="true"
                                    notAllowSelectParent="true"/>
                </div>
                <div class="form-group">
                    <span>姓&nbsp;&nbsp;&nbsp;名：</span>
                    <form:input path="name" htmlEscape="false" maxlength="50"
                                class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                </div>
            </form:form>
            <!-- 工具栏 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="pull-left"></div>
                    <div class="pull-right"></div>
                </div>
            </div>

            <table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th class="sort-column login_name">登录名</th>
                    <th class="sort-column name">姓名</th>
                    <th class="sort-column phone">电话</th>
                    <th class="sort-column mobile">手机</th>
                    <th class="sort-column c.name">归属公司</th>
                    <th class="sort-column o.name">归属部门</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="user">
                    <tr>
                        <td><input type="checkbox" id="${user.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a>
                        </td>
                        <td>${user.name}</td>
                        <td>${user.phone}</td>
                        <td>${user.mobile}</td>
                        <td>${user.company.name}</td>
                        <td>${user.office.name}</td>
                        <td>
                            <shiro:hasPermission name="sys:user:view">
                                <a id="btnView" class="btnView" href="${ctx}/sys/user/form?id=${user.id}&action=view"
                                     title="查看"><i class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:user:edit">
                                <a id="btnEdit" class="btnEdit" href="${ctx}/sys/user/form?id=${user.id}"
                                    title="编辑"><i class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:user:del">
                                <a id="btnDelete" class="btnDelete" href="${ctx}/sys/user/delete?id=${user.id}"
                                    title="删除"><i
                                        class="fa fa-trash-o"></i> </a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 没有信息数据 -->
            <%@ include file="/WEB-INF/views/include/tableNoData.jsp" %>
            <!-- 分页代码 -->
            ${page.toStringPage()}
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script></body>
</html>