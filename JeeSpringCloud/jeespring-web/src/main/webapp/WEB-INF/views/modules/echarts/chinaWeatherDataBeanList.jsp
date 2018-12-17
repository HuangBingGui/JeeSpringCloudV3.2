<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
 <div class="tabs-container">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">统计图表</a>
                        </li>
                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">数据列表</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <div class="panel-body">
							                单轴：
								<div id="line_normal"  class="main000"></div>
							    <echarts:line 
							        id="line_normal"
									title="2011年温度对比曲线" 
									subtitle="主要城市的温度对比曲线"
									xAxisData="${xAxisData}" 
									yAxisData="${yAxisData}" 
									xAxisName="预测时间"
									yAxisName="温度(℃)" />
									
								双轴：
								<div id="line_yAxisIndex"  class="main000"></div>
								<echarts:line 
								    id="line_yAxisIndex"
									title="2011年温度对比曲线" 
									subtitle="主要城市的温度对比曲线"
									xAxisData="${xAxisData}" 
									yAxisData="${yAxisData}" 
									xAxisName="预测时间"
									yAxisName="最高温度(℃),最低温度(℃)" 
									yAxisIndex="${yAxisIndex}"/>

                            </div>
                        </div>
                        <div id="tab-2" class="tab-pane">
                            <div class="panel-body">
							   <div class="wrapper wrapper-content">
								<div class="ibox">
								<div class="ibox-title">
									<h5>城市气温列表 </h5>
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
								<form:form id="searchForm" modelAttribute="chinaWeatherDataBean" action="${ctx}/echarts/chinaWeatherDataBean/" method="post" class="form-inline">
									<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
									<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
									<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
									<div class="form-group">
									 </div>	
								</form:form>
								<br/>
								</div>
								</div>
								
								<!-- 工具栏 -->
								<div class="row">
								<div class="col-sm-12">
									<div class="pull-left">
										<shiro:hasPermission name="echarts:chinaWeatherDataBean:add">
											<table:addRow url="${ctx}/echarts/chinaWeatherDataBean/form" title="城市气温"></table:addRow><!-- 增加按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="echarts:chinaWeatherDataBean:edit">
										    <table:editRow url="${ctx}/echarts/chinaWeatherDataBean/form" title="城市气温" id="contentTable"></table:editRow><!-- 编辑按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="echarts:chinaWeatherDataBean:del">
											<table:delRow url="${ctx}/echarts/chinaWeatherDataBean/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="echarts:chinaWeatherDataBean:import">
											<table:importExcel url="${ctx}/echarts/chinaWeatherDataBean/import"></table:importExcel><!-- 导入按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="echarts:chinaWeatherDataBean:export">
								       		<table:exportExcel url="${ctx}/echarts/chinaWeatherDataBean/export"></table:exportExcel><!-- 导出按钮 -->
								       	</shiro:hasPermission>
								       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
									
										</div>
									<div class="pull-right">
										<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
										<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
									</div>
								</div>
								</div>
								
								<!-- 表格 -->
								<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
									<thead>
										<tr>
											<th> <input type="checkbox" class="i-checks"></th>
											<th  class="sort-column datestr">日期</th>
											<th  class="sort-column beijingMaxTemp">北京最高气温</th>
											<th  class="sort-column beijingMinTemp">北京最低气温</th>
											<th  class="sort-column changchunMaxTemp">长春最高气温</th>
											<th  class="sort-column changchunMinTemp">长春最低气温</th>
											<th  class="sort-column shenyangMaxTemp">沈阳最高气温</th>
											<th  class="sort-column shenyangMinTemp">沈阳最低气温</th>
											<th  class="sort-column haerbinMaxTemp">哈尔滨最高气温</th>
											<th  class="sort-column haerbinMinTemp">哈尔滨最低气温</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.list}" var="chinaWeatherDataBean">
										<tr>
											<td> <input type="checkbox" id="${chinaWeatherDataBean.id}" class="i-checks"></td>
											<td><a  href="#" onclick="openDialogView('查看城市气温', '${ctx}/echarts/chinaWeatherDataBean/form?id=${chinaWeatherDataBean.id}','800px', '500px')">
												<fmt:formatDate value="${chinaWeatherDataBean.datestr}" pattern="yyyy-MM-dd "/>
											</a></td>
											<td>
												${chinaWeatherDataBean.beijingMaxTemp}
											</td>
											<td>
												${chinaWeatherDataBean.beijingMinTemp}
											</td>
											<td>
												${chinaWeatherDataBean.changchunMaxTemp}
											</td>
											<td>
												${chinaWeatherDataBean.changchunMinTemp}
											</td>
											<td>
												${chinaWeatherDataBean.shenyangMaxTemp}
											</td>
											<td>
												${chinaWeatherDataBean.shenyangMinTemp}
											</td>
											<td>
												${chinaWeatherDataBean.haerbinMaxTemp}
											</td>
											<td>
												${chinaWeatherDataBean.haerbinMinTemp}
											</td>
											<td>
												<shiro:hasPermission name="echarts:chinaWeatherDataBean:view">
													<a href="#" onclick="openDialogView('查看城市气温', '${ctx}/echarts/chinaWeatherDataBean/form?id=${chinaWeatherDataBean.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
												</shiro:hasPermission>
												<shiro:hasPermission name="echarts:chinaWeatherDataBean:edit">
							    					<a href="#" onclick="openDialog('修改城市气温', '${ctx}/echarts/chinaWeatherDataBean/form?id=${chinaWeatherDataBean.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
							    				</shiro:hasPermission>
							    				<shiro:hasPermission name="echarts:chinaWeatherDataBean:del">
													<a href="${ctx}/echarts/chinaWeatherDataBean/delete?id=${chinaWeatherDataBean.id}" onclick="return confirmx('确认要删除该城市气温吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
												</shiro:hasPermission>
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
                            </div>
                        </div>
                    </div>
                </div>
