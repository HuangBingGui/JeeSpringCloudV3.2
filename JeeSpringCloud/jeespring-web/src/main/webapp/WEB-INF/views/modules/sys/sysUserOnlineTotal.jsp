<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/modules/sys//sysUserOnlineTotal.js" type="text/javascript"></script>
	<link href="/staticViews/modules/sys//sysUserOnlineTotal.css" rel="stylesheet" />

	<div class="wrapper wrapper-content content-background">
	<div class="ibox">
    <div class="ibox-content">
		<!--查询条件-->
<div class="row">
<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysUserOnline" action="${ctx}/sys/sysUserOnline/total" method="post" class="form-inline">
	<div class="form-group">
		<input id="run" type="checkbox" value="true" name="run" checked/>自动刷新
		<form:select path="totalType"  class="form-control m-b">
			<form:option value="" label=""/>
			<form:options items="${fns:getDictList('total_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<span>登录账号：</span>
		<form:input path="loginName" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		<span>部门名称：</span>
		<form:input path="deptName" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		<span>登录IP地址：</span>
		<form:input path="ipaddr" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		<span>登录地点：</span>
		<form:input path="loginLocation" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
		<span>浏览器类型：</span>
		<form:input path="browser" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		<span>操作系统：</span>
		<form:input path="os" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		<span>在线状态on_line在线off_line离线：</span>
	<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('on_line_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		<span>session创建时间：</span>
	<input id="beginStartTimestsamp" name="beginStartTimestsamp" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.beginStartTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endStartTimestsamp" name="endStartTimestsamp" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.endStartTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<span>session最后访问时间：</span>
	<input id="beginLastAccessTime" name="beginLastAccessTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.beginLastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endLastAccessTime" name="endLastAccessTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.endLastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<span>超时时间，单位为分钟：</span>
		<form:input path="expireTime" htmlEscape="false" maxlength="5"  class=" form-control input-sm"/>
		<span>创建者：</span>
		<form:input path="createBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>创建时间：</span>
	<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysUserOnline.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<span>更新者：</span>
		<form:input path="updateBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		</div>
		</form:form>
<br/>
		</div>
		</div>

			<!-- 工具栏 -->
	<div class="row">
		<div class="col-sm-12">
			<div class="pull-left">
					<button  class="btn btn-success btn-sm " onclick="$('#searchForm').toggle();$('.fa-chevron').toggle();"  title="检索">
						<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 检索
					</button>
					<button  class="btn btn-success btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			</div>
			<div class="pull-right">
				<div class="btn-group" title="其他">
					<button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" type="button" aria-expanded="false">
						<i class="glyphicon glyphicon-th icon-th"></i>
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li data-type="放大"><a href="javascript:void(0)" onclick="$('body').css({zoom:Number($('body').css('zoom'))+0.1});$('body .echartsEval script').each(function(){eval($(this).html())});">放大</a></li>
						<li data-type="缩小"><a href="javascript:void(0)" onclick="$('body').css({zoom:$('body').css('zoom')-0.1});$('body .echartsEval script').each(function(){eval($(this).html())});">缩小</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="row" style="margin-top: 10px;">
		<div class="col-sm-12 echartsEval">
			<div id="pie"  class="main000"></div>
			<echarts:pie
					id="pie"
					title="在线用户记录数量饼图"
					subtitle="在线用户记录数量饼图"
					orientData="${orientData}"/>

			<div id="line_normal"  class="main000"></div>
			<echarts:line
			id="line_normal"
			title="在线用户记录曲线"
			subtitle="在线用户记录曲线"
			xAxisData="${xAxisData}"
			yAxisData="${yAxisData}"
			xAxisName="时间"
			yAxisName="数量" />
		</div>
	</div>
	<!-- 表格 -->
	<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>时间段</th>
				<th>数量</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="sysUserOnline">
		<tr>
			<td>${sysUserOnline.totalDate}</td>
			<td style="text-align: right;" class="totalCount">${sysUserOnline.totalCount}</td>
		</tr>
		</c:forEach>
		</tbody>
		<tfoot>
			<tr id="totalRow">
				<td>合计：</td>
				<td id="totalCount"  style="text-align: right;"><script>sumColumn("totalCount");</script></td>
			</tr>
		</tfoot>
	</table>
</div>
</div>
</div>