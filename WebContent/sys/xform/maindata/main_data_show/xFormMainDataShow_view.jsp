<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.main.dadta.insystem') }"></c:out>
</title>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>

<div id="optBarDiv">

	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysFormMainDataShow.do?method=edit&fdId=${JsParam.fdId}','_self');">


	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysFormMainDataShow.do?method=delete&fdId=${JsParam.fdId}','_self');">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.main.dadta.insystem"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
		</td><td width="35%">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
					propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
			</xform:dialog>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.docSubject"/>
		</td>
		<td width=35%>
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.docSubject') }" style="width:85%" />
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdModleName"/>
		</td>
		<td colspan="3">
			<c:out value="${sysFormMainDataShowForm.fdModelNameText }"></c:out>
		</td>
	</tr>
	
	
	<!-- 返回值 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdShowFields"/>
		</td>
		<td colspan="3">
			<c:out value="${sysFormMainDataShowForm.fdShowFieldsText }"></c:out>
		</td>
	</tr>
	
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdCreator"/>
		</td>
		<td width="35%">
			<c:out value="${sysFormMainDataShowForm.docCreatorName }"></c:out>				
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdCreateTime"/>
		</td>
		<td width="35%">
			<c:out value="${sysFormMainDataShowForm.docCreateTime }"></c:out>
		</td>
	</tr>
	<c:if test="${not empty sysFormMainDataShowForm.docAlterorName}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.docAlteror"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataShowForm.docAlterorName }"></c:out>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdAlterTime"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataShowForm.docAlterTime }"></c:out>
			</td>
		</tr>
	</c:if>
	
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>