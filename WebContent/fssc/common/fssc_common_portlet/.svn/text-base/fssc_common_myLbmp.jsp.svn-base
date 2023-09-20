<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>我启动的流程</title>
<link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
	<div class="lui_fssc_financial_card_portlet">
		<div class="lui_fssc_iframe_process">
			<div class="lui_fssc_process_header"></div>
			<div class="lui_fssc_process_content" id="content">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=listCreator',
			async : false,
			success : function(rtn) {
				rtn = JSON.parse(rtn);
				var contentDiv = $("#content");
				contentDiv.empty();
				if(rtn != null){
					var str = rtn["array"];
					var json = JSON.stringify(str);
				 	$.each($.parseJSON(json), function (i, n) {
				 		var divItem = "<div class='lui_fssc_process_item'>";
				 		$.ajax({
							url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=getICareByFdId',
							type:'POST',
							data:{"fdNum":n.fdId},
							async : false,
							success : function(data) {
								if(data != null && data != ""){
									data = JSON.parse(data);
									if(data.fdNum == n.fdId){
										divItem += "<div class='lui_fssc_process_icon process_orderd' onclick='changeStyle(this)'></div>";
									}
								}else{
									divItem += "<div class='lui_fssc_process_icon process_no_orderd' onclick='changeStyle(this)'></div>";
								}
							}
						});
				 		divItem +="<div class='lui_fssc_process_content_detail'>"
									+"<div class='lui_fssc_process_check'>"
									 	+"<span>"+n.docSubject+"</span>"
									 	+"<span style='display:none'>"+n.fdId+"</span>"
									+"</div>"
									+"<div class='lui_fssc_process_desc'>"
										+"<span class='lui_fssc_process_name'>"+n.createor+"</span>" 
										+"<span class='lui_fssc_process_time'>"+n.createTime+"</span>";
								 		if(n.status == '00'){
											divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.discard')}</span>"
									    }else if(n.status == '10'){
									    	divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.draft')}</span>"
									    }else if(n.status == '11'){
									    	divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.refuse')}</span>"
									    }else if(n.status == '20'){
									    	divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.append')}</span>"
									    }else if(n.status == '30'){
									    	divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.publish')}</span>"
									    }else if(n.status == '40'){
									    	divItem+="<span class='lui_fssc_process_node'>单据节点：${lfn:message('sys-lbpmperson:lbpmperson.status.expire')}</span>"
									    }
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
	//点关注
	function changeStyle(obj){
		if($(obj).hasClass("lui_fssc_process_icon process_no_orderd")){
			//获取标题和fdId
			var fdNum = null;
			var docSubject = null;
			var div = $(obj).next(".lui_fssc_process_content_detail").children(".lui_fssc_process_check").find("span");
			div.each(function(index,e){
				if(index == 0 ){
					docSubject = $(e).eq(index).text();
				}
				if(index == 1 ){
					fdNum = $(e).html();
				}
			});
			
			//获取创建人、时间、状态
			var fdName = null;
			var createTime = null;
			var fdStatus = null;
			var lastDiv = $(obj).next(".lui_fssc_process_content_detail").children(".lui_fssc_process_desc").find("span");
			lastDiv.each(function(index,e){
				if(index ==0){
					fdName = $(e).html();
				}else if(index ==1){
					createTime = $(e).html();
				}else if(index ==2){
					fdStatus = $(e).html();
				}
			});
			$.ajax({
				url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=saveICare',
				data:{	"fdNum":fdNum,
						"docSubject":docSubject,
						"fdName":fdName,
						"createTime":createTime,
						"fdStatus":fdStatus
					},
        		dataType:'json',
        		type:'POST',
				async : false,
				success : function(rtn) {
					if(rtn.result != null && rtn.result != ""){
						$(obj).removeClass("process_no_orderd")
						$(obj).addClass("process_orderd");
					}
				}
			});
		}else if($(obj).hasClass("lui_fssc_process_icon process_orderd")){
			//获取标题和fdId
			var fdNum = null;
			var docSubject = null;
			var div = $(obj).next(".lui_fssc_process_content_detail").children(".lui_fssc_process_check").find("span");
			div.each(function(index,e){
				if(index == 1 ){
					fdNum = $(e).html();
				}
			});
			$.ajax({
				url : '${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=delByFdId',
				data:{	"fdNum":fdNum},
        		dataType:'json',
        		type:'POST',
				async : false,
				success : function(rtn) {
					if(rtn.cnt == 1){
						$(obj).addClass("process_no_orderd")
						$(obj).removeClass("process_orderd");
					}
				}
			});
		}
	}
	</script>
</body>

