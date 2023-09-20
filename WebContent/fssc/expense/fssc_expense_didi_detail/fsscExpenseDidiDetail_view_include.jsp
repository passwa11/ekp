<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseDidiDetail') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail" >
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
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdCarLevel')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdMoney')}
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
             <div  style="width:90%;">
              	<xform:text property="fdDidiDetail_Form[${vstatus.index}].fdCarLevel" showStatus="readOnly"/>
             </div>
        </td>
        <td align="center">
            <div id="_xform_fdDidiDetail_Form[${vstatus.index}].fdMoney" _xform_type="text">
                <xform:text property="fdDidiDetail_Form[${vstatus.index}].fdMoney" showStatus="readOnly"/>
            </div>
        </td>
        </tr>
    </c:forEach>
</table>
</ui:content>
