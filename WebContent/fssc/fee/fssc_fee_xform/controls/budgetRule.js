	if(Designer_Lang.checkVersion){
	Designer_Config.operations['budgetRule']={
			imgIndex : 90,
			title:Designer_Lang.controlBudgetRule_attr_title,
			titleTip:Designer_Lang.controlNew_AddressTitleTip,
			run : function (designer) {
				designer.toolBar.selectButton('budgetRule');
			},
			type : 'cmd',
			order: 1,
			select: true,
			cursorImg: 'style/cursor/newAddress.cur'
	};
	Designer_Config.controls.budgetRule = {
			type : "budgetRule",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_Budget_Rule_OnDraw,
			drawXML : _Designer_Control_Budget_Rule_DrawXML,
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				//监听变动字段
				listenTo : {
					text :Designer_Lang.controlBudgetRule_attr_listenTo,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Listen_To_Draw,
					show : true
				},
				//匹配类型
				matchType : {
					text :Designer_Lang.controlBudgetRule_attr_matchType,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Match_Type_Draw,
					show : true
				},
				//对应明细
				matchTable : {
					text :Designer_Lang.controlBudgetRule_attr_matchTable,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Match_Table_Draw,
					show : true
				},
				//预算匹配维度
				dimension : {
					text :Designer_Lang.controlBudgetRule_attr_dimension,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Dimension_Draw,
					show : true
				},
				//归属公司
				company : {
					text :Designer_Lang.controlBudgetRule_attr_company,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Company_Draw,
					show : true,
					validator:Designer_Control_Attr_Self_Required_Validator,
					checkout: function(msg, name, attr, value, values, control){
						return true;
					}
				},
				//成本中心
				costCenter : {
					text :Designer_Lang.controlBudgetRule_attr_costCenter,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Cost_Center_Draw,
					show : true
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
				//项目
				project : {
					text :Designer_Lang.controlBudgetRule_attr_project,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Project_Draw,
					show : true
				},
				//WBS
				wbs : {
					text :Designer_Lang.controlBudgetRule_attr_wbs,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Wbs_Draw,
					show : true
				},
				//内部订单
				innerOrder : {
					text :Designer_Lang.controlBudgetRule_attr_innerOrder,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Inner_Order_Draw,
					show : true
				},
				//资产
	//			zc : {
	//				text :Designer_Lang.controlBudgetRule_attr_zc,
	//				value : '',
	//				required: false,
	//				type : 'self',
	//				draw:_Designer_Control_Attr_Budget_Rule_Self_Zc_Draw,
	//				show : true
	//			},
				//员工
				person : {
					text :Designer_Lang.controlBudgetRule_attr_person,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Person_Draw,
					show : true
				},
				//部门
				dept : {
					text :Designer_Lang.controlBudgetRule_attr_dept,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Dept_Draw,
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
					text :Designer_Lang.controlBudgetRule_attr_money,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Money_Draw,
					show : true
				},
				//显示设置
				showSet : {
					text :Designer_Lang.controlBudgetRule_attr_show,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Show_Draw,
					show : true
				},
				//显示方式
				showType : {
					text :Designer_Lang.controlBudgetRule_attr_showType,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Show_Type_Draw,
					show : true
				},
				//显示字段
	//			showField : {
	//				text :Designer_Lang.controlBudgetRule_attr_showField,
	//				value : '',
	//				required: false,
	//				type : 'self',
	//				draw:_Designer_Control_Attr_Budget_Rule_Self_Show_Field_Draw,
	//				show : true
	//			},
				//显示金额
				showMoney : {
					text :Designer_Lang.controlBudgetRule_attr_showMoney,
					value : '',
					required: false,
					type : 'self',
					draw:_Designer_Control_Attr_Budget_Rule_Self_Show_Money_Draw,
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
			onAttrLoad:_Designer_Control_Attr_Budget_Rule_OnAttrLoad,
			info : {
				name: Designer_Lang.controlBudgetRule_attr_title
			},
			resizeMode : ''
	}
	Designer_Config.buttons.control.push("budgetRule");
	//把控件增加到右击菜单区
	Designer_Menus.add.menu['budgetRule'] = Designer_Config.operations['budgetRule'];
}
function _Designer_Control_Budget_Rule_OnDraw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_Budget_Rule_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_Budget_Rule_DrawByType(parent,attrs,values,target){
	var props = {
		id : values.id,
		subject : values.label,
		fdListenToId : values.listenToId||'',
		fdMatchType : values.matchType||'',
		fdMatchTable : values.matchTableId||'',
		fdCompanyId : values.companyId||'',
		fdCostCenterId : values.costCenterId||'',
		fdExpenseItemId : values.expenseItemId||'',
		fdProjectId : values.projectId||'',
		fdWbsId : values.wbsId||'',
		fdInnerOrderId : values.innerOrderId||'',
		fdCurrencyId : values.currencyId||'',
		fdMoneyId : values.moneyId||'',
		fdPersonId : values.personId||'',
		fdDeptId : values.deptId||'',
		fdShowType : values.showType||'',
		fdShowMoney : values.showMoney||''
	};
	var htmlCode = '<div style="width:28px;height:28px;border:1px solid #ccc;border-radius:3px;line-height:28px;text-align:center;font-size:24px;font-weight:bold;"'
	htmlCode+=' props="'+JSON.stringify(props).replace(/\"/g,"'")+'">R</div><input type="hidden" props="'+JSON.stringify(props).replace(/\"/g,"'")+"\"/>";
	return htmlCode;
}
function _Designer_Control_Budget_Rule_DrawXML() {	
	var values = this.options.values;
	var buf = [];//mutiValueSplit	
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id, '_budget_status" ');
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
	buf.push('name="', values.id, '_budget_info" ');
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
function _Designer_Control_Attr_Budget_Rule_OnAttrLoad(form,control){
	
}

function _Designer_Control_Attr_Budget_Rule_Self_Company_Draw(name, attr,value, form, attrs, values, control) {
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

function _Designer_Control_Attr_Budget_Rule_Self_Cost_Center_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.costCenterName?values.costCenterName:"";
	var idValue=values.costCenterId?values.costCenterId:"";
	html.push("<input name='costCenterName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='costCenterId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('costCenterId','costCenterName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Listen_To_Draw(name, attr,value, form, attrs, values, control){
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
function _Designer_Control_Attr_Budget_Rule_Self_Match_Type_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<input name='matchType' type='radio' value='1'");
	if(values.matchType=='1'){
		html.push(" checked");
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Match_Type_Changed(this.value)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchType_main);
	html.push("<input name='matchType' type='radio' value='2'");
	if(values.matchType=='2'){
		html.push(" checked");
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Match_Type_Changed(this.value)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchType_detail);
	html.push("<span class='txtstrong'>*</span><br><span class='txtstrong'>");
	html.push(Designer_Lang.controlBudgetRule_attr_matchType_tips);
	html.push("</span>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Budget_Rule_Match_Type_Changed(v){
	var type = $("[name=matchType]:checked").val();
	if(type=='1'){
		$("#_Designer_Control_Match_Table_Tr").hide();
		$("[name=matchTableName]").val("");
		$("[name=matchTableId]").val("");
	}else{
		$("#_Designer_Control_Match_Table_Tr").show();
	}
}
function _Designer_Control_Attr_Budget_Rule_Self_Match_Table_Draw(name, attr,value, form, attrs, values, control){
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
	html.push(Designer_Lang.controlBudgetRule_attr_matchTable_tips);
	html.push("</span></td></tr>");
	return html.join("");
}
function _Designer_Control_Attr_Budget_Rule_Self_Expense_Item_Draw(name, attr,value, form, attrs, values, control){
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
function _Designer_Control_Attr_Budget_Rule_Self_Wbs_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.wbsName?values.wbsName:"";
	var idValue=values.wbsId?values.wbsId:"";
	html.push("<input name='wbsName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='wbsId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('wbsId','wbsName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Dimension_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<tr><td colspan='2' align='center' class='panel_td_title'>");
	html.push(Designer_Lang.controlBudgetRule_attr_dimension); 
	html.push("</td></tr>");
	return html.join("");
}
function _Designer_Control_Attr_Budget_Rule_Self_Show_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<tr><td colspan='2' align='center' class='panel_td_title'>");
	html.push(Designer_Lang.controlBudgetRule_attr_show); 
	html.push("</td></tr>");
	return html.join("");
}
function _Designer_Control_Attr_Budget_Rule_Self_Project_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.projectName?values.projectName:"";
	var idValue=values.projectId?values.projectId:"";
	html.push("<input name='projectName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='projectId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('projectId','projectName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
//
function _Designer_Control_Attr_Budget_Rule_Self_Inner_Order_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.innerOrderName?values.innerOrderName:"";
	var idValue=values.innerOrderId?values.innerOrderId:"";
	html.push("<input name='innerOrderName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='innerOrderId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('innerOrderId','innerOrderName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Zc_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.zcName?values.zcName:"";
	var idValue=values.zcId?values.zcId:"";
	html.push("<input name='zcName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='zcId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('zcId','zcName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Person_Draw(name, attr,value, form, attrs, values, control){
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
function _Designer_Control_Attr_Budget_Rule_Self_Dept_Draw(name, attr,value, form, attrs, values, control){
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
function _Designer_Control_Attr_Budget_Rule_Self_Currency_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.currencyName?values.currencyName:"";
	var idValue=values.currencyId?values.currencyId:"";
	html.push("<input name='currencyName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='currencyId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('currencyId','currencyName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Money_Draw(name, attr,value, form, attrs, values, control){
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
function _Designer_Control_Attr_Budget_Rule_Self_Show_Type_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	html.push("<input name='showType'type='radio' value='1' onclick='_Designer_Control_Budget_Rule_Show_Type_Changed(this.value,this)'");
	if(values.showType&&values.showType=='1'){
		html.push(" checked"); 
	}
	html.push(">");
	html.push(Designer_Lang.controlBudgetRule_attr_showType_icon)
	html.push("<input name='showType'type='radio' value='2' onclick='_Designer_Control_Budget_Rule_Show_Type_Changed(this.value,this)'");
	if(values.showType&&values.showType=='2'){
		html.push(" checked"); 
	}
	html.push(">");
	html.push(Designer_Lang.controlBudgetRule_attr_showType_money)
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Budget_Rule_Show_Type_Changed(v,e){
	if(v=='1'){
		$("#_Designer_Control_Show_Money_Tr").hide();
	}else{
		$("#_Designer_Control_Show_Money_Tr").show();
	}
}
function _Designer_Control_Attr_Budget_Rule_Self_Show_Field_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var textValue=values.showFieldName?values.showFieldName:"";
	var idValue=values.showFieldId?values.showFieldId:"";
	html.push("<input name='showFieldName' style='width:73%;' value='");
	html.push(textValue);
	html.push("' readonly></input>"); 
	html.push("<input type='hidden' name='showFieldId' value='");
	html.push(idValue)
	html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('showFieldId','showFieldName');\">")
	html.push(Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Budget_Rule_Self_Show_Money_Draw(name, attr,value, form, attrs, values, control){
	var html=[];
	var value=values.showMoney;
	html.push("<tr id='_Designer_Control_Show_Money_Tr' style='display:");
	if(!values.showType||values.showType=='1'){
		html.push("none");
	}
	html.push("'><td width='25%' class='panel_td_title'>");
	html.push(Designer_Lang.controlBudgetRule_attr_showMoney);
	html.push("</td><td>")
	html.push("<input name='_showMoney' type='checkbox' value='1'");
	if(value&&value.indexOf('1')>-1){
		html.push(" checked"); 
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Show_Money_Changed(this.value,this)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_showMoney_all)
	html.push("<input name='_showMoney' type='checkbox' value='2'");
	if(value&&value.indexOf('2')>-1){
		html.push(" checked"); 
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Show_Money_Changed(this.value,this)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_showMoney_used)
	html.push("<input name='_showMoney' type='checkbox' value='3'");
	if(value&&value.indexOf('3')>-1){
		html.push(" checked"); 
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Show_Money_Changed(this.value,this)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_showMoney_using)
	html.push("<input name='_showMoney' type='checkbox' value='4'");
	if(value&&value.indexOf('4')>-1){
		html.push(" checked"); 
	}
	html.push(" onclick='_Designer_Control_Budget_Rule_Show_Money_Changed(this.value,this)'/>");
	html.push(Designer_Lang.controlBudgetRule_attr_showMoney_canUse)
	html.push("<input type='hidden' name='showMoney' value='");
	html.push(values.showMoney||"","'/></td>");
	return html.join("");
}
function _Designer_Control_Budget_Rule_Show_Money_Changed(v,e){
	var value = [];
	$("[name=_showMoney]:checked").each(function(){
		value.push(this.value);
	});
	$("[name=showMoney]").val(value.join(";"));
}
function Designer_Control_Attr_Self_Required_Validator(a,b,c,d,e,f,g){
	console.log(a)
	console.log(b)
	console.log(c)
	console.log(d)
	console.log(e)
	console.log(f)
}

