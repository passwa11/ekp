<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 最近使用 -->
<div class="lui_review_cateTemplate_list">
    <c:if test="${listUsually['listRecent'] != null && listUsually['listRecent'].size() > 0}">
        <ul>
            <c:forEach items="${listUsually['listRecent']}" var="KmReviewUsual" varStatus="vstatus">
                <li><a data-href="${LUI_ContextPath}/${KmReviewUsual.addUrl}" onclick="Com_OpenNewWindow(this)" target="_blank"><%--修改自动页面关闭--%>
                    <c:if test="${KmReviewUsual.tempIcon == '' || KmReviewUsual.tempIcon == 'lui_icon_l_icon_1' || KmReviewUsual.tempIcon == null}">
                        <i><img src="${LUI_ContextPath}/km/review/img/icon_office.png"></i>
                    </c:if>
                    <c:if test="${KmReviewUsual.tempIcon != '' && KmReviewUsual.tempIcon != null}">
                        <c:if test="${fn:containsIgnoreCase(KmReviewUsual.tempIcon, LUI_ContextPath)}">
                            <i><img src="${KmReviewUsual.tempIcon}"></i>
                        </c:if>
                        <c:if test="${!fn:containsIgnoreCase(KmReviewUsual.tempIcon, LUI_ContextPath)}">
                            <i><img src="${LUI_ContextPath}${KmReviewUsual.tempIcon}"></i>
                        </c:if>
                    </c:if>
                    <div class="lui_review_cate_item_content">
                        <p title="${KmReviewUsual.templateName}" class="lui_review_cateTemplate_name">${KmReviewUsual.templateName}</p>
                        <p title="${KmReviewUsual.cateName}" class="lui_review_cateTemplate_cate">${KmReviewUsual.cateName}</p>
                    </div>
                </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<c:if test="${listUsually['listRecent'] == null || listUsually['listRecent'].size() <= 0}">
    <%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>