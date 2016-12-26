<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="viewport" content="width=device-width, initial-scale=1 " >
<!--   		,maximum-scale=1.0,ouser-scalable=no -->
<title>工时</title>
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
						<center>工时审批</center>
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
					<center>2016年11月 招商银行项目工时审批</center>
				</a>
				<div id="collapse2" class="panel-collapse collapse">
					<div class="panel-body table-responsive">
						<p align="center">
							<input class="btn btn-success" type="button" value="提交">
							<input class="btn btn-warning" type="button" value="退回">
						</p>
						<table class="table">
							<thead>
								<tr>
									<th>姓名</th>
									<th>全勤</th>
									<th>工时</th>
								</tr>
							</thead>
							<tbody>
								<tr class="active">
									<td>吴朝选</td>
									<td>27</td>
									<td>24</td>
								</tr>
								<tr class="success">
									<td>曾志敏</td>
									<td>27</td>
									<td>26.5</td>
								</tr>
								<tr class="warning">
									<td>陈冠希</td>
									<td>27</td>
									<td>23</td>
								</tr>
								<tr class="danger">
									<td>围桌尊</td>
									<td>27</td>
									<td>23.5</td>
								</tr>
								<tr class="active">
									<td>吴彦祖</td>
									<td>27</td>
									<td>23.3</td>
								</tr>
								<tr class="success">
									<td>刘俊宏</td>
									<td>30</td>
									<td>3</td>
								</tr>
								<tr class="warning">
									<td>古天乐</td>
									<td>21.3</td>
									<td>21.3</td>
								</tr>
								<tr class="danger">
									<td>罗红淼</td>
									<td>21.3</td>
									<td>12.3</td>
								</tr>
								<tr class="active">
									<td>胡歌</td>
									<td>31</td>
									<td>30</td>
								</tr>
								<tr class="success">
									<td>王冰</td>
									<td>23.4</td>
									<td>23.4</td>
								</tr>
								<tr class="warning">
									<td>xxx</td>
									<td>2.34</td>
									<td>2.34</td>
								</tr>
								<tr class="danger">
									<td>xxx</td>
									<td>21.4</td>
									<td>20</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<a class="list-group-item" data-toggle="collapse" data-parent="#accordion" 
				   href="#collapse3">
					<center>2016年11月 腾讯信息项目工时审批</center>
				</a>
				<div id="collapse3" class="panel-collapse collapse">
					<div class="panel-body table-responsive">
						<p align="center">
							<input class="btn btn-success" type="button" value="提交">
							<input class="btn btn-warning" type="button" value="退回">
						</p>
						<table class="table">
							<thead>
								<tr>
									<th>姓名</th>
									<th>全勤</th>
									<th>工时</th>
								</tr>
							</thead>
							<tbody>
								<tr class="active">
									<td>吴朝选</td>
									<td>27</td>
									<td>24</td>
								</tr>
								<tr class="success">
									<td>曾志敏</td>
									<td>27</td>
									<td>26.5</td>
								</tr>
								<tr class="warning">
									<td>陈冠希</td>
									<td>27</td>
									<td>23</td>
								</tr>
								<tr class="danger">
									<td>围桌尊</td>
									<td>27</td>
									<td>23.5</td>
								</tr>
								<tr class="active">
									<td>吴彦祖</td>
									<td>27</td>
									<td>23.3</td>
								</tr>
								<tr class="success">
									<td>刘俊宏</td>
									<td>30</td>
									<td>3</td>
								</tr>
								<tr class="warning">
									<td>古天乐</td>
									<td>21.3</td>
									<td>21.3</td>
								</tr>
								<tr class="danger">
									<td>罗红淼</td>
									<td>21.3</td>
									<td>12.3</td>
								</tr>
								<tr class="active">
									<td>胡歌</td>
									<td>31</td>
									<td>30</td>
								</tr>
								<tr class="success">
									<td>王冰</td>
									<td>23.4</td>
									<td>23.4</td>
								</tr>
								<tr class="warning">
									<td>xxx</td>
									<td>2.34</td>
									<td>2.34</td>
								</tr>
								<tr class="danger">
									<td>xxx</td>
									<td>21.4</td>
									<td>20</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<a class="list-group-item" data-toggle="collapse" data-parent="#accordion" 
				   href="#collapse4">
					<center>2016年11月 东方航空项目工时审批</center>
				</a>
				<div id="collapse4" class="panel-collapse collapse">
					<div class="panel-body table-responsive">
						<p align="center">
							<input class="btn btn-success" type="button" value="提交">
							<input class="btn btn-warning" type="button" value="退回">
						</p>
						<table class="table">
							<thead>
								<tr>
									<th>姓名</th>
									<th>全勤</th>
									<th>工时</th>
								</tr>
							</thead>
							<tbody>
								<tr class="active">
									<td>吴朝选</td>
									<td>27</td>
									<td>24</td>
								</tr>
								<tr class="success">
									<td>曾志敏</td>
									<td>27</td>
									<td>26.5</td>
								</tr>
								<tr class="warning">
									<td>陈冠希</td>
									<td>27</td>
									<td>23</td>
								</tr>
								<tr class="danger">
									<td>围桌尊</td>
									<td>27</td>
									<td>23.5</td>
								</tr>
								<tr class="active">
									<td>吴彦祖</td>
									<td>27</td>
									<td>23.3</td>
								</tr>
								<tr class="success">
									<td>刘俊宏</td>
									<td>30</td>
									<td>3</td>
								</tr>
								<tr class="warning">
									<td>古天乐</td>
									<td>21.3</td>
									<td>21.3</td>
								</tr>
								<tr class="danger">
									<td>罗红淼</td>
									<td>21.3</td>
									<td>12.3</td>
								</tr>
								<tr class="active">
									<td>胡歌</td>
									<td>31</td>
									<td>30</td>
								</tr>
								<tr class="success">
									<td>王冰</td>
									<td>23.4</td>
									<td>23.4</td>
								</tr>
								<tr class="warning">
									<td>xxx</td>
									<td>2.34</td>
									<td>2.34</td>
								</tr>
								<tr class="danger">
									<td>xxx</td>
									<td>21.4</td>
									<td>20</td>
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
