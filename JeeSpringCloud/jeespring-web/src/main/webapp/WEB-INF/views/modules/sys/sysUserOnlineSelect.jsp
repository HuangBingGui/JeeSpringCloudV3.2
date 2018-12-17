<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>在线用户记录管理</title>
	<meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginStartTimestsamp', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endStartTimestsamp', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
	        laydate({
	            elem: '#beginLastAccessTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endLastAccessTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
	        laydate({
	            elem: '#beginCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
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
			var loginName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("loginName");
			var deptName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("deptName");
			var ipaddr =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("ipaddr");
			var loginLocation =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("loginLocation");
			var browser =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("browser");
			var os =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("os");
			var status =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("status");
			var startTimestsamp =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("startTimestsamp");
			var updateDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateDate");

			var obj= '"id":id';
			if(loginName==undefined) loginName="";
				obj+=',"loginName":"'+loginName+'"';
			if(deptName==undefined) deptName="";
				obj+=',"deptName":"'+deptName+'"';
			if(ipaddr==undefined) ipaddr="";
				obj+=',"ipaddr":"'+ipaddr+'"';
			if(loginLocation==undefined) loginLocation="";
				obj+=',"loginLocation":"'+loginLocation+'"';
			if(browser==undefined) browser="";
				obj+=',"browser":"'+browser+'"';
			if(os==undefined) os="";
				obj+=',"os":"'+os+'"';
			if(status==undefined) status="";
				obj+=',"status":"'+status+'"';
			if(startTimestsamp==undefined) startTimestsamp="";
				obj+=',"startTimestsamp":"'+startTimestsamp+'"';
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
	<form:form id="searchForm" modelAttribute="sysUserOnline" action="${ctx}/sys/sysUserOnline/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
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
				<th  class="sort-column loginName">登录账号</th>
				<th  class="sort-column deptName">部门名称</th>
				<th  class="sort-column ipaddr">登录IP地址</th>
				<th  class="sort-column loginLocation">登录地点</th>
				<th  class="sort-column browser">浏览器类型</th>
				<th  class="sort-column os">操作系统</th>
				<th  class="sort-column status">在线状态on_line在线off_line离线</th>
				<th  class="sort-column startTimestsamp">session创建时间</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUserOnline">
			<tr>
				<td>
				<input type="checkbox" id="${sysUserOnline.id}"
					loginName="${sysUserOnline.loginName}"
					deptName="${sysUserOnline.deptName}"
					ipaddr="${sysUserOnline.ipaddr}"
					loginLocation="${sysUserOnline.loginLocation}"
					browser="${sysUserOnline.browser}"
					os="${sysUserOnline.os}"
					status="${sysUserOnline.status}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看在线用户记录', '${ctx}/sys/sysUserOnline/form?id=${sysUserOnline.id}','800px', '500px')">
					${sysUserOnline.loginName}
				</a></td>
				<td>
					${sysUserOnline.deptName}
				</td>
				<td>
					${sysUserOnline.ipaddr}
				</td>
				<td>
					${sysUserOnline.loginLocation}
				</td>
				<td>
					${sysUserOnline.browser}
				</td>
				<td>
					${sysUserOnline.os}
				</td>
				<td>
					${fns:getDictLabel(sysUserOnline.status, 'on_line_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${sysUserOnline.startTimestsamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sysUserOnline.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<!--shiro:hasPermission name="sys:sysUserOnline:view"-->
						<a href="#" onclick="openDialogView('查看在线用户记录', '${ctx}/sys/sysUserOnline/form?id=${sysUserOnline.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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