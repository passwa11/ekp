define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function SelectMedalAndDiploma(id, name) {
		var fdSelectedIds = $('input[name="' + id + '"]').val();
		dialog
				.iframe(
						'/kms/lservice/kms_lservice_module_select/medalAndDiploma_selectpage.jsp?fdSelectedIds='+fdSelectedIds,
						'选择展示的数据',
						function(value) {
							if (!value) {
								return;
							} else {
								$('input[name="' + id + '"]').val(value.fdId);
								$('input[name="' + name + '"]').val(value.fdName);
							}
						}, {"width":750,"height":550});
	}
	module.exports = SelectMedalAndDiploma;
});