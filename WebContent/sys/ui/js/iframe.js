/**
 * Iframe组件
 * TODO : 
 *  1.query
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var iframeAdapter = require('lui/spa/adapters/iframeAdapter');
	//var iframeAdapter2 = require('lui/framework/adapters/iframeAdapter');

	//兼容IE8
	if (typeof String.prototype.startsWith != 'function') {
		String.prototype.startsWith = function (start){
			return this.slice(0, start.length) === start;
		};
	}
	
	//定时器
	var _requestAnimationFrame = (function(){
		var isEdge = navigator.userAgent.indexOf("Edge") > -1;//判断是否IE的Edge浏览器
		if(isEdge){
			return  function(callback){ return window.setTimeout(callback, 500); }
		}
		return window.requestAnimationFrame 
				|| window.webkitRequestAnimationFrame
				|| window.mozRequestAnimationFrame
				|| function(callback){ return window.setTimeout(callback, 500); }
	})();
	//取消定时器
	var	_cancelAnimationFrame = (function(){
		return window.cancelAnimationFrame 
				|| function(id){ clearTimeout(id); }	
	})();
	
	var Iframe = base.Container.extend({
		
		loading : false,
		
		dyniframe : true, // 是否动态计算高度(使用setInterval)
		
		takeHash : true, //是否携带hash到内部
		
		takeQuery : false, //是否携带query到内部
		
		subscribeIframe : true, //是否监听内部事件的变化，并传递到父层（目前本组件只会监听SPA变化事件）
		
		initProps : function($super,_config){
			$super(_config);
			this.config = _config || {};
			this.src = this.config.src || 'about:blank';
			this.dyniframe = typeof(this.config.dyniframe) === 'undefined' ? true : eval(this.config.dyniframe);
			this.takeHash = typeof(this.config.takeHash) === 'undefined' ? true : eval(this.config.takeHash);
			this.takeQuery = typeof(this.config.takeQuery) === 'undefined' ? false : eval(this.config.takeQuery);
			this.subscribeIframe = typeof(this.config.subscribeIframe) === 'undefined' ? true : eval(this.config.subscribeIframe);
		},
		draw : function(){
			var self = this;
			if(this.isDrawed)
				return;
			var $iframeNode = this.$iframeNode = $('<iframe frameborder="no" border="0" height="1200" />').addClass('lui_widget_iframe');
			$iframeNode.load(function(){
				self.loading = false;
				if(self.isBlank()){
					return;
				}
				self.iframeLoaded();
			});
			this.loading = true;
			$iframeNode.attr('src', this.formatSrc(this.src));
			this.element.append($iframeNode);
			this.element.show();
			this.isDrawed = true;
			return this;
		},
		destroy : function($super){
			$super();
			//清除定时器
			if(this.$iframeTimer){
				clearInterval(self.$iframeTimer);
				self.$iframeTimer = null;
			}
			//内部对象清除
			try{
				var $body = $(this.$iframeNode[0].contentWindow.document.body);
				// 清除子节点信息，jquery默认empty在清除object对象会报错
				this.iframeDestroy($body);
				$body.remove();
				 window.CollectGarbage && window.CollectGarbage(); 
			}catch(e){
				//
			}
		},
		iframeDestroy : function($obj) {
			var self = this;
			$obj.children().each(function(i, obj) {
				if (obj.getAttribute && $(obj).children().length > 0)
					self.iframeDestroy($(obj));
				if(obj.contentWindow)
					self.iframeDestroy($(obj.contentWindow.document.body));
				obj.parentNode.removeChild(obj);
			});
		},
		iframeLoaded : function(){
			var self = this;
			if(!this.$iframeTimer && this.dyniframe){
				var frame = self.$iframeNode[0];
				try{
					$(frame.contentWindow.document.body).css('overflow-y','hidden');
				}catch(e){}
				this.$iframeTimer = _requestAnimationFrame(function(){
					try{
						frame = self.$iframeNode[0];
						if(frame.contentWindow){
							var ___win = frame.contentWindow,
								___doc = ___win.document,
								___height = ___doc.body.scrollHeight || ___doc.documentElement.scrollHeight;
							var isEdge = navigator.userAgent.indexOf("Edge") > -1;//判断是否IE的Edge浏览器
							if(isEdge){
								___height = ___doc.documentElement.scrollHeight;
							}
							if(frame.height != ___height){
								frame.height = ___height;
							}
						}
						self.$iframeTimer = _requestAnimationFrame(arguments.callee);
					}catch(e){
						//跨域情况下自适应高度无法计算,移除定时器
						_cancelAnimationFrame(self.$iframeTimer);
						self.$iframeTimer = null;
					}
				});
			}
			this.emit('iframeLoaded');
		},
		reload : function(src){
			this.src = src || this.src;
			this.loading = true;
			this.$iframeNode.attr('src',this.formatSrc(this.src));
		},
		isBlank : function(src){
			src = src || this.src;
			return !src || src == 'about:blank';
		},
		formatSrc : function(src){
			if(this.isBlank(src)){
				return src;
			}
			//hash传递
			if(this.takeHash && window.location.hash){
				var hashIndex = src.indexOf('#');
				if(hashIndex > -1){
					var ___hash = src.substring(hashIndex);
					src = src.substring(0, hashIndex);
					src += '#' + this.combineHash(window.location.hash, ___hash);
				}else{
					src += '#' + this.combineHash(window.location.hash);
				}
			}
			//query传递
			if(this.taskQuery){
				//TODO
			}
			return src;
		},
		//合并hash
		combineHash : function(){
			var paramsObject = {},
				str = [];
			for(var i = 0; i < arguments.length; i++){
				var obj = this._hashToObject(arguments[i]);
				paramsObject = $.extend(paramsObject, obj);
			}
			for ( var p in paramsObject) {
				if (paramsObject[p])
					str.push(p + "=" + encodeURIComponent(paramsObject[p]));
			}
			return str.join("&");
		},
		//合并query
		combineQuery : function(){
			
		},
		_hashToObject : function(hash){
			if(hash.startsWith('#')){
				hash = hash.substring(1);
			}
			var params = hash.split("&") || [],
				paramsObject = {};
			for (var i = 0; i < params.length; i++) {
				if (!params[i])
					continue;
				var a = params[i].split("=");
				paramsObject[a[0]] = decodeURIComponent(a[1]);
			}
			return paramsObject;
		}
		
	}).extend(iframeAdapter);
	
	exports.Iframe = Iframe;
	
});