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

		function openWindowSelect(){
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
			var userId =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("userId");
			var userName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("userName");
			var userPhone =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("userPhone");
			var lat =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("lat");
			var lng =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("lng");
			var createBy.id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("createBy.id");
			var createDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("createDate");
			var updateBy.id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateBy.id");
			var updateDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateDate");

			var obj= '"id":id';
			if(userId==undefined) userId="";
				obj+=',"userId":"'+userId+'"';
			if(userName==undefined) userName="";
				obj+=',"userName":"'+userName+'"';
			if(userPhone==undefined) userPhone="";
				obj+=',"userPhone":"'+userPhone+'"';
			if(lat==undefined) lat="";
				obj+=',"lat":"'+lat+'"';
			if(lng==undefined) lng="";
				obj+=',"lng":"'+lng+'"';
			if(createBy.id==undefined) createBy.id="";
				obj+=',"createBy.id":"'+createBy.id+'"';
			if(createDate==undefined) createDate="";
				obj+=',"createDate":"'+createDate+'"';
			if(updateBy.id==undefined) updateBy.id="";
				obj+=',"updateBy.id":"'+updateBy.id+'"';
			if(updateDate==undefined) updateDate="";
				obj+=',"updateDate":"'+updateDate+'"';

            if (window.opener) {
				window.opener.returnValue=eval("({"+obj+"})");
				if(window.backup!=undefined)
                	eval("window.opener."+window.opener.backup+"();");
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
	<div class="ibox-title">
		<h5>用户中心列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
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
				<th  class="sort-column userId">用户编号</th>
				<th  class="sort-column userName">用户名称</th>
				<th  class="sort-column userPhone">用户手机号</th>
				<th  class="sort-column lat">纬度</th>
				<th  class="sort-column lng">经度</th>
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
					${sysUserCenter.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysUserCenter.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysUserCenter.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysUserCenter.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<!--shiro:hasPermission name="usercenter:sysUserCenter:view"-->
						<a href="#" onclick="openDialogView('查看用户中心', '${ctx}/usercenter/sysUserCenter/form?id=${sysUserCenter.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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