define(function(require, exports, module){
	
	var $ = require('lui/jquery'), env = require('lui/util/env');
	var _validation =  $KMSSValidation(document.forms['sysZonePersonMultiResumeForm']);
	var LoginNameValidators = {
			'isExist' : {
				error : "该登录名不存在",
				test : function (value) {
						var result = true;
						$.ajax(env.fn.formatUrl('/sys/zone/sys_zone_person_multi_resume/sysZonePersonMultiResume.do?method=isLoginNameExist'),{
							type : "GET",
							data : {
								fdLoginName : value
							},
							async : false,
							dataType : "json",
							success : function(data) {
								if(data.isExist) {
									result = true;
								} else {
									result = false;
								}
							},
							error : function(){
								result = false;
							}
						});
						return result;
				 }
			}
	};
	_validation.addValidators(LoginNameValidators);
});
