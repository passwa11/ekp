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

                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCtripAirSettleInfoForm.fdRecordId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripAirSettleInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripAirSettleInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo') }" href="/fssc/ctrip/fssc_ctrip_air_settle_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdRecordId')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算明细主键--%>
                                <div id="_xform_fdRecordId" _xform_type="text">
                                    <xform:text property="fdRecordId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdSequence')}
                            </td>
                            <td width="16.6%">
                                <%-- 航程编号--%>
                                <div id="_xform_fdSequence" _xform_type="text">
                                    <xform:text property="fdSequence" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算生成时间--%>
                                <div id="_xform_fdCreateTime" _xform_type="text">
                                    <xform:text property="fdCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdAccountId')}
                            </td>
                            <td width="16.6%">
                                <%-- 主账户ID--%>
                                <div id="_xform_fdAccountId" _xform_type="text">
                                    <xform:text property="fdAccountId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdCorpId')}
                            </td>
                            <td width="16.6%">
                                <%-- 公司编号--%>
                                <div id="_xform_fdCorpId" _xform_type="text">
                                    <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdAccCheckBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算批次号--%>
                                <div id="_xform_fdAccCheckBatchNo" _xform_type="text">
                                    <xform:text property="fdAccCheckBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdAccBalanceBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 付款批次号--%>
                                <div id="_xform_fdAccBalanceBatchNo" _xform_type="text">
                                    <xform:text property="fdAccBalanceBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdRemark')}
                            </td>
                            <td width="16.6%">
                                <%-- 备注--%>
                                <div id="_xform_fdRemark" _xform_type="text">
                                    <xform:text property="fdRemark" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdUndeterminedAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 未确定金额--%>
                                <div id="_xform_fdUndeterminedAmount" _xform_type="text">
                                    <xform:text property="fdUndeterminedAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdPrice')}
                            </td>
                            <td width="16.6%">
                                <%-- 成交净价--%>
                                <div id="_xform_fdPrice" _xform_type="text">
                                    <xform:text property="fdPrice" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdTax')}
                            </td>
                            <td width="16.6%">
                                <%-- 民航基金/税--%>
                                <div id="_xform_fdTax" _xform_type="text">
                                    <xform:text property="fdTax" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdOilFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 燃油--%>
                                <div id="_xform_fdOilFee" _xform_type="text">
                                    <xform:text property="fdOilFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdSendTicketFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 送票费--%>
                                <div id="_xform_fdSendTicketFee" _xform_type="text">
                                    <xform:text property="fdSendTicketFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdInsuranceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 保险费--%>
                                <div id="_xform_fdInsuranceFee" _xform_type="text">
                                    <xform:text property="fdInsuranceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 预订服务费--%>
                                <div id="_xform_fdServiceFee" _xform_type="text">
                                    <xform:text property="fdServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdCoupon')}
                            </td>
                            <td width="16.6%">
                                <%-- 优惠劵--%>
                                <div id="_xform_fdCoupon" _xform_type="text">
                                    <xform:text property="fdCoupon" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdRefundServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 退票服务费--%>
                                <div id="_xform_fdRefundServiceFee" _xform_type="text">
                                    <xform:text property="fdRefundServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdRefund')}
                            </td>
                            <td width="16.6%">
                                <%-- 退票费--%>
                                <div id="_xform_fdRefund" _xform_type="text">
                                    <xform:text property="fdRefund" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdRebookQueryFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签费--%>
                                <div id="_xform_fdRebookQueryFee" _xform_type="text">
                                    <xform:text property="fdRebookQueryFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdReBookingServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签服务费--%>
                                <div id="_xform_fdReBookingServiceFee" _xform_type="text">
                                    <xform:text property="fdReBookingServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdItineraryFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 配送服务费--%>
                                <div id="_xform_fdItineraryFee" _xform_type="text">
                                    <xform:text property="fdItineraryFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 应收金额--%>
                                <div id="_xform_fdAmount" _xform_type="text">
                                    <xform:text property="fdAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdPostServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 后收服务费--%>
                                <div id="_xform_fdPostServiceFee" _xform_type="text">
                                    <xform:text property="fdPostServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdPostServiceFeeDesc')}
                            </td>
                            <td colspan="5" width="83.0%">
                                <%-- 后收服务费类型描述--%>
                                <div id="_xform_fdPostServiceFeeDesc" _xform_type="text">
                                    <xform:text property="fdPostServiceFeeDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdBaseServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 基础服务费--%>
                                <div id="_xform_fdBaseServiceFee" _xform_type="text">
                                    <xform:text property="fdBaseServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdUnWorkTimeServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 非工作时间服务费--%>
                                <div id="_xform_fdUnWorkTimeServiceFee" _xform_type="text">
                                    <xform:text property="fdUnWorkTimeServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdVipServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- VIP服务费--%>
                                <div id="_xform_fdVipServiceFee" _xform_type="text">
                                    <xform:text property="fdVipServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdBindServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 特价机票服务费--%>
                                <div id="_xform_fdBindServiceFee" _xform_type="text">
                                    <xform:text property="fdBindServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdOrderDetailType')}
                            </td>
                            <td width="16.6%">
                                <%-- 退改签类型--%>
                                <div id="_xform_fdOrderDetailType" _xform_type="text">
                                    <xform:text property="fdOrderDetailType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdOrderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdOrderType" _xform_type="radio">
                                    <xform:radio property="fdOrderType" htmlElementProperties="id='fdOrderType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_flight_order_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdSubAccCheckBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算子批次号--%>
                                <div id="_xform_fdSubAccCheckBatchNo" _xform_type="text">
                                    <xform:text property="fdSubAccCheckBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdSettlementCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算币种--%>
                                <div id="_xform_fdSettlementCurrency" _xform_type="text">
                                    <xform:text property="fdSettlementCurrency" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdDateChangeFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 更改费--%>
                                <div id="_xform_fdDateChangeFee" _xform_type="text">
                                    <xform:text property="fdDateChangeFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdPriceDifferential')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签差价--%>
                                <div id="_xform_fdPriceDifferential" _xform_type="text">
                                    <xform:text property="fdPriceDifferential" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdIsChecked')}
                            </td>
                            <td width="16.6%">
                                <%-- 确认状态--%>
                                <div id="_xform_fdIsChecked" _xform_type="radio">
                                    <xform:radio property="fdIsChecked" htmlElementProperties="id='fdIsChecked'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdDeductibleTax')}
                            </td>
                            <td width="16.6%">
                                <%-- 可抵扣税额--%>
                                <div id="_xform_fdDeductibleTax" _xform_type="text">
                                    <xform:text property="fdDeductibleTax" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdNonDeductibleTax')}
                            </td>
                            <td width="16.6%">
                                <%-- 不可抵扣税额--%>
                                <div id="_xform_fdNonDeductibleTax" _xform_type="text">
                                    <xform:text property="fdNonDeductibleTax" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdSubsidy')}
                            </td>
                            <td width="16.6%">
                                <%-- 飞享金--%>
                                <div id="_xform_fdSubsidy" _xform_type="text">
                                    <xform:text property="fdSubsidy" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdInvoiceIds')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程发票集合--%>
                                <div id="_xform_fdInvoiceIds" _xform_type="text">
                                    <xform:text property="fdInvoiceIds" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdBatchStartDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 批次开始时间--%>
                                <div id="_xform_fdBatchStartDate" _xform_type="text">
                                    <xform:text property="fdBatchStartDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripAirSettleInfo.fdBatchEndDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 批次结束时间--%>
                                <div id="_xform_fdBatchEndDate" _xform_type="text">
                                    <xform:text property="fdBatchEndDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>
