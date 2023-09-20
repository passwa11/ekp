if(Designer_Lang.checkVersion){
	Designer_Config.operations['standardRule']={
			imgIndex : 90,
			title:Designer_Lang.controlStandardRule_attr_title,
			titleTip:Designer_Lang.controlNew_AddressTitleTip,
			run : function (designer) {
				designer.toolBar.selectButton('standardRule');
			},
			type : 'cmd',
			order: 1,
			select: true,
			cursorImg: 'style/cursor/newAddress.cur'
	};
	Designer_Config.controls.standardRule = {
			type : "standardRule",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_Standard_Rule_OnDraw,
			drawXML : _Designer_Control_Standard_Rule_DrawXML,
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				//监听变动字段
				listenTo : {
					text :Designer_Lang.controlBudgetRule_attr_listenTo,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Listen_To_Draw,
					show : true
				},
				//匹配类型
				matchType : {
					text :Designer_Lang.controlBudgetRule_attr_matchType,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Match_Type_Draw,
					show : true
				},
				//对应明细
				matchTable : {
					text :Designer_Lang.controlBudgetRule_attr_matchTable,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Match_Table_Draw,
					show : true
				},
				//标准匹配维度
				dimension : {
					text :Designer_Lang.controlStandardRule_attr_dimension,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Dimension_Draw,
					show : true
				},
				//归属公司
				company : {
					text :Designer_Lang.controlStandardRule_attr_company,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Company_Draw,
					show : true,
					validator:Designer_Control_Attr_Self_Required_Validator,
					checkout: function(msg, name, attr, value, values, control){
						return true;
					}
				},
				//费用类型
				expenseItem : {
					text :Designer_Lang.controlBudgetRule_attr_expenseItem,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Expense_Item_Draw,
					show : true
				},
				//人员
				person : {
					text :Designer_Lang.controlStandardRule_attr_person,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Person_Draw,
					show : true
				},
				//人员
				dept : {
					text :Designer_Lang.controlStandardRule_attr_dept,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Dept_Draw,
					show : true
				},
				//地域
				area : {
					text :Designer_Lang.controlStandardRule_attr_area,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Area_Draw,
					show : true
				},
				//交通工具
				vehicle : {
					text :Designer_Lang.controlStandardRule_attr_vehicle,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Vehicle_Draw,
					show : true
				},
				travelDays : {
					text :Designer_Lang.controlStandardRule_attr_travelDays,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Travel_Days_Draw,
					show : true
				},
				personNumber : {
					text :Designer_Lang.controlStandardRule_attr_ctype_personNum,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Person_Number_Draw,
					show : true
				},
				//特殊事项
				special : {
					text :Designer_Lang.controlStandardRule_attr_special,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Special_Draw,
					show : true
				},
				//币种
				currency : {
					text :Designer_Lang.controlBudgetRule_attr_currency,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Currency_Draw,
					show : true
				},
				//金额
				money : {
					text :Designer_Lang.controlStandardRule_attr_money,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Standard_Rule_Self_Money_Draw,
					show : true
				},
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "28px",
					type: 'text',
					show: false,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				}
			},
			onAttrLoad:_Designer_Control_Attr_Standard_Rule_OnAttrLoad,
			info : {
				name: Designer_Lang.controlStandardRule_attr_title
			},
			resizeMode : ''
	}
	Designer_Config.buttons.control.push("standardRule");
	//把控件增加到右击菜单区
	Designer_Menus.add.menu['standardRule'] = Designer_Config.operations['standardRule'];
}
function _Designer_Control_Standard_Rule_OnDraw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_Standard_Rule_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_Standard_Rule_DrawByType(parent,attrs,values,target){
	var props = {
		id : values.id,
		subject : values.label,
		fdListenToId : values.listenToId||'',
		fdMatchType : values.matchType||'',
		fdMatchTable : values.matchTableId||'',
		fdCompanyId : values.companyId||'',
		fdExpenseItemId : values.expenseItemId||'',
		fdPersonId : values.personId||'',
		fdDeptId : values.deptId||'',
		fdAreaId : values.areaId||'',
		fdVehicleId : values.vehicleId||'',
		fdSpecialId : values.specialId||'',
		fdTravelDays : values.travelDaysId||'',
		fdMoneyId : values.moneyId||'',
		fdCurrencyId : values.currencyId||'',
		fdPersonNumberId:values.personNumberId||'',
		fdSpecialId : values.specialId||''
	};
	var htmlCode = '<div style="width:28px;height:28px;border:1px solid #ccc;border-radius:3px;line-height:28px;text-align:center;font-size:24px;font-weight:bold;"'
	htmlCode+=' props="'+JSON.stringify(props).replace(/\"/g,"'")+'">S</div><input type="hidden" props="'+JSON.stringify(props).replace(/\"/g,"'")+"\"/>";
	return htmlCode;
}
function _Designer_Control_Standard_Rule_DrawXML() {	
	var values = this.options.values;
	var buf = [];//mutiValueSplit	
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id, '_standard_status" ');
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
	buf.push('name="', values.id, '_standard_info" ');
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
	return buf.join('');
};
function _Designer_Control_Attr_Standard_Rule_OnAttrLoad(form,control){
	
}

