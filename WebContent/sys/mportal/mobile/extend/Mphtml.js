define([ "dojo/_base/declare", "dojo/dom-style", "dijit/_WidgetBase",
		 "dojo/dom-construct",
		"mui/util", 
		 "dojo/_base/lang",
		 "dojo/request"],
		function(declare, domStyle, WidgetBase,
		domConstruct, util, lang, request) {
	var menu = declare("sys.mportal.Mphtml", [ WidgetBase ], {

		htmlId : "",
		
		url : "",
		
		buildRendering : function() {
			this.inherited(arguments);

			if(this.htmlId) {
				var url = util.urlResolver("/sys/mportal/sys_mportal_html/sysMportalHtml.do?method=html&htmlId=!{htmlId}", {htmlId : this.htmlId});
				this.url = util.formatUrl(url);
			}
			
			// HTML自定义页面内容载体
			this.containerNode = domConstruct.create('div', {
				className : 'muiPortalHtmlContainer',
			},this.domNode);
			domConstruct.place(this.containerNode,this.domNode,'last');
			
		},
		
		startup : function() {
			this.inherited(arguments);
			if(this.url){
				this.source();
			}
		},
		
		source:function() {
			var self = this;
			request.get(this.url).then(function(data) {
				if(data) {
					self.containerNode.innerHTML = data;
				}
			});
		}
	});
	return menu;
});