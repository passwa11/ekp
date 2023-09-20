<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-alitrip:module.fssc.alitrip') }-${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscAlitripOrderPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
        <ui:content id="fsscAlitripOrderContent" title="${ lfn:message('fssc-alibtrip:table.fsscAlitripOrder') }">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="fdType" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="corpName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="userName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="departName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="city" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="costCenterName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="costCenterNumber" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="orderStatus" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="orderType" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="gmtCreate" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="gmtModified" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscAlitripOrder.gmtCreate" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtCreate')}" group="sort.list" />
                            <list:sort property="fsscAlitripOrder.gmtModified" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.gmtModified')}" group="sort.list" />
                            <list:sort property="fsscAlitripOrder.fdType" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}" group="sort.list" />
                            <list:sort property="fsscAlitripOrder.corpName" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.corpName')}" group="sort.list" />
                            <list:sort property="fsscAlitripOrder.userName" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                            <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder">
                                <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder')">
                                </ui:button>
                            </kmss:auth>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="" url="/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'order',
                modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder',
                templateName: '',
                basePath: '/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-alitrip:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/alitrip/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
