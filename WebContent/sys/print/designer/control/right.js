(function(window, undefined){
	/**
	 * 权限控件
	 */
	 var rightControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	//this.$domElement=$('<div sysPrintProc="true" fd_type="right" sysPrintpage="true" >');
		 	var html = [];
		 	html.push('<div printcontrol="true" fd_type="right" id="' +id);
		 	html.push('" style="BORDER-TOP: orange 1px dotted; HEIGHT: 18px; BORDER-RIGHT: orange 1px dotted; WIDTH: 100%; BORDER-BOTTOM: orange 1px dotted; PADDING-BOTTOM: 1px; PADDING-TOP: 1px; PADDING-LEFT: 1px; MIN-HEIGHT: 18px; BORDER-LEFT: orange 1px dotted; PADDING-RIGHT: 1px">');
		 	html.push('<div class="rightIconBar" style="cursor: move; height: 10px; width: 10px; position: absolute; background-color: red;"></div>');
		 	
		 	html.push('</div>');
		 	var $newDom = $(ctrHtm.join(''));
		 	this.$domElement = $newDom;
	 	},
	 	bind:function(){
	 		var self=this;
	 		
	 		this.addListener('mousedown',function(event){
				if(self instanceof sysPrintRightControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					self.$domElement.addClass("border_selected");
					
				}
			 });
	 		
	 		this.addListener('dropStop',function(event,ui){
				if(self instanceof sysPrintRightControl){
					if(event.target.innerHTML=='&nbsp;'){
						$(event.target).empty();
					}
					$(event.target).append(ui.draggable);
					ui.draggable.css({
						position:'static',left:'auto',top:'auto'
					});
					setTimeout(function(){
						ui.draggable.css({
							position:'relative'
						});
					},350)
				}
			});
	 	}
	 });
	
	 window.sysPrintRightControl=rightControl;
	
})(window);