var locat = (window.location+'').split('/'); 
$(function(){if('tool'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});


//清除空格
String.prototype.trim=function(){
     return this.replace(/(^\s*)|(\s*$)/g,'');
};

//====================上传二维码=================
$(document).ready(function(){
	var str='';
	$("#uploadify1").uploadify({
		'buttonImg'	: 	locat+"/static/img/twoDimensonCode.png",
		'uploader'	:	locat+"/static/uploadify/uploadify.swf",
		'script'    :	locat+"/static/uploadify/uploadFile.jsp;jsessionid="+jsessionid,
		'cancelImg' :	locat+"/static/uploadify/cancel.png",
		'folder'	:	locat+"/uploadFiles/twoDimensionCode",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
		'queueId'	:	"fileQueue",
		'queueSizeLimit'	:	1,//限制上传文件的数量
		//'fileExt'	:	"*.rar,*.zip",
		//'fileDesc'	:	"RAR *.rar",//限制文件类型
		'fileExt'     : '*.jpg;*.gif;*.png',
		'fileDesc'    : 'Please choose(.JPG, .GIF, .PNG)',
		'auto'		:	false,
		'multi'		:	true,//是否允许多文件上传
		'simUploadLimit':	2,//同时运行上传的进程数量
		'buttonText':	"files",
		'scriptData':	{'uploadPath':'/uploadFiles/twoDimensionCode/'},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
		'method'	:	"GET",
		'onComplete':function(event,queueId,fileObj,response,data){
			str = response.trim();//单个上传完毕执行
		},
		'onAllComplete' : function(event,data) {
			//alert(str);	//全部上传完毕执行
			readContent(str);
    	},
    	'onSelect' : function(event, queueId, fileObj){
    		$("#hasTp1").val("ok");
    	}
	});
			
});
//====================上传二维码=================

function uploadTwo(){
	if($("#uploadify1").val()){
		top.layer.alert('请选择二维码！', {icon: 0});
	return false;
	}
	$('#uploadify1').uploadifyUpload();
}	

//去后台解析二维码返回解析内容
function readContent(str){
	$.ajax({
		type: "POST",
		url: locat+'/a/tools/TwoDimensionCodeController/readTwoDimensionCode',
    	data: {imgId:str,tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" == data.result){
				 if('null' == data.readContent || null == data.readContent){
					 top.layer.alert('读取失败，二维码无效！', {icon: 0});
				 }else{
					 $("#readContent").text(data.readContent);
				 }
			 }else{
				 top.layer.alert('后台读取出错！', {icon: 0});
				 return;
			 }
		}
	});
}

//生成二维码
function createTwoD(){
	
	if($("#encoderContent").val()==""){
		top.layer.alert('输入框不能为空！', {icon: 0});
		$("#encoderContent").focus();
		return false;
	}
	$.ajax({
		type: "POST",
		url: locat+'/tools/TwoDimensionCodeController/createTwoDimensionCode.do',
    	data: {encoderContent:$("#encoderContent").val(),tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 
			 if(data.success){
				 $("#encoderImgId").attr("src",data.body.filePath);       
			 }else{
				 top.layer.alert('生成二维码失败！', {icon: 0});
				 return false;
			 }
			 
			 
		}
	});
	return true;
}