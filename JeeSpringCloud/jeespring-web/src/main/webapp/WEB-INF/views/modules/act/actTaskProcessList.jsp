<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>发起任务</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            top.$.jBox.tip.mess = null;
        });

        function page(n, s) {
            location = '${ctx}/act/task/process/?pageNo=' + n + '&pageSize=' + s;
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
            <div class="box-title"><i class="fa fa-edit"></i>新建任务</div>
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
                <li><a href="${ctx}/act/task/historic/">已办任务</a></li>
                <li class="active"><a href="${ctx}/act/task/process/">新建任务</a></li>
            </ul>
            <br>

            <form id="searchForm" action="${ctx}/act/task/process/" method="post" class="form-inline">
                <div class="form-group">
                    <label class="control-label">流程分类</label>
                    <div class="control-inline">
                        <select id="category" name="category" class="form-control m-b">
                            <option value="">全部分类</option>
                            <c:forEach items="${fns:getDictList('act_category')}" var="dict">
                                <option value="${dict.value}" ${dict.value==category?'selected':''}>${dict.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                </div>
            </form>
            <table class="table table-hover table-condensed  dataTables-example dataTable no-footer">
                <thead>
                <tr>
                    <th>流程分类</th>
                    <th>流程标识</th>
                    <th>流程名称</th>
                    <th>流程图</th>
                    <th>流程版本</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="object">
                    <c:set var="process" value="${object[0]}"/>
                    <c:set var="deployment" value="${object[1]}"/>
                    <tr>
                        <td>${fns:getDictLabel(process.category,'act_category','无分类')}</td>
                        <td><a href="${ctx}/act/task/form?procDefId=${process.id}">${process.key}</a></td>
                        <td>${process.name}</td>
                        <td><a target="_blank"
                               href="${pageContext.request.contextPath}/act/diagram-viewer/index.html?processDefinitionId=${process.id}">${process.diagramResourceName}</a><%--
						<a target="_blank" href="${ctx}/act/process/resource/read?procDefId=${process.id}&resType=image">${process.diagramResourceName}</a>--%>
                        </td>
                        <td><b title='流程版本号'>V: ${process.version}</b></td>
                        <td><fmt:formatDate value="${deployment.deploymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a href="${ctx}/act/task/form?procDefId=${process.id}">启动流程</a>
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
