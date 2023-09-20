var commonFuncs = {
	lastScreenWidth : 0,
	globalTimer : null,
	initialHandlers :function(){
		var browser=commonFuncs.getBrowser();
		if(browser!="IE"){
			commonFuncs.registerEventHandler(document,"fullscreenchange",commonFuncs.fullScreenChangeHandlerExceptIE);
			commonFuncs.registerEventHandler(document,"webkitfullscreenchange",commonFuncs.fullScreenChangeHandlerExceptIE);
			commonFuncs.registerEventHandler(document,"mozfullscreenchange",commonFuncs.fullScreenChangeHandlerExceptIE);
		}
		commonFuncs.bindResizeEvent();
	},
	getAvailableWidth : function(){
		var temp;
		try{
			temp=document.body;
		}catch(e){
			temp=document.documentElement;
		}

		return temp.clientWidth;
	},
	isIE : function(){
		return commonFuncs.getBrowser()=="IE";
	},
	direct : "next",
	head : document.getElementsByTagName('head')[0],
	onAuthentication : function(){
		return canCopy=="true"?true:false;
	},
	addCss : function(cssName,path) {
		var cssEle=document.getElementById(cssName);
		if(cssEle==null){
			if (!path || path.length === 0) {
				throw new Error('argument "path" is required !');
			}
			var link = document.createElement('link');
			link.href = path;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.id=cssName;
			commonFuncs.head.appendChild(link);
		}
	},
	addJs : function(jsName,path,gotoPageNum) {
		var jsEle=document.getElementById(jsName);
		if(jsEle==null){
			if (!path || path.length === 0) {
				throw new Error('argument "path" is required !');
			}
			var script = document.createElement('script');
			script.src = path;
			script.type = 'text/javascript';
			script.id=jsName;
			script.param=gotoPageNum;
			commonFuncs.head.appendChild(script);
		}else{
			commonFuncs.head.removeChild(jsEle);
			if (!path || path.length === 0) {
				throw new Error('argument "path" is required !');
			}
			var script = document.createElement('script');
			script.src = path;
			script.type = 'text/javascript';
			script.id=jsName;
			script.param=gotoPageNum;
			commonFuncs.head.appendChild(script);
		}
	},
	removeCssOrJsFile : function(fileName){
		var removeEle=document.getElementById(fileName);
		if(removeEle!=null){
			commonFuncs.head.removeChild(removeEle);
		}
	},
	contains : function(string, substr, isIgnoreCase){
		if (isIgnoreCase) {
			string = string.toLowerCase();
			substr = substr.toLowerCase();
		}
		var startChar = substr.substring(0, 1);
		var strLen = substr.length;
		for (var j = 0; j < string.length - strLen + 1; j++) {
			if (string.charAt(j) == startChar)//如果匹配起始字符,开始查找
			{
				if (string.substring(j, j + strLen) == substr)//如果从j开始的字符与str匹配，那ok
				{
					return true;
				}
			}
		}
		return false;
	},
	hasUrlParam : function(url,paramName){
		var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
		var r = url.match(reg);
		return r!=null;
	},
	getUrlParam : function(url,paramName){
		var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
		var r = url.match(reg);
		if(r!=null){
			return unescape(r[2]);
		}
		return "";
	},
	replaceUrlParamVal : function(oldUrl,paramName,replaceWith){
		var oUrl = oldUrl;
		var nUrl;
		if(commonFuncs.hasUrlParam(oUrl,paramName)){
			var re=eval('/('+ paramName+'=)([^&]*)/gi');
			nUrl = oUrl.replace(re,paramName+'='+replaceWith);
		}else{
			nUrl=oUrl;
		}
		return nUrl;
	},
	getBrowser : function(){
		var userAgent = navigator.userAgent;
		if(userAgent.indexOf("Firefox") > -1){
			return "Firefox";
		}
		if(userAgent.indexOf("Chrome") > -1){
			return "Chrome";
		}
		if(userAgent.indexOf("Opera") > -1){
			return "Opera";
		}
		if(userAgent.indexOf("Safari") > -1){
			return "Safari";
		}
		if(!!window.ActiveXObject || "ActiveXObject" in window){
			return "IE";
		}
		return "Other";
	},
	setWaterMarkBody : function(doc){
		seajs.use('lui/jquery',function($){
			$(".mask_div").remove();
			$(doc).find(".mask_div").remove();
		});
		var oTemp = doc.createDocumentFragment();
		//获取页面最大宽度
		var mark_width = Math.max($(doc).width(),$(window).width());
		//获取页面最大长度
		var mark_height = Math.max($(doc).height(),$(window).height())-46;
		var markWidth=waterMarkConfig.otherInfos.markWidth+16;
		var markHeight=waterMarkConfig.otherInfos.markHeight+6;
		var markType=waterMarkConfig.markType;
		var markOpacity = parseFloat(waterMarkConfig.markOpacity);
		var colSpace=parseInt(waterMarkConfig.markColSpace);
		var rowSpace=parseInt(waterMarkConfig.markRowSpace);
		var cols=parseInt((mark_width-0+colSpace) / (markWidth + colSpace));
		if(cols<3)
			cols=3;
		if(cols>=5){
			colSpace=parseInt((mark_width-0 - markWidth * cols)/ (cols - 1));
		}else{
			colSpace+=40;
		}
		var rows=parseInt((mark_height-46-46+rowSpace) / (markHeight + rowSpace));
		if(rows>1){
			rowSpace=parseInt((mark_height-46-markHeight * rows)/ (rows - 1));
		}
		var rotateType=waterMarkConfig.markRotateType;
		var angel=parseInt(waterMarkConfig.markRotateAngel);
		var rad = angel * (Math.PI / 180);
		var colLeft;
		var rowTop;
		for (var i = 0; i < rows; i++) {
			rowTop = 46 + (rowSpace + markHeight) * i;
			for (var j = 0; j < cols; j++) {
				colLeft = 0 + (markWidth + colSpace) * j;
				var mask_div = doc.createElement('div');
				mask_div.id = 'mask_div' + i + j;
				if("word"==markType){
					var markWord=waterMarkConfig.otherInfos.markWord;
					var markWordFontFamily=waterMarkConfig.markWordFontFamily;
					var markWordFontSize=waterMarkConfig.markWordFontSize;
					var markWordFontColor=waterMarkConfig.markWordFontColor;
					var fontP=doc.createElement('font');
					fontP.style.fontFamily = markWordFontFamily;
					fontP.appendChild(doc.createTextNode(markWord));
					mask_div.style.fontSize = markWordFontSize+'px';
					mask_div.style.color = markWordFontColor;
					mask_div.appendChild(fontP);
				}
				if("pic"==markType){
					var picUrl=waterMarkConfig.otherInfos.picUrl;
					var img=doc.createElement("img");
					img.src=picUrl;
					mask_div.appendChild(img);
				}
				if("declining"==rotateType){
					var commonCss="rotate(" + angel + "deg)";
					var ieCss="progid:DXImageTransform.Microsoft.Matrix(sizingMethod='auto expand', M11=";
					ieCss+=Math.cos(rad)+",M12=";
					ieCss+=-Math.sin(rad)+",M21=";
					ieCss+=Math.sin(rad)+",M22=";
					ieCss+=Math.cos(rad)+")";
					$(mask_div).css("-moz-transform",commonCss);
					$(mask_div).css("-o-transform",commonCss);
					$(mask_div).css("-webkit-transform",commonCss);
					$(mask_div).css("-ms-transform",commonCss);
					$(mask_div).css("transform",commonCss);
					if (Com_Parameter.IE && (parseFloat(navigator.appVersion.split("MSIE")[1]) == 8||parseFloat(navigator.appVersion.split("MSIE")[1]) == 7)) {
						//alert(111);
						$(mask_div).css("filter",ieCss);
					}
				}
				mask_div.style.visibility = "";
				mask_div.style.position = "absolute";
				mask_div.style.left = (colLeft) + 'px';
				mask_div.style.top = (rowTop+20) + 'px';
				mask_div.style.overflow = "hidden";
				mask_div.style.zIndex = "9";
				$(mask_div).css("opacity",markOpacity);
				mask_div.style.textAlign = "center";
				mask_div.style.height = markHeight + 'px';
				$(mask_div).css('line-height',markHeight + 'px');
				mask_div.style.display = "block";
				$(mask_div).css('pointer-events','none');
				if(cols<5)
					$(mask_div).css('text-overflow','clip');
				mask_div.className="mask_div";
				oTemp.appendChild(mask_div);
			};
		};
		$('#readerOuterContainer').append(oTemp);
	},
	setWaterMark : function(pageObj,pageNum){
		var waterMarkBgType=waterMarkConfig.markBgType;
		var bgOpacity=parseFloat(waterMarkConfig.markOpacity).toFixed(2);
		var picUrl="../sys_att_watermark/sysAttWaterMark.do?method=getWaterMarkPNG";
		var pageTr=document.getElementById("pageTr_"+pageNum);
		var markDiv=document.getElementById("waterMark-"+pageNum);
		if(markDiv==null || typeof(markDiv) == undefined){
			markDiv=document.createElement("div");
			markDiv.id="waterMark-"+pageNum;
			markDiv.className="waterMark";
			document.body.appendChild(markDiv);
		}
		seajs.use('lui/jquery',function($){
			$(markDiv).css({"background-color":"transparent","overflow":"hidden","position":"absolute"});
			var awpage=$(pageObj).contents().find(".awpage");
			if(awpage.length>0){
				$(markDiv).css({"height":$(awpage).css("height"),"width":$(awpage).css("width"),"top":$(pageTr).offset().top+$(awpage).offset().top+"px","left":$(pageObj).offset().left+$(awpage).offset().left+"px"});
			}else{
				$(markDiv).css({"height":$(pageObj).attr("height")+"px","width":$(pageObj).attr("width")+"px","left":$(pageObj).offset().left+"px","top":$(pageObj).offset().top+"px"});
			}
		});
		commonFuncs.setWaterMarkInner(markDiv,waterMarkBgType,"url("+picUrl+")",bgOpacity);
	},
	setWaterMarkInner : function(markDiv,waterMarkBgType,bgImage,bgOpacity){
		seajs.use('lui/jquery', function($) {
			$(markDiv).html("");
			var bgImageDiv;
			if(waterMarkBgType=="norepeat-center-center"){
				for(var i=1;i<=1;i++){
					bgImageDiv=$('<div></div>');
					bgImageDiv.attr('class','bgImage');
					$(markDiv).append(bgImageDiv);
					$(bgImageDiv).css("width","100%");
					$(bgImageDiv).css("height","100%");
					commonFuncs.setWaterMarkInnerCommonCss(bgImageDiv,bgImage,bgOpacity);
				}
			}
			if(waterMarkBgType=="repeaty-center-top"){
				for(var i=1;i<=5;i++){
					bgImageDiv=$('<div></div>');
					bgImageDiv.attr('class','bgImage');
					$(markDiv).append(bgImageDiv);
					$(bgImageDiv).css("width","33%");
					$(bgImageDiv).css("height","20%");
					$(bgImageDiv).css("margin","0px auto");
					commonFuncs.setWaterMarkInnerCommonCss(bgImageDiv,bgImage,bgOpacity);
				}
			}
			if(waterMarkBgType=="repeatx-left-center"){
				for(var i=1;i<=3;i++){
					bgImageDiv=$('<div></div>');
					bgImageDiv.attr('class','bgImage');
					$(markDiv).append(bgImageDiv);
					$(bgImageDiv).css("width","33%");
					$(bgImageDiv).css("height","100%");
					$(bgImageDiv).css("float","left");
					commonFuncs.setWaterMarkInnerCommonCss(bgImageDiv,bgImage,bgOpacity);
				}
			}
			if(waterMarkBgType=="repeat-left-top"){
				for(var i=1;i<=15;i++){
					bgImageDiv=$('<div></div>');
					bgImageDiv.attr('class','bgImage');
					$(markDiv).append(bgImageDiv);
					$(bgImageDiv).css("width","33%");
					$(bgImageDiv).css("height","20%");
					$(bgImageDiv).css("float","left");
					commonFuncs.setWaterMarkInnerCommonCss(bgImageDiv,bgImage,bgOpacity);
				}
			}
		});
	},
	setWaterMarkInnerCommonCss : function(bgImageDiv,bgImage,bgOpacity){
		seajs.use('lui/jquery', function($) {
			$(bgImageDiv).css("background-image",bgImage);
			$(bgImageDiv).css("background-repeat","no-repeat");
			$(bgImageDiv).css("background-position","center center");
			$(bgImageDiv).css("opacity",bgOpacity);
		});
	},
	registerEventHandler : function(target, eventType, handler){
		if(target.addEventListener){
			addHandler = function(target, eventType, handler){
				target.addEventListener(eventType, handler, false);
			};
		}else{
			addHandler = function(target, eventType, handler){
				target.attachEvent("on"+eventType, handler);
			};
		}
		addHandler(target, eventType, handler);
	},
	removeEventHandler : function(target, eventType, handler){
		if(target.removeEventListener){
			removeHandler = function(target, eventType, handler){
				target.removeEventListener(eventType, handler, false);
			};
		}else{
			removeHandler = function(target, eventType, handler){
				target.detachEvent("on"+eventType, handler);
			};
		}
		removeHandler(target, eventType, handler);
	},
	changeFullScreenExceptIE : function(){
		if (!document.fullscreenElement && !document.mozFullScreenElement
			&& !document.webkitFullscreenElement) {
			if (document.documentElement.requestFullscreen) {
				document.documentElement.requestFullscreen();
			} else if (document.documentElement.mozRequestFullScreen) {
				document.documentElement.mozRequestFullScreen();
			} else if (document.documentElement.webkitRequestFullscreen) {
				document.documentElement
					.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
			}
		} else {
			if (document.cancelFullScreen) {
				document.cancelFullScreen();
			} else if (document.mozCancelFullScreen) {
				document.mozCancelFullScreen();
			} else if (document.webkitCancelFullScreen) {
				document.webkitCancelFullScreen();
			}
		}
	},
	setPageToolBar : function(pageNum){
		document.getElementById("currentPageIndex").value=pageNum;
		if(totalPageNum==1){
			document.getElementById("prevBtn").className="unable prev";
			document.getElementById("nextBtn").className="unable next";
		}else if(pageNum==totalPageNum){
			document.getElementById("nextBtn").className="unable next";
			document.getElementById("prevBtn").className="prev";
		}else if(pageNum==1){
			document.getElementById("prevBtn").className="unable prev";
			document.getElementById("nextBtn").className="next";
		}else{
			document.getElementById("nextBtn").className="next";
			document.getElementById("prevBtn").className="prev";
		}
	},
	isLoadPage : function(pageNum){
		var dataPage=document.getElementById("dataLoad_"+pageNum);
		if(dataPage!=null){
			return true;
		}else{
			return false;
		}
	},
	getFileName : function(pageNum){
		if(fullScreen=="yes" && commonFuncs.contains(viewerStyle, "ppt", true)){
			return converterKey+"_page-"+pageNum+"-img";
		}
		return converterKey+"_page-"+pageNum+(fileKeySufix=="noSufix"?"":fileKeySufix);
	},
	prev : function (evt){
		var currentPageIndex = parseInt(document.getElementById("currentPageIndex").value);
		if(currentPageIndex-1>=1){
			goTo(currentPageIndex-1);
		}
	},
	next : function(evt){
		var currentPageIndex = parseInt(document.getElementById("currentPageIndex").value);
		if(currentPageIndex+1<=totalPageNum){
			goTo(currentPageIndex+1);
		}
	},
	onPageKeyUp : function(evt){
		if(evt.keyCode == 13){
			var currentPageIndex = parseInt(document.getElementById("currentPageIndex").value);
			if(currentPageIndex<=totalPageNum&&currentPageIndex>=1){
				goTo(currentPageIndex);
			}else{
				document.getElementById("currentPageIndex").value=currentPage;
			}
		}
	},
	onPageKeyDown : function(evt){
		// 支持右下角小键盘
		if((evt.keyCode >=48 && evt.keyCode <=57) || evt.keyCode == 8 || (evt.keyCode >=96 && evt.keyCode <=105) ){
			return true;
		}
		return false;
	},
	showPageNav : function(evt){
		seajs.use('lui/jquery',function($){
			if(commonFuncs.contains(viewerStyle, "excel", true)){
				$("#readerBottom").css({"opacity":1,"height":"66px","padding-bottom":"0px","width":"95%"});
				$("#mainMiddle").css({"top":"66px"});
			}else{
				$("#readerBottom").css({"opacity":1,"height": "66px","padding-top":"0px"});
			}
			if(commonFuncs.contains(viewerStyle, "ppt", true)){
				commonFuncs.unBindFsPptEvent();
			}
		});
	},
	hidePageNav : function(evt){
		seajs.use('lui/jquery',function($){
			if(commonFuncs.contains(viewerStyle, "excel", true)){
				$("#readerBottom").css({"opacity":0,"height":"0px","padding-bottom":"66px"});
				$("#mainMiddle").css({"top":"0px"});
			}else{
				$("#readerBottom").css({"opacity":0,"height":"0px","padding-top":"66px"});
			}
			if(commonFuncs.contains(viewerStyle, "ppt", true)){
				commonFuncs.bindFsPptEvent();
			}
		});
	},
	changeFsPptMouseIcon : function(evt){
		seajs.use('lui/jquery',function($){
			if(evt.screenX>=window.screen.width/2){
				commonFuncs.direct="next";
				if(currentPage==totalPageNum){
					//commonFuncs.direct="prev";
					//$(document.body).css({"cursor":"url(../viewer/resource/common/images/prev.ico),auto"});
					$(document.body).css({"cursor":"auto"});
				}else{
					$(document.body).css({"cursor":"url(../viewer/resource/common/images/next.ico),auto"});
				}
			}else{
				commonFuncs.direct="prev";
				if(currentPage==1){
					//commonFuncs.direct="next";
					//$(document.body).css({"cursor":"url(../viewer/resource/common/images/next.ico),auto"});
					$(document.body).css({"cursor":"auto"});
				}else{
					$(document.body).css({"cursor":"url(../viewer/resource/common/images/prev.ico),auto"});
				}
			}
		});
	},
	changeFsPptContent : function(evt){
		if(evt.target.nodeName=="IMG"||evt.target.nodeName=="TD"||evt.target.id=="waterMark"||evt.target.className=="bgImage"){
			if(commonFuncs.direct=="next"){
				commonFuncs.next();
			}
			if(commonFuncs.direct=="prev"){
				commonFuncs.prev();
			}
		}
	},
	keyDownHandler : function(evt){
		if(evt.keyCode==39){
			commonFuncs.next();
		}
		if(evt.keyCode==37){
			commonFuncs.prev();
		}
		if(evt.keyCode==27){
			window.opener.location.replace(commonFuncs.replaceUrlParamVal(commonFuncs.replaceUrlParamVal(window.location.href,"fullScreen","no"),"pageNum",""+currentPage));
			Com_CloseWindow();
		}
	},
//		激光笔ppt翻页
	keyDownHandlerPPT : function(evt){
		if(evt.keyCode==40){
			commonFuncs.next();
		}
		if(evt.keyCode==38){
			commonFuncs.prev();
		}
	},
	changeScreenStatus : function(evt){
		if(!commonFuncs.contains(viewerStyle,"excel",true)){
			currentPage=parseInt(document.getElementById("currentPageIndex").value);
		}
		var browser=commonFuncs.getBrowser();
		if(browser=="IE"){
			var newUrl;
			if(fullScreen=="no"){
				newUrl=commonFuncs.replaceUrlParamVal(commonFuncs.replaceUrlParamVal(window.location.href,"fullScreen","yes"),"pageNum",""+currentPage);
				if(!commonFuncs.contains(newUrl,"pageNum",true)){
					newUrl+="&pageNum="+currentPage;
				}
				if(!commonFuncs.contains(newUrl,"fullScreen",true)){
					newUrl+="&fullScreen=yes";
				}
				window.open(newUrl, "", "fullscreen=yes,scrollbars=yes");
			}else{
				newUrl=commonFuncs.replaceUrlParamVal(commonFuncs.replaceUrlParamVal(window.location.href,"fullScreen","no"),"pageNum",""+currentPage);
				window.opener.location.replace(newUrl);
				Com_CloseWindow();
			}
		}else{
			commonFuncs.changeFullScreenExceptIE();
		}
	},
	fullScreenChangeHandlerExceptIE : function(evt){
		seajs.use('lui/jquery',function($){
			$(".waterMark").each(function(){
				document.body.removeChild(this);
			})
		});
		var isFullScreen;
		if(document.mozFullScreen||document.mozFullScreen==false){
			isFullScreen=document.mozFullScreen;
		}
		if(document.webkitIsFullScreen||document.webkitIsFullScreen==false){
			isFullScreen=document.webkitIsFullScreen;
		}
		if(isFullScreen){
			commonFuncs.fsCommonHandler();
			commonFuncs.bindFsKeyDown();
			commonFuncs.removeCssOrJsFile("newopencss");
			commonFuncs.addCss("fullscreencss", Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/css/fullscreen.css");
			if(commonFuncs.contains(viewerStyle, "ppt", true)){
				//激光笔控制ppt翻页	开始
				commonFuncs.bindFsKeyDownPPT();
				document.getElementsByTagName("body")[0].setAttribute("style","overflow:hidden");
				//激光笔控制ppt翻页	结束
				commonFuncs.bindFsPptEvent();
				commonFuncs.removeCssOrJsFile("newopenjs");
				commonFuncs.addJs("pptfullscreenjs", Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/js/pptfullscreen.js");
			}else{
				if(!commonFuncs.contains(viewerStyle, "excel", true)){
					commonFuncs.addJs("newopenjs", Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/js/newopen.js",currentPage);
				}else{
					commonFuncs.unBindFsToolHover();
				}
			}
		}else{
			commonFuncs.fsExitCommonHandler();
			commonFuncs.unBindFsKeyDown();
			commonFuncs.removeCssOrJsFile("fullscreencss");
			commonFuncs.addCss("newopencss", Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/css/newopen.css");
			if(!commonFuncs.contains(viewerStyle, "excel", true)){
				commonFuncs.removeCssOrJsFile("pptfullscreenjs");
				commonFuncs.addJs("newopenjs", Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/js/newopen.js",currentPage);
			}
			if(commonFuncs.contains(viewerStyle, "ppt", true)){
				commonFuncs.unBindFsPptEvent();
				seajs.use('lui/jquery',function($){
					$("#mainMiddle").removeAttr("style");
				});
			}
		}
	},
	fsCommonHandler : function(){
		commonFuncs.unBindResizeEvent();
		commonFuncs.bindFsToolHover();
		fullScreen="yes";
		seajs.use('lui/jquery',function($){
			$("a.zoom_a").attr("title",exitFullScreenHint);
		});
	},
	fsExitCommonHandler : function(){
		commonFuncs.unBindFsToolHover();
		fullScreen="no";
		seajs.use('lui/jquery',function($){
			$("a.zoom_a").attr("title",goFullScreenHint);
		});
	},
	bindFsToolHover : function(){
		seajs.use('lui/jquery',function($){
			$("#readerBottom").bind("mouseenter",commonFuncs.showPageNav);
			$("#readerBottom").bind("mouseleave",commonFuncs.hidePageNav);
		});
	},
	unBindFsToolHover : function(){
		seajs.use('lui/jquery',function($){
			$("#readerBottom").unbind("mouseenter",commonFuncs.showPageNav);
			$("#readerBottom").unbind("mouseleave",commonFuncs.hidePageNav);
			$("#readerBottom").removeAttr("style");
		});
	},
	bindFsKeyDown : function(){
		seajs.use('lui/jquery',function($){
			$(document).bind("keydown",commonFuncs.keyDownHandler);
		});
	},
	bindFsKeyDownPPT : function(){
		seajs.use('lui/jquery',function($){
			$(document).bind("keydown",commonFuncs.keyDownHandlerPPT);
		});
	},
	unBindFsKeyDown : function(){
		seajs.use('lui/jquery',function($){
			$(document).unbind("keydown",commonFuncs.keyDownHandler);
		});
	},
	bindFsPptEvent : function(){
		seajs.use('lui/jquery',function($){
			$(document).bind("mousemove",commonFuncs.changeFsPptMouseIcon);
			$(document).bind("click",commonFuncs.changeFsPptContent);
		});
	},
	unBindFsPptEvent : function(){
		seajs.use('lui/jquery',function($){
			$(document).unbind("mousemove",commonFuncs.changeFsPptMouseIcon);
			$(document.body).removeAttr("style");
			$(document).unbind("click",commonFuncs.changeFsPptContent);
		});
	},
	bindResizeEvent : function(){
		seajs.use('lui/jquery',function($){
			$(window).bind("resize",commonFuncs.resizeHandler);
		});
	},
	unBindResizeEvent : function(){
		seajs.use('lui/jquery',function($){
			$(window).unbind("resize",commonFuncs.resizeHandler);
		});
	},
	resizeHandler : function(){
		clearTimeout(commonFuncs.globalTimer);
		commonFuncs.globalTimer = setTimeout(commonFuncs.resizeInnerHandler, 500);
	},
	resizeInnerHandler : function(){
		var tempAvailableW=commonFuncs.getAvailableWidth();
		if(commonFuncs.lastScreenWidth==0&&commonFuncs.isIE()){
			commonFuncs.lastScreenWidth=tempAvailableW;
			return;
		}
		if(commonFuncs.lastScreenWidth != tempAvailableW){
			commonFuncs.lastScreenWidth=tempAvailableW;
			for (var i = 1; i <= totalPageNum; i++) {
				if(commonFuncs.isLoadPage(i)){
					var dataFrame=document.getElementById("dataLoad_"+i);
					seajs.use('lui/jquery',function($){
						$(dataFrame).removeAttr("width");
						$(dataFrame).removeAttr("height");
						var dataSrc=$(dataFrame).attr("src");
						$(dataFrame).removeAttr("src").attr("src",dataSrc);
					});
				}
			}
		}else{
			if(waterMarkConfig.showWaterMark=="true"){
				for (var i = 1; i <= totalPageNum; i++) {
					if(commonFuncs.isLoadPage(i)){
						var pageObj=document.getElementById("dataLoad_"+i);
						//commonFuncs.setWaterMark(pageObj,i);
					}
				}
			}
		}
	}
};