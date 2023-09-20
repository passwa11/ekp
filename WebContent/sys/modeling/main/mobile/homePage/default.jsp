<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="mobile.simple" compatibleMode="true">
    <template:replace name="title">
        ${appName }
    </template:replace>
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/index.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/reset.css">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/dabFont/dabFont.css">
        <script src="<%=request.getContextPath()%>/sys/modeling/main/resources/js/mobile/rem.js"></script>
    </template:replace>
    <template:replace name="content">
        <div id="scrollView" data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/pageView"
             data-dojo-props="fdAppId:'${param.fdId}',fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'">
            <div class="mui_personnel_process_box">
                <div class="mui_personnel_process_content">
                    <div class="mui_personnel_process_head">
                        <!-- 问候语 -->
                        <div class="mui_pph_user">
                            <span>Hi，<%=UserUtil.getUser().getFdName() %></span>
                        </div>
                        <!-- 待办 -->
                        <div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/default/notify"></div>
                        <!-- 统计区 -->
                        <style>
                            .mui_pph_mm_item_introduce {
                                position: relative;
                                margin-top: 2px;
                                padding-left: 10px;
                                padding-right: 10px;
                                -webkit-line-clamp: 1;
                                overflow: hidden;
                                display: -webkit-box;
                                -webkit-box-orient: vertical;
                                white-space: normal;
                                max-width:65px;
                            }
                        </style>
                        <div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/default/statistics"
                             data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>
                        <!-- 功能区listview -->
                        <div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/default/listView"
                             data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>
                    </div>
                </div>
            </div>
        </div>
    </template:replace>
</template:include>