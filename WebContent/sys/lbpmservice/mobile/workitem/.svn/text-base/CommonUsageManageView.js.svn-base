define(["dojo/_base/declare", "dojo/topic", "mui/list/StoreScrollableView",
		"dojo/dom-style","dojo/query","dojo/dom-geometry"],
	function(declare,topic,StoreScrollableView,domStyle,query,domGeometry){

	return declare("sys.lbpmservice.mobile.workitem.CommonUsageManageView",[StoreScrollableView],{
		startup:function(){
			domStyle.set(this.domNode,{
				"overflow-x": "hidden"
			});
			this.inherited(arguments);
			this.checkAndFixFixedFooterHeight(this, 1);
		},

		/**
		 * 检查并修复底部固定高度
		 * @param self
		 * @param count
		 */
		checkAndFixFixedFooterHeight:function(self, count){
			if(!count){
				count = 1;
			}
			if(count > 10){
				return;
			}
			if(self.fixedFooter && !self.fixedFooterHeight){
				var fixedFooterPosition = domGeometry.position(this.fixedFooter);
				if(fixedFooterPosition.h > 0){
					self.fixedFooterHeight = fixedFooterPosition.h;
				}else{
					setTimeout(function (){
						self.checkAndFixFixedFooterHeight(self,++count);
					},100);
				}
			}
		},

		onBeforeTransitionIn:function(){
			this.inherited(arguments);
		},

		onAfterTransitionOut:function(){
			this.inherited(arguments);
		}
	})
})