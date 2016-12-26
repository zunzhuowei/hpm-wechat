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
<div class="list-group">
${user.A0101}--${user.E0122}
    <a href="#" class="list-group-item active">
        <h3 class="list-group-item-heading hcenter">
            <center>外包事业部</center>
        </h3>
    </a>
    <a href="/wait/thing/home" class="list-group-item">
        <span class="badge">4</span>
        <img src="/images/index_28.png" class="img-responsive png pull-left" style="margin-top:-4px;"
        />
        <h4 class="list-group-item-heading">
            &nbsp;&nbsp;待办事宜
        </h4>
        <p class="list-group-item-text">
            &nbsp;&nbsp;&nbsp;我的待办事宜
        </p>

    </a>
    <a href="/self/info/home" class="list-group-item">
        <h4 class="list-group-item-heading">
            <img src="/images/index_31.png" class="img-responsive png pull-left" style="margin-top:-4px;"/>
            &nbsp;&nbsp;我的信息
        </h4>
        <p class="list-group-item-text">
            &nbsp;&nbsp;&nbsp;我的个人信息
        </p>
    </a>
    <a href="/self/salary/home" class="list-group-item">
        <h4 class="list-group-item-heading">
            <img src="/images/1_03.png" class="img-responsive pull-left png img-circle" style="margin-top:-4px;"/>
            &nbsp;&nbsp;我的薪酬
        </h4>
        <p class="list-group-item-text">
            &nbsp;&nbsp;&nbsp;我的薪酬信息
        </p>
    </a>
    <a href="/work/time/home" class="list-group-item">
        <h4 class="list-group-item-heading">&nbsp;&nbsp;工时信息
            <img src="/images/zuzhijiagou.png" class="img-responsive pull-left png img-circle"
                 style="margin-top:-4px;"/>
        </h4>
        <p class="list-group-item-text">&nbsp;&nbsp;&nbsp;我的详细工时信息</p>
    </a>
    <a href="/check/work/home" class="list-group-item">
        <h4 class="list-group-item-heading">&nbsp;&nbsp;我的考勤
            <img src="/images/index_47.png" class="img-responsive pull-left png img-circle" style="margin-top:-4px;"/>
        </h4>
        <p class="list-group-item-text">&nbsp;&nbsp;&nbsp;我的考勤信息</p>
    </a>
</div>
<div class="main_nav_bottom">
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

</body>
</html>
