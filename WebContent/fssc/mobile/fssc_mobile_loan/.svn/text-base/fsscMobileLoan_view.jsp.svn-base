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
    	<c:out value="${fsscLoanMainForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
    </title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/viewApplicationForm.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/feeDetail.css">
	<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/popups.css">
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/popups.js"></script>
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
		
		//查看交单退单数据
		function openPresData() {
			var data = $("[name=data]").val();
			data = JSON.parse(data.replaceAll("'",'"'));
			var html = "<div><table>" +
					   "<tr align='center' class='tr_normal_title'>"+
					   "<td align='center' style='width:20%;'>${lfn:message('fssc-pres:fsscPresMain.fdType')}</td>"+
					   "<td align='center' style='width:80%;'>${lfn:message('fssc-pres:fsscPresMain.fdDesc')}</td>"+
					   "</tr>"+
					   "<tr class='ld-line20px'></tr>";
			for(var i=0;i<data.length;i++){
				var fdType = "";
				if(data[i].fdType==1){
					fdType="交单";
				}else if(data[i].fdType==2){
					fdType="退单";
				}
				html += "<tr KMSS_IsContentRow='1'>";
				html += "<td align='center' style='width:20%;'>"+fdType+"</td>"+
						"<td align='center' style='width:80%;'>"+data[i].fdDesc+"</td>";
				html += "</tr><tr class='ld-line20px'></tr>";
			}
			html += "</table></div>";
			jqalert({
				title:'交单退单',
				content:html,
				yestext:'返回'
			})
		}
    </script>
