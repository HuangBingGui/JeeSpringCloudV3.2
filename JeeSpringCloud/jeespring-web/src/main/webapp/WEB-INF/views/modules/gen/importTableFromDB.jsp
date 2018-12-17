<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>业务表管理</title>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
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
                业务表管理
            </div>
            <div class="box-tools pull-right">
                <a href="#" class="btn btn-sm btn-default" onclick="$('#remark').toggle();">
                    <i class="fa fa-filter"></i>
                </a>
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 业务表导入 -->
            <form:form id="inputForm" modelAttribute="genTable" action="${ctx}/gen/genTable/importTableFromDB"
                       method="post" class="form-horizontal">
                <form:hidden path="id"/>
                <sys:message content="${message}"/>
                <br/>
                <div class="form-group">
                    <label class="control-label  pull-left">表名</label>
                    <div class="controls">
                        <form:select path="name" class="form-control ">
                            <form:options items="${tableList}" itemLabel="nameAndComments" itemValue="name"
                                          htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div id="iframeSave" class="form-group ${"$"}{action}">
                    <a class="btn btn-success" onclick="doSubmit();">保存</a>
                    <a class="btn btn-primary" onclick="javascript:history.back(-1);">返回</a>
                    <!--a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a-->
                </div>
                <div id="remark" style="display:none">
                    <div class="help-block">表基本字段包括：</div>
                    <div class="help-block">`id` varchar(50) NOT NULL DEFAULT '' COMMENT '系统id',</div>
                    <div class="help-block">`xxx_number` varchar(100) DEFAULT '' COMMENT 'xxx编号',</div>
                    <div class="help-block">`name` varchar(100) DEFAULT '' COMMENT '名称',</div>
                    <div class="help-block">`label` varchar(50) DEFAULT '' COMMENT '标签名',</div>
                    <div class="help-block">`picture` varchar(100) DEFAULT '' COMMENT '图片',</div>
                    <div class="help-block">`type` varchar(50) DEFAULT '' COMMENT '类型',</div>
                    <div class="help-block">`sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',</div>
                    <div class="help-block">`description` varchar(100) DEFAULT '' COMMENT '描述',</div>
                    <div class="help-block">`remarks` varchar(100) DEFAULT '' COMMENT '备注信息',</div>
                    <div class="help-block">`html` text DEFAULT '' COMMENT '备注信息',</div>
                    <div class="help-block">`status` varchar(10) DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
                    </div>
                    <div class="help-block">`create_by` varchar(64) DEFAULT '' COMMENT '创建者',</div>
                    <div class="help-block">`create_date` datetime DEFAULT NULL COMMENT '创建时间',</div>
                    <div class="help-block">`update_by` varchar(64) DEFAULT '' COMMENT '更新者',</div>
                    <div class="help-block">`update_date` datetime DEFAULT NULL COMMENT '更新时间',</div>
                    <div class="help-block">`del_flag` char(1) DEFAULT '0' COMMENT '删除标记',</div>
                    <div class="help-block">树表额外多：</div>
                    <div class="help-block">`name` varchar(100) DEFAULT '' COMMENT '名称',</div>
                    <div class="help-block">`parent_id` varchar(64) DEFAULT '' COMMENT '父级编号',</div>
                    <div class="help-block">`parent_ids` varchar(2000) DEFAULT '' COMMENT '所有父级编号',</div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
        if ($("#inputForm").valid()) {
            $("#inputForm").submit();
            return true;
        }

        return false;
    }
</script>
</body>
</html>
