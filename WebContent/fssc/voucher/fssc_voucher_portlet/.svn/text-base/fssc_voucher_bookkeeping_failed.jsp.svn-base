<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${lfn:message('fssc-voucher:fssc.voucher.bookkeeping.failed')}</title>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<div class="lui_fssc_financial_card_portlet_container">
	<div class="lui_fssc_financial_card">
		<div class="lui_fssc_financial_card_items_header">
			<i class="lui_fssc_financial_card_icons financial_primary"> <span
					class="lui_fssc_financial_card_icon"></span>
			</i> <span class="lui_fssc_financial_card_title">${lfn:message('fssc-voucher:fssc.voucher.bookkeeping.failed')}</span>
					<span class="lui_fssc_financial_card_detail">
						<div class="lui_fssc_financial_card_detail_wrapper">
							<i class="lui_fssc_financial_card_detail_icon"> </i>
							<div class="lui_fssc_financial_card_tips">
								<span>${lfn:message('fssc-voucher:fssc.voucher.bookkeeping.failed.tip')}</span>
							</div>
						</div>
					</span>
		</div>
		<div class="lui_fssc_financial_card_content">
			<span id="fail"></span>
		</div>
		<div id="failedChart" style="width: 170px; height: 56px;"></div>
		<div class="lui_fssc_financial_card_pending">
			<span id="bottomTitle">${lfn:message('fssc-voucher:fssc.voucher.bookkeepingByYear.failed')}</span>
			<span id="failed_year"></span>
		</div>
	</div>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/voucher/fssc_voucher_portlet/fsscVoucherPortlet.do?method=bookkeepingFailed',
			async : false,
			success : function(rtn) {
				if(rtn != null  && rtn != ""){
					var json = JSON.parse(rtn);
					$("#failed_year").html(json.count);
					$("#fail").html(json.fail);
					var values = json.values;
					if(null != values){
						var myChart = echarts.init(document.getElementById('failedChart'));
						var option = {
							tooltip: {
								trigger: 'axis',
						        formatter:'{b}:{c}单'
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
