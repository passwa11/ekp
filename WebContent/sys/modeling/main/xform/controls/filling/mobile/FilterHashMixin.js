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
	"mui/property/HashMixin"
], function(declare, lang, topic, HashMixin) {
  return declare("sys.modeling.main.xform.controls.filling.mobile.FilterHashMixin", [ HashMixin ], {

	  startup: function(){
		  this.inherited(arguments);
	  },
	  
	  postCreate: function(){
		  this.inherited(arguments);
	  },
	  
	  handleViewChanged: function(view){
		  if(!this.isSameChannel(view.key)){
			  return;
		  }
		  //hash.replaceQuery(this._addPrefix(this.values));
	  },
	  
	  // 属性筛选器发生变化时，更新地址栏hash的query参数
	  submit: function(){
		  this.inherited(arguments);
		  this.defer(function(){
			 // hash.replaceQuery(this._addPrefix(this.values));
		  }, 1);
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
    
  });
})
