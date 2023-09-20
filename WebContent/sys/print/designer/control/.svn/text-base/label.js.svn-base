(function(window, undefined){
	/**
	 * 文本框
	 */
	 var labelControl=sysPrintDesignerControl.extend({
		 render:function(config){
			if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var text=DesignerPrint_Lang.controlLabelEditTip;
		 	if(config && config.text){
		 		text=config.text;
		 	}
		 	this.$domElement=$('<label class="xform_label" printcontrol="true" fd_type="label" id="'+id+'" style="font-style: normal; display: inline; font-weight: normal; TEXT-DECORATION: none">'+text+'</label>');
		 	
	 	},
	 	bind:function(){
	 		var self=this;
	 		//双击编辑
	 		this.$domElement.dblclick(function(){
	 			var labeledit=$(this);
	 		   if (labeledit.children("textarea").length > 0) {
	 			    return false;
	 		   	}
	 		   	var inputIns = $("<textarea rows='5' class='inputsgl'>");  //创建 输入框
	 		    var oldtext = $(this).html();        //保存原有的值
	 		    
	 		    inputIns.width(labeledit.width()+50).height(labeledit.height()+20); //设置INPUT与DIV宽度一致
	 		    inputIns.html(labeledit.html().replace(/<br>/g, "\n")); //将本来单元格DIV内容copy到插入的文本框INPUT中
	 		    labeledit.html(""); //删除原来单元格DIV内容
	 		    inputIns.appendTo(labeledit).focus().select(); //将需要插入的输入框代码插入DOM节点中
	 		    inputIns.click(function () {
	 		    	return false;
	 		   });
	 		    //键盘、鼠标事件
	 		   inputIns.keyup(function(){
	 			  var keycode = event.which;
	 			    if (keycode == 13) {
	 			    	//labeledit.text($(this).val());         //设置新值
	 			    }
	 			    if (keycode == 27) {
	 			    	labeledit.html(oldtext.replace(/\n/g, "<br>"));          //返回旧值
	 			    }
	 		   }).blur(function(){
	 			  labeledit.html($(this).val().replace(/&/g,"&amp;")
					   .replace(/</g,"&lt;")
					   .replace(/>/g,"&gt;")
					   .replace(/\"/g,"&quot;")
					   .replace(/¹/g, "&sup1;")
					   .replace(/²/g, "&sup2;")
					   .replace(/³/g, "&sup3;").replace(/\n/g, "<br>"));
	 		   });
	 		});

			this.addListener('mousedown',function(event){
				if(self instanceof sysPrintLabelControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					self.$domElement.addClass("border_selected");
					
					//文本属性
					self.onSelectControl();
				}
			 });
	 	},

	 	onSelectControl:function() {
	 		//页面字体类型
			var select = document.getElementById('_designer_font_style_');
			var fontFamily = this.options.attrs.fontFamily;
			select.value = fontFamily ? fontFamily : '';
			//字号
			select = document.getElementById('_designer_font_size_');
			var fontSize = this.options.attrs.fontSize;
			select.value = fontSize ? fontSize : '';
		},
	 	
	 	setAttr:function(builder,type,value){
	 		var self = this;
	 		var $selectDom = builder.$selectDomArr[0];
			var selectControl = builder.selectControl;
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
	
	 window.sysPrintLabelControl=labelControl;
	
})(window);