<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>站点管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>

    <script type="text/javascript">
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
            <div class="box-title"><i class="fa fa-edit"></i>站点列表</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
                <shiro:hasPermission name="cms:site:edit"><a href="${ctx}/cms/site/form" class="btn btn-default btn-sm">添加</a></shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="site" action="${ctx}/cms/site/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="form-group">
                    <label class="control-label">名称：</label>
                    <div class="control-inline">
                    <form:input path="name" htmlEscape="false" maxlength="50"
                                class="form-control input-sm "/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">状态：</label>
                    <div class="control-inline">
                    <form:radiobuttons onclick="$('#searchForm').submit();"
                                       path="delFlag"
                                       items="${fns:getDictList('del_flag')}"
                                       itemLabel="label"
                                       itemValue="value" htmlEscape="false"/>
                    </div>
                </div>
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            </form:form>
            <sys:message content="${message}"/>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>标题</th>
                    <th>描述</th>
                    <th>关键字</th>
                    <th>主题</th>
                    <shiro:hasPermission name="cms:site:edit">
                        <th>操作</th>
                    </shiro:hasPermission></tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="site">
                    <tr>
                        <td><a href="${ctx}/cms/site/form?id=${site.id}"
                               title="${site.name}">${fns:abbr(site.name,40)}</a></td>
                        <td>${fns:abbr(site.title,40)}</td>
                        <td>${fns:abbr(site.description,40)}</td>
                        <td>${fns:abbr(site.keywords,40)}</td>
                        <td>${site.theme}</td>
                        <shiro:hasPermission name="cms:site:edit">
                            <td>
                                <a href="${ctx}/cms/site/form?id=${site.id}">修改</a>
                                <a href="${ctx}/cms/site/delete?id=${site.id}${site.delFlag ne 0?'&isRe=true':''}"
                                   onclick="return confirmx('确认要${site.delFlag ne 0?'恢复':''}删除该站点吗？', this.href)">${site.delFlag ne 0?'恢复':''}删除</a>
                            </td>
                        </shiro:hasPermission>
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