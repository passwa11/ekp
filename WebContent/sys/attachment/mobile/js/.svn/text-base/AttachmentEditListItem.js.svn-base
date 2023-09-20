define(["dojo/_base/declare",
				"dojo/dom-construct",
				"mui/util",
				"dojo/topic",
				"dojo/_base/lang",
				"dojo/dom-style",
				"dojox/mobile/ProgressBar",
				"sys/attachment/mobile/js/_AttachmentItem",
				"mui/device/adapter",
				"sys/attachment/mobile/js/_AttachmentViewOnlineMixin","dojo/request", "dojo/query"],
		function(declare, domConstruct, util, topic, lang, domStyle,
				ProgressBar, AttachmentItem, adapter, _AttachmentViewOnlineMixin,request,query) {
			//普通附件项展示类
			return declare(
					"sys.attachment.mobile.js.AttachmentEditListItem",
					[ AttachmentItem, _AttachmentViewOnlineMixin],{
						baseClass : 'muiAttachmentEditItem',
						
						//-1准备上传,	0上传出错,	1上传中,		2上传成功 ,  3 表示阅读状态
						status : 3,
						
						canDownload : true,

						canRead : true,
						
						canEdit : true,
						// 是否允许别人删除自己上传的附件
						showDeleteIcon: 'true',

						postCreate:function(){
							this.inherited(arguments);
							this.subscribe("attachmentEditListItem_delItem",'_delItem');
							this.subscribe("attachmentListItem_viewItem",'_viewItem');
						},
						
						buildItem : function() {
							var attItemTop = domConstruct.create("div", {
									className : "muiAttachmentItemT " + this.getAttContainerType()
								}, this.containerNode);
							
							this.attItemIcon = domConstruct.create("div",{
								className: "muiAttachmentItemIcon"}, attItemTop);
							if(this.getType() == 'img' && this.status == 3){
								 if(this.thumb){
									 var image=domConstruct.create("img",{
											align:"middle",
											src: util.formatUrl(this.thumb)}, this.attItemIcon);
									 this._imgResize(image);
									 topic.publish("attachmentObject_"+this.key+"_afterView",this,{href:this.thumb,pageType:'edit'})
								}else{
									var image=domConstruct.create("img",{
										align:"middle",
										src: util.formatUrl(this.href)}, this.attItemIcon);
									this._imgResize(image);
									topic.publish("attachmentObject_"+this.key+"_afterView",this,{href:this.href,pageType:'edit'})
								}
							}else{
								domConstruct.create("i",{
										className : this.getAttTypeClass()
									}, this.attItemIcon);
							}

							this.connect(this.attItemIcon, 'click',lang.hitch(this._onItemClick));
							var delProp = {
									className: "muiAttachmentItemDel", style: {
										display: this.showDeleteIcon ==='true' ? 'block':'none'
									}
								};
							if(this.status != 3){
								this.statusDiv = domConstruct.create("div",{
									className: "muiAttachmentItemStatus"}, attItemTop);
								 domConstruct.create("i",{
										className: "fontmuis mui-spin muis-refresh"}, this.statusDiv );
								delProp.style.display = 'none';
							}
							this.attItemDel = domConstruct.create("div", delProp , attItemTop);
							domConstruct.create("i",{
									className : "fontmuis muis-epid-delete"
								}, this.attItemDel);

							this.connect(this.attItemDel, 'click', lang.hitch(
									this, function(evt) {
										topic.publish('attachmentObject_'
												+ this.key + '_del', this,{
													widget : this
												});
										if (evt.stopPropagation)
											evt.stopPropagation();
										if (evt.cancelBubble)
											evt.cancelBubble = true;
										if (evt.preventDefault)
											evt.preventDefault();
										if (evt.returnValue)
											evt.returnValue = false;
									}));
							var attItemBottom = domConstruct.create("div", {
									className : "muiAttachmentItemB"
								}, this.containerNode);
							var attItemName = domConstruct.create("div", {
									className : "muiAttachmentItemName",
									innerHTML : this.name
								}, attItemBottom);
							if (this.size != null && this.size != '') {
								domConstruct.create("div", {
									className : "muiAttachmentItemSize",
									innerHTML : this.formatFileSize()
								}, attItemBottom);
							}
							if(this.hidePicName && this.hidePicName == "true"){
								var tempId = this.id;
								setTimeout(function (){
    							  $("[id='"+tempId+"']").find(".muiAttachmentItemB").hide();								
								},100)
							}
						},
						
						changeProgress : function(val) {
							//上传进度处理
						},

						uploadError : function(msg) {
							if (this.statusDiv) {
								domConstruct.empty(this.statusDiv);
								this.statusDiv.innerHTML ="上传失败!";
							}
							if (this.attItemDel) {
								domStyle.set(this.attItemDel, {
									display : 'block'
								});
							}
						},

						uploaded : function() {//成功处理
							if(this.attItemIcon && this.getType() == 'img' && this.href){
								domConstruct.empty(this.attItemIcon);
								 var image=domConstruct.create("img",{
										align:"middle",
										src: util.formatUrl(this.href)}, this.attItemIcon);
								 this._imgResize(image);
							}
							
							if (this.statusDiv ) {
								domConstruct.destroy(this.statusDiv);
								this.statusDiv=null;
							}
							if (this.attItemDel) {
								domStyle.set(this.attItemDel, {
									'display' : 'block'
								});
							}
						},
						
						_onItemClick:function(){
							if (this.filePath)
						        adapter.fileView({
							      path : this.filePath
						         });
							else{
								if(this.inherited(arguments))
									return;
								this._downLoad();
							}
						},
						
						_downLoad : function() {
								// 记录下载日志
								var logUrl = "/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=addDownlodLog&downloadType=manual&fdId="+this.fdId;
								request.post(util.formatUrl(logUrl,true));
								adapter.download({
									fdId : this.fdId,
									name : this.name,
									type : this.type,
									href : this.href
								});
						
						},
						
						_imgResize:function(image){
							 image.onload = function(){
								   var height=image.height,width= image.width;
								   var imgStyle=query(".muiAttachmentItemT")[0].getBoundingClientRect();
								   if(height<width)
										domStyle.set(image, {
											'height' : imgStyle.height==0?'100%':(imgStyle.height+'px'),
											'position' : 'absolute',
										    'top' : '50%',
										    'left' : '50%',
										    'transform' : 'translate(-50%,-50%)',
										    '-webkit-transform' : 'translate(-50%,-50%)'
										});
									else
										domStyle.set(image, {
											'width' :imgStyle.width == 0?'100%':(imgStyle.width+'px'),
											'position' : 'absolute',
										    'top' : '50%',
										    'left' : '50%',
										    'transform' : 'translate(-50%,-50%)',
										    '-webkit-transform' : 'translate(-50%,-50%)'
										});
							};
						},

						_delItem:function(srcObj,data){
							if(data.key && data.key == this.key) {
								topic.publish('attachmentObject_'
									+ this.key + '_del', this, {
									widget: this
								});
							}
						},

						_viewItem:function(srcObj,data){
							if(data.key && data.key == this.key) {
								this._onItemClick();
							}
						}
					});
		});