<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-ding-notify:module.third.ding') }-${ lfn:message('third-ding-notify:table.thirdDingNotifyMessage') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('third-ding-notify:table.thirdDingNotifyMessage') }" />
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
                        <li><a href="${LUI_ContextPath}/third/ding/notify/third_ding_notify_log/index.jsp${j_iframe}">待办同步日志（到钉钉）</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/third/ding/notify/third_ding_notify_message/index.jsp${j_iframe}">${lfn:message('third-ding-notify:table.thirdDingNotifyMessage')}</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/third/ding/notify/third_ding_notify_queue_error/index.jsp${j_iframe}">${lfn:message('third-ding-notify:table.thirdDingNotifyQueueError')}</a>
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
                <list:cri-ref key="fdNotifyId" ref="criterion.sys.docSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyMessage.fdNotifyId')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage" property="fdSubject" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage" property="fdUser" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingNotifyMessage.docCreateTime" text="${lfn:message('third-ding-notify:thirdDingNotifyMessage.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/notify/third_ding_notify_message/thirdDingNotifyMessage.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/notify/third_ding_notify_message/thirdDingNotifyMessage.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/notify/third_ding_notify_message/thirdDingNotifyMessage.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdNotifyId;fdSubject;docCreateTime;fdDingUserId;fdDingTaskId;fdUser.name" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'notify_message',
                modelName: 'com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage',
                templateName: '',
                basePath: '/third/ding/notify/third_ding_notify_message/thirdDingNotifyMessage.do',
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