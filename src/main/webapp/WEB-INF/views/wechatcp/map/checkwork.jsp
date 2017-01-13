<%--
  Created by IntelliJ IDEA.
  User: Think
  Date: 2016/12/29
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
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

        #allmap {
            width: 100%;
            /*height: 400px;*/
            height: 85%;
            overflow: hidden;
            margin: 0;
            font-family: "微软雅黑";
        }
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=C0ZTnTVVGH4aaGZ7rrGBncWjKDXsQ0xo"></script>
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <!-- 引入 jQuery 库 -->
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <%--<script src="/js/weui/weui.min.js" type="text/javascript"></script>--%>
    <title>签到/签退</title>
</head>
<body>
<div id="allmap">
</div>

<!--BEGIN toast-->
<div id="toastload" style="display: inherit;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">地图玩命加载中，请稍后...</p>
    </div>
</div>
<!--BEGIN toast-->
<div id="toastcheck" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">签到中，请稍后...</p>
    </div>
</div>

<div id="toast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">已完成</p>
    </div>
</div>

<div id="notOpentToast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">当前时间未开启签到/签退,不能签到！</p>
    </div>
</div>
<div id="notSettingToast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">系统未设置考勤范围，请前往pc端设置考勤范围！</p>
    </div>
</div>
<div id="hasCheckToast" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
        <p class="weui-toast__content">您已签到/签退了，无须再签！</p>
    </div>
</div>

<!-- 当前时间未开启签到/签退,不能签到！ -->

<div class="weui-btn-area">
    <a class="weui-btn weui-btn_primary" href="javascript:" onclick="checkWork()"
       style="display: none;" id="checkIn">签到</a>
    <a class="weui-btn weui-btn_primary" href="javascript:" onclick="checkWork()"
       style="display: none;" id="checkOut">签退</a>
    <a href="javascript:" class="weui-btn weui-btn_disabled weui-btn_primary"
       style="display: none;" id="alreadyCheckIn">已签到(点击详情)</a>
    <a href="javascript:" class="weui-btn weui-btn_disabled weui-btn_primary"
       style="display: none;" id="alreadyCheckOut">已签退(点击详情)</a>
    <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_default"
       style="display: none;" id="isNotOpenCheck">未开启签到/签退</a>
</div>

<div id="dialogs">
    <!--BEGIN dialog2-->
    <div class="js_dialog" id="iosDialog2" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd">
                <strong class="weui-dialog__title">
                    <span id="reasonTitle">签到/签退标题</span>
                </strong>
            </div>
            <div class="weui-dialog__bd">
                <span style="color: #990e0e;display: none;" id="reasonNotNull">理由不能为空！</span>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                        <textarea class="weui-textarea" id="reasonContent"
                                  placeholder="请在此输入理由！" rows="3"></textarea>
                            <div class="weui-textarea-counter"></div>
                        </div>
                    </div>
                </div>
                <%--<input value="弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内"/>--%>
            </div>
            <div class="weui-dialog__ft">
                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary"
                   onclick="submitReason()">提交</a>
            </div>
        </div>
    </div>
    <!--END dialog2-->
</div>

<!--BEGIN actionSheet -->
<div>
    <div class="weui-mask" id="iosMask" style="display: none"></div>
    <div class="weui-actionsheet" id="iosActionsheet">
        <div class="weui-actionsheet__menu"><!-- 签到时间，签到地点，如果有理由，显示理由 -->
            <div class="weui-actionsheet__cell"><span id="checkTime"></span></div>
            <div class="weui-actionsheet__cell"><span id="checkAddress"></span></div>
            <div class="weui-actionsheet__cell" id="checkReasonDiv"><span id="checkReason"></span></div>
        </div>
        <div class="weui-actionsheet__action">
            <div class="weui-actionsheet__cell" id="iosActionsheetCancel">返回</div>
        </div>
    </div>
