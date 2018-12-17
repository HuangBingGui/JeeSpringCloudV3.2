function refresh() {//刷新
    window.location= ctx+"/sys/user/list";
}
var setting = {
    data: {simpleData: {enable: true, idKey: "id", pIdKey: "pId", rootPId: '0'}},
    callback: {
        onClick: function (event, treeId, treeNode) {
            var id = treeNode.id == '0' ? '' : treeNode.id;
            window.location=ctx+"/sys/user/list?office.id=" + id + "&office.name=" + encodeURI(treeNode.name);
        }
    }
};
function refreshTree() {
    $.getJSON(ctx+"/sys/office/treeData", function (data) {
        $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
    });
}
refreshTree();