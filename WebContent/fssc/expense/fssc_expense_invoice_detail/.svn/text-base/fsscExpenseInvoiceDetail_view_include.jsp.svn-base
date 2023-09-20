<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseInvoiceDetail') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdInvoiceList_Form" align="center" >
    <tr align="center" class="tr_normal_title">
        <td width="3%">
            ${lfn:message('page.serial')}
        </td>
         <td width="9%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}
        </td>
        <td width="7%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}
        </td>
        <td width="7%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}
        </td>
        <td width="11%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode')}
        </td>
		<td width="12%">
			${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName')}
		</td>
        <td width="11%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber')}
        </td>
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}
        </td>
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}
        </td>
        <td width="5%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}
        </td>
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}
        </td>
        <kmss:ifModuleExist path="/fssc/ledger">
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdState')}
        </td>
        </kmss:ifModuleExist>
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckStatus')}
        </td>
		<td width="6%">
			${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdIsCurrent')}
		</td>
    </tr>
    <kmss:ifModuleExist path="/fssc/ledger/"><c:set value="true" var="ledgerExist"></c:set></kmss:ifModuleExist>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td>
        	<%-- 发票类型--%>
            <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceType" _xform_type="select">
            	<div id="fdInvoiceList_Form[!{index}].fdInvoiceType">
               		<xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceTypeName" showStatus="readOnly" style="color:#333;"></xform:text>
               	</div>
		        <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceType" showStatus="noShow"></xform:text>
                <xform:text property="fdInvoiceList_Form[!{index}].fdCompanyId" showStatus="noShow"/>
                <xform:text property="fdInvoiceList_Form[!{index}].fdExpenseTypeId" showStatus="noShow"/>
                <xform:text property="fdInvoiceList_Form[!{index}].fdExpenseTypeName" showStatus="noShow"/>
               <%--  是否可抵扣 --%>
                <xform:text property="fdInvoiceList_Form[!{index}].fdIsVat" showStatus="noShow"/>
                <xform:text property="fdInvoiceList_Form[!{index}].fdTax" showStatus="noShow"/>
                <%--  验真状态 --%>
                <xform:text property="fdInvoiceList_Form[!{index}].fdCheckStatus" showStatus="noShow"/>
                <%--  发票状态 --%>
                <xform:text property="fdInvoiceList_Form[!{index}].fdState" showStatus="noShow"/>
                <xform:text property="fdInvoiceList_Form[!{index}].fdIsCurrent" showStatus="noShow"/>
            </div>
        </td>
        <td align="center">
        	<input type="hidden" name="fdInvoiceList_Form[!{index}].fdId" value="" disabled="true" />
        	<div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceNumber" _xform_type="text">
            	<xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceNumber" showStatus="readOnly" style="width:85%;color:#333;"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceCode" _xform_type="text" class="vat">
                <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode') }" showStatus="readOnly" style="width:85%;color:#333;" />
            </div>
        </td>
         <td align="center">
	            <div id="_xform_fdInvoiceList_Form[!{index}].fdCheckCode" _xform_type="text">
	                <xform:text property="fdInvoiceList_Form[!{index}].fdCheckCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode') }" showStatus="readOnly" style="width:95%;color:#333;" />
	            </div>
        </td>
		<td align="center">
			<div id="_xform_fdInvoiceList_Form[!{index}].fdPurchName" _xform_type="text">
				<xform:text property="fdInvoiceList_Form[!{index}].fdPurchName" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName') }" showStatus="readOnly" style="width:95%;color:#333;" />
			</div>
		</td>
		<td align="center">
	            <div id="_xform_fdInvoiceList_Form[!{index}].fdTaxNumber" _xform_type="text">
	                <xform:text property="fdInvoiceList_Form[!{index}].fdTaxNumber" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber') }" showStatus="readOnly" style="width:95%;color:#333;" />
	            </div>
        </td>
        <td align="center">
            <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceDate" _xform_type="text" class="vat">
                <xform:datetime dateTimeType="date" property="fdInvoiceList_Form[!{index}].fdInvoiceDate" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate') }" showStatus="readOnly" style="width:85%;color:#333;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceMoney" _xform_type="text" class="vat">
                <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceMoney" validators="currency-dollar" onValueChange="FSSC_GetTaxMoney" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney') }" showStatus="readOnly" style="width:80%;color:#333;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdInvoiceList_Form[!{index}].fdTaxMoney" _xform_type="text" class="vat">
                <xform:text property="fdInvoiceList_Form[!{index}].fdTaxMoney" validators="currency-dollar checkInvoiceMoney" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney') }" showStatus="readOnly" style="width:80%;color:#333;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdInvoiceList_Form[!{index}].fdNotTaxMoney" _xform_type="text" class="vat">
                <xform:text property="fdInvoiceList_Form[!{index}].fdNoTaxMoney" validators="currency-dollar checkInvoiceMoney" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney') }" showStatus="readOnly" style="width:80%;color:#333;" />
            </div>
        </td>
        <td align="center">
        	
        </td>
        <td align="center">

        </td>
		<td align="center">
			<div id="_xform_fdInvoiceList_Form[!{index}].fdIsCurrent" _xform_type="text" class="vat">
				<span></span>
				<xform:text property="fdInvoiceList_Form[!{index}].fdIsCurrent" showStatus="noShow" style="width:80%;color:#333;" />
			</div>
		</td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdInvoiceList_Form}" var="fdInvoiceList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td>
	        	<%-- 发票类型--%>
	            <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" _xform_type="select">
	            	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
		                <xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'">
		                    <xform:enumsDataSource enumsType="fssc_invoice_type" />
		                </xform:select>
	                </c:if>
	            	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
		                <div id="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType">
		                <xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'">
		                    <xform:enumsDataSource enumsType="fssc_invoice_type" />
		                </xform:select>
		                </div>
		                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" showStatus="noShow"></xform:text>
	                </c:if>
	                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId" showStatus="noShow"></xform:text>
	                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeName" showStatus="noShow"></xform:text>
	            	<input type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdId" value="${fdInvoiceList_FormItem.fdId }">
            		<input type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCompanyId" value="${fdInvoiceList_FormItem.fdCompanyId }">
            		<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdIsVat" showStatus="noShow"/>
            		<input type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTax" value="${fdInvoiceList_FormItem.fdTax }">
	            </div>	       
	         </td>
            <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" showStatus="readOnly" style="width:85%;color:#333;" />
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" showStatus="readOnly" style="width:85%;color:#333;" />
	                </c:if>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	</c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" onValueChange="FSSC_Invoice" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode') }" showStatus="readOnly" style="width:85%;color:#333;ime-mode:disabled;" />
	            	</c:if>
	            </div>
	        </td>
			<td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdCheckCode" _xform_type="text">
	            	<c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdCheckCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	</c:if>
	                <c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdCheckCode"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode') }" showStatus="readOnly" style="width:85%;color:#333;ime-mode:disabled;" />
	            	</c:if>
	            </div>
        	</td>
			<td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdPurchName" _xform_type="text">
	            	<c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdPurchName" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	</c:if>
	                <c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdPurchName"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName') }" showStatus="readOnly" style="width:85%;color:#333;ime-mode:disabled;" />
	            	</c:if>
	            </div>
        	</td>
			<td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" _xform_type="text">
	            	<c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	</c:if>
	                <c:if test="${fsscPaymentMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTaxNumber"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber') }" showStatus="readOnly" style="width:85%;color:#333;ime-mode:disabled;" />
	            	</c:if>
	            </div>
        	</td>

	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<xform:datetime dateTimeType="date" property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate') }" style="width:85%" />
	            	</c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
		            	<xform:datetime dateTimeType="date" property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	</c:if>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text">
	            	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<kmss:showNumber value="${fdInvoiceList_FormItem.fdInvoiceMoney }" pattern="0.00"/>
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<input name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" validate="required" value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdInvoiceMoney}" pattern="0.00"></kmss:showNumber>" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney') }" onchange="FSSC_ChangeInvoiceMoney()" class="inputsgl class1" validate="currency-dollar min(0)" readonly="readonly" style="width:80%;color:#333;"/>
                	</c:if>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<kmss:showNumber value="${fdInvoiceList_FormItem.fdTaxMoney }" pattern="0.00"/>
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<input name="fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdTaxMoney}" pattern="0.00"></kmss:showNumber>" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}" onchange="FSSC_ChangeTaxMoney()" class="inputsgl class1" validate="currency-dollar min(0)" readonly="readonly" style="width:80%;color:#333;"/>
                	</c:if>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdNotTaxMoney" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                	<kmss:showNumber value="${fdInvoiceList_FormItem.fdNoTaxMoney }" pattern="0.00"/>
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                	<input name="fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney" value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdNoTaxMoney}" pattern="0.00" />" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}" onchange="FSSC_ChangeNoTaxMoney()" class="inputsgl class1" validate="currency-dollar min(0)" readonly="readonly" style="width:80%;color:#333;"/>
                	</c:if>
	            </div>
	        </td>
	        <kmss:ifModuleExist path="/fssc/ledger">
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdState" _xform_type="text">
	                <sunbor:enumsShow enumsType="fssc_invoice_state_status" value="${fdInvoiceList_FormItem.fdState }"></sunbor:enumsShow>
	            </div>
	        </td>
	        </kmss:ifModuleExist>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdCheckStatus" _xform_type="text">
	                <sunbor:enumsShow enumsType="fssc_invoice_check_status" value="${fdInvoiceList_FormItem.fdCheckStatus }"></sunbor:enumsShow>
	                <!--  若是发票未验真且有权限，且发票类型为则出现验真按钮 -->
	                <kmss:ifModuleExist path="/fssc/baiwang">
		            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.checkInvoice eq 'true' and (empty fdInvoiceList_FormItem.fdCheckStatus or fdInvoiceList_FormItem.fdCheckStatus eq '0') and (fdInvoiceList_FormItem.fdInvoiceType eq '10100' or fdInvoiceList_FormItem.fdInvoiceType eq '10101' or fdInvoiceList_FormItem.fdInvoiceType eq '10102' or fdInvoiceList_FormItem.fdInvoiceType eq '10103' or fdInvoiceList_FormItem.fdInvoiceType eq '30100')}">
		            	<ui:button text="${lfn:message('fssc-expense:button.check')}" onclick="checkInvoice();"></ui:button>
		            </c:if>
		            </kmss:ifModuleExist>
	            </div>
	        </td>
			<td align="center">
				<div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdIsCurrent" _xform_type="text" class="vat">
					<c:if test="${fdInvoiceList_FormItem.fdIsCurrent=='1'}">
						<span>${lfn:message('message.yes') }</span>
					</c:if>
					<c:if test="${fdInvoiceList_FormItem.fdIsCurrent=='0'}">
						<span>${lfn:message('message.no') }</span>
					</c:if>
					<c:if test="${empty fdInvoiceList_FormItem.fdIsCurrent}">
						<span></span>
					</c:if>
					<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdIsCurrent" subject="${lfn:message('fssc-payment:fsscPaymentInvoiceDetail.fdIsCurrent') }" showStatus="noShow" style="width:95%;color:#333;" />
				</div>
			</td>
        </tr>
    </c:forEach>
</table>
<script>
Com_IncludeFile("doclist.js");
</script>
<script>
DocList_Info.push('TABLE_DocList_fdInvoiceList_Form');
DocListFunc_Init();
</script>
</ui:content>
<script>
seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
	//编辑时加载必填项
	$(function(){
		$("#TABLE_DocList_fdInvoiceList_Form tr:gt(0)").each(function(){
			var fdIsVat = $(this).find("[name$=fdIsVat]:checked").val();
			if(fdIsVat=='true'){
				$(this).find(".txtstrong").show();
				var validate = $(this).find("input[type=text]").attr("validate");
				$(this).find("input[type=text]").attr("validate",validate+" required");
				var validate2 = $(this).find(".class1").attr("validate");
				$(this).find(".class1").attr("validate",validate2+" required");
			}
		});
	});
	
})
</script>
<br/>
