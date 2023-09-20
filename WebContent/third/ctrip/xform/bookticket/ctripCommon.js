
(function(){
	
	function Ctrip_Xform_GetControlSubject(id){
		var subject = id;
		var xformFlag = $("xformflag[flagid='" + id + "']");
		if(xformFlag.length > 0){
			var formElements = xformFlag.find("[name*='"+ id +"']");
			if(formElements.length == 1){
				subject = formElements.attr("subject");
			}else if(formElements.length > 1){
				var type = xformFlag.attr("flagtype");
				if(type == 'xform_address'){
					subject = xformFlag.find("[name*='"+ id +".name']").attr("subject");
				}else if(type == 'map'){
					subject = xformFlag.find("[name*='"+ id +")']").attr("subject");
				}else if(type == 'xform_radio'){
					subject = $(formElements[0]).attr("subject");
				}else{
					subject = $(formElements[0]).attr("subject");
				}
			}
		}
		return subject;
	}
	
	//提交的时候校验必填数据
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		if(Xform_ObjectInfo.Xform_Controls.ctripControl){
			for(var i = 0;i < Xform_ObjectInfo.Xform_Controls.ctripControl.length;i++){
				if(!Xform_ObjectInfo.Xform_Controls.ctripControl[i].validate()){
					return false;
				}
			}
		}
		return true;
	};
	
	window.Ctrip_Xform_GetControlSubject = Ctrip_Xform_GetControlSubject;
})()