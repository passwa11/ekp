define([ "dojo/_base/declare", "dojo/dom-attr","sys/xform/mobile/controls/xformUtil"], 
		function(declare, domAttr, xUtil) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.TravelPeopleValidate", null, {
		
		passengerKey : "", // 出差人
		
		retinueKey : "", // 同性人
		
		constructor : function(passengerKey,retinueKey){
			this.passengerKey = passengerKey;
			this.retinueKey = retinueKey;
		},
		
		validate : function(relationInfoDom){
			
			var rs = {'status':'01','msg':''};
			var passengerKeyObj = relationInfoDom.attr(this.passengerKey);
			if(passengerKeyObj[0]){
				var passengerWgt = xUtil.getXformWidgetBlur(null,passengerKeyObj[0]);
				var passengerVal = passengerWgt.get("value");
				if(!passengerVal){
					// 出差人为空
					var retinueKeyObj = relationInfoDom.attr(this.retinueKey);
					if(retinueKeyObj[0]){
						var retinueWgt = xUtil.getXformWidgetBlur(null,retinueKeyObj[0]);
						var retinueVal = retinueWgt.get("value");
						if(!retinueVal){
							// 同行人员为空
							rs.status = '00';
							rs.msg = "携程控件要求 \""+ passengerWgt.get("subject") + "\"和\""+ retinueWgt.get("subject") +"\"不能同时为空";
						}
					}else{
						// 同行人员没有配置
						rs.status = '00';
						rs.msg = "携程控件要求 \""+ passengerWgt.get("subject") + "\"不能为空";
					}
				}
			}else{
				console.warn("携程控件 找不到相关控件：" + this.passengerKey + " 进行校验");
			}
			
			return rs;
		},
		
		_replaceSymbol : function(str){
			str = str.replace(/，/g, ",");
			str = str.replace(/。/g, ".");
			str = str.replace(/：/g, ":");
			str = str.replace(/；/g, ";");
			str = str.replace(/＋/g, "+");
			str = str.replace(/－/g, "-");
			str = str.replace(/×/g, "*");
			str = str.replace(/÷/g, "/");
			str = str.replace(/（/g, "(");
			str = str.replace(/）/g, ")");
			str = str.replace(/《/g, "<");
			str = str.replace(/》/g, ">");
			return str;
		}
		
	});
	return claz;
});