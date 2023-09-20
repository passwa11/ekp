<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
pageContext.setAttribute("tripartiteEnable",TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN);
%>
<script>
function confirm_delete(msg) {
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<div id="optBarDiv">
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysAuthRole.do?method=edit&fdId=<bean:write name="sysAuthRoleForm" property="fdId" />&type=${JsParam.type}','_self');">
</kmss:auth>
<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=delete&fdId=${param.fdId}&type=${param.type}" requestMethod="GET">
	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirm_delete())return;Com_OpenWindow('sysAuthRole.do?method=delete&fdId=<bean:write name="sysAuthRoleForm" property="fdId" />&type=${JsParam.type}','_self');">
</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-authorization" key="table.sysAuthRole"/></p>
<center>
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-authorization" key="sysAuthRole.fdName"/>
					</td><td width=35%>
						<bean:write name="sysAuthRoleForm" property="fdName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-authorization" key="sysAuthRole.sysAuthCategory" />
					</td><td width=35%>
						<xform:select property="fdCategoryId">
							<xform:beanDataSource serviceBean="sysAuthCategoryService" orderBy="sysAuthCategory.fdOrder"/>
						</xform:select>
					</td>
				</tr> 	
				
				<c:if test="${param.type != '2' && (sysAuthRoleForm.fdType == '1' || sysAuthRoleForm.fdType == '2')}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-authorization" key="sysAuthRole.authArea"/>
					</td><td width=85% colspan="3">
						<bean:write name="sysAuthRoleForm" property="authAreaName"/>
					</td>
				</tr>
				</c:if>
			
				<c:if test="${param.type != '2'}">
					<tr>
						<td class="td_normal_title">
							<bean:message bundle="sys-authorization" key="sysAuthRole.fdOrgElements"/>
						</td><td colspan="3" style="word-break:break-all;word-wrap:break-word;">
							<bean:write name="sysAuthRoleForm" property="fdOrgElementNames"/>
						</td>
					</tr>
					<c:if test="${not empty sysAuthRoleForm.fdTemplateName }">
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-authorization" key="sysAuthRole.fdTemplateName"/>
							</td><td colspan="3" style="word-break:break-all;word-wrap:break-word;">
								<bean:write name="sysAuthRoleForm" property="fdTemplateName"/>
							</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td class="td_normal_title">
						<bean:message bundle="sys-authorization" key="sysAuthRole.fdAuthAssign"/>
					</td><td colspan="3">
						<!-- 权限部分 -->
						<c:import charEncoding="UTF-8" url="/sys/authorization/sys_auth_role/sysAuthAssign_view.jsp">
							<c:param name="formName" value="sysAuthRoleForm"/>
							<c:param name="authAssignMapName" value="fdAuthAssignMap"/>
						</c:import>
					</td>
				</tr>
				<%-- 三员管理下没有角色继承 --%>
				<c:if test="${tripartiteEnable == 'false'}">
				<tr>
					<td class="td_normal_title">
						<bean:message bundle="sys-authorization" key="sysAuthRole.fdInhRoles"/>
					</td><td colspan="3">
						<bean:write name="sysAuthRoleForm" property="fdInhRoleNames"/>
					</td>
				</tr>
					<c:if test="${sysAuthRoleForm.fdType == '0'}">
					<tr>
						<td class="td_normal_title">
							<bean:message bundle="sys-authorization" key="sysAuthRole.authEditors"/>
						</td><td colspan="3">
							<bean:write name="sysAuthRoleForm" property="authEditorNames"/>
						</td>
					</tr>
					</c:if>
				</c:if>
				<tr>
					<td class="td_normal_title">
						<bean:message bundle="sys-authorization" key="sysAuthRole.fdDescription"/>
					</td><td colspan="3">
						<bean:write name="sysAuthRoleForm" property="fdDescription"/>
					</td>
				</tr>
					<tr>
						<td class="td_normal_title">
							<bean:message bundle="sys-authorization" key="sysAuthRole.fdCreator"/>
						</td><td width=35%>
							<bean:write name="sysAuthRoleForm" property="fdCreatorName"/>
						</td>
						<td class="td_normal_title"><bean:message bundle="sys-authorization" key="sysAuthRole.docCreateTime"/>
						</td><td width=35%>
						  <bean:write name="sysAuthRoleForm" property="docCreateTime" />
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
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
		<td>
			<iframe id="IFRAME" style="visibility: hidden;" name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysAuthRoleForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
<script>
	$(function(){
		setTimeout(function(){
			$('#IFRAME').css('visibility','visible');
		},300);
	})
</script>