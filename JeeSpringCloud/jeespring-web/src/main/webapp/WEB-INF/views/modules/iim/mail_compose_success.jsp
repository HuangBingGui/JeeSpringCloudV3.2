<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-31j">
    <meta name="decorator" content="default"/>
			    <%@ include file="/WEB-INF/views/include/head.jsp"%>
<title>Insert title here</title>
</head>
<body class="gray-bg">

    <div class="lock-word animated fadeInDown">
    </div>
    <div class="middle-box text-center lockscreen animated fadeInDown">
        <div>
            <div class="m-b-md">
                <img alt="image" style="width:150px;"class="img-circle circle-border" src="${ctxStatic}/images/success.jpg">
            </div>
            <c:if test="${mailCompose.status == '0'}">
            <p>邮件已经保存到草稿箱！</p>
            </c:if>
            <c:if test="${mailCompose.status == '1'}">
            <p>邮件发送成功！</p>
            </c:if>
            
            <form class="m-t" role="form" action="index.html">
                <a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc" class="btn btn-primary block full-width">返回</a>
            </form>
        </div>
    </div>
</body>
</html>