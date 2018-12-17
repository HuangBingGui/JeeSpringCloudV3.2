<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${IMEnable eq 'true'}">
    <!-- 即时聊天插件 -->
    <link href="${ctxStatic}/layer-v2.0/layim/layim.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        var currentId = '${fns:getUser().loginName}';
        var currentName = '${fns:getUser().name}';
        var currentFace ='${fns:getUser().photo}';
        var url="${ctx}";
        var wsServer = 'ws://'+window.document.domain+':8668';

    </script>
    <script src="${ctxStatic}/layer-v2.0/layim/layer.min.js"></script>
    <script src="${ctxStatic}/layer-v2.0/layim/layim.js"></script>
</c:if>