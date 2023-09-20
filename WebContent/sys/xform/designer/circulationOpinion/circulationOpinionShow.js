/**
 * 传阅意见展示控件
 * @作者：liwc 
 * @日期：2019年6月13日  
 */
var t_value = {
        "sysCirculationOpinionStyleDept": "传阅意见+部门+处理人+时间",
        "sysCirculationOpinionStyleDefault": "传阅意见+处理人+时间",
        "sysCirculationOpinionStyleTable": "表格显示"
};

var SysCirculationOpinionStylePlugin=function(){
	var self=this;
	this.init=function(){
		//获取扩展点中定义的样式信息
		$.ajax({
		  url: Com_Parameter.ContextPath + "sys/xform/designer/circulationOpinion/circulationOpinionShow_style.jsp",
		  type:'GET',
		  async:false,//同步请求
		  success: function(json){
		    self.extStyle=json;
		  },
		  dataType: 'json'
		});
	};
	this.GetAllExtStyle=function(){
		
		return self.extStyle;
	};
	this.GetStyleByValue=function(val){
		for(var i=0;i<self.extStyle.length;i++){
			if(self.extStyle[i].viewValue==val){
				return self.extStyle[i];
			}
		}
	};
	//设置控件属性面板选项数组
	this.GetOptionsArray=function(){
		var styleOptions=[];
		var extStyle = self.extStyle;
		for(var i=0;i<extStyle.length;i++){
			
			styleOptions.push({name: extStyle[i].order , text: extStyle[i].viewName , value: extStyle[i].viewValue});
		}
		return styleOptions;
	}
	this.init();
}

var SysCirculationOpinionStylePlugin = new SysCirculationOpinionStylePlugin();

Designer_Config.operations['circulationOpinionShow']={
		lab : "5",
		imgIndex : 72,
		title:Designer_Lang.circulationOpinionShow_name_insert,
		run : function (designer) {
			designer.toolBar.selectButton('circulationOpinionShow');
		},
		type : 'cmd',
		order: 6.5,
		shortcut : 'N',
		sampleImg : 'style/img/auditshow/auditShow.png',
		select: true,
		cursorImg: 'style/cursor/circulationOpinionShow.cur'
};

