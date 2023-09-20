define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
	'dojo/_base/lang',
	"dojo/dom-construct",
	'dojo/query',
	'dojo/topic',
	"dojox/mobile/SpinWheelSlot",
	'mui/dialog/Dialog'
	], function(declare,_WidgetBase,lang,domConstruct,query,topic,SpinWheelSlot,Dialog) {
		return declare("hr.staff.mobile.resource.js.staffPersonInfoListMixin", [SpinWheelSlot], {
			startup:function(){
				this.inherited(arguments);
				var self = this;
				this.on("flickAnimationEnd", function(){
					var item = self.getCenterItem();
				});
			}
		})
	})