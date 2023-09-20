define([ "dojo/_base/declare", "dojo/topic", "dijit/_WidgetBase",
		"mui/util","dojo/request","dojo/string" ], function(declare, topic, WidgetBase,util,request,string) {

	return declare("AttachmentViewPageTime", [ WidgetBase ], {
		
		fdId:"",
		
		_templateId:"",
		
		_contentId:"",
		
		computingTimeUrl:"/sys/attachment/computingTime.do?method=computingTime&fdId=${fdId}&_templateId=${_templateId}&_contentId=${_contentId}",
		
		startup : function() {

			this.inherited(arguments);
			
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			
			window.computingTimeTimer="";
			computingTimeTimer=setInterval(function(){ self.computingTime(); },1000*60); //开启定时器
			
			var self=this;
			document.addEventListener('visibilitychange',function(){
				//页面不可见时停止定时
				if(document.hidden){
					clearInterval(computingTimeTimer);    //清除定时器
				} else {
					computingTimeTimer=setInterval(function(){ self.computingTime();},1000*60);    //开启定时器
				}

			});
			
			this.computingTime();

		},
		
		computingTime : function() {
		   var promise = request.post(util.formatUrl(string.substitute(this.computingTimeUrl, {
				fdId : this.fdId || '',
				_templateId : this._templateId || '',
				_contentId : this._contentId || '',
			})), {handleAs : 'json'});
		   
		   promise.response.then(function(response) {
			   if(response.status == 200){
				   var data =  response.data;
				   if(data.isFinished){
					   //已通过停止定时器
					   clearInterval(computingTimeTimer);
				   }
			   }
			});
		}
	});

});