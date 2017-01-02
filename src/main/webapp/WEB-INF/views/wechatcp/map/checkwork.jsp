<%--
  Created by IntelliJ IDEA.
  User: Think
  Date: 2016/12/29
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
            height: 400px;
            overflow: hidden;
            margin: 0;
            font-family: "微软雅黑";
        }
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=C0ZTnTVVGH4aaGZ7rrGBncWjKDXsQ0xo"></script>
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <!-- 引入 jQuery 库 -->
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <title>正在定位用于签到...</title>
</head>
<body>
<div id="allmap">
</div>

<!--BEGIN toast-->
<div id="toastload" style="display: inherit;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">地图玩命加载中，请稍后...</p>
    </div>
</div>
<!--BEGIN toast-->
<div id="toastcheck" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">签到中，请稍后...</p>
    </div>
</div>

<div id="toast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">已完成</p>
    </div>
</div>

<div class="weui-btn-area">
    <a class="weui-btn weui-btn_primary" href="javascript:" onclick="checkword()" style="display: none;"
       id="showTooltips">签到</a>
</div>

</body>
</html>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 18);//初始地点，16表示放大级别
    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    var address ;
    var pointJson ;
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
                    address = result.address;
                    pointJson = JSON.stringify(r.point);
                    //alert(result.address);
                    $("#showTooltips").css("display","inherit");//加载完成之后，显示签到按钮

                    //进来的时候给一个加载中屏蔽罩
                    $('#toastload').css("display","none");
                }
            });
        }
        else {
            alert('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true});
    //关于状态码
    //BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
    //BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
    //BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
    //BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
    //BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
    //BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
    //BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
    //BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
    //BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)

    //签到
    function checkword() {//toastcheck
        $("#toastcheck").css("display", "inherit");

        $.ajax({
            type: "POST",
            url: "/cp/msg/ckeck/work",
            cache: false,
            data: {
                address: address,
                pointJson: pointJson

            },
            success: function (data, status) {//成功！
                //TODO 如果是范围内，显示已完成；范围外跳转到选择不在范围内的原因。
                //显示已完成屏蔽罩
                $("#toastcheck").css("display", "none");

                var $toast = $('#toast');
                if ($toast.css('display') != 'none') return;
                $toast.fadeIn(100);
                setTimeout(function () {
                    $toast.fadeOut(100);
                }, 1500);
            },
            error: function (data, status) {//失败！
                alert(data);
            }
        });
    }
</script>

