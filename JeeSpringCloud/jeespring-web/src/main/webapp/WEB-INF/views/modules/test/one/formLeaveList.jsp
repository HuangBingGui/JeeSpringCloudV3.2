<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假管理</title>
	<%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@ include file="/WEB-INF/views/include/echarts.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>请假管理</div>
            <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" title="筛选" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i>筛选</a>
                <a id="btnRefresh" title="刷新" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-repeat"></i>刷新</a>
                <shiro:hasPermission name="test:one:formLeave:add">
                    <a id="btnAdd" href="${ctx}/test/one/formLeave/form" title="增加" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加</a>
                     <a id="btnAdd" href="${ctx}/test/one/formLeave/form?ViewFormType=FormTwo" title="增加2" class="btn btn-default btn-sm"><i
                            class="fa fa-plus"></i>增加2</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="test:one:formLeave:del">
                    <a id="btnDeleteAll" href="${ctx}/test/one/formLeave/deleteAll" title="删除"
                       class="btn btn-default btn-sm"><i class="fa fa-trash-o"></i>删除</a>
                </shiro:hasPermission>
                <a id="btnTotalView" href="#" title="统计" class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i>统计</a>
                <shiro:hasPermission name="test:one:formLeave:import">
                    <table:importExcel url="${ctx}/test/one/formLeave/import"></table:importExcel><!-- 导入按钮 -->
                </shiro:hasPermission>
                <shiro:hasPermission name="test:one:formLeave:export">
                    <table:exportExcel url="${ctx}/test/one/formLeave/export"></table:exportExcel><!-- 导出按钮 -->
                </shiro:hasPermission>
                 <a href="${ctx}/test/one/formLeave/listVue" title="Vue" class="btn btn-default btn-sm"><i
                        class="glyphicon glyphicon-repeat"></i>Vue</a>
                <shiro:hasPermission name="test:one:formLeave:total">
                <a href="${ctx}/test/one/formLeave/total" title="统计图表" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计图表</a>
                 <a href="${ctx}/test/one/formLeave/totalMap" title="统计地图" class="btn btn-default btn-sm"><i
                            class="glyphicon glyphicon-repeat"></i>统计地图</a>
                </shiro:hasPermission>
                     <!-- 工具功能 -->
                    <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
                </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <!-- 查询条件 -->
            <form:form id="searchForm" modelAttribute="formLeave" action="${ctx}/test/one/formLeave/" method="post" class="form-inline">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
                 <div class="form-group">
                    <span>员工：</span>
                        <sys:treeselect id="user" name="user.id" value="${formLeave.user.id}" labelName="user.name" labelValue="${formLeave.user.name}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
                 </div>
                 <div class="form-group">
                    <button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
                    <button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                </div>
            </form:form>

            <!-- 表格 -->
            <table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
                <thead>
                    <tr>
                        <th> <input type="checkbox" class="i-checks"></th>
                        <th  class="sort-column user.name ">员工</th>
                        <th  class="sort-column office.name ">归属部门</th>
                        <th  class="sort-column area.name ">归属区域</th>
                        <th  class="sort-column beginDate hidden-xs">请假开始日期</th>
                        <th  class="sort-column endDate hidden-xs">请假结束日期</th>
                        <th  class="sort-column remarks hidden-xs">备注信息</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="formLeave">
                    <tr>
                        <td>
                        <input type="checkbox" id="${formLeave.id}"
                            user.id="${formLeave.user.id}"
                            office.id="${formLeave.office.id}"
                            area.id="${formLeave.area.id}"
                            remarks="${formLeave.remarks}"
                        class="i-checks"></td>
                        <td class=""><a  href="${ctx}/test/one/formLeave/form?id=${formLeave.id}&action=view">
                            ${formLeave.user.name}
                        </a></td>
                        <td class="">
                            ${formLeave.office.name}
                        </td>
                        <td class="">
                            ${formLeave.area.name}
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${formLeave.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="hidden-xs">
                            <fmt:formatDate value="${formLeave.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="hidden-xs">
                            ${formLeave.remarks}
                        </td>
                        <td>
                            <shiro:hasPermission name="test:one:formLeave:view">
                                <a href="${ctx}/test/one/formLeave/form?id=${formLeave.id}&action=view" title="查看"><i class="fa fa-search-plus"></i></a>
                            </shiro:hasPermission>
                             <shiro:hasPermission name="test:one:formLeave:edit">
                                <a href="${ctx}/test/one/formLeave/form?id=${formLeave.id}" title="修改"  title="修改"><i class="fa fa-pencil"></i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="test:one:formLeave:del">
                                <a href="${ctx}/test/one/formLeave/delete?id=${formLeave.id}" onclick="return confirmx('确认要删除该请假吗？', this.href)" title="删除"><i class="fa fa-trash-o"></i></a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页代码 -->
            ${page.toStringPage()}
            <!-- 统计 -->
            <div class="row" id="total" style="margin-top: 10px;">
                <div class="col-sm-12 echartsEval">
                    <h4>合计：${sumTotalCount}行;
                    </h4>
                    <div id="pie"  class="main000"></div>
                    <echarts:pie
                            id="pie"
                            title="请假数量饼图"
                            subtitle="请假数量饼图"
                            orientData="${orientData}"/>

                    <div id="line_normal"  class="main000"></div>
                    <echarts:line
                    id="line_normal"
                    title="请假曲线"
                    subtitle="请假曲线"
                    xAxisData="${xAxisData}"
                    yAxisData="${yAxisData}"
                    xAxisName="时间"
                    yAxisName="数量" />
                </div>
            </div>
            <!-- 统计 end-->
        </div>
	</div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<script src="/staticViews/viewBase.js"></script>
<script src="/staticViews/modules/test/one/formLeaveList.js" type="text/javascript"></script>
<link href="/staticViews/modules/test/one/formLeaveList.css" rel="stylesheet" />
</body>
</html>