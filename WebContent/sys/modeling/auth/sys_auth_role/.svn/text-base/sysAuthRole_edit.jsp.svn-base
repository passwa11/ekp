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
	var authIds=document.getElementsByName("fdAuthAssignIds")[0];
	var authIdArr=document.getElementsByName("fdAuthAssignId");
	var authStr="";
	if(authIdArr.length>0){
		for (var i=0;i<authIdArr.length;i++) {
			if(authIdArr[i].checked){
				authStr+= ";" + authIdArr[i].value;
			}
		}
		if(authStr!="")
			authIds.value=authStr.substring(1);
		else
			authIds.value="";
	}
	// 校验角色唯一
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
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdModelingAppModel" />
		</td><td width=35%>

		</td>
	</tr>  	
 	
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdOrgElements"/>
		</td><td colspan="3">
				<xform:address propertyId="fdOrgElementIds" propertyName="fdOrgElementNames"
					orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%">
				</xform:address>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdAuthAssign"/>
		</td><td colspan="3">
			<html:hidden property="fdAuthAssignIds" />
			<!-- 权限部分 -->
				<c:import charEncoding="UTF-8" url="/sys/modeling/auth/sys_auth_role/sysAuthAssign_edit.jsp">
					<c:param name="formName" value="sysModelingAuthRoleForm"/>
					<c:param name="authAssignMapName" value="fdAuthAssignMap"/>
					<c:param name="authAssignAllMapName" value="fdAuthAssignAllMap"/>
				</c:import>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-modeling-auth" key="sysModelingAuthRole.fdInhRoles"/>
		</td><td colspan="3">
				<xform:dialog propertyId="fdInhRoleIds" propertyName="fdInhRoleNames" textarea="true" style="width: 90%">
					Dialog_List(true, 'fdInhRoleIds', 'fdInhRoleNames', ';' , 'sysModelingAuthRoleDialog&fdId=${sysModelingAuthRoleForm.fdId}&type=${sysModelingAuthRoleForm.fdType}');
				</xform:dialog>
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
<html:hidden property="fdType" value="2"/>
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
