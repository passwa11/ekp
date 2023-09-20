<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.module" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var itemMap = {'PC':'PC','KK':'KK', 'WEIXIN':'<bean:message key="sys.profile.behavior.terminal.weixin" bundle="sys-profile-behavior" />', 'EKP':'<bean:message key="sys.profile.behavior.terminal.ekp" bundle="sys-profile-behavior" />', 'WEB':'<bean:message key="sys.profile.behavior.unknow.web" bundle="sys-profile-behavior" />'};
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=moduleAnalysis');
			source.params.orderBy = 'ALL';
			source.params.desc = true;
			source.bindRender(new sui.SortButton({
				buttons : [
					{key : 'ALL', dom : '#btn_ALL'},
					{key : 'PC', dom : '#btn_PC'},
					{key : 'KK', dom : '#btn_KK'},
					{key : 'WEIXIN', dom : '#btn_WEIXIN'},
					{key : 'EKP', dom : '#btn_EKP'},
					{key : 'WEB', dom : '#btn_WEB'}
				]
			}));
			source.bindRender(new sui.MonthRender('#month_select'));
			source.params.module = '<c:out value="${param.module}"/>';
			source.bindRender(new sui.TableRender('#list_table', {
				columns : [function(info){
						var data = info.data;
						var self = $(this);
						self.text(data.name);
						self.attr('title', data.path);
						self.bind('click', function(e){
							if(e.ctrlKey && e.altKey){
								var url = sui.url('${LUI_ContextPath}/sys/profile/maintenance/behavior/name.jsp', 'name', data.name,
										'path',data.path);
								window.open(url,'_blank');
								return false;
							}
							return true;
						});
					}, 'ALL', 'PC', 'KK', 'WEIXIN', 'EKP', 'WEB']
			}));
			source.bindRender({
				draw : function(data){
					var seriesData = {};
					for(var o in itemMap){
						seriesData[o] = [];
					}
					var xData = [];
					var items = data.datas;
					var type = source.params.orderBy;
					for(var i=0; i<items.length; i++){
						var item = items[i];
						xData.push(item.name);
						for(var o in itemMap){
							seriesData[o].push(item[o]);
						}
					}
					var series = [];
					var legend = {
						data:[],
						selected : {},
						y:'bottom'
					};
					var orderBy = source.params.orderBy;
					for(var o in itemMap){
						legend.data.push(itemMap[o]);
						if(orderBy!='ALL' && orderBy!=o){
							legend.selected[itemMap[o]] = false;
						}
						series.push({
							type : 'bar',
							data : seriesData[o],
							name : itemMap[o],
							stack :'ALL'
						});
					}
					var option = {
							title: {
								text: '<bean:message key="sys.profile.behavior.module.visit" bundle="sys-profile-behavior" />',
								x: 'center'
							},
							legend : legend,
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
								y : 40,
								y2 : 120
							},
							calculable: true,
							xAxis: [{
								type: 'category',
								splitLine:{show:false},
								axisLabel:{rotate:70},
								data: xData
							}],
							yAxis: [{
									name:'<bean:message key="sys.profile.behavior.module.frequency" bundle="sys-profile-behavior" />',
									type: 'value'
								}],
							series: series
						};
					var dom = $('#chart_div');
					echarts.init(dom[0]).setOption(option);
				}
			});
	
			$(function(){
				source.load();
				menu_focus("0__module");
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
              <h4><bean:message key="sys.profile.behavior.module" bundle="sys-profile-behavior" /></h4>
              <a href="${LUI_ContextPath}/sys/profile/maintenance/behavior/module.jsp?month=<c:out value="${param.month}"/>" style="font-size: 14px;float: right;"><bean:message key="sys.profile.behavior.return" bundle="sys-profile-behavior" /></a>
            </div>
            <div class="beh-card-body">
            	<center>
	           		<div id="chart_div" style="width:980px; height:400px; margin-top:10px; "></div>
	            </center>
				<table class="tb_normal" id="list_table" style="margin-top: 10px; width:100%;">
					<tr class="tr_normal_title">
						<td><bean:message key="sys.profile.behavior.module.name" bundle="sys-profile-behavior" /></td>
						<td width="80px"><span id="btn_ALL"><bean:message key="sys.profile.behavior.module.total" bundle="sys-profile-behavior" /></span></td>
						<td width="80px"><span id="btn_PC">PC</span></td>
						<td width="80px"><span id="btn_KK">KK</span></td>
						<td width="80px"><span id="btn_WEIXIN"><bean:message key="sys.profile.behavior.terminal.weixin" bundle="sys-profile-behavior" /></span></td>
						<td width="80px"><span id="btn_EKP"><bean:message key="sys.profile.behavior.terminal.ekp" bundle="sys-profile-behavior" /></span></td>
						<td width="80px"><span id="btn_WEB"><bean:message key="sys.profile.behavior.unknow.web" bundle="sys-profile-behavior" /></span></td>
					</tr>
				</table>
				<div style="text-align: right; margin:5px 0px 0px 0px;" id="paging_bar2"></div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>