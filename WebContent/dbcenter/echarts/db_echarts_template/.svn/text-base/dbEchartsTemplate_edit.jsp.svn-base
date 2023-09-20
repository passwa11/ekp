<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<kmss:windowTitle subjectKey="dbcenter-echarts:table.dbEchartsTemplate" moduleKey="dbcenter-echarts:table.dbEchartsChart" />
<script language="JavaScript">
	Com_IncludeFile("dialog.js");
</script>

<html:form
	action="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do">
	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="dbEchartsTemplateForm" />
	</c:import>
	<div id="optBarDiv"><c:if test="${dbEchartsTemplateForm.method_GET=='edit'}">
		<input
			type=button
			value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.dbEchartsTemplateForm, 'update');">
	</c:if> <c:if test="${dbEchartsTemplateForm.method_GET=='add'}">
		<input
			type=button
			value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.dbEchartsTemplateForm, 'save');">
		<input
			type=button
			value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.dbEchartsTemplateForm, 'saveadd');">
	</c:if> <input
		type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>
	<p class="txttitle">
		<bean:message bundle="dbcenter-echarts" key="table.dbEchartsTemplate" />
	</p>

	<center>
		<table class="tb_normal" id="Label_Tabel" width=99%>
			<c:import
				url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="dbEchartsTemplateForm" />
				<c:param name="requestURL"
					value="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=add" />
				<c:param name="fdModelName" value="${param.fdModelName}" />
				<c:param name="fdKey" value="dbEchartsTemplate" />
			</c:import>
			<!-- 权限设置 -->
			<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
				<td>
					<table class="tb_normal" width=100%>
						<c:import url="/sys/right/tmp_right_edit.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="dbEchartsTemplateForm" />
							<c:param name="moduleModelName"
								value="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate" />
						</c:import>
					</table>
				</td>
			</tr>
		</table>
	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
