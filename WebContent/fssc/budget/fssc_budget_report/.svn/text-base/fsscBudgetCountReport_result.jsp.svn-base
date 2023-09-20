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
    <div id="detailDiv" style="overflow:auto;width:1000px;" >
    <table class="gridtable" style="width: 1300px" align="center" tbdraggable="true">
            <tr>
                <sunbor:columnHead htmlTag="td">
                    <th style="width: 25px;">${lfn:message('page.serial')}</th>
                    <th >${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}</th>
                    <th >上级科目</th>
                    <th >${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdMonth')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdTotalMoney')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdMoney')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdAlreadyUsedMoney')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdOccupyMoney')}</th>
                   <th >${lfn:message('fssc-budget:fsscBudgetData.fdAdjustMoney')}</th>
                   <th >可使用额度</th>
                </sunbor:columnHead>
            </tr>
            <c:forEach items="${queryPage.list}" var="budgetData" varStatus="vstatus">
                <tr>
                    <td>${vstatus.index+1}</td>
                   <c:set value="0" var="len"></c:set>
                    <td>${budgetData[len]}</td>
                   <td>${budgetData[len+1]}</td>
                   <td>${budgetData[len+2]}</td>
                   <td>${budgetData[len+3]}</td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+4]}" pattern="###,##0.00"></kmss:showNumber>
                   </td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+5]}" pattern="###,##0.00"></kmss:showNumber>
                   </td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+6]}" pattern="###,##0.00"></kmss:showNumber>
                   </td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+7]}" pattern="###,##0.00"></kmss:showNumber>
                   </td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+8]}" pattern="###,##0.00"></kmss:showNumber>
                   </td>
                   <td>
                 		<kmss:showNumber value="${budgetData[len+9]}" pattern="###,##0.00"></kmss:showNumber>
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