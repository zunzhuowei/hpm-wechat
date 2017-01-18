<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1 ">
    <!--   		,maximum-scale=1.0,ouser-scalable=no -->
    <title>用户</title>
    <script src="<@s.url '/js/jquery-1.8.3.min.js'/>" type="text/javascript"></script>
    <script src="<@s.url '/js/ui/miniui.js'/>" type="text/javascript"></script>
    <script src="<@s.url '/marry/js/bootstrap.min.js'/>" type="text/javascript"></script>
    <link rel="stylesheet" href="<@s.url '/marry/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url '/js/ui/themes/default/miniui.css'/>">
    <link rel="stylesheet" href="<@s.url '/js/ui/themes/pure/skin.css'/>">
    <link rel="stylesheet" href="<@s.url '/js/ui/themes/icons.css'/>">
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
<div class="container">
    <div class="row">
        <div class="list-group">
            <a class="list-group-item active">
                <input type="button" class="btn btn-primary pull-left" id="goBack" style="display: none;"
                       onclick="JavaScript:history.go(-1);" value="返回"/>
                <#--<input type="button" class="btn btn-primary pull-right" value="刷新"/>-->
                <h3 class="list-group-item-heading">
                    <center>个人信息</center>
                </h3>
            </a>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="list-group">
            <a href="#" class="list-group-item">
                <h4 class="list-group-item-heading">
                    <img src="/images/index_04.png" class="img-responsive png pull-left" style="margin-top:-4px;"/>
                    &nbsp;&nbsp;xxx
                </h4>
                <p class="list-group-item-text">
                    &nbsp;&nbsp;&nbsp;运营中心-交付经理
                </p>
            </a>
            <div id="user_info">
                <#-- 人员信息通过ajax获取 -->
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //2、JSON字符串转换为Json对象
    //var myObject = eval('('+jsonStr+')');
    //eval是js自带的函数，不是很安全，可以考虑用json包。
    //或者
    //var myObject = jsonStr.parseJSON();   //由JSON字符串转换为JSON对象
    //或者
    //var myObject = JSON.parse(jsonStr);   //由JSON字符串转换为JSON对象
    //然后，就可以这样读取：
    //Alert(myObject.name);
    //Alert(myObject.password);
    //特别注意：如果myObject本来就是一个JSON对象，那么使用eval（）函数转换后（哪怕是多次转换）还是JSON对象
    //，但是使用parseJSON（）函数处理后会有问题（抛出语法异常）。

    mini.parse();

    $(document).ready(function(){
        var code = '${code}';
        if(code == "") {
            $('#goBack').css("display", "inherit");
        }

        $.ajax({
            url: "${ajaxUrl}",
            type: "post",
            data: {
                A0100: "${user.A0100}"
            },
            success: function (data) {
                //var jsonData = eval("'"+data+"'");
                var jsonData = JSON.parse(data);
                var add = jsonData.address;
                if (add == undefined) {
                    add = "";
                }
                var sex = jsonData.sex;
                if (sex == "1") sex = "男";
                else if (sex == "2") sex = "女";
                else sex = "人妖";
                var h = "<p class=\"list-group-item\">\n" +
                        "                    姓名：${user.A0101}\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    性别："+sex+"\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    年龄："+jsonData.age+"\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    工号："+jsonData.number+"\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    部门：${user.bumen}\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    岗位：${user.gangwei}\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    联系电话："+jsonData.tel+"\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    身份证号："+jsonData.card+"\n" +
                        "                </p>\n" +
                        "                <p class=\"list-group-item\">\n" +
                        "                    住址："+add+"\n" +
                        "                </p>";
                $("#user_info").html(h);
            }
        });

        //history.back(-1):直接返回当前页的上一页，数据全部消息，是个新页面
        //history.go(-1):也是返回当前页的上一页，不过表单里的数据全部还在

    });
</script>
</body>
</html>
