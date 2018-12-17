<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>聊天管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
		$(document).ready(function() {
			laydate({
	            elem: '#createDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
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
</head>
<body>
	
	 <div class="wrapper wrapper-content  animated fadeInRight">
	 
	 			 
				<form:form id="searchForm" modelAttribute="chatHistory" action="${ctx}/iim/chatHistory/" method="post" class="form-inline">
					<input type="hidden" name="userid1" value="${chatHistory.userid1 }"/>
					<input type="hidden" name="userid2" value="${chatHistory.userid2 }"/>
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
					<div class="form-group">
						<span>消息内容：</span>
							<form:input path="msg" htmlEscape="false" maxlength="1024"  class=" form-control input-sm"/>
							<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					 </div>	
				</form:form>
				<br>
                <div class="ibox chat-view">



                    <div class="ibox-content">

                        <div class="row">

                            <div class="col-md-12 ">
                                <div class="chat-discussion">
								<c:forEach items="${page.list}" var="chatHistory">
									<c:if test="${fns:getUser().loginName != chatHistory.userid1}">
									<div class="chat-message chat-message-left">
                                        <img class="message-avatar" src="${fns:getByLoginName(chatHistory.userid1).photo}" alt="">
                                        <div class="message">
                                            <a class="message-author" href="#"> ${fns:getByLoginName(chatHistory.userid1).name}</a>
                                            <span class="message-date"> <fmt:formatDate value="${chatHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
                                            <span class="message-content">
											${chatHistory.msg}
                                            </span>
                                        </div>
                                    </div>
                                    </c:if>
                                    <c:if test="${fns:getUser().loginName == chatHistory.userid1}">
									<div class="chat-message chat-message-right">
                                        <img class="message-avatar" src="${fns:getByLoginName(chatHistory.userid1).photo}" alt="">
                                        <div class="message">
                                            <a class="message-author" href="#"> ${fns:getByLoginName(chatHistory.userid1).name}</a>
                                            <span class="message-date"> <fmt:formatDate value="${chatHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </span>
                                            <span class="message-content">
											${chatHistory.msg}
                                            </span>
                                        </div>
                                    </div>
                                    </c:if>
									</c:forEach>
                         
                                </div>

                            </div>
                            <!-- 
                            <div class="col-md-3">
                                <div class="chat-users">


                                    <div class="users-list">
                                        <div class="chat-user">
                                            <img class="chat-avatar" src="img/a4.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">伤城Simple</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <img class="chat-avatar" src="img/a1.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">从未出现过的风景__</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <span class="pull-right label label-primary">在线</span>
                                            <img class="chat-avatar" src="img/a2.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">冬伴花暖</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <span class="pull-right label label-primary">在线</span>
                                            <img class="chat-avatar" src="img/a3.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">ZM敏姑娘	</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <img class="chat-avatar" src="img/a5.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">才越越</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <img class="chat-avatar" src="img/a6.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">时光十年TENSHI</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <img class="chat-avatar" src="img/a2.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">刘顰颖</a>
                                            </div>
                                        </div>
                                        <div class="chat-user">
                                            <span class="pull-right label label-primary">在线</span>
                                            <img class="chat-avatar" src="img/a3.jpg" alt="">
                                            <div class="chat-user-name">
                                                <a href="#">陈泳儿SccBaby</a>
                                            </div>
                                        </div>


                                    </div>

                                </div>
                            </div>
							-->
                        </div>

                    </div>
        	</div>
		 	<table:page page="${page}"></table:page>
    </div>
</body>
</html>