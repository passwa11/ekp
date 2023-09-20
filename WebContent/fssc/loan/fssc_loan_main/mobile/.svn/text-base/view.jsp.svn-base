<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@page import="com.landray.kmss.fssc.loan.util.FsscLoanUtil" %>
    <template:include ref="mobile.view" compatibleMode="true">
   	   <template:replace name="loading">
			<c:import url="/fssc/loan/fssc_loan_main/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscLoanMainForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
		
        <template:replace name="title">
            <c:out value="${fsscLoanMainForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-loan:table.fsscLoanMain') }" />
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_main/fsscLoanMain.do"  name="fsscLoanMainForm" method="post">
                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                <c:import url="/fssc/loan/fssc_loan_main/mobile/view_banner.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="fsscLoanMainForm"></c:param>
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
                            <div class="muiFormContent" >
                                <table class="muiSimple" >
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <%-- <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.docNumber')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanMainForm.docNumber}"></c:out>
                                        </td>
                                    </tr> --%>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                                <c:out value="${fsscLoanMainForm.fdCostCenterName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                                <c:out value="${fsscLoanMainForm.fdCompanyName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanPersonId" _xform_type="address">
                                                <xform:address propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanDept')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscLoanMainForm.fdLoanDeptName}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdExpectedDate" _xform_type="datetime">
                                                <xform:datetime property="fdExpectedDate" showStatus="view" dateTimeType="date" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMoney" _xform_type="text">
                                            <kmss:showNumber value="${fsscLoanMainForm.fdLoanMoney}" pattern="#,##0.00" />
                                                <%-- <xform:text property="fdLoanMoney" showStatus="view" mobile="true" style="width:95%;" /> --%>
                                            </div>
                                        </td>
                                    </tr>
                                    
                                                                        <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBaseCurrencyName" _xform_type="text">
                                             <c:out value="${fsscLoanMainForm.fdBaseCurrencyName}"></c:out>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdStandardMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdStandardMoney" _xform_type="text">
                                            <kmss:showNumber value="${fsscLoanMainForm.fdStandardMoney}" pattern="#,##0.00" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}
                                        </td>
                                        <td>
                                         <div id="_xform_fdTotalLoanMoney" _xform_type="text">
                                                  <kmss:showNumber value="${fsscLoanMainForm.fdTotalLoanMoney}" pattern="0.00"/>
                                          </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}
                                        </td>
                                        <td>
                                          <div id="_xform_fdTotalRepaymentMoney" _xform_type="text">
                                                  <kmss:showNumber value="${fsscLoanMainForm.fdTotalRepaymentMoney}" pattern="0.00"/>
                                          </div>
                                        </td>
                                    </tr>
                                    <tr>
                                     <td class="muiTitle">
				                        ${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}
				                    </td>
				                    <td>
				                        <%-- 累计未还款金额--%>
				                        <div id="_xform_fdTotalRepaymentMoney" _xform_type="text">
				                            <kmss:showNumber value="${fsscLoanMainForm.fdTotalNotRepaymentMoney}" pattern="#,##0.00" />
				                        </div>
				                    </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdReason')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReason" _xform_type="textarea">
                                                <xform:textarea property="fdReason" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                      <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOffsetterIds" _xform_type="address">
                                                <xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdAccPayeeName" _xform_type="text">
                                                <xform:text property="fdAccPayeeName" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPayeeAccount" _xform_type="text">
                                                <xform:text property="fdPayeeAccount" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPayeeBank" _xform_type="text">
                                                <xform:text property="fdPayeeBank" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                                <c:out value="${fsscLoanMainForm.fdBasePayWayName}" />
                                            </div>
                                        </td>
                                    </tr>
                                 <fssc:checkUseBank fdBank="BOC">
		                             <td class="muiTitle" width="8%">
		                                ${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}
		                            </td>
								 	<td >
				                      	 <div id="_xform_fdAccountAreaName" >
				                           <c:out value="${fsscLoanMainForm.fdBankAccountNo}" />
				                      	</div>
				                 	</td>
				            	</fssc:checkUseBank>
                                 <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
		                             <td class="muiTitle" width="8%">
		                                ${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}
		                            </td>
								 	<td >
				                      	 <div id="_xform_fdAccountAreaName" >
				                           <c:out value="${fsscLoanMainForm.fdAccountAreaName}" />
				                      	</div>
				                 	</td>
				            	</fssc:checkUseBank>
				            	 <tr>
								        <td class="muiTitle">
								            ${lfn:message('fssc-loan:fsscLoanMain.attPayment')}
								        </td>
								        <td>
								            <%-- 附件--%>
								            <c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								                <c:param name="fdKey" value="attPayment" />
								                <c:param name="formName" value="fsscLoanMainForm" />
								            </c:import>
								        </td>
								    </tr>
									<kmss:ifModuleExist path="/fssc/pres/">
                                        <c:if test="${showPres=='true' }">
                                            <tr>
                                                <td colspan="2">
                                                    <%--交单退单记录 --%>
                                                    ${lfn:message('fssc-pres:table.fsscPresMain')}<br />
                                                    <div data-dojo-type="mui/table/ScrollableHContainer">
                                                        <c:import url="/fssc/pres/fssc_pres_main/mobile/presData.jsp"></c:import>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </kmss:ifModuleExist>
                                </table>
                            </div>
                        </div>
                       <c:if test="${fsscLoanMainForm.docUseXform == 'true'}">
                           <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.BiaoDanNeiRong')}',icon:'mui-ul'">
                               <c:if test="${fsscLoanMainForm.docUseXform == 'true' || empty fsscLoanMainForm.docUseXform}">
                                   <div data-dojo-type="mui/table/ScrollableHContainer">
                                       <div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
                                           <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp" charEncoding="UTF-8">
                                               <c:param name="formName" value="fsscLoanMainForm" />
                                               <c:param name="fdKey" value="fsscLoanMain" />
                                               <c:param name="backTo" value="scrollView" />
                                               <c:param name="mobile" value="true" />
                                           </c:import>
                                       </div>
                                   </div>
                               </c:if>
                           </div>
                       </c:if>
                   	</div>
                  </div> 
                        
                     <div data-dojo-type="dojox/mobile/View" id="_noteView">
                       <div class="muiFormContent muiFlowInfoW">
                          <%--流程 --%>
                           <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
                               <c:param name="fdModelId" value="${fsscLoanMainForm.fdId}" />
                               <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                               <c:param name="formBeanName" value="fsscLoanMainForm" />
                           </c:import>
                       </div>
                    </div>
                  
                        <template:replace name="flowArea">
                            <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="fsscLoanMainForm"></c:param>
                                <c:param name="showNum" value="false"></c:param>
                                <c:param name="showOption" value="label"></c:param>
                            </c:import>
                        </template:replace>
                    
                     <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscLoanMainForm" viewName="lbpmView" allowReview="true">
                        <template:replace name="flowArea">
                        </template:replace>
                    </template:include>
                </div>
            </form>
                <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanMainForm" />
                    <c:param name="fdKey" value="fsscLoanMain" />
                    <c:param name="backTo" value="scrollView" />
                </c:import>
                <script type="text/javascript">
                    require(["mui/form/ajax-form!fsscLoanMainForm"]);
                </script>
        </template:replace>
    </template:include>
