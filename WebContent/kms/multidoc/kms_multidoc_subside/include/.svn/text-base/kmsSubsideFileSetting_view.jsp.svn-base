<%@page import="com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	if((_form instanceof IKmsMultidocSubsideForm)) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="fileForm" value="${templateForm.kmsMultidocSubsideForm}" />
<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="kms-multidoc" key="kms.knowledge.subside"/>" style="display:none" id="kmsSubside_tab" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<!-- 基础设置 -->
	<tr>
		<td colspan="4"><bean:message bundle="kms-multidoc" key="kms.knowledge.subside.option"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.category"/>
		</td>
		<td colspan="3">
            <xform:dialog subject="${lfn:message('kms-multidoc:kms.knowledge.subside.category') }" propertyId="kmsMultidocSubsideForm.categoryId" propertyName="kmsMultidocSubsideForm.categoryName" showStatus="view" style="width:220px;">
            	Dialog_SimpleCategory('com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory','kmsMultidocSubsideForm.categoryId','kmsMultidocSubsideForm.categoryName',false,null,'01',file_afterSelectCategory,false,null,'11','fdTemplateType:1;fdTemplateType:3',null);
            </xform:dialog>
		</td>
	</tr>
	<tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.Approval"/>
		</td>
		<td colspan="3">
			<c:choose>
				<c:when test="${'true' eq fileForm.fdSaveApproval }">
					${lfn:message('sys-ui:ui.switch.enabled')}
				</c:when>
				<c:otherwise>
					${lfn:message('sys-ui:ui.switch.disabled')}
				</c:otherwise>
			</c:choose>
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
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docSubject"/>
		</td>
		<td colspan="3">
			<c:import url="/kms/multidoc/kms_multidoc_subside/include/kmsSubsideFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.docSubjectMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
			</c:import>
		</td>
	</tr>
	<!-- 作者 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-multidoc" key="kms.knowledge.subside.docCreator"/>
		</td>
		<td colspan="3">
				<c:import url="/kms/multidoc/kms_multidoc_subside/include/kmsSubsideFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${fileForm.docCreatorMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
				</c:import>
		</td>
	</tr>
	</c:if>
	<!-- 文件级设置 -->
	<%-- <tr>
		<td colspan="4"><bean:message bundle="kms-multidoc" key="kms.knowledge.subside.file.setting"/></td>
	</tr>
	<tr>
		<td colspan="4">
			<table id="file_extendFieldTB" class="tb_normal" style="width:100%">
			</table>
		</td>
	</tr> --%>
</table>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<%}%>