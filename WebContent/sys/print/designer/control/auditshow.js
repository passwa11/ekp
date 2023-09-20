(function(window, undefined){
	/**
	 * 审批意见控件
	 */
	 var auditShowControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" fd_type="auditShow" style="font-style: normal; width: 100px; display: inline; font-weight: normal; TEXT-DECORATION: none">$' + DesignerPrint_Lang.auditShowName + '$</div>');
	 	}
	 });
	
	 window.sysPrintAuditShowControl=auditShowControl;
	
})(window);