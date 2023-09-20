<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
	<div id="detailDiv" style="overflow:auto;" >
    <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
        <tr align="center" class="tr_normal_title">
            <td style="width:40px;">
                ${lfn:message('page.serial')}
            </td>
            <budget:showBudgetAdjustDetail type="title" adjustType="${adjustType}" fdSchemeId="${fsscBudgetAdjustMainForm.fdBudgetSchemeId}"></budget:showBudgetAdjustDetail>
        </tr>
        <c:forEach items="${fsscBudgetAdjustMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
            <tr KMSS_IsContentRow="1">
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo}" />
                <td align="center">
                    ${vstatus.index+1}
                </td>
                <budget:showBudgetAdjustDetail adjustType="${adjustType}" type="contentline" fdSchemeId="${fsscBudgetAdjustMainForm.fdBudgetSchemeId}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budget:showBudgetAdjustDetail>
            </tr>
        </c:forEach>
    </table>
    </div>
    <input type="hidden" name="fdDetailList_Flag" value="1">
