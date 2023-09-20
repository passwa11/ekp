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
<c:set var="fileForm" value="${templateForm.sysArchivesFileTemplateForm}" />
<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr LKS_LabelName="<bean:message bundle="sys-archives" key="table.sysArchivesFileTemplate"/>" style="display:none" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<table class="tb_normal" width=100% style="border-top:0;">
	<!-- 合同归档设置 -->
	<c:if test="${param.formName eq 'kmAgreementTmplForm'}">
		<tr>
			<td colspan="4">合同归档设置</td>
		</tr>
		<!-- 是否进行文本文档 -->
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
		<td colspan="4"><bean:message bundle="sys-archives" key="py.JiChuSheZhi"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.category"/>
		</td>
		<td colspan="3">
            <xform:dialog propertyId="sysArchivesFileTemplateForm.categoryId" propertyName="sysArchivesFileTemplateForm.categoryName" showStatus="view" style="width:220px;">
            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','sysArchivesFileTemplateForm.categoryId','sysArchivesFileTemplateForm.categoryName',false,null,'01',file_afterSelectCategory,false);
            </xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFilePerson"/>
		</td>
		<td colspan="3">
			<%-- <p>
				<xform:radio property="sysArchivesFileTemplateForm.selectFilePersonType">
					<xform:simpleDataSource value="org" textKey="sysArchivesFileTemplate.fromOrg" bundle="sys-archives"></xform:simpleDataSource>
					<xform:simpleDataSource value="formula" textKey="sysArchivesFileTemplate.fromFormula" bundle="sys-archives"></xform:simpleDataSource>
				</xform:radio>
			</p> --%>
			<p>
				<c:choose>
					<c:when test="${'org' eq fileForm.selectFilePersonType }">
						<xform:address showStatus="view" style="width:220px;" propertyName="sysArchivesFileTemplateForm.fdFilePersonName" propertyId="sysArchivesFileTemplateForm.fdFilePersonId">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveApproval"/>
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPreFile"/>
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveOldFile"/>
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
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.docSubject"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="String" />
				<c:param name="selected" value="${fileForm.docSubjectMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
		</td>
	</tr>
	<%
		if("false".equals(SysArchivesUtil.isEopModel())) {
	%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdLibrary"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdVolumeYear"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPeriod"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdUnit"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdStorekeeper"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdValidityDate"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesMain.fdDenseLevel"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
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
			<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFileDate"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
				<c:param name="type" value="Date|DateTime" />
				<c:param name="selected" value="${fileForm.fdFileDateMapping }"></c:param>
				<c:param name="modelName" value="${param.modelName }" />
				<c:param name="templateService" value="${param.templateService }" />
				<c:param name="templateId" value="${templateForm.fdId }" />
				<c:param name="otherModelName" value="${param.otherModelName }" />
			</c:import>
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
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgPerson" />
					<c:param name="selected" value="${fileForm.fdKeeperMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>
		<!-- 档保管起始日期 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSafeSttime"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileForm.fdFileDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>
		<!-- 保管终止日期 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSafeEndtime"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileForm.fdValidityDateMapping }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>
		<!-- 形成时间 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdInitDate"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="Date|DateTime" />
					<c:param name="selected" value="${fileForm.fdInitDate }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>

		<!-- 件号 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFileNum"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileForm.fdFileNum }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>

		<!-- 文号 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdDocNumber"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileForm.fdDocNumber }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>

		<!-- 关键字 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdArchKeyword"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileForm.fdArchKeyword }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>

		<!-- 页数 -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdPagesNumber"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="String" />
					<c:param name="selected" value="${fileForm.fdPagesNumber }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
			</td>
		</tr>

		<!-- 档案所属部门  -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdEopAcrhdept"/>
			</td>
			<td colspan="3">
				<c:import url="/sys/archives/include/sysArchivesFileSetting_viewOptions.jsp" charEncoding="UTF-8">
					<c:param name="type" value="com.landray.kmss.sys.organization.model.SysOrgElement" />
					<c:param name="selected" value="${fileForm.fdAcrhdept }"></c:param>
					<c:param name="modelName" value="${param.modelName }" />
					<c:param name="templateService" value="${param.templateService }" />
					<c:param name="templateId" value="${templateForm.fdId }" />
					<c:param name="otherModelName" value="${param.otherModelName }" />
				</c:import>
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