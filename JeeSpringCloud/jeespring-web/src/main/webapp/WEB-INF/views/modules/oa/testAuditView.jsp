<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>审批管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>审批详情</div>
            <div class="box-tools pull-right">
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <ul class="nav nav-tabs">
                <li><a href="${ctx}/oa/testAudit/">审批列表</a></li>
                <li class="active"><a href="${ctx}/oa/testAudit/form/?procInsId=${testAudit.procInsId}">审批详情</a></li>
            </ul>
            <br>
            <form:form class="form-horizontal">
                <fieldset>
                    <legend>审批详情</legend>
                    <table class="table table-bordered">
                        <tr>
                            <td class="tit">姓名</td>
                            <td>${testAudit.user.name}</td>
                            <td class="tit">部门</td>
                            <td>${testAudit.office.name}</td>
                            <td class="tit">岗位职级</td>
                            <td>${testAudit.post}</td>
                        </tr>
                        <tr>
                            <td class="tit">调整原因</td>
                            <td colspan="5">${testAudit.content}</td>
                        </tr>
                        <tr>
                            <td class="tit" rowspan="3">调整原因</td>
                            <td class="tit">薪酬档级</td>
                            <td>${testAudit.olda}</td>
                            <td class="tit" rowspan="3">拟调整标准</td>
                            <td class="tit">薪酬档级</td>
                            <td>${testAudit.newa}</td>
                        </tr>
                        <tr>
                            <td class="tit">月工资额</td>
                            <td>${testAudit.oldb}</td>
                            <td class="tit">月工资额</td>
                            <td>${testAudit.newb}</td>
                        </tr>
                        <tr>
                            <td class="tit">年薪金额</td>
                            <td>${testAudit.oldc}</td>
                            <td class="tit">年薪金额</td>
                            <td>${testAudit.newc}</td>
                        </tr>
                        <tr>
                            <td class="tit">月增资</td>
                            <td colspan="2">${testAudit.addNum}</td>
                            <td class="tit">执行时间</td>
                            <td colspan="2">${testAudit.exeDate}</td>
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
                </fieldset>
                <act:histoicFlow procInsId="${testAudit.act.procInsId}"/>
                <div class="form-actions">
                    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                </div>
            </form:form>

        </div>
    </div>
    <!-- 信息-->
    <div id="messageBox">${message}</div>
    <%@ include file="/WEB-INF/views/include/footJs.jsp" %>
    <script src="/staticViews/viewBase.js"></script></body>
</html>
