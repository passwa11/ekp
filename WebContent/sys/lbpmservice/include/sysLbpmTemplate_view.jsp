<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil" %>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("jquery.js");
</script>
<c:set var="lbpmTemplateForm" value="${requestScope[param.formName].sysWfTemplateForms[param.fdKey]}" />
<c:set var="lbpmTemplateFormPrefix" value="sysWfTemplateForms.${param.fdKey}." />
<c:set var="lbpmTemplate_ModelName" value="${requestScope[param.formName].modelClass.name}" />
<c:set var="lbpmTemplate_Key" value="${param.fdKey}" />
<%
	pageContext.setAttribute("lbpmTemplate_MainModelName",
			LbpmTemplateUtil.getMainModelName(
					(String)pageContext.getAttribute("lbpmTemplate_ModelName"),
					(String)pageContext.getAttribute("lbpmTemplate_Key")));
%>
<tr id="WF_TR_ID_${HtmlParam.fdKey }" LKS_LabelId="WF_TR_${lbpmTemplate_Key}" LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-lbpmservice-support:lbpmTemplate.tab.label'}" />" style="display:none">
	<td>
		<table class="tb_normal" width="100%">
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType" />
				</td>
				<td width="85%">
					<c:if test="${lbpmTemplateForm.fdType=='1'}">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.default" />
					</c:if>
					<c:if test="${lbpmTemplateForm.fdType=='2'}">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.other" />
					</c:if>
					<c:if test="${lbpmTemplateForm.fdType=='3'}">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.define" />
					</c:if>
					<c:if test="${lbpmTemplateForm.fdType=='4'}">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.free" />
					</c:if>
				</td>
			</tr>
			<c:if test="${lbpmTemplateForm.fdType!='3' && lbpmTemplateForm.fdType!='4'}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdCommon"/>
				</td>
				<td>
					<c:out value="${lbpmTemplateForm.fdCommonName}" />
					<c:if test="${'true' eq lbpmTemplateForm.fdCommonIsDelete}">
						<span style="color: red">[<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdIsDelete.true" />]</span>
					</c:if>
				</td>
			</tr>
			</c:if>
			<%@ include file="/sys/lbpmservice/support/lbpm_template/lbpmTemplate_sub_view.jsp"%>
			<c:if test="${lbpmTemplateForm.fdType!='4'}">
			<!-- 模板变更记录 -->
			<%@ include file="/sys/lbpmservice/support/lbpm_template_change_history/lbpmTemplateChangeHistory_list.jsp"%>
			</c:if>
		</table>
	</td>
</tr>
<script>
Com_AddEventListener(window, "load", function() {
	var key = "${lbpmTemplate_Key}";
	// 添加标签切换事件
	var table = document.getElementById("WF_TR_ID_"+key);
	while((table != null) && (table.tagName.toLowerCase() != "table")){
		table = table.parentNode;
	}
	if(table != null && window.Doc_AddLabelSwitchEvent){
		Doc_AddLabelSwitchEvent(table, "LBPM_Template_OnLabelSwitch_"+key);
	}
});

function LBPM_Template_OnLabelSwitch_${lbpmTemplate_Key}(tableName, index) {
	var trs = document.getElementById(tableName).rows;
	if(trs[index].id!="WF_TR_ID_${lbpmTemplate_Key}")
		return;
	LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key}();
}

function LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key}(){
	var iframe = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame").contentWindow;
	var iframeDefault = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame_Default").contentWindow;
	if(iframe && iframe.FlowChartObject && iframeDefault && iframeDefault.FlowChartObject){
		var LBPM_Template_SubFormInfoList = null;
		var LBPM_Template_SubPrintInfoList = null;
		var LBPM_Template_RuleTemplate = null;
		var LBPM_Template_Rule_Key = null;
		if(window.XForm_getSubFormViewInfo_${lbpmTemplate_Key}){
			LBPM_Template_SubFormInfoList = XForm_getSubFormViewInfo_${lbpmTemplate_Key}();
		}
		if(window.Print_getSubPrintViewInfo_${lbpmTemplate_Key}){
			LBPM_Template_SubPrintInfoList = Print_getSubPrintViewInfo_${lbpmTemplate_Key}();
		}
		if(window.sysRuleTemplate){
			LBPM_Template_RuleTemplate = window.sysRuleTemplate;
			LBPM_Template_Rule_Key = "${lbpmTemplate_Key}";
		}
		if(typeof xform_subform_fdMode === "undefined"){
			xform_subform_fdMode = "1";
		}
		iframe.FlowChartObject.SubFormInfoList = iframeDefault.FlowChartObject.SubFormInfoList = LBPM_Template_SubFormInfoList;
		iframe.FlowChartObject.SubPrintInfoList = iframeDefault.FlowChartObject.SubPrintInfoList = LBPM_Template_SubPrintInfoList;
		iframe.FlowChartObject.xform_mode = iframeDefault.FlowChartObject.xform_mode = xform_subform_fdMode?xform_subform_fdMode:0;
		iframe.FlowChartObject.SysRuleTemplate = LBPM_Template_RuleTemplate;
		iframe.FlowChartObject.LbpmTemplateKey = LBPM_Template_Rule_Key;
	}else{
		setTimeout(LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key},500);
	}
}
</script>