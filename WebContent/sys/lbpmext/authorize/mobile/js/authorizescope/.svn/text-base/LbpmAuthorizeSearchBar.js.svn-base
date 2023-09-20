define([ "dojo/_base/declare","mui/search/SearchBar","mui/util","dojo/topic","dojo/_base/lang","dojo/dom-style"],
		function(declare,SearchBar,util,topic,lang,domStyle){
	
	return declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeSearchBar",[ SearchBar],{
		
		searchUrl : '/sys/lbpmext/authorize/lbpm_authorize_scope/lbpmAuthorizeScope.do?method=getAllList',
		
		//搜索结果直接挑转至searchURL界面
		jumpToSearchUrl:false,
				
		//是否需要输入提醒
		needPrompt:false,
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/mui/category/changed","_cateChange");
		},
		
		_cateChange : function(srcObj,evt){
			if(srcObj.key==this.key && this.searchNode.value){
				this.searchNode.value = "";
				if(this.clearNode) {
					domStyle.set(this.clearNode, {
						display : 'none'
					});
				}
			}
		},
		
		_onClear : function(evt) {
			this.searchNode.value = "";
			if(this.clearNode) {
				domStyle.set(this.clearNode, {
					display : 'none'
				});
			}
			this.defer(function() {
				//this._hidePrompt();
				//this._hideLayer();
				topic.publish("/mui/search/cancel",this);
			}, 450);
		},
		
		_onblur:function(srcObj) {
			topic.publish("/mui/search/onblur",this);
			this._searchFocus = false;
			var self = this;
			this.defer(function() {
				domStyle.set(self.buttonArea, {
					display : 'none'
				});
			}, 0);
		}
	});
});