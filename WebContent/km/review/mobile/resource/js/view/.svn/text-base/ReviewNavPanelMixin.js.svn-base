define([
	"dojo/_base/declare",
	"dojo/dom-style",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-attr"
], function(declare,domStyle,domConstruct,domClass,domAttr) {
	return declare("km.review.ReviewNavPanelMixin", null, {
		generateList : function(items){
			var _self = this;
			if(!items)
				return;
			var number = 0;
			for(var i=0; i<items.length; i++){
				var url = items[i].url;
				if(typeof enableModule != "undefined"){
					if(enableModule.enableSysCirculation=="false" && url.indexOf("/sys/circulation")>-1){
						continue;
					}else if(enableModule.enableSysReadlog=="false" && url.indexOf("/sys/readlog")>-1){
						continue;
					}
				}
				number ++;
				var text = items[i].text;
				var iconClassName = items[i].iconClassName;

				var tabItemNode = domConstruct.create(
					"li",
					{
						className: "muiNavItem",
					},
					_self.promptWgt.tabItems
				);
				var itemIconNode = domConstruct.create(
					"i",
					{
						className: "muiTabItemIcon",
					},
					tabItemNode
				);
				domConstruct.create(
					"span",
					{
						className: "muiTabItemText",
						innerHTML:text
					},
					tabItemNode
				);
				domAttr.set(tabItemNode,"data-url",url);
				domClass.add(itemIconNode,iconClassName);
			}
			if(number==0){
				domStyle.set(this.moreNavItemBtnNode,{"display":"none"});
				domStyle.set(this.moreNavItemBtnNode.parentNode,{"padding-right":"0"});
			}
		}
	})
})
