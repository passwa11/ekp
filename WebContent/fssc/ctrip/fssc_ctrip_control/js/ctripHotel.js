Designer_Config.operations['ctripHotel']={
    imgIndex : 78,
    title:Designer_Lang.controlCtripHotel_attr_title,
    titleTip:Designer_Lang.controlCtripHotel_attr_title,
    run : function (designer) {
        designer.toolBar.selectButton('ctripHotel');
    },
    type : 'cmd',
    order: 1,
    select: true,
    cursorImg: 'style/cursor/newAddress.cur'
};
Designer_Config.controls.ctripHotel = {
    type : "ctripHotel",
    storeType : 'field',
    inherit    : 'base',
    onDraw : _Designer_Control_Attr_Self_CtripHotel_Draw,
    drawXML : _Designer_Control_CtripHotel_CtripHotel_DrawXML,
    implementDetailsTable : true,
    attrs : {
        label : Designer_Config.attrs.label,
        canShow : {
            text: Designer_Lang.controlAttrCanShow,
            value: "true",
            type: 'hidden',
            checked: true
        },
        //控件所在位置，非input类，系统无法自动带索引
        matchType : {
            text :Designer_Lang.controlCtrip_attr_matchType,
            value : '',
            required: false,
            type : 'self',
            draw:_Designer_Control_Attr_Hotel_Self_Match_Type_Draw,
            show : true
        },
        docNumberId : {
            text :Designer_Lang.controlCtripHotel_attr_docNumber,
            value : '',
            required: true,
            type : 'self',
            draw:_Designer_Control_Attr_Self_DocNumber_Draw,
            show : true,
            checkout: function(msg, name, attr, value, values, control){
                if(!values.docNumberId){
                    msg.push(Designer_Lang.controlCtripHotel_attr_docNumber_null)
                    return false;
                }
                return true;
            }
        },
        formula: Designer_Config.attrs.formula
    },
    onAttrLoad:_Designer_Control_Attr_CtripHotel_OnAttrLoad,
    info : {
        name: Designer_Lang.controlCtripHotel_attr_title
    },
    resizeMode : 'onlyWidth'
}
Designer_Config.buttons.control.push("ctripHotel");
//把控件增加到右击菜单区
Designer_Menus.add.menu['ctripHotel'] = Designer_Config.operations['ctripHotel'];

function _Designer_Control_Attr_Self_CtripHotel_Draw(parentNode, childNode){
	// 必须要做ID设置
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
    $(domElement).attr("style","display: inline-block; width: auto;");
	var htmlCode = _Designer_Control_CtripHotel_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;	
}

function _Designer_Control_CtripHotel_DrawByType(parent,attrs,values,target){
    var width = values.width||'85%';
    if(width.indexOf('%')==-1){
        width+='px';
    }
    var props = {width:width,id:values.id,subject:values.label,docNumberId:values.docNumberId,matchType:values.matchType};
    if(values.required){
        props.required = true;
    }
    if(values.defaultValue){
        props.defaultValue = values.defaultValue;
    }

    var htmlCode = '<div style="width:65px;height:28px;line-height:28px;text-align:center;color:#fff;background:#47b5ea;border-radius:5px;" props="'+JSON.stringify(props).replace(/\"/g,"'")+'"><input type="hidden" props="'+JSON.stringify(props).replace(/\"/g,"'")+'"/>'+Designer_Lang.controlCtripHotel_attr_title+'</div>';
    return htmlCode;
}

function _Designer_Control_Attr_Hotel_Self_Match_Type_Draw(name, attr,value, form, attrs, values, control){
    var html=[];
    html.push("<input name='matchType' type='radio' value='1'");
    if(values.matchType=='1'){
        html.push(" checked");
    }
    html.push("/>");
    html.push(Designer_Lang.controlCtrip_attr_matchType_main);
    html.push("<input name='matchType' type='radio' value='2'");
    if(values.matchType=='2'){
        html.push(" checked");
    }
    html.push("/>");
    html.push(Designer_Lang.controlCtrip_attr_matchType_detail);
    html.push("<span class='txtstrong'>*</span><br><span class='txtstrong'>");
    html.push("</span>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}

function _Designer_Control_CtripHotel_CtripHotel_DrawXML() {
	return "";
};

function _Designer_Control_Attr_CtripHotel_OnAttrLoad(form,control){
	
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


