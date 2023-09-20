if(Designer_Lang.CtripExists){
	Designer_Config.operations['ctripSSO']={
	    imgIndex : 1,
	    title:Designer_Lang.controlCtripSSO_attr_title,
	    titleTip:Designer_Lang.controlCtripSSO_attr_title,
	    run : function (designer) {
	        designer.toolBar.selectButton('ctripSSO');
	    },
	    type : 'cmd',
	    order: 1,
	    select: true,
	    cursorImg: 'style/cursor/newAddress.cur'
	};
	Designer_Config.controls.ctripSSO = {
	    type : "ctripSSO",
	    storeType : 'field',
	    inherit    : 'base',
	    onDraw : _Designer_Control_CtripSSO_Berth_OnDraw,
	    drawXML : _Designer_Control_CtripSSO_Berth_DrawXML,
	    implementDetailsTable : true,
	    attrs : {
	        label : Designer_Config.attrs.label,
	        canShow : {
	            text: Designer_Lang.controlAttrCanShow,
	            value: "true",
	            type: 'hidden',
	            checked: true
	        },
	        company : {
	            text :Designer_Lang.controlCostCenter_attr_company,
	            value : '',
	            required: true,
	            type : 'self',
	            draw:_Designer_Control_Attr_Self_Company_Draw,
	            show : true,
	            checkout: function(msg, name, attr, value, values, control){
	                if(!values.companyId){
	                    msg.push(Designer_Lang.controlCtripSSO_attr_checkout_companyNull)
	                    return false;
	                }
	                return true;
	            }
	        },
	        docNumberId : {
	            text :Designer_Lang.controlCtripSSO_attr_docNumber,
	            value : '',
	            required: true,
	            type : 'self',
	            draw:_Designer_Control_Attr_Self_DocNumber_Draw,
	            show : true,
	            checkout: function(msg, name, attr, value, values, control){
	                if(!values.docNumberId){
	                    msg.push(Designer_Lang.controlCtripSSO_attr_docNumber_null)
	                    return false;
	                }
	                return true;
	            }
	        },
	        formula: Designer_Config.attrs.formula
	    },
	    onAttrLoad:_Designer_Control_Attr_Cost_Center_OnAttrLoad,
	    info : {
	        name: Designer_Lang.controlCtripSSO_attr_title
	    },
	    resizeMode : 'onlyWidth'
	}
	Designer_Config.buttons.control.push("ctripSSO");
	//把控件增加到右击菜单区
	Designer_Menus.add.menu['ctripSSO'] = Designer_Config.operations['ctripSSO'];
}
function _Designer_Control_CtripSSO_Berth_OnDraw(parentNode, childNode){
    // 必须要做ID设置
    if(this.options.values.id == null)
        this.options.values.id = "fd_" + Designer.generateID();
    var domElement = _CreateDesignElement('label', this, parentNode, childNode);
    if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
        domElement.style.whiteSpace = 'nowrap';
        domElement.style.width = this.options.values.width;
        domElement.style.overflow = 'visible';
    }
    var htmlCode = _Designer_Control_CtripSSO_Berth_DrawByType(this.parent, this.attrs, this.options.values, this);
    domElement.innerHTML = htmlCode;
}

function _Designer_Control_CtripSSO_Berth_DrawByType(parent,attrs,values,target){
    var width = values.width||'85%';
    if(width.indexOf('%')==-1){
        width+='px';
    }
    var props = {width:width,id:values.id,subject:values.label,companyId:values.companyId,docNumberId:values.docNumberId};
    if(values.required){
        props.required = true;
    }
    if(values.defaultValue){
        props.defaultValue = values.defaultValue;
    }

    var htmlCode = '<label style="display: inline-block; width: 65px;"><table><tr><td><input props="'+JSON.stringify(props).replace(/\"/g,"'")+'" name="'+values.id+'.id" type="hidden">'+Designer_Lang.controlCtripSSO_attr_title+'</td></tr></table></label>';
    return htmlCode;
}
function _Designer_Control_CtripSSO_Berth_DrawXML() {
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

function _Designer_Control_Attr_Self_Company_Draw(name, attr,
                                                              value, form, attrs, values, control) {
    var html=[];
    var textValue=values.companyName?values.companyName:"";
    var idValue=values.companyId?values.companyId:"";
    html.push("<input name='companyName' style='width:73%;' value='"+textValue+"' readonly></input>" +
        "<input type='hidden' name='companyId' value='"+idValue+"'></input>" +
        "<a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('companyId','companyName');\">"+Designer_Lang.relation_rule_choose+"</a>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}

function _Designer_Control_Attr_Self_DocNumber_Draw(name, attr,
                                                    value, form, attrs, values, control) {
    var html=[];
    var textValue=values.docNumberName?values.docNumberName:"";
    var idValue=values.docNumberId?values.docNumberId:"";
    html.push("<input name='docNumberName' style='width:73%;' value='"+textValue+"' readonly></input>" +
        "<input type='hidden' name='docNumberId' value='"+idValue+"'></input>" +
        "<a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('docNumberId','docNumberName');\">"+Designer_Lang.relation_rule_choose+"</a>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
