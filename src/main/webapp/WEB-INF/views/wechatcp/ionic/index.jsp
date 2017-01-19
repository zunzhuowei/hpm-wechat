<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%--<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">--%>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="http://www.runoob.com/static/ionic/css/ionic.min.css" rel="stylesheet">
    <script src="http://www.runoob.com/static/ionic/js/ionic.bundle.min.js"></script>
    <title>ionic</title>
</head>

<body ng-app="todo" ng-controller="TodoCtrl">
<ion-side-menus>

    <!-- 中心内容 -->
    <ion-side-menu-content>
        <ion-header-bar class="bar-dark">
            <h1 class="title">Todo</h1>
        </ion-header-bar>
        <ion-content>
            <!-- 列表 -->
            <ion-list>
                <ion-item ng-repeat="task in tasks">
                    {{task.title}}
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-side-menu-content>

    <!-- 左侧菜单 -->
    <ion-side-menu side="left">
        <ion-header-bar class="bar-dark">
            <h1 class="title">Projects</h1>
        </ion-header-bar>
    </ion-side-menu>

</ion-side-menus>
<script>
    angular.module('todo', ['ionic'])
            .controller('TodoCtrl', function($scope) {
                $scope.tasks = [
                    { title: '菜鸟教程aaa' },
                    { title: 'www.runoob.com' },
                    { title: '菜鸟教程' },
                    { title: 'www.runoob.com' }
                ];
            });
</script>
</body>
</html>
