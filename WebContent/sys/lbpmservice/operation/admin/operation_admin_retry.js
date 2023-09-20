/*******************************************************************************
 * 功能：执行重新执行出错队列事件
  使用：
  作者：傅游翔
 创建时间：2013-01-10
 ******************************************************************************/
( function(operations) {
	operations['admin_retry'] = {
		click:OperationClick,
		check:OperationCheck,
		setOperationParam:setOperationParam
	};
	//特权人操作：重新执行
	function OperationClick(operationName){
		$("#operationsTDTitle").html(lbpm.constant.opt.retryQueueTitel);
		var content = $("#operationsTDContent");
		content.empty();
		var queueArray;
		if(lbpm && lbpm.approveType == "right"){
			queueArray = lbpm.globals.errorMessages;
		}else{
			queueArray = pWin.lbpm.globals.errorMessages;
		}
		$.each(queueArray, function(index, row) {
			var tmp = lbpm.constant.opt.retryQueueTemp;
			var text = tmp.replace('{user}', row['user']).replace('{node}', row['sourceNodeName']).replace('{opr}', row['operationName']);
			content.append('<div class="error_queue"><label class="lui-lbpm-checkbox"><input type="checkbox" checked="checked" key="error_queue_id" value="'
					 + row['id'] + '" ><span class="checkbox-label">' + Com_HtmlEscape(text) 
					 + '</span></label><label class="error_showdetail" style="text-decoration: underline">'+lbpm.constant.opt.retryShowDetail+'</label>'
					 + '<label class="error_hidedetail" style="text-decoration: underline; display:none;">'+lbpm.constant.opt.retryHideDetail+'</label>'
					 +'<div class="error_detail" style="display:none">'
					 + Com_HtmlEscape(row['errorText']) + '</div></div>');
		});
		$(".error_showdetail").css({'padding-left':'5px'});
		$(".error_hidedetail").css({'padding-left':'5px'});
		$(".error_detail").css({'background':'#FBE3E4', 'border': '1px solid #FBC2C4', 'padding':'5px'});
		$(".error_showdetail").click(function() {
			var parent = $(this).parent();
			parent.children(".error_detail").show();
			parent.children(".error_hidedetail").show();
			parent.children(".error_showdetail").hide();
		});
		$(".error_hidedetail").click(function() {
			var parent = $(this).parent();
			parent.children(".error_hidedetail").hide();
			parent.children(".error_detail").hide();
			parent.children(".error_showdetail").show();
		});
		$("#rerunIfErrorRow").show();
		$("#operationsRow").show();

		//增加选择节点通知方式 add by wubing date:2015-05-06
		lbpm.globals.setAdminNodeNotifyType(lbpm.nowNodeId);

	}
	//“前后跳转”操作的检查
	function OperationCheck(){
		// 校验要有选中的队列
		var len = $('#operationsTDContent input[key="error_queue_id"]:checked').length;
		if (len < 1) {
			alert(lbpm.constant.opt.retrySelect);
			return false;
		}
		return true;
	}
	//"前后跳转"操作的获取参数
	function setOperationParam() {
		// 设置队列到参数
		var ids = [];
		$('#operationsTDContent input[key="error_queue_id"]:checked').each(function(){ids.push($(this).val());});
		lbpm.globals.setOperationParameterJson(ids.join(";"), "errorQueueIds", "param");
		lbpm.globals.setOperationParameterJson($("#rerunIfError").attr("checked"), "rerunIfError", "param");
	}

})(lbpm.operations);