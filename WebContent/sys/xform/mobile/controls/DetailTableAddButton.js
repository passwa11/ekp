define([ "dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-construct",
		"mui/dialog/Dialog", "mui/device/adapter", "dojo/_base/lang",
		"mui/util", "dojo/dom-attr", "mui/i18n/i18n!sys-mobile","dojo/query", "dojo/dom-style" ], 
		function(declare, TabBarButton, domConstruct, Dialog, adapter, lang, util,domAttr, Msg,query,domStyle) {

	return declare("sys.xform.mobile.controls.DetailTableAddButton", [ TabBarButton ], {
		
		icon1 : "fontmuis muis-new",
		
		baseClass : 'muiDetailTableAdd',
		
		startup : function() {
			this.inherited(arguments);
			var hideIcon = this.domNode.getAttribute("_hideIcon");
			if (hideIcon == "true") {
				var iconNodes = query('.mblTabBarButtonIconArea',this.domNode);
				if (iconNodes.length == 1) {
					domStyle.set(iconNodes[0],'display','none');
				}
			}
		}
		
	});
});