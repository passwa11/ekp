define([
        "dojo/_base/declare",
        "dojox/mobile/TabBar",
        "dojo/dom-class",
        "dojo/dom-style",
        "dojo/_base/lang",
    	"dojo/dom-geometry",
    	"dojo/_base/array",
		"dojo/query",
		"dojo/NodeList-dom",
		"dojo/NodeList-traverse",
		"dojo/topic"
	], function(declare, TabBar, domClass, domStyle, lang, domGeometry, array, query, nodeList, traverse, topic) {
	
	return declare("mui.tabbar.TabBar", [TabBar], {
		
		fill: 'always',		
		
		resize: function(size){
			if(!this._resized)
			   this.resizeByGrid(size);
		},
		
		initWidth:60,
		
		intiDraftBtnWidth:0,

		startup: function() {
			this.inherited(arguments);
			// 发布事件
			topic.publish("/mui/tabbar/TabBar/startup", this);
		},
		
		resizeByGrid: function(size) {
			var i, w, h;
			var _self = this;
			// 去掉flex:1样式
			query(".mblTabBarTabBar.mblTabBar>li").forEach(
				function (node) {
					query(node).children("li");
				}
			).style("flex","none");
			if(size && size.w){
				w = size.w;
			}else{
				w = domGeometry.getMarginBox(this.domNode).w;
				var draftButtonWidth = Math.floor(w/4);
				if (!this.intiDraftBtnWidth) {
					this.intiDraftBtnWidth = draftButtonWidth;
				}
				var childLength = this.getChildren().length;
				var buttonNum = childLength;
				array.forEach(this.getChildren(), function(child, i) {
					if(child.domNode.className && child.domNode.className.indexOf("muiSplitterButton")>0){
						if(!(child.totalCopies && child.totalCopies > 0 && child.proportion && child.proportion > 0)){
							w = w - domGeometry.getMarginBox(child.domNode).w;
							childLength = childLength -1;
						}
					}
					if(child.domNode.className && child.domNode.className.indexOf("muiBarFloatLeftButton")>0){
						w = w-_self.initWidth;
						childLength = childLength -1;
					}
					if(child.domNode.className && child.domNode.className.indexOf("muiBarFloatRightButton")>0){
						w = w-_self.initWidth;
						childLength = childLength -1;
					}
					if(child.domNode.className && child.domNode.className.indexOf("muiBarSaveDraftButton")>0){
						if(!child._resized){
							if (!draftButtonWidth) {
								draftButtonWidth = this.intiDraftBtnWidth;
							}
							child.domNode.style.width = draftButtonWidth + "px";
						}
						w = w - draftButtonWidth;
						childLength = childLength -1;
					}
					if(domStyle.get(child.domNode,"display") == 'none'){
						childLength = childLength -1;
					}
				});
			}
			domClass.toggle(this.domNode, "mblTabBarNoIcons",
							!array.some(this.getChildren(), function(w){ return w.iconNode1; }));
			domClass.toggle(this.domNode, "mblTabBarNoText",
							!array.some(this.getChildren(), function(w){ return w.label; }));
			
			domClass.add(this.domNode, "muiTabBarGrid");
			var cellWidth = w;
			
			//tabbar隐藏的时候不计算赋值，解决缺陷#64344
			if(w > 0){
				cellWidth = w / childLength - 2;

				// #168562计算空白宽度，平均给所有按钮
				let childNumWidth = 0;
				let isChangeWidth = true;
				if (childLength > 5){
					array.forEach(this.getChildren(), function(child, i) {
						let doNodewidth = query(child.domNode)[0].offsetWidth;
						if (doNodewidth > 200){
							//超过200宽度按钮的不做计算
							isChangeWidth = false;
						}
					})
				}else {
					isChangeWidth = false;
				}

				array.forEach(this.getChildren(), function(child, i) {
					let doNodewidth = query(child.domNode)[0].offsetWidth;
					childNumWidth += doNodewidth;
				})
				let emptyWidthAug = (w-childNumWidth) / childLength;

				array.forEach(this.getChildren(), function(child, i) {
					let tempWidth;
					if(child.totalCopies && child.totalCopies > 0 && child.proportion && child.proportion > 0){//按钮占比
						//新的按钮占比控制
						cellWidth = (w/child.totalCopies * child.proportion);
						if((child.domNode.className.indexOf("muiSplitterButton") < 0
								&& child.domNode.className.indexOf("muiBarFloatLeftButton") < 0
								&& child.domNode.className.indexOf("muiBarFloatRightButton") < 0
								&& child.domNode.className.indexOf("muiBarSaveDraftButton") < 0) || child.domNode.className.indexOf("muiSplitterButton") >= 0 ){
							child.domNode.style.width = (cellWidth) + "px";
						}
					}else{
						if(child.domNode.className.indexOf("muiSplitterButton") < 0
								&& child.domNode.className.indexOf("muiBarFloatLeftButton") < 0
								&& child.domNode.className.indexOf("muiBarFloatRightButton") < 0
								&& child.domNode.className.indexOf("muiBarSaveDraftButton") < 0){
							if (isChangeWidth){
								tempWidth = emptyWidthAug  + query(child.domNode)[0].offsetWidth;
							}else {
								tempWidth = cellWidth;
							}
							child.domNode.style.width = (tempWidth ) + "px";
						}
					}
				});
			}
			
			
			if(size && size.w) {
				domGeometry.setMarginBox(this.domNode, size);
			}
		}
	});
});