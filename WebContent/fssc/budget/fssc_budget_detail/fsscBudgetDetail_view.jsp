<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
	<div id="detailDiv" style="overflow:auto;width:100%;" >
    <table class="tb_normal" width="80%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
        <tr align="center" class="tr_normal_title">
            <td style="width:40px;">
                ${lfn:message('page.serial')}
            </td>
            <budget:showBudgetDetail type="title" fdCompanyId="${fsscBudgetMainForm.fdCompanyId}" fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}"></budget:showBudgetDetail>
        </tr>
        <c:forEach items="${fsscBudgetMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
            <tr KMSS_IsContentRow="1">
                <td align="center">
                    ${vstatus.index+1}
                </td>
                <budget:showBudgetDetail type="contentline" fdCompanyId="${fsscBudgetMainForm.fdCompanyId}" fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budget:showBudgetDetail>
                <td align="center">
                    <div id="_xform_fdDetailList_Form[${vstatus.index}].fdElasticPercent" _xform_type="text">
                    	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
                    	<c:if test="${not empty fdDetailList_FormItem.fdElasticPercent}">
                        <xform:text property="fdDetailList_Form[${vstatus.index}].fdElasticPercent" showStatus="view" />%
                         </c:if>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <input type="hidden" name="fdDetailList_Flag" value="1">
