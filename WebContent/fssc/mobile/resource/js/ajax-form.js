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
        "mui/device/adapter",
        "mui/device/device",
        "dojo/_base/window",
        "mui/util",
        "dojo/touch",
        "dojo/_base/event","mui/i18n/i18n!sys-mobile"
        ], function(lang, query, on, domStyle, domForm, domAttr ,domConstruct, request, Tip, adapter, device, win, util, touch, event,Msg) {
			var doBack = function() {
				var _referer = util.getUrlParameter(location.href,'_referer');
				if(_referer){
					_referer = decodeURIComponent(_referer);
					location.replace(_referer);
					return;
				}
				var modelFlag=$("input[name='modelFlag']").val();
				if(modelFlag){
					var backUrl;
					if(modelFlag=="fsscExpenseMain"){
						backUrl=util.formatUrl("/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=data");
					}else if(modelFlag=="fsscFeeMain"){
						backUrl=util.formatUrl("/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=data");
					}else if(modelFlag=="fsscLoanMain"){
						backUrl=util.formatUrl("/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=data");
					}else if(modelFlag=="fsscLoanRepayment"){
						backUrl=util.formatUrl("/fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=data");
					}
					window.location.href=backUrl;
				}else{
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
						var message = arguments[0][0].response.data.message;
						if(message && message.length > 0) {
							msg = message[0].msg;
						}
					}
					Tip.fail({text: msg, cover: true});
				};
				
				var _tmpfail = fail;
				fail = function(){
					_tmpfail(arguments);
					Com_Parameter.isSubmit = false;
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