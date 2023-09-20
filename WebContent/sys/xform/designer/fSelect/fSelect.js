/**********************************************************
功能：复选下拉框控件
使用：
	
作者：李文昌
创建时间：2018-09-17
**********************************************************/
Designer_Config.operations['fSelect']={
		lab : "2",
		imgIndex : 69,
		title : Designer_Lang.buttonsFSelect,
		run : function (designer) {
			designer.toolBar.selectButton('fSelect');
		},
		type : 'cmd',
		order: 6.2,
		shortcut : 'S',
		sampleImg : 'style/img/select.jpg',
		select: true,
		cursorImg: 'style/cursor/fSelect.cur'
};


Designer_Config.buttons.form.push("fSelect");
Designer_Menus.form.menu['fSelect'] = Designer_Config.operations['fSelect'];

Designer_Config.controls.fSelect = {
		type : "fSelect",
		storeType : 'field',
		inherit : 'baseStyle',
		onDraw : _Designer_Control_Select_OnDraw,
		drawMobile : _Designer_Control_FSelect_DrawMobile,
		drawXML : _Designer_Control_Select_DrawXML,
		mobileAlign : "right",
		implementDetailsTable : true,
		attrs : {
			label : Designer_Config.attrs.label,
			canShow: {
				text: Designer_Lang.controlAttrCanShow,
				value: "true",
				type: 'hidden',
				checked: true,
				show: true
			},
			required: {
				text: Designer_Lang.controlAttrRequired,
				value: "true",
				type: 'checkbox',
				checked: false,
				onclick: '_Designer_Control_Select_Onclick(this)',
				show: true
			},
			isMark: {
				text: Designer_Lang.controlAttrIsMark,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			},
			summary: {
				text: Designer_Lang.controlAttrSummary,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			},
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			encrypt : Designer_Config.attrs.encrypt,
			dataType : {
				text: Designer_Lang.controlAttrDataType,
				value: "String",
				opts: [{text:Designer_Lang.controlAttrDataTypeText,value:"String"}], //{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"}
				show: true,
				synchronous: true,
				type: 'select'
			},
			leastNItem : {
				text: Designer_Lang.controlFSelectAttrLeastNItem,
				value : 1,
				type : "self",
				show : true,
				draw: _Designer_Control_FSelect_leastNItem_Draw,
				validator: Designer_Control_Attr_LeastNItem_Validator,
				checkout: Designer_Control_Attr_LeastNItem_Checkout
			},
			items: {
				text: Designer_Lang.controlAttrItems,
				value: "",
				type: 'textarea',
				hint: Designer_Lang.controlAttrItemsHint,
				show: true,
				required: true,
				lang: true,
				validator: [Designer_Control_Attr_Required_Validator,Designer_Items_NumberType_Validator,Designer_Items_DoubleType_Validator,Designer_Items_ValRepeat_Validator],
				checkout: [Designer_Control_Attr_Required_Checkout,Designer_Items_NumberType_Checkout,Designer_Items_DoubleType_Checkout,Designer_Items_ValRepeat_Checkout],
				convertor: Designer_Control_Attr_ItemsTrimConvertor
			},
			defaultValue : {
				text: Designer_Lang.controlAttrDefaultValue,
				value: "",
				type: 'defaultValue',
				show: true,
				validator: Designer_Items_DefaultValue_Validator,
				checkout: Designer_Items_DefaultValue_Checkout,
				convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			formula: Designer_Config.attrs.formula,
			reCalculate: Designer_Config.attrs.reCalculate
		},
		info : {
			name: Designer_Lang.controlFSelectInfoName,
			preview: "select.bmp"
		},
		resizeMode : 'onlyWidth'
}

function _Designer_Control_FSelect_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	var domElement = document.createElement("div");
	$(this.options.domElement).find("div[mobileEle='true']").remove();
	domElement.setAttribute("mobileEle", "true");
	$(domElement).addClass("oldMui muiFormEleWrap");
	$(this.options.domElement).find("div[mobileEle='true']").remove();
	$(this.options.domElement).children().hide();
	$(this.options.domElement).append(domElement);
	$(domElement).append(_Designer_Control_GetSelectHtml());
	var style = _Designer_Control_Style(this.options.values);
	$(domElement).append("<div class='muiSelInput muiSelInputSize' style='" + style + "' placeholder='请选择'/>");
	return domElement;
}

