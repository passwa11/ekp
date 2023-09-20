define([
        "dojo/_base/lang",
        "dojo/query",
        "dojo/on",
        "dojo/dom-style",
        "dojo/dom-form",
        "dojo/dom-attr",
        "dojo/dom-construct",
        "dojo/request",
        "mui/dialog/Tip",
	    "mui/dialog/Dialog",
        "mui/device/adapter",
        "mui/device/device",
        "dojo/_base/window",
        "mui/util",
        "dojo/touch",
        "dojo/_base/event","mui/i18n/i18n!sys-mobile"
        ], function(lang, query, on, domStyle, domForm, domAttr ,domConstruct, request, Tip,Dialog, adapter, device, win, util, touch, event,Msg) {
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


		    window._alert = window.alert;
		    var tipAlert = function(txt) {
		    	require(["mui/dialog/Tip"], function(Tip) {
		    		Tip.fail({text:txt});
		    	});
		    };

			var processing = Tip.processing();
			var working = false;

			function tipProccessing() {
				if (working)
					return false;
				working = true;
				window.alert = tipAlert;
				processing.show();
				return true;
			}
			function hideProcessing() {
            	working = false;
            	window.alert = window._alert;
            	processing.hide(false);
			}
	function GetRequest(url) {
		var params = [],
			h;
		var hash = url.slice(url.indexOf("?") + 1).split('&');
		console.log(hash);
		for (var i = 0; i < hash.length; i++) {
			h = hash[i].split("="); //
			params[h[0]] = h[1];
			console.log(h);
		}
		return params;
	}
	function lockerVersion_fail (result){
		var elementStr = "当前文档已被【"+result.modifyPerson+"】"+result.modifyTime+"分钟之前提交，继续提交将覆盖之前的更新内容。</br>是否继续提交"
		var tempMethod = "";
		if(methodd){
			tempMethod = methodd;
		}else {
			console.info("参数错误")
			return;
		}
		var dialog=	Dialog.element({
			'title' : "文档已被他人更新",
			'showClass' : 'lockTip',
			'element' : "<div>"+elementStr+"<div>",
			'scrollable' : false,
			'parseable': false,
			'buttons' : [
				{
					title : "取消并刷新", // 取消
					fn :  function(dialog) {
						location.reload();
					}
				},
				{
					title : "继续提交",
					fn : function(dialog) {
						var fdversion = document.getElementsByName("componentLockerVersionForm.fdVersion")[0];
						var valueInt = parseInt(fdversion.value);
						var newfdversion = valueInt + 2;
						fdversion.value = newfdversion;
						Com_SubmitForm(document.forms[0],methodd);
						return;

					}
				}  ]
		});


	}
			var ajaxForm = function(formName, options) {
				var submited = false;
				var form = lang.isString(formName) ? query(formName)[0] : formName;
				var success = options.success ? options.success : function() {
					submited = true;
					Tip.success({
						text: Msg["mui.return.success"],
						callback: options.back ? doBack : null,
						cover: true
					});
				};
				var fail = options.error ? options.error : function() {
					var msg = Msg["mui.return.failure"];
					if(arguments && arguments.length > 0 && arguments[0].length > 0) {
						if(arguments[0] && arguments[0][0] && arguments[0][0].response){
							var message = arguments[0][0].response.data.message;
							if(message && message.length > 0) {
								msg = message[0].msg;
							}
						}else {
							msg = arguments[0][0].message[0].msg;
						}
					}
					Tip.fail({text: msg, cover: true});
				};

				var _tmpfail = fail;
				fail = function(){
					if(arguments[0].exceptionType && arguments[0].exceptionType == "ComponentLockerVersionException"){
						lockerVersion_fail(arguments[0]);
						Com_Parameter.isSubmit = false;
					}else {
						_tmpfail(arguments);
						Com_Parameter.isSubmit = false;
					}
				}

				var doSubmit = function(form) {
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

				if (window.Com_Submit) {
					Com_Submit.ajaxBeforeSubmit = tipProccessing;
					Com_Submit.ajaxCancelSubmit = hideProcessing;
					Com_Submit.ajaxSubmit = doSubmit;
				} else {
					on(form, "submit", onsubmit);
				}
			};
			return {
				ajaxForm: ajaxForm,

				load: function(id, require, load) {
					require(["dojo/domReady!"], function() {
						ajaxForm("[name='" + id + "']", {
							back: true
						});
						if (load)
							load();
					});
				}
			};
		});