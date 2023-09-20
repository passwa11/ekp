Com_IncludeFile('json2.js');
Com_IncludeFile("relationFormula.js",Com_Parameter.ContextPath + "sys/xform/designer/relation/formula/","js",true);

Designer_Config.operations['relationSelect'] = {
	lab : "5",
	imgIndex : 36,
	title : Designer_Lang.relation_select_name,
	run : function(designer) {
		designer.toolBar.selectButton('relationSelect');
	},
	type : 'cmd',
	order: 1,
	shortcut : '',
	sampleImg : 'style/img/relation_select.png',
	select : true,
	isAdvanced: true,
	cursorImg : 'style/cursor/relationSelect.cur'
};
Designer_Config.controls.relationSelect = {
	type : "relationSelect",
	storeType : 'field',
	inherit : 'relationCommonBase', //继承父类
	onDraw : _Designer_Control_RelationSelect_OnDraw,
	onDrawEnd : _Designer_Control_RelationSelect_OnDrawEnd,
	drawMobile : _Designer_Control_RelationSelect_DrawMobile,
	drawXML : _Designer_Control_RelationSelect_DrawXML,
	implementDetailsTable : true,
	mobileAlign : "right",
	onInitialize : _Designer_Control_RelationSelect_OnInitialize,
	onInitializeDict : _Designer_Control_DisplayText_OnInitializeDict,
	attrs : {
		width : {
			text: Designer_Lang.controlAttrWidth,
			value: "120",
			type: 'text',
			show: true,
			validator: Designer_Control_Attr_Width_Validator,
			checkout: Designer_Control_Attr_Width_Checkout
		},
		resetValue :{
			text : Designer_Lang.relation_select_clearVal,
			value : '',
			type : 'self',
			draw : _Designer_Control_Attr_RelationSelect_ResetValue_Draw,
			show : true
		},
		tipInfo : Designer_Config.attrs.tipInfo
	},
	info : {
		name : Designer_Lang.relation_select_name
	}

};
Designer_Config.buttons.tool.push("relationSelect");
Designer_Menus.tool.menu['relationSelect'] = Designer_Config.operations['relationSelect'];

function _Designer_Control_RelationSelect_OnInitialize(){
	this.onInitializeDict();
}

function _Designer_Control_RelationSelect_OnDraw(parentNode, childNode) {
	if (this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
		
	}
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className="xform_relation_select";
	domElement.label=_Get_Designer_Control_Label(this.options.values, this);

	var values = this.options.values;

	
	// end 设置宽度
	var selectDom = document.createElement('select');

	$(selectDom).attr("required",this.options.values.required == 'true' ? 'true'
			: 'false');
	$(selectDom).attr("_required", this.options.values.required == 'true' ? 'true'
			: 'false');

	// selectDom.style.width = values.width;
	
	selectDom.id = this.options.values.id;
	$(selectDom).attr("label",domElement.label);
	$(selectDom).attr("idText",this.options.values.idText);
	
	if (this.options.values.width) {
		if( this.options.values.width.toString().indexOf('%') > -1){
			selectDom.style.width = this.options.values.width;
		}
		else{
			selectDom.style.width = this.options.values.width+"px";
		}
	}
	else{
		values.width = "120";
		selectDom.style.width=values.width+"px";
	}
	$(selectDom).attr("width",this.options.values.width);
	$(domElement).css("display","inline-block");
	$(domElement).css("width",this.options.values.width);

	if (values.outputParams) {
		$(selectDom).attr("outputParams", values.outputParams);
	}
	if (values.inputParams) {
		$(selectDom).attr("inputParams",values.inputParams);
	}
	if (values.source) {
		$(selectDom).attr("source",values.source);
	}
	if (values.funKey) {
		$(selectDom).attr("funKey", values.funKey);
	}
	if(values.defValue){
		$(selectDom).attr("defValue", values.defValue);
	}
	if(values.resetValue){
		$(selectDom).attr("resetValue", values.resetValue);
	}
	//提示语
	if(values.tipInfo != null && values.tipInfo != ''){
		$(selectDom).attr("tipInfo", values.tipInfo);
	}
	//是否摘要
	if(values.summary == "true"){
		$(selectDom).attr("summary", "true");
	}else{
		$(selectDom).attr("summary", "false");
	}
	$(selectDom).html("<option>"+Designer_Lang.relation_select_pleaseChoose+"</option>");

	//safari浏览器因下拉框可以点击，导致属性面板很难点出来
	selectDom.style.display = 'none';
//	domElement.appendChild(selectDom);
//	// 设置必填
//	if (this.options.values.required == 'true') {
//		domElement.innerHTML += '<span class=txtstrong>*</span>';
//	}
	var buf = [];
	buf.push('<label class="select_tag_left" style="width:95%"><label class="select_tag_right" style="width:95%">');
	buf.push('<label class="select_tag_face" style="width:94%;overflow: hidden">');
//	if (values.width) {
//		if(values.width.toString().indexOf('%') > -1){
//			buf.push('style="width:',this.options.values.width,';">');
//		}
//		else{
//			buf.push('style="width:',this.options.values.width-20+'px',';">');
//		}
//	}else{
//		values.width = "100";
//		buf.push('style="width:',values.width+'px',';">');
//	}
	if (values.defValue) {
		buf.push(values.defValue, '</label>');
	} else {
		buf.push(Designer_Lang.controlSelectPleaseSelect,'</label>');
	}
	buf.push('</label></label>');
	if(this.options.values.required == 'true') {
		buf.push('<span class=txtstrong>*</span>');
	}
	domElement.innerHTML = buf.join('');
	domElement.appendChild(selectDom);
	setTimeout(function () {
		let selecttagleft = $(domElement).find(".select_tag_left");
		if (selecttagleft.width()){
			if (selecttagleft.width() > 600){
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","97%");
			}else{
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","85%");
			}
		}
	},100);
}

