<%@page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%
SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig(); 
String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do">
<style type="text/css">
			.tb_simple tr td{border:0}
			#typeDiv {padding-left:47px}
			.aaaa {padding-left:61px}
</style>
<div id="optBarDiv">
	<logic:equal name="sysOrgPersonForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>" onclick="Com_Submit(document.sysOrgPersonForm, 'update');">
	</logic:equal>
	<logic:equal name="sysOrgPersonForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>" onclick="Com_Submit(document.sysOrgPersonForm, 'save');">
	</logic:equal>
	<logic:equal name="sysOrgPersonForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.saveadd"/>" onclick="Com_Submit(document.sysOrgPersonForm, 'saveadd');">
	</logic:equal>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElementExternal.person"/><bean:message key="button.edit"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgElement.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" subject="${ lfn:message('sys-organization:sysOrgElement.fdName') }" validators="uniqueName invalidName" style="width:90%"></xform:text>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/>
		</td>
		<td width=35%>
			<%-- 引用通用的编号属性 --%>
		  	<input type="hidden" name="fdOrgType" value="8">
			<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgElementExternal.fdElement"/>
		</td>
		<td width=35%>
			<html:hidden property="fdParentId"/>
			<input class="inputsgl" style="width: 90%" name="fdParentName" readonly="readonly" subject="${ lfn:message('sys-organization:sysOrgElementExternal.parent') }" value="${sysOrgPersonForm.fdParentName}" type="text" validate="required">
			<a href="#" onclick="Dialog_Tree(
					false,
					'fdParentId',
					'fdParentName',
					null,
					'organizationTree&fdIsExternal=true&deptLimit=${cateId}&parent=!{value}&orgType='+(ORG_TYPE_DEPT|ORG_FLAG_BUSINESSALL)+'&sys_page=true&eco_type=outer&useDept=true',
					'<bean:message bundle="sys-organization" key="organization.moduleName"/>',
					null,
					null,
					'<bean:write name="sysOrgPersonForm" property="fdId"/>');">
				<bean:message key="dialog.selectOrg"/>
			</a>
			<span class="txtstrong">*</span>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/>
		</td><td width=35%>
			<xform:text property="fdOrder" style="width:90%"></xform:text>
		</td>
	</tr>
	<c:if test="${sysOrgPersonForm.method_GET=='add'}">
	<tr>
		<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />
		</td>
		<td width=35%>
		  <xform:text property="fdLoginName" validators="uniqueLoginName invalidLoginName normalLoginName" style="width:90%"></xform:text>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdPassword" />
		</td>
		<td width=35%>
			<xform:text style="width:90%" property="fdNewPassword" subject="${ lfn:message('sys-organization:sysOrgPerson.fdPassword') }"></xform:text>
		</td>
	</tr>
	</c:if>
	<c:if test="${sysOrgPersonForm.method_GET=='edit'}">
	<tr>
		<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />
		</td>
		<td width=85% colspan="3">
		  <xform:text property="fdLoginName" validators="uniqueLoginName invalidLoginName normalLoginName" style="width:90%"></xform:text>
		</td>
	</tr>
	</c:if>
	<tr>
		<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" />
		</td>
		<td width=35%>
		  <xform:text property="fdMobileNo" validators="required phone uniqueMobileNo" style="width:90%" htmlElementProperties="placeholder='${ lfn:message('sys-organization:sysOrgPerson.moblieNo.tips') }'"></xform:text>
		  <span class="txtstrong">*</span>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" />
		</td>
		<td width=35%>
			<xform:text property="fdEmail" validators="email" style="width:90%"></xform:text>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdPosts" />
		</td>
		<td width=35%>
			<xform:address mulSelect="true" propertyId="fdPostIds" propertyName="fdPostNames" orgType="ORG_TYPE_POST|ORG_FLAG_BUSINESSALL" isExternal="true" subject="${ lfn:message('sys-organization:sysOrgPerson.fdPosts') }" />
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdIsAvailable" />
		</td>
		<td width="35%">
		    <sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdCanLogin" />
		</td>
		<td width=85% colspan="3">
			<sunbor:enums property="fdCanLogin" value="${sysOrgPersonForm.fdCanLogin}" enumsType="common_yesno" elementType="radio" />
		</td>
	</tr>
	<%-- 扩展属性 --%>
	<c:import url="/sys/organization/sys_org_element_external/sysOrgExternal_common_ext_props_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysOrgPersonForm"/>
	</c:import>
