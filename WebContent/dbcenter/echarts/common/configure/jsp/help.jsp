<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<script>
	Com_IncludeFile("configure.css", "${LUI_ContextPath}/dbcenter/echarts/common/configure/css/", "css", true);
</script>
<center>
<br>
<table class="tb_normal" width=95%> 
	<tr class="tr_normal_title">
		<td class="config_title">
			${ lfn:message('dbcenter-echarts:shiyongshuoming') }
		</td>
	</tr>
	<tr>
		<td>
			<div class="db_help_body">
				<div class="db_help_wrap">
					<div class="db_help_block">
						<div class="db_help_title">${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataSource') }</div>
						<div class="db_help_desrc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.help.dataSourceDesrc') }</div>
					</div>
				</div>
				<div class="db_help_wrap">
					<div class="sample_img"></div>
				</div>
			</div>
			
		</td>
	</tr>
</table>
<br>
</center>
	</template:replace>
</template:include>