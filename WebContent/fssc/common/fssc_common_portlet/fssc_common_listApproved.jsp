<%@page import="net.sf.antcontrib.logic.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>已审核</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_iframe_invoice">
			<div class="lui_fssc_invoice_card_content">
				<table class="lui_fssc_invoice_table" rules="rows" frame="hsides">
					<thead id="approved_thead"></thead>
					<tbody id="approved_tbody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=listApproved',
			async : false,
			success : function(rtn) {
				var tbody = $("#approved_tbody");
				var thead = $("#approved_thead")
			 	tbody.empty();
				thead.empty();
				if(rtn != null){
					rtn = JSON.parse(rtn);
					var str = rtn["array"];
					if(str.length > 0){
						var th = $("<tr></tr>");
						th.append($("<th><span>序号</span></th>"));
						th.append($("<th><span>单据号</span></th>"));
						th.append($("<th><span>单据类型</span></th>"));
						th.append($("<th><span>单据标题</span></th>"));
						th.append($("<th><span>申请人</span></th>"));
						th.append($("<th><span>单据金额</span></th>"));
						th.append($("<th><span>流程状态</span></th>"));
						th.appendTo(thead);
						
						var json = JSON.stringify(str);
					 	$.each($.parseJSON(json), function (i, n) {
					 		//创建一个 tr
				            var tr = $("<tr></tr>");
				            //往tr里面放 td
				            tr.append($("<td><span>"+(i+1)+"</span></td>"));
						    tr.append($("<td><span>"+n.fdNo+"</span></td>"));
						    tr.append($("<td><span>"+n.fdModelName+"</span></td>"));
						    tr.append($("<td><span>"+n.docSubject+"</span></td>"));
						    tr.append($("<td><span>"+n.createor+"</span></td>"));
						    tr.append($("<td><span>"+n.money+"</span></td>"));
						    if(n.status == '00'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.discard')}</span></td>"));
						    }else if(n.status == '10'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.draft')}</span></td>"));
						    }else if(n.status == '11'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.refuse')}</span></td>"));
						    }else if(n.status == '20'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.append')}</span></td>"));
						    }else if(n.status == '30'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.publish')}</span></td>"));
						    }else if(n.status == '40'){
						    	tr.append($("<td><span>${lfn:message('sys-lbpmperson:lbpmperson.status.expire')}</span></td>"));
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
