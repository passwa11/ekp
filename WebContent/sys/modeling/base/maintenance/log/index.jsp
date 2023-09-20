<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
    if (!UserUtil.checkRole("ROLE_MODELING_MAINTENANCE")) {
        request.getRequestDispatcher("/resource/jsp/e403.jsp").forward(request, response);
    }
%>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modeling.model.fdName')}"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <!-- 全选 -->
<%--                <div class="lui_list_operation_order_btn">--%>
<%--                    <list:selectall></list:selectall>--%>
<%--                </div>--%>
                <!-- 分割线 -->
                <div class="lui_list_operation_line"></div>
                <!-- 排序 -->
                <div class="lui_list_operation_sort_btn">
                    <div class="lui_list_operation_order_text">
                            ${ lfn:message('list.orderType') }：
                    </div>
                    <div class="lui_list_operation_sort_toolbar">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sortgroup>
                                <list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }"
                                           group="sort.list"></list:sort>
                            </list:sortgroup>
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top">
                    </list:paging>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                    </div>
                </div>
            </div>
        </div>
        <ui:fixed elem=".lui_list_operation"/>
        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:appendQueryParameter('/sys/modeling/base/maintenance.do?method=data')}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="false"  name="columntable">
<%--                <list:col-checkbox/>--%>
                <list:col-serial/>
                <list:col-auto props="fdName;fdIp;fdStartTime;fdEndTime;fdTime;docCreateTime;docCreator.name;fdStatus" url=""/></list:colTable>
        </list:listview>
        <!-- 翻页 -->
        </div>
        <list:paging/>
        <div style="width: 100%;height: 40px;margin-bottom: 10px"></div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.maintenance.model.ModelingMaintenanceLog',
                templateName: '',
                basePath: '/sys/modeling/base/maintenance.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };

            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);

            <%--            seajs.use(["lui/jquery", "sys/ui/js/dialog","lui/topic"], function ($, dialog,topic) {--%>
            <%--                topic.subscribe("list.loaded",function(){--%>
            <%--                    var bodyHeight = $(document.body).outerHeight(true)+70;--%>
            <%--                    $("body",parent.document).find('#trigger_iframe').height(bodyHeight);--%>
            <%--                });--%>
            <%--            });--%>
        </script>
    </template:replace>
</template:include>