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
            <c:out value="${fsscCtripTrainSettleInfoForm.fdRecordId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_train_settle_info/fsscCtripTrainSettleInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_train_settle_info/fsscCtripTrainSettleInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripTrainSettleInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_train_settle_info/fsscCtripTrainSettleInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripTrainSettleInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo') }" href="/fssc/ctrip/fssc_ctrip_train_settle_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdRecordId')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算明细主键--%>
                                <div id="_xform_fdRecordId" _xform_type="text">
                                    <xform:text property="fdRecordId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdAccountId')}
                            </td>
                            <td width="16.6%">
                                <%-- 主账户ID--%>
                                <div id="_xform_fdAccountId" _xform_type="text">
                                    <xform:text property="fdAccountId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdCorpId')}
                            </td>
                            <td width="16.6%">
                                <%-- 公司编号--%>
                                <div id="_xform_fdCorpId" _xform_type="text">
                                    <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdDetailType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单明细类型--%>
                                <div id="_xform_fdDetailType" _xform_type="radio">
                                    <xform:radio property="fdDetailType" htmlElementProperties="id='fdDetailType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_order_detail_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdPassengerName')}
                            </td>
                            <td width="16.6%">
                                <%-- 乘客姓名--%>
                                <div id="_xform_fdPassengerName" _xform_type="text">
                                    <xform:text property="fdPassengerName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdPrice')}
                            </td>
                            <td width="16.6%">
                                <%-- 票面价--%>
                                <div id="_xform_fdPrice" _xform_type="text">
                                    <xform:text property="fdPrice" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdInsuranceFee')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 退票服务费--%>
                                <div id="_xform_fdServiceFee" _xform_type="text">
                                    <xform:text property="fdServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdRefund')}
                            </td>
                            <td width="16.6%">
                                <%-- 退票费--%>
                                <div id="_xform_fdRefund" _xform_type="text">
                                    <xform:text property="fdRefund" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdPaperTicketFee')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdDeliverFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 送票费--%>
                                <div id="_xform_fdDeliverFee" _xform_type="text">
                                    <xform:text property="fdDeliverFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdRebookServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 改签服务费--%>
                                <div id="_xform_fdRebookServiceFee" _xform_type="text">
                                    <xform:text property="fdRebookServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdRealAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 费用合计--%>
                                <div id="_xform_fdRealAmount" _xform_type="text">
                                    <xform:text property="fdRealAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算批次号--%>
                                <div id="_xform_fdBatchNo" _xform_type="text">
                                    <xform:text property="fdBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdSettleType')}
                            </td>
                            <td width="16.6%">
                                <%-- 月结M/现付N--%>
                                <div id="_xform_fdSettleType" _xform_type="text">
                                    <xform:text property="fdSettleType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 明细生成时间--%>
                                <div id="_xform_fdCreateTime" _xform_type="text">
                                    <xform:text property="fdCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdLastTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 明细最晚更新时间--%>
                                <div id="_xform_fdLastTime" _xform_type="text">
                                    <xform:text property="fdLastTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdOrderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdOrderType" _xform_type="select">
                                    <xform:select property="fdOrderType" htmlElementProperties="id='fdOrderType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_train_order_type" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdSubAccCheckBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算子批次号--%>
                                <div id="_xform_fdSubAccCheckBatchNo" _xform_type="text">
                                    <xform:text property="fdSubAccCheckBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdSettleCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算币种--%>
                                <div id="_xform_fdSettleCurrency" _xform_type="text">
                                    <xform:text property="fdSettleCurrency" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdGrabServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 抢票服务费--%>
                                <div id="_xform_fdGrabServiceFee" _xform_type="text">
                                    <xform:text property="fdGrabServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdPostServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 后收服务费--%>
                                <div id="_xform_fdPostServiceFee" _xform_type="text">
                                    <xform:text property="fdPostServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdRealAmountHasPost')}
                            </td>
                            <td width="16.6%">
                                <%-- 实收实付--%>
                                <div id="_xform_fdRealAmountHasPost" _xform_type="text">
                                    <xform:text property="fdRealAmountHasPost" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdIsChecked')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否已被客户核对--%>
                                <div id="_xform_fdIsChecked" _xform_type="radio">
                                    <xform:radio property="fdIsChecked" htmlElementProperties="id='fdIsChecked'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdSubDetailType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单明细子类型--%>
                                <div id="_xform_fdSubDetailType" _xform_type="select">
                                    <xform:select property="fdSubDetailType" htmlElementProperties="id='fdSubDetailType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_train_order_detail" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdInvoiceIds')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程发票ID集合--%>
                                <div id="_xform_fdInvoiceIds" _xform_type="text">
                                    <xform:text property="fdInvoiceIds" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdBatchStartDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 批次起始日期--%>
                                <div id="_xform_fdBatchStartDate" _xform_type="text">
                                    <xform:text property="fdBatchStartDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripTrainSettleInfo.fdBatchEndDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 批次结束日期--%>
                                <div id="_xform_fdBatchEndDate" _xform_type="text">
                                    <xform:text property="fdBatchEndDate" showStatus="view" style="width:95%;" />
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
