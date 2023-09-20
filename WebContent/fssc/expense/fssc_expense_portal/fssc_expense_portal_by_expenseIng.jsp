<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>报销中费用</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
			<div class="lui_fssc_financial_card_items_header">
				<i class="lui_fssc_financial_card_icons financial_primary"> <span
					class="lui_fssc_financial_card_icon"></span>
				</i> <span class="lui_fssc_financial_card_title">报销中费用</span> <span
					class="lui_fssc_financial_card_detail">
					<div class="lui_fssc_financial_card_detail_wrapper">
						<i class="lui_fssc_financial_card_detail_icon"> </i>
						<div class="lui_fssc_financial_card_tips">
							<span>报销中费用</span>
						</div>
					</div>
				</span>
			</div>
			<div style="font-size: 25px;height: 42px;" id="expenseAmount"></div>
			<div id="expenseChart" style="width: 170px; height: 56px;"></div>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=expsenseIng',
			async : false,
			success : function(rtn) {
				if(rtn != null  && rtn != ""){
					var json = JSON.parse(rtn);
					var fdApprovedApplyMoney=json.fdApprovedApplyMoney.toFixed(2);
					var str=fdApprovedApplyMoney.split(".");
					if(fdApprovedApplyMoney > 0){
						if(str[0].length >=5 && str[0].length <= 8){
							$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>万</span>");
						}else if(str[0].toString().length >= 9){
							$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>亿</span>");
						}else{
							$("#expenseAmount").html("<span>"+"￥" +fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,') +"</span>");
						}
					}else{
						if(str[0].indexOf("-") == -1){
							if(str[1].length >= 5 && str[1].toString().length <= 8){
								$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>万</span>");
							}else if(str[1].length >= 9){
								$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>亿</span>");
							}else{
								$("#expenseAmount").html("<span>"+"￥" +fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,') +"</span>");
							}
						}else {
							var money = str[0].split("-");
							if(money[1].length >= 5 && money[1].toString().length <= 8){
								$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>万</span>");
							}else if(money[1].length >= 9){
								$("#expenseAmount").html("<span>"+"￥" +(fdApprovedApplyMoney / 10000).toFixed(2).replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,')+"</span>&nbsp<span style='font-size:15px;'>亿</span>");
							}else{
								$("#expenseAmount").html("<span>"+"￥" +fdApprovedApplyMoney.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,') +"</span>");
							}
						}
					}
					
					var values = json.expenseIng.values;
					if(null != values){
						var myChart = echarts.init(document.getElementById('expenseChart'));
						var option = {
							tooltip: {
								trigger: 'axis',
						        formatter:'{b}:{c}元'
							},
						    xAxis: {
						    	show:false,
				                data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
						    },
						    yAxis: {
						        show:false
						    },
						    series: [{
						        data: [values["one"], values["two"], values["three"], values["four"], values["five"], values["six"], values["seven"],values["eight"],values["nine"],values["ten"],values["eleven"],values["twelve"]],
						        type: 'bar',
						        itemStyle: {
					                color: '#50C45E',
					                borderRadius:[5,5,0,0],
					            },
						    }]
						};
						myChart.setOption(option);
					}
				}
			}
		});
	});
	</script>
</body>
</html>
