/**
 * 
 */
define(['dojo/_base/declare',"dojo/dom-style", "sys/mportal/mobile/extend/MenuSlide", "dojo/dom-geometry","dojo/_base/lang", "dojo/dom-class"],
		function(declare,domStyle, MenuSlide, domGeometry,lang,domClass){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.common.menuSlide', [MenuSlide] , {
		
		isCalSwitchHeight : true,	// 是否计算滑动条的高度
		
		source: function() {
			// 数据来源不需要发送请求
		},

		_createItem: function(child, swap, index) {
			var pros = child
			if (this.itemBaseClass) {
				pros = lang.mixin(
					{
						baseClass: this.itemBaseClass,
						numberColor:this.numberColor,
						textColor:this.textColor
					},
					child
				)
			}
			var menuItem = new this.itemRenderer(pros)
			if (!this.__items) {
				this.__items = []
			}
			this.__items.push(menuItem)
			swap.domNode.appendChild(menuItem.domNode)
			//#53362 需求要求跟平铺快捷方式保持一致，8个为一次循环，目前CSS没办法做到，改用JS添加类名
			domClass.add(menuItem.domNode, "MenuModuleIndex" + (index % 8))
			this.itemGrometry(menuItem.domNode)
			return menuItem
		},
		
	    initDomNodeHeight : function(){
	    	var height = 0
	        if (this.itemPosition) {
	          height += this.rows * this.itemPosition.h
	        }
	        if (this.isCalSwitchHeight && this.swithNode) {
	          height += domGeometry.position(this.swithNode).h;
	        }
	        domStyle.set(this.domNode, "height", height + "px")
	    }
		
	});
});