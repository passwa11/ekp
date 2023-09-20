(function(window, undefined){
	/**
	 * 复合控件
	 */
	 var compositeControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
	 	},
		 bind:function(){
				var self = this;
				this.addListener('mousedown',function(event){
					if(self instanceof compositeControl){
						//清空并重设选中控件
						self.builder.setSelectDom(self.$domElement);
						self.builder.selectControl=self;
						// 设置当前选中样式
						self.$domElement.addClass("border_selected");
					}
				});
			}
	 });
	
	 window.sysPrintCompositeControl=compositeControl;
	
})(window);