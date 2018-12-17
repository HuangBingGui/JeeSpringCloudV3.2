<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>在线用户记录管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@ include file="/WEB-INF/views/include/echarts.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>服务器监控管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" title="查询" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i></a>
                <a id="btnRefresh" title="刷新" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-repeat"></i></a>
                <shiro:hasPermission name="sys:sysUserOnline:add">
                    <a id="btnAdd" href="${ctx}/sys/sysUserOnline/form" title="服务器监控" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysUserOnline:del">
                    <a id="btnDeleteAll" href="${ctx}/sys/sysUserOnline/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i></a>
                </shiro:hasPermission>
                <a id="btnTotalView" href="#" title="统计" class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i></a>
                <shiro:hasPermission name="sys:sysUserOnline:import">
                    <table:importExcel url="${ctx}/sys/sysUserOnline/import"></table:importExcel><!-- 导入按钮 -->
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysUserOnline:export">
                    <table:exportExcel url="${ctx}/sys/sysUserOnline/export"></table:exportExcel><!-- 导出按钮 -->
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="sysUserOnline" action="${ctx}/sys/sysUserOnline/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <span>登录账号：</span>
                    <form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>部门名称：</span>
                    <form:input path="deptName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>登录IP地址：</span>
                    <form:input path="ipaddr" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>登录地点：</span>
                    <form:input path="loginLocation" htmlEscape="false" maxlength="255" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>浏览器类型：</span>
                    <form:input path="browser" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>操作系统：</span>
                    <form:input path="os" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>在线状态：</span>
                    <form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('on_line_status')}"
                                       itemLabel="label" itemValue="value" htmlEscape="false"/>
                </div>
                <div class="form-group">
                    <span>session创建时间：</span>
                    <input id="beginStartTimestsamp" name="beginStartTimestsamp" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysUserOnline.beginStartTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    -
                    <input id="endStartTimestsamp" name="endStartTimestsamp" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysUserOnline.endStartTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                </div>
                <div class="form-group">
                    <span>session最后访问时间：</span>
                    <input id="beginLastAccessTime" name="beginLastAccessTime" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysUserOnline.beginLastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    -
                    <input id="endLastAccessTime" name="endLastAccessTime" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysUserOnline.endLastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                </div>
                <div class="form-group">
                    <span>超时时间，单位为分钟：</span>
                    <form:input path="expireTime" htmlEscape="false" maxlength="5" class=" form-control input-sm"/>
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
                    <th><input type="checkbox" class="i-checks"></th>
                    <th class="sort-column loginName ">登录账号</th>
                    <th class="sort-column deptName ">部门名称</th>
                    <th class="sort-column ipaddr ">登录IP地址</th>
                    <th class="sort-column loginLocation hidden-xs">登录地点</th>
                    <th class="sort-column browser hidden-xs">浏览器类型</th>
                    <th class="sort-column os hidden-xs">操作系统</th>
                    <th class="sort-column status hidden-xs">在线状态</th>
                    <th class="sort-column startTimestsamp hidden-xs">session创建时间</th>
                    <th class="sort-column updateDate hidden-xs">更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="sysUserOnline">
                    <tr>
                        <td>
                            <input type="checkbox" id="${sysUserOnline.id}"
                                   loginName="${sysUserOnline.loginName}"
                                   deptName="${sysUserOnline.deptName}"
                                   ipaddr="${sysUserOnline.ipaddr}"
                                   loginLocation="${sysUserOnline.loginLocation}"
                                   browser="${sysUserOnline.browser}"
                                   os="${sysUserOnline.os}"
                                   status="${sysUserOnline.status}"
                                   class="i-checks"></td>
                        <td class=""><a href="${ctx}/sys/sysUserOnline/form?id=${sysUserOnline.id}&action=view">
                                ${sysUserOnline.loginName}
                        </a></td>
                        <td class="">
                                ${sysUserOnline.deptName}
                        </td>
                        <td class="">
                                ${sysUserOnline.ipaddr}
                        </td>
                        <td class="hidden-xs">
                                ${sysUserOnline.loginLocation}
                        </td>
                        <td class="hidden-xs">
                                ${sysUserOnline.browser}
                        </td>
                        <td class="hidden-xs">
                                ${sysUserOnline.os}
                        </td>
                        <td class="hidden-xs">
                                ${fns:getDictLabel(sysUserOnline.status, 'on_line_status', '')}
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${sysUserOnline.startTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${sysUserOnline.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <shiro:hasPermission name="sys:sysUserOnline:view">
                                <a href="${ctx}/sys/sysUserOnline/form?id=${sysUserOnline.id}&action=view" title="查看"><i
                                        class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:sysUserOnline:edit">
                                <a href="${ctx}/sys/sysUserOnline/form?id=${sysUserOnline.id}" title="修改"><i
                                        class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:sysUserOnline:del">
                                <a href="${ctx}/sys/sysUserOnline/delete?id=${sysUserOnline.id}"
                                   onclick="return confirmx('确认要删除该在线用户记录吗？', this.href)"
                                   title="删除"><i class="fa fa-trash-o"></i></a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页代码 -->
            ${page.toStringPage()}
            <!-- 统计 -->
            <div class="row" id="total" style="margin-top: 10px;">
                <div class="col-sm-12 echartsEval">
                    <h4>合计：${sumTotalCount}行;
                    </h4>
                    <div id="pie" class="main000"></div>
                    <echarts:pie
                            id="pie"
                            title="在线用户记录数量饼图"
                            subtitle="在线用户记录数量饼图"
                            orientData="${orientData}"/>

                    <div id="line_normal" class="main000"></div>
                    <echarts:line
                            id="line_normal"
                            title="在线用户记录曲线"
                            subtitle="在线用户记录曲线"
                            xAxisData="${xAxisData}"
                            yAxisData="${yAxisData}"
                            xAxisName="时间"
                            yAxisName="数量"/>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
<script src="/staticViews/modules/sys/sysUserOnlineList.js" type="text/javascript"></script>
<link href="/staticViews/modules/sys/sysUserOnlineList.css" rel="stylesheet"/>
</body>
</html>