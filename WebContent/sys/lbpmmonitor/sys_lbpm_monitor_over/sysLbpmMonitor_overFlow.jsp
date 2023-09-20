<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.*" %>
<%@page import="net.sf.json.JSONObject" %>
<%
	//获取数据
	Map<String, Object> counts = (Map<String,Object>)request.getAttribute("counts");

	String[] workItemTimes = (String[])counts.get("workItem_times");
	Integer[] workItemCounts = (Integer[])counts.get("workItem_counts");
%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />
		<style type="text/css">
			html,body {
				height: 100%;
				background-color:white;
				overflow-x：hidden;
			}
			.over_view_container{
				min-width:800px;
				min-height:600px;
				height:100%;
			}
			.process_over_view{
				width:100%;
				box-sizing:border-box;
			}
			.view_up{
				height:33%;
				box-sizing: border-box;
				padding-top:20px;
			}
			.view_up .process_query_list{
				width:100%;
				height:90%;
				list-style: none;
			}
			.process-query-item{
				position:relative;
				float:left;
				width:25%;
				height:45%;
				/* box-sizing: border-box; */
				/* margin-bottom:8px; */
				padding-bottom:30px;
			}
			.item-link{
				position: relative;
				display:block;
				width:195px;
				text-decoration: none;
				margin:0 auto;
				padding-top:15px;
				box-shadow: 0px 1px 3px #c7c6c6;
				-webkit-box-shadow:0px 1px 3px #c7c6c6;
				-moz-box-shadow:0px 1px 3px #c7c6c6;
				box-sizing:border-box;
				border-radius: 3px;
				-webkit-border-radius:3px;
				-moz-border-radius:3px;
				outline: none;
				color: #000000;
				background-color:#ffffff;
				behavior: url(PIE.htc);
				min-height:84px;

			}
			.item-link:hover, .item-link:active, .item-link:link{
				color: #000000;
				text-decoration: none;
			}
			.item-icon{
				width:60px;
				height:60px;
				position: absolute;
				top:-10px;
				left:10px;
				background-color: #75D9FE;
				border-radius: 5px;
				box-shadow: 0px 0px 8px 0px #94e2ff;
			}
			.item-icon img{
				width:100%;
				position:absolute;
				top:0;
				left:0;
				border-radius: 5px;
			}
			.item-text{
				display:inline-block;
				max-width:50%;
				width:50%;
				position:relative;
				left:45%;
				vertical-align: middle;
			}
			.item-text>input{
				display:none;
			}
			.item-text .title{
				font-size:13px;
				margin-bottom:5px;
				color:#999999;
			}
			.item-text .count{
				font-size:25px;
				font-weight: bold;
			}
			.view_middle{
				height:22%;
			}
			.view_end{
				height:45%;
				box-sizing: border-box;
				padding-top:20px;
			}
			.view_end .statistical_chart{
				width:50%;
				height:100%;
				float:left;
			}
		</style>
		<script>
			Com_IncludeFile('echarts4.2.1.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
		</script>
		<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			//各类流程跳转
			function changeUrl(obj){
				var _treeFrame = window.parent.document.getElementsByName("treeFrame");
				var treeFrame=null;
				if(_treeFrame.length > 0){
					treeFrame = window.parent.document.getElementsByName("treeFrame")[0].contentWindow;
					var selectParentTitle = obj.getElementsByTagName("input")[0].value;
					var parentNode = treeFrame.Tree_GetChildByText(treeFrame.LKSTree.treeRoot,selectParentTitle);
					treeFrame.LKSTree.ClickNode(parentNode);
					var selectTitle = obj.getElementsByTagName("p")[0].innerText;
					var node = treeFrame.Tree_GetChildByText(parentNode,selectTitle);
					treeFrame.LKSTree.ClickNode(node);
				}else{
					var url =  '';
					var parentObj = $(obj).parent("li");
					if($(parentObj).hasClass("all_flow")){//
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all';
					}
					if($(parentObj).hasClass("run_flow")){//运行时流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=20;40&fdType=running';
					}
					if($(parentObj).hasClass("pause_flow")){//暂停的流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getPause';
					}
					if($(parentObj).hasClass("restart_flow")){//重启过的流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getProcessRestart';
					}
					if($(parentObj).hasClass("expired_flow")){//待审超时的流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getLimitExpired';
					}
					if($(parentObj).hasClass("error_flow")){//异常的流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=21&fdType=error';
					}
					if($(parentObj).hasClass("invaild_flow")){//处理人无效的流程
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=getInvalidHandler';
					}
					if($(parentObj).hasClass("queue_flow")){//流程队列
						url='${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_queue/index.jsp';
					}
					//Com_OpenWindow(url); //新窗口跳转
					window.location.href=url; //本窗口加载
				}
			}
		</script>
	</template:replace>
	<template:replace name="body">
		<% if(request.getParameter("s_path")!=null){ %>
		<span class="txtlistpath"><div class="lui_icon_s lui_icon_s_home" style="float: left;"></div><div style="float: left;"><bean:message key="page.curPath"/>${fn:escapeXml(param.s_path)}</div></span>
		<% } %>
		<div class="over_view_container">
			<div class="process_over_view view_up">
				<ul class="process_query_list">
					<li class="process-query-item all_flow">
						<a href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #F8CA43;box-shadow: 0px 0px 8px 0px #F8CA43;">
								<img src="./images/whole_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" class="parent" value="<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.wholeFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.all }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item run_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #E34E4B;box-shadow: 0px 0px 8px 0px #E34E4B;">
								<img src="./images/run_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.allFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.run }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item pause_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #69C381;box-shadow: 0px 0px 8px 0px #69C381;">
								<img src="./images/pause_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.pauseFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.pause }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item restart_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #6F9BE5;box-shadow: 0px 0px 8px 0px #6F9BE5;">
								<img src="./images/restart_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.processRestart" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.restart }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item expired_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #75D0F2;box-shadow: 0px 0px 8px 0px #75D0F2;">
								<img src="./images/expired_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.expiredFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.expired }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item error_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #EC73C1;box-shadow: 0px 0px 8px 0px #EC73C1;">
								<img src="./images/error_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processHandling" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.errorFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.error }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item invaild_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #E380E7;box-shadow: 0px 0px 8px 0px #E380E7;">
								<img src="./images/notvalid_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processHandling" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.tree.notValidFlow" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.invaild }</p>
							</div>
						</a>
					</li>
					<li class="process-query-item queue_flow">
						<a  href="javascript:void(0)" onclick="changeUrl(this)" class="item-link">
							<div class="item-icon" style="background-color: #4BDDB4;box-shadow: 0px 0px 8px 0px #4BDDB4;">
								<img src="./images/status_error_flow.png"/>
							</div>
							<div class="item-text">
								<input type="text" value="<bean:message key="sysLbpmMonitor.tree.processHandling" bundle="sys-lbpmmonitor" />" />
								<p class="title"><bean:message key="sysLbpmMonitor.queue" bundle="sys-lbpmmonitor" /></p>
								<p class="count">${counts.queue }</p>
							</div>
						</a>
					</li>
				</ul>
			</div>
			<div class="process_over_view view_middle" id="process_pie"></div>
			<div class="process_over_view view_end">
				<div id="process_queue" class="statistical_chart"></div>
				<div id="process_work_item" class="statistical_chart"></div>
			</div>
		</div>
		<script type="text/javascript">
			//饼状图
			var processPie = echarts.init(document.getElementById("process_pie"));
			option = {
				tooltip: {
					trigger: 'item',
					formatter: "{b}: {c} ({d}%)"
				},
				series: [
					{
						type:'pie',
						center: [
							"11.0%",
							"50%"
						],
						radius: ['70%', '90%'],
						avoidLabelOverlap: false,
						hoverAnimation: true,
						startAngle: 90,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: false
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:[
							{value:0,name:'<bean:message key="sysLbpmMonitor.tree.wholeFlow" bundle="sys-lbpmmonitor" />\n(${counts.all})',
								label:{
									normal:{
										show:true,
										textStyle:{
											fontSize:'11',
											color:'#000',
										}
									}
								},
								tooltip: {
									trigger: 'none',
									formatter: "{b}: {c} ({d}%)"
								},
							},
							{value:'${counts.all_run}', name:'<bean:message key="sysLbpmMonitor.tree.noFinishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#46B97C",
									}
								}
							},
							{value:'${counts.all_finish}', name:'<bean:message key="sysLbpmMonitor.tree.finishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#AAAAAA",
									}
								}
							},
						]
					},
					{
						type:'pie',
						center: [
							"35.0%",
							"50%"
						],
						radius: ['70%', '90%'],
						avoidLabelOverlap: false,
						hoverAnimation: true,
						startAngle: 90,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: false
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:[
							{value:0,name:'<bean:message key="sysLbpmMonitor.time.nearYearFlow" bundle="sys-lbpmmonitor" />\n(${counts.nearlyYear})',
								label:{
									normal:{
										show:true,
										textStyle:{
											fontSize:'11',
											color:'#000',
										}
									},
								},
								tooltip: {
									trigger: 'none',
									formatter: "{b}: {c} ({d}%)"
								},
							},
							{value:'${counts.nearlyYear_run}', name:'<bean:message key="sysLbpmMonitor.tree.noFinishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#46B97C",
									}
								}
							},
							{value:'${counts.nearlyYear_finish}', name:'<bean:message key="sysLbpmMonitor.tree.finishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#AAAAAA",
									}
								}
							},
						]
					},
					{
						type:'pie',
						center: [
							"60.0%",
							"50%"
						],
						radius: ['70%', '90%'],
						avoidLabelOverlap: false,
						hoverAnimation: true,
						startAngle: 90,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: false
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:[
							{value:0,name:'<bean:message key="sysLbpmMonitor.time.nearMonthFlow" bundle="sys-lbpmmonitor" />\n(${counts.nearlyMonth})',
								label:{
									normal:{
										show:true,
										textStyle:{
											fontSize:'11',
											color:'#000'
										}
									}
								},
								tooltip: {
									trigger: 'none',
									formatter: "{b}: {c} ({d}%)"
								},
							},
							{value:'${counts.nearlyMonth_run}', name:'<bean:message key="sysLbpmMonitor.tree.noFinishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#46B97C",
									}
								}
							},
							{value:'${counts.nearlyMonth_finish}', name:'<bean:message key="sysLbpmMonitor.tree.finishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#AAAAAA",
									}
								}
							},
						]
					},
					{
						type:'pie',
						center: [
							"85.0%",
							"50%"
						],
						radius: ['70%', '90%'],
						avoidLabelOverlap: false,
						hoverAnimation: true,
						startAngle: 90,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: false
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:[
							{value:0,name:'<bean:message key="sysLbpmMonitor.time.nearWeekFlow" bundle="sys-lbpmmonitor" />\n(${counts.nearlyWeek})',
								label:{
									normal:{
										show:true,
										textStyle:{
											fontSize:'11',
											color:'#000'
										}
									}
								},
								tooltip: {
									trigger: 'none',
									formatter: "{b}: {c} ({d}%)"
								},
							},
							{value:'${counts.nearlyWeek_run}', name:'<bean:message key="sysLbpmMonitor.tree.noFinishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#46B97C",
									}
								}
							},
							{value:'${counts.nearlyWeek_finish}', name:'<bean:message key="sysLbpmMonitor.tree.finishedFlow" bundle="sys-lbpmmonitor" />',
								itemStyle:{
									normal:{
										color:"#AAAAAA",
									}
								}
							},
						]
					}
				]
			};

			// 使用刚指定的配置项和数据显示图表
			processPie.setOption(option);

			//绑定点击事件，进行跳转
			processPie.on('click', function (handler,context){
				debugger;
				//阻止冒泡，过滤浏览器兼容
				var arg0 = arguments[0];
				arg0.preventDefault = true;
				arg0.stopPropagation = true;

				var seriesIndex = handler.seriesIndex;
				if(seriesIndex != null && typeof seriesIndex != "undefined"){
					var selectParentTitle = "<bean:message key="sysLbpmMonitor.tree.processQuery" bundle="sys-lbpmmonitor" />";
					var selectTitle = "<bean:message key="sysLbpmMonitor.tree.wholeFlow" bundle="sys-lbpmmonitor" />";
					var treeFrame_obj = window.parent.document.getElementsByName("treeFrame");
					var treeFrame=null;
					if(treeFrame_obj.length>0){
						//原逻辑保持不变
						treeFrame = window.parent.document.getElementsByName("treeFrame")[0].contentWindow;
						var parentNode = treeFrame.Tree_GetChildByText(treeFrame.LKSTree.treeRoot,selectParentTitle);
						treeFrame.LKSTree.ClickNode(parentNode);
						var node = treeFrame.Tree_GetChildByText(parentNode,selectTitle);
						treeFrame.LKSTree.ClickNode(node);

						var s_path = treeFrame.Tree_GetNodePath(node,"　>　",node.treeView.treeRoot,true);

						var url;
						if(seriesIndex == 0){
							url = '<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all" />';
						}else if(seriesIndex == 1){
							url = '<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=year" />';
						}else if(seriesIndex == 2){
							url = '<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=month" />';
						}else if(seriesIndex == 3){
							url = '<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=week" />';
						}
						//做个请求跳转
						if(url != null){
							window.location.href=url + "&s_path="+encodeURIComponent(s_path)+"&s_css=default";
						}
					}else{
						//没配或contentWindow为空，则直接新窗口跳转
						var url;
						if(seriesIndex == 0){
							url = '${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all';
						}else if(seriesIndex == 1){
							url = '${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=year';
						}else if(seriesIndex == 2){
							url = '${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=month';
						}else if(seriesIndex == 3){
							url = '${LUI_ContextPath}/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_clusterIndex.jsp?method=listChildren&fdStatus=all&timeFrame=week';
						}
						//Com_OpenWindow(url); //新窗口跳转
						window.location.href=url; //本窗口加载
					}
				}
			});
		</script>
		<script type="text/javascript">
			//队列心跳图数据初始化和更新
			var xQueueData = [];//x轴数据
			var yQueueData = [];//y轴数据
			var queueItemNum = 15;
			var interval = 2;
			var isFirst = true;
			function queue(){

				var DEFAULT_VERSION = 8.0;
				var ua = navigator.userAgent.toLowerCase();
				var isIE = ua.indexOf("msie")>-1;
				var safariVersion;
				if(isIE){
					safariVersion =  ua.match(/msie ([\d.]+)/)[1];
				}
				if(safariVersion <= DEFAULT_VERSION ){
					var closeDiv=$(".lui_data_form_one").parent().next().children().get(0);
					if(typeof(closeDiv)== undefined){

					}else{
						$(closeDiv).bind("click",function(){
							var div$One=$("#process_queue").children("div").get(0);
							$(div$One).show();
						});
					}

					var closeTwoDiv=$(".lui_data_form_two").parent().next().children().get(0);
					if(typeof(closeTwoDiv)== undefined){

					}else{
						$(closeTwoDiv).bind("click",function(){
							var div$One=$("#process_work_item").children("div").get(0);
							$(div$One).show();
						});
					}
				};


				//第一次初始化，要求x轴的数据完整填充
				if(isFirst){
					var date = new Date();
					var minute = parseInt(date.getMinutes());
					var second = parseInt(date.getSeconds());
					//格式
					var time = (Array(2).join(0)+minute).slice(-2) + ":" + (Array(2).join(0)+second).slice(-2);
					xQueueData.push(time);
					for(var i=1; i<queueItemNum; i++){
						second += interval;
						if(second >= 60){
							minute += 1;
							second -= 60;
						}
						if(minute >= 60){
							minute -= 60;
						}
						time = (Array(2).join(0)+minute).slice(-2) + ":" + (Array(2).join(0)+second).slice(-2);
						xQueueData.push(time);
					}
					isFirst = false;
				}
				var url = '<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do" />';
				var param = {
					method : "updateQueueCount"
				};
				$.post(url, param, function(data,status){
					//每次接收一个数据
					//请求成功
					var count = data.count;
					//判断数组是否已经存在规定个数
					if(yQueueData.length < queueItemNum){
						//未够数
						yQueueData.push(count);
					}else{
						//够数
						yQueueData.shift();
						yQueueData.push(count);

						time = xQueueData[xQueueData.length-1];
						minute = parseInt(time.split(":")[0]);
						second = parseInt(time.split(":")[1]);
						second += interval;
						if(second >= 60){
							minute += 1;
							second -= 60;
						}
						if(minute >= 60){
							minute -= 60;
						}
						time = (Array(2).join(0)+minute).slice(-2) + ":" + (Array(2).join(0)+second).slice(-2);
						xQueueData.shift();
						xQueueData.push(time);
					}
				},"json");
			}
		</script>
		<script type="text/javascript">
			//队列心跳图（系统压力图）
			var processQueue = echarts.init(document.getElementById("process_queue"));

			lineOption = {
				tooltip: {
					trigger: 'axis',
				},
				title: {
					text: '<bean:message key="sysLbpmMonitor.system.pressure" bundle="sys-lbpmmonitor" />',
					subtext: '<bean:message key="sysLbpmMonitor.system.pressure.describe" bundle="sys-lbpmmonitor" />',
					x: 'center',
					padding:8
				},
				xAxis: {
					type: 'category',
					data: xQueueData,
					axisLine:{
						symbol:['none', 'arrow'],
						symbolSize:[10, 12],
						symbolOffset:[0, 0]
					},
					axisTick: {
						alignWithLabel: true
					},
					axisLabel:{
						rotate:45
					}
				},
				yAxis: {
					type: 'value',
					splitNumber:10,//动态设置分隔间断
					min:0,
					axisLine:{
						symbol:['none', 'arrow'],
						symbolSize:[10, 12],
						symbolOffset:[0, 0]
					},
				},
				toolbox: {
					show: true,
					right:30,
					feature: {
						dataView: {
							title:'<bean:message key="sysLbpmMonitor.system.dataView" bundle="sys-lbpmmonitor" />',
							lang:['<bean:message key="sysLbpmMonitor.system.dataView" bundle="sys-lbpmmonitor" />','<bean:message key="sysLbpmMonitor.system.dataView.close" bundle="sys-lbpmmonitor" />'],
							readOnly: true,
							optionToContent: function(opt) {

								var DEFAULT_VERSION = 8.0;
								var ua = navigator.userAgent.toLowerCase();
								var isIE = ua.indexOf("msie")>-1;
								var safariVersion;
								if(isIE){
									safariVersion =  ua.match(/msie ([\d.]+)/)[1];
								}
								if(safariVersion <= DEFAULT_VERSION ){
									var div$One=$("#process_queue").children("div").get(0);
									$(div$One).hide();
								};

								var axisData = opt.xAxis[0].data;
								var series = opt.series;
								var table = '<table class="lui_data_form_one" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
										+ '<td><bean:message key="sysLbpmMonitor.system.pressure.xaxis" bundle="sys-lbpmmonitor" /></td>'
										+ '<td>' + series[0].name + '</td>'
										+ '</tr>';
								for (var i = 0, l = axisData.length; i < l; i++) {
									table += '<tr>'
											+ '<td>' + axisData[i] + '</td>'
											+ '<td>' + (series[0].data[i]==null ? "":series[0].data[i]) + '</td>'
											+ '</tr>';
								}
								table += '</tbody></table>';
								return table;
							}
						}
					}
				},
				series: [{
					name:'<bean:message key="sysLbpmMonitor.system.pressure.yaxis" bundle="sys-lbpmmonitor" />',
					data: yQueueData,
					type: 'line',
					smooth: true
				}]
			};

			//查询队列数据
			queue();
			processQueue.setOption(lineOption);
			setInterval(function(){
				queue();
				// 使用刚指定的配置项和数据显示图表
				processQueue.setOption(lineOption);
			},2000);
		</script>
		<script type="text/javascript">
			//系统负载图
			var processWorkItem = echarts.init(document.getElementById("process_work_item"));
			var xData = [];
			var yData = [];
			<%
				for(int i=0; i<workItemTimes.length; i++){
			%>
			xData.unshift('<%=workItemTimes[i]%>');
			<%
				}
				for(int i=0; i<workItemCounts.length; i++){
					String count = workItemCounts[i] + "";
			%>
			yData.unshift('<%=count%>');
			<%
				}
			%>
			barOption = {
				color: ['#3398DB'],
				tooltip : {
					trigger: 'axis',
					axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				title: {
					text: '<bean:message key="sysLbpmMonitor.system.load" bundle="sys-lbpmmonitor" />',
					subtext: '<bean:message key="sysLbpmMonitor.system.load.describe" bundle="sys-lbpmmonitor" />',
					x: 'center',
					padding:8,
				},
				xAxis : [
					{
						type : 'category',
						data : xData,
						axisTick: {
							alignWithLabel: true
						},
						axisLine:{
							symbol:['none', 'arrow'],
							symbolSize:[10, 12],
							symbolOffset:[0, 0]
						},
						axisLabel:{
							rotate:45
						}
					}
				],
				yAxis : {
					type: 'value',
					splitNumber:10,//动态设置分隔间断
					min:0,
					axisLine:{
						symbol:['none', 'arrow'],
						symbolSize:[10, 12],
						symbolOffset:[0, 0]
					},
				},
				toolbox: {
					show: true,
					right:30,
					feature: {
						dataView: {
							title:'<bean:message key="sysLbpmMonitor.system.dataView" bundle="sys-lbpmmonitor" />',
							lang:['<bean:message key="sysLbpmMonitor.system.dataView" bundle="sys-lbpmmonitor" />','<bean:message key="sysLbpmMonitor.system.dataView.close" bundle="sys-lbpmmonitor" />'],
							readOnly: true,
							optionToContent: function(opt) {

								var DEFAULT_VERSION = 8.0;
								var ua = navigator.userAgent.toLowerCase();
								var isIE = ua.indexOf("msie")>-1;
								var safariVersion;
								if(isIE){
									safariVersion =  ua.match(/msie ([\d.]+)/)[1];
								}
								if(safariVersion <= DEFAULT_VERSION ){
									var div$One=$("#process_work_item").children("div").get(0);
									$(div$One).hide();
								};


								var axisData = opt.xAxis[0].data;
								var series = opt.series;
								var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
										+ '<td><bean:message key="sysLbpmMonitor.system.load.xaxis" bundle="sys-lbpmmonitor" /></td>'
										+ '<td>' + series[0].name + '</td>'
										+ '</tr>';
								for (var i = 0, l = axisData.length; i < l; i++) {
									table += '<tr>'
											+ '<td>' + axisData[i] + '</td>'
											+ '<td>' + (series[0].data[i]==null ? "":series[0].data[i]) + '</td>'
											+ '</tr>';
								}
								table += '</tbody></table>';
								return table;
							}
						}
					}
				},
				series: [{
					name:'<bean:message key="sysLbpmMonitor.system.load.yaxis" bundle="sys-lbpmmonitor" />',
					type:'bar',
					barWidth: '50%',
					data:yData
				}]
			};

			// 使用刚指定的配置项和数据显示图表
			processWorkItem.setOption(barOption);
		</script>
		<script type="text/javascript">
			$(window).resize(function(){
				processPie.resize();
				processQueue.resize();
				processWorkItem.resize();
			});
		</script>
	</template:replace>
</template:include>