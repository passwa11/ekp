define( [ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class","dojo/dom-style",
		"dojo/_base/array", "dojo/_base/lang", "dojo/topic","dojo/window",
		"mui/picslide/PicSlide" ], function(declare, domConstruct, domClass,domStyle,
		array, lang, topic, win,PicSlide) {
	return declare("mui.picslide.ImagePreview", null, {

		picPlayEvtName : "/mui/image/play",

		picClick : "/mui/picitem/click",

		viewChangedEvt : "/mui/picslide/changeview",
		
		constructor : function() {
			this.inherited(arguments);
			topic.subscribe(this.picClick, lang.hitch(this, function(srcObj,argu) {
				this._imagePlayClose(argu);
			}));
		},

		// 当图片数组长度发生变化时重回
		reDraw : function(options) {
			if (options != null) {
				this.imgs = [].concat(options.srcList);
				if (this.imgSlide != null)
					this.imgSlide.destroy();
				this.buildPicSlide(options);
			}
		},
		
		// 构建
		buildPicSlide : function(options) {
			this.imgs = [].concat(options.srcList);
			var items = [];
			array.forEach(this.imgs, function(tmpImg) {
				items.push({
					icon : tmpImg
				});
			});
			var tmpStyle = {
				width : win.getBox().w + 'px',
				height : win.getBox().h + 'px'
			};
			this.imgSlide = new PicSlide(lang.mixin({
				items : items
			}, tmpStyle));
			if (!this.topDiv) {
				this.topDiv = domConstruct.create("div", {
					className : 'muiRtfPicSlider'
				}, document.body);
				domStyle.set(this.topDiv, tmpStyle);
			}
			this.topDiv.appendChild(this.imgSlide.domNode);
			if (this.imgSlide._started != true) {
				this.imgSlide.startup();
			}
		},
 
		// 播放
		play : function(options) {
			if (options == null)
				return;
			if (this.topDiv) {
				domStyle.set(this.topDiv, 'display', 'block')
			}
			if (this.imgs
					&& this.imgs.toString() != options.srcList
							.toString()) {
				this.reDraw(options)
			} else {
				if (this.topDiv == null)
					this.buildPicSlide(options);
			}
			
			setTimeout(lang.hitch(this, function() {
				domClass.add(this.topDiv, "muiRtfPicShow");
				//增加背景颜色选项
				if(options.previewImgBgColor){
					domStyle.set(this.topDiv, {background:options.previewImgBgColor});
				}
				var getImgIndex = function(imgs,curSrc){
					var index = 0;
					for(var i=0;i<imgs.length;i++){
						if(curSrc.indexOf(imgs[i])!=-1){
							index=i;
							break;
						}
					}
					return index;
				}
				
				//如果多张图片时（大于一张），则首次点击第一张时，总是显示最后一张。
				//加上if这段代码可以解决
				if(this.imgs.length > 1)
				{
					topic.publish(this.viewChangedEvt, this, {
						'curIndex' : this.imgs.length -1 
					});
				}
				
				topic.publish(this.viewChangedEvt, this, {
					'curIndex' : getImgIndex(this.imgs, options.curSrc)
				});
			}), 350);
		},

		_imagePlayClose : function(evts) {
			domClass.remove(this.topDiv, "muiRtfPicShow");
			setTimeout(lang.hitch(this, function() {
				domStyle.set(this.topDiv, 'display', 'none')
			}), 200);
		}
	});

});