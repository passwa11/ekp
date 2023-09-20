<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<c:set var="formName" value="sysAttendCategoryATemplateForm" />
<c:set var="requestURL" value="/sys/attend/sys_attend_category_atempl/sysAttendCategoryATemplate.do" />
<c:set var="fdId" value="${sysAttendCategoryATemplateForm.fdId}" />
<c:set var="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate" />
<script language="JavaScript">
	Com_IncludeFile("dialog.js");
</script>

	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_view_button.jsp"
				charEncoding="UTF-8">
		<c:param name="formName" value="${formName}" />
		<c:param name="requestURL" value="${requestURL}" />
		<c:param name="fdId" value="${fdId}" />
		<c:param name="fdModelName" value="${fdModelName}" />
	</c:import>
	
	<p class="txttitle"><bean:message bundle="sys-attend" key="table.sysAttendCategoryATemplate" /></p>

	<center>
	<table class="tb_normal" id="Label_Tabel" width=95%>
		<c:import url="/sys/simplecategory/include/sysCategoryMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="${formName}" />
			<c:param name="fdModelName" value="${fdModelName}" />
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
							value="sysAttendCategoryATemplateForm" />
						<c:param
							name="moduleModelName"
							value="com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate" />
					</c:import>
				</table>
			</td>
		</tr>
		<c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="sysAttendCategoryATemplateForm" />
			<c:param name="fdKey" value="attendMainExc" />
			<c:param name="messageKey" value="sys-attend:sysAttendCategoryTemplate.exception" />
		</c:import>
	</table>
	</center>
<%@ include file="/resource/jsp/view_down.jsp"%>	
