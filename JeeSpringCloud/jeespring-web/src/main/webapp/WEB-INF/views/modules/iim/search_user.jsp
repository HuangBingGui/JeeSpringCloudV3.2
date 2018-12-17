<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
				    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			
		

			    $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
			    	});

			    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
			    	});
			    
			
		});

		function getSelectedIds(){

			  var str="";
			  var ids="";
			  $("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
			    if(true == $(this).is(':checked')){
			      str+=$(this).attr("id")+",";
			    }
			  });

			  if(str.substr(str.length-1)== ','){
			    ids = str.substr(0,str.length-1);
			  }
			  if(ids == ""){
				top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
				return '-1';
			  }

			  return ids;
				
		}
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/iim/contact/searchUsers");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
	<div class="col-sm-1"></div>
	<div class="col-sm-11">
	<div style="float:right">
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/iim/contact/searchUsers" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn  id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"></table:sortColumn><!-- 支持排序 -->
		<div class="form-group">
			<span>姓&nbsp;&nbsp;&nbsp;名：</span><form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="return page();" ><i class="fa fa-search"></i> 查询</button>
		</div>
	</form:form>
	</div>
	</div>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead><tr><th> <input type="checkbox" class="i-checks"></th><th>公司</th><th>部门</th><th>登录名</th><th>姓名</th><th>电话</th><th>手机</th><%--<th>角色</th> --%></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td>${user.loginName}</td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	
	<input id="saveBtn" type="hidden" onclick="save()">
</body>
</html>