</div>
<!-- end actionSheet -->
</body>
</html>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 18);//初始地点，16表示放大级别
    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    var address;//定位到的地址
    var pointJson;//定位到的经纬度坐标

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            mk.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            //alert('您的位置：'+r.point.lng+','+r.point.lat);
            //alert("point:"+JSON.stringify(r.point));//将对象转成json
            // 创建地理编码实例
            var myGeo = new BMap.Geocoder();//TODO有一个loading需要去掉，已解决，是JqueryMoble.js的问题
            // 根据坐标得到地址描述
            myGeo.getLocation(new BMap.Point(r.point.lng, r.point.lat), function (result) {
                if (result) {
                    address = result.address;
                    pointJson = JSON.stringify(r.point);
                    //ajax请求，判断当前时间应该显示的按钮，(同时查询是否已签到)
                    initCheckWork('${user.A0100}');
                }
            });
        }
        else {
            alert('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true});
    //关于状态码
    //BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
    //BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
    //BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
    //BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
    //BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
    //BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
    //BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
    //BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
    //BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)

    //初始化签到
    function initCheckWork(A0100) {
        $.ajax({
            type: "POST",
            url: "/cp/msg/check/work/init",
            cache: false,
            data: {
                A0100: A0100
            },
            success: function (data, status) {//成功！
                /*if (data == "0" || data == "5") {//TODO 暂时开发需要设置的。
                 data = "2";
                 }*/
                handleInitResponse.execute(data);
            },
            error: function (data, status) {//失败！
                alert(data);
            }
        });
    }

    //操作初始化响应
    var handleInitResponse = {
        execute:function (data) {
            //进来的时候给一个加载中屏蔽罩,加载完成之后去掉屏蔽罩
            $('#toastload').css("display", "none");
            eval('handleInitResponse.handle' + data + "()");
        },
        handle0:function () {//未开启签到/签退
            $('#isNotOpenCheck').css("display", "inherit");
        },
        handle1:function () {//已签到
            getCheckInfo();
            $('#alreadyCheckIn').fadeIn(800);
        },
        handle2:function () {//未签到
            $("#checkIn").css("display", "inherit");//加载完成之后，显示签到按钮
        },
        handle3:function () {//已签退
            getCheckInfo();
            $('#alreadyCheckOut').fadeIn(800);
        },
        handle4:function () {//未签退
            $('#checkOut').css("display", "inherit");
        },
        handle5:function () {
            alert("今天的排班表，没有您的安排！");
        },
        handleerror:function () {
            alert("系统异常，无法签到/签退，请联系系统管理员处理！");
        }
    };

    //从数据库获取签到后的地址，理由，日期，时间
    function getCheckInfo() {
        $.ajax({
            type: "POST",
            url: "/cp/msg/get/check/info",
            cache: false,
            data: {
                A0100: '${user.A0100}'
            },
            //dz:地址；ly:理由；rq:日期；sj:时间
            success: function (data, status) {//成功！
                if (data != null) {
                    var json = $.parseJSON(data);
                    var shijian = json.rq + " "+ json.sj;
                    $('#checkTime').text("时间: " + shijian);
                    $('#checkAddress').text("地点: " + json.dz);
                    $('#checkReason').text("理由: " + json.ly);
                    if(json.ly == null) {
                        $('#checkReasonDiv').css('display', 'none');
                    }
                }
            },
            error: function (data, status) {//失败！
                alert("错误信息:" + data);
            }
        });
    }

    //签到/签退
    function checkWork() {//toastcheck
        $("#toastcheck").css("display", "inherit");

        $.ajax({
            type: "POST",
            url: "/cp/msg/check/work",
            cache: false,
            data: {
                address: address,//地址
                pointJson: pointJson,//坐标
                user: '${user}'//session中的用户
            },
            success: function (data, status) {//成功！
                //alert("签到返回值："+data);
                /* if (data == "888") {//TODO 暂时开发需要设置的。
                 data = "01";
                 }*/
                responseSignIn.execute(data);
                getCheckInfo();
            },
            error: function (data, status) {//失败！
                alert("签到失败，错误代码："+data);
            }
        });
    }


    //填写理由文本框
    function reason(reasonTitle) {
        $('#reasonTitle').text(reasonTitle);//设置理由
        var $iosDialog2 = $('#iosDialog2');//获取文本域
        $iosDialog2.fadeIn(200);//显示文本域
    }

    //提交理由
    function submitReason() {
        var reason = $('#reasonContent').val();//获取理由
        var o = reason.length == 0 || /^\s+$/g.test(reason);
        if (o) {
            var $reasonNotNull = $('#reasonNotNull');
            if ($reasonNotNull.css('display') != 'none') return;
            $reasonNotNull.fadeIn(100);
            setTimeout(function () {
                $reasonNotNull.fadeOut(100);
            }, 1000);//显示理由不能为空
            return;
        }
        appendReasonCheckWork(reason);
        $('.js_dialog').fadeOut(200);//隐藏dialog
    }

    //带理由签到/签退
    function appendReasonCheckWork(reason) {
        $.ajax({//带理由签到/签退
            type: "POST",
            url: "/cp/msg/check/work",
            cache: false,
            data: {
                address: address,//地址
                pointJson: pointJson,//坐标
                reason: reason,//理由
                user: '${user}'//session中的用户
            },
            success: function (data, status) {//成功！
                responseSignIn.execute(data);
                getCheckInfo();
            },
            error: function (data, status) {//失败！
                alert("签到失败，错误代码："+data);
            }
        });
    }

    /* $(function(){
     var $iosDialog2Reason = $('#iosDialog2Reason');
     $('#dialogsReason').on('click', '.weui-dialog__btn', function(){
     $(this).parents('.js_dialog').fadeOut(200);
     });
     $('#dialogs').on('click', function(){
     $iosDialog2Reason.fadeIn(200);
     });
     });*/

    //对象处理不同响应
    var responseSignIn = {
        execute:function (data) {
            $("#toastcheck").css("display", "none");
            eval('responseSignIn.k'+data+'()');
        },
        k00:function () {//范围内正常签到，显示签到完成。
            var $toast = $('#toast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);
            //签到成功之后；
            $('#alreadyCheckIn').css("display", "inherit");//显示已签到
            $("#checkIn").css("display", "none");//把签到那妞隐藏。
        },
        k01:function () {//（范围内）早上迟到
            reason("迟到理由");
        },
        k02:function () {//（范围内）早上旷工
            reason("早上旷工理由");
        },
        k03:function () {//（范围内）下午旷工
            reason("下午旷工理由");
        },
        k04:function () {//（范围内）下午早退
            reason("早退理由");
        },
        k05:function () {//（范围内）下午正常下班
            var $toast = $('#toast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);
            //签到成功之后；
            $('#alreadyCheckOut').css("display", "inherit");//显示已签到
            $("#checkOut").css("display", "none");//把签到那妞隐藏。
        },
        k06:function () {//（范围内）下午加班下班
            var $toast = $('#toast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);
            //签到成功之后；
            $('#alreadyCheckOut').css("display", "inherit");//显示已签到
            $("#checkOut").css("display", "none");//把签到那妞隐藏。
        },
        k10:function () {//(范围外)早上签到
            reason("(范围外)签到理由");
        },
        k11:function () {//(范围外)早上迟到
            reason("(范围外)迟到理由");
        },
        k12:function () {//(范围外)早上旷工
            reason("(范围外)早上旷工理由");
        },
        k13:function () {//(范围外)下午旷工
            reason("(范围外)下午旷工理由");
        },
        k14:function () {//(范围外)下午早退
            reason("(范围外)早退理由");
        },
        k15:function () {//(范围外)下午正常下班
            reason("(范围外)签退理由");
        },
        k16:function () {//(范围外)下午加班下班
            reason("(范围外)签退理由");
        },
        k111:function () {
            var $toast = $('#hasCheckToast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);//屏蔽罩
            //alert("您已签到/签退了，无须再签！");//屏蔽罩
        },
        k050:function () {
            var $toast = $('#notSettingToast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);//屏蔽罩
            //alert("系统未设置考勤范围，请前往pc端设置考勤范围！");
        },
        k888:function () {
            var $toast = $('#notOpentToast');
            if ($toast.css('display') != 'none') return;
            $toast.fadeIn(100);
            setTimeout(function () {
                $toast.fadeOut(100);
            }, 1500);
            //alert("当前时间未开启签到/签退,不能签到！");
        },
        kerror:function () {
            alert("系统异常，请联系系统管理员！");
        }
    };
    //使用方法示例
    //responseSignIn.execute("01");

    // 已签到/已签退的具体地址，时间，理由等
    $(function(){
        var $iosActionsheet = $('#iosActionsheet');
        var $iosMask = $('#iosMask');

        function hideActionSheet() {
            $iosActionsheet.removeClass('weui-actionsheet_toggle');
            $iosMask.fadeOut(200);
        }

        $iosMask.on('click', hideActionSheet);
        $('#iosActionsheetCancel').on('click', hideActionSheet);
        //点击已签到
        $("#alreadyCheckIn").on("click", function(){
            $iosActionsheet.addClass('weui-actionsheet_toggle');
            $iosMask.fadeIn(200);
        });
        //点击已签退
        $("#alreadyCheckOut").on("click", function(){
            $iosActionsheet.addClass('weui-actionsheet_toggle');
            $iosMask.fadeIn(200);
        });
    });

</script>

