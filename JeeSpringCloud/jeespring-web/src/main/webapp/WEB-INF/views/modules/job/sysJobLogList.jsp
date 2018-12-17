<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>定时任务调度日志管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>定时任务调度日志管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" title="查询" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i></a>
                <a id="btnRefresh" title="刷新" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-repeat"></i></a>
                <shiro:hasPermission name="job:sysJobLog:add">
                    <a id="btnAdd" href="${ctx}/job/sysJobLog/form" title="增加" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i></a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:sysJobLog:del">
                    <a id="btnDeleteAll" href="${ctx}/job/sysJobLog/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i></a>
                </shiro:hasPermission>
                <a id="btnTotalView" href="#" title="统计" class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i></a>
                <shiro:hasPermission name="job:sysJobLog:import">
                    <table:importExcel url="${ctx}/job/sysJobLog/import"></table:importExcel><!-- 导入按钮 -->
                </shiro:hasPermission>
                <shiro:hasPermission name="job:sysJobLog:export">
                    <table:exportExcel url="${ctx}/job/sysJobLog/export"></table:exportExcel><!-- 导出按钮 -->
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="sysJobLog" action="${ctx}/job/sysJobLog/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <span>任务名称：</span>
                    <form:input path="jobName" htmlEscape="false" maxlength="64" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>任务组名：</span>
                    <form:input path="jobGroup" htmlEscape="false" maxlength="64" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>任务方法：</span>
                    <form:input path="methodName" htmlEscape="false" maxlength="500" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>方法参数：</span>
                    <form:input path="methodParams" htmlEscape="false" maxlength="200" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>日志信息：</span>
                    <form:input path="jobMessage" htmlEscape="false" maxlength="500" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>执行状态：</span>
                    <form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('job_status')}"
                                       itemLabel="label" itemValue="value" htmlEscape="false"/>
                </div>
                <div class="form-group">
                    <span>异常信息：</span>
                    <span>创建时间：</span>
                    <input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysJobLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    -
                    <input id="endCreateDate" name="endCreateDate" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${sysJobLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
                    <th class="sort-column jobName ">任务名称</th>
                    <th class="sort-column jobGroup ">任务组名</th>
                    <th class="sort-column methodName ">任务方法</th>
                    <th class="sort-column methodParams hidden-xs">方法参数</th>
                    <th class="sort-column jobMessage hidden-xs">日志信息</th>
                    <th class="sort-column status hidden-xs">执行状态</th>
                    <th class="sort-column exceptionInfo hidden-xs">异常信息</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="sysJobLog">
                    <tr>
                        <td>
                            <input type="checkbox" id="${sysJobLog.id}"
                                   jobName="${sysJobLog.jobName}"
                                   jobGroup="${sysJobLog.jobGroup}"
                                   methodName="${sysJobLog.methodName}"
                                   methodParams="${sysJobLog.methodParams}"
                                   jobMessage="${sysJobLog.jobMessage}"
                                   status="${sysJobLog.status}"
                                   createBy="${sysJobLog.createBy}"
                                   updateDate="${sysJobLog.updateDate}"
                                   updateBy="${sysJobLog.updateBy}"
                                   delFlag="${sysJobLog.delFlag}"
                                   class="i-checks"></td>
                        <td class=""><a href="${ctx}/job/sysJobLog/form?id=${sysJobLog.id}&action=view">
                                ${sysJobLog.jobName}
                        </a></td>
                        <td class="">
                                ${sysJobLog.jobGroup}
                        </td>
                        <td class="">
                                ${sysJobLog.methodName}
                        </td>
                        <td class="hidden-xs">
                                ${sysJobLog.methodParams}
                        </td>
                        <td class="hidden-xs">
                                ${sysJobLog.jobMessage}
                        </td>
                        <td class="hidden-xs">
                                ${fns:getDictLabel(sysJobLog.status, 'job_status', '')}
                        </td>
                        <td class="hidden-xs">
                                ${sysJobLog.exceptionInfo}
                        </td>
                        <td>
                            <shiro:hasPermission name="job:sysJobLog:view">
                                <a href="${ctx}/job/sysJobLog/form?id=${sysJobLog.id}&action=view" title="查看"><i class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="job:sysJobLog:edit">
                                <a href="${ctx}/job/sysJobLog/form?id=${sysJobLog.id}" title="修改"><i class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="job:sysJobLog:del">
                                <a href="${ctx}/job/sysJobLog/delete?id=${sysJobLog.id}"
                                   onclick="return confirmx('确认要删除该定时任务调度日志吗？', this.href)"
                                   title="删除"><i class="fa fa-trash-o"></i></a>
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
                            title="定时任务调度日志数量饼图"
                            subtitle="定时任务调度日志数量饼图"
                            orientData="${orientData}"/>

                    <div id="line_normal" class="main000"></div>
                    <echarts:line
                            id="line_normal"
                            title="定时任务调度日志曲线"
                            subtitle="定时任务调度日志曲线"
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
<script src="/staticViews/modules/job/sysJobLogList.js" type="text/javascript"></script>
<link href="/staticViews/modules/job/sysJobLogList.css" rel="stylesheet"/>
</body>
</html>