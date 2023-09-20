/**
 * 
 */
define(["dojo/_base/declare",'dojo/_base/lang',"dijit/_WidgetBase",'mui/util',"dojo/request"],function(declare,lang,WidgetBase,util,request){
	return declare("dbcenter.echarts.application.common.mobile.CustomText",[WidgetBase],{
		
		url : '',
		
		_setUrlAttr : function(url){
			this._set('url',url);
			this.loadData();
		},
		
		loadData : function(){
			var url = util.formatUrl(url || this.url),
			self = this;
			request
				.post(url)
				.response
				.then(function(rtn) {
					lang.hitch(self,'renderText')(rtn.data);
			});
		},
		
		renderText : function(text){
			this.domNode.innerHTML = text;
		}
	});
});