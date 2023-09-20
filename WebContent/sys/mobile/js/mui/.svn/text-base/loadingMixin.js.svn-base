define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style"],
	function(declare, domConstruct, domClass, domStyle) {
		return declare("mui.loadingMixin", null, {
			
			isDestroy : false,
			
			// 构建加载中图标，优化体验
			buildLoading : function(paretNode) {

				this.loadingNode = domConstruct.create("div", {
					className : 'muiLoadingTextContainer'
				}, paretNode);
				
				// 显示3个
				for(var i = 0; i < 3; i++) {
					
					var muiLoadingText = domConstruct.create("div", {
						className : 'muiLoadingText'
					}, this.loadingNode);
					
					domConstruct.create("div", {
						className : 'muiLoadingTextTitle'
					}, muiLoadingText);
					
					domConstruct.create("div", {
						className : 'muiLoadingTextContent',
						innerHTML : '<i></i><span></span>'
					}, muiLoadingText);
				}
				
				this.muiLoadingTextBox = domConstruct.create("div", {
					className : 'muiLoadingTextBox'
				}, this.loadingNode);

				domClass.add(paretNode, "loading");

				this.animation();
				
			},
			
			
			
			animation : function () {

				var self = this;
				
				this.timeout = setTimeout(function(){
					domStyle.set(self.muiLoadingTextBox, {
						'left' : '100%'
					});
				}, 0);
				
				this.interval = setInterval(function(){

					domStyle.set(self.muiLoadingTextBox, {
						'transition' : 'all 0s ease',
						'-webkit-transition' : 'all 0s ease'
					});
					domStyle.set(self.muiLoadingTextBox, {
						'left' : '-110%'
					});
					
					setTimeout(function(){
						domStyle.set(self.muiLoadingTextBox, {
							'transition' : 'all 3s ease',
							'-webkit-transition' : 'all 3s ease'
						});
						domStyle.set(self.muiLoadingTextBox, {
							'left' : '100%'
						});
					}, 1);
					
				}, 3500);
			},

			// 销毁加载中图标
			destroyLoading : function(paretNode) {
				
				if (this.loadingNode) {
					
					domConstruct.destroy(this.loadingNode);
					
					domClass.remove(paretNode, "loading");
					
					clearTimeout(this.timeout);
					
					clearInterval(this.interval);
					
				}
				
			}
		}
	)
})
