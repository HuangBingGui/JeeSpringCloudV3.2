<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>审批管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
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
            <div class="box-title"><i class="fa fa-edit"></i>通知管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <ul class="nav nav-tabs">
                <li class="active"><a href="${ctx}/oa/testAudit/">审批列表</a></li>
                <shiro:hasPermission name="oa:testAudit:edit">
                    <li><a href="${ctx}/oa/testAudit/form">审批申请流程</a></li>
                </shiro:hasPermission>
            </ul>
            <br>
            <form:form id="searchForm" modelAttribute="testAudit" action="${ctx}/oa/testAudit/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="form-group">
                    <label class="control-label">姓名：</label>
                    <div class="control-inline">
                        <sys:treeselect id="user" name="user.id" value="${testAudit.user.id}"
                                        labelName="user.name" labelValue="${testAudit.user.name}"
                                        title="用户" url="/sys/office/treeData?type=3"
                                        cssClass="form-control"
                                        allowClear="true" notAllowSelectParent="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                </div>
            </form:form>
            <table id="contentTable" class="table table-hover table-condensed  dataTables-example dataTable no-footer">
                <thead>
                <tr>
                    <th>姓名</th>
                    <th>部门</th>
                    <th>岗位职级</th>
                    <th>调整原因</th>
                    <th>申请时间</th>
                    <shiro:hasPermission name="oa:testAudit:edit">
                        <th>操作</th>
                    </shiro:hasPermission></tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="testAudit">
                    <tr>
                        <td><a href="${ctx}/oa/testAudit/form?id=${testAudit.id}">${testAudit.user.name}</a></td>
                        <td>${testAudit.office.name}</td>
                        <td>${testAudit.post}</td>
                        <td>${testAudit.content}</td>
                        <td><fmt:formatDate value="${testAudit.createDate}" type="both"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <shiro:hasPermission name="oa:testAudit:edit">
                            <td>
                                <a href="${ctx}/oa/testAudit/form?id=${testAudit.id}">详情</a>
                                <a href="${ctx}/oa/testAudit/delete?id=${testAudit.id}"
                                   onclick="return confirmx('确认要删除该审批吗？', this.href)">删除</a>
                            </td>
                        </shiro:hasPermission>
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
