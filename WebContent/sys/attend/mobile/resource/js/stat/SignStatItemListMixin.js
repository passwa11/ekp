define([
    "dojo/_base/declare","dojo/topic","dojo/_base/lang",
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/SignStatItemMixin"
	], function(declare,topic,lang, _TemplateItemListMixin, SignStatItemMixin) {
	
	return declare("sys.attend.stat.SignStatItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: SignStatItemMixin,
		
		startup: function() {
			this.inherited(arguments);
			this.reload();
			//topic.subscribe('/mui/navitem/_selected',lang.hitch(this,'_onNavClick'));
		},
		
		_onNavClick:function(obj,evt){
			if(obj.moveTo=='signStatView'){
				this.reload();
			}
		}
	
	});
});