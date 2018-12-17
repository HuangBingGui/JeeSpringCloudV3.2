<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/30 0030
  Time: 下午 2:54
  To change this template use File | Settings | File Templates.
--%>
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
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
</head>

<body style="width: 100%; height: 100%;overflow: hidden">

<!--查询条件-->
<div class="row" style="margin: 10px;">
    <div class="col-sm-12">
        <form id="searchForm" action="${ctx}/../rest/utils/mapapi/getMapUserCenter" method="post" class="form-inline">
            <div class="form-group">
                <span>用户编号：</span>
                <input name="userId" htmlEscape="false" maxlength="64" value="${sysUserCenter.userId}" class=" form-control input-sm"/>
                <span>用户名称：</span>
                <input name="userName" htmlEscape="false" maxlength="64" value="${sysUserCenter.userName}" class=" form-control input-sm"/>
                <span>用户手机号：</span>
                <input name="userPhone" htmlEscape="false" maxlength="64" value="${sysUserCenter.userPhone}" class=" form-control input-sm"/>
                <span>创建时间：</span>
                <input id="beginCreateDate" name="beginCreateDate" type="text" value="<fmt:formatDate value="${sysUserCenter.beginCreateDate}" pattern="yyyy-MM-dd"/>" maxlength="20" class="laydate-icon form-control layer-date input-sm"
                       value=""/> -
                <input id="endCreateDate" name="endCreateDate" type="text" value="<fmt:formatDate value="${sysUserCenter.endCreateDate}" pattern="yyyy-MM-dd"/>" maxlength="20"  class="laydate-icon form-control layer-date input-sm"
                       value=""/>
                <input type="submit" value="查询" />
            </div>
        </form>
    </div>
</div>

<!--百度地图容器-->
<div style="width:100%;height:100%;border:#ccc solid 1px;" id="dituContent"></div>
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
<c:forEach items="${sysUserCenterList}" var="sysUserCenter">
        {title:"${sysUserCenter.userName}<br>${sysUserCenter.userPhone}|<fmt:formatDate value="${sysUserCenter.createDate}" pattern="yyyy-MM-dd HH:mm"/>",content:"${sysUserCenter.userId}|${sysUserCenter.userPhone}<br>${sysUserCenter.address}",point:"${sysUserCenter.lng}|${sysUserCenter.lat}",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}},
</c:forEach>
        {title:"用户监控<br>日活用户数---${count}次。",content:"",point:"113.271431|23.135336",isOpen:0,icon:{w:21,h:21,l:0,t:0,x:6,lb:5}}
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
                width: "180px",
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
