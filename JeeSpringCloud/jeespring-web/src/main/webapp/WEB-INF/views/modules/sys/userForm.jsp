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
                用户管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post"
                       class="form-horizontal  content-background">
            <div class="content">
                <form:hidden path="id"/>
                <div class="form-unit">基本信息</div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>头像</label>
                        <div class="col-sm-8">
                            <form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255"
                                         class="input-xlarge"/>
                            <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false"
                                          maxWidth="100" maxHeight="100"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>归属公司</label>
                        <div class="col-sm-8">
                            <sys:treeselect id="company" name="company.id" value="${user.company.id}"
                                            labelName="company.name" labelValue="${user.company.name}"
                                            title="公司" url="/sys/office/treeData?type=1"
                                            cssClass="form-control required"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>归属部门</label>
                        <div class="col-sm-8">
                            <sys:treeselect id="office" name="office.id" value="${user.office.id}"
                                            labelName="office.name" labelValue="${user.office.name}"
                                            title="部门" url="/sys/office/treeData?type=2"
                                            cssClass="form-control required" notAllowSelectParent="true"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>工号</label>
                        <div class="col-sm-8">
                            <form:input path="no" htmlEscape="false" maxlength="50"
                                        class="form-control required"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>姓名</label>
                        <div class="col-sm-8">
                            <form:input path="name" htmlEscape="false" maxlength="50"
                                        class="form-control required"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font color="red">*</font>登录名</label>
                        <div class="col-sm-8">
                            <input id="oldLoginName" name="oldLoginName" type="hidden"
                                   value="${user.loginName}">
                            <form:input path="loginName" htmlEscape="false" maxlength="50"
                                        class="form-control required userName"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><c:if
                                test="${empty user.id}"><font
                                color="red">*</font></c:if>密码</label>
                        <div class="col-sm-8">
                            <input id="newPassword" name="newPassword" type="password" value="" maxlength="50"
                                   minlength="3" class="form-control ${empty user.id?'required':''}"/>
                            <c:if test="${not empty user.id}"><span
                                    class="help-inline">若不修改密码，请留空。</span></c:if>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><c:if
                                test="${empty user.id}"><font
                                color="red">*</font></c:if>确认密码</label>
                        <div class="col-sm-8">
                            <input id="confirmNewPassword" name="confirmNewPassword" type="password"
                                   class="form-control ${empty user.id?'required':''}" value="" maxlength="50"
                                   minlength="3" equalTo="#newPassword"/>
                        </div>
                    </div>
                </div>
                <div class="form-unit">详细信息</div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">邮箱</label>
                        <div class="col-sm-8">
                            <form:input path="email" htmlEscape="false" maxlength="100"
                                        class="form-control email"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">电话</label>
                        <div class="col-sm-8">
                            <form:input path="phone" htmlEscape="false" maxlength="100"
                                        class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">手机</label>
                        <div class="col-sm-8">
                            <form:input path="mobile" htmlEscape="false" maxlength="100"
                                        class="form-control"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">是否允许登录</label>
                        <div class="col-sm-8">
                            <form:select path="loginFlag" class="form-control">
                                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label"
                                              itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">用户类型</label>
                        <div class="col-sm-8">
                            <form:select path="userType" class="form-control">
                                <form:option value="" label="请选择"/>
                                <form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label"
                                              itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left"><font
                                color="red">*</font>用户角色</label>
                        <div class="col-sm-8">
                            <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name"
                                             itemValue="id"
                                             htmlEscape="false" cssClass="i-checks required"/>
                            <label id="roleIdList-error" for="roleIdList"></label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-sm-4 pull-left">备注</label>
                        <div class="col-sm-8">
                            <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200"
                                           class="form-control"/>
                        </div>
                    </div>
                    <div class="col-xs-6">
                    </div>
                </div>
                <c:if test="${not empty user.id}">
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">创建时间</label>
                            <div class="col-sm-8">
                                <fmt:formatDate value="${user.createDate}" type="both"
                                                dateStyle="full"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">最后登陆</label>
                            <div class="col-sm-8">
                                IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间<fmt:formatDate
                                    value="${user.loginDate}" type="both" dateStyle="full"/>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div id="iframeSave" class="form-group">
                    <c:if test="${action ne 'view'}">
                        <a id="btnSubmit" class="btn btn-primary">保存</a>
                    </c:if>
                    <a id="btnBack" class="btn btn-default">返回</a>
                </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
<script src="/staticViews/modules/sys/userForm.js"></script>
</body>
</html>