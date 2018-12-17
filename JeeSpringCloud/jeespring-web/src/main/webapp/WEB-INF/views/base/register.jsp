<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html style="overflow: auto;" class=" js csstransforms3d csstransitions csstransformspreserve3d"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${fns:getConfig('productName')} 注册</title>
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
            $("#loginForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/validateLoginName"},
                    mobile: {remote: "${ctx}/sys/user/validateMobile"},
                    validateCode: {remote: "/servlet/validateCodeServlet"}
                },
                messages: {
                    confirmNewPassword: {equalTo: "输入与上面相同的密码!"},
                    ck1: {required: "必须接受用户协议!"},
                    loginName: {remote: "此用户名已经被注册!", required: "用户名不能为空!"},
                    mobile:{remote: "此手机号已经被注册!", required: "手机号不能为空!"},
                    password: {required: "密码不能为空!"},
                    confirmNewPassword: {required: "重复密码不能为空!"},
                    validateCode:{remote: "验证码不正确!", required: "验证码不能为空!"}
                },
                //errorLabelContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    error.insertAfter(element);
                }
            });
        });
        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");
	</script>
</head>
<body id="" class="" style="">
<div class="login-page">
	<div class="row">
		<div class="col-md-4 col-lg-4 col-md-offset-4 col-lg-offset-4">
			<h1>${fns:getConfig('productName')}</h1>
			<h1>用户注册</h1>
			<!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->
			<form id="loginForm" role="form" modelAttribute="user" action="${ctx}/sys/register/registerUser" method="post" novalidate="novalidate" style="background: rgba(255,255,255,.2);border: 1px solid rgba(255,255,255,.3);border-radius: 3px;padding: 30px;">
				<div class="form-content">
					<sys:message content="${message}"/>
					<div class="form-group">
						<input  type="hidden" value="system" name="roleName"><!-- 默认注册用户都是超级管理员 -->
						<input id="tel" name="mobile" type="text" value="${user.mobile }" maxlength="11" minlength="11" class="form-control input-underline input-lg text-muted required isMobile"  placeholder="手机号"/>
					</div>

					<div class="form-group">
						<input id="userId" name="loginName" type="text" value="${user.loginName }" maxlength="20" minlength="3" class="form-control input-underline input-lg required" placeholder="用户名" />
					</div>

					<div class="form-group">
						<input id="newPassword" name="password" type="password" value="${user.password }" maxlength="20" minlength="3"  class="form-control input-underline input-lg required" placeholder="密码" />
					</div>
					<div class="form-group">
						<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="${user.password }" maxlength="20" minlength="3" class="form-control input-underline input-lg required" equalTo="#newPassword" placeholder="重复密码" />
					</div>
					<div class="input-group m-b text-muted validateCode">
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;"/>
					</div>
					<label class="block">
						<input name="ck1" type="checkbox" class="required ace" />
						<span class="lbl">我接受《用户注册协议》<a href="#"></a></span>
						<label id="ck1-error" class="error" for="ck1" style="display: none;">必须接受用户协议</label>
					</label>
				</div>
				<a href="./login" class="btn btn-white btn-outline btn-lg btn-rounded progress-login">返回</a>
				&nbsp;
				<input type="submit" class="btn btn-white btn-outline btn-lg btn-rounded progress-login" value="注册">
				<div  style="padding-top: 50px;">© 2018 All Rights Reserved. <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud"> JeeSpring</a></div>
			</form>
		</div>
	</div>
</div>
</body>
</html>