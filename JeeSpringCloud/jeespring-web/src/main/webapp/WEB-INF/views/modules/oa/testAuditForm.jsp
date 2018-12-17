<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>审批管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i><shiro:hasPermission
                    name="oa:testAudit:edit">审批${not empty testAudit.id?'修改':'申请'}流程</shiro:hasPermission><shiro:lacksPermission
                    name="oa:testAudit:edit">查看</shiro:lacksPermission></div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" class="btn btn-sm btn-default" title="查询"><i class="fa fa-filter"></i>查询</a>
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <ul class="nav nav-tabs">
                <li><a href="${ctx}/oa/testAudit/">审批列表</a></li>
                <li class="active"><a href="${ctx}/oa/testAudit/form?id=${testAudit.id}"><shiro:hasPermission
                        name="oa:testAudit:edit">审批${not empty testAudit.id?'修改':'申请'}流程</shiro:hasPermission><shiro:lacksPermission
                        name="oa:testAudit:edit">查看</shiro:lacksPermission></a></li>
            </ul>
            <br>
            <form:form id="inputForm" modelAttribute="testAudit" action="${ctx}/oa/testAudit/save" method="post"
                       class="form-horizontal">
                <form:hidden path="id"/>
                <form:hidden path="act.taskId"/>
                <form:hidden path="act.taskName"/>
                <form:hidden path="act.taskDefKey"/>
                <form:hidden path="act.procInsId"/>
                <form:hidden path="act.procDefId"/>
                <form:hidden id="flag" path="act.flag"/>
                    <table class="table table-bordered">
                        <tr><td colspan="6" class="title">审批申请</td></tr>
                        <tr>
                            <td class="tit">姓名</td>
                            <td>
                                <sys:treeselect id="user" name="user.id" value="${testAudit.user.id}"
                                                labelName="user.name"
                                                labelValue="${testAudit.user.name}"
                                                title="用户" url="/sys/office/treeData?type=3"
                                                cssClass="required recipient form-control"
                                                allowClear="true" notAllowSelectParent="true" smallBtn="false"/>
                            </td>
                            <td class="tit">部门</td>
                            <td>
                                <sys:treeselect id="office" name="office.id" value="${testAudit.office.id}"
                                                labelName="office.name" labelValue="${testAudit.office.name}"
                                                title="用户" url="/sys/office/treeData?type=2"
                                                cssClass="required recipient form-control"
                                                allowClear="true" notAllowSelectParent="true" smallBtn="false"/>
                            </td>
                            <td class="tit">岗位职级</td>
                            <td>
                                <form:input path="post" class="form-control" htmlEscape="false" maxlength="50"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tit">调整原因</td>
                            <td colspan="5">
                                <form:textarea path="content" class="form-control required" rows="5" maxlength="200"
                                               cssStyle="width:500px"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tit" rowspan="3">调整原因</td>
                            <td class="tit">薪酬档级</td>
                            <td><form:input path="olda" htmlEscape="false" maxlength="50" class="form-control"/></td>
                            <td class="tit" rowspan="3">拟调整标准</td>
                            <td class="tit">薪酬档级</td>
                            <td><form:input path="newa" htmlEscape="false" maxlength="50" class="form-control"/></td>
                        </tr>
                        <tr>
                            <td class="tit">月工资额</td>
                            <td><form:input path="oldb" htmlEscape="false" maxlength="50" class="form-control"/></td>
                            <td class="tit">月工资额</td>
                            <td><form:input path="newb" htmlEscape="false" maxlength="50" class="form-control"/></td>
                        </tr>
                        <tr>
                            <td class="tit">年薪金额</td>
                            <td><form:input path="oldc" htmlEscape="false" maxlength="50" class="form-control"/></td>
                            <td class="tit">年薪金额</td>
                            <td><form:input path="newc" htmlEscape="false" maxlength="50" class="form-control"/></td>
                        </tr>
                        <tr>
                            <td class="tit">月增资</td>
                            <td colspan="2"><form:input path="addNum" htmlEscape="false" maxlength="50" class="form-control"/></td>
                            <td class="tit">执行时间</td>
                            <td colspan="2"><form:input path="exeDate" htmlEscape="false" maxlength="50" class="form-control"/></td>
                        </tr>
                        <tr>
                            <td class="tit">人力资源部意见</td>
                            <td colspan="5">
                                    ${testAudit.hrText}
                            </td>
                        </tr>
                        <tr>
                            <td class="tit">分管领导意见</td>
                            <td colspan="5">
                                    ${testAudit.leadText}
                            </td>
                        </tr>
                        <tr>
                            <td class="tit">集团主要领导意见</td>
                            <td colspan="5">
                                    ${testAudit.mainLeadText}
                            </td>
                        </tr>
                    </table>
                <div class="form-actions">
                    <shiro:hasPermission name="oa:testAudit:edit">
                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"
                               onclick="$('#flag').val('yes')"/>&nbsp;
                        <c:if test="${not empty testAudit.id}">
                            <input id="btnSubmit2" class="btn btn-inverse" type="submit" value="销毁申请"
                                   onclick="$('#flag').val('no')"/>&nbsp;
                        </c:if>
                    </shiro:hasPermission>
                    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                </div>
                <c:if test="${not empty testAudit.id}">
                    <act:histoicFlow procInsId="${testAudit.act.procInsId}"/>
                </c:if>
            </form:form>
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script></body>
</html>
