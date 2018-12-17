<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>新建模型 - 模型管理</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>

    <script type="text/javascript">
        $(document).ready(function () {
            top.$.jBox.tip.mess = null;
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                    setTimeout(function () {
                        location = '${ctx}/act/model/'
                    }, 1000);
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });

        function page(n, s) {
            location = '${ctx}/act/model/?pageNo=' + n + '&pageSize=' + s;
        }
    </script>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>新建模型</div>
            <div class="box-tools pull-right">
                <button id="btnRefresh" class="btn btn-default btn-sm" title="刷新"><i
                        class="glyphicon glyphicon-repeat"></i> 刷新
                </button>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <ul class="nav nav-tabs">
                <li><a href="${ctx}/act/model/">模型管理</a></li>
                <li class="active"><a href="${ctx}/act/model/create">新建模型</a></li>
            </ul>
            <br>
            <form id="inputForm" action="${ctx}/act/model/create" target="_blank" method="post" class="form-horizontal">
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
                        <label class="control-label col-sm-2 pull-left">模块名称：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <input id="name" name="name" type="text" class="form-control required"/>
                            <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">模块标识：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <input id="key" name="key" type="text" class="form-control required"/>
                            <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">模块描述：</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <textarea id="description" name="description" class="form-control required"></textarea>
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
