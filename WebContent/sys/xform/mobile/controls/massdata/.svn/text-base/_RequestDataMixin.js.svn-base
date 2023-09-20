/**
 * 
 */
define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/query" , "mui/util", "dojo/request","dojo/dom-construct", "dojo/dom-style"], 
		function(declare, WidgetBase , query , util, request, domConstruct, domStyle) {
	var claz = declare("sys.xform.mobile.controls.massdata._RequestDataMixin", [WidgetBase], {
		
		defaultUrl : "/sys/xform/massdata/sysFormMassData.do?method=getMassData&mainFormId=!{mainFormId}&mainModelName=!{mainModelName}&controlId=!{controlId}",

		startup : function() {
			this.inherited(arguments);
			var self = this;
			var url = util.formatUrl(util.urlResolver(this.defaultUrl,this));
			this.buildLoading();
			request.post(url,{handleAs : 'json'}).then(function(json){
				if(self.onDataLoad){
					self.onDataLoad(json);
					domStyle.set(self.tmpLoading,"display","none");
				}
			});
		},
	
		buildLoading:function(){
			if(this.tmpLoading == null){
				this.tmpLoading = domConstruct.create("div",{className:'muiCateLoading',style:{
					display:"inline-block"
				},innerHTML:'<i class="mui mui-loading mui-spin"></i>'},this.domNode);
			}else{
				domStyle.set(self.tmpLoading,"display","inline-block");
			}
		}
	});
	return claz;
});