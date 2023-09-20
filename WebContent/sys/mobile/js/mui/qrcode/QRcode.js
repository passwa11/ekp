define(['dojo/_base/declare','dijit/_WidgetBase','dojo/_base/lang','lib/qrcode/qrcode','dojo/dom-construct'],
		function(declare,WidgetBase,lang, QRCode,domConstruct) {

	var clz = declare('mui.qrcode.QRcode', [WidgetBase], {

		url : null,
		
		width : 128,
		
		height : 128,
		
		buildRendering: function(){
			this.inherited(arguments);
			this.QRCodeObj = new QRCode(this.domNode,{
				text : this.url,
				width : this.width || 128,
				height: this.height || 128,
				correctLevel : QRCode.CorrectLevel.H
			});
		}
	
	});
	
	var exports = {
		make : function(options){
			var dom = domConstruct.create('div');
			var instance =  new QRCode(dom,{
				text : options.text || options.url,
				width : options.width || 128,
				height: options.height || 128,
				correctLevel : QRCode.CorrectLevel.H
			});
			instance.domNode = dom;
			return instance;
		}
	};
	 
	return lang.mixin(clz, exports);
});