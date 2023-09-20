LUI.ready(function(){
	/** 流程说明为空隐藏 */
	var flowDescription = $("#fdFlowDescription").text() || "";
	flowDescription = flowDescription.replace(/^\s*|\s*$/g,"");
	if (flowDescription == "") {
		$("#fdFlowDescriptionRow").hide();
	}
	if (typeof lbpm != "undefined") {
		if ((!lbpm.allMyProcessorInfoObj || lbpm.allMyProcessorInfoObj.length == 0)  && 
				(!lbpm.authorityInfoObj || lbpm.authorityInfoObj.length == 0)
				&& (!lbpm.drafterInfoObj || lbpm.drafterInfoObj.length == 0) && 
				(!lbpm.historyhandlerInfoObj || lbpm.historyhandlerInfoObj.length == 0)) {
			$(".submit_btn_div").hide();
		}
	}
	
	$(".lui-lbpm-titleNode").parent("div").addClass("lui-lbpm-detail-wrap");
	
	/** 签章 */
	$(".lui-lbpm-opinion-signature").attr("onclick","thirdDingSignature()");
	$(".tab_img").find("img").each(function(index,img){
		$(img).css("float","");
	});
	/** 自定义 审批意见 */
	$(".lui-lbpm-opinion-custormBtn").remove();
	var commonUsedOpionionSize = $(".commonUsedOpinionList").find("li").length;
	$(".commonUsedOpinionList").height(commonUsedOpionionSize * 40 + "px");
	/** 查看页头部 */
	handle_head();
	setTimeout(function(){
		$(".lui_tabpage_float_navs_mark").remove();
	},500);
});

/** 查看页提交按钮 */
function _dingSubmitDoc(){
	if(lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
		lbpm.globals.extendRoleOptWindowSubmit('updateByPanel','right');
	} else {
		$('#process_review_button').click();
	}
}

function updateSubject (){
	var subject_hidden = $("#subject_hidden").val();
	if("0" == subject_hidden){//查看状态-->编辑状态
		//把查看的值给编辑
		var span = $("#docSubjectSpan").text();
		$("#docSubject").val(span);
		document.getElementById("docSubject").style.display="inline-block";
		document.getElementById("docSubjectSpan").style.display="none";
		document.getElementById("docSubjectUpdateSpan").style.display="none";
		$('#docSubject').attr('validate', 'required maxLength(500)');
		$("#subject_hidden").val("1");
	}else{//编辑状态--->查看状态
		var span = $("#docSubject").val();
		document.getElementById("docSubjectSpan").innerHTML = span;
		document.getElementById("docSubject").style.display="none";
		document.getElementById("docSubjectSpan").style.display="inline-block";
		document.getElementById("docSubjectUpdateSpan").style.display="inline-block";
		$('#docSubject').removeAttr('validate', 'required maxLength(500)');
		//把编辑的值给查看
		$("#subject_hidden").val("0");
	}
}

/** 处理查看页头部 */
function handle_head() {
	/*if (typeof lbpm != "undefined" && lbpm.nowNodeId) {
		var nodes = lbpm.nodes;
		if (nodes) {
			for (var nodeId in nodes) {
				if (nodeId === lbpm.nowNodeId) {
					var node = nodes[nodeId];
					var handlerIds = node["handlerIds"];
					var handlerNames = node["handlerNames"];
					if (handlerIds) {
						handlerNames = handlerNames.split(";");
						$(".lui-doc-status").text("等待" + handlerNames + "处理");
					}
				}
			}
		}
	}*/
	$(".lui-doc-status").show();
	$(".lui_review_type_btn").click(function(){
		var showContent = $(this).attr("show-content");
		
		if (showContent === "review_content") {
			if (!$(this).hasClass("is-active")) {
				$("[show-content='review_content']").addClass("is-active");
				$("[show-content='view_data']").removeClass("is-active");
				$("#review_content").show();
				$("#view_data").hide();
			}
		} 
		if (showContent === "view_data") {
			if (!$(this).hasClass("is-active")) {
				$("[show-content='review_content']").removeClass("is-active");
				$("[show-content='view_data']").addClass("is-active");
				$("#review_content").hide();
				$("#view_data").show();
			}
		}
	});
}

/** 电子签名 */
if (typeof(seajs) != 'undefined') {//非新UED模块屏蔽流程签章功能
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
		//新建
		window.thirdDingSignature = function() {
			var url = "/km/signature/km_signature_main_ui/dingSuit/kmSignatureMain_showSig.jsp";
			dialog.iframe(url,'电子签名',function(rtn){
				if(rtn!=null){
					var file = {
						fdAttId:rtn.attId
		            };
					signatureImgShow(file); 
				}
			},{width:480,height:270});
		};
	});
}
