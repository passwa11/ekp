<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="btn_container">
	<div class="fssc_expense_btn" onclick="FSSC_AddAdjustDetail()"><span class="iconfont icon-tianjia"></span>${lfn:message('fssc-budget:button.addDetail') }</div>
</div>
	 <div id="detailDiv" style="overflow:auto;width:1130px;" >
    <table class="tb_normal" width="100%;" id="TABLE_DocList_fdDetailList_Form"  align="center" tbdraggable="true">
        <tr align="center" class="tr_normal_title">
            <td style="width:20px;"></td>
            <td style="width:40px;">
                ${lfn:message('page.serial')}
            </td>
            <budget:showBudgetAdjustDetail type="title"  adjustType="${adjustType}" fdSchemeId="${fsscBudgetAdjustMainForm.fdBudgetSchemeId}"></budget:showBudgetAdjustDetail>
            <td style="width:80px;">
            </td>
        </tr>
        <tr KMSS_IsReferRow="1" style="display:none;">
            <td align="center">
                <input type='checkbox' name='DocList_Selected' />
                <input type="hidden" name="fdDetailList_Form[!{index}].fdId" value="" disabled="true" />
                <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetInfo" value="" />
            </td>
            <td align="center" KMSS_IsRowIndex="1">
                !{index}
            </td>
            <budget:showBudgetAdjustDetail type="datumline" adjustType="${adjustType}"  fdSchemeId="${fsscBudgetAdjustMainForm.fdBudgetSchemeId}"></budget:showBudgetAdjustDetail>
            <td align="center">
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
        <c:forEach items="${fsscBudgetAdjustMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
            <tr KMSS_IsContentRow="1">
                <td align="center">
                    <input type="checkbox" name="DocList_Selected" />
                    <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
                    <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo}" />
                </td>
                <td align="center">
                    ${vstatus.index+1}
                </td>
             	<budget:showBudgetAdjustDetail adjustType="${adjustType}" type="contentline" fdSchemeId="${fsscBudgetAdjustMainForm.fdBudgetSchemeId}" detailForm="${fdDetailList_FormItem}" method="edit" tdIndex="${vstatus.index}"></budget:showBudgetAdjustDetail>
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
