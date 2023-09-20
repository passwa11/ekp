define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	
	var fn = function(){
		this.config = null;
	};
	fn.prototype.authURL = function(url) {
		var result = false;
		$.ajax({
			url : Com_Parameter.ContextPath + 'sys/common/trustsite.do?method=authURL',
			type : 'POST',
			data : {url : url},
			async : false,
			dataType : 'json',
			success : function(data){
				if(data && data.result === true){
					result = true;
				}
			}
		});
		return result;
	};
	fn.prototype.authShow = function(roles) {
		var result = false;
		$.ajax({
			url : Com_Parameter.ContextPath + 'sys/common/trustsite.do?method=authShow',
			type : 'POST',
			data : {roles : roles},
			async : false,
			dataType : 'json',
			success : function(data){
				if(data && data.result === true){
					result = true;
				}
			}
		});
		return result;
	};
	fn.prototype.getConfig = function() {
		if(this.config == null){
			return seajs.data.env;
		}else{
			return this.config;
		}
	};
	
	fn.prototype.formatUrl = function(url, isFull) {

		if (url == null) {
			return "";
		}
		
		var targetUrl = url;

		if (url.substring(0, 1) == '/') {
			targetUrl = this.getConfig().contextPath + url;
		}

		// 全路径
		if (isFull
				&& !(new RegExp('^http').test(targetUrl) || new RegExp('^https')
						.test(targetUrl))) {

			var host = location.protocol.toLowerCase() + "//"
					+ location.hostname;
			if (location.port && location.port != '80') {
				host = host + ":" + location.port;
			}

			targetUrl = host + targetUrl;
		}

		return targetUrl;
	};
	
	// 清除html标签
	fn.prototype.clearHtml = function(str) {
		
		if(!str)
			return "";
		
		return str.replace(/<[^>]+>/g, "");
		
	};
	
	fn.prototype.formatText = function(str) {
		if (str==null || str.length == 0)
			return "";
		return str.replace(/&/g, "&amp;")
			.replace(/</g, "&lt;")
			.replace(/>/g, "&gt;")
			.replace(/ /g, "&nbsp;")
			.replace(/\'/g,"&#39;")
			.replace(/\"/g, "&quot;")
			.replace(/\n/g, "<br>")
			.replace(/¹/g, "&sup1;")
			.replace(/²/g, "&sup2;")
			.replace(/³/g, "&sup3;");
	};
	fn.prototype.formatDate = function(date, format){
		if(!date){
			return;
		}
		var o = { 
			"M+" : date.getMonth()+1,
			"d+" : date.getDate(),
			"H+" : date.getHours(),
			"m+" : date.getMinutes(),
			"s+" : date.getSeconds()
		}; 
		if(/(y+)/.test(format)) {
			//要么2位，要么4位
			format = format.replace(RegExp.$1, (date.getFullYear()+"").substring(RegExp.$1.length==2 ? 2 : 0));
		}
		for(var k in o) {
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substring((""+ o[k]).length)));
			}
		}
		return format; 
	};
	
	fn.prototype.parseDate = function(str, format){
		var _format = format || this.getConfig().pattern.datetime;
		var result=new Date();
		if(/(y+)/.test(_format))
			result.setFullYear(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		if(/(M+)/.test(_format))
			result.setMonth(parseInt(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length),10)-1,1);
		if(/(d+)/.test(_format))
			result.setDate(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		if(/(H+)/.test(_format))
			result.setHours(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		if(/(h+)/.test(_format)){
			var hours = str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length);
			hours = hours%12==0 ? 12:hours%12;
			result.setHours(hours);
		}
		if(/(m+)/.test(_format))
			result.setMinutes(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		if(/(s+)/.test(_format))
			result.setSeconds(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		if(/(S+)/.test(_format))
			result.setMilliseconds(str.substring(_format.indexOf(RegExp.$1),_format.indexOf(RegExp.$1)+RegExp.$1.length));
		return result;
	};
	fn.prototype.variableResolver = function(str, data) {
		 //aaa!{bb}asdf!{cc},正则查找替换!{}之间的值 
		 function extend(destination, source) {
				for (var property in source)
				  destination[property] = source[property];
				return destination;
		 }
		 data = extend(data,this.getConfig().config);
	     return str.replace(/\!\{([\w\.]*)\}/gi, function (_var, _key) {
	          var value = data[_key];  
	          return (value === null || value === undefined) ? "" : value;  
	     });
	};
	
	//是否内链接
	fn.prototype.isInternalLink = function(href) {
		if(!href){
			return false;
		}
		var tmpUrl = href;
		if(href.indexOf('?') > -1){
			tmpUrl = href.substring(0, href.indexOf('?'));
		}
		if (href.substring(0, 1) == '/' || ((new RegExp('^http').test(href) || new RegExp('^https').test(href)) && tmpUrl.indexOf(location.hostname)>-1)) {
			return true;
		}
		return false;
	};
	
	
	//是否白名单链接
	fn.prototype.isTrustSiteLink = function(href){
		var result = true;
		if(this.isInternalLink(href)){
			return true;
		}
		$.ajax({
			url : Com_Parameter.ContextPath + 'sys/common/trustsite.do?method=service&url=' + href,
			async : false,
			dataType : 'json',
			success : function(data){
				if(data && data.result === false){
					result = false;
				}
			}
		});
		return result;
	};
	
	module.exports = {
		fn : new fn(),
		fnclass : fn,
		win : window
	};
});