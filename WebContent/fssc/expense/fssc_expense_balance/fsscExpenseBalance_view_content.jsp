<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
                        .lui_dialog_content{
                            background-color: inherit !important;
                        }
            		
        </style>
        <script type="text/javascript">
            var formInitData = {
            	docStatus:'${fsscExpenseBalanceForm.docStatus}',
                approveModel:'${param.approveType}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("controlView.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscExpenseBalanceForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }

            function openWindowViaDynamicForm(popurl, params, target) {
                var form = document.createElement('form');
                if(form) {
                    try {
                        target = !target ? '_blank' : target;
                        form.style = "display:none;";
                        form.method = 'post';
                        form.action = popurl;
                        form.target = target;
                        if(params) {
                            for(var key in params) {
                                var
                                v = params[key];
                                var vt = typeof
                                v;
                                var hdn = document.createElement('input');
                                hdn.type = 'hidden';
                                hdn.name = key;
                                if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                    hdn.value =
                                    v +'';
                                } else {
                                    if($.isArray(
                                        v)) {
                                        hdn.value =
                                        v.join(';');
                                    } else {
                                        hdn.value = toString(
                                            v);
                                    }
                                }
                                form.appendChild(hdn);
                            }
                        }
                        document.body.appendChild(form);
                        form.submit();
                    } finally {
                        document.body.removeChild(form);
                    }
                }
            }

            function doCustomOpt(fdId, optCode) {
                if(!fdId || !optCode) {
                    return;
                }

                if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                    var param = {
                        "List_Selected_Count": 1
                    };
                    var argsObject = viewOption.customOpts[optCode];
                    if(argsObject.popup == 'true') {
                        var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                        for(var arg in argsObject) {
                            param[arg] = argsObject[arg];
                        }
                        openWindowViaDynamicForm(popurl, param, '_self');
                        return;
                    }
                    var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                    Com_OpenWindow(optAction, '_self');
                }
            }
            window.doCustomOpt = doCustomOpt;
            var viewOption = {
                contextPath: '${LUI_ContextPath}',
                basePath: '/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">

            <c:if test="${ fsscExpenseBalanceForm.docStatus=='10' || fsscExpenseBalanceForm.docStatus=='11' || fsscExpenseBalanceForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscExpenseBalance.do?method=edit&fdId=${param.fdId}','_self');" order="1" />
                </kmss:auth>
            </c:if>
            <!--重新制证-->
            <kmss:ifModuleExist path="/fssc/voucher">
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance">
                <ui:button text="${lfn:message('fssc-expense:button.refresh.voucher')}" id="refreshVoucherButton" onclick="refreshVoucher();" order="2" />
            </kmss:auth>
            </kmss:ifModuleExist>
            <c:if test="${fsscExpenseBalanceForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant=='true'}">
                <c:if test="${empty fsscExpenseBalanceForm.fdBookkeepingStatus || fsscExpenseBalanceForm.fdBookkeepingStatus == '10' || fsscExpenseBalanceForm.fdBookkeepingStatus == '11'}" >
                    <!--记账-->
                    <ui:button text="${lfn:message('fssc-expense:button.bookkeeping')}" id="bookkeepingButton" onclick="bookkeeping();" order="3" />
                </c:if>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscExpenseBalance.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <!--打印-->
            <ui:button text="${lfn:message('button.print')}" onclick="Com_OpenWindow('fsscExpenseBalance.do?method=print&fdId=${param.fdId}', '_self');" order="5" />
            <ui:button text="${lfn:message('button.close')}" order="6" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" href="/fssc/expense/fssc_expense_balance/" target="_self" />
            <ui:menu-item text="${docTemplateName }"  />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
		 <!-- 流程状态标识 -->
		<c:import url="/eop/basedata/resource/jsp/fssc_banner.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscExpenseBalanceForm" />
			<c:param name="approveType" value="${param.approveType}" />
		</c:import>
        <ui:tabpage expand="false" var-navwidth="90%" id="reviewTabPage" collapsed="true" >
            <c:if test="${param.approveType eq 'right'}">
            	<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				</c:if>
                <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${fsscExpenseBalanceForm.docSubject}
                    </div>
                    <%--条形码--%>
		            <div id="barcodeTarget" style="float:right;margin-right:10px;margin-top: -20px;" ></div>
                </div>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}
                        </td>
                        <td colspan="5" width="83.0%">
                            <%-- 主题--%>
                            <div id="_xform_docSubject" _xform_type="text">
                                <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}
                        </td>
                        <td width="16.6%">
                            <%-- 经办人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscExpenseBalanceForm.docCreatorId}" personName="${fsscExpenseBalanceForm.docCreatorName}" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreatorDept')}
                        </td>
                        <td width="16.6%">
                            <%-- 经办人部门--%>
                            <div id="_xform_docCreatorDeptId" _xform_type="address">
                                <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}
                        </td>
                        <td width="16.6%">
                            <%-- 创建日期--%>
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}
                        </td>
                        <td width="16.6%">
                            <%-- 费用公司--%>
                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}
                        </td>
                        <td width="16.6%">
                            <%-- 成本中心--%>
                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdAttNum')}
                        </td>
                        <td width="16.6%">
                            <%-- 附件(张)--%>
                            <div id="_xform_fdAttNum" _xform_type="text">
                                <xform:text property="fdAttNum" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}
                        </td>
                        <td width="16.6%">
                            <%-- 凭证类型--%>
                            <div id="_xform_fdVoucherTypeId" _xform_type="radio">
                                <xform:dialog propertyId="fdVoucherTypeId" propertyName="fdVoucherTypeName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_voucher_type_selectVoucherType','fdVoucherTypeId','fdVoucherTypeName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}
                        </td>
                        <td width="16.6%">
                            <%-- 币种--%>
                            <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                                </xform:dialog>
                            </div>
                        </td>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}
                        </td>
                        <td width="16.6%">
                            <%-- 月份--%>
                            <div id="_xform_fdMonth" _xform_type="text">
                                <xform:text property="fdMonth" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdSubject')}
                        </td>
                        <td colspan="5" width="83.0%">
                            <%-- 凭证抬头文本--%>
                            <div id="_xform_fdSubject" _xform_type="text">
                                <xform:text property="fdSubject" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
					    <td class="td_normal_title" width="16.6%">
					            ${lfn:message('fssc-expense:fsscExpenseBalance.attachment')}
					    </td>
					     <td colspan="5" width="83.0%">
					            <%-- 附件--%>
					            <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
					                <c:param name="fdKey" value="attBalance" />
					                <c:param name="formBeanName" value="fsscExpenseBalanceForm" />
					                <c:param name="fdMulti" value="true" />
					            </c:import>
					     </td>
					</tr>
                    <tr>
                        <td colspan="6" width="100%">
                            <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
                                <tr align="center" class="tr_normal_title">
                                    <td style="width:40px;">
                                        ${lfn:message('page.serial')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdPerson')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}
                                    </td>
                                    <td>
                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}
                                    </td>
                                </tr>
                                <c:forEach items="${fsscExpenseBalanceForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
                                    <tr KMSS_IsContentRow="1">
                                        <td align="center">
                                            ${vstatus.index+1}
                                        </td>
                                        <td align="center">
                                            <%-- 借/贷--%>
                                            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdType" _xform_type="select">
                                                <xform:select property="fdDetailList_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdType'" showStatus="view">
                                                    <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
                                                </xform:select>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 费用类型--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
                                                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" propertyName="fdDetailList_Form[${vstatus.index}].fdExpenseItemName" showStatus="view" style="width:95%;">
                                                    dialogSelect(false,'eop_basedata_expense_item_fdParent','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 会计科目--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
                                                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdAccountId" propertyName="fdDetailList_Form[${vstatus.index}].fdAccountName" showStatus="view" style="width:95%;">
                                                    dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdDetailList_Form[*].fdAccountId','fdDetailList_Form[*].fdAccountName');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 成本中心--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                                                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" showStatus="view" style="width:95%;">
                                                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 个人--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPersonId" _xform_type="address">
                                                <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdPersonId" propertyName="fdDetailList_Form[${vstatus.index}].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 个人--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdDeptId" _xform_type="address">
                                                <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" style="width:95%;" />
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 现金流量项目--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCashFlowId" _xform_type="dialog">
                                                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCashFlowId" propertyName="fdDetailList_Form[${vstatus.index}].fdCashFlowName" showStatus="view" style="width:95%;">
                                                    dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetailList_Form[*].fdCashFlowId','fdDetailList_Form[*].fdCashFlowName');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 项目--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdProjectId" _xform_type="dialog">
                                                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index}].fdProjectName" showStatus="view" style="width:95%;">
                                                    dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName');
                                                </xform:dialog>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 金额--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                                                <kmss:showNumber value="${fdDetailList_FormItem.fdMoney }" pattern="0.00"/>
                                            </div>
                                        </td>
                                        <td align="center">
                                            <%-- 备注--%>
                                            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRemark" _xform_type="text">
                                                <xform:text property="fdDetailList_Form[${vstatus.index}].fdRemark" showStatus="view" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </table>
                <c:if test="${not empty fsscExpenseBalanceForm.fdVoucherStatus }">
            <kmss:ifModuleExist path="/fssc/voucher/">
            	<c:set var="voucherView"  value="false"></c:set>
            	<!-- 凭证查看权限 -->
	            <kmss:authShow roles="ROLE_FSSCVOUCHER_VIEW">
	            	<c:set var="voucherView"  value="true"></c:set>
	            </kmss:authShow>
	            <!-- 财务人员 -->
	            <fssc:auth authType="staff" fdCompanyId="${fsscExpenseBalanceForm.fdCompanyId}">
	            	<c:set var="voucherView"  value="true"></c:set>
	            </fssc:auth>
	            <!-- 重新制证权限 -->
	            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseBalance">
	            	<c:set var="voucherView"  value="true"></c:set>
	            </kmss:auth>
	            <c:if test="${voucherView=='true'}">
                <ui:content title="${lfn:message('fssc-voucher:fsscVoucherMain.title.message')}" expand="true">
                    <c:import url="/fssc/voucher/fssc_voucher_main/fsscVoucherMain_modelView.jsp" charEncoding="UTF-8">
                        <c:param name="fdModelId" value="${fsscExpenseBalanceForm.fdId}" />
                        <c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
                        <c:param name="fdModelNumber" value="${fsscExpenseBalanceForm.docNumber}" />
                        <c:param name="fdBookkeepingStatus" value="${fsscExpenseBalanceForm.fdBookkeepingStatus}" />
                        <c:param name="fdIsVoucherVariant" value="${fsscExpenseBalanceForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant}" />
                    </c:import>
                </ui:content>
                </c:if>
            </kmss:ifModuleExist>
            </c:if>
            <c:if test="${param.approveType ne 'right'}">
            <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseBalanceForm" />
                <c:param name="fdKey" value="fsscExpenseBalance" />
                <c:param name="isExpand" value="true" />
            </c:import>
            <%--传阅机制 --%>
            <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseBalanceForm" />
            </c:import>
            <%--权限 --%>
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	              <c:param name="formName" value="fsscExpenseBalanceForm" />
	              <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
	        </c:import>
			</c:if>
			<c:if test="${param.approveType eq 'right'}">
				<c:if test="${fsscExpenseBalanceForm.docStatus ne '00' and  fsscExpenseBalanceForm.docStatus ne '30'}">
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseBalanceForm" />
                <c:param name="fdKey" value="fsscExpenseBalance" />
                <c:param name="isExpand" value="true" />
            	</c:import>
				</c:if>
				<c:if test="${fsscExpenseBalanceForm.docStatus eq '00' or  fsscExpenseBalanceForm.docStatus eq '30'}">
                <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscExpenseBalanceForm" />
                        <c:param name="fdKey" value="fsscExpenseBalance" />
                        <c:param name="isExpand" value="true" />
                        <c:param name="approveType" value="right" />
                        <c:param name="needInitLbpm" value="true" />
                    </c:import>
                </ui:tabpanel>
                </c:if>
	            <%--传阅机制 --%>
	            <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscExpenseBalanceForm" />
	            </c:import>
	             <%--权限 --%>
		    	<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscExpenseBalanceForm" />
	                <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
	            </c:import>
            </c:if>
        </ui:tabpage>
        
       	<%-- 条形码公共页面 --%>
        <c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
        	<c:param name="docNumber">${fsscExpenseBalanceForm.docNumber }</c:param>
        </c:import>
    </template:replace>
<c:if test="${param.approveType eq 'right' }">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:if test="${fsscExpenseBalanceForm.docStatus ne '00' and  fsscExpenseBalanceForm.docStatus ne '30'}">
			<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseBalanceForm" />
				<c:param name="fdKey" value="fsscExpenseBalance" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
				<c:param name="needInitLbpm" value="true" />
			</c:import>
			</c:if>
			<c:import url="/fssc/expense/fssc_expense_balance/fsscExpenseBalance_baseInfo_right.jsp"></c:import>
			<!-- 关联配置 -->
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseBalanceForm" />
				<c:param name="approveType" value="right" />
                <c:param name="needTitle" value="true" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>
<c:if test="${param.approveType ne 'right'}">
	<template:replace name="nav">
		<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscExpenseBalanceForm" />
		</c:import>
	</template:replace>
</c:if>
