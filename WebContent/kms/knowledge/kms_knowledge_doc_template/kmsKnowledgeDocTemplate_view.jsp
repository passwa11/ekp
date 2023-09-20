<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<kmss:auth requestURL="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit') }"  
					onclick="Com_OpenWindow('kmsKnowledgeDocTemplate.do?method=edit&fdId=${param.fdId}','_self');"/>
			</kmss:auth>
			<kmss:auth requestURL="/kms/knowledge/kms_knowledge_doc_template/kmsKnowledgeDocTemplate.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete') }"  onclick="nconfirmDelete();"/>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close') }"  onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content" >
		<div class="lui_form_content">
			<p class="txttitle"><bean:message bundle="kms-knowledge" key="table.kmsKnowledgeDocTemplate"/></p>
			<table class="tb_normal" width=95% style="table-layout: fixed;">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.fdName"/>
					</td><td width="85%" colspan="3">
						<xform:text property="fdName"  style="width:85%" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.fdOrder"/>
					</td><td width="85%" colspan="3">
						<xform:text property="fdOrder" style="width:85%" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.docContent"/>
					</td><td width="85%" colspan="3" class="templDocContent">
						<xform:rtf property="docContent" height="400"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="kms-knowledge" key="kmsKnowledge.rattachement"/>
					</td>
					<td width="85%" colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
							<c:param name="fdMulti" value="true" />
							<c:param name="formBeanName" value="kmsKnowledgeDocTemplateForm" />
							<c:param name="fdKey" value="rattachment" />
							<c:param name="fdModelName" value="kmsKnowledgeDocTemplate" />
							<c:param name="fdModelId" value="${kmsKnowledgeDocTemplateForm.fdId}" />
						</c:import>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.docCreator"/>
					</td><td width="35%">
						<c:out value="${kmsKnowledgeDocTemplateForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.docCreateTime"/>
					</td><td width="35%">
						<xform:datetime property="docCreateTime" />
					</td>
				</tr>
			</table>
		</div>
		
		<script>
			function nconfirmDelete(msg){
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							Com_OpenWindow('kmsKnowledgeDocTemplate.do?method=delete&fdId=${param.fdId}','_self')
						}
					})
				})
		}
	</script>
	</template:replace>
</template:include>