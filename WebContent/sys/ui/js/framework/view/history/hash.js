/**
 * hash形式实现
 */
define(function(require, exports, module) {
	
	var BaseHistory = require('./base');
	var env = require('lui/util/env');
	
	var Hash = BaseHistory.extend({
		
		listen : function(callback){
			
		},
		
		generateState : function(state){
			var hashParam ={ 'j_start':state.url, 'j_target':state.target };
			var customHashParams = state.customHashParams;
			if(customHashParams){
				for(key in customHashParams){
					var value = customHashParams[key];
					if(key.indexOf("c_")==0){
						hashParam[key]=value;
					}
				}
			}
			var ___url =  this.ensureURL(hashParam),
				uuId = ___url;
			state = $.extend({ uuId : uuId, ___url : ___url },state || {});
			this.states[uuId] = state;
			return state;
		},
		
		push : function(state){
			state = this.generateState(state);
			var ___url = state.___url,
				___hash =  ___url.indexOf('#') > -1 ? ___url.substring(___url.indexOf('#'),___url.length) : '';
			window.location.hash = ___hash;
		},
		
		replace : function(state){
			//this.push(state);
		},
		
		ensureURL : function(params){
			var pathname = window.location.pathname,
				search = window.location.search,
				hash = '';
			var str = [];
			// 兼容IE8，给字符串对象添加startsWith方法
			if (typeof String.prototype.startsWith !== 'function') {
			    String.prototype.startsWith = function(prefix) {
			        return this.slice(0, prefix.length) === prefix;
			    };
			}
			for(key in params){
				var value = params[key];
				if(key == 'j_start' && value.startsWith(env.fn.getConfig().contextPath)){
					value = value.substr(env.fn.getConfig().contextPath.length);
				}
				if(key == 'j_target' && value == 'rIframe'){
					value = 'iframe'; //修正target
				}
				if(value)
					hash = this.setParameter(hash, key , value);
			}
			return pathname + search + hash;
		},
		
		getParameter : function(param){
			var hash = window.location.hash,
				params = hash ? hash.substr(1).split("&") : [];
			for(var i = 0; i < params.length; i++){
				var argObj = params[i].split("=");
				if(argObj.length == 2 && argObj[0] == param){
					return argObj[1];
				}
			}
			return null;
		},
		
		setParameter : function(hash, key ,value){
			var re = new RegExp();
			re.compile("([\\#&]"+ key +"=)[^&]*", "i");
			if(!value){
				if(re.test(hash)){
					hash = hash.replace(re, "");
				}
			}else{
				value = encodeURIComponent(value);
				if(re.test(hash)){
					hash = hash.replace(re, '$1' + value);
				}else{
					hash += (hash.indexOf('#')==-1 ? '#' : '&') + key + "=" + value;
				}
			}
			if(hash.charAt(hash.length-1) == '?')
				hash = hash.substring(0, hash.length-1);
			return hash;
		}
		
	});
	
	module.exports = Hash;
	
});
