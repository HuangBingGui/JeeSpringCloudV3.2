<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html style="overflow: auto;" class=" js csstransforms3d csstransitions csstransformspreserve3d">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${fns:getConfig('productName')} 登录</title>
    <meta name="author" content="http://www.jeespring.org/">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=9,IE=10">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta name="decorator" content="ani">
    <!-- 移动端禁止缩放事件 -->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <script src="/jeeSpringStatic/plugs/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="/jeeSpringStatic/plugs/jquery-validation/1.14.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="/jeeSpringStatic/plugs/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
    <!-- 设置浏览器图标 -->
    <link rel="shortcut icon" href="../static/favicon.ico">
    <link rel="stylesheet" id="theme" href="/staticViews/index/app-midnight-blue.css">
    <script src="/staticViews/index/login.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/staticViews/index/login.css">
</head>
<body>
<div class="login-page">
    <video id="vid" width="100%" height="100%" onended="setTimeout(function(){$('#vid').hide(1000)},3000);"
           autoplay="autoplay" muted="muted" autobuffer="autobuffer" preload="auto" oncontextmenu="return false"
           data-hasaudio=""></video>
    <div id="menu">
        <a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud" style="float: left;color: #fff;"
           class="btn">介绍</a>
        <a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/wikis/pages"
           style="float: left;color: #fff;" class="btn">在线文档</a>
        <a target="_blank" href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files"
           style="float: left;color: #fff;" class="btn">帮助</a>
    </div>
    <div class="row" id="loginPage" style="padding: 3em;">
        <div class="col-md-4 col-lg-4 col-md-offset-4 col-lg-offset-4">
            <img id="logo" class="img-circle hidden-xs" src="../staticViews/index/images/flat-avatar1.png">
            <h1>${fns:getConfig('productName')}-${systemMode}-${version}</h1>
            <sys:message content="${message}"/>
            <!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->
            <form id="loginForm" role="form" action="${ctx}/login" method="post" novalidate="novalidate">
                <div class="form-content" style="padding:0px">
                    <div class="form-group">
                        <input type="text" id="username" name="username"
                               class="form-control input-underline input-lg required" value="admin" placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <input type="password" id="password" name="password" value="admin"
                               class="form-control input-underline input-lg required" placeholder="密码">
                    </div>
                    <c:if test="${validateCode == 'true'}">
                        <div class="input-group m-b text-muted validateCode">
                            <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;"/>
                        </div>
                    </c:if>
                    <label class="inline">
                        <input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}
                               class="ace"/>
                        <span class="lbl"> 记住我</span>
                    </label>
                </div>
                <a href="${ctx}/register" class="btn btn-white btn-outline btn-lg btn-rounded progress-login">注册</a>
                &nbsp;
                <input type="submit" class="btn btn-white btn-outline btn-lg btn-rounded progress-login" value="登录">
                <div style="padding-top: 20px;">© 2018 All Rights Reserved. <a
                        href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud" style="color:white;"> JeeSpring</a>
                </div>
            </form>
        </div>
    </div>
    <div id="hplaT" class="hidden-xs">
        <a class="search" target="_blank" href="https://cn.bing.com/search?q=jeespring"
           style="color:#fff;text-decoration:none;">
            <div id="hplaTtl"></div>
        </a>
        <input type="button" class="btn btn-white btn-outline btn-rounded" value="刷新" style="float: right;"
               onclick="readyInfo()">
        <input type="button" class="btn btn-white btn-outline btn-rounded" value="播放" style="float: right;"
               onclick="$('#vid').show(1000);document.getElementById('vid').play()">
        <input type="button" class="btn btn-white btn-outline btn-rounded" value="停止" style="float: right;"
               onclick="$('#vid').hide(1000);document.getElementById('vid').pause()">
        <a class="search btn btn-white btn-outline btn-rounded" style="float: right;"
           href="https://cn.bing.com/search?q=jeespring">搜索</a>
        <input type="button" class="btn btn-white btn-outline btn-rounded" value="显示" style="float: left;"
               onclick="$('#loginPage').show(1000);">
        <input type="button" class="btn btn-white btn-outline btn-rounded" value="隐藏" style="float: left;"
               onclick="$('#loginPage').hide(1000);">
    </div>
</div>
<c:if test="${systemMode eq '演示版'}">
    <iframe src="https://gitee.com/JeeHuangBingGui/jeeSpringCloud" style="height:50%;width:50%;display:none"></iframe>
    <iframe src="https://www.oschina.net/p/jeeSpringCloud" style="height:50%;width:50%;display:none"></iframe>
</c:if>
</body>
</html>