<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<p class="txttitle"><bean:message bundle="sys-xform-fragmentSet" key="category.set" /></p>
<%--按钮--%>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('xFormFragmentSetCategory.do?method=edit&fdId=${JsParam.fdId}&fdModelName=com.landray.kmss.sys.xform.fragmentSet.model.SysFormFragmentSetCategory','_self');">
	<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
</div>
<kmss:windowTitle moduleKey="sys-xform-fragmentSet:table.sysFormFragmentSet"  subjectKey="sys-xform-fragmentSet:sysFormFragmentSetCategory.set"  />
<center> 
	<table id="Label_Tabel" width=95%>
	<!-- 模板信息 -->
			<tr LKS_LabelName="<bean:message bundle='sys-simplecategory' key='table.sysSimpleCategory' />">
				<td>
					<table class="tb_normal" width="100%">
						<c:import url="/sys/xform/fragmentSet/category/xFormFragmentSetCategory_view_body.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="sysFormFragmentSetCategoryForm" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.xform.fragmentSet.model.SysFormFragmentSetCategory" />
						</c:import>
					</table>
				</td>
			</tr>
	</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>