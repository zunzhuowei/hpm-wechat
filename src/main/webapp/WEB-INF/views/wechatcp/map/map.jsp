<%--
  Created by IntelliJ IDEA.
  User: Think
  Date: 2016/12/29
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>地图</title>
    <script src="http://api.map.baidu.com/components?ak=C0ZTnTVVGH4aaGZ7rrGBncWjKDXsQ0xo&v=1.0"></script>

</head>

<body>

<!-- 定位组件 -->
<lbs-geo id="geo" city="北京" enable-modified="false"></lbs-geo>
<script>
    // 先获取元素
    var lbsGeo = document.getElementById('geo');
    //监听定位失败事件 geofail
    lbsGeo.addEventListener("geofail",function(evt){
        alert("fail");
    });
    //监听定位成功事件 geosuccess
    lbsGeo.addEventListener("geosuccess",function(evt){
        console.log(evt.detail);
        var address = evt.detail.address;
        var coords = evt.detail.coords;
        var x = coords.lng;
        var y = coords.lat;
        alert("地址："+address);
        alert("地理坐标："+x+','+y);
    });
</script>

</body>
</html>
