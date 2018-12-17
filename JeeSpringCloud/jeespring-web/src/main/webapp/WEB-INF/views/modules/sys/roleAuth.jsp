<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main" style="height: 100%;">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>
                角色管理
            </div>
            <div class="box-tools pull-right">
            <a id="btnSubmit" class="btn btn-primary" >保存</a>
            <a id="btnBack" class="btn btn-default" >返回</a>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body" style="position: absolute;top: 49px;bottom: 0px;overflow: auto;width:100%">
            <form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post"
                       class="form-horizontal">
                <form:hidden path="id"/>
                <input name="office.id" type="hidden" value="${role.office.id}">
                <input name="office.name" type="hidden" value="${role.office.name}">
                <input name="name" type="hidden" value="${role.name}">
                <input name="oldName" type="hidden" value="${role.name}">
                <input name="enname" type="hidden" value="${role.enname}">
                <input name="oldEnname" type="hidden" value="${role.enname}">
                <input name="roleType" type="hidden" value="${role.roleType}">
                <input name="sysData" type="hidden" value="${role.sysData}">
                <input name="useable" type="hidden" value="${role.useable}">
                <input name="dataScope" type="hidden" value="${role.dataScope}">
                <input name="remarks" type="hidden" value="${role.remarks}">
                <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
                <form:hidden path="menuIds"/>
                <form:hidden path="officeIds"/>
            </form:form>
        </div>
    </div>
</div>
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
<script type="text/javascript">
    function validateFormPage() {
        $("#name").focus();
        jeeSpring.inputForm = $("#inputForm").validate({

            submitHandler: function (form) {
                var ids = [], nodes = tree.getCheckedNodes(true);
                for (var i = 0; i < nodes.length; i++) {
                    ids.push(nodes[i].id);
                }
                $("#menuIds").val(ids);
                loading('正在提交，请稍等...');
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                $("#messageBox").text("输入有误，请先更正。");
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

        var setting = {
            check: {enable: true, nocheckInherit: true}, view: {selectedMulti: false},
            data: {simpleData: {enable: true}}, callback: {
                beforeClick: function (id, node) {
                    tree.checkNode(node, !node.checked, true, true);
                    return false;
                }
            }
        };

        // 用户-菜单
        var zNodes = [
                <c:forEach items="${menuList}" var="menu">{
                id: "${menu.id}",
                pId: "${not empty menu.parent.id?menu.parent.id:0}",
                name: "${not empty menu.parent.id?menu.name:'权限列表'}"
            },
            </c:forEach>];
        // 初始化树结构
        var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
        // 不选择父节点
        tree.setting.check.chkboxType = {"Y": "ps", "N": "s"};
        // 默认选择节点
        var ids = "${role.menuIds}".split(",");
        for (var i = 0; i < ids.length; i++) {
            var node = tree.getNodeByParam("id", ids[i]);
            try {
                tree.checkNode(node, true, false);
            } catch (e) {
            }
        }
        // 默认展开全部节点
        tree.expandAll(true);
    };
</script>
</body>
</html>