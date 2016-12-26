<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1 " >
    <!--   		,maximum-scale=1.0,ouser-scalable=no -->
    <title>薪酬</title>
    <script src="<@s.url '/js/jquery-1.8.3.min.js'/>" type="text/javascript"></script>
    <script src="<@s.url '/marry/js/bootstrap.min.js'/>" type="text/javascript"></script>
    <link rel="stylesheet" href="<@s.url '/marry/css/bootstrap.min.css'/>">
    <style type="text/css">
        .png{
            width:50px;
            height:50px;
        }
        .png1{
            width:47px;
            height:47px;
        }
        .hcenter{
            height:40px;
            line-height:40px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="list-group">
            <a class="list-group-item active">
                <input type="button" class="btn btn-primary pull-left" value="返回"/>
                <input type="button" class="btn btn-primary pull-right" value="刷新"/>
                <h3 class="list-group-item-heading">
                    <center>我的薪酬</center>
                </h3>
            </a>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="list-group panel-group" id="accordion">
        <#--<a class="list-group-item" data-toggle="collapse" data-parent="#accordion"
           href="#collapse1">
            1.基本信息
        </a>
        <div id="collapse1" class="panel-collapse collapse">
            <div class="panel-body">
                <p>员工姓名：xxx</p>
                <p>部门：运营中心-实施部</p>
                <p>岗位：交付经理</p>
                <p>发放日期：2016-10-15</p>
            </div>
        </div>-->
            <a class="list-group-item" data-toggle="collapse" data-parent="#accordion"
               href="#collapse3">
                2.应发
            </a>
            <div id="collapse3" class="panel-collapse collapse">
                <div class="panel-body table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>费用</th>
                            <th>金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="active">
                            <td>固定工资（不包括车补）</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>浮动工资</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>补发上月工资</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>固定工资2</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>车补</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>加班费合计</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>年终结算加班费</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>其他补税前</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>其他补税后</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>笔记本租赁费</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>独生子女费</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>高温补贴</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>驻外津贴</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>客服补贴</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>节日补贴</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>夜班补贴</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>项目奖金</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>伯乐奖</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>采购提成A</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>采购提成B</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>销售提成奖A</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>销售提成奖B</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>应发合计</td>
                            <td>312534</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <a class="list-group-item" data-toggle="collapse" data-parent="#accordion"
               href="#collapse4">
                3.应扣
            </a>
            <div id="collapse4" class="panel-collapse collapse">
                <div class="panel-body table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>费用</th>
                            <th>金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="active">
                            <td>扣考勤工资合计</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>社保个人合计</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>住房公积金个人</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>商业保险个人</td>
                            <td>21423</td>
                        </tr>
                        <tr class="active">
                            <td>工会费</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>其他扣税前</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>其他扣税后</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>应扣项目合计</td>
                            <td>21423</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <a class="list-group-item" data-toggle="collapse" data-parent="#accordion"
               href="#collapse5">
                4.实发
            </a>
            <div id="collapse5" class="panel-collapse collapse">
                <div class="panel-body table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>费用</th>
                            <th>金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="active">
                            <td>实发（工资）</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>所得税（工资）</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>基本工资（仅用作计算加班费）</td>
                            <td>2134</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <a class="list-group-item" data-toggle="collapse" data-parent="#accordion"
               href="#collapse6">
                5.年终奖
            </a>
            <div id="collapse6" class="panel-collapse collapse">
                <div class="panel-body">
                    <table class="table  table-responsive">
                        <thead>
                        <tr>
                            <th>费用</th>
                            <th>金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="active">
                            <td>年终奖1</td>
                            <td>2314.23</td>
                        </tr>
                        <tr class="success">
                            <td>年终奖1税金</td>
                            <td>1234</td>
                        </tr>
                        <tr class="warning">
                            <td>年终奖1（实发）</td>
                            <td>2134</td>
                        </tr>
                        <tr class="danger">
                            <td>年终奖2</td>
                            <td>2134</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>






</body>
</html>
