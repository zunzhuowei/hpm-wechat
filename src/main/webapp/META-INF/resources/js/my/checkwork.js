/**
 * Created by Think on 2017/1/7.
 */


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
        $('#alreadyCheckIn').css("display", "inherit");
    },
    handle2:function () {//未签到
        $("#checkIn").css("display", "inherit");//加载完成之后，显示签到按钮
    },
    handle3:function () {//已签退
        $('#alreadyCheckOut').css("display", "inherit");
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
        },
        error: function (data, status) {//失败！
            alert("签到失败，错误代码："+data);
        }
    });
    $('.js_dialog').fadeOut(200);//隐藏dialog
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
        var $toast = $('#notOpentToast');
        if ($toast.css('display') != 'none') return;
        $toast.fadeIn(100);
        setTimeout(function () {
            $toast.fadeOut(100);
        }, 1500);
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
        var $toast = $('#hasCheckToast');
        if ($toast.css('display') != 'none') return;
        $toast.fadeIn(100);
        setTimeout(function () {
            $toast.fadeOut(100);
        }, 1500);//屏蔽罩
        //alert("当前时间未开启签到/签退,不能签到！");
    },
    kerror:function () {
        alert("系统异常，请联系系统管理员！");
    }
};
//使用方法示例
//responseSignIn.execute("01");
