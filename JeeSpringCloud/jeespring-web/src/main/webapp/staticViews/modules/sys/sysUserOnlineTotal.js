$(document).ready(function() {
	laydate({
		elem: '#beginStartTimestsamp', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#endStartTimestsamp', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#beginLastAccessTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#endLastAccessTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#beginCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#endCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	var name = setInterval(
		function(){
			if($("#run").attr('checked')){
				location.reload();
			}
		}   ,
		//每20秒执行一次
		20000
	);

});

function sumColumnIndex(id,index){
	var sum=0;
	$("table tbody tr").each(function(){
		sum=sum+Number($(this).find("td:eq("+(index-1)+")").html());
	});
	$("#"+id).html(sum);
}

function sumColumn(id,columnName){
	if(columnName==null)
		columnName=id;
	var sum=0;
	$("."+columnName).each(function(){
		sum=sum+Number($(this).html());
	});
	$("#"+id).html(sum);
}