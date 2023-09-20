define([
    "dojo/_base/declare",'dojo/date/locale','mui/i18n/i18n!sys-mobile',"dojo/query",
    'dojo/topic',"dojox/mobile/viewRegistry",'dijit/registry','dojo/dom-style',
    'dojo/_base/lang','mui/util',
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/AttendStatItemMixin"
	], function(declare,locale,muiMsg,query, topic,viewRegistry,registry,domStyle, lang,util, _TemplateItemListMixin, AttendStatItemMixin) {
	
	return declare("sys.attend.AttendStatItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: AttendStatItemMixin,
		
		startup: function() {
			this.inherited(arguments);
			//debugger;
			topic.subscribe('/mui/navitem/_selected',lang.hitch(this,'__onItemChange'));
			topic.subscribe('/mui/form/datetime/change',lang.hitch(this,'__onStatDateChange'));
			topic.subscribe('/mui/list/noData',lang.hitch(this,'__onListNoData'));
			
		},
		__onItemChange:function(obj,evt){
			var statListDateObj = registry.byId('statList_statDate');
			var statDate = locale.parse(statListDateObj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
			var fdDept = registry.byNode(query('.muiStatCriterion .muiAddressForm .muiCategory')[0]);
			obj.url = util.setUrlParameter(obj.url,"fdDate",statDate.getTime());
			obj.url = util.setUrlParameter(obj.url,"fdDeptId",fdDept.get('value'));
		},
		__onStatDateChange:function(obj,evt){
			if(obj.id=='statList_statDate'){
				if(this.listDatas){
					var statDate = locale.parse(obj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
					var scrollView = viewRegistry.getEnclosingScrollable(this.domNode);
					this.url = util.setUrlParameter(this.url,"fdDate",statDate.getTime());
					var fdDept = registry.byNode(query('.muiStatCriterion .muiAddressForm .muiCategory')[0]);
					//debugger;
					this.url = util.setUrlParameter(this.url,"fdDeptId",fdDept.get('value'));
					topic.publish('/mui/list/onReload',scrollView);
					
					topic.publish('/mui/navItem/reset/num',scrollView);
					
				}
			}

		},
		__onListNoData:function(evt){
			if(evt==this){
				var h = domStyle.get(this.domNode.parentNode.parentNode,'height');
				domStyle.set(this.tempItem.domNode,'height',h+'px');
				domStyle.set(this.tempItem.domNode,'line-height',h+'px');
			}
		}
	});
});