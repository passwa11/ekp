<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingDtaskXform.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtaskXform" property="fdDingUserId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtaskXform" property="fdStatus" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtaskXform" property="fdEkpUser" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtaskXform" property="docCreateTime" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtaskXform" property="fdEkpTaskId" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingDtaskXform.fdName" text="${lfn:message('third-ding:thirdDingDtaskXform.fdName')}" group="sort.list" />
                            <list:sort property="thirdDingDtaskXform.docCreateTime" text="${lfn:message('third-ding:thirdDingDtaskXform.docCreateTime')}" group="sort.list" />
                            <list:sort property="thirdDingDtaskXform.fdEkpUser.fdName" text="${lfn:message('third-ding:thirdDingDtaskXform.fdEkpUser')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/third_ding_dtask_xform/thirdDingDtaskXform.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/third_ding_dtask_xform/thirdDingDtaskXform.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_dtask_xform/thirdDingDtaskXform.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdTaskId;fdDingUserId;fdEkpUser.name;fdInstance.name;docCreateTime;fdStatus.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'dtask_xform',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingDtaskXform',
                templateName: '',
                basePath: '/third/ding/third_ding_dtask_xform/thirdDingDtaskXform.do',
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