<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
    
    <template:include ref="mobile.view" compatibleMode="true">
     <template:replace name="loading">
			<c:import url="/fssc/expense/fssc_expense_balance/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscExpenseBalanceForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
    
        <template:replace name="title">
            <c:out value="${fsscExpenseBalanceForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
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
	            require(["dojo/store/Memory","dojo/topic", "dojo/ready"],function(Memory, topic,ready){
	    	   		window._narStore = new Memory({data:[{'text':'${lfn:message("sys-mobile:mui.mobile.info")}',
	    	   			'moveTo':'_contentView','selected':true},{'text':'${lfn:message("sys-mobile:mui.mobile.review.record")}',
	    	   			'moveTo':'_noteView'}]});
	    	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	    	   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
	    	   		});
	    	   	});
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseBalanceDetail"))}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_balance/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <form action="${LUI_ContextPath }/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do"  name="fsscExpenseBalanceForm" method="post">
            <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                 <c:import url="/fssc/expense/fssc_expense_balance/mobile/view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="fsscExpenseBalanceForm"></c:param>
				</c:import>
               <div data-dojo-type="mui/fixed/Fixed" id="fixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" data-dojo-props="store:_narStore"></div>
					</div>
				</div>
				<div data-dojo-type="dojox/mobile/View" id="_contentView">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}
                                        </td>
                                        <td>
                                            <ui:person personId="${fsscExpenseBalanceForm.docCreatorId}" personName="${fsscExpenseBalanceForm.docCreatorName}" />
                                        </td>
                                    </tr>
                                     <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreatorDept')}
                                        </td>
                                        <td>
                                            <c:out value="${fsscExpenseBalanceForm.docCreatorDeptName}"></c:out>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                                <c:out value="${fsscExpenseBalanceForm.fdCompanyName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                                <c:out value="${fsscExpenseBalanceForm.fdCostCenterName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdVoucherTypeId" _xform_type="text">
                                              <xform:text property="fdVoucherTypeName" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                                <c:out value="${fsscExpenseBalanceForm.fdCurrencyName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMonth" _xform_type="text">
                                                <xform:text property="fdMonth" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdSubject" _xform_type="text">
                                                <xform:text property="fdSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdAttNum')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdAttNum" _xform_type="text">
                                                <xform:text property="fdAttNum" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
								        <td class="muiTitle">
								            ${lfn:message('fssc-expense:fsscExpenseBalance.attachment')}
								        </td>
								        <td>
								            <div id="_xform_fdAttachment" _xform_type="text">
								            <c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								               <c:param name="fdKey" value="attBalance" />
					                           <c:param name="formName" value="fsscExpenseBalanceForm" />
								            </c:import>
								            </div>
								        </td>
								    </tr>
                                    <tr>
	                                    <td colspan="2">
												<%--调账明细 --%>
												<%-- ${lfn:message('fssc-expense:table.fsscExpenseBanlance')}<br /> --%>
												<div data-dojo-type="mui/table/ScrollableHContainer">
													<%@include file="/fssc/expense/fssc_expense_balance/mobile/balance_detail.jsp"%>
												</div>
											</td>
	                                    </tr>
                                 </table>   
                                       
                           </div>             
                        </div>
                      </div>
                    </div>  
						<%--流程记录 --%>
						<div data-dojo-type="dojox/mobile/View" id="_noteView">
							<div class="muiFormContent muiFlowInfoW">
		                        <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
		                            <c:param name="fdModelId" value="${fsscExpenseBalanceForm.fdId}" />
		                            <c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
		                            <c:param name="formBeanName" value="fsscExpenseBalanceForm" />
		                        </c:import>
		                    </div>
		                </div>
		                <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscExpenseBalanceForm" viewName="lbpmView" allowReview="true">
		                    <template:replace name="flowArea">
		                    </template:replace>
		                </template:include>
				</div>
            </form>
                <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseBalanceForm" />
                    <c:param name="fdKey" value="fsscExpenseBalance" />
                    <c:param name="backTo" value="scrollView" />
                </c:import>
                <script type="text/javascript">
                    require(["mui/form/ajax-form!fsscExpenseBalanceForm"]);
                </script>
        </template:replace>
    </template:include>
