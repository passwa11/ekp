/**
 * Hash Mixin，作用如下:
 * 	1.根据初始hash设置筛选器默认值
 * 	2.列表视图切换时，更新地址栏hash
 * 	3.属性筛选器操作后调整hash
 */
define([
	"dojo/_base/declare", 
	"dojo/_base/lang",
	"dojo/topic", 
	"mui/hash",
	"mui/ChannelMixin"
], function(declare, lang, topic, hash, ChannelMixin) {
  return declare("mui.property.HashMixin", [ ChannelMixin ], {

	  startup: function(){
		  this.inherited(arguments);
		  // 匹配路径的筛选器才需要初始化
		  if(hash.matchPath(this.key)){
			  var values = hash.getQuery();
		      if (values) {
		    	  // 赋值前，移除前缀、移除不属于本筛选器管辖的属性
		    	  this.values = this._removeOtherProps(this._removePrefix(values));
		      }
		  }
	  },
	  
	  postCreate: function(){
		  this.inherited(arguments);
		  // 列表视图切换时，更新地址栏hash的query参数
		  this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
	  },
	  
	  handleViewChanged: function(view){
		  if(!this.isSameChannel(view.key)){
			  return;
		  }
		  hash.replaceQuery(this._addPrefix(this.values));
	  },
	  
	  // 属性筛选器发生变化时，更新地址栏hash的query参数
	  submit: function(){
		  this.inherited(arguments);
		  this.defer(function(){
			  hash.replaceQuery(this._addPrefix(this.values));
		  }, 1);
	  },
    
	  _findPrefix: function(key){
		  if(this.filters && this.filters.length > 0){
				for(var i = 0; i < this.filters.length; i++){
					var filter =  this.filters[i];
					if(filter.name === key && typeof(filter.prefix) !== 'undefined'){
						return filter.prefix ? filter.prefix + '.' : '';
					}
					if(filter.name === key){
						break;
					}
				}
			}
			return 'q.';
	  },
	  
	  // 设置hash前给key添加前缀
	  _addPrefix: function(values){
		  var newValues = {};
		  for(var key in values){
			  var newkey = this._findPrefix(key) + key;
			  newValues[newkey] = values[key];
		  }
		  return newValues;
	  },
	  
	  // 将hash中的属性设置回this.values前移除key的前缀
	  _removePrefix: function(values){
		  var newValues = {};
		  for(var key in values){
			  var reg = new RegExp('^' + this._findPrefix(key));
			  var newkey = key.replace(reg, '');
			  newValues[newkey] = values[key];
		  }
		  return newValues;
	  },
	  
	  // 移除与本筛选项无关的属性
	  _removeOtherProps: function(values){
		  var newValues = {};
		  // 该key值是不是本筛选器拥有的
		  var existKey = lang.hitch(this, function(key){
			  // 分类属性
			  if(key.indexOf('_prop_.') > -1) 
				  return true;
			  if(!this.filters || this.filters.length == 0){
				  return false;
			  }
			  for(var i = 0; i < this.filters.length; i++){
				  var filter = this.filters[i];
				  if(filter.name === key){
					  return true;
				  }
			  }
		  });
		  for(var key in values){
			  if(existKey(key)){
				  newValues[key] = values[key];
			  }
		  }
		  return newValues;
	  }
    
  });
})
