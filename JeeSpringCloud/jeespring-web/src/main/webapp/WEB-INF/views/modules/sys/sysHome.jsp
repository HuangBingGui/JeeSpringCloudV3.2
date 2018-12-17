<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="default"/>
    <%@ include file="/WEB-INF/views/include/head.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {
            WinMove();
        });
    </script>
</head>
<body class="gray-bg">
</div>
<div class="row  border-bottom white-bg dashboard-header">
    <div class="col-sm-12">
        <blockquote class="text-info" style="font-size:14px">${indexSysConfig.description}</blockquote>
        <hr>
    </div>
    ${homePageInfomation}
</div>

<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>平台视频介绍</h5>
                    <div style="text-align: center;font-size: 14px;color: #666;">${oaNotify.title}</div><br/>
                </div>
                <div class="ibox-content">
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpringCloud介绍.docx</a><br><br>
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpringCloud介绍功能初级.docx</a><br><br>
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpringCloud中级培训文档.docx</a><br><br>
                    <video id="vid1" loop="loop" width="100%" height="100%" onended=""  muted="muted" autobuffer="autobuffer" preload="auto" oncontextmenu="return false" data-hasaudio=""
                           style="background-color: white;opacity: 1;visibility: visible;height: 100%;width: 100%;object-fit:cover;object-position: center center;"
                           src="../static/common/login/images/flat-avatar1.mp4" controls></video>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>平台视频部署</h5>
                    <div style="text-align: center;font-size: 14px;color: #666;">${oaNotify.title}</div><br/>
                </div>
                <div class="ibox-content">
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpringCloud部署初级.docx</a><br><br>
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpringCloud部署高级.docx</a><br><br>
                    <video id="vid2" loop="loop" width="100%" height="100%" onended="" muted="muted" autobuffer="autobuffer" preload="auto" oncontextmenu="return false" data-hasaudio=""
                           style="background-color: white;opacity: 1;visibility: visible;height: 100%;width: 100%;object-fit:cover;object-position: center center;"
                           src="../static/document/JeeSpring部署初级.mp4" controls></video>
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpring部署初级.mp4</a><br><br>
                    <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud/attach_files" target="_blank">JeeSpring部署高级.mp4</a><br><br>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>平台视频教程</h5>
                    <div style="text-align: center;font-size: 14px;color: #666;">${oaNotify.title}</div><br/>
                </div>
                <div class="ibox-content">
                    <video id="vid3" loop="loop" width="100%" height="100%" onended=""  muted="muted" autobuffer="autobuffer" preload="auto" oncontextmenu="return false" data-hasaudio=""
                           style="background-color: white;opacity: 1;visibility: visible;height: 100%;width: 100%;object-fit:cover;object-position: center center;"
                           src="../static/common/login/images/flat-avatar113.mp4" controls></video>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-4">

            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>公告通知</h5>
                    <div style="text-align: center;font-size: 14px;color: #666;">${oaNotify.title}</div><br/>
                </div>
                <div class="ibox-content">
                    <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        <ol>
                            <c:forEach items="${pageOaNotify.list}" var="oaNotify">
                                <li>${fns:abbr(oaNotify.title,50)}         ${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
                                    <fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></li></br>
                            </c:forEach>
                        </ol>
                        <hr>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>升级日志</h5> <span class="label label-primary">K+</span>
                </div>
                <div class="ibox-content">
                    <div class="panel-body">
                        <div class="panel-group" id="version">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h5 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#version" href="#v1.0"></a><code class="pull-right"></code>
                                    </h5>
                                </div>
                                <div id="v2.0" class="panel-collapse collapse in">
                                    <div class="panel-body">
                                        <ol>
                                            <c:forEach items="${pageOaNotifyLog.list}" var="oaNotify">
                                                <li>${fns:abbr(oaNotify.title,50)}         ${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
                                                    <fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></li></br>
                                            </c:forEach>
                                        </ol>
                                        <hr>
                                        ${indexTopicsShowList.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>技术支持</h5>
                </div>
                <div class="ibox-content">
                    <ol>
                        <c:forEach items="${pageOaNotifyTechnology.list}" var="oaNotify">
                            <li>${fns:abbr(oaNotify.title,50)}         ${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
                                <fmt:formatDate value="${oaNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></li></br>
                        </c:forEach>
                    </ol>
                    <hr>


                </div>
            </div>
         </div>
     </div>
    <div class="row">
        <div class="col-sm-4 ui-sortable">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>JeeSpring 技术特点</h5>
                </div>
                <div class="ibox-content" style="display: block;">
                    <ol>
                        ${homePageTechnical}
                    </ol>
                </div>
            </div>

        </div>
        <div class="col-sm-4 ui-sortable">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>开源授权</h5>
                </div>
                <div class="ibox-content">
                    <ol>
                        ${homePageAuthorization}
                    </ol>
                    <hr>
                </div>
            </div>
        </div>
        <div class="col-sm-4 ui-sortable">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>联系信息</h5>
                </div>
                <div class="ibox-content">
                    ${homePageContactInfo}
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>