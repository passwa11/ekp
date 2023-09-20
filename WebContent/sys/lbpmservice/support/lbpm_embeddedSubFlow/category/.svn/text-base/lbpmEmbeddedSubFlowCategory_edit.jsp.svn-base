<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmEmbeddedSubFlow"  subjectKey="sys-lbpmservice-support:lbpmEmbeddedSubFlowCategory.set"  />
<html:form action="/sys/lbpmservice/support/lbpmEmbeddedSubFlowCategory.do">

	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="lbpmEmbeddedSubFlowCategoryForm" />
		</c:import>

	<p class="txttitle"><bean:message key="category.set" bundle="sys-lbpmservice-support" /></p>

	<center>

	<table id="Label_Tabel" width=95%>
		<!-- 模板信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-simplecategory' key='table.sysSimpleCategory' />">
			<td>
				<table class="tb_normal" width="100%">
					<c:import url="/sys/lbpmservice/support/lbpm_embeddedSubFlow/category/lbpmEmbeddedSubFlowCategory_edit_body.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="lbpmEmbeddedSubFlowCategoryForm" />
						<c:param name="requestURL" value="/sys/lbpmservice/support/lbpmEmbeddedSubFlowCategory.do?method=add" />
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
