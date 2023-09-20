/**********************************************************
功能：前端值计算控件
使用：
	
作者：傅游翔
创建时间：2010-04-27
**********************************************************/
Com_IncludeFile("dialog.js");//对话框依赖

Designer_Config.controls['calculation'] = {
		type : "calculation",
		storeType : 'field',
		inherit    : 'baseStyle',
		implementDetailsTable : true,
		onInitialize : _Designer_Calculation_OnInitialize,
		onDraw : _Designer_Control_Calculation_OnDraw,
		drawMobile : _Designer_Control_Calculation_DrawMobile,
		onDrawEnd : _Designer_Control_Calculation_OnDrawEnd,
		drawXML : _Designer_Control_Calculation_DrawXML,
		resizeMode : 'onlyWidth',
		attrs : {
			label : Designer_Config.attrs.label,
			canShow : {
				text: Designer_Lang.controlAttrCanShow,
				value: "true",
				type: 'hidden',
				checked: true,
				show: true
			},
			readOnly : {
				text : Designer_Lang.controlAttrReadOnly,
				value : true,
				checked: true,
				type : 'checkbox',
				show: false
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
			encrypt : Designer_Config.attrs.encrypt,
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			autoCalculate : {
				text : Designer_Lang.controlAttrReadOnly, //自动计算改为只读，属性名称不改，以防有历史兼容问题
				value : true,
				checked: true,
				type : 'checkbox',
				show: true
			},
			expression : {
				text : Designer_Lang.controlCalculation_attr_expression,
				type : 'self',
				show: true,
				draw: Designer_Control_Calculation_Attr_Expression_Draw
			},
			expression_id : {
				show: false,
				skipLogChange:true,
				convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			expression_name : {
				show: false,
				text: Designer_Lang.controlCalculation_attr_expression,
				convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			paramDefault : {
				text : Designer_Lang.controlCalculation_attr_paramDefault,
				value : 'true',
				type : 'checkbox',
				checked: true,
				show : true
			},
			dataType : {
				text: Designer_Lang.controlAttrDataType,
				value: "Double",
				synchronous: true,
				opts: [{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"},
						{text:Designer_Lang.controlAttrDataTypeBigNumber,value:"BigDecimal"},
						{text:Designer_Lang.controlAttrDataTypeMoney,value:"BigDecimal_Money"}],
				type: 'select',
				onchange: '_Designer_Control_Attr_Calculation_DataTypeOnchange(this);',
				show: true
			},
			decimal: {
				text: Designer_Lang.controlInputTextAttrDecimal,
				type: 'text',
				value: '',
				show: true,
				validator: [Designer_Control_Attr_Int_Validator],
				checkout: Designer_Control_Attr_Int_Checkout
			},
			thousandShow : {
				text: Designer_Lang.controlAttrThousandShow,
				value: "false",
				type: 'checkbox',
				checked: false,
				show: true
			},
			font : {
				text: Designer_Lang.controlTextLabelAttrFont,
				value: "",
				type: 'select',
				opts: Designer_Config.font.style,
				show: false
			},
			size : {
				text: Designer_Lang.controlTextLabelAttrSize,
				value: "",
				type: 'select',
				opts: Designer_Config.font.size,
				show: false
			},
			color : {
				text: Designer_Lang.controlTextLabelAttrColor,
				value: "#000",
				type: 'color',
				show: false
			},
			b : {
				text: Designer_Lang.controlTextLabelAttrBold,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: false
			},
			i : {
				text: Designer_Lang.controlTextLabelAttrItalic,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: false
			},
			underline : {
				text: Designer_Lang.controlTextLabelAttrUnderline,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: false
			},
			defaultValue: Designer_Config.attrs.defaultValue,
			formula: Designer_Config.attrs.formula,
			reCalculate: Designer_Config.attrs.reCalculate
		},
		info : {
			name: Designer_Lang.controlCalculation_info_name
		}
};

function _Designer_Calculation_OnInitialize(){
	var domElement = this.options.domElement;
	var values = this.options.values;
	if (values['expression_name'] != null && values['expression_name'] != '') {
	domElement.children[0].value = Designer.HtmlUnEscape(values['expression_name']);
	}
}

function _Designer_Control_Calculation_OnDraw(parentNode, childNode){
	var domElement = document.createElement("div");
	domElement.setAttribute("formDesign", "landray");
	this.options.domElement = domElement;
	parentNode.insertBefore(domElement, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	_Get_Designer_Control_Label(this.options.values, this);
	domElement.style.display = "inline-block";
	domElement.className="xform_Calculation";
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";
	
	if (this.options.values.width == null) {
		this.options.values.width = '200';
	}
	if (this.options.values.width != null && this.options.values.width != '') {
		domElement.style.width = this.options.values.width;
		//兼容多浏览器
		$(domElement).css('width',this.options.values.width);
	}
	var thousandShow=' thousandShow="false"';
	//金额类型默认选中千分位
	if (this.options.values.dataType  === 'BigDecimal_Money'){
		this.options.values.thousandShow = 'true'
	}
	if (this.options.values.thousandShow == 'true') {
		thousandShow = ' thousandShow="true"';
	}
	var paramDefault=" paramDefault='false'";
	if(this.options.values.paramDefault == 'true'){
		paramDefault=" paramDefault='true'";
	}
	//是否摘要
	var summary = " summary='false'";
	if(this.options.values.summary == "true"){
		summary = " summary='true'";
	}
	//是否留痕
	var isMark = " isMark='false'";
	if(this.options.values.isMark == "true"){
		isMark = " isMark='true'";
	}
	var inputWidth;
	if(domElement.offsetWidth&&domElement.offsetWidth>40){
		inputWidth=domElement.offsetWidth-40;
	}else{
		inputWidth=40;
	}
	var html = '<input type=text class=inputsgl readOnly title="'
		+Designer_Lang.controlCalculation_html_alt+'" style="width:'
		+inputWidth+'px"   ' + thousandShow + paramDefault + isMark + summary + '/>';
	html += ' <a href="javascript:void(0);">'+Designer_Lang.controlCalculation_html_button+'</a>';
	domElement.innerHTML = html;
}

function _Designer_Control_Calculation_OnDrawEnd() {
	var domElement = this.options.domElement;
	var values = this.options.values;
	//values.dataType = 'Double';
	if (values.dataType == null) {
		values.dataType = 'Double';
	}
	if (values.decimal) {
		values.scale = values.decimal;
	}
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'label', domElement);
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'canShow', domElement);
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'readOnly', domElement);
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'dataType', domElement);
	if (values['readOnly'] != null && values['readOnly'] != '') {
		domElement.setAttribute('_readOnly', values['readOnly']);
	}
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'expression_id', domElement);
	_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'autoCalculate', domElement);
	//_Designer_Control_Calculation_OnDrawEnd_SetValue(values, 'expression_mode', domElement);
	if (values['expression_name'] != null && values['expression_name'] != '') {
		domElement.children[0].value = Designer.HtmlUnEscape(values['expression_name']);
	}
	var isRow = false;
	for (var parent = domElement.parentNode; parent != null; parent = parent.parentNode) {
		if (parent.tagName == 'TR'||parent.tagName == 'tr') {
			if ($(parent).attr("type") == 'templateRow') {
				$(domElement).attr("expression_mode",'isRow');
				isRow = true;
				break;
			}
		}
	}
	if (!isRow) {
		$(domElement).attr("expression_mode",'notRow');
	}

	if (this.options.values.decimal != null) {
		$(domElement).attr("scale",this.options.values.decimal);
	}
	$(domElement).attr("thousandShow",false);
	if(this.options.values.thousandShow=='true'){
		$(domElement).attr("thousandShow",true);
	}
	//增加参数是否默认为0配置
	$(domElement).attr("paramDefault",false);
	if(this.options.values.paramDefault=='true'){
		$(domElement).attr("paramDefault",true);
	}
	//摘要
	$(domElement).attr("summary",false);
	if(this.options.values.summary=='true'){
		$(domElement).attr("summary",true);
	}
	//留痕
	$(domElement).attr("isMark",false);
	if(this.options.values.isMark=='true'){
		$(domElement).attr("isMark",true);
	}
}

