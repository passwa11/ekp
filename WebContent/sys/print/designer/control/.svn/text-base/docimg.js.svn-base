(function(window, undefined){
	/**
	 * 图片上传控件
	 */
	 var docImgControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" fd_type="docimg" style="display: inline;">$' + DesignerPrint_Lang.controlDocImgUploadName + '$</div>');
	 	}
	 });
	
	 window.sysPrintDocImgControl=docImgControl;
	
})(window);