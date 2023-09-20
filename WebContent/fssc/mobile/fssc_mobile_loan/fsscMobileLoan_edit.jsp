<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ include file="/fssc/mobile/common/organization/organization_include.jsp" %>
<%
	session.setAttribute("S_PADFlag","1");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
    	<c:if test="${fsscLoanMainForm.method_GET =='add'}">
    		${lfn:message("operation.create")}
    	</c:if>
    	<c:if test="${fsscLoanMainForm.method_GET =='edit'}">
    		${lfn:message("button.edit")}
    	</c:if>
    	-${lfn:message("fssc-loan:module.fssc.loan")}
    </title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css">
    <script>
    var formInitData = {
    	
    };
    var message = {
    	"errors.required":"${lfn:message('errors.required')}",
    	"errors.dollar":"${lfn:message('errors.dollar')}",
    	"expectedDate.early":"${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate.IsEarly')}",
    	"message.feeMoney.less.loanMoney":"${lfn:message('fssc-loan:message.feeMoney.less.loanMoney')}",
    	"button.ok":"${lfn:message('button.ok')}",
    	"button.cancel":"${lfn:message('button.cancel')}",
    	"msg.expected.date.invalid.error":"${lfn:message('fssc-loan:msg.expected.date.invalid.error')}",
    	"msg.check.fdPayeeBank.null":"${lfn:message('fssc-mobile:msg.check.fdPayeeBank.null')}",
    	"msg.check.fdBankAccountNo.null":"${lfn:message('fssc-mobile:msg.check.fdBankAccountNo.null')}",
    	"msg.check.fdPayeeAccount.null":"${lfn:message('fssc-mobile:msg.check.fdPayeeAccount.null')}",
    };
    </script>
    <script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_loan/fsscMobileLoan_edit.js"></script>
    <script type="text/javascript">
		Com_IncludeFile("data.js");
		Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	</script>

</head>

