<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
    <%@ include file="/WEB-INF/views/include/echarts.jsp" %>
    <script src="/staticViews/modules/ylttrip//tfTicketTotal.js" type="text/javascript"></script>
	<link href="/staticViews/modules/ylttrip//tfTicketTotal.css" rel="stylesheet" />
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
        	<div class="box-title"><i class="fa fa-edit"></i>订单管理</div>
        	 <div class="box-tools pull-right">
                <a id="btnSearchView" href="#" title="筛选" class="btn btn-default btn-sm"><i
                        class="fa fa-filter"></i>筛选</a>
                <!-- 工具功能 -->
                <%@ include file="/WEB-INF/views/include/btnGroup.jsp" %>
             </div>
		</div>
	</div>
	<!-- 内容盒子身体 -->
	<div class="box-body">
		<!-- 查询条件 -->
		<form:form id="searchForm" modelAttribute="tfTicket" action="${ctx}/ylttrip/tfTicket/total" method="post" class="form-inline">
			<div class="form-group">
				<input id="run" type="checkbox" value="true" name="run" checked/>自动刷新
				<form:select path="totalType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('total_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
				<div class="form-group">
				<span>订单编号：</span>
				<form:input path="ticketNo" htmlEscape="false" maxlength="25"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>商品编号：</span>
				<form:input path="goodsNo" htmlEscape="false" maxlength="25"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>种类编号：</span>
				<form:input path="goodsItemId" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>种类名称：</span>
				<form:input path="goodsItemName" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>商品数量：</span>
				<form:input path="goodsNum" htmlEscape="false" maxlength="11"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>商品单价：</span>
				<form:input path="price" htmlEscape="false"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>订单金额：</span>
				<form:input path="salePrice" htmlEscape="false"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>下单人：</span>
				<sys:treeselect id="user" name="user.id" value="${tfTicket.user.id}" labelName="user.name" labelValue="${tfTicket.user.name}"
				title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
				</div>
				<div class="form-group">
				<span>下单时间：</span>
				<input id="beginOrderDate" name="beginOrderDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					   value="<fmt:formatDate value="${tfTicket.beginOrderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
				<input id="endOrderDate" name="endOrderDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					   value="<fmt:formatDate value="${tfTicket.endOrderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				</div>
				<div class="form-group">
				<span>订单状态：</span>
				<form:radiobuttons class="i-checks" path="state" items="${fns:getDictList('STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
				<div class="form-group">
				<span>状态时间：</span>
				<input id="beginStateDate" name="beginStateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					   value="<fmt:formatDate value="${tfTicket.beginStateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
				<input id="endStateDate" name="endStateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					   value="<fmt:formatDate value="${tfTicket.endStateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				</div>
				<div class="form-group">
				<span>客户姓名：</span>
				<form:input path="custName" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>联系电话：</span>
				<form:input path="linkPhone" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
				</div>
				<div class="form-group">
				<span>付款方式：</span>
				<form:radiobuttons class="i-checks" path="payType" items="${fns:getDictList('PAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			 <div class="form-group">
				<button id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button>
				<button id="btnReset" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
			</div>
		</form:form>
		<div class="row" style="margin-top: 10px;">
			<div class="col-sm-12 echartsEval">
				<div id="pie"  class="main000"></div>
				<echarts:pie
						id="pie"
						title="订单数量饼图"
						subtitle="订单数量饼图"
						orientData="${orientData}"/>
				<!--div id="pieSumGoodsNum"  class="main000"></div-->
				<!--xxx-echarts:pie
						id="pieSumGoodsNum"
						title="订单商品数量饼图"
						subtitle="订单商品数量饼图"
						orientData="${orientDataSumGoodsNum}"/-->
				<!--div id="pieSumPrice"  class="main000"></div-->
				<!--xxx-echarts:pie
						id="pieSumPrice"
						title="订单商品单价饼图"
						subtitle="订单商品单价饼图"
						orientData="${orientDataSumPrice}"/-->
				<!--div id="pieSumSalePrice"  class="main000"></div-->
				<!--xxx-echarts:pie
						id="pieSumSalePrice"
						title="订单订单金额饼图"
						subtitle="订单订单金额饼图"
						orientData="${orientDataSumSalePrice}"/-->

				<div id="line_normal"  class="main000"></div>
				<echarts:line
				id="line_normal"
				title="订单曲线"
				subtitle="订单曲线"
				xAxisData="${xAxisData}"
				yAxisData="${yAxisData}"
				xAxisName="时间"
				yAxisName="数量" />
			</div>
		</div>
		<!-- 表格 -->
		<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
			<thead>
				<tr>
					<th>时间段</th>
					<th>数量</th>
					<th>商品数量</th>
					<th>商品单价</th>
					<th>订单金额</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${list}" var="tfTicket">
			<tr>
				<td>${tfTicket.totalDate}</td>
				<td style="text-align: right;" class="totalCount">${tfTicket.totalCount}</td>
				<td  style="text-align: right;" class="sumGoodsNum">${tfTicket.sumGoodsNum}</td>
				<td  style="text-align: right;" class="sumPrice">${tfTicket.sumPrice}</td>
				<td  style="text-align: right;" class="sumSalePrice">${tfTicket.sumSalePrice}</td>
			</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr id="totalRow">
					<td>合计：</td>
					<td id="totalCount"  style="text-align: right;"><script>sumColumn("totalCount");</script></td>
					<td  id="sumGoodsNum" style="text-align: right;"><script>sumColumn("sumGoodsNum");</script></td>
					<td  id="sumPrice" style="text-align: right;"><script>sumColumn("sumPrice");</script></td>
					<td  id="sumSalePrice" style="text-align: right;"><script>sumColumn("sumSalePrice");</script></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<!-- 信息-->
<div id="messageBox">${message}</div>
<script src="/staticViews/viewBase.js"></script>
</body>
</head>