<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <c:forEach items="${queryList}" var="fdDetailList_FormItem" varStatus="vstatus">
        <tr>
            <td align="center">
                ${vstatus.index+1}
                <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdApprovedApplyMoney" value="${fdDetailList_FormItem.fdApprovedApplyMoney }" />
            	<input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdApprovedStandardMoney" value="${fdDetailList_FormItem.fdApprovedStandardMoney }" />
                <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId }" />
                    <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdCompanyId" value="${fdDetailList_FormItem.fdCompanyId }" />
                    <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdCompanyName" value="${fdDetailList_FormItem.fdCompanyName }" />
                    <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdCostCenterId" value="${fdDetailList_FormItem.fdCostCenterId }" />
                    <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdCurrencyId" value="${fdDetailList_FormItem.fdCurrencyId }" />
                    <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdCurrencyName" value="${fdDetailList_FormItem.fdCurrencyName }" />
	            <input type="hidden" name="fdDetailList_Form1[${vstatus.index}].fdExchangeRate" value="${fdDetailList_FormItem.fdExchangeRate }" />
            </td>
            <td align="center">
                ${fdDetailList_FormItem.fdCompanyName }
            </td>
            <td align="center">
                ${fdDetailList_FormItem.fdCostCenterName }
            </td>
            <td align="center">
	            ${fdDetailList_FormItem.fdExpenseItemName }
	        </td>
            <td align="center">
                ${fdDetailList_FormItem.fdRealUserName }
            </td>
            <td align="center">
	            ${fdDetailList_FormItem.fdHappenDate }
	        </td>
            <td align="center">
            	<kmss:showNumber value="${fdDetailList_FormItem.fdApplyMoney}" pattern="0.00"/>
            </td>
            <td align="center">
                ${fdDetailList_FormItem.fdCurrencyName }
            </td>
            <td align="center">
            	<kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney}" pattern="0.00"/>
            </td>
            <td align="center">
                ${fdDetailList_FormItem.fdUse }
            </td>
        </tr>
    </c:forEach>
