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

<div data-role="page" id="checklist">
    <div data-role="header"><!-- javascript:history.go(-1); -->
        <a href="/cp/msg/back/home" rel='external' class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>员工考勤列表</h1>
        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-icon-search ui-btn-icon-left"
           id="globalSearch">搜索</a>
    </div>

    <div data-role="main" class="ui-content">
        <div id="searchDiv" style="display: none;">
            <input name="textSearch" id="textSearch" data-clear-btn="true" type="search" placeholder="输入名字搜索"/>
            <input name="timeSearch" type="search" placeholder="选择时间搜索" data-clear-btn="true"
                   id="showDatePicker" readonly="readonly"/>
            <%--<a href="#" class="ui-btn ui-btn-inline ui-icon-delete ui-btn-icon-left ui-corner-all ui-shadow"
               id="clearSearch">清空条件</a>--%>
            <a href="#" class="ui-btn ui-btn-inline ui-icon-search ui-btn-icon-left ui-corner-all ui-shadow"
               id="confirmSearch">确认搜索</a>
        </div>
        <div class="page__bd">
            <div class="weui-panel" id="outPut">

            </div>
        </div>

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

    </div>

    <script>
        /*if(window.localStorage){
            alert("浏览器支持localStorage");     }else{
            alert("浏览器暂不支持localStorage");        }  
        if(window.sessionStorage){
           alert("浏览器支持sessionStorage");   }else{
            alert("浏览器暂不支持sessionStorage")          }*/
        $(document).on('pagecreate','#checklist',function () {//pagebeforeshow   pagecreate
            var shangwu;
            var xiawu;
            var userList;

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
////////////////////////////////////////////////////////////////////////////////////
            function init(userList) {
                var div = "";
                for (var i=0;i<userList.length;i++) {
                    var u = userList[i];
                    var sw = getShangWuInfo(u.A0100);
                    var xw = getXiaWu(u.A0100);

                    div = div + "<div class=\"weui-panel__bd\">" +
                            "<div class=\"weui-media-box weui-media-box_text\">" +
                            "<h4 class=\"weui-media-box__title\" style='font-weight: bold;'>"+u.A0101+"</h4>" ;
                    if (sw != undefined) {//上午有打卡信息
                        if (sw.liyou != undefined && sw.liyou != null && sw.liyou != "") {//上午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>上午理由:"+sw.liyou+"</p>" ;
                        }
                    }
                    if (xw != undefined) {//下午有打卡信息
                        if (xw.liyou != undefined && xw.liyou != null && xw.liyou != "") {//下午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>下午理由:"+xw.liyou+"</p>" ;
                        }
                    }
                    div = div + "<ul class=\"weui-media-box__info\">";
                    if (sw != undefined) {//上午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>上午时间:"+sw.work_time+"</li>" +
                                "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+sw.location+"</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: red;'>上午未签到</li>" ;
                    }
                    if (xw != undefined) {//下午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: #000;'>下午时间:"+xw.work_time+"</li>" ;
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+xw.location+"</li>";// +
                        //"<li class=\"weui-media-box__info__meta\">地点:</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: red;'>下午未签退</li>" ;
                    }
                    div = div + "</ul>" +
                            "</div>" +
                            "</div>" +
                            " <a class=\"weui-cell weui-cell_access\" " +
                            //"href=\"?A0100='"+u.A0100+"'&A0101='"+u.A0101+"'#monthList\"  data-transition='slide'>\n" +
                            "href=\"#monthList\" onclick=\"sessionStorage.A0100='"+u.A0100+","+u.A0101+"'\" data-transition='flow'>\n" +
                            "                <div class=\"weui-cell__bd\">\n" +
                            "                    <p>查看月考勤</p>\n" +
                            "                </div>\n" +
                            "                <div class=\"weui-cell__ft\">\n" +
                            "                </div>\n" +
                            "            </a>"+
                            /*" <a class=\"weui-cell weui-cell_access\" " +
                            "href=\"monthlist?A0100="+u.A0100+"&A0101="+u.A0101+"\">\n" +
                            "                <div class=\"weui-cell__bd\">\n" +
                            "                    <p>查看月考勤</p>\n" +
                            "                </div>\n" +
                            "                <div class=\"weui-cell__ft\">\n" +
                            "                </div>\n" +
                            "            </a>"+*/

                            "<hr/>";
                }//data-ajax='false'  rel='external'
                $('#outPut').html(div);
                $('#toastload').css("display", "none");
            }


            $('#globalSearch').on('click',function () {//ui-icon-search  ui-icon-delete
                if($('#searchDiv').css("display") == "none"){
                    $('#globalSearch').addClass("ui-icon-delete").removeClass("ui-icon-search");
                    $('#searchDiv').fadeIn('fast',function () {
                        $('#globalSearch').text("取消搜索");
                    });
                    return;
                }
                if($('#searchDiv').css("display") != "none"){
                    $('#globalSearch').addClass("ui-icon-search").removeClass("ui-icon-delete");
                    $('#searchDiv').fadeOut('fast',function () {
                        $('#globalSearch').text("搜索");
                    });
                    return;
                }

            });

            $('#cancelSearch').on('click',function () {
                $('#searchDiv').hide();
            });

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

            var dateResult = null;
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
                        dateResult = result;
                    }
                });
            });

            $('#confirmSearch').on('click',function () {
                if (dateResult == null) {
                    dateResult = $('#showDatePicker').val().replace("年",".").replace("月",".").replace("日","");
                }
                var time = dateResult.toString().replace(",",".").replace(",",".");
                $('#toastload').fadeIn(250);
                $.ajax({//获取员工考勤信息
                    type: "POST",
                    url: "/cp/msg/get/check/work/infos",
                    cache: false,
                    data: {
                        date:time,
                        empName:$('#textSearch').val(),
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
            });

            $('#clearSearch').on('click',function () {
                $('#showDatePicker').val(null);
                $('#textSearch').val(null);
                dateResult = null;
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
            };


        });

    </script>
</div>

<div data-role="page" id="monthList">
    <div data-role="header"><!-- /cp/msg/emp/checks -->
        <a href="#checklist" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left"
           data-transition='flow'>返回</a>
        <h1 id="monthTittle">月度考勤列表</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div class="page__bd">
            <div class="weui-panel" id="outPutMonthList">

            </div>
        </div>
    </div>
    <!--BEGIN toast-->
    <div id="toastloadMonthList" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">数据玩命加载中，请稍后...</p>
        </div>
    </div>
    <%--<div id="toastMonthList" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <div class="icon-box">
                <i class="weui-icon-info weui-icon_msg"></i>
                <div class="icon-box__ctn">
                    <h3 class="icon-box__title">提示</h3>
                    <p class="icon-box__desc" id="toastTextInfoMonthList">没有考勤信息...</p>
                </div>
            </div>
        </div>
    </div>--%>

    <script>
        $(document).on("pagebeforeshow","#monthList",function(){
            $('#toastloadMonthList').css("display", "inherit");
            var A0100_A0101 = sessionStorage.A0100.split(",");
            var shangwuMonthList;
            var xiawuMonthList;
            var userListMonthList;
            var A0100 = A0100_A0101[0];
            var A0101 = A0100_A0101[1];
            //alert(A0100 +","+A0101);

            $("#monthTittle").text(A0101+"月度考勤列表");

            $.ajax({//获取员工考勤信息
                type: "POST",
                url: "/cp/msg/get/check/work/month",
                cache: false,
                data: {
                    A0100: A0100  //session中的用户
                },
                success: function (data, status) {//成功！
                    var json = $.parseJSON(data);
                    userListMonthList = json.users;
                    shangwuMonthList = json.sw;
                    xiawuMonthList = json.xw;
                    initMonthList(userListMonthList);
                },
                error: function (data, status) {//失败！
                    alert("获取考勤数据失败，错误代码："+data);
                }
            });
//////////////////////////////////////////////////////////////////////////////////////
            function initMonthList(userList) {
                var div = "";
                for (var i=0;i<userList.length;i++) {
                    var u = userList[i];
                    var sw = getShangWuInfoMonthList(u.work_date);
                    var xw = getXiaWuMonthList(u.work_date);

                    div = div + "<div class=\"weui-panel__bd\">" +
                            "<div class=\"weui-media-box weui-media-box_text\">" +
                            "<h4 class=\"weui-media-box__title\" style='font-weight: bold;'>"+u.A0101+"</h4>" ;
                    if (sw != undefined) {//上午有打卡信息
                        if (sw.liyou != undefined && sw.liyou != null && sw.liyou != "") {//上午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>上午理由:"+sw.liyou+"</p>" ;
                        }
                    }
                    if (xw != undefined) {//下午有打卡信息
                        if (xw.liyou != undefined && xw.liyou != null && xw.liyou != "") {//下午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>下午理由:"+xw.liyou+"</p>" ;
                        }
                    }
                    div = div + "<ul class=\"weui-media-box__info\">";
                    if (sw != undefined) {//上午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>上午时间:"+sw.work_time+"</li>" +
                                "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+sw.location+"</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: red;'>上午未签到</li>" ;
                    }
                    if (xw != undefined) {//下午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: #000;'>下午时间:"+xw.work_time+"</li>" ;
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+xw.location+"</li>";// +
                        //"<li class=\"weui-media-box__info__meta\">地点:</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: red;'>下午未签退</li>" ;
                    }
                    div = div + "</ul>" +
                            "</div>" +
                            "</div>" +
                            "<hr/>";
                }
                $('#outPutMonthList').html(div);
                $('#toastloadMonthList').css("display", "none");
            }

            $('#globalSearchMonthList').on('click',function () {
                if($('#searchDivMonthList').css("display") == "none"){
                    $('#searchDivMonthList').fadeIn('fast',function () {
                        $('#globalSearchMonthList').text("取消搜索");
                    });
                    return;
                }
                if($('#searchDivMonthList').css("display") != "none"){
                    $('#searchDivMonthList').fadeOut('fast',function () {
                        $('#globalSearchMonthList').text("搜索");
                    });
                    return;
                }

            });

            $('#cancelSearchMonthList').on('click',function () {
                $('#searchDivMonthList').hide();
            });

            function getShangWuInfoMonthList(workDate) {
                var obj;
                for (var i=0;i<shangwuMonthList.length;i++) {
                    if (shangwuMonthList[i].work_date == workDate) {
                        obj = shangwuMonthList[i];
                        break;
                    }
                }
                return obj;
            }
            function getXiaWuMonthList(workDate) {
                var obj;
                for (var i=0;i<xiawuMonthList.length;i++) {
                    if (xiawuMonthList[i].work_date == workDate) {
                        obj = xiawuMonthList[i];
                        break;
                    }
                }
                return obj;
            }

            var $iosActionsheetMonthList = $('#iosActionsheetMonthList');
            var $iosMaskMonthList = $('#iosMaskMonthList');

            function hideActionSheetMonthList() {
                $iosActionsheetMonthList.removeClass('weui-actionsheet_toggle');
                $iosMaskMonthList.fadeOut(200);
            }

            $iosMaskMonthList.on('click', hideActionSheetMonthList);
            $('#iosActionsheetCancelMonthList').on('click', function () {
                hideActionSheetMonthList();
            });
        });

       /* $(document).on('pagecreate','#monthList',function () {
            var A0100_A0101 = sessionStorage.A0100.split(",");
            var shangwuMonthList;
            var xiawuMonthList;
            var userListMonthList;
            var A0100 = A0100_A0101[0];
            var A0101 = A0100_A0101[1];
            alert(A0100 +","+A0101);

            $("#monthTittle").text(A0101+"月度考勤列表");

            $.ajax({//获取员工考勤信息
                type: "POST",
                url: "/cp/msg/get/check/work/month",
                cache: false,
                data: {
                    A0100: A0100  //session中的用户
                },
                success: function (data, status) {//成功！
                    var json = $.parseJSON(data);
                    userListMonthList = json.users;
                    shangwuMonthList = json.sw;
                    xiawuMonthList = json.xw;
                    initMonthList(userListMonthList);
                },
                error: function (data, status) {//失败！
                    alert("获取考勤数据失败，错误代码："+data);
                }
            });
//////////////////////////////////////////////////////////////////////////////////////
            function initMonthList(userList) {
                var div = "";
                for (var i=0;i<userList.length;i++) {
                    var u = userList[i];
                    var sw = getShangWuInfoMonthList(u.work_date);
                    var xw = getXiaWuMonthList(u.work_date);

                    div = div + "<div class=\"weui-panel__bd\">" +
                            "<div class=\"weui-media-box weui-media-box_text\">" +
                            "<h4 class=\"weui-media-box__title\" style='font-weight: bold;'>"+u.A0101+"</h4>" ;
                    if (sw != undefined) {//上午有打卡信息
                        if (sw.liyou != undefined && sw.liyou != null && sw.liyou != "") {//上午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>上午理由:"+sw.liyou+"</p>" ;
                        }
                    }
                    if (xw != undefined) {//下午有打卡信息
                        if (xw.liyou != undefined && xw.liyou != null && xw.liyou != "") {//下午有签到理由
                            div = div + "<p class=\"weui-media-box__desc\" style='color: red;'>下午理由:"+xw.liyou+"</p>" ;
                        }
                    }
                    div = div + "<ul class=\"weui-media-box__info\">";
                    if (sw != undefined) {//上午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>上午时间:"+sw.work_time+"</li>" +
                                "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+sw.location+"</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: red;'>上午未签到</li>" ;
                    }
                    if (xw != undefined) {//下午有打卡信息
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: #000;'>下午时间:"+xw.work_time+"</li>" ;
                        div = div + "<li class=\"weui-media-box__info__meta\" style='color: #000;'>地点:"+xw.location+"</li>";// +
                        //"<li class=\"weui-media-box__info__meta\">地点:</li>" ;
                    }else{
                        div = div + "<li class=\"weui-media-box__info__meta " +
                                "weui-media-box__info__meta_extra\" style='color: red;'>下午未签退</li>" ;
                    }
                    div = div + "</ul>" +
                            "</div>" +
                            "</div>" +
                            "<hr/>";
                }
                $('#outPutMonthList').html(div);
                $('#toastloadMonthList').css("display", "none");
            }

            $('#globalSearchMonthList').on('click',function () {
                if($('#searchDivMonthList').css("display") == "none"){
                    $('#searchDivMonthList').fadeIn('fast',function () {
                        $('#globalSearchMonthList').text("取消搜索");
                    });
                    return;
                }
                if($('#searchDivMonthList').css("display") != "none"){
                    $('#searchDivMonthList').fadeOut('fast',function () {
                        $('#globalSearchMonthList').text("搜索");
                    });
                    return;
                }

            });

            $('#cancelSearchMonthList').on('click',function () {
                $('#searchDivMonthList').hide();
            });

            function getShangWuInfoMonthList(workDate) {
                var obj;
                for (var i=0;i<shangwuMonthList.length;i++) {
                    if (shangwuMonthList[i].work_date == workDate) {
                        obj = shangwuMonthList[i];
                        break;
                    }
                }
                return obj;
            }
            function getXiaWuMonthList(workDate) {
                var obj;
                for (var i=0;i<xiawuMonthList.length;i++) {
                    if (xiawuMonthList[i].work_date == workDate) {
                        obj = xiawuMonthList[i];
                        break;
                    }
                }
                return obj;
            }

            var $iosActionsheetMonthList = $('#iosActionsheetMonthList');
            var $iosMaskMonthList = $('#iosMaskMonthList');

            function hideActionSheetMonthList() {
                $iosActionsheetMonthList.removeClass('weui-actionsheet_toggle');
                $iosMaskMonthList.fadeOut(200);
            }

            $iosMaskMonthList.on('click', hideActionSheetMonthList);
            $('#iosActionsheetCancelMonthList').on('click', function () {
                hideActionSheetMonthList();
            });
        });*/
    </script>
</div>

</body>
</html>


