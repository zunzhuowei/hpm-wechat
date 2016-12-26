<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="viewport" content="width=device-width, initial-scale=1 " >
<!--   		,maximum-scale=1.0,ouser-scalable=no -->
<title>考勤</title>
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
						<center>我的考勤</center>
					</h3>
				</a>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="list-group panel-group" id="accordion">
				<a class="list-group-item" data-toggle="collapse" data-parent="#accordion" 
				   href="#collapse2">
					<center>请假</center>
				</a>
				<div id="collapse2" class="panel-collapse collapse">
					<div class="panel-body table-responsive">
						
						<table class="table">
							<thead>
								<tr>
									<th>请假类型</th>
									<th>请假时间</th>
									<th>结束时间</th>
									<th>天数</th>
								</tr>
							</thead>
							<tbody>
								<tr class="active">
									<td>事假</td>
									<td>2016-11-03 16:40</td>
									<td>2016-11-05 18:40</td>
									<td>2</td>
								</tr>
								<tr class="success">
									<td>病假</td>
									<td>2016-11-09 16:40</td>
									<td>2016-11-10 16:40</td>
									<td>1</td>
								</tr>
								<tr class="warning">
									<td>事假</td>
									<td>2016-11-17 16:40</td>
									<td>2016-11-21 16:40</td>
									<td>4</td>
								</tr>
								<tr class="danger">
									<td>病假</td>
									<td>2016-11-26 16:40</td>
									<td>2016-11-27 16:40</td>
									<td>1</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<a class="list-group-item" data-toggle="collapse" data-parent="#accordion" 
				   href="#collapse3">
					<center>加班</center>
				</a>
				<div id="collapse3" class="panel-collapse collapse">
					<div class="panel-body table-responsive">
						<table class="table">
							<thead>
								<tr>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>时长</th>
								</tr>
							</thead>
							<tbody>
								<tr class="active">
									<td>2016-11-01 18:00</td>
									<td>2016-11-01 20:00</td>
									<td>2</td>
								</tr>
								<tr class="success">
									<td>2016-11-02 18:00</td>
									<td>2016-11-02 21:00</td>
									<td>3</td>
								</tr>
								<tr class="warning">
									<td>2016-11-15 18:00</td>
									<td>2016-11-15 19:00</td>
									<td>1</td>
								</tr>
								<tr class="danger">
									<td>2016-11-28 18:00</td>
									<td>2016-11-29 04:00</td>
									<td>10</td>
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
