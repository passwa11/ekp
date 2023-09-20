function selectModules(ele) {
	seajs.use([ 'lui/dialog', 'lui/jquery' ],
					function(dialog, $) {
						var $table = $(ele).closest(".tb_normal");
						dialog.iframe(
										"/sys/tag/sys_tag_group/sysTagGroup_mselect.jsp",
										"选择模块",
										function(val) {
											if (!val) {
												return;
											} else {
												$table.find("[name='fdName']").val(val.text);
												$table.find("[name='fdModelName']").val(val.value);
											}
										}, {
											width : 550,
											height : 450
										});
					});
}