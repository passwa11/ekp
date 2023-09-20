<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/common.css"/>
<link type="text/css" rel="stylesheet" href="../../../km/review/resource/style/doc/table.css"/>
<div class="lui_review_card_info" style="position: relative">
    <a id="moreDetails" class="lui_review_more_href" href="" target="_blank">${ lfn:message('km-review:kmReview.nav.more') }</a>
    <ui:tabpanel id="tabInfo">
        <ui:content title="${ lfn:message('km-review:kmReview.nav.approval.my') }">
            <img id="img1" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div class="lui_review_card_list"></div>
        </ui:content>
        <ui:content title="${ lfn:message('km-review:kmReview.nav.approved.my') }">
            <img id="img2" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div class="lui_review_card_list"></div>
        </ui:content>
        <ui:content title="${ lfn:message('km-review:kmReview.nav.create.my') }">
            <img id="img3" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div class="lui_review_card_list"></div>
        </ui:content>
        <ui:content title="${ lfn:message('km-review:kmReview.nav.follow.my') }">
            <img id="img4" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div class="lui_review_card_list"></div>
        </ui:content>
    </ui:tabpanel>
</div>