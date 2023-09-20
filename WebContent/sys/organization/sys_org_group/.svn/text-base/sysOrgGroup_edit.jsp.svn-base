<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/organization/sys_org_group/sysOrgGroup.do">
<div id="optBarDiv">
	<logic:equal name="sysOrgGroupForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysOrgGroupForm, 'update');">
	</logic:equal>
	<logic:equal name="sysOrgGroupForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysOrgGroupForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysOrgGroupForm, 'saveadd');">
	</logic:equal>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElement.group"/><bean:message key="button.edit"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdName"/>
		</td><td width=85% colspan=3>
		    <xform:text property="fdName" validators="uniqueName invalidName" style="width:90%"></xform:text>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdGroupCate"/>
		</td><td width=35%>
			<html:hidden property="fdGroupCateId"/>
			<html:text style="width:90%" property="fdGroupCateName" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Tree(
				false,
				'fdGroupCateId',
				'fdGroupCateName',
				null,
				Tree_GetBeanNameFromService('sysOrgGroupCateService', 'hbmParent', 'fdName:fdId'),
				'<bean:message bundle="sys-organization" key="table.sysOrgGroupCate"/>');">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdNo"/>
		</td><td width=35%>
		   <%-- 引用通用的编号属性 --%>
		   <input type="hidden" name="fdOrgType" value="16">
			<%@ include file="/sys/organization/org_common_fdNo_edit.jsp"%>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdKeyword"/>
		</td><td width=35%>
		   <xform:text property="fdKeyword" style="width:90%"></xform:text>
		
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgDept.fdOrder"/>
		</td><td width=35%>
		    <xform:text property="fdOrder" style="width:90%" validators="min(0)"></xform:text>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
		    <bean:message bundle="sys-organization" key="sysOrgElement.fdOrgEmail"/>
		</td><td width=35% colspan="3">
		    <xform:text property="fdOrgEmail" style="width:90%"></xform:text>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdMembers"/>
		</td><td colspan=3>
			<html:hidden property="fdMemberIds" />
			<html:text style="width:90%" property="fdMemberNames" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Address(true, 'fdMemberIds', 'fdMemberNames', ';', ORG_TYPE_ALL, null, null, null, null, null, null, '${sysOrgGroupForm.fdId}', null);">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
	</tr>
	<c:if test="${sysOrgGroupForm.method_GET=='edit'}">
		<tr>
			<td width=15% class="td_normal_title">
				<bean:message bundle="sys-organization" key="sysOrgGroup.fdIsAvailable"/>
			</td><td colspan=3>
				<sunbor:enums property="fdIsAvailable" enumsType="sys_org_available" elementType="radio" />
			</td>
		</tr>
	</c:if>
	
	<!-- 可使用者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgGroup.authReaders"/></td>
		<td  width=85% colspan="3"><html:hidden property="authReaderIds" /> <html:textarea
			property="authReaderNames" style="width:80%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authReaderIds','authReaderNames', ';',null, null, null, null, null, null, null, null, null, false);"><bean:message
			key="dialog.selectOther" /></a><br>
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				<% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则本组织人员可使用） -->
			        <bean:message  bundle="sys-organization" key="sysOrgGroup.authReaders.external.describe" />
				<% } else { %>
			        <!-- （为空则所有内部人员可使用） -->
			        <bean:message key="sysOrgGroup.authReaders.internal.describe" bundle="sys-organization"/>
				    <% } %>
			<% } else { %>
				    <!-- （为空则所有人可使用） -->
				    <bean:message bundle="sys-organization" key="sysOrgGroup.authReaders.describe"/>
			<% } %>
			
	   </td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgGroup.authEditors"/></td>
		<td width=85% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:80%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null, null, null, null, null, null, null, null, null, false);"><bean:message
			key="dialog.selectOther" /></a><br>
			<bean:message bundle="sys-organization" key="sysOrgGroup.authEditors.describe"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgGroup.fdMemo"/>
		</td><td colspan="3">
			<xform:textarea property="fdMemo" style="width:100%"></xform:textarea>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<script>Com_IncludeFile("dialog.js");</script>
<script language="JavaScript">
	var _validation = 	$KMSSValidation(document.forms['sysOrgGroupForm']);

	var NameValidators = {
			'uniqueName' : {
				error : "<bean:message key='sysOrgGroup.error.fdName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("sysOrgElementService",value,fdId,"unique");
						if (!result) 
							return false;
						return true;
				      }
			},
 			'invalidName': {
				error : "<bean:message key='sysOrgGroup.error.newNameSameOldName' bundle='sys-organization' />",
				test  : function(value) {
						if (NameValidators["fdName"] && (NameValidators["fdName"]==value)){
						    return true;
					    }
					 	NameValidators["fdName"]=null;
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("sysOrgElementService",value,fdId,"invalid");
						if (!result){ 
							if(window.confirm("<bean:message key='sysOrgGroup.warn.fdName.ConfirmMsg' bundle='sys-organization' />")){
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
				+ bean + "&fdName=" + fdName+"&fdId="+fdId+"&checkType="+checkType+"&fdOrgType=16&date="+new Date());
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