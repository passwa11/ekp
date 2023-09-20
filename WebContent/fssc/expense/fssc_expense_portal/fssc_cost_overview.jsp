<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>费用总览</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
<script type="text/javascript" src="${LUI_ContextPath}/fssc/expense/resource/js/jquery.js"></script>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_cost_total">
			<div class="lui_fssc_cost_total_detail">
				<div class="lui_fssc_financial_card" id="loan">
					<div class="lui_fssc_financial_card_items_header">
						<i class="lui_fssc_financial_card_icons financial_passing"> <span
							class="lui_fssc_financial_card_icon"></span>
						</i> <span class="lui_fssc_financial_card_title" id="loan_title"></span> <span
							class="lui_fssc_financial_card_detail">
							<div class="lui_fssc_financial_card_detail_wrapper">
								<i class="lui_fssc_financial_card_detail_icon"> </i>
<%--								<div class="lui_fssc_financial_card_tips">--%>
<%--									<span id="loan_tips"></span>--%>
<%--								</div>--%>
							</div>
						</span>
					</div>
					<div class="lui_fssc_financial_card_content">
						<span id="loan_amount"></span>
					</div>
					<div class="lui_fssc_financial_card_items_process" id="loan_up_down">
						<span id="loan_year"></span>
						<i></i>
						<span id="loan_money"></span>
					</div>
				</div>
				<div class="lui_fssc_financial_card" id="expense">
					<div class="lui_fssc_financial_card_items_header">
						<i class="lui_fssc_financial_card_icons financial_passing"> <span
							class="lui_fssc_financial_card_icon"></span>
						</i> <span class="lui_fssc_financial_card_title" id="expense_title"></span> <span
							class="lui_fssc_financial_card_detail">
							<div class="lui_fssc_financial_card_detail_wrapper">
								<i class="lui_fssc_financial_card_detail_icon"> </i>
<%--								<div class="lui_fssc_financial_card_tips">--%>
<%--									<span id="expense_tips"></span>--%>
<%--								</div>--%>
							</div>
						</span>
					</div>
					<div class="lui_fssc_financial_card_content">
						<span id="expense_amount"></span>
					</div>
					<div class="lui_fssc_financial_card_items_process" id="expense_up_down">
						<span id="expense_year"></span>
						<i></i>
						<span id="expense_money"></span>
					</div>
				</div>
				<div class="lui_fssc_financial_card" id="payment">
					<div class="lui_fssc_financial_card_items_header">
						<i class="lui_fssc_financial_card_icons financial_passing"> <span
							class="lui_fssc_financial_card_icon"></span>
						</i> <span class="lui_fssc_financial_card_title" id="pay_title"></span> <span
							class="lui_fssc_financial_card_detail">
							<div class="lui_fssc_financial_card_detail_wrapper">
								<i class="lui_fssc_financial_card_detail_icon"> </i>