function _Designer_Control_RelationSelect_OnDrawEnd(){
	var domElement = this.options.domElement;
	var selectDom = $(domElement)[0].getElementsByTagName("select");
	$(selectDom).attr("width_parent",$(domElement).width());
	//163522 必填换行问题
	var txtStrongPer = 100-Math.ceil((6*100)/$(this.options.domElement).width());
	$(this.options.domElement).parent().find("label").each(function(){
		var dic_class = $(this).attr("class");
		if(dic_class &&('select_tag_left'==dic_class||'select_tag_right'==dic_class)){
			$(this).css("width",txtStrongPer+"%");
		}
		if(dic_class &&'select_tag_face'==dic_class){
			$(this).css("width",txtStrongPer-1+"%");
		}
	});
}

function _Designer_Control_RelationSelect_DrawXML() {

	var values = this.options.values;

	buf=[];
	
	buf.push( '<extendSimpleProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');

	if (values.defValue != null && values.defValue != '') {
		buf.push('defaultValue="', values.defValue, '" ');
	}
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	buf.push('businessType="', this.type, '" ');
	buf.push('/>');
	var customElementProperties = {};
	customElementProperties.isShow = "false";
	if (values.source) {
		customElementProperties.source = values.source;
	}
	if (values.funKey) {
		customElementProperties.funKey = values.funKey;
	}
	buf.push( '<extendSimpleProperty ');
	buf.push('name="', values.id+"_text", '" ');
	buf.push('label="', values.label + Designer_Lang.controlDisplayValueMessage, '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	buf.push('businessType="', this.type, '" ');
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_Attr_RelationSelect_ResetValue_Draw(name, attr, value,
		form, attrs, values, control){
	var html = '';
	html += "<label isfor='true'><input type='checkbox' name='resetValue'";
	var resetValue = control.options.values.resetValue || 'false';
	if(resetValue == 'true'){
		html += " checked='checked'";		
	}
	html += " onclick='_Designer_Control_Attr_RelationSelect_ResetValue_setValue(this);'/>" + Designer_Lang.relation_select_resetValue_tipMessage + "</label>";
	//延时赋值
	setTimeout(function(){
		if(document.getElementsByName("resetValue")[0]){
	    	document.getElementsByName("resetValue")[0].value = resetValue;
	    }
	},0);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_Attr_RelationSelect_ResetValue_setValue(dom){
	if(document.getElementsByName("resetValue")[0]){
    	document.getElementsByName("resetValue")[0].value = dom.checked ? 'true':'false';
    }
}