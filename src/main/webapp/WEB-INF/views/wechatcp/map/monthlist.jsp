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
    <title>月度考勤</title>
</head>
<body>
<div data-role="page" id="monthList">
    <div data-role="header"><!-- /cp/msg/emp/checks -->
        <a href="/cp/msg/emp/checks" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>${A0101}月度考勤列表</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div class="page__bd">
            <div class="weui-panel" id="outPutMonthList">

            </div>
        </div>

        <!--BEGIN toast-->
        <div id="toastloadMonthList" style="display: inherit;">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">数据玩命加载中，请稍后...</p>
            </div>
        </div>
        <div id="toastMonthList" style="display: none;">
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
        </div>
    </div>

    <script>

        $(document).on('pagecreate','#monthList',function () {
            var shangwu;
            var xiawu;
            var userList;

            $.ajax({//获取员工考勤信息
                type: "POST",
                url: "/cp/msg/get/check/work/month",
                cache: false,
                data: {
                    A0100: '${A0100}'//session中的用户
                },
                success: function (data, status) {//成功！
                    var json = $.parseJSON(data);
                    userList = json.users;
                    shangwu = json.sw;
                    xiawu = json.xw;
                    alert("monthList"+json);
                    init(userList);
                },
                error: function (data, status) {//失败！
                    alert("获取考勤数据失败，错误代码："+data);
                }
            });
//////////////////////////////////////////////////////////////////////////////////////
            function init(userList) {
                var div = "";
                for (var i=0;i<userList.length;i++) {
                    var u = userList[i];
                    var sw = getShangWuInfo(u.work_date);
                    var xw = getXiaWu(u.work_date);

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

            function getShangWuInfo(workDate) {
                var obj;
                for (var i=0;i<shangwu.length;i++) {
                    if (shangwu[i].work_date == workDate) {
                        obj = shangwu[i];
                        break;
                    }
                }
                return obj;
            }
            function getXiaWu(workDate) {
                var obj;
                for (var i=0;i<xiawu.length;i++) {
                    if (xiawu[i].work_date == workDate) {
                        obj = xiawu[i];
                        break;
                    }
                }
                return obj;
            }

            var $iosActionsheet = $('#iosActionsheetMonthList');
            var $iosMask = $('#iosMaskMonthList');

            function hideActionSheet() {
                $iosActionsheet.removeClass('weui-actionsheet_toggle');
                $iosMask.fadeOut(200);
            }

            $iosMask.on('click', hideActionSheet);
            $('#iosActionsheetCancelMonthList').on('click', function () {
                hideActionSheet();
            });
        });
    </script>
</div>
</body>
</html>


