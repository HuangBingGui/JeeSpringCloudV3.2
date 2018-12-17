<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>区域管理</title>
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
            <form:form id="inputForm" modelAttribute="area" action="${ctx}/sys/area/save" method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <div class="form-unit">基本信息</div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">上级区域</label>
                            <div class="col-sm-8">
                                <sys:treeselect id="area" name="parent.id" value="${area.parent.id}"
                                                labelName="parent.name"
                                                labelValue="${area.parent.name}"
                                                title="区域" url="/sys/area/treeData" extId="${area.id}"
                                                cssClass="form-control m-s" allowClear="true"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">区域名称</label>
                            <div class="col-sm-8">
                                <form:input path="name" htmlEscape="false" maxlength="50"
                                            class="form-control required"/></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left"><font color="red">*</font>区域编码</label>
                            <div class="col-sm-8">
                                <form:input path="code" htmlEscape="false" maxlength="50"
                                            class="form-control"/>
                            </div>
                        </div>
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">区域类型</label>
                            <div class="col-sm-8">
                                <form:select path="type" class="form-control">
                                    <form:options items="${fns:getDictList('sys_area_type')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6 form-group">
                            <label class="control-label col-sm-4 pull-left">备注</label>
                            <div class="col-sm-8">
                                <form:textarea path="remarks" htmlEscape="false" rows="3"
                                               maxlength="200" class="form-control"/>
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