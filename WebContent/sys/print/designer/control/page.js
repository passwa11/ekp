(function(window, undefined){
	/**
	 * 分页控件
	 */
	 var pageControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<hr printcontrol="true" fd_type="page" sysPrintpage="true" />');
	 	},
	 
	 	bind:function(){
	 		var self = this;
	 		this.addListener('mousedown',function(event){
				if(self instanceof sysPrintPageControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					self.$domElement.addClass("border_selected");
					
				}
			 });
	 	},
	 	
	 	attach:function($attach){
	 		//分页符不允许添加到table首行
	 		var ctrl = sysPrintUtil.getPrintDesignElement($attach[0]);
			var p = $(ctrl).parent();
			if(p[0].id=='sys_print_designer_draw'){
				var rows = $attach.attr('row').split(',');
				if(rows[0]=='0') return;
			}
			$attach.append(this.$domElement);
	 	}
	 });
	
	 window.sysPrintPageControl=pageControl;
	
})(window);