(function(window, undefined){
	/**
	 * jsp片断控件
	 */
	 var jspControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var html = [];
		 	html.push('<div printcontrol="true" fd_type="jsp" id="' +id);
		 	html.push('" style="">');
		 	html.push('<span class="rightIconBar" style="cursor: move; height: 10px; width: 10px; position: absolute; background-color: red;"></span>');
		 	
		 	html.push('</div>');
		 	var $newDom = $(ctrHtm.join(''));
		 	this.$domElement = $newDom;
	 	},
	 	bind:function(){
	 		var self=this;
	 		
	 		this.addListener('mousedown',function(event){
				if(self instanceof sysPrintJspControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					self.$domElement.addClass("border_selected");
					
				}
			 });
	 	}
	 });
	
	 window.sysPrintJspControl=jspControl;
	
})(window);