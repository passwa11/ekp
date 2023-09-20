define(["dojo/_base/declare",
		'dojo/_base/lang',
		'dojo/parser',
		'dojo/query',
		'dojo/topic',
		'dojo/request',
		"dojo/dom-construct",
		'dojo/_base/array',
		"sys/attachment/mobile/js/_AttachmentItem",
		'mui/util',
		"mhui/device/jssdk",
		"mhui/dialog/Dialog",
		"mui/dialog/Tip",
		"mui/i18n/i18n!sys-attachment:*"], 
		function(declare, lang, parser, query, topic, request ,domConstruct, array, AttachmentItem, util, jssdk, Dialog, Tip, Msg){
	
	if(!Object.assign){
		Object.assign = function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	}
	
	var noop = function(){};
	
	return declare("sys.attachment.maxhub.js.AttachmentOptListItem", [ AttachmentItem ] , {
		
		eventPrefix : 'attachmentObject_',
		
		baseClass : 'mhuiAttachmentEditItem mhuiAttachmentEditOptItem',
		
		postCreate : function() {
			this.inherited(arguments);
			this.lifecycle = {};//生命周期
			this.subscribe(this.eventPrefix + this.getParent().fdKey + '_success' , "_success");
			this.subscribe(this.eventPrefix + this.getParent().fdKey + '_openBoard','_handleClose');
		},
		
		successCount : 0,
		
		_success : function(){
			console.log('success:' + this.successCount)
			this.successCount = this.successCount + 1;
		},
		
		buildItem : function(){
			this.loading = this.buildTip('加载中...');
			this.processing = this.buildTip('上传中...');
			
			var attItemTop = domConstruct.create("div", {
				className : "mhuiAttachmentItemT"
			}, this.containerNode);
			
			var attItemIcon = domConstruct.create("div",{
				className: "mhuiAttachmentItemIcon"}, attItemTop);
			this.uploadDom = domConstruct.create("i",{
				className : "mui mui-plus"
			}, attItemIcon);		
			
			this.connect(this.domNode,'click','_handleOpen');
			topic.subscribe('attachmentObj_' + this.getParent().fdKey + '_selectFile' ,lang.hitch(this, this._handleOpen));
			
		},
		
		buildTip : function(text){
			var pTip = declare([Tip.Tip], {
				icon: "mui mui-loading mui-spin",
				time : -1,
				cover: true,
				text : text,
				hide : function() {
					this.inherited(arguments, [false]);
				}
			});
			return new pTip();
		},
		
		_handleOpen : function(evt){
			if(!window.AttachmentList)
				window.AttachmentList = {};
			var attachmentObj = window.AttachmentList[this.getParent().fdKey];
			if(attachmentObj && attachmentObj.selectedValues){//上一次附件上传未结束,请稍后再试
				Tip.tip({ text : '附件上传中,请耐心等待上一次附件上传结束' });
				return;
			} 
			this.loading.show();
			var self = this;
			//console.log('初始化生命周期');
			this.initlifecycle(evt);
			
			var promise = request(util.formatUrl('/sys/attachment/maxhub/tmpl/dialogTemplate.jsp'),{
				handleAs : 'text/html'
			})
			promise.then(function(data){
				var _dialogTemplate = lang.replace(data,{ fdKey : self.getParent().fdKey });
				var dom = domConstruct.create('div',{ innerHTML: _dialogTemplate , className : 'mhuiAttachmentContent' });
				parser.parse(dom)
					.then(function(ws) {
						array.forEach(ws, function(w, index) {
							if(w.declaredClass === 'sys.attachment.maxhub.js.AttachmentSelectList'){
								self.selectlist = w;
							}
						});
						self._buildDialog(dom);
						//console.log('附件弹出框创建完毕，执行生命周期钩子create');
						self.lifecycle.handleUploadCreate && self.lifecycle.handleUploadCreate();
						//console.log('通过SDK获取文件列表开始');
						jssdk.getFiles(null,function(files){
							var ___files = self._formatFiles(files);
							//console.log('SDK返回文件列表:' + JSON.stringify(___files));
							//console.log('已上传文件列表路径' + JSON.stringify(self.getParent().pathAtts));
							___files = array.filter(___files,function(file){
								//console.log('当前文件路径' + file.filePath)
								return array.indexOf(self.getParent().pathAtts,file.filePath) < 0;
							});
							//console.log('EKP过滤后文件列表:' + JSON.stringify(___files));
							self.selectlist.set('selectedValues',[]);
							self.selectlist.set('values',___files);
							//console.log('通过SDK获取文件列表结束');
							self.loading.hide();
						});
					});
			});
		},
		
		_handleClose : function(isCancel){
			this.loading.hide();
			this.processing.hide();
			this.dialog.hide();
			//console.log('附件上传弹出框关闭，执行生命周期钩子close')
			this.lifecycle.handleUploadClose && this.lifecycle.handleUploadClose({
				isCancel : isCancel
			});
		},
		
		_buildDialog : function(dom){
			//console.log('附件弹出框构建开始');
			var ctx = this;
			var dialog = Dialog.show({
				title : Msg['table.sysAttRtfData'],
				content : dom,
				baseClass : 'mhuiAttachmentDialog',
				showClose : false,
				isTop:true,//当页面嵌入有iframe时，让弹窗显示在最外层
				buttons: [
					{
				    	text: '取消',
						onClick: function(d) {
							ctx._handleClose(true);
						}
				    },{
						text: '确定',
						className: 'mhuiDialogPrimaryBtn',
						onClick: function(d) {
							ctx.processing.show();
							var selectlist = ctx.selectlist,
								selectedValues = selectlist.get('selectedValues');
							//console.log('已选文件数:' + selectedValues.length);
							if(selectedValues.length > 0){
								for(var i = 0; i < selectedValues.length; i++){
									//console.log('当前选择文件,文件序号:' + i + ',数据为:'+ JSON.stringify(selectedValues[i]));
									jssdk.uploadFiles({
										options:ctx.getParent(),
										evt : {
											path : selectedValues[i].filePath,
											name : selectedValues[i].fileName,
											size : selectedValues[i].fileSize
										}
									});
								}
								var attachmentObj = window.AttachmentList[ctx.getParent().fdKey];
								if(!attachmentObj.selectedValues){
									attachmentObj.selectedValues = selectedValues;
								}
								//console.log('开始轮询上传状态');
								ctx._handleUploadComplete();
							} else {
								ctx.lifecycle.handleUploadComplete && ctx.lifecycle.handleUploadComplete();
							}
							ctx._handleClose(false);
						}
				    }
	            ]
			});
			this.dialog = dialog;
			return dialog;
		},
		
		_formatFiles : function(files){
			var __files = [];
			for(var i = 0; i< files.length; i++){
				var fileName = files[i].filePath;
				if(fileName.lastIndexOf('/') > -1){
					fileName = fileName.substring(fileName.lastIndexOf('/') + 1,fileName.length)
				}
				__files.push(Object.assign(files[i],{
					fileName : fileName
				}));
			}
			return __files;
		},
		
		_handleUploadComplete : function(){
			var attachmentObj = window.AttachmentList[this.getParent().fdKey],
				selectedValues = attachmentObj.selectedValues,
				filekeys = this.getParent().filekeys;
			if(selectedValues){
				if (this.successCount === selectedValues.length) {//selectedValues长度与filekeys长度相等，说明所有的附件均已异步上传完毕，处理complete回调函数
					if(attachmentObj != null && attachmentObj.fileStatus != -1){
						for(var i = 0; i < filekeys.length; i++){
							if(filekeys[i].status == -1){
								console.error('第['+ i +']个文件上传失败');
								this.lifecycle.handleUploadError && this.lifecycle.handleUploadError();
								return;
							}
							if(filekeys[i].status < 2 ){
								setTimeout(lang.hitch(this,this._handleUploadComplete),500);
								return;
							}
						}
					}
				}else{
					//console.log('未上传完毕,延时500ms后再轮询检测');
					setTimeout(lang.hitch(this,this._handleUploadComplete),500);
					return;
				}
				//console.log('附件均已上传完毕，执行生命周期钩子complete');
				this.lifecycle.handleUploadComplete && this.lifecycle.handleUploadComplete();
				this.___reset();
				//console.log('结束轮询上传状态')
			}
		},
		
		___reset : function(){
			var attachmentObj = window.AttachmentList[this.getParent().fdKey];
			if(attachmentObj){
				attachmentObj.selectedValues = null;
			}
			this.successCount = 0;
		},
		
		initlifecycle : function(evt){
			evt = evt || {};
			this.lifecycle = {
				handleUploadCreate : evt.create || noop,
				handleUploadComplete : 	 evt.complete || noop,
				handleUploadClose : evt.close || noop,
				handleUploadError : evt.error || noop
			};
		}
		
	});
	
});