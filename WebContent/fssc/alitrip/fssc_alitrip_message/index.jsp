<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripMessage" property="fdAppKey" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripMessage" property="fdAppSecret" />
                <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripMessage" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscAlitripMessage.fdName" text="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdName')}" group="sort.list" />
                            <list:sort property="fsscAlitripMessage.fdAppKey" text="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=deleteall">
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
                    {url:appendQueryParameter('/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdAppKey;fdAppSecret" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripMessage',
                templateName: '',
                basePath: '/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do',
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
