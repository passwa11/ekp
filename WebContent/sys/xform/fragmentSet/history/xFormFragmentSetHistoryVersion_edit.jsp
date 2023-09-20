<%@ page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="fdKey" value="" />
<c:set var="xFormTemplateForm" value="${sysFormFragmentSetHistoryForm}" />
<kmss:windowTitle moduleKey="sys-xform-fragmentSet:module.xform.manage"
	subjectKey="sys-xform-fragmentSet:table.sysFormFragmentSet"
	subject="${sysFormFragmentSetHistoryForm.fdName}" />
<html:form
	action="/sys/xform/fragmentSet/history/xFormFragmentSetHistory.do" method="post">
	<html:hidden property="fdId" />
	<div id="optBarDiv">
		<input id="languageUpdate"  type=button value="<bean:message key="button.update"/>"
			onclick="sysFromFragmentSetHistory_submit('update');">
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle">
		<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.historyVersion" />_V${xFormTemplateForm.fdTemplateEdition }
	</p>

	<center>
	<html:hidden name="sysFormFragmentSetHistoryForm" property="fdId" />
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdName" />
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdName" />
						</td>
					</tr>
					<%--适用类别--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdCatoryName" />
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdCategoryName" />
						</td>
					</tr>
					<!-- 排序号 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message	bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdOrder" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdOrder" name="sysFormFragmentSetHistoryForm" />
						</td>
					</tr>
					<!-- 使用范围 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.scope" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdScopeName" name="sysFormFragmentSetHistoryForm" />
						</td>
					</tr>
					<tr>
					<tr>
						<!-- 修改人 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlteror" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdAlterorName" />
						</td>
						
						<!-- 修改时间 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlterTime" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdAlterTime" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<!-- 表单 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.templateSet'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${HtmlParam.fdKey}">
					<c:import url="/sys/xform/base/sysFormTemplateHistoryDisplay_edit.jsp"	charEncoding="UTF-8">
						<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
						<c:param name="formName" value="sysFormFragmentSetHistoryForm" />
						<c:param name="noProcessFlow" value="true"></c:param>
					</c:import>
				</table>
			</td>
		</tr>	
		
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

function sysFromFragmentSetHistory_submit(method){
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		customIframe.Designer.instance.isNeedBreakValidate = true;
	}
	if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
		XForm_BeforeSubmitForm(function(){
			Com_Submit(document.sysFormFragmentSetHistoryForm, method);
		});
	}else{
		Com_Submit(document.sysFormFragmentSetHistoryForm, method);
    }
}

<%-- 
=====================================
 数据字典加载相关
===================================== 
--%>
var _xform_MainModelName = '';

//限定范围仅为一个时才允许使用跟主文档相关的变量
function _XForm_GetSysDictObj(modelName){
	var rtnVal = ""
	var scopeName = $("input[name='fdScopeId']").val();
	if (!scopeName){
		return rtnVal;
	}else{
		var scopeNameArr = new Array();
		scopeNameArr = scopeName.split(";");
		if (scopeNameArr.length === 1){
			return Formula_GetVarInfoByModelName(scopeName);
		}else{
			return rtnVal;
		}
	}
}
function _XForm_GetSysDictObj_${JsParam.fdKey}() {
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
$KMSSValidation();
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>
