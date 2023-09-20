<%@page import="com.landray.kmss.tic.core.mapping.constant.Constant"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.tic.core.mapping.service.ITicCoreMappingModuleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<template:replace name="title">${ lfn:message('tic-core:module.tic.manage') }</template:replace>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% response.setHeader("X-UA-Compaticle", "IE=edge"); %>
<script type="text/javascript">
Com_IncludeFile("doclist.js|jquery.js|json2.js");
</script>
<script type="text/javascript">
/**
 * 加上流程驳回，数组加1
 */
	for(var i=0;i<7;i++){
		DocList_Info[i]="TABLE_DocList"+i;//改写DocList_Info数组，使用6个动态表格
	}
	
	function doSubmit(method){
		var tagElements = document.ticCoreMappingMainForm.getElementsByTagName('input');  
		for (var j = 0; j < tagElements.length; j++){ 
		     var value = tagElements[j].value;
		     value = value.replace("<script>", "&lt;script&gt;").replace("<\/script>", "&lt;/script&gt;");
		     tagElements[j].value = value;
		}
		Com_Submit(document.ticCoreMappingMainForm, method);
	}
	
	var dialogObject = {};
	
	function showTypeDialog_callback(itype){
		var rtnJson = dialogObject["rtnJson"];
		var pop_i_type=rtnJson[0]["itype"];
		var pop_i_name=rtnJson[0]["iname"];
		var pop_i_link=rtnJson[0]["idialogLink"];
		if(itype){
			var s_rtn=findRtnJson(rtnJson,itype);
			//alert(s_rtn["idialogLink"]);
			pop_i_type=s_rtn["itype"];
			pop_i_name=s_rtn["iname"];
			pop_i_link=s_rtn["idialogLink"];
		}
		var fieldValues={};
		// 添加验证，验证流程驳回事件sap或soap只能有一行记录
		// var fdIntegTypes = $("input[name^='${param.fdFuncForms}'][name$='.fdIntegrationType'][value='"+ pop_i_type +"']");
		/*if (fdIntegTypes.length > 0) {
			alert(pop_i_name +"<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.unique.fdIntegType"/>");
			return;
		}*/
		fieldValues[dialogObject['fdFuncForms']+"[!{index}].fdIntegrationType"]=pop_i_type;
		fieldValues[dialogObject['fdFuncForms']+"[!{index}].fdIntegrationTypeShow"]=pop_i_name; 
		fieldValues[dialogObject['fdFuncForms']+"[!{index}].fdMapperJsp"]=pop_i_link;
		var n_row=DocList_AddRow(dialogObject['tbId'] ,null,fieldValues);
	}
	
	function findRtnJson(rtnJson ,type){
		for(var i=0,len=rtnJson.length;i<len;i++){
			if(rtnJson[i]["itype"]==type){
                 return rtnJson[i];
				}
			}
		return null;
	}
	
	function editFunction_callback(){
		//重置字段值
		resetField(dialogObject,dialogObject["index"]);
	}
	
	//编辑函数后重置字段值
	function resetField(funcObject,index){
		//alert(funcObject.fdRefName + index);
		var fdFuncForms = dialogObject['fdFuncForms'];
		$('input[name="'+fdFuncForms+'['+index+'].fdJspSegmen"]').val(funcObject.fdJspSegmen);
		$('input[name="'+fdFuncForms+'['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark);
		//$('input[name="dialogObject['fdFuncForms']+['+index+'].fdRfcSettingName"]').val(funcObject.fdRfcSettingName);
		//$('input[name="dialogObject['fdFuncForms']+['+index+'].fdRfcSettingId"]').val(funcObject.fdRfcSettingId);
		$('input[name="'+fdFuncForms+'['+index+'].fdRefName"]').val(funcObject.fdRefName);
		$('input[name="'+fdFuncForms+'['+index+'].fdRefId"]').val(funcObject.fdRefId);
		$('input[name="'+fdFuncForms+'['+index+'].fdRfcParamXml"]').val(funcObject.fdRfcParamXml);
		$('input[name="'+fdFuncForms+'['+index+'].fdExtendFormsView"]').val(funcObject.fdExtendFormsView);
	}
</script>
	<c:if test="${param.cateType == '0'}">
	<template:replace name="path">
		<ui:combin ref="menu.path.tic.simplecategory">
			<ui:varParams 
				id="simplecategoryId"
				moduleTitle="${ lfn:message('tic-core:module.tic.core') }" 
				cateTitle="${ lfn:message('tic-core-mapping:tree.form.flow.mapping') }" 
				href="javascript:parent.ticLoadList('/tic/core/mapping/tic_core_mapping_main/ticCoreMappingMain.do?method=add&templateId=!{value}&name=!{text}&templateName=${param.templateName }&mainModelName=${param.mainModelName }&settingId=${param.settingId }&cateType=0');" 
				modelName="${param.templateName}" 
				categoryId="${param.templateId }" />
		</ui:combin>
	</template:replace>
	</c:if>
	<template:replace name="content">
	<p class="txttitle">${param.name }</p>
