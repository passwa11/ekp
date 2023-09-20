/**
 * 多选树
 */
define([ "dojo/_base/declare", "sys/modeling/main/xform/controls/placeholder/mobile/tree/Tree"],
	function(declare, Tree) {
		
		return declare(
			"sys.modeling.main.xform.controls.placeholder.mobile.tree.MultiTree",
			[ Tree ],
			{
				isMul : true
			});
	})