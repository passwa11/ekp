<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%
	request.setAttribute("currentUser", UserUtil.getKMSSUser(request));
    pageContext.setAttribute("tripartiteEnable",TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN);
%>
<script>
Com_IncludeFile("data.js|dialog.js|jquery.js");
function selectAuthAssign(){
	var dialog = new KMSSDialog(true, true);
	var node = dialog.CreateTree("<bean:message bundle="sys-authorization" key="sysAuthRole.sysRole"/>");
	dialog.winTitle = "<bean:message bundle="sys-authorization" key="sysAuthRole.sysRole"/>";
	node.AppendBeanData("sysAuthModuleTree", "sysAuthRoleDialog&module=!{value}", null, false);
	node.parameter = "sysAuthRoleDialog&module=!{value}";
	dialog.BindingField("fdAuthAssignIds", "fdAuthAssignNames", ";");
	dialog.Show();
}

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
	    data.AddBeanData("sysAuthRoleService&fdId=${sysAuthRoleForm.fdId}&fdType=${sysAuthRoleForm.fdType}&fdTemplate=1&fdName=" + encodeURIComponent(fdName.value));
	    var selectData = data.GetHashMapArray();
	    if (selectData != null && selectData[0] != null) {
	    	if(selectData[0]['isDuplicate'] == "true") {
				alert('<bean:message bundle="sys-authorization" key="sysAuthTemplate.fdName.duplicate" />');
				return false;
			}
		}
	}
	Com_Submit(document.sysAuthRoleForm,method,'forward');
}
//禁止回车提交
document.onkeydown = function(event) {
	var target, code, tag;
		if (!event) {
			event = window.event; //针对ie浏览器
			target = event.srcElement;
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "TEXTAREA") {
					return true;
				} else {
					stopDefault(event);
					return false;
				}
			}
		} else {
			target = event.target; //针对遵循w3c标准的浏览器，如Firefox
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "INPUT") {
					stopDefault(event);
					return false;
				} else {
					return true;
				}
			}
		}
	};
//阻止默认浏览器行为
function stopDefault(e) {    
        //如果提供了事件对象，则这是一个非IE浏览器     
        if(e && e.preventDefault) {    
        　　//阻止默认浏览器动作(W3C)    
        　　e.preventDefault();    
        } else {    
        　　//IE中阻止函数器默认动作的方式     
        　　window.event.returnValue = false;     
        }    
        return false;    
    }
</script>
<html:form action="/sys/authorization/sys_auth_role/sysAuthRole.do">
<div id="optBarDiv">
	<logic:equal name="sysAuthRoleForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="preSubmit('update');">
	</logic:equal>
	<logic:equal name="sysAuthRoleForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="preSubmit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="preSubmit('saveadd');">
	</logic:equal>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<%--可维护者 --%>
<kmss:authShow baseOrgIds="${sysAuthRoleForm.authEditorIds}">
	<c:set var="isOrgEditor" value="1"/>
	<c:set var="isOnlyOrgEditor" value="0"/>
</kmss:authShow>
<c:if test="${empty sysAuthRoleForm.authEditorIds}">
	<kmss:authShow baseOrgIds="${sysAuthRoleForm.fdCreatorId}">
		<c:set var="isOnlyOrgEditor" value="0"/>
	</kmss:authShow>
</c:if>

<kmss:authShow roles="ROLE_SYSAUTHROLE_ADMIN;SYSROLE_ADMIN">
	<c:set var="isOnlyOrgEditor" value="0"/>
</kmss:authShow>

<kmss:authShow authAreaIds="${sysAuthRoleForm.authAreaId}">
	<c:set var="isOnlyOrgEditor" value="0"/>
</kmss:authShow>

