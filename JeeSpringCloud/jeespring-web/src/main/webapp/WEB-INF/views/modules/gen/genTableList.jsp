<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>表单管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>表单管理：<br>生成单表、主附表、树表、列表和表单、增删改查云接口、redis高速缓存对接代码、图表统计、地图统计、vue.js、生成菜单
            </div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="筛选">
                    <i class="fa fa-filter"></i>筛选
                </a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新">
                    <i class="glyphicon glyphicon-repeat"></i>刷新
                </button>
                <shiro:hasPermission name="gen:genTable:add">
                    <a id="btnAdd" href="${ctx}/gen/genTable/form" title="增加" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:del">
                    <a id="btnDeleteAll" href="${ctx}/gen/genTable/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:importDb">
                    <a href="${ctx}/gen/genTable/importTableFromDB" title="导入"
                       class="btn btn-default btn-sm"><i class="fa fa-folder-open-o"></i>导入</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="gen:genTable:genCode">
                    <a href="#" title="生成代码"
                       class="btn btn-default btn-sm" onclick="genCode()"><i class="fa fa-folder-open-o"></i>生成代码</a>
                </shiro:hasPermission>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="genTable" action="${ctx}/gen/genTable/" method="post"
                       class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                <div class="form-group">
                    <span>表名：</span><form:input path="nameLike" htmlEscape="false" maxlength="50"
                                                class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>说明：</span><form:input path="comments" htmlEscape="false" maxlength="50"
                                                class=" form-control input-sm"/>
                </div>
                <div class="form-group">
                    <span>父表表名：</span><form:input path="parentTable" htmlEscape="false" maxlength="50"
                                                  class=" form-control input-sm"/>
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
                    <th class="sort-column table_type">表类型</th>
                    <th class="sort-column name">表名</th>
                    <th>说明</th>
                    <th class="sort-column class_name">类名</th>
                    <th class="sort-column parent_table">主表</th>
                    <th class="sort-column isSync">同步数据库</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="genTable">
                    <tr>
                        <td><input type="checkbox" id="${genTable.id}" class="i-checks"></td>
                        <td>${ fns:getDictLabel (genTable.tableType,'table_type',null)}</td>
                        <td><a href="#"
                               onclick="openDialogView('查看表单', '${ctx}/gen/genTable/form?id=${genTable.id}','1000px', '700px')">${genTable.name}</a>
                        </td>
                        <td>${genTable.comments}</td>
                        <td>${genTable.className}</td>
                        <td title="点击查询子表"><a href="javascript:"
                                              onclick="$('#parentTable').val('${genTable.parentTable}');$('#searchForm').submit();">${genTable.parentTable}</a>
                        </td>
                        <td ${genTable.isSync == '0'?'style="background-color:red"':''}>${genTable.isSync == '0'?'<font color=\"white\">未同步</font>':'已同步'}</td>
                        <td>
                            <shiro:hasPermission name="gen:genTable:edit">
                                <a href="${ctx}/gen/genTable/form?id=${genTable.id}" title="修改"><i
                                        class="fa fa-pencil"></i> </a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="gen:genTable:del">
                                <a href="${ctx}/gen/genTable/delete?id=${genTable.id}"
                                   onclick="return confirmx('确认要移除该条记录吗？', this.href)"><i class="fa fa-trash"></i>
                                    移除</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="gen:genTable:del">
                                <a href="${ctx}/gen/genTable/deleteDb?id=${genTable.id}"
                                   onclick="return confirmx('确认要删除该条记录并删除对应的数据库表吗？', this.href)"><i
                                        class="fa fa-trash-o"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="gen:genTable:synchDb">
                                <a href="${ctx}/gen/genTable/synchDb?id=${genTable.id}"
                                   onclick="return confirmx('确认要强制同步数据库吗？同步数据库将删除所有数据重新建表！', this.href)"><i
                                        class="fa fa-database"></i> 同步数据库</a>
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
<script type="text/javascript">
    function genCode() {
        // var url = $(this).attr('data-url');
        var size = $("#contentTable tbody tr td input.i-checks:checked").length;
        if (size == 0) {
            top.layer.alert('请至少选择一条数据!', {icon: 0, title: '警告'});
            return;
        }
        if (size > 1) {
            top.layer.alert('只能选择一条数据!', {icon: 0, title: '警告'});
            return;
        }
        var id = $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("id");
        var tableType = $("#contentTable tbody tr td input.i-checks:checkbox:checked").closest("td").next().text();
        if (tableType.indexOf("附表") >= 0) {
            top.layer.alert('不能选择附表生成代码，请选择主表!', {icon: 0, title: '警告'});
            return;
        }
        var isSync = $("#contentTable tbody tr td input.i-checks:checkbox:checked").closest("td").next().next().next().next().next().next().text();
        if (isSync.indexOf("未同步") >= 0) {
            //top.layer.alert('请同步数据库!', {icon: 0, title:'警告'});
            //return;
        }
        window.location.href = '${ctx}/gen/genTable/genCodeForm?genTable.id=' + id;
    }
</script>
</body>
</html>
