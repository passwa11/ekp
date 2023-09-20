<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");
</script>
<html:form action="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do">

	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="sysFormJdbcDataSetCategoryForm" />
		</c:import>

	<p class="txttitle"><bean:message key="news.category.set" bundle="sys-news" /></p>

	<center>

	<table id="Label_Tabel" width=95%>
		<!-- 模板信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-simplecategory' key='table.sysSimpleCategory' />">
			<td>
				<table class="tb_normal" width="100%">
					<c:import url="/sys/xform/maindata/jdbc_data_set_category/xFormMainDataCategory_edit_body.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysFormJdbcDataSetCategoryForm" />
						<c:param name="requestURL" value="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do?method=add" />
						<c:param name="fdModelName" value="${param.fdModelName}" />
					</c:import>
				</table>
			</td>
		</tr>
	</table>
	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
</html:form>
	<script>
	$KMSSValidation();

</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
