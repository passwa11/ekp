<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>未清借款</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
		<div class="lui_fssc_financial_card_items_header">
			<i class="lui_fssc_financial_card_icons financial_danger">
				<span class="lui_fssc_financial_card_icon"></span>
			</i>
			<span class="lui_fssc_financial_card_title">未清借款</span>
			<span class="lui_fssc_financial_card_detail">
				<div class="lui_fssc_financial_card_detail_wrapper">
					<i class="lui_fssc_financial_card_detail_icon"> </i>
					<div class="lui_fssc_financial_card_tips">
						<span>未清借款</span>
					</div>
				</div>
			</span>
		</div>
		<div id="loan_amount" style="font-size: 25px;height: 42px;"></div>
		<div class="lui_fssc_financial_card_chart">
			<div class="lui_fssc_financial_card_progress_contaner">
				<div class="lui_fssc_financial_card_items_container">
					<div id="pay"></div>
					<div id="expire"></div>
					<div id="not_expire"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/loan/fssc_loan_main/fsscLoanMainPortlet.do?method=loanAmount&type=ower',
				async : false,
				success : function(rtn) {
					rtn = JSON.parse(rtn);
					if (null != rtn) {
						var fdTotalRepaymentMoney = rtn.fdTotalRepaymentMoney.toFixed(2);//已还借款
						var fdTotalNotRepaymentMoney = rtn.fdTotalNotRepaymentMoney.toFixed(2);//未逾期未还借款
						var fdTotalExpireNotPayMoney = rtn.fdTotalExpireNotPayMoney.toFixed(2);//逾期未还借款
						var fdTotalLoanMoney = rtn.fdTotalLoanMoney.toFixed(2);//总借款金额

						//四舍五入，保留2位小数
						if(fdTotalNotRepaymentMoney > 0){
							var notRepay=fdTotalNotRepaymentMoney.split(".");
							if(notRepay[0].length >=5 && notRepay[0].length <= 8){
								$("#loan_amount").html("<span>"+"￥" +(fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>万</span>");
							}else if(notRepay[0].length >= 9){
								$("#loan_amount").html("<span>"+"￥" +(fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>亿</span>");
							}else{
								$("#loan_amount").html("<span>"+"￥" +fdTotalNotRepaymentMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,') +"</span>");
							}
						}else{
							var notRepay=fdTotalNotRepaymentMoney.split(".");
							if(notRepay[0].indexOf("-") == -1){
								if (notRepay[0].length >= 5 && notRepay[0].length <= 8) {
									$("#loan_amount").html("<span>" + "￥" + (fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>万</span>");
								} else if (notRepay[0].length >= 9) {
									$("#loan_amount").html("<span>" + "￥" + (fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>亿</span>");
								} else {
									$("#loan_amount").html("<span>" + "￥" + fdTotalNotRepaymentMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>");
								}
							}else {
								var notRepay=fdTotalNotRepaymentMoney.split(".");
								var notMoney = notRepay[0].split("-");
								if (notMoney[1].length >= 5 && notMoney[1].length <= 8) {
									$("#loan_amount").html("<span>" + "￥" + (fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>万</span>");
								} else if (notMoney[1].length >= 9) {
									$("#loan_amount").html("<span>" + "￥" + (fdTotalNotRepaymentMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>亿</span>");
								} else {
									$("#loan_amount").html("<span>" + "￥" + fdTotalNotRepaymentMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>");
								}
							}
						}
						if(fdTotalRepaymentMoney != 0 && fdTotalLoanMoney != 0){
							$("#pay").addClass("lui_fssc_financial_card_items_processing2");
							$("#pay").css("width",(fdTotalRepaymentMoney/fdTotalLoanMoney).toFixed(2)+"%");
						}else{
							$("#pay").css("width","0%");
						}
						if(fdTotalExpireNotPayMoney != 0  && fdTotalLoanMoney != 0){
							$("#expire").addClass("lui_fssc_financial_card_items_processing");
							$("#expire").css("width",(fdTotalExpireNotPayMoney/fdTotalLoanMoney).toFixed(2)+"%");
						}else{
							$("#expire").css("width","0%");
						}
						if(fdTotalNotRepaymentMoney != 0 && fdTotalLoanMoney != 0){
							$("#not_expire").addClass("lui_fssc_financial_card_items_processing1");
							$("#not_expire").css("width",(fdTotalNotRepaymentMoney/fdTotalLoanMoney).toFixed(2)+"%");
						}else{
							$("#not_expire").css("width","0%");
						}
					}
				}
			});
		});
	</script>
</body>
