<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>

</div>
<hr style="margin:20px 0 10px;">
<footer>
	<div class="footer_nav"><a href="${ctx}/guestbook" target="_blank">公共留言</a> | <a href="${ctx}/search" target="_blank">全站搜索</a> | <a href="${ctx}/map-${site.id}${fns:getUrlSuffix()}" target="_blank">站点地图</a> | <a href="mailto:thinkgem@163.com">技术支持</a> | <a href="${pageContext.request.contextPath}${fns:getAdminPath()}" target="_blank">后台管理</a></div>
	<div class="pull-right">${fns:getDate('yyyy年MM月dd日 E')}</div><div class="copyright">${site.copyright}</div>
</footer>
</div> <!-- /container --></html>