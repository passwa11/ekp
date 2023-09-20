<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/portal/sys_portal_guilde/sysPortalGuide.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysPortalGuide.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/portal/sys_portal_guilde/sysPortalGuide.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysPortalGuide.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-portal" key="table.sysPortalGuide"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.fdName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.fdContent"/>
		</td>
		<td width="85%" colspan="3">
			<c:if test="${sysPortalGuideForm.fdType == 'rft }">
				<xform:rtf property="fdContent" />
			</c:if>
				<c:if test="${sysPortalGuideForm.fdType == 'link }">
				<xform:text property="fdLink" style="width:98%;" showStatus="view"></xform:text>
			</c:if>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:common.msg.editors') }</td>
		<td colspan="3">
			<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:96%;height:90px;" ></xform:address>
		</td>
	</tr> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.docCreator"/>
		</td><td width="35%">
			<c:out value="${sysPortalGuideForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.docAlteror"/>
		</td><td width="35%">
			<c:out value="${sysPortalGuideForm.docAlterorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-portal" key="sysPortalGuide.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>