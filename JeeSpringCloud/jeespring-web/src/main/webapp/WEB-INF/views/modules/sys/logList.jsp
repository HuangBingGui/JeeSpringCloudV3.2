<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>日志管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>日志管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" title="查询" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i></a>
                <a id="btnRefresh" title="刷新" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-repeat"></i></a>
                <shiro:hasPermission name="sys:log:del">
                    <a id="btnDeleteAll" href="${ctx}/sys/log/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i></a>
                    <a id="btnDeleteAll" href="${ctx}/sys/log/empty" title="清空"
                       onclick="return confirmx('确认要清空日志吗？', this.href)"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>清空</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                  callback="sortOrRefresh();"/><!-- 支持排序 -->
                <div class="form-group">
                    <span>操作菜单：</span>
                    <input id="title" name="title" type="text" maxlength="50"
                           class="form-control input-sm" value="${log.title}"/>
                </div>
                <div class="form-group">
                    <span>用户ID：</span>
                    <input id="createBy.id" name="createBy.id" type="text" maxlength="50"
                           class="form-control input-sm" value="${log.createBy.id}"/>
                </div>
                <div class="form-group">
                    <span>URI：</span>
                    <input id="requestUri" name="requestUri" type="text" maxlength="50"
                           class="form-control input-sm" value="${log.requestUri}"/>
                </div>
                <div class="form-group">
                    <span>日期范围：&nbsp;</span>
                    <input id="beginDate" name="beginDate" type="text" maxlength="20"
                           class="laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>"/>
                    <label>-</label>
                    <input id="endDate" name="endDate" type="text" maxlength="20"
                           class=" laydate-icon form-control layer-date input-sm"
                           value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>"/>&nbsp;&nbsp;
                    &nbsp;<label for="exception">
                    <input id="exception" name="exception" class="i-checks"
                           type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>只查询异常信息</label>
                </div>
                <div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                </div>
            </form:form>
            <table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th><input type="checkbox" class="i-checks"></th>
                    <th>操作菜单</th>
                    <th>操作用户</th>
                    <th>所在公司</th>
                    <th>所在部门</th>
                    <th>URI</th>
                    <th>提交方式</th>
                    <th>操作者IP</th>
                    <th>操作时间</th>
                </thead>
                <tbody><%
                    request.setAttribute("strEnter", "\n");
                    request.setAttribute("strTab", "\t");
                %>
                <c:forEach items="${page.list}" var="log">
                    <tr>
                        <td><input type="checkbox" id="${log.id}" class="i-checks"></td>
                        <td>${log.title}</td>
                        <td>${log.createBy.name}</td>
                        <td>${log.createBy.company.name}</td>
                        <td>${log.createBy.office.name}</td>
                        <td><strong>${log.requestUri}</strong></td>
                        <td>${log.method}</td>
                        <td>${log.remoteAddr}</td>
                        <td><fmt:formatDate value="${log.createDate}" type="both"/></td>
                    </tr>
                    <c:if test="${not empty log.exception}">
                        <tr>
                            <td colspan="8" style="word-wrap:break-word;word-break:break-all;">
                                    <%-- 					用户代理: ${log.userAgent}<br/> --%>
                                    <%-- 					提交参数: ${fns:escapeHtml(log.params)} <br/> --%>
                                异常信息: <br/>
                                    ${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}
                            </td>
                        </tr>
                    </c:if>
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
<script type="text/javascript">
    $(document).ready(function () {
        //外部js调用
        laydate({
            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
    })
</script>
</body>
</html>