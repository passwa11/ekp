<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
        <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/table.css"/>
    </template:replace>
    <template:replace name="body">
        <div class="lui_review_card_info">
            <c:import url="/km/review/km_review_index/kmReviewIndex.do?method=${requestScope['processType']}&rowsize=${requestScope['rowsize']}"></c:import>
        </div>
        <script type="text/javascript">
            seajs.use(['lui/jquery','lui/topic'], function($ , topic) {
                //审批等操作完成后，自动刷新列表
                topic.subscribe('successReloadPage', function() {
                    window.location.reload();
                });
            });
        </script>
    </template:replace>
</template:include>
