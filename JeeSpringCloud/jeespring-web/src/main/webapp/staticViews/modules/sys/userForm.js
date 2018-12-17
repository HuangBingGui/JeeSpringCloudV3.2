function validateFormPage(){
    $("#no").focus();
    jeeSpring.inputForm = $("#inputForm").validate({
        rules: {
            loginName: {remote: ctx+"/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent($("#loginName").val())}//设置了远程验证，在初始化时必须预先调用一次。
        },
        messages: {
            loginName: {remote: "用户登录名已存在"},
            confirmNewPassword: {equalTo: "输入与上面相同的密码"}
        },
        submitHandler: function(form){
            //loading('正在提交，请稍等...');
            form.submit();
        },
        errorContainer: "#messageBox",
        errorPlacement: function(error, element) {
            //$("#messageBox").text("输入有误，请先更正。");
            if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
    //在ready函数中预先调用一次远程校验函数。
    //否则打开修改对话框，不做任何更改直接submit,这时再触发远程校验，耗时较长，
    //submit函数在等待远程校验结果然后再提交，而layer对话框不会阻塞会直接关闭同时会销毁表单，因此submit没有提交就被销毁了导致提交表单失败。
    $("#inputForm").validate().element($("#loginName"));
}
