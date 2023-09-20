define(
		[ "dojo/_base/declare","mui/tabbar/TabBarButton","dojo/topic"],
		function(declare,TabBarButton,topic) {
			var freeflowNodeOkBtn = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeOkBtn",
					[TabBarButton], {
						onClick : function() {
							//修复iPhoneX点击穿透问题
							if(!this._CLICK_FLAG) {
								return;
							}
							topic.publish("/sys/lbpmservice/freeflow/ok",this);
						}
					});
			return freeflowNodeOkBtn;
		});