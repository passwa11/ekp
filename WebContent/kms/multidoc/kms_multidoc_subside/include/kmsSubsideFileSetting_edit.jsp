<%@page import="com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm"%>
<%@page import="com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	if((_form instanceof IKmsMultidocSubsideForm) ) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="fileTemplateForm" value="${templateForm.kmsMultidocSubsideForm}" />
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js");


	
	function modifyMultidocCate() {

		seajs
				.use(
						[ 'lui/dialog' ],
						function(dialog, env) {

							dialog
									.simpleCategory({
										modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
										authType : 0,
										idField : 'kmsMultidocSubsideForm.categoryId',
										nameField : 'kmsMultidocSubsideForm.categoryName',
										mulSelect : false,
										canClose : true,
										notNull : false,
										___urlParam : {
											'fdTemplateType' : '1,3'
										}
									});
						})

	}
</script>

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="kms-multidoc" key="kms.knowledge.subside"/>" style="display:none" id="kmsSubside_tab" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<html:hidden property="kmsMultidocSubsideForm.fdId"/>
	<html:hidden property="kmsMultidocSubsideForm.docCreatorId"/>
	<html:hidden property="kmsMultidocSubsideForm.docCreateTime"/>
	<!-- 基础设置 -->
	<tr>
		<td colspan="4">
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.option"/>
		</td>
	</tr>
	<!-- 归档的存放路径 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.category"/>
		</td>
		<td colspan="3">
            <xform:dialog subject="${lfn:message('kms-multidoc:kms.knowledge.subside.category') }" propertyId="kmsMultidocSubsideForm.categoryId" propertyName="kmsMultidocSubsideForm.categoryName" showStatus="edit" style="width:220px;">
            	modifyMultidocCate();
            </xform:dialog>
		</td>
	</tr>
	<!-- 归档后保存审批意见 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.Approval"/>
		</td>
		<td colspan="3">
			<ui:switch property="kmsMultidocSubsideForm.fdSaveApproval" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
	</tr>
	<!-- 信息设置 -->
	<c:if test="${'false' != param.custom }">
	<tr>
		<td colspan="4"><bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info.field"/>
		</td>
		<td colspan="3">
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.info.field.temple"/>
		</td>
	</tr>
	<!-- 名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docSubject"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="kmsMultidocSubsideForm.docSubjectMapping" class="inputsgl" style="width:220px;">
				<c:import url="/kms/multidoc/kms_multidoc_subside/include/kmsSubsideFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.docSubjectMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 作者 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docCreator"/>
		</td>
		<td colspan="3">
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="kmsMultidocSubsideForm.docCreatorMapping" class="inputsgl" style="width:220px;">
				<c:import url="/kms/multidoc/kms_multidoc_subside/include/kmsSubsideFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${fileTemplateForm.docCreatorMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
				</c:import>
			</select>
		</td>
	</tr>
	</c:if>
	<!-- 文件级设置 -->
<%-- 	<tr>
		<td colspan="4"><bean:message bundle="kms-multidoc" key="kms.knowledge.subside.file.setting"/></td>
	</tr>
	<tr>
		<td colspan="4">
			<table id="file_extendFieldTB" class="tb_normal" style="width:100%">
			</table>
		</td>
	</tr>
	<html:hidden property="kmsMultidocSubsideForm.fdTmpXml"/> --%>
</table>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<%}%>