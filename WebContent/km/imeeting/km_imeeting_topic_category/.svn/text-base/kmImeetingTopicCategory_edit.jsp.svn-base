<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<kmss:windowTitle subjectKey="km-imeeting:table.kmImeetingTopicCategory" moduleKey="km-imeeting:module.km.imeeting" />
<script language="JavaScript">
Com_IncludeFile("dialog.js");
</script>
<html:form action="/km/imeeting/km_imeeting_topic_category/kmImeetingTopicCategory.do">
		
	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="kmImeetingTopicCategoryForm" />
	</c:import>

	<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingTopicCategory" /></p>

	<center>
	<table
		id="Label_Tabel"
		width="95%">

		<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicCategoryForm" />
			<c:param name="requestURL" value="km/imeeting/km_imeeting_topic_category/kmImeetingTopicCategory.do?method=add" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
		</c:import>
		<!-- 流程 -->
		<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp"
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
					url="/sys/right/tmp_right_edit.jsp"
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
		<c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicCategoryForm" />
			<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic"/>
		</c:import>
	</table>
	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
</html:form>
<html:javascript formName="kmImeetingTopicCategoryForm" cdata="false" dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>
