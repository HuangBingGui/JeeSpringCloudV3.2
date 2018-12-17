<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文章管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>

    <script type="text/javascript">
        function viewComment(href) {
            top.$.jBox.open('iframe:' + href, '查看评论', $(top.document).width() - 220, $(top.document).height() - 120, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                    $(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
                    $("body", h.find("iframe").contents()).css("margin", "10px");
                }
            });
            return false;
        }

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
            <div class="box-title"><i class="fa fa-edit"></i>文章管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
                <a href="${ctx}/cms/article/?category.id=${article.category.id}" class="btn btn-sm btn-default">列表</a>
                <shiro:hasPermission name="cms:article:edit">
                    <a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'>
            <c:param name='category.name' value='${article.category.name}'/>
            </c:url>" class="btn btn-sm btn-default">
                        增加</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="form-group">
                    <label class="control-label">栏目：</label>
                    <div class="control-inline">
                        <sys:treeselect id="category" name="category.id" value="${article.category.id}"
                                        labelName="category.name"
                                        labelValue="${article.category.name}"
                                        title="栏目" url="/cms/category/treeData" module="article"
                                        notAllowSelectRoot="false"
                                        cssClass="form-control input-sm"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">标题：</label>
                    <form:input path="title" htmlEscape="false" maxlength="50" class="form-control"/>

                </div>
                <div class="form-group">
                    <label class="control-label">状态：</label>
                    <form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag"
                                       items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value"
                                       htmlEscape="false"/>
                </div>
                <div class="form-group">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                </div>
            </form:form>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>栏目</th>
                    <th>标题</th>
                    <th>权重</th>
                    <th>点击数</th>
                    <th>发布者</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="article">
                    <tr>
                        <td><a href="javascript:"
                               onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a>
                        </td>
                        <td><a href="${ctx}/cms/article/form?id=${article.id}"
                               title="${article.title}">${fns:abbr(article.title,40)}</a></td>
                        <td>${article.weight}</td>
                        <td>${article.hits}</td>
                        <td>${article.user.name}</td>
                        <td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${article.category.id}-${article.id}${fns:getUrlSuffix()}"
                               target="_blank">访问</a>
                            <shiro:hasPermission name="cms:article:edit">
                                <c:if test="${article.category.allowComment eq '1'}"><shiro:hasPermission
                                        name="cms:comment:view">
                                    <a href="${ctx}/cms/comment/?module=article&contentId=${article.id}&delFlag=2"
                                       onclick="return viewComment(this.href);">评论</a>
                                </shiro:hasPermission></c:if>
                                <a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
                                <shiro:hasPermission name="cms:article:audit">
                                    <a href="${ctx}/cms/article/delete?id=${article.id}${article.delFlag ne 0?'&isRe=true':''}&categoryId=${article.category.id}"
                                       onclick="return confirmx('确认要${article.delFlag ne 0?'发布':'删除'}该文章吗？', this.href)">${article.delFlag ne 0?'发布':'删除'}</a>
                                </shiro:hasPermission>
                            </shiro:hasPermission>
                        </td>
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
<script src="/staticViews/viewBase.js"></script>
</body>
</html>