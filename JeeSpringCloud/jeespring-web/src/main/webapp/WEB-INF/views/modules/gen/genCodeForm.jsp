<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>生成代码</title>
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
                生成代码
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <form:form id="inputForm" modelAttribute="genScheme" action="${ctx}/gen/genTable/genCode" method="post"
                       class="form-horizontal content-background">
                <div class="content">
                    <form:hidden path="id" value="${genScheme.id}"/>
                    <sys:message content="${message}"/>
                    <div class="form-group">
                        <label>代码生成:单表、主附表、树表、列表和表单、增删改查云接口、redis高速缓存对接代码、图表统计、地图统计、vue.js</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>代码模式</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:select path="category" class="required form-control">
                                <form:options items="${config.categoryList}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                            <span class="help-inline">
						生成结构：{包名}/{模块名}/{分层(dao,entity,service,web)}/{子模块名}/{java类}
					</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>包路径</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="packageName" htmlEscape="false" maxlength="500"
                                        class="required form-control"/>
                            <span class="help-inline">建议项目模块包：com.company.project.modules;</span>
                            <span class="help-inline">建议框架模块包：com.jeespring.modules;</span><br>
                            <span class="help-inline">如果自定义框架模块包：com.*.*;</span><br>
                            <span class="help-inline">需要在application.xml内配置type-aliases-package:com.jeespring,com.company,com.*.*;</span><br>
                            <span class="help-inline">需要在JeeSpringDriver.java里面配置@ServletComponentScan;@ComponentScan;@MapperScan</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>模块名</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="moduleName" htmlEscape="false" maxlength="500"
                                        class="required form-control"/>
                            <span class="help-inline">可理解为子系统名，例如 sys、ticket、order...</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left">子模块名</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="subModuleName" htmlEscape="false" maxlength="500" class="form-control"/>
                            <span class="help-inline">可选，分层下的文件夹，例如 </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>功能描述</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="functionName" htmlEscape="false" maxlength="500"
                                        class="required form-control"/>
                            <span class="help-inline">将设置到类描述</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>功能名</label>
                        <div class="col-sm-9 col-lg-10 col-xs-12">
                            <form:input path="functionNameSimple" htmlEscape="false" maxlength="500"
                                        class="required form-control"/>
                            <span class="help-inline">用作功能提示，如：保存“某某”成功</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2 pull-left"><font color="red">*</font>作者</label>
                        <div class="contcol-sm-9 col-lg-10 col-xs-12rols">
                            <form:input path="functionAuthor" htmlEscape="false" maxlength="500"
                                        class="required form-control"/>
                            <span class="help-inline">功能开发者</span>
                        </div>
                    </div>

                    <input type="hidden" name="genTable.id" value="${genScheme.genTable.id}"/>
                    <!--
			<div class="control-group">
				<label class="control-label"><font color="red">*</font>业务表名:</label>
				<div class="controls">
					<form:select path="genTable.id" class="required form-control">
						<form:options items="${tableList}" itemLabel="nameAndComments" itemValue="id" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">生成的数据表，一对多情况下请选择主表。</span>
				</div>
			</div>
			 -->
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
