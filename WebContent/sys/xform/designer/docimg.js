/**********************************************************
功能：图片上传控件
使用：
	
作者：曹映辉
**********************************************************/
Designer_Config.controls['docimg'] = {
	type : 'docimg',
	storeType : 'field',
	inherit    : 'base',
	implementDetailsTable : true,
	onDraw : _Designer_Control_DocImg_OnDraw,
	drawMobile : _Designer_Control_DocImg_DrawMobile,
	drawXML : _Designer_Control_DocImg_DrawXML,
	attrs : {
		label : {
			text : Designer_Lang.controlAttrLabel,
			value: "",
			type: 'label',
			show: true,
			required: true,
			validator: [Designer_Control_Attr_Required_Validator,Designer_Control_Attr_Label_Validator],
			checkout: [Designer_Control_Attr_Required_Checkout,Designer_Control_Attr_Label_Checkout]
		},
		required: {
			text: Designer_Lang.controlAttrRequired,
			value: "false",
			type: 'checkbox',
			checked: false,
			show: true
		},
		width : {
			text : Designer_Lang.controlDocImg_attr_width,
			value: "200",
			type: 'text',
			validator: Designer_Control_Attr_Int_Validator,
			checkout: Designer_Control_Attr_Int_Checkout,
			show: true
		},
		height : {
			text : Designer_Lang.controlDocImg_attr_height,
			value: "150",
			type: 'text',
			validator: Designer_Control_Attr_Int_Validator,
			checkout: Designer_Control_Attr_Int_Checkout,
			show: true
		},
		fdMulti: {
			text : Designer_Lang.controlDocImg_imgType,
			value: 'false',
			type: 'radio',
			hint: Designer_Lang.controlDocImg_FileTypeHint,
			opts: [
				{text:Designer_Lang.controlDocImg_TypeSingle,value:'false'},
				{text:Designer_Lang.controlDocImg_TypeMuti,value:'true'}
			],
			show: true
		},
		mobilePicDisplaythumb: {
			text : Designer_Lang.controlDocImg_mobilePicDisplay,
			value: 'true',
			type: 'radio',
			opts: [
				{text:Designer_Lang.controlDocImg_thumbnail,value:'true'},
				{text:Designer_Lang.controlDocImg_orginSize,value:'false'}
			],
			show: true
		},
		hidePicName: {
			text : Designer_Lang.controlDocImg_hidePicName,
			value: 'false',
			type: 'radio',
			opts: [
				{text:Designer_Lang.truee,name: 'hidePicName',value:'true'},
				{text:Designer_Lang.falsee,name: 'hidePicName',value:'false'}
			],
			show: true
		}
	},
	info : {
		name: Designer_Lang.controlDocImg_info_name
	},
	resizeMode : 'no'
};
Designer_Config.operations['docimg'] = {
		lab : "2",
		imgIndex : 57,
		title : Designer_Lang.controlDocImg_tittle,
		run : function (designer) {
			designer.toolBar.selectButton('docimg');
		},
		type : 'cmd',
		order: 11.5,
		select: true,
		cursorImg: 'style/cursor/docimg.cur'
	};
Designer_Config.buttons.form.push("docimg");

function _Designer_Control_DocImg_OnDraw(parentNode, childNode){
	var values = this.options.values;
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	
	var width=(function(){ 
		if(!values.width){
			values.width=200;
		}
		return values.width+"px";
	})();
	var height=(function(){ 
		if(!values.height){
			values.height=150;
		}
		//高度最小不能小于25px,除去
		if(parseInt(values.height)<25){
			values.height=25;
			return 0+"px";
		}
		return (parseInt(values.height)-25)+"px";
	})();
	
	if(values.id == null)
		values.id = "fd_" + Designer.generateID();
	domElement.setAttribute('id', values.id);
	domElement.setAttribute('fdMulti', values.fdMulti=='true'?'true':'false');
	domElement.setAttribute('hidePicName', values.hidePicName=='true'?'true':'false');
	domElement.setAttribute('label', _Get_Designer_Control_Label(this.options.values, this));
	domElement.setAttribute('required', values.required==null?'false':values.required);
	$(domElement).width(values.width);
	$(domElement).attr("width",values.width);
	$(domElement).attr("height",values.height);
	domElement.setAttribute('mobilePicDisplaythumb', values.mobilePicDisplaythumb=='false'?'false':'true');
	domElement.setAttribute('class', "lui_upload_img_box");
	
	if (this.parent != null) {
		domElement.setAttribute('tableName', _Get_Designer_Control_TableName(this.parent));
	}
	var buf = [];
	buf.push("<div class='lui_upload_img_item lui_upload_img'  style='width:100%' >");
	buf.push("<span class='lui_upload_img_txt' style='width:"+values.width+"px;height:"+values.height+"px'><i class='icon icon-plus' style='margin-top:"+ (values.height/2-30)+"px'></i>"+Designer_Lang.controlDocImg_info_name);
	if(values.required=='true'){
		buf.push("<span class='txtstrong'> *</span>");
	}
	buf.push("</span></div>");
	domElement.innerHTML = buf.join('');
}

function _Designer_Control_DocImg_DrawXML() {
	
	var values = this.options.values;
	var buf = ['<attachmentProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="','docimg','" ');
	buf.push('multi="',values.fdMulti,'" ');

	buf.push('/>');
	return buf.join('');
}