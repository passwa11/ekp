define(function(require, exports, module) {	
	var $ = require("lui/jquery");
	var base = require('lui/base');
	var lang = require('lang!sys-portal');
	var env = require('lui/util/env');
	
	var Guide = base.Component.extend({
		
		draw : function($super){
			var guideId = this.config.guideId;
			if(guideId){	
				this.generatePopUp();
			}
			$super();
		},
		
		generatePopUp : function(){
			var that = this;
			if(that.config.guidecfg){
				var guideId = that.config.guideId,
					guideCfg = LUI.toJSON(that.config.guidecfg);
				//暂时只做链接形式的处理，后续拓展(如RTF形式等)...
				if(guideCfg.type == 'link'){
					that.generateLinkIframe({
						id : guideId,
						link : guideCfg.link,
						updateTime : guideCfg.updateTime,
						close : guideCfg.close
					});
				}
			}
		},
		
		bindEvent : function(){
			var that = this;
			this.guideElement.on('click',function(){
				that.openIframe();
			});
			domain.register('closeIframe',function(){
				that.closeIframe();
			});
		},
		
		openIframe : function(){
			var that = this;
			$('body').css({
				'overflow':'hidden'
 			});
			if(this.guidePage){	
				if(!that.iframeTimer){
					that.iframeTimer = setInterval(function(){
						that._resizeIframe(that.guideIframe);
					},100);
				}
				that.guidePage.show();
				that.guideIframe.show();
				that.guideClose.show();
			}
		},
		
		closeIframe : function(){
			var that = this;
			$('body').css({
				'overflow':'auto'
 			});
			if(that.guidePage){
				that.guidePage.hide();
				that.guideIframe.hide();
				that.guideClose.hide();
				clearInterval(that.iframeTimer);
				that.iframeTimer = null;
			}
		},
		
		generateLinkIframe : function(cfg){
			var that = this;
			if(!this.guidePage){
				this.guidePage =  $('<div></div>');
				this.guidePage.css({
					'width' : '100%',
					'height' : '0px',
					'position' : 'fixed',
					'top' : '0',
					'left' : '0',
					'z-index' : 100,
					'background-color' : '#000',
					'opacity': '0.6',
					'filter' : 'alpha(opacity=60)'
				});

				var guideIframe = $('<iframe class="i" scrolling="auto" frameborder="0" />');
				guideIframe.css({
					'position' : 'fixed',
					'border' : '0',
					'width' : '100%',
					'height' : '0px',
					'z-index': 101,
					'background-color' : '#ffffff'
	 			});
				var iframeParentDiv = $("<div></div>").append(guideIframe);
				$('body').append(iframeParentDiv);				
				guideIframe.load(function(){  
					// 显示屏幕右侧引导页浮动按钮
					if(!that.guideElement){
						var guideCfg = LUI.toJSON(that.config.guidecfg);
						var name = guideCfg.showName || lang['sysPortalGuide.guide'],
							guideElement = $('<a class="lui_portal_guide_element" />').hide(),
							guideIcon = $('<i class="icon icon-affix"></i>').appendTo(guideElement),
							guideText = $('<span class="lui_portal_guide_text"/>').text(name).appendTo(guideElement);
						that.guideElement = guideElement;
						that.element.append(guideElement).addClass('lui_portal_guide_container');	
						// 此处延迟显示浮动按钮（原因：IE11下过早显示会导致出现全屏灰色阴影）
						setTimeout(function(){that.guideElement.show();},500);
						// 绑定事件
						that.bindEvent();
						//cookie中的更新时间小于新的更新时间，强制显示
						if(!that._fetchCookie('__page__guide__' + cfg.id) || that._fetchCookie('__page__guide__' + cfg.id) != cfg.updateTime){
							that.openIframe();
							document.cookie="__page__guide__"+ cfg.id +"=" + cfg.updateTime;
						}						
					}
			    });
				$(window).resize(function () { //当浏览器大小变化时，动态重新计算设置IFrame显示位置
					that.resetGuideIframePosition();
				});
				guideIframe.attr('src',env.fn.formatUrl(cfg.link));
                 
				// 构建引导页的关闭按钮 
				if(cfg.close == 'true'){
					this.guideClose = $('<a class="lui_portal_guide_close"/>').hide();
					$('body').append(this.guideClose);
					this.guideClose.click(function(){
						that.closeIframe();
					});
				}
				$('body').append(this.guidePage);
				this.guideIframe = guideIframe;
			}
		},
		
		_resizeIframe : function($iframe){
			var iframeDom = $iframe[0];
			this.guidePage.css("height","100%");
			try{	
				    var oriIframeHeight = $iframe.height();
				    var oriIframeWidth = $iframe.outerWidth(true);
				    var iframeWH = this.getIframeContentWH(); // 获取引导页IFrame嵌入的页面实际“宽度”和“高度”
				    var iframeWidth = iframeWH.width;
					var iframeHeight = iframeWH.height;
					if(oriIframeWidth!=iframeWidth){
						iframeDom.style.width = (iframeWidth) + 'px';
						var checkWidth = this.getIframeContentWH().width;
						/**-----IE9下内容页有滚动条时，宽度计算可能有误（矫正宽度Start）-----**/
						if(checkWidth<iframeWidth){
							iframeDom.style.width = (iframeWidth+(iframeWidth-checkWidth)) + 'px';
						}
						if(checkWidth>iframeWidth){
							iframeDom.style.width = (iframeWidth-(checkWidth-iframeWidth)) + 'px';
						}
						/**-----IE9下内容页有滚动条时，宽度计算可能有误（矫正宽度End）-----**/
						this.resetGuideIframePosition(); // 重置IFrame显示的位置
					}
					if(oriIframeHeight!=iframeHeight){
						iframeDom.style.height = (iframeHeight) + 'px';
						this.resetGuideIframePosition(); // 重置IFrame显示的位置
					}	
			}catch(e){ // 跨域访问时将抛出异常，此时采用catch中设置的通用样式
				this.guidePage.show();
				this.guideIframe.css({
					'width' : '1000px',
					'height' : '500px',
				});
				this.resetGuideIframePosition();
			  }
		
		},
		
		_fetchCookie : function(name){
			var search = name + "=";
			var returnvalue = "";
		   	if (document.cookie && document.cookie.length > 0) {
		    	offset = document.cookie.indexOf(search);
		   	if (offset != -1) {
		        offset += search.length;
		        end = document.cookie.indexOf(";", offset);
		        if (end == -1)
		           end = document.cookie.length;
		        returnvalue=unescape(document.cookie.substring(offset, end));
		      }
		   }
		   return returnvalue;
		},
		/** 获取引导页IFrame嵌入内容的“宽度”和“高度” **/
		getIframeContentWH: function(){
			var iframeSrc = this.guideIframe[0].src;
			var iframeWH = null;
			if(/.(gif|jpg|jpeg|png|bmp)$/.test(iframeSrc.toLowerCase())){
				iframeWH = this.getIframeImageWH();   // 获取IFrame嵌入图片的宽、高
			}else{
				iframeWH = this.getIframeWebPageWH(); // 非图片时，获取IFrame嵌入网页的宽、高
			}
            return iframeWH;
		},
		/** 获取引导页IFrame嵌入“网页”实际内容的“宽度”和“高度” **/
		getIframeWebPageWH: function(){
            var iframeDom = this.guideIframe[0];
            
			var iframeWindow = iframeDom.contentWindow;
			var $iframeBody = $(iframeWindow.document.body);
			var iframeWebPageWidth = $iframeBody.outerWidth(true);

			var innerHeight = iframeWindow.innerHeight || iframeWindow.document.documentElement.clientHeight // IFrame页面可视高度
	    	var scrollHeight = iframeWindow.document.documentElement.scrollHeight;// IFrame页面滚动高度（总高度）
			var overScreenHeight = innerHeight!=0?(scrollHeight-innerHeight):0;  // IFrame超出屏幕可视区的高度
			if(overScreenHeight>0){
				iframeWebPageWidth = iframeWebPageWidth + this.getIframeScrollbarWidth();
			}
			var topPageBodyWidth = $(document.body).outerWidth(true);
			if(iframeWebPageWidth>topPageBodyWidth){
				iframeWebPageWidth = topPageBodyWidth;
			}
			var iframeWebPageHeight = $iframeBody.outerHeight(true);
			var topPageBodyHeight = window.innerHeight || window.document.documentElement.clientHeight ;
			if(iframeWebPageHeight>topPageBodyHeight || (iframeWebPageWidth!=0 && iframeWebPageHeight==0)){
				iframeWebPageHeight = topPageBodyHeight;
			}
			
            var iframeWH = {
            	"width":iframeWebPageWidth,
            	"height":iframeWebPageHeight
            }			
            return iframeWH;			
		},
		/** 获取引导页IFrame嵌入“图片”实际内容的“宽度”和“高度” **/
		getIframeImageWH: function(){
			var that = this;
            var iframeDom = this.guideIframe[0];
            if(this.guideImageWH && this.guideImageWH.width!=0 && this.guideImageWH.height!=0 ){
            	return this.guideImageWH;
            }
            // 创建一个临时IMG标签，用于单独加载出IFrame嵌入的图片，用来计算获取图片的宽高（直接读取IFrame的图片容易出现跨域问题，计算完成后会删除该IMG元素）
            var $tempImage = $('<img style="visibility:hidden;" />');
            $tempImage.attr("src", iframeDom.src );
            $("body").append($tempImage);
            var tempImageNode = $tempImage[0];
	   		var imageWidth = tempImageNode.width || 0;
		    var imageHeight = tempImageNode.height || 0;
		    $tempImage.remove();
		    if( imageWidth!=0 && imageHeight!=0 ){
				clearInterval(that.iframeTimer);
				that.iframeTimer = null;		    	
		    }
            var iframeWH = {
            	"width":imageWidth,
            	"height":imageHeight
            }
            this.guideImageWH = iframeWH;
            return iframeWH;			
		},		
		/** 获取IFrame滚动条的宽度(单位:px) **/
		getIframeScrollbarWidth: function () {  
			if (this._cachedScrollbarWidth !== undefined) {  
				return this._cachedScrollbarWidth;  
			}  
		    var iframeDom = this.guideIframe[0];
		    var iframeWindow = iframeDom.contentWindow;
		    var odiv = iframeWindow.document.createElement('div'),//创建一个div
		        styles = { width: '100px',height: '100px',overflowY: 'scroll'}, 
		        i, 
		        scrollbarWidth;
		    for (i in styles) odiv.style[i] = styles[i];
		    iframeWindow.document.body.appendChild(odiv);//把div添加到body中
		    scrollbarWidth = parseFloat(odiv.offsetWidth) - parseFloat(odiv.clientWidth);//相减
			if(odiv.remove){ // 移除创建的div
				odiv.remove();
			}else{
				if(odiv.removeNode){
					odiv.removeNode(true);
				}else{
					odiv.style["display"]="none";
				}
			} 
		    return (this._cachedScrollbarWidth = scrollbarWidth);  
		},
		/** 重置引导页IFrame的显示位置（左右横向居中、上下垂直居中）以及关闭按钮的显示位置 **/
		resetGuideIframePosition: function(){
			var iframeDom = this.guideIframe[0];
			var iframeWidth = this.guideIframe.outerWidth(true);
			var iframeHeight = this.guideIframe.outerHeight(true);
			var iframeTop = parseInt(((window.innerHeight || window.document.documentElement.clientHeight) - iframeHeight)/2);
			var iframeLeft = parseInt(($(document.body).outerWidth(true) - iframeWidth)/2);
			// 设置纵向显示位置
			iframeDom.style['top'] =  iframeTop+'px';
			// 设置横向显示位置
			iframeDom.style['left'] = iframeLeft+'px';
			// 设置关闭按钮显示位置
			if(this.guideClose){
				this.guideClose.css({
				    'top' : (iframeTop+10) + 'px',
					'left' : (iframeLeft + iframeWidth - 45) + 'px'
				});
			}
		}
		
	});
	
	exports.Guide = Guide;
	
});