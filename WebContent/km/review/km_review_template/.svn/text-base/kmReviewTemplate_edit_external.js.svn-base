Com_IncludeFile("jquery.js");
$( function() {
	//单机事件
	$("#fdIsExternal").click( function() {
		var isChecked = $(this).is(":checked");
		if (isChecked) {
			hideLabels(false);
			hideBaseInfos(false);
			hideApprove(false);
		} else {
			hideLabels(true);
			hideBaseInfos(true);
			hideApprove(true);
		}
	 });
	
	if("false" == $("input[name=fdIsMobileView][checked]").val()){
		$("input[name=fdIsMobileApprove][value='true']").prop("checked",false);
		$("input[name=fdIsMobileApprove][value='false']").prop("checked", true);
		$("input[name=fdIsMobileApprove][value='true']").attr("disabled", true);
	}
	
	// 当禁用移动端查阅时，移动端审批将不可用
	$("input[name=fdIsMobileView]").change(function() {
		if(this.value == 'false') {
			
			$("input[name=fdIsMobileApprove][value='true']").prop("checked",false);
			$("input[name=fdIsMobileApprove][value='false']").prop("checked", true);
			$("input[name=fdIsMobileApprove][value='true']").attr("disabled", true);
		} else {
			$("input[name=fdIsMobileApprove][value='true']").removeAttr("disabled");
			$("input[name=fdIsMobileApprove][value='false']").prop("checked",false);
			$("input[name=fdIsMobileApprove][value='true']").prop("checked", true);
			
		}
	});
	
});



	/**
	 * 显示/隐藏table标签
	 *  
	 */
	function hideLabels(isShow) {
		var number = 1;
		$(".td_label0 nobr").each( function(a, b) {
			if (number != 1) {
				if (isShow) {
					$(this).show();
				} else {
					$(this).hide();
				}
			}
			number++;
		});
	};
	/**
	 * 显示/隐藏字段
	 * 
	 */
	function hideBaseInfos(isShow){
		if(isShow){
			$("#fdFeedbackModify").show();
            $("#number").show();
			$("#fdKeywordIds").show();
			$("#fdExternalUrl").hide();
		}else{
			$("#fdFeedbackModify").hide();
            $("#number").hide();
			$("#fdKeywordIds").hide();
			$("#fdExternalUrl").show();
		}
	}
	
	function hideApprove(isShow) {
		var children = $("#fdIsMobile").children();
		var isMobileView = $("#fdIsMobileView");
		if(isShow) {
			$(children[2]).show();
			$(children[3]).show();
			isMobileView.show();
		} else {
			$(children[2]).hide();
			$(children[3]).hide();
			isMobileView.hide();
		}
	}
