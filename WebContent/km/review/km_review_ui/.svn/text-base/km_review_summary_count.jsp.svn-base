<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script type="text/javascript">
    Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
    function loadSummaryCount() {
        $.ajax({
            type: "post",
            url: "${LUI_ContextPath}/km/review/km_review_index/kmReviewIndex.do?method=getListAllCountSummaryOfKmReview",
            async: true,
            dataType: 'json',
            success: function (data) {
                //全部
                $("#summary_all").html(data.draft);
                //我已审的
                $("#summary_reviewed").html(data.approved);
                //待我审的
                $("#summary_pending").html(data.approval);
                //驳回
                $("#summary_reject").html(data.reject);
                //结束
                $("#summary_end").html(data.end);
                //废弃
                $("#summary_abandoned").html(data.abandoned);
            }
        });
    }

    //流程发起页统计数据点击跳转对应路由
    function clickHref(id, routeStatus) {
        window.parent.setKmReviewMainIframe(id, routeStatus);
    }
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/lbpmperson/style/css/docreater.css"/>

<style type="text/css">

    .head_div_wrap {
        padding: 0px 16px;
    }

    .head_div_box {
        width: 15%;
        height: 70px;
        background-color: rgba(66, 133, 244, 0.05);
        border-radius: 2px;
        float: left;
        margin-right: 8px;
        cursor: pointer;
    }

    .head_div_box_text_icon {
        margin-top: 10px;
        display: block;
        height: 16px;
        clear: both;
    }

    .head_div_box_text {
        height: 16px;
        font-family: PingFangSC-Regular;
        font-size: 14px;
        color: #333333;
        line-height: 16px;
        font-weight: 400;
        float: left;
        margin-left: 10px;
    }

    .head_div_box_icon {
        width: 12px;
        height: 12px;
        float: right;
        margin-right: 10px;
    }

    .head_div_box_number {
        margin-top: 10px;
        display: block;
        height: 20px;
        font-family: DINAlternate-Bold;
        font-size: 20px;
        color: #333333;
        line-height: 20px;
        font-weight: 700;
        margin-left: 10px;
    }
</style>

<div class="head_div_wrap">
    <%--全部--%>
    <%--跳转至[我发起的]，筛选条件均为不限--%>
    <div class="head_div_box" onclick="clickHref('/listCreate',null);">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.all')}</div>
				<div class="head_div_box_icon">
					<div style="background:url(../img/km_review_summary_count_all.svg);height: 100%;width: 100%;"></div>
				</div>
			</span>
            <span class="head_div_box_number" id="summary_all">0</span>
        </div>
    </div>
    <%--我已审的--%>
    <%--跳转至[我已审的]不限页签--%>
    <div class="head_div_box" onclick="clickHref('/listApproved',null);">
        <div class="item">
			<span class="head_div_box_text_icon">
			    <div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.reviewed')}</div>
				<div class="head_div_box_icon">
					<div style="background:url(../img/km_review_summary_count_reviewed.svg);height: 100%;width: 100%;"></div>
				</div>
			</span>
            <span class="head_div_box_number" id="summary_reviewed">0</span>
        </div>
    </div>
    <%--待我审的--%>
    <%--跳转至[待我审的]页面--%>
    <div class="head_div_box" onclick="clickHref('/listApproval',null);">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.pending')}</div>
				<div class="head_div_box_icon">
					<div style="background:url(../img/km_review_summary_count_pending.svg);height: 100%;width: 100%;"></div>
				</div>
			</span>
            <span class="head_div_box_number" id="summary_pending">0</span>
        </div>
    </div>
    <%--驳回--%>
    <%--跳转至页面[我的发起]驳回页签--%>
    <div class="head_div_box" onclick="clickHref('/listCreate','reject');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.reject')}</div>
				<div class="head_div_box_icon">
					<div style="background:url(../img/km_review_summary_count_reject.svg);height: 100%;width: 100%;"></div>
				</div>
			</span>
            <span class="head_div_box_number" id="summary_reject">0</span>
        </div>
    </div>
    <%--结束--%>
    <%--跳转至页面[我的发起]结束页签--%>
    <div class="head_div_box" onclick="clickHref('/listCreate','end');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.end')}</div>
				<div class="head_div_box_icon">
                    <div style="background:url(../img/km_review_summary_count_end.svg);height: 100%;width: 100%;"></div>
                </div>
			</span>
            <span class="head_div_box_number" id="summary_end">0</span>
        </div>
    </div>
    <%--废弃--%>
    <%--跳转至页面[我的发起]废弃页签--%>
    <div class="head_div_box last-child" onclick="clickHref('/listCreate','abandoned');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('km-review:km.review.summary.count.abandoned')}</div>
				<div class="head_div_box_icon">
					<div style="background:url(../img/km_review_summary_count_abandoned.svg);height: 100%;width: 100%;"></div>
				</div>
			</span>
            <span class="head_div_box_number" id="summary_abandoned">0</span>
        </div>
    </div>
</div>

<script>
    Com_AddEventListener(window, 'load', function () {
        loadSummaryCount();
    });
</script>