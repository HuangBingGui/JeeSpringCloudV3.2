<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）" %>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）" %>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）" %>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）" %>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题" %>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址" %>
<%@ attribute name="checked" type="java.lang.Boolean" required="false"
              description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true" %>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）" %>
<%@ attribute name="isAll" type="java.lang.Boolean" required="false"
              description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）" %>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点" %>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点" %>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）" %>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false"
              description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）" %>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除" %>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写" %>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式" %>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式" %>
<%@ attribute name="smallBtn" type="java.lang.Boolean" required="false" description="缩小按钮显示" %>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮" %>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled" %>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description="" %>
<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"/>
<div class="input-group">
    <input id="${id}Name" name="${labelName}" ${allowInput?'':'readonly="readonly"'} type="text" value="${labelValue}"
           data-msg-required="${dataMsgRequired}"
           class="${cssClass}" style="${cssStyle}"/>
    <a class="input-group-addon" id="${id}Button" href="javascript:">
        <i class="fa fa-search"></i>
    </a>
</div>
<label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
    $("#${id}Button, #${id}Name").click(function () {
        // 是否限制选择，如果限制，设置为disabled
        if ($("#${id}Button").hasClass("disabled")) {
            return true;
        }
        var jeeSpringLayer;
        if(top.layer) jeeSpringLayer=top.layer;
        else jeeSpringLayer=layer;
        // 正常打开
        jeeSpringLayer.open({
            type: 2,
            area: ['300px', '420px'],
            title: "选择${title}",
            ajaxData: {selectIds: $("#${id}Id").val()},
            content: "${ctx}/tag/treeselect?url=" + encodeURIComponent("${url}") + "&module=${module}&checked=${checked}&extId=${extId}&isAll=${isAll}",
            btn: ['确定', '关闭']
            , yes: function (index, layero) { //或者使用btn1
                var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                var ids = [], names = [], nodes = [];
                if ("${checked}" == "true") {
                    nodes = tree.getCheckedNodes(true);
                } else {
                    nodes = tree.getSelectedNodes();
                }
                for (var i = 0; i < nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
                    if (nodes[i].isParent) {
                        continue; // 如果为复选框选择，则过滤掉父节点
                    }//</c:if><c:if test="${notAllowSelectRoot}">
                    if (nodes[i].level == 0) {
                        //top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
                        top.layer.msg("不能选择根节点（" + nodes[i].name + "）请重新选择。", {icon: 0});
                        return false;
                    }//</c:if><c:if test="${notAllowSelectParent}">
                    if (nodes[i].isParent) {
                        //top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
                        //layer.msg('有表情地提示');
                        jeeSpringLayer.msg("不能选择父节点（" + nodes[i].name + "）请重新选择。", {icon: 0});
                        return false;
                    }//</c:if><c:if test="${not empty module && selectScopeModule}">
                    if (nodes[i].module == "") {
                        //top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
                        jeeSpringLayer.msg("不能选择公共模型（" + nodes[i].name + "）请重新选择。", {icon: 0});
                        return false;
                    } else if (nodes[i].module != "${module}") {
                        //top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
                        jeeSpringLayer.msg("不能选择当前栏目以外的栏目模型，请重新选择。", {icon: 0});
                        return false;
                    }//</c:if>
                    ids.push(nodes[i].id);
                    names.push(nodes[i].name);//<c:if test="${!checked}">
                    break; // 如果为非复选框选择，则返回第一个选择  </c:if>
                }
                $("#${id}Id").val(ids.join(",").replace(/u_/ig, ""));
                $("#${id}Name").val(names.join(","));
                $("#${id}Name").focus();
                jeeSpringLayer.close(index);
            },
            cancel: function (index) { //或者使用btn2
                //按钮【按钮二】的回调
            }
        });

    });
</script>