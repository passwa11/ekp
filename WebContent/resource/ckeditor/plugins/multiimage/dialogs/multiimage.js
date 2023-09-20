CKEDITOR.dialog
		.add(
				'multiimage',
				function(editor) {

					var config = editor.config;
					var ckId = editor.id;

					var dialog;
					// 内容
					var html = [];
					html.push('<div class="lui-attachment-upload ">');
					html.push('<div class="article">');
					html.push('<div class="img" style="width: 98%">');
					html.push('<ul id="attachment_thumb" class="clearfloat">');
					html.push('<li data-lui-type="' + ckId
							+ '_upload" class="lui-attachment-upload-btn">');
					// 上传按钮
					html.push('<div class="uploadBtn" id="' + ckId + '">');
					html
							.push('<div><input type="file" name="NewFile" accept="image/*" multiple="multiple" style="display:none;"></div>');
					html.push('</div>');
					html.push('</li>');
					html.push('</ul>');
					html.push('<div id="' + ckId + '_attachment_tip">');
					html.push('</div>');
					html.push('</div>');
					html.push('</div>');
					html.push('</div>');

					var multiimageSelector = {
						type : 'html',
						id : 'multiimageSelector',
						html : html.join(''),
						onLoad : function(event) {

							dialog = event.sender;
						},
						style : 'width: 100%; border-collapse: separate;'
					};

					return {
						title : editor.lang.multiimage.uploadImgs,
						minWidth : 735,
						minHeight : 120,
						contents : [ {
							id : 'tab1',
							label : '',
							title : '',
							expand : true,
							padding : 0,
							elements : [ multiimageSelector ]
						} ],

						// 确定后插入到编辑器中
						onOk : function() {

							if (this.upload.uploadObj) {
								var info = this.upload.uploadObj.getStats();
								if (info.progressNum > 0) {
									alert(editor.lang.multiimage.uploading);
									return false;
								}
							}
							
							var orderObj = {};
							var idoms = $("#attachment_thumb").find("i[data-lui-id]").each(function(index, item) {
								var $this = $(item), id = $this.attr("data-lui-id");
								orderObj[id] = index;
							})
							
							var fileList = this.upload.fileList;
							
							this.upload.fileList.sort(function(a, b) {
								return orderObj[a.id] - orderObj[b.id];
							})
							
							for (var i = 0; i < fileList.length; i++) {
								var src = fileList[i]['url'];
								var img = editor.document.createElement('img',
										{
											attributes : {
												src : src,
												'data-cke-saved-src' : src
											}
										});
								editor.insertElement(img);
							}
						},

						// 重置附件内容
						onShow : function() {

							if (!this.loaded) {
								// 弹出框底部
								var footer = dialog.parts.footer.$;
								var msg = editor.lang.multiimage.pictureSizeInfo
										+ (config.imageMaxSize ? config.imageMaxSize
												: 5) + 'MB，';
								$(footer)
										.append(
												$('<div class="lui-attachment-limit-msg">'
														+ msg
														+ editor.lang.multiimage.extensionInfo
														+ CKconfig.ImageUploadAllowedExtensions
														+ '</div>'));
								this.loaded = true;
							}
							this.upload.reset();
						},

						onLoad : function() {

							this.upload = new MultiUpload(config
									.getFilebrowserImageUploadUrl(editor),
									ckId, config.downloadUrl,
									config.imageMaxSize, editor);
							this.upload.show();
							var self = this;
							this.upload.reset = function() {

								self.upload.fileList = [];

								var uploadNode = $('[data-lui-type="' + ckId
										+ '_upload"]');

								uploadNode.prevAll().remove();
								uploadNode.addClass(config.initClass);
							};
						}
					};
				});

