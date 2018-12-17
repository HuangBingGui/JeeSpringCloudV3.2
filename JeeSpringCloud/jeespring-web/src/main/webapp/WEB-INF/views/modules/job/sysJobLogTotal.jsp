<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/modules/job//sysJobLogTotal.js" type="text/javascript"></script>
	<link href="/staticViews/modules/job//sysJobLogTotal.css" rel="stylesheet" />

	<div class="wrapper wrapper-content content-background">
	<div class="ibox">
    <div class="ibox-content">
		<!--查询条件-->
<div class="row">
<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysJobLog" action="${ctx}/job/sysJobLog/total" method="post" class="form-inline">
	<div class="form-group">
		<input id="run" type="checkbox" value="true" name="run" checked/>自动刷新
		<form:select path="totalType"  class="form-control m-b">
			<form:option value="" label=""/>
			<form:options items="${fns:getDictList('total_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<span>任务名称：</span>
		<form:input path="jobName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>任务组名：</span>
		<form:input path="jobGroup" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>任务方法：</span>
		<form:input path="methodName" htmlEscape="false" maxlength="500"  class=" form-control input-sm"/>
		<span>方法参数：</span>
		<form:input path="methodParams" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		<span>日志信息：</span>
		<form:input path="jobMessage" htmlEscape="false" maxlength="500"  class=" form-control input-sm"/>
		<span>执行状态（0正常 1失败）：</span>
	<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('job_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		<span>异常信息：</span>
		<span>创建时间：</span>
	<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJobLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJobLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
					title="定时任务调度日志数量饼图"
					subtitle="定时任务调度日志数量饼图"
					orientData="${orientData}"/>

			<div id="line_normal"  class="main000"></div>
			<echarts:line
			id="line_normal"
			title="定时任务调度日志曲线"
			subtitle="定时任务调度日志曲线"
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
		<c:forEach items="${list}" var="sysJobLog">
		<tr>
			<td>${sysJobLog.totalDate}</td>
			<td style="text-align: right;" class="totalCount">${sysJobLog.totalCount}</td>
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