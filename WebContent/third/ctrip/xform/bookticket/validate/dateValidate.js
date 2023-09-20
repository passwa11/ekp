
(function(){
	// 日期必填校验
	function DateValidate(startDateKey,endDateKey){
		
		this.startDateKey = startDateKey || '';
		this.endDateKey = endDateKey || '';
		this.validate = DateValidate_Validate;
	}
	
	function DateValidate_Validate(domNode){
		var rs = {'status':'01','msg':''};
		var startDateId = domNode.attr(this.startDateKey);
		var endDateId = domNode.attr(this.endDateKey);
		var startDateVal = $form(startDateId).val();
		var endDateVal = $form(endDateId).val();
		if(startDateVal && endDateVal){
			// 如果最晚时间不为空，则需要比较两个时间差
			if(startDateVal > endDateVal){
				rs.status = '00';
				rs.msg = "携程控件要求 \""+ Ctrip_Xform_GetControlSubject(startDateId) + "\"必须比\""+ Ctrip_Xform_GetControlSubject(endDateId) +"\"要早!";
			}
		}
		return rs;
	}
	
	window.DateValidate = DateValidate;
})()