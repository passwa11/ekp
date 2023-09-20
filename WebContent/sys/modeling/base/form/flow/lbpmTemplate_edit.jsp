<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
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

<html:hidden property="${lbpmTemplateFormPrefix}fdType" value="3"/>

<%@ include file="/sys/lbpmservice/support/lbpm_template/lbpmTemplate_sub_edit.jsp"%>

<script>
	if(window.LBPM_Template_Type == null) {
		LBPM_Template_Type = new Array();
	}
	LBPM_Template_Type["${lbpmTemplate_Key}"] = "${lbpmTemplateForm.fdType}";
	function LBPM_Template_Load_FlowChartObject_modelingApp(){
		var iframe = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame").contentWindow;
		if(iframe && iframe.FlowChartObject){
			var LBPM_Template_FormFieldList = null;
			var LBPM_Template_SubFormInfoList = null;
			var LBPM_Template_SubPrintInfoList = null;
			var LBPM_Template_xform_mode = null;
			iframe.FlowChartObject.isModeling = true;
			if(window.XForm_getXFormDesignerObj_modelingApp) {
				LBPM_Template_FormFieldList = XForm_getXFormDesignerObj_modelingApp();
			} else {
				LBPM_Template_FormFieldList = Formula_GetVarInfoByModelName("${lbpmTemplate_MainModelName}");
			}
			if(window.XForm_getSubFormInfo_modelingApp){
				LBPM_Template_SubFormInfoList = XForm_getSubFormInfo_modelingApp();
			}
			if(window.Print_getSubPrintInfo_modelingApp){
				LBPM_Template_SubPrintInfoList = Print_getSubPrintInfo_modelingApp();
			}
			if(window.Form_getModeValue){
				LBPM_Template_xform_mode = Form_getModeValue("modelingApp");
			}
			//获取表单设计器对象和主文档对象
			iframe.FlowChartObject.Designer = null;
			iframe.FlowChartObject.modelName = 
				iframe.FlowChartObject["MODEL_NAME"];
			if (window.XForm_getXFormDesignerInstance_modelingApp){
				var isFormTemplateMode = (LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_OTHER %>"
											|| LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_DEFINE %>"
												|| LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_SUBFORM %>");
				iframe.FlowChartObject.xform_mode = LBPM_Template_xform_mode;
				//点击流程标签时,如果表单未加载就先加载表单
				 if (!XForm_XformIframeIsLoad(window) && isFormTemplateMode ){
					LoadXForm('TD_FormTemplate_${JsParam.fdKey}');
					var frame = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
					Com_AddEventListener(frame, 'load', function(){
						var obj = XForm_getXFormDesignerInstance_modelingApp();
						if (obj){
							iframe.FlowChartObject.FormFieldList = XForm_getXFormDesignerObj_modelingApp();
							iframe.FlowChartObject.Designer = obj["designer"];
							iframe.FlowChartObject.modelName = obj["modelName"];
						}
					});
				 }else{
					 var obj = XForm_getXFormDesignerInstance_modelingApp();
					 if (obj){
						iframe.FlowChartObject.Designer = obj["designer"];
						iframe.FlowChartObject.modelName = obj["modelName"];
					 } 
				 }
			}
			iframe.FlowChartObject.FormFieldList = LBPM_Template_FormFieldList;
			iframe.FlowChartObject.SubFormInfoList = LBPM_Template_SubFormInfoList;
			iframe.FlowChartObject.SubPrintInfoList = LBPM_Template_SubPrintInfoList;
			iframe.FlowChartObject.FdAppModelId = "${JsParam.fdAppModelId}";
			iframe.FlowChartObject.FdAppId = "${JsParam.fdAppId}";
			iframe.FlowChartObject.xform_mode = LBPM_Template_xform_mode?LBPM_Template_xform_mode:0;
            iframe.FlowChartObject.isModeling = true;
		}else{
			setTimeout(LBPM_Template_Load_FlowChartObject_modelingApp,500);
		}
	}
	
	Com_AddEventListener(window , "load" , LBPM_Template_Load_FlowChartObject_modelingApp);

</script>