//数据类型切换的时候，金额类型需要隐藏千分位
function _Designer_Control_Attr_Calculation_DataTypeOnchange(dom){
	var dataType = dom.value;
	if(dataType && dataType == 'BigDecimal_Money'){
		var $thousandShow = Designer_Control_Attr_AcquireParentTr('thousandShow');		
		$thousandShow == null ? '' : $thousandShow.hide();		
		var decimal = dom.form['decimal'];
		decimal.value = decimal == null ? 0 : 2; 
	}else{
		var $thousandShow = Designer_Control_Attr_AcquireParentTr('thousandShow');		
		$thousandShow == null ? '' : $thousandShow.show();
	}
}

function _Designer_Control_Calculation_OnDrawEnd_SetValue(values, name, domElement) {
	if (values[name] != null && values[name] != '') {
		domElement.setAttribute(name, values[name]);
	}
}

function Designer_Control_Calculation_Dialog(dom) {
	var dialog = new KMSSDialog();
	var fieldLayout = Designer_Control_Calculation_getCanApplyFieldLayout();
	var objs = fieldLayout.concat(Designer.instance.getObj());
	dialog.formulaParameter = {varInfo: objs, funcInfo : Designer_Control_Calculation_FuncInfo};
	dialog.BindingField('expression_id', 'expression_name');
	dialog.SetAfterShow(function(){Designer_AttrPanel.showButtons(dom)});
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/designer/calculation/calculation_edit.jsp";
	dialog.Show(window.screen.width*780/1366,window.screen.height*480/768);
}

