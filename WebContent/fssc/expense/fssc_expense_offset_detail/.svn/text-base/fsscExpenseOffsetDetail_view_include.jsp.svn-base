<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseOffsetLoan') }" expand="true">
${lfn:message('fssc-expense:fsscExpenseOffsetLoan.isOffsetLoan')}
<xform:checkbox property="fdIsOffsetLoan" onValueChange="FSSC_ChangeIsOffsetLoan">
	<xform:enumsDataSource enumsType="fssc_expense_invoice_detail_fd_is_vat"/>
</xform:checkbox>
<div id="LoanTable" style="display:none;">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdOffsetList_Form" align="center">
    <tr align="center" class="tr_normal_title">
        <td style="width:40px;">
            ${lfn:message('page.serial')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdNumber')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}
        </td>
        <td>
            ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}
        </td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdOffsetList_Form}" var="fdOffsetList_FormItem" varStatus="vstatus">
        <c:if test="${(fdOffsetList_FormItem.fdOffsetMoney)!=null}">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
            	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdId" value="${fdOffsetList_FormItem.fdId}" />
            	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanId" value="${fdOffsetList_FormItem.fdLoanId}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].docSubject" value="${fdOffsetList_FormItem.docSubject}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdNumber" value="${fdOffsetList_FormItem.fdNumber}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanMoney" value="${fdOffsetList_FormItem.fdLoanMoney}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" value="${fdOffsetList_FormItem.fdCanOffsetMoney}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" value="${fdOffsetList_FormItem.fdOffsetMoney}" />
                <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLeftMoney" value="${fdOffsetList_FormItem.fdLeftMoney}" />
                <a target="_blank" href="<c:url value="/fssc/loan/fssc_loan_main/fsscLoanMain.do"/>?method=view&fdId=${fdOffsetList_FormItem.fdLoanId}">
	                <div style="color:#83C2EB" id="_xform_fdOffsetList_Form[${vstatus.index}].docSubject" _xform_type="text">
	                    <xform:text property="fdOffsetList_Form[${vstatus.index}].docSubject" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}" validators=" maxLength(200)" style="width:95%;" />
	                </div>
                </a>
            </td>
            <td align="center">
                <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdNumber" _xform_type="text">
                    <xform:text property="fdOffsetList_Form[${vstatus.index}].fdNumber" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdLoanMoney" _xform_type="text">
                    <kmss:showNumber value="${fdOffsetList_FormItem.fdLoanMoney }" pattern="0.00"/>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" _xform_type="text">
                    <kmss:showNumber value="${fdOffsetList_FormItem.fdCanOffsetMoney }" pattern="0.00"/>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" _xform_type="text">
                    <kmss:showNumber value="${fdOffsetList_FormItem.fdOffsetMoney }" pattern="0.00"/>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdLeftMoney" _xform_type="text">
                    <kmss:showNumber value="${fdOffsetList_FormItem.fdLeftMoney }" pattern="0.00"/>
                </div>
            </td>
        </tr>
        </c:if>
        <c:if test="${(fdOffsetList_FormItem.fdOffsetMoney)==null}">
            <tr KMSS_IsContentRow="1" style="display: none;">
                <td align="center">
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdId" value="${fdOffsetList_FormItem.fdId}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanId" value="${fdOffsetList_FormItem.fdLoanId}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].docSubject" value="${fdOffsetList_FormItem.docSubject}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdNumber" value="${fdOffsetList_FormItem.fdNumber}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanMoney" value="${fdOffsetList_FormItem.fdLoanMoney}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" value="${fdOffsetList_FormItem.fdCanOffsetMoney}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" value="${fdOffsetList_FormItem.fdOffsetMoney}" />
                    <input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLeftMoney" value="${fdOffsetList_FormItem.fdLeftMoney}" />
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</div>
</ui:content>
<script>
  Com_IncludeFile("doclist.js");
  DocList_Info.push('TABLE_DocList_fdOffsetList_Form');
</script>
<script>
    seajs.use(['lui/dialog'],function(dialog){
    	Com_AddEventListener(window,'load',function(){
    		DocList_AddRow("TABLE_DocList_fdOffsetList_Form");
    	})
    })
</script>
<br/>