Designer_Config.controls.circulationOpinionShow={
			type : "circulationOpinionShow",
			inherit : 'base',
			storeType : 'field',
			onDraw : _Designer_Control_CirculationShow_OnDraw,
			drawXML : _Designer_Control_CirculationShow_DrawXML,
            onInitialize : _Designer_Control_CirculationShow_OnInitialize,
			implementDetailsTable : false,
			attrs : {
				label : Designer_Config.attrs.label,
				detail_attr_value:{
					//控件隐藏值
					text: Designer_Lang.auditshow_attr_hiddenValue,
					type: 'hidden',
					skipLogChange:true,
					show: true
				},
				detail_attr_name:{
					text: Designer_Lang.circulationOpinionShow_Owner,
					type: 'hidden',
					//只对隐藏的name校验必填，主要兼容 自定义模式时，value可以为空。其他模式name不为空，value必不为空
					validator: [Designer_Control_Attr_Required_Validator],
					checkout: Designer_Control_circulation_DetailName_Required_Checkout,
					show: true
				},
				detail_attr_value31:{
					//控件隐藏值
					text: Designer_Lang.auditshow_attr_hiddenValue,
					value : '',
					type: 'self',
					draw : _Designer_Control_Attr_detailHidden_self_Draw,
					skipLogChange:true,
					show: true
				},
				mould : {
					text: Designer_Lang.auditshow_attr_type,
					value : '99',
					type : 'select',
					opts: [
						{name: 'handler1', text: Designer_Lang.auditshow_mould_handler1, value:'11'},
						{name: 'handler2', text: Designer_Lang.auditshow_mould_handler2, value:'12'},
						{name: 'handler99', text: Designer_Lang.auditshow_mould_handler99, value:'99'},

					],
					getVal:relationSource_getVal,
					onchange:'_Designer_Control_Attr_Mould_Change_circulationOpinion(this)',
					show: true
				},
				mouldDetail : {
					//审批人属于
					text: Designer_Lang.circulationOpinionShow_Owner,
					value : '',
					type: 'self',
					draw: _Designer_Control_Attr_MouldDetail_Self_Draw_circulationOpinion,
					show: true
				},
				showStyle : {
					//展现样式
					text: Designer_Lang.circulationOpinionShow_attr_exhibitionStyle,
					value : 'sysCirculationOpinionStyleDept',
					type : 'self',
					draw: _Designer_Control_CirculationShow_Attr_ShowStyle_Self_Draw,
					opts: SysCirculationOpinionStylePlugin.GetOptionsArray(),
					onchange:'_Designer_Control_CirculationShow_Attr_ShowStyle_Change(this)',
					translator: showStyle_translator,
				    compareChange:showStyle_compareChange,
					show: true
				},
				previewImg :{
					//预览图
					text: Designer_Lang.circulationOpinionShow_attr_preview,
					value : '',
					type: 'self',
					draw:_Designer_Control_CirculationShow_Attr_PreviewImg_Self_Draw,
					show: true
				},
				sortNote:{
					//意见排序	
					text: Designer_Lang.circulationOpinionShow_attr_noteSort,
					value : 'dateUp',
					type : 'radio',
					opts:[{text:Designer_Lang.circulationOpinionShow_sortNote_DateUp,value:"dateUp"},
					      {text:Designer_Lang.circulationOpinionShow_sortNote_DateDown,value:"dateDown"},
					      {text:Designer_Lang.circulationOpinionShow_sortNote_DeptUp,value:"deptUp"},
					      {text:Designer_Lang.circulationOpinionShow_sortNote_DeptDown,value:"deptDown"}],
					show:true
				},
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				}
			},
			info:{
				//审批意见展示控件
				name:Designer_Lang.circulationOpinionShow_name,
				preview: "mutiTab.png"
			}
			,
			resizeMode : 'onlyWidth'
};
Designer_Config.buttons.tool.push("circulationOpinionShow");
Designer_Menus.tool.menu['circulationOpinionShow'] = Designer_Config.operations['circulationOpinionShow'];
function Designer_Control_circulation_DetailName_Required_Checkout(msg, name, attr, value, values, control){
	if (value == null || value == '' || value.replace(/\r\n/g, '') == '') {
		msg.push(values.label,","+Designer_Lang.handler_NotNull);
		return false;
	}
	return true;
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
function _Designer_Control_Attr_MouldDetail_Self_Draw_circulationOpinion(name, attr, value, form, attrs, values,control){

	var mouldValue = attrs.mould.value;
	if(values.mould){
		mouldValue=values.mould;
	}
	html="<div id='auditshow_mouldDetail_html'>"+_GetHTMLByMouldType_circulationOpinion(mouldValue)+"</div>";
	if (mouldValue == '99' ){
		attr.text = "";
	}
	var resultHtml = Designer_AttrPanel.wrapTitle(name, attr, value, html);
	if (mouldValue == '99') {
		resultHtml = resultHtml.replace("<tr>","<tr style='display: none'>")
	} 

	return resultHtml;
}
function _GetHTMLByMouldType_circulationOpinion(mouldValue){
	var html=[];
	var lableText="";
	var control = Designer.instance.control;
	//是否为初始是的模式，如果不是则需要将控件的值设置为空
	var isInitMould=false;
	//处理公式中含有""时报错
	control.options.values.detail_attr_value = control.options.values.detail_attr_value?control.options.values.detail_attr_value.replace(/quat;/g, "\""):"";
	control.options.values.detail_attr_name = control.options.values.detail_attr_name?control.options.values.detail_attr_name.replace(/quat;/g, "\""):"";
	//选择回初始模式时，需要将初始值重新设置回去。
	var tempValue = control.options.values.detail_attr_name=='all' ? "":control.options.values.detail_attr_name;
	if(control.options.values.mould==mouldValue){
		isInitMould=true;


		if(document.getElementsByName("detail_attr_name")[0]){
			document.getElementsByName("detail_attr_name")[0].value=control.options.values.detail_attr_name||"";
		}
		if(document.getElementsByName("detail_attr_value")[0]){
			document.getElementsByName("detail_attr_value")[0].value=control.options.values.detail_attr_value||"";
		}

	}
	else
	{
		if(document.getElementsByName("detail_attr_name")[0] ){
			document.getElementsByName("detail_attr_name")[0].value="";
		}
		if(document.getElementsByName("detail_attr_value")[0]){
			document.getElementsByName("detail_attr_value")[0].value="";
		}

	}
	switch(mouldValue)
	{
		case '11':
		{
			//onpropertychange 审批人属于
			lableText=Designer_Lang.circulationOpinionShow_Owner;

			html.push("<input type='text' id='detail_attr_name2' name='detail_attr_name2' readonly='true'  class='inputsgl' value='"+(isInitMould?(tempValue||""):"")+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			//html.push("<input type='hidden' id='detail_attr_value2' name='detail_attr_value2' onchange='SetBack_Detail_attr_value(this);' onpropertychange='SetBack_Detail_attr_value(this);' value='"+(isInitMould?(control.options.values.detail_attr_value||""):"")+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_Address(true, 'detail_attr_value','detail_attr_name2', ';',ORG_TYPE_ALL,After_Select_Set_Name);_Designer_Control_Attr_Address_defaultValue_afterClick();\">"+Designer_Lang.auditshow_choose+"</a>");
			break;
		}
		case '12':
		{
			//审批人属于
			lableText=Designer_Lang.circulationOpinionShow_Owner;
			html.push("<input type='text' id='detail_attr_name2' name='detail_attr_name2' readonly='true'  class='inputsgl' value='"+(isInitMould?(tempValue||""):"")+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			//html.push("<input type='hidden' id='detail_attr_value2' name='detail_attr_value2' onchange='SetBack_Detail_attr_value(this);' onpropertychange='SetBack_Detail_attr_value(this);' value='"+(isInitMould?(control.options.values.detail_attr_value||""):"")+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Formula_Dialog('detail_attr_value','detail_attr_name2',Designer.instance.getObj(true),'Object',After_Select_Set_Name,null,Designer.instance.control.owner.owner._modelName);\">"+Designer_Lang.auditshow_choose+"</a>");
			break;
		}
		case '99':
		{	//自定义

			//参数
			html.push("<input style='display:none' name='detail_attr_value2' id='detail_attr_value2' onkeyup='SetBack_Detail_attr_value(this);' value='all'/><br/>");
			if(document.getElementsByName("detail_attr_name").length>0){
				document.getElementsByName("detail_attr_name")[0].value="all";
			}
			if(document.getElementsByName("detail_attr_name").length>0){

				document.getElementsByName("detail_attr_name")[0].value="all";
			}
			break;
		}
	}

	//设置mouldDetail Label
	if($("#auditshow_mouldDetail_html")[0]){
		//<tr><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>'

		$("#auditshow_mouldDetail_html").parent().prev().text(lableText);
	}
	html=html.join('');
	return html;
}
function _Designer_Control_Attr_Mould_Change_circulationOpinion(mouldSelect){

	var html=_GetHTMLByMouldType_circulationOpinion(mouldSelect.value);

	$("#auditshow_mouldDetail_html").html(html);

	_Designer_Control_Attr_Mould31_change(mouldSelect);

	_Designer_Control_Attr_NoteFilter_ControlDisplay_circulationOpinion(mouldSelect.value);
}
function _Designer_Control_Attr_NoteFilter_ControlDisplay_circulationOpinion(mouldValue){
	if (mouldValue == '99') {
		$("#auditshow_mouldDetail_html").closest('tr').hide();
	} else {
		$("#auditshow_mouldDetail_html").closest('tr').show();
		
	}
}
function _Designer_Control_Attr_Mould31_change(mouldSelect){
	if (mouldSelect.value == "31"){
		var $tr = $("#auditshow_mouldDetail_html").closest("tr");
		var html2 = _Designer_Control_MouldDetail2_Draw(mouldSelect.value);
		$tr.after(html2);
		var $nameField = $("input[type='hidden'][name='detail_attr_name']");
		$nameField.after("<input type='hidden' name='detail_attr_value31'><input type='hidden' name='detail_attr_name31'>");
		//是否为初始是的模式，如果不是则需要将控件的值设置为空
		var control = Designer.instance.control;
		if(document.getElementsByName("detail_attr_name31")[0]){
			document.getElementsByName("detail_attr_name31")[0].value=control.options.values.detail_attr_name31||"";
		}
		if(document.getElementsByName("detail_attr_value31")[0]){
			document.getElementsByName("detail_attr_value31")[0].value=control.options.values.detail_attr_value31||"";
		}
	}else{
		var $tr = $("#auditshow_mouldDetail_html2").closest("tr");
		$tr.remove();
		var $value31Field = $("input[type='hidden'][name='detail_attr_value31']");
		var $name31Field = $("input[type='hidden'][name='detail_attr_name31']");
		$value31Field.remove();
		$name31Field.remove();
	}
}
function showStyle_compareChange(name,attr,oldValue,newValue) {
	var changeResult = [];
	var oldTextValue = oldValue;
	
	var newTextValue = newValue;

	if (oldTextValue != newTextValue) {
		var textValChange = {};
		textValChange.oldVal = oldTextValue;
		textValChange.newVal  = newTextValue;
		textValChange.name = "textValue";
		changeResult.push(textValChange);
	}
	
	if (changeResult.length == 0) {
		return "";
	}
	return JSON.stringify(changeResult); 
}
function showStyle_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	var html =[];
	html.push("<span>修改 (");
	for (var i = 0;i < change.length; i++) {
		var paramChange = change[i];
		if (paramChange.name === "textValue") {
			if(t_value[paramChange.oldVal]){
				paramChange.oldVal=t_value[paramChange.oldVal]
			}
			if(t_value[paramChange.newVal]){
				paramChange.newVal=t_value[paramChange.newVal]
			}
			html.push("传出参数 " + paramChange.oldVal + " 变为 " + paramChange.newVal);
		} if (paramChange.name === "hiddenValue") {
			html.push(" ;实际值 " + paramChange.oldVal + " 变为 " + paramChange.newVal);
		}
	}
	html.push(")</span>");
	return html.join("");
}
//属性面板预览图
function _Designer_Control_CirculationShow_Attr_PreviewImg_Self_Draw(name, attr, value, form, attrs, values,control){
	
	var showStyleValue = Designer.instance.control.attrs.showStyle.value;
	
	if(values.showStyle){
		showStyleValue = values.showStyle;
	}

	var styleJSON = SysCirculationOpinionStylePlugin.GetStyleByValue(showStyleValue);
	
	if(!styleJSON){
		styleJSON = SysCirculationOpinionStylePlugin.GetStyleByValue("sysCirculationOpinionStyleDept");
	}
	
	var baseHTML = _Designer_Control_CirculationShow_Attr_showStyle_replace(styleJSON.styleHtml);
	
	var arr = [];
	if (showStyleValue === "sysCirculationOpinionStyleTable" ||  ( baseHTML.indexOf("tr") >0 && baseHTML.indexOf("td")>0) ){
		arr.push("<table align='center' class='tb_normal' width='100%' >");
		arr.push(baseHTML);
		arr.push("</table>");
	}else{
		arr.push(baseHTML);
	}

	var html="<div id='circulationShow_reivewDiv' style='width:170px;height:60px;overflow:auto;'>" + arr.join("") + "</div>";

	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//展现样式值改变事件
function _Designer_Control_CirculationShow_Attr_ShowStyle_Change(showStyle){
	var styleJSON = SysCirculationOpinionStylePlugin.GetStyleByValue(showStyle.value);
	
	var baseHTML = _Designer_Control_CirculationShow_Attr_showStyle_replace(styleJSON.styleHtml);
	
	var arr = [];
	if (showStyle.value === "sysCirculationOpinionStyleTable" ||  ( baseHTML.indexOf("tr") >0 && baseHTML.indexOf("td")>0)){
		arr.push("<table align='center' class='tb_normal' width='100%' >");
		arr.push(baseHTML);
		arr.push("</tbody></table>");
	}else{
		arr.push(baseHTML);
	}
	
	$("#circulationShow_reivewDiv").html(arr.join(""));
}

//控件绘制
function _Designer_Control_CirculationShow_OnDraw(parentNode, childNode) {
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className = "xform_circulationOpinionShow";
	$(domElement).css("display","inline-block");
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();

	domElement.id = this.options.values.id;
	
	$(domElement).attr("label",_Get_Designer_Control_Label(this.options.values, this));
	var values = this.options.values;
	
	if (this.options.values.width ) {
		if( this.options.values.width.toString().indexOf('%') > -1){
			domElement.style.whiteSpace = 'nowrap';
			domElement.style.width = this.options.values.width;
		}
		else{
			domElement.style.width = this.options.values.width+"px";
		}
	}else{
		values.width = "100%";
		domElement.style.width = values.width;
	}
	$(domElement).attr("width",values.width);
	
	
	domElement.innerHTML = Designer_Control_CirculationShow_GetExhibitionHTML(this);
	
	//该属性为 showStyle下拉框的value值，及plugin中的viewValue
	if(!values.showStyle){
		values.showStyle = this.attrs.showStyle.value;
	}
	$(domElement).attr("exhibitionStyleClass",values.showStyle);
	
	if(!values.sortNote){
		values.sortNote = this.attrs.sortNote.value;
	}
	//处理公式中含有""时报错
	if(values.detail_attr_value){
		values.detail_attr_value = values.detail_attr_value.replace(/\"/g, "quat;");
		$(domElement).attr("value",values.detail_attr_value);
	}
	if (values.mould == "31"){
		if (values.detail_attr_value31){
			var _value = values.detail_attr_value + "~" + values.detail_attr_value31;
			$(domElement).attr("value",_value);
		}
	}
	if(values.detail_attr_name == undefined || values.detail_attr_name == ''){

		values.detail_attr_name = "all" ;
	}
	$(domElement).attr("mould",values.mould);
	//设置排序类型属性
	$(domElement).attr("sortNote",values.sortNote);
}

function _Designer_Control_CirculationShow_DrawXML(){
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="String" ');
	buf.push('store="false" ');
	buf.push('businessType="', this.type, '" ');
	buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(values)),'" ');
	buf.push('/>');
	return buf.join('');
		
}

//#152951 兼容最初版本属性面板没有mould选项, 后面新增并设置了必填校验, 导致复制之前表单源码, 保存必填校验不通过
function _Designer_Control_CirculationShow_OnInitialize() {
    var values = this.options.values;
    if (!values.hasOwnProperty('mould')) {
        values.mould = '99';
        values.detail_attr_name = 'all';
        values.detail_attr_value2 = 'all';
    }
}

//根据不同的类型获取显示格式
function Designer_Control_CirculationShow_GetExhibitionHTML(control){
	var html = [];
	
	//设置初始值为默认样式
	var showStyleValue = control.options.values.showStyle || "sysCirculationOpinionStyleDept";

	var styleJSON = SysCirculationOpinionStylePlugin.GetStyleByValue(showStyleValue);
	//动态设置图片宽度。防止无法拖动到小于图片宽度的值。
	var tempWidth = "100%";
    if(control.options.domElement.style.width){
    	tempWidth = control.options.domElement.style.width;
    }
	var baseHTML = _Designer_Control_CirculationShow_Attr_showStyle_replace(styleJSON.styleHtml);
	
	if (showStyleValue === "sysCirculationOpinionStyleTable"){
		html.push("<table align='center' class='tb_normal' width='100%' >");
		html.push(baseHTML);
		html.push("</table>");
	}else{
		html.push(baseHTML);
	}
	
	return html.join('');
}

function _Designer_Control_CirculationShow_Attr_showStyle_replace(value){
	var map={"docContent":Designer_Lang.circulationOpinionShow_msg,
		"fdOrgName":Designer_Lang.circulationOpinionShow_person,
		"fdOrgDept":Designer_Lang.circulationOpinionShow_dept,
		"width":'',
		"index":1,
		"msg":Designer_Lang.sysXformAuditshow_msg,
		"fdReadTime":Designer_Lang.circulationOpinionShow_time,
		"handwrite":'<img height="30px" width="40px" src="'+Com_Parameter.ContextPath + 'sys/xform/designer/style/img/auditshow/handwrite.png">',
		"fdWriteTime":Designer_Lang.circulationOpinionShow_time,
		"person":Designer_Lang.sysXformAuditshow_person,
		"dept":Designer_Lang.sysXformAuditshow_dept,
		"post":Designer_Lang.sysXformAuditshow_post,
		"attachment":'<img src="'+Com_Parameter.ContextPath +'sys/xform/designer/style/img/auditshow/attachment.png">',
		"time":Designer_Lang.sysXformAuditshow_time,
		"fdWriteDate":Designer_Lang.sysXformAuditshow_date,
		"operation":Designer_Lang.sysXformAuditshow_operation,
		"picPath":'<img src="'+Com_Parameter.ContextPath+'sys/xform/designer/style/img/auditshow/defaultSig2.png">'};
	var text = value.replace(/\$\{(\w+)\}/g,function($1,$2){
		return map[$2];
	});
	return text;
}

//属性面板展现样式
function _Designer_Control_CirculationShow_Attr_ShowStyle_Self_Draw(name, attr, value, form, attrs, values,control){	
	var html = "<select name='showStyle' class='attr_td_select' style='width:95%' onchange='_Designer_Control_CirculationShow_Attr_ShowStyle_Change(this)'>";
	var styleOptions = SysCirculationOpinionStylePlugin.GetOptionsArray();
	for(var i =0;i<styleOptions.length;i++){
		if(value){
			html += "<option value='"+styleOptions[i].value+"'" + (value == styleOptions[i].value ? "selected='selected'" : "") + ">"+styleOptions[i].text+"</option>";
		}
	}
	html += "</select>";
	html += "<input type='button' value='"+Designer_Lang.circulationOpinionShow_style_custom+"' class='btnopt' onclick='_Designer_Control_CirculationShow_ToCustom();' />";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//样式自定义按钮点事事件
function _Designer_Control_CirculationShow_ToCustom(){
	window.open(Com_Parameter.ContextPath + "sys/xform/designer/circulationOpinion/index.jsp");
}
