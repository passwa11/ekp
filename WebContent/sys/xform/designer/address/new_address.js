/**
 * 地址本控件(新)
 * @作者：王逍 @日期：2017年7月26日  
 */

Designer_Config.operations['new_address']={
		lab : "2",
		imgIndex : 61,
		title:Designer_Lang.new_address_name,
		titleTip:Designer_Lang.controlNew_AddressTitleTip,
		run : function (designer) {
			designer.toolBar.selectButton('new_address');
		},
		type : 'cmd',
		order: 8,
		select: true,
		cursorImg: 'style/cursor/newAddress.cur'
};
Designer_Config.controls.new_address={
		type : "new_address",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_New_Address_OnDraw,
		drawMobile : _Designer_Control_New_Address_DrawMobile,
		drawXML : _Designer_Control_New_Address_DrawXML,
		implementDetailsTable : true,
		mobileAlign : "right",
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
			businessType : {
				text: Designer_Lang.controlAddressAttrBusinessType,
				value: "addressDialog",
				type: 'hidden',
				show: true
			},
			multiSelect : {
				text: Designer_Lang.controlAddressAttrMultiSelect,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true,
				onclick: '_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);'
			},
			scope :{
				text: Designer_Lang.controlnew_addressAttrScope,
				value: '',
				type: 'self',
				draw : _Designer_Control_New_Address_Self_Scope_Draw,
				show: true,
				opts:[{text:Designer_Lang.controlNew_AddressAll,value:"11"},
					      {text:Designer_Lang.controlNew_AddressOrg,value:"22"},
					      {text:Designer_Lang.controlNew_AddressDept,value:"33"},
					      {text:Designer_Lang.controlNew_AddressCustom,value:"44"}],
				translator:opts_common_translator
			},
					
			orgType :{
				text: Designer_Lang.controlnew_addressAttrOrgType,
				value: "ORG_TYPE_PERSON",
				type: 'checkGroup',
				opts: [{text:Designer_Lang.controlAddressAttrOrgTypeOrg,value:"ORG_TYPE_ORG", name: '_org_org', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_New_Address_GetSelect(this.form);_Designer_Control_Attr_Address_SelectChange(this.form);"},
					{text:Designer_Lang.controlAddressAttrOrgTypeDept,value:"ORG_TYPE_DEPT", name: '_org_dept', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_New_Address_GetSelect(this.form);_Designer_Control_Attr_Address_SelectChange(this.form);"},
					{text:Designer_Lang.controlAddressAttrOrgTypePost,value:"ORG_TYPE_POST", name: '_org_post', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_New_Address_GetSelect(this.form);_Designer_Control_Attr_Address_SelectChange(this.form);"},
					{text:Designer_Lang.controlAddressAttrOrgTypePerson,value:"ORG_TYPE_PERSON", name: '_org_person', checked: true, onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_New_Address_GetSelect(this.form);_Designer_Control_Attr_Address_SelectChange(this.form);"},
					{text:Designer_Lang.controlAddressAttrOrgTypeGroup,value:"ORG_TYPE_GROUP", name: '_org_group', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_New_Address_GetSelect(this.form);_Designer_Control_Attr_Address_SelectChange(this.form);"}],
				show: true,
				validator : Designer_Address_OrgType_Validator,
				checkout: [Designer_Address_OrgType_Checkout,Designer_Address_Scope_Checkout],
				getVal: Designer_Address_OrgType_getVal,
				translator:opts_common_translator_many
			},
			maxPersonNum : {
				text: Designer_Lang.controlAddressMaxPersonNumText,
				value: "",
				type: 'self',
				show: true,
				draw: _Designer_Control_Attr_MaxPersonNum_Draw,
				validator: Designer_Control_Attr_MaxPersonNum_Validator,
				checkout: Designer_Control_Attr_MaxPersonNum_Checkout
			},
			outputParams : {
				text : "<span>"+Designer_Lang.relation_event_params_out1+Designer_Lang.relation_event_params_out2+"</span>" +
						"<div align='center'><img onclick='_Designer_Control_Add_New_Address_Self_Output(this,0);' title='"+Designer_Lang.controlNew_AddressOutputParamsMsg+"' src='relation_event/style/icons/add.gif' style='cursor:pointer'></img></div>",//传出参数
				value : '',
				type : 'self',
				draw : _Designer_Control_New_Address_Self_Output_Draw,
				show : true,
				getVal : relationOutParams_getVal,
				compareChange: outParams_compareChange,
				translator: outputParams_translator,
				displayText: Designer_Lang.relation_event_params_out1 + Designer_Lang.relation_event_params_out2
			},
			defaultValue: {
				text: Designer_Lang.controlAttrDefaultValue,
				value: "null",
				type: 'self',
				draw: _Designer_Control_New_Address_Self_Draw,
				opts: [{text:Designer_Lang.controlAddressAttrDefaultValueNull,value:"null"},
					{text:Designer_Lang.controlAddressAttrDefaultValueSelf,value:"ORG_TYPE_PERSON"},
					{text:Designer_Lang.controlAddressAttrDefaultValueSelfOrg,value:"ORG_TYPE_ORG"},
					{text:Designer_Lang.controlAddressAttrDefaultValueSelfDept,value:"ORG_TYPE_DEPT"},
					{text:Designer_Lang.controlAddressAttrDefaultValueSelfPost,value:"ORG_TYPE_POST"},
					{text:Designer_Lang.controlAddressAttrDefaultValueSelect,value:"select"}],
				show: true,
				validator : Designer_New_Address_DefaultValue_Validator,
				checkout: Designer_Address_DefaultValue_Checkout,
				translator:opts_common_translator
			},
			template : {
				text : Designer_Lang.controlNew_AddressModel,
				value : 'SysOrgPerson',
				varInfo : '',
				isLock:false,
				type : 'text',
				show : false
			},
			formula: Designer_Config.attrs.formula,
			reCalculate: Designer_Config.attrs.reCalculate,
			isOutout : {
				text: Designer_Lang.controlNew_AddressIsShowOutputParams,
				value: 'false',
				type: 'hidden',
				skipLogChange:true
			},
			new_address_select_value_id: {
				text: Designer_Lang.controlNew_AddressCustomId,
				value: '',
				type: 'hidden',
				skipLogChange:true
			},
			new_address_select_value_name: {
				text: Designer_Lang.controlNew_AddressCustomValue,
				value: '',
				type: 'hidden'
			},
			new_address_select_otherOrgDept_ids: {
				text: Designer_Lang.controlNew_AddressOtherOrgDeptIds,
				value: '',
				type: 'hidden',
				skipLogChange:true
			},
			new_address_select_otherOrgDept_names: {
				text: Designer_Lang.controlNew_AddressOtherOrgDeptNames,
				value: '',
				type: 'hidden'
			},
			new_addressCustomType: {
				text: Designer_Lang.controlNew_AddressCustomType,
				value: '',
				type: 'hidden'
			},
			selectnumber: {
				text: Designer_Lang.controlNew_AddressSelectednNumber,
				value: '',
				type: 'hidden',
				skipLogChange:true
			},
			new_addressOrgType: {
				text: Designer_Lang.controlNew_AddressSelectedType,
				value: '',
				type: 'hidden',
				skipLogChange:true
			}
		},
		onAttrLoad : _Designer_Control_New_Address_OnAttrLoad,
		info : {
			name: Designer_Lang.controlNew_AddressInfoName,
			preview: "address.bmp"
		},
		resizeMode : 'onlyWidth',
		skipChangeLogAttrs:["new_addressCustomType"],
		
};


function outParams_compareChange(name,attr,oldValue,newValue) {
	var newValArr = [];
	var oldValArr = [];
	for (var key in newValue) {
		var newParam = newValue[key];
		if(newParam.fieldNameForm == undefined && newParam.fieldName == undefined ){
			continue;
		}
		var newItemVal = newParam.fieldNameForm + "#" + newParam.fieldName;
		newValArr.push(newItemVal);
	}
	for (var key in oldValue) {
		var oldParam = oldValue[key];
		if(oldParam.fieldNameForm == undefined && oldParam.fieldName == undefined ){
			continue;
		}
		var oldItemVal = oldParam.fieldNameForm + "#" + oldParam.fieldName;
		oldValArr.push(oldItemVal);
	}
	if (newValArr.join("~") != oldValArr.join("~")) {
		var changeResult = {}
		changeResult.oldVal = JSON.stringify(oldValArr);
		changeResult.newVal = JSON.stringify(newValArr);
		return JSON.stringify(changeResult);
	}
}

function outputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	return "<span>"+Designer_Lang.from + "\&nbsp;\&nbsp;\&nbsp;"+ change.oldVal + "\&nbsp;\&nbsp;\&nbsp;"+ Designer_Lang.to + "\&nbsp;\&nbsp;\&nbsp;"+ change.newVal + " </span>";
}
Designer_Config.buttons.form.push("new_address");
//Designer_Menus.form.menu['new_address'] = Designer_Config.operations['new_address'];

//生成地址本Dom对象
function _Designer_Control_New_Address_OnDraw(parentNode, childNode) {
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	domElement.className="xform_new_address";
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		//domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		//domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_New_Address_DrawByType(this.parent, this.attrs, this.options.values, this);
	//当控件只读的时候，只有展示样式只有设置为inline-block才对居左、居中、居右有效
	if (this.options.values.readOnly && this.options.values.readOnly == 'true') {
		domElement.style.display = 'inline-block';
	}
	domElement.innerHTML = htmlCode;
}

function _Designer_Control_New_Address_DrawByType(parent, attrs, values, control) {
	var htmlCode = '<input id="'+values.id+'" class="' + (values.canShow=='false'?'inputhidden':'inputsgl');
	if (values.canShow == 'false') {
		htmlCode += '" canShow="false"';
	} else {
		htmlCode += '" canShow="true"';
	}
	if (values.thousandShow == 'false') {
		htmlCode += '" thousandShow="false"';
	} else {
		htmlCode += '" thousandShow="true"';
	}
	if (values.readOnly == 'true') {
		htmlCode += '" _readOnly="true"';
	} else {
		htmlCode += '" _readOnly="false"';
	}
	if(values.tipInfo != null && values.tipInfo != ''){
		htmlCode += '" tipInfo ="' + values.tipInfo + '"';
	}
	if(values.width==null || values.width==''){
		values.width = "120";
	}
	if (values.width.toString().indexOf('%') > -1) {
		htmlCode += ' style="width:'+values.width+'"';
	} else {
		htmlCode += ' style="width:'+values.width+'px"';
	}
	if (values.required == "true") {
		htmlCode += ' required="true"'
		htmlCode += ' _required="true"'
	} else {
		htmlCode += ' required="false"'
		htmlCode += ' _required="false"'
	}
	//是否摘要
	if(values.summary == "true"){
		htmlCode += ' summary="true"';
	}else{
		htmlCode += ' summary="false"';
	}
	//是否留痕
	if(values.isMark == "true"){
		htmlCode += ' isMark="true"';
	}else{
		htmlCode += ' isMark="false"';
	}
	//地址本限制选择人员数量
	if(values.maxPersonNum && parseInt(values.maxPersonNum) != 0){
		htmlCode += ' maxPersonNum="' + values.maxPersonNum +'"';
	}
	if (parent != null) {
		htmlCode += ' tableName="' + _Get_Designer_Control_TableName(parent) + '"';
	}
	if (values.description != null) {
		htmlCode += ' description="' + values.description + '"';
	}
	htmlCode += ' label="' + _Get_Designer_Control_Label(values, control) + '"';
	if (values.validate != null && values.validate != 'false') {
		htmlCode += ' dataType="' + values.validate + '"';
		htmlCode += ' _dataType="' + values.dataType + '"';
		if (values.validate == 'string') {
			htmlCode += ' maxlength="' + values.maxlength + '"';
			htmlCode += ' validate="{dataType:\'string\'';
			if ( values.maxlength != null &&  values.maxlength != '') {
				htmlCode += ',maxlength:' + values.maxlength;
			}
			htmlCode += '}"';
		} else if (values.validate == 'number') {
			htmlCode += ' scale="' + values.decimal + '"'; // 小数位
			htmlCode += ' beginNum="' +  values.begin + '"';
			htmlCode += ' endNum="' +  values.end + '"';
			htmlCode += ' validate="{dataType:\'number\',decimal:' + values.decimal; // 小数位
			if (values.begin != null && values.begin != '')
				htmlCode += ',begin:' + values.begin;
			if (values.end != null && values.end != '')
				htmlCode += ',end:' + values.end;
			htmlCode += '}"';
		} else if (values.validate == 'email') {
			htmlCode += ' validate="{dataType:\'email\'}"';
		}
	}
	if (values.defaultValue != null && values.defaultValue != '') {
		htmlCode += ' defaultValue="' + values.defaultValue + '"';
		if (!attrs.businessType) {
			htmlCode += ' value="' + values.defaultValue + '"';
		}
	}
	if (attrs.businessType) {
		htmlCode += ' businessType="' + (values.businessType == null ? attrs.businessType.value : values.businessType) + '"';
		if ((values.businessType == 'dateDialog7' ||  values.businessType == 'dateDialog' ||  values.businessType == 'timeDialog' ||  values.businessType == 'datetimeDialog')) {
			if (values.defaultValue == 'select') {
				htmlCode += ' selectedValue="' + values._selectValue + '"';
				htmlCode += ' value="' + values._selectValue + '"';
			} else if (values.defaultValue == 'nowTime') {
				htmlCode += ' value="' + attrs.defaultValue.opts[1].text + '"';
			}
		} else if (values.businessType == "addressDialog") {
			if (values.defaultValue == 'select') {
				htmlCode += ' selectedValue="' + values._selectValue + '"';
				htmlCode += ' selectedName="' + values._selectName + '"';
				htmlCode += ' value="' + values._selectName + '"';
			} else if (values.defaultValue != 'null') {
				for (var jj = 0, l = attrs.defaultValue.opts.length; jj < l; jj ++) {
					if (attrs.defaultValue.opts[jj].value == values.defaultValue) {
						htmlCode += ' value="' + attrs.defaultValue.opts[jj].text + '"';
						break;
					}
				}
			}
		}
	}
	if (attrs.multiSelect) {
		htmlCode += ' multiSelect="' + (values.multiSelect == 'true' ? 'true' : 'false') + '"';
	}
	if (attrs.orgType) {
		var orgType = [];
		var opts = attrs.orgType.opts;
		for (var i = 0; i < opts.length; i ++) {
			var opt = opts[i];
			if (values[opt.name] == null && attrs.orgType.value != null && opts[i].value == attrs.orgType.value) {
				values[opt.name] = "true";
			}
			if (values[opt.name] == "true") {
				orgType.push(opt.value);
			}
		}
		values._orgType = orgType.join('|');
		htmlCode += ' orgType="' + values._orgType + '"';
	}
	if (attrs.history) {
		htmlCode += ' history="' + (values.history == "true" ? 'true' : 'false') + '"';
	}
	if(!values.scope){
		htmlCode += ' scope="11"';
	}else{
		if(values.scope){
			htmlCode += ' scope="' + values.scope + '"';
			if(values.scope=='44'){
				var info = Designer.instance.getObj(false);
				var uid = [];
				//抓取所有表达式中控件id，并剔除明细表id
				var scope_id = values.new_address_select_value_id;
				var scope_name = values.new_address_select_value_name;
				if(scope_id&&scope_name){
					values.new_address_select_value_id = scope_id.replace(/\"/g, "&quot;");
					values.new_address_select_value_name = scope_name.replace(/\"/g, "&quot;");
				}
					//$("input[name='new_address_select_value_id']").val();
				for(var i = 0;i<info.length;i++){
					if(Designer.IsDetailsTableControlObj(info[i])){
						continue;
					}
					if(scope_id.indexOf(info[i].name)>-1){
						uid.push(info[i].name);
					}
				}
				if(values.new_addressCustomType=='org'){
					htmlCode += ' scopeType="org"';
				}else{
					htmlCode += ' scope_id="'+uid.join(';')+'" scopeType="formula"';
				}
			}else if(values.scope=="55"){
				htmlCode += ' otherOrgDeptIds="'+values.new_address_select_otherOrgDept_ids+'"';
			}
		}
	}
	if (values.outputParams) {
		htmlCode += ' outputParams="' + values.outputParams + '"';
	}
	htmlCode += ' readOnly />';
	if(values.required == 'true') {
		htmlCode += '<span class=txtstrong>*</span>';
	}
	
	if (attrs.businessType && values.readOnly != 'true') {
		htmlCode += '<label>&nbsp;<a>'+Designer_Lang.controlAttrSelect+'</a></label>';
	}
	return htmlCode;
}

// 生成XML
function _Designer_Control_New_Address_DrawXML() {
	var values = this.options.values;
	var buf = [];//mutiValueSplit
	buf.push('<extendElementProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if('ORG_TYPE_PERSON'==values._orgType){
		buf.push('type="com.landray.kmss.sys.organization.model.SysOrgPerson" ');
	}
	else{
		buf.push('type="com.landray.kmss.sys.organization.model.SysOrgElement" ');
	}
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
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	buf.push('dialogJS="Dialog_Address(!{mulSelect}, \'!{idField}\',\'!{nameField}\', \';\',', values._orgType,');" ');
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	var customElementProperties = {};
	if(values.scope&&values.scope=='44'&&values.new_address_select_value_id){
		customElementProperties.formula = values.new_address_select_value_id;
		//buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(customElementProperties)),'" ');
	}
	
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	
	buf.push('businessType="', this.type, '" ');
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	if(values.isMark == "true"){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	if(values.maxPersonNum && parseInt(values.maxPersonNum) != 0){
		buf.push('maxPersonNum="',values.maxPersonNum,'" ');
	}
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_New_Address_Self_Output_Draw(name, attr,
		value, form, attrs, values, control) {
	var html = "<input name='outputParams' value='"+(values.outputParams?values.outputParams:"{}")+"' type='hidden'/>"
	html += "<input name='isOutout' value='"+(values.isOutout?values.isOutout:"false")+"' type='hidden'/>";
	if ((values.isOutout && values.isOutout=='false')||!values.isOutout) {
		html += "<div id='relation_event_outputs'>"+Designer_Lang.relation_event_noOutputParams+"</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	
	html += "<div id='relation_event_outputs'>";

	var val = value ? value : "{}";
	var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
	var i=0;
	for(var fid in outputParamsMapping){
		var param = outputParamsMapping[fid];
		param.fid=fid;
		html += _Designer_Control_New_Address_CreateOutParams(param,i==0);
		i++;
	}
	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//输出参数中+号触发的点击事件
function _Designer_Control_Add_New_Address_Self_Output(obj,hiddenFlag){
	//显示按钮面板
	Relation_ShowButton();

	var field={
			"fieldIdForm" : "",
			"fieldNameForm" : Designer_Lang.relation_event_formField,
			"fieldName" : Designer_Lang.relation_event_templateField,
			"fieldId" : "",
			"_required":false
		}; 
	//随机产生一个唯一标识
	field.fid="fm_"+Designer.generateID();
	var isFirst=false;
	//清空初始值                          
	if($("#relation_event_outputs").html()==Designer_Lang.relation_event_noOutputParams){
		$("#relation_event_outputs").html("");
		isFirst=true;
	}
	$("#relation_event_outputs").append(_Designer_Control_New_Address_CreateOutParams(field,isFirst));         
}

function _Designer_Control_New_Address_delOutputParams(obj){
	//显示按钮面板
	Relation_ShowButton();
	
	var fid=$(obj).parent().attr("id");
	
	var outputParams = $("input[name='outputParams']").val();
	
	outputParams = outputParams ? outputParams : "{}";
	var outputParamsJSON = JSON.parse(outputParams.replace(/quot;/g,"\""));
	if(outputParamsJSON[fid]){
		//删除掉 这个对象的映射值 
		delete outputParamsJSON[fid];
	}
	$("input[name='outputParams']").val(JSON.stringify(
			outputParamsJSON).replace(/"/g,"quot;"));
	$(obj).parent().remove();
	//清空到最后一条的时候需要 加上初始值
	if(!$("#relation_event_outputs").html()){
		$("#relation_event_outputs").html(Designer_Lang.relation_event_noOutputParams);
		$("input[name='isOutout']").val('false');
	}
}

function _Designer_Control_New_Address_CreateOutParams(field,isFirst) {
	
	var html = [];

	var fid=field.fid;
	var fieldNameForm = field.fieldNameForm||'';
	html.push("<span id='"+fid+"'>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	
	html.push(" <img src='relation_event/style/icons/delete.gif' onclick='_Designer_Control_New_Address_delOutputParams(this);' style='cursor:pointer;vertical-align:middle;'></img>");
	
	html.push("<input type='hidden' id='" + fid + "_fieldIdForm' value='" + field.fieldIdForm + "' />");
	html.push("<input id='" + fid + "_fieldNameForm' value='"+ fieldNameForm + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	
	
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_New_Address_OpenOutExpressEditor(this,\""+fid+"\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	// html.push("<input type='hidden' name='insParams' value='"+field.uuId+"'
	// />");
	var uuid = field.uuId ? field.uuId : field.fieldId;
	var fieldName = field.fieldName||'';
	html.push("<input type='hidden' id='" + fid
			+ "_fieldId' value='" + uuid + "' />");
	html.push("<input id='" + fid + "_fieldName' value='"
			+ fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;'/>");
	html.push(" <img src='relation_event/style/icons/edit.gif' onclick='_Designer_Control_New_Address_OpenTreeDialog(this,\""
			+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
	
	if(field._required){
		//html.push("<span class='txtstrong'>*</span>");
	}
	html.push("</span>");
	return html.join("");
}

//表单字段dialog
function _Designer_Control_New_Address_OpenOutExpressEditor(obj, uuid){
	idField = document.getElementById(uuid + "_fieldIdForm");
	nameField = document.getElementById(uuid + "_fieldNameForm");
	
	_Designer_Control_New_Address_FieldChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			
			Relation_ShowButton();
			
			$("input[name='isOutout']").val('true');
			
			var outputParams = $("input[name='outputParams']").val();
			outputParams = outputParams ? outputParams : "{}";
			var outputParamsJSON = JSON
					.parse(outputParams.replace(/quot;/g,"\""));
	
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!outputParamsJSON[uuid]){
					outputParamsJSON[uuid]={};
				}
				outputParamsJSON[uuid].fieldIdForm=rtn.data[0].id;
				outputParamsJSON[uuid].fieldNameForm=rtn.data[0].name;
				
				$("input[name='outputParams']").val(JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;"));
			}
	},Designer.instance.getObj(false));
}

//模板字段dialog
function _Designer_Control_New_Address_OpenTreeDialog(obj, uuid) {
	idField = document.getElementById(uuid + "_fieldId");
	nameField = document.getElementById(uuid + "_fieldName");

	var varInfo = _Designer_Control_New_Address_SetInfo();
	
	_Designer_Control_New_Address_FieldFormChoose(idField,nameField,function(rtn){
			if(!rtn){
				return;
			}
			Relation_ShowButton();
			
			$("input[name='isOutout']").val('true');
			var outputParams = $("input[name='outputParams']").val();
			outputParams = outputParams ? outputParams : "{}";
			var outputParamsJSON = JSON
					.parse(outputParams.replace(/quot;/g,"\""));
			if(rtn&&rtn.data&&rtn.data[0].id){
				if(!outputParamsJSON[uuid]){
					outputParamsJSON[uuid]={};
				}
				outputParamsJSON[uuid].fieldId=rtn.data[0].id;
				outputParamsJSON[uuid].fieldName=rtn.data[0].name;
				outputParamsJSON[uuid].fieldEnumType=rtn.data[0].enumType;
				outputParamsJSON[uuid].fieldIsCustom=rtn.data[0].isCustom;
				
				$("input[name='outputParams']").val(JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;"));
			}
	},varInfo);
}

//表单字段数据请求
function _Designer_Control_New_Address_FieldChoose(idField,nameField, action,varInfo) {
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	if(!varInfo){
		varInfo=Designer.instance.getObj(true);
	}
	dialog.Parameters = {
		varInfo : varInfo
	};
	dialog
			.SetAfterShow( function(rtn) {
				action(rtn);
			});
	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/address/new_address_formfields_tree.jsp?t="+encodeURIComponent(new Date());
	dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);

}

//模板字段数据请求
function _Designer_Control_New_Address_FieldFormChoose(idField,nameField, action,varInfo) {
	var dialog = new KMSSDialog();
	dialog.BindingField(idField, nameField);
	if(!varInfo){
		varInfo=Designer.instance.getObj(true);
	}
	dialog.Parameters = {
		varInfo : varInfo
	};
	dialog
			.SetAfterShow( function(rtn) {
				action(rtn);
			});
	dialog.URL = Com_Parameter.ContextPath
			+ "sys/xform/designer/address/new_address_fields_tree.jsp?t="+encodeURIComponent(new Date());
	dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);

}

//获取哪些类型被选中
function _Designer_Control_New_Address_GetSelect(form){
	var i =0;
	var control = Designer.instance.attrPanel.panel.control;
	if(form._org_person.checked){
		form.new_addressOrgType.value = "ORG_TYPE_PERSON";
		i++;
	}
	if(form._org_org.checked){
		form.new_addressOrgType.value = "ORG_TYPE_ORG";
		i++;
	}
	if(form._org_dept.checked){
		form.new_addressOrgType.value = "ORG_TYPE_DEPT";
		i++;
	}
	if(form._org_post.checked){
		form.new_addressOrgType.value = "ORG_TYPE_POST";
		i++;
	}
	if(form._org_group.checked){
		form.new_addressOrgType.value = "ORG_TYPE_GROUP";
		i++;
	}
	form.selectnumber.value=i;
	//var tr= Designer_Control_Attr_AcquireParentTr('defaultValue');
	var tr2= Designer_Control_Attr_AcquireParentTr('outputParams');
	if(i>1){
		form.new_addressOrgType.value = "ORG_TYPE_ELEMENT";
		//tr.css('display','');
		tr2.css('display','none');
	}else if(i==1){
		//tr.css('display','none');
		tr2.css('display','');
		//$("select[name='defaultValue']").val("null");
	}else{
		//tr.css('display','none');
		tr2.css('display','none');
		//$("select[name='defaultValue']").val("null");
	}
	$("input[name='outputParams']").val("");
	$("#relation_event_outputs").html(Designer_Lang.relation_event_noOutputParams);
	$("input[name='isOutout']").val('false'); 
}

//获得模板字段内容
function _Designer_Control_New_Address_SetInfo(){
	var info = [];
	$.ajax({
		  url: Com_Parameter.ContextPath + "sys/xform/controls/address.do?method=getOrgType&orgType="+$("input[name='new_addressOrgType']").val(),
		  type:'GET',
		  async:false,//同步请求
		  success: function(json){
			  info=json;
		  },
		  dataType: 'json'
		});
	return info;
}

//选择范围onDraw
function _Designer_Control_New_Address_Self_Scope_Draw(name, attr,
		value, form, attrs, values, control){
	var val = value?value:'11';
	if(!values.new_addressCustomType){
		values.new_addressCustomType = 'org';
	}
	var html = '';
	html += '<input name="selectnumber" value="'+(control.options.values.selectnumber?control.options.values.selectnumber:1)+'" type="hidden" />';
	html += '<input name="new_addressOrgType" value="'+(control.options.values.new_addressOrgType?control.options.values.new_addressOrgType:'ORG_TYPE_PERSON')+'" type="hidden" />';
	html+='<label isfor="true"><input name="scope" type="radio" value="11" '+(val=='11' ? "checked" : "")+' onclick="_Designer_Control_New_Address_hiddenDiv(this);"/>'+Designer_Lang.controlNew_AddressAll+'</label><br/>';
	html+='<label isfor="true"><input name="scope" type="radio" value="22" '+(val=='22' ? "checked" : "")+' onclick="_Designer_Control_New_Address_hiddenDiv(this);"/>'+Designer_Lang.controlNew_AddressOrg+'</label><br/>';
	html+='<label isfor="true"><input name="scope" type="radio" value="33" '+(val=='33' ? "checked" : "")+' onclick="_Designer_Control_New_Address_hiddenDiv(this);"/>'+Designer_Lang.controlNew_AddressDept+'</label><br/>';
	html+='<label isfor="true"><input name="scope" type="radio" value="55" '+(val=='55' ? "checked" : "")+' onclick="_Designer_Control_New_Address_hiddenDiv(this);"/>'+Designer_Lang.controlNew_AddressOtherOrgOrDept;
	html += '<span id="otherOrgDept_address" style='+(val=="55" ? "" : "display:none")+'>';
	html += '<input name="new_address_select_otherOrgDept_ids" value="'+(values.new_address_select_otherOrgDept_ids || "")+'" type="hidden" />';
	html += '<input name="new_address_select_otherOrgDept_names" value="'+(values.new_address_select_otherOrgDept_names || "")+'" type="text" class="inputsgl" style="width:80%" readonly />';
	html += '<a href="javascript:void(0)" onclick="_Designer_Control_New_Address_Show_Dialog_Address(\'new_address_select_otherOrgDept_ids\',\'new_address_select_otherOrgDept_names\',ORG_TYPE_ORGORDEPT);">'+Designer_Lang.controlNew_AddressSelect+'</a>';
	html += '</span></label><br/>'; 
	html+='<label isfor="true"><input name="scope" type="radio" value="44" '+(val=='44' ? "checked" : "")+' onclick="_Designer_Control_New_Address_showHiddenDiv(this);"/>'+Designer_Lang.controlNew_AddressCustom+'</label>';
	if(!values.new_address_select_value_id&&!values.new_address_select_value_name){
		values.new_address_select_value_id = '';
		values.new_address_select_value_name = '';
	}
	var info = {'id':values.new_address_select_value_id,'name':values.new_address_select_value_name};
	html += "<div id='New_AddressDiv' style='"+(val == '44' ? "" : "display:none")+"'>";
	html += '<input name="new_address_select_value_id" value="'+info.id+'" type="hidden" />';
	html += '<input name="new_address_select_value_name" value="'+info.name+'" type="text" class="inputsgl" style="width:80%" readonly />';
	html += '<span id="new_address_span1">';
	html += '<a href="javascript:void(0)" onclick="_Designer_Control_New_Address_Show_Dialog_Address();">'+Designer_Lang.controlNew_AddressSelect+'</a>';
	html += '</span>';
	html += '<span id="new_address_span2" style="display:none ">';
	html += '<a href="javascript:void(0)" onclick="_Designer_Control_New_Address_Show_Formula();">'+Designer_Lang.controlNew_AddressSelect+'</a>';
	html += '</span><br/>';
	html += '<label isfor="true"><input name="new_addressCustomType" type="radio" value="org" onclick="_Designer_Control_New_Address_switchSelectType(this);" '+(control.options.values.new_addressCustomType=='org' ? "checked" : "")+'/>'+Designer_Lang.controlNew_AddressSelectByOrg+'</label><br/>';
	html += '<label isfor="true"><input name="new_addressCustomType" type="radio" value="formula" onclick="_Designer_Control_New_Address_switchSelectType(this);" '+(control.options.values.new_addressCustomType=='formula' ? "checked" : "")+'/>'+Designer_Lang.controlNew_AddressSelectByFormula+'</label>';	
	html += "</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//范围为自定义时，org或者formula单选按钮点击事件
function _Designer_Control_New_Address_switchSelectType(obj){
	if(obj.value=="org"){
		$("#new_address_span1").css('display','');
		$("#new_address_span2").css('display','none');
	}else{
		$("#new_address_span1").css('display','none');
		$("#new_address_span2").css('display','');
	}
	$("input[name='new_address_select_value_id']").val("");
	$("input[name='new_address_select_value_name']").val("");
}

//公式定义器dialog
function _Designer_Control_New_Address_Show_Formula(){
	Formula_Dialog('new_address_select_value_id','new_address_select_value_name',Designer.instance.getObj(true),'com.landray.kmss.sys.organization.model.SysOrgElement[]',_Designer_Control_New_Address_After_Select,'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction',Designer.instance.control.owner.owner._modelName);
}

//组织架构dialog
function _Designer_Control_New_Address_Show_Dialog_Address(id,name,orgType){
	id = id || "new_address_select_value_id";
	name = name || "new_address_select_value_name";
	orgType = orgType || ORG_TYPE_ALL;
	Dialog_Address(true, id, name, ";", orgType,_Designer_Control_New_Address_After_Select);

	_Designer_Control_Attr_Address_defaultValue_afterClick();
}

//公式定义器dialog和组织架构dialog回调函数
function _Designer_Control_New_Address_After_Select(value){
	
}
 
//显示隐藏的org和formula单选按钮
function _Designer_Control_New_Address_showHiddenDiv(_self){
	$("input[name='isOutout']").val('false');
	$("#New_AddressDiv").css("display","");
	var tr= Designer_Control_Attr_AcquireParentTr('orgType');
	tr.css('display','none');
	var tr2= Designer_Control_Attr_AcquireParentTr('outputParams');
	tr2.css('display','none');
	//var tr3= Designer_Control_Attr_AcquireParentTr('defaultValue');
	//tr3.css('display','');
	$("input[name='outputParams']").val("");
	$("#relation_event_outputs").html(Designer_Lang.relation_event_noOutputParams);
	$("input[name='_org_org']").prop("checked", true);
	$("input[name='_org_dept']").prop("checked", true);
	$("input[name='_org_post']").prop("checked", true);
	$("input[name='_org_person']").prop("checked", true);
	$("input[name='_org_group']").prop("checked", true);
	$("input[name='_org_org']").prop('disabled',false);
	$("input[name='_org_dept']").prop('disabled',false);
	$("input[name='_org_post']").prop('disabled',false);
	$("input[name='_org_person']").prop('disabled',false);
	$("input[name='_org_group']").prop('disabled',false);
	_Show_Address_DefaultValue(_self.form.defaultValue);
	_self.form.selectnumber.value=5;
	_self.form.new_addressOrgType.value='ORG_TYPE_ELEMENT';
	
	_Designer_Control_Attr_Address_SelectChange(_self.form);
}

//隐藏org和formula单选按钮
function _Designer_Control_New_Address_hiddenDiv(_self){
	var control = Designer.instance.attrPanel.panel.control;
	if(_self.value=='11'){
		$("input[name='_org_org']").prop('disabled',false);
		$("input[name='_org_dept']").prop('disabled',false);
		$("input[name='_org_post']").prop('disabled',false);
		$("input[name='_org_person']").prop('disabled',false);
		$("input[name='_org_group']").prop('disabled',false);
	}else{
		control.options.values._org_org = "false";
		control.options.values._org_group = "false";
		$("input[name='_org_org']").prop('disabled','false');
		$("input[name='_org_dept']").prop('disabled',false);
		$("input[name='_org_post']").prop('disabled',false);
		$("input[name='_org_person']").prop('disabled',false);
		$("input[name='_org_group']").prop('disabled','false');
	}
	if(_self.value=='55'){
		$("#otherOrgDept_address").show();
	}else{
		$("#otherOrgDept_address").hide();
	}
	$("input[name='new_address_select_otherOrgDept_ids']").val("");
	$("input[name='new_address_select_otherOrgDept_names']").val("");
	
	var tr= Designer_Control_Attr_AcquireParentTr('orgType');
	tr.css('display','');
	$("input[name='isOutout']").val('false');
	$("#New_AddressDiv").css("display","none");
	$("input[name='new_addressCustomType']").eq(0).prop('checked', 'true');
	$("#new_address_span1").css('display','');
	$("#new_address_span2").css('display','none');
	$("input[name='new_address_select_value_id']").val('');
	$("input[name='new_address_select_value_name']").val('');
	$("input[name='_org_org']").prop('checked',false);
	$("input[name='_org_dept']").prop('checked',false);
	$("input[name='_org_post']").prop('checked',false);
	$("input[name='_org_person']").prop('checked',true);
	$("input[name='_org_group']").prop('checked',false);
	//var tr= Designer_Control_Attr_AcquireParentTr('defaultValue');
	//tr.css('display','none');
	_Show_Address_DefaultValue(_self.form.defaultValue);
	var tr2= Designer_Control_Attr_AcquireParentTr('outputParams');
	tr2.css('display','');
	$("input[name='outputParams']").val("");
	$("#relation_event_outputs").html(Designer_Lang.relation_event_noOutputParams);
	_self.form.selectnumber.value=1;
	_self.form.new_addressOrgType.value='ORG_TYPE_PERSON';
	
	_Designer_Control_Attr_Address_SelectChange(_self.form);
}

//初始值onDraw
function _Designer_Control_New_Address_Self_Draw(name, attr, value, form, attrs, values) {
	var html = "<select name='defaultValue' class='attr_td_select' style='width:95%' onchange='_Show_Address_DefaultValue(this)'>";
	html += "<option value='null' >"+Designer_Lang.controlAddressAttrDefaultValueNull+"</option>";
	if (value) {
		if (values._org_person == 'true')
			html += "<option value='ORG_TYPE_PERSON' " + (value == 'ORG_TYPE_PERSON' ? "selected='selected'" : "") + ">"+Designer_Lang.controlAddressAttrDefaultValueSelf+"</option>";
		if (values._org_org == 'true')
			html += "<option value='ORG_TYPE_ORG' " + (value == 'ORG_TYPE_ORG' ? "selected='selected'" : "") + ">"+Designer_Lang.controlAddressAttrDefaultValueSelfOrg+"</option>";
		if (values._org_dept == 'true')
			html += "<option value='ORG_TYPE_DEPT' " + (value == 'ORG_TYPE_DEPT' ? "selected='selected'" : "") + ">"+Designer_Lang.controlAddressAttrDefaultValueSelfDept+"</option>";
		if (values._org_post == 'true')
			html += "<option value='ORG_TYPE_POST' " + (value == 'ORG_TYPE_POST' ? "selected='selected'" : "") + ">"+Designer_Lang.controlAddressAttrDefaultValueSelfPost+"</option>";
	} else {
		html += "<option value='ORG_TYPE_PERSON'>"+Designer_Lang.controlAddressAttrDefaultValueSelf+"</option>";
	}
	html += "<option value='select' " + (value == 'select' ? "selected='selected'" : "") + ">"+Designer_Lang.controlAddressAttrDefaultValueSelect+"</option>";
	html += "</select>";
	html += "<div style='" + (value == 'select' ? "" : "display:none;");
	html += "'>"
	if (value == 'select') {
		var orgType = [];
		var opts = attrs.orgType.opts;
		for (var i = 0; i < opts.length; i ++) {
			var opt = opts[i];
			if (values[opt.name] == "true") {
				orgType.push(opt.value);
			}
		}
		if (orgType.length == 0) orgType.push("ORG_TYPE_ALL");
		html += "<input type='hidden' name='_selectValue'";
		html += " value='" + (values._selectValue != null ? values._selectValue : "") + "'>";
		html += "<input type='text' style='width:80%' class='attr_td_text' readonly name='_selectName'";
		html += " value='" + (values._selectName != null ? values._selectName : "") + "'>";
		html += "<span class=txtstrong>*</span><span><a href='javascript:void(0)' onclick='Dialog_Address("+ (values.multiSelect != 'true' ? "false" : "true")
			+", \"_selectValue\",\"_selectName\", \";\","+ orgType.join("|")+");return false;'>"+Designer_Lang.controlAttrSelect+"</a></span>";
	}
	html += "</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//初始值校验
function Designer_New_Address_DefaultValue_Validator(elem, name, attr, value, values) {
	if(!Designer_Address_OutputParams_Validator()||!__Designer_Address_Scope_Checkout()){
		return false;
	}
	if (value == 'select') {
		var addressInput = elem.form['_selectName'];
		if (addressInput.value == null || addressInput.value == '') {
			alert(Designer_Lang.GetMessage(Designer_Lang.controlAttrValNotNull,elem.options[elem.selectedIndex].text));
			addressInput.focus();
			return false;
		}
	}
	return true;
}

//attrs加载后事件
function _Designer_Control_New_Address_OnAttrLoad(form){
	//var tr= Designer_Control_Attr_AcquireParentTr('defaultValue');
	var tr2= Designer_Control_Attr_AcquireParentTr('orgType');
	var tr3= Designer_Control_Attr_AcquireParentTr('outputParams');
	var control = Designer.instance.attrPanel.panel.control;
	if($('input:radio[name="scope"]:checked').val()=='44'){
		//tr.css('display','');
		tr2.css('display','none');
		tr3.css('display','none');
	}else{
		if(!control.options.values.selectnumber){
			//tr.css('display','none');
			tr2.css('display','');
			tr3.css('display','');
		}else if(control.options.values.selectnumber==1){
			//tr.css('display','none');
			tr2.css('display','');
			tr3.css('display','');
		}else if(control.options.values.selectnumber>1){
			//tr.css('display','');
			tr2.css('display','');
			tr3.css('display','none');
		}
	}
	if($('input:radio[name="scope"]:checked').val()=='22'||$('input:radio[name="scope"]:checked').val()=='33'||$('input:radio[name="scope"]:checked').val()=='55'){
		$("input[name='_org_org']").prop('disabled','false');
		$("input[name='_org_dept']").prop('disabled',false);
		$("input[name='_org_post']").prop('disabled',false);
		$("input[name='_org_person']").prop('disabled',false);
		$("input[name='_org_group']").prop('disabled','false');
	}else{
		$("input[name='_org_org']").prop('disabled',false);
		$("input[name='_org_dept']").prop('disabled',false);
		$("input[name='_org_post']").prop('disabled',false);
		$("input[name='_org_person']").prop('disabled',false);
		$("input[name='_org_group']").prop('disabled',false);
	}
	if(control.options.values.new_addressCustomType=='org'){
		$("#new_address_span1").css('display','');
		$("#new_address_span2").css('display','none');
	}else{		
		$("#new_address_span1").css('display','none');
		$("#new_address_span2").css('display','');
	}
	_Designer_Control_Attr_Address_SelectChange(form);
}

//传出参数校验
function Designer_Address_OutputParams_Validator() {
	var control = Designer.instance.attrPanel.panel.control;
	var outputParams = $("input[name='outputParams']").val();
	if(!outputParams||outputParams==null||outputParams==''){
		return true;
	}
	var outputParamsMapping = JSON.parse($("input[name='outputParams']").val().replace(/quot;/g,"\""));
	var isindetailsTable = false;
	var parentId = "";
	if(_Designer_Control_NewAddress_getDetailstableParent(control)!=null){
		isindetailsTable = true;
		parentId = _Designer_Control_NewAddress_getDetailstableParent(control).options.values.id;
	}
	for(var fid in outputParamsMapping){
		var param = outputParamsMapping[fid];
		var idform = param.fieldIdForm;
		if(!idform){
			continue;
		}
		if(idform.indexOf('.')>-1&&!isindetailsTable||idform.indexOf('.')<0&&isindetailsTable){
			alert(Designer_Lang.controlNew_AddressMsg);
			return false;
		}else if(idform.indexOf('.')>-1&&isindetailsTable){
			if(parentId!=""&&idform.substring(0,idform.indexOf('.'))!=parentId){
				alert(Designer_Lang.controlNew_AddressMsg);
				return false;
			}
		}
	}
	return true;
}

//提交校验
function Designer_Address_Scope_Checkout(msg, name, attr, value, values,control) {
	var scope_id = values.new_address_select_value_id;
	var isPass = Designer_Address_Scope_Validate(control,scope_id);
	if (!isPass) {
		msg.push(Designer_Lang.GetMessage(Designer_Lang.controlNew_AddressMsg3,values.label));
		return false;
	}
	return true;
}

//范围校验chekout,validator统一调用此函数
function Designer_Address_Scope_Validate(control,scope_id){
	if(!scope_id||scope_id==null||scope_id==''){
		return true;
	}
	//是否在明细表中
	var isindetailsTable = false;
	//明细表id
	var parentId = "";
	if(_Designer_Control_NewAddress_getDetailstableParent(control)!=null){
		isindetailsTable = true;
		parentId = _Designer_Control_NewAddress_getDetailstableParent(control).options.values.id;
	}
	var info = Designer.instance.getObj(false);
	var uid = [];
	//抓取所有表达式中控件id，并剔除明细表id
	for(var i = 0;i<info.length;i++){
		if(Designer.IsDetailsTableControlObj(info[i])){
			continue;
		}
		if(scope_id.indexOf(info[i].name)>-1){
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

//自定义表达式参数校验,被validator调用
function __Designer_Address_Scope_Checkout(){
	var control = Designer.instance.attrPanel.panel.control;
	var scope_id = $("input[name='new_address_select_value_id']").val();
	var isPass = Designer_Address_Scope_Validate(control,scope_id);
	if (!isPass) {
		alert(Designer_Lang.controlNew_AddressMsg2);
		return false;
	}
	return true;
}

//获取当前明细表，如果控件不在明细表内，返回null
function _Designer_Control_NewAddress_getDetailstableParent(control){
    var closestDetailsTable = Designer.getClosestDetailsTable(control);
	if (closestDetailsTable) {
		return closestDetailsTable;
	} else {
		return;
	}
}
function relationSource_getVal(name,attr,value,controlValue) {
	var opts = attr["opts"];
	if(undefined==opts){
    		return "";
    	}
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.value === value) {
			controlValue[name] = opt.text;
			return opt.text;
		}
	}
	return "";
}


function opts_common_translator(change,obj) {
	if (!change) {
		return "";
	}
	var opts = obj.opts;
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.value === change.before) {
			change.oldVal= opt.text;
		}
		if (opt.value === change.after) {
			change.newVal= opt.text;
		}
	}
	
	if(change.oldVal==undefined){
		change.oldVal = "";
	}
	var html = "<span> 由 (" + change.oldVal + ") 变更为 (" + change.newVal + ")</span>";
	return html; 
}
