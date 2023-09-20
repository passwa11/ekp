<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">

	<template:replace name="head">
		<style>
			.headerSetting {
				margin-top: 60px;
			}
		</style>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.save') }" onclick="saveConfig()"></ui:button> 
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content">
		<center>
			<table class="tb_normal assessSet_table headerSetting">
				<tr>
					<td width="25%"  class="td_normal_title">
						<bean:message key="sysMportalPage.header.setting.page" bundle="sys-mportal" /> 
					</td>
					<td width="75%">
						<xform:radio property="headerTpye" showStatus="edit" value="${headerType}">
							<xform:enumsDataSource enumsType="sysMportalPage_headerType" />
						</xform:radio>
					</td>
				</tr>
			</table>
		</center>	
		
		<script>
			function saveConfig(){
				seajs.use(['lui/dialog'], function(dialog){
					var headerTpye = $('input[name="headerTpye"]:checked')[0].value;
					$.ajax('${LUI_ContextPath}/sys/mportal/sys_mportal_page/sysMportalPage.do?method=saveOrUpdateHeaderSetting', {
						type : "post",
						data  : {
							'headerTpye' : headerTpye,
						},
						success : function(data) {
							dialog.result(data);
						},
						error : function(data) {
							dialog.failure('<bean:message key="sysMportalPage.header.setting.error" bundle="sys-mportal" />');
						},
						dataType : "json"
					});
				});
			}
		</script>	
		
	</template:replace>

</template:include>