<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<p class="txttitle"><bean:message key="category.set" bundle="sys-lbpmservice-support" /></p>
<%--按钮--%>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('lbpmEmbeddedSubFlowCategory.do?method=edit&fdId=${JsParam.fdId}&fdModelName=com.landray.kmss.sys.lbpmservice.support.model.LbpmEmbeddedSubFlowCategory','_self');">
	<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
</div>
<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmEmbeddedSubFlow"  subjectKey="sys-lbpmservice-support:lbpmEmbeddedSubFlowCategory.set"  />
<center> 
	<table id="Label_Tabel" width=95%>
	<!-- 模板信息 -->
			<tr LKS_LabelName="<bean:message bundle='sys-simplecategory' key='table.sysSimpleCategory' />">
				<td>
					<table class="tb_normal" width="100%">
						<c:import url="/sys/lbpmservice/support/lbpm_embeddedSubFlow/category/lbpmEmbeddedSubFlowCategory_view_body.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="lbpmEmbeddedSubFlowCategoryForm" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.lbpmservice.support.model.LbpmEmbeddedSubFlowCategory" />
						</c:import>
					</table>
				</td>
			</tr>
	</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>