<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
 
    <template:include ref="mobile.view" compatibleMode="true">
     <template:replace name="loading">
			<c:import url="/fssc/expense/fssc_expense_main/mobile/view_banner.jsp"
				charEncoding="UTF-8">
				<c:param name="formBeanName" value="fsscExpenseMainForm"></c:param>
				<c:param name="loading" value="true"></c:param>
			</c:import>
		</template:replace>
		
        <template:replace name="title">
            <c:out value="${fsscExpenseMainForm.docSubject} - " />
            <c:out value="${lfn:message('fssc-expense:table.fsscExpenseMain') }" />
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
	    	   				//FSSC_ShowStandardInfo(i);
	    	   			}
					});
	    	   	});
	            
	            //显示预算状态信息
	        	window.FSSC_ShowBudgetInfo = function(index){
	            	var fdProappId = '${fsscExpenseMainForm.fdProappId}';
	            	if(fdProappId){
	            		return FSSC_ShowProappInfo(index);
	            	}
	        		var fdBudgetShowType = "${docTemplate.fdBudgetShowType}";
	       			var fdBudgetStatus = $("[name='fdDetailList_Form["+index+"].fdBudgetStatus']").val()||0;
	       			var fdFeeStatus = $("[name='fdDetailList_Form["+index+"].fdFeeStatus']").val()||0;
	       			var showBudget = null,showInfo = null;
	       			var info = $("[name='fdDetailList_Form["+index+"].fdBudgetInfo']").val()||'[]';
	       			var fee = $("[name='fdDetailList_Form["+index+"].fdFeeInfo']").val()||'[]';
	       			info = JSON.parse(info.replace(/\'/g,'"'));
	       			fee = JSON.parse(fee.replace(/\'/g,'"'));
	       			//显示事前信息
	       			if(fdFeeStatus>fdBudgetStatus&&fdBudgetStatus=='0'){
	       				if(fdBudgetShowType=='1'){//显示图标
	    					//显示红灯及超申请提示
	    					$("#buget_status_"+index).attr("class","budget_container budget_status_"+fdFeeStatus);
	    					$("#buget_status_"+index).attr("title",lang['py.fee.'+fdFeeStatus]);
	    				}else{//显示金额
	    					var showInfo = null;
	    					for(var k=0;k<fee.length;k++){
	    						if(!showInfo||showInfo.fdUsableMoney>fee[k].fdUsableMoney){
	    							showInfo = fee[k];
	    						}
	    					}
	    					if(!showInfo){
	    						showInfo = {fdTotalMoney:0,fdUsingMoney:0,fdUsedMoney:0,fdUsableMoney:0};
	    					}
	    					$("#buget_status_"+index).html(lang['py.money.total']+showInfo.fdTotalMoney+"<br>"+lang['py.money.using']+showInfo.fdUsingMoney+"<br>"+lang['py.money.used']+showInfo.fdUsedMoney+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+index+"'>"+showInfo.fdUsableMoney+"</span>");
	    					$(".budget_money_"+index).css("color",fdFeeStatus=='2'?"red":"#333");
	    				}
	       			}else{//显示预算信息
	       				for(var i=0;i<info.length;i++){
	       	   				//获取可用金额最少的预算用于展示
	       	   				if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
	       	   					showBudget = info[i];
	       	   				}
	       	   			}
	       	   			if(!showBudget){
	       	   				showBudget = {fdTotalAmount:0,fdOccupyAmount:0,fdAlreadyUsedAmount:0,fdCanUseAmount:0}
	       	   			}
   	   					
	       	   			if(fdBudgetShowType=='1'){//显示图标
	       	   				$("#buget_status_"+index).attr("class","budget_container");
	       	   				$("#buget_status_"+index).addClass("budget_status_"+fdBudgetStatus);
	       	   				$("#buget_status_"+index).attr("title", "${lfn:message('fssc-expense:py.budget."+fdBudgetStatus+"')}" ); 
	       	   			}else{//显示金额
	       	   				$("#buget_status_"+index).html("${lfn:message('fssc-expense:py.money.total')}" +showBudget.fdTotalAmount+"<br>"+ "${lfn:message('fssc-expense:py.money.using')}"+showBudget.fdOccupyAmount+ "<br>"+ "${lfn:message('fssc-expense:py.money.used')}" +showBudget.fdAlreadyUsedAmount +"<br>"+  "${lfn:message('fssc-expense:py.money.usable')}" +"<span class='budget_money_"+index+"'>"+showBudget.fdCanUseAmount+"</span>");
	   	   					$(".budget_money_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
	       	   			}
	       			}
	        		}
	            window.FSSC_ShowProappInfo = function(index){
	            		var fdBudgetShowType = "${docTemplate.fdBudgetShowType}";
	            		$("#TABLE_DocList_fdDetailList_Form").find("tr").each(function(i){
	            			if(i==0)return;
	            			var fdProappStatus = $(this).find("[name$=fdProappStatus]").val()||'0';
	            			var fdProappInfo = $(this).find("[name$=fdProappInfo]").val()||'{}';
	            			fdProappInfo = JSON.parse(fdProappInfo.replace(/\'/g,'"'));
	            			var showBudget = {
	            					fdTotalAmount:fdProappInfo.fdTotalMoney||0,
	            					fdOccupyAmount:fdProappInfo.fdUsingMoney||0,
	            					fdAlreadyUsedAmount:fdProappInfo.fdUsedMoney||0,
	            					fdCanUseAmount:fdProappInfo.fdUsableMoney||0
	            				}
	            			
	            			if(fdBudgetShowType=='1'){//显示图标
	       	   				$("#buget_status_"+index).attr("class","budget_container");
	       	   				$("#buget_status_"+index).addClass("budget_status_"+fdProappStatus);
	       	   				$("#buget_status_"+index).attr("title", "${lfn:message('fssc-expense:py.proapp."+fdProappStatus+"')}" ); 
	       	   			}else{//显示金额
	       	   				$("#buget_status_"+index).html("${lfn:message('fssc-expense:py.money.total')}" +showBudget.fdTotalAmount+"<br>"+ "${lfn:message('fssc-expense:py.money.using')}"+showBudget.fdOccupyAmount+ "<br>"+ "${lfn:message('fssc-expense:py.money.used')}" +showBudget.fdAlreadyUsedAmount +"<br>"+  "${lfn:message('fssc-expense:py.money.usable')}" +"<span class='budget_money_"+index+"'>"+showBudget.fdCanUseAmount+"</span>");
	   	   					$(".budget_money_"+index).css("color",fdProappStatus=='2'?"red":"#333");
	       	   			}
	            		})
	            }
                var formInitData = {
                		fdBudgetShowType:'${docTemplate.fdBudgetShowType}',
                };
                var lang = {
                        "the": "${lfn:message('page.the')}",
                        "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                    'fdInvoiceList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseInvoiceDetail"))}',
                    'fdTravelList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseTravelDetail"))}',
                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseDetail"))}',
                    'fdAccountsList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseAccounts"))}',
                    'fdOffsetList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseOffsetLoan"))}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
			<form action="${LUI_ContextPath }/fssc/expense/fssc_expense_main/fsscExpenseMain.do"  name="fsscExpenseMainForm" method="post">
               <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                <c:import url="/fssc/expense/fssc_expense_main/mobile/view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="fsscExpenseMainForm"></c:param>
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
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_docSubject" _xform_type="text">
	                                               <xform:text property="docSubject" showStatus="view" mobile="true" style="width:95%;" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdCompanyId" _xform_type="dialog">
	                                               <c:out value="${fsscExpenseMainForm.fdCompanyName}" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdCostCenterId" _xform_type="dialog">
	                                               <c:out value="${fsscExpenseMainForm.fdCostCenterName}" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdClaimantId" _xform_type="address">
	                                               <xform:address propertyId="fdClaimantId" propertyName="fdClaimantName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                  <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdClaimantDept')}
	                                       </td>
	                                       <td>
	                                           <c:out value="${fsscExpenseMainForm.fdClaimantDeptName}"></c:out>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdTotalStandaryMoney" _xform_type="text">
	                                               <xform:text property="fdTotalStandaryMoney" showStatus="view" mobile="true" style="width:95%;" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdTotalApprovedMoney" _xform_type="text">
	                                               <xform:text property="fdTotalApprovedMoney" showStatus="view" mobile="true" style="width:95%;" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdProjectId" _xform_type="dialog">
	                                               <c:out value="${fsscExpenseMainForm.fdProjectName}" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                    <c:if test="${docTemplate.fdIsFee=='true' }">
			                            <tr>
			                                <td class="td_normal_title" width="16.6%">
			                                    ${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}
			                                </td>
			                                <td  width="83.0%">
			                                    <div id="_xform_fdContent" _xform_type="textarea">
			                                        <xform:dialog propertyName="fdFeeNames" propertyId="fdFeeIds" style="width:85%;" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}">
			                                        	dialogSelect(true,'fssc_expense_category_selectFee','fdFeeIds','fdFeeNames',null,{'docTemplateId':$('[name=docTemplateId]').val()});
			                                        </xform:dialog>
			                                    </div>
			                                </td>
			                            </tr>
			                            </c:if>
			                            <c:if test="${docTemplate.fdIsProapp=='true' }">
			                            <tr>
			                                <td class="td_normal_title" width="16.6%">
			                                    ${lfn:message('fssc-expense:fsscExpenseMain.fdProappName')}
			                                </td>
			                                <td width="83.0%">
			                                    <div id="_xform_fdContent" _xform_type="textarea">
			                                        <xform:dialog propertyName="fdProappName" propertyId="fdProappId" style="width:85%;" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}">
			                                        	dialogSelect(true,'fssc_expense_category_selectFee','fdFeeIds','fdFeeNames',null,{'docTemplateId':$('[name=docTemplateId]').val()});
			                                        </xform:dialog>
			                                    </div>
			                                </td>
			                            </tr>
			                            </c:if>
	                                    <c:if test="${docTemplate.fdIsAmortize=='true' }">
					                        <tr>
					                            <td class="td_normal_title" width="16.6%">
					                                ${lfn:message('fssc-expense:fsscExpenseMain.fdIsAmortize')}
					                            </td>
					                            <td  width="83.0%">
					                                <div id="_xform_fdContent" _xform_type="radio">
					                                    <xform:radio property="fdIsAmortize" htmlElementProperties="id='fdIsAmortize'" showStatus="view" mobile="true" >
					                                    	<xform:enumsDataSource enumsType="common_yesno"/>
					                                    </xform:radio>
					                                </div>
					                            </td>
					                        </tr>
					                        </c:if>
					                    <tr>
	                                       <td class="muiTitle">
	                                           ${lfn:message('fssc-expense:fsscExpenseMain.fdContent')}
	                                       </td>
	                                       <td>
	                                           <div id="_xform_fdContent" _xform_type="textarea">
	                                               <xform:textarea property="fdContent" showStatus="view" mobile="true" style="width:95%;" />
	                                           </div>
	                                       </td>
	                                   </tr>
	                                   <tr>
	                                   	<td>
	                                   		${lfn:message('fssc-expense:fsscExpenseMain.attachment')}
	                                   	</td>
	                                   	<td>
	                                   		<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="invoice" />
												<c:param name="formName" value="fsscExpenseMainForm"/>
												<c:param name="fdModelId" value="${fsscExpenseMainForm.fdId}"/>
											</c:import>
	                                   	</td>
	                                   </tr>
	                                  <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true'}">
		                                 <tr>
		                                      <td colspan="2">
		                                      	<!-- 出差明细 -->
		                                             ${lfn:message('fssc-expense:table.fsscExpenseTravelDetail')}
		                                         <div data-dojo-type="mui/table/ScrollableHContainer">
														<%@include file="/fssc/expense/fssc_expense_main/mobile/travel_detail.jsp"%>
												 </div>
		                                        </td>
		                                   </tr>  
	                                   </c:if>
	                                   <tr>
	                                       <td colspan="2">
	                                       	<!-- 费用明细-->
	                                       		${lfn:message('fssc-expense:table.fsscExpenseDetail')}
		                                       	<div data-dojo-type="mui/table/ScrollableHContainer">
														<%@include file="/fssc/expense/fssc_expense_main/mobile/expense_detail.jsp"%>
												</div>
	                                        </td>
	                               		</tr> 
	                                   	<tr>
	                                   	<td colspan="2">
	                                   		<!-- 发票明细 -->
	                                   		${lfn:message('fssc-expense:table.fsscExpenseInvoiceDetail')}
	                                   		<div data-dojo-type="mui/table/ScrollableHContainer">
													<%@include file="/fssc/expense/fssc_expense_main/mobile/invoice_detail.jsp"%>
											</div>
	                                   </td>
	                                    </tr>
	                                 	<tr>
											<td colspan="2">
												<%--收款账户信息 --%>
												${lfn:message('fssc-expense:table.fsscExpenseAccounts')}<br />
												<div data-dojo-type="mui/table/ScrollableHContainer">
													<%@include file="/fssc/expense/fssc_expense_main/mobile/accounts_detail.jsp"%>
												</div>
											</td>
										</tr>
										 <c:if test="${fsscExpenseMainForm.fdIsOffsetLoan=='true' && offsetMoney >0}">
	                                   	<tr>
											<td colspan="2">
												<%--冲抵借款 --%>
												${lfn:message('fssc-expense:table.fsscExpenseOffsetLoan')}<br />
												<div data-dojo-type="mui/table/ScrollableHContainer">
													<%@include file="/fssc/expense/fssc_expense_main/mobile/offsetLoan_detail.jsp"%>
												</div>
											</td>
										</tr>
										</c:if>
										 <c:if test="${docTemplate.fdIsAmortize=='true' }">
											<tr>
											   <td colspan="2">
											   ${lfn:message('fssc-expense:table.fsscExpenseAmortize')}<br />
											   <div data-dojo-type="mui/table/ScrollableHContainer">
											   	<c:import url="/fssc/expense/fssc_expense_main/mobile/amortize_detail.jsp"></c:import>
											   </div>
											   </td>
											</tr>
										</c:if>
									   <kmss:ifModuleExist path="/fssc/ccard/">
										   <c:if test="${not empty fsscExpenseMainForm.fdTranDataList_Form }">
										   <tr>
											   <td colspan="2">
												   <%--交易数据信息 --%>
												   ${lfn:message('fssc-expense:table.fsscExpenseTranData')}<br />
												   <div data-dojo-type="mui/table/ScrollableHContainer">
													   <%@include file="/fssc/expense/fssc_expense_main/mobile/trandata_detail.jsp"%>
												   </div>
											   </td>
										   </tr>
										   </c:if>
									   </kmss:ifModuleExist>
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
	                       	  
	                      <c:if test="${fsscExpenseMainForm.docUseXform == 'true'}">  	  
		                       <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.BiaoDanNeiRong')}',icon:'mui-ul'">
	                            <c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">
	                                <div data-dojo-type="mui/table/ScrollableHContainer">
	                                    <div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
	                                        <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp" charEncoding="UTF-8">
	                                            <c:param name="formName" value="fsscExpenseMainForm" />
	                                            <c:param name="fdKey" value="fsscExpenseMain" />
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
                         <%--流程记录 --%>
						<div data-dojo-type="dojox/mobile/View" id="_noteView">
							<div class="muiFormContent muiFlowInfoW">
		                        <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
		                            <c:param name="fdModelId" value="${fsscExpenseMainForm.fdId}" />
		                            <c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
		                            <c:param name="formBeanName" value="fsscExpenseMainForm" />
		                        </c:import>
		                    </div>
		                </div>
		                <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="javascript:window.building();" formName="fsscExpenseMainForm" viewName="lbpmView" allowReview="true">
		                    <template:replace name="flowArea">
		                    </template:replace>
		                </template:include>
                   </div>
				</form>
               <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                   <c:param name="formName" value="fsscExpenseMainForm" />
                   <c:param name="fdKey" value="fsscExpenseMain" />
                   <c:param name="backTo" value="scrollView" />
               </c:import>
               <script type="text/javascript">
                   require(["mui/form/ajax-form!fsscExpenseMainForm"]);
               </script>
        </template:replace>
    </template:include>