</table>

</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
<html:hidden property="fdParentId"/>
<input type="hidden" name="cateId" value="${cateId}">
<!-- 引入通用的手机号校验规则 -->
<c:import url="/sys/organization/sys_org_person/common_mobileNo_check.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="sysOrgPersonForm" />
</c:import>
</html:form>
<script>
	Com_IncludeFile("dialog.js");
</script>
<script language="JavaScript">
	Com_IncludeFile("data.js");
	var _validation = $KMSSValidation(document.forms['sysOrgPersonForm']);

	var NameValidators = {
			'uniqueName' : {
				error : "<bean:message key='sysOrgOrg.error.fdName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
						var fdId = document.getElementsByName("fdId")[0].value;
						console.log("uniqueName: fdId=" + fdId + ", value=" + value);
						var result = checkNameUnique("sysOrgElementService",value,fdId,"unique");
						if (!result) 
							return false;
						return true;
				      }
			},
 			'invalidName': {
				error : "<bean:message key='sysOrgOrg.error.newNameSameOldName' bundle='sys-organization' />",
				test  : function(value) {
					 	if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
						    return true;
					    }
					 	NameValidators["fdName"]=null;
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid");
						if (!result){ 
							if(window.confirm("<bean:message key='sysOrgOrg.warn.fdName.ConfirmMsg' bundle='sys-organization' />")){
								NameValidators["fdName"]=value;
								return true;
							}else{
							  	return false;
							}
						}
						return true;	
			    }
			}
 	};
	_validation.addValidators(NameValidators);
	
	var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
	<%  if("true".equals(isLoginSpecialChar)){%>
		errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
	<% } %>
	var LoginNameValidators = {
			'uniqueLoginName' : {
				error : "<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = loginNameCheckUnique("sysOrgPersonService",value,fdId,"unique");
						if (!result) 
							return false;
						return true;
				      }
			},
			'invalidLoginName': {
				error : "<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",
				test  : function(value) {
					    if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
						    return true;
					    }
					    LoginNameValidators["lgName"]=null;
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = loginNameCheckUnique("sysOrgPersonService",value,fdId,"invalid");
						if (!result){ 
							if(window.confirm("<bean:message key='sysOrgPerson.newLoginName.ConfirmMsg' bundle='sys-organization' />")){
								LoginNameValidators["lgName"]=value;
								return true;
							}else{
							  	return false;
							}
						}
						return true;	
			    }
			},			
			
			'normalLoginName':{
				error:errorMsg,
				test:function(value){
					var pattern;
					<% if("true".equals(isLoginSpecialChar)){%>
						pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
					<% }else{ %>
						pattern=new RegExp("^[A-Za-z0-9_]+$");
					<% }%>
					
					if(pattern.test(value)){
						return true;
					}else{
						return false;
					}
				}
			}
		};
	_validation.addValidators(LoginNameValidators);
	
	//校验名称是否唯一
	function checkNameUnique(bean, fdName,fdId,checkType) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&checkType="+checkType+"&fdOrgType=1&date="+new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
	
	//校验登录名是否与系统中失效的登录名一致
	function loginNameCheckUnique(bean, loginName,fdId,checkType) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&loginName=" + loginName+"&fdId="+fdId+"&checkType="+checkType+"&date="+new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}

</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>