// 控件样式
function _Designer_Control_Style(mobileStyle){
	var mobileUnderline;
	if(mobileStyle.underline == 'true'){
		mobileUnderline='underline';
	}

	var style = "color:" + mobileStyle.color +
		";font-family:"+ mobileStyle.font +
		";font-size:"+ mobileStyle.size +
		"!important ;font-weight:"+ mobileStyle.b +
		";font-style:"+ mobileStyle.i +
		";text-decoration:"+ mobileUnderline;
	return style;
}

//生成下拉框Dom对象
function _Designer_Control_Select_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className="select_div_box xform_fselect";
	$(domElement).css('width','auto');
	//$(domElement).css('min-width','100px');
	$(domElement).css('text-align','left');
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";

	
	var selectDom = document.createElement('select');
	selectDom.style.display = 'none';

	// 没有值就默认auto
	if (!this.options.values.width) {
		this.options.values.width='120';
	}
	$(domElement).css('width', this.options.values.width);
	selectDom.style.width = this.options.values.width+"px";

	if (this.options.values.width.indexOf("%") >0){
		setTimeout(function () {
			$(domElement).css('max-width',$(domElement).width());
		},100);
	}

	selectDom.id = this.options.values.id;
	if (this.options.values.description) {
		selectDom.description = this.options.values.description;
	}
	selectDom.label = _Get_Designer_Control_Label(this.options.values, this);
	if (this.parent != null) {
		selectDom.tableName=_Get_Designer_Control_TableName(this.parent);
	}
	selectDom.required = (this.options.values.required == 'true' ? 'true' : 'false');
	$(selectDom).attr('_required',this.options.values.required == 'true' ? 'true' : 'false');
	//selectDom._required = (this.options.values.required == 'true' ? 'true' : 'false');
	selectDom.canShow = (this.options.values.canShow == 'true' ? 'true' : 'false');
	if (this.options.values.formula != null && this.options.values.formula != '') {
		selectDom.formula = 'true';
		selectDom.defaultValue = this.options.values.defaultValue;
		selectDom.reCalculate = (this.options.values.reCalculate == 'true' ? 'true' : 'false');
	}
	else if (this.options.values.defaultValue != null && this.options.values.defaultValue != '') {
		selectDom.defaultValue = this.options.values.defaultValue;
	}
	if (this.options.values.required === 'true'){
		$(selectDom).attr('leastNItem',this.options.values.leastNItem);
	}
	//是否摘要
	if(this.options.values.summary == "true"){
		$(selectDom).attr("summary","true");
	}else{
		$(selectDom).attr("summary","false");
	}
	//是否留痕
	if(this.options.values.isMark == "true"){
		$(selectDom).attr("isMark","true");
	}else{
		$(selectDom).attr("isMark","false");
	}
	var itemsText = [];
	var itemsValue = [];
	var defaultValueName = "";
	if (this.options.values.items != null && this.options.values.items != '') {
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		for(var i = 0; i < items.length; i++) {
			if(items[i] == "")
				continue;
			//items[i] = Designer.HtmlEscape(items[i]);
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				itemsText.push(items[i]);
				itemsValue.push(items[i]);
			}else{
				itemsText.push(items[i].substring(0, index));
				itemsValue.push(items[i].substring(index+1));
			}
			if (selectDom.defaultValue == itemsValue[itemsValue.length - 1]) {
				defaultValueName = itemsText[itemsText.length - 1]
			}
		}
	} else {
		
	}
	if (itemsValue.length > 0) {
		//selectDom.items =itemsText.join(';');// $('<input type="hidden" value="' + itemsText.join(';') + '"/>').val();
		$(selectDom).attr("items",itemsText.join(';'));
		$(selectDom).attr("itemValues",itemsValue.join(';'));// $('<input type="hidden" value="' + itemsValue.join(';') + '"/>').val();
	}
	var buf = [];
	var face_width = "85%";
	let fSelectWidth = this.options.values.width;
	if (fSelectWidth.indexOf("%")<0){
		if (fSelectWidth>600){
			face_width = "95%";
		}
		fSelectWidth+="px";
	}else{
		fSelectWidth="100%";
	}
	buf.push('<label class="select_tag_left" style="width:' + fSelectWidth + '"><label class="select_tag_right" style="width:95%">');
	if (itemsText.length == 0) {
		buf.push('<label class="select_tag_face" style="width:'+face_width+';overflow: hidden">',Designer_Lang.controlSelectPleaseAdd,'</label>');
	} else {
		var lab = '<label class="select_tag_face" style="width:'+face_width+';overflow: hidden"';
		if (this.options.values.color){
			lab += ' style="color:' + this.options.values.color + ';">';
		}else{
			lab += '>';
		}
		if (selectDom.defaultValue != null && selectDom.defaultValue != ""
				&& (this.options.values.formula == null || this.options.values.formula == '')) {
			
			buf.push(lab, defaultValueName, '</label>');
		} else {
			buf.push(lab,Designer_Lang.controlSelectPleaseSelect,'</label>');
		}
	}
	buf.push('</label></label>');
	if(this.options.values.required == 'true') {
		buf.push('<span class=txtstrong>*</span>');
	}
	domElement.innerHTML = buf.join('');
	domElement.appendChild(selectDom);
	setTimeout(function () {
		let selecttagleft = $(domElement).find(".select_tag_left");
		if (selecttagleft.width()){
			if (selecttagleft.width() > 600){
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","97%");
			}else{
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","85%");
			}
		}
	},100);
}

