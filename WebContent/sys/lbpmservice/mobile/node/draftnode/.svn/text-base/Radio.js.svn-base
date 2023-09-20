define( [ "dojo/_base/declare", "mui/form/Radio", "dojo/topic" ], function(declare, _Radio, topic) {
	var Radio = declare("sys.lbpmservice.mobile.node.draftnode.Radio",
			[ _Radio ], {
				
				_onClick : function() {
					this.inherited(arguments);
					lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE);
					lbpm.globals.setNextBranchNodes(this.domNode);
					topic.publish("/sys/lbpmservice/mobile/draftnode/radio/change", this);
				},
				
				_setCheckedAttr : function(checked) {
					this.inherited(arguments);
					if(checked){
						lbpm.events.fireListener(lbpm.constant.EVENT_SELECTEDFUTURENODE);
					}
				}
			});
	return Radio;
});
