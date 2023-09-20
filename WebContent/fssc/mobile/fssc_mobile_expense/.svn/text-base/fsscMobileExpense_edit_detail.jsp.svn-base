<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<c:if test="${ docTemplate.fdIsTravelAlone eq 'true'}">
 <!-- 新增行程 -->
    <div class="ld-addTravel-body">
        <div class="ld-addTravel-body-content">
            <div class="ld-addTravel-body-persionlInfo">
                <div class="ld-info-item">
                    <span>${lfn:message("fssc-expense:table.fsscExpenseTravelDetail")}</span>
                    <div>
                           <input type="text"  value="" name="fdSubject" id="fdSubject"  readonly="readonly" >
                           <input type="text"  value="" name="row-travel-index" hidden='true' >
                    </div>
                </div>
                
                
                <div class="ld-info-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseTravelDetail.fdPersonList")}</span>
                    <div>
                        <input type="text" placeholder="请选择" value="" name ="fdPersonList"  onclick="selectOrgElement('fdPersonListId','fdPersonList','${parentId}','true','person');"  readonly="readonly" >
                        <input type="text" placeholder="请选择" value="" name ="fdPersonListId" hidden="true" >
                        <span style="margin-left:2px;color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
                <div class="time ld-info-item">
                    <div>
                        <input type="text" placeholder="开始日期"  id="fdBeginDate0"  onclick="selectTime('fdBeginDate','fdBeginDate','0');" value=""  readonly="readonly">
                        <b></b>
                        <input type="text" placeholder="结束日期" id="fdEndDate0"  onclick="selectTime('fdEndDate','fdEndDate','0');" value="" readonly="readonly">
                    </div>
                    <div>
                        <input type="text" class="rightInp" name="days" id="days0" readonly="readonly" placeholder="请输入天数" value="1天">
                        <i></i>
                    </div>
                </div>
                <div class="city ld-info-item" onclick="selectCity('startCity');">
                    <div>
                        <input type="text" placeholder="出发地点" value="" name="startCity"  id="startCity" readonly="readonly">
                        <span style="color:#d02300;">*</span>
                        <b></b>
                        <input type="text" placeholder="到达地点" value="" name="endCity"  id="endCity" readonly="readonly">
                        <span style="color:#d02300;">*</span>
                    </div>
    <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
                    <div>
                          <input type="text" class="rightInp" placeholder="交通工具" name="fdBerthName"  id="fdBerthName" readonly="readonly">
                          <input type="text"  name="fdVehicleId" hidden="true">
                          <input type="text"  name="fdBerthId"  hidden="true">
                          <input type="text"  name="fdVehicleName"  hidden="true">
                          <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
    </c:if>
                </div>
            </div>
        </div>
        <div class="ld-footer ld-footer-travel">
	        <div class="ld-footer-whiteBg" style="margin-right:15px;" onclick="cancelTripDetail();" >${ lfn:message('button.cancel') }</div>
	        <div class="ld-footer-blueBg" onclick="saveTravelDetail()">${ lfn:message('button.save') }</div>
	    </div>
    </div>
    </c:if>
    <!-- 新增费用明细 -->
    <div class="ld-entertain-main-body">
        <div class="ld-entertain-detail">
            <div class="ld-entertain-detail-persionlInfo">
              <c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
	             <div class="ld-entertain-detail-item">
	                    <span>行程</span>
	                    <div>
	                       <input type="text"  placeholder="请选择行程"  value="" name="fdTravel" id="fdTravel" readonly="readonly" onclick="selectTravel();">
	                        <span style="color:#d02300;">*</span>
	                       <i></i>
	                    </div>
                </div> 
                </c:if>
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdRealUser")}</span>
                    <div>
                        <input type="text" placeholder="请选择人员" value=""  name="fdRealUser" onclick="selectOrgElement('fdRealUserId','fdRealUser','${parentId}','','person');" id="fdRealUser"  readonly="readonly"  >
                         <input type="text" value=""  name="fdRealUserId" id="fdRealUserId" hidden="true" >
                         <input type="hidden" name="row-expense-index" value="" >
                         <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
	                <div class="ld-entertain-detail-item">
	                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdDept")}</span>
	                    <div>
	                        <input type="text" placeholder="请选择承担部门"  id="fdDept" value="${parentName}" name="fdDept" onclick="selectOrgElement('fdDeptId','fdDept','${parentId}','','dept');" readonly="readonly" >
	                        <input type="text" value="${parentId}"  name="fdDeptId" id="fdDeptId"  hidden="true" >
	                        <i></i>
	                    </div>
	                </div>
                </c:if>
                <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdExpenseItem")}</span>
                      <div>
                        <input type="text" placeholder="请选择费用类型" id="fdExpenseItemName"  name="fdExpenseItemName"  onclick="selectObject('fdExpenseItemId','fdExpenseItemName',formOption['url']['getEopBasedataExpenseItem'],FSSC_ChangeIsDeduct);" onchange="" readonly="readonly">
                         <input type="text" value=""  name="fdExpenseItemId" id="fdExpenseItemId" hidden="true" >
                          <input type="text"  name="fdDayCalType" id="fdDayCalType"  hidden="true">
                         <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
                <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
                    <div  class="ld-entertain-detail-item">
                        <span>${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}</span>
                        <div>
                            <input type="text" placeholder="${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}" id="fdDetailProjectName" name="fdDetailProjectName" value="" readonly="readonly" onclick="selectObject('fdDetailProjectId','fdDetailProjectName',formOption['url']['getFdProject']);" >
                            <input id="fdDetailProjectId" name="fdDetailProjectId" value=""  hidden='true'>
                            <span style="margin-left:2px;color:#d02300;">*</span>
                            <i></i>
                        </div>
                    </div>
                </c:if>
                <c:if test="${docTemplate.fdAllocType=='1' }">
                		<input type="hidden" value=""  name="fdCostCenterDetailId" id="fdCostCenterDetailId" hidden="true" >
                </c:if>
                <c:if test="${docTemplate.fdAllocType!='1' }">
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdCostCenter")}</span>
                    <div>
                        <input type="text" placeholder="请选择成本中心" id="fdCostCenterDetail" name="fdCostCenterDetail" onclick="selectObject('fdCostCenterDetailId','fdCostCenterDetail',formOption['url']['getEopBasedataCostCenter'],afterSelectCostCenter);" onchange="FSSC_ChangeIsDeduct();" readonly="readonly">
                         <input type="text" value=""  name="fdCostCenterDetailId" id="fdCostCenterDetailId" hidden="true" >
                          <xform:text property="fdCostCenterName" showStatus="noShow"/>
                          <xform:text property="fdCostCenterId" showStatus="noShow" />
                          <xform:text property="fdCompanyId" value="${fdCompanyId }" showStatus="noShow"/>
                          <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
                </c:if>
                 <fssc:configEnabled property="fdFinancialSystem" value="SAP">
	       			 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
		                <div class="ld-entertain-detail-item">
		                    <span>WBS</span>
		                    <div>
		                        <input type="text" placeholder="请选择WBS" id="fdWbs" name='fdWbs' onclick="selectObject('fdWbsId','fdWbs',formOption['url']['getWbsList']);" readonly="readonly">
		                         <input type="text" value=""  name="fdWbsId"  id="fdWbsId" hidden="true" >
		                        <i></i>
		                    </div>
		                </div>
		                <div class="ld-entertain-detail-item">
		                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdInnerOrder")}</span>
		                    <div>
		                        <input type="text" placeholder="请选择内部订单" id="fdInnerOrder" name='fdInnerOrder' onclick="selectObject('fdInnerOrderId','fdInnerOrder',formOption['url']['getOrderList']);" readonly="readonly">
		                        <input type="text" value=""  name="fdInnerOrderId" id="fdInnerOrderId" hidden="true" >
		                        <i></i>
		                    </div>
		                </div>
	                </c:if>
                </fssc:configEnabled>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')==-1 }">
                <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdHappenDate")}</span>
                    <div>
                        <input type="text" placeholder="请选择发生日期" id="fdHappenDate" onclick="selectTime('fdHappenDate','fdHappenDate','');" readonly="readonly">
                        <i></i>
                    </div>
                </div>
                </c:if>
                 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStartDate")}</span>
                    <div>
                        <input type="text" placeholder="请选择开始日期"  id="fdBeginDate1"  onclick="selectTime('fdBeginDate','fdBeginDate','1');" readonly="readonly">
                        <i></i>
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseTravelDetail.fdEndDate")}</span>
                    <div>
                        <input type="text" placeholder="请选择结束日期" id="fdEndDate1"  onclick="selectTime('fdEndDate','fdEndDate','1');" readonly="readonly">
                        <i></i>
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdTravelDays")}</span>
                    <div>
                        <input type="text" class="rightInp" id="days1"   readonly="readonly" placeholder="请输入天数" value="1天" >
                    </div>
                </div>
                </c:if>
                
                <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStartPlace")}</span>
                    <div>
                         <input type="text"  id="startCity" name="startCity" placeholder="请选择出发城市" onclick="selectCity('startCity');" readonly="readonly" >
                         <input type="text"  id="startCityId" name="startCityId" hidden='true' >
                         <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
                 <div class="ld-entertain-detail-item">
                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdArrivalPlace")}</span>
                    <div>
                        <input type="text"  id="endCity" name="endCity" placeholder="请选择到达城市" onclick="selectCity('endCity');" readonly="readonly" >
                         <input type="text"  id="endCityId" name="endCityId" hidden='true' >
                         <span style="color:#d02300;">*</span>
                        <i></i>
                    </div>
                </div>
	                <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
		                 <div class="ld-entertain-detail-item">
		                    <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdBerth")}</span>
		                    <div>
		                        <input type="text"  placeholder="请选择交通工具"  id="fdBerthName" name="fdBerthName" onclick="selectCity('trafficTools');" readonly="readonly"  >
		                         <input type="text"  id="fdBerthId" name="fdBerthId" hidden='true' >
		                         <span style="color:#d02300;">*</span>
		                        <i></i>
		                    </div>
		                </div>
	                 </c:if>
                </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }"> 
	            <div class="ld-entertain-detail-item">
	                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdPersonNumber")}</span>
	                <div>
	                    <input type="text" placeholder="请输入人数" id ="fdPersonNumber">
	                    <span style="color:#d02300;">*</span>
	                </div>
	            </div>
            </c:if>
         </div>
        </div>
        <div class="ld-line20px"></div>
        <div class="ld-entertain-detail-costInfo">
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdApplyMoney")}</span>
                <div>
                <input type="text" placeholder="请输入申报金额" id="fdApplyMoney" name="fdApplyMoney" onchange="FSSC_ChangeMoney(this.value,this);">
                <input type="hidden"  name ="fdBudgetMoney" id="fdBudgetMoney" value="" />
                 <c:if test="${docTemplate.fdIsForeign!='true' }">
                  <input type="text"  name ="fdCurrencyId" id="fdCurrencyId" value="" hidden="true">
                  <input type="text"  name ="fdExchangeRate" id="fdExchangeRate" value="" hidden="true">
                  <input type="text"  name ="fdBudgetRate" id="fdBudgetRate" value="" hidden="true">
                  <input type="text"  name ="fdStandardMoney" id="fdStandardMoney" value="" hidden="true">
                </c:if>
                <span style="color:#d02300;">*</span>
                </div>
            </div>
             <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
        		<div class="amount">
                	<span>${lfn:message("fssc-expense:fsscExpenseDetail.fdNonDeductMoney")}</span>
                	<input type="text" id="fdNonDeductMoney" name="fdNonDeductMoney" value="" onchange="FSSC_ChangeIsDeduct()" placeholder="不可抵扣额"  >
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
	                		<input type="hidden" name="fdIsDeduct" value="" onclick="FSSC_ChangeIsDeductOnCheckbox();">
	                </div>
	            </div>
	            <div class="ld-entertain-detail-item fdIsDeduct">
	                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdInputTaxRate")}</span>
	                <div>
	                	<input type="text" name="fdInputTaxRate" id="fdInputTaxRate" readonly="readonly" placeholder="进项税率" onclick="selectObject('fdInputTaxRate','fdInputTaxRate',formOption['url']['getFdInputTax'],afterSelectInputTax);">
	                	<i></i>
	                </div>
	            </div>
	            <div class="ld-entertain-detail-item fdIsDeduct">
	                <span>${lfn:message("fssc-expense:enums.fd_extend_field.fdInputTax")}</span>
	                <input type="text" name="fdInputTaxMoney" id="fdInputTaxMoney"  placeholder="进项税额" onchange="FSSC_CalculateNoTaxMoney();" >
	            	
	            </div>
	            <div class="amount">
                	<span>${lfn:message("fssc-expense:fsscExpenseDetail.fdNoTaxMoney")}</span>
                	<input type="text" id="fdNoTaxMoneyExpense" value="" placeholder="不含税金额"  >
            	</div>
            </c:if>
            <c:if test="${docTemplate.fdIsForeign=='true' }">
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdCurrency")}</span>
                <div>
                    <input type="text" name="fdCurrency" placeholder="请选择货币" id="fdCurrency" onclick="selectExpenseCurrency()" value="" readonly="readonly">
                    <input type="text"  name ="fdCurrencyId" id="fdCurrencyId" value="" hidden="true">
                    <input type="text"  name ="fdExchangeRate" id="fdExchangeRate" value="" hidden="true">
                    <input type="text"  name ="fdBudgetRate" id="fdBudgetRate" value="" hidden="true">
                    <span style="color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdStandardMoney")}</span>
                <div>
                    <input type="text"  id="fdStandardMoney" value="" placeholder="本币金额" readonly="readonly">
                </div>
            </div>
            </c:if>
            <div class="ld-entertain-detail-item">
                <span>${lfn:message("fssc-expense:fsscExpenseDetail.fdUse")}</span>
                <div>
                    <input type="text" placeholder="请输入用途摘要" id='fdUse'>
                </div>
            </div>
        </div>
        <div class="ld-footer ld-footer-expense">
	        <div class="ld-footer-whiteBg" style="margin-right:15px;" onclick="cancelSaveDetail();" >${ lfn:message('button.cancel') }</div>
	        <div class="ld-footer-blueBg" onclick="saveExpeseDeteil()">${ lfn:message('button.save') }</div>
	    </div>
    </div>
    
     <!-- 发票明细 -->
    <div class="ld-travel-detail-body">
        <div class="ld-travel-detail">
            <div class="ld-travel-detail-persionlInfo">
                <div class="ld-info-item">
                    <span>   ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}</span>
                    <div class="">
                        <input type="text" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}"  id="fdInvoiceNumber">
                        <input type="hidden" name="row-invoice-index" value=""/>
                        <input type="hidden" id="fdCheckStatus" name="fdCheckStatus" value=""/>
                        <input type="hidden" id="fdState" name="fdState" value=""/>
                        <input type="hidden" id="fdIsCurrent" name="fdIsCurrent" value=""/>
                        <span style="color:#d02300;">*</span>
                        <!-- <i></i> -->
                  </div>
                </div>
                <div class="ld-info-item">
                    <span>   ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}</span>
                    <div class="">
                        <input type="text" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.select')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}" readonly id="fdInvoiceType" onclick="selectInvoiceTypeItem();">
                        <input type="text" id="fdInvoiceTypeId" value="" hidden='true'>
                        <span style="color:#d02300;">*</span>
                        <i></i>
                  </div>
                </div>
            </div>
        </div>
        <div class="ld-line20px "></div> 
        <div class="ld-travel-detail-costInfo">
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}</span>
                <div>
                    <input type="text" id="fdInvoiceCode" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}" value="">
                    <span style="color:#d02300;" class="isVat">*</span>
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode')}</span>
                <div>
                    <input type="text" id="fdCheckCode" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCheckCode')}" value="">
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName')}</span>
                <div>
                    <input type="text" id="fdPurchName" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdPurchName')}" value="">
                </div>
            </div>
           <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber')}</span>
                <div>
                    <input type="text" id="fdTaxNumber" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxNumber')}" value="">
                </div>
            </div>
            <div class="currency ld-info-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}</span>
                <div>
                    <input type="text" id="fdInvoiceDate"  value="" onclick="selectTime('fdInvoiceDate','fdInvoiceDate','');"  readonly="readonly">
                    <span style="color:#d02300;display:none;" class="isVat">*</span>
                    <i></i>
                </div>
            </div>
             <div class="currency ld-info-item">
              <span>${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}</span>
              <div>
                 <input type="text" id="fdInvoiceMoney" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}" value="" onchange="selectTaxrate();">
                 <span style="color:#d02300;">*</span>
              </div>
        	 </div>
            <div class="currency ld-info-item">
              <span> ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}</span>
              <div>
                  <input type="text" id="fdTaxValue" name="fdTaxValue" placeholder="${lfn:message('fssc-expense:fssc.mobile.placeholder.input')}${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}"" value=""  onchange="selectTaxrate();" >
                  <input type="text" id="fdTaxId" name="fdTaxId" hidden='true' value="">
                  <span>%</span>
                  <span style="color:#d02300;display:none;" class="isVat">*</span>
                 <!--  <i></i> -->
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
        <div class="ld-footer ld-footer-invoice">
	            <div class="ld-footer-whiteBg" style="margin-right:15px;" onclick="cancelInvoiceDetail();" >${ lfn:message('button.cancel') }</div>
                <!-- 存在合力中税模块，且开启提交人验真，且是费控解决方案版 -->
                <fssc:checkVersion version="true">
                <kmss:ifModuleExist path="/fssc/baiwang">
                    <c:if test="${fdCreatorCheck}">
                        <div class="ld-footer-blueBg" onclick="checkInvoice()" style="margin-right:0.3rem;">${lfn:message('fssc-mobile:button.checkInvoice')}</div>
                    </c:if>
                </kmss:ifModuleExist>
                </fssc:checkVersion>
	    		<div class="ld-footer-blueBg" onclick="saveInvoiceDetail()">${ lfn:message('button.save') }</div>
	    </div>
    </div>
    
    <!-- 新增账户 -->
    <div class="ld-addAccount-body">
        <div class="ld-addAccount-body-content">
            <div class="ld-addAccount-body-content-item">
                <span> ${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay')}</span>
                <div>
                    <input type="text" placeholder="请选择支付方式" id="fdPayWayName"  onclick="selectObject('fdPayWayId','fdPayWayName',formOption['url']['getFsBasePay'],afterSelectPayWay);" readonly="readonly">
                    <input  type="text"  id="fdPayWayId" hidden="true" value="">
                    <input type="text"   id="fdBankId" hidden="true" value="">
                    <input type="text" id="fdIsTransfer" hidden="true" value="">
                    <input type="hidden" name="row-account-index" value=""/>
                    <fssc:checkVersion version="false">
                    <input type="text"   id="fdCurrencyIdAccount" hidden="true" value="">
                    <input type="text"   id="fdExchangeRateAccount" hidden="true" value="">
                    </fssc:checkVersion>
                    <span style="color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <fssc:checkVersion version="true">
	            <div class="ld-addAccount-body-content-item">
	                <span> ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}</span>
	                <div>
	                    <input type="text" placeholder="请选择币种" id="fdCurrencyAccount" name="fdCurrencyAccount"  onclick="selectAccountCurrency('account');" readonly="readonly">
	                      <input type="text"   id="fdCurrencyIdAccount" name="fdCurrencyIdAccount" hidden="true" value="">
	                      <input type="text" id="fdExchangeRateAccount" hidden="true" value="">
	                      <span style="color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
            </fssc:checkVersion>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}</span>
                 <div>
                <input type="text" placeholder="请输入收款人账户名" id="fdAccountName"  onclick="selectObject('fdAccountId','fdAccountName',formOption['url']['getAccountInfo'],afterSelectAccount);" readonly="readonly">
                <input type="text"  id="fdAccountId" hidden="true">
                <span style="color:#d02300;" class="vat">*</span>
                <i></i>
                </div>
            </div>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}</span>
                <div>
                <input type="text" placeholder="请输入收款人账号" id="fdBankAccount">
                <span style="color:#d02300;" class="vat">*</span>
                </div>
            </div>
            <div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}</span>
                <div>
                <input type="text" placeholder="请输入收款人开户行" id="fdBankName">
                <span style="color:#d02300;" class="vat">*</span>
                </div>
            </div>
		<fssc:checkUseBank fdBank="CMB,CBS,CMInt">
			<div class="ld-addAccount-body-content-item">
			  <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}</span>
			  <div>
			  <input type="text" placeholder="请选择账户归属地"  onclick="selectAreaObject('fdAccountAreaCode','fdAccountAreaName');" id="fdAccountAreaName" name="fdAccountAreaName" readonly="readonly">
				<span style="color:#d02300;" class="vat">*</span>
                <i></i>	 
				</div>
			</div>
		</fssc:checkUseBank>
		<div class="ld-addAccount-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}</span>
                <div>
                <input type="text" placeholder="请输入收款金额" id="fdMoney" >
                <span style="color:#d02300;">*</span>
                </div>
            </div>
        </div>
        <div class="ld-footer ld-footer-account">
	        <div class="ld-footer-whiteBg" style="margin-right:15px;" onclick="cancelAccount();" >${ lfn:message('button.cancel') }</div>
	        <div class="ld-footer-blueBg" onclick="saveAccountDetail()">${ lfn:message('button.save') }</div>
	    </div>
    </div>
    
    
     <!--冲抵借款编辑 -->
    <div class="ld-addLoan-body">
        <div class="ld-addLoan-body-content">
            <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}</span>
                <input type="text" id="docSubject"   readonly="readonly">
                <input type="hidden" name="row-loan-index"/>
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
                <input type="text" placeholder="请输入本次冲抵金额" id="fdOffsetMoney" onchange="offsetMoneyChange()">
            </div>
             <div class="ld-addLoan-body-content-item">
                <span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}</span>
                <input type="text" id="fdLeftMoney" readonly="readonly">
            </div>
        </div>
        <div class="ld-footer ld-footer-loan">
	        <div class="ld-footer-whiteBg" style="margin-right:15px;" onclick="cancelLoanOffset();" >${ lfn:message('button.cancel') }</div>
	        <div class="ld-footer-blueBg" onclick="saveLoanOffset()">${ lfn:message('button.save') }</div>
	    </div>
    </div>
    
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
    <!-- 处理中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask" style="z-index:100;">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:py.processing')}</span>
            </div>
        </div>
    </div>
 
    
    
   
 <!-- 城市选择器 -->
 <c:import url="/fssc/mobile/fssc_mobile_note/fsscMobileNote_city.jsp">
   <c:param name="type" value="expense"></c:param>
</c:import>
  
