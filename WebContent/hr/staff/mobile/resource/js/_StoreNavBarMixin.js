define(["dojo/_base/declare","dojo/dom-attr", "dojo/dom-class","dojo/dom-style","dojo/dom-construct","dojo/query",
				 "dojo/_base/array", 
				"mui/store/JsonRest", "dojo/topic", "mui/util", 'dojo/_base/lang'], function(
				declare,domAttr, domClass,domStyle, domConstruct,query, array, JsonRest, topic,
				util, lang) {
			var cls = declare('hr.staff.mobile._StoreNavBarMixin', null, {

				buildRendering : function() {
					this.inherited(arguments);
					
				},

				onComplete : function(items) {
					this.inherited(arguments);
					console.log(items)
				}
			});
			return cls;
		});