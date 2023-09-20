<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>

 <!-- 新增行程 -->
    <div class="ld-addTravel-body">
        <div class="ld-addTravel-body-content">
            <div class="ld-addTravel-body-persionlInfo">
                <div class="ld-info-item">
                    <span>${lfn:message("fssc-expense:table.fsscExpenseTravelDetail")}</span>
                    <div>
                           <input type="text"  value="" name="travelId" id="travelId"  readonly="readonly" >
                           <input type="text"  value="" name="travelIndex" hidden='true' >
                    </div>
                </div>
                <div class="ld-info-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseTravelDetail.fdPersonList")}</span>
                    <div>
                        <input type="text" placeholder="请选择" value="" name ="fdPersonList"   readonly="readonly" >
                        <input type="text" placeholder="请选择" value="" name ="fdPersonListId" hidden="true" >
                        
                    </div>
                </div>
                <div class="time ld-info-item">
                    <div>
                        <input type="text" placeholder="开始日期"  id="fdBeginDate" value="" readonly="readonly">
                        <b></b>
                        <input type="text" placeholder="结束日期" id="fdEndDate" value=""  readonly="readonly">
                    </div>
                    <div>
                        <input type="text" class="rightInp" name="days" id="days" value=""  readonly="readonly">
                        
                    </div>
                </div>
                <div class="city ld-info-item">
                    <div>
                        <input type="text" placeholder="出发地点" value="" name="startCity" readonly="readonly">
                        <b></b>
                        <input type="text" placeholder="到达地点" value="" name="endCity" readonly="readonly">
                    </div>
<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
                    <div>
                          <input type="text" class="rightInp" placeholder="交通工具" name="trafficTools" readonly="readonly">
                          <input type="text"  name="fdVehicleId" hidden="true">
                          <input type="text"  name="fdBerthId"  hidden="true">
                          <input type="text"  name="fdBerthName"  hidden="true">
                          <input type="text"  name="fdVehicleName"  hidden="true">
                    </div>
