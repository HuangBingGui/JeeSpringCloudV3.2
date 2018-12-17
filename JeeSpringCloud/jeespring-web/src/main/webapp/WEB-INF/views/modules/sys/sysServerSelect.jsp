<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务器监控管理</title>
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

		function openWindowSelect(){
			window.backup="selectData";
			// 正常打开
			top.layer.open({
				type: 2,
				area: ['800px', '720px'],
				title:"选择部门",
				ajaxData:{},
				content: location.href ,
				btn: ['确定', '关闭']
				   ,yes: function(index, layero){ //或者使用btn1
							var window = layero.find("iframe")[0].contentWindow;//h.find("iframe").contents();
							//回调方法，可以选择使用
							window.backup="selectData";
							window.select();
							//直接处理returnValue值，可以选择使用
							if (window.opener) {
								console.log("openSelect:"+window.opener.returnValue);
							}
							else if(window.parent){
                                if(window.parent.returnValue!=undefined)
									console.log("openSelect:"+window.parent.returnValue);
							}
							else {
								console.log("openSelect:"+window.returnValue);
							}
							top.layer.close(index);
						},
			cancel: function(index){ //或者使用btn2
					   //按钮【按钮二】的回调
				   }
			});
		}
		function openSelect(){
            var  iWidth=560; //模态窗口宽度
            var  iHeight=300;//模态窗口高度
            var  iTop=(window.screen.height-iHeight-100)/2;
            var  iLeft=(window.screen.width-iWidth)/2;
            window.backup="selectData";
            window.open(location.href, "newwindow", "dialogHeight:"+iHeight+"px; dialogWidth:"+iWidth+"px; toolbar:no; menubar:no; scrollbars:no; resizable:no; location:no; status:no;left:200px;top:100px;");
        }

        function selectData(){
            if (window.opener) {
                console.log("openSelect:"+window.opener.returnValue);
            }
            else {
                console.log("openSelect:"+window.returnValue);
            }
        }

        function select(){
            var str="";
            var ids="";
            var size = $("#contentTable tbody tr td input.i-checks:checked").size();
            if(size == 0 ){
                top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
                return;
            }
            if(size > 1 ){
                top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
                return;
            }
            var id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("id");
			var serverNumber =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("serverNumber");
			var serverAddress =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("serverAddress");
			var name =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("name");
			var label =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("label");
			var picture =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("picture");
			var type =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("type");
			var sort =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("sort");
			var description =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("description");
			var createDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("createDate");
			var updateDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateDate");

			var obj= '"id":id';
			if(serverNumber==undefined) serverNumber="";
				obj+=',"serverNumber":"'+serverNumber+'"';
			if(serverAddress==undefined) serverAddress="";
				obj+=',"serverAddress":"'+serverAddress+'"';
			if(name==undefined) name="";
				obj+=',"name":"'+name+'"';
			if(label==undefined) label="";
				obj+=',"label":"'+label+'"';
			if(picture==undefined) picture="";
				obj+=',"picture":"'+picture+'"';
			if(type==undefined) type="";
				obj+=',"type":"'+type+'"';
			if(sort==undefined) sort="";
				obj+=',"sort":"'+sort+'"';
			if(description==undefined) description="";
				obj+=',"description":"'+description+'"';
			if(createDate==undefined) createDate="";
				obj+=',"createDate":"'+createDate+'"';
			if(updateDate==undefined) updateDate="";
				obj+=',"updateDate":"'+updateDate+'"';

            if (window.opener) {
				window.opener.returnValue=eval("({"+obj+"})");
				if(window.opener.backup!=undefined)
                	eval("window.opener."+window.opener.backup+"();");
            }
            else if(window.parent!=undefined){
                window.parent.returnValue =eval("({"+obj+"})");
                if(window.parent.backup!=undefined)
                    eval("window.parent."+window.parent.backup+"();");
            }
            else {
                window.returnValue =eval("({"+obj+"})");
                if(window.backup!=undefined)
                	eval("window."+window.backup+"();");
            }
            window.close();
        }
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
	<form:form id="searchForm" modelAttribute="sysServer" action="${ctx}/sys/sysServer/" method="post" class="form-inline">
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
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			<button  class="btn btn-success btn-sm " onclick="openSelect()"><i class="fa fa-refresh"></i> OpenSelect</button>
			<button  class="btn btn-success btn-sm " onclick="openWindowSelect()"><i class="fa fa-refresh"></i> OpenWindowSelect</button>
			<button  class="btn btn-success btn-sm " onclick="select()"><i class="fa fa-refresh"></i> select</button>
			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column serverNumber">服务器编号</th>
				<th  class="sort-column serverAddress">服务器监控地址</th>
				<th  class="sort-column name">名称</th>
				<th  class="sort-column label">标签名</th>
				<th  class="sort-column picture">图片</th>
				<th  class="sort-column type">类型</th>
				<th  class="sort-column sort">排序（升序）</th>
				<th  class="sort-column description">描述</th>
				<th  class="sort-column createDate">创建时间</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysServer">
			<tr>
				<td>
				<input type="checkbox" id="${sysServer.id}"
					serverNumber="${sysServer.serverNumber}"
					serverAddress="${sysServer.serverAddress}"
					name="${sysServer.name}"
					label="${sysServer.label}"
					picture="${sysServer.picture}"
					type="${sysServer.type}"
					sort="${sysServer.sort}"
					description="${sysServer.description}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id=${sysServer.id}','800px', '500px')">
					${sysServer.serverNumber}
				</a></td>
				<td>
					${sysServer.serverAddress}
				</td>
				<td>
					${sysServer.name}
				</td>
				<td>
					${sysServer.label}
				</td>
				<td>
					${sysServer.picture}
				</td>
				<td>
					${fns:getDictLabel(sysServer.type, 'server_type', '')}
				</td>
				<td>
					${sysServer.sort}
				</td>
				<td>
					${sysServer.description}
				</td>
				<td>
					<fmt:formatDate value="${sysServer.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sysServer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<!--shiro:hasPermission name="sys:sysServer:view"-->
						<a href="#" onclick="openDialogView('查看服务器监控', '${ctx}/sys/sysServer/form?id=${sysServer.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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