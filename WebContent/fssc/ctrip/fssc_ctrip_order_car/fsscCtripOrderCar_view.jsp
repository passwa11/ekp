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

                    'fdPassengerInfoList': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderCarPassenger"))}',
                    'fdPaymentInfoList': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderCarPayment"))}',
                    'fdCarControlInfoList': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderCarControl"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCtripOrderCarForm.fdOrderId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripOrderCar') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_order_car/fsscCtripOrderCar.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_car/fsscCtripOrderCar.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripOrderCar.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_car/fsscCtripOrderCar.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripOrderCar.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripOrderCar') }" href="/fssc/ctrip/fssc_ctrip_order_car/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripOrderCar')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程单号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单总金额--%>
                                <div id="_xform_fdOrderAmount" _xform_type="text">
                                    <xform:text property="fdOrderAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDealAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 最终成交金额--%>
                                <div id="_xform_fdDealAmount" _xform_type="text">
                                    <xform:text property="fdDealAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdServiceFee')}
                            </td>
                            <td width="16.6%">
                                <%-- 服务费--%>
                                <div id="_xform_fdServiceFee" _xform_type="text">
                                    <xform:text property="fdServiceFee" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderDetailStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态--%>
                                <div id="_xform_fdOrderDetailStatus" _xform_type="text">
                                    <xform:text property="fdOrderDetailStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPaymentStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付状态--%>
                                <div id="_xform_fdPaymentStatus" _xform_type="text">
                                    <xform:text property="fdPaymentStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdAuthorizeStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 授权状态--%>
                                <div id="_xform_fdAuthorizeStatus" _xform_type="text">
                                    <xform:text property="fdAuthorizeStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdNeedInvoice')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否需要发票--%>
                                <div id="_xform_fdNeedInvoice" _xform_type="radio">
                                    <xform:radio property="fdNeedInvoice" htmlElementProperties="id='fdNeedInvoice'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdContactEmail')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系邮箱--%>
                                <div id="_xform_fdContactEmail" _xform_type="text">
                                    <xform:text property="fdContactEmail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdContactName')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人姓名--%>
                                <div id="_xform_fdContactName" _xform_type="text">
                                    <xform:text property="fdContactName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdContactTel')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系电话--%>
                                <div id="_xform_fdContactTel" _xform_type="text">
                                    <xform:text property="fdContactTel" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPayType')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付方式--%>
                                <div id="_xform_fdPayType" _xform_type="radio">
                                    <xform:radio property="fdPayType" htmlElementProperties="id='fdPayType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_pay_type" />
                                    </xform:radio>
                                    <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPayType.tips')}</span>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEmployeeName')}
                            </td>
                            <td width="16.6%">
                                <%-- 持卡人姓名--%>
                                <div id="_xform_fdEmployeeName" _xform_type="text">
                                    <xform:text property="fdEmployeeName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCorpPayType')}
                            </td>
                            <td width="16.6%">
                                <%-- 费用类型--%>
                                <div id="_xform_fdCorpPayType" _xform_type="radio">
                                    <xform:radio property="fdCorpPayType" htmlElementProperties="id='fdCorpPayType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_corp_pay_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderCreateDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 预定时间--%>
                                <div id="_xform_fdOrderCreateDate" _xform_type="datetime">
                                    <xform:datetime property="fdOrderCreateDate" showStatus="view" dateTimeType="date" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdReachCarControl')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否符合差标--%>
                                <div id="_xform_fdReachCarControl" _xform_type="text">
                                    <xform:text property="fdReachCarControl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCarControlItems')}
                            </td>
                            <td width="16.6%">
                                <%-- 管控指标--%>
                                <div id="_xform_fdCarControlItems" _xform_type="text">
                                    <xform:text property="fdCarControlItems" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderTypeName')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型Name--%>
                                <div id="_xform_fdOrderTypeName" _xform_type="text">
                                    <xform:text property="fdOrderTypeName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdOrderType')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单类型--%>
                                <div id="_xform_fdOrderType" _xform_type="text">
                                    <xform:text property="fdOrderType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEmployeeId')}
                            </td>
                            <td width="16.6%">
                                <%-- 持卡人编号--%>
                                <div id="_xform_fdEmployeeId" _xform_type="text">
                                    <xform:text property="fdEmployeeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdConfirmType')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权方式--%>
                                <div id="_xform_fdConfirmType" _xform_type="text">
                                    <xform:text property="fdConfirmType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdConfirmTypeTwo')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权方式--%>
                                <div id="_xform_fdConfirmTypeTwo" _xform_type="text">
                                    <xform:text property="fdConfirmTypeTwo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdServerFrom')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单来源--%>
                                <div id="_xform_fdServerFrom" _xform_type="text">
                                    <xform:text property="fdServerFrom" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCorporationId')}
                            </td>
                            <td width="16.6%">
                                <%-- 公司编号--%>
                                <div id="_xform_fdCorporationId" _xform_type="text">
                                    <xform:text property="fdCorporationId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCorporationName')}
                            </td>
                            <td width="16.6%">
                                <%-- 公司名称--%>
                                <div id="_xform_fdCorporationName" _xform_type="text">
                                    <xform:text property="fdCorporationName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdAccountId')}
                            </td>
                            <td width="16.6%">
                                <%-- 主账户ID--%>
                                <div id="_xform_fdAccountId" _xform_type="text">
                                    <xform:text property="fdAccountId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdSubAccountId')}
                            </td>
                            <td width="16.6%">
                                <%-- 子账户ID--%>
                                <div id="_xform_fdSubAccountId" _xform_type="text">
                                    <xform:text property="fdSubAccountId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter1')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心一--%>
                                <div id="_xform_fdCostCenter1" _xform_type="text">
                                    <xform:text property="fdCostCenter1" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter2')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心二--%>
                                <div id="_xform_fdCostCenter2" _xform_type="text">
                                    <xform:text property="fdCostCenter2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter3')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心三--%>
                                <div id="_xform_fdCostCenter3" _xform_type="text">
                                    <xform:text property="fdCostCenter3" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter4')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心四--%>
                                <div id="_xform_fdCostCenter4" _xform_type="text">
                                    <xform:text property="fdCostCenter4" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter5')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心五--%>
                                <div id="_xform_fdCostCenter5" _xform_type="text">
                                    <xform:text property="fdCostCenter5" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostCenter6')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心六--%>
                                <div id="_xform_fdCostCenter6" _xform_type="text">
                                    <xform:text property="fdCostCenter6" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdJuourneyReason')}
                            </td>
                            <td width="16.6%">
                                <%-- 出行目的--%>
                                <div id="_xform_fdJuourneyReason" _xform_type="text">
                                    <xform:text property="fdJuourneyReason" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdProject')}
                            </td>
                            <td width="16.6%">
                                <%-- 项目号--%>
                                <div id="_xform_fdProject" _xform_type="text">
                                    <xform:text property="fdProject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDefineValue1')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段一--%>
                                <div id="_xform_fdDefineValue1" _xform_type="text">
                                    <xform:text property="fdDefineValue1" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDefineValue2')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段二--%>
                                <div id="_xform_fdDefineValue2" _xform_type="text">
                                    <xform:text property="fdDefineValue2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdRcCodeId')}
                            </td>
                            <td width="16.6%">
                                <%-- ReasonCode编码ID--%>
                                <div id="_xform_fdRcCodeId" _xform_type="text">
                                    <xform:text property="fdRcCodeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdJourneyNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 关联行程号--%>
                                <div id="_xform_fdJourneyNo" _xform_type="text">
                                    <xform:text property="fdJourneyNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdVendorId')}
                            </td>
                            <td width="16.6%">
                                <%-- 供应商ID--%>
                                <div id="_xform_fdVendorId" _xform_type="text">
                                    <xform:text property="fdVendorId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdVendorName')}
                            </td>
                            <td width="16.6%">
                                <%-- 供应商名称--%>
                                <div id="_xform_fdVendorName" _xform_type="text">
                                    <xform:text property="fdVendorName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdVendorCarLogo')}
                            </td>
                            <td width="16.6%">
                                <%-- 供应商车型Logo--%>
                                <div id="_xform_fdVendorCarLogo" _xform_type="text">
                                    <xform:text property="fdVendorCarLogo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdVehicleId')}
                            </td>
                            <td width="16.6%">
                                <%-- 车型Id--%>
                                <div id="_xform_fdVehicleId" _xform_type="text">
                                    <xform:text property="fdVehicleId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdVehicleName')}
                            </td>
                            <td width="16.6%">
                                <%-- 车型--%>
                                <div id="_xform_fdVehicleName" _xform_type="text">
                                    <xform:text property="fdVehicleName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCarCapacity')}
                            </td>
                            <td width="16.6%">
                                <%-- 车辆容量--%>
                                <div id="_xform_fdCarCapacity" _xform_type="text">
                                    <xform:text property="fdCarCapacity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCarDescription')}
                            </td>
                            <td width="16.6%">
                                <%-- 车辆描述--%>
                                <div id="_xform_fdCarDescription" _xform_type="text">
                                    <xform:text property="fdCarDescription" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartCityId')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始城市ID--%>
                                <div id="_xform_fdStartCityId" _xform_type="text">
                                    <xform:text property="fdStartCityId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartCityName')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始城市名--%>
                                <div id="_xform_fdStartCityName" _xform_type="text">
                                    <xform:text property="fdStartCityName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.starttime')}
                            </td>
                            <td width="16.6%">
                                <%-- 用车开始时间--%>
                                <div id="_xform_starttime" _xform_type="text">
                                    <xform:text property="starttime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 用车结束时间--%>
                                <div id="_xform_fdEndTime" _xform_type="text">
                                    <xform:text property="fdEndTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始地点--%>
                                <div id="_xform_fdStartAddress" _xform_type="text">
                                    <xform:text property="fdStartAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartLongitude')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始地点经度--%>
                                <div id="_xform_fdStartLongitude" _xform_type="text">
                                    <xform:text property="fdStartLongitude" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartLatitude')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始地点纬度--%>
                                <div id="_xform_fdStartLatitude" _xform_type="text">
                                    <xform:text property="fdStartLatitude" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartContactInfo')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始地点联系方式--%>
                                <div id="_xform_fdStartContactInfo" _xform_type="text">
                                    <xform:text property="fdStartContactInfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdStartOpenHours')}
                            </td>
                            <td width="16.6%">
                                <%-- 起始地点营业时间--%>
                                <div id="_xform_fdStartOpenHours" _xform_type="text">
                                    <xform:text property="fdStartOpenHours" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndCityId')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束城市ID--%>
                                <div id="_xform_fdEndCityId" _xform_type="text">
                                    <xform:text property="fdEndCityId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndCityName')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束城市名--%>
                                <div id="_xform_fdEndCityName" _xform_type="text">
                                    <xform:text property="fdEndCityName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点--%>
                                <div id="_xform_fdEndAddress" _xform_type="text">
                                    <xform:text property="fdEndAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndAddressDes')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点详情--%>
                                <div id="_xform_fdEndAddressDes" _xform_type="text">
                                    <xform:text property="fdEndAddressDes" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndLongitude')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点经度--%>
                                <div id="_xform_fdEndLongitude" _xform_type="text">
                                    <xform:text property="fdEndLongitude" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndLatitude')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点纬度--%>
                                <div id="_xform_fdEndLatitude" _xform_type="text">
                                    <xform:text property="fdEndLatitude" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndContactInfo')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点联系方式--%>
                                <div id="_xform_fdEndContactInfo" _xform_type="text">
                                    <xform:text property="fdEndContactInfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdEndOpenHours')}
                            </td>
                            <td width="16.6%">
                                <%-- 结束地点营业时间--%>
                                <div id="_xform_fdEndOpenHours" _xform_type="text">
                                    <xform:text property="fdEndOpenHours" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPickupStoreCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 取车门店Code--%>
                                <div id="_xform_fdPickupStoreCode" _xform_type="text">
                                    <xform:text property="fdPickupStoreCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPickupStoreType')}
                            </td>
                            <td width="16.6%">
                                <%-- 取车门店类型--%>
                                <div id="_xform_fdPickupStoreType" _xform_type="text">
                                    <xform:text property="fdPickupStoreType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDropoffStoreCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 还车门店Code--%>
                                <div id="_xform_fdDropoffStoreCode" _xform_type="text">
                                    <xform:text property="fdDropoffStoreCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDropoffStoreType')}
                            </td>
                            <td width="16.6%">
                                <%-- 还车门店类型--%>
                                <div id="_xform_fdDropoffStoreType" _xform_type="text">
                                    <xform:text property="fdDropoffStoreType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdProductName')}
                            </td>
                            <td width="16.6%">
                                <%-- 产品名称--%>
                                <div id="_xform_fdProductName" _xform_type="text">
                                    <xform:text property="fdProductName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPnr')}
                            </td>
                            <td width="16.6%">
                                <%-- 租车确认号--%>
                                <div id="_xform_fdPnr" _xform_type="text">
                                    <xform:text property="fdPnr" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPickupStoreId')}
                            </td>
                            <td width="16.6%">
                                <%-- 取车门店id--%>
                                <div id="_xform_fdPickupStoreId" _xform_type="text">
                                    <xform:text property="fdPickupStoreId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDropoffStoreId')}
                            </td>
                            <td width="16.6%">
                                <%-- 还车门店Id--%>
                                <div id="_xform_fdDropoffStoreId" _xform_type="text">
                                    <xform:text property="fdDropoffStoreId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCancelRuleDesc')}
                            </td>
                            <td width="16.6%">
                                <%-- 取消规则描述--%>
                                <div id="_xform_fdCancelRuleDesc" _xform_type="text">
                                    <xform:text property="fdCancelRuleDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdTip')}
                            </td>
                            <td width="16.6%">
                                <%-- 温馨提示--%>
                                <div id="_xform_fdTip" _xform_type="text">
                                    <xform:text property="fdTip" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCostType')}
                            </td>
                            <td width="16.6%">
                                <%-- 发票费用类型--%>
                                <div id="_xform_fdCostType" _xform_type="text">
                                    <xform:text property="fdCostType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdTitle')}
                            </td>
                            <td width="16.6%">
                                <%-- 发票抬头--%>
                                <div id="_xform_fdTitle" _xform_type="text">
                                    <xform:text property="fdTitle" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDetail')}
                            </td>
                            <td width="16.6%">
                                <%-- 发票明细--%>
                                <div id="_xform_fdDetail" _xform_type="text">
                                    <xform:text property="fdDetail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdReceiverName')}
                            </td>
                            <td width="16.6%">
                                <%-- 收件人名称--%>
                                <div id="_xform_fdReceiverName" _xform_type="text">
                                    <xform:text property="fdReceiverName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdReceiveTel')}
                            </td>
                            <td width="16.6%">
                                <%-- 收件人电话--%>
                                <div id="_xform_fdReceiveTel" _xform_type="text">
                                    <xform:text property="fdReceiveTel" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdProvinceName')}
                            </td>
                            <td width="16.6%">
                                <%-- 省--%>
                                <div id="_xform_fdProvinceName" _xform_type="text">
                                    <xform:text property="fdProvinceName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCityName')}
                            </td>
                            <td width="16.6%">
                                <%-- 市--%>
                                <div id="_xform_fdCityName" _xform_type="text">
                                    <xform:text property="fdCityName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdDistrictName')}
                            </td>
                            <td width="16.6%">
                                <%-- 区--%>
                                <div id="_xform_fdDistrictName" _xform_type="text">
                                    <xform:text property="fdDistrictName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 地址--%>
                                <div id="_xform_fdAddress" _xform_type="text">
                                    <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdPostCode')}
                            </td>
                            <td width="16.6%">
                                <%-- 邮编--%>
                                <div id="_xform_fdPostCode" _xform_type="text">
                                    <xform:text property="fdPostCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdInvoiceType')}
                            </td>
                            <td width="16.6%">
                                <%-- 发票类型--%>
                                <div id="_xform_fdInvoiceType" _xform_type="text">
                                    <xform:text property="fdInvoiceType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderCar.fdCustomerTaxNumber')}
                            </td>
                            <td width="16.6%">
                                <%-- 纳税人识别号--%>
                                <div id="_xform_fdCustomerTaxNumber" _xform_type="text">
                                    <xform:text property="fdCustomerTaxNumber" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>
