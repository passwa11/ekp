<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseInvoiceDetail') }" expand="true" >
<table class="tb_normal" width="100%" id="TABLE_DocList_fdInvoiceList_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td width="3%">
            ${lfn:message('page.serial')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}
        </td>
        <td width="8%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}
        </td>
        <td width="8%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}
        </td>
        <td width="13%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode')}
        </td>
		<td width="13%">
				${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName')}
		</td>
		<td width="13%">
				${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber')}
		</td>
        <td width="8%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}
        </td>
        <td width="7%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}
        </td>
        <td width="6%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}
        </td>
        <td width="7%">
            ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}
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
            <div id="_xform_fdInvoiceType" _xform_type="select">
                <xform:select property="fdInvoiceList_Form[!{index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'" onValueChange="FSSC_ChangeIsVat" required="" showStatus="readOnly">
                    <xform:enumsDataSource enumsType="fssc_invoice_type" />
                </xform:select>
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
	            <div id="_xform_fdInvoiceType" _xform_type="select">
	            <c:if test="${empty fdInvoiceList_FormItem.fdInvoiceType}">
	                <select name="_fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" id="fdInvoiceType" style="ime-mode:disabled" disabled="">
						<option value=""></option>
						<option value="10100">增值税专用发票</option>
						<option value="10101">增值税普通发票</option>
						<option value="10102">增值税电子普通发票</option>
						<option value="10103">增值税普通发票(卷票)</option>
						<option value="10104">机动车销售统一发票</option>
						<option value="10105">二手车销售统一发票</option>
						<option value="10200">定额发票</option>
						<option value="10400">机打发票</option>
						<option value="10500">出租车发票</option>
						<option value="10503">火车票</option>
						<option value="10505">航空运输电子客票行程单</option>
						<option value="10507">过路费发票</option>
						<option value="10900">可报销其他发票</option>
						<option value="00000">其他</option>
						<option value="20100">国际小票</option>
						<option value="20105">滴滴出行行程单</option>
					</select>
					<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" showStatus="noShow"></xform:text>
				</c:if>
	            <c:if test="${not empty fdInvoiceList_FormItem.fdInvoiceType}">
	                <xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'" onValueChange="FSSC_ChangeIsVat" showStatus="view">
	                    <xform:enumsDataSource enumsType="fssc_invoice_type" />
	                </xform:select>
				</c:if>
				<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" value="${fdInvoiceList_FormItem.fdInvoiceType}" showStatus="noShow"  />
	            </div>
	        </td>
            <td align="center">
            	<div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" _xform_type="text">
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdId" showStatus="noShow"/>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdCompanyId" showStatus="noShow"/>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId" showStatus="noShow"/>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeName" showStatus="noShow"/>
	            	<%-- 是否可抵扣 --%>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdIsVat" showStatus="noShow"/>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTax" showStatus="noShow"/>
	            	<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" showStatus="readOnly" style="color:#333;" />
	            	<%--  验真状态 --%>
               		<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdCheckStatus" showStatus="noShow"/>
               		<%--  发票状态 --%>
               		<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdState" showStatus="noShow"/>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" _xform_type="text" class="vat">
	                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            </div>
	            <span class="txtstrong" style="display:none;">*</span>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdCheckCode" _xform_type="text">
	                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdCheckCode" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode') }" showStatus="readOnly" style="width:95%;color:#333;" />
	            </div>
        	</td>
			<td align="center">
				<div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdPurchName" _xform_type="text">
					<xform:text property="fdInvoiceList_Form[${vstatus.index}].fdPurchName" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName') }" showStatus="readOnly" style="width:95%;color:#333;" />
				</div>
			</td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" _xform_type="text">
	                <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber') }" showStatus="readOnly" style="width:95%;color:#333;" />
	            </div>
        	</td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" _xform_type="text" class="vat">
	                <xform:datetime dateTimeType="date" property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" subject="${lfn:message('fssc-payment:fsscPaymentInvoiceDetail.fdInvoiceDate') }" showStatus="readOnly" style="width:85%;color:#333;" />
	            	<span class="txtstrong" style="display:none;">*</span>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text" class="vat">
	                 <input type="text" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdInvoiceMoney }" pattern="0.00" />"  validate="currency-dollar"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney') }" class="inputsgl" readonly="readonly" style="width:80%;color:#333;" />
	            </div>
	            <span class="txtstrong" style="display:none;">*</span>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" _xform_type="text" class="vat">
	                <input type="text" name="fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdTaxMoney }" pattern="0.00" />"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney') }" class="inputsgl" readonly="readonly" style="width:80%;color:#333;" />
	            </div>
	            <span class="txtstrong" style="display:none;">*</span>
	        </td>
	        <td align="center">
	            <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdNotTaxMoney" _xform_type="text" class="vat">
	               <input type="text" name="fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney"  value="<kmss:showNumber value="${fdInvoiceList_FormItem.fdNoTaxMoney }" pattern="0.00" />" validate="currency-dollar checkInvoiceMoney"  subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney') }" class="inputsgl" readonly="readonly" style="width:80%;color:#333;" />
	            </div>
	            <span class="txtstrong" style="display:none;">*</span>
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
</ui:content>
