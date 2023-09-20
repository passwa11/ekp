/**
 * 更新列表URL Mixin
 * bad hack...目前所有筛选器的监听逻辑杂糅在一起,后续重新设计机制拆分
 */
define([
	'dojo/_base/declare',
	'dojo/_base/lang',
	'dojo/_base/array',
	'dojo/topic',
	'mui/util',
	'mui/hash',
	'mui/ChannelMixin',
	'dojo/request'
], function(declare, lang, array, topic, util, hash, ChannelMixin, request){
	
	return declare('mui/list/JsonStoreListHashMixin', [ ChannelMixin ], {
		
		postMixInProperties: function(){
		  this.inherited(arguments);
		  // 更新前查询对象
		  this.previousQueryData = {};
		  //最新查询对象
		  this.queryData = {};
		},
		
		startup: function(){
			var isSameChannle = hash.matchPath(this.key);
			var canHash = hash.canHash();
			// TODO: 处理lazy=false的情况
			if(isSameChannle && canHash){
				var queryData = hash.getQuery();
				if(queryData){
					this.queryData = this.__removeQueryNamePrefix(queryData);
				}
			}
			this.inherited(arguments);
			// 匹配路径的列表才需要初始化
			if(isSameChannle && canHash){
				// 导航栏上的url更新
			    var container = this.getParent();
			    if(container && container.rel && container.rel.url){
			    	container.rel.url = this.__formatQueryUrl(container.rel.url);
			    }
			}
		},
		
		postCreate: function(){
			this.inherited(arguments);
			if(!hash.canHash()){
				return;
			}
			// 监听分类变化
			this.subscribe('/mui/catefilter/confirm', 'onCate');
			// 获取统计数
			this.subscribe('/mui/list/count', 'onCount');
			// 监听筛选器变化
			this.subscribe('/mui/property/filter', 'onFilter');
		},
		
		onCount: function(obj, evt) {
		      if (!this.isSameChannel(obj.key) || !evt) {
		        return
		      }

		      this.previousQueryData = lang.clone(this.queryData)
		      for (var key in evt.values) {
		        this.queryData[key] = evt.values[key]
		      }
		      var container = this.getParent()
		      if (!container) {
		        return
		      }
		      var countUrl = this.__formatQueryUrl(container.rel.url)
		      countUrl = util.formatUrl(countUrl + "&rowsize=1")
		      request
		        .post(countUrl, {
		          handleAs: "json"
		        })
		        .then(function(data) {
		          var count = 0
		          if (data.page && data.page.totalSize) {
		            count = parseInt(data.page.totalSize)
		          }else{
		        	  if(data[data.length - 1] && data[data.length - 1].totalSize){
		        		  count = parseInt(data[data.length - 1].totalSize)
		        	  }else{
		        		  count = data.length;
		        	  }
		          }
		          evt.callback(count)
		        })

		      this.queryData = lang.clone(this.previousQueryData)
	    },
		
		onCate: function(obj, evt){
			if(!this.isSameChannel(obj.key) || !evt){
				return;
			}
			// 优化减少请求
			if(this.queryData[evt.name] === evt.value){
				return
			}
			
			// 设置查询条件
			this.previousQueryData = lang.clone(this.queryData);
			this.queryData[evt.name] = evt.value;
		    // 修改url并刷新
		    this.__reload();
		},
		
		onFilter: function(obj, values){
			if(!this.isSameChannel(obj.key) || !values){
				return;
			}
			// 设置查询条件
			this.previousQueryData = lang.clone(this.queryData);
			for(var key in values){
				this.queryData[key] = values[key];
			}
		    // 修改url并刷新
		    this.__reload();
		},
		
		__reload: function(){
			// 使列表回到顶部
		    topic.publish("/mui/list/toTop", this, {t: 0});
			// 更新url查询条件
		    var container = this.getParent();
		    if(!container){
		    	return;
		    }
		    var url = this.__formatQueryUrl(container.rel.url);
		    // 导航栏上的url更新，以便下次能获取到最新值
		    container.rel.url = url;
		    // 修改列表组件绑定的url
		    this.url = util.formatUrl(url);
		    // 刷新列表
		    this.reload();
		},
		
		__formatQueryUrl: function(url){
			for(var key in this.queryData){
		    	var value = this.queryData[key];
		    	var querykey = this.__formatQueryName(key, value);
		    	// 先清除查询条件:
		    	//  1、将对应querykey的值清除 
		    	//  2、将previousQueryData中对应的表达式清除
		    	url = util.setUrlParameter(url, querykey, null);
		    	if(this.previousQueryData[key] && this.previousQueryData[key].expr){
		    		url = url.replace('&' + this.previousQueryData[key].expr, '');
		    	}
		    	// 重新设置查询条件:
		    	// 如果存在expr表达式，直接使用;否则使用querykey=value的形式
		    	if(lang.isObject(value) && value.expr){
		    		url += '&' + value.expr;
		    	}else{
		    		value = value.value ? value.value : value;
		    		value = lang.isString(value) ? [value] : value;
		    		array.forEach(value, function(v){
		    			url += '&' + querykey + '=' + encodeURIComponent(v)
		    		}, this);
		    	}
		    }
			return url;
		},
		
		// 移除query参数中的前缀；
		// TODO: 这个实现目前是有瑕疵的; 如果query的key刚好等于就是q.这种形式开头的就会误改了key
		__removeQueryNamePrefix: function(queryData){
			for(var key in queryData){
				var value = queryData[key];
				var isObject = lang.isObject(value) && !lang.isArray(value);
				if(/^(\w+)\.(.*)/.test(key)){ // 存在前缀
					queryData[RegExp.$2] = isObject ? 
							Object.assign({ prefix: RegExp.$1 }, value) : { prefix: RegExp.$1, value: value };
					delete queryData[key];
				}else{
					queryData[key] = isObject ? 
							Object.assign({ prefix: '' }, value) : { prefix: '', value: value };
				}
			}
			return queryData;
		},
		
		__formatQueryName: function(queryName,queryValue){
			var isObject = lang.isObject(queryValue) && !lang.isArray(queryValue);
			if(isObject && queryValue.prefix){
				return queryValue.prefix + '.' + queryName;
			}
			if(queryValue && queryValue.prefix === ''){
				return queryName;
			}
			return 'q.' + queryName;
		}
		
	});
	
});