<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<html:form action="/sys/archives/sys_archives_file_template/sysArchivesFileTemplate.do">
	<html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <input type="hidden" name="fdMainModelId" value="${HtmlParam.fdMainModelId }"/>
    <input type="hidden" name="serviceName" value="${HtmlParam.serviceName }"/>
    <input type="hidden" name="userSetting" value="1"/>
    <input type="hidden" name="fdOldSaveApproval" value="${sysArchivesFileTemplateForm.fdSaveApproval}"/>
    <input type="hidden" name="fdOldSaveOldFile" value="${sysArchivesFileTemplateForm.fdSaveOldFile}"/>
    <input type="hidden" name="fdOldFilePersonId" value="${sysArchivesFileTemplateForm.fdFilePersonId}"/>
    <table class="tb_normal" width="100%">
    	<!-- 归档的存放路径 -->
    	<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.category"/>
			</td>
			<td colspan="3">
	            <xform:dialog required="true" subject="${lfn:message('sys-archives:sysArchivesFileTemplate.category') }" style="width:220px" propertyId="categoryId" propertyName="categoryName" showStatus="edit">
	            	Dialog_SimpleCategory('com.landray.kmss.eop.arch.model.EopArchCategory','categoryId','categoryName',false,null,'02',null,false);
	            </xform:dialog>
			</td>
		</tr>
		<!-- 归档人身份设置 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdFilePerson"/>
			</td>
			<td colspan="3">
				<p>
					<xform:radio property="selectFilePersonType" showStatus="edit" onValueChange="file_changeFilePerson" value="${sysArchivesFileTemplateForm.selectFilePersonType}">
						<xform:simpleDataSource value="org" textKey="sysArchivesFileTemplate.fromOrg" bundle="sys-archives"></xform:simpleDataSource>
						<xform:simpleDataSource value="formula" textKey="sysArchivesFileTemplate.fromFormula" bundle="sys-archives"></xform:simpleDataSource>
					</xform:radio>
				</p>
				<p>
					<div id="orgSelect">
						<xform:address orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:220px" subject="${lfn:message('sys-archives:sysArchivesFileTemplate.fdFilePerson') }" propertyName="fdFilePersonName" propertyId="fdFilePersonId">
						</xform:address>
					</div>
					<div id="formulaSelect" style="display:none">
						<input name="fdFilePersonFormula" style="width:220px;display: none" class="inputsgl" readonly="" value="${sysArchivesFileTemplateForm.fdFilePersonFormula}">
						<input name="fdFilePersonFormulaName" style="width:220px" class="inputsgl" readonly="" value="${sysArchivesFileTemplateForm.fdFilePersonFormulaName}">
						<a href="#" onclick="file_selectByFormula('fdFilePersonFormula', 'fdFilePersonFormulaName');"><bean:message key="button.select"/></a>
					</div>
				</p>
			</td>
		</tr>
		<!-- 归档后保存审批意见 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveApproval"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdSaveApproval" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			</td>
		</tr>

		<!-- 归档后保留原始文件 -->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-archives" key="sysArchivesFileTemplate.fdSaveOldFile"/>
			</td>
			<td colspan="3">
				<ui:switch property="fdSaveOldFile" 
					enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
					disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				<span style="color: red;">${lfn:message('sys-archives:sysArchivesFileTemplate.fdSaveOldFile.attention') }</span>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<ui:button text="${lfn:message('button.submit') }" onclick="Com_Submit(sysArchivesFileTemplateForm,'fileDoc')"></ui:button>
				<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</td>
		</tr>
    </table>
</html:form>
<script>
Com_IncludeFile("formula.js");
seajs.use(['lui/jquery'],function($) {
	var _validate = $KMSSValidation(document.sysArchivesFileTemplateForm);
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
		file_changeFilePerson("${sysArchivesFileTemplateForm.selectFilePersonType}");
	}); 
})
</script>
</template:replace>
</template:include>
