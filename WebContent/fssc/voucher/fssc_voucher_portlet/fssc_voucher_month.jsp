<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${lfn:message('fssc-voucher:portlet.approve.view.title')}</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
		<div class="lui_fssc_financial_card_items_header">
			<i class="lui_fssc_financial_card_icons financial_primary"> <span
					class="lui_fssc_financial_card_icon"></span>
			</i>
			<span class="lui_fssc_financial_card_title">${lfn:message('fssc-voucher:portlet.approve.view.titleTow')}</span> <span
				class="lui_fssc_financial_card_detail">
						<div class="lui_fssc_financial_card_detail_wrapper">
							<i class="lui_fssc_financial_card_detail_icon"> </i>
							<div class="lui_fssc_financial_card_tips">
								<span>${lfn:message('fssc-voucher:portlet.approve.view.title')}</span>
							</div>
						</div>
					</span>
		</div>
		<div class="lui_fssc_financial_card_content">
			<span id="count"></span>
		</div>
		<div class="lui_fssc_financial_card_items_process" id="month_up_down">
			<span id="year"></span><i></i><span id="yearCount"></span>
		</div>
		<div class="lui_fssc_financial_card_pending">
			<span>${lfn:message('fssc-voucher:fssc.voucher.month.bookkeeping')}</span>
			<span id="month"></span>
		</div>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/voucher/fssc_voucher_portlet/fsscVoucherPortlet.do?method=countStatus',
				async : false,
				success : function(rtn) {
					if(rtn != null  && rtn != ""){
						var obj = JSON.parse(rtn);
						//当前年份待记账数量
						var curCount = obj["curCount"];
						//上一年待记账数量
						var lastCount = obj["lastCount"];
						//当月待记账数量
						var monthCount = obj["monthCount"];
						$("#count").html(curCount);
						var myDate = new Date();
						var year = myDate.getFullYear() - 1;
						var count = 0;
						if(lastCount > curCount){//下降
							count = lastCount/curCount.toFixed(2);
							$("#year").html("年同比" + year + "年少");
							$("#month_up_down").addClass("process_down");
						}else if(lastCount < curCount){//上涨
							if(lastCount != 0){
								count = curCount/lastCount.toFixed(2);
							}else{
								count = curCount;
							}
							$("#year").html("年同比" + year + "年多");
							$("#month_up_down").addClass("process_up");
						}else{
							$("#year").html("年同比" + year + "年持平");
						}
						$("#yearCount").html(count+"%");
						$("#month").html(monthCount);
					}
				}
			});
		});
	</script>
</body>
