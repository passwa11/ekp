<%@ page import="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" %>
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
                        .lui_dialog_content{
                            background-color: inherit !important;
                        }
            		
        </style>
        <script type="text/javascript">
            var formInitData = {
            		docStatus:'${fsscLoanRepaymentForm.docStatus}',
            		approveModel:'${param.approveModel}',
            };
            var messageInfo = {

            };
            //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("controlView.js", "${LUI_ContextPath}/fssc/common/resource/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscLoanRepaymentForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
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
                basePath: '/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
		<!-- 右侧流程按钮 -->
        <c:if test="${param.approveModel eq 'right'}">
            <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' || fsscLoanRepaymentForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscLoanRepayment.do?method=edit&fdId=${param.fdId}','_self');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" order="1" />
                </kmss:auth>
            </c:if>
            <!--重新制证-->
            <kmss:ifModuleExist path="/fssc/voucher">
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment">
                <ui:button text="${lfn:message('fssc-loan:button.refresh.voucher')}" id="refreshVoucherButton" onclick="refreshVoucher();" styleClass="lui_widget_btn_primary" isForcedAddClass="true" order="2" />
            </kmss:auth>
            <c:if test="${fsscLoanRepaymentForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant=='true'}">
                <c:if test="${empty fsscLoanRepaymentForm.fdBookkeepingStatus || fsscLoanRepaymentForm.fdBookkeepingStatus == '10' || fsscLoanRepaymentForm.fdBookkeepingStatus == '11'}" >
                    <!--记账-->
                    <ui:button text="${lfn:message('fssc-loan:button.bookkeeping')}" id="bookkeepingButton" onclick="bookkeeping();" styleClass="lui_widget_btn_primary" isForcedAddClass="true" order="3" />
                </c:if>
            </c:if>
            </kmss:ifModuleExist>
            <c:if test="${ fsscLoanRepaymentForm.docStatus=='30'}">
                <!-- 归档 -->
                <c:import url="/sys/archives/include/sysArchivesFileButton.jsp" charEncoding="UTF-8">
                    <c:param name="fdId" value="${param.fdId}" />
                    <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                    <c:param name="serviceName" value="fsscLoanRepaymentService" />
                    <c:param name="userSetting" value="true" />
                    <c:param name="cateName" value="docTemplate" />
                    <c:param name="moduleUrl" value="fssc/loan" />
                </c:import>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscLoanRepayment.do?method=delete&fdId=${param.fdId}');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" order="4" />
            </kmss:auth>
           </c:if>
        <!-- 旧版按钮 -->
        <c:if test="${param.approveModel ne 'right'}">
            <c:if test="${ fsscLoanRepaymentForm.docStatus=='10' || fsscLoanRepaymentForm.docStatus=='11' || fsscLoanRepaymentForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscLoanRepayment.do?method=edit&fdId=${param.fdId}','_self');" order="1" />
                </kmss:auth>
            </c:if>
            <!--重新制证-->
            <kmss:ifModuleExist path="/fssc/voucher">
            <kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment">
                <ui:button text="${lfn:message('fssc-loan:button.refresh.voucher')}" id="refreshVoucherButton" onclick="refreshVoucher();" order="2" />
            </kmss:auth>
            <c:if test="${fsscLoanRepaymentForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant=='true'}">
                <c:if test="${empty fsscLoanRepaymentForm.fdBookkeepingStatus || fsscLoanRepaymentForm.fdBookkeepingStatus == '10' || fsscLoanRepaymentForm.fdBookkeepingStatus == '11'}" >
                    <!--记账-->
                    <ui:button text="${lfn:message('fssc-loan:button.bookkeeping')}" id="bookkeepingButton" onclick="bookkeeping();" order="2" />
                </c:if>
            </c:if>
            </kmss:ifModuleExist>
            <c:if test="${ fsscLoanRepaymentForm.docStatus=='30'}">
                <!-- 归档 -->
                <c:import url="/sys/archives/include/sysArchivesFileButton.jsp" charEncoding="UTF-8">
                    <c:param name="fdId" value="${param.fdId}" />
                    <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                    <c:param name="serviceName" value="fsscLoanRepaymentService" />
                    <c:param name="userSetting" value="true" />
                    <c:param name="cateName" value="docTemplate" />
                    <c:param name="moduleUrl" value="fssc/loan" />
                </c:import>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscLoanRepayment.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
           </c:if>
           <ui:button text="${lfn:message('button.print')}" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=print&fdId=${param.fdId}');" order="4"  />
           <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" href="/fssc/loan/fssc_loan_repayment/" target="_self" />
             <ui:menu-item text="${docTemplateName }"  />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <!-- 流程状态标识 -->
		<c:import url="/eop/basedata/resource/jsp/fssc_banner.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscLoanRepaymentForm" />
			<c:param name="approveType" value="${param.approveModel}" />
		</c:import>
        <ui:tabpage expand="false" var-navwidth="90%">
             <div class='lui_form_title_frame'>
                <div class='lui_form_subject'>
                    ${fsscLoanRepaymentForm.docSubject}
                </div>
                <%--条形码--%>
          		<div id="barcodeTarget" style="float:right;margin-right:10px;margin-top: -20px;" ></div>
            </div>
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}
                    </td>
                    <td colspan="5" width="83.0%">
                            <%-- 标题  style="width:45%;text-align:center;font-size:25px;" --%>
                        <xform:text property="docSubject" style="width:95%;" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款人--%>
                        <div id="_xform_fdRepaymentPersonId" _xform_type="address">
                            <xform:address propertyId="fdRepaymentPersonId" propertyName="fdRepaymentPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款人部门--%>
                        <div id="_xform_fdRepaymentDeptId" _xform_type="address">
                            <xform:address propertyId="fdRepaymentDeptId" propertyName="fdRepaymentDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}
                    </td>
                    <td width="16.6%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}
                    </td>
                    <td width="16.6%">
                        <%-- 借款单--%>
                        <div id="_xform_fdLoanMainId" _xform_type="dialog">
                            <a target="_blank" href="<c:url value="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=view&fdId="/>${fsscLoanRepaymentForm.fdLoanMainId}">${fsscLoanRepaymentForm.fdLoanMainName}</a>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 未冲销金额--%>
                        <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="##0.00"></kmss:showNumber>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款金额--%>
                        <div id="_xform_fdRepaymentMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="##0.00"></kmss:showNumber>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}
                    </td>
                    <td width="16.6%">
                        <%-- 付款方式--%>
                        <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                            <xform:dialog propertyId="fdBasePayWayId" propertyName="fdBasePayWayName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdBasePayWayId','fdBasePayWayName');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}
                    </td>
                    <td colspan="3" width="49.8%">
                        <%-- 收款账号--%>
                        <div id="_xform_fdPaymentAccount" _xform_type="text">
                            <xform:text property="fdPaymentAccount" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}
                    </td>
                    <td colspan="5" width="83.0%">
                            <%-- 还款说明--%>
                        <div id="_xform_fdReason" _xform_type="textarea">
                            <xform:textarea property="fdReason" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:module.attachment')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                            <c:param name="fdKey" value="attachment"/>
                            <c:param name="formBeanName" value="fsscLoanRepaymentForm"/>
                        </c:import>
                    </td>
                </tr>
            </table>
            <c:if test="${param.approveModel ne 'right'}">
            <!-- 其他页签信息 -->
	        <c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
            <c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_view_content_import.jsp" charEncoding="UTF-8"></c:import>
            <c:import url="/fssc/loan/resource/jsp/fsscLoanLbpmProcess_view.jsp" charEncoding="UTF-8">
                <c:param name="docStatus" value="${fsscLoanRepaymentForm.docStatus}" />
                <c:param name="formName" value="fsscLoanRepaymentForm" />
                <c:param name="fdKey" value="fsscLoanRepayment" />
                <c:param name="isExpand" value="true" />
            </c:import>
            <%--关联机制 --%>
             <template:replace name="nav">
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscLoanRepaymentForm" />
			</c:import>
			</template:replace>
             <%--传阅 --%>
            <c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                   <c:param name="formName" value="fsscLoanRepaymentForm" />
            </c:import>
               <%--权限 --%>
			            <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscLoanRepaymentForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
	                    </c:import>
            </c:if>
            <c:if test="${param.approveModel eq 'right'}">
            <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
            	<!-- 其他页签信息 -->
	        	<c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_edit_content_import.jsp" charEncoding="UTF-8"></c:import>
            	<c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_view_content_import.jsp" charEncoding="UTF-8"></c:import>
            	<c:choose>
            	<c:when test="${fsscLoanRepaymentForm.docStatus>='30' || fsscLoanRepaymentForm.docStatus=='00'}">
		            <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanRepaymentForm" />
						<c:param name="fdKey" value="fsscLoanRepayment" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanRepaymentForm" />
						<c:param name="fdKey" value="fsscLoanRepayment" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
					</c:import>
				</c:otherwise>
				</c:choose>
				 <%--传阅 --%>
          		<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp" charEncoding="UTF-8">
                 <c:param name="formName" value="fsscLoanRepaymentForm" />
          		</c:import>
          		   <%--权限 --%>
			            <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscLoanRepaymentForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
	                    </c:import>
			</ui:tabpanel>
			</c:if>
        </ui:tabpage>
        <%-- 条形码公共页面 --%>
        <c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
        	<c:param name="docNumber">${fsscLoanRepaymentForm.docNumber }</c:param>
        </c:import>
    </template:replace>
    <c:if test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<!-- 发布后废弃右侧不显示流程信息 -->
			<c:if test="${fsscLoanRepaymentForm.docStatus<'30' && fsscLoanRepaymentForm.docStatus!='00'}">
				<%--流程--%>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanRepaymentForm" />
					<c:param name="fdKey" value="fsscLoanRepayment" />
					<c:param name="isExpand" value="true" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
				<!-- 审批记录 -->
				<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanRepaymentForm" />
					<c:param name="fdModelId" value="${fsscLoanRepaymentForm.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
				</c:import>
				</c:if>
				<!-- 基本信息-->
				<c:import url="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment_viewBaseInfoContent.jsp" charEncoding="UTF-8">
				</c:import>
				<!-- 关联 -->
				<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanRepaymentForm" />
					<c:param name="approveType" value="right" />
                    <c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>	
