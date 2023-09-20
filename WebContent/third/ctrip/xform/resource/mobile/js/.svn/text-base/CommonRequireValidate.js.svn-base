define([ "dojo/_base/declare", "dojo/dom-attr","sys/xform/mobile/controls/xformUtil"], 
		function(declare, domAttr, xUtil) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.CommonRequireValidate", null, {
		
		controldIdKey : "",
		
		constructor : function(controldIdKey){
			this.controldIdKey = controldIdKey;
		},
		
		validate : function(relationInfoDom){
			var rs = {'status':'01','msg':''};
			var keyObj = relationInfoDom.attr(this.controldIdKey);
			if(keyObj[0]){
				var controldId = keyObj[0];
				var wgt = xUtil.getXformWidgetBlur(null,controldId);
				var val = wgt.get("value");
				
				if(typeof(val) == 'undefined' || val == ''){
					rs.status = '00';
					rs.msg = "携程控件要求 \""+ wgt.get("subject") + "\"不能为空!";
				}	
			}else{
				console.warn("携程控件 找不到相关控件：" + this.controldIdKey + " 进行校验");
			}
			
			return rs;
		}
		
	});
	return claz;
});