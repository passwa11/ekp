<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTagNew"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTagNew.isLangSuportEnabled());
%>
<script language="JavaScript">
if(DocList_Info == null){
	DocList_Info = new Array("TABLE_DocList_Historyhandler");
}else{
	DocList_Info.push("TABLE_DocList_Historyhandler");
} 
</script>
<table class="tb_normal" width=100% id="TABLE_DocList_Historyhandler">	
	<tr>
		<td class="td_normal_title" align="center" width=20%>
			<bean:message key="lbpmOperations.fdOperType" bundle="sys-lbpmservice-support"/>
		</td>
		<td class="td_normal_title" align="center" width=50%>
			<bean:message key="lbpmOperations.fdOperName" bundle="sys-lbpmservice-support" />
		</td>
		<td class="td_normal_title" align="center" width=30%>
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/add.gif"/>" 
				 title="<bean:message key="button.create" />" 
				 onclick="AddRow_InitHistoryhandlerSelect();"> 
		</td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display:none">
		<td align="center">
			<input type="hidden" name="historyhandlerOperations[!{index}].fdId">
			<xform:select property="historyhandlerOperations[!{index}].fdOperType" showPleaseSelect="true" required="true" subject="${operType}" onValueChange="selectOperType">
			</xform:select>
		</td>
		<td align="left">
			<%-- <xform:text property="historyhandlerOperations[!{index}].fdOperName" required="true" subject="${operName}" style="width:80%"/>
			<xlang:lbpmlang property="historyhandlerOperations[!{index}].fdOperName" style="width:80%"/> --%>
			<c:if test="${!isLangSuportEnabled }">
				<xform:text property="historyhandlerOperations[!{index}].fdOperName" required="true" subject="${operName}" style="width:80%"/>
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="historyhandlerOperations[!{index}].fdOperName" validators="required" subject="${operName}" style="width:80%"/>
			</c:if>
			<input type="hidden" name="historyhandlerOperations[!{index}].fdLangJson" />
		</td>
		<td align="center">
		    <img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/delete.gif"/>" 
				title="<bean:message key="button.delete" />"
				onclick="DocList_DeleteRow();">
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/up.gif"/>" 
				title="<bean:message key="button.moveup" />"
				onclick="DocList_MoveRow(-1);"> 
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/down.gif"/>" 
				title="<bean:message key="button.movedown" />"
				onclick="DocList_MoveRow(1);"> 
		</td>
	</tr>
	<c:forEach items="${lbpmOperMainForm.historyhandlerOperations}" var="lbpmOperationsForm" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		<td align="center">
			<input type="hidden" name="historyhandlerOperations[${vstatus.index}].fdId" value="${lbpmOperationsForm.fdId}"/>
			<c:if test="${lbpmOperMainForm.method_GET=='edit'}">
			    <xform:select property="historyhandlerOperations[${vstatus.index}].fdOperType" value="${lbpmOperationsForm.fdOperType}" showPleaseSelect="true" required="true" subject="${operType}" onValueChange="selectOperType">
			        <xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.HistoryhandlerOperationDataSource" />
			    </xform:select>
			</c:if>
			<c:if test="${lbpmOperMainForm.method_GET=='add'}">
			    <xform:select property="historyhandlerOperations[${vstatus.index}].fdOperType" value="${lbpmOperationsForm.fdOperType}" showPleaseSelect="true" required="true" subject="${operType}" onValueChange="selectOperType">
			    </xform:select>
			</c:if>
		</td>
		<td align="left">	
			<%-- <xform:text property="historyhandlerOperations[${vstatus.index}].fdOperName" required="true" subject="${operName}" style="width:80%" value="${lbpmOperationsForm.fdOperName}"/>
			<xlang:lbpmlang property="historyhandlerOperations[${vstatus.index}].fdOperName" langs="${lbpmOperationsForm.fdLangJson}" style="width:80%" /> --%>
			<c:if test="${!isLangSuportEnabled }">
				<xform:text property="historyhandlerOperations[${vstatus.index}].fdOperName" required="true" subject="${operName}" style="width:80%" value="${lbpmOperationsForm.fdOperName}"/>
			</c:if>
			<c:if test="${isLangSuportEnabled }">
				<xlang:lbpmlangNew property="historyhandlerOperations[${vstatus.index}].fdOperName" validators="required" subject="${operName}" value="${lbpmOperationsForm.fdOperName}" langs="${lbpmOperationsForm.fdLangJson}" style="width:80%" /> 
			</c:if>
			<html:hidden property="historyhandlerOperations[${vstatus.index}].fdLangJson" />
		</td>
		<td align="center">
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/delete.gif"/>" 
				title="<bean:message key="button.delete" />"
				onclick="DocList_DeleteRow();">
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/up.gif"/>" 
				title="<bean:message key="button.moveup" />"
				onclick="DocList_MoveRow(-1);"> 
			<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/down.gif"/>" 
				title="<bean:message key="button.movedown" />"
				onclick="DocList_MoveRow(1);">
		</td>
	</tr>	
	</c:forEach>
</table>