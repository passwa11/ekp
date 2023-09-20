<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
function confirm_invalidated(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
	return msg;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=edit&fdId=${sysOrgPostForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgElementExternalPost.do?method=edit&fdId=${sysOrgPostForm.fdId}','_self');">
	</kmss:auth>
	<c:if test="${'true' eq sysOrgPostForm.fdIsAvailable}">
	<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=invalidated&fdId=${sysOrgPostForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />" onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgElementExternalPost.do?method=invalidated&fdId=${sysOrgPostForm.fdId}','_self');">
	</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElementExternal.post"/></div>
<center>
<c:set var="showLog" value="false" />
<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPostForm.fdId}" requestMethod="GET">
<c:set var="showLog" value="true" />
</kmss:auth>
<c:if test="${'true' eq showLog}">
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
		<td>
</c:if>
			<table class="tb_normal" width=95%>
				<tr>
				    <!-- 岗位名称  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdName"/></td>
					<td width=35%><pre><bean:write name="sysOrgPostForm" property="fdName"/></pre></td>
					<!-- 编号  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdNo"/></td>
					<td width=35%><bean:write name="sysOrgPostForm" property="fdNo"/></td>
				</tr>
				<tr>
				    <!-- 所在部门  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElementExternal.fdElement"/></td>
					<td width=35%>
						<pre><%=com.landray.kmss.sys.organization.util.SysOrgUtil.getFdParentsNameByForm((com.landray.kmss.sys.organization.forms.SysOrgPostForm)request.getAttribute("sysOrgPostForm"))%></pre>
					</td>
					<!-- 岗位领导  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdThisLeader"/></td>
					<td width=35%><pre><bean:write name="sysOrgPostForm" property="fdThisLeaderName"/></pre></td>
				</tr>
				<tr>
				    <!-- 人员列表  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdPersons"/></td>
					<td colspan=3><pre><bean:write name="sysOrgPostForm" property="fdPersonNames"/></pre></td>
				</tr>
				<tr>
					<!-- 排序号  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdOrder"/></td>
					<td width=35%><bean:write name="sysOrgPostForm" property="fdOrder"/></td>
					<!-- 是否有效  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdIsAvailable"/></td>
					<td><sunbor:enumsShow value="${sysOrgPostForm.fdIsAvailable}" enumsType="sys_org_available_result" /></td>
				</tr>
				<tr>
				    <!-- 备注  -->
					<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPost.fdMemo"/></td>
					<td colspan="3"><pre><kmss:showText value="${sysOrgPostForm.fdMemo}"/></pre></td>
				</tr>
			</table>
<c:if test="${'true' eq showLog}">
		</td>
	</tr>
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
		<td>
			<iframe name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPostForm.fdId}" />' frameBorder=0 width="100%"></iframe>
		</td>
	</tr>
</table>
</c:if>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>