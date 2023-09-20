<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm,com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<% SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig(); 

   String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<html:form action="/sys/organization/sys_org_person/sysOrgPerson.do">
	<div id="optBarDiv">
		<logic:equal name="sysOrgPersonForm" property="method_GET" value="edit">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.sysOrgPersonForm, 'update');">
		</logic:equal>
		<logic:equal name="sysOrgPersonForm" property="method_GET" value="add">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.sysOrgPersonForm, 'save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysOrgPersonForm, 'saveadd');">
		</logic:equal>
		<c:if test="${'false' ne param.hideClose}">
		<input type="button" value="<bean:message key="button.close"/>"
			onClick="Com_CloseWindow();">
		</c:if>
	</div>
	<p class="txttitle"><bean:message bundle="sys-organization"
		key="sysOrgElement.person" /><bean:message key="button.edit" /></p>
	<center>
	<table class="tb_normal" width=95%>
		<% if(com.landray.kmss.sys.organization.util.SysOrgUtil.isAnonymousOrEveryOne((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))) { %>
			<html:hidden property="fdNo" />
			<html:hidden property="fdOrder" />
			<html:hidden property="fdKeyword" />
			<html:hidden property="fdMobileNo" />
			<html:hidden property="fdRtxNo" />
			<html:hidden property="fdWechatNo" />
			<html:hidden property="fdCardNo" />
			<html:hidden property="fdEmail" />
			<html:hidden property="fdWorkPhone" />
			<html:hidden property="fdDefaultLang" />
			<tr>
				<td width=15% class="td_normal_title"><bean:message
					bundle="sys-organization" key="sysOrgPerson.fdName" /></td>
				<td width=35%><xform:text property="fdName" style="width:90%"></xform:text></td>
				<td width=15% class="td_normal_title"><bean:message
					bundle="sys-organization" key="sysOrgPerson.fdLoginName" /></td>
				<td width=35%><html:text style="width:90%"
					property="fdLoginName" readonly="true" /></td>
			</tr>
			<tr>
				<td width=15% class="td_normal_title"><bean:message
					bundle="sys-organization" key="sysOrgPerson.fdMemo" /></td>
				<td colspan=3><html:textarea property="fdMemo"
					style="width:100%" /></td>
			</tr>
		<% } else { %>
			<c:if test="${personImportType=='outer'}">
				<c:import url="${personExtendFormUrl}" charEncoding="UTF-8" />
			</c:if>
			<c:if test="${personImportType!='outer'}">
				<c:import
					url="/sys/organization/sys_org_person/sysOrgPerson_commonEdit.jsp"
					charEncoding="UTF-8" />
			</c:if>
		<% } %>
	</table>
	</center>
	<html:hidden property="method_GET" />
	<html:hidden property="fdId" />
	<c:if test="${'true' eq param.outToIn}">
	<html:hidden property="fdIsExternal" value="false"/>
	<html:hidden property="outToIn" value="true"/>
	</c:if>
</html:form>
<script>
	Com_IncludeFile("dialog.js");
</script>
<!-- 引入通用的手机号校验规则 -->
<c:import url="/sys/organization/sys_org_person/common_mobileNo_check.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="sysOrgPersonForm" />
</c:import>
<script language="JavaScript">
	var _validation = $KMSSValidation(document.forms['sysOrgPersonForm']);
	_validation.addValidator('office','请输入有效的办公电话',function(v, e, o) {
		if(v==""){
			return true;
		}
		if(/^0\d{2,3}-?\d{7,8}$/.test(v)) {
		     return true; // 校验通过
		 }
		 return false;
	});
	

	var isLoginSpecialChar = <%=isLoginSpecialChar%>;
	
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
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>