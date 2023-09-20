/**********************************************************
功能：图片上传
使用：
	
作者：朱国荣
创建时间：2016-4-11
**********************************************************/
document.write('<script>Com_IncludeFile("base64.js","../sys/attachment/js/");</script>');
Com_IncludeFile("webuploader.min.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
Designer_Config.controls['uploadimg'] = {
		type : "uploadimg",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_Uploadimg_OnDraw,
		onDrawEnd : _Designer_Control_Uploadimg_OnDrawEnd,
		drawXML : _Designer_Control_Uploadimg_DrawXML,
		drawMobile : _Designer_Control_Uploadimg_DrawMobile,
		onInitialize : _Designer_Control_Uploadimg_OnInitialize,
		implementDetailsTable : true,
		mobileAlign : "right",
		info : {
			name: Designer_Lang.controlUploadImg_info_name
		},
		resizeMode : 'all',
		attrs : {
			url : {
				text: Designer_Lang.controlUploadImg_attr_url,
				value: "",
				type: 'self',
				draw: _Designer_Control_Attr_Url_Self_Draw,
				show: true,
				required: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout
			},
			src:{
				text:'图片的src',
				type:'hidden',
				skipLogChange:true,
				show:true
			},
			origWidth:{
				text:'上传图片的原始宽度',
				type:'hidden',
				skipLogChange:true,
				show:true
			},
			origHeight:{
				text:'上传图片的原始高度',
				type:'hidden',
				skipLogChange:true,
				show:true
			},
			width : {
				text: Designer_Lang.controlUploadImg_attr_width,
				value: "",
				type: 'self',
				draw: _Designer_Control_Attr_Width_Self_Draw,
				show: true,
				validator: Designer_Control_Attr_Int_Validator,
				checkout: Designer_Control_Attr_Int_Checkout
			},
			height : {
				text: Designer_Lang.controlUploadImg_attr_height,
				value: "",
				type: 'self',
				draw: _Designer_Control_Attr_Height_Self_Draw,
				show: true,
				validator: Designer_Control_Attr_Int_Validator,
				checkout: Designer_Control_Attr_Int_Checkout
			}		
		},
		mimeTypes:null
};

Designer_Config.operations['uploadimg'] = {
		lab : "2",
		imgIndex : 33,
		title : Designer_Lang.controlUploadImg_tittle,
		run : function (designer) {
			designer.toolBar.selectButton('uploadimg');
		},
		type : 'cmd',
		order: 15,
		select: true,
		cursorImg: 'style/cursor/uploadimg.cur'
	};

Designer_Config.buttons.layout.push("uploadimg");

Designer_Menus.layout.menu['uploadimg'] = Designer_Config.operations['uploadimg'];

var oldWidth;		//记录未更改宽度，当输入宽度大于设定宽度时返回该值
var oldHeight;		////记录未更改高度，当输入高度导致宽度大于设定宽度时返回该值

function _Designer_Control_Attr_Width_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;
	setTimeout(function(){
		$("input[name='src']").val(values.src);
		$("input[name='origWidth']").val(values.origWidth);
		$("input[name='origHeight']").val(values.origHeight);
	},0);
	
	value = typeof(value) == 'undefined' ? '' : value;
	html += "' value='" + value;	
	//每次打开属性框，记录原始宽度，用于输入数值失效时返回该数据
	oldWidth = value;
	html += "' onKeyUp='xform_uploadimg_autoResizeImageWrap(1,this.value)";//onpropertychange只支持ie
	html += "'>";
	var locked = "<img src='style/img/lock.png' name='locked' title='锁定比例' style='cursor:pointer;width:16px;height:16px' checked='true' onclick='xform_uploadimg_changeLocked(this)'/>";
	var refresh = "<img src='style/img/refresh.png' title='原始尺寸' style='cursor:pointer;width:16px;height:16px' onclick='xform_uploadimg_refreshWHWrap()' />";
	html += locked;
	html += refresh;
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//重置图片外层
function xform_uploadimg_refreshWHWrap(){
	var origWidth = $("input[name='origWidth']").val();
	var origHeight = $("input[name='origHeight']").val();
	if(origWidth == '' && origHeight == '' ){
		xform_uploadimg_refreshWH();
	}else{
		xform_uploadimg_refreshWH(origWidth,origHeight);
	}
	
}

//重置图片宽度为原本的宽度
function xform_uploadimg_refreshWH(origWidth,origHeight){
	if(origWidth && origWidth != null && origHeight && origHeight != null){
		$("input[name='height']")[0].setAttribute("value",origHeight);
		$("input[name='width']")[0].setAttribute("value",origWidth);
	}else{
		return;
	}
	//更改宽度、高度时，底部三个按钮应该显示出来
	Designer_AttrPanel._changed = true;
	Designer_AttrPanel.showButtons($("input[name='height']")[0]);
}

//等比压缩标志更改
function xform_uploadimg_changeLocked(object){
	var type = object.getAttribute("checked");
	if(type == 'true'){
		object.setAttribute("checked","false");
		object.setAttribute("src","style/img/lock-open.png");
	}else if(type == 'false'){
		object.setAttribute("checked","true");
		object.setAttribute("src","style/img/lock.png");
	}
}

//等比压缩外层
function xform_uploadimg_autoResizeImageWrap(type,value){
	var origWidth = $("input[name='origWidth']").val();
	var origHeight = $("input[name='origHeight']").val();
	if(origWidth == '' && origHeight == '' ){
		xform_uploadimg_autoResizeImage(type,value);
	}else{
		xform_uploadimg_autoResizeImage(type,value,origWidth,origHeight);
	}
	
}

//等比压缩
function xform_uploadimg_autoResizeImage(type,value,origWidth,origHeight){	
	//1为宽度，2为高度
	var checked = $("img[name='locked']")[0].getAttribute("checked");//是否设置为等比压缩
	//首次origWidth/oldHeight没有值	
	if(oldWidth == null || oldHeight == null){
		oldWidth = origWidth;
		oldHeight = origHeight;
	}
	if(origWidth && origWidth != null && origHeight && origHeight != null){
		if(checked == 'true'){
			if(type == '1'){	
				//判断输入宽度是否大于系统设定宽度，大于则返回原本的宽度
				if( value > _image_bigImage_width){
					alert('输入的宽度大于系统配置的宽度限制('+_image_bigImage_width+'px)');
					$("input[name='width']")[0].setAttribute("value",oldWidth);
					return;
				}
				var changedHeight;
				if(value == null || value == ''){
					changedHeight = 0;
				}else{
					var wRatio = parseInt(value) / origWidth.valueOf();				
					changedHeight = parseInt(origHeight * wRatio);
				}
				//当输入宽度没有大于系统设定宽度时，更新原本宽度，用于下次赋值
				oldWidth = value;
				$("input[name='height']")[0].setAttribute("value",changedHeight);
			}
			if(type == '2'){
				var changedWidth;
				if(value == null || value == ''){
					changedWidth = 0;
				}else{
					var hRatio = parseInt(value) / origHeight.valueOf();				
					changedWidth = parseInt(origWidth * hRatio);
				}
				if(changedWidth > _image_bigImage_width){
					alert('在锁定比例情况下，输入的高度导致图片宽度大于系统配置的宽度限制('+_image_bigImage_width+'px)');
					$("input[name='height']")[0].setAttribute("value",oldHeight);
					return;
				}
				oldHeight = value;
				$("input[name='width']")[0].setAttribute("value",changedWidth);
			}
		}
	}
	
}

function _Designer_Control_Attr_Height_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;
	value = typeof(value) == 'undefined' ? '' : value;
	html += "' value='" + value;	
	oldHeight = value;

	html += "' onKeyUp='xform_uploadimg_autoResizeImageWrap(2,this.value)";//onpropertychange只支持ie
	
	html += "'>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_Attr_Url_Self_Draw(name, attr, value, form, attrs, values,control){
	var imgName = values.url;
	if(imgName == null ){
		imgName = '';
	}
	var html = "<div style='margin-top:2px;'><input type='text' readonly='true' style='width:79%;float:left' class='attr_td_text' value='"+imgName+"' name='" + name + "'/>";
	var btnId = 'sys_xform_uploadimg_btn';
	html += "<div style='float:left;padding:0 0 0 0' class='btnopt' id='" + btnId + "' style='width:22px;'></div>";
	html += "</div>";
	Designer_AttrPanel._changed = true;
	var attrDom = $(Designer.instance.attrPanel.domElement);
	setTimeout(function(){
		var swf = xform_uploadimg_createSwfUpload(btnId);
		//监听事件
		swf.on("fileQueued",xform_uploadimg_fileQueued);				// 文件加入队列	
		swf.on("error",xform_uploadimg_fileQueueError);						// 文件上传过程中错误监控		
		swf.on("uploadSuccess",xform_uploadimg_uploadSuccess);		// 文件上传成功
		$('.webuploader-pick',attrDom).addClass('btnopt');
	},30);
	
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//_image_max_size在designPanel.jsp里面有定义
function xform_uploadimg_createSwfUpload(btnId){
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
			mimeTypes: Designer_Config.controls.uploadimg.mimeTypes
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

function xform_uploadimg_uploadFail(file, data){
	if (data.status == '0')
		alert('图片附件超过系统设定大小限制(' + _image_max_size + 'MB)');
	if (data.status == '3')
		alert('文件上传数超过限制！');
	if (data.status == '4')
		alert('图片上传的文件类型不匹配！');
}

//文件上传错误
function xform_uploadimg_fileQueueError(errorCode, info){
	if(errorCode=="Q_EXCEED_NUM_LIMIT"){//文件上传数超过限制
		xform_uploadimg_uploadFail(info, {
			status : '3'
		});
	}else if(errorCode=="Q_EXCEED_SIZE_LIMIT" || errorCode == 'F_EXCEED_SIZE'){//超过大小限制
		xform_uploadimg_uploadFail(info, {
			status : '0'
		});
	}else if(errorCode=="Q_TYPE_DENIED"){//文件类型不匹配
		xform_uploadimg_uploadFail(info, {
			status : '4'
		});
	}
}

function xform_uploadimg_fileQueued(file){
	this.upload();
}

//上传成功
function xform_uploadimg_uploadSuccess(file, data){
	var input = $("input[name='url']")[0];
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
	if(data.width == '' && data.height == ''){
		alert('系统无法获取到该图片的宽高，请手动设置宽高！');
	}
	//判断原本图片的宽度是否大于系统设定的宽度
	if( data.width > _image_bigImage_width){
		var radio = _image_bigImage_width / data.width;
		data.width = _image_bigImage_width;
		data.height = parseInt(data.height * radio);
	}
	//设置初始的长宽
	$("input[name='width']")[0].setAttribute("value",data.width);
	$("input[name='height']")[0].setAttribute("value",data.height);
	$("input[name='origWidth']")[0].setAttribute("value",data.width);
	$("input[name='origHeight']")[0].setAttribute("value",data.height);
	$("input[name='src']")[0].setAttribute("value",url);	
}

function _Designer_Control_Uploadimg_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	//domElement.style.display = 'inline-block';
	var img = document.createElement("img");
	img.setAttribute("id", "img_"+domElement.id);
	img.style.display = 'none';
	img.style.maxWidth = '100%';
	if(this.options.values.src){
		//已选的图片
		img.style.display = 'inline-block';
		_Designer_Control_UploadImg_SetAttribute(img, this.options.values.src, "src");
	}else{			
		//默认图片
		domElement.style.width='27px';
		var div = document.createElement("div");
		html = "<img src='style/img/btn_img_bg.png' />";
		div.innerHTML = html;
		domElement.appendChild(div);
	}
	if(this.options.values.width){
		_Designer_Control_UploadImg_SetAttribute(img, this.options.values.width, "width");
	}
	if(this.options.values.height){
		_Designer_Control_UploadImg_SetAttribute(img, this.options.values.height, "height");
	}
	domElement.appendChild(img);
}

function _Designer_Control_Uploadimg_OnInitialize(){
	if(Designer_Config.controls.uploadimg.mimeTypes == null){
		$.ajax(Com_Parameter.ContextPath + "sys/attachment/js/mime.jsp",{dataType:'json',
			data:"extFileNames=gif,jpg,jpeg,bmp,png,tif", type:'POST', async:false, success:function(res){
			if(res.status==1){
				Designer_Config.controls.uploadimg.mimeTypes = res.message.join(",");
			}else{
				Designer_Config.controls.uploadimg.mimeTypes = "*";
			}
		},error:function(xhr, status, errorInfo){
			Designer_Config.controls.uploadimg.mimeTypes = "*";
		}});
	}
}

function _Designer_Control_UploadImg_SetAttribute(domElement, targetValue, attrName){
	if(targetValue==null || targetValue=='') {
		domElement.setAttribute(attrName,null);
	} else {
		domElement.setAttribute(attrName,targetValue);
	}
}

function _Designer_Control_Uploadimg_OnDrawEnd(){
	
}

//暂时只用于新增、删除图片上传控件的时候，提交显示是否保存为新版本
function _Designer_Control_Uploadimg_DrawXML(){
	var values = this.options.values;
	var buf = ['<attachmentProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('imgWidth="',values.width,'" ');
	buf.push('imgHeight="',values.height,'" ');
	buf.push('imgName="',Designer.HtmlEscape(values.url),'" ');
	buf.push('type="','uploadimg','" ');
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_Uploadimg_Destroy(){
	var domElement = this.options.domElement;
	var div = document.getElementById(domElement.id);
	if (div != null) {
		div.parentNode.removeChild(div);
	}
	this._destroy();

}