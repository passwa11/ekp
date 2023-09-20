

define(function(require, exports, module) { 
	var base = require('lui/base');
	var strutil = require('lui/util/str');
	var $ = require('lui/jquery');
	
	var BaseSource = base.DataSource.extend({
		_getCode: function(cfg) {
			if (cfg.code)
				return cfg.code;
			if (cfg.element)
				return $(cfg.element).children("script[type='text/code']").html();
			return null;
		},
		getCodeAsJson: function(cfg) {
			var code = this._getCode(cfg);
			if (code)
				return strutil.toJSON(code);
			return null;
		},
		getCodeAsText: function(cfg) {
			return this._getCode(cfg);
		},
		_done: function(old, cb, ctx) {
			var data = old;
			if (cb)
				data = cb.call(ctx, old);
			this.emit('data', data);
		},
		abort: function() {
			return this;
		},
		get: function(done, ctx) {
			this._done({}, done, ctx);
			return this;
		},
		destroy: function($super) {
			$super();
			this.abort();
		}
	});
	
	var UrlSource = BaseSource.extend({
		setUrl: function(url) {
			this.url = url;
			this._url = url;
		},
		initProps: function($super, cfg) {
			$super(cfg);
			if (Object.isString(cfg)) {
				this.setUrl(cfg);
			} else {
				this.setUrl(cfg.url);
			}
			this.commitType=cfg.commitType?cfg.commitType:"GET";
			this.params = cfg.params || {};
			this.timeout = cfg.timeout ? cfg.timeout : 0;
			this.vars = {};
			$.extend(this.vars, this.config.vars);
			this.index = 0;
            this.callbacks = [];
	    },
		abort: function() {
			this.url = null;
			this.callbacks = [];
			return this;
		},
		onload: function(data, done, ctx) {
			this._done(data, done, ctx);
		},
		get: function(done, ctx) {
			var self = this, dataType;
			
			var url = this.url;
			if (this.dataType) {
				dataType = this.dataType;
			} else if (/^(https?:\/\/)/.test(url)) {
            	dataType = 'jsonp';
            } else {
            	dataType = 'json';
            }
            this._request(url, dataType, done, ctx);
			return this;
		},
		_request: function(url, dataType, done, ctx) {
			var requestUrl = $.trim(url);
			// 如果没有传入URL，则不需要向后台请求（在使用<ui:chart>组件时，由于新建页面并未选择筛选条件，因此初始的URL为空，后续的操作才会有请求）
			if (requestUrl.length < 1) {
				return;
			}
			// #152841 有些请求的参数太多，导致URL超长，针对这些超长的URL，把参数放到data里
			// 临时参数，只用于本次请求，用完后需要清理
			var temp_params = [];
			var temp_commitType = this.commitType;
			if (requestUrl.length > 2048) {
				var __url = requestUrl.split("?");
				var __params = __url[1].split("&");
				var tempParams = [];
				for (var i = 0; i < __params.length; i++) {
					if (__params[i].length > 0) {
						var temp = __params[i].split("=");
						if (temp[1].length > 500) {
							var ___temp_val = temp[1];
							try {
								// 从GET请求中获取的中文字符，需要解码
								___temp_val = decodeURIComponent(temp[1]);
							} catch (e) {
							}
							this.params[temp[0]] = ___temp_val;
							temp_params.push(temp[0]);
							// 有超长参数时，设置为POST提交
							temp_commitType = "POST";
						} else {
							tempParams.push(__params[i]);
						}
					}
				}
				requestUrl = __url[0] + "?" + tempParams.join("&");
			}
			var self = this;
			var callbackId = 'callback_' + this.index++;
			this.callbacks.push(callbackId);
			requestUrl = self.getEnv().fn.formatUrl(requestUrl);
			var reqType = dataType == 'json' ? 'text' : dataType;
			$.ajax({
				url: requestUrl,
				dataType: reqType,
				timeout: this.timeout,
				data: this.params,
            	type:temp_commitType,
            	jsonp:"jsonpcallback",
            	success: function(data,textStatus, jqXHR) {
					if(temp_params.length > 0 && self.params) {
						// 清理临时参数
						for(var idx=0; idx<temp_params.length; idx++) {
							delete self.params[temp_params[idx]];
						}
					}
            		var rtnData = data; 
            		if(typeof(data)=='string' && (dataType=='json' || dataType=='jsonp')){
            			try {
            				rtnData = strutil.toJSON(data);
            			} catch(e) {
            				if (window.console) {
            					window.console.error("数据JSON转换发生异常！");
            					window.console.error("请求服务发生异常的URL: "+requestUrl);
            					window.console.error(e);
            				}
            				self.emit("error", self._buildErrorContentText(600)); // 将600约定为服务器返回的数据错误
            				self.emit("errorCode", 600); // 触发异常状态码监听事件（用于渲染公共的请求异常提醒，详见sys\ui\js\base.js）
            				rtnData = {};
            			}
            		}
                    if ($.inArray(callbackId, self.callbacks) > -1) {
                        delete self.callbacks[callbackId];
                        self.onload(rtnData, done, ctx);
                    }
                },
                error: function(request, textStatus, errorThrown) {
                	if ($.inArray(callbackId, self.callbacks) > -1) {
                        delete self.callbacks[callbackId];
                    }
                    var __status = request.status;
					if (textStatus == "timeout") {
						self.onload([], done, ctx);
					} else {
						self.emit("error", self._buildErrorContentText(__status));
						self.emit("errorCode", __status); // 触发异常状态码监听事件（用于渲染公共的请求异常提醒，详见sys\ui\js\base.js）
					}
                }
            });
		},
		_buildErrorContentText: function(errorCode){
			var errorText = null;
			if(typeof errorCode === 'number'){
				var msgText = "";
				switch(errorCode) {
			     case 0:
			    	msgText = "已停止服务或网络出现异常";
			        break;
			     case 403:
			    	msgText = "无该内容访问权限";
			        break;
				 case 404:
			    	msgText = "找不到请求的资源";
			        break;	
			     case 500:
			    	msgText = "服务器程序发生错误";
			        break;
			     case 600:
				    msgText = "服务器数据错误";
				 break;   
			     default:
			    	msgText = "";
			    } 
				errorText = "发生异常，状态码： "+errorCode+" "+(msgText!=""?"（"+msgText+"）":"");
			}else{
				errorText = "发生异常:"+errorCode;
			}
			return errorText;
		}
	});
	
	var ScriptSource = BaseSource.extend({
		fn: function(){return false;},
		getArgs: function(cfg) {
			if (cfg.args)
				return cfg.args;
			if (cfg.element)
				return $(cfg.element).children("script[type='text/code']").attr('data-lui-args');
			return null;
		},
		initProps: function(cfg) {
			var argNames = ["done", "query"];
			if ($.isString(cfg)) {
				this.fn = new Function(argNames, cfg);
			} else if ($.isFunction(cfg)) {
				this.fn = cfg;
			} else if (cfg.element) {
				var code = this.getCodeAsText(cfg);
				var args = this.getArgs(cfg);
				if (args) {
					if (Object.isArray(args)) {
						argNames = args;
					} else {
						var str = args.toString();
						if (str == null || $.trim(str) == 0) {
							argNames = [];
						} else {
							argNames = $.map(str.split(/[,;]/), function(arg) {return $.trim(arg);});
						}
					}
				}
				this.fn = new Function(argNames, element.innerHTML);
			}
	    },
		get: function(done, ctx) {
			var self = this, fn = this.fn;
			function _callback(data) {
				self._done(data, done, ctx);
            }
            var data = fn.call(this, _callback, query);
            if (data) {
                this._done(data, done, ctx);
            }
			return this;
		}
	});
	
	var AjaxSource = UrlSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			var json = this.getCodeAsJson(cfg);
			if (json){
				this.setUrl(json.url);
			}
			this.ajaxConfig = json;
		},
		resolveUrl: function(params) {
			if(this.url){
				var tempData = {};
				$.extend(tempData, this.vars);
				//paramsEscape为url需要转义的参数，防止IE参数带中文请求失败
				var paramsEscape=strutil.urlParam(this.url, "paramsEscape");
				if(paramsEscape){
					tempData[paramsEscape]=encodeURIComponent(tempData[paramsEscape]);
				}
				if (params != null)
					$.extend(tempData, params);
				//不知道为啥组织架构中心概览初始化时报错，加个逻辑处理
				if(this.parent){
					tempData["lui.element.id"] = this.parent.id;
					tempData["lui.theme"] = ""; // TODO 取到环境参数
				}
				// 每次都使用元素参数
				this.emit('beforeResolveUrl', tempData);
				this.url = strutil.variableResolver(this._url, tempData);
					
				var ts = (new Date()).getTime();
				if(this.url.indexOf("?")>-1){
					this.url += "&t=" + ts;
				}else{
					this.url += "?t=" + ts;
				}
				this.emit('afterResolveUrl', this);
			}
			return this;
		},
		startup:function($super){
			$super();
			this.resolveUrl();
		}
	});
	
	/**
	获取服务端Json数据
	**/
	var AjaxJson = AjaxSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.dataType = 'json';
		}
	});
	
	/**
	跨域获取服务端Json数据
	**/
	var AjaxJsonp = AjaxSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.dataType = 'jsonp';
		}
	});
	
	/**
	获取服务端Xml数据,然后转换成呈现器能识别的JSON数据
	**/
	var AjaxXml = AjaxSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.dataType = 'text';
		},
		onload: function($super, data, done, ctx) {
			var source = [];
			var nodes = $(data).find("data");
			for(var i=0; i<nodes.length; i++){
				source[i] = {};
				var attNodes = nodes[i].attributes;
				for(var j=0; j<attNodes.length; j++)
					source[i][attNodes[j].nodeName] = attNodes[j].nodeValue;
			}
			$super(source, done, ctx);
		}
	});
	
	var AjaxText = AjaxSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.dataType = 'text';
			this.formatKey = cfg.formatKey || this.ajaxConfig.formatKey;
		},
		onload: function($super, data, done, ctx) {
			var source = {};
			source[this.formatKey] = data;
			$super(source, done, ctx);
		},
		startup:function($super){
			$super();
			// #50503 在portlet中，可以通过render传入参数，参数名固定为extend
			if(this.parent && this.parent.render && this.parent.render.config && this.parent.render.config.param){
				var extend = this.parent.render.config.param.extend;
				if(extend) {
					this.url = Com_SetUrlParameter(this.url, "extend", extend);
				}
			}
		}
	});
	
	var AjaxJsonpText = AjaxSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.dataType = 'jsonp';
			this.formatKey = cfg.formatKey || this.ajaxConfig.formatKey;
		},
		onload: function($super, data, done, ctx) {
			var source = {};
			source[this.formatKey] = data.text;
			$super(source, done, ctx);
		}
	});
	
	/**
	静态数据源
	**/
	var Static = BaseSource.extend({
		initProps : function($super, cfg){
			$super(cfg);
			this.vars = {};
			$.extend(this.vars, this.config.vars);
		},
		startup : function(){
			var codeText = this.getCodeAsText(this.config);
			if (codeText){
				var tempData = {};
				$.extend(tempData, this.vars); 
				tempData["lui.element.id"] = this.parent.id;
				tempData["lui.theme"] = ""; // TODO 取到环境参数
				codeText = strutil.variableResolver(codeText, tempData);
				codeText = strutil.toJSON(codeText);
			}			
			this.source = this.config.datas || codeText;
		},
		get: function(done, ctx) {
			this._done(this.source, done, ctx);
			return this;
		}
	}); 
	

	exports.BaseSource = BaseSource;
	exports.UrlSource = UrlSource;
	exports.ScriptSource = ScriptSource;
	exports.AjaxJson = AjaxJson;
	exports.AjaxJsonp = AjaxJsonp;
	exports.AjaxXml = AjaxXml;
	exports.AjaxText = AjaxText;
	exports.AjaxJsonpText = AjaxJsonpText;
	exports.Static = Static;
	
});