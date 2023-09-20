<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="help_title">图表配置样例</div>
<div class="help_content">
	<ul>
		<li id="sample1Help"><b>样例1</b>：
			<ul>
				<li>
				需要从多张表中获取到相关数据，然后进行数据补全、分组参数、堆积或参数深化等 操作，并展示出统计图表的效果。<br>
				实例：新建用户访问趋势的图表，需要查询 2 张表，双 Y 轴显示，需要统计某个时间段的用户访问趋势。<br>
				【数据集配置】，这里需要查询 2 张表，均返回数组，一个是访问次数，一个是登录次数。这 2 个 SQL 语句分别如下： <br>
				select count(*) v_count1, convert(varchar(20), fd_created, 23) v_create1 from sys_log_app_bak group by convert(varchar(20), fd_created, 23) order by v_create1；<br>
				select count(*) v_count2, convert(varchar(20), fd_create_time, 23) v_create2 from sys_log_login_bak group by convert(varchar(20), fd_create_time, 23)；<br>
				<img src="images/sample/chart1.png"><br>
				【图表数据配置】，这里是双 Y 轴显示，把其中一个勾选“右 Y 抽”，此外还用到数据补全，如登录次数， 按 0 补全，这样统计的图表就不会中断。<br>
				<img src="images/sample/chart2.png"><br>
				【图表选项配置】，数据样例。<br>
				<img src="images/sample/chart3.png"><br>
					点击【提交】按钮后，可展示统计图表的效果，如下图所示。<br>
				<img src="images/sample/chart4.png"><br>
				</li>
			</ul>
		</li>
	</ul>
</div>