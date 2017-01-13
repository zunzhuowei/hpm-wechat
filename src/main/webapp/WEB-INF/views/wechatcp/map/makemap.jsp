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
        body, html {
            width: 100%;
            height: 100%;
            margin: 0;
            font-family: "微软雅黑";
        }

        #allmap {
            width: 100%;
            height: 500px;
            overflow: hidden;
        }

        #result {
            width: 100%;
            font-size: 12px;
        }

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
    <title>鼠标绘制考勤范围</title>
</head>
<body>
<div id="allmap" style="overflow:hidden;zoom:1;position:relative;">
    <div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>
</div>

<div class="page__bd page__bd_spacing" id="result">
    <a href="javascript:;" class="weui-btn weui-btn_plain-primary" onclick="alert(overlays.length)">查看已经划范围个数</a>
    <a href="javascript:;" class="weui-btn weui-btn_primary"
       id="showLoadingToast" onclick="send2server()">设置已划范围为考勤范围</a>
    <a href="javascript:;" class="weui-btn weui-btn_warn" onclick="clearAll()" id="showToast">清除所有已划范围</a>
</div>

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
    var map = new BMap.Map('map');
    var poi = new BMap.Point(116.307852, 40.057031);
    map.centerAndZoom(poi, 18);
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

    //获取当前位置
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);

            //进来的时候给一个加载中屏蔽罩
            $('#toastload').css("display","none");
        }
        else {
            alert('failed'+this.getStatus());
        }
    },{enableHighAccuracy: true});


    var overlays = [];
    var overlaycomplete = function (e) {
        overlays.push(e.overlay);
    };
    var styleOptions = {
        strokeColor: "red",    //边线颜色。
        fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
    };
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5) //偏离值
        },
        circleOptions: styleOptions, //圆的样式
        polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
    });
    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);

    function clearAll() {
        if (!confirm("您确定要清除所有已绘制的考勤范围吗？")){
            return;
        }
        for (var i = 0; i < overlays.length; i++) {
            map.removeOverlay(overlays[i]);
        }
        overlays.length = 0;

        //显示已完成屏蔽罩
        var $toast = $('#toast');
        if ($toast.css('display') != 'none') return;
        $toast.fadeIn(100);
        setTimeout(function () {
            $toast.fadeOut(100);
        }, 1000);
    }

    //发送到服务器
    function send2server() {
        if (overlays.length < 1){
            alert("请绘制考勤范围！");
            return;
        }
        if (!confirm("你确定使用已划范围为考勤范围？\n")){
            return;
        }

        $('#loadingToast').css("display","inherit");//屏蔽罩

        var result = "设置刷卡签到范围";
        var count = 0;
        var err = 0;
        for (var i = 0; i < overlays.length; i++) {
            try {//除掉Marker。
                var o = overlays[i].getIcon();
            } catch (e) {
                var rangeArr = overlays[i].getPath();
                var json = JSON.stringify(rangeArr);
                $.ajax({
                    type: "POST",
                    url: "/cp/msg/get/points",
                    cache: false,
                    data: {
                        overlays: json,
                        A0100:"${user.A0100}",
                        desc:"默认描述"
                    },
                    success: function (data, status) {//成功！
                        $('#loadingToast').css("display","none");
                        if (count == 0 && err == 0) {
                            count++;
                            alert(result + "成功！");
                        }
                        data = $.trim(data); //去掉前后空格
                        //alert(data);
                    },
                    error: function (data, status) {//失败！
                        err++;
                        result = result + "失败！";
                        data = $.trim(data); //去掉前后空格
                        //alert(data);
                    }
                });
            }
        }
    }

    // loading
  /*  $(function(){
        var $loadingToast = $('#loadingToast');
        $('#showLoadingToast').on('click', function(){
            if ($loadingToast.css('display') != 'none') return;

            $loadingToast.fadeIn(100);
            setTimeout(function () {
                $loadingToast.fadeOut(100);
            }, 4000);
        });
    });*/

</script>
</body>
</html>
