<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>定时任务调度管理</title>
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
                定时任务调度管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="sysJob" action="${ctx}/job/sysJob/save" method="post"
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
                        <label class="control-label col-sm-2 pull-left">cron执行表达式</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="cron执行表达式" path="cronExpression" htmlEscape="false" maxlength="255"
                                        class="form-control "/>
                            <div class="help-block">请填写cron执行表达式</div>
                            <div class="help-block">Cron表达式生成器:<a href="http://www.pdtools.net/tools/becron.jsp"
                                                                  target="_blank">http://www.pdtools.net/tools/becron.jsp</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">计划执行错误策略（0默认 1继续 2等待 3放弃）</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:radiobuttons path="misfirePolicy" items="${fns:getDictList('misfire_policy')}"
                                               itemLabel="label" itemValue="value" htmlEscape="false"
                                               class="i-checks "/>
                            <div class="help-block">请填写计划执行错误策略（0默认 1继续 2等待 3放弃）</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">状态（0正常 1暂停）</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:radiobuttons path="status" items="${fns:getDictList('job_status')}" itemLabel="label"
                                               itemValue="value" htmlEscape="false" class="i-checks "/>
                            <div class="help-block">请选择状态（0正常 1暂停）</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">备注信息</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:textarea path="remark" htmlEscape="false" rows="4" maxlength="500"
                                           class="form-control "/>
                            <sys:ckeditor replace="remark" height="400" uploadPath="/job/sysJob"/>
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
<script src="/staticViews/modules/job/sysJobForm.js" type="text/javascript"></script>
<link href="/staticViews/modules/job/sysJobForm.css" rel="stylesheet"/>
</body>
</html>