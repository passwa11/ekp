(function(window, undefined){
	/**
	 * 大数据控件
	 */
	 var massDataControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" ' + id + ' fd_type="massData"><img src="style/massdata.png" style="width: 100%;"></div>');
	 	},
		 bind:function(){
				var self = this;
				this.addListener('mousedown',function(event){
					if(self instanceof massDataControl){
						//清空并重设选中控件
						self.builder.setSelectDom(self.$domElement);
						self.builder.selectControl=self;
						// 设置当前选中样式
						self.$domElement.addClass("border_selected");
					}
				});
			}
	 });
	
	 window.sysPrintMassDataControl=massDataControl;
	
})(window);