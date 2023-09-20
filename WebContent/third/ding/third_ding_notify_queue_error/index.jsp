<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-ding-notify:module.third.ding') }-${ lfn:message('third-ding-notify:table.thirdDingNotifyQueueError') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('third-ding-notify:table.thirdDingNotifyQueueError') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
        
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('list.otherOpt') }">
                    <ul class='lui_list_nav_list'>
                        <li><a href="${LUI_ContextPath}/third/ding/third_ding_notify_log/index.jsp${j_iframe}">待办同步日志（到钉钉）</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/third/ding/third_ding_notify_workrecord/index.jsp${j_iframe}">${lfn:message('third-ding-notify:table.thirdDingNotifyWorkrecord')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/third/ding/third_ding_notify_queue_error/index.jsp${j_iframe}">${lfn:message('third-ding-notify:table.thirdDingNotifyQueueError')}</a>
                        </li>
                    </ul>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSubject')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdMethod" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdRepeatHandle" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdTodoId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdDingUserId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError" property="fdUser" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingNotifyQueueError.fdRepeatHandle" text="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdRepeatHandle')}" group="sort.list" />
                            <list:sort property="thirdDingNotifyQueueError.fdSendTime" text="${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSendTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdSubject;fdErrorMsg;fdSendTime;fdRepeatHandle;fdTodoId;fdUser.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'notify_queue_error',
                modelName: 'com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError',
                templateName: '',
                basePath: '/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-ding-notify:treeModel.alert.templateAlert")}',
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