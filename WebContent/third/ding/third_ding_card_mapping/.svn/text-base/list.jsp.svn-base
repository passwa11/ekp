<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingCardMapping.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingCardMapping" property="fdCardId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingCardMapping" property="fdModelId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingCardMapping" property="docCreateTime" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingCardMapping" property="docAlterTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingCardMapping.fdName" text="${lfn:message('third-ding:thirdDingCardMapping.fdName')}" group="sort.list" />
                            <list:sort property="thirdDingCardMapping.docAlterTime" text="${lfn:message('third-ding:thirdDingCardMapping.docAlterTime')}" group="sort.list" />
                            <list:sort property="thirdDingCardMapping.docCreateTime" text="${lfn:message('third-ding:thirdDingCardMapping.docCreateTime')}" group="sort.list" />
                            <list:sort property="thirdDingCardMapping.fdCardId" text="${lfn:message('third-ding:thirdDingCardMapping.fdCardId')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/third_ding_card_mapping/thirdDingCardMapping.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!--deleteall-->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/third_ding_card_mapping/thirdDingCardMapping.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_card_mapping/thirdDingCardMapping.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdCardId;fdModelId;docAlterTime;docCreateTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'card_mapping',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingCardMapping',
                templateName: '',
                basePath: '/third/ding/third_ding_card_mapping/thirdDingCardMapping.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>