var locat = (window.location+'').split('/'); 
$(function(){if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

//重置
function gReload(){
	$("#serverUrl").val('');
	$("#requestBody").val('');
	$("#json-field").val('');
	$("#S_TYPE_S").val('');
	$("input.i-checks:first").iCheck('check');
}
$('input.i-checks:radio').on('ifChecked', function(event){
	  $("#S_TYPE").val($(this).attr("value"));
});
//请求类型
function setType(value){
}
function sendSever(){
	if($("#serverUrl").val()==""){
		top.layer.alert('输入请求地址！', {icon: 0});
		$("#serverUrl").focus();
		return false;
	}
	//alert($.md5(paraname+nowtime+',fh,'));
	var startTime = new Date().getTime(); //请求开始时间  毫秒
	$.ajax({
		type: "POST",
		url: locat+'/tools/testInterface/severTest',
    	data: {serverUrl:$("#serverUrl").val(),requestBody:$("#requestBody").val(),requestMethod:$("#S_TYPE").val(),tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" == data.errInfo){
				 top.layer.alert('服务器请求成功！', {icon: 6});
				 var endTime = new Date().getTime();  //请求结束时间  毫秒
				 $("#ctime").text(endTime-startTime);

			 }else{
				 top.layer.alert('服务器请求失败！', {icon: 0});
				 var endTime = new Date().getTime();  //请求结束时间  毫秒
				 $("#ctime").text(endTime-startTime);
			 }
			 $("#json-field").val(data.result);
			 $("#stime").text(data.rTime);
		}
	});
}

function intfBox(){
	var intfB = document.getElementById("json-field");
	var intfBt = document.documentElement.clientHeight;
	intfB .style.height = (intfBt  - 320) + 'px';
}
intfBox();
window.onresize=function(){
	intfBox();
};

//js  日期格式
function date2str(x,y) {
     var z ={y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
     return y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))});
 	};