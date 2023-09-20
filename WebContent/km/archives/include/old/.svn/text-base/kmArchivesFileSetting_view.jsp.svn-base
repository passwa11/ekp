<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateForm"%>
<%@ page import="com.landray.kmss.sys.archives.util.SysArchivesUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	String moduleUrl = request.getParameter("moduleUrl");
	if((_form instanceof IKmArchivesFileTemplateForm) && (KmArchivesUtil.isStartFile(moduleUrl)|| SysArchivesUtil.isStartFile(moduleUrl))) {
%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="fileForm" value="${templateForm.kmArchivesFileTemplateForm}" />
<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="km-archives" key="table.kmArchivesFileTemplate"/>" style="display:none" >
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
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
				<c:choose>
					<c:when test="${'true' eq kmAgreementTmplForm.fdReviewTextArchive }">
						${lfn:message('sys-ui:ui.switch.enabled')}
					</c:when>
					<c:otherwise>
						${lfn:message('sys-ui:ui.switch.disabled')}
					</c:otherwise>
				</c:choose>
				<br>
				<span style="">${lfn:message('km-agreement:kmAgreementTmpl.fdReviewTextArchive.desc') }</span>
			</td>
		</tr>
		<!-- 是否进行履约过程归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdNextProcessArchive"/>
			</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${'true' eq kmAgreementTmplForm.fdNextProcessArchive }">
						${lfn:message('sys-ui:ui.switch.enabled')}
					</c:when>
					<c:otherwise>
						${lfn:message('sys-ui:ui.switch.disabled')}
					</c:otherwise>
				</c:choose>
				<br>
				<span style="">${lfn:message('km-agreement:kmAgreementTmpl.fdNextProcessArchive.desc') }</span>
			</td>
		</tr>
		</c:if>
		<!-- 是否允许自动归档 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-agreement" key="kmAgreementTmpl.fdAutoArchive"/>
			</td>
			<td colspan="3">
				<c:choose>
					<c:when test="${'true' eq kmAgreementTmplForm.fdAutoArchive }">
						${lfn:message('sys-ui:ui.switch.enabled')}
					</c:when>
					<c:otherwise>
						${lfn:message('sys-ui:ui.switch.disabled')}
					</c:otherwise>
				</c:choose>
				<br>
				<span style="">
				${lfn:message('km-agreement:kmAgreementTmpl.fdAutoArchive.desc') }
				</span>
			</td>
		</tr>
	</c:if>
	
	<!-- 基础设置 -->
	<tr>
		<td colspan="4"><bean:message bundle="km-archives" key="py.JiChuSheZhi"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.category"/>
		</td>
		<td colspan="3">
            <xform:dialog propertyId="kmArchivesFileTemplateForm.categoryId" propertyName="kmArchivesFileTemplateForm.categoryName" showStatus="view" style="width:220px;">
            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','kmArchivesFileTemplateForm.categoryId','kmArchivesFileTemplateForm.categoryName',false,null,'01',file_afterSelectCategory,false);
            </xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdFilePerson"/>
		</td>
		<td colspan="3">
			<%-- <p>
				<xform:radio property="kmArchivesFileTemplateForm.selectFilePersonType">
					<xform:simpleDataSource value="org" textKey="kmArchivesFileTemplate.fromOrg" bundle="km-archives"></xform:simpleDataSource>
					<xform:simpleDataSource value="formula" textKey="kmArchivesFileTemplate.fromFormula" bundle="km-archives"></xform:simpleDataSource>
				</xform:radio>
			</p> --%>
			<p>
				<c:choose>
					<c:when test="${'org' eq fileForm.selectFilePersonType }">
						<xform:address showStatus="view" style="width:220px;" propertyName="kmArchivesFileTemplateForm.fdFilePersonName" propertyId="kmArchivesFileTemplateForm.fdFilePersonId">
						</xform:address>
					</c:when>
					<c:otherwise>
						<c:out value="${fileForm.fdFilePersonFormulaName==null?fileForm.fdFilePersonFormula: fileForm.fdFilePersonFormulaName}"></c:out>
					</c:otherwise>
				</c:choose>
				
			</p>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveApproval"/>
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
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdPreFile"/>
		</td>
		<td colspan="3">
			<c:choose>
				<c:when test="${'true' eq fileForm.fdPreFile }">
					${lfn:message('sys-ui:ui.switch.enabled')}
				</c:when>
				<c:otherwise>
					${lfn:message('sys-ui:ui.switch.disabled')}
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveOldFile"/>
		</td>
		<td colspan="3">
			<c:choose>
				<c:when test="${'true' eq fileForm.fdSaveOldFile }">
					${lfn:message('sys-ui:ui.switch.enabled')}
				</c:when>
				<c:otherwise>
					${lfn:message('sys-ui:ui.switch.disabled')}
				</c:otherwise>
			</c:choose>
			<span style="color: red;">${lfn:message('km-archives:kmArchivesFileTemplate.fdSaveOldFile.attention') }</span>
		</td>
	</tr>
	<!-- 档案信息设置 -->
	<c:if test="${'false' != param.custom }">
	<tr>
		<td colspan="4"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.setting"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.info.field"/>
		</td>
		<td colspan="3">
			<bean:message bundle="km-archives" key="kmArchivesFileTemplate.template.field"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.docSubject"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.docSubjectMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdLibrary"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.fdLibraryMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.fdVolumeYearMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdPeriod"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.fdPeriodMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdUnit"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.fdUnitMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
				<c:param name="selected" value="${fileForm.fdKeeperMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="Date|DateTime" />
				<c:param name="selected" value="${fileForm.fdValidityDateMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<%--
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdDenseLevel"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.fdDenseLevelMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	 --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-archives" key="kmArchivesMain.fdFileDate"/>
		</td>
		<td colspan="3">
			<c:import url="/km/archives/include/kmArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="Date|DateTime" />
				<c:param name="selected" value="${fileForm.fdFileDateMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	</c:if>
	<!-- 文件级设置 -->
	<tr>
		<td colspan="4"><bean:message bundle="km-archives" key="kmArchivesFileTemplate.file.setting"/></td>
	</tr>
	<tr>
		<td colspan="4">
			<table id="file_extendFieldTB" class="tb_normal" style="width:100%">
			</table>
		</td>
	</tr>
</table>
<script>
Com_AddEventListener(window, 'load', function() {
	var extendDatas = '${ fileForm.fdTmpXml}';
	if(extendDatas != '') {
		extendDatas = JSON.parse(extendDatas);
		file_buildExtendData(extendDatas);
	}
});
function file_buildExtendData(data) {
	var $table = $("#file_extendFieldTB");
	$table.empty();
	for (var i = 0; i < data.length; i++) {
		$table.append(file_buildTR(data[i]));
	}
}
function file_buildTR(row) {
	var text = '';
	if(row.value) {
		text = row.valueName;
	}
	var $tr = $("<tr>");
	$("<td class=\"td_normal_title\" width=\"15%\">").html(row.fdDisplayName).appendTo($tr);
	$("<td colspan=\"3\">").html(text).appendTo($tr);
	return $tr;
}
</script>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<%}%>