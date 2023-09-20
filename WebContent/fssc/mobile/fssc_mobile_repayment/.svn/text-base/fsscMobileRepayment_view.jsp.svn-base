<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/common/attachement/attachment_view.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
    	<c:out value="${fsscLoanRepaymentForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
    </title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/viewApplicationForm.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/feeDetail.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/common/attachement/attachment.js"></script>
    <style>
		.titleCss{
	   		color:#999999;
	   }          
    </style> 
    <script>
	    $(document).ready(function(){
	 		$('.txtstrong').remove();
	 		$('.inputsgl').removeClass();
	 		$(".ld-remember-attact-info img").each(function(){
	 			this.src = getSrcByName($(this).data("file"));
	 		})
	 	});
    </script>
</head>
<body>
    <div class="ld-new-reimbursement-form">
        <div class="ld-new-reimbursement-form-head">
            <div class="ld-new-reimbursement-form-head-customer">
                <span>${fsscLoanRepaymentForm.docSubject}</span>
            </div>
            <div class="ld-new-reimbursement-form-head-info">
                <div class="ld-new-reimbursement-form-head-info-department">
                  <div>
                    <span class="ld-new-reimbursement-form-head-info-name">${fsscLoanRepaymentForm.docCreatorName}</span> 
                    <span class="ld-line"></span>
                    <span>${fsscLoanRepaymentForm.fdRepaymentDeptName}</span>
                  </div>
                  <span><sunbor:enumsShow value="${fsscLoanRepaymentForm.docStatus}" enumsType="common_status" /></span>
                </div>
                <p></p>
            </div>
        </div>
        <div class="ld-entertain-main">
                
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}</span>
		                    <span><xform:text property="fdRepaymentPersonName" value="${fsscLoanRepaymentForm.fdRepaymentPersonName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}</span>
		                    <span><xform:text property="fdRepaymentPersonName" value="${fsscLoanRepaymentForm.fdRepaymentDeptName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}</span>
		                    <span><xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" /></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}</span>
		                    <span style="margin-left:2rem;word-break:break-all;">${fsscLoanRepaymentForm.fdLoanMainName}</span>
		                </div>
		                <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}</span>
		                    <span><kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="##0.00"></kmss:showNumber></span>
		                </div>
		                <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}</span>
		                    <span><kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="##0.00"></kmss:showNumber></span>
		                </div>
		                <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}</span>
		                    <span>${fsscLoanRepaymentForm.fdBasePayWayName}</span>
		                </div>
		                <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}</span>
		                    <span>${fsscLoanRepaymentForm.fdPaymentAccount}</span>
		                </div>
		                <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}</span>
		                    <span>${fsscLoanRepaymentForm.fdReason}</span>
		                </div>
	                </div>
                </div>
                <div class="ld-line20px"></div>
                <!-- 附件明细 -->
		        <div class="ld-rememberOne" style="padding-bottom:0.001rem;">
       				 <div class="ld-rememberOne-main">
		        		<div class="ld-remember-attach">
			                <div class="ld-remember-attach-title">
			                    <h3>${lfn:message('fssc-loan:fsscLoanMain.attPayment')}</h3>
			                    <i></i>
			                </div>
			                <ul>
			                	<c:forEach items="${attData}" var="att">
			                		 <li onclick="showAtt('${att['fdId']}','${att['fileName']}');">
				                        <div class="ld-remember-attact-info">
				                            <img src="" alt="" data-file="${att['fileName']}">
				                            <span>${att['fileName']}</span>
				                        </div>
				                    </li>
			                	</c:forEach>
			                </ul>
			            </div>
			         </div>
			    </div>
		       <!-- 流程处理 -->
		       <div class="ld-new-reimbursement-form-progress">
		       	<iframe id="frame" width="100%" frameborder="0"  height="500px" src ="${LUI_ContextPath}/fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=viewLbpm&fdId=${fsscLoanRepaymentForm.fdId}"></iframe>
		       </div>
		       <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=edit&fdId=${param.fdId}">
		       	<c:if test="${fsscLoanRepaymentForm.docStatus =='10' or fsscLoanRepaymentForm.docStatus =='11'}">
		        	<div class="editModel" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=edit&fdId=${fsscLoanRepaymentForm.fdId }'"></div>
		    	</c:if>
		    	</kmss:auth>
    </div>
    </div>
    <div class="backHome" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/mobile/'"></div>
</body>
</html>
