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
    	<c:if test="${fsscLoanRepaymentForm.method_GET =='add'}">
    		${lfn:message("operation.create")}
    	</c:if>
    	<c:if test="${fsscLoanRepaymentForm.method_GET =='edit'}">
    		${lfn:message("button.edit")}
    	</c:if>
    	-${lfn:message("fssc-loan:table.fsscLoanRepayment")}</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css">
    <script>
    var formInitData = {
    	
    };
    var message = {
    	"errors.required":"${lfn:message('errors.required')}",
    	"errors.dollar":"${lfn:message('errors.dollar')}",
    	"button.ok":"${lfn:message('button.ok')}",
    	"button.cancel":"${lfn:message('button.cancel')}",
    	"select.loan.before":"${lfn:message('fssc-mobile:select.loan.before')}",
    	"repay.less.loan":"${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney.message')}",
    };
    </script>
    <script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_repayment/fsscMobileRepayment_edit.js"></script>
    <script type="text/javascript">
		Com_IncludeFile("data.js");
		Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	</script>

</head>

<body>
<form action="${LUI_ContextPath }/fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do" name="fsscLoanRepaymentForm"  method="post">
    <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-header">
            <div class="ld-newApplicationForm-header-title">
           		 <c:if test="${docTemplate.fdSubjectType=='1' }">
                <input type="text" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}" subject="${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}" value="${fsscLoanRepaymentForm.docSubject}" name="docSubject" validator="required">
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
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}</span>
                <div class="ld-selectPersion">
                     <input name="fdRepaymentPersonId" type="hidden" value="${fsscLoanRepaymentForm.fdRepaymentPersonId }" />
                     <input name="fdRepaymentPersonName" value="${fsscLoanRepaymentForm.fdRepaymentPersonName }" readonly="readonly" />
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}</span>
                <div>
                     <input type="text" name="fdRepaymentDeptName" value="${fsscLoanRepaymentForm.fdRepaymentDeptName }" readonly="readonly" placeholder="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}" />
                     <input type="hidden" name="fdRepaymentDeptId"  value="${fsscLoanRepaymentForm.fdRepaymentDeptId}" />
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-loan:fsscLoanRepayment.fdLoanMain")}</span>
                <div>
                    <input type="text" validator="required"  id="fdLoanMainName" name="fdLoanMainName" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}" value="${fsscLoanRepaymentForm.fdLoanMainName }"  readonly="readonly" onclick="selectLoanMain();" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanRepayment.fdLoanMain")}'/>
                    <input id="fdLoanMainId" name="fdLoanMainId" value="${fsscLoanRepaymentForm.fdLoanMainId }" type="hidden" />
                    <input name="fdOldLoanMainId" value="${fsscLoanRepaymentForm.fdLoanMainId }" type="hidden" />
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}</span>
                <div>
                     <input type="text" name="fdCanOffsetMoney" value="<kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="##0.00"></kmss:showNumber>" readonly="readonly" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}" />
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}</span>
                <div>
                     <input type="text" validator="required currency-dollar" name="fdRepaymentMoney" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanRepayment.fdRepaymentMoney")}' value="<kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="##0.00"></kmss:showNumber>"  subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}" />
                     <span style="margin-left:2px;color:#d02300;">*</span>
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}</span>
                <div>
                    <input type="text" validator="required"  id="fdBasePayWayName" name="fdBasePayWayName" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}" value="${fsscLoanRepaymentForm.fdBasePayWayName }"  readonly="readonly" onclick="selectPayway();" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.select")}${lfn:message("fssc-loan:fsscLoanRepayment.fdBasePayWay")}'/>
                    <input id="fdBasePayWayId" name="fdBasePayWayId" value="${fsscLoanRepaymentForm.fdBasePayWayId }" type="hidden" />
                    <span style="margin-left:2px;color:#d02300;">*</span>
                    <i></i>
                </div>
            </div>
             <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}</span>
                <div>
                     <input type="text" name="fdPaymentAccount" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanRepayment.fdPaymentAccount")}'  value="${fsscLoanRepaymentForm.fdPaymentAccount }" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}" />
                </div>
            </div>
            <div>
                <span>${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}</span>
                <div>
                     <input type="text" validator="required" placeholder='${lfn:message("fssc-mobile:fssc.mobile.placeholder.input")}${lfn:message("fssc-loan:fsscLoanRepayment.fdReason")}' subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}" name="fdReason" value="${fsscLoanRepaymentForm.fdReason}" />
                     <span style="margin-left:2px;color:#d02300;">*</span>
                </div>
            </div>
            <div>
                <span>${lfn:message("fssc-loan:fsscLoanRepayment.docCreateTime")}</span>
                <div>
                     <input name="docCreateTime" value="${fsscLoanRepaymentForm.docCreateTime }" readonly="readonly" />
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
         	<c:if test="${fsscLoanRepaymentForm.method_GET=='add' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscLoanRepaymentForm,'10','save');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscLoanRepaymentForm,'20','save');" >${ lfn:message('fssc-mobile:button.next') }</div>
          	</c:if>
          	<c:if test="${fsscLoanRepaymentForm.method_GET=='edit' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscLoanRepaymentForm,'10','update');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscLoanRepaymentForm,'20','update');" >${ lfn:message('fssc-mobile:button.next') }</div>
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
    <!-- 加载中 -->   
     <div class="ld-main" id="ld-main-data-loading" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.loading')}</span>
            </div>
        </div>
    </div>
     <input type="hidden" name="fdId" value="${fsscLoanRepaymentForm.fdId }" />
     <input type="hidden" name="method_GET" value="${fsscLoanRepaymentForm.method_GET }" />
     <input type="hidden" name="fdTemplateId" value="${docTemplate.fdId }" />
     <input type="hidden" name="docCreateTime" value="${fsscLoanRepaymentForm.docCreateTime }" />
     <input type="hidden" name="docCreatorId" value="${fsscLoanRepaymentForm.docCreatorId }" />
     <input type="hidden" name="docCreatorName" value="${fsscLoanRepaymentForm.docCreatorName }" />
     <input hidden="true" value="${isShowDraftsmanStatus}" name="isShowDraftsmanStatus" />
     <xform:text property="docStatus" value="${fsscLoanRepaymentForm.docStatus }" showStatus="noShow"></xform:text>
     <input name="fdRemind" type="hidden"/>
     <fssc:checkVersion version="true">
		<input name="checkVersion" value="true"  type="hidden" />
	</fssc:checkVersion>
    </form>
    
</body>
</html>
