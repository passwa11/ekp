define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function SelectMenu(id, name) {
		dialog
				.iframe(
						'/sys/mportal/sys_mportal_card/sysMportalCard_menu.jsp',
						'选择菜单',
						function(value) {
							if (!value) {
								return;
							} else {
								$('input[name="' + id + '"]').val(value.id);
								$('input[name="' + name + '"]').val(value.name);
								$("#_menu_delete").remove();
							}
						}, {"width":650,"height":550});
	}
	module.exports = SelectMenu;
});