define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function SelectImgSource(id, name) {
		var fdSelectedIds = $('input[name="' + id + '"]').val();
		dialog
				.iframe(
						'/sys/mportal/sys_mportal_imgsource/sysMportalImgSource_selectpage.jsp?fdSelectedIds='+fdSelectedIds,
						'选择幻灯片',
						function(value) {
							if (!value) {
								return;
							} else {
								//alert(value.fdId);
								//alert(value.fdName);
								$('input[name="' + id + '"]').val(value.fdId);
								$('input[name="' + name + '"]').val(value.fdName);
							}
						}, {"width":750,"height":550});
	}
	module.exports = SelectImgSource;
});