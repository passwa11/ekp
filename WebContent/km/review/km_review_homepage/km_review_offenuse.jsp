<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 常用流程 -->
<div class="lui_review_cateTemplate_list">
    <c:if test="${offenUseList['offenUseProcess'] != null && offenUseList['offenUseProcess'].size() > 0}">
        <ul>
            <c:forEach items="${offenUseList['offenUseProcess']}" var="KmReviewOffen" varStatus="vstatus">
                <li>
                    <a data-href="${LUI_ContextPath}${KmReviewOffen.url}" onclick="Com_OpenNewWindow(this)" target="_blank" > <%--修改自动页面关闭--%>
                        <c:if test="${KmReviewOffen.icon == null || KmReviewOffen.icon=='lui_icon_l_icon_1' || KmReviewOffen.icon==''}">
                            <i><img src="${LUI_ContextPath}/km/review/img/icon_office.png"></i>
                        </c:if>
                        <c:if test="${KmReviewOffen.icon != null && KmReviewOffen.icon != ''}">
                            <c:if test="${fn:containsIgnoreCase(KmReviewOffen.icon, LUI_ContextPath)}">
                                <i><img src="${KmReviewOffen.icon}"></i>
                            </c:if>
                            <c:if test="${!fn:containsIgnoreCase(KmReviewOffen.icon, LUI_ContextPath)}">
                                <i><img src="${LUI_ContextPath}${KmReviewOffen.icon}"></i>
                            </c:if>
                        </c:if>
                        <div class="lui_review_cate_item_content">
                            <p title="<c:out value='${KmReviewOffen.text}'/>" class="lui_review_cateTemplate_name"><c:out value='${KmReviewOffen.text}'/></p>
                            <p title="<c:out value='${KmReviewOffen.cateName}'/>" class="lui_review_cateTemplate_cate"><c:out value='${KmReviewOffen.cateName}'/></p>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<c:if test="${offenUseList['offenUseProcess'] == null || offenUseList['offenUseProcess'].size() <= 0}">
    <%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
