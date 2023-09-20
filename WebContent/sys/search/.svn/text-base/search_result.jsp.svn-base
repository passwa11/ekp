<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.sys.search.web.SearchConditionEntry" %>
<%@ page import="com.landray.kmss.util.ModelUtil"%>
<%@ page import="com.landray.kmss.sys.search.web.SearchResultColumn"%>
<%@ page import="com.landray.kmss.sys.search.web.SearchResult"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.common.actions.*" %>
<%@ page import="com.sunbor.web.tag.Page" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/edit.jsp" sidebar="auto">
	<template:replace name="head">
    	<link href="${KMSS_Parameter_ResPath}style/common/list/list.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
		<script>
			if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
				Com_Parameter.__dataInitlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
		</script>
		<script type="text/javascript">
			Com_IncludeFile("optbar.js|list.js");
			function List_CheckSelect(checkName){
				if(checkName==null)
					checkName = List_TBInfo[0].checkName;
				var obj = document.getElementsByName("List_Selected");
				for(var i=0; i<obj.length; i++)
					if(obj[i].checked)
						return true;
				alert("<bean:message key="page.noSelect"/>");
				return false;
			}
			function List_ConfirmDel(checkName){
				return List_CheckSelect(checkName) && confirm("<bean:message key="page.comfirmDelete"/>");
			}
		</script>
		
		<script type="text/javascript">
			var exportDialogObj = {exportUrl: "${lfn:escapeJs(exportURL)}", exportNum: "${queryPage.totalrows}"};
			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				showExportDialog = function() {
					var selectedData = getSelectIndexs();
					var hasSelected = selectedData.length > 0 ? true : false;
					var url = '/sys/search/search_result_export.jsp?fdModelName=${JsParam.fdModelName}&searchId=${JsParam.searchId}&hasSelected=' + hasSelected;
					dialog.iframe(url, '<bean:message bundle="sys-search" key="search.export.select.column" />',
							function (value) {
		                    	exportCallbak(value);
							},
							{width:460,height:500,params:{exportDialogObj:exportDialogObj}}
					);
					
				}
				
				exportCallbak = function(returnValue) {
					if(returnValue==null || returnValue==undefined){
						return;  
					}
					var selectedData = getSelectIndexs();
					document.getElementsByName("fdNum")[0].value = ${queryPage.totalrows};
					document.getElementsByName("fdNumStart")[0].value = returnValue["fdNumStart"];
					document.getElementsByName("fdNumEnd")[0].value = returnValue["fdNumEnd"];
					document.getElementsByName("fdKeepRtfStyle")[0].checked = returnValue["fdKeepRtfStyle"];
					document.getElementsByName("fdColumns")[0].value = returnValue["fdColumns"];
					document.getElementsByName("checkIdValues")[0].value = selectedData.join("|");
					dialog.confirm('<bean:message bundle="sys-search" key="search.export.confirm" />',function(value){
						if(value == true) {
							exportForm.action = exportDialogObj.exportUrl;
							exportForm.submit();
						}
					});
				}
				
				getSelectIndexs = function() {
					var selectedData = [];
					var $table = $("#List_ViewTable");
					$table.find("[name='List_Selected']:checkbox").each(function(){	
						if (this.checked){
						    selectedData.push(this.value);
						}
					});
					return selectedData;
				}
				
				hid = function(obj) {
					if(obj.checked)
						obj.parentNode.parentNode.style.display= "none ";
				}
			});
			
			window.onload = function() {
				/* #127699 数据导出中，数据选择后，有部分数据显示未被选择中状态-开始 */
				$("#List_ViewTable>tbody>tr>td").mouseenter(function () {
					var href = $(this).parent("tr").attr("kmss_href");
					$(this).parent("tr").attr("same", "true");
					$(this).parent("tr").siblings().each(function () {
						if ($(this).attr("kmss_href") == href) {
							$(this).attr("same", "true");
						}
					})
				});

				$("#List_ViewTable>tbody>tr>td").mouseleave(function () {
					var href = $(this).parent("tr").attr("kmss_href");
					$(this).parent("tr").attr("same", "false");
					$(this).parent("tr").siblings().each(function () {
						if ($(this).attr("kmss_href") == href) {
							$(this).attr("same", "false");
						}
					})
				});
				/* #127699 数据导出中，数据选择后，有部分数据显示未被选择中状态-结束 */
			};
		</script>
	</template:replace>
	<%-- 标题 --%>
	<template:replace name="title">
		${searchResultInfo.title}
	</template:replace>
	<%-- 内容 --%>
	<template:replace name="content">
		<br style="font-size:5px">
		<div class="listtable_box" style="overflow-x:auto;" >
			<table width="98%" height="97%" cellspacing="0" border="0" cellpadding="0" align="center">
				<tr>
					<td height="100%" valign="top">
						<div id="search_content">
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
							<c:if test="${empty param.canClose or param.canClose}">
							   <input type="button" value="<bean:message key="button.close"/>"	onclick='Com_CloseWindow();'>
							</c:if>
						</div>
							<% if (((Page)request.getAttribute("queryPage")).getTotalrows()==0){ %>
							<%@ include file="/resource/jsp/list_norecord.jsp"%>
							<% }else{ %>
							<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
							<table id="List_ViewTable">
								<tr>
									<sunbor:columnHead htmlTag="td">
										<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
										<td width="60pt"><bean:message key="page.serial"/></td>
										<c:forEach items="${searchResultInfo.columns}" var="searchResultColumn">
										   <c:if test="${searchResultColumn.calculated || searchResultColumn.property.type == 'RTF'}">
										        <c:choose>
											         <c:when test="${searchResultColumn.property.name == 'docSubject' or searchResultColumn.property.name == 'fdName'}">
											           <td style="min-width:220px">
											        </c:when>
											        <c:otherwise>
											          <td style="min-width:80px">
											        </c:otherwise>
										        </c:choose>
										          ${searchResultColumn.label}</td>
											</c:if>
											<c:if test="${!searchResultColumn.calculated && searchResultColumn.property.type != 'RTF'}">
												<sunbor:column property="${searchResultColumn.name}">
												   <c:choose>
											         <c:when test="${searchResultColumn.property.name == 'docSubject' or searchResultColumn.property.name == 'fdName'}">
											             <div style="min-width:220px">
											        </c:when>
											        <c:otherwise>
											            <div style="min-width:80px">
											        </c:otherwise>
										          </c:choose>
										            ${searchResultColumn.label}</div>
												</sunbor:column>
											</c:if>
										</c:forEach>
										<c:if test="${isWfMain}">
										<td style="min-width:80px"><bean:message  bundle="sys-search" key="sysSearchMain.wfNodeName"/></td>
										<td style="min-width:80px"><bean:message  bundle="sys-search" key="sysSearchMain.wfHandlerName"/></td>
										</c:if>
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
										<td style="width:10px" rowspan="<%=searchResult.getColumnRowMaxSize() %>"><input type="checkbox" name="List_Selected" value="${resultModel.fdId}"></td>
										<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">${vstatus.index+1}</td>
										</c:if>
										<c:forEach items="${columnRow.columns}" var="searchResultColumn">
											<c:if test="${colVstatus.index == 0 or searchResultColumn.rowSpan == 1}">
											<td rowspan="${searchResultColumn.rowSpan }">${searchResultColumn.propertyValue}</td>
											</c:if>
										</c:forEach>
										<c:if test="${isWfMain and colVstatus.index == 0}">
										<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">
											<kmss:showWfPropertyValues  var="nodevalue" idValue="${resultModel.fdId}" propertyName="nodeName" />
										    <c:out value="${nodevalue}"></c:out>
										</td>
										<td rowspan="<%=searchResult.getColumnRowMaxSize() %>">
											<kmss:showWfPropertyValues  var="handlerValue" idValue="${resultModel.fdId}" propertyName="handlerName" />
									        <c:out value="${handlerValue}"></c:out>
										</td>
										</c:if>
									</tr>
									</c:forEach>
								</c:forEach>
							</table>
							<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
						<% } %>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>