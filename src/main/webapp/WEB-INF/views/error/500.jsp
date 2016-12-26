<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>Error</title>
    <link href="../css/base.css" rel="stylesheet" type="text/css"/>
    <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    <style>
        body, html {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div style="background:#fff;height:100%;width:100%;position:absolute;z-index:-999999">

</div>
<div class="bb">
    <div class="main clearfix pt5 pb5">
        <div class="l"><a href="javascript:void(0);" onclick="showExInfo()" class="pl10">
            <img src="../images/login_05.png" class="v_m"/>
            <span class="v_m"> 点击此处查看详细错误</span></a></div>
    </div>
</div>
<div class="main">
    <img src="../images/500.jpg" style=""/>
    <input type="button" value="返回上一步操作" class="c_p c_f v_m f16 lo_btn ml"
           onclick="window.history.go(-1)"/>
</div>
<span id="ex" style="display:none">
    ${ex}
</span>
</body>
<script type="text/javascript">
    function showExInfo() {
        alert(document.getElementById("ex").innerText);
    }
</script>
</html>