/**
 * 支持属性列表
 * @returns
 */
function Designer_Control_Calculation_getCanApplyFieldLayout () {
	var builder = Designer.instance.builder;
	var controls = builder.controls.sort(Designer.SortControl);
	var objs = [];
	_Designer_Control_Calculation_getCanApplyFieldLayout(controls, objs);
	return objs;
}

function _Designer_Control_Calculation_getCanApplyFieldLayout(controls, objs) {
	for (var i = 0, l = controls.length; i < l; i ++) {
		var control = controls[i];
		if (control.storeType == 'none' && control.type != "fieldlaylout")
			continue;
		if (control.storeType == 'layout' && control.type != "fieldlaylout") {
			_Designer_Control_Calculation_getCanApplyFieldLayout(control.children.sort(Designer.SortControl), objs);
			continue;
		}
		var rowDom = Designer_GetObj_GetParentDom(function(parent) {
			return (Designer.checkTagName(parent, 'tr') && parent.getAttribute('type') == 'templateRow')
		}, control.options.domElement);
		
		var obj = {}, isTempRow = (rowDom != null && rowDom.getAttribute('type') == 'templateRow');
		if (control.type != "fieldlaylout"){
			continue;
		}
		if (control.options.values.__dict__) {
			var dict = control.options.values.__dict__;
			for (var di = 0; di < dict.length; di ++) {
				
				var _dict = dict[di];
				objs.push({
					name: _dict.id,
					label: _dict.label,
					type: _dict.type,
					controlType: control.type,
					isTemplateRow: isTempRow
				});
			}
		} else {
			obj.name = control.options.values.fieldIds;
			obj.label = Designer.HtmlUnEscape(control.options.values.label);
			obj.type = control.options.values.__type;
			if (control.options.values.__type == "Integer" || 
					control.options.values.__type == "Double" || 
					control.options.values.__type == "BigDecimal") {
				obj.type = "BigDecimal";
			}
			obj.controlType = control.type;
			obj.isTemplateRow = isTempRow;
			objs.push(obj);
		}
	}
}

