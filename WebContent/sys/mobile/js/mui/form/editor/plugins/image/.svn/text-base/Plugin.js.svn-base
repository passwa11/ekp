define(
		[ "dojo/_base/declare", "dojo/dom-construct","dojo/text!../tmpl/panelH.html", "dojo/html", "dojo/query",
				'mui/util', "dojo/_base/lang", "dojo/dom-style", "dojo/dom-attr", "dojo/dom-class", "dojo/topic",
				"mui/form/editor/plugins/EditorPluginBaseMixin", "dojo/dom","dojo/_base/array", "dojo/string", "mui/device/device", 
				"mui/device/adapter", "mui/dialog/Tip", "dojox/mobile/sniff","mui/i18n/i18n!sys-mobile" ,"dojo/touch"],
		function(declare, domConstruct, tmpl, html, query, util,lang, domStyle, domAttr, domClass, topic,EditorPluginBaseMixin, 
				dom, array, string,device, adapter, Tip, has,Msg,touch) {

			return declare(
					"mui.form.editor.plugins.face.Plugin",
					[ EditorPluginBaseMixin ],{
						type : 'image',

						event : function(evt) {
							this._imageShow(evt);
						},

						icon : 'mui-editor-image',

						// 事件前缀
						eventPrefix : "attachmentObject_",

						show : function() {
							this.inherited(arguments);

							domStyle.set(this.container, {
								'display' : 'block'
							});

						},

						hide : function() {
							this.inherited(arguments);
							this.defer(function() {
								domStyle.set(this.container, {
									'display' : 'none'
								});
							}, 200);
						},

						constructor : function(options) {
							this.inherited(arguments);
							this.fdKey = options.editor.name;
						},

						// 上传开始
						_imgUploadStart : function(obj, evt) {
							if (window.console)
								console.log("图片上传开始");
							if (obj == window.AttachmentList[this.fdKey])
								this._imageBuildImage(evt);
						},

						// 上传完毕
						_imgUploadSuccess : function(obj, evt) {							
							if (window.console)
								console.log("图片上传成功");
							if (!evt)
								return;
							if (obj !=  window.AttachmentList[this.fdKey])
								return;
							var item = query('#' + evt.file._fdId)[0];
							var img = query('img', item)[0], close = query(
									'.muiEditorImageClose', item)[0], defaultItem = query(
									'.muiEditorImageDefault', item)[0], loading = query(
									'.muiEditorImageStatus', item)[0], thumb = query(
									'.muiEditorImageThumb', item)[0];
							domConstruct.destroy(loading);
							domConstruct.destroy(defaultItem);
							domStyle.set(close, 'display', 'block');
							var src = window.AttachmentList[this.fdKey].downloadUrl
									+ '?fdId=' + evt.filekey + '&picthumb=big';
							domAttr.set(img, 'src', util.formatUrl(evt.file.href));
							domStyle.set(thumb, 'opacity', 1);
							this.images.push({
								src : src,
								id : evt.file._fdId
							});
							this.editor.publishValueChange();
							this._onUploadSuccessAffer();
						},
						_onUploadSuccessAffer:function(){ // 根据是否多附件隐藏操作按钮
							if (!this.editor.fdMulti && this.images.length>=1){
								this._uploadNode && domStyle.set(this._uploadNode, 'display', 'none');
							}else{
								this._uploadNode && domStyle.set(this._uploadNode, 'display', '');
							}
						},
						getImageById : function(id) {
							for (var i = 0; i < this.images.length; i++) {
								if (this.images[i].id == id)
									return this.images[i];
							}
						},

						deleteImageById : function(id) {
							for (var i = 0; i < this.images.length; i++) {
								if (this.images[i].id == id)
									this.images.splice(i, 1);
							}
							this.editor.publishValueChange();
							this._onUploadSuccessAffer();
						},

						// 上传中
						_imgUploadProcess : function(obj, evt) {
							if (window.console)
								console.log('图片上传中');
						},

						_imgUploadFail : function(obj, evt) {
							if(evt && evt.rtn && evt.rtn.errorMessage){
								Tip.fail({text:evt.rtn.errorMessage});
							}
							if (evt && evt.file && evt.file._fdId) {
								var tmpId = evt.file._fdId;
								this.deleteImageById(tmpId);
								domConstruct.destroy(dom.byId(tmpId));
							}
							if (window.console)
								console.log("图片上传失败");
							
						},

						_imageBuildImage : function(evt) {
							var file = evt.file;
							var itemNode = domConstruct.create('div', {
								className : 'muiEditorImageItem',
								tabindex : 0,
								id : file._fdId
							}, this._uploadNode, 'before');
							domConstruct.create('div', {
								className : 'muiEditorImageDefault',
								innerHTML : '<i class="mui mui-file-img"></i>'
							}, itemNode);

							domConstruct.create(
											'div',{
												className : 'muiEditorImageStatus',
												innerHTML : '<i class="mui mui-spin mui-loading2"></i>'
											}, itemNode);

							domConstruct.create('div', {
								className : 'muiEditorImageThumb',
								innerHTML : '<img src=""  align="middle">'
							}, itemNode);
							var deleteNode = domConstruct.create('div', {
								innerHTML : '<i class="mui mui-close"></i>',
								className : 'muiEditorImageClose'
							}, itemNode);
							this.connect(deleteNode, 'click', '_deleteNode');
						},

						_deleteNode : function(evt) {
							var target = evt.target;
							var parent = target.parentNode.parentNode;
							var id = domAttr.get(parent, 'id');
							if (id) {
								this.deleteImageById(id);
								domConstruct.destroy(dom.byId(id));
							}
						},

						startup : function() {
							this.eventPrefix = this.eventPrefix + this.fdKey + "_";
							this.subscribe(this.eventPrefix + 'start',
									'_imgUploadStart');
							this.subscribe(this.eventPrefix + 'fail',
									'_imgUploadFail');
							this.subscribe(this.eventPrefix + 'success',
									'_imgUploadSuccess');
							this.subscribe(this.eventPrefix + 'process',
									'_imgUploadProcess');
							this.images = [];
							this.editor.formatContent.push(lang.hitch(this,
									this.imageFormat));
						},

						imageFormat : function(html) {
							if (!this.images || this.images.length == 0)
								return html;
							var img = '<img src="${src}">';
							array.forEach(this.images, function(image) {
								var src = image.src;
								var tmpl = string.substitute(img, {
									src : src
								});
								html += tmpl;
							}, this);
							return html;
						},

						_imageShow : function(evt) {
							
							if(this.lock) {
								return;
					        }
					        this.lock = true;
					        this.defer(function(){
					            this.lock = false;
					        },1000);
					          
							if (this._isShow) {
								this.hide();
								return;
							}

							this._isShow = true;

							if (this._imageIsInit) {
								this.show();
								return;
							}

							this.iconNode = evt.target;

							this.container = domConstruct.create('div', {
								className : 'muiEditorImage'
							}, this.editor.domNode, 'last');
							this._imageBuildPanel(evt);
							this.show();
							this._imageIsInit = true;
						},

						_upload : function(evt) {
							
							if(this.lock) {
								return;
					        }
					        this.lock = true;
					        this.defer(function(){
					        	this.lock = false;
					        },1000);
					          
							var target = evt.target;
							if (target)
								target.blur();
							adapter.selectFile({options:{
										fdKey : this.fdKey,
										uploadType:2,
										fdMulti : this.editor.fdMulti?this.editor.fdMulti:false,
										fdModelId : this.editor.fdModelId,
										fdModelName : this.editor.fdModelName
									},evt : evt});
						},
						_imageBuildPanel : function(evt) {
							var dhs = new html._ContentSetter({
								parseContent : true,
								cleanContent : true,
								node : this.container,
								onBegin : function() {
									this.content = this.content.replace(
											/!{panel}/g, '');
									this.inherited("onBegin", arguments);
								}
							});
							dhs.set(tmpl);
							dhs.parseDeferred.then(lang.hitch(this, function(
									parseResults) {
								if (parseResults.length == 0)
									return;
								this._imagePanel = parseResults[0];
								this.buildUploadBtn();
							}));
							dhs.tearDown();
						},

						// 构建上传按钮
						buildUploadBtn : function() {
							if (!this._imageContainer)
								this._imageContainer = domConstruct
										.create('div',
												{
													className : 'muiEditorImageContainer'
												},
												this._imagePanel.containerNode);

							this._uploadNode = domConstruct
									.create('div',{
												className : 'muiEditorImageUpload',
												innerHTML : '<div><i class="mui mui-plus" feature="album"></i></div><div class="muiEditorImageUploadTip">'+Msg["mui.add.to.content"]+'</div>'
											}, this._imageContainer);
							var devType = device.getClientType();
							if(devType>6 && devType<11){ //kk客户端
								this.connect(this._uploadNode, touch.press, '_upload');
							}else{
								var input = domConstruct.create('input', {
									type : 'file',
									accept : 'image/*',
//									capture : 'camera',
									className : 'muiEditorImageInput'
								}, this._uploadNode);
								// ios13下，JSP模板会设置全局的dojoClick为true，会导致上传无效，故此处做节点例外
								input.dojoClick = !has('ios')
								this.connect(input, 'click', '_click');
								this.connect(input, 'change', '_upload');
							}
						},

						_click : function(evt) {
							if (has("android")) {
								if (evt.stopPropagation)
									evt.stopPropagation();
								if (evt.cancelBubble)
									evt.cancelBubble = true;
								if (evt.preventDefault)
									evt.preventDefault();
								if (evt.returnValue)
									evt.returnValue = false;
							}
						}
					});
		});