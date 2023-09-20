<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>Com_IncludeFile("data.js");</script>
<script>
	function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.edit"/>" onclick="Com_OpenWindow('kmCalendarPersonGroup.do?method=edit&fdId=${JsParam.fdId}','_self');">
	<input type="button" value="<bean:message key="button.delete"/>" onclick="if(!confirmDelete())return;Com_OpenWindow('kmCalendarPersonGroup.do?method=delete&fdId=${JsParam.fdId}','_self');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-calendar" key="table.kmCalendarPersonGroup"/></p>

<center>
	<table class="tb_normal" width=100%>
		<tr>
			<%--模板名称--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.docSubject"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="docSubject" style="width:85%" />
			</td>
		</tr>
		<tr>
			<%--群组描述 --%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdDescription"/>
			</td>
			<td>
				<xform:textarea property="fdDescription" />
			</td>
		</tr>
		<tr>
			<%--排序号--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdOrder"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="fdOrder" style="width:100px" />
			</td>
		</tr>
		<tr>
			<%--群组成员--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdPersonGroup"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true"  propertyId="fdPersonGroupIds" propertyName="fdPersonGroupNames" style="width:85%" />
			</td>
		</tr>
		<tr>
			<%--可阅读者--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.authReaders"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true"  propertyId="authReaderIds" propertyName="authReaderNames" style="width:85%" />
			</td>
		</tr>
		<tr>
			<%--可维护者--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.authEditors"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true"  propertyId="authEditorIds" propertyName="authEditorNames" style="width:85%" />
			</td>
		</tr>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>