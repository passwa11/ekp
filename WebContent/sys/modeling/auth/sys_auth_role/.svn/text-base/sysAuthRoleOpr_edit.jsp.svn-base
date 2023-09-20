<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%
	request.setAttribute("currentUser", UserUtil.getKMSSUser(request));
%>
<script>
Com_IncludeFile("data.js|dialog.js|jquery.js");

function preSubmit(method){
	// 校验角色唯一
	var fdAppAlias = document.getElementsByName("fdAppAlias")[0];
	var fdAlias = document.getElementsByName("fdAlias")[0];
	var fdModelingAppModelId = document.getElementsByName("fdModelingAppModelId")[0];
	fdAlias.value=fdAppAlias.value+"_"+fdModelingAppModelId.value;
	var fdName = document.getElementsByName("fdName")[0];
	if(fdName != null) {
		var data = new KMSSData();
	    data.AddBeanData("sysModelingAuthRoleService&fdId=${sysModelingAuthRoleForm.fdId}&fdType=${sysModelingAuthRoleForm.fdType}&fdName=" + encodeURIComponent(fdName.value));
	    var selectData = data.GetHashMapArray();
	    if (selectData != null && selectData[0] != null) {
	    	if(selectData[0]['isDuplicate'] == "true") {
				alert('<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdName.duplicate" />');
				return false;
			}
		}
	}
	Com_Submit(document.sysModelingAuthRoleForm,method);
}
</script>
<html:form action="/sys/modeling/auth/sys_auth_role/sysModelingAuthRole.do">
<div id="optBarDiv">
	<logic:equal name="sysModelingAuthRoleForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="preSubmit('update');">
	</logic:equal>
	<logic:equal name="sysModelingAuthRoleForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="preSubmit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="preSubmit('saveadd');">
	</logic:equal>
		<input type=button value="发布"
			onclick="Com_Submit(document.sysModelingAuthRoleForm,'publish');">
	
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-modeling-auth" key="table.sysModelingAuthRole"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdName"/>
		</td><td width=35%>
				<xform:text property="fdName" style="width:90%"></xform:text>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdAlias"/>
		</td><td width=35%>
				<xform:text property="fdAppAlias" style="width:90%"></xform:text>
				<html:hidden property="fdAlias"/>
		</td>
	</tr>  	
 	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdModelingAppModel" />
		</td><td width=35%>
				<xform:text property="fdModelingAppModelId" style="width:90%"></xform:text>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdModulePath" />
		</td><td width=35%>
				<xform:text property="fdModulePath" style="width:90%"></xform:text>
		</td>
	</tr>  	

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdOprCode" />
		</td><td width=35%>
				<xform:text property="fdOprCode" style="width:90%"></xform:text>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdOprName"/>
		</td><td width=35%>
				<xform:text property="fdOprName" style="width:90%"></xform:text>
		</td>
	</tr>  	
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdUrl" />
		</td><td width=85% colspan="3">
				<xform:textarea property="fdUrl" style="width:90%"></xform:textarea>
		</td>
	</tr>  	

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdOprJs" />
		</td><td width=85% colspan="3">
				<xform:textarea property="fdOprJs" style="width:90%"></xform:textarea>
		</td>
	</tr>  	

	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdDescription"/>
		</td><td colspan="3">
				<xform:textarea property="fdDescription" style="width:90%"></xform:textarea>
		</td>
	</tr>

	<c:if test="${sysModelingAuthRoleForm.method_GET=='edit'}">
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdCreator"/>
			</td><td width=35%>
				<html:text property="fdCreatorName" readonly="true" />
			</td>
			<td class="td_normal_title">${lfn:message('sys-modeling-auth:sysModelingAuth.CreationTime')}
			</td><td width=35%>
			  <html:text property="docCreateTime"
				readonly="true" style="width:50%;" />
			</td>
		</tr>
		<c:if test="${not empty sysModelingAuthRoleForm.docAlterorName}">
		<tr>
			<!-- 修改人 -->
			<td class="td_normal_title" width=15%>${lfn:message('sys-modeling-auth:sysModelingAuth.Reviser')}</td>
			<td width=35%><bean:write name="sysModelingAuthRoleForm"
				property="docAlterorName" /></td>
			<!-- 修改时间 -->
			<td class="td_normal_title" width=15%>${lfn:message('sys-modeling-auth:sysModelingAuth.ModificationTime')}</td>
			<td width=35%><bean:write name="sysModelingAuthRoleForm"
				property="docAlterTime" /></td>
		</tr>
		</c:if>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
<html:hidden property="fdType" value="1"/>
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
