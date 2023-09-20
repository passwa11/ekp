/**********************************************************
功能：DIV布局控件
使用：

**********************************************************/
document.write('<script>Com_IncludeFile("base64.js","../sys/attachment/js/");</script>');
Com_IncludeFile("webuploader.min.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
Designer_Config.controls['divcontrol'] = {
		type : "divcontrol",
		inherit    : 'base',
		container : true,
		onDraw : _Designer_Control_Divcontrol_OnDraw,
		onDrawEnd : _Designer_Control_Divcontrol_OnDrawEnd,
		drawMobile : _Designer_Control_Divcontrol_DrawMobile,
		onInitialize : _Designer_Control_Divcontrol_OnInitialize,
		onShiftDrag   : _Designer_Control_Base_DoDrag,
		onShiftDragMoving: _Designer_Control_Base_DoDragMoving,
		onShiftDragStop: _Designer_Control_Base_DoDragStop,
		onDragStop:function(){},     
		onDrag:function(event){
			var _prevDragDomElement = this.owner._dragDomElement, currElement = event.srcElement || event.target;
			//绑定调整大小框
			this.owner.resizeDashBox.attach(this);
			//若当前拖拽控件与前一个拖拽控件不同，则调用前一控件取消锁定状态
			if (_prevDragDomElement && _prevDragDomElement !== this && _prevDragDomElement.onUnLock)
				_prevDragDomElement.onUnLock();
			//记录当前拖拽控件
			this.owner._dragDomElement = this;
			
		},
		onDragMoving:function(event){
			
		},
		drawXML : _Designer_Control_DivControl_DrawXML,
		implementDetailsTable : true,
		insertValidate: _Designer_Control_DivControl_InsertValidate, //插入校验
		destroyMessage:Designer_Lang.div_sureDeleteAllControls,
		info : {
			name: Designer_Lang.controlDivcontrol_info_name
		},
		resizeMode : 'onlyWidth',
		storeType : 'layout',
		attrs : {
			label : {
				text : Designer_Lang.controlAttrLabel,
				value: "",
				type: 'label',
				show: true,
				lang: true,
				synchronous: true,//多表单是否同步
				required: true,
				validator: [Designer_Control_Attr_Required_Validator,Designer_Control_Attr_Label_Validator],
				convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
			},
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "100%",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			height: {
				text: Designer_Lang.div_height,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Divcontrol_Validator,
				checkout: Designer_Control_Divcontrol_Checkout
			},
			overflow:{
				text: Designer_Lang.div_contentOverflowDefinition,
				value: "auto",
				type: 'select',
				opts: [{text:Designer_Lang.div_visible,value:"visible"},
						{text:Designer_Lang.div_hidden,value:"hidden"},
						{text:Designer_Lang.div_scroll,value:"scroll"},
						{text:Designer_Lang.div_auto,value:"auto"},
						{text:Designer_Lang.div_inherit,value:"inherit"}],
				translator: opts_common_translator,
				show: true
				
			},
			borderSize : {
				text: Designer_Lang.div_borderThickness,
				value: "1px",
				type: 'select',
				opts: [
				       	{text:'0px',value:"0px"},
						{text:'1px',value:"1px"}, // tb_normal
						{text:'2px',value:"2px"},
						{text:'3px',value:"3px"},
						{text:'4px',value:"4px"},
						{text:'5px',value:"5px"},
						{text:'10px',value:"10px"}
					],
				show:true
			},
			background : {
				text: Designer_Lang.div_background,
				value: "img",
				type: 'self',
				draw : _Designer_Control_Div_Self_Background_Draw,
				opts: [{text:Designer_Lang.div_background_image,value:"img"},{text:Designer_Lang.div_background_color,value:"color"}],
				translator: opts_common_translator,
				show:true
			},
			background_img: {
				text: Designer_Lang.div_background_img,
				show: true,
				type: 'self',
				draw : _Designer_Control_Div_Self_Background_Img_Draw
			},
			background_img_src:{
				text: Designer_Lang.div_background_img_src,
				type:'hidden',
				show:true
			},
			background_img_showtype: {
				text: Designer_Lang.div_background_img_showtype,
				value: '',
				type: 'hidden',
				opts: [{text:Designer_Lang.div_background_cover,value:"cover"},{text:Designer_Lang.div_background_repeat,value:"repeat"}],
				translator: opts_common_translator
			},
			background_color : {
				text: Designer_Lang.div_background_color,
				value: "#ffffff",
				type: 'color',
				show: true
			},
			customCss : {
				text: Designer_Lang.div_DIYStyle,
				value: "",
				type: 'textarea',
				show: true
			},
			isSupportMobile: {
				text: Designer_Lang.div_isSupportMobile,
				value: 'disable',
				type: 'radio',
				opts: [{text:Designer_Lang.div_enable,value:"enable"},{text:Designer_Lang.div_disable,value:"disable"}],
				translator: opts_common_translator,
				show: true
			},
			help:{
				text: Designer_Lang.div_shiftEnter,
				type: 'help',
				align:'left',
				show: true
			}
		},
		onAttrLoad : _Designer_Control_Div_OnAttrLoad,
		info : {
			name: Designer_Lang.div_name
		},
		mimeTypes:null
};

function _Designer_Control_Divcontrol_OnDraw(parentNode, childNode) {
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.id = this.options.values.id;
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	//设置初始值
	if(!this.options.values.customCss){
		this.options.values.customCss="border-style:solid;border-color:#d2d2d2;padding:0px;";
		$(domElement).attr("customCss",this.options.values.customCss);
	}
	domElement.style.cssText = this.options.values.customCss;
	var bar = document.createElement("div");
	domElement.appendChild(bar);
	//bar.className = 'rightIconBar';
	bar.style.cssText="background-color: #F5F5F5;border: 1px solid #DDDDDD;color: #9DA0A4;font-size: 12px;font-weight: bold;left: -1px;font-style:italic;padding: 2px;top: -1px;width:40px;height:15px;float:left";
	bar.innerHTML="DIV";
	
}

function _Designer_Control_Divcontrol_OnDrawEnd() {
	var domElement = this.options.domElement;
	domElement.style.cssText = this.options.values.customCss;
	$(domElement).attr("customCss",this.options.values.customCss);
	if (this.options.values.width){
		if(this.options.values.width.toString().indexOf('%') > -1) {
			$(domElement).attr("width",this.options.values.width);
			$(domElement).css("width",this.options.values.width);
		}
		else{
		    $(domElement).attr("width",this.options.values.width+ 'px');
		    $(domElement).css("width",this.options.values.width+ 'px');
		}
	}
	else{
		this.options.values.width='100%';
		$(domElement).attr("width",this.options.values.width);
		$(domElement).css("width",this.options.values.width);
	}
	//高度
	if (this.options.values.height){
		if(this.options.values.height.toString().indexOf('%') > -1) {
			$(domElement).attr("height",this.options.values.height);
			$(domElement).css("height",this.options.values.height);
		}
		else{
			$(domElement).attr("height",this.options.values.height+ 'px');
		    $(domElement).css("height",this.options.values.height+ 'px');
		}
	}
	else{
			this.options.values.height='';
			$(domElement).attr("height",'auto');
			$(domElement).css("height",'auto');
	}
	
	if (this.options.values.overflow){
		$(domElement).css("overflow",this.options.values.overflow);
		$(domElement).attr("overflow",this.options.values.overflow);
		if(this.children.length>0){
			for (let i = 0; i < this.children.length; i++) {
				if (this.children[i].type == "select"){
					// 如果是下拉框，就改为auto。因为之前下拉框没有宽度，所以这里是"visible"，现在加上宽度，历史数据还是visible，不会更新为auto，现在在循环中，使select的历史数据改为’auto‘
					$(domElement).css("overflow",'auto');
					$(domElement).attr("overflow",'auto');
				}
			}
		}
	}
	else{
		this.options.values.overflow='auto';
		$(domElement).css("overflow",'auto');
		$(domElement).attr("overflow",'auto');
	}
	if (this.options.values.borderSize){
		$(domElement).css("border-width",this.options.values.borderSize);
		$(domElement).attr("borderSize",this.options.values.borderSize);
	}
	else{
		this.options.values.borderSize='1px';
		$(domElement).css("border-width",'1px');
		$(domElement).attr("borderSize",'1px');
	}
	$(domElement).attr("id",this.options.values.id);
	
	var background = this.options.values.background;
	if(background){
		var groundStyle = '';
		if(background=="img"){
			var src = this.options.values.background_img_src;
			var showtype =  this.options.values.background_img_showtype;
			if(src && showtype){
				domElement.style.backgroundColor = '';
				domElement.style.backgroundImage = 'url('+src+')';
				groundStyle += 'background-image:url('+src+');';
				if(showtype=="cover"){
					domElement.style.backgroundRepeat = 'no-repeat';
					domElement.style.backgroundSize = '100% 100%';
					groundStyle += 'background-repeat:no-repeat;background-size:100% 100%;';
				}else{
					//repeat
					domElement.style.backgroundRepeat = 'repeat';
					groundStyle += 'background-repeat:repeat;';
				}
			}
		}else{
			if(this.options.values.background_color){
				domElement.style.backgroundColor = this.options.values.background_color;
				groundStyle += 'background-color:'+this.options.values.background_color+';';
			}
		}
		if(groundStyle){
			$(domElement).attr("groundStyle",groundStyle);
		}
	}
}
function _Designer_Control_DivControl_DrawXML() {
	if (this.children.length > 0) {
		var xmls = [];
		this.children = this.children.sort(Designer.SortControl);
		for (var i = 0, l = this.children.length; i < l; i ++) {
			var c = this.children[i];
			if (c.drawXML) {
				var xml = c.drawXML();
				if (xml != null)
					xmls.push(xml, '\r\n');
			}
		}
		return xmls.join('');
	}
	return '';
}

function _Designer_Control_DivControl_InsertValidate(cell, control){
	//父控件
	var parentControl = this.parent;
    var detailsTable = Designer.getClosestDetailsTable(this);
    if(detailsTable){
        //如果父控件是明细表，则判断插入的控件是否支持明细表
        if(control && control.implementDetailsTable != true){
            if  (detailsTable.isAdvancedDetailsTable) {
                alert(Designer_Lang.div_notSupportInsertToAdvancedListControl);
            } else {
                alert(Designer_Lang.div_notSupportInsertToListControl);
            }
            return false;
        }
    }
		
	//关联控件不支持插入
	if(control) {
        if (control.type == 'relevance') {
            alert(Designer_Lang.div_relevanceNotSupportInsertToDivControl);
            return false;
        }
        if (control.type == 'ocr') {
            alert(Designer_Lang.div_ocrNotSupportInsertToDivControl);
            return false;
        }
	}
	return true;
}

function Designer_Control_Divcontrol_Validator(elem, name, attr, value, values){
	if (value != null && value != '' && !(/^([+]?)(\d+)([%]?)$/.test(value.toString()))) {
		alert(Designer_Lang.divControlValHeight);
		return false;
	}
	return true;
}

function Designer_Control_Divcontrol_Checkout(msg, name, attr, value, values){
	if (value != null && value != '' && !(/^([+]?)(\d+)([%]?)$/.test(value.toString()))) {
		msg.push(Designer_Lang.GetMessage(Designer_Lang.divControlChkHeight,attr.text));
		return false;
	}
	return true;
}

function _Designer_Control_Divcontrol_OnInitialize(){
	if(Designer_Config.controls.divcontrol.mimeTypes == null){
		$.ajax(Com_Parameter.ContextPath + "sys/attachment/js/mime.jsp",{dataType:'json',
			data:"extFileNames=gif,jpg,jpeg,bmp,png,tif", type:'POST', async:false, success:function(res){
			if(res.status==1){
				Designer_Config.controls.divcontrol.mimeTypes = res.message.join(",");
			}else{
				Designer_Config.controls.divcontrol.mimeTypes = "*";
			}
		},error:function(xhr, status, errorInfo){
			Designer_Config.controls.divcontrol.mimeTypes = "*";
		}});
	}
}

function _Designer_Control_Div_Self_Background_Draw(name, attr,
		value, form, attrs, values, control){
	var val = value?value:'img';
	var html = '';
	html+='<label isfor="true"><input name="background" type="radio" value="img" '+(val=='img' ? "checked" : "")+' onclick="_Designer_Control_Div_Background_change(this);"/>'+Designer_Lang.div_background_image+'</label>&nbsp;&nbsp;&nbsp;&nbsp;';
	html+='<label isfor="true"><input name="background" type="radio" value="color" '+(val=='color' ? "checked" : "")+' onclick="_Designer_Control_Div_Background_change(this);"/>'+Designer_Lang.div_background_color+'</label>';
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}


function _Designer_Control_Div_Self_Background_Img_Draw(name, attr,
		value, form, attrs, values, control){
	value = value?value:'';
	var shoetype = values.background_img_showtype?values.background_img_showtype:'cover'
	var html = '<div style="margin-top:2px;">';
	html += '<input name="'+name+'" value="'+value+'" type="text" class="attr_td_text" style="width:78%;float:left" readonly />';
	var btnId = 'sys_xform_div_background_img_btn';
	html += '<div style="float:left;padding:0 0 0 0" class="btnopt" id="' + btnId + '" style="width:22px;"></div>';
	html += '</div>';
	html += '<select name="background_img_showtype" class="attr_td_select" style="width:50%">';
	html += '<option value="cover"'+(shoetype=='cover' ? "selected" : "")+'>'+Designer_Lang.div_background_cover+'</option>';
	html += '<option value="repeat"'+(shoetype=='repeat' ? "selected" : "")+'>'+Designer_Lang.div_background_repeat+'</option>';
	html += '</select>&nbsp;&nbsp;&nbsp;';
	html += '<input type="button" onclick="_Designer_Control_Div_Self_Background_Img_Destroy(this);" class="btnopt" style="width:50px;height:20px;" value="'+Designer_Lang.div_background_clean+'"/>';
	
	Designer_AttrPanel._changed = true;
	var attrDom = $(Designer.instance.attrPanel.domElement);
	setTimeout(function(){
		var swf = xform_div_background_img_createSwfUpload(btnId);
		//监听事件
		swf.on("fileQueued",xform_divcontrol_fileQueued);				// 文件加入队列	
		swf.on("error",xform_divcontrol_fileQueueError);						// 文件上传过程中错误监控		
		swf.on("uploadSuccess",xform_divcontrol_uploadSuccess);		// 文件上传成功
		$('.webuploader-pick',attrDom).addClass('btnopt');
	},30);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function xform_divcontrol_uploadSuccess(file, data){
	var input = $("input[name='background_img']")[0];
	Designer_AttrPanel.showButtons(input);
	var fileName = file.name;
	//文件名解码
	if(fileName.indexOf("base64:")==0){
		var unicode = fileName.substring(7);
		unicode = BASE64.decoder(unicode);
		fileName="";
		for(var i = 0 ; i<unicode.length ; i++){  
			fileName += String.fromCharCode(unicode[i]);  
		}
	}
	input.value = fileName;
	var url = Com_Parameter.ContextPath + 'resource/fckeditor/editor/filemanager/download' + '?fdId=' + data.filekey + '&picthumb=big';
	$("input[name='background_img_src']").val(url);
}

function xform_div_background_img_createSwfUpload(btnId){
	var self = this;
	var imageMaxSize = parseFloat(_image_max_size)*1024*1024;
	
	this.getHost = function() {
		var host = location.protocol.toLowerCase() + "//" + location.hostname;
		if (location.port != '' && location.port != '80') {
			host = host + ":" + location.port;
		}
		return host;
	}
	
	this.setting = {
		swf: self.getHost() + Com_Parameter.ContextPath + "sys/attachment/webuploader/uploader.swf",
		server: self.getHost() + Com_Parameter.ContextPath + 'resource/fckeditor/editor/filemanager/upload/simpleuploader?Type=Image&json=true',
		disableGlobalDnd:false,
		paste:document.body,						//document.body粘贴
		pick:{	//指定选择文件的按钮容器，不指定则不创建按钮。
			id:'#' + btnId,
			multiple:false,
			innerHTML:'...'
		},
		accept:{
			title:'请选择图片',					//文字描述
			extensions:'gif,jpg,jpeg,bmp,png,tif',				//允许的文件后缀，允许的文件后缀，不带点，多个用逗号分割。
			mimeTypes: Designer_Config.controls.divcontrol.mimeTypes
		},
		compress:false,					// 不允许压缩
		auto:true,
		runtimeOrder:'html5,flash',		// 默认值：html5,flash
		prepareNextFile:true,
		chunked:false,					// 切片
		chunkSize:5*1024*1024,			// 切片大小默认5m
		chunkRetry:2, 					// 切片失败尝试次数
		threads:3,						// 上传并发数
		formData:{},					// 文件上传请求的参数表，每次发送都会发送此对象中的参数
		fileVal:"__landray_file_" + btnId,		// 设置文件上传域的name。
		method:'POST',					// 文件上传方式，POST或者GET。
		sendAsBinary:false,				// 是否已二进制的流的方式发送文件，这样整个上传内容都为文件内容， 其他参数在$_GET数组中。
		fileNumLimit:100,				// 验证文件总数量, 超出则不允许加入队列。
		fileSizeLimit:imageMaxSize*100,			// 验证文件总大小是否超出限制, 超出则不允许加入队列。
		fileSingleSizeLimit:imageMaxSize, 	// 验证单个文件大小是否超出限制, 超出则不允许加入队列。
		duplicate:true					// 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
	};
	
	return WebUploader.create(self.setting);
}

function xform_divcontrol_fileQueued(file){
	this.upload();
}

//文件上传错误
function xform_divcontrol_fileQueueError(errorCode, info){
	if(errorCode=="Q_EXCEED_NUM_LIMIT"){//文件上传数超过限制
		xform_divcontrol_uploadFail(info, {
			status : '3'
		});
	}else if(errorCode=="Q_EXCEED_SIZE_LIMIT"){//超过大小限制
		xform_divcontrol_uploadFail(info, {
			status : '0'
		});
	}else if(errorCode=="Q_TYPE_DENIED"){//文件类型不匹配
		xform_divcontrol_uploadFail(info, {
			status : '4'
		});
	}
}

function xform_divcontrol_uploadFail(file, data){
	if (data.status == '0')
		alert('图片附件超过系统设定大小限制(' + _image_max_size + 'MB)');
	if (data.status == '3')
		alert('文件上传数超过限制！');
	if (data.status == '4')
		alert('图片上传的文件类型不匹配！');
}

function _Designer_Control_Div_Self_Background_Img_Destroy(dom){
	event.cancelBubble = true;
	if (event.stopPropagation) {event.stopPropagation();}
	var table = $(dom).closest("table");
	table.find("input[name='background_img']").val('');
	table.find("input[name='background_img_src']").val('');
	table.find("select[name='background_img_showtype']").val('cover');
	Designer_AttrPanel.showButtons(dom);
}

function _Designer_Control_Div_Background_change(dom) {
	var background = dom.value;
	var $color = Designer_Control_Attr_AcquireParentTr('background_color');	
	var $img = Designer_Control_Attr_AcquireParentTr('background_img');	
	if(background=="color"){
		$color == null ? '' : $color.show();	
		$img == null ? '' : $img.hide();
	}else{
		$color == null ? '' : $color.hide();
		$img == null ? '' : $img.show();
	}
}

function _Designer_Control_Div_OnAttrLoad(form,control){
	var $color = Designer_Control_Attr_AcquireParentTr('background_color');	
	var $img = Designer_Control_Attr_AcquireParentTr('background_img');	
	if(control.options.values.background=='color'){
		$img == null ? '' : $img.hide();
	}else{
		$color == null ? '' : $color.hide();
	}
}

Designer_Config.operations['divcontrol'] = {
		lab : "2",
		imgIndex : 28,
		title : Designer_Lang.div,
		run : function (designer) {
			designer.toolBar.selectButton('divcontrol');
		},
		type : 'cmd',
		group: 'layout',
		order: 10,
		select: true,
		cursorImg: 'style/cursor/div.cur'
		
	};

	Designer_Config.buttons.layout.push("divcontrol");

	Designer_Menus.layout.menu['divcontrol'] = Designer_Config.operations['divcontrol'];