<body>
<form action="${LUI_ContextPath }/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do" name="fsscLoanMainForm"  method="post">
    <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-header">
            <div class="ld-newApplicationForm-header-title">
           		<c:if test="${docTemplate.fdSubjectType=='1' }">
                <input type="text" placeholder="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" value="${fsscLoanMainForm.docSubject}" subject="${lfn:message('fssc-loan:fsscLoanMain.docSubject')}" name="docSubject" validator="required">
                </c:if>
                <c:if test="${docTemplate.fdSubjectType=='2' }">
                 <input type="text" placeholder="${lfn:message('fssc-loan:py.auto.create.after.submit') }" name="" readonly="readonly" >
                </c:if>
            </div>
            <div class="ld-newApplicationForm-header-desc">
            </div>
        </div>
        <div class="ld-newApplicationForm-info">
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}</span>
                <div class="ld-selectPersion">
                	<!-- 启用授权 -->
                	<c:if test="${fdIsAuthorize=='true'}">
                		<input type="text" validator="required" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" name="fdLoanPersonName"  onclick="selectObject('fdLoanPersonId','fdLoanPersonName','${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=getFdLoanPerson',changeFdLoanPerson);" value="${fsscLoanMainForm.fdLoanPersonName }" readonly="readonly" />
                	</c:if>
                	<!-- 未启用授权 -->
                	<c:if test="${fdIsAuthorize=='false'}">
                		<input type="text" validator="required" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" name="fdLoanPersonName"  onclick="selectOrgElement('fdLoanPersonId','fdLoanPersonName','${parentId}','false','person',changeFdLoanPerson);" value="${fsscLoanMainForm.fdLoanPersonName }" readonly="readonly" />
                	</c:if>
                     <input name='fdLoanPersonId' value="${fsscLoanMainForm.fdLoanPersonId }" hidden='true'/>
                     <input name='fdOldLoanPersonId' value="${fsscLoanMainForm.fdLoanPersonId }" hidden='true'/>
                     <input name='fdOldLoanPersonName' value="${fsscLoanMainForm.fdLoanPersonName }" hidden='true'/>
                     <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdLoanDept')}</span>
                <div>
                     <input type="text" name="fdLoanDeptName" value="${fsscLoanMainForm.fdLoanDeptName }" readonly="readonly" placeholder='${lfn:message("fssc-loan:fsscLoanMain.fdLoanDept")}'/>
                     <input type="hidden" name="fdLoanDeptId"  value="${fsscLoanMainForm.fdLoanDeptId }" />
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-loan:fsscLoanMain.fdCompany")}</span>
                <div>
                     <input type="text" validator="required" id="fdCompanyName" name="fdCompanyName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}" value="${fsscLoanMainForm.fdCompanyName }" readonly="readonly" onclick="selectObject('fdCompanyId','fdCompanyName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCompany',afterSelectCompany);" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanMain.fdCompany")}'/>
                     <input type="text" id="fdCompanyId" name="fdCompanyId"  value="${fsscLoanMainForm.fdCompanyId }" hidden='true' >
                     <input type="text" id="fdCompanyIdOld" name="fdCompanyIdOld"  value="${fsscLoanMainForm.fdCompanyId }" hidden='true' >
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-loan:fsscLoanMain.fdCostCenter")}</span>
                <div>
                    <input type="text" validator="required"  id="fdCostCenterName" name="fdCostCenterName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}" value="${fsscLoanMainForm.fdCostCenterName }"  readonly="readonly" onclick="selectObject('fdCostCenterId','fdCostCenterName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getEopBasedataCostCenter');" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanMain.fdCostCenter")}'/>
                    <input id="fdCostCenterId" name="fdCostCenterId" value="${fsscLoanMainForm.fdCostCenterId }"  hidden='true'>
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <kmss:ifModuleExist path="/fssc/fee">
            <c:if test="${docTemplate.fdIsRequiredFee=='true' }">
	            <div>
	               <span>${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}</span>
	                <div>
	                    <input type="text" validator="required" name="fdFeeMainName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}" value="${fsscLoanMainForm.fdFeeMainName }" readonly="readonly" onclick="selectObject('fdFeeMainId','fdFeeMainName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getFeeMain');" />
	                    <input type="hidden"  name="fdFeeMainId" value="${fsscLoanMainForm.fdFeeMainId }" />
	                    <span style="margin-left:2px;color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
            </c:if>
            </kmss:ifModuleExist>
            <c:if test="${docTemplate.fdIsProject=='true' }">
	            <div>
	                 <span>${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}</span>
	                <div>
	                    <input type="text" validator="required" name="fdBaseProjectName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}" value="${fsscLoanMainForm.fdBaseProjectName }" readonly="readonly" onclick="selectObject('fdBaseProjectId','fdBaseProjectName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getFdProject',afterSelectProject);" />
	                    <input type="hidden"  name="fdBaseProjectId" value="${fsscLoanMainForm.fdBaseProjectId }" />
	                    <input type="hidden"  name="fdOldBaseProjectId" value="${fsscLoanMainForm.fdBaseProjectId }" />
	                    <span style="margin-left:2px;color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
            </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
            	<div>
	                 <span>${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}</span>
	                <div>
	                     <input type="text" name="fdBaseWbsName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}" value="${fsscLoanMainForm.fdBaseWbsName }" readonly="readonly" onclick="selectObject('fdBaseWbsId','fdBaseWbsName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getWbsList',afterSelectWbs);" />
	                    <input type="hidden"  name="fdBaseWbsId" value="${fsscLoanMainForm.fdBaseWbsId }" />
	                    <i></i>
	                </div>
	            </div>
            </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
            	<div>
	                 <span>${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}</span>
	                <div>
	                     <input type="text" name="fdBaseInnerOrderName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}" value="${fsscLoanMainForm.fdBaseInnerOrderName }" readonly="readonly" onclick="selectObject('fdBaseInnerOrderId','fdBaseInnerOrderName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getOrderList',afterSelectOrder);" />
	                    <input type="hidden"  name="fdBaseInnerOrderId" value="${fsscLoanMainForm.fdBaseInnerOrderId }" />
	                    <i></i>
	                </div>
	            </div>
            </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
            	<div>
	                 <span>${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}</span>
	                <div>
	                     <input type="text" validator="required" name="fdBaseProjectAccountingName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}" value="${fsscLoanMainForm.fdBaseProjectAccountingName }" readonly="readonly" onclick="selectObject('fdBaseProjectAccountingId','fdBaseProjectAccountingName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getFdProject&fdType=2');" />
	                    <input type="hidden"  name="fdBaseProjectAccountingId" value="${fsscLoanMainForm.fdBaseProjectAccountingId }" />
	                    <span style="margin-left:2px;color:#d02300;">*</span>
	                    <i></i>
	                </div>
	            </div>
            </c:if>

            <div>
                <span>${lfn:message("fssc-loan:fsscLoanMain.fdApplyMoney")}</span>
                <div>
                    <input type="text" name="fdApplyMoney" onblur="changeLoanMoney(this)" validator="required currency-dollar" subject="${lfn:message('fssc-loan:fsscLoanMain.fdApplyMoney')}" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanMain.fdApplyMoney")}' value="<kmss:showNumber value="${fsscLoanMainForm.fdLoanMoney}" pattern="##0.00"></kmss:showNumber>" />
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <input type="hidden" name="fdLoanMoney" value="${ fsscLoanMainForm.fdLoanMoney}"/>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}</span>
                <div>
                    <input type="text" validator="required" name="fdBaseCurrencyName" value="${fsscLoanMainForm.fdBaseCurrencyName }"   readonly="readonly" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}"  onclick="selectObject('fdBaseCurrencyId','fdBaseCurrencyName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCurrencyData',FSSC_reloadBaseCurreny);" placeholder='${lfn:message("fssc-loan:fsscLoanMain.fdBaseCurrency")}'/>
                    <input type="hidden" name="fdBaseCurrencyId"  value="${fsscLoanMainForm.fdBaseCurrencyId }" />
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>

            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdExchangeRate')}</span>
                <div>
                    <input type="text" validator="required" name="fdExchangeRate" value="${fsscLoanMainForm.fdExchangeRate }"   readonly="readonly" />

                </div>

            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdStandardMoney')}</span>
                <div>
                    <input type="text" validator="required" name="fdStandardMoney" value="${fsscLoanMainForm.fdStandardMoney }"   readonly="readonly" />

                </div>

            </div>

            <div>
                <span>${lfn:message("fssc-loan:fsscLoanMain.fdExpectedDate")}</span>
                <div class="dateClass">
                    <input type="text" validator="required" id="fdExpectedDate" name="fdExpectedDate" value="${fsscLoanMainForm.fdExpectedDate }"  subject="${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanMain.fdExpectedDate")}' onclick="selectTime('fdExpectedDate','fdExpectedDate');" changeFunc="onChangFdExpectedDate" readonly="readonly" />
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}</span>
                <div>
                     <input type="text"  validator="currency-dollar" name="fdTotalLoanMoney" subject="${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}" value="<kmss:showNumber value="${fsscLoanMainForm.fdTotalLoanMoney}" pattern="##0.00"></kmss:showNumber>" readonly=currency-dollarnly" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanMain.fdBaseCurrency")}'/>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}</span>
                <div>
                     <input type="text" validator="currency-dollar" name="fdTotalRepaymentMoney" subject="${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}" value="<kmss:showNumber value="${fsscLoanMainForm.fdTotalRepaymentMoney}" pattern="##0.00"></kmss:showNumber>" readonly="readonly" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanMain.fdTotalRepaymentMoney")}'/>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}</span>
                <div>
                     <input type="text" validator="currency-dollar" name="fdTotalNotRepaymentMoney" subject="${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}" value="<kmss:showNumber value="${fsscLoanMainForm.fdTotalNotRepaymentMoney}" pattern="##0.00"></kmss:showNumber>" readonly="readonly" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney")}'/>
                </div>
            </div>
            <c:if test="${fdIsErasable==true}">
            	 <div>
	                <span>${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}</span>
	                <div>
	                     <input type="text" validator="required" subject="${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanMain.fdOffsetters")}' name="fdOffsetterNames"  onclick="selectOrgElement('fdOffsetterIds','fdOffsetterNames','${parentId}','true','person');" value="${fsscLoanMainForm.fdOffsetterNames }" readonly="readonly" />
	                     <input type="hidden"  name="fdOffsetterIds" value="${fsscLoanMainForm.fdOffsetterIds }" />
	                     <span style="margin-left:2px;color:#d02300;">*</span>
	                     <i></i>
	                </div>
	            </div>
            </c:if>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdReason')}</span>
                <div>
                     <input type="text" validator="required" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanMain.fdReason")}' subject="${lfn:message('fssc-loan:fsscLoanMain.fdReason')}" value="${fsscLoanMainForm.fdReason}" name="fdReason" />
                     <span style="margin-left:2px;color:#d02300;">*</span>
                </div>
            </div>
        </div>
        <div class="ld-line20px"></div>
        <!-- 收款账户 -->
        <div class="ld-newApplicationForm-info">
        	<div>
                <span> ${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}</span>
                <div>
                    <input type="text" name="fdBasePayWayName" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}"  
                    			subject="${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}" value="${fsscLoanMainForm.fdBasePayWayName}" onclick="selectObject('fdBasePayWayId','fdBasePayWayName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getFsBasePay',afterSelectPayWay);" readonly="readonly" validator="required" />
                    <input  type="hidden"  name="fdBasePayWayId"  value="${fsscLoanMainForm.fdBasePayWayId}" />
                    <span style="color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
        	<div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}</span>
                <div>
	                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}" name="fdAccPayeeName"  
	                	subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}" value="${fsscLoanMainForm.fdAccPayeeName}" onclick="selectObject('fdAccPayeeId','fdAccPayeeName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getAccountInfo',afterSelectAccount);" readonly="readonly" />
	                <input type="hidden" name="fdAccPayeeId" value="" />
	                <span style="color:#d02300;" class="vat">*</span>
	                <i></i>
                </div>
            </div>
        	<div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}</span>
                <div>
	                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}" value="${fsscLoanMainForm.fdPayeeBank}"  name="fdPayeeBank" subject="${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}" />
	                <span style="color:#d02300;" class="vat">*</span>
                </div>
            </div>
            <fssc:checkUseBank fdBank="BOC">
        	<div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}</span>
                <div>
	                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}" value="${fsscLoanMainForm.fdBankAccountNo}" name="fdBankAccountNo" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}" />
	                <span style="color:#d02300;" class="vat">*</span>
                </div>
            </div>
            </fssc:checkUseBank>
            <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
        	<div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}</span>
                <div>
	                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}" value="${fsscLoanMainForm.fdAccountAreaName}" name="fdAccountAreaName" subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}" 
	                	onclick="selectObject('fdAccountAreaId','fdAccountAreaName','${LUI_ContextPath}/fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=getAccountAreaInfo');" readonly="readonly" />
	                <input name="fdAccountAreaId" type="hidden" />
	                <span style="color:#d02300;" class="vat">*</span>
	                <i></i>
                </div>
            </div>
            </fssc:checkUseBank>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}</span>
                <div>
	                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}" value="${fsscLoanMainForm.fdPayeeAccount}" name="fdPayeeAccount" subject="${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}" />
	                <span style="color:#d02300;" class="vat">*</span>
                </div>
            </div>
        </div>
        
        
        <div class="ld-line20px"></div>
         
 		<!-- 附件明细 -->
        <div class="ld-newApplicationForm-attach">
            <div class="ld-newApplicationForm-attach-title">
                <h3>${lfn:message('fssc-loan:fsscLoanMain.attPayment')}</h3>
                <i></i>
            </div>
            <ul id="fdAttachListId" >
                <!-- 附件明细 -->
               	<c:forEach items="${attData}" var="att">
               		 <li>
                        <div class="ld-remember-attact-info" onclick="showAtt('${att['fdId']}','${att['fileName']}')"  data-attid="${att['fdId']}">
                            <img src="" alt="" data-file="${att['fileName']}">
                            <span>${att['fileName']}</span>
                        </div>
                        <span onclick="deleteAtt('${att['fdId']}','${att['fileName']}');"></span>
					</li>
               	</c:forEach>
            </ul>
            <div class="ld-newApplicationForm-attach-btn">
                <div>
                    <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAttach')}</span>
                    <input type="file" id="loanAtt" multiple="multiple" onchange="uploadFile(this.files);">
                </div>
            </div>
        </div>
        <div class="ld-footer">
           <c:if test="${fsscLoanMainForm.method_GET=='add' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscLoanMainForm,'10','save');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscLoanMainForm,'20','save');" >${ lfn:message('fssc-mobile:button.next') }</div>
          	</c:if>
          	<c:if test="${fsscLoanMainForm.method_GET=='edit' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscLoanMainForm,'10','update');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscLoanMainForm,'20','update');" >${ lfn:message('fssc-mobile:button.next') }</div>
          	</c:if>
        </div>
    </div>
    <!-- 侧边栏 -->
   
    <!-- 上传中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.uploading')}</span>
            </div>
        </div>
    </div>
     <input type="hidden" name="fdId" value="${fsscLoanMainForm.fdId }" />
     <input type="hidden" name="method_GET" value="${fsscLoanMainForm.method_GET }" />
     <input type="hidden" name="fdTemplateId" value="${docTemplate.fdId }" />
     <input type="hidden" name="docCreateTime" value="${fsscLoanMainForm.docCreateTime }" />
     <input type="hidden" name="docCreatorId" value="${fsscLoanMainForm.docCreatorId }" />
     <input type="hidden" name="docCreatorName" value="${fsscLoanMainForm.docCreatorName }" />
     <input type="hidden" name="fdIsContRepayDay" value="${fdIsContRepayDay}" />
     <input type="hidden" name="fdIsTransfer" value="${fdIsTransfer}" >
     <input hidden="true" value="${isShowDraftsmanStatus}" name="isShowDraftsmanStatus" />
     <xform:text property="docStatus" value="${fsscLoanMainForm.docStatus }" showStatus="noShow"></xform:text>
     <input name="fdRemind" type="hidden"/>
     <fssc:checkVersion version="true">
		<input name="checkVersion" value="true"  type="hidden" />
	</fssc:checkVersion>
    </form>
    
</body>
</html>
