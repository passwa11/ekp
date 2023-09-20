if(Designer_Lang.CtripExists){
	Designer_Config.operations['ctripOrderDetail']={
			imgIndex : 91,
			title:Designer_Lang.controlCtripOrderDetail_attr_title,
			titleTip:Designer_Lang.controlCtripOrderDetail_attr_title,
			run : function (designer) {
				designer.toolBar.selectButton('ctripOrderDetail');
			},
			type : 'cmd',
			order: 1,
			select: true,
	    	cursorImg: 'style/cursor/uploadimg.cur'
	};
	Designer_Config.controls.ctripOrderDetail = {
			type : "ctripOrderDetail",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_CtripOrderDetail_Berth_OnDraw,
			drawXML : _Designer_Control_CtripOrderDetail_Berth_DrawXML,
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow : {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true
				},
				formula: Designer_Config.attrs.formula
			},
			onAttrLoad:_Designer_Control_Attr_Cost_Center_OnAttrLoad,
			info : {
				name: Designer_Lang.controlCtripOrderDetail_attr_title
			},
			resizeMode : 'onlyWidth'
	}
	Designer_Config.buttons.control.push("ctripOrderDetail");
	//把控件增加到右击菜单区
	Designer_Menus.add.menu['ctripOrderDetail'] = Designer_Config.operations['ctripOrderDetail'];
}
function _Designer_Control_CtripOrderDetail_Berth_OnDraw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_CtripOrderDetail_Berth_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_CtripOrderDetail_Berth_DrawByType(parent,attrs,values,target){
	var width = values.width||'85%';
	if(width.indexOf('%')==-1){
		width+='px';
	}
	var props = {width:width,id:values.id,subject:values.label,companyId:values.companyId};
	if(values.required){
		props.required = true;
	}
	if(values.defaultValue){
		props.defaultValue = values.defaultValue;
	}
    var htmlCode = '<label style="display: inline-block; width: 88px;"><table><tr><td>'+Designer_Lang.controlCtripOrderDetail_attr_title+'</td></tr></table></label>'
    return htmlCode;
}
function _Designer_Control_CtripOrderDetail_Berth_DrawXML() {
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
	buf.push('name="', values.id, '_vehicle_name" ');
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
	buf.push('name="', values.id, '_vehicle_id" ');
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
function _Designer_Control_Attr_Cost_Center_OnAttrLoad(form,control){
	
}
