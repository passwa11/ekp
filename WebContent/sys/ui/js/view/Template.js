define(function(require, exports, module) {
	module.exports = function(str){
		//核心分析方法
		var _analyze = function(text){
			if(text){
				return text.replace(/<(script)[\s|\S]*?<\/\1>/ig,function(scriptHTML){//模板不支持外部网站script的嵌入
					var src = $(scriptHTML).attr("src");
					if(src){
						src = $.trim(src);
						if (src.substring(0, 1)=='/') {
							return scriptHTML;
						}
					}else{
						return scriptHTML;
					}
					return "";
				}).replace(/{\$(\s|\S)*?\$}/g,function(s){	
							return s.replace(/("|\\)/g,"\\$1")
											.replace("{$",'_s.push("')
											.replace("$}",'");')
											.replace(/{\%([\s\S]*?)\%}/g, '",$1,"').replace(/(\r\n|\n)/g,"\\$1");
				});
			}else{
				return "";
			}
		};
		//返回生成器render方法
		return {
			render:function(mapping){
				var _a = []; 
				var _v = []; 
				var i;
				for(i in mapping){
					_a.push(i);
					_v.push(mapping[i]);
				}
				var fn = new Function(_a,"var _s=[];"+_analyze(str)+" return _s.join('');");
				return fn.apply(window,_v);
			}
		};
	};	 
});