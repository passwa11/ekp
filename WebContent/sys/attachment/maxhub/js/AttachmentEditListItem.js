define(["dojo/_base/declare",
				"dojo/dom-construct",
				"mui/util",
				"mhui/device/jssdk",
				"dojo/topic",
				"dojo/_base/lang",
				"dojo/dom-style",
				"sys/attachment/mobile/js/_AttachmentItem"],
		function(declare, domConstruct, util, jssdk, topic, lang, domStyle, AttachmentItem) {
			//普通附件项展示类
			return declare(
					"sys.attachment.maxhub.js.AttachmentEditListItem",
					[ AttachmentItem],{
						baseClass : 'mhuiAttachmentEditItem',
						
						//-1准备上传,	0上传出错,	1上传中,		2上传成功 ,  3 表示阅读状态
						status : 3,
						
						buildItem : function() {
							var itemL = domConstruct.create("div", {
								className : "mhuiAttachmentItemL " +
								this.getAttContainerType()
							}, this.containerNode);
							this.attItemIcon = domConstruct.create("div",{ className: "mhuiAttachmentItemIcon" }, itemL);
							var iconClass = this.getAttTypeClass();
							if (this.icon != null && this.icon != '') {
								iconClass = this.icon;
							}
							domConstruct.create("i", { className :  iconClass }, this.attItemIcon);
							var itemC = domConstruct.create("div", { className : "mhuiAttachmentItemC"}, this.containerNode);
							domConstruct.create("span", {
								className : "mhuiAttachmentItemName",
								innerHTML : this.name
							}, itemC);
							
							var delProp = {
									className: "mhuiAttachmentItemDel", style: {
										display: 'inline-block'
									}
								};
							if(this.status != 3){
								this.statusDiv = domConstruct.create("div",{
									className: "mhuiAttachmentItemStatus"}, this.domNode);
								 domConstruct.create("i",{
										className: "mui mui-spin mui-loading2"}, this.statusDiv );
								delProp.style.display = 'none';
							}
							this.attItemDel = domConstruct.create("div", delProp , this.domNode);
							domConstruct.create("i",{
									className : "mui mui-close"
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
							if (this.href) {
								this.connect(this.domNode, "click",lang.hitch(this._onItemClick));
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
									display : 'inline-block'
								});
							}
						},

						uploaded : function() {//成功处理
							domConstruct.empty(this.attItemIcon);
							var image=domConstruct.create("i",{
								className : "mui " + this.getAttTypeClass()
							}, this.attItemIcon);
							
							if (this.statusDiv ) {
								domConstruct.destroy(this.statusDiv);
								this.statusDiv=null;
							}
							if (this.attItemDel) {
								domStyle.set(this.attItemDel, {
									'display' : 'inline-block'
								});
							}
							if(this.href || this.path){
								this.connect(this.domNode, "click",lang.hitch(this._onItemClick));
							}
						},
						
						_imgResize:function(image){
							 image.onload = function(){
								   var height=image.height,width= image.width;
								   if(height<width)
										domStyle.set(image, {
											'height' : '40px',
										    'position' : 'relative',
										    'top' : '50%',
										    'left' : '50%',
										    'transform' : 'translate(-50%,-50%)'
										});
									else
										domStyle.set(image, {
											'width' :'40px',
											'position' : 'relative',
											'top' : '50%',
											'left' : '50%',
											'transform' : 'translate(-50%,-50%)'
										});
							};
						},
						
						_onItemClick : function(){
							if(this.href){
								console.log('_onItemClick href:' + util.formatUrl(this.href));
								window.location.href = util.formatUrl(this.href);
							}else if(this.path){
								jssdk.viewFile({
									path : this.path
								});
							}
						}
						
					});
		});