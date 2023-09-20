<%@ page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/datainit/resource/css/helpTip.css" rel="stylesheet" type="text/css">
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="entityName" value="${param.fdMainModelName}_${sysFormCommonTemplateForm.fdId}" />
<kmss:windowTitle 
	moduleKey="sys-xform:xform.title" 
	subjectKey="sys-xform:tree.xform.def" 
	subject="${sysFormCommonTemplateForm.fdName}" />
<script>
	function sysFromCommonTemplate_submit(method){
		if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
			XForm_BeforeSubmitForm(function(){
				Com_Submit(document.sysFormCommonTemplateForm, method);
			});
		}else{
			Com_Submit(document.sysFormCommonTemplateForm, method);
        }
	}
	// XForm_ConfirmFormChangedEvent必须放在Xform_BuildValueInConfirm方法之前
	Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = XForm_ConfirmFormChangedEvent;

	function XForm_ConfirmFormChangedEvent(formObj, method, clearParameter, moreOptions,isNotSupportModalDailog , callback) {
		return XForm_ConfirmFormChangedFun(isNotSupportModalDailog , callback);
	}
	//初始默认模板
	$(document).ready(function(){
		var method = Com_GetUrlParameter(location.href,"method");
		if(method=='add'){
			var fdModelName = "${JsParam.fdModelName}";
			var fdKey = "${JsParam.fdKey}";
			var data = new KMSSData();
			data.AddBeanData('sysFormCommonTemplateDeafaultService&fdModelName='+fdModelName+'&fdKey='+fdKey);
			var html = data.GetHashMapArray()[0]['fdDesignerHtml'];
			if(html!=null&&html!=''&&html!=undefined) {
				var iframe = document.getElementById('IFrame_FormTemplate_' + fdKey);
				Com_AddEventListener(iframe, 'load', function (){
					iframe.contentWindow.Designer.instance.setHTML(html);
				});
			}
		}
	});
</script>
<html:form
	action="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do">
	<html:hidden property="fdId" />
	<div id="optBarDiv">
	<c:if test="${sysFormCommonTemplateForm.method_GET=='edit'}">
		<%--内置表单模板不允许编辑和删除--%>
		<c:if test="${sysFormCommonTemplateForm.fdIsInside ne 1}">
			<input id="languageUpdate"  type=button value="<bean:message key="button.update"/>"
				onclick="sysFromCommonTemplate_submit('update');">
		</c:if>
	</c:if>
	<c:if test="${sysFormCommonTemplateForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="sysFromCommonTemplate_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="sysFromCommonTemplate_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle">
		<bean:message bundle="sys-xform" key="table.sysFormCommonTemplate" />
	</p>

	<center>
	<c:if test="${sysFormCommonTemplateForm.method_GET=='add'}">
		<html:hidden property="fdModelName" value="${HtmlParam.fdModelName }" />
		<html:hidden property="fdKey" value="${HtmlParam.fdKey }" />
	</c:if>
	<c:if test="${sysFormCommonTemplateForm.method_GET=='edit'}">
		<html:hidden property="fdModelName" />
		<html:hidden property="fdKey" />
	</c:if>
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-xform' key='sysFormCommonTemplate.templateInfo'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${HtmlParam.fdKey}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform" key="sysFormCommonTemplate.fdName" />
						</td>
						<td>
							<xform:text property="${sysFormTemplateFormPrefix}fdName" subject="${lfn:message('sys-xform:sysFormCommonTemplate.fdName')}" required="true" validators="maxLength(200)" style="width:95%" htmlElementProperties="onkeydown='if(event.keyCode==13){return false;}'" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform" key="sysFormCommonTemplate.fdIsDefault.view"/>
							<div class="lui_prompt_tooltip" title="${lfn:message('sys-xform:sysFormCommonTemplate.fdIsDefault.des')}">
								<label class="lui_prompt_tooltip_drop">
									<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/style/img/promptControl.png">
								</label>
								<div class="lui_dropdown_tooltip_menu" name="useDefaultLayoutTip" style="display: none;"><bean:message bundle="sys-xform" key="sysFormCommonTemplate.fdIsDefault.des"/></div>
							</div>
						</td><td width="85%" colspan="3">
						<xform:checkbox property="fdIsDefault">
							<xform:simpleDataSource value="1"><bean:message key="message.yes"/></xform:simpleDataSource>
						</xform:checkbox>
					</td>
					</tr>
					<%@ include file="/sys/xform/base/sysFormTemplateDisplay_edit.jsp"%>
				</table>
			</td>
		</tr>	
		<%--多语言 --%>
		<% if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
		<c:import url="/sys/xform/lang/include/sysFormCommonMultiLang_edit.jsp"	charEncoding="UTF-8">
		</c:import>
		<%}%>
	</table>	
	</center>
	<html:hidden property="method_GET" />
</html:form>
<script language="JavaScript">
$KMSSValidation();
Com_IncludeFile("dialog.js");

Com_AddEventListener(window, "load", function() {
	XForm_DisplayFormRowSet();
	LoadXForm(document.getElementById('TD_FormTemplate_${JsParam.fdKey}'));
});

if (window.$) {
		$("tr:visible *[onresize]").each(function(){
			var funStr = this.getAttribute("onresize");
			if(funStr!=null && funStr!=""){
				var tmpFunc = new Function(funStr);
				tmpFunc.call(this);
			}
		});
}
<%-- 
=====================================
 数据字典加载相关
===================================== 
--%>
var _xform_MainModelName = '${JsParam.fdMainModelName}';

function _XForm_GetSysDictObj(modelName){
	return Formula_GetVarInfoByModelName(modelName);
}
function _XForm_GetSysDictObj_${JsParam.fdKey}() {
	return _XForm_GetSysDictObj(_xform_MainModelName);
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
