<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<script type="text/javascript">
function addTimeToForm(){
	var startTime = document.getElementById("startTime");
	var endTime = document.getElementById("endTime");
	document.sysFtsearchIndexStatusForm.startTime = startTime;
	document.sysFtsearchIndexStatusForm.endTime = endTime;
}
function List_ConfirmDelIndex(checkName){
	var startTime = document.getElementsByName("startTime")[0].value;
	var confirmInfo = "<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.index.delete.confirm.info'/>".replace("{0}",startTime);
	if(startTime == null || startTime == ""){
		confirmInfo = "<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.index.delete.confirm.only.model.info'/>";
	}
	return List_CheckSelect(checkName) && confirm(confirmInfo);
}
</script>
<html:form action="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do">
	<div id="optBarDiv">
		<%-- 
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do" />?method=add');">
		</kmss:auth>
		--%>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysFtsearchIndexStatusForm, 'deleteall');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do?method=deleteIndex">
			<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.delete.button"/>"
			 	title="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.delete.tip"/>"
				onclick="if(!List_ConfirmDelIndex())return;Com_Submit(document.sysFtsearchIndexStatusForm, 'deleteIndex');">
		</kmss:auth>
	</div>
<xform:datetime property="startTime"/>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<script>
		Com_IncludeFile("dialog.js|calendar.js");
	</script>
	<div>
		<span><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.delete.choose.time"/></span>
		<!-- 开始时间 -->
	   	<input type="text" readonly="true" class="inputsgl" name="startTime" style="width: 15%;">
		<a href="#" onclick="selectDateTime('startTime',null,addTimeToForm);"> <bean:message
			key="dialog.selectTime" /></a>
		<span>-</span>
		<!-- 结束时间 -->
		<input type="text" readonly="true" class="inputsgl" name="endTime" style="width: 15%;">
		<a href="#" onclick="selectDateTime('endTime',null,addTimeToForm);"> <bean:message
			key="dialog.selectTime" /></a>
			&nbsp;
		<span style="color:red;"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.delete.time.description"/></span>
	</div>
        
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<%-- <sunbor:column property="sysAppConfig.fdField">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelClass"/>
				</sunbor:column> --%>
				<td>
				   <bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelClass"/>
				</td>
				<td>
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelName"/>
				</td>
				<%-- <sunbor:column property="sysAppConfig.fdValue">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelLastIndexTime"/>
				</sunbor:column> --%>
				<td>
				  <bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelLastIndexTime"/>
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFtsearchLastIndexTime" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do" />?method=view&fdId=${sysFtsearchLastIndexTime.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysFtsearchLastIndexTime.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td width="40%">
					<c:out value="${sysFtsearchLastIndexTime.fdField}" />
				</td>
				<td width="40%">
					<c:out value="${sysFtsearchLastIndexTime.fdModelName}" />
				</td>
				<td width="40%">
					<c:out value="${sysFtsearchLastIndexTime.fdValue}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>