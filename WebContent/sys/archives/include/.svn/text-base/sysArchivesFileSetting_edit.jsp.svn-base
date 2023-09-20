<%@page import="com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm"%>
<%@page import="com.landray.kmss.sys.archives.util.SysArchivesUtil"%>
<%@page import="com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	String moduleUrl = request.getParameter("moduleUrl");
	if((_form instanceof ISysArchivesFileTemplateForm) && SysArchivesUtil.isStartFile(moduleUrl)) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="fileTemplateForm" value="${templateForm.sysArchivesFileTemplateForm}" />
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js");
</script>

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="sys-archives" key="table.sysArchivesFileTemplate"/>" style="display:none" id="sysArchivesFile_tab" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<html:hidden property="sysArchivesFileTemplateForm.fdId"/>
	<html:hidden property="sysArchivesFileTemplateForm.docCreatorId"/>
	<html:hidden property="sysArchivesFileTemplateForm.docCreateTime"/>
	<tr>
		<td colspan="4" style="color:red;">${lfn:message('sys-archives:file.include.tip') }</td>
	</tr>
	
	<!-- 合同归档设置 -->
	<c:if test="${param.formName eq 'kmAgreementTmplForm'}">
		<tr>
			<td colspan="4">合同归档设置</td>
		</tr>
		<!-- 是否进行文本文档 -->
		<c:if test="${version eq 'professional' }">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdReviewTextArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdReviewTextArchive" 
					id = "fdReviewTextArchiveId"
					onValueChange="archives_text_change(this.checked);" 
					checked = "${kmAgreementTmplForm.fdReviewTextArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdReviewTextArchive.desc') }
				</span>
			</td>
		</tr>
		<!-- 是否进行履约过程归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdNextProcessArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdNextProcessArchive" 
					id = "fdNextProcessArchiveId"
					onValueChange="archives_process_change(this.checked);" 
					checked = "${kmAgreementTmplForm.fdNextProcessArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdNextProcessArchive.desc') }
				</span>
			</td>
		</tr>
		</c:if>
		<!-- 是否允许自动归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdAutoArchive"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdAutoArchive" 
					checked = "${kmAgreementTmplForm.fdAutoArchive}"
					checkVal="true"
					unCheckVal="false"
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
				</ui:switch>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdAutoArchive.desc') }
				</span>
			</td>
		</tr>
	</c:if>
	
	<!-- 基础设置 -->
	<tr>
		<td colspan="4">
			<bean:message bundle="sys-archives" key="py.JiChuSheZhi"/>
			<% if(_form instanceof ISysFormTemplateForm) {%>
			 <%@include file="/sys/archives/include/sysArchivesFileSetting_importFields.jsp" %>
			<%} %>
		</td>
	</tr>
	<!-- 归档的存放路径 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.category"/>
		</td>
		<td colspan="3">
			<%
				if("true".equals(SysArchivesUtil.isEopModel())) {
			%>
			<xform:dialog subject="${lfn:message('sys-archives:sysArchivesFileTemplate.category') }" propertyId="sysArchivesFileTemplateForm.categoryId" propertyName="sysArchivesFileTemplateForm.categoryName" showStatus="edit" style="width:220px;">
				Dialog_SimpleCategory('com.landray.kmss.eop.arch.model.EopArchCategory','sysArchivesFileTemplateForm.categoryId','sysArchivesFileTemplateForm.categoryName',false,null,'00',eop_file_afterSelectCategory,false);
			</xform:dialog>
			<%
				}else{
			%>
			<xform:dialog subject="${lfn:message('sys-archives:sysArchivesFileTemplate.category') }" propertyId="sysArchivesFileTemplateForm.categoryId" propertyName="sysArchivesFileTemplateForm.categoryName" showStatus="edit" style="width:220px;">
				Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','sysArchivesFileTemplateForm.categoryId','sysArchivesFileTemplateForm.categoryName',false,null,'00',file_afterSelectCategory,false);
			</xform:dialog>
			<%
				}
			%>
		</td>
	</tr>
	<!-- 归档人身份设置 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFilePerson"/>
		</td>
		<td colspan="3">
			<p>
				<xform:radio property="sysArchivesFileTemplateForm.selectFilePersonType" onValueChange="file_changeFilePerson">
					<xform:simpleDataSource value="org" textKey="sysArchivesFileTemplate.fromOrg" bundle="sys-archives"></xform:simpleDataSource>
					<xform:simpleDataSource value="formula" textKey="sysArchivesFileTemplate.fromFormula" bundle="sys-archives"></xform:simpleDataSource>
				</xform:radio>
			</p>
			<p>
				<div id="orgSelect">
					<xform:address orgType="ORG_TYPE_PERSON" subject="${lfn:message('sys-archives:sysArchivesFileTemplate.fdFilePerson') }" style="width:220px;" propertyName="sysArchivesFileTemplateForm.fdFilePersonName" propertyId="sysArchivesFileTemplateForm.fdFilePersonId">
					</xform:address>
				</div>
				<div id="formulaSelect">
					<input name="sysArchivesFileTemplateForm.fdFilePersonFormula" class="inputsgl" style="width:200px" type="hidden" value='${fileTemplateForm.fdFilePersonFormula }'>
					<input name="sysArchivesFileTemplateForm.fdFilePersonFormulaName" class="inputsgl" style="width:200px" readonly="readonly" value='${fileTemplateForm.fdFilePersonFormulaName==null? fileTemplateForm.fdFilePersonFormula:fileTemplateForm.fdFilePersonFormulaName}'>
	            	<a href="#" onclick="file_selectByFormula('sysArchivesFileTemplateForm.fdFilePersonFormula', 'sysArchivesFileTemplateForm.fdFilePersonFormulaName');"><bean:message key="button.select"/></a>
				</div>
			</p>
		</td>
	</tr>
	<!-- 归档后保存审批意见 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveApproval"/>
		</td>
		<td colspan="3">
			<ui:switch property="sysArchivesFileTemplateForm.fdSaveApproval" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
	</tr>
	<%
		if(!"true".equals(SysArchivesUtil.isEopModel())) {
	%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPreFile"/>
		</td>
		<td colspan="3">
			<ui:switch property="sysArchivesFileTemplateForm.fdPreFile" 
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
	</tr>
	<%
		}
	%>
	<!-- 归档后保留原始文件 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveOldFile"/>
		</td>
		<td colspan="3">
			<ui:switch property="sysArchivesFileTemplateForm.fdSaveOldFile"
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			<span style="color: red;">${lfn:message('sys-archives:sysArchivesFileTemplate.fdSaveOldFile.attention') }</span>
		</td>
	</tr>
	<!-- 档案信息设置 -->
	<c:if test="${'false' != param.custom }">
	<tr>
		<td colspan="4"><bean:message bundle="sys-archives" key="sysArchivesFileTemplate.info.setting"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.info.field"/>
		</td>
		<td colspan="3">
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.template.field"/>
		</td>
	</tr>
	<!-- 档案名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.docSubject"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.docSubjectMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.docSubjectMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<%
		if("false".equals(SysArchivesUtil.isEopModel())) {
	%>
	<!-- 所属卷库 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdLibrary"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.fdLibraryMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdLibraryMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 组卷年度 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.fdVolumeYearMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdVolumeYearMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管期限 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPeriod"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.fdPeriodMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdPeriodMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管单位 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdUnit"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.fdUnitMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgElement" />
					<c:param name="selected" value="${fileTemplateForm.fdUnitMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 保管员 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="sysArchivesFileTemplateForm.fdKeeperMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${fileTemplateForm.fdKeeperMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 档案有效期 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdValidityDate"/>
		</td>
		<td colspan="3">
			<select ftype="Date|DateTime" name="sysArchivesFileTemplateForm.fdValidityDateMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileTemplateForm.fdValidityDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 密级程度 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdDenseLevel"/>
		</td>
		<td colspan="3">
			<select ftype="String" name="sysArchivesFileTemplateForm.fdDenseLevelMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileTemplateForm.fdDenseLevelMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>

	<!-- 归档日期 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFileDate"/>
		</td>
		<td colspan="3">
			<select ftype="Date|DateTime" name="sysArchivesFileTemplateForm.fdFileDateMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileTemplateForm.fdFileDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
			<%
			}
		%>
		<%--数字档案字段开始--%>
		<%
			if("true".equals(SysArchivesUtil.isEopModel())) {
		%>


	<!-- 责任人 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdEopCreator"/>
		</td>
		<td colspan="3">
			<select ftype="com.landray.kmss.sys.organization.model.SysOrgPerson" name="sysArchivesFileTemplateForm.fdKeeperMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${fileTemplateForm.fdKeeperMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
	<!-- 档保管起始日期 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSafeSttime"/>
		</td>
		<td colspan="3">
			<select ftype="Date|DateTime" name="sysArchivesFileTemplateForm.fdFileDateMapping" class="inputsgl" style="width:220px;">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileTemplateForm.fdFileDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</select>
		</td>
	</tr>
		<!-- 保管终止日期 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSafeEndtime"/>
			</td>
			<td colspan="3">
				<select ftype="Date|DateTime" name="sysArchivesFileTemplateForm.fdValidityDateMapping" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="Date|DateTime" />
						<c:param name="selected" value="${fileTemplateForm.fdValidityDateMapping }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>
		<!-- 形成时间 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdInitDate"/>
			</td>
			<td colspan="3">
				<select ftype="Date|DateTime" name="sysArchivesFileTemplateForm.fdInitDate" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="Date|DateTime" />
						<c:param name="selected" value="${fileTemplateForm.fdInitDate }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>

		<!-- 件号 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFileNum"/>
			</td>
			<td colspan="3">
				<select ftype="String" name="sysArchivesFileTemplateForm.fdFileNum" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="String" />
						<c:param name="selected" value="${fileTemplateForm.fdFileNum }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>

		<!-- 文号 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdDocNumber"/>
			</td>
			<td colspan="3">
				<select ftype="String" name="sysArchivesFileTemplateForm.fdDocNumber" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="String" />
						<c:param name="selected" value="${fileTemplateForm.fdDocNumber }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>

		<!-- 关键字 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdArchKeyword"/>
			</td>
			<td colspan="3">
				<select ftype="String" name="sysArchivesFileTemplateForm.fdArchKeyword" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="String" />
						<c:param name="selected" value="${fileTemplateForm.fdArchKeyword }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>

		<!-- 页数 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPagesNumber"/>
			</td>
			<td colspan="3">
				<select ftype="String" name="sysArchivesFileTemplateForm.fdPagesNumber" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="String" />
						<c:param name="selected" value="${fileTemplateForm.fdPagesNumber }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>



		<!-- 档案所属部门  -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdEopAcrhdept"/>
			</td>
			<td colspan="3">
				<select ftype="String" name="sysArchivesFileTemplateForm.fdAcrhdept" class="inputsgl" style="width:220px;">
					<c:import url="/sys/archives/include/sysArchivesFileSetting_editOptions.jsp" charEncoding="UTF-8">
						<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgElement" />
						<c:param name="selected" value="${fileTemplateForm.fdAcrhdept }"></c:param>
						<c:param name="modelName" value="${param.modelName }" />
						<c:param name="templateService" value="${param.templateService }" />
						<c:param name="templateId" value="${templateForm.fdId }" />
						<c:param name="otherModelName" value="${param.otherModelName }" />
					</c:import>
				</select>
			</td>
		</tr>
		<%
			}
		%>
		<%--数字档案字段结束--%>
	</c:if>
	<!-- 文件级设置 -->
	<tr>
		<td colspan="4"><bean:message bundle="sys-archives" key="sysArchivesFileTemplate.file.setting"/></td>
	</tr>
	<tr>
		<td colspan="4">
			<table id="file_extendFieldTB" class="tb_normal" style="width:100%">
			</table>
		</td>
	</tr>
	<html:hidden property="sysArchivesFileTemplateForm.fdTmpXml"/>
</table>
<script>
	Com_AddEventListener(window, 'load', function() {
		var extendDatas = $("input[name='sysArchivesFileTemplateForm.fdTmpXml']").val();
		if(extendDatas) {
			extendDatas = JSON.parse(extendDatas);
			file_buildExtendData(extendDatas);
		}
		file_changeFilePerson('${fileTemplateForm.selectFilePersonType}');
	});
	//公式选择器
	function file_selectByFormula(idField,nameField) {
		Formula_Dialog_Simple(idField, nameField, "${param.modelName }", "com.landray.kmss.sys.organization.model.SysOrgElement[]");
	}
	//切换归档人身份设置类型
	function file_changeFilePerson(type) {
		var $formulaSelect = $("#formulaSelect");
		var $orgSelect = $("#orgSelect");
		if(type == 'org') {
			$orgSelect.show();
			$formulaSelect.hide();
		}else {
			$orgSelect.hide();
			$formulaSelect.show();
		}
	}
	//选择分类之后
	function file_afterSelectCategory(rtn) {
		if(rtn && rtn.data && rtn.data.length > 0) {
			var url = '${LUI_ContextPath}/km/archives/km_archives_category/kmArchivesCategory.do?method=getExtendData&fdId='+rtn.data[0].id;
			$.get(url,function(data) {
				if(data) {
					file_buildExtendData(data);
				}
			});
		}
	}
	function eop_file_afterSelectCategory(rtn) {
		if(rtn && rtn.data && rtn.data.length > 0) {
			var url = '${LUI_ContextPath}/eop/arch/eop_arch_category/eopArchCategory.do?method=getExtendData&fdId='+rtn.data[0].id;
			$.get(url,function(data) {
				if(data) {
					file_buildExtendData(data);
				}
			});
		}
	}
	//根据扩展数据构建文件级标签的表格
	function file_buildExtendData(data) {
		var $table = $("#file_extendFieldTB");
		$table.empty();
		for (var i = 0; i < data.length; i++) {
			var row = data[i];
			var url = '${LUI_ContextPath}/sys/archives/sys_archives_file_template/sysArchivesFileTemplate.do?method=getTypeFields';
			$.ajax({
				type:'get',
				url:url,
				data:{type:row.fdType,modelName:'${param.modelName}',templateService:'${param.templateService}',templateId:'${templateForm.fdId}',otherModelName:'${param.otherModelName}'},
				dataType:'json',
				async:false,
				success:function(options) {
					$table.append(file_buildTR(row,options));
				}
			})
		}
	}
	//构建行
	function file_buildTR(row,options) {
		//选项
		var $select = file_buildOptions(row,options);
		var $tr = $("<tr>");
		$("<td class=\"td_normal_title\" width=\"15%\">").html(row.fdDisplayName).appendTo($tr);
		$("<td colspan=\"3\">").append($select).appendTo($tr);
		if(row.value) {
			$select.val(row.value);
		}
		return $tr;
	}
	//构建select
	function file_buildOptions(row,options) {
		var $select = $("<select ftype=\""+row.fdType+"\" class=\"inputsgl\" style=\"width:220px;\">");
		$select.append($("<option value>").text("${lfn:message('page.firstOption') }"))
		for(var o in options) {
			$select.append($("<option value=\""+o+"\">").text(options[o]));
		}
		$select.data('extendData',row);
		return $select;
	}
	//提交表单前保存归档扩展字段数据
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		var tmpXml = [];
		$("#file_extendFieldTB select").each(function(item,index) {
			var $select = $(this);
			var obj = $select.data('extendData');
			obj.value = $select.val();
			if(obj.value != null && obj.value != '') {
				obj.valueName = $select.find('option:selected').text();
			}
			tmpXml.push(obj);
		});
		$("input[name='sysArchivesFileTemplateForm.fdTmpXml']").val(JSON.stringify(tmpXml));
		return true;
	};
	//文本归档开关切换事件
	function archives_text_change(checked) {
		//文本归档关闭时，过程归档自动关闭
		if (checked == false) {
			if (LUI("fdNextProcessArchiveId").checkbox.prop("checked") == true) {
				LUI("fdNextProcessArchiveId").checkbox.click();
			}
		}
	}
	//过程归档开关切换事件
	function archives_process_change(checked) {
		//过程归档打开时，文本归档自动打开
		if (checked == true) {
			if (LUI("fdReviewTextArchiveId").checkbox.prop("checked") == false) {
				LUI("fdReviewTextArchiveId").checkbox.click();
			}
		}
	}
</script>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<%}%>