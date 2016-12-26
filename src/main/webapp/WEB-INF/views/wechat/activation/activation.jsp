<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>绑定hr</title>
    <script src="../js/jquery-1.8.3.min.js" type="text/javascript"></script>
   <%-- <script src="../marry/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../marry/css/bootstrap.min.css"  rel="stylesheet" type="text/css">--%>
    <link href="../css/weui.css" rel="stylesheet" type="text/css"/>
    <style>
       .my_title{
           font-size: 24px;
           line-height: 1.41176471;
           color: #990508;
           text-align: center;
       }
    </style>
</head>
<body>

<div class="page">
    <div class="weui-cells__title my_title">微信与HR系统绑定</div>

    <div class="weui-cells">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">微信OpenID:</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" disabled="disabled" type="text" id="openId"
                       placeholder="${snsUserInfo.openId}" value="${snsUserInfo.openId}"/>
            </div>
        </div>
    </div>
    <div class="weui-cells">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">Hr账号:</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text" id="account" placeholder="请输入Hr账号"/>
            </div>
        </div>
    </div>
    <div class="weui-cells">
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">Hr密码:</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text" id="password" placeholder="请输入Hr密码"/>
            </div>
        </div>
    </div>

    <div class="weui-btn-area">
        <a class="weui-btn weui-btn_primary" href="javascript:" onclick="submit()" id="showTooltips">确定绑定</a>
    </div>
    <div class="weui-footer weui-footer_fixed-bottom">
        <p class="weui-footer__links">
            <a class="weui-footer__link" href="javascript:home();">Keega首页</a>
        </p>
        <p class="weui-footer__text">Copyright © 2008-2016 weui.io</p>
    </div>
</div>

</div>
<%--<script type="text/javascript">--%>
<script>

    var count = 0;

    function submit() {
        var openId = document.getElementById("openId").value;
        var account = document.getElementById("account").value;
        var password = document.getElementById("password").value;
        var o = openId.length == 0 || /^\s+$/g.test(openId);
        var a = account.length == 0 || /^\s+$/g.test(account);
        if (o){
            alert("openId不能为空！");
            return;
        }
        if (a){
            alert("账号不能为空！");
            return;
        }
        if (count > 4) {
            alert("操作太频繁，请稍后再试！");
            return;
        }

        $.ajax({
            url: "../wx/activation",
            type: "post",
            data: {
                openId: openId,
                account: account,
                password: password
            },
            success: function (data) {//01表示成功，02表示失败
                if (data == "01") {
                    window.location.href = "http://161818x71d.iask.in/wx/ac/su";
                }else if (data == "02") {
                    count ++;
                    alert("绑定失败！\n第"+count+"次绑定失败！");
                }else if(data == "03"){
                    count ++;
                    alert("该用户已经绑定！");
                }else if(data == "04"){
                    count ++;
                    alert("绑定失败，请检查hr账号和密码！\n第"+count+"次绑定失败！");
                }else {
                    count ++;
                    alert("绑定失败，系统异常！");
                }
            }
        });
    }

    function home() {
        alert("首页");
    }
</script>
</body>
</html>
