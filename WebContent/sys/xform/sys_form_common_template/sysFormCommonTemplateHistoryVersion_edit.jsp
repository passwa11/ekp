<%@ page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="xFormTemplateForm" value="${sysFormCommonTempHistoryForm}" />
<kmss:windowTitle 
	moduleKey="sys-xform:xform.title" 
	subjectKey="sys-xform:tree.xform.def" 
	subject="${sysFormCommonTempHistoryForm.fdName}" />
<html:form
	action="/sys/xform/sys_form_common_template_history/sysFormCommonTemplateHistory.do" method="post">
	<html:hidden property="fdId" />
	<div id="optBarDiv">
		<input id="languageUpdate"  type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysFormCommonTempHistoryForm, 'update');">
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle">通用表单历史模板_V${xFormTemplateForm.fdTemplateEdition }</p>

	<center>
	<html:hidden property="fdModelName" />
	<html:hidden property="fdKey" />
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-xform' key='sysFormCommonTemplate.templateInfo'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${xFormTemplateForm.fdKey}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform" key="sysFormCommonTemplate.fdName" />
						</td>
						<td>
							<bean:write name="sysFormCommonTempHistoryForm" property="fdName" />
						</td>
					</tr>
					<c:import url="/sys/xform/base/sysFormTemplateHistoryDisplay_edit.jsp"	charEncoding="UTF-8">
						<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
						<c:param name="formName" value="sysFormCommonTempHistoryForm" />
						<c:param name="noProcessFlow" value="true"></c:param>
					</c:import>
				</table>
			</td>
		</tr>	
		<%--多语言 --%>
		<% if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
		<c:import url="/sys/xform/lang/include/sysFormHistoryMultiLang_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysFormCommonTempHistoryForm" />
			<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
		</c:import>
		<%}%>
	</table>	
	</center>
	<html:hidden property="method_GET" />
</html:form>
<script language="JavaScript">
Com_IncludeFile("dialog.js");
Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = XForm_ConfirmFormChangedEvent;

function XForm_ConfirmFormChangedEvent() {
	return XForm_ConfirmFormChangedFun();
}
Com_AddEventListener(window, "load", function() {
	LoadXForm(document.getElementById('TD_FormTemplate_${xFormTemplateForm.fdKey}'));
});

<%-- 
=====================================
 数据字典加载相关
===================================== 
--%>
var _xform_MainModelName = '${param.fdMainModelName}';

function _XForm_GetSysDictObj(modelName){
	return Formula_GetVarInfoByModelName(modelName);
}
function _XForm_GetSysDictObj_${xFormTemplateForm.fdKey}() {
	return _XForm_GetSysDictObj(_xform_MainModelName);
}
function _XForm_GetTempExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
}
function _XForm_GetCommonExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=common&tempId="+tempId).GetHashMapArray();
}
function _XForm_GetExitFileDictObj(fileName) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileName).GetHashMapArray();
}
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>
