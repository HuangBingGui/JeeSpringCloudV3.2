<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>定时任务调度日志管理</title>
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
                定时任务调度日志管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="sysJobLog" action="${ctx}/job/sysJobLog/save" method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">任务名称<font color="red">*</font></label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="任务名称" path="jobName" htmlEscape="false" maxlength="64"
                                        class="form-control required"/>
                            <div class="help-block">请填写任务名称</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">任务组名<font color="red">*</font></label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="任务组名" path="jobGroup" htmlEscape="false" maxlength="64"
                                        class="form-control required"/>
                            <div class="help-block">请填写任务组名</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">任务方法</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="任务方法" path="methodName" htmlEscape="false" maxlength="500"
                                        class="form-control "/>
                            <div class="help-block">请填写任务方法</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">方法参数</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="方法参数" path="methodParams" htmlEscape="false" maxlength="200"
                                        class="form-control "/>
                            <div class="help-block">请填写方法参数</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">日志信息</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="日志信息" path="jobMessage" htmlEscape="false" maxlength="500"
                                        class="form-control "/>
                            <div class="help-block">请填写日志信息</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">执行状态（0正常 1失败）</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:radiobuttons path="status" items="${fns:getDictList('job_status')}" itemLabel="label"
                                               itemValue="value" htmlEscape="false" class="i-checks "/>
                            <div class="help-block">请选择执行状态（0正常 1失败）</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">异常信息</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:textarea path="exceptionInfo" htmlEscape="false" rows="4" class="form-control "/>
                            <sys:ckeditor replace="exceptionInfo" height="400" uploadPath="/job/sysJobLog"/>
                        </div>
                    </div>
                    <div class="form-group">
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
<script src="/staticViews/modules/job/sysJobLogForm.js" type="text/javascript"></script>
<link href="/staticViews/modules/job/sysJobLogForm.css" rel="stylesheet"/>
</body>
</html>