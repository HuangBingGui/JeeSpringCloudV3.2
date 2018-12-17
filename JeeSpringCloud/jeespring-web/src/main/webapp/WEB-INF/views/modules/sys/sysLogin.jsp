<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html style="overflow: auto;" class=" js csstransforms3d csstransitions csstransformspreserve3d"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="author" content="http://www.jeespring.org/">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=9,IE=10">
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-store">
	<!-- 移动端禁止缩放事件 -->
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
	<!-- 设置浏览器图标 -->
	<link rel="shortcut icon" href="../static/favicon.ico">
	<link rel="stylesheet" id="theme" href="${ctxStatic}/common/login/app-midnight-blue.css">
	<meta name="decorator" content="ani">
	<style>
		/* Validation */
		label.error {
			color: #cc5965;
			display: inline-block;
			margin-left: 5px;
		}
	</style>
	<script type="text/javascript">
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
			$(document).ready(function() {
                readyInfo();
				$("#loginForm").validate({
					rules: {
						validateCode: {remote: "/servlet/validateCodeServlet"}
					},
					messages: {
						username: {required: "请填写用户名!"},password: {required: "请填写密码!"},
						validateCode: {remote: "验证码不正确!", required: "请填写验证码!"}
					},
					//errorLabelContainer: "#messageBox",
					errorPlacement: function(error, element) {
                        error.insertAfter(element);
					}
				});
			});
			// 如果在框架或在对话框中，则弹出提示并跳转到首页
			if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
				alert('未登录或登录超时。请重新登录，谢谢！');
				top.location = "/admin";
			}

			function readyInfo(){
                $('#vid').hide();
                //$('#loginPage').hide();
                var loginImgUrl="${loginImgUrl}";
                var hplaTtl=[
                    "侏罗纪海岸<br>英国南部英吉利海峡"
                    ,"短耳朵的大兔子<br>肯尼亚，马拉北部保护区"
                    ,"超质感流线型身躯<br>美国，克里斯特尔里弗"
                    ,"跃动的蒙德里安<br>美国，纽约"
                    ,"纯净之地<br>美国，苏必利尔湖"
                    ,"历史积淀雄厚的城市<br>挪威，特隆赫姆"
                    ,"牦牛<br>西藏"
                    ,"金毛<br>昆虫"
                    ,"岩洞<br>美国岩洞"
                    ,"彩蛋农场<br>法国农场"
                    ,"温顺可人的大家伙 <br>美国，林恩运河"
                    ,"我愿迷失在这阑珊灯火中<br>葡萄牙，辛特拉市"
                    ,"舒缓湖景<br>中国"
                    ,"足球界的最高表彰<br>挪威，亨宁斯韦尔"
                    ,"圣洁之地<br>德国，赖兴瑙岛"
                    ,"终极旅行目的地<br>南极洲，佩诺拉海峡"
                ];
                var random=1;
                if(loginImgUrl==""){
                    random=Math.floor(Math.random() * (16 - 1 + 1) + 1);
                    loginImgUrl="../static/common/login/images/loginImg"+random+".jpg";
                }
                $(".login-page").css("background-image","url("+loginImgUrl+")");
                $(".login-page").css("background-size","cover");
                if(hplaTtl.length >= random){
                    $("#hplaTtl").html(hplaTtl[random-1]);
                    $(".search").attr("href","https://cn.bing.com/search?q="+hplaTtl[random-1].replace("<br>"," "));
                }
                $("#logo").attr("src","../static/common/login/images/flat-avatar"+Math.floor(Math.random() * (4 - 1 + 1) + 1)+".png");
                //random=Math.floor(Math.random() * (4 - 1 + 1) + 1);
                random=1;
                $("#vid").attr("src","../static/common/login/images/flat-avatar"+random+".mp4");
                //$('#loginPage').show(1000);
			}
            setTimeout(function(){
                $('#vid').show(888);
            },2000);
	</script>
