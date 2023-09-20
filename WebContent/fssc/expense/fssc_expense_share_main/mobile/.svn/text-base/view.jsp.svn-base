<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
   
    <template:include ref="mobile.view" compatibleMode="true">
     <template:replace name="loading">
			<c:import url="/fssc/expense/fssc_expense_share_main/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscExpenseShareMainForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
    
        <template:replace name="title">
            <c:out value="${fsscExpenseShareMainForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
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
    	   		ready(function() {
    	   			var len = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
    	   			for(var i=0;i<len;i++){
    	   				FSSC_ShowBudgetInfo(i);
    	   			}
				});
    	   	});
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseShareDetail"))}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_share_main/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <form action="${LUI_ContextPath }/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do"  name="fsscExpenseShareMainForm" method="post">
                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
	                <c:import url="/fssc/expense/fssc_expense_share_main/mobile/view_banner.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="fsscExpenseShareMainForm"></c:param>
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
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperatorId" _xform_type="address">
                                                <xform:address propertyId="fdOperatorId" propertyName="fdOperatorName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperatorDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperatorDeptId" _xform_type="address">
                                                <xform:address propertyId="fdOperatorDeptId" propertyName="fdOperatorDeptName" orgType="ORG_TYPE_ALL" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                                ${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdModelId" _xform_type="dialog">
                                                <c:out value="${fsscExpenseShareMainForm.fdModelName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperateDate')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperateDate" _xform_type="datetime">
                                                <xform:datetime property="fdOperateDate" showStatus="view" dateTimeType="date" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdDescription')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdDescription" _xform_type="textarea">
                                                <xform:textarea property="fdDescription" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
								        <td class="muiTitle">
								            ${lfn:message('fssc-expense:fsscExpenseShareMain.attachment')}
								        </td>
								        <td>
								            <div id="_xform_fdAttachment" _xform_type="text">
								            <c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								               <c:param name="fdKey" value="attShareMain" />
					                           <c:param name="formName" value="fsscExpenseShareMainForm" />
								            </c:import>
								            </div>
								        </td>
								    </tr>
                                    <tr>
                                       <!-- 费用明细 -->
                                    	<td colspan="2">
                                    		${lfn:message('fssc-expense:table.fsscExpenseDetail') }
                                    		<div data-dojo-type="mui/table/ScrollableHContainer">
                                    			<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_invoice">
			                        				<table class="muiNormal"  width="100%" id="TABLE_EXPENSE" border="0" cellspacing="0" cellpadding="0">
			                        				<tr align="center" class="tr_normal_title">
			                        				<td >${lfn:message('page.serial') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney') }</td>
			                        				<td >${lfn:message('fssc-expense:fsscExpenseDetail.fdUse') }</td>
			                        				</tr>
			                        				</table>
			                        				<input type="hidden" name="fdAccountsList_Flag" value="1">
			                        			</div>
			                        		</div>
			                        	</td>
                                     </tr>  
                                    <tr>
                                       <!-- 分摊明细 -->
                                    	<td colspan="2">
												${lfn:message('fssc-expense:fsscExpenseShareMain.fdDetailList')}<br />
												<div data-dojo-type="mui/table/ScrollableHContainer">
													<%@include file="/fssc/expense/fssc_expense_share_main/mobile/share_detail.jsp"%>
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
		                            <c:param name="fdModelId" value="${fsscExpenseShareMainForm.fdId}" />
		                            <c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
		                            <c:param name="formBeanName" value="fsscExpenseShareMainForm" />
		                        </c:import>
		                    </div>
		                </div>
		                <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscExpenseShareMainForm" viewName="lbpmView" allowReview="true">
		                    <template:replace name="flowArea">
		                    </template:replace>
		                </template:include>
                	</div>
            </form>
                <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="fdKey" value="fsscExpenseShareMain" />
                    <c:param name="backTo" value="scrollView" />
                </c:import>
                <script type="text/javascript">
                    require(["mui/form/ajax-form!fsscExpenseShareMainForm"]);
                </script>
        </template:replace>
    </template:include>