<html:form action="/tic/core/mapping/tic_core_mapping_main/ticCoreMappingMain.do">
<%-- 显示列表按钮行 --%>
<c:if test="${param.cateType == '0'}">
<div class="lui_list_operation">
	<table width="100%">
		<tr>
			<td align="right">
				<ui:toolbar>
					<ui:button text="${lfn:message('tic-core-mapping:ticCoreMappingMain.lang.lookFlowTemplate')}" order="4" onclick="Com_OpenWindow('ticCoreMappingMain.do?method=redirectToTemplate&fdModelName=${param.templateName}&fdModelId=${param.templateId}','_blank');"></ui:button>
				<c:if test="${ticCoreMappingMainForm.method_GET=='edit'}">
					<ui:button text="${lfn:message('button.update')}" order="4" onclick="doSubmit('update');"></ui:button>
					<c:set var="fdFormFileName" value="${param.fdFormFileName}" /><!-- add和edit得到该参数不一样，所以设置一个页面变量统一 -->
				</c:if>
				<c:if test="${ticCoreMappingMainForm.method_GET=='add'}">
					       <c:set var="fdFormFileName" value="${fdFormFileName}" />
					<ui:button text="${lfn:message('button.save')}" order="4" onclick="doSubmit('save');"></ui:button>
				</c:if>
				</ui:toolbar>						
			</td>
		</tr>
	</table>
</div>
</c:if>
<c:if test="${param.cateType != '0'}">
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="tic-core-mapping" key="ticCoreMappingMain.lang.lookFlowTemplate"/>" onclick="Com_OpenWindow('ticCoreMappingMain.do?method=redirectToTemplate&fdModelName=${param.templateName}&fdModelId=${param.templateId}','_blank');">
	<c:if test="${ticCoreMappingMainForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="doSubmit('update');">
				<c:set var="fdFormFileName" value="${param.fdFormFileName}" /><!-- add和edit得到该参数不一样，所以设置一个页面变量统一 -->
	</c:if>
	<c:if test="${ticCoreMappingMainForm.method_GET=='add'}">
		       <c:set var="fdFormFileName" value="${fdFormFileName}" />
		<input type=button value="<bean:message key="button.save"/>"
			onclick="doSubmit('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
</c:if>
	<center>
		<table id="td_normal" <c:if test="${param.cateType == '0'}">width="100%"</c:if><c:if test="${param.cateType != '0'}">width="90%"</c:if>>
			<!-- 函数信息 -->
			<tr>
				<td align="center">
					<!-- 表单事件 --> 
					<%@ include
						file="../tic_core_mapping_func/ticCoreMappingFuncFormEvent_view.jsp"%>
					<!-- 机器人节点 --> 
					<%@ include
						file="../tic_core_mapping_func/ticCoreMappingFuncRobot_view.jsp"%>
					<%-- 
					<%
						//检查是否有模块注册SAP			
						String settingId =request.getParameter("settingId");
						ITicCoreMappingModuleService eSettingService=(ITicCoreMappingModuleService)SpringBeanUtil.getBean("ticCoreMappingModuleService");
						boolean check=eSettingService.checkModuleContainType(settingId, Constant.FD_TYPE_SAP);
						if(check)	{	
					%>
					<c:import url="/tic/sap/mapping/plugins/controls/ticSapMappingFuncFormControl_view_bak.jsp"
									charEncoding="UTF-8">
						<c:param name="fdFuncForms" value="fdFormControlFunctionListForms" />
						<c:param name="fdOrder" value="5" />
						<c:param name="fdFormFileName" value="${fdFormFileName}" />
					</c:import> 
						<%@ include file="../../../sap/mapping/plugins/controls/ticSapMappingFuncFormControl_view.jsp"	>
					<%
						}
					%>		
					--%>	
					<%-- 流程驳回 --%>
					<c:import url="../tic_core_mapping_func/ticCoreMappingFuncFlowReject_view.jsp"
									charEncoding="UTF-8">
						<c:param name="fdFuncForms" value="fdFlowRejectListForms" />
						<c:param name="fdOrder" value="6" />
						<c:param name="fdFormFileName" value="${fdFormFileName}" />
					</c:import> 
					<%-- include
						file="../tic_core_mapping_func/ticCoreMappingFuncFlowReject_view.jsp"--%>	
				</td>
			</tr>
		</table>
	</center>
<html:hidden property="fdId" />
<!-- 统一从url传递参数中取，而不是从form中读，因都是一致的 -->
<html:hidden property="fdTemplateId"  value="${param.templateId }"/>
<html:hidden property="fdTemplateName"  value="${param.templateName }"/>
<html:hidden property="fdMainModelName"  value="${param.mainModelName}"/>
<!-- 应用注册ID -->
<html:hidden property="settingId"  value="${param.settingId}"/>

<html:hidden property="method_GET" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
</template:replace>
</template:include>
