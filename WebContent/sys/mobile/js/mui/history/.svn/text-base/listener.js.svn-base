/**
 * 历史记录监听器
 * 	使用场景: 用于处理单个页面内弹层、切换视图
 */
define([ "dojo/_base/declare", "dojo/_base/lang","mui/hash" ],function(declare, lang, hash) {
	
	var noop = function(){};
	
	/**
	 * 解析hash
	 */
	var parseHash = function(hashStr){
		var params = hashStr ? hashStr.split("&") : [], 
			paramsObject = {};
		for (var i = 0; i < params.length; i++) {
			var a = params[i].split("=");
			paramsObject[a[0]] = decodeURIComponent(a[1]);
		}
		return paramsObject;
	};
	
	/**
	 * hash字符串化
	 */
	var stringifyHash = function(params){
		var str = [];
		for ( var p in params) {
			str.push(p + "=" + encodeURIComponent(params[p]));
		}
		return str.join("&");
	};
	
	var claz = declare('mui.history.listener', null, {
		
		key : 'listener',
		
		currentIndex: 0,
		
		// 存储历史记录Id序列
		historyArr: [],
		
		// 存储历史记录forwardCallback/backCallback
		historyData : {},
		
		// 临时callback
		tempCallback : null,
		
		constructor : function(){
			var self = this;
			var currentHash = this.gethash({ href: location.href });
			var currentId = this.getparam(currentHash, this.key) || 'listener';
			this.historyArr.push(currentId);
			this.currentIndex = 0;
			// hash change事件
			window.onhashchange = lang.hitch(this,function(evt){
				var previousHash = this.gethash({ href: evt.oldURL }),
					currentHash = this.gethash({ href: evt.newURL }),
					currentId = this.getparam(currentHash, this.key);
				var previousIndex = this.currentIndex;
				var currentIndex = this.historyArr.indexOf(currentId);
				// 存在tempCallback则执行,否则按先前给的forwardCallback/backCallback串行调用
				if(this.tempCallback){
					this.tempCallback();
					this.tempCallback = null;
				}else{
					var isforward = currentIndex > previousIndex;
					if(isforward){
						for(var i = previousIndex + 1; i <= currentIndex; i++){
							var historyId = this.historyArr[i];
							this.historyData[historyId].forwardCallback && this.historyData[historyId].forwardCallback();
						}
					}else{
						for(var i = previousIndex; i > currentIndex; i--){
							var historyId = this.historyArr[i];
							this.historyData[historyId].backCallback && this.historyData[historyId].backCallback();
						}
					}
				}
				// 设置当前currentIndex
				this.currentIndex = currentIndex;
				// 恢复非listener参数，此处对于hash的处理应该仅限于listener参数；
				// 如果在onhashchange事件处理前后有其他参数变化了(如query参数,通过history.replaceState改变了query)，不应该抹掉这些变化
				var previousHashObject = parseHash(previousHash);
				var currentHashObject = parseHash(currentHash);
				if(previousHashObject.listener){
					delete previousHashObject.listener; 
				}
				window.history.replaceState(null, null, '#' + stringifyHash(lang.mixin(currentHashObject, previousHashObject)));
			});
		},
		
		get: function(historyId){
			if(this.historyArr.indexOf(historyId) === -1){
				console.error('不存在或者已经被清除的historyId');
				return;
			}
			return this.historyData[historyId];
		},
		
		/**
		 * 跳转到指定历史记录
		 * @param options.step: 跳转的步数，如-1、2
		 * @param options.historyId: 跳转的历史记录Id，该Id为push函数的返回值
		 * @param options.callback: 跳转回调函数,如果不传则按原先提供的forwardCallback/backCallback串行调用。
		 * 							options.callback可以起到优化体验的作用，提供一次机会让开发可以合并多步跳转所需要执行的函数。
		 * 							options.callback仅仅会在此次跳转中执行，并不会影响原先历史记录中的forwardCallback/backCallback
		 */
		go: function(options){
			if(options && options.step){
				var nextIndex = this.currentIndex + options.step;
				if(nextIndex >= this.historyArr.length || nextIndex < 0){
					console.warn('跳转step失败', options.step);
					return;
				}
				if(options && options.callback){
					this.tempCallback = options.callback;
				}
				history.go(options.step);
				return;
			}
			if(options && options.historyId){
				var currentIndex = this.currentIndex;
				var nextIndex = this.historyArr.indexOf(options.historyId);
				if(nextIndex === -1 || currentIndex === nextIndex){
					console.warn('跳转historyId失败', options.historyId);
					window.location.reload();
					return;
				}
				if(options && options.callback){
					this.tempCallback = options.callback;
				}
				history.go(nextIndex - currentIndex);
			}
		},
		
		/**
		 * 不做任何操作跳转到指定历史记录
		 */
		goNoop: function(options){
			options = lang.mixin({ callback: noop }, options);
			this.go(options);
		},
		
		/**
		 * 增加历史记录
		 * @param backCallback: 后退时需要执行的函数，TODO:支持异步操作
		 * @param callback: backCallback alias,兼容旧函数
		 * @param forwardCallback: 前进时需要执行的函数
		 */
		push: function(options){
			var __backCallback = options.callback || options.backCallback || noop;
			var __forwardCallback = options.forwardCallback || noop;
			var uuId = this.getId();
			// 一旦增加历史记录,所有的前进操作将废除；TODO:清除冗余historyData数据
			this.historyArr = this.historyArr.slice(0, this.currentIndex + 1);
			// 放入新的历史记录
			this.historyArr.push(uuId);
			this.historyData[uuId] = {
				backCallback : __backCallback,
				forwardCallback: __forwardCallback
			};
			// 改变地址栏hash
			hash.add({ 'listener' : uuId });
			return {
				// 跳转前页面的Id
				previousId: this.historyArr[this.currentIndex],
				// 跳转后页面(即当前页面)的Id
				currentId: this.historyArr[this.currentIndex + 1]
			};
		},
		
		/**
		 * push alias, 建议使用push(push命名与history api一致，更容易理解)
		 */
		add : function(options){
			this.push(options);
		},
		
		gethash : function(evt){
			var href = evt.href || '',
				hash = evt.hash || (href.indexOf('#') > -1 ? href.substr(href.indexOf('#')) : '');
			hash = hash ? hash.substr(1) : '';
			return hash;
		},
		
		getparam : function(hash, param){
			var params = hash ? hash.split("&") : [];
			for (var i = 0; i < params.length; i++) {
				var a = params[i].split("=");
				if(a[0] == param){
					return decodeURIComponent(a[1]);
				}
			}
			return 'listener';
		},
		
		getId : function(){
			return 'listener' + new Date().getTime();
		}
		
	});
	
	return new claz();
	
});