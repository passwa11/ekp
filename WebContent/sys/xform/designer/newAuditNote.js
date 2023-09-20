/*
* @Author: liwenchang
* @Date:   2017-09-21 10:44:06
* @Last Modified by:   liwenchang
* @Last Modified time: 2017-09-21 14:59:05
*/

Designer_Config.operations['newAuditNote']={
		lab : "5",
		imgIndex : 62,
		title:Designer_Lang.newAuditNote,
		run : function (designer) {
			designer.toolBar.selectButton('newAuditNote');
		},
		type : 'cmd',
		order: 6,
		shortcut : '',
		sampleImg : 'style/img/newAuditNote/newAuditNote.png',
		select: true,
		cursorImg: 'style/cursor/newAuditNote.cur'
};

Designer_Config.controls.newAuditNote={
		type : "newAuditNote", //控件类型
		inherit : 'base',
		storeType : 'field',
		needInsertValidate : true, //插入校验
	    insertValidate:_Designer_Control_newAuditNote_InsertValidate,//插入校验，用于不支持权限控件
		onDraw : _Designer_Control_newAuditNote_OnDraw,
		onDrawEnd : _Designer_Control_newAuditNote_OnDrawEnd,
		drawMobile : _Designer_Control_NewAuditNote_DrawMobile,
		drawXML : _Designer_Control_newAuditNote_DrawXML,
		onAttrLoad : _Designer_Control_newAudti_OnAttrLoad,
		implementDetailsTable : false,
		attrs : {	//属性面板对象
			label : Designer_Config.attrs.label, //显示文字
			mould : {	//类型
				text: Designer_Lang.newAuditNote_attr_type,
				value : '21',
				type : 'select',
				opts: [
				    {name: 'node1', text: Designer_Lang.newAuditNote_flowNote, value:'21'},
					{name: 'handler1', text: Designer_Lang.newAuditNote_handerShowByAddress, value:'11'},
					{name: 'handler2', text: Designer_Lang.newAuditNote_handerShowByFormula, value:'12'}
					
				],
				onchange:'_Designer_Control_NewAuditNote_Attr_Mould_Change(this)', //类型值改变事件
				show: true
			},
			mouldDetail : {//审批人属于
				text: Designer_Lang.newAuditNote_chooseNode,
				value : '',
				type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw,
				checkout:function(msg, name, attr, value, values, control){
					if(!value||value=='~'){
						msg.push(values.label+',' + Designer_Lang.NewAuditNote_CheckRequiredAttributeNotNull);
						return false;
					}
					return true;
				},
				validator:function (elem, name, attr, value, values) {
					if (!value||value=='~') {
						alert(Designer_Lang.GetMessage(Designer_Lang.NewAuditNote_requiredAttributeNotNull,attr.text));
						elem.focus();
						return false;
					}
					return true;
				},
				show: true
			},
			operationType : { //操作项,通过/签字默认选中
				text : Designer_Lang.newAuditNote_operationType,
				value : 'handler_pass~handler_sign',
				type : 'checkGroup',
				opts : [
					{name:"passAndSign",text : Designer_Lang.newAuditNote_passAndsign, value:'handler_pass',checked:true}, //通过/签字
					{name:"refuse",text: Designer_Lang.newAuditNote_refuse, value: 'handler_refuse',checked: true}, //驳回
					{name:"communicate",text: Designer_Lang.newAuditNote_communicate, value: 'handler_communicate'}, //沟通
					{name:"commission",text: Designer_Lang.newAuditNote_commission, value: 'handler_commission'}, //转办
					{name:"additionSign",text: Designer_Lang.newAuditNote_additionSign, value: 'handler_additionSign'}, // 补签
					{name:"assign",text: Designer_Lang.newAuditNote_assign, value: 'handler_assign'} // 加签
				],
				show: true
			},
			width : { //宽度
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			height : { //高度
				text: Designer_Lang.controlAttrHeight,
				value: "",
				type: 'text',
				show: true,
				validator: [Designer_Control_Attr_Int_Validator,Designer_Control_NewAuditNote_Height_Validator],
				checkout: [Designer_Control_Attr_Int_Checkout,Designer_Control_NewAuditNote_Height_Checkout]
			},
			newAuditNote_detail_attr_name :{
					skipLogChange:true,
					show: false
				},
			newAuditNote_detail_attr_value :{
					skipLogChange:true,
					show: false
			},
			refuse:{//驳回
				text: Designer_Lang.newAuditNote_refuse,
				show: false,
				type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw
			},
			communicate:{ //沟通
				text: Designer_Lang.newAuditNote_communicate,
				show: false,
				type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw
			},
			commission:{//转办
			    text: Designer_Lang.newAuditNote_commission,	
                show: false,
                type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw
			},
			additionSign:{//补签
			    text: Designer_Lang.newAuditNote_additionSign,
                show: false,
                type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw
			},
			assign:{// 加签
				text: Designer_Lang.newAuditNote_assign,
				show: false,
				type: 'self',
				draw: _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw
			},
			
			formula: Designer_Config.attrs.formula,
			reCalculate: Designer_Config.attrs.reCalculate
		},
		onAttrLoad : _Designer_Control_New_AuditNote_OnAttrLoad,
		info:{
			//新审批操作控件
			name:Designer_Lang.newAuditNote,
			preview: "mutiTab.png"
		},
		resizeMode : 'onlyWidth'
}

Designer_Config.buttons.tool.push("newAuditNote");
Designer_Menus.tool.menu['newAuditNote'] = Designer_Config.operations['newAuditNote'];

function _Designer_Control_NewAuditNote_Attr_MouldDetail_Self_Draw(name, attr, value, form, attrs, values,control){
	var mouldValue = attrs.mould.value;
	if(values.mould){
		mouldValue=values.mould;
	}
	/*html="<div id='auditnote_mouldDetail_html'>"+_GetHTMLByHanderType(mouldValue,value)+"</div>";*/
	html = "<input type='hidden' value='"+(values.mouldDetail?values.mouldDetail:'')+"' name='mouldDetail' />";
	value = Designer_Builder_unscapeNodeAndValues(value);
	html += "<div id='newAuditnote_mouldDetail_html'>"+_newAuditNoteGetHTMLByHanderType(mouldValue,value)+"</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_newAudti_OnAttrLoad(panelForm,control){
	var $passAndSignDom = $(panelForm).find("[name='passAndSign']");
	$passAndSignDom.attr("disabled",true);
}

//cell指被插入的单元，control只即将插入的控件
function _Designer_Control_newAuditNote_InsertValidate(cell, control) {
	//权限控件不支持插入
	if(control && control.container){
		alert(Designer_Lang.controlNoSupportRight);
		return false;
	}
	return true;
}

function _Designer_Control_newAuditNote_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	// domElement.className="xform_auditNote";
	if(this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
	}
	domElement.id = this.options.values.id;
	domElement.label=_Get_Designer_Control_Label(this.options.values, this);
	domElement.style.border="0px solid #DFDFDF";
	$(domElement).css("display","inline-block");
	var values =this.options.values;
	//对”“特殊处理
	var detailAttrValue = this.options.values.newAuditNote_detail_attr_value;
	var detailAttrName = this.options.values.newAuditNote_detail_attr_name;
	var mouldDetail = this.options.values.mouldDetail;
	this.options.values.newAuditNote_detail_attr_value = detailAttrValue ? detailAttrValue.replace(/"/g,"quat;") : "";
	this.options.values.newAuditNote_detail_attr_name = detailAttrName ? detailAttrName.replace(/"/g,"quat;") : "";
	this.options.values.mouldDetail = mouldDetail ? mouldDetail.replace(/"/g,"quat;") : "";

	if (this.options.values.width) {
		if( this.options.values.width.toString().indexOf('%') > -1){
			domElement.style.whiteSpace = 'nowrap';
			domElement.style.width = this.options.values.width;
		}
		else{
			domElement.style.width = this.options.values.width+"px";
		}
	}
	else{
		values.width = "100%";
		domElement.style.width=values.width;
	}
	$(domElement).attr("width",values.width);
	$(domElement).attr("operationtype",values.operationType);
	$(domElement).attr("mould",values.mould);
	$(domElement).attr("mouldDetail",values.mouldDetail);
	if (this.options.values.height) {
		if( this.options.values.height.toString().indexOf('%') > -1){
			domElement.style.whiteSpace = 'nowrap';
			domElement.style.height = this.options.values.height;
		}
		else{
			//作者 曹映辉 #日期 2015年8月20日 +30像素是考虑在设计器的时候把按钮放到div里面去，高度作用在div上。在运行时直接把高度作用在文本域上
			domElement.style.height = (parseInt(this.options.values.height)+30)+"px";
		}
	}else{
		values.height = "60";
		domElement.style.height=(parseInt(values.height)+30)+"px";
	}
	$(domElement).attr("height",values.height);

	//预览dom元素
	var arr = new Array();
	var fdId = this.options.values.id;
	arr.push("<textarea style='width:100%;height:"+(values.height)+"px;margin-top:0px'>");
	arr.push("</textarea><br/>");
	arr.push("<label>");
	arr.push("<input type='radio' name='operationType_" + fdId + "' checked='true' value='passAndSign' disabled='disabled'/>" + Designer_Lang.newAuditNote_passAndsign +"&nbsp;&nbsp;");
	arr.push("</label>");
	var operationType = new Array();
	operationType.push("handler_pass");
	if ((typeof values["refuse"]) == "undefined"){
		values["refuse"] = 'true';
	}
	if (values["refuse"] == 'true'){
		arr.push("<label>");
		arr.push("<input type='radio' name='operationType_" + fdId + "' value='refuse' disabled='disabled'/>" + Designer_Lang.newAuditNote_refuse + "&nbsp;&nbsp;");
		arr.push("</label>");
		operationType.push("handler_refuse");
	}
	if (values["communicate"] == 'true'){
		arr.push("<label>");
		arr.push("<input type='radio' name='operationType_" + fdId + "' value='communicate' disabled='disabled'/>" + Designer_Lang.newAuditNote_communicate + "&nbsp;&nbsp;");
		arr.push("</label>");
		operationType.push("handler_communicate");
	}
	if (values["commission"] == 'true'){
		arr.push("<label>");
		arr.push("<input type='radio' name='operationType_" + fdId + "' value='commission' disabled='disabled'/>" + Designer_Lang.newAuditNote_commission + "&nbsp;&nbsp;");
		arr.push("</label>");
		operationType.push("handler_commission");
	}
	if (values["additionSign"] == 'true'){
		arr.push("<label>");
		arr.push("<input type='radio' name='operationType_" + fdId + "' value='additionSign' disabled='disabled'/>" + Designer_Lang.newAuditNote_additionSign + "&nbsp;&nbsp;");
		arr.push("</label>");
		operationType.push("handler_additionSign");
	}
	if (values["assign"] == 'true'){
		arr.push("<label>");
		arr.push("<input type='radio' name='operationType_" + fdId + "' value='assign' disabled='disabled'/>" + Designer_Lang.newAuditNote_assign + "&nbsp;&nbsp;");
		arr.push("</label>");
		operationType.push("handler_assign");
	}
	$(domElement).attr("operationType",operationType.join(";"));
	arr.push("<label>"+Designer_Lang.newAuditNote_commonOpinion+"</label>&nbsp<select disabled='disabled'><option>=="+Designer_Lang.newAuditNote_select+"==</option></select>");
	domElement.innerHTML = arr.join(" ");
}

function _Designer_Control_newAuditNote_DrawXML(){
}

function _Designer_Control_newAuditNote_OnDrawEnd(){
}

function _Designer_Control_NewAuditNote_Attr_Mould_Change(mouldSelect){
	var html=_newAuditNoteGetHTMLByHanderType(mouldSelect.value);
	$("#newAuditnote_mouldDetail_html").html(html);
}

function _newAuditNoteGetHTMLByHanderType(mouldValue,val){
	var html=[];
	var names="";
	var labels="";
	if(val){
		names=val.split("~")[0];
		labels=val.split("~")[1];
	}
	switch(mouldValue)
	{
		case '11':
		{
			//onpropertychange 审批人属于
			lableText=' '+Designer_Lang.newAuditNote_handlerBelongsTo;
			html.push("<input type='text' id='newAuditNote_detail_attr_name' name='newAuditNote_detail_attr_name' readonly='true'  class='inputsgl' value='"+labels+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			html.push("<input type='hidden' id='newAuditNote_detail_attr_value' name='newAuditNote_detail_attr_value' value='"+names+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_Address(true, 'newAuditNote_detail_attr_value','newAuditNote_detail_attr_name', ';',ORG_TYPE_ALL,NewAudit_After_MouldDetailSelect_Set);_Designer_Control_Attr_Address_defaultValue_afterClick();\">"+Designer_Lang.newAuditNote_choose+"</a>");
			break;
		}
		case '12':
		{
			//审批人属于
			lableText=' '+Designer_Lang.newAuditNote_handlerBelongsTo;
			html.push("<input type='text' id='newAuditNote_detail_attr_name' name='newAuditNote_detail_attr_name' readonly='true'  class='inputsgl' value='"+labels+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			html.push("<input type='hidden' id='newAuditNote_detail_attr_value' name='newAuditNote_detail_attr_value'  value='"+names+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Formula_Dialog('newAuditNote_detail_attr_value','newAuditNote_detail_attr_name',Designer.instance.getObj(true),'com.landray.kmss.sys.organization.model.SysOrgElement[]',NewAudit_After_MouldDetailSelect_Set,'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction',null);\">"+Designer_Lang.newAuditNote_choose+"</a>");
			break;
		}
		case '21':
		{
			//选择展示节点
			lableText=' ' + Designer_Lang.newAuditNote_chooseNode;
			html.push("<input type='text' id='newAuditNote_detail_attr_name' name='newAuditNote_detail_attr_name'   readonly='true'  class='inputsgl' value='"+labels+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			html.push("<input type='hidden' id='newAuditNote_detail_attr_value' name='newAuditNote_detail_attr_value'  value='"+names+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_List_ShowNode('newAuditNote_detail_attr_value','newAuditNote_detail_attr_name', ';','',NewAudit_After_MouldDetailSelect_Set,false);\">"+Designer_Lang.newAuditNote_choose+"</a>");
			break;
		}
	}
	
	//设置mouldDetail Label
	if($("#newAuditnote_mouldDetail_html")[0]){
		$("#newAuditnote_mouldDetail_html").parent().prev().text(lableText);
	}
	html=html.join('');
	return html;
}

function Designer_Builder_unscapeNodeAndValues(value){
	return ( typeof value != "undefined" && value !=null ) ? value.replace(/quat;/g,"\"") : value;
}
function Designer_Control_NewAuditNote_Height_Validator(elem, name, attr, value, values){
	if(value<60){
		alert(Designer_Lang.newAuditNote_heightValidation);
		return false;
	}
	return true;
}

function Designer_Control_NewAuditNote_Height_Checkout(msg, name, attr, value, values, control){
	if(value<60){
		alert(Designer_Lang.newAuditNote_heightValidation);
		return false;
	}
	return true;
}

function NewAudit_After_MouldDetailSelect_Set(rtnvalue){
	if(!rtnvalue){
		return;
	}
	var control=Designer.instance.attrPanel.panel.control;
	var values=control.options.values;
	
	var names=[];
	var labels=[];
	
	for(var i=0;i<rtnvalue.data.length;i++){
		names.push(rtnvalue.data[i].id);
		labels.push(rtnvalue.data[i].name);
	}
	if(names.length>0){
//		values.mouldDetail=names.join(";")+"~"+labels.join(";");
		$("input[name='mouldDetail']").val(names.join(";")+"~"+labels.join(";"));
	}
	else{
//		values.mouldDetail="";
		$("input[name='mouldDetail']").val('');
	}
}


function _Designer_Control_New_AuditNote_OnAttrLoad(form,control){
	var values = control.options.values;
	if(values.mould == '11' || values.mould == '12'){
		$(form).find("#newAuditnote_mouldDetail_html").parent().prev().text(Designer_Lang.auditNote_handlerBelongsTo);
	}
}