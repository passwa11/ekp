<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseDidiDetail') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdDidiDetail" >
    <tr align="center" class="tr_normal_title">
        <td width="5%">
            ${lfn:message('page.serial')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdPassenger')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndPlace')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdStartTime')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndTime')}
        </td>
        <td width="15%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdCarLevel')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdMoney')}
        </td>
        <td width="5%">
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td align="center">
        	<div id="_xform_fdDidiDetail_Form[!{index}].fdPayWay" _xform_type="text">
              <xform:text property="fdDidiDetail_Form[!{index}].fdPassenger" showStatus="readOnly"/>
        	</div>
        	<xform:text property="fdDidiDetail_Form[!{index}].fdId" showStatus="noShow"/>
        	<xform:text property="fdDidiDetail_Form[!{index}].docMainId" showStatus="noShow"/>
        </td>
        <td align="center"> 
            <div id="_xform_fdDidiDetail_Form[!{index}].fdAccountId" _xform_type="dialog">
                <xform:text property="fdDidiDetail_Form[!{index}].fdStartPlace" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[!{index}].fdBankName" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[!{index}].fdEndPlace" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[!{index}].fdBankAccount" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[!{index}].fdStartTime" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
        	<div id="_xform_fdDidiDetail_Form[!{index}].fdBankAccountNo" _xform_type="text">
               <xform:text property="fdDidiDetail_Form[!{index}].fdEndTime" showStatus="readOnly"/>
           	</div>
         </td>
	 	 <td  align="center">
	 	 	<xform:text property="fdDidiDetail_Form[!{index}].fdCarLevel" showStatus="readOnly"/>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[!{index}].fdMoney" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[!{index}].fdMoney" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdDidiDetail_Form}" var="fdDidiDetail_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
        	<div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdPayWay" _xform_type="text">
              <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdPassenger" showStatus="readOnly"/>
        	</div>
        	<xform:text property="fdDidiDetail_Form[${vstatus.index}].fdId" showStatus="noShow"/>
        	<xform:text property="fdDidiDetail_Form[${vstatus.index}].docMainId" showStatus="noShow"/>
        </td>
        <td align="center"> 
            <div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
                <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdStartPlace" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdBankName" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdEndPlace" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdStartTime" showStatus="readOnly"/>
            </div>
        </td>
        <td align="center">
        	<div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdBankAccountNo" _xform_type="text">
               <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdEndTime" showStatus="readOnly"/>
           	</div>
         </td>
	 	 <td  align="center">
	 	 	<xform:text property="fdDidiDetail_Form[${vstatus.index}].fdCarLevel" showStatus="readOnly"/>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdMoney" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdMoney" showStatus="readOnly"/>
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
<input name="fdDidiDetailId" type="hidden"/>
<input name="fdDidiDetailName" type="hidden" subject="${lfn:message('fssc-expense:table.fsscExpenseDidiDetail') }"/>
</ui:content>
