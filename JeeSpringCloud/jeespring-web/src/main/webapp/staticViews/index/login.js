if (window.top !== window.self) {
    window.top.location = window.location;
}

$(document).ready(function () {
    readyInfo();
    $("#loginForm").validate({
        rules: {
            validateCode: {remote: "/servlet/validateCodeServlet"}
        },
        messages: {
            username: {required: "请填写用户名!"}, password: {required: "请填写密码!"},
            validateCode: {remote: "验证码不正确!", required: "请填写验证码!"}
        },
        //errorLabelContainer: "#messageBox",
        errorPlacement: function (error, element) {
            error.insertAfter(element);
        }
    });
});

// 如果在框架或在对话框中，则弹出提示并跳转到首页
if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
    alert('未登录或登录超时。请重新登录，谢谢！');
    top.location = "/admin";
}

function readyInfo() {
    $('#vid').hide();
    //$('#loginPage').hide();
    var hplaTtl = [
        "侏罗纪海岸<br>英国南部英吉利海峡"
        , "短耳朵的大兔子<br>肯尼亚，马拉北部保护区"
        , "超质感流线型身躯<br>美国，克里斯特尔里弗"
        , "跃动的蒙德里安<br>美国，纽约"
        , "纯净之地<br>美国，苏必利尔湖"
        , "历史积淀雄厚的城市<br>挪威，特隆赫姆"
        , "牦牛<br>西藏"
        , "金毛<br>昆虫"
        , "岩洞<br>美国岩洞"
        , "彩蛋农场<br>法国农场"
        , "温顺可人的大家伙 <br>美国，林恩运河"
        , "我愿迷失在这阑珊灯火中<br>葡萄牙，辛特拉市"
        , "舒缓湖景<br>中国"
        , "足球界的最高表彰<br>挪威，亨宁斯韦尔"
        , "圣洁之地<br>德国，赖兴瑙岛"
        , "终极旅行目的地<br>南极洲，佩诺拉海峡"
    ];
    var random = Math.floor(Math.random() * (16 - 1 + 1) + 1);
    var loginImgUrl = "../staticViews/index/images/loginImg" + random + ".jpg";
    $(".login-page").css("background-image", "url(" + loginImgUrl + ")");
    $(".login-page").css("background-size", "cover");
    if (hplaTtl.length >= random) {
        $("#hplaTtl").html(hplaTtl[random - 1]);
        $(".search").attr("href", "https://cn.bing.com/search?q=" + hplaTtl[random - 1].replace("<br>", " "));
    }
    //$("#logo").attr("src", "../static/common/login/images/flat-avatar" + Math.floor(Math.random() * (1 - 1 + 1) + 1) + ".png");
    random = Math.floor(Math.random() * (1 - 1 + 1) + 1);
    $("#vid").attr("src", "../staticViews/index/images/flat-avatar" + random + ".mp4");
    //$('#loginPage').show(1000);
}
setTimeout(function () {
    $('#vid').show(888);
}, 3000);