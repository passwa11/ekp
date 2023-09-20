<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-ctrip:module.fssc.ctrip') }-${ lfn:message('fssc-ctrip:table.fsscCtripPaymentMapping') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripPaymentMapping') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-ctrip:table.fsscCtripPaymentMapping') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do"} ]
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
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_order_hotel_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripOrderHotelInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_air_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripAirSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_hotel_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripHotelSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_filed_mapping/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripFiledMapping')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_car_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripCarSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_train_settle_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripTrainSettleInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_order_flight_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripOrderFlightInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_order_train_info/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripOrderTrainInfo')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_order_car/index.jsp${j_iframe}">${lfn:message('fssc-ctrip:table.fsscCtripOrderCar')}</a>
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
                    {url:appendQueryParameter('/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'payment_mapping',
                modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripPaymentMapping',
                templateName: '',
                basePath: '/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do',
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
