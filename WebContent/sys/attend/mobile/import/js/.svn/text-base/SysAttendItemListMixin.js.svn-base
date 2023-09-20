define([
    "dojo/_base/declare",
    'dojo/topic',
    'dojo/_base/lang',
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/import/js/SysAttendItemMixin",
	'mui/util'
	], function(declare, topic, lang, _TemplateItemListMixin, SysAttendItemMixin,util) {
	
	return declare("sys.attend.SysAttendItemListMixin", [_TemplateItemListMixin], {
		
		startup : function(){
			this.inherited(arguments);
			topic.subscribe('sys.attend.navItem.changed',lang.hitch(this,function(evt){
				var value = evt.value;
				this.url = util.setUrlParameter(this.url,'operType',evt.value || 0);
				//this.url = util.setUrlParameter(this.url,'pageno',1);
				this.reload();
			}));
		},
		
		itemTemplateString : null,
		
		itemRenderer: SysAttendItemMixin
	});
});