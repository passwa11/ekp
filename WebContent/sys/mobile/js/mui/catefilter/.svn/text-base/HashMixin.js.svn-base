/**
 * Hash Mixin，作用如下:
 * 	1.根据初始hash设置分类筛选器默认值
 * 	2.列表视图切换后调整hash的query参数
 * 	3.分类筛选器操作后调整hash的query参数
 */
define([
	"dojo/_base/declare", 
	"dojo/topic", 
	"mui/hash",
	"mui/ChannelMixin"
], function(declare, topic, hash, ChannelMixin) {
  return declare("mui.catefilter.HashMixin", [ ChannelMixin ], {

	  postMixInProperties: function(){
		  this.inherited(arguments);
		  // 匹配路径的分类筛选器才需要初始化
		  if(hash.matchPath(this.key)){
			  var catekey = this.getCatekeyWithPrefix();
			  var curIds = hash.getQuery(catekey);
		      if (curIds) {
		        this.curIds = curIds
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
		  var queryParameters = {};
		  var catekey = this.getCatekeyWithPrefix();
		  queryParameters[catekey] = this.curIds;
		  hash.replaceQuery(queryParameters);
	  },
	  
	  submit: function(evt){
		  this.inherited(arguments);
		  var queryParameters = {};
		  var catekey = this.getCatekeyWithPrefix();
		  queryParameters[catekey] = evt.curIds;
		  hash.replaceQuery(queryParameters);
	  },
	  
	  getCatekeyWithPrefix: function(){
		  var prefix = typeof(this.prefix) === 'undefined' ? 'q' : this.prefix;
		  return prefix ? (prefix + '.' + this.catekey) : this.catekey;
	  }
    
  });
})
