define([
    "dojo/_base/declare","dojo/dom-class","dojo/dom-style",
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/list/item/GroupItemMixin","mui/dialog/Tip"
	], function(declare,domClass,domStyle, _TemplateItemListMixin, ItemMixin,Tip) {
	
	return declare("sys.attend.mobile.resource.js.GroupItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: ItemMixin,
		
		currentDate:new Date(),
		
		buildRendering:function(){
			this.inherited(arguments);
			this.subscribe('/mui/calendar/valueChange','saveCurrentDate');
			this.subscribe('/mui/list/noData','onCalendarNoData');
			this.subscribe('/mui/calendar/loading','onCalendarloading');
			this.subscribe('/mui/list/loaded','onCalendarloaded');
		},
	
		//选中日期改变时,缓存选中日期
		saveCurrentDate:function(widget,args){
			this.currentDate=args.currentDate;
		},
		
		onCalendarNoData:function(obj,evt){
			if(this==obj){
				var liNode = obj.tempItem.domNode;
				domClass.add(obj.domNode,'muiCalendarList');
				this.defer(function(){
					domStyle.set(liNode, {
						'height' :'auto',
						'line-height' : 'normal'
					});
				},100);
			}
		},
		
		onCalendarloading:function(evt){
			if(this==evt){
				if(!this.processing){
					this.processing = Tip.processing();
				}
				this.processing.show();
			}
		},
		
		onCalendarloaded:function(evt){
			if(this==evt){
				this.processing && this.processing.hide(false);
			}
		}
	});
});