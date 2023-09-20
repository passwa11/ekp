/**
 * 
 */
define([ "dojo/_base/declare", "sys/xform/mobile/controls/EventDataDialog", "dijit/registry"], 
		function(declare, EventDataDialog, registry) {
	var claz = declare("sys.modeling.main.xform.controls.placeholder.mobile.dialog.DataDialog", [EventDataDialog], {
		
		//模板地址
		templURL : "sys/modeling/main/xform/controls/placeholder/mobile/dialog/dataDialogRender.tmpl"
	});
	return claz;
});