<%--
  Created by IntelliJ IDEA.
  User: Think
  Date: 2016/12/30
  Time: 0:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <style type="text/css">
        body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
        #allmap {height:500px; width: 100%;}
        #control{width:100%;}
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=C0ZTnTVVGH4aaGZ7rrGBncWjKDXsQ0xo"></script>
    <!--加载鼠标绘制工具-->
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <!--加载检索信息窗口-->
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css"/>

    <!-- 引入 jQuery Mobile 样式 -->
    <%--<link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">--%>
    <!-- 引入 jQuery 库 -->
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <!-- 引入 jQuery Mobile 库 -->
    <%--<script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>--%>
    <!-- 引入weui样式 -->
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <title>考勤范围</title>
</head>
<body>
<div id="allmap" style="overflow:hidden;zoom:1;position:relative;">
    <div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>
</div>

<div class="page__bd page__bd_spacing" id="result">
    <a href="javascript:;" class="weui-btn weui-btn_plain-primary" onclick="alert(overlays.length)">考勤范围</a>
    <a href="javascript:;" class="weui-btn weui-btn_primary"
       id="showLoadingToast" onclick="send2server()">考勤范围</a>
    <a href="javascript:;" class="weui-btn weui-btn_warn" onclick="clearOne()" id="showToast">删除选中的考勤范围</a>
</div>

<input type="text" id="del" style="display: none"/>

<!--BEGIN toast-->
<div id="toastload" style="display: inherit;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">地图玩命加载中，请稍后...</p>
    </div>
</div>
<!--end toast-->
<!--BEGIN toast-->
<div id="toast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">已完成</p>
    </div>
</div>
<!--end toast-->
<!-- loading toast -->
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中...</p>
    </div>
</div>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);
    map.enableScrollWheelZoom();

    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    map.addControl(navigationControl);

    var jsonObject = eval(${points});//lng 经度，lat 纬度

    for(var k=0;k<jsonObject.length;k++) {
        var pointsArrays = JSON.parse(jsonObject[k].map);
        var pasePoints = new Array(pointsArrays.length);
        for (var j=0;j<pointsArrays.length;j++) {
            pasePoints[j] = new BMap.Point(parseFloat(pointsArrays[j].lng),parseFloat(pointsArrays[j].lat));
        }
        var polygon = new BMap.Polygon(pasePoints,
                {strokeColor:"red",fillColor:"red", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
        map.addOverlay(polygon);   //增加多边形
        polygon.addEventListener("click", showInfo);
        function showInfo(e){
            var delPoint = JSON.stringify(e.point);
            $('#del').val(delPoint);
            //alert("已选中对象！");
            //alert(e.point.lng + ", " + e.point.lat);
        }
    }

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            //alert('您的位置：'+r.point.lng+','+r.point.lat);
            //alert("point:"+JSON.stringify(r.point));//将对象转成json
            // 创建地理编码实例
            var myGeo = new BMap.Geocoder();//TODO有一个loading需要去掉，已解决，是JqueryMoble.js的问题
            // 根据坐标得到地址描述
            myGeo.getLocation(new BMap.Point(r.point.lng, r.point.lat), function (result) {
                if (result) {
                    $("#toastload").css("display", "none");
                }
            });
        }
        else {
            alert('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true});

    function clearOne() {
        var point = $('#del').val();
        alert(point);//TODO ajax 传到后台判断点在哪里，进行删除考勤范围。
    }

</script>
</body>
</html>
