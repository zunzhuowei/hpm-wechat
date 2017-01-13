<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1 ">
    <!--   		,maximum-scale=1.0,ouser-scalable=no -->
    <title>首页</title>
    <script src="<@s.url '/js/jquery-1.8.3.min.js'/>" type="text/javascript"></script>
    <script src="<@s.url '/marry/js/bootstrap.min.js'/>" type="text/javascript"></script>
    <link rel="stylesheet" href="<@s.url '/marry/css/bootstrap.min.css'/>">
    <link href="<@s.url '/css/weui.css'/>" rel="stylesheet"/>
    <style type="text/css">
        .png {
            width: 50px;
            height: 50px;
        }

        .png1 {
            width: 47px;
            height: 47px;
        }

        .hcenter {
            height: 40px;
            line-height: 40px;
        }
    </style>
</head>
<body>
<!--BEGIN toast-->
<div id="toastload" style="display: inherit;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">玩命加载中，请稍后...</p>
    </div>
</div>

<div class="list-group" id="homeId"></div>

<div class="main_nav_bottom" style="display: none">
    <nav class="navbar navbar-default navbar-fixed-bottom" style="background-color: #337ab7">
        <div class="container" align="center">
            <style>
                .nav-tabs {
                    text-align: center;
                    height: 40px;
                    line-height: 40px;
                }
            </style>
            <ul class="nav nav-tabs nav-tabs-justified">
                <div class="row" align="center">
                    <div class="col-md-4 col-sm-4 col-xs-4" align="center">
                        <li><a href="#"><img src="/images/index_10.png" class="img-responsive png1 img-circle"/></a>
                        </li>
                    </div>
                    <div class="col-md-4 col-sm-4 col-xs-4" align="center">
                        <li><a href="#"><img src="/images/ry.png" class="img-responsive png1 img-circle"/></a></li>
                    </div>
                    <div class="col-md-4 col-sm-4 col-xs-4" align="center">
                        <li><a href="#"><img src="/images/sy.png" class="img-responsive png1 img-circle"/></a></li>
                    </div>
                </div>
            </ul>
        </div>
    </nav>
</div>
<script>
    //前台先将其转换为json对象:
    //javascript方式:var json = eval(后台传后来的json串);
    //jQuery方法:var json = $.parseJSON(后台传后来的json串);

    $(document).ready(function(){
        $.ajax({
            type: "POST",
            url: "/cp/msg/get/custom/menus",
            cache: false,
            data: {
                A0100: '${user.A0100}'
            },
            success: function (data, status) {//成功！
                var jsonData = eval(data);
                //var h = "";
                var h = "<a href=\"#\" class=\"list-group-item active\">\n"+
                        "        <h3 class=\"list-group-item-heading hcenter\">\n"+
                        "            <center>首页菜单</center>\n"+
                        "        </h3>\n"+
                        "    </a>";
                for(var i = 0;i<jsonData.length;i++) {
                    h += "<a href=\""+jsonData[i].url+"\" class=\"list-group-item\">\n"+
                            "        <h4 class=\"list-group-item-heading\">\n"+
                            "            <img src=\""+jsonData[i].imgUrl+"\" class=\"img-responsive png pull-left\" " +
                            "style=\"margin-top:-4px;\"/>\n"+
                            "            &nbsp;&nbsp;"+jsonData[i].menuText+"\n"+
                            "        </h4>\n"+
                            "        <p class=\"list-group-item-text\">\n"+
                            "            &nbsp;&nbsp;&nbsp;"+jsonData[i].menuDesc+"\n"+
                            "        </p>\n"+
                            "    </a>";
                }
                $("#homeId").html(h);
                $('#toastload').css("display", "none");
            },
            error: function (data, status) {//失败！
                alert(data);
            }
        });
    });

</script>
</body>
</html>
