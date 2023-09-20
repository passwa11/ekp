<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 流程统计 -->
<div class="lui_review_statist_data" >
    <div class="lui_review_statist_panel">
        <ul>
            <li>
                <h3><i class="lui_review_statist_icon lui_review_statist_icon_todo"></i><span>${ lfn:message('km-review:kmReview.nav.approval.my') }</span></h3>
                <p><span class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.week') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listApproval','approval','arrivalTime','week')" target="_blank">${homeInfo.review.weekCount}</a></span><span
                        class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listApproval','approval','arrivalTime','month')" target="_blank">${homeInfo.review.monthCount}</a></span></p>
            </li>
            <li>
                <h3><i class="lui_review_statist_icon lui_review_statist_icon_done"></i><span>${ lfn:message('km-review:kmReview.nav.approved.my') }</span></h3>
                <p><span class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listApproved','approved','docCreateTime','month')" target="_blank">${homeInfo.reviewedInfo.monthCount}</a></span><span
                        class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.year') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listApproved','approved','docCreateTime','year')" target="_blank">${homeInfo.reviewedInfo.yearCount}</a></span></p>
            </li>
            <li>
                <h3><i class="lui_review_statist_icon lui_review_statist_icon_launch"></i><span>${ lfn:message('km-review:kmReview.nav.create.my') }</span></h3>
                <p><span class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listCreate','create','docCreateTime','month')" target="_blank">${homeInfo.draft.monthCount}</a></span><span
                        class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.year') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listCreate','create','docCreateTime','year')" target="_blank">${homeInfo.draft.yearCount}</a></span></p>
            </li>
            <li>
                <h3><i class="lui_review_statist_icon lui_review_statist_icon_lib"></i><span>${ lfn:message('km-review:kmReview.nav.all') }</span></h3>
                <p><span class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.month') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listAll','all','docCreateTime','month')" target="_blank">${homeInfo.all.monthCount}</a></span><span
                        class="lui_review_statist_item">${ lfn:message('km-review:kmReview.nav.year') }<a onclick="bindTimeTogether('${LUI_ContextPath}','listAll','all','docCreateTime','year')" target="_blank">${homeInfo.all.yearCount}</a></span></p>
            </li>
        </ul>
    </div>
</div>
