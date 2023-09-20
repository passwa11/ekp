<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/fssc/mobile/common/attachement/attachment_view.jsp" %>

<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>${lfn:message("fssc-expense:module.fssc.expense")}</title>
</head>
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/city.css?s_cache=${LUI_Cache }" >
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/Mdate.css?s_cache=${LUI_Cache }">
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/popups.css?s_cache=${LUI_Cache }">
<script type="text/javascript">
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/", 'js', true);
</script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/popups.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/iScroll.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js?s_cache=${LUI_Cache }"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/Mdate.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/common/attachement/attachment.js"></script>
<body>
<form action="${LUI_ContextPath }/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do"  name="fsscExpenseMainForm" method="post">
 <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-header">
        
            <div class="ld-newApplicationForm-header-title">
                <input type="text" placeholder="请输入差旅标题" name="docSubject" value="${fsscExpenseMainForm.docSubject }" readonly="readonly" >
            </div>
            <div class="ld-newApplicationForm-header-desc">
                <input type="text"  name="fdContent" value="${fsscExpenseMainForm.fdContent }" readonly="readonly" >
            </div>
        </div>
        <div class="ld-newApplicationForm-info">
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdClaimant")}</span>
                <div class="ld-selectPersion">
                     <input type="text" placeholder="报销人" name="fdClaimantName" value="${fsscExpenseMainForm.fdClaimantName }" readonly="readonly" >
                     <xform:text property="fdClaimant" showStatus="noShow" value="${fsscExpenseMainForm.fdClaimantId }"></xform:text>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdCompany")}</span>
                <div>
                     <input type="text" id="fdCompanyName"  value="${fsscExpenseMainForm.fdCompanyName }" readonly="readonly" onclick="selectFdCompany()">
                      <input type="text" id="fdCompanyId"  value="${fsscExpenseMainForm.fdCompanyId }" hidden='true' >
                     <xform:text property="fdCompany" value="${fsscExpenseMainForm.fdCompanyId }" showStatus="noShow"></xform:text>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdCostCenter")}</span>
                <div>
                    <input type="text"  id="fdCostCenter" name="fdCostCenter" value="${fsscExpenseMainForm.fdCostCenterName }"  readonly="readonly" onclick="selectFdCostCenter();" >
                    <input id="fdCostCenterId" name="fdCostCenterId" value="${fsscExpenseMainForm.fdCostCenterId }"  hidden='true'>
                </div>
            </div>
			<c:if test="${docTemplate.fdIsProject=='true'&&(docTemplate.fdIsProjectShare=='false' or empty docTemplate.fdIsProjectShare) }">
	            <div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}</span>
	                <div>
	                    <input type="text"  id="fdProject" name="fdProject" value="${fsscExpenseMainForm.fdProjectName }" readonly="readonly" onclick="selectFdProject();" >
	                    <input id="fdProjectId" name="fdProjectId" value="${fsscExpenseMainForm.fdProjectId }"  hidden='true'>
	                </div>
	            </div>
            </c:if>
              <c:if test="${docTemplate.fdIsFee=='true' }">
	            <div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdFeeNames")}</span>
	                <div>
	                    <input type="text" id="fdFeeNames" name="fdFeeNames" value="${fsscExpenseMainForm.fdFeeNames }" readonly="readonly" onclick="selectFdFee();">
	                    <input id="fdFeeIds"  name="fdFeeIds" value="${fsscExpenseMainForm.fdFeeIds }"  hidden='true'>
	                </div>
	            </div>
	             <div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdIsCloseFee")}</span>
	                 <div>
	                		<div class="checkbox_item ${fsscExpenseMainForm.fdIsCloseFee?'checked':'' }">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">是</div>
	                			<input type="radio" name="_fdIsCloseFee" value="true">
	                		</div>
	                		<div class="checkbox_item ${fsscExpenseMainForm.fdIsCloseFee?'':'checked' }"">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">否</div>
	                			<input type="radio" name="_fdIsCloseFee" value="false">
	                		</div>
	                		<input type="hidden" name="fdIsCloseFee" value="" onclick="checkFeeRelation();">
	                </div>
	            </div>
            </c:if>
			 <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'8')>-1 }">
				 <div>
					 <span>${lfn:message("fssc-expense:fsscExpenseMain.fdProjectAccounting")}</span>
					 <div>
						 <div style="text-align:right;">${fsscExpenseMainForm.fdProjectAccountingName }</div>
						 <input id="fdProjectAccountingId"  name="fdProjectAccountingId" value="${fsscExpenseMainForm.fdProjectAccountingId }"  hidden='true'>
					 </div>
				 </div>
			 </c:if>
            		<div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdAttNumber")}</span>
	                <div>
	                   ${fsscExpenseMainForm.fdAttNumber }
	                </div>
	            </div>
        </div>
        <div class="ld-line20px"></div>
        
        <c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
        <!-- 行程明细 -->
        <div class="ld-addTrip-list">
            <div class="ld-addTrip-list-title">
                <h3>${lfn:message("fssc-expense:table.fsscExpenseTravelDetail")}</h3>
                <i></i>
            </div>
            <ul id="fdTravelListId">
            <table   id="TABLE_DocList_fdTravelList_Form" align="center"  style="width:100%;">
       			 <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
			        	<td >
			             ${vstatus.index+1}
			             </td>
			             <td>
			             <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdTravelId" value="${vstatus.index}" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdTravelDays" value="${fdTravelList_FormItem.fdTravelDays }" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBeginDate" value="${fdTravelList_FormItem.fdBeginDate }" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdEndDate" value="${fdTravelList_FormItem.fdEndDate }" />
                        </td>
                       <td>
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdStartPlace" value="${fdTravelList_FormItem.fdStartPlace }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdArrivalId" value="${fdTravelList_FormItem.fdArrivalId }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdArrivalPlace" value="${fdTravelList_FormItem.fdArrivalPlace }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdVehicleId" value="${fdTravelList_FormItem.fdVehicleId }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdVehicleName" value="${fdTravelList_FormItem.fdVehicleName }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthName" value="${fdTravelList_FormItem.fdBerthName }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthId" value="${fdTravelList_FormItem.fdBerthId }" />
                       </td>
                       <td>
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthId" value="${fdTravelList_FormItem.fdBerthId }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdSubject" value="${fdTravelList_FormItem.fdSubject }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdPersonListIds" value="${fdTravelList_FormItem.fdPersonListIds }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdPersonListNames" value="${fdTravelList_FormItem.fdPersonListNames }" />
                       </td>
            		  <li>
	                    <div class="ld-newApplicationForm-trip-top">
	                        <div>
	                            <img src="${LUI_ContextPath}/fssc/mobile/resource/images/train.png" alt=""><span>${fdTravelList_FormItem.fdSubject }</span>
	                        </div>
	                        <input name='fdTravelList_span[${vstatus.index}].travelId' value="${vstatus.index}" hidden='true'>
	                    </div>
	                    <div class="ld-newApplicationForm-trip-bottom">
	                        <div>
	                            <span name="fdTravelList_span[${vstatus.index}].fdStartPlace" >${fdTravelList_FormItem.fdStartPlace }</span>
	                            <span name="fdTravelList_span[${vstatus.index}].fdBerthName">${fdTravelList_FormItem.fdBerthName }</span>
	                            <span name="fdTravelList_span[${vstatus.index}].fdArrivalName">${fdTravelList_FormItem.fdArrivalPlace }</span>
	                        </div>
	                        <div>
	                            <span name="fdTravelList_span[${vstatus.index}].fdBeginDate">${fdTravelList_FormItem.fdBeginDate }</span>
	                            <span class="fdPersonListNames">${fdTravelList_FormItem.fdPersonListNames }</span>
	                            <span name="fdTravelList_span[${vstatus.index}].fdEndDate">${fdTravelList_FormItem.fdEndDate }</span>
	                        </div>
	                    </div>
	                  </li>
       			 </tr>
       			</c:forEach>
            </table> 
           </ul>
        </div>
         <div class="ld-line20px"></div>
        </c:if> 
        
        <!-- 费用明细 -->
        <div class="ld-newApplicationForm-costInfo">
            <div class="ld-newApplicationForm-travelInfo-title">
                <h3>${lfn:message('fssc-expense:table.fsscExpenseDetail') }</h3>
                <i></i>
            </div>
            <ul id="fdDetailListId" >
            <table   id="TABLE_DocList_fdDetailList_Form" align="center"  style="width:100%;">
       			 <c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_Form" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
			           <td >
		       			${vstatus.index+1}
		       			<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_Form.fdId }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" value="${fdDetailList_Form.fdApprovedApplyMoney }" />
	            		<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApprovedStandardMoney" value="${fdDetailList_Form.fdApprovedStandardMoney }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyId" value="${fdDetailList_Form.fdCurrencyId }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyName" value="${fdDetailList_Form.fdCurrencyName }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExchangeRate" value="${fdDetailList_Form.fdExchangeRate }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardMoney" value="${fdDetailList_Form.fdStandardMoney }" />
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCompanyId" value="${fdDetailList_Form.fdCompanyId }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_Form.fdBudgetInfo }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetStatus" value="${fdDetailList_Form.fdBudgetStatus }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeInfo" value="${fdDetailList_Form.fdFeeInfo }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeStatus" value="${fdDetailList_Form.fdFeeStatus }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardStatus" value="${fdDetailList_Form.fdStandardStatus }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoney" value="${fdDetailList_Form.fdBudgetMoney }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetRate" value="${fdDetailList_Form.fdBudgetRate}" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" value="${fdDetailList_Form.fdInputTaxRate}" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseItemName" value="${fdDetailList_Form.fdExpenseItemName }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" value="${fdDetailList_Form.fdExpenseItemId }" />
			         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCostCenterId" value="${fdDetailList_Form.fdCostCenterId }" />
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCostCenterName" value="${fdDetailList_Form.fdCostCenterName }" /> 
			        	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdDeptId" value="${fdDetailList_Form.fdDeptId }" /> 
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdDeptName" value="${fdDetailList_Form.fdDeptName }" />
			         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdWbsId" value="${fdDetailList_Form.fdWbsId }" />
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdWbsName" value="${fdDetailList_Form.fdWbsName }" />
			        	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInnerOrderName" value="${fdDetailList_Form.fdInnerOrderName }" />
			         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInnerOrderId" value="${fdDetailList_Form.fdInnerOrderId }" /> 
			        	 <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartDate" value="${fdDetailList_Form.fdStartDate }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdHappenDate" value="${fdDetailList_Form.fdHappenDate }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdTravelDays" value="${fdDetailList_Form.fdTravelDays }" />
			        	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartPlaceId" value="${fdDetailList_Form.fdStartPlaceId }" />
		             	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartPlace" value="${fdDetailList_Form.fdStartPlace }" />
			         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdArrivalPlaceId" value="${fdDetailList_Form.fdArrivalPlaceId }" />
		             	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdArrivalPlace" value="${fdDetailList_Form.fdArrivalPlace }" />
			         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBerthId" value="${fdDetailList_Form.fdBerthId }" /> 
		             	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBerthName" value="${fdDetailList_Form.fdBerthName }" /> 
		             	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdVehicleName" value="${fdDetailList_Form.fdVehicleName }" />
			        	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdRealUserId" value="${fdDetailList_Form.fdRealUserId }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdRealUserName" value="${fdDetailList_Form.fdRealUserName }" /> 
				         <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdPersonNumber" value="${fdDetailList_Form.fdPersonNumber }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="${fdDetailList_Form.fdApplyMoney }" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInvoiceMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdInvoiceMoney }" pattern="0.00"/>" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdNonDeductMoney }" pattern="0.00"/>" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdIsDeduct" value="${fdDetailList_Form.fdIsDeduct }" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdInputTaxMoney }"  pattern="0.00"/>" />         
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdNoTaxMoney }"  pattern="0.00"/>" />  
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdTravel" value="${fdDetailList_Form.fdTravel }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdUse" value="${fdDetailList_Form.fdUse }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempId" value="${fdDetailList_Form.fdExpenseTempId }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempDetailIds" value="${fdDetailList_Form.fdExpenseTempDetailIds }" />
							<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectId" value="${fdDetailList_Form.fdProjectId }" />
							<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectName" value="${fdDetailList_Form.fdProjectName }" />
			        </td>
			         <li class="ld-notSubmit-list-item">
	                    <div class="ld-newApplicationForm-travelInfo-top">
	                    		<div class="icon_type">
	                             <img src="${LUI_ContextPath}/fssc/mobile/resource/images/taxi.png" alt="" >
	                            <span class="fdExpenseItemName">${fdDetailList_Form.fdExpenseItemName }</span>
	                        </div>
                            		<div class="ld-newApplicationForm-travelInfo-top-status">
	                            		<c:if test="${empty  fdDetailList_Form.fdFeeStatus or  fdDetailList_Form.fdFeeStatus eq '0'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-0" >无申请</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdFeeStatus eq '1'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-1" >申请内</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdFeeStatus eq '2'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-2" >超申请</span>
	                            		</c:if>
	                            		<c:if test="${empty  fdDetailList_Form.fdBudgetStatus or  fdDetailList_Form.fdBudgetStatus eq '0'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-0" >无预算</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdBudgetStatus eq '1'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-1" >预算内</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdBudgetStatus eq '2'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-2" >超预算</span>
	                            		</c:if>
		                            <fssc:checkVersion version="true">
		                                <c:if test="${fdDetailList_Form.fdStandardStatus=='2' }">
		                                <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-2" >超标准</span>
			                            </c:if>
			                             <c:if test="${fdDetailList_Form.fdStandardStatus=='1' }">
			                               <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-1" >标准内</span>
			                            </c:if>
			                             <c:if test="${empty fdDetailList_Form.fdStandardStatus or fdDetailList_Form.fdStandardStatus=='0' }">
			                               <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-0" >无标准</span>
			                            </c:if>
									</fssc:checkVersion>
								</div>
	                        <input type="hidden" name="detailId" value="${vstatus.index}" />
	                        <i class="viewInvoiceInfo" onclick="viewInvoiceInfo();" style="right:0;">&nbsp;</i>
	                    </div>
	                    <div class="ld-notSubmit-list-bottom">
	                        <div class="ld-notSubmit-list-bottom-info">
	                            <div>
	                                <span name="fdDetailList_span[${vstatus.index}].fdHappenDate"> ${fdDetailList_Form.fdHappenDate }</span>
	                                <span class="ld-verticalLine"></span>
	                                <span  name="fdDetailList_span[${vstatus.index}].fdRealUserName">${fdDetailList_Form.fdRealUserName }</span>
	                            </div>
	                             <div>
	                           	 <span  name="fdDetailList_span[${vstatus.index}].fdApplyMoney">${fdDetailList_Form.fdApplyMoney }</span>
	                           	 <span></span>
	                           	 </div>
	                        </div>
	                        <c:if test="${not empty fdDetailList_Form.fdUse   }">
	                        		<p name="fdDetailList_span[${vstatus.index}].fdUse">事由：${fdDetailList_Form.fdUse }</p>
	                        </c:if>
	                    </div>
                      </li>
			        </tr>
			      </c:forEach>
			</table>        
            </ul>
             <div class="ld-newApplicationForm-info">
              <div>
                  <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscExpenseMain.fdTotalStandaryMoney')}:</span>
                   <input readonly="" type="text" validate="required"  name="fdTotalStandaryMoney"  value="${fsscExpenseMainForm.fdTotalStandaryMoney }" __validate_serial="_validate_1">
                   <input name="fdTotalApprovedMoney" value="${fsscExpenseMainForm.fdTotalApprovedMoney }" hidden='true' >
               </div>
             </div>
        </div>
        
        <div class="ld-line20px"></div>
        
        <!-- 发票明细-->
        <div class="ld-addInvioce-list">
            <div class="ld-addInvioce-list-title">
                <h3>${lfn:message('fssc-expense:table.fsscExpenseInvoiceDetail') }</h3>
                <i></i>
            </div>
            <ul id="fdInvoiceListId">
            
             <table   id="TABLE_DocList_fdInvoiceList_Form" align="center"  style="width:100%;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td KMSS_IsRowIndex='1' style="width:100%;">
			            !{index}
			       </td>
			        <td >
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceNumber" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdExpenseTypeId" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdExpenseTypeName" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceTypeName" value=""/>
			          <xform:select property="fdInvoiceList_Form[!{index}].fdInvoiceType" showStatus="edit">
                          <xform:enumsDataSource enumsType="fssc_invoice_type" />
                      </xform:select>
			        </td>
			        <td>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdIsVat" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceCode" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdCheckCode" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceDate" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdTax" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdTaxMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdNoTaxMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdCheckStatus" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdState" value=""/>
			        </td>
			      </tr>
			       <c:forEach items="${fsscExpenseMainForm.fdInvoiceList_Form}" var="fdInvoiceList_Form" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
       			 	 	<td >
			           		${vstatus.index+1}
			       		</td>
			           <td>
			               	<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" value="${fdInvoiceList_Form.fdInvoiceMoney}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" value="${fdInvoiceList_Form.fdInvoiceNumber}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId" value="${fdInvoiceList_Form.fdExpenseTypeId}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeName" value="${fdInvoiceList_Form.fdExpenseTypeName}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceTypeName" value=""/>
			          		<xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType"   showStatus="edit">
	                          <xform:enumsDataSource enumsType="fssc_invoice_type" />
	                       </xform:select>
			           </td>
			           <td>
			           		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdIsVat" value="${fdInvoiceList_Form.fdIsVat}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" value="${fdInvoiceList_Form.fdInvoiceCode}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCheckCode" value="${fdInvoiceList_Form.fdCheckCode}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdPurchName" value="${fdInvoiceList_Form.fdPurchName}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" value="${fdInvoiceList_Form.fdTaxNumber}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" value="${fdInvoiceList_Form.fdInvoiceDate}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTax" value="${fdInvoiceList_Form.fdTax}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" value="${fdInvoiceList_Form.fdTaxMoney}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney" value="${fdInvoiceList_Form.fdNoTaxMoney}"/>
					        <input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCheckStatus" value="${fdInvoiceList_Form.fdCheckStatus}"/>
					        <input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdState" value="${fdInvoiceList_Form.fdState}"/>
			           </td>
			        </tr>
			        <li>
                    <div class="ld-newApplicationForm-invioce-top">
                        <div>
                            <img src="${LUI_ContextPath}/fssc/mobile/resource/images/specialTicket.png" alt="">
                             <xform:select property="fdInvoiceList_span[${vstatus.index}].fdInvoiceType"   value="${fdInvoiceList_Form.fdInvoiceType}" showStatus="view">
	                          <xform:enumsDataSource enumsType="fssc_invoice_type" />
	                       </xform:select>
	                       <c:if test="${fdInvoiceList_Form.fdCheckStatus==0}">
	                       	<span class="ld-newApplicationForm-travelInfo-top-buget-0">${lfn:message('fssc-mobile:invoice.satuts.0') }</span>
	                       </c:if>
	                       <c:if test="${fdInvoiceList_Form.fdCheckStatus==1}">
								<span class="ld-newApplicationForm-travelInfo-top-buget-1">${lfn:message('fssc-mobile:invoice.satuts.1') }</span>
	                       </c:if>
							<c:if test="${not empty fdInvoiceList_Form.fdIsCurrent}">
								<span class="ld-newApplicationForm-travelInfo-top-buget-${fdInvoiceList_Form.fdIsCurrent}">${lfn:message('fssc-mobile:invoice.is.current') }</span>
							</c:if>
                            <input name='fdInvoiceList_span[${vstatus.index}].invioceId' value="${vstatus.index}" hidden='true'>
                        </div>
                    </div>
                    <div class="ld-newApplicationForm-invioce-bottom" onclick="editInvioce(${vstatus.index})" >
                        <span  name='fdInvoiceList_span[${vstatus.index}].fdInvoiceNumber'>${fdInvoiceList_Form.fdInvoiceNumber}</span>
                        <div>
                          <span  name='fdInvoiceList_span[${vstatus.index}].fdInvoiceMoney'>${fdInvoiceList_Form.fdInvoiceMoney}</span>
                          <span></span>
                        </div>
                    </div>
                	</li>
			       </c:forEach>
             	</table>
             </ul>
        </div>
        
        <!-- 收款账户 -->
        <div class="ld-line20px"></div>
        <div class="ld-addAccount-list">
            <div class="ld-newApplicationForm-attach-title">
                <h3>${lfn:message('fssc-expense:table.fsscExpenseAccounts') }</h3>
                <i></i>
            </div>
            
            <ul id="fdAccountsListId" >
             <table   id="TABLE_DocList_fdAccountsList_Form" align="center"  style="width:100%;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td KMSS_IsRowIndex='1' style="width:100%;">
			            !{index}
			       </td>
			        <td >
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankId" value=""/>
			            <input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankName" value=""/>
			        </td>
			        <td>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdExchangeRate" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdPayWayName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdPayWayId" value=""/>
			        </td>
			        <td>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdCurrencyName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdCurrencyId" value=""/>
			        </td>
			        <td>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdAccountId" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdAccountName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankAccount" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdMoney" value=""/>
			        </td>
			      </tr>
			      <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_Form" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
       			 	 	<td>
			           		${vstatus.index+1}
			       		</td>
			            <td>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankId" value="${fdAccountsList_Form.fdBankId}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankName" value="${fdAccountsList_Form.fdBankName}"/>
			      		</td>
			      		<td>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" value="${fdAccountsList_Form.fdExchangeRate}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdPayWayName" value="${fdAccountsList_Form.fdPayWayName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdPayWayId" value="${fdAccountsList_Form.fdPayWayId}"/>
			      		</td>
			      		<td>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyName" value="${fdAccountsList_Form.fdCurrencyName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" value="${fdAccountsList_Form.fdCurrencyId}"/>
			      		</td>
			      		<td>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountName" value="${fdAccountsList_Form.fdAccountName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountId" value="${fdAccountsList_Form.fdAccountId}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankAccount" value="${fdAccountsList_Form.fdBankAccount}"/>
			        	  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" value="${fdAccountsList_Form.fdAccountAreaName}"/>
			        	   <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdMoney" value="${fdAccountsList_Form.fdMoney}"/>
			      		</td>
			      	</tr>
			      	<li>
	                    <div class="ld-newApplicationForm-account-top">
	                        <div>
	                            <img src="${LUI_ContextPath}/fssc/mobile/resource/images/collectionAccount.png" alt="">
	                            <span name="fdAccountsList_span[${vstatus.index}].accountName">${fdAccountsList_Form.fdAccountName}</span>
	                        </div>
	                         <input name="fdAccountsList_span[${vstatus.index}].accountId" value="${vstatus.index}" hidden='true'>
	                    </div>
	                    <div class="ld-newApplicationForm-account-bottom" onclick="editAccount(${vstatus.index})">
	                        <div>
	                            <p  name="fdAccountsList_span[${vstatus.index}].fdBankAccount">${fdAccountsList_Form.fdBankAccount}</p>
	                        </div>
	                        <div>
	                            <span>${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney') }：</span>
	                            <div>
	                            	<span name="fdAccountsList_span[${vstatus.index}].fdMoney">${fdAccountsList_Form.fdMoney}</span>
	                            	<span></span>
	                            </div>
	                        </div>
	                    </div>
	                </li>
			      </c:forEach>
			  </table>
            </ul>
        </div>
        
        <!-- 冲抵借款明细 -->
		<c:if test="${offsetMoney >0 }">
        <div class="ld-line20px"></div>
        		<div class="ld-addAccount-list" style="height: 1rem;padding: 0 0.3rem;line-height: 1rem;">
	            <div style="height: 1rem;float: left;">${lfn:message('fssc-expense:fsscExpenseOffsetLoan.isOffsetLoan')}</div>
				
	        		<div class="checkbox_item ${fsscExpenseMainForm.fdIsOffsetLoan?'':'checked' }" style="height: 1rem;float: right;">
	        			<div class="checkbox_item_out">
	        				<div class="checkbox_item_in"></div>
	        			</div>
	        			<div class="checkbox_item_text">否</div>
	        			<input type="radio" name="_fdIsOffsetLoan" value="false">
	        		</div>
	        		<div class="checkbox_item ${fsscExpenseMainForm.fdIsOffsetLoan?'checked':'' }" style="height: 1rem;float: right;">
	        			<div class="checkbox_item_out">
	        				<div class="checkbox_item_in"></div>
	        			</div>
	        			<div class="checkbox_item_text">是</div>
	        			<input type="radio" name="_fdIsOffsetLoan" value="true">
	        		</div>
        		</div>
			<div class="ld-addAccount-list"  style="padding:0 0.3rem 0.3rem 0.3rem;">
            <ul id="fdOffsetListId" >
             <table   id="TABLE_DocList_fdOffsetList_Form" align="center"  style="width:100%;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td KMSS_IsRowIndex='1' style="width:100%;">
			            !{index}
			       </td>
			        <td >
			        	<input  type="hidden" name="fdOffsetList_Form[!{index}].fdId" value=""/>
			        	<input  type="hidden" name="fdOffsetList_Form[!{index}].fdLoanId" value=""/>
			            <input  type="hidden" name="fdOffsetList_Form[!{index}].docSubject" value=""/>
			        </td>
			      	<td>
		      			<input type="hidden" name="fdOffsetList_Form[!{index}].fdNumber" value="" />
	                	<input type="hidden" name="fdOffsetList_Form[!{index}].fdLoanMoney" value="" />
	                	<input type="hidden" name="fdOffsetList_Form[!{index}].fdLeftMoney" value="" />
		      		</td>
		      		<td>
		      			<input  type="hidden" name="fdOffsetList_Form[!{index}].fdOffsetMoney" value=""/>
		      			<input  type="hidden" name="fdOffsetList_Form[!{index}].fdCanOffsetMoney" value=""/>
		      		</td>
			      </tr>
			      <c:forEach items="${fsscExpenseMainForm.fdOffsetList_Form}" var="fdOffsetList_FormItem" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
       			 	 	<td>
			           		${vstatus.index+1}
			       		</td>
			            <td>
			            	<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdId" value="${fdOffsetList_FormItem.fdId}"/>
			              	<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanId" value="${fdOffsetList_FormItem.fdLoanId}"/>
			              	<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].docSubject" value="${fdOffsetList_FormItem.docSubject}"/>
			      		</td>
			      		<td>
			      			<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdNumber" value="${fdOffsetList_FormItem.fdNumber}" />
		                	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanMoney" value="${fdOffsetList_FormItem.fdLoanMoney}" />
		                	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLeftMoney" value="${fdOffsetList_FormItem.fdLeftMoney}" />
			      		</td>
			      		<td>
			      			<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" value="${fdOffsetList_FormItem.fdOffsetMoney}"/>
			      			<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" value="${fdOffsetList_FormItem.fdCanOffsetMoney}"/>
			      		</td>
			      	<c:if test="${fsscExpenseMainForm.fdIsOffsetLoan && fdOffsetList_FormItem.fdOffsetMoney!=null}">
			      	<li onclick="editLoanOffset(${vstatus.index})">
					  	<div class="ld-newApplicationForm-loan-top">
						  	<div>
						  		<div><img src="${LUI_ContextPath }/fssc/mobile/resource/images/specialTicket.png" alt="">
						  		<span name="fdOffsetList_span[${vstatus.index}].docSubject" class="subject">${fdOffsetList_FormItem.docSubject}</span>
						  	</div>
						  	<div>
						  		<span class="ld-newApplicationForm-loan-top-div">${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}:</span>
						  		<span name="fdOffsetList_span[${vstatus.index}].fdLoanMoney">${fdOffsetList_FormItem.fdLoanMoney}</span>
						  		<span></span>
						  	</div>
						  	</div>
					  	</div>
					  	<div class="ld-newApplicationForm-loan-bottom">
						  	<div><p name="fdOffsetList_span[${vstatus.index}].fdNumber">${fdOffsetList_FormItem.fdNumber}</p></div>
						  	<div><span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}：</span><span name="fdOffsetList_span[0].fdCanOffsetMoney">${fdOffsetList_FormItem.fdCanOffsetMoney}</span><span>
						  		</span>&nbsp;&nbsp;<span>${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}:</span>
						  		<span name="fdOffsetList_span[${vstatus.index}].fdOffsetMoney">${fdOffsetList_FormItem.fdOffsetMoney}</span>
						  		<span></span>
						  		<input name="fdOffsetList_span[${vstatus.index}].loanId" value="${fdOffsetList_FormItem.fdLoanId}" hidden="true">
						  	</div>
					  	</div>
					  	</li>
					    </c:if>
			      	</tr>
			      </c:forEach>
			  </table>
           	</ul>
        </div>
		</c:if>
		
		<kmss:ifModuleExist path="/fssc/pres/">
			<c:if test="${showPres=='true' }">
				<div class="ld-line20px"></div>
				<div class="ld-newApplicationForm-costInfo">
					<div class="ld-newApplicationForm-travelInfo-title">
						<div onclick="openPresData();">
							<span>${lfn:message('fssc-pres:module.fssc.pres.openPresData') }</span>
						</div>
					</div>
				</div>
			</c:if>
		</kmss:ifModuleExist>

		<!-- 交易数据明细 -->
		<kmss:ifModuleExist path="/fssc/ccard/">
		<c:if test="${not empty fsscExpenseMainForm.fdTranDataList_Form }">
			<div class="ld-line20px"></div>
			<div class="ld-addTranData-list">
				<div class="ld-newApplicationForm-attach-title">
					<h3>${lfn:message('fssc-expense:table.fsscExpenseTranData') }</h3>
					<i></i>
				</div>
				<ul id="fdTranDataListId" >
					<table id="TABLE_DocList_fdTranDataList_Form" align="center"  style="width:100%;">
						<c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_Form" varStatus="vstatus">
							<li>
								<div class="ld-newApplicationForm-account-top">
									<div>
										<img src="${LUI_ContextPath}/fssc/mobile/resource/images/persionBorrow.png" alt="">
										<span name="fdTranDataList_span[${vstatus.index}].fdActChiNam">${fdTranDataList_Form.fdActChiNam}</span>
										<span class="ld-verticalLine"></span>
										<span name="fdTranDataList_span[${vstatus.index}].fdTrsDte">${fdTranDataList_Form.fdTrsDte}</span>
									</div>
									<div>
										<xform:select property="fdTranDataList_span[${vstatus.index}].fdTrsCod" value="${fdTranDataList_Form.fdTrsCod}" showStatus="view">
											<xform:enumsDataSource enumsType="fssc_tran_data_trsCod" />
										</xform:select>
										<span></span>
									</div>
									<input name="fdTranDataList_span[${vstatus.index}].fdTranDataId" value="${fdTranDataList_Form.fdTranDataId}" hidden='true'>
									<input name="fdTranDataList_span[${vstatus.index}].fdState" value="${fdTranDataList_Form.fdState}" hidden='true'>
								</div>
								<div class="ld-newApplicationForm-account-bottom">
									<div>
										<span name="fdTranDataList_span[${vstatus.index}].fdCrdNum">${fdTranDataList_Form.fdCrdNum}</span>
									</div>
									<div>
										<span>${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurAmt') }：</span>
										<div>
											<span name="fdTranDataList_span[${vstatus.index}].fdOriCurAmt">${fdTranDataList_Form.fdOriCurAmt}</span>
											<span></span>
										</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</table>
				</ul>
			</div>
		</c:if>
		</kmss:ifModuleExist>

        <div class="ld-line20px"></div>
         
 		<!-- 附件明细 -->
        <div class="ld-newApplicationForm-attach">
            <div class="ld-newApplicationForm-attach-title">
                <h3>${lfn:message('fssc-expense:fsscExpenseMain.attachment')}</h3>
                <i></i>
            </div>
            <ul id="fdAttachListId" >
                <!-- 附件明细 -->
               <c:forEach items="${fdAttData}" var="list"  varStatus="status">
                   <li>
	                    <div class="ld-remember-attact-info" onclick="showAtt('${list.fdId}','${list.fileName}');">
	                        <img src="${LUI_ContextPath }/sys/attachment/view/img/file/2x/icon_${list.icon}.png" alt="" />
	                        <span style="color:#434343;">${list.fileName}</span>
	                       <input name="attId" hidden='true' value="${list.fdId}">
	                    </div>
	                </li>
                </c:forEach>
            </ul>
        </div>
        <kmss:auth requestURL="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=edit&fdId=${fsscExpenseMainForm.fdId }">
        <c:if test="${fsscExpenseMainForm.docStatus =='10' or fsscExpenseMainForm.docStatus =='11' }">
        	<div class="editModel" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=edit&fdId=${fsscExpenseMainForm.fdId }'"></div>
        </c:if>
        </kmss:auth>
    </div> 
    <div class="backHome" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/mobile/'"></div>

  <!-- 明细编辑模板 -->
 <c:import url="/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_view_detail.jsp"></c:import>

<input  hidden="true" value='${fdCompanyData}' name="fdCompanyData" >
<input  hidden="true" value="${fsscExpenseMainForm.docCreatorId }" name="docCreatorId">
<input  hidden="true" value="${parentId}" name="parentId" >
<input  hidden="true" value="${fsscExpenseMainForm.fdId}" name="fdId" >
<input  hidden="true" value="${docTemplate.fdId}" name="fdTemplateId">
<input  hidden="true" value="${docTemplate.fdIsProject}" name="fdIsProject">
<input  hidden="true" value="${docTemplate.fdIsFee}" name="fdIsFee">
<input  hidden="true" value="${docTemplate.fdBudgetShowType}" name="fdBudgetShowType">
<input  hidden="true" value="${docTemplate.fdExpenseType}" name="fdExpenseType">
<input  hidden="true" value="${docTemplate.fdIsTravelAlone}" name="fdIsTravelAlone">
<input type="hidden" name="fdDetailList_Flag" value="1">
<input type="hidden" name="fdTravelList_Flag" value="1">
<input type="hidden" name="fdInvoiceList_Flag" value="1">
<input type="hidden" name="fdAccountsList_Flag" value="1">
<input type="hidden" name="fdOffsetList_Flag" value="1">
<input type="hidden" name="fdAmortizeList_Flag" value="1">
<input type="hidden" name="data" value="${queryList}">
<fssc:checkVersion version="true">
		<input name="checkVersion" value="true"  type="hidden" />
</fssc:checkVersion>
</form>
<!-- 流程处理 -->
        <div class="ld-new-reimbursement-form-progress" style="margin-bottom:20px;">
        	<iframe id="frame" width="100%" frameborder="0"  height="500px" src ="${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=viewLbpm&fdId=${fsscExpenseMainForm.fdId}"></iframe>
        </div>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_view.js"></script>
</body>
<%@ include file="/resource/jsp/edit_down.jsp" %>
