(function(window, undefined){
	/**
	 * 携程控件
	 */
	 //订票控件
	 var bookticketControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var html = [];
		 	//var imgsrc = Com_Parameter.ContextPath +"sys/print/designer/style/img/btn_img_bg.png";
		 	html.push('<div id="' + id + '" printcontrol="true" fd_type="bookticket" style="display:inline-block;">');
		 	html.push('<img />');
		 	html.push('</div>');
		 	this.$domElement=$(html.join(''));
	 	}
	 });
	 //预订信息控件
	 var bookinfoControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	this.$domElement=$('<div printcontrol="true" fd_type="bookinfo" style="display: inline;">$' + DesignerPrint_Lang.bookticketInfo + '$</div>');
	 	}
	 });
	
	 window.sysPrintBookticketControl=bookticketControl;
	 window.sysPrintBookinfoControl=bookinfoControl;
	
})(window);