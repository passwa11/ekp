define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-attr" ],
	function(declare,_WidgetBase,domAttr) {
	var claz = declare("mui.form.TextLabel", [_WidgetBase], {

		buildRendering : function() {
			this.inherited(arguments);
			var xformStyle = this.buildXFormStyle();
			if(xformStyle){
				domAttr.set(this.domNode,"style",xformStyle);
			}
		},
		//设置表单自定义样式
		buildXFormStyle: function () {
			var xformStyle = this.get("xformStyle");
			if (xformStyle) {
				var showMobileStyle;
				if (typeof KMSSData != 'undefined') {
					try{
						var data = new KMSSData();
						data.AddBeanData("sysFormDefaultSettingService");
						data = data.GetHashMapArray();
						if (data.length > 0) {
							showMobileStyle = data[0].showMobileStyle;
						}
					}catch (e){

					}
				}
				if (showMobileStyle == "true") {
					return xformStyle;
				}
			}
		}
	});

	return claz;
})
