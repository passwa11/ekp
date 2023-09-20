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
            <c:out value="${fsscCtripHotelSettleInfoForm.fdRecordId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_hotel_settle_info/fsscCtripHotelSettleInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_hotel_settle_info/fsscCtripHotelSettleInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripHotelSettleInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_hotel_settle_info/fsscCtripHotelSettleInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripHotelSettleInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo') }" href="/fssc/ctrip/fssc_ctrip_hotel_settle_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdRecordId')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算明细主键--%>
                                <div id="_xform_fdRecordId" _xform_type="text">
                                    <xform:text property="fdRecordId" showStatus="view" style="width:95%;" />
                                    <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdRecordId.tips')}</span>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdAccountId')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdDetailType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单明细类型--%>
                                <div id="_xform_fdDetailType" _xform_type="radio">
                                    <xform:radio property="fdDetailType" htmlElementProperties="id='fdDetailType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_order_detail_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdPayType')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算类型--%>
                                <div id="_xform_fdPayType" _xform_type="radio">
                                    <xform:radio property="fdPayType" htmlElementProperties="id='fdPayType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_pay_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdHotelType')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店类型--%>
                                <div id="_xform_fdHotelType" _xform_type="radio">
                                    <xform:radio property="fdHotelType" htmlElementProperties="id='fdHotelType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdPrice')}
                            </td>
                            <td width="16.6%">
                                <%-- 单价--%>
                                <div id="_xform_fdPrice" _xform_type="text">
                                    <xform:text property="fdPrice" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdQuantity')}
                            </td>
                            <td width="16.6%">
                                <%-- 间夜数--%>
                                <div id="_xform_fdQuantity" _xform_type="text">
                                    <xform:text property="fdQuantity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 金额--%>
                                <div id="_xform_fdAmount" _xform_type="text">
                                    <xform:text property="fdAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 服务费--%>
                                <div id="_xform_fdServiceFee" _xform_type="text">
                                    <xform:text property="fdServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdExtraCharge')}
                            </td>
                            <td width="16.6%">
                                <%-- 手续费（加收税额）--%>
                                <div id="_xform_fdExtraCharge" _xform_type="text">
                                    <xform:text property="fdExtraCharge" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdAccCheckBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算批次号--%>
                                <div id="_xform_fdAccCheckBatchNo" _xform_type="text">
                                    <xform:text property="fdAccCheckBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 明细生成时间--%>
                                <div id="_xform_fdCreateTime" _xform_type="text">
                                    <xform:text property="fdCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdDatachangeLasttime')}
                            </td>
                            <td width="16.6%">
                                <%-- 明细最晚更新时间--%>
                                <div id="_xform_fdDatachangeLasttime" _xform_type="text">
                                    <xform:text property="fdDatachangeLasttime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdOrderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdOrderType" _xform_type="radio">
                                    <xform:radio property="fdOrderType" htmlElementProperties="id='fdOrderType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_order_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdSubAccCheckBatchNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算子批次号--%>
                                <div id="_xform_fdSubAccCheckBatchNo" _xform_type="text">
                                    <xform:text property="fdSubAccCheckBatchNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdSettlementCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算币种--%>
                                <div id="_xform_fdSettlementCurrency" _xform_type="text">
                                    <xform:text property="fdSettlementCurrency" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdIsChecked')}
                            </td>
                            <td width="16.6%">
                                <%-- 确认状态--%>
                                <div id="_xform_fdIsChecked" _xform_type="radio">
                                    <xform:radio property="fdIsChecked" htmlElementProperties="id='fdIsChecked'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_confirm_status" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.invoiceids')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程发票集合--%>
                                <div id="_xform_invoiceids" _xform_type="text">
                                    <xform:text property="invoiceids" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdBatchStartDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 批次起始日期--%>
                                <div id="_xform_fdBatchStartDate" _xform_type="text">
                                    <xform:text property="fdBatchStartDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripHotelSettleInfo.fdBatchEndDate')}
                            </td>
                            <td colspan="3" width="49.8%">
                                <%-- 批次结束日期,--%>
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
