<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <%@ include file="/WEB-INF/views/include/head.jsp"%>
 <title>我的日程</title>
    
    
<style type="text/css">
	#calendar{ margin:40px auto 10px auto;width:70%;height:50%;}
	.fancy{width:450px; height:200px}
	.fancy h3{height:30px; line-height:30px; border-bottom:1px solid #d3d3d3; font-size:14px}
	.fancy form{padding:10px}
	.fancy p{height:28px; line-height:28px; padding:4px; color:#999}
	.input{height:20px; line-height:20px; padding:2px; border:1px solid #d3d3d3; width:100px}
	.btn{-webkit-border-radius: 3px;-moz-border-radius:3px;padding:5px 12px; cursor:pointer}
	.btn_ok{background: #360;border: 1px solid #390;color:#fff}
	.btn_cancel{background:#f0f0f0;border: 1px solid #d3d3d3; color:#666 }
	.btn_del{background:#f90;border: 1px solid #f80; color:#fff }
	.sub_btn{height:32px; line-height:32px; padding-top:6px; border-top:1px solid #f0f0f0; text-align:right; position:relative}
	.sub_btn .del{position:absolute; left:2px}
</style>

<script src='${ctxStatic}/fullcalendar/js/jquery-1.9.1.js'></script>
<script src='${ctxStatic}/fullcalendar/js/jquery-ui.js'></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/fullcalendar/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/fullcalendar/css/fancybox.css">
<script src='${ctxStatic}/fullcalendar/js/jquery.fancybox-1.3.1.pack.js'></script>
<link href="${ctxStatic}/fullcalendar/css/fullcalendar2.css" rel="stylesheet">
<link href="${ctxStatic}/fullcalendar/fullcalendar.print.css" rel="stylesheet">
<script src="${ctxStatic}/fullcalendar/js/fullcalendar.js"></script>
<meta name="decorator" content="default"/>




<!--
	说明：需要整合农历节气和节日，引入fullcalendar.js fullcalendar2.css
	不需要则引入：fullcalendar.min.js fullcalendar.css
-->

<script type="text/javascript">
$(function() {
	//页面加载完初始化日历 
	$('#calendar').fullCalendar({
		//设置日历头部信息
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		firstDay:1,//每行第一天为周一 
        editable: true,//启用拖动 
		events: '${ctx}/iim/myCalendar/findList',
		//点击某一天时促发
		dayClick: function(date, allDay, jsEvent, view) {
			var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');
			$.fancybox({
				'type':'ajax',
				'href':'${ctx}/iim/myCalendar/addform?date='+selDate
			});
    	},
		//单击事件项时触发 
        eventClick: function(calEvent, jsEvent, view) { 
           $.fancybox({ 
                'type':'ajax', 
                'href':'${ctx}/iim/myCalendar/editform?id='+calEvent.id 
           }); 
        },
		
		//拖动事件 
		eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) { 
        	$.post("${ctx}/iim/myCalendar/drag",{id:event.id,daydiff:dayDelta, minudiff:minuteDelta,allday:allDay},function(msg){ 
            	if(msg!=1){ 
                	alert(msg); 
                	revertFunc(); //恢复原状 
            	} 
        	}); 
    	},
		
		//日程事件的缩放
		eventResize: function(event,dayDelta,minuteDelta,revertFunc) { 
    		$.post("${ctx}/iim/myCalendar/resize",{id:event.id,daydiff:dayDelta,minudiff:minuteDelta},function(msg){ 
        		if(msg!=1){ 
            		alert(msg); 
            		revertFunc(); 
        		} 
    		}); 
		},
		
		selectable: true, //允许用户拖动 
		select: function( startDate, endDate, allDay, jsEvent, view ){ 
	    	var start =$.fullCalendar.formatDate(startDate,'yyyy-MM-dd'); 
	    	var end =$.fullCalendar.formatDate(endDate,'yyyy-MM-dd'); 
	    	$.fancybox({ 
	        	'type':'ajax', 
	        	'href':'${ctx}/iim/myCalendar/addform?date='+start+'&end='+end 
	    	}); 
		} 
	});
});
</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">

            <div class="col-sm-11">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>我的日程 </h5>
                    </div>
                    <div class="ibox-content">
                        <div id="calendar"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>


</html>