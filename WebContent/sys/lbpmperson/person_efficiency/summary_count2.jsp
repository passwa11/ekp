<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script type="text/javascript">
    Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
    function loadSummaryCount() {
        $.ajax({
            type:"post",
            url:"${LUI_ContextPath}/sys/lbpmperson/SysLbpmPersonSummary.do?method=listAllCountSummary",
            async:true,
            dataType : 'json',
            success:function(data){
                $("#summary_draft").html(data.draftCount);
                $("#summary_create").html(data.createCount);
                $("#summary_approval").html(data.approvalCount);
                $("#summary_approved").html(data.approvedCount);
                $("#summary_abandon").html(data.abandonCount);

            }
        });
    }

    //流程发起页统计数据点击跳转对应路由
    function clickHref(id){
        window.parent.setPersonMainIframe(id);
    }
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/lbpmperson/style/css/docreater.css"/>

<style type="text/css">

    .head_div_wrap {
        padding: 0px 16px;
    }

    .head_div_box {
		width: 20%;
		/* height: 70px; */
		/* background-color: rgba(66, 133, 244, 0.05); */
		border-radius: 2px;
		float: left;
		/* margin-right: 8px; */
		cursor: pointer;
		padding-right: 8px;
		box-sizing: border-box;
    }

	.head_div_box > .item{
		border-radius: 4px;
		height: 70px;
		background-color: rgba(66, 133, 244, 0.05);
		padding-top: 12px;
		box-sizing: border-box;
	}

    .head_div_box_text_icon {
        /*margin-top: 10px;*/
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
    <div class="head_div_box" onclick="clickHref('/draft');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.draft')}</div>
			</span>
            <span class="head_div_box_number" id="summary_draft">0</span>
        </div>
    </div>
    <div class="head_div_box" onclick="clickHref('/create');">
        <div class="item">
			<span class="head_div_box_text_icon">
			    <div class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.create')}</div>
			</span>
            <span class="head_div_box_number" id="summary_create">0</span>
        </div>
    </div>
    <div class="head_div_box" onclick="clickHref('/approve');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.approval')}</div>
			</span>
            <span class="head_div_box_number" id="summary_approval">0</span>
        </div>
    </div>
    <div class="head_div_box" onclick="clickHref('/approved');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.approved')}</div>
			</span>
            <span class="head_div_box_number" id="summary_approved">0</span>
        </div>
    </div>
    <div class="head_div_box" onclick="clickHref('/abandon');">
        <div class="item">
			<span class="head_div_box_text_icon">
				<div class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.abandon')}</div>
			</span>
            <span class="head_div_box_number" id="summary_abandon">0</span>
        </div>
    </div>
</div>

<script>
    Com_AddEventListener(window, 'load', function () {
        loadSummaryCount();
    });
</script>