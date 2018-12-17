<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>已办任务</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
        });

        function page(n, s) {
            location = '${ctx}/act/task/historic/?pageNo=' + n + '&pageSize=' + s;
        }
    </script>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>已办任务</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <ul class="nav nav-tabs">
                <li><a href="${ctx}/act/task/todo/">待办任务</a></li>
                <li class="active"><a href="${ctx}/act/task/historic/">已办任务</a></li>
                <li><a href="${ctx}/act/task/process/">新建任务</a></li>
            </ul>
            <br>
            <form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/historic/" method="get"
                       class="form-inline">
                <div class="form-group">
                    <label class="control-label">流程类型：&nbsp;</label>
                    <div class="control-inline">
                    <form:select path="procDefKey" class="form-control m-b">
                        <form:option value="" label="全部流程"/>
                        <form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">完成时间：</label>
                    <div class="control-inline"><input id="beginDate" name="beginDate" type="text" maxlength="20"
                                                       class="laydate-icon form-control layer-date input-sm"
                                                       value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                        　--　
                        <input id="endDate" name="endDate" type="text" maxlength="20"
                               class="laydate-icon form-control layer-date input-sm"
                               value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                        &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                    </div>
                </div>

            </form:form>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>标题</th>
                    <th>当前环节</th>
                    <%--
                                    <th>任务内容</th> --%>
                    <th>流程名称</th>
                    <th>流程版本</th>
                    <th>完成时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="act">
                    <c:set var="task" value="${act.histTask}"/>
                    <c:set var="vars" value="${act.vars}"/>
                    <c:set var="procDef" value="${act.procDef}"/><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
                    <c:set var="status" value="${act.status}"/>
                    <tr>
                        <td>
                            <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a>
                        </td>
                        <td>
                            <a target="_blank"
                               href="${pageContext.request.contextPath}/act/diagram-viewer/index.html?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.name}</a><%--
						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">${task.name}</a>
						<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">${task.name}</a> --%>
                        </td>
                            <%--
                                                <td>${task.description}</td> --%>
                        <td>${procDef.name}</td>
                        <td><b title='流程版本号'>V: ${procDef.version}</b></td>
                        <td><fmt:formatDate value="${task.endTime}" type="both"/></td>
                        <td>
                            <a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            ${page.toStringPage()}
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script></body>
</html>
