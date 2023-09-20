/**
 * 提示控件
 * @作者：李文昌 @日期：2018年07月30日  
 */
Designer_Config.operations['prompt']={
		lab : "5",
		imgIndex : 66,
		title:Designer_Lang.prompt_name,
		run : function (designer) {
			designer.toolBar.selectButton('prompt');
		},
		type : 'cmd',
		order: 10,
		line_splits_end:true,
		shortcut : 'N',
		select: true,
		cursorImg: 'style/cursor/prompt.cur'
};
Designer_Config.controls.prompt= {
		type : "prompt",
		storeType : 'none',
		inherit    : 'textLabel',
		onDraw : _Designer_Control_prompt_OnDraw,
		onDrawEnd : _Designer_Control_prompt_OnDrawEnd,
		attrs : {
			content : {
				text: Designer_Lang.controlPromptAttrContent,
				value: "",
				type: 'self',
				show: true,
				required: true,
				draw:Designer_Control_prompt_textareaDraw,
				lang: true,
				hint: Designer_Lang.controlPromptTip,
				validator: Designer_Control_Attr_Required_Validator,
				convertor: Designer_Control_Attr_HtmlEscapeConvertor
			},
			line:{
				text:Designer_Lang.controlBrcontrol_html_title,
				show: true,
				translator: valueTranslate
			}
		},
		info : {
			name: Designer_Lang.controlPromptInfoName
		},
		resizeMode : 'no'  //尺寸调整模式(onlyWidth, onlyHeight, all, no)
}
function valueTranslate(item,pname) {
	 var t_value = {
        "normal": Designer_Lang.controlTextLabelAttrNormal,
        "breakWord": Designer_Lang.controlTextLabelAttrBreakWord,  
        "nowrap": Designer_Lang.controlTextLabelAttreNoWrap,  
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
Designer_Config.buttons.tool.push("prompt");
Designer_Menus.tool.menu['prompt'] = Designer_Config.operations['prompt'];

function _Designer_Control_prompt_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	$(domElement).attr("id",this.options.values.id);
	//背景样式
	with(domElement.style) {
		width='24px';
		display = 'inline-block';
		textAlign = "left";
	}
	//背景图标
	var label = document.createElement("label");
	$(label).addClass("promptControl");
	domElement.appendChild(label);
	//提示语
	var div = document.createElement("div");
	div.style.display = 'none';
	div.setAttribute("isDesignElement", "false");
	domElement.appendChild(div);
	//将样式设置在span元素上
	if(this.options.values.font)	
		_Designer_Control_prompt_SetStyle(div, this.options.values.font, "fontFamily");
	if(this.options.values.size)
		_Designer_Control_prompt_SetStyle(div, this.options.values.size, "fontSize");
	if(this.options.values.color)
		_Designer_Control_prompt_SetStyle(div, this.options.values.color, "color");
	if(this.options.values.isHiddenInMobile)
		domElement.setAttribute("isHiddenInMobile",this.options.values.isHiddenInMobile);
	if(this.options.values.b=="true") div.style.fontWeight="bold";
	if(this.options.values.i=="true") div.style.fontStyle = "italic";
	if(this.options.values.underline=="true") div.style.textDecoration="underline";
	//提示内容
	if(this.options.values.content==null || this.options.values.content==''){
		//不处理
	} else {
		html = this.options.values.content.replace(/\r\n/g, '<br>').replace(/\n/g, '<br>');
		html = html.replace(/ /g, '&nbsp;');
		div.innerHTML = html;
	}
}
function Designer_Control_prompt_textareaDraw(name, attr, value) {
	var html = "<textarea style='width:93%;' name='" + name + "' title='" + (attr.hint ? attr.hint : "")
		+ "' class='attr_td_textarea'>";
	if (value != null && value.length > 0) {
		html += value;
	}
	html += "</textarea>";
	return Designer_Control_prompt_wrapTitle(name, attr, value, html);
}

function Designer_Control_prompt_wrapTitle(name, attr, value, html) {
	if (attr.required == true) {
		html += '<span class="txtstrong">*</span>';
	}
	if (attr.hint) {
		html += '<div id="attrHint_' + name + '">' + Designer.HtmlEscape(attr.hint) + '</div>';
	}
	return ('<tr><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
}

function _Designer_Control_prompt_OnDrawEnd(){
	
}

function _Designer_Control_prompt_SetStyle(domElement, targetValue, styleName){
	if(targetValue==null || targetValue=='') {
		domElement.style[styleName] = null;
	} else {
		domElement.style[styleName] = targetValue;
	}
}