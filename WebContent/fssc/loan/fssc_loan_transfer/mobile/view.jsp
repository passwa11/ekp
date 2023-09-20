<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.loan.util.FsscLoanUtil" %>
    
    <template:include ref="mobile.view" compatibleMode="true">
       <template:replace name="loading">
			<c:import url="/fssc/loan/fssc_loan_transfer/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscLoanTransferForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
    
        <template:replace name="title">
            <c:out value="${fsscLoanTransferForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_transfer/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do"  name="fsscLoanTransferForm" method="post">
			  <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                <c:import url="/fssc/loan/fssc_loan_transfer/mobile/view_banner.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="fsscLoanTransferForm"></c:param>
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
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdLoanMain')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                                <c:out value="${fsscLoanTransferForm.fdLoanMainName}" />
                                            </div>
                                        </td>
                                    </tr>
                                   <%--  <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.docCreator')}
                                        </td>
                                        <td>
                                            <ui:person personId="${fsscLoanTransferForm.docCreatorId}" personName="${fsscLoanTransferForm.docCreatorName}" />
                                        </td>
                                    </tr> 
                                    --%>
                                    
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOut')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanTransferForm.fdTurnOutName}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOutDept')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanTransferForm.fdTurnOutDeptName}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney')}
                                        </td>
                                        <td>
                                            <kmss:showNumber value="${fsscLoanTransferForm.fdTransferMoney}" pattern="0.00"></kmss:showNumber>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdCanOffsetMoney')}
                                        </td>
                                        <td>
                                            <kmss:showNumber value="${fsscLoanTransferForm.fdCanOffsetMoney}" pattern="0.00"></kmss:showNumber>
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveId" _xform_type="address">
                                                <xform:address propertyId="fdReceiveId" propertyName="fdReceiveName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveDeptId" _xform_type="address">
                                                <xform:address propertyId="fdReceiveDeptId" propertyName="fdReceiveDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveCostCenterId" _xform_type="dialog">
                                                <c:out value="${fsscLoanTransferForm.fdReceiveCostCenterName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReason')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReason" _xform_type="textarea">
                                                <xform:textarea property="fdReason" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
	                                   	<td>
	                                   		${lfn:message('fssc-loan:fsscLoanTransfer.attPayment')}
	                                   	</td>
	                                   	<td>
	                                   		<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="attachment" />
												<c:param name="formName" value="fsscLoanTransferForm"/>
												<c:param name="fdModelId" value="${fsscLoanTransferForm.fdId}"/>
											</c:import>
	                                   	</td>
	                                   </tr>   
                                </table>
                            </div>
                        </div>
                    </div>
                   <%--  <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>

                        <kmss:auth requestURL="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do?method=edit&fdId=${param.fdId}">
                            <div data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'',label:'${lfn:message('button.edit') }',align:'',href:'/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do?method=edit&fdId=${param.fdId}'"></div>
                        </kmss:auth>
                    </ul> --%>
                </div>
                
                <div data-dojo-type="dojox/mobile/View" id="_noteView">
                       <div class="muiFormContent muiFlowInfoW">
                          <%--流程 --%>
                           <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
                               <c:param name="fdModelId" value="${fsscLoanTransferForm.fdId}" />
                               <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanTransfer" />
                               <c:param name="formBeanName" value="fsscLoanTransferForm" />
                           </c:import>
                       </div>
                 </div>
                  <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscLoanTransferForm" viewName="lbpmView" allowReview="true">
                      <template:replace name="flowArea">
                      </template:replace>
                  </template:include>
			</div>
            </form>
			  <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanTransferForm" />
                    <c:param name="fdKey" value="fsscLoanTransfer" />
                    <c:param name="backTo" value="scrollView" />
                </c:import>
                
                <script type="text/javascript">
                    require(["mui/form/ajax-form!fsscLoanTransferForm"]);
                </script>
        </template:replace>
    </template:include>
