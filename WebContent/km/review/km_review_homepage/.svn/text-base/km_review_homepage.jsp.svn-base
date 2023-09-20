<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<template:include ref="default.simple">
    <template:replace name="head">
        <!-- 系统重置样式-此次不需要引用 -->
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
        <!-- 工作台样式 -->
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/review.css"/>
    </template:replace>
    <template:replace name="body">
        <!-- 流程统计 -->
        <%@ include file="/km/review/km_review_homepage/km_review_count.jsp"%>
        <!-- 流程报表 -->
        <%@ include file="/km/review/km_review_homepage/km_review_echarts.jsp"%>
        <!-- 流程分类 -->
        <%@ include file="/km/review/km_review_homepage/km_review_usual.jsp"%>
        <!-- 流程列表 -->
        <%@ include file="/km/review/km_review_homepage/km_review_detaillist.jsp"%>
    </template:replace>
    <script type="text/javascript">
        Com_IncludeFile("homeInfo.js","${LUI_ContextPath}/km/review/km_review_homepage/js/","js",true);
    </script>
</template:include>
