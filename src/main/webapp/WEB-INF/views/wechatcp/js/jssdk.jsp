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
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
    <link href="/css/weui.css" rel="stylesheet" type="text/css"/>
    <title>jssdk_test</title>
</head>
<body>

<h1>test for jssdk</h1>
<button id="sao" type="button">选择相片</button>
<button id="qq" type="button">分享qq</button>
<script>

    //    private String appid;
    //    private String noncestr;
    //    private long timestamp;
    //    private String url;
    //    private String signature;
    $(function () {
                var appId = '${signature.appid}';
                var nonceStr = '${signature.noncestr}';
                var timestamp = '${signature.timestamp}';
                var signature = '${signature.signature}';
                wx.config({
                    debug: true,//debug: true,
                    appId: appId,
                    timestamp: timestamp,
                    nonceStr: nonceStr,
                    signature: signature,
                    jsApiList: [
                        'checkJsApi',
                        'chooseImage',
                        'openLocation',
                        'getLocation',
                        'onMenuShareTimeline',
                        'onMenuShareAppMessage',
                        'onMenuShareQQ'
                    ]
                });

                //在这里写微信扫一扫的接口
                $("#sao").bind("click", function () {
                    wx.chooseImage({
                        count: 9, // 默认9
                        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                        success: function (res) {
                            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                        }
                    });

                });

            }
    );

    wx.ready(function () {
        // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
        // alert("1230k");
        wx.onMenuShareAppMessage({
            title: '标题',
            desc: '描述',
            link: 'www.baidu.com',
            imgUrl: 'http://upload.chinaz.com/2015/0923/1442988834858.jpg',
            trigger: function (res) {
                // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                // alert('用户点击发送给朋友');
            },
            success: function (res) {
                // alert('已分享');
            },
            cancel: function (res) {
                // alert('已取消');
            },
            fail: function (res) {
                // alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareTimeline({
            title: '标题1',
            link: 'www.baidu.com',
            imgUrl: 'http://upload.chinaz.com/2015/0923/1442988834858.jpg',
            trigger: function (res) {
                // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                // alert('用户点击分享到朋友圈');
            },
            success: function (res) {
                // alert('已分享');
            },
            cancel: function (res) {
                // alert('已取消');
            },
            fail: function (res) {
                // alert(JSON.stringify(res));
            }
        });

        wx.onMenuShareQQ({
            title: 'qq标题', // 分享标题
            desc: 'qq描述', // 分享描述
            link: 'www.baidu.com', // 分享链接
            imgUrl: 'http://upload.chinaz.com/2015/0923/1442988834858.jpg', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                alert("成功分享qq");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                alert("取消分享qq");
            }
        });
    });

    wx.error(function (res) {
        alert(res.errMsg);
    });

</script>
</body>
</html>


