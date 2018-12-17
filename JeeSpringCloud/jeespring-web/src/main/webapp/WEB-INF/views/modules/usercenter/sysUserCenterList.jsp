<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户中心管理</title>
	<meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
	        laydate({
	            elem: '#beginUpdateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endUpdateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysUserCenter" action="${ctx}/usercenter/sysUserCenter/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>用户编号：</span>
				<form:input path="userId" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>用户名称：</span>
				<form:input path="userName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>用户手机号：</span>
				<form:input path="userPhone" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>纬度：</span>
				<form:input path="lat" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
			<span>经度：</span>
				<form:input path="lng" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
			<span>城市：</span>
				<form:input path="city" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
			<span>地址：</span>
				<form:input path="address" htmlEscape="false" maxlength="30"  class=" form-control input-sm"/>
			<span>创建者：</span>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>创建时间：</span>
				<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${sysUserCenter.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
				<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${sysUserCenter.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>更新者：</span>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>更新时间：</span>
				<input id="beginUpdateDate" name="beginUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${sysUserCenter.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
				<input id="endUpdateDate" name="endUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${sysUserCenter.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>删除标记：</span>
				<form:radiobuttons class="i-checks" path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="usercenter:sysUserCenter:add">
				<table:addRow url="${ctx}/usercenter/sysUserCenter/form" title="用户中心"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="usercenter:sysUserCenter:edit">
			    <table:editRow url="${ctx}/usercenter/sysUserCenter/form" title="用户中心" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="usercenter:sysUserCenter:del">
				<table:delRow url="${ctx}/usercenter/sysUserCenter/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="usercenter:sysUserCenter:import">
				<table:importExcel url="${ctx}/usercenter/sysUserCenter/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="usercenter:sysUserCenter:export">
	       		<table:exportExcel url="${ctx}/usercenter/sysUserCenter/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			<button  class="btn btn-white btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		<div class="pull-right">
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
	<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column userId">用户编号</th>
				<th  class="sort-column userName">用户名称</th>
				<th  class="sort-column userPhone">用户手机号</th>
				<th  class="sort-column lat">纬度</th>
				<th  class="sort-column lng">经度</th>
				<th  class="sort-column city">城市</th>
				<th  class="sort-column address">地址</th>
				<th  class="sort-column createBy.id">创建者</th>
				<th  class="sort-column createDate">创建时间</th>
				<th  class="sort-column updateBy.id">更新者</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUserCenter">
			<tr>
				<td>
				<input type="checkbox" id="${sysUserCenter.id}"
					userId="${sysUserCenter.userId}"
					userName="${sysUserCenter.userName}"
					userPhone="${sysUserCenter.userPhone}"
					lat="${sysUserCenter.lat}"
					lng="${sysUserCenter.lng}"
					createBy.id="${sysUserCenter.createBy.id}"
					updateBy.id="${sysUserCenter.updateBy.id}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户中心', '${ctx}/usercenter/sysUserCenter/form?id=${sysUserCenter.id}','800px', '500px')">
					${sysUserCenter.userId}
				</a></td>
				<td>
					${sysUserCenter.userName}
				</td>
				<td>
					${sysUserCenter.userPhone}
				</td>
				<td>
					${sysUserCenter.lat}
				</td>
				<td>
					${sysUserCenter.lng}
				</td>
				<td>
					${sysUserCenter.city}
				</td>
				<td>
					${sysUserCenter.address}
				</td>
				<td>
					${sysUserCenter.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysUserCenter.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${sysUserCenter.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysUserCenter.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<!--shiro:hasPermission name="usercenter:sysUserCenter:view"-->
						<a href="#" onclick="openDialogView('查看用户中心', '${ctx}/usercenter/sysUserCenter/form?id=${sysUserCenter.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
					<!--/shiro:hasPermission-->
					<!--shiro:hasPermission name="usercenter:sysUserCenter:edit"-->
    					<a href="#" onclick="openDialog('修改用户中心', '${ctx}/usercenter/sysUserCenter/form?id=${sysUserCenter.id}','800px', '500px')" class="btn btn-success btn-sm" ><i class="fa fa-edit"></i> 修改</a>
    				<!--/shiro:hasPermission-->
    				<!--shiro:hasPermission name="usercenter:sysUserCenter:del"-->
						<a href="${ctx}/usercenter/sysUserCenter/delete?id=${sysUserCenter.id}" onclick="return confirmx('确认要删除该用户中心吗？', this.href)"   class="btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
					<!--/shiro:hasPermission-->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>