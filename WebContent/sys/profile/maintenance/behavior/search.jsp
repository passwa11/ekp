<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.search" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.js"></script>
		<script type="text/javascript">
			var itemMap = {'sCount':'<bean:message key="sys.profile.behavior.search.sCount" bundle="sys-profile-behavior" />','sPageCount':'<bean:message key="sys.profile.behavior.search.sPageCount" bundle="sys-profile-behavior" />', 'sUserCount':'<bean:message key="sys.profile.behavior.search.sUserCount" bundle="sys-profile-behavior" />', 'hCount':'<bean:message key="sys.profile.behavior.search.hCount" bundle="sys-profile-behavior" />', 'hUserCount':'<bean:message key="sys.profile.behavior.search.hUserCount" bundle="sys-profile-behavior" />'};
			//数据源
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=searchAnalysis');
			source.params.orderBy = 'sUserCount';
			source.params.desc = true;
			//选择器
			source.bindRender(new sui.MonthRender('#month_select'));
			//翻页与排序
			source.bindRender(new sui.Paging('#paging_bar'));
			source.bindRender(new sui.Paging('#paging_bar2'));
			source.bindRender(new sui.SortButton({
				buttons : [
					{key : 'sCount', dom : '#btn_sCount'},
					{key : 'sPageCount', dom : '#btn_sPageCount'},
					{key : 'sUserCount', dom : '#btn_sUserCount'},
					{key : 'hCount', dom : '#btn_hCount'},
					{key : 'hUserCount', dom : '#btn_hUserCount'}
				]
			}));
			//绘制标签云
			source.bindRender({
				draw : function(data){
					var nodes = [];
					var links = [];
					for(var i=0; i<data.datas.length; i++){
						var info = data.datas[i];
						nodes.push({
							category : 0,
							name:info.key,
							value:info[source.params.orderBy]
						});
						for(var j=0; j<info.keywords.length; j++){
							for(var k=i+1; k<data.datas.length; k++){
								if(info.keywords[j].key == data.datas[k].key){
									links.push({
										source: info.key,
										target: info.keywords[j].key,
										weight: info.keywords[j].count
									});
								}
							}
						}
					}
					var itemName = itemMap[source.params.orderBy];
					var option = buildChartOption(nodes, links);
					option.title.text = '<bean:message key="sys.profile.behavior.search.option1" bundle="sys-profile-behavior" />';
					option.title.subtext = itemName+'<bean:message key="sys.profile.behavior.search.option2" bundle="sys-profile-behavior" />';
					option.series[0].minRadius = 10;
					option.series[0].maxRadius = 30;
					option.tooltip.formatter = function(param){
						var data = param.data;
						if(data.name){
							return '<bean:message key="sys.profile.behavior.search.keyword" bundle="sys-profile-behavior" />:'+data.name+'<br/>'+itemName+'：'+data.value;
						}else{
							return '<bean:message key="sys.profile.behavior.search.option3" bundle="sys-profile-behavior" /> '+data.weight+' <bean:message key="sys.profile.behavior.search.option4" bundle="sys-profile-behavior" />:<br/>'+data.source+' '+data.target;
						}
					};
					var chart = echarts.init($('#chart_div')[0]);
					chart.on('click', function(param){
						if(param.data.source){
							return;
						}
						showDetail(data.datas[param.dataIndex]);
					});
					chart.setOption(option);
				}
			});
			//绘制表格
			source.bindRender(new sui.TableRender('#list_table',{
				columns:['key','sCount','sPageCount', 'sUserCount', 'hCount', 'hUserCount'],
				rowClick:showDetail
			}));
			//显示详细信息
			function showDetail(data){
				var frame = $('#detail_frame');
				frame.fadeIn(500);
				var position = {
					top : ($(window).height()-frame.outerHeight())/2,
					right : ($(window).width()-frame.outerWidth())/2
				};
				if(position.top < 5){
					position.top = 5;
				}
				if(position.right < 5){
					position.right = 5;
				}
				frame.css(position);
				$('#detail_key').text(data.key);
				buildRelationChart({
					datas : data.urls,
					keyword : data.key,
					count : data.sCount,
					title : '<bean:message key="sys.profile.behavior.search.option5" bundle="sys-profile-behavior" />',
					tooltip : function(keyword, target, count){
						return '<bean:message key="sys.profile.behavior.search.option3" bundle="sys-profile-behavior" /> '+count+' <bean:message key="sys.profile.behavior.search.option6" bundle="sys-profile-behavior" />:<br/>'+target;
					},
					dom : '#chart_link'
				});
				buildRelationChart({
					datas : data.keywords,
					keyword : data.key,
					count : data.sCount,
					title : '<bean:message key="sys.profile.behavior.search.option7" bundle="sys-profile-behavior" />',
					tooltip : function(keyword, target, count){
						return '<bean:message key="sys.profile.behavior.search.option3" bundle="sys-profile-behavior" /> '+count+' <bean:message key="sys.profile.behavior.search.option8" bundle="sys-profile-behavior" />:<br/>'+keyword+' '+target;
					},
					dom : '#chart_combin'
				});
			}
			//创建关系图
			function buildRelationChart(config){
				var nodes = [];
				var links = [];
				for(var i=0; i<config.datas.length; i++){
					var data = config.datas[i];
					nodes.push({
						category: 1,
						name: data.key,
						value: data.count
					});
					links.push({
						source: config.keyword,
						target: data.key,
						weight: data.count
					});
				}
				nodes.push({
					category: 0,
					name: config.keyword,
					value: config.count
				});
				var option = buildChartOption(nodes, links);
				option.title.text = config.title;
				option.tooltip.formatter = function(param){
					var data = param.data;
					if(data.category==0){
						return '<bean:message key="sys.profile.behavior.search.keyword" bundle="sys-profile-behavior" />:'+config.keyword+'<br><bean:message key="sys.profile.behavior.search.sCount" bundle="sys-profile-behavior" />:'+config.count;
					}
					return config.tooltip(config.keyword, data.name || data.target, data.value || data.weight);
				};
				option.series[0].linkSymbol = 'arrow';
				echarts.init($(config.dom)[0]).setOption(option);
			}
			//创建力导向图的选项
			function buildChartOption(nodes, links){
				var option =  {
					title: {
						x: 'left',
						y: 'top',
						textStyle : {
							fontFamily : 'Microsoft YaHei, Geneva, "sans-serif"'
						},
						subtextStyle : {
							fontFamily : 'Microsoft YaHei, Geneva, "sans-serif"'
						}
					},
					tooltip: {
						trigger: 'item'
					},
					series: [{
						type: 'force',
						ribbonType: false,
						categories : [
							{
								name: '0',
								itemStyle : {
									normal : {
										color : '#87cefa'
									}
								}
							},
							{
								name: '1',
								itemStyle : {
									normal : {
										color : '#ff7f50'
									}
								}
							}
						],
						itemStyle: {
							normal: {
								label: {
									show: true,
									textStyle: {
										color: '#333'
									}
								},
								nodeStyle : {
									brushType : 'both',
									borderColor : 'rgba(255,215,0,0.4)',
									borderWidth : 1
			                    },
								linkStyle: {
									type: 'curve'
								}
							}
						},
						useWorker: false,
						minRadius: 15,
						maxRadius: 25,
						gravity: 1.2,
						roam: 'move',
						nodes: nodes,
						links: links
					}]
				};
				//若所有节点的值都一样，点会显示很小，强制设定点的大小
				if(nodes.length>0){
					var value = nodes[0].value;
					for(var i=1; i<nodes.length; i++){
						if(value!=nodes[i].value){
							value = -1;
							break;
						}
					}
					if(value!=-1){
						option.series[0].symbolSize = 15;
					}
				}
				return option;
			}
	
			$(function(){
				source.load();
				menu_focus("0__search");
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
              <h4><bean:message key="sys.profile.behavior.search" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <div id="chart_div" style="width:980px; height:420px; margin:10px auto;"></div>
				<div style="text-align: right; margin:5px auto 0px auto; width:95%;" id="paging_bar"></div>
				<table class="tb_normal" id="list_table" style="margin-top: 10px; width:95%">
					<tr class="tr_normal_title">
						<td width="200px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.keyword') }"><bean:message key="sys.profile.behavior.search.keyword" bundle="sys-profile-behavior" /></td>
						<td width="100px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.sCount.info') }"><span id="btn_sCount"><bean:message key="sys.profile.behavior.search.sCount" bundle="sys-profile-behavior" /></span></td>
						<td width="100px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.sPageCount.info') }"><span id="btn_sPageCount"><bean:message key="sys.profile.behavior.search.sPageCount" bundle="sys-profile-behavior" /></span></td>
						<td width="100px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.sUserCount.info') }"><span id="btn_sUserCount"><bean:message key="sys.profile.behavior.search.sUserCount" bundle="sys-profile-behavior" /></span></td>
						<td width="100px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.hCount.info') }"><span id="btn_hCount"><bean:message key="sys.profile.behavior.search.hCount" bundle="sys-profile-behavior" /></span></td>
						<td width="100px" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search.hUserCount.info') }"><span id="btn_hUserCount"><bean:message key="sys.profile.behavior.search.hUserCount" bundle="sys-profile-behavior" /></span></td>
					</tr>
				</table>
				<div  id="detail_frame" style="display:none; position:fixed; width:1000px; height:400px; padding:8px; border:2px solid #ccc; background-color: white; z-index: 1030;">
					<div class="clearfloat">
						<div style="float:left; font-size:14px;"><bean:message key="sys.profile.behavior.search.keyword" bundle="sys-profile-behavior" />:<span id="detail_key"></span></div>
						<div style="float:right;"><a href="#" onclick="$('#detail_frame').fadeOut(500);return false;">[X]</a></div>
					</div>
					<div class="clearfloat">
						<div id="chart_link" style="width:495px; height:380px;float:left;"></div>
						<div id="chart_combin" style="width:495px; height:380px;float:right;"></div>
					</div>
				</div>
				<div style="text-align: right; margin:5px auto 0px auto; width:95%;" id="paging_bar2"></div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>