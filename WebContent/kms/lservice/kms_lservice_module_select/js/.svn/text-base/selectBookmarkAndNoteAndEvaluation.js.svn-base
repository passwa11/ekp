define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function SelectBookmarkAndNoteAndEvaluation(id, name) {
		var fdSelectedIds = $('input[name="' + id + '"]').val();

		var showBookmark = true;
		var showNote = true;
		var showEvaluation = true;
		
		if(fdSelectedIds){
			if(fdSelectedIds.indexOf("bookmark") < 0){
				showBookmark = false;
			}
			if(fdSelectedIds.indexOf("note") < 0){
				showNote = false;
			}
			if(fdSelectedIds.indexOf("evaluation") < 0){
				showEvaluation = false;
			}
		}	
		var url = "/kms/lservice/kms_lservice_module_select/bookmarkAndNoteAndEvaluation_selectpage.jsp?";
		url += "showBookmark=" + showBookmark;
		url += "&showNote=" + showNote;
		url += "&showEvaluation=" + showEvaluation;
		dialog
				.iframe( url,
						'选择展示的数据',
						function(value) {
							if (!value) {
								return;
							} else {
								console.log(value);
								$('input[name="' + id + '"]').val(value.fdId);
								$('input[name="' + name + '"]').val(value.fdName);
								
								
							}
						}, {"width":750,"height":550});
	}
	module.exports = SelectBookmarkAndNoteAndEvaluation;
});