<%--								<div class="lui_fssc_financial_card_tips">--%>
<%--									<span id="pay_tips"></span>--%>
<%--								</div>--%>
							</div>
						</span>
					</div>
					<div class="lui_fssc_financial_card_content">
						<span id="pay_amount"></span>
					</div>
					<div class="lui_fssc_financial_card_items_process" id="pay_up_down">
						<span id="pay_year"></span>
						<i></i>
						<span id="pay_money"></span>
					</div>
				</div>
			</div>
			<div class="lui_fssc_cost_total_chart">
				<div id="cost_total" style="width: 100%; height: 330px;"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			loadHeadTotal();
			loadLoanInfo();
			
			 $("#loan").on("click", function () {
				 loadLoanInfo();
	          });
			 
			 $("#expense").on("click", function () {
				 loadExpenseInfo();
	          });
			 
			 $("#payment").on("click", function () {
				 loadPaymentInfo();
	          });
		});
		
		function loadHeadTotal(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=loadCostHead',
				async : false,
				success : function(rtn) {
					rtn = JSON.parse(rtn);
					if (null != rtn) {
						var loan = rtn.loan;
						if(null != loan){
							var thisYearTotal = loan.thisLoanTotal;
							var lastYearTotal = loan.lastLoanTotal;
							var money = 0;
							//获取年份
							var myDate = new Date();
							var year = myDate.getFullYear() - 1;
							
							$("#loan_title").html(myDate.getFullYear() +"年借款金额合计(元)");
							//$("#loan_tips").html(myDate.getFullYear() +"年借款金额合计(元)");
							$("#loan_amount").html("￥"+ (thisYearTotal + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							// （今年金额-去年金额）/去年金额*100%
							if(lastYearTotal > thisYearTotal){
								money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								$("#loan_year").html("同比"+year+"年少");
								$("#loan_up_down").addClass("process_down");
								$("#loan_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else if(lastYearTotal < thisYearTotal){
								if(0 != lastYearTotal){
									money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								}else{
									money = thisYearTotal;
								}
								$("#loan_year").html("同比" + year + "年多");
								$("#loan_up_down").addClass("process_up");
								$("#loan_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else{
								$("#loan_year").html("同比" + year + "年持平");
							}
						}
						
						var expense = rtn.expense;
						if(null != expense){
							var thisYearTotal = expense.thisExpenseTotal;
							var lastYearTotal = expense.lastExpenseTotal;
							var money = 0;
							//获取年份
							var myDate = new Date();
							var year = myDate.getFullYear() - 1;
							
							$("#expense_title").html(myDate.getFullYear() +"年报销金额合计(元)");
							//$("#expense_tips").html(myDate.getFullYear() +"年报销金额合计(元)");
							$("#expense_amount").html("￥"+ (thisYearTotal + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							if(lastYearTotal > thisYearTotal){
								money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								$("#expense_year").html("同比"+year+"年少");
								$("#expense_up_down").addClass("process_down");
								$("#expense_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else if(lastYearTotal < thisYearTotal){
								if(0 != lastYearTotal){
									money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								}else{
									money = thisYearTotal;
								}
								$("#expense_year").html("同比" + year + "年多");
								$("#expense_up_down").addClass("process_up");
								$("#expense_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else{
								$("#expense_year").html("同比" + year + "年持平");
							}
							
						}
						
						var payment = rtn.payment;
						if(null != payment){
							var thisYearTotal = payment.thisPayTotal;
							var lastYearTotal = payment.lastPayTotal;
							var money = 0;
							//获取年份
							var myDate = new Date();
							var year = myDate.getFullYear() - 1;
							
							$("#pay_title").html(myDate.getFullYear() +"年实付金额合计(元)");
							//$("#pay_tips").html(myDate.getFullYear() +"年实付金额合计(元)");
							$("#pay_amount").html("￥"+ (thisYearTotal + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							if(lastYearTotal > thisYearTotal){
								money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								$("#pay_year").html("同比"+year+"年少");
								$("#pay_up_down").addClass("process_down");
								$("#pay_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else if(lastYearTotal < thisYearTotal){
								if(0 != lastYearTotal){
									money = (thisYearTotal - lastYearTotal) / lastYearTotal * 100;
								}else{
									money = thisYearTotal;
								}
								$("#pay_year").html("同比" + year + "年多");
								$("#pay_up_down").addClass("process_up");
								$("#pay_money").html("￥"+ (money.toFixed(2) + '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, '$1,'));
							}else{
								$("#pay_year").html("同比" + year + "年持平");
							}
						}
					}
				}
			});
		}
		
		
		
		function loadLoanInfo(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=costContent',
				async : false,
				success : function(rtn) {
					rtn = JSON.parse(rtn);
					if (null != rtn) {
						var loanChart = echarts.init(document.getElementById('cost_total'));
						var loan = rtn.loan;
						if(null != loan){
							var loan_option_cost = {
						            tooltip: {
						                trigger: 'axis',
										confine: true,
										extraCssText: 'white-space: normal; word-break: break-all;'
						            },
						            legend: {
						                data: [{
						                        name: '还款中',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle',
						                    },
						                    {
						                        name: '未还借款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                    {
						                        name: '已还借款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                ]
						            },
						            grid: {
						                left: '3%',
						                right: '4%',
						                bottom: '3%',
						                containLabel: true
						            },

						            xAxis: {
						                type: 'category',
						                axisTick: {
						                    show: false
						                },
						                axisLine: {
						                    show: false
						                },
						                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
						            },
						            yAxis: {
						                type: 'value',
						                axisLabel: {
						                    formatter: '{value}'
						                }
						            },
						            series: [{
						                    name: '还款中',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [loan.repIng.values["one"], loan.repIng.values["two"], loan.repIng.values["three"], loan.repIng.values["four"],
						                    	loan.repIng.values["five"], loan.repIng.values["six"], loan.repIng.values["seven"], loan.repIng.values["eight"], 
						                    	loan.repIng.values["nine"], loan.repIng.values["ten"], loan.repIng.values["eleven"], loan.repIng.values["twelve"]],
						                    itemStyle: {
						                        color: '#4285f4'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,209,251,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,209,251,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '未还借款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [loan.notRep.values["one"], loan.notRep.values["two"], loan.notRep.values["three"], loan.notRep.values["four"],
						                    	loan.notRep.values["five"], loan.notRep.values["six"], loan.notRep.values["seven"], loan.notRep.values["eight"], 
						                    	loan.notRep.values["nine"], loan.notRep.values["ten"], loan.notRep.values["eleven"], loan.notRep.values["twelve"]],
						                    itemStyle: {
						                        color: '#F7C548'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(255,227,158,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(255,227,158,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '已还借款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [loan.already.values["one"], loan.already.values["two"], loan.already.values["three"], loan.already.values["four"],
						                    	loan.already.values["five"], loan.already.values["six"], loan.already.values["seven"], loan.already.values["eight"], 
						                    	loan.already.values["nine"], loan.already.values["ten"], loan.already.values["eleven"], loan.already.values["twelve"]],
						                    itemStyle: {
						                        color: '#50C45E'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,238,190,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,238,190,0.2)'
						                            }
						                        ])
						                    },
						                }
						            ]
						        };
						}
						loanChart.clear();
						loanChart.setOption(loan_option_cost);
					}
				}
			});
		}
		
		function loadExpenseInfo(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=costContent',
				async : false,
				success : function(rtn) {
					rtn = JSON.parse(rtn);
					if (null != rtn) {
						var expenseChart = echarts.init(document.getElementById('cost_total'));
						var expense = rtn.expense;
						if(null != expense){
							var expense_option_cost = {
						            tooltip: {
						                trigger: 'axis',
										confine: true,
										extraCssText: 'white-space: normal; word-break: break-all;'
						            },
						            legend: {
						                data: [{
						                        name: '报销中',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle',
						                    },
						                    {
						                        name: '未报销',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                    {
						                        name: '已报销',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                ]
						            },
						            grid: {
						                left: '3%',
						                right: '4%',
						                bottom: '3%',
						                containLabel: true
						            },

						            xAxis: {
						                type: 'category',
						                axisTick: {
						                    show: false
						                },
						                axisLine: {
						                    show: false
						                },
						                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
						            },
						            yAxis: {
						                type: 'value',
						                axisLabel: {
						                    formatter: '{value}'
						                }
						            },
						            series: [{
						                    name: '报销中',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [expense.expenseIng.values["one"], expense.expenseIng.values["two"], expense.expenseIng.values["three"], expense.expenseIng.values["four"],
						                    	expense.expenseIng.values["five"], expense.expenseIng.values["six"], expense.expenseIng.values["seven"], expense.expenseIng.values["eight"], 
						                    	expense.expenseIng.values["nine"], expense.expenseIng.values["ten"], expense.expenseIng.values["eleven"], expense.expenseIng.values["twelve"]],
						                    itemStyle: {
						                        color: '#4285f4'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,209,251,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,209,251,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '未报销',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [expense.notExpense.values["one"], expense.notExpense.values["two"], expense.notExpense.values["three"], expense.notExpense.values["four"],
						                    	expense.notExpense.values["five"], expense.notExpense.values["six"], expense.notExpense.values["seven"], expense.notExpense.values["eight"], 
						                    	expense.notExpense.values["nine"], expense.notExpense.values["ten"], expense.notExpense.values["eleven"], expense.notExpense.values["twelve"]],
						                    itemStyle: {
						                        color: '#F7C548'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(255,227,158,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(255,227,158,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '已报销',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [expense.publicExpense.values["one"], expense.publicExpense.values["two"], expense.publicExpense.values["three"], expense.publicExpense.values["four"],
						                    	expense.publicExpense.values["five"], expense.publicExpense.values["six"], expense.publicExpense.values["seven"], expense.publicExpense.values["eight"], 
						                    	expense.publicExpense.values["nine"], expense.publicExpense.values["ten"], expense.publicExpense.values["eleven"], expense.publicExpense.values["twelve"]],
						                    itemStyle: {
						                        color: '#50C45E'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,238,190,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,238,190,0.2)'
						                            }
						                        ])
						                    },
						                }
						            ]
						        };
						}
						expenseChart.clear();
						expenseChart.setOption(expense_option_cost);
					}
				}
			});
		}
		
		function loadPaymentInfo(){
			$.ajax({
				url : '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainPortlet.do?method=costContent',
				async : false,
				success : function(rtn) {
					rtn = JSON.parse(rtn);
					if (null != rtn) {
						var paymentChart = echarts.init(document.getElementById('cost_total'));
						var payment = rtn.payment;
						if(null != payment){
							var payment_option_cost = {
						            tooltip: {
						                trigger: 'axis',
										confine: true,
										extraCssText: 'white-space: normal; word-break: break-all;'
						            },
						            legend: {
						                data: [{
						                        name: '付款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle',
						                    },
						                    {
						                        name: '预付款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                    {
						                        name: '挂账付款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                    {
						                        name: '退款',
						                        textStyle: {
						                            fontsize: 12,
						                            color: '#9999'
						                        },
						                        icon: 'circle'
						                    },
						                ]
						            },
						            grid: {
						                left: '3%',
						                right: '4%',
						                bottom: '3%',
						                containLabel: true
						            },

						            xAxis: {
						                type: 'category',
						                axisTick: {
						                    show: false
						                },
						                axisLine: {
						                    show: false
						                },
						                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
						            },
						            yAxis: {
						                type: 'value',
						                axisLabel: {
						                    formatter: '{value}'
						                }
						            },
						            series: [{
						                    name: '付款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [payment.pay.values["one"], payment.pay.values["two"], payment.pay.values["three"], payment.pay.values["four"],
						                    	payment.pay.values["five"], payment.pay.values["six"], payment.pay.values["seven"], payment.pay.values["eight"], 
						                    	payment.pay.values["nine"], payment.pay.values["ten"], payment.pay.values["eleven"], payment.pay.values["twelve"]],
						                    itemStyle: {
						                        color: '#4285f4'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,209,251,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,209,251,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '预付款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [payment.prePay.values["one"], payment.prePay.values["two"], payment.prePay.values["three"], payment.prePay.values["four"],
						                    	payment.prePay.values["five"], payment.prePay.values["six"], payment.prePay.values["seven"], payment.prePay.values["eight"], 
						                    	payment.prePay.values["nine"], payment.prePay.values["ten"], payment.prePay.values["eleven"], payment.prePay.values["twelve"]],
						                    itemStyle: {
						                        color: '#F7C548'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(255,227,158,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(255,227,158,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '挂账付款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [payment.suspend.values["one"], payment.suspend.values["two"], payment.suspend.values["three"], payment.suspend.values["four"],
						                    	payment.suspend.values["five"], payment.suspend.values["six"], payment.suspend.values["seven"], payment.suspend.values["eight"], 
						                    	payment.suspend.values["nine"], payment.suspend.values["ten"], payment.suspend.values["eleven"], payment.suspend.values["twelve"]],
						                    itemStyle: {
						                        color: '#50C45E'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,238,190,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,238,190,0.2)'
						                            }
						                        ])
						                    },
						                },
						                {
						                    name: '退款',
						                    type: 'line',
						                    smooth: true,
						                    showSymbol: false,
						                    data: [payment.refund.values["one"], payment.refund.values["two"], payment.refund.values["three"], payment.refund.values["four"],
						                    	payment.refund.values["five"], payment.refund.values["six"], payment.refund.values["seven"], payment.refund.values["eight"], 
						                    	payment.refund.values["nine"], payment.refund.values["ten"], payment.refund.values["eleven"], payment.refund.values["twelve"]],
						                    itemStyle: {
						                        color: '#50C45E'
						                    },
						                    areaStyle: {
						                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                                offset: 0,
						                                color: 'rgba(184,238,190,1)'
						                            },
						                            {
						                                offset: 1,
						                                color: 'rgba(184,238,190,0.2)'
						                            }
						                        ])
						                    },
						                }
						            ]
						        };
						}
						paymentChart.clear();
						paymentChart.setOption(payment_option_cost);
					}
				}
			});
		}
	</script>
</body>
