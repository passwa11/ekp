define([
	"dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"km/review/mobile/km_review_feedback_info/js/list/FeedbackInfoItemMixin"
], function(declare, _TemplateItemListMixin, itemMixin) {

	return declare("km.review.mobile.km_review_feedback_info.js.list.FeedbackInfoListMixin", [_TemplateItemListMixin], {

		itemTemplateString : null,

		itemRenderer: itemMixin ,

		onComplete: function(items) {
			this.inherited(arguments);
			var tempList = document.getElementsByClassName("feedbackInfoDiv_1_2_1_5");
			if(tempList && tempList.length > 0){
				tempList[tempList.length-1].remove(); // 移除最后一个元素的虚线
			}
		}

	});

});
