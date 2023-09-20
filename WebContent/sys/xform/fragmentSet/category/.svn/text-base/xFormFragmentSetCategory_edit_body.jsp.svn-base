<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>	
<%
	String fdModelName = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("fdModelName"));
	Set propertyNameSet =  SysDataDict.getInstance().getModel(fdModelName).getPropertyMap().keySet();
%>
<c:set var="sysSimpleCategoryMain" value="${requestScope[param.formName]}" />
		<c:set var="selectEmpty" value="true" />
		<kmss:auth
			requestURL="${param.requestURL}"
			requestMethod="Get">
			<c:set var="selectEmpty" value="false" />
		</kmss:auth>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdParentName" />
			</td>
			<td colspan="3"><html:hidden property="fdParentId" /> 
				<input type="text" value="${sysSimpleCategoryMain.fdParentName}" name="fdParentName" readonly="readonly" style="width:90%" class="inputsgl" unselectable="on">
				<a href="#"
				onclick="Dialog_SimpleCategory_Bak('${JsParam.fdModelName}','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}','${JsParam.titleKey}');Cate_getParentMaintainer();">
			<bean:message key="dialog.selectOther" /> </a></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
            	<bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdName" />
			</td>
			<td colspan="3"><html:text property="fdName" style="width:90%" /><span
				class="txtstrong">*</span></td>
		</tr>
		<% if(propertyNameSet.contains("fdDesc")){ %>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-simplecategory" key="sysSimpleCategory.fdDesc" /></td>
			<td colspan="3"><html:textarea property="fdDesc" style="width:90%" /></td>
		</tr>
		<%} %>

		
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdOrder" /></td>
			<td colspan="3"><xform:text property="fdOrder" validators="digits min(0)"  style="width:90%;"/></td>
		</tr>
		<!-- 可维护者 -->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempEditorName" /></td>
			<td colspan="3"><html:hidden  property="authEditorIds"/><html:textarea property="authEditorNames" style="width:90%" rows="4" readonly="true" styleClass="inputmul"/>
			<a href="#" onclick="Dialog_Address(true, 'authEditorIds', 'authEditorNames', ';', null);">
				<bean:message key="dialog.selectOrg"/>
			</a>
			<div class="description_txt">
				<bean:message	bundle="sys-xform-fragmentSet" key="sysFormFragmentSetCategory.description.cate.tempEditor" />
			</div>
			</td>
		</tr>
		<!-- 可使用者 -->
		<%-- <tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempReaderName" /></td>
			<td colspan="3">
				<label>
					<input type="checkbox" name="authNotReaderFlag" value="${sysSimpleCategoryMain.authNotReaderFlag}" onclick="Cate_CheckNotReaderFlag(this);" 
					<c:if test="${sysSimpleCategoryMain.authNotReaderFlag eq 'true'}">checked</c:if>>
					<bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
				</label>
			<div id="Cate_AllUserId">
			<html:hidden  property="authReaderIds"/><html:textarea property="authReaderNames" style="width:90%" rows="4" readonly="true" styleClass="inputmul"/>
			<a href="#" onclick="Dialog_Address(true, 'authReaderIds', 'authReaderNames', ';', null);">
				<bean:message key="dialog.selectOrg"/>
			</a>
			</div>
			<div id="Cate_AllUserNote">
				<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSetCategory.description.cate.tempReader.allUse" />
			</div>
			</td>
		</tr> --%>
		
		<c:if test="${sysSimpleCategoryMain.method_GET!='add'}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreator" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docCreatorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreateTime" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docCreateTime"/></td>			
		</tr>
		<c:if test="${sysSimpleCategoryMain.docAlterorName!=''}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.docAlteror" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docAlterorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdAlterTime" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docAlterTime"/></td>
		</tr>
		</c:if>
		</c:if>
<script>
	Com_IncludeFile("jquery.js|dialog.js");
	function Cate_CheckNotReaderFlag(el){
		/* document.getElementById("Cate_AllUserId").style.display=el.checked?"none":""; 
		 document.getElementById("Cate_AllUserNote").style.display=el.checked?"none":"";
		el.value=el.checked; */
	}
	
	function Cate_Win_Onload(){
		/* Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]); */
	}

	Com_AddEventListener(window, "load", Cate_Win_Onload);
	
	function checkParentId(){
		var formObj = document.forms['${JsParam.formName}'];
		if(formObj.fdParentId.value!="" && formObj.fdParentId.value==formObj.fdId.value){
			alert('<bean:message bundle="sys-simplecategory" key="error.illegalSelected" />');
			return false;
		}else
			return true;	
	}
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = checkParentId;

	function Cate_getParentMaintainer(){
		<%String requestURL = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("requestURL"));
		requestURL=requestURL.replaceAll("&amp;", "&");
		requestURL = requestURL.substring(0,requestURL.indexOf("?"));
		if(requestURL.startsWith("/")){
			requestURL = requestURL.substring(1);
		}%>
		var parameters ="parentId="+document.getElementsByName("fdParentId")[0].value;
		var s_url = Com_Parameter.ContextPath+"<%=requestURL%>?method=getParentMaintainer";
		$.ajax({
				url: s_url,
				type: "GET",
				data: parameters,
				dataType:"text",
				async: false,
				success: function(text){
					$(document.getElementById("parentMaintainerId")).text(text);
				}});
	}

</script>
