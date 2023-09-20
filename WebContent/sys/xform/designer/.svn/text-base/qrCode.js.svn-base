/**
 * 二维码控件
 * @作者：李文昌 @日期：2018年08月28日  
 */
Designer_Config.operations['qrCode']={
		lab : "5",
		imgIndex : 67,
		title:Designer_Lang.qrCode_name,
		run : function (designer) {
			designer.toolBar.selectButton('qrCode');
		},
		type : 'cmd',
		order: 10,
		shortcut : 'N',
		select: true,
		cursorImg: 'style/cursor/qrCode.cur'
};

Designer_Config.controls.qrCode= {
		type : "qrCode",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_QRCode_OnDraw,
		drawMobile : _Designer_Control_QRCode_DrawMobile,
		drawXML : _Designer_Control_QRCode_DrawXML,
		onDrawEnd : _Designer_Control_QRCode_OnDrawEnd,
		implementDetailsTable : false,
		attrs : {
			label : Designer_Config.attrs.label,
			mold : {//类型
				text: Designer_Lang.qrCode_attr_mold,
				value : '11',
				type : 'select',
				opts: [
					{name: 'hyperlink', text: Designer_Lang.qrCode_mold_hyperlink, value:'11'}//超链接
				],
				onchange:'_Designer_Control_Attr_Mold_Change(this)',
				show: true
			},
			valType : {
				text: Designer_Lang.qrCode_attr_valType,
				value : '11',
				type : 'select',
				opts: [
					{name: 'docURL', text: Designer_Lang.qrCode_mold_docURL, value:'11'},//文档链接
					{name: 'custom', text: Designer_Lang.qrCode_mold_custom, value:'12'}//自定义链接
				],
				onchange:'_Designer_Control_Attr_ValType_Change(this)',
				show: true
			},
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "140",
				type: 'text',
				show: true,
				validator: [Designer_Control_Attr_Int_Validator,Designer_Control_Attr_Width_Validator],
				checkout: [Designer_Control_Attr_Int_Checkout,Designer_Control_Attr_Width_Checkout]
			},
			height: {
				text: Designer_Lang.div_height,
				value: "140",
				type: 'text',
				validator: [Designer_Control_Attr_Int_Validator],
				show: true,
				checkout: [Designer_Control_Attr_Int_Checkout]
			},
			content : {
				text: Designer_Lang.controlQRCodeAttrContent,
				value: "",
				type: 'self',
				show: true,
				lang: true,
				synchronous: true,//多表单是否同步
				draw: _Designer_Control_QRCode_Context_Draw,
				convertor: Designer_Control_Attr_HtmlEscapeConvertor
			},
			isShowDownLoadButton : {
				text: Designer_Lang.controlAttrIsShowDownLoadButton,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			}
		},
		info : {
			name: Designer_Lang.controlQRCodeInfoName
		},
		resizeMode : 'no'  //尺寸调整模式(onlyWidth, onlyHeight, all, no)
};

Designer_Config.buttons.tool.push("qrCode");
Designer_Menus.tool.menu['qrCode'] = Designer_Config.operations['qrCode'];

/**
 * [_Designer_Control_QRCode_OnDraw description]
 * @param  {[type]} parentNode [description]
 * @param  {[type]} childNode  [description]
 * @return {[type]}            [description]
 */
function _Designer_Control_QRCode_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	$(domElement).attr("id",this.options.values.id);
	$(domElement).attr("label",_Get_Designer_Control_Label(this.options.values, this));
	//背景样式
	with(domElement.style) {
		width='24px';
		display = 'inline-block';
		textAlign = "left";
	}
	var values =this.options.values;
	//设置宽度
	if (!values.width) {
		values.width = this.attrs.width.value;
	}
	//设置高度
	if (!values.height) {
		values.height = this.attrs.height.value;
	}
	if (!values.mold){
		values.mold = this.attrs.mold.value;
	}
	if (!values.valType){
		values.valType = this.attrs.valType.value;
	}
	//背景图标
	var label = document.createElement("label");
	$(label).addClass("qrCodeControl");
	domElement.appendChild(label);
}

/**
 * [_Designer_Control_Attr_Mold_Change description]
 * @param  {[type]} moldSelect [description]
 * @return {[type]}            [description]
 */
function _Designer_Control_Attr_Mold_Change(moldSelect){
	
}

/**
 * [_Designer_Control_Attr_ValType_Change description]
 * @param  {[type]} valTypeSelect [description]
 * @return {[type]}                [description]
 */
function _Designer_Control_Attr_ValType_Change(valTypeSelect){
	var val = $(valTypeSelect).val();
	var content = $("textarea[name='content']");
	var tr = content.closest("tr");
	if (val === "12"){
		tr.show();
	}else{
		tr.hide();
	}
}

function _Designer_Control_QRCode_OnDrawEnd(){
	
}

/**
 * 二维码链接绘制函数
 * @param name
 * @param attr
 * @param value
 * @param panelForm
 * @param attrs
 * @param values
 * @param control
 * @returns
 */
function _Designer_Control_QRCode_Context_Draw(name, attr, value, panelForm, attrs, values, control) {
	var display = "display:none;";
	if (values.valType === "12"){
		display = "";
	}
	var html = "<textarea style='width:93%;' name='" + name + "' title='" + (attr.hint ? attr.hint : "")
		+ "' class='attr_td_textarea'>";
	if (value != null && value.length > 0) {
		html += value;
	}
	html += "</textarea>";
	if (attr.required == true) {
		html += '<span class="txtstrong">*</span>';
	}
	if (attr.hint) {
		html = '<div id="attrHint_' + name + '">' + attr.hint + '</div>' + html;
	}
	return ('<tr  style="' + display + '"><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
}

function _Designer_Control_QRCode_DrawXML(){
	var values = this.options.values;
	var content = {url:values.content,mold:values.mold,valType:values.valType};
	content.isShowDownLoadButton=values.isShowDownLoadButton;
	content = JSON.stringify(content);
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="String" ');
	buf.push('store="false" ');
	buf.push('customElementProperties="', Designer.HtmlEscape(content), '" ');
	buf.push('businessType="', this.type, '" ');
	buf.push('/>');
	return buf.join('');
		
}