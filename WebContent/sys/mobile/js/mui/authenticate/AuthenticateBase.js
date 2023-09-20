define(['dojo/_base/declare','dijit/_WidgetBase','dijit/_Container','dojo/dom-style','dojo/dom-construct','dojo/request','mui/util'],
		function(declare,WidgetBase,Container,domStyle,domConstruct,request,util) {

	return declare('mui.authenticate.AuthenticateBase', [ WidgetBase, Container ], {
		
		url : '',
		
		startup : function(){
			this.inherited(arguments);
			var self = this;
			this.formatUrl();
			request(util.formatUrl(this.url),{
				handleAs : 'json'
			}).then(function(result){
				if(result.status && result.status == 'true'){
					var slice = Array.prototype.slice,
						parentWidget = self.getParent(),
						childNodes = self.domNode.childNodes;
					childNodes = slice.call(childNodes);
					while(dom = childNodes.shift()){
						domConstruct.place(dom,self.domNode,'after');
					}
					domConstruct.destroy(self.domNode);
					if(parentWidget && parentWidget.resize){
						//parentWidget.resize();
					}
				}else{
					self.destroyRecursive();
				}
			});
		},
		
		formatUrl : function(){
			
		}

	});
});