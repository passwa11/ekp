define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmperson/mobile/resource/js/FastReviewTodoListItem",
	"dojo/topic"
	], function(declare, _TemplateItemListMixin, fastReviewTodoListItem,topic) {
	
	return declare("sys.lbpmperson.mobile.FastReviewTodoList", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: fastReviewTodoListItem,
		
		_reviewSuccess : function(){
			this.reload();
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/sys/lbpmperson/mobile/FastReviewTodo/reviewSuccess","_reviewSuccess");
		},

		onComplete: function(items) {
			this.inherited(arguments);
			if(!this.append){
				topic.publish("/sys/lbpmperson/mobile/FastReviewTodo/listReloadDataSuccess",this);
			}

		},

	});
});