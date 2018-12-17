<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
    <title>收件箱</title>
   	<meta name="decorator" content="default"/>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-content mailbox-content">
                        <div class="file-manager">
                            <a class="btn btn-block btn-success compose-mail" href="${ctx}/iim/mailCompose/sendLetter">写信</a>
                            <div class="space-25"></div>
                            <h5>文件夹</h5>
                            <ul class="folder-list m-b-md" style="padding: 0">
                                <li>
                                    <a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right">${noReadCount}/${mailBoxCount}</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/iim/mailCompose/list?status=1&orderBy=sendtime desc"> <i class="fa fa-envelope-o"></i> 已发送 <span class="label label-info pull-right">${mailComposeCount}</span></a>
                                </li>
                                <!--  等待下个版本升级
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-envelope"></i> 群邮件</a>
                                </li>
                                -->
                                <li>
                                    <a href="${ctx}/iim/mailCompose/list?status=0&orderBy=sendtime desc"> <i class="fa fa-file-text-o"></i> 草稿箱 <span class="label label-danger pull-right">${mailDraftCount}</span>
                                    </a>
                                </li>
                                <!-- 等待下个版本升级 by刘高峰 
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱</a>
                                </li>
                                -->
                            </ul>
                            <h5>分类</h5>
                            <ul class="category-list" style="padding: 0">
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-navy"></i> 工作</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-danger"></i> 文档</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-primary"></i> 社交</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-info"></i> 广告</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                                </li>
                            </ul>

                            <h5 class="tag-title">标签</h5>
                            <ul class="tag-list" style="padding: 0">
                                <li><a href="#"><i class="fa fa-tag"></i> 朋友</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 工作</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 家庭</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 孩子</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 假期</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 音乐</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 照片</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 电影</a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9 animated fadeInRight">
                <div class="mail-box-header">

                    <form:form  id="searchForm" modelAttribute="mailBox" action="${ctx}/iim/mailBox/" method="post" class="pull-right mail-search">
                    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<table:sortColumn  id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"></table:sortColumn><!-- 支持排序 -->
                        <div class="input-group">
                        	<form:input path="mail.title" htmlEscape="false" maxlength="128"  class=" form-control input-sm" placeholder="搜索邮件标题，正文等"/>
                            <div class="input-group-btn">
                                <button id="btnSubmit" type="submit" class="btn btn-success btn-sm ">
                                    搜索
                                </button>
                            </div>
                        </div>
                    </form:form>
                    <h2>
                    收件箱 (总计${mailBoxCount}，未读${noReadCount })
                </h2>
                    <div class="mail-tools tooltip-demo m-t-md">
                        <button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新邮件列表"><i class="fa fa-refresh"></i> 刷新</button>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为已读"><i class="fa fa-eye">标为已读</i>
                        </button>
                        <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为重要"><i class="fa fa-exclamation">标为重要</i>
                        </button>
                        <table:delRow url="${ctx}/iim/mailBox/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			

                    </div>
                </div>
                <div class="mail-box">

                    <table id="contentTable" class="table table-hover table-mail">
                    	<thead> 
                    		<tr>
                    			<th class="check-mail">
	                                    <input type="checkbox" class="i-checks">
	                             </th>
                    			<th  class="sort-column readstatus">状态</th>
                    			<th  class="sort-column sender.name">发件人</th>
                    			<th  class="sort-column title">标题</th>
                    			<th  class="sort-column overview">内容</th>
                    			<th  class="sort-column sendtime">时间</th>
                    			<th>操作</th>
                    		</tr>
                    	</thead>
                        <tbody>
                        
                        	<c:forEach items="${page.list}" var="mailBox">
								<tr>
									<td class="check-mail">
	                                    <input type="checkbox" id="${mailBox.id}" class="i-checks">
	                                </td>
	                                <td>
		                                <a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
			                                <c:if test="${mailBox.readstatus =='0'}">
			                                  <i class=" fa fa-envelope"> 未读</i> 
			                                </c:if>
			                                 <c:if test="${mailBox.readstatus =='1'}">
			                                <i class="fa fa fa-envelope-o btn-white "> 已读 </i>
			                                </c:if>
			                            </a>
	                                </td>
	                                <td class=""><a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
                                		${(fns:getUserById(mailBox.sender)).name}
									</a></td>
                                	<td class="mail-ontact"><a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
                                		
										${mailBox.mail.title}
									</a></td>
									<td class="mail-subject"><a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
										${mailBox.mail.overview}
									</a>
	                                </td>
	                                <td class="mail-date">${fns:formatDateTime(mailBox.sendtime)}</td>
									
									<td>
										<a href="${ctx}/iim/mailBox/delete?id=${mailBox.id}" onclick="return confirmx('确认要删除该站内信吗？', this.href)"   class="btn btn-info btn-xs btn-success"><i class="fa fa-trash"></i> 删除</a>
									</td>
								</tr>
							</c:forEach>
                         
                        </tbody>
                    </table>


                </div>
           
                <div class="btn-group pull-right">
                        	${page }
                        </div>
            </div>
        </div>
    </div>



    <script>
      
        
	   function search(){//查询，页码清零
			$("#pageNo").val(0);
			$("#searchForm").submit();
	   		return false;
	   }

		function reset(){//重置，页码清零
			$("#pageNo").val(0);
			$("#searchForm div.form-group input").val("");
			$("#searchForm div.form-group select").val("");
			$("#searchForm").submit();
	  		return false;
	 	 }
		function sortOrRefresh(){//刷新或者排序，页码不清零
			
			$("#searchForm").submit();
	 		return false;
	 	}
		function page(n,s){//翻页
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			$("span.page-size").text(s);
			return false;
		}
    </script>


</body>

</html>