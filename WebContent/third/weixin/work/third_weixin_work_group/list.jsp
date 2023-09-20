<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup" property="fdModelName" />
                <list:cri-auto modelName="com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkGroup" property="fdModelId" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>

            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/weixin/work/third_weixin_work_group/thirdWeixinWorkGroup.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdModelName;fdModelId;fdGroupId;" url="" /></list:colTable>
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