<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.response.speed" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var queryTime = '<c:out value="${param.time}"/>';
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=pathAnalysis', {
				format : function(data){
					var items = data.datas;
					if(items.length==0){
						return data;
					}
					data.count = items[0].count;
					data.avgTime = items[0].avgTime;
					data.datas = items.slice(1, items.length);
					return data;
				}
			});
			if(queryTime==''){
				source.bindRender(new sui.MonthRender('#month_select'));
			}
			source.bindRender({
					draw : function(data){
						//清理数据
						var items = data.datas;
						var legendData = [];
						var seriesData = [];
						var max = 0;
						var other = 0;
						for(var i=0; i<items.length; i++){
							var item = items[i];
							var rate = item.count * 1.0 / data.count;
							if(max<item.count){
								max = item.count;
							}
							if(rate<0.01){
								other += item.count;
								continue;
							}
							var level = item.dt>100?100:(item.dt>10?10:1);
							var name = (item.dt-level) + '~' + item.dt + '<bean:message key="sys.profile.behavior.system.load.second" bundle="sys-profile-behavior" />';
							legendData.push(name);
							seriesData.push({name:name, value:item.count});
						}
						if(other>0){
							legendData.push('<bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" />');
							seriesData.push({name:'<bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" />', value:other});
						}
						var option = {
								title : {
									text: '<bean:message key="sys.profile.behavior.response.speed.text1" bundle="sys-profile-behavior" />',
									x:'center'
					    		},
								tooltip : {
									trigger: 'item',
									formatter: "{b} : {c} ({d}%)"
								},
								legend: {
									orient : 'vertical',
									x : 'left',
									data : legendData
								},
								toolbox: {
									show : true,
									feature : {
										magicType : {
											show: true, 
											type: ['pie', 'funnel'],
											option: {
												funnel: {
													x: '35%',
													width: '50%',
													funnelAlign: 'left',
													max: max
												}
											}
										},
										restore : {show: true}
									}
								},
								calculable : true,
								series : [
									{
										name:'<bean:message key="sys.profile.behavior.response.speed.text2" bundle="sys-profile-behavior" />',
										type:'pie',
										radius : '60%',
										center: ['60%', '50%'],
										data : seriesData
									}
								]
							};
						echarts.init($('#chart_div')[0]).setOption(option);
					}
				});
			source.bindRender({
				draw:function(data){
					if(timelineSource.params.month != source.params.month){
						timelineSource.params.month = source.params.month;
						timelineSource.load();
					}
					$('#path_table').hide();
					var items = data.datas;
					var tb = $('#list_table')[0];
					for(var i = tb.rows.length - 1; i>0; i--){
						tb.deleteRow(i);
					}
					var key, lastKey = null;
					var sum = 0, count = 0;
					for(var i=0; i<items.length; i++){
						var item = items[i];
						var level = item.dt>100?100:(item.dt>10?10:1);
						key = (item.dt-level) + '~' + item.dt + '秒';
						if(key!=lastKey){
							if(lastKey!=null){
								insertRow(tb, lastKey, count, sum, data.count);
								count = 0;
							}
							lastKey = key;
							key = null;
						}
						sum += item.count;
						count += item.count;
					}
					if(lastKey!=null){
						insertRow(tb, lastKey, count, sum, data.count);
					}
				}
			});
			var timelineSource = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=pathAnalysis');
			timelineSource.params.time = '*';
			timelineSource.bindRender({
					draw : function(data){
						var counts = [];
						var avgTimes = [];
						var maxHour = -1;
						var minHour = -1;
						var items = data.datas;
						for(var i=0; i<items.length; i++){
							var item = items[i];
							var time = new Date(item.time*3600000);
							if(maxHour < item.time){
								maxHour = item.time;
							}
							if(minHour > item.time || minHour==-1){
								minHour = item.time;
							}
							counts.push([time,item.count]);
							avgTimes.push([time,item.avgTime]);
						}
						var zoomStart = 0;
						var hours = maxHour - minHour;
						if(hours > 24*7){
							zoomStart = 100 - Math.round(24*700.0/hours);
						}
						var option = {
								title : {
									text : '<bean:message key="sys.profile.behavior.response.speed.text3" bundle="sys-profile-behavior" />',
									x : 'center',
									subtext : timelineSource.params.month
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
										return info;
						        	}
					        	},
								legend: {
									x : 'left',
									data: ['<bean:message key="sys.profile.behavior.system.load.text2" bundle="sys-profile-behavior" />', '<bean:message key="sys.profile.behavior.system.load.text3" bundle="sys-profile-behavior" />']
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
								series: [{
									name:'<bean:message key="sys.profile.behavior.system.load.text2" bundle="sys-profile-behavior" />',
									type:'line',
									markPoint:{data:[{type:'max',name:'<bean:message key="sys.profile.behavior.system.load.max" bundle="sys-profile-behavior" />'}]},
									smooth:true,
									showAllSymbol:true,
									symbolSize:2,
									data:counts
								},{
									name:'<bean:message key="sys.profile.behavior.system.load.text3" bundle="sys-profile-behavior" />',
									yAxisIndex:1,
									type:'line',
									markPoint:{data:[{type:'max',name:'<bean:message key="sys.profile.behavior.system.load.max" bundle="sys-profile-behavior" />'}]},
									smooth:true,
									showAllSymbol:true,
									symbolSize:2,
									data:avgTimes
								}]
							};
						var chart = echarts.init($('#timeline_chart_div')[0]);
						chart.on('click',function(data){
							if(data.value.length){
								var time = data.value[0].getTime();
								var url = sui.url('${LUI_ContextPath}/sys/profile/maintenance/behavior/time_count.jsp', 'path', timelineSource.params.path, 'time', time);
								window.open(url, '_self');
							}
						});
						chart.setOption(option);
					}
				});
			function insertRow(tb, key, count, sum, total){
				var row = tb.insertRow(-1);
				var cell = $(row.insertCell(-1));
				cell.text(key);
				cell.css({'text-align':'center'});
				cell = $(row.insertCell(-1));
				cell.text(count);
				cell.css({'text-align':'center'});
				cell = $(row.insertCell(-1));
				cell.text(rate(count, total)+'%');
				cell.css({'text-align':'center'});
				cell = $(row.insertCell(-1));
				cell.text(rate(sum, total)+'%');
				cell.css({'text-align':'center'});
			}
			function rate(value, total){
				return Math.round(value * 10000.0 / total)/100.0;
			}
			function loadLogCost(){
				var logCostSource = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=logAnalysis');
				logCostSource.params.path = source.params.path;
				logCostSource.params.month = source.params.month;
				logCostSource.params.time = source.params.time;
				logCostSource.bindRender(new sui.TableRender('#path_table',{
					dataKey:'paths',
					columns:['path','dt', 'time'],
					align : [-1, 1, 0]
				}));
				logCostSource.load();
			}
			function load(){
				timelineSource.params.path = source.params.path = $('input[name="path"]').val();
				if(queryTime!=''){
					var l_time = parseInt(queryTime);
					source.params.time = Math.round(l_time / 3600000);
					var time = new Date(l_time);
					var month = time.getMonth()+1;
					timelineSource.params.month = source.params.month = time.getFullYear() + (month>9?'':'0')+month;
					var html = dateNumToText(l_time-1800000) + ' ~ ' + dateNumToText(l_time+1800000);
					html += '<a href="'+sui.url('time_count.jsp', 'path', source.params.path)+'" target="_self" style="margin-left:20px;">[ <bean:message key="sys.profile.behavior.response.speed.alltime" bundle="sys-profile-behavior" /> ]</a>';
					$('#month_select').html(html);
				}
				timelineSource.load();
				source.load();
			}
			function dateNumToText(num){
				var time = new Date(num);
				return time.getFullYear()+'-'+(time.getMonth()+1)+'-'+time.getDate()
					+' '+time.getHours()+(time.getMinutes()<10?':0':':')+time.getMinutes();
			}
	
			$(function(){
				load();
				menu_focus("0__time");
				
				$(".beh-container-body").css("margin-top", "100px");
			});
		</script>
	</template:replace>
	<template:replace name="filter">
		<div class="beh-container-heading">
			<div class="criteria_frame">
				<span class="criteria_title"><bean:message key="sys.profile.behavior.time.slot" bundle="sys-profile-behavior" /></span>
				<span id="month_select"></span><br>
				<span class="criteria_title">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.analysis.address') }</span>
				<span>
					<input value="<c:out value="${param.path}"/>" name="path" style="width:800px; height:25px; margin-left:5px;">
					<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.confirm') }" style="padding:4px 12px;" onclick="load();">
				</span>
			</div>
		</div>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.response.speed" bundle="sys-profile-behavior" /></h4>
              <a href="${LUI_ContextPath}/sys/profile/maintenance/behavior/time.jsp?time=<c:out value="${param.time}"/>&month=<c:out value="${param.month}"/>" style="font-size: 14px;float: right;">${ lfn:message('sys-profile-behavior:sys.profile.behavior.return') }</a>
            </div>
            <div class="beh-card-body">
           		<div id="timeline_chart_div" style="width:980px; height:400px;margin:10px auto;"></div>
				<hr>
				<div id="chart_div" style="width:680px; height:400px;margin:10px auto;"></div>
				<table class="tb_normal" id="list_table" style="margin-top: 10px;">
					<tr class="tr_normal_title">
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info1') }</td>
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info2') }</td>
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info3') }</td>
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info4') }</td>
					</tr>
				</table>
				<hr>
				<div style="margin:10px 0px 0px 0px; text-align: center;"><a href="#" onclick="loadLogCost();return false;" style="font-weight: bold;"><bean:message key="sys.profile.behavior.response.speed.text4" bundle="sys-profile-behavior" /></a></div>
				<table class="tb_normal" id="path_table" style="margin-top: 10px; width:100%; style:display:none;">
					<tr class="tr_normal_title">
						<td>${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info5') }</td>
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info6') }</td>
						<td width="150px">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.info7') }</td>
					</tr>
				</table>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>