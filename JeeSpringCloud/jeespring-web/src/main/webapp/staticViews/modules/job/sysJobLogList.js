$(document).ready(function() {
	laydate({
		elem: '#beginCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});
	laydate({
		elem: '#endCreateDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	});


});