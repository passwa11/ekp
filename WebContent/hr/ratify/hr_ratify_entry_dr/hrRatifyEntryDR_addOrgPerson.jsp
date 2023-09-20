<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil,com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<script>
seajs.use(['theme!form']);
Com_IncludeFile("doclist.js|xform.js|dialog.js|calendar.js|data.js|jquery.js");
Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');
</script>

<html:form action="/hr/ratify/hr_ratify_entry_dr/hrRatifyEntryDR.do">
<div style="padding: 10px 0px">
<p class="txttitle"><bean:message bundle="hr-ratify" key="hrRatifyEntry.addOrgPerson"/></p>
</div>
<center>
<table class="tb_normal" width=95%>
	<html:hidden property="fdId"/>
	<html:hidden property="fdEntryId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdLoginName"/>
		</td>
		<td width="35%">
			<xform:text property="fdLoginName" showStatus="edit" required="true" validators="uniqueLoginName invalidLoginName normalLoginName" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdLoginName') }" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdPassword"/>
		</td>
		<td width="35%">
			<xform:text property="fdPassword" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdPassword') }" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryDept"/>
		</td>
		<td width="35%">
			<xform:address 
	            isLoadDataDict="false"
	            showStatus="edit"
				required="true"
				subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryDept')}"
				propertyId="fdEntryDeptId" propertyName="fdEntryDeptName"
				orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT' className="input" style="width:95%">
	   		</xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryPosts"/>
		</td>
		<td width="35%">
			<xform:address 
	            isLoadDataDict="false"
	            showStatus="edit"
				required="true"
				subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryPosts')}"
				propertyId="fdEntryPostIds" propertyName="fdEntryPostNames"
				orgType='ORG_TYPE_POST' className="input" mulSelect="false" style="width:95%">
	   		</xform:address>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdStaffNo"/>
		</td>
		<td>
			<% if (sysOrganizationConfig.isNoRequired()) { %>
				<xform:text property="fdNo" validators="required uniqueNo" showStatus="edit"></xform:text>
				<span class="txtstrong">*</span>
			<% } else { %>
				<xform:text property="fdNo" showStatus="edit"></xform:text>
			<% } %>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryDate"/>
		</td>
		<td width="35%">
			<xform:datetime property="fdEntryDate"
				required="true"
                showStatus="edit" 
                dateTimeType="date" 
                subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryDate')}" />
		</td>
	</tr>
</table>
<div style="padding-top:17px;padding-bottom:20px">
	   <ui:button text="${ lfn:message('button.submit') }"  onclick="submitForm();">
	   </ui:button>
	   <ui:button text="${ lfn:message('button.close') }" order="5" styleClass="lui_toolbar_btn_gray"  onclick="$dialog.hide('cancel');">
	    </ui:button>
 </div>
</center>
<html:hidden property="method_GET"/>
</html:form>
	<script>
		var _validation = $KMSSValidation(document.forms['hrRatifyEntryDRForm']);
		var isLoginSpecialChar = <%=isLoginSpecialChar%>;
		var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
		<%  if("true".equals(isLoginSpecialChar)){%>
			errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
		<% } %>
	   	var LoginNameValidators = {
	   			'uniqueLoginName' : {
	   				error : "<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",
	   				test : function (value) {
	   						var fdId = document.getElementsByName("fdEntryId")[0].value;
	   						var result = loginNameCheckUnique("hrRatifyEntryService",value,fdId,"unique");
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
	   						var fdId = document.getElementsByName("fdEntryId")[0].value;
	   						var result = loginNameCheckUnique("hrRatifyEntryService",value,fdId,"invalid");
	   						if (!result){
	   							return false;
	   						}else{
	   							return true;	
	   						}
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
	   					console.log("normalLoginName "+pattern.test(value))
	   					if(pattern.test(value)){
	   						return true;
	   					}else{
	   						return false;
	   					}
	   				}
	   			},
	   			'uniqueNo' : {
					error : "<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",
					test : function(v, e, o) {
	    				if (v.length < 1)
	    					return true;
	    				var fdId = document.getElementsByName("fdId")[0].value,
	    					fdNo = document.getElementsByName("fdNo")[0].value;
	    				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdOrgType=8"
	    						+ "&fdId=" + fdId + "&fdNo=" + fdNo + "&_=" + new Date());
	    				return _CheckUnique(url);
	    			}
				}
	   		};
	   	_validation.addValidators(LoginNameValidators);
	  	//校验登录名是否与系统中失效的登录名一致
		function _CheckUnique(url) {
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
	   	function submitForm(){
	   		Com_Submit(document.hrRatifyEntryDRForm, 'saveOrgPerson');	
	   	}
	</script>
	</template:replace>
</template:include>
