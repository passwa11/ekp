Designer_Config.operations['company']={
		imgIndex : 81,
		title:Designer_Lang.controlCompany_attr_title,
		titleTip:Designer_Lang.controlNew_AddressTitleTip,
		run : function (designer) {
			designer.toolBar.selectButton('company');
		},
		type : 'cmd',
		order: 1,
		select: true,
		cursorImg: 'style/cursor/newAddress.cur'
};
Designer_Config.controls.company = {
		type : "company",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_Company_OnDraw,
		drawXML : _Designer_Control_Company_DrawXML,
		onInitializeDict : _Designer_Control_DisplayText_OnInitializeExpDict,
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
			defaultValue: {
				text: Designer_Lang.controlAttrDefaultValue,
				value: "null",
				type: 'self',
				draw: _Designer_Control_New_Company_Self_Draw,
				show: true,
				validator : Designer_Company_DefaultValue_Validator,
				checkout: Designer_Company_DefaultValue_Checkout
			},
			defaultCompanyId:{
				text:"",
				type:"hidden",
				value:""
			},
			defaultCompanyName:{
				text:"",
				type:"hidden",
				value:""
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
		onAttrLoad:_Designer_Control_Attr_Company_OnAttrLoad,
		info : {
			name: Designer_Lang.controlCompany_attr_title
		},
		resizeMode : 'onlyWidth'
}
Designer_Config.buttons.control.push("company");
//把控件增加到右击菜单区
Designer_Menus.add.menu['company'] = Designer_Config.operations['company'];
function _Designer_Control_Company_OnDraw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_Company_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_Company_DrawByType(parent,attrs,values,target){
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
	var width = values.width||'85%';
	var htmlCode = '<div class="inputselectsgl" style="width:'+width+';">'
	htmlCode+='<input name="'+values.id+'.id" type="hidden">'
	htmlCode+='<div class="input"><input subject="" name="'+values.id+'.name" props="'+JSON.stringify(props).replace(/\"/g,"'")+'" type="text" props='+values.id ;
	var defaultValue = {0:"",1:Designer_Lang.controlCompany_attr_default_value};
	htmlCode+=" value=\""+defaultValue[values.defaultValue||'0'];
	htmlCode+='" readonly></div><div class="selectitem"></div></div>';
	if(values.required=='true'){
		htmlCode+='<span class="txtstrong">*</span>';
	}
	return htmlCode;
}
function _Designer_Control_Attr_Company_OnAttrLoad(form,control){
	
}
//显示默认值的属性面板
function _Designer_Control_New_Company_Self_Draw(name, attr, value, form, attrs, values){
	var display = "none",selected="";
	if(values.defaultValue=='1'){
		selected = "selected";
	}
	var htmlCode = "<select name='defaultValue' class='attr_td_select' style='width:95%' onchange='_Show_Company_DefaultValue(this)'>";
	htmlCode+='<option value="">'+Designer_Lang.controlAddressAttrDefaultValueNull+'</option>';
	htmlCode+='<option value="1" '+selected+'>'+Designer_Lang.controlCompany_attr_default_value+'</option>';
	htmlCode+="</select><div id='defaultCompany' style='display:"+display+";'><input type='text' style='width:80%' readonly name='defaultCompanyName'></div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, htmlCode);
}
//如果默认值选择的是指定公司，则显示公司选择框
function _Show_Company_DefaultValue(obj){
	if(obj.value=='2'){
		document.getElementById('defaultCompany').style.display='';
	}else{
		document.getElementById('defaultCompany').style.display='none';
	}
}

function _Designer_Control_Company_DrawXML() {	
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


function Designer_Company_DefaultValue_Validator(){
	return true;
}

function Designer_Company_DefaultValue_Checkout(){
	return true;
}
//构造实际值和显示值的数据字典，用于designer的getObj方法
function _Designer_Control_DisplayText_OnInitializeExpDict(){
	var dict = [];
	// 实际值
	var realVal = {};
	realVal.id = this.options.values.id;
	realVal.label = this.options.values.label;
	realVal.type = "String";
	
	// 显示值
	var displayTextVal = {};
	displayTextVal.id = this.options.values.id + "_name";
	var textLable = this.options.values.label ? this.options.values.label + Designer_Lang.controlDisplayValueMessage : this.options.values.label;
	displayTextVal.label = textLable;
	displayTextVal.type = "String";
	
	dict.push(realVal);
	dict.push(displayTextVal);
	this.options.values.__dict__ = dict;
}