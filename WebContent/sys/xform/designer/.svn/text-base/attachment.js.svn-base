/**********************************************************
功能：附件控件
使用：
	
作者：傅游翔
创建时间：2009-04-28
**********************************************************/
Designer_Config.controls.attachment = {
	type : 'attachment',
	storeType : 'field',
	inherit    : 'base',
	implementDetailsTable : true,
	onDraw : _Designer_Control_Attachment_OnDraw,
	drawMobile : _Designer_Control_Attachment_DrawMobile,
    onInitialize : _Designer_Control_Attachment_OnInitialize,
	drawXML : _Designer_Control_Attachment_DrawXML,
	mobileAlign : "right",
	attrs : {
		label : {
			text : Designer_Lang.controlAttrLabel,
			value: "",
			type: 'label',
			show: true,
			required: true,
			validator: [Designer_Control_Attr_Required_Validator,Designer_Control_Attr_Label_Validator],
			checkout: [Designer_Control_Attr_Required_Checkout,Designer_Control_Attr_Label_Checkout]
		},
		required: {
			text: Designer_Lang.controlAttrRequired,
			value: "false",
			type: 'checkbox',
			checked: false,
			show: true
		},
		fileType : {
			text : Designer_Lang.controlAttachAttrFileType,
			value: "",
			type: 'text',
			hint: Designer_Lang.controlAttachAttrFileTypeHint,
			show: true
		},
		sizeType: {
			text : Designer_Lang.controlAttachAttrSizeType,
			value: 'multi',
			type: 'radio',
			opts: [
				{text:Designer_Lang.controlAttachAttrSizeTypeSingle,value:'single'},
				{text:Designer_Lang.controlAttachAttrSizeTypeMulti,value:'multi'}
			],
			translator:opts_common_translator,
			show: true
		},
		isShowDownloadCount: {
			text : Designer_Lang.controlAttachAttrDisplayDetails,
			value: '',
			type: 'checkGroup',
			opts: [
				{text:Designer_Lang.controlAttachAttrShowDownloadCount,name: 'isShowDownloadCount',value:'isShowDownloadCount',checked:true}
			],
			show: true
		},
		otherCanNotDelete: {
			text : Designer_Lang.controlAttachDeleteRight,
			value: '',
			type: 'self',
			draw : _Designer_Control_Attachment_Self_Scope_Draw,
			opts: [
				{text:Designer_Lang.controlAttachDeleteRightTip,name: 'otherCanNotDelete',value:'otherCanNotDelete',checked:false}
			],
			show: true,
			translator:attachment_translator,
		},
		allCanNotDelete: {
            text : "",
            value: '',
            type: 'checkGroup',
            displayText:Designer_Lang.controlAttachDeleteRight,
            opts: [
                {text:Designer_Lang.controlAttachDeleteAllRightTip,name: 'allCanNotDelete',value:'allCanNotDelete',checked:false}
            ],
            show: false,
            translator:attachment_translator,
        },
        show: {
            text : Designer_Lang.controlAttachAttrShow,
            value: 'slideDown',
            type: 'radio',
            opts: [
                {text:Designer_Lang.controlAttachSlideUp,value:'slideUp'},
                {text:Designer_Lang.controlAttachSlideDown,value:'slideDown'}
            ],
            hintAfter: Designer_Lang.controlAttrSlideHint,
            translator:opts_common_translator,

            show: true
        }
	},
	info : {
		name: Designer_Lang.controlAttachInfoName
	},
	resizeMode : 'no'
}

