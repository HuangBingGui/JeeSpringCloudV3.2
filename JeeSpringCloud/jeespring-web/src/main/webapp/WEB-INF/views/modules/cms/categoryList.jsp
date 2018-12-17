<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>栏目管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>

    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#treeTable").treeTable({expandLevel: 3});
        });

        function updateSort() {
            //loading('正在提交，请稍等...');
            $("#listForm").attr("action", "${ctx}/cms/category/updateSort");
            $("#listForm").submit();
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
            <div class="box-title"><i class="fa fa-edit"></i>栏目管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
                <shiro:hasPermission name="cms:category:edit">
                    <a class="btn btn-default btn-sm"  href="${ctx}/cms/category/form">添加</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cms:category:edit">
                    <a class="btn btn-default btn-sm" onclick="updateSort();"/>保存排序</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form id="listForm" method="post">
                <table id="treeTable" class="table table-striped table-bordered table-condensed">
                    <tr>
                        <th>栏目名称</th>
                        <th>归属机构</th>
                        <th>栏目模型</th>
                        <th style="text-align:center;">排序</th>
                        <th title="是否在导航中显示该栏目">导航菜单</th>
                        <th title="是否在分类页中显示该栏目的文章列表">栏目列表</th>
                        <th>展现方式</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${list}" var="tpl">
                        <tr id="${tpl.id}" pId="${tpl.parent.id ne '1'?tpl.parent.id:'0'}">
                            <td><a href="${ctx}/cms/category/form?id=${tpl.id}">${tpl.name}</a></td>
                            <td>${tpl.office.name}</td>
                            <td>${fns:getDictLabel(tpl.module, 'cms_module', '公共模型')}</td>
                            <td style="text-align:center;">
                                <shiro:hasPermission name="cms:category:edit">
                                    <input type="hidden" name="ids" value="${tpl.id}"/>
                                    <input name="sorts" type="text" value="${tpl.sort}"
                                           style="width:50px;margin:0;padding:0;text-align:center;">
                                </shiro:hasPermission><shiro:lacksPermission name="cms:category:edit">
                                ${tpl.sort}
                            </shiro:lacksPermission>
                            </td>
                            <td>${fns:getDictLabel(tpl.inMenu, 'show_hide', '隐藏')}</td>
                            <td>${fns:getDictLabel(tpl.inList, 'show_hide', '隐藏')}</td>
                            <td>${fns:getDictLabel(tpl.showModes, 'cms_show_modes', '默认展现方式')}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}${fns:getFrontPath()}/list-${tpl.id}${fns:getUrlSuffix()}"
                                   target="_blank">访问</a>
                                <shiro:hasPermission name="cms:category:edit">
                                    <a href="${ctx}/cms/category/form?id=${tpl.id}">修改</a>
                                    <a href="${ctx}/cms/category/delete?id=${tpl.id}"
                                       onclick="return confirmx('要删除该栏目及所有子栏目项吗？', this.href)">删除</a>
                                    <a href="${ctx}/cms/category/form?parent.id=${tpl.id}">添加下级栏目</a>
                                </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
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