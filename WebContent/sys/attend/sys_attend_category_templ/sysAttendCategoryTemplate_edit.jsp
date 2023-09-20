<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js|validation.js|plugin.js|validation.jsp");
</script>
<html:form action="/sys/attend/sys_attend_category_templ/sysAttendCategoryTemplate.do">
	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysAttendCategoryTemplateForm" />
	</c:import>

	<p class="txttitle"><bean:message bundle="sys-attend" key="table.sysAttendCategoryTemplate" /></p>
	
	<center>
	<table class="tb_normal" id="Label_Tabel" width=95%>
		<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysAttendCategoryTemplateForm" />
			<c:param name="requestURL" value="/sys/attend/sys_attend_category_templ/sysAttendCategoryTemplate.do?method=add" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="fdKey" value="sysAttendCategoryTemplate" />
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
						value="sysAttendCategoryTemplateForm" />
					<c:param
						name="moduleModelName"
						value="com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate" />
				</c:import>
			</table>
			</td>
		</tr>
	</table>
	</center>
	
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
</html:form>
<script type="text/javascript">
	$KMSSValidation();
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
