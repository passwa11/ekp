/**
 * hash工具类<br>
 * 注意：当前版本KK在做分享的时候会丢失hash参数
 */
define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/_base/array",
	'dojo/json'
],function(declare, lang, array, json) {

	/**
	 * 从地址栏上获取hash并转为对象
	 */
	var fromHash = function() {
		var params = window.location.hash ? window.location.hash.substr(1).split("&") : [], 
			paramsObject = {};
		for (var i = 0; i < params.length; i++) {
			var a = params[i].split("=");
			paramsObject[a[0]] = decodeURIComponent(a[1]);
		}
		return paramsObject;
	};
			
	/**
	 * 将对象转为hash字符串
	 */
	var toHashStr = function(params){
		var str = [];
		for ( var p in params) {
			str.push(p + "=" + encodeURIComponent(params[p]));
		}
		return str.join("&");
	};
	
	/**
	 * 添加hash记录
	 */
	var pushHash = function(params) {
		window.location.hash = toHashStr(params);
	};
	
	/**
	 * 替换hash记录
	 */
	var replaceHash = function(params){
		window.history.replaceState(null, null, '#' + toHashStr(params));
	};

	/**
	  	将query字符串解析为对象
	  	docCategory:x1574329715528x;docStatus:30;docStatus:40;mydoc:{"expr": "docStatus=10"} 
	  	=>
	 	{
	 		docCategory: 'x1574329715528x',
	 		docStatus: ['30', '40'],
	 		mydoc: {
	 			expr: 'docStatus=10'
	 		}
	 	}
	 */
	var parseQuery = function(queryStr){
		var queryParameters = {};
		if(!queryStr){
			return queryParameters;
		}
		// 查询条件之间以;分割
		var arr = queryStr.split(';');
		array.forEach(arr, function(element){
			if(!element){
				return;
			}
			// 查询条件的key、value以:分割
			var splitIndex= element.indexOf(':');
			var key = element.substring(0, splitIndex);
			var value = element.substring(splitIndex + 1);
			// 解析形如{a:b}的value为对象
			if(/^{.*}$/.test(value)){
				value = json.parse(value);
			}
			if(queryParameters[key]){
				// 如果存在值，将value转化为数组
				value = lang.isArray(queryParameters[key]) ? 
					queryParameters[key].concat(value) : [queryParameters[key] ,value];
			}
			queryParameters[key] = value;
		});
		return queryParameters;
	};
	
	/**
	 * 将query对象转化为字符串
	 	{
	 		docCategory: 'x1574329715528x',
	 		docStatus: ['30', '40'],
	 		mydoc: {
	 			expr: 'docStatus=10'
	 		}
	 	}
	 	=>
	 	docCategory:x1574329715528x;docStatus:30;docStatus:40;mydoc:{"expr": "docStatus=10"};
	 */
	var stringifyQuery = function(queryParameters){
		var queryStr = ''
		for(var key in queryParameters){
			var value = queryParameters[key];
			if(lang.isString(value)){
				// 字符串
				// bad hack...心累了,就先对ordertype做个特殊处理先吧
				if(value || key === 'ordertype'){
					queryStr += key + ':' + value + ';';
				}
			}else if(lang.isArray(value)){
				// 数组，拆分成多个
				array.forEach(value, function(element, index, arr){
					if(element || (arr.length > 1 && !element)){
						queryStr += key + ':' + element + ';';
					}
				});
			}else if(lang.isObject(value)){
				// 对象，解析成json字符串
				queryStr += key + ':' + json.stringify(value) + ';';
			}
		}
		return queryStr;
	};
	
	var claz = declare("mui.hash", null,{

		get : function(param) {
			var params = fromHash();
			if (param) {
				return params[param];
			} else {
				return params;
			}
		},

		add : function(newParams) {
	
			var params = fromHash();
			for ( var p in newParams) {
				params[p] = newParams[p];
			}
			pushHash(params);
		},

		replace : function(newParams){
			
			var params = fromHash();
			for ( var p in newParams) {
				params[p] = newParams[p];
			}
			replaceHash(params)
			
		},
						
		remove : function(removeParams) {
			removeParams = (typeof (removeParams) == 'string') ? [ removeParams ]
					: removeParams;
			var params = fromHash();
			for (var i = 0; i < removeParams.length; i++) {
				delete params[removeParams[i]];
			}
			pushHash(params);
		},

		clear : function() {
			pushHash({});
		},
		
		/** 
		 	EKP移动端hash规范: #path=a/b&query=docCategory:1;publishTime:2018;publishTime:2019;mydoc:{"expr":"docStatus=10"}&listener=listener157431
		 	EKP移动端hash定义了几个特殊参数: path、query、listener
		  		1.path: 与当前定位的NavItem相关
		  		2.query: 与分类/筛选/排序信息相关
		  		3.listener: 与内部弹出框，内部切换页面相关；此参数不参与初始化
		 */
		
		
		/**
		 * 页面是否开启hash
		 */
		canHash: function(){
			return dojoConfig && dojoConfig.canHash;
		},
		
		/**
		 * 获取hash path
		 * @return Array  为了后续支持多级，返回数组
		 */
		getPath: function(){
			var path = this.get('path');
			if(!path){
				return [];
			}
			if(path.startsWith('/')){
				path = path.substring(1);
			}
			return path ? path.split('/') : [];
		},
		
		/**
		 * 替换hash path
		 * @param pathArray 为了后续支持多级，传递数组
		 */
		replacePath: function(pathArray){
			if(!pathArray || pathArray.length === 0){
				return '';
			}
			var path = pathArray.join('/');
			this.replace({ path: path });
		},
		
		/**
		 * 是否包含在当前路径中
		 */
		matchPath: function(comparePath){
			var path = this.get('path');
			if (!path) {
				path = '0';
			}
			// 目前仅支持1级路径，先用===判断
			return path === comparePath;
		},
		
		/**
		 * 获取hash query
		 */
		getQuery: function(param, excludeParam){
			var queryStr = this.get('query');
			if(!queryStr){
				return param ? null : {};
			}
			var parameterMap = parseQuery(queryStr);
			return param ? parameterMap[param] : parameterMap;
		},
		
		/**
		 * 替换hash query
		 */
		replaceQuery: function(parameterMap){
			var queryStr = this.get('query');
			var queryParameters = parseQuery(queryStr);
			if(queryParameters){
				queryParameters = Object.assign(queryParameters, parameterMap);
			}else{
				queryParameters = parameterMap;
			}
			this.replace({ query: stringifyQuery(queryParameters) });
		},
		
		/**
		 * 清理掉掉query(replace方式)
		 */
		clearQueryR: function(){
			this.replace({ query: '' })
		}
		
	});

	return new claz();
});