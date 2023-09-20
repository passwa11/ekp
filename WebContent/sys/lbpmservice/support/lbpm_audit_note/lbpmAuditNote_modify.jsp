<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="body">
<script>
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
Com_IncludeFile("swf_attachment.js?mode=edit","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);
/**
 * 校验审批意见必填和长度小于200
 */
function _validateFdAuditNote(){
	var $KMSSValidation = $GetKMSSDefaultValidation();
	var fdAduitNote = $("textarea[name='fdAuditNote']",document)[0];
	var result = $KMSSValidation.validateElement(fdAduitNote);
	if (!result){
		return false;
	}else{
		return true;
	}
}
</script>
<html:form action="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do">
<p class="txttitle" style="color:#333;"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmAuditNote.modify"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdUsageContent"/>
		</td><td width="85%">
			<xform:textarea property="fdAuditNote" style="width:95%;height:200px" validators="maxLength(4000)" showStatus="edit" value="${lbpmAuditNote.fdAuditNote}" />
		</td>
	</tr>
	 <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdAttachments"/>
		</td>
		 <td width="85%">
			<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
		          <c:param name="formBeanName" value="${requestScope['formBeanName']}"/>
		          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
		          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
		          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
		          <c:param name="fdViewType" value="byte" />
			 </c:import>
			
		</td> 
	</tr>
</table>
<div style="padding-top: 17px;">
	<ui:button text="${ lfn:message('button.save') }"
		onclick="if (_validateFdAuditNote()) {Com_Submit(document.lbpmAuditNoteForm, 'updateAuditNote');}" />
	<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
</div>
</center>
<html:hidden property="fdId" value="${lbpmAuditNote.fdId}"/>
<html:hidden property="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
<html:hidden property="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
<input type="hidden" name = "historyAuditNoteId" value="${requestScope['historyAuditNoteId']}"/>
<input type="hidden" name="_${_jsKey}_oldAttachmentsId" />
</html:form>
	</template:replace>
</template:include>