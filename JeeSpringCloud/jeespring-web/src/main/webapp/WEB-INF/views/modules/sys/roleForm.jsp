<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>
                <c:if test="${action ne 'view'}">
                    <c:if test="${empty oaNotify.id}">新增</c:if>
                    <c:if test="${not empty oaNotify.id}">编辑</c:if>
                </c:if>
                <c:if test="${action eq 'view'}">查看</c:if>
                角色管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="role" autocomplete="off" action="${ctx}/sys/role/save"
                       method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <sys:message content="${message}"/>
                    <form:hidden path="menuIds"/>
                    <div class="form-unit">基本信息</div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">归属机构</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="office" name="office.id" value="${role.office.id}"
                                                labelName="office.name"
                                                labelValue="${role.office.name}"
                                                title="机构" url="/sys/office/treeData"
                                                cssClass="form-control required"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>角色名称</label>
                            <div class="col-sm-8">
                                <input id="oldName" name="oldName" type="hidden" value="${role.name}">
                                <form:input path="name" htmlEscape="false" maxlength="50"
                                            class="form-control required"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>英文名称:</label>
                            <div class="col-sm-8">
                                <input id="oldEnname" name="oldEnname" type="hidden"
                                       value="${role.enname}">
                                <form:input path="enname" htmlEscape="false" maxlength="50"
                                            class="form-control required"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">角色类型</label>
                            <div class="col-sm-8">
                                <form:select path="roleType" class="form-control ">
                                    <form:option value="assignment">任务分配</form:option>
                                    <form:option value="security-role">管理角色</form:option>
                                    <form:option value="user">普通角色</form:option>
                                </form:select>
                                <span class="help-inline"
                                      title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
							工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">是否系统数据</label>
                            <div class="col-sm-8">
                                <form:select path="sysData" class="form-control ">
                                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                                  htmlEscape="false"/>
                                </form:select>
                                <span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><label class="pull-left">是否可用</label></label>
                            <div class="col-sm-8">
                                <form:select path="useable" class="form-control ">
                                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                                  htmlEscape="false"/>
                                </form:select>
                                <span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">数据范围</label>
                            <div class="col-sm-8">
                                <form:select path="dataScope" class="form-control ">
                                    <form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label"
                                                  itemValue="value"
                                                  htmlEscape="false"/>
                                </form:select>
                                <span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
                                <div class="controls">
                                    <div id="officeTree" class="ztree" style="margin-top:3px;"></div>
                                    <form:hidden path="officeIds"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><label class="pull-left">备注</label></label>
                            <div class="col-sm-8">
                                <form:textarea path="remarks" htmlEscape="false" rows="3"
                                               maxlength="200"
                                               class="form-control "/>
                            </div>
                        </div>
                    </div>
                    <div id="iframeSave" class="form-group">
                        <c:if test="${action ne 'view'}">
                            <a id="btnSubmit" class="btn btn-primary" >保存</a>
                        </c:if>
                        <a id="btnBack" class="btn btn-default" >返回</a>
                    </div>
                </div>
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
            rules: {
                name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},//设置了远程验证，在初始化时必须预先调用一次。
                enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
            },
            messages: {
                name: {remote: "角色名已存在"},
                enname: {remote: "英文名已存在"}
            },
            submitHandler: function (form) {
                //var ids = [], nodes = tree.getCheckedNodes(true);
                //for(var i=0; i<nodes.length; i++) {
                //	ids.push(nodes[i].id);
                //}
                //$("#menuIds").val(ids);
                var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                for (var i = 0; i < nodes2.length; i++) {
                    ids2.push(nodes2[i].id);
                }
                $("#officeIds").val(ids2);
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

        //在ready函数中预先调用一次远程校验函数，是一个无奈的回避案。(刘高峰）
        //否则打开修改对话框，不做任何更改直接submit,这时再触发远程校验，耗时较长，
        //submit函数在等待远程校验结果然后再提交，而layer对话框不会阻塞会直接关闭同时会销毁表单，因此submit没有提交就被销毁了导致提交表单失败。
        $("#inputForm").validate().element($("#name"));
        $("#inputForm").validate().element($("#enname"));

        var setting = {
            check: {enable: true, nocheckInherit: true}, view: {selectedMulti: false},
            data: {simpleData: {enable: true}}, callback: {
                beforeClick: function (id, node) {
                    tree.checkNode(node, !node.checked, true, true);
                    return false;
                }
            }
        };


        // 用户-机构
        var zNodes2 = [
                <c:forEach items="${officeList}" var="office">{
                id: "${office.id}",
                pId: "${not empty office.parent?office.parent.id:0}",
                name: "${office.name}"
            },
            </c:forEach>];
        // 初始化树结构
        var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
        // 不选择父节点
        tree2.setting.check.chkboxType = {"Y": "ps", "N": "s"};
        // 默认选择节点
        var ids2 = "${role.officeIds}".split(",");
        for (var i = 0; i < ids2.length; i++) {
            var node = tree2.getNodeByParam("id", ids2[i]);
            try {
                tree2.checkNode(node, true, false);
            } catch (e) {
            }
        }
        // 默认展开全部节点
        tree2.expandAll(true);
        // 刷新（显示/隐藏）机构
        refreshOfficeTree();
        $("#dataScope").change(function () {
            refreshOfficeTree();
        });
    }

    function refreshOfficeTree() {
        if ($("#dataScope").val() == 9) {
            $("#officeTree").show();
        } else {
            $("#officeTree").hide();
        }
    }
</script>
</body>
</html>