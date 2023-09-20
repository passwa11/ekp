<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.search.web.SearchResultColumn"%>
<%@page import="com.landray.kmss.sys.search.web.SearchResult"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:if test="${empty searchResultInfo.columns}">
<%@ include file="/km/review/km_review_main/kmReviewMain_list.jsp"%>
</c:if>
<c:if test="${not empty searchResultInfo.columns}">
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.search.web.SearchResultColumn"%>
<kmss:windowTitle
	subject="${searchResultInfo.title}" />
<script type="text/javascript">
Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("dialog.js");
var exportDialogObj = {exportUrl: "${exportURL}", exportNum: "${queryPage.totalrows}"};
function showExportDialog() {
	var style = "width=460,height=480,top=150,left=460, status=0,scrollbars =1, resizable=1";
	window.open(Com_Parameter.ContextPath+"sys/search/search_result_export.jsp?fdModelName=${JsParam.fdModelName}&searchId=${JsParam.searchId}", "", style);
	//Dialog_PopupWindow("<c:url value="/sys/search/search_result_export.jsp?fdModelName=${JsParam.fdModelName}&searchId=${JsParam.searchId}"/>", 460, 480, exportDialogObj);
}
function exportCallbak(returnValue) {
	if(returnValue==null || returnValue==undefined){
		return;  
	}
	//var selectedData = getSelectIndexs();
	document.getElementsByName("fdNum")[0].value = ${queryPage.totalrows};
	document.getElementsByName("fdNumStart")[0].value = returnValue["fdNumStart"];
	document.getElementsByName("fdNumEnd")[0].value = returnValue["fdNumEnd"];
	document.getElementsByName("fdKeepRtfStyle")[0].checked = returnValue["fdKeepRtfStyle"];
	document.getElementsByName("fdColumns")[0].value = returnValue["fdColumns"];
	//document.getElementsByName("checkIdValues")[0].value = selectedData.join("|");
	if(!confirm('<bean:message bundle="sys-search" key="search.export.confirm" />')){
		return;
	}
	exportForm.action = exportDialogObj.exportUrl;
	exportForm.submit();
}
</script>
<div style="display:none">
	<form name="exportForm" action="" method="POST">	
		<input type="hidden" name="fdColumns" />
		<input name="fdNum" class="inputsgl" style="width:35px" />
		<input name="fdNumStart" class="inputsgl" style="width:35px" />
		<input name="fdNumEnd" class="inputsgl" style="width:35px" />
		<input type="checkbox" value="true" name="fdKeepRtfStyle" checked="checked"/>
		<input name="checkIdValues" class="inputsgl" />
	</form>
</div>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/search/search.do?method=export&fdModelName=${param.fdModelName}">
		<input type=button value="<bean:message key="button.export"/>" onclick="showExportDialog();">
	</kmss:auth>
</div>
	<p class="txttitle"><c:out value="${searchResultInfo.title}"/></p>
	<% if (((Page)request.getAttribute("queryPage")).getTotalrows()==0){ %>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
	<% }else{ %>
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="40pt"><bean:message key="page.serial"/></td>
				<c:forEach items="${searchResultInfo.columns}" var="searchResultColumn">
					<c:if test="${searchResultColumn.calculated}">
						<td>${searchResultColumn.label}</td>
					</c:if>
					<c:if test="${!searchResultColumn.calculated}">
						<sunbor:column property="${searchResultColumn.name}">
							${searchResultColumn.label}
						</sunbor:column>
					</c:if>
				</c:forEach>
				<td>
					<bean:message bundle="km-review" key="sysWfNode.processingNode.currentProcess" />
				</td>
				<td>
					<bean:message bundle="km-review" key="sysWfNode.processingNode.currentProcessor" />
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="resultModel" varStatus="vstatus">
			<%
				Object resultModel = pageContext.getAttribute("resultModel");
				pageContext.setAttribute("modelURL", ModelUtil.getModelUrl(resultModel));
				SearchResult searchResult = (SearchResult) request.getAttribute("searchResultInfo");
			%>
			<c:forEach items="<%=searchResult.getColumnRowIter(resultModel) %>" var="columnRow" varStatus="colVstatus">
			<tr kmss_href="<c:url value="${modelURL}"/>">
				<c:if test="${colVstatus.index == 0 }">
				<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">${vstatus.index+1}</td>
				</c:if>
				<c:forEach items="${columnRow.columns}" var="searchResultColumn">
					<c:if test="${colVstatus.index == 0 or searchResultColumn.rowSpan == 1}">
					<td rowspan="${searchResultColumn.rowSpan }"><c:out value="${searchResultColumn.propertyValue}"/></td>
					</c:if>
				</c:forEach>
				<c:if test="${colVstatus.index == 0 }">
				<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">
					<kmss:showWfPropertyValues idValue="${resultModel.fdId}" propertyName="nodeName" />
				</td>
				<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">
					<kmss:showWfPropertyValues idValue="${resultModel.fdId}" propertyName="handlerName" />
				</td>
				</c:if>
			</tr>
			</c:forEach>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
<% } %>
<%@ include file="/resource/jsp/list_down.jsp"%>
</c:if>