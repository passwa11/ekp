<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do">

<script type="text/javascript">
Com_IncludeFile("dialog.js", null, "js");
Com_IncludeFile("jquery.js");
function saveAdd() {
	Com_Submit(document.ticJdbcDataSetCategoryForm, 'saveadd');
}

</script>

	<center>
	<table class="tb_normal" width=95%>
		<tr>
		<td class="td_normal_title" width=15%><bean:message
				bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdParent" /></td>
			<td>
				<html:hidden property="fdParentId"/>
				<html:text property="fdParentName" readonly="true" styleClass="inputsgl" style="width:35%"/>
				<a href="#" onclick="Dialog_Tree(false, 'fdParentId', 'fdParentName', ',', 
						'ticJdbcDataSetCategoryTreeService&parentId=!{value}', 
						'<bean:message key="table.ticJdbcDataSetCategory" bundle="tic-jdbc"/>', 
						null, null, '${ticJdbcDataSetCategoryForm.fdId}', null, null, 
						'<bean:message  bundle="tic-jdbc" key="table.ticJdbcDataSetCategory"/>');">
					<bean:message key="dialog.selectOther"/>
				</a>
			</td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdName" /></td>
			<td width="35%"  id="_fdName"><xform:text property="fdName" style="width:85%" required="true"/>
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
