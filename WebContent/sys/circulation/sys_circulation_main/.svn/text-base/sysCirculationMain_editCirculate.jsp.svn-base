<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<script language="JavaScript">
seajs.use(['theme!form']);
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
Com_IncludeFile("dialog.js|optbar.js");
</script>
<html:form action="/sys/circulation/sys_circulation_main/sysCirculationMain.do">
	<p class="txttitle"> <bean:message bundle="sys-circulation" key="table.sysCirculationMain" /></p>
	<center>
	<table class="tb_normal" width=95%>
	<html:hidden property="fdId" />
	<html:hidden property="fdFromParentId" />
	<html:hidden property="fdFromOpinionId" />
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
			</td>
			<td>
				<html:text property="fdCirculatorName" readonly="true" />
			</td>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculationTime" />
			</td>
			<td>
				<html:text property="fdCirculationTime" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
			</td>
			<td colspan="3">
				<xform:address htmlElementProperties="id='receivedCirCulator'" propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
				    required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }"  useNewStyle="false" showStatus="edit" style="width:95%">
				</xform:address><br/>
				<input name="fdOpinionRequired" type="hidden" value="0">
				<label>
			    	<input type="checkbox" name="opinionRequired" onclick="changeOpinionRequired(this);">传阅意见必填
			    </label>
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdNotifyType" />
			</td>
			<td colspan="3">
				<kmss:editNotifyType property="fdNotifyType" />
			</td>
		</tr>
		<tr>
		   <td class="td_normal_title" width=18%>
				<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
			</td>
			<td colspan="3">
				<xform:textarea property="fdRemark" validators="maxLength(500)" style="width:95%" showStatus="edit"/>
			</td>
		</tr>
	</table>
	<div style="padding-top: 17px;">
		<ui:button text="${ lfn:message('button.submit') }" id="submit"  onclick="CommitCirculation(document.sysCirculationMainForm, 'saveCirculate');">
		</ui:button>
		<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray"  onclick="Com_CloseWindow()">
		</ui:button>
    </div>
	</center>
	<html:hidden property="method_GET" />
	<html:hidden property="fdKey" />
	<html:hidden property="fdModelId" />
	<html:hidden property="fdModelName" />
</html:form>
	<script language="JavaScript">
		$KMSSValidation(document.forms['sysCirculationMainForm']);
		
		function CommitCirculation(form,method){
			if($KMSSValidation(document.forms['sysCirculationMainForm']).validate()){
				LUI('submit').setDisabled(true);
			}
			Com_Submit(document.sysCirculationMainForm, 'saveCirculate');
		}
		
		function changeOpinionRequired(obj){
			if(obj.checked){
				document.getElementsByName("fdOpinionRequired")[0].value="1";
			}else{
				document.getElementsByName("fdOpinionRequired")[0].value="0";
			}
		}
		
		changeRegular();
		
		function changeRegular(){
			$("#receivedCirCulator").parent().parent().removeAttr("onclick");
			$("#receivedCirCulator").parent().parent().unbind("click");
			
			if("${latestForm.fdSpreadScope}" == '0'){
				$("#receivedCirCulator").parent().parent().click(function(){
					Dialog_Address(true,'receivedCirCulatorIds','receivedCirCulatorNames',';',ORG_TYPE_ALL,null,null,null,null,null,null,null,'myDept');
             	});
			}else if("${latestForm.fdSpreadScope}" == '1'){
				$("#receivedCirCulator").parent().parent().click(function(){
					Dialog_Address(true,'receivedCirCulatorIds','receivedCirCulatorNames',';',ORG_TYPE_ALL,null,null,null,null,null,null,null,'myOrg');
             	});
			}else{
				$("#receivedCirCulator").parent().parent().click(function(){
					Dialog_AddressList(true,'receivedCirCulatorIds','receivedCirCulatorNames',';','sysCirculateOrgScopeService&orgIds=${latestForm.fdOtherSpreadScopeIds}',function(){
					
					},'sysCirculateOrgScopeService&orgIds=${latestForm.fdOtherSpreadScopeIds}&keyword=!{keyword}',null,null,"选择传阅范围");
				});
			}
		}
	</script>
	</template:replace>
</template:include>
