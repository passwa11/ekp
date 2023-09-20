/**
 * 
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "mui/form/Select",
		"sys/modeling/main/xform/controls/placeholder/mobile/FormPlaceholder"], 
		function(declare, lang, Select, FormPlaceholder) {
	
		return declare("sys.modeling.main.xform.controls.placeholder.mobile.Select", [Select, FormPlaceholder], {
			
			mul:false,
			
		});
})