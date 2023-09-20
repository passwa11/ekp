<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdNotifyId" ref="criterion.sys.docSubject" title="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdNotifyId')}" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog" property="fdSubject" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog" property="fdMethod" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog" property="fdReqDate" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdWeixinNotifyLog.fdReqDate" text="${lfn:message('third-weixin-work:thirdWeixinNotifyLog.fdReqDate')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/third/weixin/work/third_weixin_notify_log/thirdWeixinNotifyLog.do?method=deleteall">
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
                    {url:appendQueryParameter('/third/weixin/work/third_weixin_notify_log/thirdWeixinNotifyLog.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/weixin/work/third_weixin_notify_log/thirdWeixinNotifyLog.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdSubject;fdNotifyId;fdApiType;fdMethod;fdResult.name;fdReqDate;fdExpireTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'notify_log',
                modelName: 'com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog',
                templateName: '',
                basePath: '/third/weixin/work/third_weixin_notify_log/thirdWeixinNotifyLog.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("third-weixin-work:treeModel.alert.templateAlert")}',
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