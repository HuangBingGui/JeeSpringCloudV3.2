<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>在线用户记录管理</title>
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
                在线用户记录管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="sysUserOnline" action="${ctx}/sys/sysUserOnline/save"
                       method="post" class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <sys:message content="${message}"/>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">登录账号:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="登录账号" path="loginName" htmlEscape="false" maxlength="50"
                                        class="form-control "/>
                            <div class="help-block">请填写登录账号</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">部门名称:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="部门名称" path="deptName" htmlEscape="false" maxlength="50"
                                        class="form-control "/>
                            <div class="help-block">请填写部门名称</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">登录IP地址:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="登录IP地址" path="ipaddr" htmlEscape="false" maxlength="50"
                                        class="form-control "/>
                            <div class="help-block">请填写登录IP地址</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">登录地点:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="登录地点" path="loginLocation" htmlEscape="false" maxlength="255"
                                        class="form-control "/>
                            <div class="help-block">请填写登录地点</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">浏览器类型:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="浏览器类型" path="browser" htmlEscape="false" maxlength="50"
                                        class="form-control "/>
                            <div class="help-block">请填写浏览器类型</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">操作系统:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="操作系统" path="os" htmlEscape="false" maxlength="50"
                                        class="form-control "/>
                            <div class="help-block">请填写操作系统</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">在线状态:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:radiobuttons path="status" items="${fns:getDictList('on_line_status')}"
                                               itemLabel="label"
                                               itemValue="value" htmlEscape="false" class="i-checks "/>
                            <div class="help-block">请选择在线状态on_line在线off_line离线</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">session创建时间:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <input id="startTimestsamp" name="startTimestsamp" type="text" maxlength="20"
                                   class="laydate-icon form-control layer-date "
                                   value="<fmt:formatDate value="${sysUserOnline.startTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                            <div class="help-block">请选择session创建时间</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">session最后访问时间:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <input id="lastAccessTime" name="lastAccessTime" type="text" maxlength="20"
                                   class="laydate-icon form-control layer-date "
                                   value="<fmt:formatDate value="${sysUserOnline.lastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                            <div class="help-block">请选择session最后访问时间</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">超时时间，单位为分钟:</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input placeholder="超时时间，单位为分钟" path="expireTime" htmlEscape="false" maxlength="5"
                                        class="form-control  digits"/>
                            <div class="help-block">请填写超时时间，单位为分钟</div>
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
<script src="/staticViews/modules/sys/sysUserOnlineForm.js" type="text/javascript"></script>
<link href="/staticViews/modules/sys/sysUserOnlineForm.css" rel="stylesheet"/>
</body>
</html>