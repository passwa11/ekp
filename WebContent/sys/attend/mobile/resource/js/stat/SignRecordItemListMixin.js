define([
    "dojo/_base/declare","dojox/mobile/viewRegistry",'dijit/registry','dojo/date/locale',
    'dojo/topic','mui/i18n/i18n!sys-mobile','mui/util',
    'dojo/_base/lang',
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/SignRecordItemMixin"
	], function(declare,viewRegistry,registry,locale, topic,muiMsg,util, lang, _TemplateItemListMixin, SignRecordItemMixin) {
	
	return declare("sys.attend.SignRecordItemListMixin", [_TemplateItemListMixin], {
		
		startup : function(){
			this.inherited(arguments);
			topic.subscribe('/mui/form/datetime/change',lang.hitch(this,'__onSignStatDateChange'));
			topic.subscribe('sys.attend.signStat.navItem.changed',lang.hitch(this,function(evt){
				var value = evt.value;
				this.url = util.setUrlParameter(this.url,'operType',evt.value || 0);
				this.url = util.setUrlParameter(this.url,'pageno',1);
				this.reload();
			}));
		},
		
		itemTemplateString : null,
		
		itemRenderer: SignRecordItemMixin,
		
		__onSignStatDateChange : function(obj,evt){
			if(obj.id=='sign_statDate'){
				var statDate = locale.parse(obj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var scrollView = viewRegistry.getEnclosingScrollable(this.domNode);
				this.url = util.setUrlParameter(this.url,"fdDate",statDate.getTime());
				topic.publish('/mui/list/onReload',scrollView);
			}
		}
	});
});