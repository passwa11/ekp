<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no" spa="true">
	<template:replace name="body">
		<script>
		Com_IncludeFile("statisticsOverview.css","${LUI_ContextPath}/dbcenter/echarts/db_echars_ui/css/","css",true);
		Com_IncludeFile("statistics_overview.js", "${LUI_ContextPath}/dbcenter/echarts/db_echars_ui/js/", null, true);
		</script>	
	    <!-- 悬浮Tab（默认隐藏，当顶部Tab滑动至不可视区域的时候显示出来） -->
	    <div class="suspend_overview_tab_panel" >
	        <div class="suspend_overview_tab suspend_overview_tab_selected" anchor_point_id="chart_type_title">${lfn:message('dbcenter-echarts:overview.category.chartType')}</div>
	        <div class="suspend_overview_tab" anchor_point_id="config_method_title">${lfn:message('dbcenter-echarts:overview.category.configMethod')}</div>
	        <div class="suspend_overview_tab" anchor_point_id="app_scenario_title">${lfn:message('dbcenter-echarts:overview.category.appScenario')}</div>
	    </div>
	     
	    <!-- Tab切换区域  -->
	    <div class="overview_tab_panel">
	       <div class="tab_category_area">
	          <!-- 图表类型 -->
	       	  <div class="tab_category tab_category_selected" anchor_point_id="chart_type_title" >
		       	  <div class="category_icon chart_type_icon"></div>
		       	  <div class="category_text">${lfn:message('dbcenter-echarts:overview.category.chartType')}</div>
	       	  </div>
	       	  <!-- 配置方式 -->
	          <div class="tab_category" anchor_point_id="config_method_title">
	              <div class="category_icon config_method_icon"></div>
	              <div class="category_text">${lfn:message('dbcenter-echarts:overview.category.configMethod')}</div>
	          </div>
	          <!-- 应用场景 -->
	          <div class="tab_category" anchor_point_id="app_scenario_title">
	              <div class="category_icon app_scenario_icon"></div>
	              <div class="category_text">${lfn:message('dbcenter-echarts:overview.category.appScenario')}</div>
	          </div>
	       </div>
	    </div>
	    
	    <!-- 图表类型  -->
	    <div id="chart_type_title" class="basic_title">${lfn:message('dbcenter-echarts:overview.chartType')}</div>
	    <div class="basic_description">
	        <div>${lfn:message('dbcenter-echarts:overview.chartType.desc.row1')}</div>
	        <div>${lfn:message('dbcenter-echarts:overview.chartType.desc.row2')}</div>
	    </div>
	    
	    <div class="chart_type_region" >
	      <div class="chart_type_content" >
	      
	        <!-- 自定义数据 -->
	        <div class="chart_type_cell">
	           <div class="chart_type_title">${lfn:message('dbcenter-echarts:overview.chartType.title.customData')}</div>
	           <div class="chart_type_cell_icon"><div class="custom_data_icon"></div></div>  
	           <div class="chart_type_detail">
	              <div class="detail_show">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.show')}：</div>
	                 <div class="detail_desc">${lfn:message('dbcenter-echarts:overview.chartType.customData.show')}</div>
	              </div>
	              <div class="detail_feature">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.feature')}：</div>
	                 <div class="detail_desc">${lfn:message('dbcenter-echarts:overview.chartType.customData.feature')}</div>
	              </div>
	           </div>  
	           <kmss:authShow roles="ROLE_DBCENTERECHARTS_CUSTOM_ADD">
		          <div class="button_area">
		              <div class="create_button" onclick="addCustomData()">${lfn:message('button.add')}</div>
		          </div> 
               </kmss:authShow>     
	        </div>
	        
	        <!-- 统计图表 -->
	        <div class="chart_type_cell">
	           <div class="chart_type_title">${lfn:message('dbcenter-echarts:overview.chartType.title.statisticChart')}</div>
	           <div class="chart_type_cell_icon"><div class="statistic_chart_icon"></div></div>
	           <div class="chart_type_detail">
	              <div class="detail_show">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.show')}：</div>
	                 <div class="detail_desc">${lfn:message('dbcenter-echarts:overview.chartType.statisticChart.show')}</div>
	              </div>
	              <div class="detail_feature">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.feature')}：</div>
	                 <div class="detail_desc">
	                 ${lfn:message('dbcenter-echarts:overview.chartType.statisticChart.feature.row1')}<BR/>
	                 ${lfn:message('dbcenter-echarts:overview.chartType.statisticChart.feature.row2')}<BR/>
	                 ${lfn:message('dbcenter-echarts:overview.chartType.statisticChart.feature.row3')}
	                 </div>
	              </div>
	           </div> 
	           <kmss:authShow roles="ROLE_DBCENTERECHARTS_CHART_ADD">
	              <div class="button_area">
	                  <div class="create_button" onclick="addStatisticChart()">${lfn:message('button.add')}</div>
	              </div>   	
			   </kmss:authShow>         
	        </div>
	        
	        <!-- 统计列表 -->
	        <div class="chart_type_cell">
	           <div class="chart_type_title">${lfn:message('dbcenter-echarts:overview.chartType.title.statisticList')}</div>
	           <div class="chart_type_cell_icon"><div class="statistic_list_icon"></div></div>  
	           <div class="chart_type_detail">
	              <div class="detail_show">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.show')}：</div>
	                 <div class="detail_desc">${lfn:message('dbcenter-echarts:overview.chartType.statisticList.show')}</div>
	              </div>
	              <div class="detail_feature">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.feature')}：</div>
	                 <div class="detail_desc">
	                 ${lfn:message('dbcenter-echarts:overview.chartType.statisticList.feature.row1')}<BR/>
	                 ${lfn:message('dbcenter-echarts:overview.chartType.statisticList.feature.row2')}
	                 </div>
	              </div>
	           </div>
	           <kmss:authShow roles="ROLE_DBCENTERECHARTS_TABLE_ADD">
	              <div class="button_area">
	                  <div class="create_button" onclick="addStatisticList()">${lfn:message('button.add')}</div>
	              </div>  	
			   </kmss:authShow>  	           	                                     
	        </div>
	        
	        <!-- 统计图表集 -->
	        <div class="chart_type_cell">
	           <div class="chart_type_title">${lfn:message('dbcenter-echarts:overview.chartType.title.chartSet')}</div>
	           <div class="chart_type_cell_icon"><div class="chart_set_icon"></div></div>      
	           <div class="chart_type_detail">
	              <div class="detail_show">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.show')}：</div>
	                 <div class="detail_desc">${lfn:message('dbcenter-echarts:overview.chartType.chartSet.show')}</div>
	              </div>
	              <div class="detail_feature">
	                 <div class="detail_title">${lfn:message('dbcenter-echarts:overview.chartType.feature')}：</div>
	                 <div class="detail_desc">
	                   ${lfn:message('dbcenter-echarts:overview.chartType.chartSet.feature.row1')}<BR/>
	                   ${lfn:message('dbcenter-echarts:overview.chartType.chartSet.feature.row2')}
	                 </div>
	              </div>
	           </div>  
	           <kmss:authShow roles="ROLE_DBCENTERECHARTS_CHARTSET_ADD"> 	
		          <div class="button_area">
		              <div class="create_button" onclick="addChartSet()">${lfn:message('button.add')}</div>
		          </div>	           
			   </kmss:authShow> 	           	                                 
	        </div>
	        
	      </div>
	    </div>
	    

	    <!-- 配置方式  -->
	    <div id="config_method_title" class="basic_title">${lfn:message('dbcenter-echarts:overview.configMethod')}</div>
	    <div class="basic_description">
	        <div>${lfn:message('dbcenter-echarts:overview.configMethod.desc.row1')}</div>
	        <div>${lfn:message('dbcenter-echarts:overview.configMethod.desc.row2')}</div>
	    </div>
	    
	    <div class="config_method_region" >
	      <div class="config_method_content" >
	      
	         <!-- 静态数据导入 -->
	         <div class="config_method_cell">
	            <div class="config_method_cell_icon"><div class="static_data_import_icon"></div></div>
	            <div class="config_detail_title" >${lfn:message('dbcenter-echarts:overview.configMethod.staticDataImport')}</div> 
	            <div class="config_method_detail">
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.staticDataImport.desc1')}</div>
	              </div>
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.staticDataImport.desc2')}</div>
	              </div>
	           </div>
	         </div>
	         
	         <!-- 动态数据配置 -->
	         <div class="config_method_cell">
	            <div class="config_method_cell_icon"><div class="dynamic_data_config_icon"></div></div>
	            <div class="config_detail_title" >${lfn:message('dbcenter-echarts:overview.configMethod.dynamicDataConfig')}</div> 
	            <div class="config_method_detail">
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.dynamicDataConfig.desc1')}</div>
	              </div>
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.dynamicDataConfig.desc2')}</div>
	              </div>
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.dynamicDataConfig.desc3')}</div>
	              </div>	              
	           </div>	            
	         </div>
	         
	         <!-- 编程模式 -->
	         <div class="config_method_cell">
	            <div class="config_method_cell_icon"><div class="program_mode_icon"></div></div>
	            <div class="config_detail_title" >${lfn:message('dbcenter-echarts:overview.configMethod.programMode')}</div>
	            <div class="config_method_detail">
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.programMode.desc1')}</div>
	              </div>
	              <div class="detail_row">
	                 <div class="detail_separator">-</div>
	                 <div class="detail_content">${lfn:message('dbcenter-echarts:overview.configMethod.programMode.desc2')}</div>
	              </div>
	           </div>	             
	         </div>
	         
	      </div>
	    </div>  
	    
	    <!-- 图表应用场景  -->
	    <div id="app_scenario_title" class="basic_title">${lfn:message('dbcenter-echarts:overview.appScenario')}</div>
	    <div class="basic_description">
	        <div>${lfn:message('dbcenter-echarts:overview.appScenario.desc')}</div>
	    </div>
	    
	    <div class="app_scenario_region" >
	      <div class="app_scenario_content" >
	         <div class="scenario_menu">
	            <div class="scenario_menu_item scenario_menu_item_selected" scenario_index="1" >${lfn:message('dbcenter-echarts:overview.appScenario.menu.item1')}</div>
	            <div class="scenario_menu_item" scenario_index="2" >${lfn:message('dbcenter-echarts:overview.appScenario.menu.item2')}</div>
	            <div class="scenario_menu_item" scenario_index="3" >${lfn:message('dbcenter-echarts:overview.appScenario.menu.item3')}</div>
	            <div class="scenario_menu_item" scenario_index="4" >${lfn:message('dbcenter-echarts:overview.appScenario.menu.item4')}</div>
	         </div>
	         <div class="scenario_content" >
	            <div class="scenario_screenshot"></div>
	            <div class="scenario_text" text_index="1">${lfn:message('dbcenter-echarts:overview.appScenario.menu.item1.desc')}</div>
	            <div class="scenario_text" text_index="2" style="display:none;">${lfn:message('dbcenter-echarts:overview.appScenario.menu.item2.desc')}</div>
	            <div class="scenario_text" text_index="3" style="display:none;">${lfn:message('dbcenter-echarts:overview.appScenario.menu.item3.desc')}</div>
	            <div class="scenario_text" text_index="4" style="display:none;">${lfn:message('dbcenter-echarts:overview.appScenario.menu.item4.desc')}</div>
	         </div>
	      </div>
	    </div> 
	   <script>
	     var categoryId = "${JsParam.categoryId}";
	   </script>
	</template:replace> 
</template:include>	