</c:if>
                </div>
            </div>
        </div>
        <div class="ld-addTrip-btn-single" >${ lfn:message('button.back') }</div>
    </div>
    
    <!-- 新增费用明细 -->
    <div class="ld-entertain-main-body">
        <div class="ld-entertain-detail">
            <div class="ld-entertain-detail-persionlInfo">
              <c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
	             <div class="ld-entertain-detail-item">
	                    <span>行程</span>
	                    <div>
	                       <input type="text"  value="" name="fdTravel" id="fdTravel" readonly="readonly" >
	                       
	                    </div>
                </div> 
                </c:if>
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdRealUser")}</span>
                    <div>
                        <input type="text"  value=""  name="fdRealUser" id="fdRealUser" readonly="readonly"  >
                         <input type="text" value=""  name="fdRealUserId" id="fdRealUserId" hidden="true" >
                         <input type="text" name="detailIndex" hidden='true' >
                        
                    </div>
                </div>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
	                <div class="ld-entertain-detail-item">
	                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdDept")}</span>
	                    <div>
	                        <input type="text"  id="fdDept" name="fdDept" readonly="readonly" >
	                        <input type="text" value="${parentId}"  name="fdDeptId" id="fdDeptId"  hidden="true" >
	                        
	                    </div>
	                </div>
                </c:if>
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdExpenseItem")}</span>
                    <div>
                        <input type="text"  id="fdExpenseItem"  name="fdExpenseItem"    readonly="readonly">
                         <input type="text" value=""  name="fdExpenseItemId" id="fdExpenseItemId" hidden="true" >
                        
                    </div>
                </div>
                <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
                    <div  class="ld-entertain-detail-item">
                        <span>${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}</span>
                        <div>
                            <input type="text" placeholder="${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}" id="fdDetailProjectName" name="fdDetailProjectName" value="" readonly="readonly" onclick="selectObject('fdDetailProjectId','fdDetailProjectName',formOption['url']['getFdProject']);" >
                            <input id="fdDetailProjectId" name="fdDetailProjectId" value=""  hidden='true'>
                        </div>
                    </div>
                </c:if>
                <c:if test="${docTemplate.fdAllocType!='1' }">
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdCostCenter")}</span>
                    <div>
                        <input type="text" id="fdCostCenterDetail" name="fdCostCenterDetail" readonly="readonly">
                         <input type="text" value=""  name="fdCostCenterDetailId" id="fdCostCenterDetailId" hidden="true" >
                        
                    </div>
                </div>
                </c:if>
                 <fssc:configEnabled property="fdFinancialSystem" value="SAP">
	       			 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
		                <div class="ld-entertain-detail-item">
		                    <span>WBS</span>
		                    <div>
		                        <input type="text"  id="fdWbs" name='fdWbs'  readonly="readonly">
		                         <input type="text" value=""  name="fdWbsId"  id="fdWbsId" hidden="true" >
		                    </div>
		                </div>
		                <div class="ld-entertain-detail-item">
		                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdInnerOrder")}</span>
		                    <div>
		                        <input type="text" id="fdInnerOrder" name='fdInnerOrder'  readonly="readonly">
		                        <input type="text" value=""  name="fdInnerOrderId" id="fdInnerOrderId" hidden="true" >
		                    </div>
		                </div>
	                </c:if>
                </fssc:configEnabled>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')==-1 }">
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdHappenDate")}</span>
                    <div>
                        <input type="text" id="fdHappenDate"  readonly="readonly">
                    </div>
                </div>
                </c:if>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStartDate")}</span>
                    <div>
                        <input type="text" id="fdStartDate"  readonly="readonly">
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdHappenDate")}</span>
                    <div>
                        <input type="text" id="fdHappenDate" readonly="readonly">
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdTravelDays")}</span>
                    <div>
                        <input type="text"  id="fdTravelDays"  readonly="readonly">
                    </div>
                </div>
                </c:if>
                
                <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStartPlace")}</span>
                    <div>
                         <input type="text"  id="startCity" name="startCity"  readonly="readonly" >
                         <input type="text"  id="startCityId" name="startCityId" hidden='true' >
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdArrivalPlace")}</span>
                    <div>
                        <input type="text"  id="endCity" name="endCity"   readonly="readonly" >
                         <input type="text"  id="endCityId" name="endCityId" hidden='true' >
                    </div>
                </div>
	                <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
		                 <div class="ld-entertain-detail-item">
		                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdBerth")}</span>
		                    <div>
		                        <input type="text"   id="trafficTools" name="trafficTools"  readonly="readonly"  >
		                         <input type="text"  id="trafficToolsId" name="trafficToolsId" hidden='true' >
		                    </div>
		                </div>
	                 </c:if>
                </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }"> 
	            <div class="ld-entertain-detail-item">
	                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdPersonNumber")}</span>
	                <div>
	                    <input type="text" id ="fdPersonNumber" readonly="readonly">
	                </div>
	            </div>
            </c:if>
         </div>
        </div>
        <div class="ld-line20px"></div>
        <div class="ld-entertain-detail-costInfo">
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdApplyMoney")}</span>
                <input type="text"   id="fdApplyMoney" readonly="readonly" >
                 <c:if test="${docTemplate.fdIsForeign!='true' }">
                  <input type="text"  name ="fdCurrencyId" id="fdCurrencyId" value="" hidden="true">
                  <input type="text"  name ="fdExchangeRate" id="fdExchangeRate" value="" hidden="true">
                  <input type="text"  name ="fdBudgetRate" id="fdBudgetRate" value="" hidden="true">
                </c:if>
            </div>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
        		<div class="amount">
                	<span>${lfn:message("fssc-expense:fsscExpenseDetail.fdNonDeductMoney")}</span>
                	<input type="text" id="fdNonDeductMoney" name="fdNonDeductMoney" value="" readonly >
            	</div>
            </c:if>
              <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
              	<div class="ld-entertain-detail-item">
	                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdIsDeduct")}</span>
	                <div>
	                		<div class="checkbox_item">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">是</div>
	                			<input type="radio" name="_fdIsDeduct" value="true">
	                		</div>
	                		<div class="checkbox_item">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">否</div>
	                			<input type="radio" name="_fdIsDeduct" value="false">
	                		</div>
	                		<input type="hidden" id="fdIsDeduct" value="" onclick="FSSC_ChangeIsDeductOnCheckbox();">
	                </div>
	            </div>
	            <div class="ld-entertain-detail-item fdIsDeduct">
	                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdInputTaxRate")}</span>
	                <input type="text" name="fdInputTaxRate"  readonly="readonly">
	                <input type="hidden" name="fdIsDeduct" value="" />
	            </div>
	            <div class="ld-entertain-detail-item fdIsDeduct">
	                <span>${lfn:message("fssc-expense:enums.fd_extend_field.fdInputTax")}</span>
	                <input type="text" name="fdInputTaxMoney" id="fdInputTaxMoney"  readonly="readonly">
	            </div>
	             <div class="amount">
                	<span>${lfn:message("fssc-expense:fsscExpenseDetail.fdNoTaxMoney")}</span>
                	<input type="text" id="fdNoTaxMoneyExpense" value=""   readonly="readonly" >
            	</div>
            </c:if>
            <c:if test="${docTemplate.fdIsForeign=='true' }">
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdCurrency")}</span>
                <div>
                    <input type="text"  id="fdCurrency"  value="" readonly="readonly">
                    <input type="text"  name ="fdCurrencyId" id="fdCurrencyId" value="" hidden="true">
                    <input type="text"  name ="fdExchangeRate" id="fdExchangeRate" value="" hidden="true">
                    <input type="text"  name ="fdBudgetRate" id="fdBudgetRate" value="" hidden="true">
                    
                </div>
            </div>
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStandardMoney")}</span>
                <div>
                    <input type="text"  id="fdStandardMoney" value=""  readonly="readonly">
                </div>
            </div>
            </c:if>
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdUse")}</span>
                <div>
                    <input type="text" id='fdUse' readonly="readonly">
                </div>
            </div>
        </div>
        <div class="ld-entertain-detail-btn-single"  >${ lfn:message('button.back') }</div>
    </div>
    
     <!-- 发票明细 -->
    <div class="ld-travel-detail-body">
        <div class="ld-travel-detail">
            <div class="ld-travel-detail-persionlInfo">
                <div class="ld-info-item">
                    <span>   ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}</span>
                    <div class="">
                        <input type="text"  id="fdInvoiceNumber" readonly="readonly">
                        <!--  -->
                  </div>
                </div>
                <div class="ld-info-item">
                    <span>   ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}</span>
                    <div class="">
                        <input type="text"  id="fdInvoiceType"  readonly="readonly">
                        <input type="text" id="fdInvoiceTypeId" value="" hidden='true'>
                        
                  </div>
                </div>
            </div>
        </div>
        
        <div class="ld-travel-detail-costInfo">
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}</span>
                <div>
                    <input type="text" id="fdInvoiceCode" value=""  readonly="readonly">
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode')}</span>
                <div>
                    <input type="text" id="fdCheckCode" value=""  readonly="readonly">
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName')}</span>
                <div>
                    <input type="text" id="fdPurchName" value=""  readonly="readonly">
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber')}</span>
                <div>
                    <input type="text" id="fdTaxNumber" value=""  readonly="readonly">
                </div>
            </div>
            <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}</span>
                <div>
                    <input type="text" id="fdInvoiceDate" value=""  readonly="readonly">
                    
                </div>
            </div>
             <div class="currency ld-info-item">
              <span>${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}</span>
              <div>
                 <input type="text" id="fdInvoiceMoney" value="" readonly="readonly">
              </div>
        	 </div>
            <div class="currency ld-info-item">
              <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}</span>
              <div>
                  <input type="text" id="fdTaxValue" name="fdTaxValue" value=""   readonly="readonly">
                  <input type="text" id="fdTaxId" name="fdTaxId" hidden='true' value="">
                  <span>%</span>
                 <!--   -->
              </div>
        	 </div>
        	  <div class="currency ld-info-item">
              <span>${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}</span>
              <div>
                 <input type="text" id="fdTaxMoney"  value="" readonly="readonly">
              </div>
        	 </div>
            <div class="amount">
                <span>${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}</span>
                <input type="text" id="fdNoTaxMoney" value="" readonly="readonly">
            </div>
        </div>
        <div class="ld-save-btn" >${ lfn:message('button.back') }</div>
    </div>
    
    <!-- 新增账户 -->
    <div class="ld-addAccount-body">
        <div class="ld-addAccount-body-content">
            <div class="ld-addAccount-body-content-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay')}</span>
                <div>
                    <input type="text" id="fdPayWayName"  readonly="readonly">
                    <input  type="text"  id="fdPayWayId" hidden="true" value="">
                    <input type="text"   id="fdBankId" hidden="true" value="">
                    <fssc:checkVersion version="false">
                    <input type="text"   id="fdCurrencyIdAccount" hidden="true" value="">
                    <input type="text"   id="fdExchangeRateAccount" hidden="true" value="">
                    </fssc:checkVersion>
                    
                </div>
            </div>
            <fssc:checkVersion version="true">
	            <div class="ld-addAccount-body-content-item">
	                <span> ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}</span>
	                <div>
	                    <input type="text" id="fdCurrencyAccount" name="fdCurrencyAccount"   readonly="readonly">
	                      <input type="text"   id="fdCurrencyIdAccount" name="fdCurrencyIdAccount" hidden="true" value="">
	                      <input type="text" id="fdExchangeRateAccount" hidden="true" value="">
	                    
	                </div>
	            </div>
            </fssc:checkVersion>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}</span>
                 <div>
                <input type="text" id="fdAccountName"   readonly="readonly">
                <input type="text"  id="fdAccountId" hidden="true">
                
                </div>
            </div>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}</span>
                <input type="text" id="fdBankAccount"  readonly="readonly">
            </div>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}</span>
                <input type="text" id="fdBankName"  readonly="readonly">
            </div>
            		<fssc:checkUseBank fdBank="CMB,CBS,CMInt">
			<div class="ld-addAccount-body-content-item">
			  <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}</span>
			  <div>
			  <input type="text" id="fdAccountAreaName" readonly="readonly">
				</div>
			</div>
		</fssc:checkUseBank>
             <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}</span>
                <input type="text" id="fdMoney"  readonly="readonly">
            </div>
        </div>
        <div class="ld-addAccount-btn-single">${ lfn:message('button.back') }</div>
    </div>
    
     <!--冲抵借款编辑 -->
    <div class="ld-addLoan-body">
        <div class="ld-addLoan-body-content">
            <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}</span>
                <input type="text" id="docSubject"   readonly="readonly">
            </div>
            <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdNumber')}</span>
                <input type="text"  id="fdNumber" readonly="readonly">
            </div>
            <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}</span>
                <input type="text" id="fdLoanMoney" readonly="readonly">
            </div>
            <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}</span>
                <input type="text" id="fdCanOffsetMoney" readonly="readonly">
            </div>
             <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}</span>
                <input type="text" id="fdOffsetMoney"  readonly="readonly">
            </div>
             <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}</span>
                <input type="text" id="fdLeftMoney" readonly="readonly">
            </div>
        </div>
        <div class="ld-addLoan-btn-single" >${ lfn:message('button.back') }</div>
    </div>
 <!-- 城市选择器 -->
   <!--发票及附件查看 -->
    <div class="ld-temp-body">
        <div class="ld-temp-body-content">
            <h3>附件</h3>
            <div class="ld-temp-body-content-invoice-attachment">
            </div>
            <h3>发票信息</h3>
            <div class="ld-temp-body-content_invoice">
	            <div class="ld-temp-body-content_invoice_title">
	            	<div class="ld-temp-body-content_invoice_title_item" style="color:#999;">发票类型</div>
	            	<div class="ld-temp-body-content_invoice_title_item" style="color:#999;">费用类型</div>
	            	<div class="ld-temp-body-content_invoice_title_item" style="color:#999;">发票号码</div>
	            	<div class="ld-temp-body-content_invoice_title_item" style="color:#999;">发票代码</div>
	            	<div class="ld-temp-body-content_invoice_title_item" style="color:#999;">发票金额</div>
	            </div>
            </div>
            
        </div>
        <div class="ld-footer ld-footer-temp" style="display:none;">
	        <div class="ld-footer-whiteBg" style="width:100%" onclick="cancelTemp();" >${ lfn:message('button.back') }</div>
	    </div>
    </div>
  
