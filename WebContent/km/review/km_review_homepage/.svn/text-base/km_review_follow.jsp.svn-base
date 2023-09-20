<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="lui_review_card_list">
    <c:if test="${followProcessList['followInfo'] != null && followProcessList['followInfo'].size() > 0}">
        <c:forEach items="${followProcessList['followInfo']}" var="KmReviewInfo" varStatus="vstatus">
            <div class="lui_review_card_list_inner">
                <div class="lui_review_card_list_head clearfloat">
                    <a class="lui_review_card_list_head" data-href="${LUI_ContextPath}${KmReviewInfo.url}" target="_blank" onclick="Com_OpenNewWindow(this)" style="display: block">
                        <div class="lui_review_card_list_headL">
                            <c:if test="${KmReviewInfo.docStatus=='10'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.draft') }</span>
                            </c:if>
                            <c:if test="${KmReviewInfo.docStatus=='20'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.review') }</span>
                            </c:if>
                            <c:if test="${KmReviewInfo.docStatus=='30'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.published') }</span>
                            </c:if>
                            <c:if test="${KmReviewInfo.docStatus=='11'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.refused') }</span>
                            </c:if>
                            <c:if test="${KmReviewInfo.docStatus=='31'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.feedback') }</span>
                            </c:if>
                            <c:if test="${KmReviewInfo.docStatus=='00'}">
                                <span class="lui_review_list_title">${ lfn:message('km-review:status.discard') }</span>
                            </c:if>
                            <span class="lui_review_doc_title" title="<c:out value='${KmReviewInfo.docSubject}'/>"><c:out value='${KmReviewInfo.docSubject}'/></span>
                        </div>
                        <div class="lui_review_card_list_headR">
                            <!-- 申请人 -->
                            <div class="lui_review_card_list_headR_proposer">
                                <span class=" lui_review_card_list_headR_proposer_item">${ lfn:message('km-review:kmReviewMain.docCreator') }</span>
                                <span class="lui_review_card_list_desc lui_review_card_proposer_name">${KmReviewInfo.docCreatorName}</span>
                            </div>
                            <!-- 申请类别 -->
                            <div class="lui_review_card_list_headR_application_category">
                                <span class="lui_review_card_list_headR_application_category_item">${ lfn:message('km-review:kmReviewMain.docCategory') }</span>
                                <span title="<c:out value='${KmReviewInfo.tempName}'/>" class="lui_review_card_list_desc lui_review_card_application_desc"><c:out value='${KmReviewInfo.tempName}'/></span>

                            </div>
                        </div>
                    </a>
                </div>
                <div class="lui_review_card_list_content clearfloat">
                        <%--<div class="lui_review_card_list_content_right">
                            <div class="lui_review_card_list_operation">
                                <a href="" class="lui_review_card_list_content_permission">通过</a>
                                <a href="" class="lui_review_card_list_content_reject">驳回</a>
                            </div>
                        </div>--%>
                    <div class="lui_review_card_list_content_left">
                        <table>
                            <tr>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:kmReviewMain.fdNum') }</i>
                                        <span class="lui_review_card_list_content_detail"><c:out value="${KmReviewInfo.fdNumber}"/></span>
                                    </p>
                                </td>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:sysWfNode.processingNode.current') }</i>
                                        <span class="lui_review_card_list_content_detail"><c:out value="${KmReviewInfo.nodeName}"/></span>
                                    </p>
                                </td>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:kmReviewMain.portlet.currResolve') }</i>
                                        <span title="${KmReviewInfo.reviewPerson}" class="lui_review_card_list_content_detail"><c:out value="${KmReviewInfo.reviewPerson}"/></span>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:kmReviewMain.createTime') }</i>
                                        <span class="lui_review_card_list_content_detail">${KmReviewInfo.docCreateTime}</span>
                                    </p>
                                </td>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:kmReviewMain.docfinishTime') }</i>
                                        <span class="lui_review_card_list_content_detail">${KmReviewInfo.docPublishTime}</span>
                                    </p>
                                </td>
                                <td>
                                    <p>
                                        <i class="lui_review_card_list_content_item">${ lfn:message('km-review:kmReviewMain.status') }</i>
                                        <c:if test="${KmReviewInfo.docStatus=='20'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:kmReviewMain.processStatus.reviewing') }</span>
                                        </c:if>
                                        <c:if test="${KmReviewInfo.docStatus=='10'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:status.draft') }</span>
                                        </c:if>
                                        <c:if test="${KmReviewInfo.docStatus=='30'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:status.published') }</span>
                                        </c:if>
                                        <c:if test="${KmReviewInfo.docStatus=='11'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:status.refused') }</span>
                                        </c:if>
                                        <c:if test="${KmReviewInfo.docStatus=='31'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:status.feedback') }</span>
                                        </c:if>
                                        <c:if test="${KmReviewInfo.docStatus=='00'}">
                                            <span class="lui_review_card_list_content_detail">${ lfn:message('km-review:status.discard') }</span>
                                        </c:if>
                                    </p>
                                </td>
                            </tr>
                            <c:if test="${KmReviewInfo.summaryInfo !=''}">
                                <c:forEach items="${KmReviewInfo.summaryInfo}" var="KmReviewSummary" varStatus="vstatus">
                                    <tr>
                                        <c:forEach items="${KmReviewSummary}" var="KmReviewInfo" varStatus="vstatus">
                                            <td>
                                                <p>
                                                    <i class="lui_review_card_list_content_item"><c:out value='${KmReviewInfo.label}'/></i>
                                                    <%--value值在后台已经转义过，此处不转义--%>
                                                    <span title="${KmReviewInfo.value}" class="lui_review_card_list_content_detail">${KmReviewInfo.value}</span>
                                                </p>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>

                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${followProcessList['followInfo'] == null || followProcessList['followInfo'].size() <= 0}">
        <%@ include file="/resource/jsp/list_norecord.jsp"%>
    </c:if>
</div>