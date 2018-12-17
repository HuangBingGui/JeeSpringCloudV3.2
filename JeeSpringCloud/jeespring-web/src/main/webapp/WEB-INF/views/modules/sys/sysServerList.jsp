<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>服务器监控管理</title>
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
                <a id="btnSearchView" href="#" title="筛选" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i>筛选</a>
                <a id="btnRefresh" title="刷新" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-repeat"></i>刷新</a>
                <shiro:hasPermission name="sys:sysServer:add">
                    <a id="btnAdd" href="${ctx}/sys/sysServer/form" title="服务器监控" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysServer:del">
                    <a id="btnDeleteAll" href="${ctx}/sys/sysServer/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysServer:total">
                    <a id="btnAdd" href="${ctx}/sys/sysServer/total" title="统计图表"
                       class="btn btn-default btn-sm">统计图表</a>
                    <a id="btnAdd" href="${ctx}/sys/sysServer/totalMap" title="统计地图"
                       class="btn btn-default btn-sm">统计图表</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysServer:import">
                    <table:importExcel url="${ctx}/sys/sysServer/import"></table:importExcel><!-- 导入按钮 -->
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysServer:export">
                    <table:exportExcel url="${ctx}/sys/sysServer/export"></table:exportExcel><!-- 导出按钮 -->
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="sysServer" action="${ctx}/sys/sysServer/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <span>服务器编号：</span>
                    <form:input path="serverNumber" htmlEscape="false" maxlength="255" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>服务器监控地址：</span>
                    <form:input path="serverAddress" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>名称：</span>
                    <form:input path="name" htmlEscape="false" maxlength="255" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>标签名：</span>
                    <form:input path="label" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>类型：</span>
                    <form:radiobuttons class="i-checks" path="type" items="${fns:getDictListAddAll('server_type')}"
                                       itemLabel="label" itemValue="value" htmlEscape="false"/>
                </div>
                <div class="form-group">
                    <span>排序（升序）：</span>
                    <form:input path="sort" htmlEscape="false" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>描述：</span>
                    <form:input path="description" htmlEscape="false" maxlength="100" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>备注信息：</span>
                    <form:input path="remarks" htmlEscape="false" maxlength="100" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>在线状态on_line在线off_line离线：</span>
                    <form:radiobuttons class="i-checks" path="status" items="${fns:getDictListAddAll('on_line_status')}"
                                       itemLabel="label" itemValue="value" htmlEscape="false"/>
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
                    <th class="sort-column serverNumber ">服务器编号</th>
                    <th class="sort-column serverAddress ">服务器监控地址</th>
                    <th class="sort-column name ">名称</th>
                    <th class="sort-column label hidden-xs" style="display:table-cell;">标签名</th>
                    <th class="sort-column picture hidden-xs">图片</th>
                    <th class="sort-column type hidden-xs">类型</th>
                    <th class="sort-column status hidden-xs">状态</th>
                    <th class="sort-column sort hidden-xs">排序（升序）</th>
                    <th class="sort-column description hidden-xs">描述</th>
                    <th class="sort-column createDate hidden-xs">创建时间</th>
                    <th class="sort-column updateDate hidden-xs">更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="sysServer">
                    <tr>
                        <td>
                            <input type="checkbox" id="${sysServer.id}"
                                   serverNumber="${sysServer.serverNumber}"
                                   serverAddress="${sysServer.serverAddress}"
                                   name="${sysServer.name}"
                                   label="${sysServer.label}"
                                   picture="${sysServer.picture}"
                                   type="${sysServer.type}"
                                   sort="${sysServer.sort}"
                                   description="${sysServer.description}"
                                   class="i-checks"></td>
                        <td class=""><a href="${ctx}/sys/sysServer/form?id=${sysServer.id}&action=view">
                                ${sysServer.serverNumber}
                        </a></td>
                        <td class="">
                                ${sysServer.serverAddress}
                        </td>
                        <td class="">
                                ${sysServer.name}
                        </td>
                        <td class="hidden-xs">
                                ${sysServer.label}
                        </td>
                        <td class="hidden-xs">
                                ${sysServer.picture}
                        </td>
                        <td class="hidden-xs">
                                ${fns:getDictLabel(sysServer.type, 'server_type', '')}
                        </td>

                        <td class="hidden-xs">
                                ${fns:getDictLabel(sysServer.status, 'on_line_status', '')}
                        </td>
                        <td class="hidden-xs">
                                ${sysServer.sort}
                        </td>
                        <td class="hidden-xs">
                                ${sysServer.description}
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${sysServer.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${sysServer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <shiro:hasPermission name="sys:sysServer:view">
                                <a href="${ctx}/sys/sysServer/form?id=${sysServer.id}&action=view" title="查看"><i
                                        class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:sysServer:edit">
                                <a href="${ctx}/sys/sysServer/form?id=${sysServer.id}" title="修改"><i
                                        class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:sysServer:del">
                                <a href="${ctx}/sys/sysServer/delete?id=${sysServer.id}"
                                   onclick="return confirmx('确认要删除该服务器监控吗？', this.href)" title="删除"><i
                                        class="fa fa-trash-o"></i></a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页代码 -->
            ${page.toStringPage()}
            <br/>
            <!-- 统计 -->
            <div class="row" id="total" style="margin-top: 10px;">
                <div class="col-sm-12 echartsEval">
                    <h4>合计：${sumTotalCount}行;
                    </h4>
                    <div id="pie" class="main000"></div>
                    <echarts:pie
                            id="pie"
                            title="服务器监控数量饼图"
                            subtitle="服务器监控数量饼图"
                            orientData="${orientData}"/>

                    <div id="line_normal" class="main000"></div>
                    <echarts:line
                            id="line_normal"
                            title="服务器监控曲线"
                            subtitle="服务器监控曲线"
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
<script src="/staticViews/modules/sys/sysServerList.js" type="text/javascript"></script>
<link href="/staticViews/modules/sys/sysServerList.css" rel="stylesheet"/>
</body>
</html>