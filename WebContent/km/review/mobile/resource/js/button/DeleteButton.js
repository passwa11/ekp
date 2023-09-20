define([
        "mui/tabbar/TabBarButton",
    	"dojo/_base/declare",
    	"dojo/dom-construct",
    	"mui/util",
    	"dojo/dom-attr",
		"mui/dialog/Confirm",
		"dojo/request",
	 	"mui/i18n/i18n!km-review:kmReviewMain.mui"
	], function(TabBarButton, declare, domConstruct, util,domAttr,confirm,request,mui) {

	return declare("km.review.mobile.resource.js.button.DeleteButton", [TabBarButton], {
		_url:'',
		label:mui['kmReviewMain.mui.delete'],
		_onClick : function(evt) {
			_url = top.location.href;
			var _formatUrl = util.setUrlParameter(_url,"method","delete");
			confirm(mui['kmReviewMain.mui.isDelete'],null,function(tip){
				if(tip){
					var _form = document[this.formName]||document.forms[0];
					_form.method = 'get';
					// 去除所有校验
					var sEvent = dojo.clone(Com_Parameter.event);
					Com_Parameter.event["submit"] = [];
					Com_Parameter.event["confirm"] = [];
					Com_Parameter.event["submit_failure_callback"] = [];
					if(!(Com_Submit(document[this.formName]||document.forms[0], 'delete'))){
						// 提交失败还原校验
						Com_Parameter.event = sEvent;
					}
				}
			});
		}
		
	});
});