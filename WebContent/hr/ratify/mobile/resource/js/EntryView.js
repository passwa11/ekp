
define([
	'dojo/_base/declare', 
	'dojo/_base/lang', 
	"dojo/on",
	'dojox/mobile/_StoreListMixin', 
	'mui/store/JsonRest','mui/store/JsonpRest',
	'dojox/mobile/viewRegistry',
	'dojo/when',
	'dojo/topic',
	'mui/util'
	], function(declare, lang, on,
		_StoreListMixin, JsonStore, JsonpStore ,viewRegistry, when, topic, 
		util) {
	
	return declare("hr.ratify.mobile.EntryView", null, {
		startup : function() {
			this.inherited(arguments);
			if(this._started){ return; }

		}
	});
});