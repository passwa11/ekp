<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="lui_review_cateTemplate_info" style="position: relative">
    <template:include ref="default.simple">
        <template:replace name="head">
            <!-- 系统重置样式-此次不需要引用 -->
            <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
            <!-- 工作台样式 -->
            <link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/review.css"/>
        </template:replace>
        <template:replace name="body">
            <%--如果在部件中配置的是“常用流程”--%>
            <c:if test="${commonRecently['oftenRecentFlow'] != null && commonRecently['oftenRecentFlow'] == 'offen'}">
                <c:import
                        url="/km/review/km_review_index/kmReviewIndex.do?method=offenUseList&rowSize=${commonRecently['rowSize']}"></c:import>
            </c:if>
            <%--如果在部件中配置的是“最近使用”--%>
            <c:if test="${commonRecently['oftenRecentFlow'] != null && commonRecently['oftenRecentFlow'] == 'recent'}">
                <c:import
                        url="/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsually&rowSize=${commonRecently['rowSize']}"></c:import>
            </c:if>
        </template:replace>
    </template:include>
</div>