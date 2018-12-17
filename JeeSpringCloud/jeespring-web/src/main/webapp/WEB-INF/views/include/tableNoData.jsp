<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${fn:length(page.list) eq 0}">
    <img class="img-circle" src="/static/common/login/images/flat-avatar3.png">请录入相应信息！
</c:if>