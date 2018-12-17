<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内容管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
	<%@ include file="/WEB-INF/views/include/headCss.jsp" %>
	<%@ include file="/WEB-INF/views/include/headJs.jsp" %>
</head>
<body>
	<div id="content" class="row-fluid row">
		<div class="col-sm-2">
			<iframe id="cmsMenuFrame" name="cmsMenuFrame" src="${ctx}/cms/tree" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
		<!--div id="openClose" class="close">&nbsp;</div-->
		<div class="col-sm-10">
			<iframe id="cmsMainFrame" name="cmsMainFrame" src="${ctx}/cms/article/?category.id=2" style="overflow:visible;"
				scrolling="yes" frameborder="no" width="100%"></iframe>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = "160"; // 左侧窗口大小
		function wSize(){
			var strs=getWindowSize().toString().split(",");
			$("#cmsMenuFrame, #cmsMainFrame, #openClose").height(strs[0]-5);
			$("#right").width($("body").width()-$("#left").width()-$("#openClose").width()-20);
		}
		// 鼠标移动到边界自动弹出左侧菜单
		$("#openClose").mouseover(function(){
			if($(this).hasClass("open")){
				$(this).click();
			}
		});
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>