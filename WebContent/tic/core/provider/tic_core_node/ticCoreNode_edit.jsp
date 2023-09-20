<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/core/provider/tic_core_node/ticCoreNode.do">
<div id="optBarDiv">
	<c:if test="${ticCoreNodeForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.ticCoreNodeForm, 'update');">
	</c:if>
	<c:if test="${ticCoreNodeForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.ticCoreNodeForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.ticCoreNodeForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-provider" key="table.ticCoreNode"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeLevel"/>
		</td><td width="35%">
			<xform:text property="fdNodeLevel" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeName"/>
		</td><td width="35%">
			<xform:text property="fdNodeName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodePath"/>
		</td><td width="35%">
			<xform:text property="fdNodePath" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDataType"/>
		</td><td width="35%">
			<xform:text property="fdDataType" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeEnable"/>
		</td><td width="35%">
			<xform:radio property="fdNodeEnable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDefName"/>
		</td><td width="35%">
			<xform:text property="fdDefName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeContent"/>
		</td><td width="35%">
			<xform:text property="fdNodeContent" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdIface"/>
		</td><td width="35%">
			<xform:select property="fdIfaceId">
				<xform:beanDataSource serviceBean="ticCoreIfaceService" selectBlock="fdId,fdId" orderBy="" />
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