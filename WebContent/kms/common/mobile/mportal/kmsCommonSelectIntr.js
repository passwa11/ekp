define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function select(id, name) {
		var fdSelectedIds = $('input[name="' + id + '"]').val();
		dialog.iframe(
						'/kms/common/kms_home_knowledge_intro_portlet/kmsHomeKnowledgeIntroPortlet.jsp?fdSelectedIds='+fdSelectedIds,
						'选择专题',
						function(value) {
							if (!value) {
								return;
							} else {
								$('input[name="' + id + '"]').val(value.fdId);
								$('input[name="' + name + '"]').val(value.fdName);
							}
						}, {"width":750,"height":550});
	}
	module.exports = select;
});