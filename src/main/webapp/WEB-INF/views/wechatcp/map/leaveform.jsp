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
    </style>
    <!-- 引入 jQuery 库 -->
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <!-- wechat lib -->
    <script src="/js/weui/weui.min.js" type="text/javascript"></script>
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <title>请假</title>
</head>
<body>

<div data-role="page" id="leaveform">
    <div data-role="header"> <!-- javascript:history.go(-1); rel='external' -->
        <a href="/cp/msg/emp/leave/home" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>员工请假表单</h1>
        <%--<a href="#" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left"
           id="goback2home">主页</a>--%>
    </div>

    <div data-role="main" class="ui-content">
        <div class="page__bd">
            <form method="post" action="/cp/msg/leave/form/submit">
                <label for="fname" class="ui-hidden-accessible">姓名：</label>
                <input type="text" name="name" id="fname" data-clear-btn="true" placeholder="姓名...">
                <label for="date">Date:</label>
                <input name="date" id="date" value="" class="required" type="date">
                <input type="submit" data-inline="true" value="提交">
            </form>
        </div>
    </div>

    <!--BEGIN toast-->
    <div id="toastloadLeaveForm" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">数据玩命加载中，请稍后...</p>
        </div>
    </div>
    <div id="toastLeaveForm" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <div class="icon-box">
                <i class="weui-icon-info weui-icon_msg"></i>
                <div class="icon-box__ctn">
                    <h3 class="icon-box__title">提示</h3>
                    <p class="icon-box__desc" id="toastTextInfoLeaveForm">提示信息...</p>
                </div>
            </div>
        </div>
    </div>


</div>


</body>
</html>


