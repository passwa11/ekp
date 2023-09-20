<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingDtemplate.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdProcessCode" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdAgentId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdType" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdFlow" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdIsAvailable" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingDtemplate" property="fdDisableFormEdit" />

            </list:criteria>
             -->
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingDtemplate.fdName" text="${lfn:message('third-ding:thirdDingDtemplate.fdName')}" group="sort.list" />
                            <list:sort property="thirdDingDtemplate.fdProcessCode" text="${lfn:message('third-ding:thirdDingDtemplate.fdProcessCode')}" group="sort.list" />
                            <list:sort property="thirdDingDtemplate.fdAgentId" text="${lfn:message('third-ding:thirdDingDtemplate.fdAgentId')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <%-- <kmss:auth requestURL="/third/ding/third_ding_dtemplate/thirdDingDtemplate.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/third/ding/third_ding_dtemplate/thirdDingDtemplate.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" /> --%>

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/third_ding_dtemplate/thirdDingDtemplate.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_dtemplate/thirdDingDtemplate.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdProcessCode;fdCorpId;fdAgentId;fdIsAvailable.name;fdType.name;fdFlow.name;fdDisableFormEdit.name;docCreateTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingDtemplate',
                templateName: '',
                basePath: '/third/ding/third_ding_dtemplate/thirdDingDtemplate.do',
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