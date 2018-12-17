<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜单管理</title>
    <meta name="decorator" content="default"/>
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
            <div class="box-title"><i class="fa fa-edit"></i>
                <c:if test="${action ne 'view'}">
                    <c:if test="${empty oaNotify.id}">新增</c:if>
                    <c:if test="${not empty oaNotify.id}">编辑</c:if>
                </c:if>
                <c:if test="${action eq 'view'}">查看</c:if>
                菜单管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <sys:message content="${message}"/>
                    <div class="form-unit">基本信息</div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">上级菜单</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}"
                                                labelName="parent.name"
                                                labelValue="${menu.parent.name}"
                                                title="菜单" url="/sys/menu/treeData" extId="${menu.id}"
                                                cssClass="form-control required"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>名称</label>
                            <div class="col-sm-8">
                                <form:input path="name" htmlEscape="false" maxlength="50"
                                            class="required form-control "/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">链接</label>
                            <div class="col-sm-8">
                                <form:input path="href" htmlEscape="false" maxlength="2000"
                                            class="form-control "/>
                                <span class="help-inline">点击菜单跳转的页面</span>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>目标</label>
                            <div class="col-sm-8">
                                <form:input path="target" htmlEscape="false" maxlength="10"
                                            class="form-control "/>
                                <span class="help-inline">链接打开的目标窗口，默认：mainFrame</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">图标</label>
                            <div class="col-sm-8">
                                <sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">排序</label>
                            <div class="col-sm-8">
                                <form:input path="sort" htmlEscape="false" maxlength="50"
                                            class="required digits form-control "/>
                                <span class="help-inline">排列顺序，升序。</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">可见</label>
                            <div class="col-sm-8">
                                <form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}"
                                                   itemLabel="label" itemValue="value" htmlEscape="false"
                                                   class="required i-checks "/>
                                <span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">权限标识</label>
                            <div class="col-sm-8">
                                <form:input path="permission" htmlEscape="false" maxlength="100"
                                            class="form-control "/>
                                <span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">备注</label>
                            <div class="col-sm-8">
                                <form:textarea path="remarks" htmlEscape="false" rows="3"
                                               maxlength="200" class="form-control "/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"></label>
                            <div class="col-sm-8">
                            </div>
                        </div>
                    </div>

                    <div id="iframeSave" class="form-group">
                        <c:if test="${action ne 'view'}"><a id="btnSubmit" class="btn btn-primary">保存</a></c:if>
                        <a id="btnBack" class="btn btn-default">返回</a>
                        <!--a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a-->
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
</body>
</html>