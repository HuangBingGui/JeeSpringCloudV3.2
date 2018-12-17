var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}

$(function () {
// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//左右折叠ibox
$('.collapse-left-link').click(function () {
    var ibox = $(this).closest('div.leftBox');
    var button = $(this).find('i');
    var content = ibox.find('div.leftBox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-left').toggleClass('fa-chevron-right');
    if(button.hasClass('fa-chevron-left')){
    	setTimeout(function () {
            ibox.width("180px");
           // ibox.find('[id^=map-]').resize();
        }, 200);
    }else{
    	
    	setTimeout(function () {
            ibox.width("10px");
           // ibox.find('[id^=map-]').resize();
        }, 200);
    	
    }
  
    
});


//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=3.1" title="返回首页"><i class="fa fa-home"></i></a></div>';　
    $('body').append(gohome);
}
});
//animation.css
function animationHover(element, animation) {
    element = $(element);
    var close = $(element).find(".close-link");
    element.hover(
        function () {
        	close.removeClass("hidden");
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
                close.addClass("hidden");
               
            }, 300);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};