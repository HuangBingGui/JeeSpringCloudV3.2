<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>通知管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>通知管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="筛选">
                    <i class="fa fa-filter"></i>筛选
                </a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新">
                    <i class="glyphicon glyphicon-repeat"></i>刷新
                </button>
                <c:if test="${!requestScope.oaNotify.self}">
                    <shiro:hasPermission name="oa:oaNotify:add">
                        <a id="btnAdd" href="${ctx}/oa/oaNotify/form" data-addTab="true"
                           class="btn btn-default btn-sm" title="新增"><i
                                class="fa fa-plus"></i>新增</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="oa:oaNotify:del">
                        <a id="btnDeleteAll" href="${ctx}/oa/oaNotify/deleteAll" class="btn btn-default btn-sm"
                           title="删除"><i class="fa fa-plus"></i> 删除</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="oa:oaNotify:import">
                        <table:importExcel url="${ctx}/oa/oaNotify/import">导入</table:importExcel><!-- 导入按钮 -->
                    </shiro:hasPermission>
                    <shiro:hasPermission name="oa:oaNotify:export">
                        <table:exportExcel url="${ctx}/oa/oaNotify/export">导出</table:exportExcel><!-- 导出按钮 -->
                    </shiro:hasPermission>
                </c:if>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}"
                       method="post" class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <label class="control-label">标题：</label>
                    <div class="control-inline"><form:input path="title" htmlEscape="false" maxlength="200"
                                                            class=" form-control input-sm"/></div>
                </div>
                <div class="form-group">
                    <label class="control-label">类型：</label>
                    <div class="control-inline">
                        <form:select path="type" class="form-control m-b">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label"
                                          itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <c:if test="${!requestScope.oaNotify.self}">
                        <label class="control-label">状态：</label>
                        <div class="control-inline">
                            <form:radiobuttons path="status" class="i-checks"
                                               items="${fns:getDictListAddAll('oa_notify_status')}" itemLabel="label"
                                               itemValue="value" htmlEscape="false"/>
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                </div>
            </form:form>
            <!-- 列表 -->
            <table id="contentTable" class="table table-hover table-condensed  dataTables-example dataTable no-footer">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th class="sort-column title">标题</th>
                    <th class="sort-column type">类型</th>
                    <th class="sort-column status">状态</th>
                    <th class="sort-column readFlag">查阅状态</th>
                    <th class="sort-column updateDate">更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="oaNotify">
                    <tr>
                        <td><input type="checkbox" id="${oaNotify.id}" class="i-checks"></td>
                        <td>
                            <shiro:hasPermission name="oa:oaNotify:edit">
                            <a href="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=${oaNotify.id}">
                                </shiro:hasPermission>
                                    ${fns:abbr(oaNotify.title,50)}
                                <shiro:hasPermission name="oa:oaNotify:edit">
                            </a>
                            </shiro:hasPermission>
                        </td>
                        <td>${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}</td>
                        <td>${fns:getDictLabel(oaNotify.status, 'oa_notify_status', '')}</td>
                        <td>
                            <c:if test="${requestScope.oaNotify.self}">
                                ${fns:getDictLabel(oaNotify.readFlag, 'oa_notify_read', '')}
                            </c:if>
                            <c:if test="${!requestScope.oaNotify.self}">
                                ${oaNotify.readNum} / ${oaNotify.readNum + oaNotify.unReadNum}
                            </c:if>
                        </td>
                        <td><fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${!requestScope.oaNotify.self}">
                                <shiro:hasPermission name="oa:oaNotify:view">
                                    <a id="btnView" class="btnView"
                                       href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}&action=view" title="查看"><i
                                            class="fa fa-search-plus"></i></a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="oa:oaNotify:edit">
                                    <a id="btnEdit" class="btnEdit" href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}"
                                       title="编辑"><i
                                            class="fa fa-pencil"></i></a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="oa:oaNotify:del">
                                    <a id="btnDelete" class="btnDelete"
                                       href="${ctx}/oa/oaNotify/delete?id=${oaNotify.id}" title="删除"><i
                                            class="fa fa-trash-o"></i></a>
                                </shiro:hasPermission>
                            </c:if>
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
<script src="/staticViews/viewBase.js"></script>
</body>
</html>