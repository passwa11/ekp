/**
 * 多页签控件
 */
(function(window, undefined){
	var mutiTabControl = sysPrintDesignerControl.extend({
		 render:function(config){
			 if(config && config.renderLazy) return;
		 }
	});
	window.sysPrintDesignerMutiTabControl = mutiTabControl;
})(window);