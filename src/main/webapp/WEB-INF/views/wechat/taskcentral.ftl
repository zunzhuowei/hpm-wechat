<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="viewport" content="width=device-width, initial-scale=1 " >
<!--   		,maximum-scale=1.0,ouser-scalable=no -->
<title>待办</title>
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
						<center>任务中心</center>
					</h3>
				</a>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="list-group">
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">4人</span>
					1.增加缴费（保险）
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">13人</span>
					2.工资及社保评定表
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">5人</span>
					3.入职审批
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">4人</span>
					4.员工入项
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">2人</span>
					5.人员调动
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">1人</span>
					6.离职申请
				</a>
				<a href="#" class="list-group-item">
					<span class="badge" style="background-color: #FF5809">2个</span>
					7.合同签订
				</a>
			</div>
		</div>
	</div>
</body>
</html>
