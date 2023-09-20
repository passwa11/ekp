define([
        "dojo/_base/declare", 
        'dojo/dom-construct',
        'mui/util',
        "dojo/request",
        "dijit/_WidgetBase"
        ],function(declare, domConstruct,
        		util, request,  _WidgetBase){
	
	return declare("sys.relation.RelationText", [_WidgetBase], {
		
		url : "",
		
		buildRendering : function(){
			this.inherited(arguments);
			var content = domConstruct.create("div" , {
				className : "muiRelationText"
			}, this.domNode);
			
			var rurl = util.formatUrl(this.url),
			 	self = this;
			request.get(rurl, {
	             handleAs: 'json'
			}).then(function(result) {
				if(result && result.length > 0) {
					for(var i = 0; i < result.length; i ++) {
						var tmp = domConstruct.create("div", null, content);
						tmp.innerHTML = util.formatText(result[i]);
					}
				}
			});
		}
		
	});
	
});