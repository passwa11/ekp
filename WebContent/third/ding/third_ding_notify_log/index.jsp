<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list">
    <template:replace name="title">
        <c:out value="${ lfn:message('third-ding-notify:module.third.ding') }-${ lfn:message('third-ding-notify:table.thirdDingNotifyLog') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('third-ding-notify:table.thirdDingNotifyLog') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="nav">
       
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

               

                <ui:content title="相关日志">
                    <ul class='lui_list_nav_list'>
                    	<li><a href="${LUI_ContextPath}/third/ding/notify/third_ding_notify_log/index.jsp${j_iframe}">待办同步日志（到钉钉）</a>
                        </li>
                        <li><a href="${LUI_ContextPath}/third/ding/notify/third_ding_notify_workrecord/index.jsp${j_iframe}">${lfn:message('third-ding-notify:table.thirdDingNotifyWorkrecord')}</a>
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
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('third-ding-notify:thirdDingNotifyLog.docSubject')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog" property="fdNotifyId" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog" property="fdResult" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingNotifyLog.fdSendTime" text="${lfn:message('third-ding-notify:thirdDingNotifyLog.fdSendTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/ding/notify/third_ding_notify_log/thirdDingNotifyLog.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/ding/notify/third_ding_notify_log/thirdDingNotifyLog.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/notify/third_ding_notify_log/thirdDingNotifyLog.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;fdNotifyId;fdSendTime;fdResult.name;fdUrl" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'notify_log',
                modelName: 'com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog',
                templateName: '',
                basePath: '/third/ding/notify/third_ding_notify_log/thirdDingNotifyLog.do',
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