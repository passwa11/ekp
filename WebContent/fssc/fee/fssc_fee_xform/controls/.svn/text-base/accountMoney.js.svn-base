if(Designer_Lang.checkVersion){
	Designer_Config.operations['accountMoney']={
	    imgIndex : 89,
	    title:Designer_Lang.controlAccountMoney_attr_title,
	    titleTip:Designer_Lang.controlAccountMoney_attr_title,
	    run : function (designer) {
	        designer.toolBar.selectButton('accountMoney');
	    },
	    type : 'cmd',
	    order: 1,
	    select: true,
	    cursorImg: 'style/cursor/newAddress.cur'
	};
	Designer_Config.controls.accountMoney = {
	    type : "accountMoney",
	    storeType : 'field',
	    inherit    : 'base',
	    onDraw : _Designer_Control_AccountMoney_OnDraw,
	    drawXML : _Designer_Control_AccountMoney_DrawXML,
	    implementDetailsTable : true,
	    attrs : {
	        label : Designer_Config.attrs.label,
	        canShow : {
	            text: Designer_Lang.controlAttrCanShow,
	            value: "true",
	            type: 'hidden',
	            checked: true
	        },
	        //匹配类型
	        matchType : {
	            text :Designer_Lang.controlAccountMoney_attr_matchType,
	            value : '',
	            required: false,
	            type : 'self',
	            draw:_Designer_Control_Attr_AccountMoney_Self_Match_Type_Draw,
	            show : true
	        },
	        //对应明细
	        matchTable : {
	            text :Designer_Lang.controlAccountMoney_attr_matchTable,
	            value : '',
	            required: false,
	            type : 'self',
	            draw:_Designer_Control_Attr_AccountMoney_Self_Match_Table_Draw,
	            show : true,
	            checkout: function(msg, name, attr, value, values, control){
	                if(values.matchType == '2' && !values.matchTableId){
	                    msg.push(Designer_Lang.controlAccountMoney_attr_checkout_matchTableNull)
	                    return false;
	                }
	                return true;
	            }
	        },
	        currency : {
	            text :Designer_Lang.controlAccountMoney_attr_currency,
	            value : '',
	            required: true,
	            type : 'self',
	            draw:_Designer_Control_Attr_AccountMoney_Self_Currenty_Draw,
	            show : true,
	            checkout: function(msg, name, attr, value, values, control){
	                if(!values.currencyId){
	                    msg.push(Designer_Lang.controlAccountMoney_attr_checkout_currencyNull)
	                    return false;
	                }
	                return true;
	            }
	        },
	        money:{
	            text :Designer_Lang.controlAccountMoney_attr_money,
	            value : '',
	            required: true,
	            type : 'self',
	            draw:_Designer_Control_Attr_AccountMoney_Self_Money_Draw,
	            show : true,
	            checkout: function(msg, name, attr, value, values, control){
	                if(!values.moneyId){
	                    msg.push(Designer_Lang.controlAccountMoney_attr_checkout_moneyNull)
	                    return false;
	                }
	                return true;
	            }
	        },
	        width : {
	            text: Designer_Lang.controlAttrWidth,
	            value: "",
	            type: 'text',
	            show: true,
	            validator: Designer_Control_Attr_Width_Validator,
	            checkout: Designer_Control_Attr_Width_Checkout
	        },
	        formula: Designer_Config.attrs.formula
	    },
	    onAttrLoad:_Designer_Control_Attr_AccountMoney_OnAttrLoad,
	    info : {
	        name: Designer_Lang.controlAccountMoney_attr_title
	    },
	    resizeMode : 'onlyWidth'
	}
	Designer_Config.buttons.control.push("accountMoney");
	//把控件增加到右击菜单区
	Designer_Menus.add.menu['accountMoney'] = Designer_Config.operations['accountMoney'];
}
function _Designer_Control_AccountMoney_OnDraw(parentNode, childNode){
    // 必须要做ID设置
    if(this.options.values.id == null)
        this.options.values.id = "fd_" + Designer.generateID();
    var domElement = _CreateDesignElement('label', this, parentNode, childNode);
    if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
        domElement.style.whiteSpace = 'nowrap';
        domElement.style.width = this.options.values.width;
        domElement.style.overflow = 'visible';
    }
    var htmlCode = _Designer_Control_AccountMoney_DrawByType(this.parent, this.attrs, this.options.values, this);
    domElement.innerHTML = htmlCode;
}

