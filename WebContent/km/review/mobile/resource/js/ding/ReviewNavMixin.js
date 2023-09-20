define(['dojo/_base/declare',"mui/util","dojo/dom-style"], function(declare, util,domStyle) {
	
		return declare('km.review.ding.ReviewNavMixin', null, {
			
			startup : function() {
				if (this._started)
					return;
				
				this.inherited(arguments);
				this.defer(function(){
					domStyle.set(this.domNode,'display','none');
				},1)
			},
			
			// 格式化数据
			_createItemProperties : function(item) {
				if (this.isTiny) {
					item['text'] = item[dojoConfig.locale] || item.text;
				}
				return item;
			}
		
		});
});