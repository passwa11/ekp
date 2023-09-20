<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 流程统计 -->
<template:include ref="default.simple">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/reviewCharts.css"/>
    </template:replace>
    <template:replace name="body">
        <div class="lui_review_statist_echarts" width="100%">
                <c:import url="/km/review/km_review_index/kmReviewIndex.do?method=${requestScope['summary']}"></c:import>
        </div>
    </template:replace>
</template:include>
<script type="text/javascript">
    domain.autoResize();
</script>
