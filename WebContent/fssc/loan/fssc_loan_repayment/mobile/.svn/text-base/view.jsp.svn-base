<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.landray.kmss.fssc.loan.util.FsscLoanUtil" %>
    <template:include ref="mobile.view" compatibleMode="true">
      <template:replace name="loading">
			<c:import url="/fssc/loan/fssc_loan_repayment/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscLoanRepaymentForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
		
        <template:replace name="title">
            <c:out value="${fsscLoanRepaymentForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
        </template:replace>
        <template:replace name="head">
            <style>
                .detailTips{
                				color: red;
                	    		font-weight: lighter;
                	    		display: inline-block;
                	    		font-size: 1rem;
                			}
                			.muiFormNoContent{
                				padding-left:1rem;
                				border-top:1px solid #ddd;
                				border-bottom: 1px solid #ddd;
                			}
                			.muiDocFrameExt{
                				margin-left: 0rem;
                			}
                			.muiDocFrameExt .muiDocInfo{
                				border: none;
                			}
            </style>
            <script type="text/javascript">
	        	require(["dojo/store/Memory","dojo/topic", "dojo/ready",'mui/dialog/Tip'],function(Memory, topic,ready,Tip){
	    	   		window._narStore = new Memory({data:[{'text':'${lfn:message("sys-mobile:mui.mobile.info")}',
	    	   			'moveTo':'_contentView','selected':true},{'text':'${lfn:message("sys-mobile:mui.mobile.review.record")}',
	    	   			'moveTo':'_noteView'}]});
	    	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	    	   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
	    	   		});
	    	   		ready(function() {
	    	   			
					});
	    	   	});
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_repayment/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do"  name="fsscLoanRepaymentForm" method="post">
           <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                <c:import url="/fssc/loan/fssc_loan_repayment/mobile/view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="fsscLoanRepaymentForm"></c:param>
				</c:import>
				  <div data-dojo-type="mui/fixed/Fixed" id="fixed">
						<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
							<div data-dojo-type="mui/nav/NavBarStore"
								data-dojo-props="store:_narStore"></div>
						</div>
				  </div>
				  
				 <div data-dojo-type="dojox/mobile/View" id="_contentView">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                                <c:out value="${fsscLoanRepaymentForm.fdLoanMainName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdRepaymentPersonId" _xform_type="address">
                                                <xform:address propertyId="fdRepaymentPersonId" propertyName="fdRepaymentPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanRepaymentForm.fdRepaymentDeptName}"></c:out>
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}
                                        </td>
                                        <td>
                                            <kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="0.00"></kmss:showNumber>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdRepaymentMoney" _xform_type="text">
                                              <kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="0.00"></kmss:showNumber>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                                <c:out value="${fsscLoanRepaymentForm.fdBasePayWayName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPaymentAccount" _xform_type="text">
                                                <xform:text property="fdPaymentAccount" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReason" _xform_type="textarea">
                                                <xform:textarea property="fdReason" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>  
                                  <tr>
	                                  <td>
	                                   		${lfn:message('fssc-loan:fsscLoanRepayment.attPayment')}
	                                  </td>
	                                  <td>
	                                   	<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="attachment" />
												<c:param name="formName" value="fsscLoanRepaymentForm"/>
												<c:param name="fdModelId" value="${fsscLoanRepaymentForm.fdId}"/>
										</c:import>
	                                   </td>
	                                   </tr>  
                                   <%--  <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.docCreatorDept')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanRepaymentForm.docCreatorDeptName}"></c:out>
                                        </td>
                                    </tr> --%>
                                </table>
                             </div>
                           </div>
                        </div>
                      </div> 
                        
                     <div data-dojo-type="dojox/mobile/View" id="_noteView">
	                       <div class="muiFormContent muiFlowInfoW">
	                          <%--流程 --%>  
	                            <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
	                                <c:param name="fdModelId" value="${fsscLoanRepaymentForm.fdId}" />
	                                <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
	                                <c:param name="formBeanName" value="fsscLoanRepaymentForm" />
	                            </c:import>
							</div>
						</div>
						
						 <template:replace name="flowArea">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="fsscLoanRepaymentForm"></c:param>
                                <c:param name="showNum" value="false"></c:param>
                                <c:param name="showOption" value="label"></c:param>
                            </c:import>
                        </template:replace>
  
                     	<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscLoanRepaymentForm" viewName="lbpmView" allowReview="true">
                        	<template:replace name="flowArea">
                        	</template:replace>
                    	</template:include>
                  </div>
            </form>
                <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanRepaymentForm" />
                    <c:param name="fdKey" value="fsscLoanRepayment" />
                    <c:param name="backTo" value="scrollView" />
                   <%--  <c:param name="onClickSubmitButton" value="Com_Submit(document.fsscLoanRepaymentForm, 'update');" /> --%>
                </c:import>
                <script type="text/javascript">
                    require(["mui/form/ajax-form!fsscLoanRepaymentForm"]);
                </script>
        </template:replace>
    </template:include>
