<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.system.load" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=systemLoadAnalysis', {
				format:function(data){
					var items = data.datas;
					delete data.datas;
					var nodes = [];
					var counts = {};
					var avgTimes = {};
					var maxHour = -1;
					var minHour = -1;
					for(var i=0; i<items.length; i++){
						var item = items[i]; // node,time,count,avgTime
						if($.inArray(item.node, nodes)==-1){
							nodes.push(item.node);
							counts[item.node] = [];
							avgTimes[item.node] = [];
						}
						if(maxHour < item.time){
							maxHour = item.time;
						}
						if(minHour > item.time || minHour==-1){
							minHour = item.time;
						}
						var time = new Date(item.time*3600000);
						counts[item.node].push([time,item.count]);
						avgTimes[item.node].push([time,item.avgTime]);
					}
					window.chartData = {
							hours : maxHour - minHour,
							nodes : nodes,
							counts : counts,
							avgTimes : avgTimes
						};
					return data;
				}
			});
			source.bindRender(new sui.MonthRender('#month_select'));
			source.bindRender({
					draw : function(){
						if(chartData.nodes.length==0){
							return;
						}
						drawRelationChart(chartData.nodes[0]);
						var dom = $('#relation_select_div');
						if(chartData.nodes.length==1){
							dom.hide();
							$('#count_chart_div').hide();
							$('#avgTime_chart_div').hide();
							return;
						}
						var series1 = [];
						var series2 = [];
						var html = '<bean:message key="sys.profile.behavior.system.load.select.node" bundle="sys-profile-behavior" />';
						for(var i=0; i<chartData.nodes.length; i++){
							var node = chartData.nodes[i];
							html += '<label style="margin:0px 5px;"><input type="radio" name="relation_select" value="'+node+'" '+(i==0?'checked':'')
								+' onclick="drawRelationChart(this.value);">&nbsp;'+node+'</label>';
							series1.push({name:node, data:chartData.counts[node]});
							series2.push({name:node, yAxisIndex:1, data:chartData.avgTimes[node]});
						}
						dom.show();
						dom.html(html);
						drawChart({
							title : '<bean:message key="sys.profile.behavior.system.load.contrast1" bundle="sys-profile-behavior" />',
							legend : chartData.nodes,
							series : series1,
							key : 'count'
						});
						drawChart({
							title : '<bean:message key="sys.profile.behavior.system.load.contrast2" bundle="sys-profile-behavior" />',
							legend : chartData.nodes,
							series : series2,
							key : 'avgTime'
						});
					}
				});
			function drawRelationChart(value){
				var title = chartData.nodes.length>1?('(<bean:message key="sys.profile.behavior.system.load.node" bundle="sys-profile-behavior" />'+value+')'):'';
				drawChart({
					title:'<bean:message key="sys.profile.behavior.system.load.text1" bundle="sys-profile-behavior" />'+title,
					legend:['<bean:message key="sys.profile.behavior.system.load.text2" bundle="sys-profile-behavior" />', '<bean:message key="sys.profile.behavior.system.load.text3" bundle="sys-profile-behavior" />'],
					series : [{
							name:'<bean:message key="sys.profile.behavior.system.load.text2" bundle="sys-profile-behavior" />',
							data:chartData.counts[value]
						},{
							name:'<bean:message key="sys.profile.behavior.system.load.text3" bundle="sys-profile-behavior" />',
							yAxisIndex:1,
							data:chartData.avgTimes[value]
						}],
					key : 'relation',
					click : function(data){
						if(data.value.length){
							var time = data.value[0].getTime();
							var url = sui.url('${LUI_ContextPath}/sys/profile/maintenance/behavior/time.jsp', 'time', time);
							window.open(url, '_self');
						}
					}
				});
			}
			function drawChart(config){
				for(var i=0; i<config.series.length; i++){
					config.series[i].type = 'line';
					config.series[i].markPoint = {data:[{type:'max',name:'<bean:message key="sys.profile.behavior.system.load.max" bundle="sys-profile-behavior" />'}]};
					config.series[i].smooth = true;
					config.series[i].showAllSymbol = true;
					config.series[i].symbolSize = 2;
				}
				var zoomStart = 0;
				var hours = chartData.hours;
				if(hours > 24*7){
					zoomStart = 100 - Math.round(24*700.0/hours);
				}
				var option = {
						title: {
							text: config.title,
							x: 'center'
						},
						tooltip: { 
							trigger: 'item',
							formatter : function (params) {
								if(params.value.length==null){
									return params.series.name + '<br><bean:message key="sys.profile.behavior.system.load.max" bundle="sys-profile-behavior" />:'+params.value + (params.series.yAxisIndex==1?'<bean:message key="sys.profile.behavior.system.load.millisecond" bundle="sys-profile-behavior" />':'');
								}
								var date = new Date(params.value[0]);
								date = date.getFullYear() + '-'
									+ (date.getMonth() + 1) + '-'
									+ date.getDate() + ' '
									+ date.getHours() + ':00';
								var info = date + '<br/>' + params.series.name + ' : '+params.value[1] + (params.series.yAxisIndex==1?'<bean:message key="sys.profile.behavior.system.load.millisecond" bundle="sys-profile-behavior" />':'');
								if(config.click){
									info += '<br><bean:message key="sys.profile.behavior.system.load.text4" bundle="sys-profile-behavior" />';
								}
								return info;
				        	}
			        	},
						legend: {
							x : 'left',
							data: config.legend
						},
						dataZoom : {
							show : true,
							start : zoomStart
						},
						xAxis: [{
							type: 'time',
							axisLabel : {
								formatter : 'MM-dd hh:00'
							}
						}],
						yAxis: [
							{name:'<bean:message key="sys.profile.behavior.system.load.text2" bundle="sys-profile-behavior" />', type: 'value', splitNumber:4},
							{name:'<bean:message key="sys.profile.behavior.system.load.text3" bundle="sys-profile-behavior" />(ms)', type: 'value', axisLabel:{formatter:'{value} ms'}, splitNumber:4}
						],
						toolbox: {
							show : true,
							feature : {
								restore : {show: true}
							}
						},
						calculable : true,
						series: config.series
					};
				var dom = $('#'+config.key+'_chart_div');
				dom.show();
				var chart = echarts.init(dom[0]);
				if(config.click){
					option.title.subtext = '<bean:message key="sys.profile.behavior.system.load.text5" bundle="sys-profile-behavior" />';
					chart.on('click', config.click);
				}
				chart.setOption(option);
			}
	
			$(function(){
				source.load();
				menu_focus("0__system");
			});
		</script>
	</template:replace>
	<template:replace name="filter">
		<div class="beh-container-heading">
			<div class="criteria_frame">
				<span class="criteria_title"><bean:message key="sys.profile.behavior.time.slot" bundle="sys-profile-behavior" /></span>
				<span id="month_select"></span>
			</div>
		</div>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.system.load" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
            	<center>
		            <div id="relation_select_div" style="width:980px; margin-top: 10px; display:none;"></div>
					<div id="relation_chart_div" style="width:980px; height:400px; margin-top: 10px;"></div>
					<div id="count_chart_div" style="width:980px; height:400px; margin-top: 30px; display:none;"></div>
					<div id="avgTime_chart_div" style="width:980px; height:400px; margin-top: 30px; display:none;"></div>
				</center>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>