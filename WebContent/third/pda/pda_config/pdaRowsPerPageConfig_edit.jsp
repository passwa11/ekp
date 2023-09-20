<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="content" >
		<div style="padding: 20px;">
		<html:form action="/third/pda/pda_rows_per_page_config/pdaRowsPerPageConfig.do">
		<p class="txttitle"><bean:message key="pdaGeneralConfig.rowsPerPageConfig" bundle="third-pda"/></p>
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="pdaGeneralConfig.fdRowsNumber" bundle="third-pda"/>
				</td><td colspan=3>
					<html:text property="fdRowsNumber" size="10" onkeydown="if(event.keyCode==13){return false;}" /><span class="txtstrong">*</span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="pdaGeneralConfig.fdAttDownLoadEnabled" bundle="third-pda"/>
				</td><td colspan=3>
					<ui:switch property="fdAttDownLoadEnabled" 
						enabledText= "${lfn:message('third-pda:pdaGeneralConfig.fdIsEnabled') }" 
						disabledText="${lfn:message('third-pda:pdaModuleConfigMain.status.disable') }">
					</ui:switch>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="pdaGeneralConfig.fdExtendsUrl" bundle="third-pda"/>
				</td><td colspan=3>
					<html:textarea property="fdExtendsUrl" style="width:80%"/><br/>
					<span><bean:message key="pdaGeneralConfig.fdExtends.remark" bundle="third-pda"/></span>
				</td>
			</tr>
		</table>
		<div style="margin-top: 20px;">
			<ui:button text="${lfn:message('button.save') }" onclick="submint();" width="120" height="35"></ui:button>
		</div>
		</center>
		<html:hidden property="method_GET"/>
		</html:form>
		</div>
		<script type="text/javascript">
		function submint(){
			var rowsNumber = document.getElementsByName("fdRowsNumber")[0].value;
			if(rowsNumber == null || rowsNumber == ""){
				alert('<bean:message key="pdaGeneralConfig.fdRowsNumber" bundle="third-pda"/><bean:message key="validate.notNull" bundle="third-pda"/>');
				return;
			}
			var   r   =  /^[0-9]*[1-9][0-9]*$/;//正整数     
			if(!r.test(rowsNumber)){
				alert('<bean:message key="pdaGeneralConfig.fdRowsNumber" bundle="third-pda"/><bean:message key="validate.mustBeInteger" bundle="third-pda"/>');
				return;
			}
			Com_Submit(document.pdaRowsPerPageConfigForm, 'update');
		}
		</script>
	</template:replace>	
</template:include>