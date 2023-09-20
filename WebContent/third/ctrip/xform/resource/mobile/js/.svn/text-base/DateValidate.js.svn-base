define([ "dojo/_base/declare", "dojo/dom-attr","sys/xform/mobile/controls/xformUtil"], 
		function(declare, domAttr, xUtil) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.DateValidate", null, {
		
		startDateKey : "",
		
		endDateKey : "",
		
		constructor : function(startDateKey,endDateKey){
			this.startDateKey = startDateKey;
			this.endDateKey = endDateKey;
		},
		
		validate : function(relationInfoDom){
			
			var rs = {'status':'01','msg':''};
			var startDateKeyObj = relationInfoDom.attr(this.startDateKey);
			var endDateKeyObj = relationInfoDom.attr(this.endDateKey);
			if(startDateKeyObj[0] && endDateKeyObj[0]){
				var startDateWgt = xUtil.getXformWidgetBlur(null,startDateKeyObj[0]);
				var endDateWgt = xUtil.getXformWidgetBlur(null,endDateKeyObj[0]);
				var startDateVal = startDateWgt.get("value");
				var endDateVal = endDateWgt.get("value");
				if(startDateVal && endDateVal){
					// 如果最晚时间不为空，则需要比较两个时间差
					if(startDateVal > endDateVal){
						rs.status = '00';
						rs.msg = "携程控件要求 \""+ startDateWgt.get("subject") + "\"必须比\""+ endDateWgt.get("subject") +"\"要早!";
					}
				}
			}else{
				console.warn("携程控件 找不到相关控件：" + this.controldIdKey + " 进行校验");
			}
			
			return rs;
		}
		
	});
	return claz;
});