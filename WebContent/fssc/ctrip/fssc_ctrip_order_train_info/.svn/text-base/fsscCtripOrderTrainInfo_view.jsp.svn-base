<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.ctrip.util.FsscCtripUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
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

                };
                var messageInfo = {

                    'fdInsuranceInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainInsurance"))}',
                    'fdOrderTicketInvoice': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainTinvoice"))}',
                    'fdInvoiceImageList': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainInvoiceImage"))}',
                    'fdPassengerInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainPassenger"))}',
                    'fdPaymentInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainPayment"))}',
                    'fdTicketInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainTicket"))}',
                    'fdControlList': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderTrainControl"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCtripOrderTrainInfoForm.fdOrderId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripOrderTrainInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_order_train_info/fsscCtripOrderTrainInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_train_info/fsscCtripOrderTrainInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripOrderTrainInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_train_info/fsscCtripOrderTrainInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripOrderTrainInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripOrderTrainInfo') }" href="/fssc/ctrip/fssc_ctrip_order_train_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripOrderTrainInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程单号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdEmployeeId')}
                            </td>
                            <td width="16.6%">
                                <%-- 员工编号--%>
                                <div id="_xform_fdEmployeeId" _xform_type="text">
                                    <xform:text property="fdEmployeeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单总金额--%>
                                <div id="_xform_fdOrderAmount" _xform_type="text">
                                    <xform:text property="fdOrderAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdDealAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 最终成交金额--%>
                                <div id="_xform_fdDealAmount" _xform_type="text">
                                    <xform:text property="fdDealAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdTotalQuantity')}
                            </td>
                            <td width="16.6%">
                                <%-- 订票总张数--%>
                                <div id="_xform_fdTotalQuantity" _xform_type="text">
                                    <xform:text property="fdTotalQuantity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdRemark')}
                            </td>
                            <td width="16.6%">
                                <%-- 备注--%>
                                <div id="_xform_fdRemark" _xform_type="text">
                                    <xform:text property="fdRemark" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdNeedInvoice')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否需要发票--%>
                                <div id="_xform_fdNeedInvoice" _xform_type="radio">
                                    <xform:radio property="fdNeedInvoice" htmlElementProperties="id='fdNeedInvoice'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdIsIncludeInsuranceInvoice')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否需要保险发票--%>
                                <div id="_xform_fdIsIncludeInsuranceInvoice" _xform_type="radio">
                                    <xform:radio property="fdIsIncludeInsuranceInvoice" htmlElementProperties="id='fdIsIncludeInsuranceInvoice'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态--%>
                                <div id="_xform_fdOrderStatus" _xform_type="text">
                                    <xform:text property="fdOrderStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderStatusName')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态名称--%>
                                <div id="_xform_fdOrderStatusName" _xform_type="text">
                                    <xform:text property="fdOrderStatusName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdOrderType" _xform_type="radio">
                                    <xform:radio property="fdOrderType" htmlElementProperties="id='fdOrderType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_order_train_ticket_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdOrderTypeDesc')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型描述--%>
                                <div id="_xform_fdOrderTypeDesc" _xform_type="text">
                                    <xform:text property="fdOrderTypeDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 服务费--%>
                                <div id="_xform_fdServiceFee" _xform_type="text">
                                    <xform:text property="fdServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdContactEmail')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系邮箱--%>
                                <div id="_xform_fdContactEmail" _xform_type="text">
                                    <xform:text property="fdContactEmail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdContactName')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人姓名--%>
                                <div id="_xform_fdContactName" _xform_type="text">
                                    <xform:text property="fdContactName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdContactTel')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系电话--%>
                                <div id="_xform_fdContactTel" _xform_type="text">
                                    <xform:text property="fdContactTel" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdUserName')}
                            </td>
                            <td width="16.6%">
                                <%-- 用户姓名--%>
                                <div id="_xform_fdUserName" _xform_type="text">
                                    <xform:text property="fdUserName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdRefundTicketStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 退票状态--%>
                                <div id="_xform_fdRefundTicketStatus" _xform_type="radio">
                                    <xform:radio property="fdRefundTicketStatus" htmlElementProperties="id='fdRefundTicketStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_refund_ticket_status" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdPaymentType')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付方式--%>
                                <div id="_xform_fdPaymentType" _xform_type="radio">
                                    <xform:radio property="fdPaymentType" htmlElementProperties="id='fdPaymentType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_train_payway" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdDataChangeCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 预定时间--%>
                                <div id="_xform_fdDataChangeCreateTime" _xform_type="datetime">
                                    <xform:datetime property="fdDataChangeCreateTime" showStatus="view" dateTimeType="date" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdDeliverFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送费--%>
                                <div id="_xform_fdDeliverFee" _xform_type="text">
                                    <xform:text property="fdDeliverFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdPaperTicketFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 纸质票出票费--%>
                                <div id="_xform_fdPaperTicketFee" _xform_type="text">
                                    <xform:text property="fdPaperTicketFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdAccountName')}
                            </td>
                            <td width="16.6%">
                                <%-- 12306账号--%>
                                <div id="_xform_fdAccountName" _xform_type="text">
                                    <xform:text property="fdAccountName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdConfirmType')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权方式--%>
                                <div id="_xform_fdConfirmType" _xform_type="text">
                                    <xform:text property="fdConfirmType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdConfirmPerson')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权人--%>
                                <div id="_xform_fdConfirmPerson" _xform_type="text">
                                    <xform:text property="fdConfirmPerson" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdConfirmPersonCc')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送一次授权人--%>
                                <div id="_xform_fdConfirmPersonCc" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdAuditResult')}
                            </td>
                            <td width="16.6%">
                                <%-- 授权结果--%>
                                <div id="_xform_fdAuditResult" _xform_type="text">
                                    <xform:text property="fdAuditResult" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdAuditResultDesc')}
                            </td>
                            <td width="16.6%">
                                <%-- 授权结果描述--%>
                                <div id="_xform_fdAuditResultDesc" _xform_type="text">
                                    <xform:text property="fdAuditResultDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdChangeServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签服务费--%>
                                <div id="_xform_fdChangeServiceFee" _xform_type="text">
                                    <xform:text property="fdChangeServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdChangeTicketStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签状态--%>
                                <div id="_xform_fdChangeTicketStatus" _xform_type="radio">
                                    <xform:radio property="fdChangeTicketStatus" htmlElementProperties="id='fdChangeTicketStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_change_ticket_status" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdChangeTicketStatusName')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签状态名称--%>
                                <div id="_xform_fdChangeTicketStatusName" _xform_type="text">
                                    <xform:text property="fdChangeTicketStatusName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdServerFrom')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单来源--%>
                                <div id="_xform_fdServerFrom" _xform_type="text">
                                    <xform:text property="fdServerFrom" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdNeedBigInvoice')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否需要大发票--%>
                                <div id="_xform_fdNeedBigInvoice" _xform_type="radio">
                                    <xform:radio property="fdNeedBigInvoice" htmlElementProperties="id='fdNeedBigInvoice'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdCountryCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 国家二字码--%>
                                <div id="_xform_fdCountryCode" _xform_type="text">
                                    <xform:text property="fdCountryCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdPlatformOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 平台订单号--%>
                                <div id="_xform_fdPlatformOrderId" _xform_type="text">
                                    <xform:text property="fdPlatformOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送地址--%>
                                <div id="_xform_fdShipAddress" _xform_type="text">
                                    <xform:text property="fdShipAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipAddressDetail')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送详细地址--%>
                                <div id="_xform_fdShipAddressDetail" _xform_type="text">
                                    <xform:text property="fdShipAddressDetail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipZipCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送地址邮编--%>
                                <div id="_xform_fdShipZipCode" _xform_type="text">
                                    <xform:text property="fdShipZipCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipReceiverMobile')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送接收人手机--%>
                                <div id="_xform_fdShipReceiverMobile" _xform_type="text">
                                    <xform:text property="fdShipReceiverMobile" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipReceiverName')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送接收人姓名--%>
                                <div id="_xform_fdShipReceiverName" _xform_type="text">
                                    <xform:text property="fdShipReceiverName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipReceiverTel')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送接收人电话--%>
                                <div id="_xform_fdShipReceiverTel" _xform_type="text">
                                    <xform:text property="fdShipReceiverTel" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdAreaId')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送地区代码--%>
                                <div id="_xform_fdAreaId" _xform_type="text">
                                    <xform:text property="fdAreaId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdProvince')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送省--%>
                                <div id="_xform_fdProvince" _xform_type="text">
                                    <xform:text property="fdProvince" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdCity')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送市--%>
                                <div id="_xform_fdCity" _xform_type="text">
                                    <xform:text property="fdCity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdArea')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送区/县--%>
                                <div id="_xform_fdArea" _xform_type="text">
                                    <xform:text property="fdArea" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送地址--%>
                                <div id="_xform_fdAddress" _xform_type="text">
                                    <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipmentCompany')}
                            </td>
                            <td width="16.6%">
                                <%-- 物流公司--%>
                                <div id="_xform_fdShipmentCompany" _xform_type="text">
                                    <xform:text property="fdShipmentCompany" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdShipmentNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 物流单号--%>
                                <div id="_xform_fdShipmentNo" _xform_type="text">
                                    <xform:text property="fdShipmentNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdDeliverStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 快递状态--%>
                                <div id="_xform_fdDeliverStatus" _xform_type="text">
                                    <xform:text property="fdDeliverStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderTrainInfo.fdDeliverFailMessage')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送失败原因--%>
                                <div id="_xform_fdDeliverFailMessage" _xform_type="text">
                                    <xform:text property="fdDeliverFailMessage" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td colspan="4">
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>
