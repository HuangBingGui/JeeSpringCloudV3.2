<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>部门管理</title>
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
                部门管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <div class="form-unit">基本信息</div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">上级部门</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}"
                                                labelName="parent.name" labelValue="${office.parent.name}" title="部门"
                                                url="/sys/office/treeData" extId="${office.id}" cssClass="form-control"
                                                allowClear="${office.currentUser.admin}"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>归属区域</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name"
                                                labelValue="${office.area.name}" title="区域" url="/sys/area/treeData"
                                                cssClass="form-control required"/></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>部门名称</label>
                            <div class="col-sm-8"><form:input path="name" htmlEscape="false" maxlength="50"
                                                              class="form-control required"/></div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">部门编码</label>
                            <div class="col-sm-8"><form:input path="code" htmlEscape="false" maxlength="50"
                                                              class="form-control"/></div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">部门类型</label>
                            <div class="col-sm-8">
                                <form:select path="type" class="form-control">
                                    <form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">部门级别</label>
                            <div class="col-sm-8">
                                <form:select path="grade" class="form-control">
                                    <form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">是否可用</label>
                            <div class="col-sm-8">
                                <form:select path="useable" class="form-control">
                                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                  itemValue="value"
                                                  htmlEscape="false"/>
                                </form:select>
                                <!--span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span-->
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">主负责人</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="primaryPerson" name="primaryPerson.id"
                                                value="${office.primaryPerson.id}"
                                                labelName="office.primaryPerson.name"
                                                labelValue="${office.primaryPerson.name}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="form-control" allowClear="true"
                                                notAllowSelectParent="true"/>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">副负责人</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="deputyPerson" name="deputyPerson.id"
                                                value="${office.deputyPerson.id}"
                                                labelName="office.deputyPerson.name"
                                                labelValue="${office.deputyPerson.name}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="form-control" allowClear="true"
                                                notAllowSelectParent="true"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">联系地址</label>
                            <div class="col-sm-8">
                                <form:input path="address" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-unit">详细信息</div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">邮政编码</label>
                            <div class="col-sm-8">
                                <form:input path="zipCode" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">负责人</label>
                            <div class="col-sm-8">
                                <form:input path="master" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">电话</label>
                            <div class="col-sm-8">
                                <form:input path="phone" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">传真</label>
                            <div class="col-sm-8">
                                <form:input path="fax" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">邮箱</label>
                            <div class="col-sm-8">
                                <form:input path="email" htmlEscape="false" maxlength="50"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">备注</label>
                            <div class="col-sm-8">
                                <form:textarea path="remarks" htmlEscape="false" rows="3"
                                               maxlength="200"
                                               class="form-control"/>
                            </div>
                        </div>
                    </div>

                    <div id="iframeSave" class="form-group">
                        <c:if test="${action ne 'view'}">
                            <a id="btnSubmit" class="btn btn-primary">保存</a>
                        </c:if>
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