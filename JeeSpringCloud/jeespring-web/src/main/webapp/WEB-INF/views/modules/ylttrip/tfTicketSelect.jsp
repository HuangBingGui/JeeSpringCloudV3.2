<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginOrderDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endOrderDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
	        laydate({
	            elem: '#beginStateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endStateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
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
			var ticketNo =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("ticketNo");
			var goodsNo =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("goodsNo");
			var goodsItemId =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("goodsItemId");
			var goodsItemName =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("goodsItemName");
			var goodsNum =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("goodsNum");
			var price =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("price");
			var salePrice =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("salePrice");
			var user.id =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("user.id");
			var linkPhone =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("linkPhone");
			var payType =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("payType");
			var checkinCode =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("checkinCode");
			var reserveId =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("reserveId");
			var remark =  $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("remark");

			var obj= '"id":id';
			if(ticketNo==undefined) ticketNo="";
				obj+=',"ticketNo":"'+ticketNo+'"';
			if(goodsNo==undefined) goodsNo="";
				obj+=',"goodsNo":"'+goodsNo+'"';
			if(goodsItemId==undefined) goodsItemId="";
				obj+=',"goodsItemId":"'+goodsItemId+'"';
			if(goodsItemName==undefined) goodsItemName="";
				obj+=',"goodsItemName":"'+goodsItemName+'"';
			if(goodsNum==undefined) goodsNum="";
				obj+=',"goodsNum":"'+goodsNum+'"';
			if(price==undefined) price="";
				obj+=',"price":"'+price+'"';
			if(salePrice==undefined) salePrice="";
				obj+=',"salePrice":"'+salePrice+'"';
			if(user.id==undefined) user.id="";
				obj+=',"user.id":"'+user.id+'"';
			if(linkPhone==undefined) linkPhone="";
				obj+=',"linkPhone":"'+linkPhone+'"';
			if(payType==undefined) payType="";
				obj+=',"payType":"'+payType+'"';
			if(checkinCode==undefined) checkinCode="";
				obj+=',"checkinCode":"'+checkinCode+'"';
			if(reserveId==undefined) reserveId="";
				obj+=',"reserveId":"'+reserveId+'"';
			if(remark==undefined) remark="";
				obj+=',"remark":"'+remark+'"';

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
	<form:form id="searchForm" modelAttribute="tfTicket" action="${ctx}/ylttrip/tfTicket/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>订单编号：</span>
				<form:input path="ticketNo" htmlEscape="false" maxlength="25"  class=" form-control input-sm"/>
			<span>商品编号：</span>
				<form:input path="goodsNo" htmlEscape="false" maxlength="25"  class=" form-control input-sm"/>
			<span>种类编号：</span>
				<form:input path="goodsItemId" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<span>种类名称：</span>
				<form:input path="goodsItemName" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<span>商品数量：</span>
				<form:input path="goodsNum" htmlEscape="false" maxlength="11"  class=" form-control input-sm"/>
			<span>商品单价：</span>
				<form:input path="price" htmlEscape="false"  class=" form-control input-sm"/>
			<span>订单金额：</span>
				<form:input path="salePrice" htmlEscape="false"  class=" form-control input-sm"/>
			<span>下单人：</span>
				<sys:treeselect id="user" name="user.id" value="${tfTicket.user.id}" labelName="user.name" labelValue="${tfTicket.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>下单时间：</span>
				<input id="beginOrderDate" name="beginOrderDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${tfTicket.beginOrderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endOrderDate" name="endOrderDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${tfTicket.endOrderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>订单状态：</span>
				<form:radiobuttons class="i-checks" path="state" items="${fns:getDictList('STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			<span>状态时间：</span>
				<input id="beginStateDate" name="beginStateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${tfTicket.beginStateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endStateDate" name="endStateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${tfTicket.endStateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>客户姓名：</span>
				<form:input path="custName" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>联系电话：</span>
				<form:input path="linkPhone" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>付款方式：</span>
				<form:radiobuttons class="i-checks" path="payType" items="${fns:getDictList('PAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th  class="sort-column ticketNo">订单编号</th>
				<th  class="sort-column goodsNo">商品编号</th>
				<th  class="sort-column goodsItemId">种类编号</th>
				<th  class="sort-column goodsItemName">种类名称</th>
				<th  class="sort-column goodsNum">商品数量</th>
				<th  class="sort-column price">商品单价</th>
				<th  class="sort-column salePrice">订单金额</th>
				<th  class="sort-column user.name">下单人</th>
				<th  class="sort-column linkPhone">联系电话</th>
				<th  class="sort-column payType">付款方式</th>
				<th  class="sort-column checkinCode">入园号</th>
				<th  class="sort-column reserveId">票务系统订单号</th>
				<th  class="sort-column remark">订单备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tfTicket">
			<tr>
				<td>
				<input type="checkbox" id="${tfTicket.id}"
					ticketNo="${tfTicket.ticketNo}"
					goodsNo="${tfTicket.goodsNo}"
					goodsItemId="${tfTicket.goodsItemId}"
					goodsItemName="${tfTicket.goodsItemName}"
					goodsNum="${tfTicket.goodsNum}"
					price="${tfTicket.price}"
					salePrice="${tfTicket.salePrice}"
					user.id="${tfTicket.user.id}"
					linkPhone="${tfTicket.linkPhone}"
					payType="${tfTicket.payType}"
					checkinCode="${tfTicket.checkinCode}"
					reserveId="${tfTicket.reserveId}"
					remark="${tfTicket.remark}"
				class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看订单', '${ctx}/ylttrip/tfTicket/form?id=${tfTicket.id}','800px', '500px')">
					${tfTicket.ticketNo}
				</a></td>
				<td>
					${tfTicket.goodsNo}
				</td>
				<td>
					${tfTicket.goodsItemId}
				</td>
				<td>
					${tfTicket.goodsItemName}
				</td>
				<td>
					${tfTicket.goodsNum}
				</td>
				<td>
					${tfTicket.price}
				</td>
				<td>
					${tfTicket.salePrice}
				</td>
				<td>
					${tfTicket.user.name}
				</td>
				<td>
					${tfTicket.linkPhone}
				</td>
				<td>
					${fns:getDictLabel(tfTicket.payType, 'PAY_TYPE', '')}
				</td>
				<td>
					${tfTicket.checkinCode}
				</td>
				<td>
					${tfTicket.reserveId}
				</td>
				<td>
					${tfTicket.remark}
				</td>
				<td>
					<!--shiro:hasPermission name="ylttrip:tfTicket:view"-->
						<a href="#" onclick="openDialogView('查看订单', '${ctx}/ylttrip/tfTicket/form?id=${tfTicket.id}','800px', '500px')" class="btn btn-info btn-sm" ><i class="fa fa-search-plus"></i> 查看</a>
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