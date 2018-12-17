<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务调度管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/modules/job//sysJobList.js" type="text/javascript"></script>
	<link href="/staticViews/modules/job//sysJobList.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content"  id="rrapp">
		<div class="ibox">
		<!--div class="ibox-title">
			<h5>定时任务调度列表 </h5>
		</div-->

		<div class="ibox-content">
		<sys:message content="${message}"/>

		<!--查询条件-->
		<div class="row">
		<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="sysJob" action="${ctx}/../rest/job/sysJob/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
			<div class="form-group">
				<span>任务名称：</span>
					<form:input path="jobName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
				<span>任务组名：</span>
					<form:input path="jobGroup" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
				<span>任务方法：</span>
					<form:input path="methodName" htmlEscape="false" maxlength="500"  class=" form-control input-sm"/>
				<span>方法参数：</span>
					<form:input path="methodParams" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
				<span>cron执行表达式：</span>
					<form:input path="cronExpression" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
				<span>计划执行错误策略（0默认 1继续 2等待 3放弃）：</span>
					<form:radiobuttons class="i-checks" path="misfirePolicy" items="${fns:getDictList('misfire_policy')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<span>状态（0正常 1暂停）：</span>
					<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('job_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<span>创建者：</span>
					<form:input path="createBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
				<span>创建时间：</span>
					<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysJob.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
					<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysJob.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<span>更新者：</span>
					<form:input path="updateBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
				<span>更新时间：</span>
					<input id="beginUpdateDate" name="beginUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysJob.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
					<input id="endUpdateDate" name="endUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysJob.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<span>备注信息：</span>
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
				<button  class="btn btn-success btn-sm " @click="search()" title="查询"><i class="fa fa-search"></i> 查询</button>
				<shiro:hasPermission name="job:sysJob:add">
					<table:addRow url="${ctx}/job/sysJob/form" title="定时任务调度"></table:addRow><!-- 增加按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="job:sysJob:edit">
					<table:editRow url="${ctx}/job/sysJob/form" title="定时任务调度" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="job:sysJob:del">
					<table:delRow url="${ctx}/job/sysJob/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="job:sysJob:import">
					<table:importExcel url="${ctx}/job/sysJob/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="job:sysJob:export">
					<table:exportExcel url="${ctx}/job/sysJob/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				<button  class="btn btn-white btn-sm " onclick="reset()"  title="重置"><i class="fa fa-refresh"></i> 重置</button>
			</div>
			<div class="pull-right">
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/job/sysJob','定时任务调度', false)" title="list"><i class="glyphicon glyphicon-repeat"></i> list</button>
				<shiro:hasPermission name="job:sysJob:total">
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/job/sysJob/total','统计定时任务调度', false)" title="统计图表"><i class="glyphicon glyphicon-repeat"></i> 统计图表</button>
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/job/sysJob/totalMap','统计定时任务调度', false)" title="统计地图"><i class="glyphicon glyphicon-repeat"></i> 统计地图</button>
				</shiro:hasPermission>
				<button class="btn btn-success " type="button" name="toggle" title="切换" onclick="$('.table').toggle()"><i class="glyphicon glyphicon-list-alt icon-list-alt"></i></button>
				<div class="btn-group" title="其他">
					<button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" type="button" aria-expanded="false">
						<i class="glyphicon glyphicon-th icon-th"></i>
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li data-type="放大"><a href="javascript:void(0)" onclick="$('body').css({zoom:Number($('body').css('zoom'))+0.1})">放大</a></li>
						<li data-type="缩小"><a href="javascript:void(0)" onclick="$('body').css({zoom:$('body').css('zoom')-0.1})">缩小</a></li>
					</ul>
				</div>
			</div>
		</div>
		</div>

		<!-- 表格 -->
		<div class="table" style="display:none">
				<div style="border: 1px solid #e7eaec;padding: 8px;" class="row" v-for="item in page">
					<div>
					<input type="checkbox" :id="item.id"
						jobName="${sysJob.jobName}"
						jobGroup="${sysJob.jobGroup}"
						methodName="${sysJob.methodName}"
						methodParams="${sysJob.methodParams}"
						cronExpression="${sysJob.cronExpression}"
						misfirePolicy="${sysJob.misfirePolicy}"
						status="${sysJob.status}"
						createBy.id="${sysJob.createBy.id}"
						delFlag="${sysJob.delFlag}"
					class="i-checks">
					</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					任务名称:
					<a  href="#" v-on:onclick="openDialogView('查看定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')">
						{{item.jobName}}
					</a></span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					任务组名:
					
						{{item.jobGroup}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					任务方法:
					
						{{item.methodName}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					方法参数:
					
						{{item.methodParams}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					cron执行表达式:
					
						{{item.cronExpression}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					计划执行错误策略（0默认 1继续 2等待 3放弃）:
					
						{{item.misfirePolicyLabel}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					状态（0正常 1暂停）:
					
						{{item.statusLabel}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					创建者:
					
						{{item.createBy.id}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					删除标记:
					
						{{item.delFlagLabel}}
					</span>
							</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<shiro:hasPermission name="job:sysJob:view">
							<a href="#" onclick="openDialogView('查看定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> 查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="job:sysJob:edit">
							<a href="#" onclick="openDialog('修改定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> 修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="job:sysJob:del">
							<a href="${ctx}/job/sysJob/delete?id='+item.id" onclick="return confirmx('确认要删除该定时任务调度吗？', this.href)"   class="btn btn-danger btn-sm"  title="删除"><i class="fa fa-trash"></i> 删除</a>
						</shiro:hasPermission>
					</div>
				</div>
		</div>

		<!-- 表格 -->
		<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
			<thead>
				<tr>
					<th> <input type="checkbox" class="i-checks"></th>
					<th  class="sort-column jobName ">任务名称</th>
					<th  class="sort-column jobGroup ">任务组名</th>
					<th  class="sort-column methodName ">任务方法</th>
					<th  class="sort-column methodParams hidden-xs">方法参数</th>
					<th  class="sort-column cronExpression hidden-xs">cron执行表达式</th>
					<th  class="sort-column misfirePolicy hidden-xs">计划执行错误策略（0默认 1继续 2等待 3放弃）</th>
					<th  class="sort-column status hidden-xs">状态（0正常 1暂停）</th>
					<th  class="sort-column createBy.id hidden-xs">创建者</th>
					<th  class="sort-column delFlag hidden-xs">删除标记</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr  v-for="item in page" >
					<td>
					<input type="checkbox" :id="item.id"
						jobName="${sysJob.jobName}"
						jobGroup="${sysJob.jobGroup}"
						methodName="${sysJob.methodName}"
						methodParams="${sysJob.methodParams}"
						cronExpression="${sysJob.cronExpression}"
						misfirePolicy="${sysJob.misfirePolicy}"
						status="${sysJob.status}"
						createBy.id="${sysJob.createBy.id}"
						delFlag="${sysJob.delFlag}"
					class="i-checks"></td>
					<td  class=""><a  href="#" v-on:click="openDialogView('查看定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')">
					{{item.jobName}}
					</a></td>
					<td  class="">
					{{item.jobGroup}}
					</td>
					<td  class="">
					{{item.methodName}}
					</td>
					<td  class="hidden-xs">
					{{item.methodParams}}
					</td>
					<td  class="hidden-xs">
					{{item.cronExpression}}
					</td>
					<td  class="hidden-xs">
					{{item.misfirePolicy}}
					</td>
					<td  class="hidden-xs">
					{{item.statusLabel}}
					</td>
					<td  class="hidden-xs">
					{{item.createBy.id}}
					</td>
					<td  class="hidden-xs">
					{{item.delFlagLabel}}
					</td>
					<td>
						<shiro:hasPermission name="job:sysJob:view">
							<a href="#" v-on:click="openDialogView('查看定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="job:sysJob:edit">
							<a href="#" v-on:click="openDialog('修改定时任务调度', '${ctx}/job/sysJob/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="job:sysJob:edit">
    						<a href="#" v-on:click="top.openTab('${ctx}/job/sysJob/form?id='+item.id,'修改定时任务调度', false)" title="修改" class="btn btn-success btn-sm" title=" 修改(页签)"><i class="fa fa-edit"></i></a>
    					</shiro:hasPermission>
						<shiro:hasPermission name="job:sysJob:del">
							<a v-bind:href="'${ctx}/job/sysJob/delete?id='+item.id" onclick="return confirmx('确认要删除该定时任务调度吗？', this.href)"   class="btn btn-danger btn-sm" title="删除"><i class="fa fa-trash"></i> </a>
						</shiro:hasPermission>
					</td>
				</tr>
			</tbody>
		</table>
			<!-- 分页代码 -->
			<div v-html="result.html">
				{{result.html}}
			</div>
		<br/>
		<br/>
		</div>
		</div>
	</div>
	<script src="/static/vue/vue.min.js"></script>
	<script src="/static/common/SpringUI.js"></script>
</body>
</html>