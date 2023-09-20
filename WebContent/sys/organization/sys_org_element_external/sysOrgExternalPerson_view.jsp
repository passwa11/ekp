<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<style type="text/css">
			.tb_simple tr td{border:0}
			#typeDiv {padding-left:47px}
			.aaaa {padding-left:61px}
			.bbbb {padding-left:71px}
</style>
<script>
function confirm_invalidated(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
	return msg;
}
<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
function confirm_unLock(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.unLock.comfirm"/>");
	return msg;
}
</c:if>
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_person/chgPersonInfo.do?method=chgPwd&fdId=${sysOrgPersonForm.fdId}" requestMethod="GET">
		<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
		<input type="button" value="<bean:message bundle="sys-organization" key="sysOrgPerson.unLock" />"
			onClick="if(!confirm_unLock())return;Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_person/sysOrgPerson.do?method=savePersonUnLock&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
		</c:if>
		<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
		<input type="button"
			value="<bean:message bundle="sys-organization" key="sysOrgPerson.button.changePassword"/>"
			onClick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_person/chgPersonInfo.do?method=chgPwd&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
		</c:if>
	</kmss:auth>
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}" requestMethod="GET">
		<input type="button" value="${ lfn:message('sys-organization:sysOrgElementExternal.outToOut.title') }" onClick="updateOutToOut()">
		<kmss:auth requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}&outToIn=true" requestMethod="GET">
		<input type="button" value="${ lfn:message('sys-organization:sysOrgElementExternal.outToIn.title') }" onClick="updateOutToIn()">
		</kmss:auth>
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}','_self');">
	</kmss:auth>
	<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=invalidated&fdId=${sysOrgPersonForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />"
			onClick="if(!confirm_invalidated())return;Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=invalidated&fdId=${sysOrgPersonForm.fdId}','_self');">
	</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElementExternal.person"/></div>
<center>
<c:set var="showLog" value="false" />
<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPersonForm.fdId}" requestMethod="GET">
<c:set var="showLog" value="true" />
</kmss:auth>
<c:if test="${'true' eq showLog}">
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
		<c:if test="${'false' eq sysOrgPersonForm.fdIsAvailable}">
			<bean:message bundle="sys-organization" key="sysOrgPerson.leave.alert"/>
		</c:if>
		<td>
</c:if>
			<table class="tb_normal" width=95%>
				<tr>
				    <!-- 机构名称  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdName"/></td>
					<td width=35% ><pre><c:out value="${sysOrgPersonForm.fdName}"/></pre></td>
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdNo"/></td>
					<td width=35%>${sysOrgPersonForm.fdNo}</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgElementExternal.fdElement"/>
					</td>
					<td width=35%>
						<c:out value="${deptName}"/>
					</td>
					<!-- 排序号 -->
					<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgElement.fdOrder"/></td>
					<td width="35%">
						${sysOrgPersonForm.fdOrder}
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />
					</td>
					<td width=85% colspan="3">
					  <c:out value="${sysOrgPersonForm.fdLoginName}"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
					<bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" />
					</td>
					<td width=35%>
						${sysOrgPersonForm.fdMobileNo}
					</td>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" />
					</td>
					<td width=35%>
						${sysOrgPersonForm.fdEmail}
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdPosts" /></td>
					<td width=35%>
					 	<c:forEach items="${postList}" var="fdPost" varStatus="index">
							<c:choose>
								<c:when test="${'true' eq fdPost.fdIsExternal}">
									<a href="${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=view&fdId=${fdPost.fdId}" target="blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
								</c:when>
								<c:otherwise>
									<a href="${LUI_ContextPath}/sys/organization/sys_org_post/sysOrgPost.do?method=view&fdId=${fdPost.fdId}" target="blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</td>
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdIsAvailable" /></td>
					<td width="35%"><sunbor:enumsShow value="${sysOrgPersonForm.fdIsAvailable}" enumsType="sys_org_available_result" /></td>
				</tr>
				<tr>
					<!-- 是否登录系统 -->
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgPerson.fdCanLogin" />
					</td>
					<td width=85% colspan="3">
						<sunbor:enumsShow value="${sysOrgPersonForm.fdCanLogin}" enumsType="common_yesno" />
					</td>
				</tr>
				<%-- 扩展属性 --%>
				<c:import url="/sys/organization/sys_org_element_external/sysOrgExternal_common_ext_props_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysOrgPersonForm"/>
				</c:import>
				<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
				<tr>
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.unLock.prompt" /></td>
					<td colspan=3>
						<bean:message bundle="sys-organization" key="sysOrgPerson.unLock.message" arg0="${autoUnlockTime}"/>
					</td>
				</tr>
				</c:if>
			</table>
<c:if test="${'true' eq showLog}">
		</td>
	</tr>
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
		<td>
			<iframe name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPersonForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>
</c:if>
<script type="text/javascript">Com_IncludeFile("dialog.js");</script>
<script>
<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
// 外转外
function updateOutToOut() {
	// 选择外部组织
	Dialog_Tree(false, null, null, null, "sysOrgElementExternalService&type=cate&parent=!{value}", "${ lfn:message('sys-organization:sysOrgEco.name') }", null, function(result) {
		if(result && result.data && result.data.length > 0) {
			Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=outToOut&fdId=${sysOrgPersonForm.fdId}&parent=' + result.data[0].id);
		}
	}, "${sysOrgPersonForm.fdParentId}", null, null, "${ lfn:message('sys-organization:sysOrgElementExternal.selectOrg') }");
}
// 外转内
function updateOutToIn() {
	Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/organization/sys_org_person/sysOrgPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}&outToIn=true');
}
</c:if>
</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>