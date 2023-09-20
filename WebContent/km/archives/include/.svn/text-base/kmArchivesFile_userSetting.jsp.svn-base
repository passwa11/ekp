<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<html:form action="/km/archives/km_archives_file_template/kmArchivesFileTemplate.do">
	<html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <input type="hidden" name="fdMainModelId" value="${HtmlParam.fdMainModelId }"/>
    <input type="hidden" name="serviceName" value="${HtmlParam.serviceName }"/>
    <input type="hidden" name="userSetting" value="1"/>
    <input type="hidden" name="fdOldSaveApproval" value="${kmArchivesFileTemplateForm.fdSaveApproval}"/>
    <input type="hidden" name="fdOldSaveOldFile" value="${kmArchivesFileTemplateForm.fdSaveOldFile}"/>
    <input type="hidden" name="fdOldFilePersonId" value="${kmArchivesFileTemplateForm.fdFilePersonId}"/>
    <table class="tb_normal" width="100%">
    	<!-- 归档的存放路径 -->
    	<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.category"/>
			</td>
			<td colspan="3">
	            <xform:dialog required="true" subject="${lfn:message('km-archives:kmArchivesFileTemplate.category') }" style="width:220px" propertyId="categoryId" propertyName="categoryName" showStatus="edit">
	            	Dialog_SimpleCategory('com.landray.kmss.km.archives.model.KmArchivesCategory','categoryId','categoryName',false,null,'02',null,false);
	            </xform:dialog>
			</td>
		</tr>
		<!-- 归档人身份设置 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdFilePerson"/>
			</td>
			<td colspan="3">
				<p>
					<xform:radio property="selectFilePersonType" showStatus="edit" onValueChange="file_changeFilePerson" value="${kmArchivesFileTemplateForm.selectFilePersonType}">
						<xform:simpleDataSource value="org" textKey="kmArchivesFileTemplate.fromOrg" bundle="km-archives"></xform:simpleDataSource>
						<xform:simpleDataSource value="formula" textKey="kmArchivesFileTemplate.fromFormula" bundle="km-archives"></xform:simpleDataSource>
					</xform:radio>
				</p>
				<p>
					<div id="orgSelect">
						<xform:address orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:220px" subject="${lfn:message('km-archives:kmArchivesFileTemplate.fdFilePerson') }" propertyName="fdFilePersonName" propertyId="fdFilePersonId">
						</xform:address>
					</div>
					<div id="formulaSelect" style="display:none">
						<input name="fdFilePersonFormula" style="width:220px" class="inputsgl" readonly="" value="${kmArchivesFileTemplateForm.fdFilePersonFormula}">
		            	<a href="#" onclick="file_selectByFormula('fdFilePersonFormula', 'fdFilePersonFormula');"><bean:message key="button.select"/></a>
					</div>
				</p>
			</td>
		</tr>
		<!-- 归档后保存审批意见 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveApproval"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdSaveApproval" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdPreFile"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdPreFile" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</td>
		</tr>
		<!-- 归档后保留原始文件 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="km-archives" key="kmArchivesFileTemplate.fdSaveOldFile"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdSaveOldFile" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				<span style="color: red;">${lfn:message('km-archives:kmArchivesFileTemplate.fdSaveOldFile.attention') }</span>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<ui:button text="${lfn:message('button.submit') }" onclick="Com_Submit(kmArchivesFileTemplateForm,'fileDoc')"></ui:button>
				<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</td>
		</tr>
    </table>
</html:form>
<script>
Com_IncludeFile("formula.js");
seajs.use(['lui/jquery'],function($) {
	var _validate = $KMSSValidation(document.kmArchivesFileTemplateForm);
	// 切换归档人身份设置类型
	window.file_changeFilePerson = function(type) {
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
	//公式选择器
	window.file_selectByFormula = function(idField,nameField) {
		Formula_Dialog_Simple(idField, nameField, "${JsParam.fdMainModelName}", "com.landray.kmss.sys.organization.model.SysOrgElement[]");
	}
	
	$(document).ready(function() { 
		// 控制 归档人身份设置 显示
		file_changeFilePerson("${kmArchivesFileTemplateForm.selectFilePersonType}");
	}); 
})
</script>
</template:replace>
</template:include>
