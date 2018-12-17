<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>区域管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>区域管理</div>
            <div class="box-tools pull-right">
                <shiro:hasPermission name="sys:area:add">
                    <a id="btnAdd" href="${ctx}/sys/area/form"
                       class="btn btn-default btn-sm" title="新增"><i
                            class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <button data-placement="left" onclick="refresh()"
                     class="btn btn-default btn-sm" title="刷新"><i class="glyphicon glyphicon-repeat"></i>刷新
                </button>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 工具栏 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="pull-left"></div>
                </div>
            </div>
            <table id="treeTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th>区域名称</th>
                    <th>区域编码</th>
                    <th>区域类型</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="treeTableList"></tbody>
            </table>
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
<script type="text/template" id="treeTableTpl">
    <tr id="{{row.id}}" pId="{{pid}}">
        <td><a href="${ctx}/sys/area/form?id={{row.id}}">{{row.name}}</a>
        </td>
        <td>{{row.code}}</td>
        <td>{{dict.type}}</td>
        <td>{{row.remarks}}</td>
        <td>
            <shiro:hasPermission name="sys:area:view">
                <a href="${ctx}/sys/area/form?id={{row.id}}" title="查看"><i class="fa fa-search-plus"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:area:edit">
                <a href="${ctx}/sys/area/form?id={{row.id}}"
                    title="编辑"><i class="fa fa-pencil"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:area:del">
                <a href="${ctx}/sys/area/delete?id={{row.id}}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)"
                    title="删除"><i class="fa fa-trash-o"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:area:add">
                <a href="${ctx}/sys/area/form?parent.id={{row.id}}"
                   class="btn btn-sm"><i class="fa fa-plus"></i> 添加下级区域</a>
            </shiro:hasPermission>
        </td>
    </tr>
</script>
<script type="text/javascript">
    $(document).ready(function () {
        var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
        var data = ${fns:toJson(list)}, rootId = "0";
        addRow("#treeTableList", tpl, data, rootId, true);
        $("#treeTable").treeTable({expandLevel: 2});
    });

    function addRow(list, tpl, data, pid, root) {
        for (var i = 0; i < data.length; i++) {
            var row = data[i];
            if ((${fns:jsGetVal('row.parentId')}) == pid) {
                $(list).append(Mustache.render(tpl, {
                    dict: {
                        type: getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, row.type)
                    }, pid: (root ? 0 : pid), row: row
                }));
                addRow(list, tpl, data, row.id);
            }
        }
    }

    function refresh() {//刷新
        window.location = "${ctx}/sys/area/";
    }
</script>
</body>
</html>