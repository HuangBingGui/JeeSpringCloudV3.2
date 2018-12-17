<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>通知管理</title>
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
                通知管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post"
                       class="form-horizontal  content-background">
                <div class="content">
                    <form:hidden path="id"/>
                    <div class="form-unit">基本信息</div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">类型：<font color="red">*</font><i class="fa icon-question"></i></label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:select path="type" class="form-control required">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label"
                                              itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">标题：<font color="red">*</font></label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="title" htmlEscape="false" maxlength="200" class="form-control required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">内容：<font color="red">*</font></label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000"
                                           class="form-control required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">附件：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <c:if test="${oaNotify.status ne '1'}">
                                <form:hidden id="files" path="files" htmlEscape="false" maxlength="255"
                                             class="form-control"/>
                                <sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
                            </c:if>
                            <c:if test="${oaNotify.status eq '1'}">
                                <form:hidden id="files" path="files" htmlEscape="false" maxlength="255"
                                             class="form-control"/>
                                <sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true"
                                              readonly="true"/>
                            </c:if>
                        </div>
                    </div>

                    <c:if test="${oaNotify.status ne '1'}">
                        <div class="form-group">
                            <label class="control-label col-sm-2 pull-left">状态：<font color="red">*</font></label>
                            <div class="col-sm-9 col-lg-10 col-xs-12">
                                <form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}"
                                                   itemLabel="label"
                                                   itemValue="value" htmlEscape="false" class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2 pull-left">接受人：<font color="red">*</font></label>
                            <div class="col-sm-9 col-lg-10 col-xs-12">
                                <sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds"
                                                value="${oaNotify.oaNotifyRecordIds}"
                                                labelName="oaNotifyRecordNames"
                                                labelValue="${oaNotify.oaNotifyRecordNames}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="form-control required"
                                                notAllowSelectParent="true" checked="true"/>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${oaNotify.status eq '1'}">
                        <div class="form-group">
                            <label class="control-label col-sm-2 pull-left">接受人：</label>
                            <div class="col-sm-9 col-lg-10 col-xs-12">
                                <table id="contentTable"
                                       class="table table-striped table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>接受人</th>
                                        <th>接受部门</th>
                                        <th>阅读状态</th>
                                        <th>阅读时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
                                        <tr>
                                            <td>
                                                    ${oaNotifyRecord.user.name}
                                            </td>
                                            <td>
                                                    ${oaNotifyRecord.user.office.name}
                                            </td>
                                            <td>
                                                    ${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${oaNotifyRecord.readDate}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp;
                                总共：${oaNotify.readNum + oaNotify.unReadNum}</td>
                            </div>
                        </div>
                    </c:if>
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
</body>
</html>