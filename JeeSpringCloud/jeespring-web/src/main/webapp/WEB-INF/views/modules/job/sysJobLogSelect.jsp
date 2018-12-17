<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务调度日志管理</title>
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
			var jobName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("jobName");
			var jobGroup =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("jobGroup");
			var methodName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("methodName");
			var methodParams =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("methodParams");
			var jobMessage =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("jobMessage");
			var status =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("status");
			var exceptionInfo =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("exceptionInfo");
			var createDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("createDate");
			var createBy =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("createBy");
			var updateDate =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateDate");
			var updateBy =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("updateBy");
			var delFlag =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("delFlag");

			var obj= '"id":id';
			if(jobName==undefined) jobName="";
				obj+=',"jobName":"'+jobName+'"';
			if(jobGroup==undefined) jobGroup="";
				obj+=',"jobGroup":"'+jobGroup+'"';
			if(methodName==undefined) methodName="";
				obj+=',"methodName":"'+methodName+'"';
			if(methodParams==undefined) methodParams="";
				obj+=',"methodParams":"'+methodParams+'"';
			if(jobMessage==undefined) jobMessage="";
				obj+=',"jobMessage":"'+jobMessage+'"';
			if(status==undefined) status="";
				obj+=',"status":"'+status+'"';
			if(exceptionInfo==undefined) exceptionInfo="";
				obj+=',"exceptionInfo":"'+exceptionInfo+'"';
			if(createDate==undefined) createDate="";
				obj+=',"createDate":"'+createDate+'"';
			if(createBy==undefined) createBy="";
				obj+=',"createBy":"'+createBy+'"';
			if(updateDate==undefined) updateDate="";
				obj+=',"updateDate":"'+updateDate+'"';
			if(updateBy==undefined) updateBy="";
				obj+=',"updateBy":"'+updateBy+'"';
			if(delFlag==undefined) delFlag="";
				obj+=',"delFlag":"'+delFlag+'"';

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
	<form:form id="searchForm" modelAttribute="sysJobLog" action="${ctx}/job/sysJobLog/" method="post" class="form-inline">
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
				<th  class="sort-column jobName">任务名称</th>
				<th  class="sort-column jobGroup">任务组名</th>
				<th  class="sort-column methodName">任务方法</th>
				<th  class="sort-column methodParams">方法参数</th>
				<th  class="sort-column jobMessage">日志信息</th>
				<th  class="sort-column status">执行状态（0正常 1失败）</th>
				<th  class="sort-column exceptionInfo">异常信息</th>
				<th  class="sort-column createDate">创建时间</th>
				<th  class="sort-column createBy">创建者</th>
				<th  class="sort-column updateDate">修改时间</th>
				<th  class="sort-column updateBy">修改者</th>
				<th  class="sort-column delFlag">删除标记</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysJobLog">
			<tr>
				<td>
				<input type="checkbox" id="${sysJobLog.id}"
					jobName="${sysJobLog.jobName}"
					jobGroup="${sysJobLog.jobGroup}"
					methodName="${sysJobLog.methodName}"
					methodParams="${sysJobLog.methodParams}"
					jobMessage="${sysJobLog.jobMessage}"
					status="${sysJobLog.status}"
					exceptionInfo="${sysJobLog.exceptionInfo}"
					createBy="${sysJobLog.createBy}"
					updateDate="${sysJobLog.updateDate}"
					updateBy="${sysJobLog.updateBy}"
					delFlag="${sysJobLog.delFlag}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看定时任务调度日志', '${ctx}/job/sysJobLog/form?id=${sysJobLog.id}','800px', '500px')">
					${sysJobLog.jobName}
				</a></td>
				<td>
					${sysJobLog.jobGroup}
				</td>
				<td>
					${sysJobLog.methodName}
				</td>
				<td>
					${sysJobLog.methodParams}
				</td>
				<td>
					${sysJobLog.jobMessage}
				</td>
				<td>
					${fns:getDictLabel(sysJobLog.status, 'job_status', '')}
				</td>
				<td>
					${sysJobLog.exceptionInfo}
				</td>
				<td>
					<fmt:formatDate value="${sysJobLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysJobLog.createBy}
				</td>
				<td>
					${sysJobLog.updateDate}
				</td>
				<td>
					${sysJobLog.updateBy}
				</td>
				<td>
					${fns:getDictLabel(sysJobLog.delFlag, 'del_flag', '')}
				</td>
				<td>
					<!--shiro:hasPermission name="job:sysJobLog:view"-->
						<a href="#" onclick="openDialogView('查看定时任务调度日志', '${ctx}/job/sysJobLog/form?id=${sysJobLog.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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