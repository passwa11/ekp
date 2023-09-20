define([
    "mui/tabbar/TabBarButton",
	"dojo/_base/declare",
	"mui/i18n/i18n!km-forum",
	"dojo/dom-attr","dojo/dom-style",
	"km/forum/mobile/resource/js/view/ForumSortMixin"
	], function(TabBarButton, declare, Msg , domAttr,domStyle, ForumSortMixin) {
	
	return declare("km.forum.mobile.resource.js.ForumSortButton", [TabBarButton, ForumSortMixin], {
		
		sortType :"desc",
		
		totalSize:0,//总条数
		
		buildRendering:function(){
			this.inherited(arguments);
			var text = Msg['mui.sort.desc'];
			if (this.sortType=="asc"){
				text = Msg['mui.sort.asc'];
			}
			this.labelNode.innerHTML = text; // 
			domAttr.set(this.domNode, 'title', text);
		},
		
		_onClick : function(evt) {
			this.sortList(this,this.sortType);
		},
		
		changeSortType : function(){
			var text = Msg['mui.sort.desc'];
			if (this.sortType=="asc"){
				this.sortType="desc";
			}else if (this.sortType=="desc"){
				this.sortType="asc";
				text = Msg['mui.sort.asc'];
			} 
			this.labelNode.innerHTML = text; // 
			domAttr.set(this.domNode, 'title', text);
		},
		hideNoPages:function(){ //这里是隐藏按钮在页数小于等于1的时候。
			if((this.totalSize!=null) && (this.totalSize<=1)){
			    domStyle.set(this.domNode, {"display" :"none"});
			}else{
				domStyle.set(this.domNode, {"display" :""});
			}
		}
	});
});