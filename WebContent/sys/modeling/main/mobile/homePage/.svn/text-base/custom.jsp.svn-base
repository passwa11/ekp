<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="mobile.simple" compatibleMode="true">
    <template:replace name="title">
        ${appName }
    </template:replace>
    <template:replace name="head">
        <mui:cache-file name="mui-portal.css" cacheType="md5" />
        <link rel="stylesheet" type="text/css"
              href="${KMSS_Parameter_ContextPath}sys/mobile/css/themes/default/font-mui.css?s_cache=${MUI_Cache}"></link>
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/index.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/reset.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/dabFont/dabFont.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/cardList.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/base/resources/css/boardMobile.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/custom.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/mportal.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/mportalList.css?s_cache=${MUI_Cache}">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/list.css?s_cache=${MUI_Cache}">
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
    </template:replace>
    <template:replace name="content">
        <div class="customPageIndex" id="scrollView" data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/customPageView"
             data-dojo-props="fdAppId:'${param.fdId}',fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'">
        </div>
    </template:replace>
</template:include>