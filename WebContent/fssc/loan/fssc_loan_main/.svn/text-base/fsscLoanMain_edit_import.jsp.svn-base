<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <template:replace name="head">
        <style type="text/css">
            
            		.lui_paragraph_title{
            			font-size: 15px;
            			color: #15a4fa;
            	    	padding: 15px 0px 5px 0px;
            		}
            		.lui_paragraph_title span{
            			display: inline-block;
            			margin: -2px 5px 0px 0px;
            		}
            		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            		    border: 0px;
            		    color: #868686
            		}
            		
        </style>
        <script type="text/javascript">
            var formInitData = {
            		fdIsRequiredFee:"${fdIsRequiredFee}",
            		fdIsProject:"${fdIsProject}"
            };
            var messageInfo = {
 				"fssc-loan:module.docSubject.message" : "${lfn:message('fssc-loan:module.docSubject.message')}",
				"fssc-loan:fsscLoanMain.fdExpectedDate.IsEarly" : "${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate.IsEarly')}",
                "fssc-loan:message.fsscLoanMain.fdCompany.isNull" : "${lfn:message('fssc-loan:message.fsscLoanMain.fdCompany.isNull')}",
                "fssc-loan:tips.exchangeRateNotExist" : "${lfn:message('fssc-loan:tips.exchangeRateNotExist')}"
            };
            //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("data.js");
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("fsscLoanMain_edit.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
            LUI.ready(function(){
            	var extendFields = $(".extendFields>td").length;
            	$(".extendFields>td").eq(extendFields-1).attr("colspan",1+6-extendFields);
            })
            function changeLoanMoney(obj){
            	$("[name=fdLoanMoney]").val(obj.value);
                FSSC_ChangeStandardMoney();
            }
        </script>
    </template:replace>
    <c:if test="${fsscLoanMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscLoanMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscLoanMainForm.docSubject} - " />
                    <c:out value="${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
             <c:if test="${param.approveModel eq 'right'}">
                 <c:if test="${ fsscLoanMainForm.method_GET == 'edit' }">
                     <c:if test="${ fsscLoanMainForm.docStatus=='10' || fsscLoanMainForm.docStatus=='11' }">
                         <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" styleClass="lui_widget_btn_primary" isForcedAddClass="true"  />
                     </c:if>
                     <c:if test="${ fsscLoanMainForm.docStatus=='10' || fsscLoanMainForm.docStatus=='11' || fsscLoanMainForm.docStatus=='20' }">
                         <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"  />
                     </c:if>
                 </c:if>
                 <c:if test="${ fsscLoanMainForm.method_GET == 'add' }">
                     <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="mySubmit('10','save',true);" />
                     <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="mySubmit('20','save');" styleClass="lui_widget_btn_primary" isForcedAddClass="true"  />
                 </c:if>
             </c:if>
             <c:if test="${param.approveModel ne 'right'}">
                 <c:if test="${ fsscLoanMainForm.method_GET == 'edit' }">
                     <c:if test="${ fsscLoanMainForm.docStatus=='10' || fsscLoanMainForm.docStatus=='11' }">
                         <ui:button text="${ lfn:message('button.savedraft') }" onclick="mySubmit('10','update',true);" />
                     </c:if>
                     <c:if test="${ fsscLoanMainForm.docStatus=='10' || fsscLoanMainForm.docStatus=='11' || fsscLoanMainForm.docStatus=='20' }">
                         <ui:button text="${ lfn:message('button.submit') }" onclick="mySubmit('20','update');" />
                     </c:if>
                 </c:if>
                 <c:if test="${ fsscLoanMainForm.method_GET == 'add' }">
                     <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="mySubmit('10','save',true);" />
                     <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="mySubmit('20','save');" />
                 </c:if>
             </c:if>
             <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
         </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
                <ui:menu-item text="${docTemplateName }"  />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
        <c:if test="${param.approveModel ne 'right'}">
            <form action="${LUI_ContextPath }/fssc/loan/fssc_loan_main/fsscLoanMain.do"  name="fsscLoanMainForm" method="post">
		</c:if>
                <ui:tabpage expand="false" var-navwidth="90%">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            <table width="100%">
                                <tr>
                                    <td align="center" height="80px" style="width:45%;text-align:center;font-size:25px;">
                                <c:if test="${empty fsscLoanMainForm.docSubject }">
                            		${ docTemplate.fdName}
                            	</c:if>
                            	<c:if test="${not empty fsscLoanMainForm.docSubject }">
                            		${ fsscLoanMainForm.docSubject}
                            	</c:if>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.docSubject')}
                            </td>
                            <td colspan="5" width="83.0%">
                                <%-- 标题  style="width:45%;text-align:center;font-size:25px;" --%>
                                <xform:text property="docSubject" style="width:95%;" />
                                <span id="docSubjectSp" class="txtstrong">*</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}
                            </td>
                            <td width="16.6%">
                                <%-- 借款人--%>
                                <div id="_xform_fdLoanPersonId" _xform_type="address">
                                	<!-- 启用提单转授权 -->
									<c:if test="${fdIsAuthorize=='true'}">
										<xform:dialog propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" style="width:95%;">
				                            dialogSelect(false,'fssc_loan_selectAuthorize','fdLoanPersonId','fdLoanPersonName',null,null,onValueChangeFdLoanPerson);
				                        </xform:dialog>
									</c:if>
									<!-- 未启用提单转授权 -->
									<c:if test="${fdIsAuthorize=='false'}">
										<xform:address propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" onValueChange="onValueChangeFdLoanPerson" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" style="width:95%;" />
									</c:if>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdLoanDept')}
                            </td>
                            <td width="16.6%">
                                <%-- 借款人部门--%>
                                <div id="_xform_fdLoanDeptId" _xform_type="address">
                                    <xform:address propertyId="fdLoanDeptId" propertyName="fdLoanDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="readOnly" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" dateTimeType="dateTime" showStatus="readOnly" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}
                            </td>
                            <td colspan="1" width="16.6%">
                                <%-- 费用归属公司--%>
                                <div id="_xform_fdCompanyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}" style="width:95%;">
                                        oldFdCompanyId = $('[name=fdCompanyId]').val();
                                        dialogSelect(false,'eop_basedata_company_getCompanyByPerson','fdCompanyId','fdCompanyName',null,{'fdPersonId':$('[name=fdLoanPersonId]').val(),fdModelName:'com.landray.kmss.fssc.loan.model.FsscLoanMain'},selectFdCompanyNameCallback);
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}
                            </td>
                            <td width="16.6%">
                                    <%-- 费用承担部门--%>
                                <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                    <%--,'selectType':'person','fdPersonId':$('[name=fdLoanPersonId]').val()--%>
                                    <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}
                            </td>
                            <td width="16.6%">
                                    <%-- 预计还款日期--%>
                                <div id="_xform_fdExpectedDate" _xform_type="datetime">
                                    <xform:datetime property="fdExpectedDate" onValueChange="onChangFdExpectedDate()" validators="checkExpectedDate(${fdIsContRepayDay})" showStatus="edit" dateTimeType="date" required="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/fee">
	                        <c:if test="${fdIsRequiredFee==true}">
		                        <tr>
		                            <td class="td_normal_title" width="16.6%">
		                                    <span class="tempClass" style="display:none;">${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}</span>
		                            </td>
		                            <td colspan="5" width="83.0%">
		                                <%-- 关联事前申请--%>
		                                <span class="tempClass" style="display:none;">
		                                <div id="_xform_fdOffsetterIds" _xform_type="dialog">
		                                    <xform:dialog propertyId="fdFeeMainId" propertyName="fdFeeMainName" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}" style="width:95%;">
		                                        dialogSelect(false,'fssc_loan_fee_main_getFeeMain','fdFeeMainId','fdFeeMainName',null,{'docTemplateId':$('[name=docTemplateId]').val(),'fdPersonId':$('[name=fdLoanPersonId]').val()},FSSC_AfterFeeMainSelected);
		                                    </xform:dialog>
		                                    <span class="txtstrong" style="display:none;">*</span>
		                                </div>
		                                </span>
		                            </td>
		                        </tr>
	                        </c:if>
                        </kmss:ifModuleExist>
                        <c:if test="${fdIsProject==true||fn:indexOf(fdExtendFields,'1')>-1||fn:indexOf(fdExtendFields,'2')>-1}">
                        <tr class="extendFields">
                           <c:if test="${fdIsProject==true}">
                     		    <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}
	                            </td>
	                            <td width="16.6%" class="fdBaseProjectContent">
	                            <%-- 项目--%>
                                <div id="_xform_fdBaseProjectId" _xform_type="dialog" >
                                    <xform:dialog propertyId="fdBaseProjectId" propertyName="fdBaseProjectName" required="true" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_project_project','fdBaseProjectId','fdBaseProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':1},afterSelectProject);
                                    </xform:dialog>
                                </div>
                            	</td>
                     	 </c:if>
                     	 <c:if test="${fn:contains(fdExtendFields,'1')}">
                     		<td class="td_normal_title fdBaseWbs" width="16.6%" >
                                ${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}
                            </td>
                            <td width="16.6%" class="fdBaseWbs" id="fdBaseWbss">
                                <%-- WBS号--%>
                                <div id="_xform_fdBaseWbsId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdBaseWbsId" propertyName="fdBaseWbsName" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_wbs_fdWbs','fdBaseWbsId','fdBaseWbsName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectId':$('[name=fdBaseProjectId]').val(),'selectType':'person'});
                                    </xform:dialog>
                               </div>
                            </td>
                         </c:if>
                         <c:if test="${fn:contains(fdExtendFields,'2')}">
                            <td class="td_normal_title fdBaseInnerOrder" width="16.6%" >
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}
                            </td>
                            <td width="16.6%" class="fdBaseInnerOrder" id="fdBaseInnerOrders">
                                    <%-- 内部订单--%>
                                <div id="_xform_fdBaseInnerOrderId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdBaseInnerOrderId" propertyName="fdBaseInnerOrderName" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdBaseInnerOrderId','fdBaseInnerOrderName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'person'});
                                    </xform:dialog>
                                </div>
                            </td>
                        </c:if>
                         </tr>
                         </c:if>
                         <c:if test="${fn:indexOf(fdExtendFields,'3')>-1 }">
                         <tr>
                                <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}
                                </td>
                                <td colspan="5" width="83.0%">
                                    <div id="_xform_fdBaseProjectAccountingId" _xform_type="dialog">
                                        <xform:dialog propertyName="fdBaseProjectAccountingName" propertyId="fdBaseProjectAccountingId" style="width:95%;" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}">
                                        	dialogSelect(false,'eop_basedata_project_project','fdBaseProjectAccountingId','fdBaseProjectAccountingName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':2});
                                        </xform:dialog>
                                    </div>
                                </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdApplyMoney')}
                            </td>
                            <td width="16.6%">
                                <%-- 借款金额--%>
                                <div id="_xform_fdLoanMoney" _xform_type="text">
                                    <input name="fdApplyMoney" onblur="changeLoanMoney(this)" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}" class="inputsgl" value="<kmss:showNumber value="${fsscLoanMainForm.fdApplyMoney==null?fsscLoanMainForm.fdLoanMoney:fsscLoanMainForm.fdApplyMoney}" pattern="##0.00"></kmss:showNumber>" type="text" validate="required number min(0) scaleLength(2)" style="width:93%;">
                                	<span class="txtstrong" style="float:right;">*</span>
                                	<input type="hidden" name="fdLoanMoney" value="${ fsscLoanMainForm.fdLoanMoney}"/>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 币种--%>
                                <div id="_xform_fdOffsetterIds" _xform_type="address">
                                    <xform:dialog propertyId="fdBaseCurrencyId"   propertyName="fdBaseCurrencyName" required="true"   showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}" style="width:90%;">
                                        FSSC_SelectCurrency();
                                    </xform:dialog>

                                </div>
                                    <xform:text property="fdExchangeRate"  showStatus="readonly" />
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdStandardMoney')}
                            </td>
                            <td width="16.6%">
                                    <%-- 本币金额--%>
                                <div id="_xform_fdStandardMoney" _xform_type="address">
                                    <xform:text property="fdStandardMoney"  showStatus="readonly" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                    ${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}
                            </td>
                            <td width="16.6%">
                                    <%-- 累计借款金额--%>
                                <div id="_xform_fdTotalLoanMoney" _xform_type="text">
                                    <span id="fdTotalLoanMoney"><kmss:showNumber value="${fsscLoanMainForm.fdTotalLoanMoney}" pattern="##0.00"></kmss:showNumber> </span>
                                    <xform:text property="fdTotalLoanMoney" showStatus="noShow" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}
                            </td>
                            <td width="16.6%">
                                <%-- 累计已还款金额--%>
                                <div id="_xform_fdTotalRepaymentMoney" _xform_type="text">
                                	<span id="fdTotalRepaymentMoney"><kmss:showNumber value="${fsscLoanMainForm.fdTotalRepaymentMoney}" pattern="##0.00"></kmss:showNumber> </span>
                                    <xform:text property="fdTotalRepaymentMoney" showStatus="noShow" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}
                            </td>
                            <td width="16.6%">
                                <%-- 累计未还款金额--%>
                                <div id="_xform_fdTotalNotRepaymentMoney" _xform_type="text">
                                	<span id="fdTotalNotRepaymentMoney"><kmss:showNumber value="${fsscLoanMainForm.fdTotalNotRepaymentMoney}" pattern="##0.00"></kmss:showNumber> </span>
                                    <xform:text property="fdTotalNotRepaymentMoney" showStatus="noShow" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                         <c:if test="${fdIsErasable==true}">
	                        <tr>
	                          
	                            <td class="td_normal_title" width="16.6%">
	                                ${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}
	                            </td>
	                            <td colspan="5" width="83.0%">
	                                <%-- 可冲销者--%>
	                                <div id="_xform_fdOffsetterIds" _xform_type="address">
	                                    <xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" mulSelect="true" orgType="ORG_TYPE_PERSON" required="true" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}" style="width:95%;" />
	                                </div>
	                            </td>
	                        </tr>
                        </c:if>
                        <c:if test="${fdIsErasable==false}">
	                        <tr style="display:none">
	                            <td class="td_normal_title" width="16.6%">
	                                ${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}
	                            </td>
	                            <td colspan="5" width="83.0%">
	                                <%-- 可冲销者--%>
	                                <div id="_xform_fdOffsetterIds" _xform_type="address">
	                                    <xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" mulSelect="true" orgType="ORG_TYPE_PERSON" required="true" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}" style="width:95%;" />
	                                </div>
	                            </td>
	                        </tr>
                        </c:if>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdReason')}
                            </td>
                            <td colspan="5" width="83.0%">
                                <%-- 借款事由--%>
                                <div id="_xform_fdReason" _xform_type="textarea">
                                    <xform:textarea property="fdReason" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                         <tr>
						    <td class="td_normal_title" width="16.6%">
						       ${lfn:message('fssc-loan:fsscLoanMain.attPayment')}
						    </td>
						    <td colspan="5" width="83.0%">
						        <%-- 附件--%>
						        <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						            <c:param name="fdKey" value="attPayment" />
						            <c:param name="formBeanName" value="fsscLoanMainForm" />
						            <c:param name="fdMulti" value="true" />
						        </c:import>
						    </td>
						</tr>
                    </table>
                    <div class="lui_paragraph_title">
                        <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('fssc-loan:py.ShouKuanZhangHuXin') }
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="8%" align="center">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}
                            </td>
                            <td class="td_normal_title" width="8%" align="center">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}
                            </td>
                            <td class="td_normal_title" width="8%" align="center">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}
                            </td>
                            <fssc:checkUseBank fdBank="BOC">
                            	<td class="td_normal_title" width="8%" align="center">
	                                ${lfn:message('fssc-loan:fsscLoanMain.fdBankAccountNo')}
	                         	 </td>
                            </fssc:checkUseBank>
                            <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
                            	<td class="td_normal_title" width="8%" align="center">
	                                ${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaName')}
	                         	 </td>
                            </fssc:checkUseBank>
                            <td class="td_normal_title" width="8%" align="center">
                                ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}
                            </td>
                          </tr>
                          <tr>
                            <td width="12%" align="center">
                                <%-- 付款方式--%>
                                <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdBasePayWayId" propertyName="fdBasePayWayName" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_pay_way_fdPayWay','fdBasePayWayId','fdBasePayWayName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()}, afterSelectPayWay);
                                    </xform:dialog>
                                    <xform:text property="fdBankId" showStatus="noShow"/>
                                </div>
                            </td>
                            <td width="12%" align="center">
                                <%-- 收款账户名--%>
                                <div id="_xform_fdAccPayeeName" _xform_type="dialog">
				                    <div class="inputselectsgl" style="width:90%;" >
					                	<input name="fdAccPayeeId" type="hidden"/>
					                	<div class="input">
					                		<input subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName') }" name="fdAccPayeeName" value="${fsscLoanMainForm.fdAccPayeeName}"  >
					                	</div>
					                	<div class="selectitem" onclick="FSSC_SelectAccount();"></div>
					                </div>
                                    <span class="txtstrong vat">*</span>
	                           </div>
                            </td>
                            <td width="12%" align="center">
                                <%-- 收款人开户行--%>
                                <div id="_xform_fdPayeeBank" _xform_type="text">
                                    <xform:text property="fdPayeeBank" showStatus="edit"  style="width:90%;" />
                                    <span class="txtstrong vat">*</span>
                                </div>
                            </td>
                            <fssc:checkUseBank fdBank="BOC">
                            <td width="10%" align="center">
                                <%-- 收款人开户行--%>
                                <div id="_xform_fdBankAccountNo" _xform_type="text">
                                    <xform:text property="fdBankAccountNo" showStatus="edit" style="width:90%;" />
                                    <span class="txtstrong vat">*</span>
                                </div>
                            </td>
                            </fssc:checkUseBank>
                           	<fssc:checkUseBank fdBank="CMB">
							 	<td  width="10%" align="center">
			                      	<div id="_xform_fdAccountAreaName" _xform_type="dialog">
			                          <xform:dialog propertyId="fdAccountAreaCode" propertyName="fdAccountAreaName" showStatus="edit"  subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaCode')}" style="width:90%;"  >
			                          dialogSelect(false,'fssc_cmb_city_code','fdAccountAreaCode','fdAccountAreaName',null,null,selectFdAccountAreaCallback);
			                          </xform:dialog>
			                          <input name="fdAccountAreaCode" type="hidden" value="${fsscLoanMainForm.fdAccountAreaCode }"/>
			                      	  <span class="txtstrong vat">*</span>
			                      	</div>
			                 	</td>
			            	</fssc:checkUseBank>
                              <fssc:checkUseBank fdBank="CBS">
                                  <td  width="10%" align="center">
                                      <div id="_xform_fdAccountAreaName" _xform_type="dialog">
                                          <xform:dialog propertyId="fdAccountAreaCode" propertyName="fdAccountAreaName" showStatus="edit"  subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaCode')}" style="width:90%;"  >
                                              dialogSelect(false,'fssc_cbs_city','fdAccountAreaCode','fdAccountAreaName',null,null,selectFdAccountAreaCbsCallback);
                                          </xform:dialog>
                                          <input name="fdAccountAreaCode" type="hidden" value="${fsscLoanMainForm.fdAccountAreaCode }"/>
                                          <span class="txtstrong vat">*</span>
                                      </div>
                                  </td>
                              </fssc:checkUseBank>
                              <fssc:checkUseBank fdBank="CMInt">
                                  <td  width="10%" align="center">
                                      <div id="_xform_fdAccountAreaName" _xform_type="dialog">
                                          <xform:dialog propertyId="fdAccountAreaCode" propertyName="fdAccountAreaName" showStatus="edit"  subject="${lfn:message('fssc-loan:fsscLoanMain.fdAccountAreaCode')}" style="width:90%;"  >
                                              dialogSelect(false,'fssc_cmbint_city_code','fdAccountAreaCode','fdAccountAreaName',null,null,selectFdAccountAreaCmbIntCallback);
                                          </xform:dialog>
                                          <input name="fdAccountAreaCode" type="hidden" value="${fsscLoanMainForm.fdAccountAreaCode }"/>
                                          <span class="txtstrong vat">*</span>
                                      </div>
                                  </td>
                              </fssc:checkUseBank>
                            <td width="12%" align="center">
                                <%-- 收款人账户--%>
                                <div id="_xform_fdPayeeAccount" _xform_type="text">
                                    <xform:text property="fdPayeeAccount" showStatus="edit"  style="width:90%;" />
                                    <span class="txtstrong vat">*</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="lui_paragraph_title fdRemind" style="display: none">
                        <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('fssc-loan:fsscLoanMain.fdRemind') }
                    </div>
                    <table class="tb_normal fdRemind" width="100%"  style="display: none">
                        <tr>
                            <td width="100%">
                                <%-- 提示信息--%>
                                <div id="_xform_fdRemind" _xform_type="dialog">
                                    <span class="txtstrong" id="fdRemindSpan">${fsscLoanMainForm.fdRemind }</span>
                                </div>
                                <input type="hidden" name="fdRemind" value="${fsscLoanMainForm.fdRemind }" />
                            </td>
                        </tr>
                    </table>
	                 <c:if test="${param.approveModel ne 'right'}">
	                 	<!-- 其他页签信息 -->
	                 	<c:if test="${fsscLoanMainForm.docUseXform == 'true' || empty fsscLoanMainForm.docUseXform}">
				         <ui:content title="${lfn:message('fssc-loan:py.BiaoDanNeiRong')}" expand="true">
					       <c:import url="/sys/xform/include/sysForm_edit.jsp"
						                charEncoding="UTF-8">
						           <c:param name="formName" value="fsscLoanMainForm" />
						           <c:param name="fdKey" value="fsscLoanMain" />
						           <c:param name="useTab" value="false" />
					           </c:import>
				           </ui:content>
			            </c:if>
	                 	<c:import url="/fssc/loan/fssc_loan_main/fsscLoanMain_edit_content_import.jsp"></c:import>
	                 	<!-- 流程信息 -->
		                 <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
		                     <c:param name="formName" value="fsscLoanMainForm" />
		                     <c:param name="fdKey" value="fsscLoanMain" />
		                     <c:param name="isExpand" value="true" />
		                 </c:import>
		                 <%--权限 --%>
		                 <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                          <c:param name="formName" value="fsscLoanMainForm" />
	                          <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
	                     </c:import>
	                 </c:if>
	                 <c:if test="${param.approveModel eq 'right'}">
		                 <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
		                 	<!-- 其他页签信息 -->
		                 	<c:if test="${fsscLoanMainForm.docUseXform == 'true' || empty fsscLoanMainForm.docUseXform}">
				                  <ui:content title="${lfn:message('fssc-loan:py.BiaoDanNeiRong')}" expand="true">
					              <c:import url="/sys/xform/include/sysForm_edit.jsp"
					                  	charEncoding="UTF-8">
						              <c:param name="formName" value="fsscLoanMainForm" />
						              <c:param name="fdKey" value="fsscLoanMain" />
						               <c:param name="useTab" value="false" />
					              </c:import>
				              </ui:content>
			                 </c:if>
	                 		<c:import url="/fssc/loan/fssc_loan_main/fsscLoanMain_edit_content_import.jsp"></c:import>
							<%--流程--%>
							<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="fsscLoanMainForm" />
								<c:param name="fdKey" value="fsscLoanMain" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
							</c:import>
							 <%--权限 --%>
		                    <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                          <c:param name="formName" value="fsscLoanMainForm" />
	                          <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
	                        </c:import>
						</ui:tabpanel>
	                 </c:if>
                </ui:tabpage>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />
                <html:hidden property="docTemplateSubjectType" value="${fsscLoanMainForm.docTemplateSubjectType }"/>
                <html:hidden property="fdExchangeRate" value="${fsscLoanMainForm.fdExchangeRate }"/>
                <html:hidden property="docTemplateId" value="${fsscLoanMainForm.docTemplateId }"/>
                <html:hidden property="docCreatorId" value="${fsscLoanMainForm.docCreatorId }"/>
                <html:hidden property="docCreatorName" value="${fsscLoanMainForm.docCreatorName }"/>
                <html:hidden property="fdIsChechedSAP" value="${fdIsChechedSAP }"/>
                <html:hidden property="fdExtendFields" value="${fdExtendFields }"/>
                <html:hidden property="fdIsProject" value="${fdIsProject }"/>
                <html:hidden property="fdIsErasable" value="${fdIsErasable }"/>
                <html:hidden property="fdIsRequiredFee" value="${fdIsRequiredFee }"/>
                <html:hidden property="fdIsTransfer" value="${fdIsTransfer }"/>
                <fssc:checkVersion version="true">
                <html:hidden property="checkVersion" value="true"/>
                </fssc:checkVersion>
                <script>
                	Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
                </script>
                <c:if test="${param.approveModel ne 'right'}">
            		</form>
            	</c:if>
        </template:replace>
        <c:if test="${param.approveModel eq 'right'}">
			<template:replace name="barRight">
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
					<%--流程--%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanMainForm" />
						<c:param name="fdKey" value="fsscLoanMain" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
					<!-- 关联机制 -->
					<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanMainForm" />
						<c:param name="approveType" value="right" />
						<c:param name="needTitle" value="true" />
					</c:import>
				</ui:tabpanel>
			</template:replace>
		</c:if>
    </c:if>
    <c:if test="${param.approveType ne 'right'}">
	<template:replace name="nav">
		<%--关联机制--%>
		<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscLoanMainForm" />
		</c:import>
	</template:replace>
</c:if>
