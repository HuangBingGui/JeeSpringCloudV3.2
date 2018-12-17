<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>留言管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>留言列表</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">

            <form:form id="searchForm" modelAttribute="guestbook" action="${ctx}/cms/guestbook/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="form-group">
                    <label class="control-label">分类：</label>
                    <div class="control-inline">
                    <form:select id="type" path="type" class="form-control">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('cms_guestbook')}" itemValue="value" itemLabel="label"
                                      htmlEscape="false"/>
                    </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">内容 ：</label>
                    <form:input path="content" htmlEscape="false" maxlength="50" class="form-control input-sm"/>
                </div>
                <div class="form-group">
                    <label class="control-label">状态：</label>
                    <form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag"
                                                     items="${fns:getDictList('cms_del_flag')}" itemLabel="label"
                                                     itemValue="value" htmlEscape="false"/>
                </div>
                <div  class="form-group">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                </div>
            </form:form>
            <sys:message content="${message}"/>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>留言分类</th>
                    <th>留言内容</th>
                    <th>留言人</th>
                    <th>留言时间</th>
                    <th>回复人</th>
                    <th>回复内容</th>
                    <th>回复时间</th>
                    <shiro:hasPermission name="cms:guestbook:edit">
                        <th>操作</th>
                    </shiro:hasPermission></tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="guestbook">
                    <tr>
                        <td>${fns:getDictLabel(guestbook.type, 'cms_guestbook', '无分类')}</td>
                        <td><a href="${ctx}/cms/guestbook/form?id=${guestbook.id}">${fns:abbr(guestbook.content,40)}</a>
                        </td>
                        <td>${guestbook.name}</td>
                        <td><fmt:formatDate value="${guestbook.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${guestbook.reUser.name}</td>
                        <td>${fns:abbr(guestbook.reContent,40)}</td>
                        <td><fmt:formatDate value="${guestbook.reDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <shiro:hasPermission name="cms:guestbook:edit">
                            <td>
                                <c:if test="${guestbook.delFlag ne '2'}"><a
                                        href="${ctx}/cms/guestbook/delete?id=${guestbook.id}${guestbook.delFlag ne 0?'&isRe=true':''}"
                                        onclick="return confirmx('确认要${guestbook.delFlag ne 0?'恢复审核':'删除'}该留言吗？', this.href)">${guestbook.delFlag ne 0?'恢复审核':'删除'}</a></c:if>
                                <c:if test="${guestbook.delFlag eq '2'}"><a
                                        href="${ctx}/cms/guestbook/form?id=${guestbook.id}">审核</a></c:if>
                            </td>
                        </shiro:hasPermission>
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
<script src="/staticViews/viewBase.js"></script></body>
</html>