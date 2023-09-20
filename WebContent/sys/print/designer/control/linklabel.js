(function(window, undefined){
	/**
	 * 链接控件
	 */
	 var linkLabelControl=sysPrintLabelControl.extend({
		 render:function(config){
			if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var text=DesignerPrint_Lang.controlLinkName;
		 	if(config && config.text){
		 		text=config.text;
		 	}

		 	var html = [];
		 	html.push('<label printcontrol="true" fd_type="label" id="' +id);
		 	html.push('" style="font-style: normal; display: inline; font-weight: normal; TEXT-DECORATION: none">');
		 	html.push('<a href="#" _target="_blank" _href="" style="font-weight: normal; font-style: normal; text-decoration: underline;">');
		 	html.push(text);
		 	html.push('</a>');
		 	html.push('</label>');
		 	var $newDom = $(ctrHtm.join(''));
		 	this.$domElement = $newDom;
	 	},
	 	bind:function(){
	 		var self=this;
			this.addListener('mousedown',function(event){
				if(self instanceof linkLabelControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					self.$domElement.addClass("border_selected");
					
					//文本属性
					self.onSelectControl();
				}
			 });
	 	},
	 	setAttr:function(builder,type,value){
	 		 var self = this;
	 		 var $selectDom = builder.$selectDomArr[0];
			 var selectControl = builder.selectControl;
			 $selectDom = $selectDom.children('a');
			 var attrs = selectControl.options.attrs;
			 switch(type){
			 	case 'bold':
			 		if(attrs.fontWeight=='bold'){
						 $selectDom.css('font-weight','');
						 attrs.fontWeight='';
					 }else{
						 $selectDom.css('font-weight','bold');
						 attrs.fontWeight='bold';
					 }
			 		break;
			 	case 'italic':
			 		if(attrs.fontStyle=='italic'){
						 $selectDom.css('font-style','');
						 attrs.fontStyle='';
					 }else{
						 $selectDom.css('font-style','italic');
						 attrs.fontStyle='italic';
					 }
			 		break;
			 	case 'underline':
			 		if(attrs.textDecoration=='underline'){
						 $selectDom.css('text-decoration','');
						 attrs.textDecoration='';
					 }else{
						 $selectDom.css('text-decoration','underline');
						 attrs.textDecoration='underline';
					 }
			 		break;
			 	case 'fontColor':
			 		$selectDom.css('color',value);
			 		break;
			 	case 'fontFamily':
			 		$selectDom.css('font-family',value);
			 		attrs.fontFamily=value;
			 		break;
			 	case 'fontSize':
			 		$selectDom.css('font-size',value);
			 		attrs.fontSize=value;
			 		break;
			 }
			 
			 
	 	}
	 });
	
	 window.sysPrintLinkLabelControl=linkLabelControl;
	
})(window);