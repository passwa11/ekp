<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-common:module.fssc.common') }-${ lfn:message('fssc-common:table.fsscCommonTransferLog') }" />
    </template:replace>
    <template:replace name="content">
    	<ui:tabpanel id="fsscCommonTransferLogPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
		<ui:content id="fsscCommonTransferLogContent" title="${ lfn:message('fssc-common:table.fsscCommonTransferLog') }">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
			<list:criteria id="criteria1">
                <list:cri-ref key="fdTarget" ref="criterion.sys.docSubject" title="${lfn:message('fssc-common:fsscCommonTransferLog.fdTarget')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.common.model.FsscCommonTransferLog" property="fdResult" expand="true" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/common/fssc_common_transfer_log/fsscCommonTransferLog.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/common/fssc_common_transfer_log/fsscCommonTransferLog.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdTarget;fdStartTime;fdEndTime;fdCount;fdResult;fdMessage;fdOperator" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'transfer_log',
                modelName: 'com.landray.kmss.fssc.common.model.FsscCommonTransferLog',
                templateName: '',
                basePath: '/fssc/common/fssc_common_transfer_log/fsscCommonTransferLog.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-common:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
        </script>
        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>