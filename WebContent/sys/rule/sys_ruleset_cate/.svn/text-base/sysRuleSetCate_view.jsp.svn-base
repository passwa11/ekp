<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
</script>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.edit"/>"
		onClick="Com_OpenWindow('sysRuleSetCate.do?method=edit&fdId=<bean:write name="sysRuleSetCateForm" property="fdId" />','_self');">
	<input type="button" value="<bean:message key="button.delete"/>"
		onClick="if(!confirm_delete())return;Com_OpenWindow('sysRuleSetCate.do?method=delete&fdId=<bean:write name="sysRuleSetCateForm" property="fdId" />','_self');">
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-rule" key="table.sysRuleSetCate"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdParent"/>
		</td>
		<td width=35%>
			<bean:write name="sysRuleSetCateForm" property="fdParentName"/>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdName"/>
		</td>
		<td width=35%>
			<bean:write name="sysRuleSetCateForm" property="fdName"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdCreator"/>
		</td>
		<td width=35%>
			${ sysRuleSetCateForm.fdCreatorName}
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdCreateTime"/>
		</td>
		<td width=35%>
			<bean:write name="sysRuleSetCateForm" property="fdCreateTime" format="yyyy-MM-dd HH:mm"/>
		</td>
	</tr>
	<!-- 可使用者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-rule" key="sysRuleSetCate.authReaders"/></td>
		<td  width=85% colspan="3">
		  <kmss:showText value="${sysRuleSetCateForm.authReaderNames}"/>
	   </td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors"/></td>
		<td width=85% colspan="3">
		  <kmss:showText value="${sysRuleSetCateForm.authEditorNames}"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>