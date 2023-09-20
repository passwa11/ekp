<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.terminal" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=clientAnalysis');
			source.bindRender(new sui.MonthRender('#month_select'));
			source.bindRender(buildRender({
				type : 'PC',
				title : '<bean:message key="sys.profile.behavior.terminal.pc" bundle="sys-profile-behavior" />',
				key : 'pc',
				map : {msie:'IE '}
			}));
			source.bindRender(buildRender({
				type : 'PHONE',
				title : '<bean:message key="sys.profile.behavior.terminal.phone" bundle="sys-profile-behavior" />',
				key : 'phone',
				map : {}
			}));
			source.bindRender(buildRender({
				type : 'ENTRY',
				title : '<bean:message key="sys.profile.behavior.terminal.entry" bundle="sys-profile-behavior" />',
				key : 'entry',
				map : {WEIXIN:'<bean:message key="sys.profile.behavior.terminal.weixin" bundle="sys-profile-behavior" />', EKP:'<bean:message key="sys.profile.behavior.terminal.ekp" bundle="sys-profile-behavior" />',DING:'<bean:message key="sys.profile.behavior.terminal.ding" bundle="sys-profile-behavior" />'}
			}));
			function buildRender(config){
				// config : {type, value, title}
				var render = {
					draw:function(data){
						var items = data.datas;
						if(items==null || items.length==0){
							return;
						}
						var legendData = [];
						var seriesData = [];
						var series = [];
						// 逆序排序，第一个一定是总数
						var total = items[0].count;
						var sum, name, i;
						for(i = 1; i<items.length; i++){
							//type, value, version
							var item = items[i];
							if(config.type != item.type){
								continue;
							}
							if(config.value==null){
								if(item.value=='*'){
									if(item.version=='*'){
										sum = item.count;
									}
									continue;
								}else{
									if(item.value=='WEB' || item.version!='*'){
										continue;
									}
									name = item.value;
								}
							}else{
								if(config.value != item.value){
									continue;
								}
								if(item.version=='*'){
									sum = item.count;
									continue;
								}else{
									name = item.version;
								}
							}
							legendData.push(name);
							seriesData.push(item.count);
						}
						if(config.showUnuse!=false && total!=sum){
							legendData.push('<bean:message key="sys.profile.behavior.unknow" bundle="sys-profile-behavior" />');
							seriesData.push(total - sum);
						}
	
						// 构造饼图参数
						var dx = 100 / legendData.length;
						var x = dx / 2;
						for(i=0; i<seriesData.length; i++){
							legendData[i] = (config.prefix || '') + (config.map[legendData[i]] || legendData[i]);
							series.push({
									type: 'pie',
									center: [x + '%', '50%'],
									radius: [50, 70],
									itemStyle: {
										normal: {
											label: {
												formatter: function(params) {
													return (10000-Math.round(100*params.percent))/100.0 + '%';
												},
												textStyle: { baseline: 'top' }
											}
										}
									},
									data: [{
										name: '<bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" />',
										value: total - seriesData[i],
										itemStyle: {
											normal: {
												color: '#ccc',
												label: {
													show: true,
													position: 'center'
												},
												labelLine: { show: false }
											},
											emphasis: { color: 'rgba(0,0,0,0)' }
										}
									},{
										name: legendData[i],
										value: seriesData[i],
										itemStyle: {
											normal: {
												label: {
													show: true,
													position: 'center',
													formatter: '{b}',
													textStyle: { baseline: 'bottom'}
												},
												labelLine: { show: false }
											}
										}
									}]
								});
							x += dx;
						}
						var option = {
							legend : {
								x : 'center',
								y : 'bottom',
								data: legendData
							},
							title: {
								text: config.title,
								x: 'center'
							},
							toolbox: { show: false },
							tooltip : {
								trigger: 'item',
								formatter: "{b} : {c} ({d}%)"
							},
							series : series
						};
						echarts.init($('#'+config.key+'_chart_div')[0]).setOption(option);
					}
				};
				return render;
			}
			source.bindRender({draw:function(data){
				var items = data.datas;
				if(items==null || items.length==0){
					return;
				}
				var total = items[0].count, pc = 0, mobile = 0;
				for(var i=0; i<items.length; i++){
					var item = items[i];
					if(item.value=='*' && item.version=='*'){
						if(item.type=='PC'){
							pc = item.count;
						}else if(item.type=='PHONE'){
							mobile = item.count;
						}
					}
				}
				//显示数字
				$('#all_person').text(total);
				$('#pc_person').text(pc);
				$('#mobile_person').text(mobile);
			}});
	
			$(function(){
				source.load();
				menu_focus("0__client");
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
              <h4><bean:message key="sys.profile.behavior.terminal" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <div style="margin:5px 0px 0px 10px; ">
					<bean:message key="sys.profile.behavior.terminal.total1" bundle="sys-profile-behavior" /><span id="all_person" style="font-size: 14px; font-weight: bold;"></span>，
					<bean:message key="sys.profile.behavior.terminal.total2" bundle="sys-profile-behavior" /><span id="pc_person" style="font-size: 14px; font-weight: bold;"></span>，
					<bean:message key="sys.profile.behavior.terminal.total3" bundle="sys-profile-behavior" /><span id="mobile_person" style="font-size: 14px; font-weight: bold;"></span>
				</div>
				<div style="width:980px;margin: 5px auto;">
					<div id="pc_chart_div" style="width:980px; height:240px; margin-top: 10px;"></div>
					<div id="phone_chart_div" style="width:980px; height:240px; margin-top: 30px;"></div>
					<div id="entry_chart_div" style="width:980px; height:240px; margin-top: 30px;"></div>
				</div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>