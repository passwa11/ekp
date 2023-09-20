<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<script>
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
</script>
<center>
<br>
<table class="tb_normal" width=95%> 
	<tr class="tr_normal_title">
		<td colspan="4" class="config_title">
			${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.desrc') }
		</td>
	</tr>
	<tr>
		<td colspan="4">
		
		    <!------------------- 数据配置  start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataConfigure') }</div>
			<div class="help_content">
				<ul>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataSource') }</b>： 
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataSourceDesrc') }
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataFilter') }</b>： 	
                    ${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.filterDesrc') }
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.cri') }</b>： 
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.criDesrc') }
					</li>														
				</ul>
			</div>
			<!------------------- 数据配置  end ------------------->
			
			<!------------------- 图表属性  start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.chartAttr') }</div>
			<div class="help_content">
				<ul>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.category') }</b>： 
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.categoryDesrc') }
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.series') }</b>： 
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.seriesDesrc') }
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.returnVal') }</b>： 
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.returnValDesrc') }
					</li>														
				</ul>
			</div>
			<!------------------- 图表属性  end ------------------->
			
			<!------------------- 场景举例  start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.example') }</div>
			<div class="help_content">
				<ul>
					<li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.chartItems') }</b></li>
                    <div>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.exampleDesrc1') }</div>		
                    <div>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.exampleDesrc2') }</div>
                    <div style="text-align: center;margin-top: 20px;margin-bottom: 20px;">
                        <img src="db_echarts_chart/configure/images/echart-config-u147.png">
                    </div>		
                    <li><b>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.seriesRsRelation') }</b></li>
                    <div>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.seriesRsRelationDesrc1') }</div>		
                    <div>${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.seriesRsRelationDesrc2') }</div>											
				</ul>
			</div>
			<!------------------- 场景举例  end ------------------->
			
		</td>
	</tr>
</table>
<br>
</center>
	</template:replace>
</template:include>