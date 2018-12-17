<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<div class="row">
    <div class="col-sm-2 hidden-xs" style="border-right: 1px solid #eee; padding-right: 0px;">
        <!-- 内容-->
        <div class="wrapper">
            <!-- 内容盒子-->
            <div class="box box-main">
                <!-- 内容盒子头部 -->
                <div class="box-header">
                    <div class="box-title">
                        <i class="fa fa-edit"></i>部门
                    </div>
                    <div class="box-tools pull-right">
                        <a type="button" class="btn btn-box-tool" href="#"
                           title="机构管理" onclick="top.addTabs({title: '机构管理', url: '${ctx}/sys/office/list'});">
                            <i class="fa fa-edit"></i></a>
                        <button type="button" class="btn btn-box-tool" id="btnExpand" title="展开" style="display:none;">
                            <i class="fa fa-chevron-up"></i></button>
                        <button type="button" class="btn btn-box-tool" id="btnCollapse" title="折叠"><i
                                class="fa fa-chevron-down"></i></button>
                        <button type="button" class="btn btn-box-tool" id="btnRefresh" title="刷新"><i
                                class="fa fa-refresh"></i></button>
                    </div>
                </div>
                <!-- 内容盒子身体 -->
                <div class="box-body">
                    <div id="ztree" class="ztree leftBox-content"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-10" style="padding-left: 0px;">
        <iframe id="iframeContent" name="iframeContent" src="${ctx}/sys/user/list" width="100%" height="100%"
                frameborder="0"></iframe>
    </div>
</div>
<script type="text/javascript">
    var tree;
    var setting = {
        data: {simpleData: {enable: true, idKey: "id", pIdKey: "pId", rootPId: '0'}},
        callback: {
            onClick: function (event, treeId, treeNode) {
                var id = treeNode.id == '0' ? '' : treeNode.id;
                $('#iframeContent').attr("src", "${ctx}/sys/user/list?office.id=" + id + "&office.name=" + treeNode.name);
            }
        }
    };

    function refreshTree() {
        $.getJSON("${ctx}/sys/office/treeData", function (data) {
            tree = $.fn.zTree.init($("#ztree"), setting, data);
            //tree.expandAll(true);
            // 展开第一级节点
            var nodes = tree.getNodesByParam("level", 0);
            for(var i=0; i<nodes.length; i++) {
                tree.expandNode(nodes[i], true, false, false);
            }
            // 展开第二级节点
            // 		nodes = tree.getNodesByParam("level", 1);
            // 		for(var i=0; i<nodes.length; i++) {
            // 			tree.expandNode(nodes[i], true, false, false);
            // 		}
        });
    }

    refreshTree();

    function refresh() {//刷新
        window.location = "${ctx}/sys/user/index";
    }

    // 工具栏按钮绑定
    $('#btnExpand').click(function () {
        tree.expandAll(true);
        $(this).hide();
        $('#btnCollapse').show();
    });
    $('#btnCollapse').click(function () {
        tree.expandAll(false);
        $(this).hide();
        $('#btnExpand').show();
    });
    $('#btnRefresh').click(function () {
        refresh();
    });
</script>
</body>
</html>