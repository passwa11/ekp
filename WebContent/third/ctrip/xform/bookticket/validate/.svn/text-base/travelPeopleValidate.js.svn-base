
(function(){
	// 出差人员校验
	function TravelPeopleValidate(passengerKey,retinueKey){
		this.passengerKey = passengerKey || ''; // 出行人
		this.retinueKey = retinueKey || ''; // 同行人
		
		this.validate = TravelPeopleValidate_Validate;
		this._replaceSymbol = _TravelPeopleValidate_ReplaceSymbol;
	}
	
	function TravelPeopleValidate_Validate(domNode){
		var rs = {'status':'01','msg':''};
		var passengerId = domNode.attr(this.passengerKey);
		var retinueId = domNode.attr(this.retinueKey);
		var passengerVal = $form(passengerId + ".id").val();
		var retinueVal = $form(retinueId).val();
		if(typeof(passengerVal) == 'undefined' || passengerVal == ''){
			if(retinueId && retinueId != ''){
				if(typeof(retinueVal) == 'undefined' || retinueVal == ''){
					rs.status = '00';
					rs.msg = "携程控件要求 \""+ Ctrip_Xform_GetControlSubject(passengerId) + "\"和\""+ Ctrip_Xform_GetControlSubject(retinueId) +"\"不能同时为空";
				}
			}else{
				rs.status = '00';
				rs.msg = "携程控件要求 \"" + Ctrip_Xform_GetControlSubject(passengerId) + "\"不能为空";
			}
		}
		return rs;
	}
	
	//中文标点符号转换
	function _TravelPeopleValidate_ReplaceSymbol(str){
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
	
	window.TravelPeopleValidate = TravelPeopleValidate;
})()