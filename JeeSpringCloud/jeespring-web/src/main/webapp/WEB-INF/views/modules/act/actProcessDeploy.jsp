<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>部署流程 - 流程管理</title>
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
            <div class="box-title"><i class="fa fa-edit"></i>部署流程</div>
            <div class="box-tools pull-right">
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <ul class="nav nav-tabs">
                <li><a href="${ctx}/act/process/">流程管理</a></li>
                <li class="active"><a href="${ctx}/act/process/deploy/">部署流程</a></li>
                <li><a href="${ctx}/act/process/running/">运行中的流程</a></li>
            </ul>
            <br>
            <form id="inputForm" action="${ctx}/act/process/deploy" method="post" enctype="multipart/form-data"
                  class="form-horizontal">
                <div class="content">
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">流程分类：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <select id="category" name="category" class="form-control required">
                                <c:forEach items="${fns:getDictList('act_category')}" var="dict">
                                    <option value="${dict.value}">${dict.label}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">流程文件：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <label for="file" class="btn btn-default">选择文件</label>
                            <input type="file" id="file" name="file" class="required"/>
                            <span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
                        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script></body>
</html>
