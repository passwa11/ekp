/**
 * CK 图片表格宽度压缩
 */
Com_RegisterFile("ckeditor/ckresize.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile("ckresize_lang.jsp", "ckeditor/");
Com_IncludeFile("ckresize.css", Com_Parameter.ContextPath
		+ "resource/ckeditor/resource/", "css", true);
CKResize = new Object();
CKResize.propertyNames = [];
CKResize.drawed = false;
CKResize.addPropertyName = function(property) {
	CKResize.propertyNames.push(property);
}

try {
	if(top['seajs']) {
		CKResize.top = top;
	} else {
		CKResize.top = window;
	}
} catch (e) {
	CKResize.top = window;
}


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

/**
 * video resize，兼容ie8 h5视频播放
 */
CKResize.__videoResize__ = function(name){
	var $rtf = $(document.getElementById('_____rtf_____' + name));
	CKResize.__fixVideoResize($rtf);
}

CKResize.__fixVideoResize = function($container){
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
//	var embedHtml = '<embed autostart="false" src="' + src + '" type="video/mp4"';
//	if(width && height){
//		embedHtml += ' "units="pixels"';
//		embedHtml += ' width=' + width +' height=' + height;  
//	}
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

CKResize.____ckresize____ = function(imgReader) {
	if (CKResize.drawed)
		return;
	
	for (var i = 0; i < CKResize.propertyNames.length; i++) {
		var name = CKResize.propertyNames[i];
		CKResize.__videoResize__(name);
		CKResize.____resetWidth____(name, imgReader);	
	}
	;
	CKResize.drawed = true;
}

CKResize.____resetWidth____ = function(name, imgReader) {
	
	var $temp = $(document.getElementById('_____rtf__temp_____' + name)), w = $temp.width();
	var sidebar = [];
	if(typeof LUI !== 'undefined'){
		sidebar = LUI.$(".lui_form_sidebar");
	}else{
		//移动端LUI可能会不存在将导致RTF中的内容无法显示，如果不存在时将导致sidebar.parent().css('display')报错
		//如果LUI不存在则获取jquery选择器获取".lui_form_sidebar" 元素，修复#172630
		sidebar = $(".lui_form_sidebar");
	}
	var disVal = sidebar.parent().css('display');
	if(sidebar.length > 0 && disVal != 'none'){
		var height = sidebar.height();
		if(height < 20){
			var tdContain = sidebar.parent();
			w = w-tdContain.width();
		}
	}
	
	var $rtf = $(document.getElementById('_____rtf_____' + name));
	if (w == 0) {
		setTimeout(function() {
			CKResize.____resetWidth____(name, imgReader);
		}, 100);
		return;
	}

	CKResize[name] = {};

	CKResize[name].tables = $rtf.find('table');

	CKResize[name].tables.each(function(i) {
		var ___css = {};
		if (this.width && this.width > w
				|| parseInt($(this).css('width')) > w) {
			___css.width = '100%';
			___css.height = 'auto';
		}
		$(this).css(___css);
	});

	CKResize[name].imgs = $([]);

	if (CKResize.extendImage)
		CKResize[name].imgs = $(CKResize.extendImage);

	CKResize[name]._tmplImgs = [];
	$rtf.find("img[contentEditable!='false']").each(function() {
			var src = $(this).attr('src');
			if(typeof(src)!='undefined' && src!=null && src!=''){
				// 过滤表情
				if (src.indexOf('/images/smiley/wangwang/') < 0
						&& $(this).attr('data-type') != 'face'){
					CKResize[name]._tmplImgs.push($(this)[0]);
				}
			}
	});

	CKResize[name].imgs = $.merge(CKResize[name].imgs, CKResize[name]._tmplImgs);
			
	// 获取鼠标位置
	function getMousePos(evt) {
		if (evt.pageX || evt.pageY)
			return {
				x : evt.pageX,
				y : evt.pageY
			};
	}

	// 获取对象高宽
	function getW2H(obj) {
		return {
			x : $(obj).width(),
			y : $(obj).height()
		}
	}

	function getOuterW2H(obj) {
		return {
			x : $(obj).outerWidth(),
			y : $(obj).outerHeight()
		}
	}

	// 获取对象位置
	function getPosition(obj) {
		return {
			x : $(obj).position().left,
			y : $(obj).position().top
		};
	}

	// 确定拖拽边缘
	function contain(evt, x2y_m, w2h, __w2h) {
		var x2y = getMousePos(evt);
		var css = {};

		var isW = w2h.x > __w2h.x, isH = w2h.y > __w2h.y;

		if (x2y.x - x2y_m.x > 0) {
			if (isW)
				css.left = 0;
			if (isH)
				css.top = x2y.y - x2y_m.y;
		}

		if (x2y.y - x2y_m.y > 0) {
			if (isW)
				css.left = css.left === 0 ? 0 : x2y.x - x2y_m.x;
			if (isH)
				css.top = 0;
		}

		if (w2h.y - __w2h.y < -(x2y.y - x2y_m.y)) {
			if (isH) {
				css.top = '';
				css.bottom = 0;
			}
			if (isW)
				css.left = css.left === 0 ? 0 : x2y.x - x2y_m.x;
		}

		if (w2h.x - __w2h.x < -(x2y.x - x2y_m.x)) {
			if (isW) {
				css.right = 0;
				css.left = '';
			}
			if (isH)
				css.top = css.top === 0 ? 0 : css.top === '' ? '' : x2y.y
						- x2y_m.y;
		}

		if (x2y.x - x2y_m.x <= 0 && x2y.y - x2y_m.y <= 0
				&& w2h.y - __w2h.y >= -(x2y.y - x2y_m.y)
				&& w2h.x - __w2h.x >= -(x2y.x - x2y_m.x)) {
			if (isW)
				css.left = x2y.x - x2y_m.x;
			if (isH)
				css.top = x2y.y - x2y_m.y;
		}

		return css;
	}

	// 缩略图确定拖拽边缘
	function __contain(evt, x2y_m, w2h, ___w2h, __pos, __w2h) {
		var x2y = getMousePos(evt);
		var pos = getPosition($(evt.target));
		// 是否已显示区域比图片宽或高
		var isW = w2h.x > ___w2h.x, isH = w2h.y > ___w2h.y;
		var css = {};
		if (isW) {
			css.left = pos.x;
		} else {
			if (x2y.x - x2y_m.x <= __pos.x) {
				css.right = '';
				css.left = __pos.x;
			}
			if (x2y.x - x2y_m.x + w2h.x + 6 >= __pos.x + ___w2h.x) {
				css.right = __w2h.x - (__pos.x + ___w2h.x);
				css.left = '';
			}
		}
		if (isH) {
			css.top = pos.y;
		} else {
			if (x2y.y - x2y_m.y <= __pos.y) {
				css.top = __pos.y;
				css.bottom = '';
			}
			if (x2y.y - x2y_m.y + w2h.y + 6 >= __pos.y + ___w2h.y) {
				css.bottom = __w2h.y - (__pos.y + ___w2h.y);
				css.top = '';
			}
		}
		if (!css.top && css.top != 0 && !css.bottom && css.bottom != 0)
			css.top = x2y.y - x2y_m.y;
		if (!css.left && css.left != 0 && !css.right && css.right != 0)
			css.left = x2y.x - x2y_m.x;
		return css;
	}

	// 重置图片位置~~垂直水平居中
	function __resizeImg(__img, w, h) {
		var iw = parseInt(w) - 60;
		var ih = parseInt(h);
		var __width = __img.width(), __height = __img.height();
		if (__width === 0 && __height === 0) {
			setTimeout(function() {
				__resizeImg(__img, w, h);
			}, 1);
			return;
		}

		__img.css({
			'left' : (iw - __width) / 2,
			'top' : (ih - __height) / 2
		});
		__img.on({
			'mousedown' : function(evt) {
				var $target = $(evt.target);
				var x2y = getMousePos(evt);
				var pos = getPosition($target);
				x2y_m = {
					x : x2y.x - pos.x,
					y : x2y.y - pos.y
				};
				__w2h = getW2H($target.parents('.ckeditor_kanban'));
				w2h = getW2H($target);
				$target.css('cursor', 'move');
				CKResize.status = true;
			},
			'mouseup' : function(evt) {
				CKResize.status = false;
				$(evt.target).css('cursor', 'default');
				moveSelected();
			},
			'mousemove' : function(evt) {
				if (!CKResize.status)
					return false;
				var $target = $(evt.target);
				$target.css(contain(evt, x2y_m, w2h, __w2h));
				return false;
			}
		});
	}

	// 构建缩略图显示区域
	function buildSelected(timg) {
		var $container = $('.ckeditor_container');
		var bdiv = $container.find('.ckeditor_bdiv'), bimg = bdiv.find('img');
		var w2h = getW2H(bimg), __w2h = getW2H(bdiv);
		var width = w2h.x, height = w2h.y, __width = __w2h.x, __height = __w2h.y;
		var i2d_w_rate = width / __width, i2d_h_rate = height / __height;
		var selected = $('<div class="ckeditor_selected" />');
		var ___w2h = getW2H(timg), ___x2y = getPosition(timg);
		var t2l = getSelected(timg, bimg);
		selected.css({
			'width' : ___w2h.x / i2d_w_rate - 6,
			'height' : ___w2h.y / i2d_h_rate - 6,
			'top' : t2l.top,
			'left' : t2l.left
		});
		$container.find('.ckeditor_thumb').append(selected);
		selectedBind(selected);
	}

	// 绑定显示区域拖动事件
	function selectedBind(selected) {
		var $selected = $(selected);
		var t_x2y_m, t___w2h, t_w2h, t__w2h, t___pos, t__pos;
		$selected.on({
			'mousedown' : function(evt) {
				var $target = $(evt.target);
				var x2y = getMousePos(evt);
				var pos = getPosition($target);
				t_x2y_m = {
					x : x2y.x - pos.x,
					y : x2y.y - pos.y
				};
				var img = $target.prev('.ckeditor_thumb_img');
				t__w2h = getW2H($target.parents('.ckeditor_thumb'));
				t___pos = getPosition(img);
				t___w2h = getW2H(img);
				t__pos = getPosition($target);
				t_w2h = getOuterW2H($target);
				$target.css('cursor', 'move');
				CKResize.t_status = true;
			},
			'mouseup' : function(evt) {
				CKResize.t_status = false;
				$(evt.target).css('cursor', 'default');
				__moveSelected(evt, t__pos);
			},
			'mousemove' : function(evt) {
				if (!CKResize.t_status)
					return false;
				var $target = $(evt.target);
				$target.css(__contain(evt, t_x2y_m, t_w2h, t___w2h, t___pos,t__w2h));
				return false;
			}
		});
	}

	function getSelected(timg, bimg) {
		var x2y = getPosition(bimg), ___w2h = getW2H(timg), ___x2y = getPosition(timg), w2h = getW2H(bimg);
		var left = -x2y.x, top = -x2y.y;
		var w_rate = w2h.x / ___w2h.x, h_rate = w2h.y / ___w2h.y;
		return {
			top : top / h_rate + ___x2y.y,
			left : left / w_rate + ___x2y.x
		};
	}

	function __getSelected(timg, bimg, selected, t__pos) {
		var x2y = getPosition(bimg), ___w2h = getW2H(timg), ___x2y = getPosition(selected), w2h = getW2H(bimg);
		var w_rate = w2h.x / ___w2h.x, h_rate = w2h.y / ___w2h.y;
		return {
			top : (t__pos.y - ___x2y.y) * h_rate + x2y.y,
			left : (t__pos.x - ___x2y.x) * w_rate + x2y.x
		};
	}

	// 移动缩略图显示区域
	function moveSelected() {
		var $container = $('.ckeditor_container');
		var t2l = getSelected($container.find('.ckeditor_thumb_img'),$container.find('.ckeditor_bdiv img'));
		$container.find('.ckeditor_selected').css(t2l);
	}

	function __moveSelected(evt, t__pos) {
		var $container = $('.ckeditor_container');
		var t2l = __getSelected($container.find('.ckeditor_thumb_img'),
						$container.find('.ckeditor_bdiv img'), 
						$container.find('.ckeditor_selected'),
						t__pos);
						
		$container.find('.ckeditor_bdiv img').css(t2l);
	}

	function showThumb(src, name) {
		if (!getIsMagnify(name))
			return;
		var ___tdiv = $('.ckeditor_container .ckeditor_thumb');
		if (!___tdiv.html()) {
			var ___timg = $("<img src=\"" + src + "\"  border=0 class='ckeditor_thumb_img' >");
			___tdiv.append(___timg);
			___timg.load(function() {
				buildSelected(this);
			});
		}
		___tdiv.show();
	}

	function hideThumb() {
		$('.ckeditor_container .ckeditor_thumb').hide();
	}

	function destroyThumb() {
		$('.ckeditor_container .ckeditor_thumb').html('');
		$('.ckeditor_container .ckeditor_thumb').hide();
	}

	function showToolbar() {
		$('.ckeditor_container .ckeditor_toolbar').show();
	}

	function hideToolbar() {
		$('.ckeditor_container .ckeditor_toolbar').hide();
	}

	// 重置缩略图
	function resetThumb(src, name) {
		destroyThumb();
		showThumb(src, name);
	}

	function showImg(img, src) {
		if (src)
			img.attr('src', src);
		imgUnbind(img);
	}

	// 重置图片
	function resetImg(img, src) {
		showImg(img, src);
		imgBind(img);
	}

	// 绑定拖拽事件
	function imgBind(img) {
		__resizeImg(img, CKResize.dialog_width, CKResize.dialog_height);
	}

	// 解绑拖拽事件
	function imgUnbind(img) {
		img.off();
	}

	function getCurrentImage(name) {
		return CKResize[name].imgs[CKResize.top.$('.ckeditor_container').attr(
				CKResize.INDEX)];
	}

	function setIsMagnify(type, name) {
		CKResize[name].imgs[CKResize.top.$('.ckeditor_container').attr(CKResize.INDEX)].isMagnify = type;
	}

	function clearMagnify(name) {
		for (var i = 0; i < CKResize[name].imgs.length; i++) {
			CKResize[name].imgs[i].isMagnify = false;
		}
	}

	function getIsMagnify(name) {
		return CKResize[name].imgs[CKResize.top.$('.ckeditor_container').attr(CKResize.INDEX)].isMagnify;
	}
    
	function hideOrShowImgScroll(isShow){		
		var ___leftScroll = CKResize.top.$(".ckeditor_left_scroll");
		var ___rightScroll = CKResize.top.$(".ckeditor_right_scroll");
		if (isShow==true){
			___leftScroll.show();
			___rightScroll.show();
		}else{
			___leftScroll.hide();
			___rightScroll.hide();
		}		
	} 
	
	CKResize.INDEX = "data-ckeditor-imgs-index";
	CKResize.rotate = 0;
	CKResize.plugins = [ {
		'currentClass' : 'magnify',
		'toggleClass' : 'shrink',
		'event' : function(evt, nn) {
			var $target = $(evt.target);
			var className = $target.attr('data-ckeditor-toggle-class');
			if (!getIsMagnify(nn)) {
				setIsMagnify(true, nn);
				magnifyBimg();
				// 绑定图片拖动事件
				// resetImg(CKResize.___bimg, getCurrentImage(nn).src);
				// showThumb(getCurrentImage(nn).src, nn);
			} else {
				setIsMagnify(false, nn);
				shrinkBimg();
				// imgUnbind(CKResize.___bimg);
				// hideThumb();
			}
		}
	} ];

	function magnifyBimg(___bdiv, ___bimg) {
		if (!___bdiv && !___bimg)
			___bdiv = CKResize.top.$('.ckeditor_container .ckeditor_bdiv'),
					___bimg = ___bdiv.find('img');
		var c = ___bimg.css('transform');
		if(c != 'none' && c) {
			c = c.split('(')[1].split(')')[0].split(',');
			c = getmatrix(c[0],c[1],c[2],c[3],c[4],c[5])
			var needReset = ((c/10) % 2 ==0) ? false : true ;
			var max_line_height = ___bimg[0]['naturalWidth'] > CKResize.dialog_height - 2 ? ___bimg[0]['naturalWidth'] : CKResize.dialog_height - 2;
			if(needReset) {
				___bdiv.css({
					'overflow' : 'auto',
					'line-height': max_line_height +'px'
				});	
			}else {
				___bdiv.css({
					'overflow' : 'auto'
				});	
			}
		} else {
			___bdiv.css({
				'overflow':''
			})
		}
		$(___bimg.parents()[0]).css({
			
		});
		if(IEVersion() != '-1') {
			___bimg.css({
				'max-height':'',
				'max-width':'',
				'height':'auto',
				'width':'auto',
				'padding':'0 500px',
				'overflow' : 'auto'
			});
		}else {
			___bimg.css({
				'max-height' : '',
				'max-width' : ''
			});	
		}
		
		___bdiv.removeClass('nomal');
		hideOrShowImgScroll(false);
	}


	//获取图片角度
	function getmatrix(a,b,c,d,e,f){  
        var aa=Math.round(180*Math.asin(a)/ Math.PI);  
        var bb=Math.round(180*Math.acos(b)/ Math.PI);  
        var cc=Math.round(180*Math.asin(c)/ Math.PI);  
        var dd=Math.round(180*Math.acos(d)/ Math.PI);  
        var deg=0;  
        if(aa==bb||-aa==bb){  
            deg=dd;  
        }else if(-aa+bb==180){  
            deg=180+cc;  
        }else if(aa+bb==180){  
            deg=360-cc||360-dd;  
        }  
        return deg>=360?0:deg;  
	}
	
	function shrinkBimg(___bdiv, ___bimg) {
		if (!___bdiv && !___bimg)
			___bdiv = CKResize.top.$('.ckeditor_container .ckeditor_bdiv'),
			___bimg = ___bdiv.find('img');
		___bdiv.css({
			'line-height' : CKResize.dialog_height - 2 + 'px'
		});
		___bimg.css({
			'position':'',
			'left':'',
			'top':''
		});
		var orgMaxHeight = ___bimg[0]['org-max-height'];
		if(IEVersion() != '-1') {
			___bimg.css('max-height',(orgMaxHeight!= null)? orgMaxHeight : CKResize.dialog_height - 4)
			___bimg.css('max-width',"100%")
			___bimg.css('height','')
			___bimg.css('width','')
			___bimg.css('padding','unset')
		}else {
			___bimg.css({
				'max-height' :(orgMaxHeight!= null)? orgMaxHeight : CKResize.dialog_height - 4,
				'max-width' : "100%"
			});
		}
		var c = ___bimg.css('transform')
		if(c != 'none' && c) {
			c = c.split('(')[1].split(')')[0].split(',');
			c = getmatrix(c[0],c[1],c[2],c[3],c[4],c[5])
			var needReset = ((c/10) % 2 ==0) ? false : true ;
			var max_line_height = ___bimg[0]['clientWidth'] > CKResize.dialog_height - 2 ? ___bimg[0]['clientWidth'] : CKResize.dialog_height - 2;
			if(needReset) {
				___bdiv.css({
					'overflow' : 'auto',
					'line-height': max_line_height +'px'
				});	
			}else {
				___bdiv.css({
					'line-height' : CKResize.dialog_height - 2 + 'px',
					'overflow':'auto'
				});
			}
		} else {
			___bdiv.css({
				'overflow':''
			})
		}
		___bdiv.addClass('nomal');
		___bimg.removeClass('transform_big_img')
		hideOrShowImgScroll(true);
	}

	function clearToolbar() {
		clearMagnify(name);
		CKResize.___toolbarPlugins.find('div').each(function() {
			$(this).removeClass($(this).attr('data-ckeditor-toggle-class'));
			$(this).attr('title', Ckresize_lang.size);
		});
	}

	function hideScroll() {
		$('body').css('overflow-y', 'hidden');
	}

	function showScroll() {
		$('body').css('overflow-y', 'auto');
	}

	// 显示“跳转到第一页”提示
	function showBackTip(tipMsg) {
		var time = 1000;
		var $tip = CKResize.top.$('.ckeditor_tip');
		if ($tip.length == 0){
			$tip = $('<div class="ckeditor_tip"><span name="backTip">' + tipMsg + '</span></div>')
			   .appendTo(CKResize.top.$('.ckeditor_kanban'));
		}else{
			$tip.find('span[name="backTip"]').text(tipMsg);
		}
		$tip.show();
		setTimeout(function() {
			$tip.slideUp(time);
		}, 1000);
	}
	
	function replaceParamVal(oUrl,paramName,replaceWith) {
	    var re=eval('/('+ paramName+'=)([^&]*)/gi');
	    var nUrl = oUrl.replace(re,paramName+'='+replaceWith);
	    return nUrl;
	}
	
	function formatSrc(imgSrc) {
		// 排除base64生成的图片
		if (imgSrc.indexOf("data:image") > -1) {
		} else{
			imgSrc=replaceParamVal(imgSrc,"picthumb","original");
		}
		return imgSrc;
	}
	function IEVersion() {
	      var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
	      var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
	      var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
	      var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
	      if(isIE) {
	          var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
	          reIE.test(userAgent);
	          var fIEVersion = parseFloat(RegExp["$1"]);
	          if(fIEVersion == 7) {
	              return 7;
	          } else if(fIEVersion == 8) {
	              return 8;
	          } else if(fIEVersion == 9) {
	              return 9;
	          } else if(fIEVersion == 10) {
	              return 10;
	          } else {
	              return 6;//IE版本<=7
	          }   
	      } else if(isEdge) {
	          return 'edge';//edge
	      } else if(isIE11) {
	          return 11; //IE11  
	      }else{
	          return -1;//不是ie浏览器
	      }
	  }
	//图片高度由于旋转导致的图片越界
    function resetImgHeight(___img,angle,___div){
    	var needReset = ((angle/10) % 2 ==0) ? false : true ;
        var height
        if (needReset) {
        	if(___img.width > CKResize.dialog_height) {
        		___div.css({
        			'line-height' : ___img.width + 'px',
        			'overflow':'auto'
        		});
        	}else {
        		___div.css({
        			'line-height' : CKResize.dialog_height - 2 + 'px'
        		});
        	}
			if (IEVersion()) {
				height = CKResize.dialog_height;
			} else {
				height = CKResize.dialog_height * .5;
			}
		} else {
			___div.css({
    			'line-height' : CKResize.dialog_height - 2 + 'px',
    			'overflow':'auto'
    		});
			if (IEVersion()) {
				height = CKResize.dialog_height;
			} else {
				height = CKResize.dialog_height * .5;
			}
		}
        /*$(___img).css({
			'max-height' :	height						
		});*/
        ___img['org-max-height'] = needReset? height: null; //记下旋转之后的max-height;
    
    }
	
	function initImgSizeZoom(img){
		var h = 0;
		var w = 0;
		img.onload = function(){
			$(img).css({
				'height' : '',
				'width' : ''
			})
			getImgNaturalDimensions(img,function(e){
				h = e.h;
				w = e.w;
			})
			var oh = img.clientHeight;
			var ow = img.clientWidth;
			var p = Math.ceil(oh/h*100);
			if(h>0||w>0){
				CKResize.___toolBox.find(".ckeditor_zoom_input")[0] ? CKResize.___toolBox.find(".ckeditor_zoom_input")[0].value = p : null ;
			}else{
				setTimeout(function() {
					initImgSizeZoom(img);
				}, 100);
			}
		}
	}
	
	function getImgNaturalDimensions(oImg, callback) {
		var nWidth, nHeight;
		if (oImg.naturalWidth) { // 现代浏览器
			nWidth = oImg.naturalWidth;
			nHeight = oImg.naturalHeight;
			callback({w: nWidth, h:nHeight});
		} else { // IE6/7/8
			var nImg = new Image();
			nImg.onload = function() {
				var nWidth = nImg.width,
					nHeight = nImg.height;
				callback({w: nWidth, h:nHeight});
			}
			nImg.src = oImg.src;
		}
	}
    
    function zoomImg(img,type){
    	var _ck_old = [],_ck_new = [];
    	_ck_old.per = parseInt(CKResize.___toolBox.find(".ckeditor_zoom_input")[0].value);
    	_ck_new.per = 0;
    	var css = {};
		getImgNaturalDimensions(img[0],function(e){
			_ck_old.nh = e.h;
			_ck_old.nw = e.w;
		})
    	if (type == "0"){
    		//放大10%
    		_ck_new.per = _ck_old.per + 10;
    		if(_ck_new.per > 300){
    			_ck_new.per = 300;
    		}
    		css['max-height'] = '';
    		css['max-width'] = '';
    		css.height = _ck_old.nh * _ck_new.per / 100;
    		css.width = _ck_old.nw * _ck_new.per / 100;
        	img.css(css);
        	img.parent('div').css("overflow","auto");
    		CKResize.___toolBox.find(".ckeditor_zoom_input")[0].value = _ck_new.per;
    	} else if (type == "1"){
    		//缩小10%
    		_ck_new.per = _ck_old.per - 10;
    		if(_ck_new.per < 10){
    			_ck_new.per = 10;
    		}
    		css['max-height'] = '';
    		css['max-width'] = '';
    		css.height = _ck_old.nh * _ck_new.per / 100;
    		css.width = _ck_old.nw * _ck_new.per / 100;
        	img.css(css);
        	img.parent('div').css("overflow","auto");
    		CKResize.___toolBox.find(".ckeditor_zoom_input")[0].value = _ck_new.per;
    	} else if (type == "2"){
    		if(_ck_old.per<10){
    			_ck_new.per = 10
    		}else if (_ck_old.per > 300){
    			_ck_new.per = 300
    		}else{
    			_ck_new.per = _ck_old.per
    		}
    		css['max-height'] = '';
    		css['max-width'] = '';
    		css.height = _ck_old.nh * _ck_new.per / 100;
    		css.width = _ck_old.nw * _ck_new.per / 100;
        	img.css(css);
        	img.parent('div').css("overflow","auto");
    	} else {
    		return false;
    	}
    }
    
	function dragImg(obj){
		obj.onmousedown=function(event){    //鼠标被按下
			 event = event || window.event;
	
			//div的水平偏移量  鼠标.clentX-元素.offsetLeft
			//div的垂直偏移量  鼠标.clentY-元素.offsetTop
			var ol=event.clientX-obj.offsetLeft;
			var ot=event.clientY-obj.offsetTop;

			obj.style.left=event.clientX-ol+"px";
			obj.style.top=event.clientY-ot+"px";
			obj.style.position = "absolute";
			
			//绑定鼠标移动事件
			document.onmousemove=function(event){
				event = event || window.event;
				var left=event.clientX-ol;
				var top=event.clientY-ot;
				obj.style.left=left+"px";
				obj.style.top=top+"px";
			}
		
			//为document绑定一个鼠标松开事件
			document.onmouseup=function(){
				//当鼠标松开时，被拖拽元素固定在当前位置
				//取消document.onmousemove事件
				document.onmousemove=null;
				document.onmouseup=null;
			}
		}
	}
	CKResize[name].imgs.each(function(i) {
		var ___css = { 'max-width' : "100%" };
		
		var w_css = parseInt($(this).css('width'))
		if ((!w_css && this.width && this.width > w) || w_css > w) {
			___css.width = '100%';
			___css.height = '100%';
		}
		$(this).css(___css);
		if (!imgReader)
			return true;
		$(this).css('cursor', 'pointer');
		// 绑定图片点击事件
		$(this).on('click',function() {
				
				// 图片绑定了链接，则点击不显示图片，只跳转
				var itemParentNode = this.parentNode;
				if(itemParentNode.nodeName=="A" && itemParentNode.href != ""){
					return;
				}
				
				hideScroll();
				// 定义弹出框高宽信息
				CKResize.dialog_width = $(top).width(),
				CKResize.dialog_height = $(top).height();

				var __i = 0, len = CKResize[name].imgs.length;
				for (var i = 0; i < len; i++) {
					if (CKResize[name].imgs[i] == this) {
						__i = i;
						break;
					}
				}
				var MOUSEUP = "data-ckeditor-mouseup";
				var ___pos = {};
				var ___container = $('<div class="ckeditor_container" ' + MOUSEUP + '="ckeditor_container_up"/>');
				___container.attr(CKResize.INDEX, __i);
				var ___kanban = $('<div class="ckeditor_kanban" />');
				CKResize.___bdiv = $('<div class="ckeditor_bdiv"/>');
				var imgSrc = this.src;
				CKResize.___bimg = $("<img src=\"" + formatSrc(imgSrc) + "\"  border=0 draggable='false' >");
				CKResize.___bimg.bind("contextmenu",function(e){
					return false;
				});
				var ___left = $('<a href="javascript:;" class="ckeditor_left" />');
				var ___right = $('<a href="javascript:;" class="ckeditor_right" />');

				CKResize.___toolBox = $('<div class="ckeditor_tool_box" />');
				
				var ___leftScroll = $('<span class="ckeditor_left_scroll"><i></i></span>');
				var ___rightScroll = $('<span class="ckeditor_right_scroll"><i></i></span>');
				var ___zoomPlus = $('<span class="ckeditor_zoom_plus"><i></i></span>');
				var ___zoomNam = $('<span class="ckeditor_zoom_nam"><span><input type="text" value="100" class="ckeditor_zoom_input">%</span></span>');
				var ___zoomMinus = $('<span class="ckeditor_zoom_minus"><i></i></span>');
				if(IEVersion() < 9 && IEVersion() > 0){
					CKResize.___toolBox.append(___leftScroll).append(___rightScroll);
				}else{
					CKResize.___toolBox.append(___leftScroll).append(___zoomPlus).append(___zoomNam).append(___zoomMinus).append(___rightScroll);
				}
				// var ___tdiv = $('<div
				// class="ckeditor_thumb"/>');
				
				clearMagnify(name);
				shrinkBimg(CKResize.___bdiv,CKResize.___bimg);
						

				// 工具栏
				CKResize.___toolbar = $('<div class="ckeditor_toolbar"/>');
				CKResize.___toolbarPlugins = $('<div class="ckeditor_toolbar_plugins" />');

				for (var j = 0; j < CKResize.plugins.length; j++) {
					var plugin = $('<div class="' + CKResize.plugins[j].currentClass
							+ '" data-ckeditor-toggle-class="' + CKResize.plugins[j].toggleClass
							+ '" title="' + Ckresize_lang.size + '"/>');
					
					~~function(jj, nn) {
						plugin.click(function(evt) {
							CKResize.plugins[jj].event(evt, nn);
							var $target = $(evt.target);
							var toggleClass = $target.attr('data-ckeditor-toggle-class');
									
							if ($target.hasClass(toggleClass)) {
								$target.removeClass(toggleClass);
								$target.attr('title',Ckresize_lang.size);
							} else {
								$target.addClass(toggleClass);
								$target.removeAttr('title');
							}
						});
					}(j, name);
					
					CKResize.___toolbarPlugins.append(plugin);
							
				};
				

				CKResize.close = $('<div class="ckeditor_close" title="' + Ckresize_lang.close + ' "/>');
				
				if(IEVersion() < 9 && IEVersion() > 0){
					___container.append(___kanban.append(CKResize.___bdiv.append(CKResize.___bimg)))
								.append(CKResize.close)
								.append(CKResize.___toolbarPlugins)
								.append(___left)
								.append(___right)
								.append(CKResize.___toolBox);
				}else{
					___container.append(___kanban.append(CKResize.___bdiv.append(CKResize.___bimg)))
								.append(CKResize.close)
								.append(___left)
								.append(___right)
								.append(CKResize.___toolBox);
					dragImg(CKResize.___bimg[0]);
				}
				

				// 显示隐藏左右箭头
				___container.on({
					'mouseover' : function() {
						$(this).addClass($(this).attr(MOUSEUP));
						// showThumb(self.src, name);
						// showToolbar();
					},
					'mouseleave' : function() {
						$(this).removeClass($(this).attr(MOUSEUP));
						// hideThumb();
						// hideToolbar();
					}
				});

				var x2y_m, __w2h, w2h;
				// 是否处于拖动状态
				CKResize.status = false;

				~~function(nn) {
					___left.on('click',function() {
						var parents = $(this).parents('['+ CKResize.INDEX + ']');
						var __index = parseInt(parents.attr(''+ CKResize.INDEX + ''));
						if (__index >= 1) {
							shrinkBimg(CKResize.___bdiv,CKResize.___bimg);
							clearToolbar();
							// 重置原图信息
							showImg(CKResize.___bimg,formatSrc(CKResize[nn].imgs[__index - 1].src));
							// 设置当前阅读图片序号
							parents.attr(CKResize.INDEX,__index - 1);
							// 重置缩略图信息
							resetThumb(CKResize[nn].imgs[__index - 1].src,nn);
							rotate(CKResize.___bimg[0],0);
							currPos = 0;
							// 重置缩略图比例信息
							initImgSizeZoom(CKResize.___bimg[0]);
						}else{
							// 第一张图片给予提示
							showBackTip(Ckresize_lang.first);
						}
					});

					___right.on('click',function() {
							var parents = $(this).parents('[' + CKResize.INDEX + ']');
							var __index = parseInt(parents.attr('' + CKResize.INDEX + ''));
							if (__index >= len - 1) {
								__index = -1;
								// 跳转到第一页
								showBackTip(Ckresize_lang.tip);
							}
							shrinkBimg(CKResize.___bdiv,CKResize.___bimg);
							clearToolbar();
							showImg(CKResize.___bimg,formatSrc(CKResize[nn].imgs[__index + 1].src));
									
							parents.attr(CKResize.INDEX,__index + 1);
							resetThumb(CKResize[nn].imgs[__index + 1].src,nn);
							rotate(CKResize.___bimg[0],0);
							currPos = 0;
							// 重置缩略图比例信息
							initImgSizeZoom(CKResize.___bimg[0]);
					});
					var currPos = 0;
					___leftScroll.on('click',function() {
						currPos = currPos-90;
						if (currPos <= -360){
							currPos = 0;
						}
						rotate(CKResize.___bimg[0],currPos);
						resetImgHeight(CKResize.___bimg[0],currPos,CKResize.___bdiv);
					});
					___rightScroll.on('click',function() {
						currPos = currPos+90;
						if (currPos >= 360){
							currPos = 0;
						}
						rotate(CKResize.___bimg[0],currPos);
						resetImgHeight(CKResize.___bimg[0],currPos,CKResize.___bdiv);
					});

					//图片放大缩小
					___zoomPlus.on("click",function(){
						zoomImg(CKResize.___bimg,"0");
					});
					___zoomMinus.on("click",function(){
						zoomImg(CKResize.___bimg,"1");
					});
					___zoomNam.find("input").on("input",function(){
						//判断数字
						var val = $(this).val();
						val = val.replace(/[^0-9#]/g,'');
						val = Number(val) > 300? 300 : (Number(val)<0?0:Number(val));
						$(this).val(val);
						zoomImg(CKResize.___bimg,"2");
					});
				}(name);
				
				//---下面是浏览器旋转功能的实现
				var rotate = (function() {
					var userAgent = navigator.userAgent.toLocaleLowerCase();
				    if (userAgent.indexOf('trident')>=0) {
	    	           if (IEVersion() < 9) {
	    	        	   return function(dom, angle) { 
	    	        		   var p= parseInt(angle/90);		    	        		   
	    	        		   dom.style.filter = "progid:DXImageTransform.Microsoft.BasicImage(rotation="+Math.abs(p)+")";
					        }; 
		    		   }else{
		    			   return function(dom, angle) {
					            dom.style.msTransform = 'rotate(' + angle + 'deg)';
					        };  
		    		   }			    	
				    }else if (userAgent.indexOf('webkit')>=0) { 
				        return function(dom, angle) {  
				            dom.style.webkitTransform = angle != 0 ? 'rotate(' + angle + 'deg)' : '';	
				        };  
				    } else if (userAgent.indexOf('gecko')>=0) { 
				        return function(dom, angle) {  
				            dom.style.MozTransform = 'rotate(' + angle + 'deg)';  
				        };  
				    }else if (userAgent.indexOf('presto')>=0) { 
				        return function(dom, angle) {  
				            dom.style.OTransform = 'rotate(' + angle + 'deg)';  
				        };  
				    } else{  
				        return function(dom, angle) {  
				            dom.style.transform = angle != 0 ? 'rotate(' + angle + 'deg)' : '';  
				        };  
				    }  
				})();  
				//---

				CKResize.top.seajs.use('lui/dialog',
					function(__dialog) {
						var $dialog = __dialog.build({config : {
										width : CKResize.dialog_width,
										height : CKResize.dialog_height,
										lock : true,
										cache : false,
										content : {
											type : "Element",
											elem : ___container
										}
									}}).show();
						
						$dialog.on('show',function() {
									$dialog.element.find('.lui_dialog_frame').css('border','none').css('background-color','rgba(0, 0, 0, 0.3)');
									$dialog.element.css("position",'fixed');   // 161606 防止图片背景下移
									$dialog.element.css("top",'0');
									$dialog.element.css("zIndex",'19999');  //图片预览模式下置于最顶层
									initImgSizeZoom(CKResize.___bimg[0]);
							});

						CKResize.close.on('click',function() {
								$dialog.hide();
								showScroll();
						});
					});
			});
	});
	if($rtf[0]){
		var n = $rtf[0].firstChild;
		if (n) {
			var r = [];
			for (; n; n = n.nextSibling) {
				if (n.nodeType === 1 || n.nodeType === 3) {
					r.push(n);
				}
			}
			for (var i = r.length - 1; i >= 0; i--) {
				$(r[i]).css({'word-wrap' : 'break-word','overflow-y' : 'hidden','overflow-x' : 'auto'});
				$(r[i]).insertAfter($temp);
			}
		}
		$rtf.remove();
		$temp.remove();		
	}
};
