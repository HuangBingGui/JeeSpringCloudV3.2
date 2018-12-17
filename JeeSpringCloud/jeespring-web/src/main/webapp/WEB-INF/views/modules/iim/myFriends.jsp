<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>

    <title>通讯录</title>
    <meta name="decorator" content="default"/>
    
     <link href="${ctxStatic}/common/css/animate.css" rel="stylesheet">

			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script type="text/javascript">
	
		function search(n,s){
			$("#searchForm").attr("action","${ctx}/iim/contact/index");
			$("#searchForm").submit();
	    	return false;
	    }

	    function deleteFriend(id){

	    	 window.location = "${ctx}/iim/contact/delFriend?id="+id;
	    }
	</script>
</head>

<body class="gray-bg">
       <div class="wrapper wrapper-content animated fadeInRight">
       <div class="row">
       <c:forEach var="user" items="${list}">
     		
            <div class="col-sm-4">
                <div class="contact-box">
                    <a href="#">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" class="img-circle m-t-xs img-responsive" src="<c:out value="${user.photo}" />">
                            </div>
                        </div>
                        <div class="col-sm-7">
                            <h3><strong><c:out value="${user.name}" /></strong></h3>
                            <p><i class="fa fa-map-marker"></i> <c:out value="${user.company.name}" /></p>
                            <address>
                            <strong><c:out value="${user.office.name}" /></strong><br>
                          	<c:out value="${user.email}" /><br>
                            tel:<c:out value="${user.phone}" /><br>
                            <abbr title="Phone">mobile:</abbr> <c:out value="${user.mobile}" />
                        </address>
                        </div>
                          <div class="col-sm-1">
                            <a class="hidden pull-right close-link btn btn-outline  btn-default btn-circle"   onclick="deleteFriend('${user.id}')"><i class="fa fa-times"></i>
                            </a>
                       	  </div>
                        <div class="clearfix"></div>
                    </a>
                </div>
            </div>
    </c:forEach>
            <div class="col-sm-4">
                <div class="contact-box">
                    <a href="#" onclick="searchFriend()">
                        <div class="col-sm-4">
                            <div class="text-center">
                                <img alt="image" style="height:140px;width:140px" class="img-circle m-t-xs img-responsive" src="${ctxStatic}/images/add_user.jpg">
                                <div class="m-t-xs font-bold"></div>
                            </div>
                        </div>
                        <div class="col-sm-8">
                        	<br/>
                        	<br/>
                            <h1><strong>添加好友</strong></h1>
                        </div>
                        <div class="clearfix"></div>
                    </a>
                </div>
            </div>
           
        </div>
    </div>
    <!-- 全局js -->

    <script>
	    $(document).ready(function () {
	        $('.contact-box').each(function () {
	            animationHover(this, 'pulse');
	        });

	    });

	    function searchFriend(){
			top.layer.open({
			    type: 2,  
			    area: ['800px', '500px'],
			    title:"添加好友",
			    name:'friend',
			    content: "${ctx}/iim/contact/searchUsers" ,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			    	 var ids = iframeWin.getSelectedIds();

			    	 if(ids == "-1"){
				    	 return;
			    	 }
			    	 window.location = "${ctx}/iim/contact/addFriend?ids="+ids;
					 top.layer.close(index);//关闭对话框。
				  },
				  cancel: function(index){ 
	    	       }
			}); 
		};
	</script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>