<%--
  Created by IntelliJ IDEA.
  User: 96581
  Date: 2020/11/12
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
    if (!UserUtil.checkRole("ROLE_MODELING_MAINTENANCE")) {
        request.getRequestDispatcher("/resource/jsp/e403.jsp").forward(request, response);
    }
%>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="content">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appDesign.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appList.css"/>
        <script type="text/javascript">seajs.use(['theme!profile'])</script>
        <script type="text/javascript">seajs.use(['theme!iconfont'])</script>
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("maintenance.css", "${LUI_ContextPath}/sys/modeling/base/maintenance/resource/", "css", true);
        </script>
        <div class="lui_modeling" style="padding: 0;height: 90%">
            <div class="lui_modeling_main" id="toolMain" style="border-left: none;border-right: none">
<%--                <div class="lui_modeling_main_head">--%>
<%--                    <div class="lui_profile_listview_searchWrap" style="float:left;margin:0 0 0 25px;">--%>
<%--                        <input type="text" class="lui_profile_search_input"--%>
<%--                               placeholder="${lfn:message('sys-modeling-base:modeling.profile.search')}"--%>
<%--                               onkeyup='searchTools(event,this);'>--%>
<%--                        <i class="lui_profile_listview_icon lui_icon_s_icon_search" style="cursor: pointer"--%>
<%--                           onclick="searchApp_icon(this)"></i>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="lui_modeling_main_content">
                    <div class="lui_profile_listview_content gridContent">
                        <div>
                            <ul id="toolGrid" class="lui_profile_listview_card_page" style="float:none">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div id="toolMainTemp" style="display: none">
                <li data-lui-mark="temp-grid-li" class="lui_profile_block_grid_item grid_item_bg itemStyle_{%i%}" modeling-tool-name="{%toolName%}">
                    <div class="appMenu_item_block default">
                        <div class="appMenu_main">
                            <div class="appMenu_main_icon">
                                <i class="iconfont_nav {%icon%}"></i>
                            </div>
                        </div>
                        <div class="appMenu_title_box">
                            <div class="appMenu_title_name textEllipsis" title="{%name%}">{%name%}</div>
                            <p class="appMenu_title_desc textEllipsis" title="{%desc%}">{%desc%}</p>
                        </div>
                    </div>
                </li>
            </div>
        </div>
        <script type="text/javascript">
            var __TOOLS_LIST=[
                {id:"cfgUpgrade",name:"${lfn:message('sys-modeling-base:modeling.configuration.item.upgrade')}",desc:"${lfn:message('sys-modeling-base:modeling.configuration.item.upgrade')}",icon:"lui_iconfont_nav_sys_number"},
                {id:"historyFix",name:"${lfn:message('sys-modeling-base:modeling.historical.data.repair')}",desc:"${lfn:message('sys-modeling-base:modeling.historical.data.repair')}",icon:"lui_iconfont_nav_mechanism_langtools"},
                {id:"multiAddrMigration",name:"${lfn:message('sys-modeling-base:modeling.multi-select.address.tool')}",desc:"${lfn:message('sys-modeling-base:modeling.centrally.migrate.intermediate.tables')}",icon:"lui_iconfont_nav_sys_relation"},
                {id:"userAssignUpgrade",name:"${lfn:message('sys-modeling-base:modeling.user.assign.upgrade')}",desc:"${lfn:message('sys-modeling-base:modeling.user.assign.upgrade.desc')}",icon:"lui_iconfont_nav_sys_recycle"},
                {id:"dataBaseCheckTask",name:"${lfn:message('sys-modeling-base:modeling.database.check.task')}",desc:"${lfn:message('sys-modeling-base:modeling.database.check.task.desc')}",icon:"lui_iconfont_nav_km_doc"}

                ]
        </script>
        <script type="text/javascript">
            seajs.use(["lui/jquery", "lui/dialog", "sys/modeling/base/maintenance/resource/tools"], function ($, dialog, t) {
                function init() {
                    var cfg = {
                        toolMain: $("#toolMain"),
                        toolGrid: $("#toolGrid"),
                        toolMainTemp: $("#toolMainTemp"),
                        source:__TOOLS_LIST

                    };
                    window.t = new t.Tools(cfg);
                }

                init();
            });
        </script>
    </template:replace>
</template:include>
