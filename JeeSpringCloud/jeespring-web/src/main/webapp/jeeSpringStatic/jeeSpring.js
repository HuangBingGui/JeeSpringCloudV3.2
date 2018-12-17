function include(id, path, file) {
    if (document.getElementById(id) == null) {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i == 0 ? " id=" + id : "") + attr + link + "></" + tag + ">");
        }
    }
}

// 警告对话框
function alertx(mess, closed){
    top.$.jBox.info(mess, '提示', {closed:function(){
        if (typeof closed == 'function') {
            closed();
        }
    }});
    top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
    top.layer.confirm(mess, {icon: 3, title:'系统提示'}, function(index){
        //do something
        if (typeof href == 'function') {
            href();
        }else{
            //resetTip(); //loading();
            location = href;
        }
        top.layer.close(index);
    });
    return false;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
    var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
        options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
            "resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
    window.open(url ,name , options);
}

// 提示输入对话框
function promptx(title, lable, href, closed){
    top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
        title: title, submit: function (v, h, f){
            if (f.txt == '') {
                top.$.jBox.tip("请输入" + lable + "。", 'error');
                return false;
            }
            if (typeof href == 'function') {
                href();
            }else{
                resetTip(); //loading();
                location = href + encodeURIComponent(f.txt);
            }
        },closed:function(){
            if (typeof closed == 'function') {
                closed();
            }
        }});
    return false;
}

// 恢复提示框显示
function resetTip(){
    if(!top.$.jBox) return false;
    top.$.jBox.tip.mess = null;
}

function loading(message){
    if(!message) message="正在提交，请稍等...";
    info(message);
}
function message(){
    if(typeof(toastr)=="undefined") return;
    if(toastr){
        toastr.options = {
            "closeButton": true,
            "debug": false,
            //"positionClass": "toast-top-full-width",
            "positionClass": "toast-bottom-full-width",
            //"positionClass": "toast-bottom-right",
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
    }
    var message=$("#messageBox").html();
    if(!message) return;
    if(message.length==0) return;
    if(message.indexOf("失败")>=0){
        warning(message);
    }else{
        info(message);
    }
    $("#messageBox").hide();
}

function info(message){
    toastr.info(message);
}

function warning(message){
    toastr.warning(message);
}

function success(message){
    toastr.success(message);
}

function error(message){
    toastr.error(message);
}

function clear(){
    toastr.clear();
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
    for (var i=0; i<data.length; i++){
        var row = data[i];
        if (row.value == value){
            return row.label;
        }
    }
    return defaultValue;
}

var ctx = "${ctx}";