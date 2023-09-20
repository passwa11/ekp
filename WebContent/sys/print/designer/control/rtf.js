(function(window, undefined){
	/**
	 * 富文本框控件
	 */
	 var rtfControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<img printcontrol="true" fd_type="rtf" style="font-style: normal; width: 100px; display: inline; font-weight: normal; TEXT-DECORATION: none" />');
	 	}
	 });
	
	 window.sysPrintRtfControl=rtfControl;
	
})(window);