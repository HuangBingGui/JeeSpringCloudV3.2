/*!
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/JeeSpring">JeeSpring</a> All rights reserved..
 * 
 * 通用公共方法
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-4-29
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select").select2();
	}catch(e){
		// blank
	}
	try{
        printGods()
	}catch(e){}
});
function printGods(){
    console.log(
        "--------------- 佛祖保佑 神兽护体 女神助攻 流量冲天 ---------------\n" +
        "                             _ooOoo_                                 \n" +
        "                            o8888888o                                \n" +
        "                            88\" . \"88                              \n" +
        "                            (| ^_^ |)                                \n" +
        "                            O\\  =  /O                               \n" +
        "                         ____/`---'\\____                            \n" +
        "                       .'  \\\\|     |//  `.                         \n" +
        "                      /   \\|||  :  |||//  \\                        \n" +
        "                     /  _||||| -:- |||||-  \\                        \n" +
        "                     |   | \\\\\\  -  /// |   |                      \n" +
        "                     | \\_|  ''\\---/''  |   |                       \n" +
        "                     \\  .-\\__  `-`  ___/-. /                       \n" +
        "                   ___`. .'  /--.--\\  `. . ___                      \n" +
        "                 .\"\" '<  `.___\\_<|>_/___.'  >'\"\".               \n" +
        "               | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |                 \n" +
        "               \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /               \n" +
        "         ========`-.____`-.___\\_____/___.-`____.-'========          \n" +
        "                              `=---='                                \n" +
        "         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^          \n" +
        "            佛祖保佑       永不宕机     永无BUG    流量冲天             \n" +
        "");
    console.log(
        "\n" +
        "　　　　　　　  ┏┓             ┏┓+ +                                    \n" +
        "　　　　　　　┏┛┻━━━━━━━┛┻┓ + +                          \n" +
        "　　　　　　　┃　　　　　　         ┃                                       \n" +
        "　　　　　　　┃　　　━　　　       ┃ ++ + + +                             \n" +
        "　　　　　　 █████━█████  ┃+                              \n" +
        "　　　　　　　┃　　　　　　         ┃ +                                     \n" +
        "　　　　　　　┃　　　┻　　　       ┃                                      \n" +
        "　　　　　　　┃　　　　　　         ┃ + +                                   \n" +
        "　　　　　　　┗━━┓　   ┏━━━━┛                                   \n" +
        "                  ┃　　  ┃                                            \n" +
        "　　　　　　　　　 ┃　　  ┃ + + + +                                  \n" +
        "　　　　　　　　 　┃　　　┃　                                         \n" +
        "　　　　　　　 　　┃　　　┃ + 　　　　神兽护体,流量冲天       \n" +
        "　　　　　　　 　　┃　　　┃          永不宕机,代码无bug                   \n" +
        "　　　　　　　　 　┃　　　┃　　+                                        \n" +
        "　　　　　　　　 　┃　 　 ┗━━━━━┓ + +                                  \n" +
        "　　　　　　　　 　┃ 　　　　　       ┣┓                                       \n" +
        "　　　　　　　　 　┃ 　　　　　       ┏┛                                       \n" +
        "　　　　　　　　 　┗┓┓┏━━━┳┓┏┛ + + + +                           \n" +
        "　　　　　　　　 　  ┃┫┫　    ┃┫┫                                        \n" +
        "　　　　　　　　 　  ┗┻┛　    ┗┻┛+ + + +                                 \n" +
        "");
    console.log(
        "\n" +
        "　　　　　　 ┏┓         ┏┓                                    \n" +
        "          ┏┛┻━━━━━━┛┻┓   \n" +
        "          ┃　　　　　　        ┃   \n" +
        "          ┃　　　━　　　      ┃   \n" +
        "          ┃　┳┛　  ┗┳　    ┃   \n" +
        "          ┃　　　　　　        ┃   \n" +
        "          ┃　　　┻　　　      ┃   \n" +
        "          ┃　　　　　　       ┃   \n" +
        "          ┗━┓　　　┏━━━┛   \n" +
        "              ┃　　　┃   神兽护体 流量冲天   \n" +
        "              ┃　　　┃   永不宕机 代码无BUG！   \n" +
        "              ┃　　　┗━━━━━━━━━┓   \n" +
        "              ┃　　　　　　　           ┣┓   \n" +
        "              ┃　　　　                 ┏┛   \n" +
        "              ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛   \n" +
        "                  ┃ ┫  ┫    ┃  ┫ ┫   \n" +
        "                  ┗━┻━┛   ┗━┻━┛   \n" +
        "");
    console.log(
        "\n" +
        "                       .::::.                                     \n" +
        "                     .::::::::.                                   \n" +
        "                    :::::::::::                                   \n" +
        "                 ..:::::::::::'                                   \n" +
        "              '::::::::::::'                                      \n" +
        "                .::::::::::                                       \n" +
        "           '::::::::::::::..        女神助攻,流量冲天               \n" +
        "                ..::::::::::::.     永不宕机,代码无bug              \n" +
        "              ``::::::::::::::::                                   \n" +
        "               ::::``:::::::::'        .:::.                      \n" +
        "              ::::'   ':::::'       .::::::::.                    \n" +
        "            .::::'      ::::     .:::::::'::::.                   \n" +
        "           .:::'       :::::  .:::::::::' ':::::.                 \n" +
        "          .::'        :::::.:::::::::'      ':::::.               \n" +
        "         .::'         ::::::::::::::'         ``::::.              \n" +
        "     ...:::           ::::::::::::'              ``::.             \n" +
        "    ```` ':.          ':::::::::'                  ::::..           \n" +
        "                       '.:::::'                    ':'````..        \n" +
        "");
    console.log(
        "\n" +
        "           唐伯虎:\n" +
        "                             桃花庵歌                           \n" +
        "                  桃花坞里桃花庵，桃花庵下桃花仙；                \n" +
        "                  桃花仙人种桃树，又摘桃花卖酒钱。                \n" +
        "                  酒醒只在花前坐，酒醉还来花下眠；                \n" +
        "                  半醒半醉日复日，花落花开年复年。                \n" +
        "                  但愿老死花酒间，不愿鞠躬车马前；                \n" +
        "                  车尘马足富者趣，酒盏花枝贫者缘。                \n" +
        "                  若将富贵比贫贱，一在平地一在天；                \n" +
        "                  若将贫贱比车马，他得驱驰我得闲。                \n" +
        "                  别人笑我太疯癫，我笑他人看不穿；                \n" +
        "                  不见五陵豪杰墓，无花无酒锄作田。                \n" +
        "");
    console.log(
        "\n" +
        "           曹操:\n" +
        "                                  短歌行                  \n" +
        "                  对酒当歌，人生几何？譬如朝露，去日苦多。                  \n" +
        "                  概当以慷，忧思难忘。何以解忧？唯有杜康。                  \n" +
        "                  青青子衿，悠悠我心。但为君故，沈吟至今。                  \n" +
        "                  呦呦鹿鸣，食野之苹。我有嘉宾，鼓瑟吹笙。                  \n" +
        "                  明明如月，何时可掇？忧从中来，不可断绝。                  \n" +
        "                  越陌度阡，枉用相存。契阔谈咽，心念旧恩。                  \n" +
        "                  月明星稀，乌鹊南飞。绕树三匝，何枝可依。                  \n" +
        "                  山不厌高，海不厌深，周公吐哺，天下归心。                  \n" +
        "");
    console.log(
        "\n" +
        "          关羽：                                                \n" +
        "                             咏关公                             \n" +
        "                 桃园结义薄云天，偃月青龙刀刃寒。                  \n" +
        "                 一骑绝尘走千里，五关斩将震坤乾。                  \n" +
        "                 忠心报国为梁栋，肝胆护兄铸铁肩。                  \n" +
        "                 一去麦城无复返，英魂庙里化青烟。                  \n" +
        "");
    console.log(
        "\n" +
        "          程序员:                                               \n" +
        "                            程序开发行                            \n" +
        "                  写字楼里写字间，写字间里程序员；                \n" +
        "                  程序人员做开发，又拿程序换活钱。                \n" +
        "                  上班只在网上坐，下班还来网下眠；                \n" +
        "                  奔驰宝马贵者趣，公交自行程序员。                \n" +
        "                  不见满街漂亮妹，哪个归得程序员；                \n" +
        "                  别人笑我忒疯癫，我笑他人看不穿。                \n" +
        "                  年复一年代码圈，精益求精产品圈；                \n" +
        "                  至情之人同成长，缔造和谐至情间。                \n" +
        "                  千锤百炼飞冲天，辉煌有为戏人间；                \n" +
        "                  谈笑风生社会圈，享天福天下归心。                \n" +
        "\n" +
        "          产品专员:征集中                                   \n" +
        "          美工专员:征集中                                   \n" +
        "          UI专员:征集中                                     \n" +
        "          测试专员:征集中                                   \n" +
        "          老董:征集中                                       \n" +
        "          JeeSpring官方QQ群：328910546                                       \n" +
        "          JeeSpring官方QQ群(VIP)：558699173                                       \n" +
        "          JeeSpring官方架构群：464865153                                       \n" +
        "          JeeSpring是官方分布式微服务集群开源框架，使用前端HTML或后端模板引擎+mvvm+spring mvc+spring boot+spring cloud、mybatis、alibaba dubbo 分布式、\n" +
        "          微服务、集群、代码生成（前端界面、底层代码、dubbo、微服务的生成）等核心技术。\n" +
        "          开源中国 https://git.oschina.net/JeeHuangBingGui/JeeSpring\n" +
        "");
}
// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
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

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	if(!top.$.jBox) return false;
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	try {
        top.$.jBox.tip(mess, 'loading', {opacity: 0});
    }catch(e){}
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
			resetTip(); //loading();
			location = href;
		}
	    top.layer.close(index);
	});
	
//	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
//		if(v=='ok'){
//			if (typeof href == 'function') {
//				href();
//			}else{
//				resetTip(); //loading();
//				location = href;
//			}
//		}
//	},{buttonsFocus:1, closed:function(){
//		if (typeof closed == 'function') {
//			closed();
//		}
//	}});
//	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
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

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}


//打开对话框(添加修改)
function openDialog(title,url,width,height,target,type){
	if(type==null){
		window.location=url;
		return;
	}
    if(type=="tap") {
		top.openTabEdit(title,url,width,height);
		return;
    }
    //type==dialog

	top.layer.open({
	    type: 2,  
	    area: [width, height],
	    title: title,
        maxmin: true, //开启最大化最小化按钮
	    content: url ,
	    btn: ['保存', '关闭'],
	    yes: function(index, layero){
	    	 var body = top.layer.getChildFrame('body', index);
	         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	         var inputForm = body.find('#inputForm');
	         var top_iframe;
	         if(target){
	        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
	         }else{
	        	 if(top.getActiveTab)
	        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
	         }
	         if(top_iframe)
	         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
            if(iframeWin.contentWindow.springvm){
                if(iframeWin.contentWindow.springvm.save()){
					top.layer.close(index);//关闭对话框。
					return;
            	}
            }
	        if(iframeWin.contentWindow.doSubmit && iframeWin.contentWindow.doSubmit() ){
				//top.layer.close(index);//关闭对话框。
                setTimeout(function(){
                	top.layer.close(index)
				}, 100);//延时0.1秒，对应360 7.1版本bug
	         }

		  },
		  cancel: function(index){ 
	       }
	}); 	
	
}

//打开对话框(查看)
function openDialogView(title,url,width,height,type){
	if(type=="tap" || type==null) {
        top.openTabView(title, url, width, height);
        return;
    }
	top.layer.open({
	    type: 2,  
	    area: [width, height],
	    title: title,
        maxmin: true, //开启最大化最小化按钮
	    content: url ,
	    btn: ['关闭'],
	    cancel: function(index){ 
	       }
	}); 	
	
}

function search(){//查询，页码清零
	$("#pageNo").val(0);
	$("#searchForm").submit();
		return false;
}

function reset(){//重置，页码清零
	$("#pageNo").val(0);
	$("#searchForm div.form-group input").val("");
	$("#searchForm div.form-group select").val("");
	$("#searchForm").submit();
		return false;
	 }
function sortOrRefresh(){//刷新或者排序，页码不清零
	
	$("#searchForm").submit();
		return false;
}
function page(n,s){//翻页
	try{
        if(springvm!=undefined)
        {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            springvm.search();
            $("span.page-size").text(s);
            return false;
        }
	}catch(e){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		$("span.page-size").text(s);
		return false;
	}
}

