/**
 * 节点控件
 * 
 */
Designer_Config.operations['showHandlerNode']={
		lab : "5",
		imgIndex : 76,
		title:Designer_Lang.ShowHandlerNodeName,
		run : function (designer) {
			designer.toolBar.selectButton('showHandlerNode');
		},
		type : 'cmd',
		order: 7,
		line_splits_end:true,
		shortcut : 'N',
		select: true,
		cursorImg: 'style/cursor/select.cur'
};
Designer_Config.controls.showHandlerNode= {
		type : "showHandlerNode",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_ShowHandlerNode_OnDraw,
		implementDetailsTable : true,
		attrs : {
			styleType:{
				text: Designer_Lang.ShowHandlerNodeType,
				value : '11',
				type : 'select',
				opts: [
					{name: 'handler1', text: Designer_Lang.ShowHandlerNodeDisplayType1, value:'11'},
					{name: 'handler2', text: Designer_Lang.ShowHandlerNodeDisplayType2, value:'12'}
				],
				show: true
			},
		/*	mouldDetail : {//选择节点
				text: Designer_Lang.ShowHandlerNodeSelectNodes,
				value : '',
				type: 'self',
				draw: _Designer_Control_ShowHandlerNode_Attr_MouldDetail_Self_Draw,
				checkout:function(msg, name, attr, value, values, control){
					if(!value||value=='~'){
						msg.push(values.label+',' + Designer_Lang.handlerShowNode_CheckRequiredAttributeNotNull);
						return false;
					}
					return true;
				},
				validator:function (elem, name, attr, value, values) {
					if (!value||value=='~') {
						alert(Designer_Lang.GetMessage(Designer_Lang.handlerShowNode_requiredAttributeNotNull,attr.text));
						elem.focus();
						return false;
					}
					return true;
				},
				show: true
			},*/
			content : {
				text: Designer_Lang.ShowHandlerNodeNameContent,
				value: "",
				type: 'self',
				show: true,
				required: false,
				draw:Designer_Control_ShowHandlerNode_textareaDraw,
				lang: true,
				hint: Designer_Lang.ShowHandlerNodeTips,
				convertor: Designer_Control_Attr_HtmlEscapeConvertor,
				validator: Designer_Control_Attr_ShowHandlerNodeContent_Validator
			},
			isHiddenInMobile : {
				text : Designer_Lang.controlTextLabelAttrIsHiddenInMobile,
				value : 'false',
				type : 'checkbox',
				show: true						
			}
		},
		info : {
			name: Designer_Lang.ShowHandlerNodeName
		},
		resizeMode : 'no'  //尺寸调整模式(onlyWidth, onlyHeight, all, no)
}

Designer_Config.buttons.tool.push("showHandlerNode");
Designer_Menus.tool.menu['showHandlerNode'] = Designer_Config.operations['showHandlerNode'];

function _Designer_Control_ShowHandlerNode_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	$(domElement).attr("id",this.options.values.id);
//	$(domElement).attr("content",this.options.values.content);
//	console.log(this.options.values.content);
//	debugger
	var content = this.options.values.content;
	if(content){
		content = content.replace(/\r\n/g, '__').replace(/\n/g, '__');
	}
	$(domElement).attr("content",content);
	$(domElement).attr("styleType",this.options.values.styleType);
	/*$(domElement).attr("mouldDetail",this.options.values.mouldDetail);*/
	$(domElement).attr("isHiddenInMobile",this.options.values.isHiddenInMobile);
	//背景图标
	var label = document.createElement("label");
	$(label).addClass("handler_node_icon");
	domElement.appendChild(label);
}
function Designer_Control_ShowHandlerNode_textareaDraw(name, attr, value) {
	var html = "<textarea style='width:93%;' name='" + name + "' title='" + (attr.hint ? attr.hint : "")
		+ "' class='attr_td_textarea'>";
	if (value != null && value.length > 0) {
		html += value;
	}
	html += "</textarea>";
	return Designer_Control_ShowHandlerNode_wrapTitle(name, attr, value, html);
}

function Designer_Control_ShowHandlerNode_wrapTitle(name, attr, value, html) {
	if (attr.required == true) {
		html += '<span class="txtstrong">*</span>';
	}
	if (attr.hint) {
		html += '<div id="attrHint_' + name + '">' + Designer.HtmlEscape(attr.hint) + '</div>';
	}
	return ('<tr><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
}


function _Designer_Control_ShowHandlerNode_Attr_MouldDetail_Self_Draw(name, attr, value, form, attrs, values,control){
	
	/*html="<div id='auditnote_mouldDetail_html'>"+_GetHTMLByHanderType(mouldValue,value)+"</div>";*/
	html = "<input type='hidden' value='"+(values.mouldDetail?values.mouldDetail:'')+"' name='mouldDetail' />";
	html += "<div id='handlerShowNode_mouldDetail_html'>"+_ShowHandlerNodeGetHTMLByHanderType('21',value)+"</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

/*function _ShowHandlerNodeGetHTMLByHanderType(mouldValue,val){
	var html=[];
	var names="";
	var labels="";
	if(val){
		names=val.split("~")[0];
		labels=val.split("~")[1];
	}
	switch(mouldValue)
	{
		case '21':
		{
			//选择展示节点
			lableText=' ' + Designer_Lang.handlerShowNode_chooseNode;
			html.push("<input type='text' id='handlerShowNode_detail_attr_name' name='handlerShowNode_detail_attr_name'   readonly='true'  class='inputsgl' value='"+labels+"'/>");
			html.push("<span class='txtstrong'>*</span>");
			html.push("<input type='hidden' id='handlerShowNode_detail_attr_value' name='handlerShowNode_detail_attr_value'  value='"+names+"'/>");
			html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_List_ShowNode('handlerShowNode_detail_attr_value','handlerShowNode_detail_attr_name', ';','',NewAudit_After_MouldDetailSelect_Set,true);\">"+Designer_Lang.handlerShowNode_chooseNode+"</a>");
			break;
		}
	}
	
	//设置mouldDetail Label
	if($("#handlerShowNode_mouldDetail_html")[0]){
		$("#handlerShowNode_mouldDetail_html").parent().prev().text(lableText);
	}
	html=html.join('');
	return html;
}*/

function Designer_Control_Attr_ShowHandlerNodeContent_Validator(elem, name, attr, value, values){
	if('' == value || typeof(value)=='undefined')//为空可以通过，不为空时必须包含占位符{0}
		return true;
	
	if(value.length > 200){
		var len = value.length;
		alert(Designer_Lang.GetMessage(Designer_Lang.ShowHandlerTextLengthLimit,len));
		return false;
	}
		
	if(value.indexOf('{0}') > -1)
		return true;
	alert(Designer_Lang.GetMessage(Designer_Lang.ShowHandlerNodeNotice,attr.text));
	return false;
}