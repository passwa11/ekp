
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var MyCrypto =require('lui/util/crypto');
	exports.decodeOutHTML = function(html){
	    //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement ("div");
        //2.然后将要转换的字符串设置为这个元素的innerText或者textContent
        (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
        var output = temp.innerHTML;
        temp = null;
        return output;
    };
    
	exports.decodeHTML = function(str){
		return str.replace(/&quot;/g, '"')
           .replace(/&apos;/g, '\'')
		   .replace(/&gt;/g, '>')
           .replace(/&lt;/g, '<')
           .replace(/&amp;/g, '&')
		   .replace(/&sup1;/g, "¹")
		   .replace(/&sup2;/g, "²")
		   .replace(/&sup3;/g, "³");
    };
    exports.encodeHTML = function(str){ 
		return str.replace(/&/g,"&amp;")
			.replace(/</g,"&lt;")
			.replace(/>/g,"&gt;")
			.replace(/\"/g,"&quot;")
			.replace(/¹/g, "&sup1;")
			.replace(/²/g, "&sup2;")
			.replace(/³/g, "&sup3;");
	};
    exports.toJSON = function(str){
		// 去除特殊字符，防止IE浏览器转换失败
		if(str) {
			str = str.replace(/\u2028/g, "").replace(/\u2029/g, "");
		}
		return (new Function("return (" + str + ");"))();
	};
	exports.toJson = exports.toJSON;
	
	exports.upperFirst = function(str) {
		return str.charAt(0).toUpperCase() + str.substring(1);
	};
	exports.variableResolver = function(str, data) {  
		 //aaa!{bb}asdf!{cc},正则查找替换!{}之间的值 
		 function extend(destination, source) {
				for (var property in source)
				  destination[property] = source[property];
				return destination;
		 }
		 data = extend(data,seajs.data.env.config);
	     return str.replace(/\!\{([\w\.]*)\}/gi, function (_var, _key) {
	          var value = data[_key];  

	          if ($.isArray(value) && value.length > 0){
	        	  value = value[0];  
	          }
				
	          return (value === null || value === undefined) ? "" : value;  
	     });
	}; 
	exports.errorMessage = function(str,url,num){
		num = num || 0;
		return str.replace(/\), <anonymous>:([0-9]+):([0-9]+)\)/g, function (_var, _row, _col) {
	          return _var + "\n <span style='color:red'><b>"+(url ? url : "")+",行:"+(parseInt(_row)+parseInt(num))+",列:"+_col+" 出错了</b></span> ";  
	    }).replace(/(\r|\n)/g,"<br>");
	};
	exports.urlParam = function(qstr, name) {
		var pre = name + "=";
		var start, end, changed = true;
		if ((start = qstr.indexOf(pre)) > -1) {
			end = qstr.indexOf('&');
			if (end < 0)
				end = qstr.length;
			return qstr.substring(start + pre.length, end);
		}
		return null;
	};
	
	exports.textEllipsis = function(str, num) {
		if (str) {
			if (str.length * 2 <= num)
				return str;
			var rtnLength = 0;
			for (var i = 0; i < str.length; i++) {
				if (Math.abs(str.charCodeAt(i)) > 200)
					rtnLength = rtnLength + 2;
				else
					rtnLength++;
				if (rtnLength > num)
					return str.substring(0, i)
							+ (rtnLength % 2 == 0 ? ".." : "...");
			}
			return str;
		}
	};
	
	// XSS敏感字符过滤
	var replaceFilters = [];
	// 过滤非法html对象
	replaceFilters.push(function(str) {
		return str.replace(/<(form|input|select|option|script|link|iframe)[^>]*>/ig, "").replace(/<\/(form|input|select|option|script|link|iframe)>/ig, "");
	});
	// 过滤Object标签中的data属性
	replaceFilters.push(function(str) {
		return str.replace(/(<object[^>]*?)(data\s*=\s*["|'][\s\S]*?["|'])([^>]*>)/ig, "$1$3");
	});
	// 过滤form
	replaceFilters.push(function(str) {
		return str.replace(/<(form|input|select|option)[^>]*>/ig, "").replace(/<\/(form|input|select|option)>/ig, "");
	});
	// 过滤触发脚本
	replaceFilters.push(function(str) {
		return str.replace(/<([a-z][^>]*)[\s|\\]+?on[a-z]+\s*=\s*("[^"]+"|'[^']+'|[^\s]+)([^>]*)>/ig, "<$1$3>");
	});
	exports.filter = function(str) {
		var rtn = str;
		var fns = replaceFilters;
		for (var i = 0; i < fns.length; i++) {
			rtn = fns[i](rtn);
		}
		return rtn;
	} 
	var aseKey = "MEkp20201231DFAA";
	//加密CryptoJS
	exports.encryptStr = function(str) { 
		return MyCrypto.CryptoJS.AES.encrypt(str, MyCrypto.CryptoJS.enc.Utf8.parse(aseKey), {
		    mode: MyCrypto.CryptoJS.mode.ECB,
		    padding: MyCrypto.CryptoJS.pad.Pkcs7
		  }).toString();
	}  
	exports.decryptStr = function(str) {
		//解密
		return MyCrypto.CryptoJS.AES.decrypt(str, MyCrypto.CryptoJS.enc.Utf8.parse(aseKey), {
		    mode: MyCrypto.CryptoJS.mode.ECB,
		    padding: MyCrypto.CryptoJS.pad.Pkcs7
		  }).toString(MyCrypto.CryptoJS.enc.Utf8);
		
	}
});