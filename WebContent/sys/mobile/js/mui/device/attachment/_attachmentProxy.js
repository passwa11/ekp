/**
 * 代理上传插件
 */

define(
		[
				"dojo/_base/declare",
				"dojo/request",
				"dojo/topic",
				"mui/dialog/Tip",
				"dojo/_base/lang",
				"mui/util",
				"mui/base64",
				"./_md5FileMixin" ],
		function(declare, request, topic, Tip, lang, util,base64, _md5FileMixin) {

			return declare(
					"mui.device.attachment._attachmentProxy",
					[ _md5FileMixin ],
					{

						rawFilePath : '',

						fdKey : '',

						fdModelName : '',

						fdModelId : '',

						fdMulti : false,

						// 附件类型 office,pic,byte
						fdAttType : 'byte',

						// 附件显示样式 office,pic,link
						fdViewType : 'byte',

						// 附件处理状态
						editMode : 'edit',

						// 上传类型
						uploadStream : false,

						// 事件前缀
						eventPrefix : "attachmentObject_",

						// 获取上传token
						tokenurl : util
								.formatUrl(
										"/sys/attachment/sys_att_main/sysAttMain.do?"
												+ "method=handleAttUpload&gettype=getuserkey&format=json"
												+ "&isSupportDirect=false",
										true),

						// 注册附件信息
						attachurl : util
								.formatUrl(
										"/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=submit&format=json",
										true),
						// 上传附件信息
						uploadurl : util
								.formatUrl(
										"/sys/attachment/uploaderServlet?gettype=upload&format=json",
										true),
						// 上传图片base64附件信息
						uploadStreamUrl : util
								.formatUrl(
										"/sys/attachment/uploaderServlet?gettype=uploadStream&type=pic&format=json",
										true),

						deleteUrl : util
								.formatUrl(
										"/sys/attachment/sys_att_main/sysAttMain.do?method=delete&format=json",
										true),
						// md5秒传验证
						md5Url : util
								.formatUrl(
										"/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=checkMd5&format=json",
										true),

						uploadType : 1, // 值：1 附件机制上传 2 RTF图片上传

						rtfUploadUrl : util
								.formatUrl(
										"/resource/fckeditor/editor/filemanager/upload/simpleuploader?Type=Image",
										true),

						downloadUrl : util
								.formatUrl('/resource/fckeditor/editor/filemanager/download'),

						FILE_EXT_PIC : "Picture Files|*.gif;*.jpg;*.jpeg;*.bmp;*.png;*.tif|All Files (*.*)|*.*",

						constructor : function(options) {

							this.init(options);
						},

						init : function(options) {// 设置属性

							this.fdKey = options.fdKey;
							this.fdModelName = options.fdModelName;
							this.fdModelId = options.fdModelId;
							this.fdMulti = options.fdMulti;
							this.fdAttType = options.fdAttType;
							this.fdSmallMaxSize = options.fdSmallMaxSize;
							this.fdImageMaxSize = options.fdImageMaxSize;
							this.fdViewType = this.fdAttType;
							if (options.uploadType)
								this.uploadType = options.uploadType;
							this.editMode = options.editMode;
							this.uploadStream = options.uploadStream;
							// 属性间逻辑处理
							if (this.fdKey == null || this.fdKey == '') {
								if (window.console)
									window.console.error("附件机制错误:fdKey信息为空!");
								return;
							}
							if (this.uploadType == 1) {
								this.fileLimitType = options.fileLimitType;
								this.fdDisabledFileType = options.fdDisabledFileType;
								this.fdSmallMaxSize = options.fdSmallMaxSize;
								if (options.extParam) {
									this.extParam = new String(options.extParam);
								}
								if (this.fdModelName == null
										|| this.fdModelName == '') {
									if (window.console)
										window.console
												.error("附件机制错误:fdModelName信息为空!");
									return;
								}
							} else if (this.uploadType == 2) {
								if (this.fdModelName)
									this.rtfUploadUrl = util.setUrlParameter(
											this.rtfUploadUrl, "fdModelName",
											this.fdModelName);
								if (this.fdModelId)
									this.rtfUploadUrl = util.setUrlParameter(
											this.rtfUploadUrl, "fdModelId",
											this.fdModelId);
							}
							this.eventPrefix = this.eventPrefix + this.fdKey
									+ "_";
							this.UPLOAD_EVENT_START = this.eventPrefix
									+ "start";
							this.UPLOAD_EVENT_SUCCESS = this.eventPrefix
									+ "success";
							this.UPLOAD_EVENT_FAIL = this.eventPrefix + "fail";
							this.UPLOAD_EVENT_PROCESS = this.eventPrefix
									+ "process";
							this.UPLOAD_EVENT_SHARE = this.eventPrefix
								+ "share";

						},

						// 生成唯一标示
						guid : (function() {

							var counter = 0;
							return function(prefix) {

								var guid = (+new Date().getTime()).toString(32), i = 0;
								for (; i < 5; i++) {
									guid += Math.floor(Math.random() * 65535)
											.toString(32);
								}
								return (prefix || 'mobile_') + guid
										+ (counter++).toString(32);
							};
						})(),

						// 是否为禁止文件
						isTypeDisable : function(file) {

							if(file.name && typeof(this.fdDisabledFileType) != "undefined"){
								var fileType = null;
								var _fileName = file.name;
								fileType = _fileName.substring(_fileName.lastIndexOf("."));
								fileType = fileType.toLowerCase();
								var fileTypes= new Array();
								fileTypes = this.fdDisabledFileType.split(';');
								if("1"==this.fileLimitType){
									
									var isPass = true;
									
									for (i=0;i<fileTypes.length ;i++ )
									{
										if(fileType == fileTypes[i]){
											isPass = false;
											break;
										}
									}
									
									if(!isPass){
										Tip.tip({text:'系统不允许上传“'+this.fdDisabledFileType+'”类型附件.'});
										return true;
									}
									
								}else if("2"==this.fileLimitType){

									var isPass = false;
									
									for (i=0;i<fileTypes.length ;i++ )
									{
										if(fileType == fileTypes[i]){
											isPass = true;
											break;
										}
									}
									
									if(!isPass){
										Tip.tip({text:'系统只允许上传“'+this.fdDisabledFileType+'”类型附件.'});
										return true;
									}										
								}
							}

							return false;

						},

						// 是否超过文件大小限制
						isSizeDisable : function(file) {

							if (null != this.fdSmallMaxSize
									&& "pic" != this.fdAttType) {
								if (file.size > (this.fdSmallMaxSize * 1024 * 1024)) {
									Tip.tip({
										text : '单个附件上传最大不超过'
												+ this.fdSmallMaxSize
												+ 'MB,请重新选择文件'
									});
									return true;
								}
							}

							return false;

						},

						// 该上传图片是否合规
						isPicDisable : function(file) {

							var _fileName = file.name;

							if ("pic" == this.fdAttType) {
								if (_fileName.lastIndexOf(".") > -1) {
									var fileType = _fileName
											.substring(_fileName
													.lastIndexOf("."));
									fileType = fileType.toLowerCase();
									if (".gif" != fileType
											&& ".jpg" != fileType
											&& ".jpeg" != fileType
											&& ".png" != fileType) {
										Tip.tip({
											text : '仅支持gif、jpg、jpeg、png格式'
										});
										return true;
									}
								}
								if (null != this.fdImageMaxSize) {
									if (file.size > (this.fdImageMaxSize * 1024 * 1024)) {
										Tip.tip({
											text : '图片上传最大不超过'
													+ this.fdImageMaxSize
													+ 'MB,请重新选择图片'
										});
										return true;
									}
								}
							}

							return false;

						},

						tokenSuccess : function(file) {

							/**
							 * 供子类实现<br>
							 * 一般用于对获取到的token进行记录等操作
							 */
						},

						// MD5检查成功回调函数
						md5Success : function(file, md5) {

							if (this.token && this.token.body) {
								this.token.body.fileMd5 = md5;
							}

						},

						// 文件上传
						fileUpload : function(file) {

							// 类型不允许
							if (this.isTypeDisable(file)) {
								return "false";
							}

							// 大小不允许
							if (this.isSizeDisable(file)) {
								return "false";
							}

							// 图片不合规
							if (this.isPicDisable(file)) {
								return "false";
							}

							var _fileName = file.name;

							if (file._fdId == null || file._fdId == '') {
								file._fdId = this.guid();
							}

							if (window.console) {
								window.console.log("startUploadFile begin..");
							}

							file.edit = this.editMode == "edit";

							file.key = file.key ? file.key : this.fdKey;

							file.status = -1;

							var fileName = encodeURIComponent(file.name);
							var extendData = "filesize=" + file.size
									+ "&fileName=" + fileName;

							var self = this;

							self.uploadStart(file, {});

							if(file.filePath == 'dingUpload'){

								file.status = 2;

								self._uploadSuccess(file);
							}else {

								request
									.post(self.tokenurl, {
										data: extendData,
										handleAs: 'json'
									})
									.then(
										function (data) {

											if (window.console) {
												window.console
													.log("startUploadFile getToken end.. result:"
														+ data.status);
											}

											// 获取token成功
											if (data.status == '1') {
												file.status = 1;

												file.token = data.token;


												// 根据请求的token信息封装上传信息
												self.tokenSuccess(file);

												// 获取文件的MD5信息
												self.loadFromBlob(file);

											} else {
												file.status = 0;
												self.uploadError(file, {
													rtn: data
												});
											}

										}, function (data) {

											file.status = 0;
											self.uploadError(file, {
												rtn: data
											});
										});
							}

						},

						// 文件MD5检查，做极速上传
						md5Next : function(file, md5) {

							var self = this;
							var extendData = "filesize=" + file.size
									+ "&fileName=" + file.name + "&md5=" + md5;
							request.post(self.md5Url, {
								data : extendData,
								handleAs : 'json'
							}).then(function(data) {

								file.filekey = data.fileId;

								self.md5Success(file, md5, data);

								if (data.md5Exists) {

									file.status = 2;

									self._uploadSuccess(file);

								} else {
									// 未上传过则进行上传
									self._uploadFile(file, file.token);
								}

							})

						},

						// RTF文件上传
						rtfUpload : function(file) {

							var ext = file.name.substring(file.name
									.lastIndexOf("."));

							if (this.FILE_EXT_PIC.indexOf(ext) < 0
									&& (file.type && file.type.indexOf('image') < 0)) {
								return "false";
							}

							if (file._fdId == null || file._fdId == '') {
								file._fdId = this.guid();
							}
							file.status = 1;
							// 渲染开始展示
							this.uploadStart(file, {});
							// RTF上传文件
							this._uploadFile(file);
						},

						// 开始上传附件
						// file信息:fdId, size, name, type,
						// fullpath,status,fileKey
						startUploadFile : function(file) {

							var _file = file;
							// 非H5-input—file标准文件对象，例如手写批注语音等
							if (!(file instanceof File)) {
								// KK接口没有href属性，例如语音审批
								if (file.href) {
									_file = this.dataURItoBlob(file.href);
								}

								if (file.type) {
									_file.type = file.type;
								}

								_file.name = file.name;
								_file.href = file.href;
								_file.fullPath = file.fullPath;

							}

							// 支持免登上传
							if (window.attachmentConfig && window.attachmentConfig.isSupportShare) {
								this.uploadShare(_file);
								return;
							}

							if (this.uploadType == 1) {
								return this.fileUpload(_file);
							}

							if (this.uploadType == 2) {
								return this.rtfUpload(_file);
							}
						},

						// 附件上传后，注册附件信息,同步执行
						registFile : function(file, callback) {

							if (window.dojo4OfflineKK) {
								return this.registFileOffline(file, callback);
							}

							var xdata = "filekey=" + file.filekey
									+ "&filename="
									+ encodeURIComponent(file.name) + "&fdKey="
									+ this.fdKey + "&fdModelName="
									+ this.fdModelName + "&fdModelId="
									+ this.fdModelId + "&fdAttType="
									+ this.fdAttType;
							var self = this;
							
							var fdSign = base64.encode(file.name);
							fdSign = fdSign.replace(/\+/g,"");
							fdSign = fdSign.replace(/\//g,"");
							fdSign = fdSign.replace(/\=/g,"");
							
							xdata = xdata + "&fdSign=" + fdSign;
							request.post(this.attachurl, {
								data : xdata,
								handleAs : 'json',
								sync : true
							}).then(function(data) {

								if (data.status == '1') {
									file.fdId = data.attid;
									file.status = 2;
									if (callback)
										callback(file, {
											rtn : data
										});
								} else {
									file.status = 0;
									if (callback)
										callback(file, {
											rtn : data
										});
								}
							}, function(data) {

								file.status = 0;
								if (callback)
									callback(file, {
										rtn : data
									});
							});
							return file.fdId;
						},

						// 离线特殊处理，后续考虑将所有同步AJAX请求统一处理
						registFileOffline : function(file, callback) {

							var xdata = "filekey=" + file.filekey
									+ "&filename="
									+ encodeURIComponent(file.name) + "&fdKey="
									+ this.fdKey + "&fdModelName="
									+ this.fdModelName + "&fdModelId="
									+ this.fdModelId + "&fdAttType="
									+ this.fdAttType;
							var self = this;
							
							var fdSign = base64.encode(file.name);
							fdSign = fdSign.replace(/\+/g,"");
							fdSign = fdSign.replace(/\//g,"");
							fdSign = fdSign.replace(/\=/g,"");
							
							xdata = xdata + "&fdSign=" + fdSign;
							var url = this.attachurl;
							var options = {
								url : url,
								data : xdata,
								async : false,
								dataType : 'json',
								success : function(data) {

									if (data.status == '1') {
										file.fdId = data.attid;
										file.status = 2;
										if (callback)
											callback(file, {
												rtn : data
											});
									} else {
										file.status = 0;
										if (callback)
											callback(file, {
												rtn : data
											});
									}
								}
							};
							options.crossDomain = true;
							options.xhrFields = options.xhrFields || {};
							options.xhrFields.withCredentials = true;
							$.ajax(options);
							return file.fdId;
						},

						_uploadFile : function(file, token) {

							// 子类各自实现
						},

						uploadStart : function(file, context) {

							topic.publish(this.UPLOAD_EVENT_START, this, lang
									.mixin(context, {
										file : file
									}));
						},

						// 上传成功回调
						uploadSuccess : function(file, data) {

							if (!data.status || data.status == '1') {
								file.status = 2;
								file.filekey = data.filekey;

								this._uploadSuccess(file, data);
							} else {
								file.status = 0;
								this.uploadError(file, {
									rtn : data
								});
							}

						},

						_uploadSuccess : function(file, context) {

							topic.publish(this.UPLOAD_EVENT_SUCCESS, this, lang
									.mixin(context, {
										file : file
									}));
						},

						uploadError : function(file, context) {

							topic.publish(this.UPLOAD_EVENT_FAIL, this, lang
									.mixin(context, {
										file : file
									}));
						},

						uploadProcess : function(loaded, file) {

							topic.publish(this.UPLOAD_EVENT_PROCESS, this, {
								file : file,
								loaded : loaded
							});
						},

						destroy : function() {

							this.inherited(arguments);
						},

						dataURItoBlob : function(dataURI) {

							var byteString;
							if (dataURI.indexOf('base64,') >= 0) {
								// 去掉base64中的换行符，webkit会自动去除，但是ios9以及ios8中不会自动去除，导致转换出错
								dataURI = dataURI.replace(/\s/g, '');
								byteString = atob(dataURI.split('base64,')[1]);
							} else {
								byteString = unescape(dataURI.split('base64,')[1]);
							}
							var mimeString = dataURI.split(',')[0].split(':')[1]
									.split(';')[0];
							var ia = new Uint8Array(byteString.length);
							for (var i = 0; i < byteString.length; i++) {
								ia[i] = byteString.charCodeAt(i);
							}
							return new Blob([ ia ], {
								type : mimeString
							});
						},

						// 构建获取token参数
						buildOptions : function(file, token) {

							var d = null;
							var body = null;
							var header = null;

							if (token) {
								body = token.body;
								header = token.header;
							}

							if (window.FormData) {

								d = new FormData;
								// 阿里云OSS必须将file对象放在最后
								if (body) {
									for ( var key in body) {
										d.append(key, body[key]);
									}
								}

								if (this.extParam)
									d.append("extParam", this.extParam);

								var fileVal = 'NewFile';

								if (window.attachmentConfig
										&& window.attachmentConfig.fileVal) {
									fileVal = window.attachmentConfig.fileVal;
								}

								d.append(fileVal, file);

								this._url = this.uploadurl;

							} else {

								d = {
									'data' : file.href,
									'extParam' : this.extParam,
									'fileName' : file.name
								};

								if (body) {
									lang.extend(d, body)
								}

								this._url = this.uploadStreamUrl;

							}

							if (this.uploadType == 2) {
								this._url = this.rtfUploadUrl;
							}

							var options = {
								data : d,
								handleAs : 'json'
							}

							if (header) {
								options.headers = header;
							}

							return options;

						},

						uploadShare : function(file, context) {

							topic.publish(this.UPLOAD_EVENT_SHARE, this, lang
								.mixin(context, {
									file : file
								}));
						},
					});

		})