function Designer_Control_Calculation_Attr_Expression_Draw(name, attr, value, form, attrs, values, control) {
	var dataType = values.dataType;
	if(dataType && dataType == 'BigDecimal_Money'){
		setTimeout(function(){
			var $thousandShow = Designer_Control_Attr_AcquireParentTr('thousandShow');		
			$thousandShow == null ? '' : $thousandShow.hide();			
		},0);	
	}
	var buff = [];
	buff.push('<nobr><input type="hidden" name="expression_id"');
	if (values.expression_id != null) {
		buff.push(' value="' , values.expression_id, '"');
	}
	buff.push('>');
	buff.push('<input type="text" name="expression_name" style="width:78%" readOnly ');
	if (values.expression_name != null) {
		buff.push(' value="' , values.expression_name, '"');
	}
	buff.push('>');
	buff.push('<input type=button onclick="Designer_Control_Calculation_Dialog(this);" class="btnopt" value="..."></nobr>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

function _Designer_Control_Calculation_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	//修复 #47720 编辑流程模板时，前端计算数据类型选择为金额，保存后，前端计算数据类型变为大数字
	
	buf.push('type="', values.dataType ? (values.dataType.indexOf('BigDecimal') > -1 ? 'BigDecimal' : values.dataType) : 'Double', '" ');
	//end
//	buf.push('type="', values.dataType ? values.dataType : "Double", '" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	if (values.decimal != '' || values.decimal != null) {
		buf.push('scale="', values.decimal, '" ');
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
	//显示格式
	var customElementProperties = {};
	if (values.thousandShow == 'true') {
		customElementProperties.thousandShow = 'true';
	}
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	buf.push('/>');
	return buf.join('');
}

Designer_Config.operations['calculation'] = {
		lab : "2",
		imgIndex : 9,
		title : Designer_Lang.controlCalculation_btn_title,
		run : function (designer) {
			designer.toolBar.selectButton('calculation');
		},
		type : 'cmd',
		order: 9,
		select: true,
		cursorImg: 'style/cursor/calculation.cur',
		isAdvanced: false
	};

Designer_Config.buttons.form.push('calculation');
Designer_Menus.form.menu['calculation'] = Designer_Config.operations['calculation'];

var Designer_Control_Calculation_FuncInfo = [
{text:Designer_Lang.controlCalculation_func_sum_text, value: Designer_Lang.controlCalculation_func_sum_value, title:Designer_Lang.controlCalculation_func_sum_title, fun: 'XForm_CalculatioFuns_Sum'},
{text:Designer_Lang.controlCalculation_func_avg_text, value: Designer_Lang.controlCalculation_func_avg_value, title:Designer_Lang.controlCalculation_func_avg_title, fun: 'XForm_CalculatioFuns_Avg'},
{text:Designer_Lang.controlCalculation_func_count_text, value: Designer_Lang.controlCalculation_func_count_value, title:Designer_Lang.controlCalculation_func_count_title, fun: 'XForm_CalculatioFuns_Count'},
{text:Designer_Lang.controlCalculation_func_defaultValue_text, value: Designer_Lang.controlCalculation_func_defaultValue_value, title:Designer_Lang.controlCalculation_func_defaultValue_title, fun: 'XForm_CalculatioFuns_DefaultValue'},
{text:Designer_Lang.controlCalculation_func_timeDistance_text, value: Designer_Lang.controlCalculation_func_timeDistance_value, title:Designer_Lang.controlCalculation_func_timeDistance_title, fun: 'XForm_CalculatioFuns_TimeDistance'},
{text:Designer_Lang.controlCalculation_func_dayDistance_text, value: Designer_Lang.controlCalculation_func_dayDistance_value, title:Designer_Lang.controlCalculation_func_dayDistance_title, fun: 'XForm_CalculatioFuns_DayDistance'}
];