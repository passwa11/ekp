<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="kmsKnowledgeFsRecord.docCreateTime" text="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.docCreateTime')}" group="sort.list" />
                            <list:sort property="kmsKnowledgeFsRecord.fdSuccessSize" text="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.fdSuccessSize')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/kms/knowledge/kms_knowledge_fs_record/kmsKnowledgeFsRecord.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
<%--                                <ui:button text="${lfn:message('button.delete')}"--%>
<%--                                           order="5" onclick="deleteAll()"></ui:button>--%>
                            </kmss:auth>
                            <!---->

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/kms/knowledge/kms_knowledge_fs_record/kmsKnowledgeFsRecord.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/kms/knowledge/kms_knowledge_fs_record/kmsKnowledgeFsRecord.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdTotalSize;fdSuccessSize;fdErrorSize;fdStatus;docCreateTime;docCreator.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeFsRecord',
                templateName: '',
                basePath: '/kms/knowledge/kms_knowledge_fs_record/kmsKnowledgeFsRecord.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("kms-knowledge:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/kms/knowledge/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>