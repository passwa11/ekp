Designer_Config.operations['cityCategory']={
		imgIndex : 80,
		title:Designer_Lang.controlCityCategory_attr_title,
		titleTip:Designer_Lang.controlNew_AddressTitleTip,
		run : function (designer) {
			designer.toolBar.selectButton('cityCategory');
		},
		type : 'cmd',
		order: 1,
		select: true,
		cursorImg: 'style/cursor/newAddress.cur'
};
Designer_Config.controls.cityCategory = {
		type : "cityCategory",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_CityCategory_OnDraw,
		drawXML : _Designer_Control_CityCategory_DrawXML,
		onInitializeDict : _Designer_Control_DisplayText_OnInitializeDict,
		implementDetailsTable : true,
		attrs : {
			label : Designer_Config.attrs.label,
			canShow : {
				text: Designer_Lang.controlAttrCanShow,
				value: "true",
				type: 'hidden',
				checked: true
			},
			readOnly : Designer_Config.attrs.readOnly,
			required: {
				text: Designer_Lang.controlAttrRequired,
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
			formula: Designer_Config.attrs.formula
		},
		onAttrLoad:_Designer_Control_Attr_CityCategory_OnAttrLoad,
		info : {
			name: Designer_Lang.controlCityCategory_attr_title
		},
		resizeMode : 'onlyWidth'
}
Designer_Config.buttons.control.push("cityCategory");
//把控件增加到右击菜单区
Designer_Menus.add.menu['cityCategory'] = Designer_Config.operations['cityCategory'];
function _Designer_Control_CityCategory_OnDraw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_CityCategory_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_CityCategory_DrawByType(parent,attrs,values,target){
	var width = values.width||'85%';
	if(width.indexOf('%')==-1){
		width+='px';
	}
	var props = {width:width,id:values.id,subject:values.label};
	if(values.required=='true'){
		props.required = true;
	}
	if(values.defaultValue){
		props.defaultValue = values.defaultValue;
	}
	if(values.readOnly=='true'){
		props.readOnly = values.readOnly;
	}
	var htmlCode = '<div class="inputselectsgl" style="width:'+width+';">'
	htmlCode+='<input name="'+values.id+'.id" type="hidden">'
	htmlCode+='<div class="input"><input subject="" name="'+values.id+'.name" props="'+JSON.stringify(props).replace(/\"/g,"'")+'" type="text" props='+values.id ;
	var defaultValue = {'null':"",0:"",1:Designer_Lang.controlCostCenter_attr_default_value};
	htmlCode+=" value=\""+defaultValue[values.defaultValue||'0'];
	htmlCode+='" readonly></div><div class="selectitem"></div></div>';
	if(values.required=='true'){
		htmlCode+='<span class="txtstrong">*</span>';
	}
	return htmlCode;
}
function _Designer_Control_CityCategory_DrawXML() {
	var values = this.options.values;
	var buf = [];//mutiValueSplit	
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="String" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	buf.push('/>');
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id, '_name" ');
	buf.push('label="', values.label,Designer_Lang.control_property_name, '" ');
	buf.push('type="String" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	buf.push('/>');
	return buf.join('');
};
function _Designer_Control_Attr_CityCategory_OnAttrLoad(form,control){
	
}

