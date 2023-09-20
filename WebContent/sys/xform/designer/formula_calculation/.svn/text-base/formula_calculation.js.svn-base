/********************************************
功能：公式计算控件
使用：
	
作者：李文昌
创建时间：2017-08-08
*********************************************/
/**
 * 公式加载控件的配置
 */
Designer_Config.operations['formula_calculation'] = {
		lab : "2",
		imgIndex : 60,
		title : Designer_Lang.controlFormluCalculation_btn_title,
		titleTip:Designer_Lang.controlFormulaLoadDescription,
		run : function (designer) {
			designer.toolBar.selectButton('formula_calculation');
		},
		type : 'cmd',
		order: 9.5,
		select: true,
		cursorImg: 'style/cursor/formulaLoad.cur',
		isAdvanced: false
};

Designer_Config.controls['formula_calculation'] = {
		type:'formula_calculation',//控件类型
		storeType:'field',
		inherit:'baseStyle',
		implementDetailsTable : true,//是否支持明细表
		onDraw : _Designer_Control_formula_caculation_OnDraw,//绘制控件在模板页面的HTMl结构
		onDrawEnd : _Designer_Control_formula_caculation_OnDrawEnd,
		drawMobile : _Designer_Control_FormulaLoad_DrawMobile,
		drawXML : _Designer_Control_formula_caculation_DrawXML,//绘制控件的数据字典
		resizeMode : 'onlyWidth',
		attrs:{
			label:Designer_Config.attrs.label,//显示文字
			width:{//宽度属性
				text  : Designer_Lang.controlAttrWidth,
				value :  200,
				type  : 'text',
				show  : true,
				validator : Designer_Control_Attr_Width_Validator,
				checkout  : Designer_Control_Attr_Width_Checkout
			},
			required:{//必填属性
				text : Designer_Lang.controlAttrRequired,
				value : 'true',
				checked : false,
				type : 'checkbox',
				show : true
			},
			encrypt : Designer_Config.attrs.encrypt,
			formulaValue : {//公式属性
				text: Designer_Lang.controlAttrFormulas,
				value : '',
				type : 'self',
				show : true,
				draw : Designer_Control_formula_calculation_formulaValue_Draw, //公式属性HTML生成函数
				validator : Designer_FormulaLoad_Scope_Validator,
				checkout : [Designer_InputText_DefaultValue_Checkout,Designer_FormulaLoad_Scope_Checkout],
				convertor : Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			formula : {//公式按钮
				text : Designer_Lang.controlAttrFormula,
				value : '',
				type : '',
				show : false,
				skipLogChange: true,
				convertor : Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			loadType:{
				text : Designer_Lang.controlAttrLoadType,
				value : '',
				type : 'checkGroup',
				required: true,
				opts: [
						{name: 'autoLoad', text: Designer_Lang.controlAttrAutoLoad, value:'autoLoad'},
						{name: 'manualLoad', text: Designer_Lang.controlAttrLoad, value:'manualLoad'},
					],
				show: true,
				validator : Designer_Control_Attr_loadType_Validator,
				checkout : Designer_Control_Attr_Required_Checkout,
				translator:opts_common_translator_many
			},
			autoLoad:{
				text : Designer_Lang.controlAttrAutoLoad,
				skipLogChange: true

			},
			manualLoad:{
				text : Designer_Lang.controlAttrLoad,
				skipLogChange: true

			},
			returnType : {//返回数据的展现形式
				text : Designer_Lang.div_background_img_showtype,
				value : 'select',
				opts: [{text:Designer_Lang.controlAttrReturnTypeSelect,value:"select"},
						{text:Designer_Lang.controlAttrReturnTypeRadio,value:"radio"},
						{text:Designer_Lang.controlAttrReturnTypeCheckbox,value:"checkbox"},
						{text:Designer_Lang.controlAttrReturnTypetext,value:"text"},
						{text:Designer_Lang.controlAttrReturnTypeAddress,value:"address"}
					  ],
				type: 'select',
				show : true,
				onchange: '_Designer_Control_Attr_returnTypeOnchange(this);',
				translator:opts_common_translator
			},
			dataType : {
				text : Designer_Lang.controlAttrDataType,
				value : 'String',
				opts: [
						{text:Designer_Lang.controlAttrDataTypeText,value:"String"},
						{text:Designer_Lang.fieldLayout_number,value:"number"},
					  ],
				type : 'self',
				show : true,
				display:'none',
				draw:dataTypeDraw,
				translator:opts_common_translator
			}
		},
		info : {
			name: Designer_Lang.controlFormulaCalculation_info_name
		}
};

function dataTypeWrapTitle(name, attr, value, html,values) {
	var returnType = values.returnType
	if (attr.required == true) {
		html += '<span class="txtstrong">*</span>';
	}
	if (attr.hint) {
		html = '<div id="attrHint_' + name + '">' + attr.hint + '</div>' + html;
	}else if(attr.hintAfter){
		html = html + '<div id="attrHint_' + name + '">' + attr.hintAfter + '</div>';
	}
	if(returnType=="text"){
	   return ('<tr><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');	  
	}else{
	   return ('<tr style="display:none"><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
	}	
	
}

function dataTypeDraw (name, attr, value, form, attrs, values, control) {
	var html = "<select name='" + name +"'";
	if (attr.multi == true) {
		html += "' multiple='multiple' size='" + attr.size +"'";
	}
	if (attr.onchange) {
		html += " onchange=\"" + attr.onchange + "\"";
	}
	html += " class='attr_td_select' style='width:95%'>";
	if (attr.opts) {
		for (var i = 0; i < attr.opts.length; i ++) {
			var opt = attr.opts[i];
			html += "<option value='" + opt.value;
			if (opt.style) {
				html += "' style='" + opt.style;
			}
			if (opt.value == value) {
				html += "' selected='selected";
			} else if (value == null && attr.value == opt.value) {
				html += "' selected='selected";
			}
			html += "'>" + opt.text + "</option>";
		}
	}
	html += "</select>";
	return dataTypeWrapTitle(name, attr, value, html,values);
}

Designer_Config.buttons.form.push('formula_calculation');

//当返回类型改变的时候
function _Designer_Control_Attr_returnTypeOnchange(dataTypeSelect) {
	var value = dataTypeSelect.value;
	var dataType = dataTypeSelect.form['dataType'];
	dataType.value="String";
	if(value=="text"){	
		$(dataType).closest("tr").css("display","");
	}else{
		$(dataType).closest("tr").css("display","none");
	}
	var dataType = dataTypeSelect.form['dataType'];
}
/**
 * 公式属性HMTL生成函数
 */
function Designer_Control_formula_calculation_formulaValue_Draw(name, attr, value, form, attrs, values, control) {
	var buff = [];
	var isFormula = (values.formula != null && values.formula != '');
	buff.push('<input type="hidden" name="formula"');
	if (isFormula) {
		buff.push(' value="' , values.formula, '"');
	}
	buff.push('><input type="text" style="width:95%" name="formulaValue"');
	if (value != null) {
		buff.push(' value="' , value, '"');
	}
	if (isFormula) {
		buff.push(' class="inputread"');
		buff.push(' readOnly ');//设置属性面板的公式输入框只读
	} else {
		buff.push(' class="attr_td_text"');
	}
	buff.push('><br>');
	buff.push('<button onclick="Designer_Control_formula_calculation_openFormulaDialog(this,\'', control.owner.owner._modelName, '\');" class="btnopt">');
	buff.push(Designer_Lang.attrpanelDefaultValueFormula);
	buff.push('</button>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

Designer_Control_formula_calculation_openFormulaDialog = function(dom, modelName) {
	var defValue = null;
	if (dom.form['formula'].value == '') {
		defValue = dom.form['formulaValue'].value;
	}
	var callback = function() {
		if (dom.form['formula'].value != '') {
			dom.form['formulaValue'].readOnly = true;
			dom.form['formulaValue'].className = 'inputread';
			var hint = document.getElementById('attrDefaultValueHint');
			if (hint) hint.style.display = 'none';
		} else {
			dom.form['formulaValue'].readOnly = false;
			dom.form['formulaValue'].className = 'attr_td_text';
			if (defValue != null) {dom.form['formulaValue'].value = defValue;}
			var hint = document.getElementById('attrDefaultValueHint');
			if (hint) hint.style.display = '';
		}
		if (dom.form['formula'].value != dom.form['formula'].formulaValue) {
			Designer_AttrPanel.showButtons(dom);
		}
	};
	var currentControl = Designer.instance.control;
	if (currentControl && currentControl.type == "formula_calculation"){
		var values = currentControl.options.values;
		if (values.returnType === "address"){
			Formula_Dialog('formula','formulaValue',Designer.instance.getObj(true),
					'com.landray.kmss.sys.organization.model.SysOrgElement[]',callback,null,modelName);
			return ;
		}
	}
	Formula_Dialog('formula', 'formulaValue', Designer.instance.getObj(true), "Object", callback, null, modelName);
}

/**
 * formula_calculation控件HMTL元素生成函数
 * @returns
 */
function _Designer_Control_formula_caculation_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	$(domElement).css("display","inline-block");
//	$(domElement).css("position","relative");
	var values = this.options.values;
	if (values.id == null){
		values.id = "fd_" + Designer.generateID();
	}
	domElement.id = values.id;
	//显示文字的绑定文本
	$(domElement).attr("label",_Get_Designer_Control_Label(values, this));
	if(values.font) _Designer_Control_Common_SetStyle(domElement, values.font, "fontFamily");
	if(values.size) _Designer_Control_Common_SetStyle(domElement, values.size, "fontSize");
	if(values.color) _Designer_Control_Common_SetStyle(domElement, values.color, "color");
	if(values.b=="true") domElement.style.fontWeight="bold";
	if(values.i=="true") domElement.style.fontStyle = "italic";
	if(values.underline=="true") domElement.style.textDecoration="underline";
	if (!values.width) {
		values.width = this.attrs.width.value;
	}
//	$(domElement).css('width',values.width);
	$(domElement).attr('_width',values.width);
	$(domElement).attr("class","xform_formula_load");
	var html = '';
	var loadType=[];
	var loadTypeOpts=this.attrs.loadType.opts;//数据加载方式
	for(var i=0;i<loadTypeOpts.length;i++){
		if(values[loadTypeOpts[i].name]=='true'){
			loadType.push(loadTypeOpts[i].value);
		}
	}
	values.loadType = loadType.join(",");
	domElement.setAttribute("loadType",loadType.join(","));
	if (!values.returnType){
		values.returnType = this.attrs.returnType.value;
	}
	domElement.setAttribute("returnType",values.returnType);//设置返回数据展现类型
	domElement.setAttribute("dataType",values.dataType);
	if(values.returnType == "select"){
		html += Designer_Control_formulaCalculation_getSelect(values);
	}else if (values.returnType == "radio"){
		html += Designer_Control_formulaCalculation_getInputRadio(true);
	}else if (values.returnType == 'checkbox'){
		html += Designer_Control_formulaCalculation_getInputCheckbox(true);
	}else{
		var _width = values.width;
		if (values.width && values.width.indexOf("%") == -1){
			_width = values.width - 70 + 'px';
		}
		var _class = "inputsgl";
		if (values.returnType == "address"){
			_class += " orgelement";
		}
		html += '<input type="text" class="' + _class + '" readOnly title='
			 +Designer_Lang.controlFormula_html_alt+'" style="width:'
			 +_width+'" ';
		html += '/>'
	}
	if (values.required == "true"){
		html += "<span class='txtstrong'>&nbsp;&nbsp;*</span>";
	}
	html += ' <a href="javascript:void(0);">'+Designer_Lang.controlFormula_html_button+'</a>';
	domElement.innerHTML += html;
}

/**
 * formula_calculation控件HMTL元素生成函数
 * @returns
 */
function _Designer_Control_formula_caculation_OnDrawEnd(){
	var domElement = this.options.domElement;
	var values = this.options.values;
	_Designer_Control_Formula_Calculation_OnDrawEnd_SetValue(values, 'label', domElement);
	_Designer_Control_Formula_Calculation_OnDrawEnd_SetValue(values, 'required', domElement);
	if (values['required'] != null && values['required'] != '') {//设置必填属性
		domElement.setAttribute('required', values['required']);
	}
	if (values['formula'] != null && values['formula'] != ''){//设置表达式
		domElement.setAttribute("formula",values['formula']);
	}
	var cid = Designer_FormulaControl_getFromulaRelationControlId(values);//获取跟表达式相关的控件id
	var controlIds  = '';
	if (cid.length >= 1){
		controlIds = cid.join(";");
	}
	domElement.setAttribute("controlIds",controlIds);
	var isRow = false;
	//判断此控件是在明细表内还是在明细表外(type=='templateRow'表示此控件在明细表内)
	for (var parent = domElement.parentNode; parent != null; parent = parent.parentNode) {
		if (parent.tagName == 'TR'||parent.tagName == 'tr') {
			if ($(parent).attr("type") == 'templateRow') {
				//如果在明细表内添加一个属性 (expression_mode='isRow')表示此控件在明细表内
				$(domElement).attr("expression_mode",'isRow');
				isRow = true;
				break;
			}
		}
	}
	if (!isRow) {
		$(domElement).attr("expression_mode",'notRow');
	}
}

//获取跟公式表达式相关的控件id
function Designer_FormulaControl_getFromulaRelationControlId(values){
	var formula = values['formula'];
	var formFieldList = Designer.instance.getObj(false);//获取公式变量对象
	var uid = [];
	for(var i = 0;i<formFieldList.length;i++){//抓取所有表达式中控件id，并剔除明细表id
		if(Designer.IsDetailsTableControlObj(formFieldList[i])){
			continue;
		}
		if(formula != null && formula.indexOf(formFieldList[i]['name'])>-1){
			uid.push(formFieldList[i]['name']);
		}
	}
	var varNames = GetFieldsDictVarInfoByModelName();
	if(formula && varNames && varNames.length > 0){
		var varObj;
		for(var i = 0; i < varNames.length; i++){
			varObj = varNames[i];
			if(formula.indexOf(varObj['name']) > -1){
				uid.push(varObj['name']);
			}
		}
	}
	return uid;
	
}

/**
 * 渲染数据字典
 * @returns
 */
function _Designer_Control_formula_caculation_DrawXML(){
	var customElementProperties = {};
	var values = this.options.values;
	customElementProperties.formula = values.formula;
	var buf = [];
	if(values.returnType === "address"){
		
		buf.push('<extendElementProperty ');
		buf.push('name="', values.id, '" ');
		buf.push('label="', values.label, '" ');
		buf.push('type="com.landray.kmss.sys.organization.model.SysOrgElement" ');
		if (values.required == "true") {
			buf.push('notNull="true" ');
		}
		if (values.multiSelect == 'true') {
			buf.push('mutiValueSplit=";" ');
		}
		if (values.defaultValue == 'select') {
			buf.push('formula="true" ');
			buf.push('defaultValue="OtherFunction.getModel(&quot;', values._selectValue
				, '&quot;, &quot;com.landray.kmss.sys.organization.model.SysOrgElement&quot;');
			if (values.multiSelect == "true") {
				buf.push(', &quot;;&quot;');
			} else {
				buf.push(', null');
			}
			buf.push(')" ');
		} else if (values.defaultValue != '' && values.defaultValue != 'null') {
			//ORG_TYPE_PERSON ORG_TYPE_ORG ORG_TYPE_DEPT ORG_TYPE_POST
			var dv = null;
			if (values.defaultValue == 'ORG_TYPE_PERSON') {
				dv = 'OrgFunction.getCurrentUser()';
			} else if (values.defaultValue == 'ORG_TYPE_ORG') {
				dv = 'OrgFunction.getCurrentOrg()';
			} else if (values.defaultValue == 'ORG_TYPE_DEPT') {
				dv = 'OrgFunction.getCurrentDept()';
			} else if (values.defaultValue == 'ORG_TYPE_POST') {
				if (values.multiSelect == 'true') {
					dv = 'OrgFunction.getCurrentPosts()';
				} else {
					dv = 'OrgFunction.getCurrentPost()';
				}
			}
			buf.push('defaultValue="', dv, '" ');
			buf.push('formula="true" ');
		}
		buf.push('dialogJS="Dialog_Address(!{mulSelect}, \'!{idField}\',\'!{nameField}\', \';\',', values._orgType,');" ');
		buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(customElementProperties)),'" ');
		
		// 字段是否需要加密
		if (values.encrypt == 'true') {
			buf.push('encrypt="true" ');
			buf.push('encryptionMethod="AES" ');
		}
		buf.push('businessType="new_address" ');
		buf.push('/>');
	}else{
		buf.push('<extendSimpleProperty ');
		buf.push('name="', values.id, '" ');
		buf.push('label="', values.label, '" ');
		if(values.dataType=="number"){
		  buf.push('type="Double" ');
		}else{		
		  buf.push('type="String" ');
		}
		if (values.formula != '' && values.formula != null) {
			buf.push('formula="true" ');//.replace(/"/g,"quot;")
			buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
		} 
		
		// 字段是否需要加密
		if (values.encrypt == 'true') {
			buf.push('encrypt="true" ');
			buf.push('encryptionMethod="AES" ');
		}
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
		
		buf.push( '<extendSimpleProperty ');
		buf.push('name="', values.id+"_text", '" ');
		buf.push('label="', values.label + Designer_Lang.controlDisplayValueMessage, '" ');
		buf.push('type="String"  ');
		
		// 字段是否需要加密
		if (values.encrypt == 'true') {
			buf.push('encrypt="true" ');
			buf.push('encryptionMethod="AES" ');
		}
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
	}
	
	return buf.join('');
}

/**
 * 设置属性
 * @param values
 * @param name
 * @param domElement
 * @returns
 */
function _Designer_Control_Formula_Calculation_OnDrawEnd_SetValue(values, name, domElement) {
	if (values[name] != null && values[name] != '') {
		domElement.setAttribute(name, values[name]);
	}
}

/**
 * 设置select样例
 */
function Designer_Control_formulaCalculation_getSelect(values){
	var buf = [];
	buf.push('<label class="select_tag_left"><label class="select_tag_right">');
	buf.push('<label class="select_tag_face" ');
	if (values.width) {
		if(values.width.toString().indexOf('%') > -1){
			buf.push('style="width:',values.width,';">');
		}else{
			buf.push('style="width:',values.width-100+'px',';">');
		}
	}else{
		values.width = "100";
		buf.push('style="width:',values.width+'px',';">');
	}
	if (values.defValue) {
		buf.push(values.defValue, '</label>');
	} else {
		buf.push(Designer_Lang.controlSelectPleaseSelect,'</label>');
	}
	buf.push('</label></label>');
	return buf.join(" ");
}

/**
 * 设置input radio样例
 */ 
function Designer_Control_formulaCalculation_getInputRadio(isHorizontal){
	var initNum = 3;
	var html = "";
	for(var i = 0; i < initNum; i++){
		html += "<label>";
		html += "<input disabled='true' type='radio' />"+Designer_Lang.controlAttrItems + (i + 1);
		html += "</label>";
		if(i < (initNum-1) && !isHorizontal){
			html += '</br>';
		}
	}
	return html;
}

/**
 * 设置input checkbox样例
 */
function Designer_Control_formulaCalculation_getInputCheckbox(isHorizontal){
	var initNum = 3;
	var html = "";
	for(var i = 0; i < initNum; i++){
		html += "<label>";
		html += "<input type='checkbox' disabled='true' onclick='return false;'/>"+Designer_Lang.controlAttrItems + (i + 1);
		html += "</label>";
		if(i < (initNum-1) && !isHorizontal){
			html += '</br>';
		}
	}
	return html;
}

//范围提交校验,校验入参控件跟此控件是否均在明细表内或者外
function Designer_FormulaLoad_Scope_Checkout(msg, name, attr, value, values,control) {
	var formula = values.formula;
	var isPass = Designer_FormulaLoad_Validate(control,formula);
	if (!isPass) {
		msg.push(Designer_Lang.GetMessage(Designer_Lang.controlFormula_scopeMessage2,values.label));
		return false;
	}
	return true;
}

//范围提交校验，属性面板确定都调用此函数
function Designer_FormulaLoad_Validate(control,formula) {
	if(!formula||formula==null||formula==''){
		return true;
	}
	
	//是否在明细表中
	var isindetailsTable = false;
	//明细表id
	var parentId = "";
	if(_Designer_Control_formulaCalculation_getDetailstableParent(control)!=null){
		isindetailsTable = true;
		parentId = _Designer_Control_formulaCalculation_getDetailstableParent(control).options.values.id;
	}
	var info = Designer.instance.getObj(false);
	var uid = [];
	//抓取所有表达式中控件id，并剔除明细表id
	for(var i = 0;i<info.length;i++){
		if(info[i].controlType=='detailsTable' || info[i].controlType=='seniorDetailsTable'){
			continue;
		}
		if(formula.indexOf(info[i].name)>-1){
			uid.push(info[i].name);
		}
	}
	if(uid!=null&&uid.length>0){
		for(var i = 0;i<uid.length;i++){
			if(uid[i].indexOf('.')>-1&&!isindetailsTable||uid[i].indexOf('.')<0&&isindetailsTable){
				return false;
			}else if(uid[i].indexOf('.')>-1&&isindetailsTable){
				if(parentId!=""&&uid[i].substring(0,uid[i].indexOf('.'))!=parentId){
					return false;
				}
			}
		}
	}
	return true;
}

//属性面板确定范围校验
function Designer_FormulaLoad_Scope_Validator(){
	var control = Designer.instance.attrPanel.panel.control;
	var formula = $("input[name='formula']").val();
	var isPass = Designer_FormulaLoad_Validate(control,formula);
	if (!isPass) {
		alert(Designer_Lang.controlFormula_scopeMessage1);
		return false;
	}
	return true;
	
}

//获取当前明细表，如果控件不在明细表内，返回null
function _Designer_Control_formulaCalculation_getDetailstableParent(control){
    var closestDetailsTable = Designer.getClosestDetailsTable(control);
    if (closestDetailsTable) {
        return closestDetailsTable;
    } else {
        return;
    }
}
//加载方式必填校验
function Designer_Control_Attr_loadType_Validator(elem,name,attr,value,values){
	var autoLoad = $("input[name='autoLoad']")[0].checked;
	var manualLoad = $("input[name='manualLoad']")[0].checked;
	if (!autoLoad && !manualLoad){
		alert(Designer_Lang.controlFormula_loadType_requiredMessage);
		return false;
	}
	return true;
}