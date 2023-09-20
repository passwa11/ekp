function ajaxPostData(methodName,id) {
 	 	var valid = $KMSSValidation(document.forms['sysAuthenVerifyForm']);
 		if (!valid.validate()) {
			return;
 		}
 		if(methodName!='save' && methodName!='update' && methodName!='savePhone' && methodName!='updatePhone'){
 			return;
 		}
		seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
			// 备份一下原始密码，当修改密码失败时还原备份的密码
			var __fdVerifyOldPwd = $("input[name='fdVerifyOldPwd']").val();
			var __fdVerifyPwd = $("input[name='fdVerifyPwd']").val();
			var __fdVerifyQpwd = $("input[name='fdVerifyQpwd']").val();
			// 密码加密处理
			$("input[name='fdVerifyOldPwd']").val(desEncrypt(__fdVerifyOldPwd));
			$("input[name='fdVerifyPwd']").val(desEncrypt(__fdVerifyPwd));
			$("input[name='fdVerifyQpwd']").val(desEncrypt(__fdVerifyQpwd));
			$.ajax({
				url : Com_Parameter.ContextPath +'sys/authentication/verify/sysAuthenVerify.do?method='+methodName+'&r=new Date()',
				type : 'POST',
				dataType : 'text',
				async : false,
				data : $("form[name='sysAuthenVerifyForm']").serialize(),
				success:function(dataStr) {
					var data=JSON.parse(dataStr);
					if(data.status == '200') {
						dialog.success(data.message);
						//self.hide(null);
						parent.close(id);
					} else {
						dialog.failure(data.message);
						// 修改密码失败，还原加密密码
						$("input[name='fdVerifyOldPwd']").val(__fdVerifyOldPwd);
						$("input[name='fdVerifyPwd']").val(__fdVerifyPwd);
						$("input[name='fdVerifyQpwd']").val(__fdVerifyQpwd);   
					}
				} 
			});
		});
 	}


function sendMobileValidationCode(type){
	var mobile=$("input[name='fdPhone']").val();
	$("#m_code").attr("disabled","disabled");//发送校验码不校验 验证码字段
	
	//提交表单校验
    for(var i=0; i<Com_Parameter.event["submit"].length; i++){
      if(!Com_Parameter.event["submit"][i](document.elecAuthenVerifyForm,"save")){
        if (Com_Submit.ajaxCancelSubmit) {
          Com_Submit.ajaxCancelSubmit(document.elecAuthenVerifyForm);
        }
        Com_Parameter.isSubmit = false;
        return false;
      }
    }
    $('#button_sendCode')[0].disabled=true;
	var datas={"type":"1","mobile":mobile};
	$("#button_sendCode").attr("disabled","disabled");
	$("#button_sendCode").addClass("btn-disabled");
	$("#m_code").removeAttr("disabled");
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : Com_Parameter.ContextPath +"sys/authentication/verify/sysAuthenVerify.do?method=sendMobileValidationCode&r="+Math.random(),
		data: datas,
		success : function(data) {
			if (data.result==true) {
				$("#msg_block").text(smssent);
			} else {
				$("#msg_block").html(data.errMsg);
			}
		},
		error : function(data) {
			alert(data);
			$("#msg_block").html(data.errMsg);
		}
	});
	times();
	
}
var t;
var s;
if(typeof(INTERVAL_TIME) == 'undefined'){
	s = 60;
}else{
	s = INTERVAL_TIME || 60;
}
function times(){
 	s--;
 	$("#button_sendCode").val(s+resendInSeconds);
	t = setTimeout('times()', 1000);
	if ( s <= 0 ){
		s = INTERVAL_TIME || 60;
		 clearTimeout(t);
		 $("#button_sendCode").val(getVerificationCode);
		 $("#button_sendCode").attr("disabled","");
		 $("#button_sendCode").removeAttr("disabled");
		 $("#button_sendCode").removeClass("btn-disabled");
	}
}

function returnFailMsg(flag){
	if(flag == "true"){
		seajs.use(['lui/dialog'],function(dialog){
		       dialog.iframe("/sys/organization/sys_org_retrieve_password/retrievePasswordFail.jsp",null,null,{width:240,height:120})
		   });
	}
}