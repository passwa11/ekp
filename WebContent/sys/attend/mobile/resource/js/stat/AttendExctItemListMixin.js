define([
    "dojo/_base/declare",'dojo/date/locale','mui/i18n/i18n!sys-mobile',
    'dojo/topic',"dojox/mobile/viewRegistry",'dijit/registry',
    'dojo/_base/lang','mui/util',"dojo/query",
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/AttendExcItemMixin"
	], function(declare,locale,muiMsg, topic,viewRegistry,registry, lang,util,query, _TemplateItemListMixin, AttendExcItemMixin) {
	
	return declare("sys.attend.AttendExctItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: AttendExcItemMixin,
		
		startup: function() {
			this.inherited(arguments);
			this.subscribe("/mui/list/loaded",'refreshExcCount');
		},
		
		refreshExcCount : function(obj,evt){
			if(this==obj){
				query('.muiExcListHeading .title em')[0].innerHTML ="(" + obj.totalSize +")"
			}
		}
		
	});
});