define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "mui/i18n/i18n!km-forum:kmForumTopic.fdReplyCount"
	], function(declare, domConstruct,Msg) {
	var create = declare("km.forum.mobile.resource.js.ForumTopicReplayMixin", null, {
		
		align:"center",
		
		count:0,
		
		postCreate : function() {
			this.inherited(arguments);
			var self=this;
			this.subscribe("/km/forum/replaySuccess",'changeCount');
			this.subscribe("/mui/list/loaded",function(wgt){
				if(wgt.totalSize){
					 self.set("count",wgt.totalSize);	
				}
			});
		},
		
		_onClick:function(evt){
			this.replayPost();
		},
		
		changeCount:function(){
			this.count = this.count + 1; 
			this.labelNode.innerHTML = Msg['kmForumTopic.fdReplyCount']+"("+ this.count+")";
		},
		
		_setCountAttr:function(count){
			if(count>0){
				this.labelNode.innerHTML =  Msg['kmForumTopic.fdReplyCount']+"("+ this.count+")" ;
			}else{
				this.labelNode.innerHTML =Msg['kmForumTopic.fdReplyCount'];
			}
		}
	});
	return create;
});