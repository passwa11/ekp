define([
        "dojo/_base/declare", 
        'dojo/dom-construct',
        'mui/util',
        "dojo/topic",
        "dojo/request",
        "dojox/mobile/TransitionEvent",
        "mui/history/listener",
        "dojo/parser",
        "dojo/_base/array",
    	"mui/device/adapter"
        ],function(declare, domConstruct,
        		util,
        		topic, request, TransitionEvent, listener, parser, array, adapter){
	
	return declare("sys.relation.Box", null, {
		
		url : "/sys/relation/mobile/tmpl/relation_tmpl.jsp?modelName=!{modelName}&modelId=!{modelId}",
		
		modelName : "",
		
		modelId: "",
		
		backTo : "",
		
		buildRendering : function(){
			this.inherited(arguments);
		},
		
		startup : function() {
			this.inherited(arguments);
			this.url = util.urlResolver(this.url, {
				modelName : this.modelName,
				modelId : this.modelId
			});
			this.url = this.url + "&fdKey=!{fdKey}";
			this.subscribe("/sys/relation/box/show", "showBox");
		},
		
		showBox : function(evt) {
			if(!evt) {
				return;
			}
			
			var jspUrl = util.formatUrl(util.urlResolver(this.url, {
				fdKey : evt.fdKey
			}));
			
			var self = this;
			
			request.get(jspUrl, {
	             handleAs: 'text'
			}).then(function(result) {
				if(result)
					result = result.replace(/(^\s*)|(\s*$)/g, "");
				if(!result) {
					return;
				}
				
				if(result.startsWith("http") || result.startsWith("/")) {
					var fdUrl = result;
					if(/^\//.test(fdUrl)) {
						fdUrl = util.formatUrl(fdUrl);
					}
					adapter.open(fdUrl, "_self");
					return;
				}
				
				array.forEach(self.getChildren(), function(child) {
					child.destroyRecursive();
				});
				
				self.containerNode.innerHTML = result;
				parser.parse(self.containerNode ,'last')
					.then(function(widgetList) {
					var opts = {
						transition : 'slide',
						moveTo : self.id,
						transitionDir : 1
					};
					new TransitionEvent(document.body, opts).dispatch();
					
					listener.add({ callback : function() {
									new TransitionEvent(document.body, 
										{ moveTo: self.backTo, 
										 transition: "slide", 
										 transitionDir: -1}).dispatch();
									}
								 });
				});
				
			});
			
		}
	});
	
});