function _Designer_Control_Attr_Standard_Rule_Self_Company_Draw(name, attr,value, form, attrs, values, control) {
	var html=[];
	var textValue=values.companyName?values.companyName:"";
	var idValue=values.companyId?values.companyId:"";
	html.push("<input name='companyName' style='width:73%;' value='")
	html.push(textValue)
	html.push("' readonly></input><input type='hidden' name='companyId' value='")
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('companyId','companyName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}

function _Designer_Control_Attr_Standard_Rule_Self_Listen_To_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.listenToName?values.listenToName:"";
	var idValue=values.listenToId?values.listenToId:"";
	html.push("<input name='listenToName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='listenToId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('listenToId','listenToName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a><span class='txtstrong'>*</span>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Match_Type_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<input name='matchType' type='radio' value='1'");
	if(values.matchType=='1'){
		html.push(" checked");
	}
	html.push(" onclick='_Designer_Control_Standard_Rule_Match_Type_Changed(this.value)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchType_main);
	html.push("<input name='matchType' type='radio' value='2'");
	if(values.matchType=='2'){
		html.push(" checked");
	}
	html.push(" onclick='_Designer_Control_Standard_Rule_Match_Type_Changed(this.value)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchType_detail);
	html.push("<span class='txtstrong'>*</span><br><span class='txtstrong'>");
	html.push(Designer_Lang.controlStandardRule_attr_matchType_tips);
	html.push("</span>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Standard_Rule_Match_Type_Changed(v){
	var type = $("[name=matchType]:checked").val();
	if(type=='1'){
		$("#_Designer_Control_Match_Table_Tr").hide();
	}else{
		$("#_Designer_Control_Match_Table_Tr").show();
	}
}
function _Designer_Control_Attr_Standard_Rule_Self_Match_Table_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var value=values.matchTableId||'',text = values.matchTableName||'';
	html.push("<tr id='_Designer_Control_Match_Table_Tr' style='display:");
	if(!values.matchType||values.matchType=='1'){
		html.push("none");
	}
	html.push("'><td width='25%' class='panel_td_title'>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchTable);
	html.push("</td><td>")
	html.push("<input name='matchTableName' style='width:73%;' value='");
	html.push(text);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='matchTableId' value='");
	html.push(value)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('matchTableId','matchTableName','true');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a><span class='txtstrong'>*</span>");
	html.push("<br><span class='txtstrong'>");
	html.push(Designer_Lang.controlStandardRule_attr_matchTable_tips);
	html.push("</span></td></tr>");
	return html.join("");
}
function _Designer_Control_Attr_Standard_Rule_Self_Expense_Item_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.expenseItemName?values.expenseItemName:"";
	var idValue=values.expenseItemId?values.expenseItemId:"";
	html.push("<input name='expenseItemName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='expenseItemId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('expenseItemId','expenseItemName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Person_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.personName?values.personName:"";
	var idValue=values.personId?values.personId:"";
	html.push("<input name='personName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='personId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('personId','personName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Dept_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.deptName?values.deptName:"";
	var idValue=values.deptId?values.deptId:"";
	html.push("<input name='deptName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='deptId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('deptId','deptName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Dimension_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<tr><td colspan='2' align='center' class='panel_td_title'>");
	html.push(Designer_Lang.controlStandardRule_attr_dimension); 
	html.push("</td></tr>");
	return html.join("");
}
function _Designer_Control_Attr_Standard_Rule_Self_Area_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.areaName?values.areaName:"";
	var idValue=values.areaId?values.areaId:"";
	html.push("<input name='areaName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='areaId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('areaId','areaName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
//
function _Designer_Control_Attr_Standard_Rule_Self_Vehicle_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.vehicleName?values.vehicleName:"";
	var idValue=values.vehicleId?values.vehicleId:"";
	html.push("<input name='vehicleName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='vehicleId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('vehicleId','vehicleName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Person_Number_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.personNumber?values.personNumber:"";
	var idValue=values.personNumberId?values.personNumberId:"";
	html.push("<input name='personNumber' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='personNumberId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('personNumberId','personNumber');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Special_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.specialName?values.specialName:"";
	var idValue=values.specialId?values.specialId:"";
	html.push("<input name='specialName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='specialId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('specialId','specialName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Money_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.moneyName?values.moneyName:"";
	var idValue=values.moneyId?values.moneyId:"";
	html.push("<input name='moneyName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='moneyId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('moneyId','moneyName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Target_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<input name='target' type='radio' value='1'");
	if(values.target&&values.target=='1'){
		html.push(" checked"); 
	}
	html.push(">");
	html.push(Designer_Lang.controlStandardRule_attr_target_money)
	html.push("<input name='target' type='radio' value='2' ");
	if(values.target&&values.target=='2'){
		html.push(" checked"); 
	}
	html.push(">");
	html.push(Designer_Lang.controlStandardRule_attr_target_vehicle)
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Standard_Rule_Self_Ctype_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var value=values.showMoney;
	html.push("<tr><td width='25%' class='panel_td_title'>");
	html.push(Designer_Lang.controlStandardRule_attr_ctype);
	html.push("</td><td>")
	html.push("<input name='ctype' type='radio' value='1'");
	if(value&&value.indexOf('1')>-1){
		html.push(" checked"); 
	}
	html.push(" />");
	html.push(Designer_Lang.controlStandardRule_attr_ctype_personNum)
	html.push("<input name='ctype' type='radio' value='2'");
	if(value&&value.indexOf('2')>-1){
		html.push(" checked"); 
	}
	html.push(" />");
	html.push(Designer_Lang.controlStandardRule_attr_ctype_day)
	html.push("<input name='ctype' type='radio' value='3'");
	if(value&&value.indexOf('3')>-1){
		html.push(" checked"); 
	}
	html.push(" />");
	html.push(Designer_Lang.controlStandardRule_attr_ctype_time)
	html.push("<input name='ctype' type='radio' value='4'");
	if(value&&value.indexOf('4')>-1){
		html.push(" checked"); 
	}
	html.push(" />");
	html.push(Designer_Lang.controlStandardRule_attr_ctype_monthTime)
	html.push("</td>");
	return html.join("");
}

function _Designer_Control_Standard_Rule_Ctype_Changed(){
	var val = [];
	$("[name=_ctype]:checked").each(function(){
		val.push(this.value);
	})
	$("[name=ctype]").val(val.join(";"));
}

function _Designer_Control_Attr_Standard_Rule_Self_Config_Draw(){
	var html=[];
	html.push("<tr><td colspan='2' align='center' class='panel_td_title'>");
	html.push(Designer_Lang.controlStandardRule_attr_config); 
	html.push("</td></tr>");
	return html.join("");
}

function _Designer_Control_Attr_Standard_Rule_Self_Travel_Days_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var travelDaysName=values.travelDaysName?values.travelDaysName:"";
	var travelDaysId=values.travelDaysId?values.travelDaysId:"";
	html.push("<input name='travelDaysName' style='width:73%;' value='");
	html.push(travelDaysName);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='travelDaysId' value='");
	html.push(travelDaysId)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('travelDaysId','travelDaysName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}

