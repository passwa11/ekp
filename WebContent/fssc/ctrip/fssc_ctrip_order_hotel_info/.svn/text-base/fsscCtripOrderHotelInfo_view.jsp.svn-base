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

                    'fdClientInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderHotelClient"))}',
                    'fdRoomInfo': '${lfn:escapeJs(lfn:message("fssc-ctrip:table.fsscCtripOrderHotelRoom"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCtripOrderHotelInfoForm.fdOrderId} - " />
            <c:out value="${ lfn:message('fssc-ctrip:table.fsscCtripOrderHotelInfo') }" />
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
                    basePath: '/fssc/ctrip/fssc_ctrip_order_hotel_info/fsscCtripOrderHotelInfo.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_hotel_info/fsscCtripOrderHotelInfo.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripOrderHotelInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_order_hotel_info/fsscCtripOrderHotelInfo.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCtripOrderHotelInfo.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripOrderHotelInfo') }" href="/fssc/ctrip/fssc_ctrip_order_hotel_info/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-ctrip:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-ctrip:table.fsscCtripOrderHotelInfo')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单号--%>
                                <div id="_xform_fdOrderId" _xform_type="text">
                                    <xform:text property="fdOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdJourneyNo')}
                            </td>
                            <td width="16.6%">
                                <%-- 关联行程号--%>
                                <div id="_xform_fdJourneyNo" _xform_type="text">
                                    <xform:text property="fdJourneyNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdTripId')}
                            </td>
                            <td width="16.6%">
                                <%-- 行程单号--%>
                                <div id="_xform_fdTripId" _xform_type="text">
                                    <xform:text property="fdTripId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdUid')}
                            </td>
                            <td width="16.6%">
                                <%-- 携程卡号--%>
                                <div id="_xform_fdUid" _xform_type="text">
                                    <xform:text property="fdUid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdEmployeeId')}
                            </td>
                            <td width="16.6%">
                                <%-- 持卡人编号--%>
                                <div id="_xform_fdEmployeeId" _xform_type="text">
                                    <xform:text property="fdEmployeeId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdEmployeeName')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAmountRMB')}
                            </td>
                            <td width="16.6%">
                                <%-- 总金额--%>
                                <div id="_xform_fdAmountRMB" _xform_type="text">
                                    <xform:text property="fdAmountRMB" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdPostAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 快递费--%>
                                <div id="_xform_fdPostAmount" _xform_type="text">
                                    <xform:text property="fdPostAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdHotelType')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店类型--%>
                                <div id="_xform_fdHotelType" _xform_type="radio">
                                    <xform:radio property="fdHotelType" htmlElementProperties="id='fdHotelType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdHotelName')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店名称--%>
                                <div id="_xform_fdHotelName" _xform_type="text">
                                    <xform:text property="fdHotelName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdHotelEnName')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店英文名--%>
                                <div id="_xform_fdHotelEnName" _xform_type="text">
                                    <xform:text property="fdHotelEnName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdStar')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店星级--%>
                                <div id="_xform_fdStar" _xform_type="text">
                                    <xform:text property="fdStar" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdTelephone')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店电话--%>
                                <div id="_xform_fdTelephone" _xform_type="text">
                                    <xform:text property="fdTelephone" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAddress')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店地址--%>
                                <div id="_xform_fdAddress" _xform_type="text">
                                    <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdOrderDetailStatus')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdOrderDetailStatusName')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单状态名称--%>
                                <div id="_xform_fdOrderDetailStatusName" _xform_type="text">
                                    <xform:text property="fdOrderDetailStatusName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAuthorizeStatus')}
                            </td>
                            <td width="16.6%">
                                <%-- 授权状态--%>
                                <div id="_xform_fdAuthorizeStatus" _xform_type="radio">
                                    <xform:radio property="fdAuthorizeStatus" htmlElementProperties="id='fdAuthorizeStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_order_auth_status" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdOrderDate')}
                            </td>
                            <td width="16.6%">
                                <%-- 下单时间--%>
                                <div id="_xform_fdOrderDate" _xform_type="text">
                                    <xform:text property="fdOrderDate" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdStartTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 入住时间--%>
                                <div id="_xform_fdStartTime" _xform_type="text">
                                    <xform:text property="fdStartTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdEndTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 离店时间--%>
                                <div id="_xform_fdEndTime" _xform_type="text">
                                    <xform:text property="fdEndTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLastCancelTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 最晚取消时间--%>
                                <div id="_xform_fdLastCancelTime" _xform_type="text">
                                    <xform:text property="fdLastCancelTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCancelTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单取消时间--%>
                                <div id="_xform_fdCancelTime" _xform_type="text">
                                    <xform:text property="fdCancelTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdFundAccount')}
                            </td>
                            <td width="16.6%">
                                <%-- 主账户--%>
                                <div id="_xform_fdFundAccount" _xform_type="text">
                                    <xform:text property="fdFundAccount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSubAccount')}
                            </td>
                            <td width="16.6%">
                                <%-- 子账户--%>
                                <div id="_xform_fdSubAccount" _xform_type="text">
                                    <xform:text property="fdSubAccount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdPayType')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付方式--%>
                                <div id="_xform_fdPayType" _xform_type="radio">
                                    <xform:radio property="fdPayType" htmlElementProperties="id='fdPayType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_pay_type" />
                                    </xform:radio>
                                    <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdPayType.tips')}</span>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdBalanceType')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付类型--%>
                                <div id="_xform_fdBalanceType" _xform_type="radio">
                                    <xform:radio property="fdBalanceType" htmlElementProperties="id='fdBalanceType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_balance_type" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCorpPayType')}
                            </td>
                            <td width="16.6%">
                                <%-- 费用类型--%>
                                <div id="_xform_fdCorpPayType" _xform_type="radio">
                                    <xform:radio property="fdCorpPayType" htmlElementProperties="id='fdCorpPayType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_corp_pay_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdProvinceEnName')}
                            </td>
                            <td width="16.6%">
                                <%-- 省、直辖市英文名--%>
                                <div id="_xform_fdProvinceEnName" _xform_type="text">
                                    <xform:text property="fdProvinceEnName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCityId')}
                            </td>
                            <td width="16.6%">
                                <%-- 城市ID--%>
                                <div id="_xform_fdCityId" _xform_type="text">
                                    <xform:text property="fdCityId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCityName')}
                            </td>
                            <td width="16.6%">
                                <%-- 城市名称--%>
                                <div id="_xform_fdCityName" _xform_type="text">
                                    <xform:text property="fdCityName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCityEnName')}
                            </td>
                            <td width="16.6%">
                                <%-- 城市英文名称--%>
                                <div id="_xform_fdCityEnName" _xform_type="text">
                                    <xform:text property="fdCityEnName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdClientName')}
                            </td>
                            <td width="16.6%">
                                <%-- 入住人姓名--%>
                                <div id="_xform_fdClientName" _xform_type="text">
                                    <xform:text property="fdClientName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdContactEmail')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdContactName')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系人姓名--%>
                                <div id="_xform_fdContactName" _xform_type="text">
                                    <xform:text property="fdContactName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdContactTel')}
                            </td>
                            <td width="16.6%">
                                <%-- 联系电话--%>
                                <div id="_xform_fdContactTel" _xform_type="text">
                                    <xform:text property="fdContactTel" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心一--%>
                                <div id="_xform_fdCostCenter" _xform_type="text">
                                    <xform:text property="fdCostCenter" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter2')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心二--%>
                                <div id="_xform_fdCostCenter2" _xform_type="text">
                                    <xform:text property="fdCostCenter2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter3')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心三--%>
                                <div id="_xform_fdCostCenter3" _xform_type="text">
                                    <xform:text property="fdCostCenter3" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter4')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter5')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心五--%>
                                <div id="_xform_fdCostCenter5" _xform_type="text">
                                    <xform:text property="fdCostCenter5" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCostCenter6')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心六--%>
                                <div id="_xform_fdCostCenter6" _xform_type="text">
                                    <xform:text property="fdCostCenter6" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdJuourneyReason')}
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
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdProject')}
                            </td>
                            <td width="16.6%">
                                <%-- 项目号--%>
                                <div id="_xform_fdProject" _xform_type="text">
                                    <xform:text property="fdProject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdDefineflag')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段一--%>
                                <div id="_xform_fdDefineflag" _xform_type="text">
                                    <xform:text property="fdDefineflag" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdDefineflag2')}
                            </td>
                            <td width="16.6%">
                                <%-- 自定义字段二--%>
                                <div id="_xform_fdDefineflag2" _xform_type="text">
                                    <xform:text property="fdDefineflag2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfirmPerson')}
                            </td>
                            <td width="16.6%">
                                <%-- 授权人--%>
                                <div id="_xform_fdConfirmPerson" _xform_type="text">
                                    <xform:text property="fdConfirmPerson" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfirmPerson2')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权人--%>
                                <div id="_xform_fdConfirmPerson2" _xform_type="text">
                                    <xform:text property="fdConfirmPerson2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfirmPersonCc')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送授权人--%>
                                <div id="_xform_fdConfirmPersonCc" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfirmPersonCc2')}
                            </td>
                            <td width="16.6%">
                                <%-- 抄送二次授权人--%>
                                <div id="_xform_fdConfirmPersonCc2" _xform_type="text">
                                    <xform:text property="fdConfirmPersonCc2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLowPriceRc')}
                            </td>
                            <td width="16.6%">
                                <%-- 金额差标代码--%>
                                <div id="_xform_fdLowPriceRc" _xform_type="text">
                                    <xform:text property="fdLowPriceRc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLowPriceRcW')}
                            </td>
                            <td width="16.6%">
                                <%-- 超标原因--%>
                                <div id="_xform_fdLowPriceRcW" _xform_type="text">
                                    <xform:text property="fdLowPriceRcW" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLowPriceRcInfo')}
                            </td>
                            <td width="16.6%">
                                <%-- 金额差标内容--%>
                                <div id="_xform_fdLowPriceRcInfo" _xform_type="text">
                                    <xform:text property="fdLowPriceRcInfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAgreementRc')}
                            </td>
                            <td width="16.6%">
                                <%-- 协议代码--%>
                                <div id="_xform_fdAgreementRc" _xform_type="text">
                                    <xform:text property="fdAgreementRc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAgreementRcW')}
                            </td>
                            <td width="16.6%">
                                <%-- 超协议价原因--%>
                                <div id="_xform_fdAgreementRcW" _xform_type="text">
                                    <xform:text property="fdAgreementRcW" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAgreementInfo')}
                            </td>
                            <td width="16.6%">
                                <%-- 协议内容--%>
                                <div id="_xform_fdAgreementInfo" _xform_type="text">
                                    <xform:text property="fdAgreementInfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdMinPriceRc')}
                            </td>
                            <td width="16.6%">
                                <%-- 最低价RC码--%>
                                <div id="_xform_fdMinPriceRc" _xform_type="text">
                                    <xform:text property="fdMinPriceRc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdMinPriceRcW')}
                            </td>
                            <td width="16.6%">
                                <%-- 超最低价原因--%>
                                <div id="_xform_fdMinPriceRcW" _xform_type="text">
                                    <xform:text property="fdMinPriceRcW" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdRoomName')}
                            </td>
                            <td width="16.6%">
                                <%-- 房型名称--%>
                                <div id="_xform_fdRoomName" _xform_type="text">
                                    <xform:text property="fdRoomName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdRoomQuantity')}
                            </td>
                            <td width="16.6%">
                                <%-- 房间数--%>
                                <div id="_xform_fdRoomQuantity" _xform_type="text">
                                    <xform:text property="fdRoomQuantity" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdRoomDays')}
                            </td>
                            <td width="16.6%">
                                <%-- 间夜数--%>
                                <div id="_xform_fdRoomDays" _xform_type="text">
                                    <xform:text property="fdRoomDays" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdDomestic')}
                            </td>
                            <td width="16.6%">
                                <%-- 国内海外酒店标识--%>
                                <div id="_xform_fdDomestic" _xform_type="radio">
                                    <xform:radio property="fdDomestic" htmlElementProperties="id='fdDomestic'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_domestic" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdIsHasSpecialInvoice')}
                            </td>
                            <td width="16.6%">
                                <%-- 开票--%>
                                <div id="_xform_fdIsHasSpecialInvoice" _xform_type="radio">
                                    <xform:radio property="fdIsHasSpecialInvoice" htmlElementProperties="id='fdIsHasSpecialInvoice'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_invoice" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdGdlat')}
                            </td>
                            <td width="16.6%">
                                <%-- 高德地图纬度--%>
                                <div id="_xform_fdGdlat" _xform_type="text">
                                    <xform:text property="fdGdlat" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdGdlon')}
                            </td>
                            <td width="16.6%">
                                <%-- 高德地图经度--%>
                                <div id="_xform_fdGdlon" _xform_type="text">
                                    <xform:text property="fdGdlon" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdGlat')}
                            </td>
                            <td width="16.6%">
                                <%-- Google地图纬度--%>
                                <div id="_xform_fdGlat" _xform_type="text">
                                    <xform:text property="fdGlat" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdGlon')}
                            </td>
                            <td width="16.6%">
                                <%-- Google地图经度--%>
                                <div id="_xform_fdGlon" _xform_type="text">
                                    <xform:text property="fdGlon" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLat')}
                            </td>
                            <td width="16.6%">
                                <%-- 纬度--%>
                                <div id="_xform_fdLat" _xform_type="text">
                                    <xform:text property="fdLat" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLon')}
                            </td>
                            <td width="16.6%">
                                <%-- 经度--%>
                                <div id="_xform_fdLon" _xform_type="text">
                                    <xform:text property="fdLon" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdIsAutoAmadeus')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否是自动Amadeus--%>
                                <div id="_xform_fdIsAutoAmadeus" _xform_type="radio">
                                    <xform:radio property="fdIsAutoAmadeus" htmlElementProperties="id='fdIsAutoAmadeus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_is_auto" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdIsHandAmadeus')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否是手动Amadeus--%>
                                <div id="_xform_fdIsHandAmadeus" _xform_type="radio">
                                    <xform:radio property="fdIsHandAmadeus" htmlElementProperties="id='fdIsHandAmadeus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_is_auto" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfirmType')}
                            </td>
                            <td width="16.6%">
                                <%-- 一次授权方式--%>
                                <div id="_xform_fdConfirmType" _xform_type="text">
                                    <xform:text property="fdConfirmType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdConfrimType2')}
                            </td>
                            <td width="16.6%">
                                <%-- 二次授权方式--%>
                                <div id="_xform_fdConfrimType2" _xform_type="text">
                                    <xform:text property="fdConfrimType2" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdServerFrom')}
                            </td>
                            <td width="16.6%">
                                <%-- 订单来源--%>
                                <div id="_xform_fdServerFrom" _xform_type="text">
                                    <xform:text property="fdServerFrom" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdStarLicence')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否为星级酒店--%>
                                <div id="_xform_fdStarLicence" _xform_type="radio">
                                    <xform:radio property="fdStarLicence" htmlElementProperties="id='fdStarLicence'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_is_auto" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCustomerEval')}
                            </td>
                            <td width="16.6%">
                                <%-- 酒店钻级--%>
                                <div id="_xform_fdCustomerEval" _xform_type="text">
                                    <xform:text property="fdCustomerEval" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCustomPayAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付金额--%>
                                <div id="_xform_fdCustomPayAmount" _xform_type="text">
                                    <xform:text property="fdCustomPayAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCustomPayCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付币种--%>
                                <div id="_xform_fdCustomPayCurrency" _xform_type="text">
                                    <xform:text property="fdCustomPayCurrency" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCustomPayExchange')}
                            </td>
                            <td width="16.6%">
                                <%-- 支付币种汇率--%>
                                <div id="_xform_fdCustomPayExchange" _xform_type="text">
                                    <xform:text property="fdCustomPayExchange" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSettlementAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算金额--%>
                                <div id="_xform_fdSettlementAmount" _xform_type="text">
                                    <xform:text property="fdSettlementAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSettlementCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算币种--%>
                                <div id="_xform_fdSettlementCurrency" _xform_type="text">
                                    <xform:text property="fdSettlementCurrency" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSettlementExchange')}
                            </td>
                            <td width="16.6%">
                                <%-- 结算币种汇率--%>
                                <div id="_xform_fdSettlementExchange" _xform_type="text">
                                    <xform:text property="fdSettlementExchange" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdIsMixPayment')}
                            </td>
                            <td width="16.6%">
                                <%-- 是否混付订单--%>
                                <div id="_xform_fdIsMixPayment" _xform_type="radio">
                                    <xform:radio property="fdIsMixPayment" htmlElementProperties="id='fdIsMixPayment'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_is_auto" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSettlementAccntAmt')}
                            </td>
                            <td width="16.6%">
                                <%-- 混付公司账户支付金额--%>
                                <div id="_xform_fdSettlementAccntAmt" _xform_type="text">
                                    <xform:text property="fdSettlementAccntAmt" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdSettlementPersonAmt')}
                            </td>
                            <td width="16.6%">
                                <%-- 混付个人账户支付总金额--%>
                                <div id="_xform_fdSettlementPersonAmt" _xform_type="text">
                                    <xform:text property="fdSettlementPersonAmt" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdCouponAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 优惠券金额--%>
                                <div id="_xform_fdCouponAmount" _xform_type="text">
                                    <xform:text property="fdCouponAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdAddedFees')}
                            </td>
                            <td width="16.6%">
                                <%-- 加收税额--%>
                                <div id="_xform_fdAddedFees" _xform_type="text">
                                    <xform:text property="fdAddedFees" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdTmcPriceType')}
                            </td>
                            <td width="16.6%">
                                <%-- 商旅产品类型字段--%>
                                <div id="_xform_fdTmcPriceType" _xform_type="text">
                                    <xform:text property="fdTmcPriceType" showStatus="view" style="width:95%;" />
                                    <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdTmcPriceType.tips')}</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdLadderDeductAmount')}
                            </td>
                            <td width="16.6%">
                                <%-- 阶梯金额--%>
                                <div id="_xform_fdLadderDeductAmount" _xform_type="text">
                                    <xform:text property="fdLadderDeductAmount" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdPlatformOrderId')}
                            </td>
                            <td width="16.6%">
                                <%-- 平台订单号--%>
                                <div id="_xform_fdPlatformOrderId" _xform_type="text">
                                    <xform:text property="fdPlatformOrderId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdRoomType')}
                            </td>
                            <td width="16.6%">
                                <%-- 子房型--%>
                                <div id="_xform_fdRoomType" _xform_type="radio">
                                    <xform:radio property="fdRoomType" htmlElementProperties="id='fdRoomType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_room_type" />
                                    </xform:radio>
                                    <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdRoomType.tips')}</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-ctrip:fsscCtripOrderHotelInfo.fdMealType')}
                            </td>
                            <td width="16.6%">
                                <%-- 餐食类型--%>
                                <div id="_xform_fdMealType" _xform_type="radio">
                                    <xform:radio property="fdMealType" htmlElementProperties="id='fdMealType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_ctrip_hotel_meal_type" />
                                    </xform:radio>
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
