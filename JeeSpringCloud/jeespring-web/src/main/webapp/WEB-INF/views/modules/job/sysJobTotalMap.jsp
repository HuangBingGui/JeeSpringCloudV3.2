<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:100%; height:100%;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
    <meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
    <title>百度地图API自定义地图</title>
    <!--引用百度地图API-->
    <style type="text/css">
        html,body{margin:0;padding:0;}
        .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
        .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
        .BMapLabel {max-width: none;}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
    <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
    <%@ include file="/WEB-INF/views/include/head.jsp"%>
    <script src="/staticViews/modules/job//sysJobTotal.js" type="text/javascript"></script>
	<link href="/staticViews/modules/job//sysJobTotal.css" rel="stylesheet" />
</head>

<body style="width: 100%; height: 100%;overflow: hidden">
<div class="wrapper wrapper-content" style="padding: 0px 20px 0px 20px;">
<div class="ibox">

<!--查询条件-->
<div class="row">
<div class="col-sm-12"  style="z-index: 1000;background-color: white;">
	<form:form id="searchForm" modelAttribute="sysJob" action="${ctx}/job/sysJob/totalMap" method="post" class="form-inline" style="display:none">
	<div class="form-group">
		<input id="run" type="checkbox" value="true" name="run" checked/>自动刷新
		<form:select path="totalType"  class="form-control m-b">
			<form:option value="" label=""/>
			<form:options items="${fns:getDictList('total_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<span>任务名称：</span>
		<form:input path="jobName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>任务组名：</span>
		<form:input path="jobGroup" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>任务方法：</span>
		<form:input path="methodName" htmlEscape="false" maxlength="500"  class=" form-control input-sm"/>
		<span>方法参数：</span>
		<form:input path="methodParams" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		<span>cron执行表达式：</span>
		<form:input path="cronExpression" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
		<span>计划执行错误策略（0默认 1继续 2等待 3放弃）：</span>
		<form:input path="misfirePolicy" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		<span>状态（0正常 1暂停）：</span>
	<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('job_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		<span>创建者：</span>
		<form:input path="createBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>创建时间：</span>
	<input id="beginCreateDate" name="beginCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJob.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endCreateDate" name="endCreateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJob.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<span>更新者：</span>
		<form:input path="updateBy.id" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		<span>更新时间：</span>
	<input id="beginUpdateDate" name="beginUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJob.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> -
	<input id="endUpdateDate" name="endUpdateDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
		   value="<fmt:formatDate value="${sysJob.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<span>备注信息：</span>
		</div>
		</form:form>
<br/>
		</div>
		</div>

			<!-- 工具栏 -->
	<div class="row">
		<div class="col-sm-12"  style="z-index: 1000;background-color: white;">
			<div class="pull-left">
					<button  class="btn btn-success btn-sm " onclick="$('#searchForm').toggle();$('.fa-chevron').toggle();"  title="检索">
						<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 检索
					</button>
					<button  class="btn btn-success btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			</div>
			<div class="pull-right">
				<div class="btn-group" title="其他">
					<button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" type="button" aria-expanded="false">
						<i class="glyphicon glyphicon-th icon-th"></i>
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li data-type="放大"><a href="javascript:void(0)" onclick="$('body').css({zoom:Number($('body').css('zoom'))+0.1});$('body .echartsEval script').each(function(){eval($(this).html())});">放大</a></li>
						<li data-type="缩小"><a href="javascript:void(0)" onclick="$('body').css({zoom:$('body').css('zoom')-0.1});$('body .echartsEval script').each(function(){eval($(this).html())});">缩小</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
<!--查询条件-->
</div>
</div>

<!--百度地图容器-->
<div style="width:100%;height:100%;position: absolute; bottom: 0px;top: 0px;">
<div style="width:100%;height:100%;border:#ccc solid 1px;" id="dituContent"></div>
</div>
</body>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMarker();//向地图中添加marker
    }

    //创建地图函数：
    function createMap(){
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(113.271431,23.135336);//定义一个中心点坐标
        map.centerAndZoom(point,13);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }

    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }

    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
        map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
        map.addControl(ctrl_sca);
    }
    //标注点数组
    var markerArr = [
<c:forEach items="${list}" var="sysJob">
        {title:"${sysJob.totalDate}<br>统计${sysJob.totalCount}次"
        ,content:""
        ,point:(113.271431+${sysJob.totalCount})+"|"+(23.135336+${sysJob.totalCount}),isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}},
</c:forEach>
	{title:"定时任务调度<br>统计${sumTotalCount}次。"
	,content:""
	,point:"113.271431|23.135336",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}
    ];
    //创建marker
    function addMarker(){
        for(var i=0;i<markerArr.length;i++){
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
            var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
            var iw = createInfoWindow(i);
            var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
            label.setStyle({
                width: "300px",
                color: '#fff',
                borderRadius: "5px",
                textAlign: "center",
                height: "50px",
                lineHeight: "26px"
            });
            marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                borderColor:"#808080",
                color:"#333",
                cursor:"pointer"
            });

            (function(){
                var index = i;
                var _iw = createInfoWindow(i);
                var _marker = marker;
                _marker.addEventListener("click",function(){
                    this.openInfoWindow(_iw);
                });
                _iw.addEventListener("open",function(){
                    _marker.getLabel().hide();
                })
                _iw.addEventListener("close",function(){
                    _marker.getLabel().show();
                })
                label.addEventListener("click",function(){
                    _marker.openInfoWindow(_iw);
                })
                if(!!json.isOpen){
                    label.hide();
                    _marker.openInfoWindow(_iw);
                }
            })()
        }
    }
    //创建InfoWindow
    function createInfoWindow(i){
        var json = markerArr[i];
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
        return iw;
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("http://api.map.baidu.com/lbsapi/creatmap/images/us_cursor.gif", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }

    initMap();//创建和初始化地图
</script>
</html>