<p class="txttitle"><bean:message bundle="sys-authorization" key="sysAuthTemplate.name"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-authorization" key="sysAuthTemplate.fdName"/>
		</td><td width=85% colspan="3">
			<c:choose>
			<c:when test="${isOnlyOrgEditor=='0' || isOrgEditor=='1'}">
				<xform:text property="fdName" subject="${ lfn:message('sys-authorization:sysAuthTemplate.fdName') }" style="width:90%"></xform:text>
			</c:when>
			<c:otherwise>
				<xform:text property="fdName" style="width:90%" showStatus="readOnly" className="inputread"></xform:text>
			</c:otherwise>
			</c:choose>
		</td>
	</tr>  	
 	
	<c:if test="${param.type != '2' && (sysAuthRoleForm.fdType == '1' || sysAuthRoleForm.fdType == '2')}">
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-authorization" key="sysAuthRole.authArea"/>
		</td><td width=85% colspan="3">
			<html:hidden property="authAreaId" />
			<html:hidden property="authAreaName" />
			<bean:write name="sysAuthRoleForm" property="authAreaName"/>
		</td>
	</tr>
	</c:if>
	
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-authorization" key="sysAuthRole.fdAuthAssign"/>
		</td><td colspan="3">
			<html:hidden property="fdAuthAssignIds" />
			<!-- 权限部分 -->
			<c:choose>
			<c:when test="${isOnlyOrgEditor=='0'}">
				<c:import charEncoding="UTF-8" url="/sys/authorization/sys_auth_role/sysAuthAssign_edit.jsp">
					<c:param name="formName" value="sysAuthRoleForm"/>
					<c:param name="authAssignMapName" value="fdAuthAssignMap"/>
					<c:param name="authAssignAllMapName" value="fdAuthAssignAllMap"/>
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import charEncoding="UTF-8" url="/sys/authorization/sys_auth_role/sysAuthAssign_view.jsp">
					<c:param name="formName" value="sysAuthRoleForm"/>
					<c:param name="authAssignMapName" value="fdAuthAssignMap"/>
				</c:import>
			</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<%-- 三员管理下没有角色继承 --%>
    <c:if test="${tripartiteEnable == 'false'}">
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="sys-authorization" key="sysAuthTemplate.authReader"/>
			</td><td colspan="3">
				<c:choose>
				<c:when test="${isOnlyOrgEditor=='0' || isOrgEditor=='1'}">
					<xform:address propertyId="authReaderIds" propertyName="authReaderNames"
						orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%">
					</xform:address>
				</c:when>
				<c:otherwise>
					<xform:address propertyId="authReaderIds" propertyName="authReaderNames"
						orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%" showStatus="readOnly">
					</xform:address>
				</c:otherwise>
				</c:choose>
				<br><span class="com_help">${ lfn:message('sys-authorization:sysAuthTemplate.authReader.msg') } </span>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="sys-authorization" key="sysAuthTemplate.authEditors"/>
			</td><td colspan="3">
				<c:choose>
				<c:when test="${isOnlyOrgEditor=='0' || isOrgEditor=='1'}">
					<xform:address propertyId="authEditorIds" propertyName="authEditorNames"
						orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%">
					</xform:address>
				</c:when>
				<c:otherwise>
					<xform:address propertyId="authEditorIds" propertyName="authEditorNames"
						orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%" showStatus="readOnly">
					</xform:address>
				</c:otherwise>
				</c:choose>
				<br><span class="com_help">${ lfn:message('sys-authorization:sysAuthTemplate.authEditors.msg') } </span>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-authorization" key="sysAuthRole.fdDescription"/>
		</td><td colspan="3">
			<c:choose>
			<c:when test="${isOnlyOrgEditor=='0' || isOrgEditor=='1'}">
				<xform:textarea property="fdDescription" style="width:90%"></xform:textarea>
			</c:when>
			<c:otherwise>
				<xform:textarea property="fdDescription" style="width:90%" showStatus="view"></xform:textarea>
			</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<c:if test="${sysAuthRoleForm.method_GET=='edit'}">
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="sys-authorization" key="sysAuthRole.fdCreator"/>
			</td><td width=35%>
				<html:text property="fdCreatorName" readonly="true" />
			</td>
			<td class="td_normal_title"><bean:message bundle="sys-authorization" key="sysAuthRole.docCreateTime"/>
			</td><td width=35%>
			  <html:text property="docCreateTime"
				readonly="true" style="width:50%;" />
			</td>
		</tr>
		<c:if test="${not empty sysAuthRoleForm.docAlterorName}">
		<tr>
			<!-- 修改人 -->
			<td class="td_normal_title" width=15%><bean:message bundle="sys-authorization" key="sysAuthRole.docAlteror"/></td>
			<td width=35%><bean:write name="sysAuthRoleForm"
				property="docAlterorName" /></td>
			<!-- 修改时间 -->
			<td class="td_normal_title" width=15%><bean:message bundle="sys-authorization" key="sysAuthRole.docAlterTime"/></td>
			<td width=35%><bean:write name="sysAuthRoleForm"
				property="docAlterTime" /></td>
		</tr>
		</c:if>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
<html:hidden property="fdType"/>
<html:hidden property="fdTemplate" value="1"/>
<script>
	$KMSSValidation();
</script>
</html:form>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>