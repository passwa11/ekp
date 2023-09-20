define([ "dojo/_base/declare","mui/search/SearchBar","mui/util","dojo/topic","dojo/_base/lang","dojo/dom-style"
		, "mui/i18n/i18n!sys-mobile","dojo/dom-attr"],
		function(declare,SearchBar,util,topic,lang,domStyle,Msg,domAttr){
	
	return declare("sys.xform.mobile.controls.relevance.RelevanceSearchBar",[ SearchBar],{
		
		searchCateUrl : '/sys/xform/controls/relevance.do?method=searchCateList&keyword=!{keyword}',
		
		searchDocUrl : '/sys/xform/controls/relevance.do?method=searchDocList&keyword=!{keyword}',
		
		//搜索结果直接挑转至searchURL界面
		jumpToSearchUrl:false,
				
		//是否需要输入提醒
		needPrompt:false,

		//提示文字
		placeHolder: Msg['mui.search.hint.doc'],
		
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
			//搜索框提示信息处理
			if(evt.isBase){
				domAttr.set(this.searchNode,"placeHolder",Msg['mui.search.hint.model']);
			}else if(evt.isBase == false){
				domAttr.set(this.searchNode,"placeHolder",Msg['mui.search.hint.doc']);
			}else{
				if(typeof evt.fdId == 'undefined' ||  evt.fdId == '' ){
					domAttr.set(this.searchNode,"placeHolder",Msg['mui.search.hint.model']);
				}else{
					domAttr.set(this.searchNode,"placeHolder",Msg['mui.search.hint.doc']);
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
			if(this.buttonArea){
				domStyle.set(this.buttonArea, {
			        display: "none"
			      })
			}
			topic.publish("/mui/search/cancel/back",this);
		},
		
		_onSearch : function(evt) {
			this.searchNode.blur(); 
			this._eventStop(evt);
			if (this.searchNode.value != '' || this.emptySearch) {
				var arguObj = lang.clone(this);
				arguObj.keyword = encodeURIComponent(this.searchNode.value);
				
				var cateUrl =  util.formatUrl(util.urlResolver(
						this.searchCateUrl, arguObj));

				var docUrl =  util.formatUrl(util.urlResolver(
						this.searchDocUrl, arguObj));
				
				topic.publish("/sys/xform/relevance/search",this,
					{   keyword : arguObj.keyword ,
						cateUrl : cateUrl,
						docUrl : docUrl,
						modelName : arguObj.modelName,
						fdControlId : arguObj.fdControlId
					});
			}
			return false;
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