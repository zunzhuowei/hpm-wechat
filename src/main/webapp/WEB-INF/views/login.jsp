<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>登陆页</title>
    <link href="/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="/css/style.css" rel="stylesheet" type="text/css"/>
    <style>
        body, html {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body class="ff">

<div class="bb bgf">
    <div class="main clearfix pt5 pb5">
        <span class="l">欢迎登录绩佳软件后台系统</span>
        <div class="r"><a href="#"><img src="/images/login_03.png" class="v_m"/> <span class="v_m">请登录</span></a> <a
                href="#" class="pl10"><img src="/images/login_08.png" class="v_m"/> <span class="v_m">免费注册</span></a> <a
                href="#" class="pl10"><img src="/images/login_05.png" class="v_m"/> <span class="v_m"> 帮助</span></a><span
                class="v_m"> | <a href="#">网站导航</a></span></div>
    </div>
</div>

<div class="bgf">
    <div class="main clearfix pt15 pb15">
        <a href="#" class="l"><img src="/images/index_01.png" class="v_m"/></a>
        <img src="/images/login_14.png" class="v_m r pt15"/>
    </div>
</div>
<div class="l_bgimg"><img src="/images/login_18.png" width="100%" class="v_m"/></div>


<div class="t_c lo_box">
    <form name="loginForm" action="../login/home" method="post">
        <input type="text" class="bod lo_txt v_m l_bg1" placeholder="用户名"/>
        <input type="text" class="bod lo_txt v_m l_bg2 ml" placeholder="密码"/>
        <input type="button" value="登录" class="c_p c_f v_m f16 lo_btn ml" onclick="loginSubmit()"/>
    </form>
</div>
<div class="t_c bgcolor01 pt10 pb10 b_t footer">Copyright © 2015-2020 绩佳软件科技有限公司 版权所有</div>
<script>
    function loginSubmit() {
        loginForm.submit();
    }
</script>
</body>
</html>

