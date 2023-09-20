(function(window, undefined){
	/**
	 * 模板上传控件
	 */
	 var uploadTemplateAttachmentControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
	 	},
		 bind:function(){
				var self = this;
				this.addListener('mousedown',function(event){
					if(self instanceof uploadTemplateAttachmentControl){
						//清空并重设选中控件
						self.builder.setSelectDom(self.$domElement);
						self.builder.selectControl=self;
						// 设置当前选中样式
						self.$domElement.addClass("border_selected");
					}
				});
			}
	 });
	
	 window.sysPrintUploadTemplateAttachmentControl=uploadTemplateAttachmentControl;
	
})(window);