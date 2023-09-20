<%@page import="net.sf.antcontrib.logic.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${lfn:message('fssc-voucher:fssc.voucher.not.bookkeeping')}</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_iframe_invoice">
			<div class="lui_fssc_invoice_card_content">
				<table class="lui_fssc_invoice_table" rules="rows" frame="hsides">
					<thead id="data_thead"></thead>
					<tbody id="data_tbody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/voucher/fssc_voucher_portlet/fsscVoucherPortlet.do?method=bookkeeping&status=10',
			async : false,
			success : function(rtn) {
				var tbody = $("#data_tbody");
				var thead = $("#data_thead");
			 	tbody.empty();
			 	thead.empty();
				if(rtn != null && rtn != ""){
					rtn = JSON.parse(rtn);
					var str = rtn["array"];
					if(str.length > 0){
						var th = $("<tr></tr>");
						th.append($("<th><span>${lfn:message('fssc-voucher:fssc.voucher.serialNumber.bookkeeping')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.fdCompany')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.docFinanceNumber')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fssc.voucher.Number.bookkeeping')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseVoucherType')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.fdAccountingYear')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherMain.fdPeriod')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdVoucherDateFormula')}</span></th>"));
						th.append($("<th><span>${lfn:message('fssc-voucher:fssc.voucher.type.bookkeeping')}</span></th>"));
						th.appendTo(thead);
						
						var json = JSON.stringify(str);
					 	$.each($.parseJSON(json), function (i, n) {
					 		//创建一个 tr
				            var tr = $("<tr></tr>");
				          	//往tr里面放 td
				            tr.append($("<td><span>"+(i+1)+"</span></td>"));
						    tr.append($("<td><span>"+n.company+"</span></td>"));
						    tr.append($("<td><span>"+n.financeNumber+"</span></td>"));
						    tr.append($("<td><span>"+n.docNumber+"</span></td>"));
						    tr.append($("<td><span>"+n.mondelNumber+"</span></td>"));
						    tr.append($("<td><span>"+n.voucherType+"</span></td>"));
						    tr.append($("<td><span>"+n.year+"</span></td>"));
						    tr.append($("<td><span>"+n.period+"</span></td>"));
						    tr.append($("<td><span>"+n.voucherDate+"</span></td>"));
						    if(n.status == '10'){
						    	tr.append($("<td><span>待记账</span></td>"));
						    }
						    tr.appendTo(tbody);
						  });
					}else{
						//创建一个 tr
			            var tr = $("<tr></tr>");
			            //往tr里面放 td
			            tr.append($("<td colspan='10' style='text-align: center;'>暂无数据</td>"));
			            //将 tr 放进 tbody
			            tr.appendTo(tbody);
			            return;
					}
				}else{
					//创建一个 tr
					var tr = $("<tr></tr>");
					//往tr里面放 td
					tr.append($("<td colspan='10' style='text-align: center;'>暂无数据</td>"));
					//将 tr 放进 tbody
					tr.appendTo(tbody);
					return;
				}
			}
		});
	});
	</script>
</body>
