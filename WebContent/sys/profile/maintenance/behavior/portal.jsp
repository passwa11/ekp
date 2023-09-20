<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.portal" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var portalData = {};
			var defaultServer = undefined;
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=portalAnalysis', {
					format : function(result){
						var data = [];
						for(var i=0; i<result.datas.length; i++){
							var info = result.datas[i];
							var name = info.key==null?'<bean:message key="sys.profile.behavior.portal.default" bundle="sys-profile-behavior" />':portalData[info.key];
							if(name==null){
								continue;
							}
							data.push({name:name, value:info.count, key:info.key});
						}
						result.datas = data;
						return result;
					}
				});
			source.bindRender(new sui.MonthRender('#month_select'));
			source.bindRender(new sui.TableRender('#list_table', {
				columns:['name', 'value'],
				rowClick : function(data){
					open(sui.url(defaultServer + '/sys/portal/page.jsp', 'pageId', data.key), '_blank');
				}}));
			source.bindRender({draw : function(data){
				var sum = 0;
				for(var i=0; i<data.datas.length; i++){
					sum += data.datas[i].value;
				}
				var legendData = [];
				var seriesData = [];
				var _sum = 0;
				for(var i=0; i<data.datas.length; i++){
					var info = data.datas[i];
					if((_sum/sum)>0.95 || (info.value/sum)<0.01){
						legendData.push('<bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" />');
						seriesData.push({name:'<bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" />', value:sum-_sum});
						break;
					}
					_sum += info.value;
					legendData.push(info.name);
					seriesData.push(info);
				}
				
				var max = data.datas.length>0?data.datas[0].value:0;
				var option = {
					title : {
						text: '<bean:message key="sys.profile.behavior.portal.statistics" bundle="sys-profile-behavior" />',
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
							name:'<bean:message key="sys.profile.behavior.portal.frequency" bundle="sys-profile-behavior" />',
							type:'pie',
							radius : '60%',
							center: ['60%', '50%'],
							data : seriesData
						}
					]
				};
				echarts.init($('#chart_div')[0]).setOption(option);
			}});
			function load(){
				$.ajax({
					url : defaultServer + '/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=portalNavTree&jsonpcallback=?',
					cache : true,
					dataType : 'jsonp',
					success : function(data){
						for(var i=0; i<data.length; i++){
							loadPortalInfo(data[i]);
						}
						source.load();
					},
					error : function(){
						if(defaultServer==undefined||defaultServer==''){
							alert('<bean:message key="sys.profile.behavior.portal.error1" bundle="sys-profile-behavior" />');
							return;
						 }
						if(confirm('<bean:message key="sys.profile.behavior.portal.error2" bundle="sys-profile-behavior" />')){
							open(defaultServer, '_blank');
						}
					}
				});
			}
			function loadPortalInfo(portal){
				if(portal.fdType=='portal'){
					if(portal.children!=null){
						for(var i=0; i<portal.children.length; i++){
							loadPortalInfo(portal.children[i]);
						}
					}
				}else{
					portalData[portal.fdId] = portal.text;
				}
			}
	
			$(function(){
				var dialog = new sui.Dialog();
				dialog.loading();
				$.ajax({
					url : Com_Parameter.ContextPath + '/sys/profile/maintenance/behavior/behaviorSetting.do?method=defaultServer',
					dataType : 'json',
					success : function(result){
						if(!result.state) {
							// 出错啦
							alert(result.errorMsg);
							return false;
						}
						if(result.path && result.path != "") {
							defaultServer = result.path;
							load();
						} else {
							alert('<bean:message key="sys.profile.behavior.portal.error1" bundle="sys-profile-behavior" />');
						}
					},
					error : function(){
						alert('<bean:message key="sys.profile.behavior.portal.error1" bundle="sys-profile-behavior" />');
					},
					complete : function() {
						dialog.hide();
					}
				});
				menu_focus("0__portal");
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
              <h4><bean:message key="sys.profile.behavior.portal" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <div id="chart_div" style="width:680px; height:400px; margin:10px auto;"></div>
				<center>
				<bean:message key="sys.profile.behavior.portal.info" bundle="sys-profile-behavior" />
				<table class="tb_normal" id="list_table" style="margin-top: 10px;">
					<tr class="tr_normal_title">
						<td width="400px"><bean:message key="sys.profile.behavior.portal.name" bundle="sys-profile-behavior" /></td>
						<td width="100px"><bean:message key="sys.profile.behavior.portal.total" bundle="sys-profile-behavior" /></td>
					</tr>
				</table>
				</center>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>