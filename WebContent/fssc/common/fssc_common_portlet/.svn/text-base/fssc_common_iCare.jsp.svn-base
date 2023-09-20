<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>我的关注</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_iframe_process">
			<div class="lui_fssc_process_header"></div>
			<div class="lui_fssc_process_content" id="iCare">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=listICare',
			type:'POST',
			data:{"IsFollow":1},
			async : false,
			success : function(rtn) {
				var contentDiv = $("#iCare");
				contentDiv.empty();
				if(rtn != null && "" != rtn){
				 	$.each($.parseJSON(rtn), function (i, n) {
				 	var divItem = "<div class='lui_fssc_process_item'>"
				 					 +"<div class='lui_fssc_process_icon process_orderd'  onclick='removeOrder(\""+n.fdId+"\")'></div>"
									 +"<div class='lui_fssc_process_content_detail'>"
									 	+"<div class='lui_fssc_process_check'>"
									 		+"<span>"+n.docSubject+"</span>"
									 	+"</div>"
										+"<div class='lui_fssc_process_desc'>"
											+"<span class='lui_fssc_process_name'>"+n.fdName+"</span>"
											+"<span class='lui_fssc_process_time'>"+n.createTime+"</span>"
											+"<span class='lui_fssc_process_node'>"+n.fdStatus+"</span>"
										+"</div>"
									 +"</div>"
								  +"</div>";
						contentDiv.append(divItem);
					  });
				}else{
		            var div = "<div>暂无数据</div>";
		            contentDiv.append(div);
		            return;
				}
			}
		});
	});


	function removeOrder(fdId){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=saveICare',
			data:{	"fdId":fdId
			},
			dataType:'json',
			type:'POST',
			async : false,
			success : function(rtn) {
				if(rtn.result != null && rtn.result != ""){
					reloadData();
				}
			}
		});
	}

	function reloadData(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=listICare',
			type:'POST',
			data:{"IsFollow":1},
			async : false,
			success : function(rtn) {
				var contentDiv = $("#iCare");
				contentDiv.empty();
				if(rtn != null && "" != rtn){
					$.each($.parseJSON(rtn), function (i, n) {
						var divItem = "<div class='lui_fssc_process_item'>"
								+"<div class='lui_fssc_process_icon process_orderd' onclick='removeOrder(\""+n.fdId+"\")'></div>"
								+"<div class='lui_fssc_process_content_detail'>"
								+"<div class='lui_fssc_process_check'>"
								+"<span>"+n.docSubject+"</span>"
								+"</div>"
								+"<div class='lui_fssc_process_desc'>"
								+"<span class='lui_fssc_process_name'>"+n.fdName+"</span>"
								+"<span class='lui_fssc_process_time'>"+n.createTime+"</span>"
								+"<span class='lui_fssc_process_node'>"+n.fdStatus+"</span>"
								+"</div>"
								+"</div>"
								+"</div>";
						contentDiv.append(divItem);
					});
				}else{
					var div = "<div>暂无数据</div>";
					contentDiv.append(div);
					return;
				}
			}
		});
	}
	</script>
</body>