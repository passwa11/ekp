<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
		<div class="lui_fssc_financial_card_items_header">
			<i class="lui_fssc_financial_card_icons financial_primary">
				<span class="lui_fssc_financial_card_icon"></span>
			</i>
			<span class="lui_fssc_financial_card_title">${lfn:message('fssc-voucher:fssc.voucher.bookkeeping.success')}</span>
			<span class="lui_fssc_financial_card_detail">
						<div class="lui_fssc_financial_card_detail_wrapper">
							<i class="lui_fssc_financial_card_detail_icon"> </i>
							<div class="lui_fssc_financial_card_tips">
								<span>${lfn:message('fssc-voucher:fssc.voucher.bookkeeping.success.tip')}</span>
							</div>
						</div>
					</span>
		</div>
		<div class="lui_fssc_financial_card_content">
			<span id="successPercent"></span>
		</div>
		<div id="voucherSuccessChart" style="width: 170px; height: 56px;"></div>
		<div class="lui_fssc_financial_card_pending">
			<span id="bottomTitle">${lfn:message('fssc-voucher:portlet.approve.booking.year.total')}</span>
			<span id="voucher_success_total"></span>
		</div>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/voucher/fssc_voucher_portlet/fsscVoucherPortlet.do?method=getBookingSuccessData',
			async : false,
			success : function(rtn) {
				if(rtn){
					var json = JSON.parse(rtn);
					$("#successPercent").html(json.percent+"%");
					$("#voucher_success_total").html(json.total);
					var values = json.monthData;
					if(values){
						var myChart = echarts.init(document.getElementById('voucherSuccessChart'));
						var x_data=[],y_data=[];
						for (var prop in values) {
							if (values.hasOwnProperty(prop)&&values[prop]>0) {
								x_data.push(prop);
								y_data.push(values[prop]);
							}
						}
						// 指定图表的配置项和数据
						var option = {
							grid: {
								top:'10%',
								bottom:'10%'
							},
							tooltip: {
								trigger: 'axis',
								formatter:'{b}月:{c}单'
							},
							color: ['#50C45E'],
							title: {
								text: ''
							},
							xAxis: {
								data: x_data,
								show:false
							},
							yAxis: {
								show:false
							},
							series: [{
								type: 'line',
								data: y_data
							}]
						};
						myChart.setOption(option);
					}
				}
			}
		});
	});
	</script>
<style>
	#bottomTitle{
		margin-right: 10px;
	}
	#bottomTitle{
		width: 96px;
		height: 12px;
		font-family: PingFangSC-Regular;
		font-size: 12px;
		color: rgba(0,0,0,0.65);
		line-height: 12px;
		margin-right:10px;
		font-weight: 400;
	}
</style>
</body>
