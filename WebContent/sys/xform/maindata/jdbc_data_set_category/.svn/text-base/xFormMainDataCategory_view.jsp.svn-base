<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<p class="txttitle"><bean:message bundle="sys-news" key="table.sysNewsTemplate" /></p>
<%--按钮--%>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('xFormJdbcDataSetCategory.do?method=edit&fdId=${JsParam.fdId}&fdModelName=com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory','_self');">
	<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
</div>
<center> 
	<table id="Label_Tabel" width=95%>
	<!-- 模板信息 -->
			<tr LKS_LabelName="<bean:message bundle='sys-simplecategory' key='table.sysSimpleCategory' />">
				<td>
					<table class="tb_normal" width="100%">
						<c:import url="/sys/xform/maindata/jdbc_data_set_category/xFormMainDataCategory_view_body.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="sysFormJdbcDataSetCategoryForm" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory" />
						</c:import>
					</table>
				</td>
			</tr>
	</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>