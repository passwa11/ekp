define(["dijit/registry","mui/dialog/Tip","mui/i18n/i18n!sys-mobile","mui/device/adapter","mui/device/device","mui/util",
		"dojo/_base/lang","dojo/request","dojo/dom-form"],
	function(registry,Tip,Msg,adapter,device,util,lang,request,domForm){
	//"抢办"操作类
	var handlerRobTodo={};
	// 处理人操作：收回加签
	var OperationClick = function(operationName) {
		lbpm.globals.setDefaultUsageContent('handler_robTodo');
	};
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_robTodo');
	};
	// “抢办”操作的检查
	var OperationCheck= function(){
		return true;
	};
	
	// 设置"抢办"操作的参数
	var setOperationParam = function() {
		if(!lbpm.hasAddComfirm){
			lbpm.hasAddComfirm = true;
			Com_Parameter.event["confirm"].unshift(_doSubmit);
			Com_Parameter.event["submit_failure_callback"].push(_callback);
		}
	};

	window._alert = window.alert;
	var tipAlert = function(txt) {
		Tip.fail({text:txt});
	};
	var processing = Tip.processing();
	var working = false;
	var submited = false;
	var tipProccessing = function() {
		if (working)
			return false;
		working = true;
		window.alert = tipAlert;
		processing.show();
		return true;
	};
	var hideProcessing = function() {
		working = false;
		window.alert = window._alert;
		processing.hide(false);
	};
	var doBack = function() {
		var _referer = util.getUrlParameter(location.href,'_referer');
		if(Com_Submit.refresh){
			Com_Submit.refresh = false;
			location.reload();
			return;
		}
		if(_referer){
			_referer = decodeURIComponent(_referer);
			location.replace(_referer);
			return;
		}
		var rtn = adapter.goBack();
		if(rtn == null){
			history.back();
		}
		var deviceType = device.getClientType();
		//解决kk下调用两次退出，导致kk闪退
		if(deviceType != 7 && deviceType != 8 && deviceType != 9 && deviceType != 10){
			//由于目前没有办法知道history.back是否生效,执行closeWindow还能生效，说明后退未能生效
			setTimeout(function(){
				adapter.closeWindow();
			},500);
		}
	};
	var success = function() {
		submited = true;
		Tip.success({
			text: "抢办成功！",//Msg["mui.return.success"],
			callback: function(){
				location.reload();
			},
			cover: true
		});
	};
	var fail = function() {
		var msg = Msg["mui.return.failure"];
		if(arguments && arguments.length > 0) {
			if(arguments[0] && arguments[0].response){
				var message = arguments[0].response.data.message;
				if(message && message.length > 0) {
					msg = message[0].msg;
				}
			}else {
				msg = arguments[0].message[0].msg;
			}
		}
		Tip.fail({text: msg,callback: doBack, cover: true});
	};
	var GetRequest = function(url) {
		var params = [],h;
		var hash = url.slice(url.indexOf("?") + 1).split('&');
		for (var i = 0; i < hash.length; i++) {
			h = hash[i].split("=");
			params[h[0]] = h[1];
		}
		return params;
	}
	var doSubmit = function(){
		var form = document.forms[0];
		if(submited){
			Tip.fail({text:Msg["mui.return.submitted"],callback:function(){
				location.reload();
			}});
			hideProcessing();
			return;
		}
		var url = form.action;
		methodd = GetRequest(url).method;
		var formMethod = form.method|| 'post';
		//临时解决KK离线包问题，错误兼容姿势，勿学习 --开始
		var re = new RegExp();
		re.compile("[^:]*:\/\/app.[^k]*.kk");
		if(re.test(url)){
			url = util.formatUrl(url.replace(/[^:]*:\/\/app.[^k]*.kk/, "",''),true);
		}
		//临时解决KK离线包问题，错误兼容姿势，勿学习 --结束
		if(window.Com_BeforeSubmitDeferred){
			var deferred = window.Com_BeforeSubmitDeferred();
			deferred.then(lang.hitch(this,function(){
				var promise = request.post(url, {
					data: domForm.toObject(form),
					headers: {'Accept': 'application/json'},
					handleAs: 'json'
				}).then(function(result) {
					hideProcessing();
					if (result['status'] === false) {
						fail(result);
						return;
					}
					success(result);
				}, function(result) {
					hideProcessing();
					fail(result);
				});
			}),lang.hitch(this,function(result){
				hideProcessing();
				fail(result);
			}));
		}else{
			// 移动端根据form的方法来进行发相应请求
			if((formMethod.toLowerCase()) === 'get'){
				var promise = request.get(url, {
					data: domForm.toObject(form),
					headers: {'Accept': 'application/json'},
					handleAs: 'json'
				}).then(function(result) {
					hideProcessing();
					if (result['status'] === false) {
						fail(result);
						return;
					}
					success(result);
				}, function(result) {
					hideProcessing();
					fail(result);
				});
			}else{
				var promise = request.post(url, {
					data: domForm.toObject(form),
					headers: {'Accept': 'application/json'},
					handleAs: 'json'
				}).then(function(result) {
					hideProcessing();
					if (result['status'] === false) {
						fail(result);
						return;
					}
					success(result);
				}, function(result) {
					hideProcessing();
					fail(result);
				});
			}
		}
	};

	var onsubmit = function(evt) {
		evt.stopPropagation();
		evt.preventDefault();
		tipProccessing();
		doSubmit(form);
	};

	var _doSubmit = function(){
		var isSuccess = false;
		var url = "/sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=updateIsCanRobTodo";
		url = util.formatUrl(url);
		request.post(url, {
			data: {"fdNodeId":lbpm.nowNodeId,"fdProcessId":lbpm.modelId},
			headers: {'Accept': 'application/json'},
			handleAs: 'json',
			sync: true
		}).then(function(result) {
			isSuccess = result['status'];
			if (!isSuccess) {
				Tip.fail({text: result['msg'],callback: doBack, cover: true});
			}
		}, function(result) {
			isSuccess = false;
			Tip.fail({text: result['msg'],callback: doBack, cover: true});
		});
		if(isSuccess){
			if (window.Com_Submit) {
				Com_Submit.ajaxBeforeSubmit = tipProccessing;
				Com_Submit.ajaxCancelSubmit = hideProcessing;
				Com_Submit.ajaxSubmit = doSubmit;
			} else {
				on(document.forms[0], "submit", onsubmit);
			}
		}
		return isSuccess;
	};

	var _callback = function(){
		var isSuccess = false;
		var url = "/sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=removeCanRobTodo";
		url = util.formatUrl(url);
		request.post(url, {
			data: {"fdNodeId":lbpm.nowNodeId,"fdProcessId":lbpm.modelId},
			headers: {'Accept': 'application/json'},
			handleAs: 'json',
			sync: false
		}).then(function(result) {

		}, function(result) {

		});
	};

	handlerRobTodo['handler_robTodo'] = lbpm.operations['handler_robTodo'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};

	handlerRobTodo.init = function() {
	};
	
	return handlerRobTodo;
});
