<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ include file="/fssc/mobile/common/organization/organization_include.jsp" %>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>${lfn:message("operation.create")}${lfn:message("fssc-expense:module.fssc.expense")}</title>
    <link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/layer.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/city.css?s_cache=${LUI_Cache }" >
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/notSubmit.css?s_cache=${LUI_Cache }">
	<script src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>

  <script>
    var formInitData = {
    		"LUI_ContextPath":"${LUI_ContextPath}",
    		"fdIsShare":"${docTemplate.fdExpenseType}",
    		"fdIsAuthorize":"${fdIsAuthorize}"
    };
    </script>
    <script type="text/javascript">
		Com_IncludeFile("doclist.js");
		Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
		Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/", 'js', true);
		Com_IncludeFile("kk-1.2.73.min.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
		Com_IncludeFile("bmap.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
	</script>
</head>
<body>
<form action="${LUI_ContextPath }/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do"  name="fsscExpenseMainForm" method="post">
 <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-header">
        
            <div class="ld-newApplicationForm-header-title">
           		 <c:if test="${docTemplate.fdSubjectType=='1' }">
                <input type="text" placeholder="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" value="${fsscExpenseMainForm.docSubject }" name="docSubject">
                </c:if>
                <c:if test="${docTemplate.fdSubjectType=='2' }">
                 <input type="text" placeholder="${lfn:message('fssc-expense:py.BianHaoShengCheng') }" name=""docSubject value="${fsscExpenseMainForm.docSubject }" readonly="readonly" >
                </c:if>
            </div>
            <div class="ld-newApplicationForm-header-desc">
                <input type="text" placeholder="${lfn:message('fssc-mobile:fsscExpenseMain.InputfdContent')}" value="${fsscExpenseMainForm.fdContent }" name="fdContent">
            </div>
        </div>
        <div class="ld-newApplicationForm-info">
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdClaimant")}</span>
                <div class="ld-selectPersion">
                	<!-- 启用授权 -->
                	<c:if test="${fdIsAuthorize=='true'}">
                		<input type="text" placeholder='${lfn:message("fssc-expense:fsscExpenseMain.fdClaimant")}' name="fdClaimantName"  onclick="selectObject('fdClaimantId','fdClaimantName',formOption['url']['getFdClaimant'],changeFdClaimant);" value="${fsscExpenseMainForm.fdClaimantName }" readonly="readonly" >
                	</c:if>
                	<!-- 未启用授权 -->
                	<c:if test="${fdIsAuthorize=='false' or empty fdIsAuthorize}">
                		<input type="text" placeholder='${lfn:message("fssc-expense:fsscExpenseMain.fdClaimant")}' name="fdClaimantName"  onclick="selectOrgElement('fdClaimantId','fdClaimantName','${parentId}','false','person',changeFdClaimant);" value="${fsscExpenseMainForm.fdClaimantName }" readonly="readonly" >
                	</c:if>
                     <input name='fdClaimantId' id="fdClaimantId" value="${fsscExpenseMainForm.fdClaimantId }" hidden='true'/>
                     <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdCompany")}</span>
                <div>
                     <input type="text" id="fdCompanyName" name="fdCompanyName" value="${fsscExpenseMainForm.fdCompanyName }" readonly="readonly" onclick="selectObject('fdCompanyId','fdCompanyName',formOption['url']['getFsCompany'],clearDetailWhenCompanyChanged);" >
                      <input type="text" id="fdCompanyId" name="fdCompanyId"  value="${fsscExpenseMainForm.fdCompanyId }" hidden='true' >
                      <input type="text" id="fdCompanyIdOld" name="fdCompanyIdOld"  value="${fsscExpenseMainForm.fdCompanyId }" hidden='true' >
                      <input type="text" id="fdCompanyNameOld" name="fdCompanyNameOld"  value="${fsscExpenseMainForm.fdCompanyName }" hidden='true' >
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdCostCenter")}</span>
                <div>
                    <input type="text"  id="fdCostCenterName" placeholder="请选择成本中心" name="fdCostCenterName" value="${fsscExpenseMainForm.fdCostCenterName }"  readonly="readonly" onclick="selectObject('fdCostCenterId','fdCostCenterName',formOption['url']['getEopBasedataCostCenter'],afterSelectCostCenter);" >
                    <input id="fdCostCenterId" name="fdCostCenterId" value="${fsscExpenseMainForm.fdCostCenterId }"  hidden='true'>
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
			<c:if test="${docTemplate.fdIsProject=='true'&&(docTemplate.fdIsProjectShare=='false' or empty docTemplate.fdIsProjectShare) }">
	            <div>
	                 <span>${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}</span>
	                <div>
	                    <input type="text" placeholder="${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-expense:fsscExpenseMain.fdProject")}"  id="fdProject" name="fdProject" value="${fsscExpenseMainForm.fdProjectName }" readonly="readonly" onclick="selectObject('fdProjectId','fdProject',formOption['url']['getFdProject'],afterSelectProject);" >
	                    <input id="fdProjectId" name="fdProjectId" value="${fsscExpenseMainForm.fdProjectId }"  hidden='true'>
	                    <span style="margin-left:2px;color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
            </c:if>
              <c:if test="${docTemplate.fdIsFee=='true' }">
	            <div>
	               <span>${lfn:message("fssc-expense:fsscExpenseMain.fdFeeNames")}</span>
	                <div>
	                    <input type="text" id="fdFeeNames" placeholder="请选择申请单" name="fdFeeNames" value="${fsscExpenseMainForm.fdFeeNames }" readonly="readonly" onclick="selectObject('fdFeeIds','fdFeeNames',formOption['url']['getFeeMain']);">
	                     <input id="fdFeeIds"  name="fdFeeIds" value="${fsscExpenseMainForm.fdFeeIds }"  hidden='true'>
	                    <span style="margin-left:2px;color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
	             <div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdIsCloseFee")}</span>
	                <%-- <div>
	                   <xform:checkbox property="fdIsCloseFee"  onValueChange="checkFeeRelation">
								<xform:simpleDataSource value="1">是</xform:simpleDataSource>
						</xform:checkbox>
	                </div> --%>
	                <div>
	                		<div class="checkbox_item ${fsscExpenseMainForm.fdIsCloseFee=='true'?'checked':'' }">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">是</div>
	                			<input type="radio" name="_fdIsCloseFee" value="true">
	                		</div>
	                		<div class="checkbox_item ${(fsscExpenseMainForm.fdIsCloseFee==''||fsscExpenseMainForm.fdIsCloseFee=='false')?'checked':'' }">
	                			<div class="checkbox_item_out">
	                				<div class="checkbox_item_in"></div>
	                			</div>
	                			<div class="checkbox_item_text">否</div>
	                			<input type="radio" name="_fdIsCloseFee" value="false">
	                		</div>
	                		<input type="hidden" name="fdIsCloseFee" value="${fsscExpenseMainForm.fdIsCloseFee }" onclick="checkFeeRelation();">
	                </div>
	            </div>
            </c:if>
			<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'8')>-1 }">
				<div>
					<span>${lfn:message("fssc-expense:fsscExpenseMain.fdProjectAccounting")}</span>
					<div>
						<input type="text" id="fdProjectAccountingName" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-expense:fsscExpenseMain.fdProjectAccounting')}" name="fdProjectAccountingName" value="${fsscExpenseMainForm.fdProjectAccountingName }" readonly="readonly" onclick="selectObject('fdProjectAccountingId','fdProjectAccountingName',formOption['url']['getAccountProject'],null,{fdType:'2'});">
						<input id="fdProjectAccountingId"  name="fdProjectAccountingId" value="${fsscExpenseMainForm.fdProjectAccountingId }"  hidden='true'>
						<span style="margin-left:2px;color:#d02300;">*</span>
						<i></i>
					</div>
				</div>
			</c:if>
			<div>
	                <span>${lfn:message("fssc-expense:fsscExpenseMain.fdAttNumber")}</span>
	                <div>
	                   <input name="fdAttNumber" placeholder="请输入附件张数" value="${fsscExpenseMainForm.fdAttNumber}"/>
	                   <span style="margin-left:2px;color:#d02300;">*</span>
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
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%"  >
			        <td>
			           	<input type="hidden" name="fdTravelList_Form[!{index}].fdTravelId" value="" />
			           	<input type="hidden" name="fdTravelList_Form[!{index}].fdTravelDays" value="" />
                       	<input type="hidden" name="fdTravelList_Form[!{index}].fdBeginDate" value="" />
                       	<input type="hidden" name="fdTravelList_Form[!{index}].fdEndDate" value="" />
			            <input type="hidden" name="fdTravelList_Form[!{index}].fdStartPlace" value="" />
                        <input type="hidden" name="fdTravelList_Form[!{index}].fdArrivalId" value="" />
                        <input type="hidden" name="fdTravelList_Form[!{index}].fdArrivalPlace" value="" />
                        <input type="hidden" name="fdTravelList_Form[!{index}].fdVehicleId" value="" />
                        <input type="hidden" name="fdTravelList_Form[!{index}].fdVehicleName" value="" />
                        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
	                        <input type="hidden" name="fdTravelList_Form[!{index}].fdBerthName" value="" />
				          	<input type="hidden" name="fdTravelList_Form[!{index}].fdBerthId" value="" />
			          	</c:if>
                       	<input type="hidden" name="fdTravelList_Form[!{index}].fdSubject" value="" />
                       	<input type="hidden" name="fdTravelList_Form[!{index}].fdPersonListIds" value="" />
                       	<input type="hidden" name="fdTravelList_Form[!{index}].fdPersonListNames" value="" />
                       	<input type="hidden" name="fdTravelList_Form[!{index}].trafficTools" value="" />
			       </td>
       			 </tr>
       			 <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
			           <td>
			             <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdId" value="${fdTravelList_FormItem.fdId}" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdTravelDays" value="${fdTravelList_FormItem.fdTravelDays }" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBeginDate" value="${fdTravelList_FormItem.fdBeginDate }" />
                         <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdEndDate" value="${fdTravelList_FormItem.fdEndDate }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdStartPlace" value="${fdTravelList_FormItem.fdStartPlace }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdArrivalId" value="${fdTravelList_FormItem.fdArrivalId }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdArrivalPlace" value="${fdTravelList_FormItem.fdArrivalPlace }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdVehicleId" value="${fdTravelList_FormItem.fdVehicleId }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdVehicleName" value="${fdTravelList_FormItem.fdVehicleName }" />
                        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
	                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthName" value="${fdTravelList_FormItem.fdBerthName }" />
	                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthId" value="${fdTravelList_FormItem.fdBerthId }" />
                        </c:if>
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdSubject" value="${fdTravelList_FormItem.fdSubject }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdPersonListIds" value="${fdTravelList_FormItem.fdPersonListIds }" />
                        <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdPersonListNames" value="${fdTravelList_FormItem.fdPersonListNames }" />
                       </td>
       			 </tr>
       			</c:forEach>
            </table> 
            <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
            	<li onclick="editTravelDetail()">
                   <div class="ld-newApplicationForm-trip-top">
                       <div>
                           <img src="${LUI_ContextPath}/fssc/mobile/resource/images/train.png" alt=""><span name="travelSubject">${fdTravelList_FormItem.fdSubject }</span>
                       </div>
                       <input name='travelId' value="${vstatus.index}" hidden='true'>
                       <span onclick="deleteTravelDetail()"></span>
                   </div>
                   <div class="ld-newApplicationForm-trip-bottom">
                       <div>
                           <span class="fdStartPlace" >${fdTravelList_FormItem.fdStartPlace }</span>
                           <span class="fdBerthName">${fdTravelList_FormItem.fdBerthName }</span>
                           <span class="fdArrivalName">${fdTravelList_FormItem.fdArrivalPlace }</span>
                       </div>
                       <div>
                           <span class="fdBeginDate">${fdTravelList_FormItem.fdBeginDate }</span>
                           <span class="fdPersonListNames">${fdTravelList_FormItem.fdPersonListNames }</span>
                           <span class="fdEndDate">${fdTravelList_FormItem.fdEndDate }</span>
                       </div>
                   </div>
                 </li>
            </c:forEach>
           </ul>
            <div class="ld-newApplicationForm-trip-btn" onclick="addTravelDetail()">
                <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAtrip')} </span>
            </div>
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
            <table   id="TABLE_DocList_fdDetailList_Form" align="center"  style="width:100%;" style="display:none;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			        <td >
		       			<input type="hidden" name="fdDetailList_Form[!{index}].fdId" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdApprovedApplyMoney" value="" />
	            		<input type="hidden" name="fdDetailList_Form[!{index}].fdApprovedStandardMoney" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdCurrencyId" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdCurrencyName" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdExchangeRate" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdStandardMoney" value="" />
			            <input type="hidden" name="fdDetailList_Form[!{index}].fdCompanyId" value="" />
		                <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetInfo" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetStatus" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdFeeInfo" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdFeeStatus" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdStandardStatus" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdStandardInfo" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetMoney" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetRate" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdInputTaxRate" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseItemName" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseItemId" value="" />
			         	<input type="hidden" name="fdDetailList_Form[!{index}].fdCostCenterId" value="" />
			            <input type="hidden" name="fdDetailList_Form[!{index}].fdCostCenterName" value="" /> 
			        	<input type="hidden" name="fdDetailList_Form[!{index}].fdDeptId" value="" /> 
			            <input type="hidden" name="fdDetailList_Form[!{index}].fdDeptName" value="" />
			         	<input type="hidden" name="fdDetailList_Form[!{index}].fdWbsId" value="" />
			            <input type="hidden" name="fdDetailList_Form[!{index}].fdWbsName" value="" />
			        	<input type="hidden" name="fdDetailList_Form[!{index}].fdInnerOrderName" value="" />
			         	<input type="hidden" name="fdDetailList_Form[!{index}].fdInnerOrderId" value="" /> 
			        	 <input type="hidden" name="fdDetailList_Form[!{index}].fdStartDate" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdHappenDate" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdTravelDays" value="" />
			        	<input type="hidden" name="fdDetailList_Form[!{index}].fdStartPlaceId" value="" />
		             	<input type="hidden" name="fdDetailList_Form[!{index}].fdStartPlace" value="" />
			         	<input type="hidden" name="fdDetailList_Form[!{index}].fdArrivalPlaceId" value="" />
		             	<input type="hidden" name="fdDetailList_Form[!{index}].fdArrivalPlace" value="" />
		             	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
				         	<input type="hidden" name="fdDetailList_Form[!{index}].fdBerthId" value="" /> 
			             	<input type="hidden" name="fdDetailList_Form[!{index}].fdBerthName" value="" /> 
		             	</c:if>
			        	<input type="hidden" name="fdDetailList_Form[!{index}].fdRealUserId" value="" />
		            	<input type="hidden" name="fdDetailList_Form[!{index}].fdRealUserName" value="" /> 
		            	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }"> 
				         	<input type="hidden" name="fdDetailList_Form[!{index}].fdPersonNumber" value="" />
				         </c:if>
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdApplyMoney" value="" /> 
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdInvoiceMoney" value="" /> 
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdNonDeductMoney" value="" /> 
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdIsDeduct" value="" /> 
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdInputTaxMoney" value="" />         
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdNoTaxMoney" value="" />  
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdTravel" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdUse" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdNoteId" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseTempId" value="" />
			             <input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseTempDetailIds" value="" />
						<input type="hidden" name="fdDetailList_Form[!{index}].fdDayCalType" value="" />
						<input type="hidden" name="fdDetailList_Form[!{index}].fdProjectId" value="" />
						<input type="hidden" name="fdDetailList_Form[!{index}].fdProjectName" value="" />
			        </td>
       			 </tr>
       			 
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
			            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardInfo" value="${fdDetailList_Form.fdStandardInfo }" />
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
		             	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
				         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBerthId" value="${fdDetailList_Form.fdBerthId }" /> 
			             	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBerthName" value="${fdDetailList_Form.fdBerthName }" /> 
		             	</c:if>
				        	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdRealUserId" value="${fdDetailList_Form.fdRealUserId }" />
			            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdRealUserName" value="${fdDetailList_Form.fdRealUserName }" /> 
			            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }"> 
				         	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdPersonNumber" value="${fdDetailList_Form.fdPersonNumber }" />
				         </c:if>
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="${fdDetailList_Form.fdApplyMoney }" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInvoiceMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdInvoiceMoney }" pattern="0.00"/>" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdNonDeductMoney }" pattern="0.00"/>" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdIsDeduct" value="${fdDetailList_Form.fdIsDeduct }" /> 
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdInputTaxMoney }"  pattern="0.00"/>"/>         
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" value="<kmss:showNumber value="${fdDetailList_Form.fdNoTaxMoney }"  pattern="0.00"/>"/>  
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdTravel" value="${fdDetailList_Form.fdTravel }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdUse" value="${fdDetailList_Form.fdUse }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoteId" value="${fdDetailList_Form.fdNoteId }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempId" value="${fdDetailList_Form.fdExpenseTempId }" />
			             <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempDetailIds" value="${fdDetailList_Form.fdExpenseTempDetailIds }" />
						 <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdDayCalType" value="${fdDetailList_Form.fdDayCalType }" />
						 <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectId" value="${fdDetailList_Form.fdProjectId }" />
						 <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectName" value="${fdDetailList_Form.fdProjectName }" />
					   </td>
			        </tr>
			      </c:forEach>
			</table>   
				<c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_Form" varStatus="vstatus">
					<li class="ld-notSubmit-list-item" onclick="editExpenseDeteil()">
	                    <div class="ld-newApplicationForm-travelInfo-top">
	                        <div class="icon_type">
	                             <img src="${LUI_ContextPath}/fssc/mobile/resource/images/taxi.png" alt="" >
	                            <span class="fdExpenseItemName">${fdDetailList_Form.fdExpenseItemName }</span>
	                        </div>
                            		<div class="ld-newApplicationForm-travelInfo-top-status">
	                            		<c:if test="${empty  fdDetailList_Form.fdFeeStatus or  fdDetailList_Form.fdFeeStatus eq '0'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-0 feeStatus" >无申请</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdFeeStatus eq '1'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-1 feeStatus" >申请内</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdFeeStatus eq '2'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-2 feeStatus" >超申请</span>
	                            		</c:if>
	                            		<c:if test="${empty  fdDetailList_Form.fdBudgetStatus or  fdDetailList_Form.fdBudgetStatus eq '0'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-0 bugetStatus" >无预算</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdBudgetStatus eq '1'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-1 bugetStatus" >预算内</span>
	                            		</c:if>
	                            		<c:if test="${fdDetailList_Form.fdBudgetStatus eq '2'}">
	                            			<span class="ld-newApplicationForm-travelInfo-top-buget-2 bugetStatus" >超预算</span>
	                            		</c:if>
		                            <fssc:checkVersion version="true">
		                                <c:if test="${fdDetailList_Form.fdStandardStatus=='2' }">
		                                <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-2 fdStandardStatus" >超标准</span>
			                            </c:if>
			                             <c:if test="${fdDetailList_Form.fdStandardStatus=='1' }">
			                               <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-1 fdStandardStatus" >标准内</span>
			                            </c:if>
			                             <c:if test="${empty fdDetailList_Form.fdStandardStatus or fdDetailList_Form.fdStandardStatus=='0' }">
			                               <span  name="fdDetailList_span[${vstatus.index}].bugetStatus" class="ld-newApplicationForm-travelInfo-top-buget-0 fdStandardStatus" >无标准</span>
			                            </c:if>
									</fssc:checkVersion>
								</div>
	                        <i onclick="deleteExpenseDeteil();"></i>
	                        <i class="viewInvoiceInfo" onclick="viewInvoiceInfo();"></i>
	                        <input type="hidden" name="detailId" value="${vstatus.index}" />
	                    </div>
	                    <div class="ld-notSubmit-list-bottom">
	                        <div class="ld-notSubmit-list-bottom-info">
	                            <div>
	                                <span class="fdHappenDate"> ${fdDetailList_Form.fdHappenDate }</span>
	                                <span class="ld-verticalLine"></span>
	                                <span  class="fdRealUserName">${fdDetailList_Form.fdRealUserName }</span>
	                            </div>
	                             <div>
	                           	 <span  class="fdApplyMoney">${fdDetailList_Form.fdApplyMoney }</span>
	                           	 <span></span>
	                           	 </div>
	                        </div>
	                        <p class="fdUse">${fdDetailList_Form.fdUse }</p>
	                    </div>
                      </li>  
                </c:forEach>   
            </ul>
           
            <fssc:checkVersion version="true">
            <div class="ld-newApplicationForm-costInfo-btn" onclick="addExpensePre()">
                <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addExpense')}</span>
            </div>
            </fssc:checkVersion>
           <fssc:checkVersion version="false">
            <div class="ld-newApplicationForm-costInfo-btn" onclick="addExpenseDeteil();">
                <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addExpense')}</span>
            </div>
            </fssc:checkVersion>
             <div class="ld-newApplicationForm-info">
              <div>
                  <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscExpenseMain.fdTotalStandaryMoney')}:</span>
                   <input readonly type="text"  value="<kmss:showNumber value="${fsscExpenseMainForm.fdTotalStandaryMoney}" pattern="0.00"/>" name="fdTotalStandaryMoney" >
                   <input name="fdTotalApprovedMoney" value="<kmss:showNumber value="${fsscExpenseMainForm.fdTotalStandaryMoney}" pattern="0.00"/>" hidden='true' >
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
            
             <table   id="TABLE_DocList_fdInvoiceList_Form" align="center"  style="width:100%;display:none;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td KMSS_IsRowIndex='1' style="width:100%;">
			            !{index}
			       </td>
			        <td >
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdId" value=""/>
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
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdPurchName" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdTaxNumber" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdInvoiceDate" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdTax" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdTaxMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdNoTaxMoney" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdCheckStatus" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdState" value=""/>
			          <input  type="hidden" name="fdInvoiceList_Form[!{index}].fdIsCurrent" value=""/>
			        </td>
			      </tr>
			       <c:forEach items="${fsscExpenseMainForm.fdInvoiceList_Form}" var="fdInvoiceList_Form" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
       			 	 	<td >
			           		${vstatus.index+1}
			       		</td>
			           <td>
			           		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdId" value="${fdInvoiceList_Form.fdId}"/>
			               	<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" value="${fdInvoiceList_Form.fdInvoiceMoney}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" value="${fdInvoiceList_Form.fdInvoiceNumber}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId" value="${fdInvoiceList_Form.fdExpenseTypeId}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeName" value="${fdInvoiceList_Form.fdExpenseTypeName}"/>
			          		<xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType"   value="${fdInvoiceList_Form.fdInvoiceType}" showStatus="edit">
	                          <xform:enumsDataSource enumsType="fssc_invoice_type" />
	                       </xform:select>
			           </td>
			           <td>
			           		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdIsVat" value="${fdInvoiceList_Form.fdIsVat}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" value="${fdInvoiceList_Form.fdInvoiceCode}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCheckCode" value="${fdInvoiceList_Form.fdCheckCode}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdPurchName" value="${fdInvoiceList_Form.fdPurchName}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTaxNumber" value="${fdInvoiceList_Form.fdTaxNumber}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCheckCode" value="${fdInvoiceList_Form.fdCheckCode}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" value="${fdInvoiceList_Form.fdInvoiceDate}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTax" value="${fdInvoiceList_Form.fdTax}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" value="${fdInvoiceList_Form.fdTaxMoney}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney" value="${fdInvoiceList_Form.fdNoTaxMoney}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdCheckStatus" value="${fdInvoiceList_Form.fdCheckStatus}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdState" value="${fdInvoiceList_Form.fdState}"/>
			          		<input  type="hidden" name="fdInvoiceList_Form[${vstatus.index}].fdIsCurrent" value="${fdInvoiceList_Form.fdIsCurrent}"/>
			           </td>
			        </tr>
			        <li>
                    <div class="ld-newApplicationForm-invioce-top">
                        <div>
                            <img src="${LUI_ContextPath}/fssc/mobile/resource/images/specialTicket.png" alt="">
                            <xform:select property="fdInvoiceType"   value="${fdInvoiceList_Form.fdInvoiceType}" showStatus="view">
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
	                       <c:if test="${empty fdInvoiceList_Form.fdIsCurrent}">
								<span class="ld-newApplicationForm-travelInfo-top-buget-0" style="display:none;">${lfn:message('fssc-mobile:invoice.is.current') }</span>
	                       </c:if>
                            <input name='invioceId' value="${vstatus.index}" hidden='true'>
                        </div>
                        <i onclick="deleteInvioce(${vstatus.index},this)"></i>
                    </div>
                    <div class="ld-newApplicationForm-invioce-bottom" onclick="editInvioce(${vstatus.index})" >
                        <span  class='fdInvoiceNumber'>${fdInvoiceList_Form.fdInvoiceNumber}</span>
                        <div>
                          <span  class='fdInvoiceMoney'>${fdInvoiceList_Form.fdInvoiceMoney}</span>
                          <span></span>
                        </div>
                    </div>
                	</li>
			       </c:forEach>
             	</table>
             </ul>
            <div class="ld-newApplicationForm-invioce-btn" onclick="addInvioceDeteil();">
                <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addInvice')}</span>
            </div>
        </div>
        
        <!-- 收款账户 -->
        <div class="ld-line20px"></div>
        <div class="ld-addAccount-list">
            <div class="ld-newApplicationForm-attach-title">
                <h3>${lfn:message('fssc-expense:table.fsscExpenseAccounts') }</h3>
                <i></i>
            </div>
            
            <ul id="fdAccountsListId" >
             <table   id="TABLE_DocList_fdAccountsList_Form" align="center"  style="width:100%;display:none;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td style="width:100%;">
			       		<input  type="hidden" name="fdAccountsList_Form[!{index}].fdId" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankId" value=""/>
			            <input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdExchangeRate" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdPayWayName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdPayWayId" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdCurrencyName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdCurrencyId" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdAccountId" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdAccountName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdBankAccount" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdAccountAreaName" value=""/>
			        	<input  type="hidden" name="fdAccountsList_Form[!{index}].fdMoney" value=""/>
			        </td>
			      </tr>
			      <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_Form" varStatus="vstatus">
       			 	<tr  KMSS_IsContentRow="1" style="display:none;width:100%;">
       			 	 	<td>
       			 	 	  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdId" value="${fdAccountsList_Form.fdId}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankId" value="${fdAccountsList_Form.fdBankId}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankName" value="${fdAccountsList_Form.fdBankName}"/>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" value="${fdAccountsList_Form.fdExchangeRate}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdPayWayName" value="${fdAccountsList_Form.fdPayWayName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdPayWayId" value="${fdAccountsList_Form.fdPayWayId}"/>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyName" value="${fdAccountsList_Form.fdCurrencyName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" value="${fdAccountsList_Form.fdCurrencyId}"/>
			      		  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountName" value="${fdAccountsList_Form.fdAccountName}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountId" value="${fdAccountsList_Form.fdAccountId}"/>
			              <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdBankAccount" value="${fdAccountsList_Form.fdBankAccount}"/>
			        	  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" value="${fdAccountsList_Form.fdAccountAreaName}"/>
			        	  <input  type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdMoney" value="${fdAccountsList_Form.fdMoney}"/>
			      		</td>
			      	</tr>
			      </c:forEach>
			  </table>
			  <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_Form" varStatus="vstatus">
			  <li onclick="editAccountDetail()">
                    <div class="ld-newApplicationForm-account-top">
                        <div>
                           <img src="${LUI_ContextPath}/fssc/mobile/resource/images/collectionAccount.png" alt="">
                            <span class="accountName">${fdAccountsList_Form.fdAccountName}</span>
                        </div>
                        <span onclick="deleteAccount()"></span>
                         <input name="accountId" value="${vstatus.index}" hidden='true'>
                    </div>
                    <div class="ld-newApplicationForm-account-bottom">
                        <div>
                            <p  class="fdBankAccount">${fdAccountsList_Form.fdBankAccount}</p>
                        </div>
                        <div>
                            <span>收款金额：</span>
                            <div>
                            	<span class="fdMoney">${fdAccountsList_Form.fdMoney}</span>
                            	<span></span>
                            </div>
                        </div>
                    </div>
                </li>
                </c:forEach>
            </ul>
            <div class="ld-newApplicationForm-account-btn" onclick="addAccountDetail()">
                <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAcount')}</span>
            </div>
        </div>
        
        <!-- 冲抵借款明细 -->
        <div class="ld-line20px"></div>
        <div class="ld-addAccount-list" style="height: 1rem;padding: 0 0.3rem;line-height: 1rem;">
            <div style="height: 1rem;float: left;">${lfn:message('fssc-expense:fsscExpenseOffsetLoan.isOffsetLoan')}</div>
			
        		<div class="checkbox_item  ${(fsscExpenseMainForm.fdIsOffsetLoan==''||fsscExpenseMainForm.fdIsOffsetLoan=='false')?'checked':'' }" style="height: 1rem;float: right;">
        			<div class="checkbox_item_out">
        				<div class="checkbox_item_in"></div>
        			</div>
        			<div class="checkbox_item_text">${lfn:message('message.no')}</div>
        			<input type="radio" name="_fdIsOffsetLoan" value="false">
        		</div>
        		<div class="checkbox_item ${fsscExpenseMainForm.fdIsOffsetLoan=='true'?'checked':'' }" style="height: 1rem;float: right;">
        			<div class="checkbox_item_out">
        				<div class="checkbox_item_in"></div>
        			</div>
        			<div class="checkbox_item_text">${lfn:message('message.yes')}</div>
        			<input type="radio" name="_fdIsOffsetLoan" value="true">
        		</div>
        		<input type="hidden" name="fdIsOffsetLoan" value="${fsscExpenseMainForm.fdIsOffsetLoan}" onclick="FSSC_ChangeIsOffsetLoan();">
        		</div>
        		<div class="ld-addAccount-list"  style="padding:0 0.3rem;">
            <ul id="fdOffsetListId" style="margin-bottom:0.3rem;">
             <table   id="TABLE_DocList_fdOffsetList_Form" align="center"  style="width:100%;">
	             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
			       <td KMSS_IsRowIndex='1' style="width:100%;">
			            !{index}
			       </td>
			        <td >
			        	<input  type="hidden" name="fdOffsetList_Form[!{index}].fdId" value=""/>
			        	<input  type="hidden" name="fdOffsetList_Form[!{index}].fdLoanId" value=""/>
			            <input  type="hidden" name="fdOffsetList_Form[!{index}].docSubject" value=""/>
		      			<input type="hidden" name="fdOffsetList_Form[!{index}].fdNumber" value="" />
	                	<input type="hidden" name="fdOffsetList_Form[!{index}].fdLoanMoney" value="" />
	                	<input type="hidden" name="fdOffsetList_Form[!{index}].fdLeftMoney" value="" />
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
			      			<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdNumber" value="${fdOffsetList_FormItem.fdNumber}" />
		                	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLoanMoney" value="${fdOffsetList_FormItem.fdLoanMoney}" />
		                	<input type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdLeftMoney" value="${fdOffsetList_FormItem.fdLeftMoney}" />
			      			<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" value="${fdOffsetList_FormItem.fdOffsetMoney}"/>
			      			<input  type="hidden" name="fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" value="${fdOffsetList_FormItem.fdCanOffsetMoney}"/>
			      		</td>
			      	</tr>
			      </c:forEach>
			  </table>
           	</ul>
        </div>
        
        <div class="ld-line20px"></div>
		 <!-- 交易数据明细 -->
		 <kmss:ifModuleExist  path="/fssc/ccard/">
			 <div class="ld-addTranData-list">
				 <div class="ld-newApplicationForm-attach-title">
					 <h3>${lfn:message('fssc-expense:table.fsscExpenseTranData') }</h3>
					 <i></i>
				 </div>
				 <ul id="fdTranDataListId" >
					 <table id="TABLE_DocList_fdTranDataList_Form" align="center"  style="width:100%;">
						 <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
							 <td>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdId" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdTranDataId" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdCrdNum" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdActChiNam" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdTrsDte" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdTrxTim" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdOriCurAmt" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdOriCurCod" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdTrsCod" value=""/>
								 <input type="hidden" name="fdTranDataList_Form[!{index}].fdState" value=""/>
							 </td>
						 </tr>
						 <c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_FormItem" varStatus="vstatus">
							 <tr KMSS_IsContentRow="1" style="display:none;width:100%;">
								 <td>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdId" value="${fdTranDataList_FormItem.fdId}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTranDataId" value="${fdTranDataList_FormItem.fdTranDataId}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdCrdNum" value="${fdTranDataList_FormItem.fdCrdNum}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdActChiNam" value="${fdTranDataList_FormItem.fdActChiNam}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTrsDte" value="${fdTranDataList_FormItem.fdTrsDte}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTrxTim" value="${fdTranDataList_FormItem.fdTrxTim}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" value="${fdTranDataList_FormItem.fdOriCurAmt}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdOriCurCod" value="${fdTranDataList_FormItem.fdOriCurCod}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTrsCod" value="${fdTranDataList_FormItem.fdTrsCod}"/>
									 <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdState" value="${fdTranDataList_FormItem.fdState}"/>
								 </td>
							 </tr>
						 </c:forEach>
					 </table>
					 <c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_Form" varStatus="vstatus">
						 <li class="ld-notSubmit-list-item">
							 <div class="ld-newApplicationForm-account-top">
								 <div>
									 <img src="${LUI_ContextPath}/fssc/mobile/resource/images/persionBorrow.png" alt="">
									 <span class="fdActChiNam">${fdTranDataList_Form.fdActChiNam}</span>
									 <span class="ld-verticalLine"></span>
									 <span class="fdTrsDte">${fdTranDataList_Form.fdTrsDte}</span>
									 <input name="index" value="${vstatus.index}" hidden="true">
									 <input name="fdTranDataId" value="${fdTranDataList_Form.fdTranDataId}" hidden="true">
									 <input name="fdTrsCod" value="${fdTranDataList_Form.fdTrsCod}" hidden="true">
								 </div>
								 <span onclick="deleteTranData()"></span>
							 </div>
							 <div class="ld-newApplicationForm-account-bottom">
								 <div>
									 <p class="fdCrdNum">${fdTranDataList_Form.fdCrdNum}</p>
								 </div>
								 <div>
									 <span>交易金额：</span>
									 <div>
										 <span class="fdOriCurAmt">${fdTranDataList_Form.fdOriCurAmt}</span>
										 <span></span>
									 </div>
								 </div>
							 </div>
						 </li>
					 </c:forEach>
				 </ul>
			 </div>
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
	                    <div class="ld-remember-attact-info" onclick="showAtt('${list.fdId}','${list.fileName}')">
	                        <img src="${LUI_ContextPath }/sys/attachment/view/img/file/2x/icon_${list.icon}.png" alt="" data-file="${list.icon}"/>
	                        <span>${list.fileName}</span>
	                       <input name="attId" hidden='true' value="${list.fdId}">
	                    </div>
						<span onclick="deleteAtt('${list.fdId}',this);"></span>
	                </li>
                </c:forEach>
            </ul>
            <div class="ld-newApplicationForm-attach-btn">
                <div>
                    <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAttach')}</span>
                </div>
            </div>
        </div>
        <div class="ld-footer">
          <c:if test="${fsscExpenseMainForm.method_GET=='add' }">
          	 <div class="ld-footer-whiteBg" style="width:25%" onclick="submitForm('10','save',true);" >${ lfn:message('button.savedraft')}</div>
             <div class="ld-footer-blueBg" style="width:65%" onclick="submitForm('20','save');" >${ lfn:message('fssc-mobile:button.next') }</div>
          </c:if>
          <c:if test="${fsscExpenseMainForm.method_GET=='edit' }">
          	 <div class="ld-footer-whiteBg" style="width:25%" onclick="submitForm('10','update',true);" >${ lfn:message('button.savedraft')}</div>
             <div class="ld-footer-blueBg" style="width:65%" onclick="submitForm('20','update');" >${ lfn:message('fssc-mobile:button.next') }</div>
          </c:if>
        </div>
    </div>
    
    <!-- 费用选择方式 -->
     <div class="ld-remember-modal" >
        <div class="ld-remember-modal-main">
           <ul>
               <li>
                   <div onclick="addExpenseDeteil();" style="width:95%;">
                        <span class="ld-manual-invoice"></span>
                       <div class="ld-remember-modal-text" >
                           <p>${lfn:message('fssc-mobile:addByHand')}</p>
                           <p></p>
                       </div>
                   </div>
                    <i></i>
               </li>
                <li>
                   <div onclick="addExpenseByRemember();" style="width:95%;">
                      <span class="ld-saomiao-invoice"></span>
                       <div class="ld-remember-modal-text">
                           <p>${lfn:message('fssc-mobile:addByByRemember')}</p>
                            <p></p>
                       </div>
                   </div>
                    <i></i>
               </li>
			   <kmss:ifModuleExist  path="/fssc/ccard/">
			   <li>
				   <div onclick="selectTranData();" style="width:95%;">
					   <span class="ld-chooseCost-invoice"></span>
					   <div class="ld-remember-modal-text">
						   <p>${lfn:message('fssc-mobile:fsscExpenseMain.selectTranData')}</p>
						   <p></p>
					   </div>
				   </div>
				   <i></i>
			   </li>
			   </kmss:ifModuleExist>
           </ul>
           <div class="ld-remember-modal-cancel" onclick="cancelAddExpense()">取消</div>
        </div>
    </div>
    
  <!-- 明细编辑模板 -->
 <c:import url="/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_edit_detail.jsp"></c:import>
<input  hidden="true" value='${fsscExpenseMainForm.docStatus}' name="docStatus" />
<input  hidden="true" value='${fsscExpenseMainForm.fdId}' name="fdId" >
<input  hidden="true" value='${fdCompanyData}' name="fdCompanyData" >
<input  hidden="true" value="${fsscExpenseMainForm.docCreatorId }" name="docCreatorId">
<input  hidden="true" value="${fsscExpenseMainForm.docCreatorName }" name="docCreatorName">
<input  hidden="true" value="${parentId}" name="parentId" >
<input  hidden="true" value="${fsscExpenseMainForm.fdId}" name="fdId" >
<input  hidden="true" value="${docTemplate.fdId}" name="fdTemplateId">
<input  hidden="true" value="${docTemplate.fdIsProject}" name="fdIsProject">
<input  hidden="true" value="${docTemplate.fdIsFee}" name="fdIsFee">
<input  hidden="true" value="${docTemplate.fdBudgetShowType}" name="fdBudgetShowType">
<input  hidden="true" value="${docTemplate.fdExpenseType}" name="fdExpenseType">
<input  hidden="true" value="${docTemplate.fdIsTravelAlone}" name="fdIsTravelAlone">
<input  hidden="true" value="${docTemplate.fdExtendFields}" name="fdExtendFields">
<input hidden="true" value="${fdDeduFlag}" name="fdDeduFlag" />
<input hidden="true" value="${isShowDraftsmanStatus}" name="isShowDraftsmanStatus" />
<input hidden="true" value="${isPopupWindowRemindSubmitter}" name="isMulClaimantStatus" />
<input type="hidden" name="fdDetailList_Flag" value="1">
<input type="hidden" name="fdTravelList_Flag" value="1">
<input type="hidden" name="fdInvoiceList_Flag" value="1">
<input type="hidden" name="fdAccountsList_Flag" value="1">
<input type="hidden" name="fdOffsetList_Flag" value="1">
<input type="hidden" name="fdAmortizeList_Flag" value="1">
<input type="hidden" name="fdTranDataList_Flag" value="1">
<input type="hidden" name="method_GET" value="${fsscExpenseMainForm.method_GET}">
<%-- <html:hidden property="docStatus" />
<html:hidden property="method_GET" /> --%>
<fssc:checkVersion version="true">
		<input name="checkVersion" value="true"  type="hidden" />
</fssc:checkVersion>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/layer.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/orderbyPingyin.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_edit.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_budget.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/fsscMobileExpense_submitEvent.js"></script>
<kmss:ifModuleExist path="/fssc/budget">
	<script>Com_IncludeFile("expense_submit_edit.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_expense/", 'js', true);</script>
</kmss:ifModuleExist>
</form>
</body>
<%@ include file="/resource/jsp/edit_down.jsp" %>
