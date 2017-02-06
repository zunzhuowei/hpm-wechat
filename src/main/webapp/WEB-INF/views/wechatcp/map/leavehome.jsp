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
    <!-- 引入angluarjs 库 -->
    <script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
    <!-- wechat lib -->
    <script src="/js/weui/weui.min.js" type="text/javascript"></script>
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <title>请假菜单</title>
</head>
<body>

<!-- 请假主页 -->
<div data-role="page" id="leavehome">
    <div data-role="header"> <!-- javascript:history.go(-1); -->
        <a href="/cp/msg/back/home" rel='external' class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>请假主菜单</h1>
        <a href="/cp/msg/back/home" rel='external' class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left"
           id="goback2home">主页</a>
    </div>

    <div data-role="main" class="ui-content">
        <div>
            <h1 align="center">
                <img src="/images/index_01.png">
            </h1>
        </div>
        <div class="page__bd">
            <ul data-role="listview" data-inset="true">
                <li><!--   /cp/msg/emp/leave/form -->
                    <a href="#leaveform">
                        <img src="/images/qingjia.png" style="width: 60px;height: 60px;"><!--  class="ui-li-icon" -->
                        <h2>请假</h2>
                        <p>填写请假表单</p>
                    </a>
                </li>
                <li>
                    <a href="/cp/msg/share" rel='external'>
                        <img src="/images/shenpi.png" style="width: 60px;height: 60px;">
                        <h2>审批中的假</h2>
                        <p>查看审批中的假</p>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <img src="/images/xiaojia.png" style="width: 60px;height: 60px;">
                        <h2>销假</h2>
                        <p>销掉已经完成的假期</p>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <img src="/images/qingjiaguanli.png" style="width: 60px;height: 60px;">
                        <h2>请假管理</h2>
                        <p>管理请假的信息</p>
                    </a>
                </li>
            </ul>
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


<!-- 请假表单 -->
<div data-role="page" id="leaveform">
    <div data-role="header"> <!-- /cp/msg/emp/leave/home  rel='external' -->
        <a href="#leavehome" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>员工请假表单</h1>
        <a class="ui-btn ui-corner-all ui-shadow ui-icon-action ui-btn-icon-right"
           id="submitQj">提交申请</a>
    </div>

    <div data-role="main" class="ui-content">
        <div class="page__bd">
            <div class="weui-cells weui-cells_form"  ng-app="leaveFormApp" ng-controller="leaveForm">
                <div class="ui-grid-a">
                    <div class="ui-block-a">
                        <div class="weui-cell">
                            <div class="weui-cell__bd">
                                <div class="weui-cell__hd">
                                    <label class="weui-label">申请人姓名:</label>
                                </div>
                                <input class="weui-input" type="text" value="${user.A0101}" required="required"
                                       disabled="disabled" placeholder="请输入姓名..."/>
                            </div>
                        </div>
                    </div>
                    <div class="ui-block-b">
                        <div class="weui-cell">
                            <div class="weui-cell__bd">
                                <div class="weui-cell__hd">
                                    <label class="weui-label">审核人姓名:</label>
                                </div>
                                <input class="weui-input" name="shenheren" type="text"
                                       ng-model="shenheren" required="required"
                                       disabled="disabled" placeholder="请输入姓名..."/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="weui-cell">
                    <fieldset data-role="controlgroup" data-type="horizontal">
                        <legend>请假类型:<span style="color: red;display: none;" id="qjTypeSpan">请假类型不能为空...</span></legend>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2a" value="shijia" checked="checked" type="radio">
                        <label for="radio-choice-h-2a">事假</label>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2b" value="nianjia" type="radio">
                        <label for="radio-choice-h-2b">年假</label>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2c" value="chanjia" type="radio">
                        <label for="radio-choice-h-2c">产假</label>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2d" value="burujia" type="radio">
                        <label for="radio-choice-h-2d">哺乳假</label>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2e" value="hunjia" type="radio">
                        <label for="radio-choice-h-2e">婚假</label>
                        <input name="qjType" ng-model="qjType"
                               id="radio-choice-h-2f" value="sangjia" type="radio">
                        <label for="radio-choice-h-2f">丧假</label>
                    </fieldset>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd">
                            <legend>请假开始时间:<span style="color: red;display: none;" id="beginTimeSpan">请选择请假开始时间...</span></legend>
                            </div>
                        <input class="weui-input" name="beginTime" type="datetime-local" required="required"
                               data-clear-btn="true" ng-model="beginTime" placeholder="开始时间..."/>
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd"><legend>请假结束时间:
                            <span style="color: red;display: none;" id="endTimeSpan">请选择请假结束时间...</span></legend></div>
                        <input class="weui-input" name="endTime" type="datetime-local" required="required"
                               data-clear-btn="true" ng-model="endTime" placeholder="结束时间..."/>
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd"><legend>请假理由:
                            <span style="color: red;display: none;" id="reasonSpan">请假理由不能为空...</span></legend></div>
                        <textarea class="weui-textarea" name="reason" ng-model="reason" required="required" placeholder="请输入理由..."
                                  rows="4"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <script>
        var shr = null;
        var app = angular.module("leaveFormApp", []);
        $('#toastloadLeaveForm').fadeIn(1);
        app.controller("leaveForm", function ($scope, $http) {
            $http({
                method:'post',
                url:'/cp/msg/leave/get/checker',
                data:{
                    A0100:'${user.A0100}'
                },
                headers:{'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (data) {
                    return $.param(data);
                }
            }).success(function (data) {
                if (data != "null") {
                    var json = $.parseJSON(data);//审核人的所有信息
                    $scope.shenheren = json.A0101;
                    shr = json.A0100;
                }
                $('#toastloadLeaveForm').fadeOut(250);
            }).error(function (data) {
                alert("发生错误:"+data);
            });

            $("#submitQj").on('tap',function () {//提交请假单
                resetCheckData(qjType, beginTime, endTime, reason);
                var beginTime = $scope.beginTime;
                var qjType = $scope.qjType;
                var endTime = $scope.endTime;
                var reason = $scope.reason;

                var hasError = checkFormDataFormat(qjType, beginTime, endTime, reason);
                if(hasError != 0) {
                    hasError = 0;
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: "/cp/msg/submit/check/case",
                    cache: false,
                    data: {
                        qjType: qjType,
                        endTime: endTime,
                        reason: reason,
                        beginTime:beginTime
                    },
                    success: function (data, status) {//成功！
                        alert(data);
                        $('#toastloadLeaveForm').fadeOut(150);
                    },
                    error: function (data, status) {//失败！
                        weui.alert("发生错误，请联系管理员！");
                    }
                });
            });
        });

        function checkFormDataFormat(qjType, beginTime, endTime, reason) {
            var hasError = 0;
            if(beginTime == undefined) {
                $('#beginTimeSpan').fadeIn(100);
                hasError = 1;
            }
            if (endTime == undefined) {
                $('#endTimeSpan').fadeIn(100);
                hasError = 1;
            }
            if (reason == undefined) {
                $('#reasonSpan').fadeIn(100);
                hasError = 1;
            }
            if (qjType == undefined) {
                $('#qjTypeSpan').fadeIn(100);
                hasError = 1;
            }
            return hasError;
        }

        function resetCheckData(qjType, beginTime, endTime, reason) {
            $('#beginTimeSpan').fadeOut(100);
            $('#endTimeSpan').fadeOut(100);
            $('#reasonSpan').fadeOut(100);
            $('#qjTypeSpan').fadeOut(100);
        }

        $(document).on('pagebeforecreate','#leavehome',function () {

        });

    </script>

</body>
</html>


