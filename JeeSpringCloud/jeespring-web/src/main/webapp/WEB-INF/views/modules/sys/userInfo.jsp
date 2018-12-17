<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>个人信息</title>
    <%@ include file="/WEB-INF/views/include/headMeta.jsp" %>
    <%@ include file="/WEB-INF/views/include/headCss.jsp" %>
    <%@ include file="/WEB-INF/views/include/headJs.jsp" %>
</head>
<body>
<!-- 内容-->
<div class="wrapper">
    <!-- 内容盒子-->
    <div class="box box-main">
        <!-- 内容盒子头部 -->
        <div class="box-header">
            <div class="box-title"><i class="fa fa-edit"></i>
                个人信息
            </div>
        </div>
        <!-- 内容盒子身体 -->
        <div class="box-body">
            <sys:message hideType="1" content="${message}"/>
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人资料</h5>
                        <div class="ibox-tools" style="display: none">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                编辑<i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a id="userImageBtn" data-toggle="modal" data-target="#register">更换头像</a>
                                </li>
                                <li><a id="userInfoBtn" data-toggle="modal" data-target="#register">编辑资料</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-4" style="margin-bottom: 10px;">
                                <img alt="image" class="img-responsive" src="${user.photo }"/>
                            </div>
                            <div class="col-sm-8">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <tbody>
                                        <tr>
                                            <td><strong>姓名</strong></td>
                                            <td>${user.name}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>邮箱</strong></td>
                                            <td>${user.email}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>手机</strong></td>
                                            <td>${user.mobile}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>电话</strong></td>
                                            <td>${user.phone}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>公司</strong></td>
                                            <td>${user.company.name}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>部门</strong></td>
                                            <td>${user.office.name}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>备注</strong></td>
                                            <td>${user.remarks}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <strong>上次登录</strong>
                                    IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate
                                        value="${user.oldLoginDate}" type="both" dateStyle="full"/>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>注册信息</h5>
                        <div class="ibox-tools"  style="display: none">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                编辑<i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a id="userPassWordBtn" data-toggle="modal" data-target="#register">更换密码</a>
                                </li>
                                <li><a href="#" data-toggle="modal" data-target="#register">更换手机号</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <tbody>
                                        <tr>
                                            <td><strong>用户名</strong></td>
                                            <td>${user.loginName}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>注册手机号码</strong></td>
                                            <td>${user.mobile}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>用户角色</strong></td>
                                            <td>${user.roleNames}</td>
                                        </tr>
                                        <tr>
                                            <td><strong>用户类型</strong></td>
                                            <td>${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>

                            </div>
                            <!--div class="col-sm-4">
									<img width="100%" style="max-width:264px;" src="${user.qrCode}">
								</div-->
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {

        $("#userPassWordBtn").click(function () {
            top.layer.open({
                type: 2,
                area: ['600px', '350px'],
                title: "修改密码",
                content: "${ctx}/sys/user/modifyPwd",
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var body = top.layer.getChildFrame('body', index);
                    var inputForm = body.find('#inputForm');
                    var btn = body.find('#btnSubmit');
                    var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
                    inputForm.validate({
                        rules: {},
                        messages: {
                            confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                        },
                        submitHandler: function (form) {
                            loading('正在提交，请稍等...');
                            form.submit();

                        },
                        errorContainer: "#messageBox",
                        errorPlacement: function (error, element) {
                            $("#messageBox").text("输入有误，请先更正。");
                            if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                                error.appendTo(element.parent().parent());
                            } else {
                                error.insertAfter(element);
                            }
                        }
                    });
                    if (inputForm.valid()) {
                        loading("正在提交，请稍等...");
                        inputForm.submit();
                        ////top.layer.close(index);//关闭对话框。
                    } else {
                        return;
                    }


                },
                cancel: function (index) {
                }
            });
        });

        $("#userInfoBtn").click(function () {
            top.layer.open({
                type: 2,
                area: ['600px', '500px'],
                title: "个人信息编辑",
                content: "${ctx}/sys/user/infoEdit",
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var body = top.layer.getChildFrame('body', index);
                    var inputForm = body.find('#inputForm');
                    var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
                    inputForm.validate();
                    if (inputForm.valid()) {
                        loading("正在提交，请稍等...");
                        inputForm.submit();
                    } else {
                        return;
                    }

                    ////top.layer.close(index);//关闭对话框。

                },
                cancel: function (index) {
                }
            });
        });

        $("#userImageBtn").click(function () {
            top.layer.open({
                type: 2,
                area: ['700px', '500px'],
                title: "上传头像",
                content: "${ctx}/sys/user/imageEdit",
                //  btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var body = top.layer.getChildFrame('body', index);
                    var inputForm = body.find('#inputForm');
                    var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
                    inputForm.validate();
                    if (inputForm.valid()) {
                        loading("正在提交，请稍等...");
                        inputForm.submit();
                    } else {
                        return;
                    }

                    ////top.layer.close(index);//关闭对话框。

                },
                cancel: function (index) {
                }
            });
        });

    });
</script>
</body>
</html>