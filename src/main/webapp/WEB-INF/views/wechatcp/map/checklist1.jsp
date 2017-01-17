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
    <title>员工考勤列表</title>
</head>
<body>
<div data-role="page" id="pageone">
    <div data-role="header">
        <h1>员工考勤列表</h1>
    </div>
    <div data-role="main" class="ui-content">
    <p>可根据单位/部门/岗位/名字进行搜索</p>
    <div class="weui-cell__bd">
        <input class="weui-input" readonly="readonly" id="showDatePicker"
               placeholder="选择考勤日期查询..." >
    </div>
     <form class="ui-filterable">
         <input id="myFilter" data-type="search">
    </form>
        <ul data-role="listview" data-filter="true" data-input="#myFilter"
        data-autodividers="true" data-inset="true" id="outPut">
            <%--<li data-filtertext="+u.B0110+u.E0122+u.E01A1+u.A0101+"
            onclick="selectOne()">
            <a id="+u.A0100+" >张三丰</a>
            </li>--%>
        </ul>
    </div>
</div>

<!--BEGIN actionSheet 上午/下午-->
<div>
    <div class="weui-mask" id="iosMask" style="display: none"></div>
    <div class="weui-actionsheet" id="iosActionsheet">
        <div class="weui-actionsheet__menu">
            上午
            <div class="weui-actionsheet__cell"><span id="checkTimeMo">没有打卡记录</span></div>
            <div class="weui-actionsheet__cell"><span id="checkAddressMo">没有打卡记录</span></div>
            <div class="weui-actionsheet__cell" id="checkReasonDivMo">
                <span id="checkReasonMo">没有打卡记录</span>
            </div>
            <hr/>
            下午
            <div class="weui-actionsheet__cell" id="checkTimeAf"><span >没有打卡记录</span></div>
            <div class="weui-actionsheet__cell"><span id="checkAddressAf">没有打卡记录</span></div>
            <div class="weui-actionsheet__cell" id="checkReasonDivAf">
                <span id="checkReasonAf">没有打卡记录</span>
            </div>
        </div>
        <div class="weui-actionsheet__action">
            <div class="weui-actionsheet__cell" id="iosActionsheetCancel">返回</div>
        </div>
    </div>
</div>
<!-- end actionSheet -->
<!--BEGIN toast-->
<div id="toastload" style="display: inherit;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据玩命加载中，请稍后...</p>
    </div>