//选择范围onDraw
function _Designer_Control_Attachment_Self_Scope_Draw(name, attr,
				value, form, attrs, values, control){
	var html = '';
	html += '<input name="otherCanNotDelete" type="hidden" />';
	if("true"==values.otherCanNotDelete){
		html+='<label isfor="true"><input type="checkbox" checked value="true" name="otherCanNotDelete"/>'+Designer_Lang.controlAttachDeleteRightTip+'</label><br/>';
	}else{
		html+='<label isfor="true"><input type="checkbox" value="true" name="otherCanNotDelete"/>'+Designer_Lang.controlAttachDeleteRightTip+'</label><br/>';
	}

	html += '<input name="allCanNotDelete" type="hidden" />';
	if("true"==values.allCanNotDelete){
		html+='<label isfor="true"><input type="checkbox" checked value="true" name="allCanNotDelete"/>'+Designer_Lang.controlAttachDeleteAllRightTip+'</label><br/>';
	}else{
		html+='<label isfor="true"><input type="checkbox" value="true" name="allCanNotDelete"/>'+Designer_Lang.controlAttachDeleteAllRightTip+'</label><br/>';
	}


	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function attachment_translator(change,obj) {
	 var t_value = {
        "true": Designer_Lang.truee,
        "false": Designer_Lang.falsee,
    };
	if (!change) {
		return "";
	}	
	var temptext="";
	if(obj&&obj.opts&&obj.opts.length>0){
		temptext=obj.opts[0].text;
	}
	var before = (change.before == undefined || change.before == "") ? "" : t_value[change.before] ;
	var after =  (change.after == undefined || change.after == "") ? "" : t_value[change.after] ;
	var html = "<span> "+temptext+Designer_Lang.from+" (" + before + ") "+Designer_Lang.to+" (" + after + ")</span>";
	return html; 
}

function _Designer_Control_Attachment_OnInitialize() {
    var domElement = this.options.domElement;
    if ($(domElement).closest("table[fd_type='detailsTable']").length == 0) {
        if (this.options.values.show == null) {
            this.options.values.show = "slideUp";
        }
        if (!$(domElement).attr('slideDown')) {
            $(domElement).attr('slideDown', 'false');
        }
    }
}

function _Designer_Control_Attachment_OnDraw(parentNode, childNode){
	var values = this.options.values;
	var multi = (values.sizeType == 'multi' || values.sizeType == null);
	
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if ($(domElement).closest("table[fd_type='detailsTable']").length == 0) {
	    if (values.show == null) {
            values.show = "slideUp";
        }

    }
	domElement.style.width = '280px';
	domElement.style.textAlign = 'left';
	if (values.fileType != null && values.fileType != '') {
		domElement.title = Designer_Lang.controlAttachDomTitle.replace(/\{type\}/, values.fileType);
	}
	if(values.id == null)
		values.id = "fd_" + Designer.generateID();
	domElement.setAttribute('id', values.id);
	domElement.setAttribute('enabledFileType', (values.fileType == null ? '' : values.fileType));
	domElement.setAttribute('fdMulti', multi.toString());
	domElement.setAttribute('label', _Get_Designer_Control_Label(this.options.values, this));
	domElement.setAttribute('required', values.required==null?'false':values.required);
	domElement.setAttribute('isShowDownloadCount', values.isShowDownloadCount==null?'true':values.isShowDownloadCount);
	domElement.setAttribute('otherCanNotDelete', values.otherCanNotDelete=='true'?'true':'false'); 
	domElement.setAttribute('allCanNotDelete', values.allCanNotDelete=='true'?'true':'false');
    domElement.setAttribute('slideDown', (values.show==null || values.show== "slideDown") ? 'true' : 'false');
    if (this.parent != null) {
		domElement.setAttribute('tableName', _Get_Designer_Control_TableName(this.parent));
	}
	var buf = [' <button onclick="return false;" class="btnopt" style="width:71px;height:25px;">',Designer_Lang.controlAttachDomUploadBtn,'</button> '];
	buf.push('<span style="padding-left:8px;display:inline-block">',Designer_Lang.controlAttachDomTip);
	if(values.required && values.required=='true'){
		buf.push('<span class="txtstrong">*</span>');
	}
	buf.push('</span>');
	domElement.innerHTML = buf.join('');
}

function _Designer_Control_Attachment_DrawXML() {
	var values = this.options.values;
	var label = Designer.HtmlEscape(values.label);
	var multi = (values.sizeType == 'multi' || values.sizeType == null);
	var buf = ['<attachmentProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', label, '" ');
	buf.push('type="','attachment','" ');
	buf.push('multi="',multi,'" ');
	buf.push('/>');
	return buf.join('');
}