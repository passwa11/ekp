<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do">
<div id="optBarDiv">
	<c:if test="${ticJdbcRelationForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.ticJdbcRelationForm, 'update');">
	</c:if>
	<c:if test="${ticJdbcRelationForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.ticJdbcRelationForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.ticJdbcRelationForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-jdbc" key="table.ticJdbcRelation"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcRelation.fdUseExplain"/>
		</td><td width="35%">
			<xform:text property="fdUseExplain" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcRelation.fdSyncType"/>
		</td><td width="35%">
			<xform:textarea property="fdSyncType" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcRelation.ticJdbcMappCategory"/>
		</td><td width="35%">
			<xform:select property="ticJdbcMappCategoryId">
				<xform:beanDataSource serviceBean="ticJdbcMappManageService" selectBlock="fdId,docSubject" orderBy="" />
			</xform:select>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcRelation.ticJdbcTaskManage"/>
		</td><td width="35%">
			<xform:select property="ticJdbcTaskManageId">
				<xform:beanDataSource serviceBean="ticJdbcTaskManageService" selectBlock="fdId,fdId" orderBy="" />
			</xform:select>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>