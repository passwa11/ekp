<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('kms-lservice:mportal.select.selectType')}</template:replace>
	<template:replace name="body">
	<table class="tb_normal" style="width:90%;margin-top:25px;">
		<kmss:ifModuleExist path="/kms/integral">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showIntegral')}
				</td>
				<td width="35%">
					<xform:radio showStatus="edit" property="showIntegral" value="${param.showIntegral}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.integralShowType')}
				</td>
				<td width="35%" >
					<xform:radio showStatus="edit" property="integralShowProp" value="${param.integralShowProp}">
						<xform:enumsDataSource enumsType="kms_integral_rankShow" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/kms/credit">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showCredit')}
				</td>
				<td width="85%" colspan="3">
					<xform:radio showStatus="edit" property="showCredit" value="${param.showCredit}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/kms/loperation">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showLearnTime')}
				</td>
				<td width="85%" colspan="3">
					<xform:radio showStatus="edit" property="showLearnTime"  value="${param.showLearnTime}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
	</table>
	
	<script>
		function submitSelected() {
			var showIntegral = LUI.$("input[name='showIntegral']:checked").val();
			var integralShowProp = LUI.$("input[name='integralShowProp']:checked").val();
			var showCredit = LUI.$("input[name='showCredit']:checked").val();
			var showLearnTime = LUI.$("input[name='showLearnTime']:checked").val();
		
			if(showIntegral == "false"){
				showIntegral = false;
			}
			if(showLearnTime == "false"){
				showLearnTime = false;
			}
			if(showCredit == "false"){
				showCredit = false;
			}
			if(!showIntegral && !showLearnTime && !showCredit){
				seajs.use("lui/dialog",function(dialog){
					dialog.alert("${lfn:message('kms-lservice:mportal.select.atLeastSelectOne')}");
				})
				return;
			}
			var fdId = "";
			var fdName = "";
			
			if(showIntegral && showIntegral != "false"){
				fdId = "integral_" + integralShowProp;
				if(integralShowProp == "fdTotalScore"){
					fdName = "${lfn:message('kms-lservice:mportal.selectintegral_fdTotalScore')}";	
				}else{
					fdName = "${lfn:message('kms-lservice:mportal.selectintegral_fdTotalRiches')}";	
				}		
			}
			
			if(showCredit && showCredit != "false"){
				if(fdId){
					fdId += ",credit";
				}else{
					fdId += "credit";
				}		
				if(fdName){
					fdName += "," + "${lfn:message('kms-lservice:mportal.select.credit')}";
				}else{
					fdName += "${lfn:message('kms-lservice:mportal.select.credit')}";
				}				
			}			
			if(showLearnTime && showLearnTime != "false"){
				if(fdId){
					fdId += ",learnTime";
				}else{
					fdId += "learnTime";
				}		
				if(fdName){
					fdName += "," + "${lfn:message('kms-lservice:mportal.select.learnTime')}";
				}else{
					fdName += "${lfn:message('kms-lservice:mportal.select.learnTime')}";
				}				
			}
			
			window.$dialog.hide({
				"fdId":fdId, 
				"fdName":fdName
			});
		}
	</script>
				
	<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom: 5px;right: 5px;" class="lui_dialog_common_buttons clearfloat">
		<ui:button onclick="submitSelected();" text="${lfn:message('kms-lservice:mportal.select.ensure')}" />
	</div>
	
	</template:replace>
</template:include>
