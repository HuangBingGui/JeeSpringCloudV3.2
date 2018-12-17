<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务器监控管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/staticViews/modules/sys//sysServerList.js" type="text/javascript"></script>
	<link href="/staticViews/modules/sys//sysServerList.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content"  id="rrapp">
		<div class="ibox">
		<!--div class="ibox-title">
			<h5>服务器监控列表 </h5>
		</div-->

		<div class="ibox-content">
		<sys:message content="${message}"/>

		<!--查询条件-->
		<div class="row">
		<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="sysServer" action="${ctx}/../rest/sys/sysServer/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
			<div class="form-group">
				<span>服务器编号：</span>
					<form:input path="serverNumber" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
				<span>服务器监控地址：</span>
					<form:input path="serverAddress" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
				<span>名称：</span>
					<form:input path="name" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
				<span>标签名：</span>
					<form:input path="label" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
				<span>类型：</span>
					<form:radiobuttons class="i-checks" path="type" items="${fns:getDictList('server_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<span>排序（升序）：</span>
					<form:input path="sort" htmlEscape="false"  class=" form-control input-sm"/>
				<span>描述：</span>
					<form:input path="description" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
				<span>备注信息：</span>
					<form:input path="remarks" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
				<span>在线状态on_line在线off_line离线：</span>
					<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('on_line_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<span>创建时间：</span>
					<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysServer.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
					<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysServer.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<span>更新时间：</span>
					<input id="beginUpdateDate" name="beginUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysServer.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
					<input id="endUpdateDate" name="endUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
						value="<fmt:formatDate value="${sysServer.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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
				<shiro:hasPermission name="sys:sysServer:add">
					<table:addRow url="${ctx}/sys/sysServer/form" title="服务器监控"></table:addRow><!-- 增加按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:sysServer:edit">
					<table:editRow url="${ctx}/sys/sysServer/form" title="服务器监控" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:sysServer:del">
					<table:delRow url="${ctx}/sys/sysServer/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:sysServer:import">
					<table:importExcel url="${ctx}/sys/sysServer/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:sysServer:export">
					<table:exportExcel url="${ctx}/sys/sysServer/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				<button  class="btn btn-white btn-sm " onclick="reset()"  title="重置"><i class="fa fa-refresh"></i> 重置</button>
			</div>
			<div class="pull-right">
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/sys/sysServer','服务器监控', false)" title="list"><i class="glyphicon glyphicon-repeat"></i> list</button>
				<shiro:hasPermission name="sys:sysServer:total">
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/sys/sysServer/total','统计服务器监控', false)" title="统计图表"><i class="glyphicon glyphicon-repeat"></i> 统计图表</button>
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/sys/sysServer/totalMap','统计服务器监控', false)" title="统计地图"><i class="glyphicon glyphicon-repeat"></i> 统计地图</button>
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
						serverNumber="${sysServer.serverNumber}"
						serverAddress="${sysServer.serverAddress}"
						name="${sysServer.name}"
						label="${sysServer.label}"
						picture="${sysServer.picture}"
						type="${sysServer.type}"
						sort="${sysServer.sort}"
						description="${sysServer.description}"
					class="i-checks">
					</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					服务器编号:
					<a  href="#" v-on:onclick="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')">
						{{item.serverNumber}}
					</a></span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					服务器监控地址:
					
						{{item.serverAddress}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					名称:
					
						{{item.name}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					标签名:
					
						{{item.label}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					图片:
					
						{{item.picture}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					类型:
					
						{{item.typeLabel}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					排序（升序）:
					
						{{item.sort}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					描述:
					
						{{item.description}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					创建时间:
					
						{{item.createDate}}
					</span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					更新时间:
					
						{{item.updateDate}}
					</span>
							</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<shiro:hasPermission name="sys:sysServer:view">
							<a href="#" onclick="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> 查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:sysServer:edit">
							<a href="#" onclick="openDialog('修改服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> 修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:sysServer:del">
							<a href="${ctx}/sys/sysServer/delete?id='+item.id" onclick="return confirmx('确认要删除该服务器监控吗？', this.href)"   class="btn btn-danger btn-sm"  title="删除"><i class="fa fa-trash"></i> 删除</a>
						</shiro:hasPermission>
					</div>
				</div>
		</div>

		<!-- 表格 -->
		<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
			<thead>
				<tr>
					<th> <input type="checkbox" class="i-checks"></th>
					<th  class="sort-column serverNumber ">服务器编号</th>
					<th  class="sort-column serverAddress ">服务器监控地址</th>
					<th  class="sort-column name ">名称</th>
					<th  class="sort-column label hidden-xs">标签名</th>
					<th  class="sort-column picture hidden-xs">图片</th>
					<th  class="sort-column type hidden-xs">类型</th>
					<th  class="sort-column sort hidden-xs">排序（升序）</th>
					<th  class="sort-column description hidden-xs">描述</th>
					<th  class="sort-column createDate hidden-xs">创建时间</th>
					<th  class="sort-column updateDate hidden-xs">更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr  v-for="item in page" >
					<td>
					<input type="checkbox" :id="item.id"
						serverNumber="${sysServer.serverNumber}"
						serverAddress="${sysServer.serverAddress}"
						name="${sysServer.name}"
						label="${sysServer.label}"
						picture="${sysServer.picture}"
						type="${sysServer.type}"
						sort="${sysServer.sort}"
						description="${sysServer.description}"
					class="i-checks"></td>
					<td  class=""><a  href="#" v-on:click="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')">
					{{item.serverNumber}}
					</a></td>
					<td  class="">
					{{item.serverAddress}}
					</td>
					<td  class="">
					{{item.name}}
					</td>
					<td  class="hidden-xs">
					{{item.label}}
					</td>
					<td  class="hidden-xs">
					{{item.picture}}
					</td>
					<td  class="hidden-xs">
					{{item.typeLabel}}
					</td>
					<td  class="hidden-xs">
					{{item.sort}}
					</td>
					<td  class="hidden-xs">
					{{item.description}}
					</td>
					<td  class="hidden-xs">
					{{item.createDate}}
					</td>
					<td  class="hidden-xs">
					{{item.updateDate}}
					</td>
					<td>
						<shiro:hasPermission name="sys:sysServer:view">
							<a href="#" v-on:click="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:sysServer:edit">
							<a href="#" v-on:click="openDialog('修改服务器监控', '${ctx}/sys/sysServer/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:sysServer:edit">
    						<a href="#" v-on:click="top.openTab('${ctx}/sys/sysServer/form?id='+item.id,'修改服务器监控', false)" title="修改" class="btn btn-success btn-sm" title=" 修改(页签)"><i class="fa fa-edit"></i></a>
    					</shiro:hasPermission>
						<shiro:hasPermission name="sys:sysServer:del">
							<a v-bind:href="'${ctx}/sys/sysServer/delete?id='+item.id" onclick="return confirmx('确认要删除该服务器监控吗？', this.href)"   class="btn btn-danger btn-sm" title="删除"><i class="fa fa-trash"></i> </a>
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