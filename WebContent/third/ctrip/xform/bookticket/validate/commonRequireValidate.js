
(function(){
	// 通用必填校验
	function CommonRequireValidate(controldIdKey){
		
		this.controldIdKey = controldIdKey || '';
		
		this.validate = CommonRequireValidate_Validate;
	}
	
	function CommonRequireValidate_Validate(domNode){
		var controldId = domNode.attr(this.controldIdKey);
		var val = $form(controldId).val();
		var rs = {'status':'01','msg':''};
		if(typeof(val) == 'undefined' || val == ''){
			rs.status = '00';
			rs.msg = "携程控件要求 \""+ Ctrip_Xform_GetControlSubject(controldId) + "\"不能为空!";
		}
		return rs;
	}
	
	window.CommonRequireValidate = CommonRequireValidate;
})()