<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>	
<script>
	function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('kmImeetingEquipment.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('kmImeetingEquipment.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingEquipment"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<%--设备名称--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingEquipment.fdName"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<%--排序号--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingEquipment.fdOrder"/>
		</td>
		<td width="85%" colspan="3">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<%--是否有效--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingEquipment.fdIsAvailable"/>
		</td>
		<td width="85%" colspan="3">
			<xform:radio property="fdIsAvailable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>

	<tr>
		<%-- 可维护者 --%>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.tempEditorName" />
		</td>
		<td width=85% colspan="3">
			<c:out value="${kmImeetingEquipmentForm.authEditorNames}" />
		</td>
	</tr>
	
	<tr>
		<!-- 可使用者 -->
		<td class="td_normal_title" width=15%>
			<bean:message key="model.tempReaderName"/>
		</td>
		<td width="85%" colspan="3">
			<c:out value="${kmImeetingEquipmentForm.authReaderNames}" />
		</td>
	</tr>
	
	<tr>
		<%--创建人--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingEquipment.docCreator"/>
		</td>
		<td width="35%">
			<c:out value="${kmImeetingEquipmentForm.docCreatorName}" />
		</td>
		<%--创建时间--%>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-imeeting" key="kmImeetingEquipment.docCreateTime"/>
		</td>
		<td width=35%>
			<c:out value="${kmImeetingEquipmentForm.docCreateTime}" />
		</td>
	</tr>
	<%-- 所属场所 --%>
     <%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
     <tr>
	   <td class="td_normal_title" width="15%">
		    <bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
		</td>
		<td char="3">
			<xform:text style="width:97%" property="authAreaName" showStatus="view" />	
		</td>
	</tr>	
	<% } %>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>