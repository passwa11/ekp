<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
function confirm_invalidated(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
	return msg;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=edit&fdId=${sysOrgGroupForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgGroup.do?method=edit&fdId=<bean:write name="sysOrgGroupForm" property="fdId" />','_self');">
	</kmss:auth>
	<c:if test="${sysOrgGroupForm.fdIsAvailable}">
	<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidated&fdId=${sysOrgGroupForm.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />" onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgGroup.do?method=invalidated&fdId=<bean:write name="sysOrgGroupForm" property="fdId" />','_self');">
	</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-organization" key="sysOrgElement.group"/></div>
<center>
<table class="tb_normal" width=95%>
	<tr>
	    <!-- 群组名称  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdName"/></td>
		<td colspan="3"><pre><bean:write name="sysOrgGroupForm" property="fdName"/></pre></td>
	</tr>
	<tr>
	    <!-- 群组类别  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdGroupCate"/></td>
		<td width=35%><pre><bean:write name="sysOrgGroupForm" property="fdGroupCateName"/></pre></td>
		<!-- 编号  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdNo"/></td>
		<td width=35%><bean:write name="sysOrgGroupForm" property="fdNo"/></td>
	</tr>
	<tr>
	    <!-- 关键字  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdKeyword"/></td>
		<td width=35%><pre><bean:write name="sysOrgGroupForm" property="fdKeyword"/></pre></td>
		<!-- 排序号  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdOrder"/></td>
		<td width=35%><bean:write name="sysOrgGroupForm" property="fdOrder"/></td>
	</tr>
	<tr>
	    <!-- 邮件地址  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgElement.fdOrgEmail"/></td>
		<td width=35% colspan="3"><pre><bean:write name="sysOrgGroupForm" property="fdOrgEmail"/></pre></td>
	</tr>
	<tr>
	    <!-- 群组成员  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdMembers"/></td>
		<td colspan="3"><pre style="word-wrap:break-word;word-break:break-all;"><bean:write name="sysOrgGroupForm" property="fdMemberNames"/></pre></td>
	</tr>
	<tr>
	    <!-- 是否有效  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdIsAvailable"/></td>
		<td colspan="3"><sunbor:enumsShow value="${sysOrgGroupForm.fdIsAvailable}" enumsType="sys_org_available_result" /></td>
	</tr>
	<tr>
	    <!-- 可使用者  -->
		<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgGroup.authReaders"/></td>
		<td width=85% colspan="3"><pre><kmss:showText value="${sysOrgGroupForm.authReaderNames}"/></pre></td>
	</tr>
	<tr>
	    <!-- 可维护者  -->
		<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgGroup.authEditors"/></td>
		<td width=85% colspan="3"><pre><kmss:showText value="${sysOrgGroupForm.authEditorNames}"/></pre></td>
	</tr>
	<tr>
	    <!-- 备注  -->
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgGroup.fdMemo"/></td>
		<td colspan="3"><pre><kmss:showText value="${sysOrgGroupForm.fdMemo}"/></pre></td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>