
define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var base = require("lui/base");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var topic = require("lui/topic");
	
	var PREVIEW_SELECTED_CHANGE = "file/change";
	var fileValue = require('./fileValue');
	
	var filePlay =  base.Container
			.extend({
				
				className : "lui-media-player-playcontainer",

				initProps : function($super, cfg) {
					$super(cfg);
					this.medias = [];
					this.value = cfg.value;
					this.isSupportDirect = cfg.isSupportDirect;
				},
				
				startup : function() {
					
					if (this.isStartup) {
						return;
					}
					if(!this.value) {
						this.isStartup = true;
						return;
					}
					var data = this.value.data;
					
					if (data && data.length > 0) {
						for(var i = 0; i < data.length; i ++) {
							var file = data[i], fileType = this.value.getFileType(file);
							
							var media = new fileMedias[fileType]({
								index : i,
								file : file,
								isSupportDirect : this.isSupportDirect
							});
							this.medias.push(media);
							this.addChild(media);
							
							media.startup();
							
						}
						
						if(data.length > 1) {
							var url = env.fn.formatUrl("/sys/attachment/player/css/img/",true);
							this.left = $("<div class='lui-media-arrow left' data-lui-mark='play-left' />");
							this.left.css("cursor", "url('" + url +"/pic_prev.cur'), w-resize");
							this.right = $("<div class='lui-media-arrow right' data-lui-mark='play-right' />");
							this.right.css("cursor", "url('" + url +"/pic_next.cur'), e-resize");
						} 
					}
					
					this.isStartup = true;
				},
				
				draw : function($super, obj) {
					if(this.isDrawed)
						return this;
					
					for ( var i = 0; i < this.medias.length; i++) {
						var child = this.medias[i];
						child.setParentNode(this.element);
					}
					
					this.element.show();
					this.isDrawed = true;
					
					//初始化
					if(this.value.data.length > 0) {
						this.value.setIndex(0);
					}
					
					if(this.left) {
						this.left.appendTo(this.element);
					}
					if(this.right) {
						this.right.appendTo(this.element);
					}
					
					this.bindEvent();
					return this;
				},
				
				bindEvent : function() {
					var self = this;
					if(this.left) {
						this.left.on("click", function() {
							self.pauseAllVideo();
							self.value.setIndexByStep(-1);
						});
					}
					if(this.right) {
						this.right.on("click", function() {
							self.pauseAllVideo();
							self.value.setIndexByStep(1);
						});
					}
					
				},
				
				pauseAllVideo : function() {
					var mediaFrameList = $("iframe[name='mediaFrame']");
					for(var i = 0; i < mediaFrameList.length; i++){
						var videoList = $(mediaFrameList).contents().find('video');
						for(var j = 0; j < videoList.length; j++)
							videoList[j].pause();
					}
				}
				
			});

	
	var mediaBase = base.Component.extend({
		
		className : "lui-media-player-play-item",
		
		initProps: function(cfg) {
			this.file = cfg.file;
			this.index = cfg.index;
			this.isShow = false;
			this.mediaFrame = null;
			this.isSupportDirect = cfg.isSupportDirect;
		},
		
		startup : function() {
			if (this.isStartup) {
				return;
			}
			topic.subscribe(PREVIEW_SELECTED_CHANGE, this.change, this);
			topic.subscribe("media/screen/show",  this.screen, this);
			
			
			this.isStartup = true;
		},
		
		change : function(evt) {
			if(evt && evt.index >= 0) {
				if(evt.index == this.index) {
					this.show();
				} else {
					this.hide();
				}
			}
		},
		
		show : function() {
			this.isShow = true;
			this.draw();
			this.element.show();
		},
		
		hide : function() {
			this.isShow = false;
			this.element.hide();
		},
		
		
		draw : function() {
			if(this.isDrawed)
				return;
			var iframe = $("<iframe allowFullScreen='true'></iframe>");
			this.mediaFrame = iframe;
			var src= "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + this.file.fdId + "&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes"
			iframe.attr("src", env.fn.formatUrl(src));
			
			this.element.append(iframe);
			this.element.show();
			iframe.css("height", this.element.height());
			this.isDrawed = true;
			return this;
		},
		
		screen : function(evt) {
			if(!this.isShow) {
				return;
			}
			
			if(this.mediaFrame) {
				var  frame = this.mediaFrame[0];
				var frameWindow = frame.contentWindow;
				if(frameWindow && frameWindow.commonFuncs) {
					frameWindow.commonFuncs.initialHandlers();
					frameWindow.commonFuncs.changeScreenStatus(evt);
					if (!(window.navigator.userAgent.indexOf("MSIE")>-1)){
						frame.focus();
						frameWindow.focus();
					}
				}
			}
			
		}
		
	});
	
	var mediaImage = mediaBase.extend({
		
		mediaType : "image",
			
		draw : function() {
			if(this.isDrawed)
				return;
			var a = $("<a class='lui-media-image-container' href='javascript:;' title='点击查看原图'></a>");
			var img = $("<img>");
			var fileId = this.file.fdId;
			img.attr("src" , 
					env.fn.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=view&filekey=image2thumbnail_s2&fdId=") + fileId);
			a.append(img);
			this.element.append(a);
			this.element.show();
			
			a.on("click", function() {
				var url = env.fn.formatUrl('/sys/attachment/sys_att_main/showOriginalImg.jsp');
			    window.open(url+"?fdId=" + fileId, "_blank");
			})
			
			this.isDrawed = true;
			return this;
		}
		
	});
	
	var mediaCad = mediaBase
		.extend({

			mediaType : "cad",

			draw : function() {

				if (this.isDrawed)
					return;
				var a = $("<a class='lui-media-image-container' href='javascript:;' title='点击查看原图'></a>");
				var img = $("<img>");
				var fileId = this.file.fdId;
				img
						.attr(
								"src",
								env.fn
										.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=")
										+ fileId);
				a.append(img);
				this.element.append(a);
				this.element.show();

				a
						.on(
								"click",
								function() {

									var url = env.fn
											.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view');
									window.open(url + "&fdId=" + fileId,
											"_blank");
								})

				this.isDrawed = true;
				return this;
			}

		});
	
	 
	var mediaOffice  = mediaBase.extend({
		mediaType : "office"
	});
	
	var mediaPdf  = mediaBase.extend({
		mediaType : "pdf"
	});
	
	var mediaHtml  = mediaBase.extend({
		mediaType : "html",
		
		draw : function() {
			if(this.isDrawed)
				return;
			var iframe = $("<iframe></iframe>");
			this.mediaFrame = iframe;
			var src= "/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&open=1&fdId=" + this.file.fdId 
				+ "&isSupportDirect=" + (this.isSupportDirect || "");
			iframe.attr("src", env.fn.formatUrl(src));
			
			this.element.append(iframe);
			this.element.show();
			iframe.css("height", this.element.height());
			this.isDrawed = true;
			return this;
		}
	});
	
	var mediaMP3  = mediaBase.extend({
		mediaType : "mp3",
		
		draw : function() {
			if(this.isDrawed)
				return;
			var iframe = $("<iframe></iframe>");
			var src= "/sys/attachment/viewer/audio_mp3.jsp?attId=" + this.file.fdId;
			iframe.attr("src", env.fn.formatUrl(src));
			this.mediaFrame = iframe;
			this.element.append(iframe);
			this.element.show();
			iframe.css("height", this.element.height());
			this.isDrawed = true;
			
			return this;
		}
	});
	
	var mediaVideo  = mediaBase.extend({
		mediaType : "video",
		
		hide : function($super) {
			
			if(this.mediaFrame) {
				var contentWindow = this.mediaFrame[0].contentWindow
				if(contentWindow.video && contentWindow.video.pause) {
					contentWindow.video.pause();
				}
			}
			
			if(!Com_Parameter.IE && 
					!(/\.mp4$/.test(this.file.fileName) || /\.m4v$/.test(this.file.fileName))) {
				//非ie的时候，flash类型的视频切换的时候会出现播放不了的问题，只能重现加载解决。。
				if(this.mediaFrame) {
					this.mediaFrame.attr("src", "");
				}
			}
			
			$super();
		},
		
		draw : function() {
			if(this.isDrawed) {
				if(!Com_Parameter.IE && 
						!(/\.mp4$/.test(this.file.fileName) || /\.m4v$/.test(this.file.fileName))) {
					var src= "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + this.file.fdId + "&viewer=video_viewer";
					this.mediaFrame.attr("src", env.fn.formatUrl(src));
				}
				return;
			} 
			var iframe = $("<iframe webkitallowfullscreen='true' name='mediaFrame' mozallowfullscreen='true' allowfullscreen='true'></iframe>");
			this.mediaFrame = iframe;
			var src= "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + this.file.fdId + "&viewer=video_viewer";
			iframe.attr("src", env.fn.formatUrl(src));
			
			this.element.append(iframe);
			this.element.show();
			iframe.css("height", this.element.height());
			this.isDrawed = true;
			return this;
		}
	});
	
	
	var fileMedias = {
			"image" : mediaImage,
			"office" : mediaOffice,
			"pdf" : mediaPdf,
			"mp3" : mediaMP3,
			"video": mediaVideo,
			"html" : mediaHtml,
			"cad" : mediaCad
	}
	module.exports = filePlay;
});