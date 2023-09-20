<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.update') }" onclick="updateFdOffsetters();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do">
			<div class="lui_form_content_frame" style="padding-top:20px;height:200px;">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width="15%" align="center">
							<bean:message bundle="fssc-loan" key="fsscLoanMain.updateFdOffsetters.botton" />
						</td>
						<td width="40%">
							<xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" required="true" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
							<html:hidden property="fdId" value="${param.fdId}"/>
						</td>
					</tr>
				</table>
			</div>						
		<ui:tabpage expand="false" >							
		</ui:tabpage>		 
		</html:form>				
		<script>
		$KMSSValidation(document.forms['fsscLoanMainForm']);	
		function updateFdOffsetters(){
			Com_Submit(document.fsscLoanMainForm, "updateFdOffsetters");
		}
		
		</script>
	</template:replace>		
</template:include>
