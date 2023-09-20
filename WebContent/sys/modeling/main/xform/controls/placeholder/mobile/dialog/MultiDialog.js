/**
 * 单选选择框
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "sys/modeling/main/xform/controls/placeholder/mobile/dialog/Dialog"],
		function(declare, lang, PlaceholderDialog) {
			
			return declare(
					"sys.modeling.main.xform.controls.placeholder.mobile.dialog.Dialog",
					[ PlaceholderDialog ],
					{
						isMul : true

					});
		})