<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>树管理</title>
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
                数据字典管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
			<form:form id="inputForm" modelAttribute="testTree" action="${ctx}/test/tree/testTree/save" method="post" class="form-horizontal  content-background">
				<div class="content">
					<form:hidden path="id"/>
					<div class="form-unit">基本信息</div>
					<div class="row">
						<div class="col-xs-6 form-group">
							<label class="control-label col-sm-4 pull-left"><font color="red">*</font>名称：</label>
							<div class="col-sm-8">
								<form:input path="name" htmlEscape="false" maxlength="100" class="form-control  required"/>

							</div>
						</div>
						<div class="col-xs-6 form-group">
							<label class="control-label col-sm-4 pull-left"><font color="red">*</font>排序：</label>
							<div class="col-sm-8">
								<form:input path="sort" htmlEscape="false" class="form-control  required digits"/>

							</div>
						</div>
						<div class="col-xs-6 form-group">
							<label class="control-label col-sm-4 pull-left">上级父级编号:</label>
							<div class="col-sm-8">
								<sys:treeselect id="parent" name="parent.id" value="${testTree.parent.id}" labelName="parent.name" labelValue="${testTree.parent.name}"
									title="父级编号" url="/test/tree/testTree/treeData" extId="${testTree.id}" cssClass="form-control " allowClear="true"/>
							 </div>
						</div>
						<div class="col-xs-6 form-group">
							<label class="control-label col-sm-4 pull-left">备注信息：</label>
							<div class="col-sm-8">
								<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>

							</div>
						</div>
					</div>
					<div id="iframeSave" class="form-group">
						<c:if test="${action ne 'view'}">
							<a id="btnSubmit" class="btn btn-primary">保存</a>
						</c:if>
						<a id="btnBack" class="btn btn-default">返回</a>
					</div>
				</div>
			</form:form>
        </div>
    </div>
</div>
<div id="messageBox">${message}</div>
<%@ include file="/WEB-INF/views/include/footJs.jsp" %>
<script src="/staticViews/viewBase.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</body>
</html>