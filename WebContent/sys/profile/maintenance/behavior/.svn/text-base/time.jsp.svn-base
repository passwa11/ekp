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
			var itemMap = {'count':'<bean:message key="sys.profile.behavior.response.speed.count" bundle="sys-profile-behavior" />','sumTime':'<bean:message key="sys.profile.behavior.response.speed.sumTime" bundle="sys-profile-behavior" />(ms)', 'avgTime':'<bean:message key="sys.profile.behavior.response.speed.avgTime" bundle="sys-profile-behavior" />(ms)', 'maxTime':'<bean:message key="sys.profile.behavior.response.speed.maxTime" bundle="sys-profile-behavior" />(ms)'};
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=timeCostAnalysis');
			//排序
			source.params.orderBy = 'sumTime';
			source.params.desc = true;
			if(queryTime==''){
				source.bindRender(new sui.MonthRender('#month_select'));
			}
			source.bindRender(new sui.Paging('#paging_bar'));
			source.bindRender(new sui.SortButton({
				buttons : [
					{key : 'count', dom : '#btn_count'},
					{key : 'avgTime', dom : '#btn_avgTime'},
					{key : 'maxTime', dom : '#btn_maxTime'},
					{key : 'sumTime', dom : '#btn_sumTime'}
				]
			}));
			source.bindRender({
					draw : function(data){
						drawChart(data);
					}
				});
			function drawChart(data){
				var xData = [];
				var yData = [];
				var items = data.datas;
				var type = source.params.orderBy;
				for(var i=0; i<items.length; i++){
					var item = items[i];
					xData.push(item.name);
					yData.push(item[type]);
				}
				var text = itemMap[type];
				if(text.indexOf('(')>-1){
					text = text.substring(0, text.indexOf('('));
				}
				var option = {
						title: {
							text: text+'<bean:message key="sys.profile.behavior.response.speed.ranking" bundle="sys-profile-behavior" />',
							x: 'center'
						},
						tooltip: { trigger: 'axis' },
						toolbox: {
							show: true,
							feature: {
								restore: {
									show: true
								}
							}
						},
						grid : {
							x2:10,
							y2:10
						},
						calculable: true,
						xAxis: [{
							type: 'category',
							splitLine:{show:false},
							axisLabel:{show:false},
							data: xData
						}],
						yAxis: [{
								name:itemMap[type],
								type: 'value'
							}],
						series: [{
								type : 'bar',
								data : yData,
								name : itemMap[type]
							}]
					};
				var dom = $('#chart_div');
				echarts.init(dom[0]).setOption(option);
			}
			//绘制表格
			source.bindRender(new sui.TableRender('#list_table',{
				columns:['name', function(context){
					var info = context.data;
					var self = $(this);
					self.text(getFullPath(info));
					self.bind('click', function(e){
						if(e.ctrlKey && e.altKey){
							var url = sui.url('${LUI_ContextPath}/sys/profile/maintenance/behavior/name.jsp', 'name', data.name,
									'module',data.path);
							window.open(url,'_blank');
							return false;
						}
						return true;
					});
				},'count', 'avgTime', 'maxTime','sumTime'],
				align : [-1, -1],
				rowClick : function(data){
					var url = sui.url('time_count.jsp', 'path', getFullPath(data), 'time', queryTime, 'month', source.params.month);
					window.open(url,'_self');
				}
			}));
			function getFullPath(data){
				return data.module + data.path + (data.method=='' || data.method==null?'':'?method='+data.method);
			}
			function load(){
				if(queryTime!=''){
					var l_time = parseInt(queryTime);
					source.params.time = Math.round(l_time / 3600000);
					var time = new Date(l_time);
					var month = time.getMonth()+1;
					source.params.month = time.getFullYear() + (month>9?'':'0')+month;
					var html = dateNumToText(l_time-1800000) + ' ~ ' + dateNumToText(l_time+1800000);
					html += '<a href="time.jsp" target="_self" style="margin-left:20px;">[ <bean:message key="sys.profile.behavior.response.speed.alltime" bundle="sys-profile-behavior" /> ]</a>';
					$('#month_select').html(html);
				}
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
              <h4><bean:message key="sys.profile.behavior.response.speed" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
           		<center>
	           		<div id="chart_div" style="width:980px; height:320px; margin-top: 10px;"></div>
	            </center>
				<div style="text-align: right; margin:5px 0px 0px 0px;" id="paging_bar"></div>
				<table class="tb_normal" id="list_table" style="margin-top: 10px; width:100%;">
					<tr class="tr_normal_title">
						<td>${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.name') }</td>
						<td>${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.url') }</td>
						<td width="80px"><span id="btn_count">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.count') }</span></td>
						<td width="80px"><span id="btn_avgTime">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.avgTime') }</span></td>
						<td width="80px"><span id="btn_maxTime">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.maxTime') }</span></td>
						<td width="80px"><span id="btn_sumTime">${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed.sumTime') }</span></td>
					</tr>
				</table>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>