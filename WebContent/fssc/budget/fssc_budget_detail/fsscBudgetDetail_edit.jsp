<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="btn_container">
	<div class="fssc_expense_btn" onclick="FSSC_AddBudgetDetail()"><span class="iconfont icon-tianjia"></span>${lfn:message('fssc-budget:button.addDetail') }</div>
</div>
	<div id="detailDiv" style="overflow:auto;width:1130px;" >
    <table class="tb_normal" width="2200px;" id="TABLE_DocList_fdDetailList_Form"  align="center" tbdraggable="true">
        <tr align="center" class="tr_normal_title">
            <td style="width:20px;"></td>
            <td style="width:40px;">
                ${lfn:message('page.serial')}
            </td>
            <budget:showBudgetDetail type="title" fdCompanyId="${fsscBudgetMainForm.fdCompanyId}" fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}"></budget:showBudgetDetail>
            <td style="width:80px;">
            </td>
        </tr>
        <tr KMSS_IsReferRow="1" style="display:none;">
            <td align="center">
                <input type='checkbox' name='DocList_Selected' />
            </td>
            <td align="center" KMSS_IsRowIndex="1">
                !{index}
            </td>
            <budget:showBudgetDetail type="datumline" fdCompanyId="${fsscBudgetMainForm.fdCompanyId}" fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}"></budget:showBudgetDetail>
             <td align="center" style="display:none;">
                <div id="_xform_fdDetailList_Form[!{index}].fdElasticPercent" _xform_type="text">
                	<input type="hidden" name="fdDetailList_Form[!{index}].fdId" value="" disabled="true" />
                    <xform:text property="fdDetailList_Form[!{index}].fdElasticPercent" subject="${lfn:message('fssc-budget:fsscBudgetDetail.fdElasticPercent')}" showStatus="edit" style="width:95%;display:none;" />
                	<span style="display:none;">%</span>
                </div>
            </td>
            <td align="center">
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
        <c:forEach items="${fsscBudgetMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
            <tr KMSS_IsContentRow="1">
                <td align="center">
                    <input type="checkbox" name="DocList_Selected" />
                </td>
                <td align="center">
                    ${vstatus.index+1}
                </td>
              <budget:showBudgetDetail type="contentline" fdCompanyId="${fsscBudgetMainForm.fdCompanyId}" fdSchemeId="${fsscBudgetMainForm.fdBudgetSchemeId}" detailForm="${fdDetailList_FormItem}" method="edit" tdIndex="${vstatus.index}"></budget:showBudgetDetail>
                 <td align="center">
                    <div id="_xform_fdDetailList_Form[${vstatus.index}].fdElasticPercent" _xform_type="text">
                    	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
                        <xform:text property="fdDetailList_Form[${vstatus.index}].fdElasticPercent" subject="${lfn:message('fssc-budget:fsscBudgetDetail.fdElasticPercent')}" showStatus="edit" style="width:95%;" />
                    	%
                    </div>
                </td>
                <td align="center">
                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <input type="hidden" name="fdDetailList_Flag" value="1">
    <script>
        DocList_Info.push('TABLE_DocList_fdDetailList_Form');
    </script>
