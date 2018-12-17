<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    			    <%@ include file="/WEB-INF/views/include/head.jsp"%>

    <title>查看邮件</title>
   	<meta name="decorator" content="default"/>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-content mailbox-content">
                        <a class="btn btn-block btn-primary compose-mail" href="${ctx}/iim/mailCompose/sendLetter">写信</a>
                            <div class="space-25"></div>
                            <h5>文件夹</h5>
                             <ul class="folder-list m-b-md" style="padding: 0">
                                <li>
                                    <a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc"> <i class="fa fa-inbox "></i> 收件箱 <span class="label label-warning pull-right">${noReadCount}/${mailBoxCount}</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="${ctx}/iim/mailCompose/list?status=1&orderBy=sendtime desc"> <i class="fa fa-envelope-o"></i> 已发送 <span class="label label-info pull-right">${mailComposeCount}</span></a>
                                </li>
                                <!--  等待下个版本升级
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-envelope"></i> 群邮件</a>
                                </li>
                                -->
                                <li>
                                    <a href="${ctx}/iim/mailCompose/list?status=0&orderBy=sendtime desc"> <i class="fa fa-file-text-o"></i> 草稿箱 <span class="label label-danger pull-right">${mailDraftCount}</span>
                                    </a>
                                </li>
                                <!-- 等待下个版本升级 by刘高峰 
                                <li>
                                    <a href="mailbox.html"> <i class="fa fa-trash-o"></i> 垃圾箱</a>
                                </li>
                                -->
                            </ul>
                            <h5>分类</h5>
                            <ul class="category-list" style="padding: 0">
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-navy"></i> 工作</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-danger"></i> 文档</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-primary"></i> 社交</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-info"></i> 广告</a>
                                </li>
                                <li>
                                    <a href="##"> <i class="fa fa-circle text-warning"></i> 客户端</a>
                                </li>
                            </ul>

                            <h5 class="tag-title">标签</h5>
                            <ul class="tag-list" style="padding: 0">
                                <li><a href="#"><i class="fa fa-tag"></i> 朋友</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 工作</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 家庭</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 孩子</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 假期</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 音乐</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 照片</a>
                                </li>
                                <li><a href="#"><i class="fa fa-tag"></i> 电影</a>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            <div class="col-sm-9 animated fadeInRight">
                <div class="mail-box-header">
                    <div class="pull-right tooltip-demo">
                    	<a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="返回"><i class="fa fa-backward"></i> 返回</a>
                        <a href="${ctx}/iim/mailCompose/replyLetter?id=${mailBox.id}" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="回复"><i class="fa fa-reply"></i> 回复</a>
                        <a href="#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="打印邮件"><i class="fa fa-print"></i> </a>
                        <a href="#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为垃圾邮件"><i class="fa fa-trash-o"></i> </a>
                    </div>
                    <h2>
                    查看邮件
                </h2>
                    <div class="mail-tools tooltip-demo m-t-md">


                        <h3>
                        <span class="font-noraml">主题： </span>${mailBox.mail.title }
                    </h3>
                        <h5>
                        <span class="pull-right font-noraml"><fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        <span class="font-noraml">发件人： </span>${(fns:getUserById(mailBox.sender)).name}
                        </h5>
                        <h5>
                        <span class="font-noraml">收件人： </span>${(fns:getUserById(mailBox.receiver)).name}
                    </h5>
                    </div>
                </div>
                <div class="mail-box">


                    <div id="content" class="mail-body">
                        ${mailBox.mail.content}
                    </div>
                    <div class="mail-attachment">
                     
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                        <a class="btn btn-sm btn-white" href="${ctx}/iim/mailCompose/replyLetter?id=${mailBox.id}"><i class="fa fa-reply"></i> 回复</a>
                       <!--   <a class="btn btn-sm btn-white" href="#"><i class="fa fa-arrow-right"></i> 下一封</a>
                        <button title="" data-placement="top" data-toggle="tooltip" type="button" data-original-title="打印这封邮件" class="btn btn-sm btn-white"><i class="fa fa-print"></i> 打印</button>-->
                        <button title="" onclick="return confirmx('确认要删除该站内信吗？', '${ctx}/iim/mailBox/delete?id=${mailBox.id}')"  data-placement="top" data-toggle="tooltip" data-original-title="删除邮件" class="btn btn-sm btn-white"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                    <div class="clearfix"></div>


                </div>
            </div>
        </div>
    </div>


   
    <script>
        $(document).ready(function () {
        	 $("#content").html($("#content").text());
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>

 

</body>

</html>