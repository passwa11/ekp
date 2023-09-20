(function(window, undefined){
	/**
	 * div布局控件
	 */
	 var divControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var html = [];
		 	html.push('<div printcontrol="true" fd_type="divcontrol" id="' +id);
		 	html.push('" style="border: 1px solid rgb(210, 210, 210); padding: 0px; width: 100%; height: auto; overflow: auto;">');
		 	html.push('<div style="border: 1px solid rgb(221, 221, 221); color: rgb(157, 160, 164); font-size: 12px; font-weight: bold; left: -1px; font-style: italic; padding: 2px; top: -1px; width: 40px; height: 15px; float: left; background-color: rgb(245, 245, 245);">DIV</div>');
		 	
		 	html.push('</div>');
		 	var $newDom = $(ctrHtm.join(''));
		 	this.$domElement = $newDom;
	 	},
	 	bind:function(){
	 	}
	 });
	
	 window.sysPrintDivControl=divControl;
	
})(window);