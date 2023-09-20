define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var base = require("lui/base");
	var topic = require("lui/topic");
	
	var PREVIEW_SELECTED_CHANGE = "file/change";
	
	var ICON_SRC_PRE = "/resource/style/common/fileIcon/";
	
	var fileThumb = base.Component.extend({
		
		
		className : "lui-media-player-thumb-container",
		
		
		initProps : function($super, cfg) {
			$super(cfg);
			this.slideHeight = 0;
			this.itemHeight = 0;
			this.value = cfg.value;
		},
		
		startup : function() {
			if (this.isStartup) {
				return;
			}
			
			this.items = [];
			
			this.ulNode = $("<ul class='thumbs'></ul>");
			
			this.topBtn = $("<div class='btn top-btn'/>");
			this.bottomBtn = $("<div class='btn bottom-btn'/>");
			
			this.slideContainer = $("<div class='slide-container'></div>");
			this.slideInner = $("<div class='slide-inner'></div>");
			
			topic.subscribe(PREVIEW_SELECTED_CHANGE, this.change, this);
			topic.subscribe("media/preview/show", this.thumbTogge, this);
			this.isStartup = true;
		},
		
		thumbTogge : function() {
			if(this.isShow) {
				this.hide();
				this.isShow = false;
			} else {
				this.show();
				this.isShow = true;
			}
		},
		
		show : function() {
			this.draw();
			this.element.show();
			this.element.animate({
				"right" : 0
			}, 300);
			var page = parseInt(this.value.index / 3);
			if(page >= 0) {
				this.pageTo(page);
			}
		},
		
		hide : function() {
			var self = this;
			this.element.animate({
				"right" : -138
			}, 300, null, function() {
				self.element.hide();
			});
		},
		
		change : function(evt) {
			
			this.pauseAllVideo();
			
			if(evt && evt.index >= 0) {
				if(this.items[evt.index]) {
					this.select(evt.preIndex, evt.index);
					
					if(evt.changeObj !== this) {
						var page = parseInt(this.value.index / 3);
						if(page >= 0) {
							this.pageTo(page);
						}
					}
				}
			}
		},
		
		pauseAllVideo : function() {
			var mediaFrameList = $("iframe[name='mediaFrame']");
			for(var i = 0; i < mediaFrameList.length; i++){
				var videoList = $(mediaFrameList).contents().find('video');
				for(var j = 0; j < videoList.length; j++)
					videoList[j].pause();
			}
		},
		
		select : function(preIndex, index) {
			if(preIndex != index) {
				if(this.items[preIndex])
					this.items[preIndex].removeClass("select");
				if(this.items[index])
					this.items[index].addClass("select");
			}
		},
		
		draw : function($super) {
			if(this.isDrawed)
				return this;
			
			var self = $super();
			
			this.topBtn.appendTo(this.element);
			this.bottomBtn.appendTo(this.element);
			
			this.slideContainer.append(this.slideInner);
			this.slideInner.append(this.ulNode);
			this.element.append(this.slideContainer);
			
			if(this.value.data && this.value.data.length > 0) {
				for(var i = 0; i < this.value.data.length; i++) {
					var item = $("<li></li>");
					var itema = $("<a href='javascript:;' class='lui-media-player-thumb-item-border' data-thumb-index='" + i + "'></a>");
					
					if(this.value.index === i) {
						itema.addClass("select");
					}
					
					this.items.push(itema);
					var urls = this.thumbUrl(this.value.data[i]);
					var thumb = $("<img class='big'>");
					thumb.attr("src" , urls.thumb);
					var icon = $("<img class='small'>");
					icon.attr("src" , urls.icon);
					
					item.append(itema);
					
					itema.append(thumb);
					itema.append(icon);
					
					this.ulNode.append(item);
					
					if(!this.itemHeight) {
						this.itemHeight = item.innerHeight();
					}
				}
 			}
			this.isDrawed = true;
			
			this.slideHeight = this.slideInner.height();
			this.containerHeight = this.slideContainer.height();
			
			
			this.bindEvent();
			
			return self;
		},
		
		bindEvent :  function() {
			var self = this;
			
			this.ulNode.on("click", "[data-thumb-index]", function(evt) {
				var index = $(evt.currentTarget).attr("data-thumb-index");
				if(index >= 0 && self.value.data[index]) {
					self.value.setIndex(index, self);
				}
			});
			
			this.topBtn.on("click" , function() {
				self.slide(-3);
			});
			
			this.bottomBtn.on("click" , function() {
				self.slide(3);
			});
		},
		
		thumbUrl : function(file) {
			var icon = "default.gif";
			var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
			
			//var File_EXT_VIDEO = ".flv;.mp4;.f4v;.mp4;.m3u8;.webm;.ogg;.theora;.mp4;.avi;
			//.mpg;.wmv;.3gp;.mov;.asf;.asx;.wmv9;.rm;.rmvb;.wrf" ;
			//var File_EXT_READ = ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.vsd;";

			var thumb = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + file.fdId + "&filethumb=yes";
			
			if(".doc|.docx|.wps".indexOf(fileExt) > -1 ) {
				icon = "word.png";
			} else if (".ppt|.pptx".indexOf(fileExt) > -1 ) {
				icon = "ppt.png";
			} else if(".xls|.xlsx".indexOf(fileExt) > -1) {
				icon = "excel.png";
			} else if(".3gp|.f4v|.mp4|.mpg|.mov|.asx|.asf|.avi|.flv|.rm|.wav|.m4v|.ogg|.wmv".indexOf(fileExt) > -1) {
				icon = "video.png";
			} else if (".rar|.gz|.7z".indexOf(fileExt) > -1 ) {
				icon = "7z.png";
			} else if (".ttf|.ttc|.otf".indexOf(fileExt) > -1 ) {
				icon = "font.png";
			} else if(".pdf" == fileExt) {
				icon = "pdf.png";
			} else if(".et" == fileExt) {
				icon = "et.png";
			}  else if(".html" == fileExt) {
				icon = "html.png";
			} else if(".txt" == fileExt) {
				icon = "txt_default.gif";
			} else if(".dps" == fileExt) {
				icon = "dps.png";
			} else if(".vsd" == fileExt) {
				icon = "vsd.png";
			} else if(".text" == fileExt) {
				icon = "text.png";
			} else if(".psd" == fileExt) {
				icon = "psd.png";
			} else if(".svg" == fileExt) {
				icon = "svg.png";
			} else if(".wps" == fileExt) {
				icon = "wps.png";
			} else if(".tif" == fileExt) {
				icon = "tif.png";
			} else if(".ofd" == fileExt) {
				icon = "ofd.png";
			} else if(".key" == fileExt) {
				icon = "keynote.png";
			} else if(".rmvb" == fileExt) {
				icon = "rmvb.png";
			} else if(".ai" == fileExt) {
				icon = "ai.png";
			} else if(".raq" == fileExt) {
				icon = "raq.png";
			} else if(".sketch" == fileExt) {
				icon = "sketch.png";
			} else if(".pro" == fileExt) {
				icon = "pro.png";
			} else if(".js" == fileExt) {
				icon = "js.png";
			} else if(".zip" == fileExt) {
				icon = "zip.png";
			} else if(".out" == fileExt) {
				icon = "out.png";
			} else if(".rtf" == fileExt) {
				icon = "wps.png";
			} else if(this.value.getFileType(file) == "image") {
				thumb = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + file.fdId + "&filekey=image2thumbnail_s1";
				icon = "image.png";
			} else if(this.value.getFileType(file) == "video") {
				icon = "video.png";
			} else if(this.value.getFileType(file) == "mp3") {
				icon = "audio.png";
				thumb = "/sys/attachment/sys_att_main/img/musicicon.jpg";
			}
			
			
			return {
				icon : env.fn.formatUrl(ICON_SRC_PRE + icon),
				thumb : env.fn.formatUrl(thumb)
			}
		},
		
		
		slideTo : function(dh, isAnimate) {
			if(this.slideHeight <= this.containerHeight) {
				this.resetBtn(0);
				return;
			}
			var time = isAnimate ? 300 : 0;
			if(dh >= 0) {
				dh = 0;
			} else if((this.slideHeight + dh) < this.containerHeight) {
				dh =  this.containerHeight - this.slideHeight;
			}
			this.slideInner.animate({
				top : dh
			}, time);
			
			this.resetBtn(dh);
		},
		
		slide : function(step) {
			var h = - parseInt(this.itemHeight * step),
				top = parseInt(this.slideInner.css("top") || 0);
			var dh = top + h;
			this.slideTo(dh, true);
		},
		
		pageTo : function(pageNo) {
			var perPageH =  this.itemHeight * 3;
			var h = perPageH * pageNo;
			this.slideTo(-h, false);
			
		},
		
		resetBtn : function(top) {
			if(top >= 0) {
				this.topBtn.addClass("btn-disabled");
			} else {
				this.topBtn.removeClass("btn-disabled");
			}
			
			if(this.slideHeight + top  <= this.containerHeight) {
				this.bottomBtn.addClass("btn-disabled");
			} else {
				this.bottomBtn.removeClass("btn-disabled");
			}
			
		}
		
	});
	
	
	module.exports = fileThumb;
});