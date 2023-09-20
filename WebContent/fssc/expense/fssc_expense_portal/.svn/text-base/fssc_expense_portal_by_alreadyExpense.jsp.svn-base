<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>已报销总额</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
			<div class="lui_fssc_financial_card_items_header">
				<i class="lui_fssc_financial_card_icons financial_danger"> <span
					class="lui_fssc_financial_card_icon"></span>
				</i> <span class="lui_fssc_financial_card_title">已报销总额</span> <span
					class="lui_fssc_financial_card_detail">
					<div class="lui_fssc_financial_card_detail_wrapper">
						<i class="lui_fssc_financial_card_detail_icon"> </i>
						<div class="lui_fssc_financial_card_tips">
							<span>已报销总额</span>
						</div>
					</div>
				</span>
			</div>
			<div style="font-size: 25px;height: 42px;" id="alreadyAmount"></div>
			<div class="lui_fssc_financial_card_items_process" id="alread_up_down">
				<span id="alread_last_year"></span><i></i><span id="alread_amount"></span>
			</div>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=alreadyExpense',
			async : false,
			success : function(rtn) {
				if(rtn != null  && rtn != ""){
					var json = JSON.parse(rtn);
					//已报销金额
					var fdApprovedApplyMoney = json.fdApprovedApplyMoney.toFixed(2);
					if(null != fdApprovedApplyMoney){
						var str=fdApprovedApplyMoney.split(".");
						if(fdApprovedApplyMoney > 0){
							if(str[0].length >=5 && str[0].length <= 8){
								$("#alreadyAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>万</span>");
							}else if(str[0].length >= 9){
								$("#alreadyAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>亿</span>");
							}else{
								$("#alreadyAmount").html("<span>"+"￥" +fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,') +"</span>");
							}
						}else{
							if(str[0].indexOf("-") == -1){
								if (str[0].length >= 5 && str[0].length <= 8) {
									$("#alreadyAmount").html("<span>" + "￥" + (fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>万</span>");
								} else if (str[0].length >= 9) {
									$("#alreadyAmount").html("<span>" + "￥" + (fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>亿</span>");
								} else {
									$("#alreadyAmount").html("<span>" + "￥" + fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>");
								}
							}else {
								var money = str[0].split("-");
								if (money[1].length >= 5 && money[1].length <= 8) {
									$("#alreadyAmount").html("<span>" + "￥" + (fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>万</span>");
								} else if (money[1].length >= 9) {
									$("#alreadyAmount").html("<span>" + "￥" + (fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>&nbsp<span style='font-size:15px;'>亿</span>");
								} else {
									$("#alreadyAmount").html("<span>" + "￥" + fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,') + "</span>");
								}
							}
						}
					}
					//今年报销金额
					var thisYearMoney = json.thisYearMoney;
					//去年报销金额
					var lastYearMoney = json.lastYearMoney;
					var money = 0;
					var myDate = new Date();
					var year = myDate.getFullYear() - 1;
					if(lastYearMoney > thisYearMoney){//下降
						// （今年实付金额-去年实付金额）/去年实付金额*100%
						money = ((thisYearMoney - lastYearMoney) / lastYearMoney * 100).toFixed(2);
						$("#alread_last_year").html("同比" + year + "年少");
						$("#alread_up_down").addClass("process_down");
						var str=money.split(".");
						if(money > 0){
							if(str[0].length >=5 && str[0].length <= 8){
								$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
							}else if(str[0].length >= 9){
								$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
							}else{
								$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
							}
						}else{
							if(str[0].indexOf("-") == -1){
								if(str[0].length >=5 && str[0].length <= 8){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
								}else if(str[0].length >= 9){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
								}else{
									$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
								}
							}else {
								var stValue = str[0].split("-");
								if(stValue[1].length >= 5 && stValue[1].length <= 8){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
								}else if(stValue[1].length >= 9){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
								}else{
									$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
								}
							}
						}
					}else if(lastYearMoney < thisYearMoney){//上涨
						if(0 != lastYearMoney){
							money = ((thisYearMoney - lastYearMoney) / lastYearMoney * 100).toFixed(2);
						}else{
							money = thisYearMoney.toFixed(2);
						}
						$("#alread_last_year").html("同比" + year + "年多");
						$("#alread_up_down").addClass("process_up");
						if(money > 0){
							var str=money.split(".");
							if(str[0].length >=5 && str[0].length <= 8){
								$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
							}else if(str[0].length >= 9){
								$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
							}else{
								$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
							}
						}else{
							if(str[0].indexOf("-") == -1){
								if(str[0].length >=5 && str[0].length <= 8){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
								}else if(str[0].length >= 9){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
								}else{
									$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
								}
							}else {
								var stValue = str[0].split("-");
								if(stValue[1].length >= 5 && stValue[1].length <= 8){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>万</span>");
								}else if(stValue[1].length >= 9){
									$("#alread_amount").html("￥" +(money / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"&nbsp<span style='font-size:12px;'>亿</span>");
								}else{
									$("#alread_amount").html("￥" +money.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,'));
								}
							}
						}
					}else{
						$("#alread_last_year").html("同比" + year + "年持平");
					}
				}
			}
		});
	});
	</script>
</body>
