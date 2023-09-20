<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<kmss:windowTitle subjectKey="km-imeeting:table.kmImeetingTopicCategory" moduleKey="km-imeeting:module.km.imeeting" />
<script language="JavaScript">
Com_IncludeFile("dialog.js");
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/km/imeeting/km_imeeting_topic_category/kmImeetingTopicCategory.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('kmImeetingTopicCategory.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

	<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingTopicCategory" /></p>

	<center>
	<table
		id="Label_Tabel"
		width="95%">

		<c:import url="/sys/simplecategory/include/sysCategoryMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicCategoryForm" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="fdKey" value="DocCategory" />
		</c:import>
		<!-- 流程 -->
		<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicCategoryForm" />
			<c:param name="fdKey" value="mainTopic" /> 
		</c:import>
		<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
			<td>
			<table
				class="tb_normal"
				width=100%>
				<c:import
					url="/sys/right/tmp_right_view.jsp"
					charEncoding="UTF-8">
					<c:param
						name="formName"
						value="kmImeetingTopicCategoryForm" />
					<c:param
						name="moduleModelName"
						value="com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory" />
				</c:import>
			</table>
			</td>
		</tr>
		<!-- 编号机制 -->
		<c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicCategoryForm" />
			<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic"/>
		</c:import>
	</table>
	</center>
<html:javascript formName="kmImeetingTopicCategoryForm" cdata="false" dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/view_down.jsp"%>