</head>
<body id="" class="" style="">
		<div class="login-page" style="overflow: hidden;padding:0px;background-position: center;">
			<video id="vid" width="100%" height="100%" onended="setTimeout(function(){$('#vid').hide(1000)},3000);" autoplay="autoplay" muted="muted" autobuffer="autobuffer" preload="auto" oncontextmenu="return false" data-hasaudio=""
				   style="display:none;background-color: white;opacity: 1;visibility: visible;position: absolute;top: 0px;bottom: 0px;left: 0px;right: 0px;height: 100%;width: 100%;object-fit:cover;object-position: center center;"
				   src="../static/common/login/images/flat-avatar1.mp4"></video>
			<div id="menu" style="z-index: 10000;position: absolute;top: 0px;left: 0px;border-radius: 3px;padding: 10px;width: 100%;margin: auto 50px;">
				<a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud" style="float: left;color: #fff;" class="btn">介绍</a>
				<a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis/pages" style="float: left;color: #fff;" class="btn">在线文档</a>
				<a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" style="float: left;color: #fff;" class="btn">帮助</a>
			</div>
		<div class="row" id="loginPage" style="padding: 3em;">
			<div class="col-md-4 col-lg-4 col-md-offset-4 col-lg-offset-4">
				<img id="logo" class="img-circle hidden-xs" src="../static/common/login/images/flat-avatar.png" style="width: 150px;height: 150px;">
				<h1>${fns:getConfig('productName')}-${systemMode}-${version}</h1>
				<sys:message content="${message}"/>
				<!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->
				<form id="loginForm" role="form" action="${ctx}/login" method="post" novalidate="novalidate" style="background: rgba(255,255,255,.2);border: 1px solid rgba(255,255,255,.3);border-radius: 3px;padding: 30px;">
					<div class="form-content" style="padding:0px">

						<div class="form-group">
							<input type="text" id="username" name="username" class="form-control input-underline input-lg required" value="admin" placeholder="用户名" >
						</div>

						<div class="form-group">
							<input type="password" id="password" name="password" value="admin" class="form-control input-underline input-lg required" placeholder="密码" >
						</div>
						<c:if test="${validateCode == 'true'}">
							<div class="input-group m-b text-muted validateCode">
								<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;"/>
							</div>
						</c:if>
						<label class="inline">
								<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
								<span class="lbl"> 记住我</span>
						</label>
					</div>
					<a href="${ctx}/register" class="btn btn-white btn-outline btn-lg btn-rounded progress-login">注册</a>
					&nbsp;
					<input type="submit" class="btn btn-white btn-outline btn-lg btn-rounded progress-login" value="登录" >
					<div style="padding-top: 20px;">© 2018 All Rights Reserved. <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud"> JeeSpring</a></div>
				</form>
			</div>			
		</div>

		<div id="hplaT" class="hidden-xs" style="position: absolute;bottom: 0px;right: 0px;/* background: rgba(255,255,255,.2); */border: 1px solid rgba(255,255,255,.3);border-radius: 3px;padding: 10px;width: 30%;">
			<a class="search" target="_blank" href="https://cn.bing.com/search?q=jeespring" style="color:#fff;text-decoration:none;"><div id="hplaTtl"></div></a>
			<input type="button" class="btn btn-white btn-outline btn-rounded" value="刷新" style="float: right;" onclick="readyInfo()">
			<input type="button" class="btn btn-white btn-outline btn-rounded" value="播放" style="float: right;" onclick="$('#vid').show(1000);document.getElementById('vid').play()">
			<input type="button" class="btn btn-white btn-outline btn-rounded" value="停止" style="float: right;" onclick="$('#vid').hide(1000);document.getElementById('vid').pause()">
			<a class="search btn btn-white btn-outline btn-rounded" style="float: right;" href="https://cn.bing.com/search?q=jeespring">搜索</a>
			<input type="button" class="btn btn-white btn-outline btn-rounded" value="显示" style="float: left;" onclick="$('#loginPage').show(1000);">
			<input type="button" class="btn btn-white btn-outline btn-rounded" value="隐藏" style="float: left;" onclick="$('#loginPage').hide(1000);">

		</div>
	</div>
</body>
</html>