function _Designer_Control_AccountMoney_DrawByType(parent,attrs,values,target){
    var width = values.width||'85%';
    if(width.indexOf('%')==-1){
        width+='px';
    }
    var props = {width:width,id:values.id,subject:values.label,matchType:values.matchType,matchTableId:values.matchTableId,currencyId:values.currencyId,moneyId:values.moneyId};
    var htmlCode = '<div class="inputselectsgl" style="width:'+width+';">'
    htmlCode+='<div class="input"><input subject="" name="'+values.id+'" props="'+JSON.stringify(props).replace(/\"/g,"'")+'" type="text" props='+values.id ;
    htmlCode+='" readonly></div></div>';
    return htmlCode;
}
function _Designer_Control_AccountMoney_DrawXML() {
    var values = this.options.values;
    var buf = [];//mutiValueSplit
    //id
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
    //名称
    buf.push('<extendSimpleProperty ');
    buf.push('name="', values.id, '_name" ');
    buf.push('label="', values.label, '" ');
    buf.push('type="String" ');
    buf.push('notNull="true" ');
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
function _Designer_Control_Attr_AccountMoney_OnAttrLoad(form,control){

}

function _Designer_Control_Attr_AccountMoney_Self_Match_Type_Draw(name, attr,value, form, attrs, values, control){
    var html=[];
    html.push("<input name='matchType' type='radio' value='1'");
    if(values.matchType=='1'){
        html.push(" checked");
    }
    html.push(" onclick='_Designer_Control_AccountMoney_Match_Type_Changed(this.value)'/>");
    html.push(Designer_Lang.controlAccountMoney_attr_matchType_main);
    html.push("<input name='matchType' type='radio' value='2'");
    if(values.matchType=='2'){
        html.push(" checked");
    }
    html.push(" onclick='_Designer_Control_AccountMoney_Match_Type_Changed(this.value)'/>");
    html.push(Designer_Lang.controlAccountMoney_attr_matchType_detail);
    html.push("<span class='txtstrong'>*</span><br><span class='txtstrong'>");
    html.push(Designer_Lang.controlAccountMoney_attr_matchType_tips);
    html.push("</span>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_AccountMoney_Match_Type_Changed(v){
    var type = $("[name=matchType]:checked").val();
    if(type=='1'){
        $("#_Designer_Control_AccountMoney_Match_Table_Tr").hide();
    }else{
        $("#_Designer_Control_AccountMoney_Match_Table_Tr").show();
    }
}
function _Designer_Control_Attr_AccountMoney_Self_Match_Table_Draw(name, attr,value, form, attrs, values, control){
    var html=[];
    var value=values.matchTableId||'',text = values.matchTableName||'';
    html.push("<tr id='_Designer_Control_AccountMoney_Match_Table_Tr' style='display:");
    if(!values.matchType||values.matchType=='1'){
        html.push("none");
    }
    html.push("'><td width='25%' class='panel_td_title'>");
    html.push(Designer_Lang.controlAccountMoney_attr_matchTable);
    html.push("</td><td>")
    html.push("<input name='matchTableName' style='width:73%;' value='");
    html.push(text);
    html.push("' readonly></input>");
    html.push("<input type='hidden' name='matchTableId' value='");
    html.push(value)
    html.push("'></input><a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('matchTableId','matchTableName','true');\">")
    html.push(Designer_Lang.relation_rule_choose+"</a><span class='txtstrong'>*</span>");
    html.push("<br><span class='txtstrong'>");
    html.push(Designer_Lang.controlAccountMoney_attr_matchTable_tips);
    html.push("</span></td></tr>");
    return html.join("");
}
function _Designer_Control_Attr_AccountMoney_Self_Currenty_Draw(name, attr,
                                                                value, form, attrs, values, control) {
    var html=[];
    var textValue=values.currencyName?values.currencyName:"";
    var idValue=values.currencyId?values.currencyId:"";
    html.push("<input name='currencyName' style='width:73%;' value='"+textValue+"' readonly></input>" +
        "<input type='hidden' name='currencyId' value='"+idValue+"'></input>" +
        "<a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('currencyId','currencyName');\">"+Designer_Lang.relation_rule_choose+"</a>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}
function _Designer_Control_Attr_AccountMoney_Self_Money_Draw(name, attr,
                                                                value, form, attrs, values, control) {
    var html=[];
    var textValue=values.moneyName?values.moneyName:"";
    var idValue=values.moneyId?values.moneyId:"";
    html.push("<input name='moneyName' style='width:73%;' value='"+textValue+"' readonly></input>" +
        "<input type='hidden' name='moneyId' value='"+idValue+"'></input>" +
        "<a href='javascript:void(0)' onclick=\"_Relation_Rule_DestDomControlsChoose('moneyId','moneyName');\">"+Designer_Lang.relation_rule_choose+"</a>");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(""));
}


function Designer_AccountMoney_DefaultValue_Validator(){
    return true;
}

function Designer_AccountMoney_DefaultValue_Checkout(){
    return true;
}
