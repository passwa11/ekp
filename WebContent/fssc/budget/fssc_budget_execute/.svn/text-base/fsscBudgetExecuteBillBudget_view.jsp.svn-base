<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
    <template:replace name="head">
        <script>
            Com_AddEventListener(window,'load',function(){
                $("#top").hide();
                $(".tempTB").attr("style","width:100%; margin: 0px auto;");
            });
        </script>
    </template:replace>
    <template:replace name="content">
        <c:if test="${empty billBudget}">
            <div style="text-align: center;font-size:16px;">${lfn:message('fssc-budget:fssc.budget.bill.no.budget')}</div>
        </c:if>
        <c:if test="${not empty billBudget}">
        <table class="tb_normal" width="100%">
        <tr>
        	<td colspan="9">
	        	<div>
	        		<div style="text-align: center;">
		        	<div style="float:left;width:33%;"><div style="background-color: #9AFF9A;height:20px;width:45px;float:left;"></div><div style="float:left;">${lfn:message('fssc-budget:bill.budget.in')}</div></div>
		        	</div>
		        	<div style="float:left;width:33%;"><div style="background-color: #FF6347;height:20px;width:45px;float:left;"></div><div style="float:left;">${lfn:message('fssc-budget:bill.budget.out')}</div></div>
		        	<div style="float:left;width:33%;"><div style="background-color: #FFFF00;height:20px;width:45px;float:left;"></div><div style="float:left;">${lfn:message('fssc-budget:bill.budget.warn')}</div></div>
	        	</div>
        	</td>
        </tr>
        <tr>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.info')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.init.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.adjust.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.total.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.used.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.using.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.this.using.account')}
            </td>
            <td class="td_normal_title">
                ${lfn:message('fssc-budget:fssc.budget.bill.canUse.account')}
            </td>
        </tr>
        <c:forEach items="${billBudget}" var="budget">
            <c:choose>
                <c:when test="${(not empty fdBudgetWarn and budget.value['totalAmount']-budget.value['alreadyUsedAmount']-budget.value['occupyAmount']-budget.value['totalAmount']*(100-fdBudgetWarn)/100.0>0) or (empty fdBudgetWarn and budget.value['canUseAmount']>=0)}">
                    <tr style="background-color: #9AFF9A;cursor:pointer;" onclick="window.open('${LUI_ContextPath}/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=view&fdId=${budget.key}','_blank');">
                </c:when>
                <c:when test="${budget.value['canUseAmount']<0}">
                    <tr style="background-color: #FF6347;cursor:pointer;" onclick="window.open('${LUI_ContextPath}/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=view&fdId=${budget.key}','_blank');">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #FFFF00;cursor:pointer;" onclick="window.open('${LUI_ContextPath}/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=view&fdId=${budget.key}','_blank');">
                </c:otherwise>
            </c:choose>
                <td>
                    <input name="fdBudgetId" type="hidden" value="${budget.key}" />
                        ${budget.value['subject']}
                </td>
                <td>
                    <c:if test="${not empty budget.value['initAmount']}">
                        <kmss:showNumber value="${budget.value['initAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['adjustAmount']}">
                        <kmss:showNumber value="${budget.value['adjustAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['totalAmount']}">
                        <kmss:showNumber value="${budget.value['totalAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['alreadyUsedAmount']}">
                        <kmss:showNumber value="${budget.value['alreadyUsedAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['occupyAmount']}">
                        <kmss:showNumber value="${budget.value['occupyAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['thisOccupyAmount']}">
                        <kmss:showNumber value="${budget.value['thisOccupyAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty budget.value['canUseAmount']}">
                        <kmss:showNumber value="${budget.value['canUseAmount']}" pattern="###,##0.00" ></kmss:showNumber>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </table>
        </c:if>
    </template:replace>
</template:include>