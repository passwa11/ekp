
define(function(require, exports, module) {

	/*************************************************
	 * 
	 * 
	 *   重新渲染 H5 video 开始
	 * 
	 */
	function __getUrlParameter(url, param)  {
		var re = new RegExp();
		re.compile("[\\?&]" + param + "=([^&]*)", "i");
		var arr = re.exec(url);
		if (arr == null)
			return null;
		else
			return decodeURIComponent(arr[1]);
	}

	function __fixVideoResize($container){
		var canUseHtml5Video = false;
		if(!!document.createElement('video').canPlayType){
			canUseHtml5Video = true;
		}
		var videos = $container.find("video");
		if(videos != null && videos.length > 0){	 
			videos.each(function(){				
				__handleVideoSrc($(this), canUseHtml5Video);
			});	
		}	
	}


	function __handleVideoSrc(video, canUseHtml5Video){
		var src = video.attr("src");	
		if(src){	
			var srcReg = /.*?\/resource\/fckeditor\/editor\/filemanager\/download.*?/;
			var fdId = __getUrlParameter(src, "fdId");
			if(srcReg.test(src) && fdId){
				if(!__isIE9OrIE8()){
					video.attr("preload","auto");
					video.removeAttr("src");
					video.attr("controlslist", "nodownload");			
					var poster = video.attr("poster");
					//不存在封面则设置封面
					if(!poster){		
						var posterUrl = Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdType=rtf&fdId=' + fdId;
						video.attr("poster",posterUrl);
					}
				}			
				$.getJSON(
						Com_Parameter.ContextPath +'sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdType=rtf&fdId=' + fdId,
						function(data) {					
						   if (data && data.token) {
						       var url = Com_Parameter.ContextPath + 'sys/attachment/mobile/viewer/play.jsp?fdType=rtf&token=' + data.token;
						       __startBuildVideo(video, url, canUseHtml5Video, src);	      
						   }else{
							   __startBuildVideo(video, src, canUseHtml5Video);
						   }
						},
						function(err){
							__startBuildVideo(video, src, canUseHtml5Video);
						}
					);	
			}
		}else{		
			var sourceNodes = video.find("source");	
			var isBuildIEVideo = false;
			for(var i=0; i < sourceNodes.length; i++){
				$source = $(sourceNodes[i]);
				src = $source.attr("src");
				if(src){
					var srcReg = /.*?\/resource\/fckeditor\/editor\/filemanager\/download.*?/;
					var fdId = __getUrlParameter(src, "fdId");
					if(srcReg.test(src) && fdId){
						var poster = video.attr("poster");
						//不存在封面则设置封面
						if(!poster){		
							var posterUrl = Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdType=rtf&fdId=' + fdId;
							video.attr("poster",posterUrl);
						}
						$.getJSON(
								Com_Parameter.ContextPath +'sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdType=rtf&fdId=' + fdId,
								function(data) {									
								   if (data && data.token) {
									   if(!isBuildIEVideo){
										   var url = Com_Parameter.ContextPath + 'sys/attachment/mobile/viewer/play.jsp?fdType=rtf&token=' + data.token;
										   $source.removeAttr("src");	
										   $source.attr("src",url);										      
									       if(__isIE9OrIE8()){
												__buildIEVideo(video, url);
									       }	
									       isBuildIEVideo = true;
									   }					      
								   }else{
									   if(__isIE9OrIE8() && !isBuildIEVideo){
											__buildIEVideo(video, src);	
											isBuildIEVideo = true;
										}								   
								   }
								}
							);	
					}else{
						if(__isIE9OrIE8() && !isBuildIEVideo){
							__buildIEVideo(video, src);	
							isBuildIEVideo = true;
						}
					}			
				}
			}
		}
	}

	/**
	 * 构建IE video
	 * @param video
	 * @param src
	 * @returns
	 */
	function __buildIEVideo(video, src){
		var width = video.attr("width");
		var height = video.attr("height");	
//		var embedHtml = '<embed autostart="false" src="' + src + '" type="video/mp4"';
//		if(width && height){
//			embedHtml += ' "units="pixels"';
//			embedHtml += ' width=' + width +' height=' + height;  
//		}
		var swfStrc = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/video/gddflvplayer.swf";
		var embedHtml = '<embed flashvars="?&amp;autoplay=false&amp;sound=70&amp;buffer=2&amp;vdo='
			+ encodeURIComponent(src) + '"'
			+ ' pluginspage="http://www.macromedia.com/go/getflashplayer" src="' + swfStrc + '" type="application/x-shockwave-flash" '
			
			if(width && height){
				embedHtml += 'height="' + height + '" width="' + width + '" ';
			}	
			embedHtml += ">";	
		video.before(embedHtml);
		video.remove();
	}

	/**
	 * 构建并且移除旧的video
	 * @param video
	 * @param src
	 * @returns
	 */
	function __buildAndRemoveVideo(video, src, oldSrc){
		var newVideo = $("<video></video>");
		if(video[0]){
			 for(var i=0; i < video[0].attributes.length; i++){
				var attribute = video[0].attributes[i];
				newVideo.attr(attribute.name, attribute.value);	
			 }
		 }	
		newVideo.append('<source src="' + src +'" type="video/mp4"/>');
		if(oldSrc){
			newVideo.append('<source src="' + oldSrc +'" type="video/mp4"/>');
		}
		video.before(newVideo);
		video.remove();
	}


	function __isIE9OrIE8(){
		if(navigator.appName == "Microsoft Internet Explorer"&&parseInt(navigator.appVersion.split(";")[1].replace(/[ ]/g, "").replace("MSIE",""))<10){
	       return true;
	    }
		return false;
	}

	function __startBuildVideo(video, src, canUseHtml5Video, src2){	
		if(canUseHtml5Video && !__isIE9OrIE8()){		 
			__buildAndRemoveVideo(video, src, src2);
		}else{	
			__buildIEVideo(video, src);
		}	
	}

	/**
	 * 
	 *   重新渲染 H5 video 结束
	 * 
	 ************************************************/
	exports.fixVideoResize = __fixVideoResize;
});