<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>表单管理</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <script type="text/javascript" src="${ctxStatic}/jquery-combox/jquery.combox.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/jquery-combox/styles/style.css" type="text/css"/>
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
                表单管理
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">

            <table style="display:none">
                <tr id="template1" style="display:none">
                    <td>
                        <input type="hidden" name="columnList[0].sort" value="0" maxlength="200"
                               class="form-control required   digits"/>
                        <label>0</label>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control  " name="ck" value="1"/>
                    </td>
                    <td>
                        <input type="text" class="form-control required" name="columnList[0].name" value=""/>
                    </td>
                    <td>
                        <input type="text" class="form-control required" name="columnList[0].comments" value=""
                               maxlength="200" class="required"/>
                    </td>
                    <td>
                        <span name="template_columnList[0].jdbcType" class="required" value="varchar(64)"/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control" name="columnList[0].isPk" value="1"/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control " name="columnList[0].isNull" value="1" checked/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control  " name="columnList[0].isInsert" value="1" checked/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control  " name="columnList[0].isEdit" value="1" checked/>
                    </td>
                </tr>
                <tr id="template2" style="display:none">
                    <td>
                        <input type="text" class="form-control" readOnly="readonly" name="page-columnList[0].name"
                               value=""/>
                    </td>
                    <td>
                        <select name="columnList[0].javaType" class="form-control required m-b">
                            <c:forEach items="${config.javaTypeList}" var="dict">
                                <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                        title="${dict.description}">${dict.label}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="columnList[0].javaField" value="" maxlength="200"
                               class="form-control required "/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control  " name="columnList[0].isList" value="1" checked/>
                    </td>
                    <td>
                        <input type="checkbox" class="form-control  " name="columnList[0].isQuery" value="1"/>
                    </td>
                    <td>
                        <select name="columnList[0].queryType" class="form-control required  m-b">
                            <c:forEach items="${config.queryTypeList}" var="dict">
                                <option value="${fns:escapeHtml(dict.value)}" ${fns:escapeHtml(dict.value)==column.queryType?'selected':''}
                                        title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="columnList[0].showType" class="form-control required  m-b">
                            <c:forEach items="${config.showTypeList}" var="dict">
                                <option value="${dict.value}" ${dict.value==column.showType?'selected':''}
                                        title="${dict.description}">${dict.label}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="columnList[0].dictType" value="${column.dictType}" maxlength="200"
                               class="form-control   "/>
                    </td>
                </tr>

            </table>

            <!-- 业务表添加 -->
            <form:form id="inputForm" modelAttribute="genTable" action="${ctx}/gen/genTable/save" method="post"
                       class="form-horizontal">
                <form:hidden path="id"/>
                <form:hidden path="isSync"/>
                <sys:message content="${message}"/>
                <table class="width-100  table-condensed dataTables-example dataTable no-footer">
                    <tbody>
                    <tr>
                        <td class="width-15 active"><label class="pull-left"><font color="red">*</font>表名:</label></td>
                        <td class="width-35">
                            <form:input path="name" htmlEscape="false" maxlength="200"
                                        class="form-control required"/>
                        </td>
                        <td class="width-15 active"><label class="pull-left"><font color="red">*</font>说明:</label></td>
                        <td class="width-35">
                            <form:input path="comments" htmlEscape="false" maxlength="200"
                                        class="form-control required"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-left">表类型</label></td>
                        <td class="width-35">
                            <form:select path="tableType" class="form-control m-b">
                                <form:options items="${fns:getDictList('table_type')}" itemLabel="label"
                                              itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span class="help-inline">如果是附表，请指定主表表名和当前表的外键</span>
                        </td>
                        <td class="width-15 active"><label class="pull-left"><font color="red">*</font>类名:</label></td>
                        <td class="width-35">
                            <form:input path="className" htmlEscape="false" maxlength="200"
                                        class="form-control required"/>
                        </td>

                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-left">主表表名:</label></td>
                        <td class="width-35">
                            <form:select path="parentTable" cssClass="form-control">
                                <form:option value="">无</form:option>
                                <form:options items="${tableList}" itemLabel="nameAndComments" itemValue="name"
                                              htmlEscape="false"/>
                            </form:select>
                        </td>
                        <td class="width-15 active"><label class="pull-left">当前表外键：</label></td>
                        <td class="width-35">
                            <form:input path="parentTableFk" htmlEscape="false" maxlength="200" class="form-control"
                                        value="${genTable.parentTableFk}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-left"><font
                                color="red">*</font>当前表主键:</label></td>
                        <td class="width-35">
                            <form:input path="pk" htmlEscape="false" maxlength="200" class="form-control required"/>
                        </td>
                        <td class="width-15 active"></td>
                        <td class="width-35">
                        </td>
                    </tr>
                    </tbody>
                </table>
                <button class="btn btn-white btn-sm" onclick="return addColumn()"><i class="fa fa-plus"> 增加</i></button>
                <button class="btn btn-white btn-sm" onclick="return delColumn()"><i class="fa fa-minus"> 删除</i>
                </button>
                <br/>

                <div class="tabs-container">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 数据库属性</a>
                        </li>
                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">页面属性</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <div class="panel-body">
                                <table id="contentTable1" class="table table-hover  dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th width="40px">序号</th>
                                        <th>操作</th>
                                        <th title="数据库字段名">列名</th>
                                        <th title="默认读取数据库字段备注">说明</th>
                                        <th title="数据库中设置的字段类型及长度">物理类型</th>
                                        <th title="是否是数据库主键">主键</th>
                                        <th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th>
                                        <th title="选中后该字段被加入到insert语句里">插入</th>
                                        <th title="选中后该字段被加入到update语句里">编辑</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${empty genTable.name}">
                                        <!-- id -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[0].sort" value="0" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>0</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[0].name"
                                                       value="id"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[0].comments"
                                                       value="主键" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[0].jdbcType" class="required "
                                                      value="varchar(64)"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isPk"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isEdit"
                                                       value="1"/>
                                            </td>

                                        </tr>
                                        <!-- create_by -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[1].sort" value="1" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>1</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[1].name"
                                                       value="create_by"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[1].comments"
                                                       value="创建者" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[1].jdbcType" class="required "
                                                      value="varchar(64)"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isEdit"
                                                       value="1"/>
                                            </td>

                                        </tr>

                                        <!-- create_date -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[2].sort" value="2" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>2</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[2].name"
                                                       value="create_date"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[2].comments"
                                                       value="创建时间" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[2].jdbcType" class="required " value="datetime"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isEdit"
                                                       value="1"/>
                                            </td>

                                        </tr>

                                        <!-- update_by -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[3].sort" value="3" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label></label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[3].name"
                                                       value="update_by"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[3].comments"
                                                       value="更新者" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[3].jdbcType" class="required "
                                                      value="varchar(64)"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isEdit"
                                                       value="1" checked/>
                                            </td>
                                        </tr>

                                        <!-- update_date -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[4].sort" value="4" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>4</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[4].name"
                                                       value="update_date"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[4].comments"
                                                       value="更新时间" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[4].jdbcType" class="required " value="datetime"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isEdit"
                                                       value="1" checked/>
                                            </td>
                                        </tr>

                                        <!-- remarks -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[5].sort" value="5" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>5</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[5].name"
                                                       value="remarks"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[5].comments"
                                                       value="备注信息" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[5].jdbcType" class="required "
                                                      value="nvarchar(255)"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isNull"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isEdit"
                                                       value="1" checked/>
                                            </td>

                                        </tr>

                                        <!-- del_flag -->
                                        <tr>
                                            <td>
                                                <input type="hidden" name="columnList[6].sort" value="0" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>6</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[6].name"
                                                       value="del_flag"/>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" name="columnList[6].comments"
                                                       value="逻辑删除标记（0：显示；1：隐藏）" maxlength="200" class="required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[6].jdbcType" class="required "
                                                      value="varchar(64)"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isPk"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isNull"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isInsert"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isEdit"
                                                       value="1"/>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:forEach items="${genTable.columnList}" var="column" varStatus="vs">
                                        <tr${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}>
                                            <td>
                                                <input type="hidden" name="columnList[${vs.index}].sort"
                                                       value="${column.sort}" maxlength="200"
                                                       class="form-control required   digits"/>
                                                <label>${column.sort}</label>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                            </td>
                                            <td>
                                                <input type="hidden" name="columnList[${vs.index}].id"
                                                       value="${column.id}"/>
                                                <input type="hidden" name="columnList[${vs.index}].delFlag"
                                                       value="${column.delFlag}"/>
                                                <input type="hidden" name="columnList[${vs.index}].genTable.id"
                                                       value="${column.genTable.id}"/>
                                                <input type="text" name="columnList[${vs.index}].name"
                                                       value="${column.name}" class="form-control required"/>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[${vs.index}].comments"
                                                       value="${column.comments}" maxlength="200"
                                                       class="form-control required"/>
                                            </td>
                                            <td>
                                                <span name="columnList[${vs.index}].jdbcType" class="required"
                                                      value="${column.jdbcType}"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isPk"
                                                       value="1" ${column.isPk eq '1' ? 'checked' : ''}/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isNull"
                                                       value="1" ${column.isNull eq '1' ? 'checked' : ''}/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isInsert"
                                                       value="1" ${column.isInsert eq '1' ? 'checked' : ''}/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isEdit"
                                                       value="1" ${column.isEdit eq '1' ? 'checked' : ''}/>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="tab-2" class="tab-pane">
                            <div class="panel-body">
                                <table id="contentTable2"
                                       class="table table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th title="数据库字段名" width="15%">列名</th>
                                        <th title="实体对象的属性字段类型" width="15%">Java类型</th>
                                        <th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）">
                                            Java属性名称 <i class="icon-question-sign"></i></th>
                                        <th title="选中后该字段被加入到查询列表里">列表</th>
                                        <th title="选中后该字段被加入到查询条件里">查询</th>
                                        <th title="该字段为查询字段时的查询匹配放松" width="15%">查询匹配方式</th>
                                        <th title="字段在表单中显示的类型" width="15%">显示表单类型</th>
                                        <th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:if test="${empty genTable.name}">
                                        <!-- id -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[0].name" value="id"/>
                                            </td>
                                            <td>
                                                <select name="columnList[0].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[0].javaField" value="id"
                                                       maxlength="200" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[0].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[0].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[0].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}"
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[0].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>

                                        </tr>
                                        <!-- create_by -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[1].name" value="create_by"/>
                                            </td>
                                            <td>
                                                <select name="columnList[1].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[1].javaField" value="createBy.id"
                                                       maxlength="200" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[1].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[1].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[1].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='input'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[1].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>

                                        <!-- create_date -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[2].name" value="create_date"/>
                                            </td>
                                            <td>
                                                <select name="columnList[2].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='java.util.Date'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[2].javaField" value="createDate"
                                                       maxlength="200" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[2].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[2].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[2].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='dateselect'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[2].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>

                                        <!-- update_by -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[3].name" value="update_by"/>
                                            </td>
                                            <td>
                                                <select name="columnList[3].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[3].javaField" value="updateBy.id"
                                                       maxlength="200" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[3].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[3].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[3].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='input'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[3].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>

                                        <!-- update_date -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[4].name" value="update_date"/>
                                            </td>
                                            <td>
                                                <select name="columnList[4].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='java.util.Date'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[4].javaField" value="updateDate"
                                                       maxlength="200" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[4].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[4].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[4].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='dateselect'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[4].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>

                                        <!-- remarks -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[5].name" value="remarks"/>
                                            </td>
                                            <td>
                                                <select name="columnList[5].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[5].javaField" value="remarks"
                                                       maxlength="255" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isList"
                                                       value="1" checked/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[5].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[5].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[5].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='textarea'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[5].dictType"
                                                       value="${column.dictType}" maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>

                                        <!-- del_flag -->
                                        <tr>
                                            <td>
                                                <input type="text" class="form-control" readonly="readonly"
                                                       name="page-columnList[6].name" value="del_flag"/>
                                            </td>
                                            <td>
                                                <select name="columnList[6].javaType" class="form-control required m-b">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='String'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[6].javaField" value="delFlag"
                                                       maxlength="255" class="form-control required "/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isList"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks" name="columnList[6].isQuery"
                                                       value="1"/>
                                            </td>
                                            <td>
                                                <select name="columnList[6].queryType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}"
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[6].showType"
                                                        class="form-control required  m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value=='radiobox'?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[6].dictType" value="del_flag"
                                                       maxlength="200" class="form-control"/>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:forEach items="${genTable.columnList}" var="column" varStatus="vs">
                                        <tr${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}>
                                            <td>
                                                <input type="text" readonly="readonly"
                                                       name="page-columnList[${vs.index}].name" value="${column.name}"
                                                       class="form-control required"/>
                                            </td>
                                            <td>
                                                <select name="columnList[${vs.index}].javaType"
                                                        class="form-control required">
                                                    <c:forEach items="${config.javaTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value==column.javaType?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[${vs.index}].javaField"
                                                       value="${column.javaField}" maxlength="200"
                                                       class="form-control required"/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isList"
                                                       value="1" ${column.isList eq '1' ? 'checked' : ''}/>
                                            </td>
                                            <td>
                                                <input type="checkbox" class="i-checks"
                                                       name="columnList[${vs.index}].isQuery"
                                                       value="1" ${column.isQuery eq '1' ? 'checked' : ''}/>
                                            </td>
                                            <td>
                                                <select name="columnList[${vs.index}].queryType"
                                                        class="required form-control m-b">
                                                    <c:forEach items="${config.queryTypeList}" var="dict">
                                                        <option value="${fns:escapeHtml(dict.value)}" ${fns:escapeHtml(dict.value)==column.queryType?'selected':''}
                                                                title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="columnList[${vs.index}].showType"
                                                        class="required form-control m-b">
                                                    <c:forEach items="${config.showTypeList}" var="dict">
                                                        <option value="${dict.value}" ${dict.value==column.showType?'selected':''}
                                                                title="${dict.description}">${dict.label}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" name="columnList[${vs.index}].dictType"
                                                       value="${column.dictType}" maxlength="200"
                                                       class="form-control "/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div id="iframeSave">
                    <a class="btn btn-success" onclick="doSubmit();">保存</a>
                    <a class="btn btn-primary" onclick="javascript:history.back(-1);">返回</a>
                    <!--a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a-->
                </div>
            </form:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var validateForm;

    function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
        if (validateForm.form()) {
            $("#inputForm").submit();
            return true;
        }

        return false;
    }

    $(document).ready(function () {
        //	$("#comments").focus();
        validateForm = $("#inputForm").validate({
            submitHandler: function (form) {
                loading('正在提交，请稍等...');
                $("input[type=checkbox]").each(function () {
                    $(this).after("<input type=\"hidden\" name=\"" + $(this).attr("name") + "\" value=\""
                        + ($(this).attr("checked") ? "1" : "0") + "\"/>");
                    $(this).attr("name", "_" + $(this).attr("name"));
                });
                form.submit();
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

        resetColumnNo();
        $("#tableType").change(function () {
            if ($("#tableType").val() == "3") {
                addForTreeTable();
            } else {
                removeForTreeTable();
            }

        });

        var fromIndex, toIndex;
        $("#contentTable1").tableDnD({//支持拖拽

            onDragClass: "myDragClass",
            onDrop: function (table, row) {
                toIndex = $(row).index();//移动后的位置
                var targetTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + toIndex + ")");//同步页面属性,目标元素位置（移动到该元素后面）
                var objTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + fromIndex + ")");//同步页面属性, 需要移动的元素
                if (fromIndex < toIndex) {
                    objTR2.insertAfter(targetTR2);
                } else {
                    objTR2.insertBefore(targetTR2);
                }
                resetColumnNo();

            },
            onDragStart: function (table, row) {
                fromIndex = $(row).index();//移动前的位置
            }
        });
    });

    function resetColumnNo() {
        $("#tab-2 #contentTable2 tbody tr").each(function (index, element) {
            $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                var name = $(this).attr("name");
                var attr_name = name.split(".")[1];
                var newName = "columnList[" + index + "]." + attr_name;
                $(this).attr("name", newName);
                if (name.indexOf(".sort") >= 0) {

                    $(this).val(index);
                    $(this).next().text(index);
                }
            });

            $(this).find("label[id*=columnList]").each(function () {
                var id = $(this).attr("id");
                var attr_id = id.split(".")[1];
                var newId = "columnList[" + index + "]." + attr_id;
                $(this).attr("id", newId);
                $(this).attr("for", "columnList[" + index + "].jdbcType");
            });

            $(this).find("input[name*=name]").each(function () {
                var name = $(this).attr("name");
                var attr_name = name.split(".")[1];
                var newName = "page-columnList[" + index + "]." + attr_name;
                $(this).attr("name", newName);
            });

        });
        $("#tab-1 #contentTable1 tbody tr").each(function (index, element) {
            $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                var name = $(this).attr("name");
                var attr_name = name.split(".")[1];
                var newName = "columnList[" + index + "]." + attr_name;
                $(this).attr("name", newName);//重新对name排序
                if (name.indexOf(".sort") >= 0) {

                    $(this).val(index);
                    $(this).next().text(index);
                }
            });

            $(this).find("label[id*=columnList]").each(function () {
                var id = $(this).attr("id");
                var attr_id = id.split(".")[1];
                var newId = "columnList[" + index + "]." + attr_id;
                $(this).attr("id", newId);
                $(this).attr("for", "columnList[" + index + "].jdbcType");
            });

            $(this).find("input[name*=name]").change(function () {//修改数据库列名，同时同步页面列名
                var name = $(this).attr("name");
                var newName = "page-" + name;
                $("#tab-2 #contentTable2 tbody tr input[name='" + newName + "']").val($(this).val());

            });

        });
        $('#contentTable1 tbody tr span[name*=jdbcType]').combox({datas: ['varchar(64)', 'nvarchar(64)', 'integer', 'double', 'datetime', 'longblob', 'longtext']});
    }

    function addColumn() {
        var column1 = $("#template1").clone();
        column1.removeAttr("style");
        column1.removeAttr("id");
        var column2 = $("#template2").clone();
        column2.removeAttr("style");
        column2.removeAttr("id");
        $("#tab-1 #contentTable1 tbody").append(column1);
        $("#tab-2 #contentTable2 tbody").append(column2);
        column1.find('input:checkbox').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });
        column2.find('input:checkbox').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });
        resetColumnNo();
        $("#contentTable1").tableDnD({//支持拖拽

            onDragClass: "myDragClass",
            onDrop: function (table, row) {
                toIndex = $(row).index();//移动后的位置
                var targetTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + toIndex + ")");//同步页面属性,目标元素位置（移动到该元素后面）
                var objTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + fromIndex + ")");//同步页面属性, 需要移动的元素
                if (fromIndex < toIndex) {
                    objTR2.insertAfter(targetTR2);
                } else {
                    objTR2.insertBefore(targetTR2);
                }
                resetColumnNo();

            },
            onDragStart: function (table, row) {
                fromIndex = $(row).index();//移动前的位置
            }
        });
        return false;
    }

    function removeForTreeTable() {
        $("#tab-1 #contentTable1 tbody").find("#tree_11,#tree_12,#tree_13,#tree_14").remove();
        $("#tab-2 #contentTable2 tbody").find("#tree_21,#tree_22,#tree_23,#tree_24").remove();
        resetColumnNo();
        return false;
    }

    function addForTreeTable() {
        if (!$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=parent_id]").val()) {
            var column11 = $("#template1").clone();
            column11.removeAttr("style");
            column11.attr("id", "tree_11");
            column11.find("input[name*=name]").val("parent_id");
            column11.find("input[name*=comments]").val("父级编号");
            column11.find("span[name*=jdbcType]").val("varchar(64)");
            column11.find("input[name*=isNull]").removeAttr("checked");
            var column21 = $("#template2").clone();
            column21.removeAttr("style");
            column21.attr("id", "tree_21");
            column21.find("input[name*=name]").val("parent_id");
            column21.find("select[name*=javaType]").val("This");
            column21.find("input[name*=javaField]").val("parent.id|name");
            column21.find("input[name*=isList]").removeAttr("checked");
            column21.find("select[name*=showType]").val("treeselect");
            $("#tab-1 #contentTable1 tbody").append(column11);
            $("#tab-2 #contentTable2 tbody").append(column21);
            column11.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
            column21.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
        }

        if (!$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=parent_ids]").val()) {
            var column12 = $("#template1").clone();
            column12.removeAttr("style");
            column12.attr("id", "tree_12");
            column12.find("input[name*=name]").val("parent_ids");
            column12.find("input[name*=comments]").val("所有父级编号");
            column12.find("span[name*=jdbcType]").val("varchar(2000)");
            column12.find("input[name*=isNull]").removeAttr("checked");
            var column22 = $("#template2").clone();
            column22.removeAttr("style");
            column22.attr("id", "tree_22");
            column22.find("input[name*=name]").val("parent_ids");
            column22.find("select[name*=javaType]").val("String");
            column22.find("input[name*=javaField]").val("parentIds");
            column22.find("select[name*=queryType]").val("like");
            column22.find("input[name*=isList]").removeAttr("checked");
            $("#tab-1 #contentTable1 tbody").append(column12);
            $("#tab-2 #contentTable2 tbody").append(column22);
            column12.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
            column22.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
        }

        if (!$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=name]").val()) {
            var column13 = $("#template1").clone();
            column13.removeAttr("style");
            column13.attr("id", "tree_13");
            column13.find("input[name*=name]").val("name");
            column13.find("input[name*=comments]").val("名称");
            column13.find("span[name*=jdbcType]").val("varchar(100)");
            column13.find("input[name*=isNull]").removeAttr("checked");
            var column23 = $("#template2").clone();
            column23.removeAttr("style");
            column23.attr("id", "tree_23");
            column23.find("input[name*=name]").val("name");
            column23.find("select[name*=javaType]").val("String");
            column23.find("input[name*=javaField]").val("name");
            column23.find("input[name*=isQuery]").attr("checked", "checked");
            column23.find("select[name*=queryType]").val("like");
            $("#tab-1 #contentTable1 tbody").append(column13);
            $("#tab-2 #contentTable2 tbody").append(column23);
            column13.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
            column23.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
        }

        if (!$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=sort]").val()) {
            var column14 = $("#template1").clone();
            column14.removeAttr("style");
            column14.attr("id", "tree_14");
            column14.find("input[name*=name]").val("sort");
            column14.find("input[name*=comments]").val("排序");
            column14.find("span[name*=jdbcType]").val("decimal(10,0)");
            column14.find("input[name*=isNull]").removeAttr("checked");
            var column24 = $("#template2").clone();
            column24.removeAttr("style");
            column24.attr("id", "tree_24");
            column24.find("input[name*=name]").val("sort");
            column24.find("select[name*=javaType]").val("Integer");
            column24.find("input[name*=javaField]").val("sort");
            column24.find("input[name*=isList]").removeAttr("checked");
            $("#tab-1 #contentTable1 tbody").append(column14);
            $("#tab-2 #contentTable2 tbody").append(column24);
            column14.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
            column24.find('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%'
            });
        }
        resetColumnNo();
        return false;
    }

    function delColumn() {
        $("input[name='ck']:checked").closest("tr").each(function () {

            var name = $(this).find("input[name*=name]").attr("name");
            $(this).remove();
            $("#tab-2 #contentTable2 tbody tr input[name='page-" + name + "']").closest("tr").remove();
        })
        resetColumnNo();
        return false;
    }

    function up(obj) {
        var objParentTR = $(obj).parent().parent();
        var index = objParentTR.index();

        var prevTR = objParentTR.prev();
        if (prevTR.length > 0) {
            prevTR.insertAfter(objParentTR);
        }


        var objParentTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + index + ")");
        var prevTR2 = objParentTR2.prev();
        if (prevTR2.length > 0) {
            prevTR2.insertAfter(objParentTR2);
        }

        resetColumnNo();
    }

    function down(obj) {
        var objParentTR = $(obj).parent().parent();
        var index = objParentTR.index();
        var nextTR = objParentTR.next();
        if (nextTR.length > 0) {
            nextTR.insertBefore(objParentTR);
        }

        var objParentTR2 = $("#tab-2 #contentTable2 tbody tr:eq(" + index + ")");
        var nextTR2 = objParentTR2.next();
        if (nextTR2.length > 0) {
            nextTR2.insertBefore(objParentTR2);
        }
        resetColumnNo();
    }
</script>
</body>
</html>
