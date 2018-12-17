<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>部门管理</title>
    <meta name="decorator" content="default"/>
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
            <div class="box-title"><i class="fa fa-edit"></i>部门管理</div>
            <div class="box-tools pull-right">
                <shiro:hasPermission name="sys:user:add">
                    <a id="btnAdd" href="${ctx}/sys/office/form?parent.id=${office.id}" data-addTab="true" title="新增"  class="btn btn-default btn-sm"><i class="fa fa-plus"></i>新增</a>
                </shiro:hasPermission>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <table id="treeTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th>部门名称</th>
                    <th>归属区域</th>
                    <th>部门编码</th>
                    <th>部门类型</th>
                    <th>备注</th>
                    <shiro:hasPermission name="sys:office:edit">
                        <th>操作</th>
                    </shiro:hasPermission></tr>
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
        <td><a href="#"
               onclick="openDialogView('查看部门', '${ctx}/sys/office/form?id={{row.id}}','800px', '620px')">{{row.name}}</a>
        </td>
        <td>{{row.area.name}}</td>
        <td>{{row.code}}</td>
        <td>{{dict.type}}</td>
        <td>{{row.remarks}}</td>
        <td>
            <shiro:hasPermission name="sys:office:view">
                <a  id="btnView" class="btnView"  href="${ctx}/sys/office/form?id={{row.id}}&action=view" class="btn btn-success btn-sm" title="查看"><i class="fa fa-search-plus"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:office:edit">
                <a  id="btnEdit" class="btnEdit" href="${ctx}/sys/office/form?id={{row.id}}" class="btn btn-white btn-sm"  title="编辑"><i class="fa fa-pencil"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:office:del">
                <a  id="btnDelete" class="btnDelete" href="${ctx}/sys/office/delete?id={{row.id}}"
                   onclick="return confirmx('要删除该部门及所有子部门项吗？', this.href)" class="btn btn-white btn-sm"  title="删除"><i
                        class="fa fa-trash-o"></i></a>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:office:add">
                <a href="${ctx}/sys/office/form?parent.id={{row.id}}" class="btn  btn-white btn-sm"><i class="fa fa-plus"></i> 添加下级部门</a>
            </shiro:hasPermission>
        </td>
    </tr>
</script>
<script type="text/javascript">
    $(document).ready(function () {
        var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
        var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
        addRow("#treeTableList", tpl, data, rootId, true);
        $("#treeTable").treeTable({expandLevel: 2});
    });

    function addRow(list, tpl, data, pid, root) {
        for (var i = 0; i < data.length; i++) {
            var row = data[i];
            if ((${fns:jsGetVal('row.parentId')}) == pid) {
                $(list).append(Mustache.render(tpl, {
                    dict: {
                        type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
                    }, pid: (root ? 0 : pid), row: row
                }));
                addRow(list, tpl, data, row.id);
            }
        }
    }
</script>
</body>
</html>