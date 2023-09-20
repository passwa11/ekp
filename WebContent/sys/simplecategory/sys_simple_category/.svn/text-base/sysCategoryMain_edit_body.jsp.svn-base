<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>	
<%
	String fdModelName = request.getParameter("fdModelName");
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
			<c:if test="${empty param.fdParentNameStr}"><bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdParentName" /></c:if>
			<c:if test="${not empty param.fdParentNameStr}">${HtmlParam.fdParentNameStr}</c:if>
			</td>
			<td colspan="3"><html:hidden property="fdParentId" /> 
				<input type="text" value="${sysSimpleCategoryMain.fdParentName}" name="fdParentName" readonly="readonly" style="width:90%" class="inputsgl" unselectable="on">
				<a href="javascript:;" onclick="addCategory();">
			<bean:message key="dialog.selectOther" /> </a></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
			<c:choose>
				<c:when test="${'saveadd' eq param.method}">
					<bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdName" />
				</c:when>
				<c:otherwise>
					<c:if test="${empty param.fdNameStr}"><bean:message bundle="sys-simplecategory" key="sysSimpleCategory.fdName" /></c:if>
		            <c:if test="${not empty param.fdNameStr}">${HtmlParam.fdNameStr}</c:if>
				</c:otherwise>
			</c:choose>
			</td>
			<td colspan="3">
			<%--
			<html:text property="fdName" style="width:90%" /><span
				class="txtstrong">*</span>
			 --%>
				<xform:text property="fdName" style="width:90%" required="true"></xform:text>
				</td>
		</tr>
		<% if(propertyNameSet.contains("fdDesc")){ %>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-simplecategory" key="sysSimpleCategory.fdDesc" /></td>
			<td colspan="3">
				<xform:textarea property="fdDesc" style="width:90%" subject="${lfn:message('sys-simplecategory:sysSimpleCategory.fdDesc') }"/>
			</td>
		</tr>
		<%} %>

		<% if(propertyNameSet.contains("docProperties")){ %>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="sys-category" key="menu.sysCategory.property" /></td>
			<td colspan="3"><html:hidden property="docPropertyIds" /> 						
				  <html:text
						property="docPropertyNames"
						readonly="true"
						styleClass="inputsgl"
						style="width:90%" /> <a
						href="#"
						onclick="Dialog_property(true, 'docPropertyIds','docPropertyNames', ';',ORG_TYPE_PERSON);"> 
						<bean:message key="dialog.selectOther" /> </a></td> 
		</tr>
		<%} %>

		<% if(propertyNameSet.contains("authArea") && ISysAuthConstant.IS_AREA_ENABLED){ %>
		<%-- 所属场所 --%>
		<td class="td_normal_title" width="15%">
			<bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
		</td>
		<td colspan="3">
		    <html:hidden property="authAreaId" /> 
		    <c:out value="${sysSimpleCategoryMain.authAreaName}" />
		</td>
		<%} %>
		
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdOrder" /></td>
			<td colspan="3"><xform:text property="fdOrder" validators="digits min(0)" /></td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%><bean:message bundle="sys-simplecategory"
				key="sysSimpleCategory.parentMaintainer" /></td>
			<td colspan="3" id="parentMaintainerId">${parentMaintainer}</td>
		</tr>

		<tr>
			<td class="td_normal_title" width=15%>
				<c:if test="${empty param.tempEditorName}">
					<bean:message key="model.tempEditorName" />
				</c:if>
				<c:if test="${not empty param.tempEditorName}">
					${param.tempEditorName}
				</c:if>
			</td>
			<td colspan="3"><html:hidden  property="authEditorIds"/>
			<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:97%;height:90px;" ></xform:address>
			<div class="description_txt">
			<c:if test="${empty param.tempEditorNote}">
				<bean:message	bundle="sys-simplecategory" key="description.main.tempEditor" />
			</c:if>
			<c:if test="${not empty param.tempEditorNote}">
				${param.tempEditorNote}
			</c:if>
			</div>
			</td>
		</tr>

		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempReaderName" /></td>
			<td colspan="3">
			<input type="checkbox" name="authNotReaderFlag" value="${sysSimpleCategoryMain.authNotReaderFlag}" onclick="Cate_CheckNotReaderFlag(this);" 
			<c:if test="${sysSimpleCategoryMain.authNotReaderFlag eq 'true'}">checked</c:if>>
			<bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
			<div id="Cate_AllUserId">
			<html:hidden  property="authReaderIds"/>
			<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="Cate_AllUserNote">
			<c:if test="${empty param.tempReaderNote}">
			 <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			 	
			        <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可使用） -->
				       <bean:message bundle="sys-simplecategory" key="description.main.tempReader.organizationUse" arg0="${ecoName}" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可使用） -->
				        <bean:message bundle="sys-simplecategory" key="description.main.tempReader.allUse" />
				    <% } %>
			 <% } else { %>
			
			     <bean:message bundle="sys-simplecategory" key="description.main.tempReader.nonOrganizationAllUse" />
			<% } %>
			
				
			</c:if>
			<c:if test="${not empty param.tempReaderNote}">
				${param.tempReaderNote}
			</c:if>
			</div>
			</td>
		</tr>
		<tr style="display:none">
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-simplecategory" key="sysSimpleCategory.fdIsinheritMaintainer" /></td>
			<td width=35%>
			<sunbor:enums property="fdIsinheritMaintainer" enumsType="common_yesno" elementType="radio" />
			</td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-simplecategory" key="sysSimpleCategory.fdIsinheritUser" /></td>
			<td width=35%>
				<sunbor:enums property="fdIsinheritUser" enumsType="common_yesno" elementType="radio" />
			</td>			
		</tr>
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
		document.getElementById("Cate_AllUserId").style.display=el.checked?"none":"";
		document.getElementById("Cate_AllUserNote").style.display=el.checked?"none":"";
		el.value=el.checked;
	}
	
	function Cate_Win_Onload(){
		Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
	}

	Com_AddEventListener(window, "load", Cate_Win_Onload);
	
	function checkParentId(){
		//debugger;
		var formObj = document.forms['${JsParam.formName}'];
		if(formObj.fdParentId.value!="" && formObj.fdParentId.value==formObj.fdId.value){
			alert('<bean:message bundle="sys-simplecategory" key="error.illegalSelected" />');
			return false;
		}else
			return true;	
	}
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = checkParentId;

	function Cate_getParentMaintainer(){
		<%String requestURL = request.getParameter("requestURL");
		requestURL = StringEscapeUtils.escapeHtml(requestURL);
		requestURL = requestURL.replaceAll("&amp;", "&");
				
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
				}
		});
	}
	
	function addCategory(){
		if (typeof (seajs) != 'undefined' && typeof (top['seajs']) != 'undefined') {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.simpleCategory('${param.fdModelName}', 'fdParentId',
						'fdParentName', false, Cate_getParentMaintainer, null,
						true, null, false, '${JsParam.fdId}');
			})
		} else {
			Dialog_SimpleCategory_Bak('${JsParam.fdModelName}', 'fdParentId',
					'fdParentName', false, null, '01', Cate_getParentMaintainer, false,
					'${JsParam.fdId}', '${JsParam.titleKey}');
		}
	}
</script>
