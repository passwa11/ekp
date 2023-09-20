<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdEventType" ref="criterion.sys.docSubject" title="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.fdEventType')}" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback" property="docCreateTime" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback" property="fdIsSuccess" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdWeixinWorkCallback.docCreateTime" text="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.docCreateTime')}" group="sort.list" />
                            <list:sort property="thirdWeixinWorkCallback.fdEventType" text="${lfn:message('third-weixin-work:thirdWeixinWorkCallback.fdEventType')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/weixin/work/third_weixin_work_callback/thirdWeixinWorkCallback.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/weixin/work/third_weixin_work_callback/thirdWeixinWorkCallback.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/weixin/work/third_weixin_work_callback/thirdWeixinWorkCallback.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdEventType;fdEventTypeTip;docCreateTime;fdIsSuccess.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'work_callback',
                modelName: 'com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback',
                templateName: '',
                basePath: '/third/weixin/work/third_weixin_work_callback/thirdWeixinWorkCallback.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: 'work',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/weixin/work/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>