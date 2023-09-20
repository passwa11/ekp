<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('dbcenter-echarts:dbEcharts.setting')}</template:replace>
		<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		Com_IncludeFile("echarts.js", "${LUI_ContextPath}/sys/ui/js/chart/echarts/", null, true);
		Com_IncludeFile("landrayblue.js|dark.js|infographic.js|macarons.js|roma.js|shine.js|vintage.js", "${LUI_ContextPath}/sys/ui/js/chart/theme/", null, true);
		</script>
		<style type="text/css"> 
		    .echarts_row {
		       min-width: 764px;
		       margin-top: 10px;
		    }
		    .echarts_row_second {
		        margin-bottom: 10px;
		    }
			.echarts_div {
			    display: inline-block;
			    *display: inline; 
			    *zoom: 1;		
			    border: 1px solid #E1E2E4;	
			    width: 250px;
			    height: 160px;
			}
			.echarts_div:first-child+div {
			    margin-left: 4px;
			}
			.echarts_div:first-child+div+div {
			    margin-left: 4px;
			}
			.theme_colors {
			   display: inline-block;
			   *display: inline; 
			   *zoom: 1;	
			   margin-left: 20px;		
			}
			.theme_colors>div{
			   width: 18px;
			   height: 10px;
			   margin-right: 2px;
			   display: inline-block;
			   *display: inline; 
			   *zoom: 1;
			}
		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('dbcenter-echarts:dbEcharts.setting')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<div>
					<div>
						<table id='lab_detail' class="tb_normal" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;">
							<tr>
								<td class="td_normal_title" rowspan="2" width="15%">${lfn:message('dbcenter-echarts:dbcenterEcharts.theme.chartDefault')}</td>
								<td> 
									<xform:select property="value(echarts.default.theme)" onValueChange="drawChart" showPleaseSelect="false">
									    <xform:simpleDataSource value="default"><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></xform:simpleDataSource>
										<c:forEach items="${themes}" var="theme">
											<xform:simpleDataSource value="${theme}">${theme}</xform:simpleDataSource>
										</c:forEach>
									</xform:select>
									<div class="theme_colors"></div>
								</td>
							</tr>
							<tr>
							   <td style="text-align:center">
							       <div class="echarts_row echarts_row_first">
							           <div id="example_echart_1" class="echarts_div"></div>
							           <div id="example_echart_2" class="echarts_div"></div>
							           <div id="example_echart_3" class="echarts_div"></div>
							       </div>
							       <div class="echarts_row echarts_row_second">
							           <div id="example_echart_4" class="echarts_div"></div>
							           <div id="example_echart_5" class="echarts_div"></div>
							           <div id="example_echart_6" class="echarts_div"></div>
							       </div>
							   </td>
							</tr>
						</table>
					</div>
				</div>
			</center>
			
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.dbcenter.echarts.model.DbEchartsConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			function validateAppConfigForm(thisObj) {
				return true;
			}
			
			// 定义echarts的配置数据对象
			var chartOption1,chartOption2,chartOption3,chartOption4,chartOption5,chartOption6 = null;
			
			function drawChart(){
				var theme = $("select[name='value(echarts.default.theme)']").val(); // 获取下拉框选中的主题
				// 删除并重新创建渲染图表的div（为了避免切换主题的时候echarts无法在原div上重新绘制新主题）
				$(".echarts_row_first").empty();
				$(".echarts_row_first").append("<div id=\"example_echart_1\" class=\"echarts_div\"></div>");
				$(".echarts_row_first").append("<div id=\"example_echart_2\" class=\"echarts_div\"></div>");
				$(".echarts_row_first").append("<div id=\"example_echart_3\" class=\"echarts_div\"></div>");
				$(".echarts_row_second").empty();
				$(".echarts_row_second").append("<div id=\"example_echart_4\" class=\"echarts_div\"></div>");			    
				$(".echarts_row_second").append("<div id=\"example_echart_5\" class=\"echarts_div\"></div>");
				$(".echarts_row_second").append("<div id=\"example_echart_6\" class=\"echarts_div\"></div>");
				
				if(true){
					// 基于准备好的dom，初始化echarts实例
			        var chart1 = echarts.init(document.getElementById('example_echart_1'), theme);
			        var chart2 = echarts.init(document.getElementById('example_echart_2'), theme);
			        var chart3 = echarts.init(document.getElementById('example_echart_3'), theme);
			        var chart4 = echarts.init(document.getElementById('example_echart_4'), theme);
			        var chart5 = echarts.init(document.getElementById('example_echart_5'), theme);
			        var chart6 = echarts.init(document.getElementById('example_echart_6'), theme);
			        var themeColors = [];
			        if(chart1._theme){
			        	themeColors = chart1._theme.color; // 获取当前选择的主题颜色代码集合	
			        }
			        showThemeColors(themeColors);
			        if(chartOption1==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption1.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption1 = result;
				        	chart1.setOption(chartOption1);
				        });		        	
			        } else { chart1.setOption(chartOption1); }
			        if(chartOption2==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption2.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption2 = result;
				        	chart2.setOption(chartOption2);
				        });
			        } else { chart2.setOption(chartOption2); }
			        if(chartOption3==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption3.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption3 = result;
				        	chart3.setOption(chartOption3);
				        });
			        } else { chart3.setOption(chartOption3); }
			        if(chartOption4==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption4.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption4 = result;
				        	chart4.setOption(chartOption4);
				        });
			        } else { chart4.setOption(chartOption4); }
			        if(chartOption5==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption5.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption5 = result;
				        	chart5.setOption(chartOption5);
				        });
			        } else { chart5.setOption(chartOption5); }
			        if(chartOption6==null){
				        $.getJSON(Com_Parameter.ContextPath+"dbcenter/echarts/example_json_data/exampleOption6.json?s_cache=${LUI_Cache}",null,function(result){
					        chartOption6 = result;
				        	chart6.setOption(chartOption6);
				        });		        	
			        } else { chart6.setOption(chartOption6); }
				}else{
					showThemeColors(null); // 清空主题颜色格子列表
				}
			}
			
			/** 设置显示主题颜色集合小格子 **/
			function showThemeColors(themeColors){
				var $themeColor = $(".theme_colors")
				$themeColor.empty();
				if(themeColors){
					for(var i=0;i<themeColors.length;i++){
						var color = themeColors[i];
						$themeColor.append("<div style=\"background:"+color+"\"></div>");
					}					
				}
			}
			
			drawChart();
		</script>
	</template:replace>
</template:include>
