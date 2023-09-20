Com_IncludeFile("tag.js", Com_Parameter.ContextPath+"sys/zone/import/js/", "js", true);
( function() {
	var dateTime = "";
	var dataValue = {
			"fdName" : userName,
			"fdLoginName" : loginName,
			"fdDeptName" : dept,
			"fdTel" : telphone,
			"fdPostNames" : title,
			"fdEmail" : email,
			"fdCustomerServiceId" : licenseId
		};
	function sendRequest() {
		var url ="http://mservice.landray.com.cn/app/lrservice/personcenter/getTickCode";
		var waitImgUrl= Com_Parameter.ContextPath+'sys/portal/template/default/loading.gif';
		var waitImgHtml='<img style="padding:12px 0px;line-height: 22px;" src="'+waitImgUrl+'"/>';
		var defaultImgUrl = Com_Parameter.ContextPath+'sys/portal/template/default/qrcode.jpg';
		var htmlInfor = "";
		LUI.$
				.ajax( {
					async : true,
					type : "POST",
					url : url,
					data : {
						"param" : JSON.stringify(dataValue)
					},
					dataType : "json",
					beforeSend:function(){
						LUI.$("#_qywechatimg").children().remove();
						LUI.$("#_qywechatimg").append(waitImgHtml); 
				    },
					success : function(data) {
						if (data != null) {
							var imgPath = data.success;
							if (imgPath != null && imgPath.length > 0) {
								htmlInfor = '<img src="' + imgPath + '" width="106px" height="106px" style="margin-left: 22%  background-color:transparent border: 1px solid #E2E2E2;"/>';
								LUI.$("#_qywechatimg").children().remove();
								LUI.$("#_qywechatimg").html(htmlInfor);
							} else {
								htmlInfor = '<img src="' + defaultImgUrl + '" width="106px" height="106px" style="margin-left: 22%  background-color:transparent border: 1px solid #E2E2E2;"/>';
								LUI.$("#_qywechatimg").children().remove();
								LUI.$("#_qywechatimg").append(htmlInfor);
							}
						}
					},
					error : function() {
						htmlInfor = '<img src="' + defaultImgUrl + '" width="106px" height="106px" style="margin-left: 22%  background-color:transparent border: 1px solid #E2E2E2;"/>';
						LUI.$("#_qywechatimg").children().remove();
						LUI.$("#_qywechatimg").html(htmlInfor);
					}
				});
	}

	function checkField() {
		if (email == null || email == '' || email.length < 1) {
			htmlInfor = '<font color="red">'+messagetips+'</font>';
			LUI.$("#_qywechatimg").children().remove();
			LUI.$("#_qywechatimg").html(htmlInfor);
			return false;
		}
		return true;
	}

	function checkTime() {
		var date = new Date(); 
		var currentTime = date.getTime();// 获取当前时间(从1970.1.1开始的毫秒数)
		if (dateTime != null && dateTime != '') {
			//检查第二次跟第一次请求是否相差1800s(预留100s)
			var result = currentTime - dateTime;
			if (result > 1700 * 1000) {
				return true;
			} else {
				return false;
			}
		} else {
			dateTime = currentTime;// 得到毫秒数
			return true;
		}
	}

	LUI.ready( function() {
		LUI.$('#_qywechat').bind("click", function() {
			var fieldFlag = checkField();
			if (fieldFlag) {
				var flag = checkTime();
				// 当 时间差超过1800s则进行新的请求
				if (flag) {
					sendRequest();
				}
			}
		});

		LUI.$('#_qywechat').hover(function(){
			     LUI.$('#_qywechatimg').show('fast');
		     },
			function(){
				LUI.$('#_qywechatimg').hide('fast');
			}).click(function(){
				return false;
		});
	});
})();