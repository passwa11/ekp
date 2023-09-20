<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_org_post/sysOrgPost.do">
<div id="optBarDiv">
	<logic:equal name="sysOrgPostForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysOrgPostForm, 'update');">
	</logic:equal>
	<logic:equal name="sysOrgPostForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysOrgPostForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysOrgPostForm, 'saveadd');">
	</logic:equal>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElement.post"/><bean:message key="button.edit"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdParent"/>
			<html:hidden property="fdOldParentId" value="${sysOrgPostForm.fdParentId}"/>
		</td><td width=35%>
			<html:hidden property="fdParentId"/>
			<html:text style="width:90%" property="fdParentName" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Address(false, 'fdParentId', 'fdParentName', null, ORG_TYPE_ORGORDEPT|ORG_FLAG_BUSINESSALL, changeParent, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" validators="uniqueName invalidName" style="width:90%"></xform:text>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdNo"/>
		</td><td width=35%>
			<%-- 引用通用的编号属性 --%>
			<input type="hidden" name="fdOrgType" value="4">
			<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdThisLeader"/>
		</td><td width=35%>
			<html:hidden property="fdThisLeaderId"/>
			<html:text style="width:90%" property="fdThisLeaderName" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Address(false, 'fdThisLeaderId', 'fdThisLeaderName', null, ORG_TYPE_POSTORPERSON, null, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdKeyword"/>
		</td><td width=35%>
		<xform:text property="fdKeyword" style="width:90%"></xform:text>
		
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdOrder"/>
		</td><td width=35%>
		    <xform:text property="fdOrder" style="width:90%"></xform:text>
		</td>
	</tr>
	<tr>
		<c:set var="_colspan" value="3" />
		<c:if test="${sysOrgPostForm.method_GET=='edit'}">
			<c:set var="_colspan" value="1" />
			<td width=15% class="td_normal_title">
				<bean:message bundle="sys-organization" key="sysOrgPost.fdIsAvailable"/>
			</td>
			<td width="35%">
				<sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
			</td>
		</c:if>
		<td width=15% class="td_normal_title">
		    <bean:message bundle="sys-organization" key="sysOrgPost.fdIsBusiness"/>	
		</td>
		<td width=35% colspan="${_colspan}">
		    <sunbor:enums property="fdIsBusiness" enumsType="common_yesno" elementType="radio" />	
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdPersons"/>
		</td><td colspan=3>
			<html:hidden property="fdPersonIds" />
			<html:hidden property="fdOldPersonIds" value="${sysOrgPostForm.fdPersonIds}"/>
			<html:text style="width:90%" property="fdPersonNames" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Address(true, 'fdPersonIds', 'fdPersonNames', ';', ORG_TYPE_PERSON|ORG_FLAG_BUSINESSALL, null, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPost.fdMemo"/>
		</td><td colspan="3">
			<xform:textarea property="fdMemo" style="width:100%"></xform:textarea>
		</td>
	</tr>
	<%-- 引入动态属性 --%>
	<c:import url="/sys/property/custom_field/custom_fieldEdit.jsp" charEncoding="UTF-8" />
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<script>Com_IncludeFile("dialog.js");</script>
<script language="JavaScript">
	var _validation = $KMSSValidation(document.forms['sysOrgPostForm']);

	var NameValidators = {
			'uniqueName' : {
				error : "<bean:message key='sysOrgPost.error.fdName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("sysOrgElementService",value,fdId,"unique");
						if (!result) 
							return false;
						return true;
				      }
			},
 			'invalidName': {
				error : "<bean:message key='sysOrgPost.error.newNameSameOldName' bundle='sys-organization' />",
				test  : function(value) {
						if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
						    return true;
					    }
					 	NameValidators["fdName"]=null;
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid");
						if (!result){ 
							if(window.confirm("<bean:message key='sysOrgPost.warn.fdName.ConfirmMsg' bundle='sys-organization' />")){
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
	
	//校验名称是否唯一
	function checkNameUnique(bean, fdName,fdId,checkType) {
		fdName = encodeURIComponent(fdName);
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&checkType="+checkType+"&fdOrgType=4&date="+new Date());
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

	function changeParent(rtnVal) {
		<c:if test="${'true' eq isRelation}">
		if(rtnVal != null && rtnVal != undefined 
				&& rtnVal.data && rtnVal.data.length > 0) {
			$("input[name=fdName]").val(rtnVal.data[0].name + "_");
		}
		</c:if>
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