function MultiUpload(uploadurl, ckId, downloadurl, imageMaxSize, editor) {

	var self = this;
	this.uploadurl = uploadurl;
	this.downloadurl = downloadurl;
	this.showed = false;
	this.tempUrl = Com_Parameter.ContextPath
			+ "resource/ckeditor/plugins/multiimage/dialogs/style/images/bj.png";
	this.imageMaxSize = imageMaxSize;
	this.editor = editor;
	// 初始化上传功能
	this.show = function() {

		if (self.showed)
			return;
		setTimeout(function() {

			self.draw();
			self.showed = true;
		}, 1);
	};

	// 绘画并初始化flash
	this.draw = function() {

		self.bindEvent();
		self.initUploadSetting();
		self.uploadObj = WebUploader.create(self.setting);
		self.initUploadEvent();
	};

	this.initUploadSetting = function() {

		var formatEnabledFileType = function() {

			var enabledFile = CKconfig.ImageUploadAllowedExtensions
			if (enabledFile == null || enabledFile == '')
				return null;
			enabledFile = enabledFile.replace(/\|/g, ",");
			enabledFile = enabledFile.replace(/\;/g, ",");
			enabledFile = enabledFile.replace(/[\.|\*]/g, "");
			return enabledFile;
		};

		self.setting.accept.title = "Picture Files";
		self.setting.id = ckId;
		var enableFiles = formatEnabledFileType();
		self.setting.accept.extensions = enableFiles;
		$.ajax(Com_Parameter.ContextPath + "sys/attachment/js/mime.jsp", {
			dataType : 'json',
			data : "extFileNames=" + enableFiles,
			type : 'POST',
			async : false,
			success : function(res) {

				if (res.status == 1) {
					self.setting.accept.mimeTypes = res.message.join(",");
				} else {
					self.setting.accept.mimeTypes = "*";
				}
			},
			error : function(xhr, status, errorInfo) {

				self.setting.accept.mimeTypes = "*";
				if (window.console)
					window.console.error(errorInfo);
			}
		});
		self.setting.pick.id = '#' + ckId;
		self.setting.pick.multiple = true;
		self.setting.fileSingleSizeLimit = parseFloat(self.imageMaxSize) * 1024 * 1024;
	};

	this.bindEvent = function() {

		$('#attachment_thumb').on('click', function(evt) {

			var target = evt.target;
			if (target && target.parentNode) {
				var $parent = $(target.parentNode);
				if ($parent.hasClass('lui-attachment-image')) {
					var selectedClass = "lui-attachment-image-selected";
					$('.lui-attachment-image').removeClass(selectedClass)
					$parent.addClass(selectedClass);
				}
			}
		});
		
		$("#attachment_thumb").sortable({ 
			opacity: 0.6, 
			cursor: 'move', 
			tolerance : 'pointer',
			items :'li.lui-attachment-image',
			containment : '#attachment_thumb',
			scroll:true,
			helper: 'clone',
			distance:10
		});
	};

	this.initUploadEvent = function() {

		if (self.uploadObj) {
			self.uploadObj.on("beforeFileQueued", self.initial); // 文件加入队列前
			self.uploadObj.on("fileQueued", self.fileQueued); // 文件加入队列
			// self.uploadObj.on("uploadBeforeSend",self.__uploadBeforeSend); //
			// 文件上传服务器时前
			self.uploadObj.on("uploadProgress", self.__uploadProgress); // 文件上传中
			self.uploadObj.on("uploadError", self.__uploadError); // 文件上传错误
			self.uploadObj.on("uploadSuccess", self.__uploadSuccess); // 文件上传成功
			self.uploadObj.on("error", self.__error); // 文件上传过程中错误监控
		}
	};

	this.getHost = function() {

		var host = location.protocol.toLowerCase() + "//" + location.hostname;
		if (location.port != '' && location.port != '80') {
			host = host + ":" + location.port;
		}
		return host;
	};

	this.initial = function() {
	
	};

	// 上传队列
	this.fileQueued = function(file) {

		self.addDoc(file);
	};

	this.__error = function(errorCode, info) {

		if (errorCode == "Q_EXCEED_NUM_LIMIT" || errorCode == "F_EXCEED_SIZE"
				|| errorCode == "Q_EXCEED_SIZE_LIMIT") {// 文件上传数超过限制
			self.__uploadFail(info, {
				status : '0'
			});
		} else if (errorCode == "Q_TYPE_DENIED") {// 文件类型不匹配
			self.__uploadFail(info, {
				status : '4'
			});
		}
	};

	this.fileDialogComplete = function() {

	};

	// 渲染默认图片
	this.__uploadStart = function(file) {

	};

	// 转圈圈
	this.__uploadProgress = function(file, loaded, total) {

		var id = file.id;
		var rate = loaded / total, className = '';
		if (rate >= 0.9)
			className = 'lui_attachment_rate9';
		else if (rate >= 0.8)
			className = 'lui_attachment_rate8';
		else if (rate >= 0.7)
			className = 'lui_attachment_rate7';
		else if (rate >= 0.6)
			className = 'lui_attachment_rate6';
		else if (rate >= 0.5)
			className = 'lui_attachment_rate5';
		else if (rate >= 0.4)
			className = 'lui_attachment_rate4';
		else if (rate >= 0.3)
			className = 'lui_attachment_rate3';
		else if (rate >= 0.2)
			className = 'lui_attachment_rate2';
		else
			return;
		$('#' + ckId).next().attr('class', className);
	};

	this.__uploadError = function() {

	};

	this.fileList = [];

	// 删除文档
	this.delDoc = function(id) {

		var fileList = self.fileList;
		for (var i = 0; i < fileList.length; i++) {
			if (fileList[i].id == id)
				fileList.splice(i, 1);
		}
	};

	// 根据id获取对应的附件信息
	this.getFileById = function(id) {

		var fileList = self.fileList;
		for (var i = 0; i < fileList.length; i++) {
			if (fileList[i].id == id)
				return fileList[i];
		}
	};

	this.buildImage = function(url, id) {

		var $rate = $('<div class="lui_attachment_rate1">');
		var $img = $('<img>').attr('src', url).attr('id', ckId + '_' + id), $li = $('<li class="lui-attachment-image"/>'), $i = $(
				'<i data-lui-id="' + id + '"/>').on('click', function(evt) {

			var $target = $(evt.target);
			var id = $target.attr('data-lui-id');
			self.delDoc(id);
			$target.parent().remove();
		});
		return $li.append($img).append($rate).append($i);
	};

	// 上传成功后构建图片缩略图
	this.__uploadSuccess = function(file, serverData) {

		var data = serverData;
		if (data.status != '1') {
			self.__uploadFail(file, data);
			return;
		}

		var type = ['gif', 'jpg', 'jpeg', 'bmp', 'png', 'GIF', 'JPG', 'JPEG', 'BMP', 'PNG'];
		var typeIndexOf = false;
		for(var i = 0; i < type.length; i++){
			if(type[i] == data.fileName){
				typeIndexOf = true;
				break;
			}
		}
		if (!typeIndexOf) {
			self.__uploadFail(file, data);
			return;
		}

		var id = this.options.id;

		var url = self.downloadurl + '?fdId=' + data.filekey + '&picthumb=big&isSupportDirect=false';
		var fileId = file.id;

		var imgNode = $('#' + id + "_" + fileId);
		imgNode.attr('src', url);
		imgNode.next().remove();

		self.fileList.push({
			id : fileId,
			url : url
		});
	};

	// 显示提示信息
	this.showTip = function(text) {

		if (self.isShowTip)
			return;
		self.isShowTip = true;
		var html = '<div class="lui-attachment-tip">';
		html += '<div class="lui-attachment-tip-r">';
		html += '<div class="lui-attachment-tip-c">';
		html += '<span class="lui-attachment-tip-text">' + text + '</span>';
		html += '</div></div></div>';
		var $html = $(html);
		$('#' + ckId + '_attachment_tip').append($html);
		setTimeout(function() {

			$html.animate({
				opacity : 0
			}, 500, function() {

				$html.remove();
				self.isShowTip = false;
			});
		}, 3000);
	};

	this.__uploadFail = function(file, data) {

		var fileId = file.id;

		$('#' + fileId).parents('.lui-attachment-image').remove();

		var lang = this.editor.lang.multiimage;
		if (data.status == '0')
			self.showTip(lang.error1);
		if (data.status == '3')
			self.showTip(lang.error2);
		if (data.status == '4')
			self.showTip(lang.error3);
	};

	// 上传图片
	this.addDoc = function(file) {

		var img = self.buildImage(self.tempUrl, file.id);
		$('[data-lui-type="' + ckId + '_upload"]').before(img);
	};

	this.setting = {
		swf : self.getHost() + Com_Parameter.ContextPath
				+ "sys/attachment/webuploader/uploader.swf",
		server : (self.uploadurl.substr(0,4)=='http'?(self.uploadurl):(self.getHost() + self.uploadurl)) + '&json=true',
		disableGlobalDnd : false,
		paste : document.body, // document.body粘贴
		accept : {
			title : '', // 文字描述
			extensions : '' // 允许的文件后缀，允许的文件后缀，不带点，多个用逗号分割。
		},
		pick : {
			id : '',
			multiple : false,
			name : "__landray_file_" + ckId // 修改组件产生的<input
		// type="flie">的name属性，防止与表单的字段冲突
		},
		thumb : {
			width : 110,
			height : 110,
			quality : 70, // 图片质量，只有type为`image/jpeg`的时候才有效
			allowMagnify : true, // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
			crop : true, // 是否允许裁剪
			type : 'image/jpeg' // 为空的话则保留原有图片格式,否则强制转换成指定的类型。
		},
		compress : false, // 不允许压缩
		/*
		 * compress:{ width:1600, height:1600, quality: 90, //
		 * 图片质量，只有type为`image/jpeg`的时候才有效。 allowMagnify: false, //
		 * 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false. crop: false, // 是否允许裁剪。
		 * preserveHeaders: true, // 是否保留头部meta信息。 noCompressIfLarger: false, //
		 * 如果发现压缩后文件大小比原来还大，则使用原来图片,此属性可能会影响图片自动纠正功能 compressSize: 0 //
		 * 单位字节，如果图片大小小于此值，不会采用压缩。 },
		 */
		auto : true,
		runtimeOrder : 'html5,flash', // 默认值：html5,flash
		prepareNextFile : true,
		chunked : false, // 切片
		chunkSize : 5 * 1024 * 1024, // 切片大小默认5m
		chunkRetry : 2, // 切片失败尝试次数
		threads : 3, // 上传并发数
		formData : {}, // 文件上传请求的参数表，每次发送都会发送此对象中的参数
		fileVal : "__landray_file_" + ckId, // 设置文件上传域的name。
		method : 'POST', // 文件上传方式，POST或者GET。
		sendAsBinary : false, // 是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容，
		// 其他参数在$_GET数组中。
		fileNumLimit : 100, // 验证文件总数量, 超出则不允许加入队列。
		fileSizeLimit : 10 * 1024 * 1024 * 1024, // 验证文件总大小是否超出限制,
		// 超出则不允许加入队列。
		fileSingleSizeLimit : 100 * 1024 * 2400, // 验证单个文件大小是否超出限制,
		// 超出则不允许加入队列。
		duplicate : true, // 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
		disableWidgets : undefined
	};
};
