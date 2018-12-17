<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统配置管理</title>
	<meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
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
			var type =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("type");
			var value =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("value");
			var label =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("label");
			var description =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("description");
			var sort =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("sort");

			var obj= '"id":id';
			if(type==undefined) type="";
				obj+=',"type":"'+type+'"';
			if(value==undefined) value="";
				obj+=',"value":"'+value+'"';
			if(label==undefined) label="";
				obj+=',"label":"'+label+'"';
			if(description==undefined) description="";
				obj+=',"description":"'+description+'"';
			if(sort==undefined) sort="";
				obj+=',"sort":"'+sort+'"';

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
		<h5>系统配置列表 </h5>
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
	<form:form id="searchForm" modelAttribute="sysConfig" action="${ctx}/sys/sysConfig/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>类型：</span>
				<form:input path="type" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>数据值：</span>
				<form:input path="value" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>标签名：</span>
				<form:input path="label" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>描述：</span>
				<form:input path="description" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>排序（升序）：</span>
				<form:input path="sort" htmlEscape="false"  class=" form-control input-sm"/>
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
				<th  class="sort-column type">类型</th>
				<th  class="sort-column value">数据值</th>
				<th  class="sort-column label">标签名</th>
				<th  class="sort-column description">描述</th>
				<th  class="sort-column sort">排序（升序）</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysConfig">
			<tr>
				<td>
				<input type="checkbox" id="${sysConfig.id}"
					type="${sysConfig.type}"
					value="${sysConfig.value}"
					label="${sysConfig.label}"
					description="${sysConfig.description}"
					sort="${sysConfig.sort}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看系统配置', '${ctx}/sys/sysConfig/form?id=${sysConfig.id}','800px', '500px')">
					${sysConfig.type}
				</a></td>
				<td>
					${sysConfig.value}
				</td>
				<td>
					${sysConfig.label}
				</td>
				<td>
					${sysConfig.description}
				</td>
				<td>
					${sysConfig.sort}
				</td>
				<td>
					<!--shiro:hasPermission name="sys:sysConfig:view"-->
						<a href="#" onclick="openDialogView('查看系统配置', '${ctx}/sys/sysConfig/form?id=${sysConfig.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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