</head>
<body>
    <div class="ld-new-reimbursement-form">
        <div class="ld-new-reimbursement-form-head">
            <div class="ld-new-reimbursement-form-head-customer">
                <span>${fsscLoanMainForm.docSubject}</span>
            </div>
            <div class="ld-new-reimbursement-form-head-info">
                <div class="ld-new-reimbursement-form-head-info-department">
                  <div>
                    <span class="ld-new-reimbursement-form-head-info-name">${fsscLoanMainForm.docCreatorName}</span> 
                    <span class="ld-line"></span>
                    <span>${fsscLoanMainForm.fdLoanDeptName}</span>
                  </div>
                  <span><sunbor:enumsShow value="${fsscLoanMainForm.docStatus}" enumsType="common_status" /></span>
                </div>
                <p></p>
            </div>
        </div>
        <div class="ld-entertain-main">
                
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}</span>
		                    <span><xform:text property="fdLoanPersonName" value="${fsscLoanMainForm.fdLoanPersonName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}</span>
		                    <span><xform:text property="fdCompanyName" value="${fsscLoanMainForm.fdCompanyName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}</span>
		                    <span><xform:text property="fdCostCenterName" value="${fsscLoanMainForm.fdCostCenterName}" showStatus="view"></xform:text></span>
		                </div>
		                <kmss:ifModuleExist path="/fssc/fee">
			            <c:if test="${docTemplate.fdIsRequiredFee=='true' }">
				            <div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}</span>
			                    <span style="margin-left:2rem;word-break : break-all;"><xform:text property="fdFeeMainName" value="${fsscLoanMainForm.fdFeeMainName}" showStatus="view"></xform:text></span>
			                </div>
			            </c:if>
			            </kmss:ifModuleExist>
			            <c:if test="${docTemplate.fdIsProject=='true' }">
				            <div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}</span>
			                    <span><xform:text property="fdBaseProjectName" value="${fsscLoanMainForm.fdBaseProjectName}" showStatus="view"></xform:text></span>
			                </div>
			            </c:if>
			            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
			            	<div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}</span>
			                    <span><xform:text property="fdBaseWbsName" value="${fsscLoanMainForm.fdBaseWbsName}" showStatus="view"></xform:text></span>
			                </div>
			            </c:if>
			            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
			            	<div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}</span>
			                    <span><xform:text property="fdBaseInnerOrderName" value="${fsscLoanMainForm.fdBaseInnerOrderName}" showStatus="view"></xform:text></span>
			                </div>
			            </c:if>
			            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
			            	<div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}</span>
			                    <span><xform:text property="fdBaseProjectAccountingName" value="${fsscLoanMainForm.fdBaseProjectAccountingName}" showStatus="view"></xform:text></span>
			                </div>
			            </c:if>
						<div>
							<span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}</span>
							<span><kmss:showNumber value="${fsscLoanMainForm.fdLoanMoney}" pattern="###,##0.00"></kmss:showNumber></span>
						</div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}</span>
		                    <span><xform:text property="fdBaseCurrencyName" value="${fsscLoanMainForm.fdBaseCurrencyName}" showStatus="view"></xform:text></span>
		                </div>
						<div>
							<span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdExchangeRate')}</span>
							<span>${fsscLoanMainForm.fdExchangeRate}</span>
						</div>
						<div>
							<span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdStandardMoney')}</span>
							<span><kmss:showNumber value="${fsscLoanMainForm.fdStandardMoney}" pattern="###,##0.00"></kmss:showNumber></span>
						</div>

		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}</span>
		                    <span>${fsscLoanMainForm.fdExpectedDate}</span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}</span>
		                    <span><kmss:showNumber value="${fsscLoanMainForm.fdTotalLoanMoney}" pattern="###,##0.00"></kmss:showNumber></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}</span>
		                    <span><kmss:showNumber value="${fsscLoanMainForm.fdTotalRepaymentMoney}" pattern="###,##0.00"></kmss:showNumber></span>
		                </div>
		                <div>
			                <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}</span>
			                <span><kmss:showNumber value="${fsscLoanMainForm.fdTotalNotRepaymentMoney}" pattern="###,##0.00"></kmss:showNumber></span>
			            </div>
			            <c:if test="${fdIsErasable==true}">
			            	 <div>
				                <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}</span>
			                	<span><xform:text property="fdOffsetterNames" value="${fsscLoanMainForm.fdOffsetterNames}" showStatus="view"></xform:text></span>
				            </div>
			            </c:if>
			            <div>
			                <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdReason')}</span>
		                	<span><xform:text property="fdReason" value="${fsscLoanMainForm.fdReason}" showStatus="view"></xform:text></span>
			            </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}</span>
		                    <span><xform:text property="docCreateTime" value="${fsscLoanMainForm.docCreateTime}" showStatus="view"></xform:text></span>
		                </div>
	                </div>
                </div>
         
                <div class="ld-line20px"></div>
                <div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}</span>
		                    <span><xform:text property="fdBasePayWayName" value="${fsscLoanMainForm.fdBasePayWayName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}</span>
		                    <span><xform:text property="fdAccPayeeName" value="${fsscLoanMainForm.fdAccPayeeName}" showStatus="view"></xform:text></span>
		                </div>
		       			<div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}</span>
		                    <span><xform:text property="fdPayeeBank" value="${fsscLoanMainForm.fdPayeeBank}" showStatus="view"></xform:text></span>
		                </div>
		                <fssc:checkUseBank fdBank="BOC">
		                 	<div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}</span>
			                    <span><xform:text property="fdBankAccountNo" value="${fsscLoanMainForm.fdBankAccountNo}" showStatus="view"></xform:text></span>
			                </div>
				        </fssc:checkUseBank>
			            <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
			            	<div>
			                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}</span>
			                    <span><xform:text property="fdAccountAreaName" value="${fsscLoanMainForm.fdAccountAreaName}" showStatus="view"></xform:text></span>
			                </div>
			            </fssc:checkUseBank>
			            <div>
		                    <span class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}</span>
		                    <span><xform:text property="fdPayeeAccount" value="${fsscLoanMainForm.fdPayeeAccount}" showStatus="view"></xform:text></span>
		                </div>
	                </div>
                </div>
				
				<kmss:ifModuleExist path="/fssc/pres/">
					<c:if test="${showPres=='true' }">
						<div class="ld-line20px"></div>
						<div class="ld-entertain-detail">
							<div class="ld-entertain-detail-costInfo">
								<div onclick="openPresData();">
									<span class="titleCss">${lfn:message('fssc-pres:module.fssc.pres.openPresData') }</span>
								</div>
							</div>
						</div>
					</c:if>
				</kmss:ifModuleExist>
				
                <div class="ld-line20px"></div>
                <!-- 附件明细 -->
		        <div class="ld-rememberOne" style="padding-bottom:0.001rem;">
       				 <div class="ld-rememberOne-main">
		        		<div class="ld-remember-attach">
			                <div class="ld-remember-attach-title">
			                    <h3 class="titleCss">${lfn:message('fssc-loan:fsscLoanMain.attPayment')}</h3>
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
		       	<iframe id="frame" width="100%" frameborder="0"  height="500px" src ="${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=viewLbpm&fdId=${fsscLoanMainForm.fdId}"></iframe>
		       </div>
    		</div>
    		<kmss:auth requestURL="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=edit&fdId=${param.fdId}">
    		<c:if test="${fsscLoanMainForm.docStatus =='10' or fsscLoanMainForm.docStatus =='11'}">
	           <div class="editModel" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=edit&fdId=${fsscLoanMainForm.fdId }'"></div>
		    </c:if>
		    </kmss:auth>
			<input  hidden="true" value="${queryList}" name="data" >
    	</div>
    	<div class="backHome" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/mobile/'"></div>
</body>
</html>
