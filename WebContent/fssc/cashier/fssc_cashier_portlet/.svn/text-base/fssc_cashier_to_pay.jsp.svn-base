<%@page import="net.sf.antcontrib.logic.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${lfn:message('fssc-cashier:fssc.cashier.pay')}</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_iframe_invoice">
			<div class="lui_fssc_invoice_card_content">
				<table class="lui_fssc_invoice_table" rules="rows" frame="hsides">
					<thead id="toPay_thead"></thead>
					<tbody id="toPay_tbody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/cashier/fssc_cashier_portlet/fsscCashierPortle.do?method=pay&status=10&myflow=approval',
			async : false,
			success : function(rtn) {
				var tbody = $("#toPay_tbody");
				var thead = $("#toPay_thead");
			 	tbody.empty();
				thead.empty();
				if(rtn != null && rtn != ""){
					rtn = JSON.parse(rtn);
					var str = rtn["array"];
					if(str.length > 0){
						var th = $("<tr></tr>");
						th.append($("<th><span>${lfn:message('fssc-cashier:fssc.cashier.no')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:fssc.cashier.number')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCompany')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:portlet.doc.view.sum')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:fsscCashierRuleDetail.fdRemarksText')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPlanPaymentDate')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}</span></th>"));
						th.appendTo(thead);

						var json = JSON.stringify(str);
						$.each($.parseJSON(json), function (i, n) {
							//创建一个 tr
							var tr = $("<tr style='background-color: #ffffff;'></tr>");
							//往tr里面放 td
							tr.append($("<td><span>"+(i+1)+"</span></td>"));
							tr.append($("<td><span>"+n.number+"</span></td>"));
							tr.append($("<td><span>"+n.company+"</span></td>"));
							tr.append($("<td><span>"+n.money+"</span></td>"));
							tr.append($("<td><span>"+n.remark+"</span></td>"));
							tr.append($("<td><span>"+n.payTime+"</span></td>"));
							if(n.status == '10'){
								tr.append($("<td><span>待付款</span></td>"));
							}else if(n.status == '11'){
								tr.append($("<td><span>付款失败</span></td>"));
							}else if(n.status == '20'){
								tr.append($("<td><span>付款中</span></td>"));
							}
							else if(n.status == '31'){
								tr.append($("<td><span>其他失败</span></td>"));
							}
							else if(n.status == '40'){
								tr.append($("<td><span>撤销付款</span></td>"));
							}
							else if(n.status == '50'){
								tr.append($("<td><span>银行已受理</span></td>"));
							}
							tr.appendTo(tbody);
						});
					}else{
						//创建一个 tr
						var tr = $("<tr></tr>");
						//往tr里面放 td
						tr.append($("<td colspan='7' style='text-align: center;'>暂无数据</td>"));
						//将 tr 放进 tbody
						tr.appendTo(tbody);
						return;
					}
				}else{
					//创建一个 tr
					var tr = $("<tr></tr>");
					//往tr里面放 td
					tr.append($("<td colspan='7' style='text-align: center;'>暂无数据</td>"));
					//将 tr 放进 tbody
					tr.appendTo(tbody);
					return;
				}
			}
		});
	});
	</script>
</body>
