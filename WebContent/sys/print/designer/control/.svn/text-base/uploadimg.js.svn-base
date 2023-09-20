(function(window, undefined){
	/**
	 * 标题图片控件
	 * @author linxiuxian
	 * @date 2016.5.9
	 **/
	 Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
	 Com_IncludeFile("base64.js",Com_Parameter.ContextPath + "sys/attachment/js/","js",true);
	 Com_IncludeFile("webuploader.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
	 
	 var uploadimgControl=sysPrintDesignerControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	var id=sysPrintUtil.generateID();
		 	var html = [];
		 	var imgsrc = Com_Parameter.ContextPath +"sys/print/designer/style/img/btn_img_bg.png";
		 	html.push('<div id="' + id + '" printcontrol="true" fd_type="uploadimg" style="width: 28px;display:inline-block;">');
		 	html.push('<img src="' + imgsrc + '" />');
		 	html.push('</div>');
		 	this.$domElement=$(html.join(''));
	 	},
	 	bind:function(){
	 		var self=this;
	 		this.$domElement.dblclick(function(){
	 			//设置当前ctrl数据
	 			var ctrl = self.builder.selectControl;
	 			var uploadDom=$(this);
	 			var values = ctrl.options.values || {};
	 			values.id=uploadDom[0].id;
	 			if(!values.imgName){
	 				values.imgName=uploadDom.attr('imgName');
	 				values.width=uploadDom.attr('_width');
	 				values.height=uploadDom.attr('_height');
	 			}
	 			ctrl.options.values=values;
	 			self.builder.attrPanelShow();
	 		});
	 	},
	 	info:{
	 		name:DesignerPrint_Lang.controlUploadName
	 	},
	 	reRender:reRender,//重渲染
	 	attrs:{
	 		url : {
				text: DesignerPrint_Lang.controlUploadUrl,
				value: "",
				type: 'self',
				draw: attrHtmlDraw,
				validator: sysPrintValidator.attrRequiredValidator,
				hint: '',
				show: true,
				required: true
			},
	 		width : {
				text: DesignerPrint_Lang.controlUploadWidth,
				value: "",
				type: 'self',
				draw: attrWidthHtmlDraw,
				validator: sysPrintValidator.attrIntValidator,
				show: true
			},
			height : {
				text: DesignerPrint_Lang.controlUploadHeight,
				value: "",
				type: 'self',
				draw: attrHeightHtmlDraw,
				validator: sysPrintValidator.attrIntValidator,
				show: true
			}
			
	 	}
	 });

	 //属性设置后重绘
	 function reRender(){
		var values = this.options.values;
		if(values.url){
			 var $img = this.$domElement.find('img');
			 var width = values.width>0 ? values.width:values.originalWidth;
			 var height = values.height>0 ? values.height:values.originalWidth;
			//设置当前control属性到对应dom
			 this.$domElement.attr("_width",width).attr("_height",height)
			 				 .attr("_originalWidth",values.originalWidth)
			 				 .attr("_originalHeight",values.originalHeight)
			 				 .attr('imgName',values.imgName);
			 this.$domElement.css('width',width);
			 $img.attr('src',values.src).attr('imgName',values.imgName)
			 	 .attr('width',width).attr('height',height);
			//增加对撤销功能的支持
			if(typeof (sysPrintUndoSupport)  != 'undefined'){
				sysPrintUndoSupport.saveOperation();
			}
		}
	 }
	 //自定义属性框绘画
	 function attrHtmlDraw(name, attr, value, form, attrs, values,control){
		 	var $domElement = control.$domElement;
		 	var img = control.$domElement[0].firstChild;
			var imgName = img.getAttribute('imgName') || $domElement.attr('imgName');
			if(!imgName){
				imgName = '';
			}
			setTimeout(function(){
				var swf = createSwfUpload(values.id);
				control.swfUpload = swf;
				initUploadEvent(swf);//事件初始化
				$('.webuploader-pick').addClass('btnopt');
			},100);
			var _html = [];
			_html.push("<div style='margin-top:2px;'><input type='text' readonly='true' style='width:79%;float:left;' class='attr_td_text' value='"+imgName+"' name='" + name + "'/>");
			_html.push("<div style='width:22px;height:18px;float:left;padding:0px;' id='" + values.id+ "_filePicker' >");
			_html.push("</div></div>");
			sysPrintAttrPanel._changed = true;
			return sysPrintAttrPanel.wrapTitle(name, attr, value, _html.join(""));
	 }
	 function attrWidthHtmlDraw(name, attr, value, form, attrs, values,control){
		 	var $domElement = control.$domElement;
		 	var img = control.$domElement[0].firstChild;
			var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;	
			var imgName = img.getAttribute('imgName') || $domElement.attr('imgName');
			if(img.clientWidth != '0' && img.clientWidth != null && imgName != null ){
				html += "' value='" + img.clientWidth;
			}else {
				html += "' value='" + attr.value;
			}

			html += "' onKeyUp='sysPrintUploadimgControl.autoResizeImage(1,this.value)";
			html += "'>";
			var imgsrc = Com_Parameter.ContextPath +"sys/print/designer/style/img/lock.png";
			var locked = "<img src='" + imgsrc + "' name='locked' title='" + DesignerPrint_Lang.controlUploadWHLock +"' style='cursor:pointer;width:16px;height:16px' checked='true' onclick='sysPrintUploadimgControl.changeLocked(this)'/>";
			html += locked;
			return sysPrintAttrPanel.wrapTitle(name, attr, value, html);
	 }
	 function attrHeightHtmlDraw(name, attr, value, form, attrs, values,control){
		 	var $domElement = control.$domElement;
		 	var img = control.$domElement[0].firstChild;
			var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;
			var imgName = img.getAttribute('imgName')|| $domElement.attr('imgName');
			if(img.clientHeight != '0' && img.clientHeight != null && imgName != null){
				html += "' value='" + img.clientHeight;
			}else {
				html += "' value='" + attr.value;
			}
			html += "' onKeyUp='sysPrintUploadimgControl.autoResizeImage(2,this.value)";
			html += "'>";
			return sysPrintAttrPanel.wrapTitle(name, attr, value, html);
	 }
	 
	 function getUploaderConfig(btnId){
		 var imageMaxSize = parseFloat(_image_max_size)*1024*1024;
		 var config = {
				 	swf: getHost() + Com_Parameter.ContextPath + "sys/attachment/webuploader/uploader.swf",
					server: getHost() + Com_Parameter.ContextPath + "resource/fckeditor/editor/filemanager/upload/simpleuploader?Type=Image&json=true",
					disableGlobalDnd:false,
					paste:null,								//document.body粘贴
					accept:{
						title:DesignerPrint_Lang.controlUploadSelect,					//文字描述
						extensions:'gif,jpg,jpeg,bmp,png',	//允许的文件后缀，允许的文件后缀，不带点，多个用逗号分割。
						mimeTypes: 'image/jpeg,image/png,image/gif,image/bmp'
					},
					pick:{
						id:'#'+btnId +'_filePicker',
						multiple:false,
						innerHTML:'...',
						name : "__landray_file_"+btnId      //修改组件产生的<input type="flie">的name属性，防止与表单的字段冲突
					},
					thumb:{
						width:110,
						height:110,
						quality:70,  				// 图片质量，只有type为`image/jpeg`的时候才有效
						allowMagnify: true,			// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
						crop: true,					// 是否允许裁剪
						type: 'image/jpeg'			// 为空的话则保留原有图片格式,否则强制转换成指定的类型。
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
					fileVal:"__landray_file_"+btnId,		// 设置文件上传域的name。
					method:'POST',					// 文件上传方式，POST或者GET。
					sendAsBinary:false,				// 是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容， 其他参数在$_GET数组中。
					fileNumLimit:100,				// 验证文件总数量, 超出则不允许加入队列。
					fileSizeLimit:100*imageMaxSize,			// 验证文件总大小是否超出限制, 超出则不允许加入队列。
					fileSingleSizeLimit:imageMaxSize, 	// 验证单个文件大小是否超出限制, 超出则不允许加入队列。
					duplicate:true,					// 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
					disableWidgets:undefined
		 };
		 return config;
	 }

	 function initUploadEvent(uploadObj){
		 if(uploadObj){
				//self.uploadObj.on("beforeFileQueued",_beforeFileQueued);	// 文件加入队列前
			 	uploadObj.on("fileQueued",_fileQueued);				// 文件加入队列
				//uploadObj.on("uploadBeforeSend",_uploadBeforeSend);	// 文件上传服务器时前
				uploadObj.on("uploadProgress",_uploadProgress);		// 文件上传中		
				uploadObj.on("uploadError",_fileQueueError);			// 文件上传错误
				uploadObj.on("uploadSuccess",_uploadSuccess);		// 文件上传成功
				uploadObj.on("uploadComplete",_uploadComplete);
				uploadObj.on("error",_error);				// 文件上传过程中错误监控
		}
	 }
	 function createSwfUpload(btnId){
		 var uploader = WebUploader.create(getUploaderConfig(btnId));
		 return uploader;
	 }
	 function getHost() {
			var host = location.protocol.toLowerCase() + "//" + location.hostname;
			if (location.port != '' && location.port != '80') {
				host = host + ":" + location.port;
			}
			return host;
	}
	 function _fileQueued(file){
		this.upload();
	 }
	 function _uploadSuccess(file, serverData, responseReceived){
			var pickId = this.options.pick.id;
			var $tdDom = $(pickId).parent().parent();
			var $input = $tdDom.find('input[name=url]');
			//sysPrintAttrPanel.showButtons(input);//注意该方法
			sysPrintDesigner.instance.attrPanel.showBottomBar();
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
			data = serverData;
			var url = Com_Parameter.ContextPath + 'resource/fckeditor/editor/filemanager/download' + '?fdId=' + data.filekey + '&picthumb=big';
			
			//判断原本图片的宽度是否大于系统设定的宽度
			if( data.width > _image_bigImage_width){
				var radio = _image_bigImage_width / data.width;
				data.width = _image_bigImage_width;
				data.height = parseInt(data.height * radio);
				//alert("您上传的图片宽度超过系统设置的宽度限制("+_image_bigImage_width+"px)，现已对该图片等比压缩");
			}
			var values = sysPrintDesigner.instance.attrPanel.control.options.values;
			//设置属性
			var $domElement = $(sysPrintDesigner.instance.attrPanel.domElement);
			$domElement.find("input[name='width']").val(data.width);
			$domElement.find("input[name='height']").val(data.height);
			$input.val(fileName);
			if(!data.width && !data.height){
				alert(DesignerPrint_Lang.controlUploadGetSizeAlert);
			}
			values.width = data.width;
			values.height = data.height;
			//原始的宽高，用于尺寸还原
			values.originalWidth = data.width;
			values.originalHeight = data.height; 
			values.src = url;
			values.imgName = fileName;
	 }
	 function _fileQueueError(file, errorCode, message){
		 //debugger;
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			uploadFail(file, {
				status : '0'
			});
			break;
		}
	}
	function _uploadProgress(){
	}
	function _uploadComplete(){
	}
	function _error(type, errorCode, file){
		//debugger;
		switch (type) {
		case 'F_EXCEED_SIZE':
			uploadFail(file, {
				status : '0'
			});
			break;
		}
	}
	 function uploadFail(file, data){
		if (data.status == '0')
			alert(DesignerPrint_Lang.controlUploadMaxSizeAlert +_image_max_size +"MB");
		if (data.status == '3')
			alert(DesignerPrint_Lang.controlUploadUploadingErrAlert);
		if (data.status == '4')
			alert(DesignerPrint_Lang.controlUploadUploadingConfigErrAlert);
	}
	 //静态方法
	uploadimgControl.changeLocked = function(object){
		var type = object.getAttribute("checked");
		if(type == 'true'){
			object.setAttribute("checked","false");
			object.setAttribute("src",object.src.replace("lock.png","lock-open.png"));
		}else if(type == 'false'){
			object.setAttribute("checked","true");
			object.setAttribute("src",object.src.replace("lock-open.png","lock.png"));
		}
	};
	uploadimgControl.autoResizeImage = function(type,value){	
		//1为宽度，2为高度
		var checked = $('#sysPrintdesignPanel').find("img[name='locked']")[0].getAttribute("checked");//是否设置为等比压缩
		var values = sysPrintDesigner.instance.attrPanel.control.options.values;
		var width = values.width || values.originalWidth;
		var height = values.height || values.originalHeight;
		var oriWith = values.originalWidth || values.width;
		var oriHeight = values.originalHeight || values.height;

		if(oriWith && oriHeight){
			if(checked == 'true'){
				if(type == '1'){	
					//判断输入宽度是否大于系统设定宽度，大于则返回原本的宽度
					if( value > _image_bigImage_width){
						alert(DesignerPrint_Lang.controlUploadInputMaxWidthAlert + '('+_image_bigImage_width+'px)');
						$('#sysPrintdesignPanel').find("input[name='width']").val(width);
						return;
					}
					var changedHeight;
					if(value == null || value == ''){
						changedHeight = 0;
					}else{
						var wRatio = parseInt(value) / oriWith.valueOf();				
						changedHeight = parseInt(oriHeight * wRatio);
					}
					$('#sysPrintdesignPanel').find("input[name='height']").val(changedHeight);
				}
				if(type == '2'){
					var changedWidth;
					if(value == null || value == ''){
						changedWidth = 0;
					}else{
						var hRatio = parseInt(value) / oriHeight.valueOf();				
						changedWidth = parseInt(oriWith * hRatio);
					}
					if(changedWidth > _image_bigImage_width){
						alert(DesignerPrint_Lang.controlUploadOverMaxWidthAlert + '('+_image_bigImage_width+'px)');
						$('#sysPrintdesignPanel').find("input[name='height']").val(height);
						return;
					}
					$('#sysPrintdesignPanel').find("input[name='width']").val(changedWidth);
				}
			}
		 }
	}
	
	//对外暴露
	window.sysPrintUploadimgControl=uploadimgControl;
	
})(window);