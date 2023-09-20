(function(window, undefined){
	/**
	 * 审批记录控件
	 */
	 var proccessControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" fd_type="process" style="display: inline;">$' + DesignerPrint_Lang.approvalRecord + '$</div>');
	 	},
	    bind:function(){
			var self = this;
			this.addListener('mousedown',function(event){
				if(self instanceof sysPrintProcControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					// 设置当前选中样式
					self.$domElement.addClass("border_selected");
				}
			});
		}
	 });
	
	 window.sysPrintProcControl=proccessControl;
	
})(window);