// 生成XML
function _Designer_Control_Select_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	buf.push('enumValues="', Designer_Control_MultiValues(values.items), '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	
	buf.push('businessType="', this.type, '" ');
	
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_Select_Onclick(target){
	var isRequired = $(target).is(':checked');
	var leastNItem = $("input[name='leastNItem']");
	var tr = leastNItem.closest("tr");
	if (isRequired){
		tr.show();
	}else{
		tr.hide();
		leastNItem.val(1);
	}
}

function _Designer_Control_FSelect_leastNItem_Draw(name, attr, value, panelForm, attrs, values, control){
	var display = "display:none;";
	if (values.required === "true"){
		display = "";
	}
	var html = "<input type='text' class='attr_td_text' style='width:93%' name='" + name;
	if (attr.value != value && value != null) {
		html += "' value='" + value;
	} else {
		html += "' value='" + attr.value;
	}
	html += "'>";
	return ('<tr  style="' + display + '"><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
}

function Designer_Control_Attr_LeastNItem_Validator(elem, name, attr, value, values) {
	if (values.required == "false"){
		return true;
	}
	if (value == 0){
		alert(Designer_Lang.controlFSelectAttrLeastNItemTip);
		return false;
	}
	if (value != null && value != '' && !(/^([+]?)(\d+)$/.test(value.toString()))) {
		alert(Designer_Lang.controlFSelectAttrLeastNItemTip);
		return false;
	}
	return true;
}

function Designer_Control_Attr_LeastNItem_Checkout(msg, name, attr, value, values) {
	if (values.required == "false"){
		return true;
	}
	if (value == 0){
		msg.push(Designer_Lang.GetMessage(Designer_Lang.controlFSelectAttrLeastNItemChkTip,attr.text));
		return false;
	}
	if (value != null && value != '' && !(/^([+]?)(\d+)$/.test(value.toString()))) {
		msg.push(Designer_Lang.GetMessage(Designer_Lang.controlFSelectAttrLeastNItemChkTip,attr.text));
		return false;
	}
	return true;
}