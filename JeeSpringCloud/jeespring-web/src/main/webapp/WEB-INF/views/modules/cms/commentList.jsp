<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评论管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>

    <script type="text/javascript">
        function view(href) {
            top.$.jBox.open('iframe:' + href, '查看文档', $(top.document).width() - 220, $(top.document).height() - 180, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    //$(".jbox-content", top.document).css("overflow-y","hidden");
                    //$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
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
            <div class="box-title"><i class="fa fa-edit"></i>评论列表</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
                <!--shiro hasPermission name="cms:comment:edit">
                    <a href="${ctx}/cms/comment/form?id=${comment.id}"  class="btn btn-default btn-sm">评论添加</a>
                </shiro hasPermission-->
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="comment" action="${ctx}/cms/comment/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="form-group">
                    <label class="control-label">文档标题：</label>
                    <form:input path="title" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <label class="control-label">状态：</label>
                    <form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag"
                                       items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value"
                                       htmlEscape="false"/>
                </div>
                <div class="form-group"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></div>
            </form:form>
            <table id="contentTable" class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>评论内容</th>
                    <th>文档标题</th>
                    <th>评论人</th>
                    <th>评论IP</th>
                    <th>评论时间</th>
                    <th nowrap="nowrap">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="comment">
                    <tr>
                        <td><a href="javascript:"
                               onclick="$('#c_${comment.id}').toggle()">${fns:abbr(fns:replaceHtml(comment.content),40)}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${comment.category.id}-${comment.contentId}${fns:getUrlSuffix()}"
                               title="${comment.title}"
                               onclick="return view(this.href);">${fns:abbr(comment.title,40)}</a></td>
                        <td>${comment.name}</td>
                        <td>${comment.ip}</td>
                        <td><fmt:formatDate value="${comment.createDate}" type="both"/></td>
                        <td><shiro:hasPermission name="cms:comment:edit">
                            <c:if test="${comment.delFlag ne '2'}"><a
                                    href="${ctx}/cms/comment/delete?id=${comment.id}${comment.delFlag ne 0?'&isRe=true':''}"
                                    onclick="return confirmx('确认要${comment.delFlag ne 0?'恢复审核':'删除'}该审核吗？', this.href)">${comment.delFlag ne 0?'恢复审核':'删除'}</a></c:if>
                            <c:if test="${comment.delFlag eq '2'}"><a
                                    href="${ctx}/cms/comment/save?id=${comment.id}">审核通过</a></c:if></shiro:hasPermission>
                        </td>
                    </tr>
                    <tr id="c_${comment.id}" style="background:#fdfdfd;display:none;">
                        <td colspan="6">${fns:replaceHtml(comment.content)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script></body>
</html>
