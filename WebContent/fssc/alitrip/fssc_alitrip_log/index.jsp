<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-alitrip:module.fssc.alitrip') }-${ lfn:message('fssc-alitrip:table.fsscAlitripLog') }" />
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscAlitripLogPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
            <ui:content id="fsscAlitripLogContent" title="${ lfn:message('fssc-alibtrip:table.fsscAlitripLog') }">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripLog" property="fdType" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripLog" property="fdErrCode" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripLog" property="fdModelId" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripLog" property="fdModelName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripLog" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                            <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripLog">
                                <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripLog')">
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
                    {url:appendQueryParameter('/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdDesc;docCreateTime;fdModelId;fdErrCode;fdType;fdInterType" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
    </ui:content>
</ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'log',
                modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripLog',
                templateName: '',
                basePath: '/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do',
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
