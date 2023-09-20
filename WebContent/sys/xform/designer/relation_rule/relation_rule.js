
// 获取所有数据源
// var relationSource = new relationSource();
Designer_Config.operations['relationRule'] = {
	lab : "5",
	imgIndex : 56,
	title :Designer_Lang.relation_rule_name,
	run : function(designer) {
		designer.toolBar.selectButton('relationRule');
	},
	type : 'cmd',
	order: 2.5,
	line_splits_end:true,
	shortcut : '',
	isAdvanced: true,
	select : true,
	cursorImg : 'relation_rule/style/relation_rule.cur'
};
Designer_Config.controls.relationRule = {
	type : "relationRule",
	storeType : 'none',
	inherit : 'base',
	container : false,
	onDraw : _Designer_Control_RelationRule_OnDraw,
	drawMobile : _Designer_Control_RelationRule_DrawMobile,
	drawXML : _Designer_Control_RelationEvent_DrawXML,
	implementDetailsTable : true,
	// 在新建流程文档的时候，是否显示
	hideInMainModel : true,
	attrs : {
		bindDom : {
			text : Designer_Lang.relation_rule_trigger_dom,
			value : '',
			required: true,
			type : 'self',
			draw:_Designer_Control_Attr_Relation_Rule_Self_BindDom_Draw,
			show : true,
			checkout: function(msg, name, attr, value, values, control){
				if(!values.binddomId){
					 msg.push(Designer_Lang.relation_rule_name+""+values.id,","+Designer_Lang.relation_rule_trigger_dom+""+Designer_Lang.relation_rule_notNul);
					return false;
				}
				return true;
			}
		},
		binddomName : {
			text : Designer_Lang.relation_rule_trigger_dom,
			show : false
		},
		bindEvent : {
			text : Designer_Lang.relation_event_trigger_event,
			value : '',
			type : 'select',
			opts:[
			      {"text":Designer_Lang.relation_event_onchange,'value':'change'},
			      {"text":Designer_Lang.relation_rule_cusotmEvent,'value':'relation_rule_event'}],
			show : true,
			translator:valueTranslate
		},
		destDom : {
			text : Designer_Lang.relation_rule_destdom,
			value : '',
			required: true,
			type : 'self',
			draw:_Designer_Control_Attr_Relation_Rule_Self_DestDom_Draw,
			show : true,
			checkout: function(msg, name, attr, value, values, control){
				if(!values.destdomId){
				    msg.push(Designer_Lang.relation_rule_name+""+values.id,","+Designer_Lang.relation_rule_destdom+""+Designer_Lang.relation_rule_notNul);
					return false;
				}
				return true;
			}
		},
		destdomName : {
			text : Designer_Lang.relation_rule_destdom,
			show : false
		},
		op:{
			text : "<span style='cursor:pointer' onclick='addRelationRuleConditionOp();' title='"+Designer_Lang.relation_rule_op+"'>"+Designer_Lang.relation_rule_op+"<img src='relation_rule/style/icons/add.gif'></img></span>",//动作
			value:'',
			show:true,
			type : 'self',
			draw : _Designer_Control_Attr_Relation_Rule_Self_Op_Draw,
			checkout: function(msg, name, attr, value, values, control){
				var val = value ? value : "[]";
				var opValues = _Designer_Control_Attr_Relation_Rule_parseStr(val);
				for(var uid in opValues){
					var cond = opValues[uid]["cndId"];
					if(cond==''){
						  msg.push(Designer_Lang.relation_rule_name+""+values.id,","+Designer_Lang.relation_rule_op+""+Designer_Lang.relation_rule_notNul);
						return false;
					}
				}
				return true;
			},
			getVal : relationRule_getVal,
			compareChange: relationRule_compareChange,
			translator: relationRule_translator,
			displayText: Designer_Lang.relation_rule_op
		}
	},
	info : {
		name : Designer_Lang.relation_rule_name
		//preview : "mutiTab.png"
	},
	skipChangeLogAttrs:["required_","readonly_","display_","destdomId","binddomId","condName","condID"],
	resizeMode : 'no'
};
Designer_Config.buttons.tool.push("relationRule");
Designer_Menus.tool.menu['relationRule'] = Designer_Config.operations['relationRule'];
function _Designer_Control_Attr_Relation_Rule_Self_DestDom_Draw(name, attr,
		value, form, attrs, values, control) {
	var html=[];
	var textValue=values.destdomName?values.destdomName:"";
	var idValue=values.destdomId?values.destdomId:"";
	html.push("<input name='destdomName' style='width:73%;' value='"+textValue+"' readonly></input>" +
			"<input type='hidden' name='destdomId' value='"+idValue+"'></input>" +
					"<a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('destdomId','destdomName');\">"+Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Relation_Rule_Self_BindDom_Draw(name, attr,
		value, form, attrs, values, control) {
	var html=[];
	var textValue=values.binddomName?values.binddomName:"";
	var idValue=values.binddomId?values.binddomId:"";
	html.push("<input name='binddomName' style='width:73%;' value='"+textValue+"' readonly></input>" +
			"<input type='hidden' name='binddomId' value='"+idValue+"'></input>" +
					"<a href='javascript:void(0)' onclick=\"_Relation_Rule_BindDomControlsChoose('binddomId','binddomName');\">"+Designer_Lang.relation_rule_choose+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_Relation_Rule_Self_Op_Draw(name, attr,
		value, form, attrs, values, control) {
	
	var html = "<div id='relation_rule_ops'>";
	var val = value ? Relation_Convert_HTML_ForJSon(value) : "[]";
	var opValues = _Designer_Control_Attr_Relation_Rule_parseStr(val);
	var i=0;
	html += "<input type='hidden' name='op' value='"+ val +"'/>";
	for(var uid in opValues){
		var val = opValues[uid];
		html += _CreateRelationRuleConditionOp(val,i==0);
		i++;
	}
	if(val=="[]"){
		html +=_CreateRelationRuleConditionOp({uid:'uid'+Designer.generateID()},true);
	}

	html+="</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

// 解析string
function _Designer_Control_Attr_Relation_Rule_parseStr(str){
	var val;
	try{
		val = JSON.parse(str.replace(/quot;/g,"\""));
	}catch(e){
		console.log(str + " 转换为json时出错！");
	}
	return val;
}

function addRelationRuleConditionOp(){
	var isFist=$("#relation_rule_ops").html()=='';
	$("#relation_rule_ops").append(_CreateRelationRuleConditionOp({uid:'uid'+Designer.generateID()},isFist)); 
}
function removeRelationRuleConditionOp(uid,obj){
	var control = Designer.instance.attrPanel.panel.control;
	/*var opVals=control.options.values.op;*/
	var opVals = Relation_Convert_HTML_ForJSon($("input[type='hidden'][name='op']").val());
	var opVals=opVals?opVals:"[]";
	var opValObjs=_Designer_Control_Attr_Relation_Rule_parseStr(opVals);
	//校验至少保留一个动作
	if($("#relation_rule_ops").children("div").length==1){
		alert(Designer_Lang.relation_rule_requiredOne+Designer_Lang.relation_rule_op);
		return ;
	}
	var hasIn=false;
	for(var opV in opValObjs){
		if(uid==opValObjs[opV].uid){
			//从数据组中移除掉这个元素
			opValObjs.splice(opV, 1);
			/*control.options.values.op = JSON.stringify(
					opValObjs).replace(/"/g,"quot;");*/
			$("input[type='hidden'][name='op']").val(JSON.stringify(
					opValObjs).replace(/"/g,"quot;"));
			//Designer.spliceArray(opValObjs,opValObjs[opV]);
			break;
		}
	}
	$(obj).parent().remove();
	//显示按钮面板
	Relation_ShowButton();
}
function _CreateRelationRuleConditionOp(field,isFirst) {
	//显示按钮面板
	Relation_ShowButton();
	var c=Designer.instance.getObj(false);
	var html = [];

	var uid=field.uid;
	/*var cndId=Relation_Convert_HTML_ForHTML(field.cndId?field.cndId:'');
	var cndName=Relation_Convert_HTML_ForHTML(field.cndName?field.cndName:'');*/
	var cndId=field.cndId?field.cndId:'';
	var cndName=field.cndName?field.cndName:'';
	var disp= field.display?field.display:'';
	var required= field.required?field.required:'';
	var readonly= field.readonly?field.readonly:'';
	
	html.push("<div id=''>");
	//第一个元素不需要分隔符
	if(!isFirst){
		html.push("<hr />");
	}
	html.push(Designer_Lang.relation_rule_condition+"<input  type='hidden'  value='"+cndId+"' readOnly=true  style='width:120px;vertical-align:middle;' name='condID_"+uid+"' />" +
			"<input   value='"+cndName+"' readOnly=true  style='width:114px;vertical-align:middle;' name='condName_"+uid+"' />" +
			"<a href='javascript:void(0)' onclick =\"_Relation_Rule_Condition_Choose('"+uid+"');\">"+Designer_Lang.relation_rule_choose+"</a>");
	html.push("<span class=txtstrong>*</span></br>");		
	html.push(Designer_Lang.relation_rule_display+"<select name='display_"+uid+"'  onchange=\"_Relation_Rule_OpItems_Change('"+uid+"',this,'display');\">");
	html.push("<option value=''>"+Designer_Lang.relation_rule_choose_please+"</option>");
	html.push("<option value='1' "+(('1'==disp)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_display_dispcontrol+"</option>");
	html.push("<option value='0' "+(('0'==disp)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_display_hidecontrol+"</option>");
	html.push("<option value='21' "+(('21'==disp)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_display_disprow+"</option>");
	html.push("<option value='20' "+(('20'==disp)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_display_hiderow+"</option>");
	html.push("</select>");
	html.push(Designer_Lang.relation_rule_required+"<select name='required_"+uid+"' onchange=\"_Relation_Rule_OpItems_Change('"+uid+"',this,'required');\">");
	html.push("<option value=''>"+Designer_Lang.relation_rule_choose_please+"</option>");
	html.push("<option value='1' "+(('1'==required)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_required_yes+"</option>");
	html.push("<option value='0' "+(('0'==required)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_required_no+"</option>");
	html.push("</select>");
	html.push(Designer_Lang.relation_rule_readonly+"<select name='readonly_"+uid+"' onchange=\"_Relation_Rule_OpItems_Change('"+uid+"',this,'readonly');\">");
	html.push("<option value=''>"+Designer_Lang.relation_rule_choose_please+"</option>");
	html.push("<option value='1' "+(('1'==readonly)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_readonly_yes+"</option>");
	html.push("<option value='0' "+(('0'==readonly)?"selected='selected'":"")+">"+Designer_Lang.relation_rule_readonly_no+"</option>");
	html.push("</select>");
	html.push("<img src='relation_rule/style/icons/delete.gif' style='cursor:pointer' onclick=\"removeRelationRuleConditionOp('"+uid+"',this);\"></img>");
	html.push("</div>");
	return html.join("");
}
function _Relation_Rule_OpItems_Change(uid,obj,profix){
	var control = Designer.instance.attrPanel.panel.control;
	/*var opVals=control.options.values.op;*/
	var opVals = Relation_Convert_HTML_ForJSon($("input[type='hidden'][name='op']").val());
	var opVals=opVals?opVals:"[]";
	var opValObjs=_Designer_Control_Attr_Relation_Rule_parseStr(opVals);
	
	var opVal={"uid":uid};
	var hasIn=false;
	for(var opV in opValObjs){
		if(uid==opValObjs[opV].uid){
			opVal=opValObjs[opV];
			hasIn=true;
			break;
		}
	}
	opVal[profix]=$(obj).val();
	if(!hasIn){
		opValObjs.push(opVal);
	}
	/*control.options.values.op = JSON.stringify(
			opValObjs).replace(/"/g,"quot;");*/
	$("input[type='hidden'][name='op']").val(JSON.stringify(
			opValObjs).replace(/"/g,"quot;"));
}
function Relation_Convert_HTML_ForJSon(str){
	if(!str) return str;
	str= str.replace(/\"/g, "&#34;");
	str= str.replace(/\'/g, "&#39;");
	return str;
}
function Relation_Convert_HTML_ForHTML(str){
	if(!str) return str;
	str= str.replace(/\&#34;/g, "\"");
	str= str.replace(/\&#39;/g, "\'");
	return str;
}
function _Relation_Rule_Condition_Choose(uid){
	var control = Designer.instance.attrPanel.panel.control;
	/*var opVals=control.options.values.op;*/
	var opVals = Relation_Convert_HTML_ForJSon($("input[type='hidden'][name='op']").val());
	var opVals=opVals?opVals:"[]";
	var opValObjs = _Designer_Control_Attr_Relation_Rule_parseStr(opVals);
	var id="condID_"+uid;
	var name="condName_"+uid;
	var opVal={"uid":uid};
	var hasIn=false;
	for(var opV in opValObjs){
		if(uid==opValObjs[opV].uid){
			opVal=opValObjs[opV];
			hasIn=true;
			break;
		}
	}
	var fieldLayout = Designer_Control_Relation_Rule_getCanApplyFieldLayout();
	var objs = fieldLayout.concat(Designer.instance.getObj(false));
	//选择条件
	Relation_Formula_Eval_Dialog(id,name,objs,'String',function(rtn){
		if(!rtn) return;
		//保存条件
		opVal.cndId=Relation_Convert_HTML_ForJSon(rtn.data[0].id);
		opVal.cndName=Relation_Convert_HTML_ForJSon(rtn.data[0].name);
		if(!hasIn){
			opValObjs.push(opVal);
		}
		/*control.options.values.op = JSON.stringify(
				opValObjs).replace(/"/g,"quot;");*/
		$("input[type='hidden'][name='op']").val(JSON.stringify(
				opValObjs).replace(/"/g,"quot;"));
	});
	
}

function _Relation_Rule_OpenDialog(url,id,name,varInfos,callback){
	var dialog = new KMSSDialog();
	dialog.BindingField(id, name);
	dialog.Parameters = {
		varInfo : varInfos
	};
	if(callback){
		dialog.SetAfterShow(callback);	
	}
	dialog.URL = Com_Parameter.ContextPath + url;
	dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);
}

// 目标控件选择
function _Relation_Rule_DestDomControlsChoose(id,name,isShowCheckBox){
	// 获取所有控件，包括容器类
	var c = Designer.instance.getObj(false);
	var c1 = [];
	var controls = Designer.instance.builder.controls;
	var objs = [];
	_Relation_Rule_GetAllDivControl(controls,objs);
	var obj = [];
	c = c.concat(objs);
	var noShowControls = ["auditShow", "auditNote", "newAuditNote", "circulationOpinionShow"];
	// 过滤掉所有不支持的控件
	for (var i = 0, l = c.length; i < l; i ++) {
		var control = c[i];
		var controlType = control.controlType;
		if (noShowControls.indexOf(controlType) > -1) {
			continue;
		}
		obj.push(c[i]);
	}
	c = obj;
	var tableId = _Relation_Rule_BindDomControlsIsInDetailTable();
	if(tableId){//若属性变更控件在明细表中，目标控件只读该明细表的
		for(var i in c ){
			if(c[i].name && c[i].name.indexOf(".")>=0 && c[i].name.indexOf(tableId)>=0){
				c1.push(c[i]);
			}
		}
	}else{
		c1 = c;
	}
	//属性列表
	var fieldLayout = Designer_Control_Relation_Rule_getCanApplyFieldLayout();
	c1 = fieldLayout.concat(c1);
	var url = "sys/xform/designer/relation_rule/relation_rule_destdom.jsp?isShowCheckBox="+isShowCheckBox;
	_Relation_Rule_OpenDialog(url,id,name,c1);
}

function _Relation_Rule_GetAllDivControl(controls,objs){
	for (var i = 0, l = controls.length; i < l; i ++) {
		var control = controls[i];
		if (control.storeType == 'layout') {
			if(control.type == 'divcontrol'){
				/*#150419-V16属性变更目标控件无法选择div-开始
				if(control.hideInMainModel == 'true'){//666
				#150419-V16属性变更目标控件无法选择div-结束*/
					var obj = {}
					obj.name = control.options.values.id;
					obj.label = control.options.values.label;
					obj.type = control.type;
					obj.controlType = control.type;
					objs.push(obj);
					if (control.children) {
						_Relation_Rule_GetAllDivControl(control.children.sort(Designer.SortControl), objs);
					}
				/*#150419-V16属性变更目标控件无法选择div-开始
				}
				#150419-V16属性变更目标控件无法选择div-结束*/

			}else{
				_Relation_Rule_GetAllDivControl(control.children.sort(Designer.SortControl), objs);
			}
		}
	}
}

/**
 * 支持属性列表
 * @returns
 */
function Designer_Control_Relation_Rule_getCanApplyFieldLayout () {
	var builder = Designer.instance.builder;
	var controls = builder.controls.sort(Designer.SortControl);
	var objs = [];
	_Designer_Control_Relation_Rule_getCanApplyFieldLayout(controls, objs);
	return objs;
}

function _Designer_Control_Relation_Rule_getCanApplyFieldLayout(controls, objs) {
	for (var i = 0, l = controls.length; i < l; i ++) {
		var control = controls[i];
		if (control.storeType == 'none' && control.type != "fieldlaylout")
			continue;
		if (control.storeType == 'layout' && control.type != "fieldlaylout") {
			_Designer_Control_Relation_Rule_getCanApplyFieldLayout(control.children.sort(Designer.SortControl), objs);
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
			var dataType = obj.type;
			obj.controlType = control.type;
			obj.isTemplateRow = isTempRow;
			objs.push(obj);
		}
	}
}

// 触发控件选择
function _Relation_Rule_BindDomControlsChoose(id,name){
	// 获取所有控件，包括容器类
	var c = Designer.instance.getObj(false);
	var varInfos= [];
	var tableId = _Relation_Rule_BindDomControlsIsInDetailTable();
	for(var i in c ){
		//明细表外的控件触发控件不读取明细表的,明细表内的控件触发控件读取对应明细表内的
		if(!tableId && c[i].name && c[i].name.indexOf(".")>=0 || (tableId && !(c[i].name && c[i].name.indexOf(".")>=0 && c[i].name.indexOf(tableId)>=0))){
			continue;
		}
		if(!c[i].controlType){
			continue;
		}
		if(c[i].controlType=='inputText' || 
				c[i].controlType=='textarea' || 
				c[i].controlType=='inputCheckbox' || 
				c[i].controlType=='inputRadio' || 
				c[i].controlType=='select' || 
				c[i].controlType=='address' || 
				c[i].controlType=='new_address' || 
				c[i].controlType=='datetime'  || 
				c[i].controlType=='relationSelect' || 
				c[i].controlType=='relationRadio' || 
				c[i].controlType=='relationCheckbox')
		varInfos.push(c[i]);
	}
	
	c=varInfos;
	//属性列表
	var fieldLayout = Designer_Control_Relation_Rule_getCanApplyFieldLayout();
	c = fieldLayout.concat(c);
	
	var url = "sys/xform/designer/relation/relation_formfields_tree.jsp";
	
	_Relation_Rule_OpenDialog(url,id,name,c);
	
}

function _Relation_Rule_BindDomControlsIsInDetailTable(){
	try{
		if(Designer && Designer.instance && Designer.instance.control){
			var curControl = Designer.instance.control;
			if(curControl.type === "relationRule" && curControl.options && curControl.options.domElement){
				var dom = curControl.options.domElement;
				var trObj = $(dom).parents("tr[type='templateRow']")[0];
				var tableObj = $(dom).parents("table[fd_type='detailsTable']")[0];
				if(tableObj && Designer.instance.builder && trObj){
					var control = Designer.instance.builder.getControlByDomElement(tableObj);
					if(control && control.options && control.options.values){
						return control.options.values.id;
					}else{
						return null;
					}
				}
			}
			return null;
		}
	}catch(e){
		return null;
	}
}

function _TrigetBindDomRuleControl(a,name){
	var c=Designer.instance.getObj(false);
	RelatoinFormFieldChoose(document.getElementById(name),document.getElementById(name),function(rtn){
		if(rtn&&rtn.data&&rtn.data[0].id){
			document.getElementById(name).value=rtn.data[0].id;
		}
	},c);
}

function _Designer_Control_RelationRule_OnDraw(parentNode, childNode) {
	if (this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
	}
	//修复 属性保存后丢失问题
	for (var obj in this.options.values){
		if (obj.substring(0,"condID_".length) == "condID_" && this.options.values[obj]){
			this.options.values[obj] = this.options.values[obj].
							replace(/"/g,"quot;");
		}
		if (obj.substring(0,"condName_".length) == "condName_" && this.options.values[obj]){
			this.options.values[obj] = this.options.values[obj].
										replace(/"/g,"quot;");
		}
	}
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.style.width='25px';
	var values = this.options.values;
	var inputDom = document.createElement('input');
	inputDom.type='hidden';
	domElement.appendChild(inputDom);
	
	inputDom.id = this.options.values.id;
	if (values.binddomId) {
		$(inputDom).attr("binddomId",values.binddomId);
	}
	if (values.op) {
		values.op = Relation_Convert_HTML_ForJSon(values.op);
		$(inputDom).attr("op", values.op);
	}
	if (values.destdomId) {
		$(inputDom).attr("destdomId",values.destdomId);
	}
	if (values.bindEvent) {
		$(inputDom).attr("bindEvent",values.bindEvent);
	}
	var label = document.createElement("label");
	label.style.background = "url(relation_rule/style/relation_rule_icon.png) no-repeat";
	label.style.margin = '0px 0px 0px 0px';
	label.style.display = 'inline-block';
	label.style.width='24px';
	label.style.height='24px';
	domElement.appendChild(label);
}
function _Designer_Control_RelationEvent_DrawXML() {

}
/** 日志相关start */
function relationRule_getVal(name,attr,value,controlValue){
	var val = value || "";
	val = val.replace(/quot;/g,"\"");
	if (val === "") {
		return;
	}
	val = JSON.parse(val);
	controlValue[name] = val;
}

function relationRule_compareChange(name,attr,oldValue,newValue) {
	var oldOpTexts = relation_getOpTexts(oldValue);
	var newOpTexts = relation_getOpTexts(newValue);
	if (oldOpTexts != newOpTexts) {
		var change = {};
		change.oldVal = oldValue;
		change.newVal  = newValue;
		return JSON.stringify(change); 
	}
}

function relationRule_translator(change,attr){
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	var oldVal = change.oldVal || [];
	var newVal = change.newVal || [];
	var oldOpTexts = relation_getOpTexts(oldVal);
	var newOpTexts = relation_getOpTexts(newVal);
	var html = "<span> 由 (" + oldOpTexts + ") 变更为 (" + newOpTexts + ")</span>";
	return html; 
}

function relation_getOpTexts(vals){
	var opTexts = [];
	vals = vals || [];
	for (var i = 0; i < vals.length; i++) {
		var op = vals[i];
		var cndName = op.cndName;
		var displayText = relationRule_getDisplayText(op.display);
		var requiredText = relationRule_getRequiedText(op.required);
		var readonlyText = relationRule_getReadonlyText(op.readonly);
		var opText = relationRule_getChangeText(cndName,displayText,requiredText,readonlyText);
		opTexts.push(opText);
	}
	return JSON.stringify(opTexts);
}

function relationRule_getChangeText(cndName,displayText,requiredText,readonlyText){
	var delemiter = "#";
	var text = "";
	for (var i = 0; i < arguments.length; i++) {
		text += arguments[i];
		if (i != arguments.length -1) {
			text += delemiter;
		}
	}
	return text;
}

function relationRule_getDisplayText(value){
	var text = "";
	if (value == "1") {
		text = Designer_Lang.relation_rule_display_dispcontrol;
	} else if (value == "0") {
		text = Designer_Lang.relation_rule_display_hidecontrol;
	} else if (value == "21") {
		text = Designer_Lang.relation_rule_display_disprow;
	} else if (value == "20") {
		text = Designer_Lang.relation_rule_display_hiderow;
	} else {
		text = Designer_Lang.relation_rule_choose_please;
	}
	return text;
}

function relationRule_getRequiedText(value){
	var text = "";
	if (value == "1") {
		text = Designer_Lang.relation_rule_required_yes;
	} else if (value == "0") {
		text = Designer_Lang.relation_rule_required_no;
	} else {
		text = Designer_Lang.relation_rule_choose_please;
	}
	return text;
}

function relationRule_getReadonlyText(value){
	var text = "";
	if (value == "1") {
		text = Designer_Lang.relation_rule_readonly_yes;
	} else if (value == "0") {
		text = Designer_Lang.relation_rule_display_hidecontrol;
	} else {
		text = Designer_Lang.relation_rule_readonly_no;
	}
	return text;
}


function valueTranslate(item,pname) {
	 var t_value = {
        "change": Designer_Lang.relation_event_onchange,
        "relation_rule_event": Designer_Lang.relation_rule_cusotmEvent,  
    };
        	 var txt = "";
             if(t_value[item.before]){
	            item.before=t_value[item.before]
             }
             if(t_value[item.after]){
	            item.after=t_value[item.after]
             }
            
             //修改
             if (item.status == "1") {
                 txt += " "+Designer_Lang.from+"  (" + (item.before || '') + ")\&nbsp;\&nbsp;\&nbsp; "+Designer_Lang.to+"\&nbsp;\&nbsp;\&nbsp;(" + item.after + ")";
             }
             return txt;
        }

/** 变更日志相关end */




