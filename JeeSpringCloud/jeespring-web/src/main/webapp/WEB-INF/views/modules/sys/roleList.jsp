<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>角色管理</div>
            <div class="box-tools pull-right">
                <shiro:hasPermission name="sys:role:add">
                    <a id="btnAdd" href="${ctx}/sys/role/form" data-addTab="true"
                       class="btn btn-default btn-sm" title="新增"><i
                            class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:role:del">
                    <a id="btnDeleteAll" href="${ctx}/sys/role/deleteAll" class="btn btn-default btn-sm"
                       title="删除"><i class="fa fa-trash-o"></i>删除</a>
                </shiro:hasPermission>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="role" action="${ctx}/sys/role/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                  callback="sortOrRefresh();"/><!-- 支持排序 -->
                <div class="form-group">
                    <span>角色名称：</span>
                    <form:input path="name" value="${role.name}" htmlEscape="false" maxlength="64"
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
                </div>
            </div>

            <table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th>角色名称</th>
                    <th>英文名称</th>
                    <th>归属机构</th>
                    <th>数据范围</th>
                    <shiro:hasPermission name="sys:role:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="role">
                    <tr>
                        <td><input type="checkbox" id="${role.id}" class="i-checks"></td>
                        <td><a href="#"
                               onclick="openDialogView('查看角色', '${ctx}/sys/role/form?id=${role.id}','800px', '500px')">${role.name}</a>
                        </td>
                        <td><a href="#"
                               onclick="openDialogView('查看角色', '${ctx}/sys/role/form?id=${role.id}','800px', '500px')">${role.enname}</a>
                        </td>
                        <td>${role.office.name}</td>
                        <td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
                        <td>
                            <shiro:hasPermission name="sys:role:view">
                                <a  id="btnView" class="btnView" href="${ctx}/sys/role/form?id=${role.id}" title="查看"><i
                                        class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:role:edit">
                                <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
                                    <a  id="btnEdit" class="btnEdit" href="${ctx}/sys/role/form?id=${role.id}"
                                        title="修改"><i class="fa fa-pencil"></i></a>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:role:del">
                                <a  id="btnDelete" class="btnDelete" href="${ctx}/sys/role/delete?id=${role.id}"
                                   onclick="return confirmx('确认要删除该角色吗？', this.href)"><i
                                        class="fa fa-trash-o" title="删除"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:role:assign">
                                <a href="${ctx}/sys/role/auth?id=${role.id}"
                                   ><i class="fa fa-edit"></i>权限设置</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:role:assign">
                                <a href="${ctx}/sys/role/assign?id=${role.id}"
                                   ><i class="glyphicon glyphicon-plus"></i>分配用户</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页代码 -->
            ${page.toStringPage()}
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
</body>
</body>
</html>