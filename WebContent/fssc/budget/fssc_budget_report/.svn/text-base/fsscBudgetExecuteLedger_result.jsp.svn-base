<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	text-align: center;
}

table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: black!important;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff; 
}
</style>
<head>
    <script>
        if(window.parent.document.getElementsByClassName("lui_mask_l")){
            var index=window.parent.document.getElementsByClassName("lui_mask_l").length;
            for(var i=0;i<index;i++){
                window.parent.document.getElementsByClassName("lui_mask_l")[i].style.display="none";
                window.parent.document.getElementsByClassName("lui_dialog_main")[i].style.display="none";
            }
        }
    </script>
</head>
<html:form action="/fssc/budget/fssc_budget_data/fsscBudgetData.do">
    <c:if test="${queryPage == null || queryPage.totalrows == 0}">
        <%@ include file="/resource/jsp/list_norecord.jsp"%>
    </c:if>
    <c:if test="${queryPage.totalrows > 0}">
        <c:set var="totalMoney" value="0"/>
        <div id="detailDiv" style="overflow:auto;width:1000px;" >
    <table class="gridtable" width="1300px;" align="center" tbdraggable="true">
            <tr>
                <sunbor:columnHead htmlTag="td">
                    <th  style="width: 25px;">${lfn:message('page.serial')}</th>
                   <c:forEach items="${ledgerTitle}" var="title" varStatus="status">
                   		<th>${title}</th>
                   </c:forEach>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdYear')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdQuarter')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdMonth')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdTotalMoney')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdMoney')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdAlreadyUsedMoney')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdOccupyMoney')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdAdjustMoney')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdCanUseMoney')}</th>
                  <th>${lfn:message('fssc-budget:fsscBudgetData.fdElasticPercent')}</th>
                   <th>${lfn:message('fssc-budget:fsscBudgetData.fdBudgetStatus')}</th>
                </sunbor:columnHead>
            </tr>
            <c:forEach items="${queryPage.list}" var="budgetData" varStatus="vstatus">
                <tr kmss_href="<c:url value="/fssc/budget/fssc_budget_data/fsscBudgetData.do" />?method=view&fdId=${budgetData[0]}">
                    <td>${vstatus.index+1}</td>
                    <c:forEach items="${ledgerTitle}" var="title"  varStatus="status">
                   		<td>${budgetData[status.index+1]}</td>
                   </c:forEach>
                   <c:set value="${fn:length(ledgerTitle)}" var="len"></c:set>
                   <td>${budgetData[len+1]}</td>
                   <td>
                   		<c:if test="${not empty  budgetData[len+2]}">
                   			${budgetData[len+2]}${lfn:message('fssc-budget:enums.budget.period.type.quarter')}
                   		</c:if>
                   </td>
                   <td>
                   		<c:if test="${not empty  budgetData[len+3]}">
                   			${budgetData[len+3]}${lfn:message('fssc-budget:enums.budget.period.type.report.month')}
                   		</c:if>
                   </td>
                   <c:forEach var="i" begin="4" end="8">
                   <td>
                    		<kmss:showNumber value="${budgetData[len+i]}" pattern="###,##0.00"></kmss:showNumber>
  				 </td>
				</c:forEach>
				<c:set var="num" value="8"></c:set>
					<c:if test="${empty fdBudgetWarn}">
                       <td><kmss:showNumber value="${budgetData[len+num]}" pattern="###,##0.00"></kmss:showNumber></td>
                   </c:if>
                   <c:if test="${not empty fdBudgetWarn}">
                       <c:choose>
                           <c:when test="${budgetData[len+num+1]>0}">
                               <td><kmss:showNumber value="${budgetData[len+num+1]}" pattern="###,##0.00"></kmss:showNumber></td>
                           </c:when>
                           <c:otherwise>
                               <td style="background-color:#FFFF00;"><span style="color:#000;"><kmss:showNumber value="0" pattern="###,##0.00">0</kmss:showNumber></span></td>
                           </c:otherwise>
                       </c:choose>
                   </c:if>
						<td>
						<c:if test="${empty  budgetData[len+num+2]}">0</c:if> 
        				<c:if test="${not empty  budgetData[len+num+2]}">${budgetData[len+num+2]}</c:if>
        				%</td>
				<td>
                 		<sunbor:enumsShow enumsType="fssc_budget_status" value="${budgetData[len+num+3]}"></sunbor:enumsShow>
                   </td>
                </tr>
            </c:forEach>
        </table>
        </div>
        <%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
    </c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
<script>
function setIframeHeight(){
	var main = window.parent.document.getElementById("searchIframe").parentNode.style.width='1050px';
	console.log('main'+main);
	console.log(233);
}
setIframeHeight();
</script>