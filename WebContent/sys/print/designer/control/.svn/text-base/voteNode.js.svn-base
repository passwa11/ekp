(function(window, undefined){
	/**
	 * 二维码控件
	 */
	 var voteNodeControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" ' + id + ' fd_type="voteNode" style="display: inline-block;width: 24px;"><label class="voteNodeControl"></label></div>');
	 	},
		 bind:function(){
				var self = this;
				this.addListener('mousedown',function(event){
					if(self instanceof sysPrintVoteNodeControl){
						//清空并重设选中控件
						self.builder.setSelectDom(self.$domElement);
						self.builder.selectControl=self;
						// 设置当前选中样式
						self.$domElement.addClass("border_selected");
					}
				});
			}
	 });
	
	 window.sysPrintVoteNodeControl=voteNodeControl;
	
})(window);