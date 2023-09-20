
function dingConfig(config){
	DingTalkPC.config({
		agentId:config.appId,
	    corpId: config.corpId,
	    timeStamp: config.timeStamp,
	    nonceStr: config.nonceStr,
	    signature: config.signature,
	    jsApiList: [
	        'device.notification.confirm',
	        'device.notification.alert',
	        'device.notification.prompt',
			'runtime.permission.requestAuthCode',
			'biz.util.openLink'
		]
	});

	DingTalkPC.ready(function(res) {
	    logger.i('dd.ready rocks!');
	    DingTalkPC.runtime.permission.requestAuthCode({
	        corpId: config.corpId,
	        onSuccess: function (info) {
	            logger.i('authcode: ' + info.code);
				_scode=info.code;
	            $.ajax({
	                url: _ctx+'/third/ding/jsapi.do?method=userinfo&from=dingpc&code=' + info.code,
	                type: 'GET',
	                success: function (data, status, xhr) {
	                    var info = $.parseJSON(data);
						if (info.errcode === 0) {
	                        logger.i('user id: ' + info.userid);
	 						dingOpen();
							//history.back();
	                   }else {
	                	   document.getElementById("_load").innerHTML = "user id parse fail:"+JSON.stringify(info);  
	                        logger.e('auth error: ' + data);
							//history.back();
	                    }
	                },
	                error: function (xhr, errorType, error) {
	                	document.getElementById("_load").innerHTML = "user id get fail:"+JSON.stringify(error);  
	                    logger.e(errorType + ', ' + error);
	                }
	            });
	        },
	        onFail: function (err) {
	            logger.e('fail: ' + $.parseJSON(err));
	            document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);  
	        }

	    });
	});

	DingTalkPC.error(function(err) {
		document.getElementById("_load").innerHTML = "DingTalkPC config fail:"+JSON.stringify(err);  
		//logger.e('dd error: ' + $.parseJSON(err));
	});
}