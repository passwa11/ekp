<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-ctrip:module.fssc.ctrip') }-${ lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('list.search') }">
                    <ul class='lui_list_nav_list'>

                        <li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').clearValue();">${ lfn:message('list.alldoc') }</a>
                        </li>
                    </ul>
                </ui:content>

                <ui:content title="${ lfn:message('list.otherOpt') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_order_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelOrderInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_client_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelClientInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_room_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripRoomInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_order_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirOrderInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_passenger/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirPassenger')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_rebook_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirRebookInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_refund_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirRefundInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_voyageinfo/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirVoyageinfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_print_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirPrintInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_seg_print_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirSegPrintInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_daily_avg_price/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripDailyAvgPrice')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_settle_base/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelSettleBase')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_settle_base/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainSettleBase')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_passenger/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainPassenger')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_data/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainData')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_seat_data/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripSeatData')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_relation/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainRelation')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_insu_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainInsuInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_ticket_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainTicketInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_invoice/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainInvoice')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_filed_mapping/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripFiledMapping')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_corp_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarCorpInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_passenger/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarPassenger')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_sd_product/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarSdProduct')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_ch_product/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarChProduct')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_och_product/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarOchProduct')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_quick_product/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarQuickProduct')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_chart_product/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarChartProduct')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_syn_config/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripSynConfig')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/fssc/ctrip" target="_blank">${ lfn:message('list.manager') }</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="" url="/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.fssc.ctrip.model.FsscCtripAirSettleInfo" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'airSettle',
                modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripAirSettleInfo',
                templateName: '',
                basePath: '/fssc/ctrip/fssc_ctrip_air_settle_info/fsscCtripAirSettleInfo.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-ctrip:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
