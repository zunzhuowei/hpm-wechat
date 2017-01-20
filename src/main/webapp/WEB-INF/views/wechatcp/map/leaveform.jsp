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
    <title>请假</title>
</head>
<body>

<div data-role="page" id="leaveform">
    <div data-role="header"> <!-- javascript:history.go(-1); rel='external' -->
        <a href="/cp/msg/emp/leave/home" class="ui-btn ui-corner-all ui-shadow ui-icon-back ui-btn-icon-left">返回</a>
        <h1>员工请假表单</h1>
        <a href="#" class="ui-btn ui-corner-all ui-shadow ui-icon-action ui-btn-icon-right"
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
                        <legend>请假类型:</legend>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2a" value="shijia" type="radio">
                        <label for="radio-choice-h-2a">事假</label>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2b" value="nianjia" type="radio">
                        <label for="radio-choice-h-2b">年假</label>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2c" value="chanjia" type="radio">
                        <label for="radio-choice-h-2c">产假</label>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2d" value="burujia" type="radio">
                        <label for="radio-choice-h-2d">哺乳假</label>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2e" value="hunjia" type="radio">
                        <label for="radio-choice-h-2e">婚假</label>
                        <input name="qjType" ng-model="formDate.type"
                               id="radio-choice-h-2f" value="sangjia" type="radio">
                        <label for="radio-choice-h-2f">丧假</label>
                    </fieldset>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd"><label class="weui-label">请假开始时间:</label></div>
                        <input class="weui-input" name="beginTime" type="datetime-local" required="required"
                               data-clear-btn="true" ng-model="beginTime" placeholder="开始时间..."/>
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd"><label class="weui-label">请假结束时间:</label></div>
                        <input class="weui-input" name="endTime" type="datetime-local" required="required"
                               data-clear-btn="true" ng-model="endTime" placeholder="结束时间..."/>
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-cell__hd"><label class="weui-label">请假理由:</label></div>
                        <textarea class="weui-textarea" name="reason" required="required" placeholder="请输入理由..."
                                  rows="4"></textarea>
                    </div>
                </div>
            </div>
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

    <script>
        $("#leaveform").bind("pagebeforecreate",function(){

        });

        $(document).on('pagebeforecreate','#leaveform',function () {

        });
    </script>

</div>


</body>
</html>