</div>
<div id="toast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <div class="icon-box">
            <i class="weui-icon-info weui-icon_msg"></i>
            <div class="icon-box__ctn">
                <h3 class="icon-box__title">提示</h3>
                <p class="icon-box__desc" id="toastTextInfo">没有考勤信息...</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">

    var shangwu;
    var xiawu;
    var userList;

    $(function(){
        $.ajax({//获取员工考勤信息
            type: "POST",
            url: "/cp/msg/get/check/work/infos",
            cache: false,
            data: {
                A0100: '${user.A0100}'//session中的用户
            },
            success: function (data, status) {//成功！
                var json = $.parseJSON(data);
                userList = json.users;
                shangwu = json.sw;
                xiawu = json.xw;
                init(userList);
                if (userList == "") {
                    showToast(new Date().Format("yyyy年MM月dd日"));
                }
            },
            error: function (data, status) {//失败！
                alert("获取考勤数据失败，错误代码："+data);
            }
        });
    });

    function init(userList) {
        var lis = "";
        for (var i=0;i<userList.length;i++) {
            var u = userList[i];
            var sw = getShangWuInfo(u.A0100);
            var xw = getXiaWu(u.A0100);
            var spanText = "<span>"+u.A0101+"</span>";
            if(sw == undefined && xw == undefined) {
                spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(未打卡)</span>";
            }else if (sw != undefined && xw == undefined) {
                spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(下午未打卡)</span>";
                if (sw.liyou != undefined && sw.liyou != null && sw.liyou != "") { //有理由的
                    spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(上午有签到理由)</span>";
                }
            }else if (sw == undefined && xw != undefined) {
                spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(上午未打卡)</span>";
            }else if (sw != undefined && xw != undefined) {
                spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(上午未打卡)</span>";
            }

            if (sw != undefined) {
                if (sw.work_date == undefined) {//未打卡的
                    spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(上午未打卡)</span>";
                }else if (sw.liyou != undefined && sw.liyou != null && sw.liyou != "") { //有理由的
                    spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(上午有签到理由)</span>";
                }
            }
            if (xw != undefined) {
                if (xw.work_date == undefined) {//未打卡的
                    spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(下午未打卡)</span>";
                }else if (xw.liyou != undefined && xw.liyou != null && xw.liyou != "") {//有理由的
                    spanText = "<span style='color: red;display: inherit;'>*"+u.A0101+"(下午有签到理由)</span>";
                }
            }

            lis = lis + "<li class=\"ui-li-divider ui-bar-inherit ui-first-child\"" +
                    " role=\"heading\" data-role=\"list-divider\">"+u.A0101.substring(0,1)+"</li>";
            lis = lis + "<li data-filtertext=\""+u.B0110+u.E0122+u.E01A1+u.A0101+"\"" +
                         " onclick=\"selectOne('"+u.A0100+"')\"" +
                    " class='ui-last-child'>" +
                            "<a id=\""+u.A0100+"\" class='ui-btn ui-btn-icon-right ui-icon-carat-r'" +
                    ">"+spanText+"</a>" +
                        "</li>";
        }
        $('#outPut').html(lis);
        $('#toastload').css("display", "none");
    }

    function selectOne(A0100) {
        resetData();
        var sw = getShangWuInfo(A0100);
        var xw = getXiaWu(A0100);
        setData2View(sw, xw);
        $iosActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    }

    function resetData() {
        $('#checkTimeMo').text("没有打卡记录");
        $('#checkAddressMo').text("没有打卡记录");
        $('#checkReasonMo').text("没有打卡记录");
        $('#checkTimeAf').text("没有打卡记录");
        $('#checkAddressAf').text("没有打卡记录");
        $('#checkReasonAf').text("没有打卡记录");
    }

    function setData2View(sw,xw) {
        if (sw != undefined) {
            $('#checkTimeMo').text("时间:"+sw.work_date + " " +sw.work_time);
            $('#checkAddressMo').text("地点:"+sw.location);
            $('#checkReasonMo').text("理由:"+sw.liyou);
            if (sw.liyou == null || sw.liyou == "") {
                $('#checkReasonDivMo').css("display", "none");
            }
        }
        if (xw != undefined) {
            $('#checkTimeAf').text("时间:"+xw.work_date + " " +xw.work_time);
            $('#checkAddressAf').text("地点:"+xw.location);
            $('#checkReasonAf').text("理由:"+xw.liyou);
            if (xw.liyou == null || xw.liyou == "") {
                $('#checkReasonDivAf').css("display", "none");
            }
        }
    }

    function getShangWuInfo(A0100) {
        var obj;
        for (var i=0;i<shangwu.length;i++) {
            if (shangwu[i].A0100 == A0100) {
                obj = shangwu[i];
                break;
            }
        }
        return obj;
    }
    function getXiaWu(A0100) {
        var obj;
        for (var i=0;i<xiawu.length;i++) {
            if (xiawu[i].A0100 == A0100) {
                obj = xiawu[i];
                break;
            }
        }
        return obj;
    }

    var $iosActionsheet = $('#iosActionsheet');
    var $iosMask = $('#iosMask');

    function hideActionSheet() {
        $iosActionsheet.removeClass('weui-actionsheet_toggle');
        $iosMask.fadeOut(200);
    }

    $iosMask.on('click', hideActionSheet);
    $('#iosActionsheetCancel').on('click', hideActionSheet);

    $('#showDatePicker').on('click', function () {
        weui.datePicker({
            start: 2013,
            end: new Date().getFullYear(),
            onChange: function (result) {
                //console.log(result);
            },
            onConfirm: function (result) {//result 为 Array  2016,1,1
                //console.log(result);
                var showInView = result.toString().replace(",","年").replace(",","月") + "日";
                $('#showDatePicker').val(showInView);
                var time = result.toString().replace(",",".").replace(",",".");
                $('#toastload').fadeIn(250);
                $.ajax({//获取员工考勤信息
                    type: "POST",
                    url: "/cp/msg/get/check/work/infos",
                    cache: false,
                    data: {
                        date:time,
                        A0100: '${user.A0100}'//session中的用户
                    },
                    success: function (data, status) {//成功！
                        var json = $.parseJSON(data);
                        userList = json.users;
                        shangwu = json.sw;
                        xiawu = json.xw;
                        init(userList);
                        if (userList == "") {//没有数据提示
                            showToast(showInView);
                        }
                    },
                    error: function (data, status) {//失败！
                        alert("获取考勤数据失败，错误代码："+data);
                    }
                });
            }
        });
    });

    function showToast(showInView) {
        $('#toastTextInfo').text(showInView + "\n" + "没有考勤信息...");
        var $toast = $('#toast');
        if ($toast.css('display') != 'none') return;
        $toast.fadeIn(300);
        setTimeout(function () {
            $toast.fadeOut(300);
        }, 1250);
    }

    //var time1 = new Date().Format("yyyy-MM-dd");
    //var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

</script>

