$(document).ready(function(){
    getCount();
});

$(function () {
    App.setbasePath("../");
    App.setGlobalImgPath("dist/img/");
    addTabs({
        id: '10008',
        title: '<i class="fa fa-home"></i>首页',
        close: false,
        url: '/admin/home',//
        urlType: "http"
    });
    /*addTabs({
        id: '10009',
        title: '论坛社区',
        close: false,
        url: 'https://jeespring.kf5.com/hc/community/topic/',
        urlType: "http"
    });*/
    /*addTabs({
        id: '10010',
        title: '网站首页',
        close: false,
        url: '/cms',
        urlType: "http"
    });*/
    activeTabByPageId(10008);
})

function getCount(){
    $.ajax({
        type: "GET",
        url: "/rest/redis/getCount",
        success: function(data){
            data=eval(data);
            $("#cancleRedis").html("清除缓存("+data.RESULT+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/redis/getCountShiro",
        success: function(data){
            data=eval(data);
            $("#cancleShiroRedis").html("清除单点登录缓存("+data.RESULT+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/redis/getExpire",
        success: function(data){
            data=eval(data);
            $("#getExpire").html("缓存有效时间("+data.RESULT+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/redis/getExpireShiro",
        success: function(data){
            data=eval(data);
            $("#getExpireShiro").html("单点登录缓存有效时间("+data.RESULT+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/oauth/getApiTimeLimi",
        success: function(data){
            data=eval(data);
            $("#getApiTimeLimi").html("访问次数("+data.MESSAGE+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/oauth/getApiTime",
        success: function(data){
            data=eval(data);
            $("#getApiTime").html("调用次数("+data.MESSAGE+")");
        }
    });
    $.ajax({
        type: "GET",
        url: "/rest/oauth/userOnlineAmount",
        success: function(data){
            data=eval(data);
            $("#userOnlineAmount").html("在线人数("+data.MESSAGE+")");
        }
    });
}
// 清除缓存
function cancleRedis() {
    $.ajax({url:"/rest/redis/removePattern",async:false});
    getCount()
}
function cancleShiroRedis() {
    $.ajax({url:"/rest/redis/removePatternShiroRedis",async:false});
    getCount()
}

function search_menu() {
    //要搜索的值
    var text = $('input[name=q]').val();
    if(text=="") return;
    var $ul = $('.sidebar-menu');
    $ul.find("a.nav-link").each(function () {
        var $a = $(this).css("border", "");
        //判断是否含有要搜索的字符串
        if ($a.children("span.nav-label").text().indexOf(text) >= 0) {
            //如果a标签的父级是隐藏的就展开
            $ul = $a.parents("ul");
            if ($ul.is(":hidden")) {
                $a.parents("ul").prev().click();
            }
            //点击该菜单
            $a.click().css("border", "1px solid #777");
//                return false;
        }
    });
}