define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "mui/rating/Rating","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ], function(declare, domConstruct,
		domClass, domStyle, domAttr, ItemBase, util, Praise,Msg) {
	var item = declare("sys.circulation.CirculationStatusItemMixin", [ ItemBase ], {
		
		tag : "div",
		
		text:"",
		
		value:"",
		
		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'status'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},
		buildInternalRender : function() {
			console.log("1233");
			
		},

		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
		},

		_setLabelAttr : function(text) {
			if (text)
				this._set("label", text);
		}
	});
	return item;
});