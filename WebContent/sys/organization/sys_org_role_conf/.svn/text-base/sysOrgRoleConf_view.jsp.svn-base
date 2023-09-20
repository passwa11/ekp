<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
function confirm_invalidated(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
	return msg;
}
function confirm_copy(){
	var msg = confirm("<bean:message bundle="sys-organization" key="sysOrgRoleConf.copy.comfirm"/>");
	return msg;	
}
</script>
<style>
.btn_txt {
	margin: 0px 2px;
	color: #2574ad;
	border-bottom: 1px solid transparent;
}

.btn_txt:hover {
	text-decoration: underline;
}
</style>
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator"/>"
		onclick="Com_OpenWindow('<c:url value="/sys/organization/sys_org_role/sysOrgRole_simulator.jsp"/>','_blank');">
	<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="table.sysOrgRoleLine"/>"
			onclick="Com_OpenWindow('<c:url value="/sys/organization/sys_org_role_line/sysOrgRoleLine.do?method=roleTree&fdConfId=${JsParam.fdId}" />','_blank');">
		<input type="button" value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysOrgRoleConf.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<!--<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysOrgRoleConf.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>-->
	<c:if test="${sysOrgRoleConfForm.fdIsAvailable}">
	<kmss:auth
		requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=invalidated&fdId=${param.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="organization.invalidated" bundle="sys-organization"/>"
				onclick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgRoleConf.do?method=invalidated&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth
		requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=updateCopy&fdId=${param.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="sysOrgRoleConf.copy" bundle="sys-organization"/>"
				onclick="if(!confirm_copy())return;Com_OpenWindow('sysOrgRoleConf.do?method=updateCopy&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>	
	</c:if>		
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-organization" key="table.sysOrgRoleConf"/></p>
<center>
<table class="tb_normal" width=60%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdName"/>
		</td><td width=80%>
			<c:out value="${sysOrgRoleConfForm.fdName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdRoleConfCate"/>
		</td><td width=80%>
			<c:out value="${sysOrgRoleConfForm.fdRoleConfCateName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdOrder"/>
		</td><td width=80%>
			<c:out value="${sysOrgRoleConfForm.fdOrder}" />
		</td>
	</tr>
	<tr>
		<td width=20% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgRoleConf.fdIsAvailable"/>
		</td><td width=80%>
			<sunbor:enumsShow value="${sysOrgRoleConfForm.fdIsAvailable}" enumsType="sys_org_available_result" />
		</td>
	</tr>
	<tr title="<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdRoleLineEditors.detail"/>">
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.fdRoleLineEditors"/>
		</td><td width=85%>
			<c:out value="${sysOrgRoleConfForm.fdRoleLineEditorNames}" />
			
		</td>
	</tr>
	<!-- 可使用者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="sys-organization" key="sysOrgRoleConf.fdRoleLineReaders" /></td>
		<td width=85% colspan="3"><c:out value="${sysOrgRoleConfForm.fdRoleLineReaderNames}" /></td>
	</tr>	
	<tr align="center">
		<td class="td_normal_title" colspan="2"><bean:message bundle="sys-organization" key="table.sysOrgRole" /></td>
	</tr>
	<tr>
		<td colspan="2">
			<table width="100%" class="tb_normal">
				<tr align="center">
					<td class="td_normal_title" width="10%">
						<bean:message bundle="sys-organization" key="sysOrgRole.fdOrder" />
					</td>
					<td class="td_normal_title" width="25%">
						<bean:message bundle="sys-organization" key="sysOrgRole.fdName" />
					</td>
					<td class="td_normal_title" width="45%">
						<bean:message bundle="sys-organization" key="sysOrgRole.fdDescription" />
					</td>
					<td class="td_normal_title" width="10%">
						<bean:message bundle="sys-organization" key="sysOrgRole.fdIsAvailable" />
					</td>
					<td class="td_normal_title" width="10%">
						<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=add&fdConfId=${sysOrgRoleConfForm.fdId}" requestMethod="GET">
							<a class="btn_txt" target="_blank" onclick="Com_OpenNewWindow(this)" data-href="<c:url value='/sys/organization/sys_org_role/sysOrgRole.do?method=add&fdConfId=${sysOrgRoleConfForm.fdId}'/>"><bean:message key="button.add"/></a>
							<a class="btn_txt" href="#" onclick="history.go(0);"><bean:message key="button.refresh"/></a>
						</kmss:auth>
					</td>
				</tr>
				<c:forEach items="${roles}" var="role">
					<tr align="center">
						<td><c:out value="${role.fdOrder}" /></td>
						<td><c:out value="${role.fdName}" /></td>
						<td><c:out value="${role.fdMemo}" /></td>
						<td><sunbor:enumsShow value="${role.fdIsAvailable}" enumsType="common_yesno" /></td>
						<td>
							<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=edit&fdId=${role.fdId}&fdConfId=${sysOrgRoleConfForm.fdId}" requestMethod="GET">
								<a class="btn_txt" onclick="Com_OpenNewWindow(this)" data-href="<c:url value='/sys/organization/sys_org_role/sysOrgRole.do?method=edit&fdId=${role.fdId}&fdConfId=${sysOrgRoleConfForm.fdId}'/>" target="_blank"><bean:message key="button.edit"/></a>
							</kmss:auth>
							<!--<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=delete&fdId=${role.fdId}&fdConfId=${sysOrgRoleConfForm.fdId}" requestMethod="GET">
								<a href="<c:url value='/sys/organization/sys_org_role/sysOrgRole.do?method=delete&fdId=${role.fdId}&fdConfId=${sysOrgRoleConfForm.fdId}'/>" target="_blank"><bean:message key="button.delete"/></a>
							</kmss:auth>-->
						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
</center>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
<%@ include file="/resource/jsp/view_down.jsp"%>