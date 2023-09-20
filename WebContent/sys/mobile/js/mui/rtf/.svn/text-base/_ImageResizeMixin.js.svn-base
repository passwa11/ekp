define(
		[ "dojo/_base/declare", "dojo/dom-style", "dojo/dom-attr",
				"dojo/query", "dojo/_base/array", "mui/device/adapter",
				"dojo/on", "dojo/_base/lang", ],
		function(declare, domStyle, domAttr, query, array, adapter, on, lang) {

			return declare(
					"mui.rtf._ImageResizeMixin",
					null,
					{formatContent : function(domNode) {
							this.inherited(arguments);
							var imgs = [];
							if (typeof (domNode) == "object") {
								imgs = query('img', domNode);
							} else {
								imgs = query(domNode + ' img');
							}
							this.initSrcList();
							array.forEach(imgs,lang.hitch(this,
								function(item) {
									
									// 图片绑定了链接，则点击不显示图片，只跳转
									var itemParentNode = item.parentNode;
									if(itemParentNode.nodeName=="A" && itemParentNode.href != ""){
										return;
									}
								
									var src = item.src;
									if (domAttr.get(item,'data-type') != 'face'
											&& src.indexOf('/images/smiley/wangwang/') < 0) {
										this.addSrcList(item.src);
										this.resizeDom(item);
									}
							}));
						},

						resizeDom : function(item) {
							domAttr.remove(item, "style");
							var styleVar = {
								'height' : 'auto',
								'textIndent' : '0px',
								"max-width" : '100%'
							};
							domStyle.set(item, styleVar);
							var self = this;
							var src = item.src;
							if(src.indexOf('picthumb')>0){
								src = src.replace('&picthumb=big','');
							}
							on(item, "click", function(evt) {
								adapter.imagePreview({
									curSrc : src, 
									srcList : self.getSrcList(),
									previewImgBgColor : self.previewImgBgColor  //preview��屳��ɫ

								});
							});
						},

						initSrcList : function() {
							this.srcList = [];
						},

						addSrcList : function(src) {
						
							if(src.indexOf('picthumb')>0){
								src = src.replace('&picthumb=big','');
							}
							this.srcList.push(src);
						},

						getSrcList : function() {
							return this.srcList;
						}
					});

		});