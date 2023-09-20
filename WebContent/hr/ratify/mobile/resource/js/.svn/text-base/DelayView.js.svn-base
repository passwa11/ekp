define(["dojo/_base/declare", "dojo/topic", "dojox/mobile/View","dojo/parser"],
		function(declare, topic, View, parser){
	
	return declare("hr.ratify.DelayView",[View],{
		stopParser:true,
		
		startup:function(){
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		

		onAfterTransitionIn:function(){
			this._initView();
		},
		_initView:function(){
			//异步请求资源，进行界面初始化
			var self = this;
			if(!this.loading){
				this.loading=true;
				parser.parse(self.domNode).then(function(){
					topic.publish("/mui/list/resize");
				});
			}
		}
		
	});
});