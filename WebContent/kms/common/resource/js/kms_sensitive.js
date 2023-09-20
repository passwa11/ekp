define(function(require, exports, module) {
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	var lang = require('lang!kms-common');
	
	/**
	 * __$kmssValidation 需要校验form的校验对象
	 * rtfFields: 字段名称数组
	 */
	var kmsSense = function (__$kmssValidation, rtfNameArr){
		this.__$kmssValidation = __$kmssValidation;
		this.rtfNameArr = rtfNameArr;
		this.url = env.fn.formatUrl("/kms/common/kms_common_sensitive/kmsCommonSensitive.do?method=validateSenSitive");
		this.isRTFBind = false;
		this.__init__();
	};
	
	this.__init__ = function (){
		var self = this;
		self.__$kmssValidation.addValidator('__sensitiveValidate',
				'{name} ' + lang['kmsCommonSensitive.hasSen'], function(value, element) {
				if(!value){
					return true;
				}
				return self.__validateSen__(value);
			}
		);
		
		CKEDITOR.on('instanceReady', function(){
			var rtfNameArr = self.rtfNameArr;
			for(var i = 0; i < rtfNameArr.length; i++){
				var editor = CKEDITOR.instances[rtfNameArr[i]];
				self.__buildValidateRender(editor.container.$);
				editor.on('blur',function(){
					var spanId = this.container.$.id;
					if(!self.__validateSen__(this.document.getBody().getText())){
						$("#advice-rtf-" + spanId).show();
					}else{
						$("#advice-rtf-" + spanId).hide();
					}
				});
			}
			self.isRTFBind = true;
		});
		
		Com_Parameter.event["submit"].push(function(){
			for(var i = 0; i < rtfNameArr.length; i++){
				var editor = CKEDITOR.instances[rtfNameArr[i]];
				var value = editor.document.getBody().getText();
				if(!value){
					continue;
				}
				if(!self.__validateSen__(value)){
					$("#advice-rtf-" + rtfNameArr[i]).show();
					return false;
				}
			}
			return true;
		});
	};
	
	this.__buildValidateRender = function(containerDom){
		if(this.isRTFBind){
			return;
		}
		var aHtml = '<div class="validation-advice" id="advice-rtf-' + containerDom.id + '" _reminder="true" style="display: none;">';
		aHtml += '<table class="validation-table"><tr><td>';
		aHtml += '<div class="lui_icon_s lui_icon_s_icon_validator"></div></td>';
		aHtml += '<td class="validation-advice-msg">'+ lang['kmsCommonSensitive.hasSen'] +'</td></tr></table></div>';
		$(containerDom).parent().append(aHtml);
	};
	
	this.__validateSen__ = function(value){
		var rntFlag = true;
		var url = this.url;
		$.ajax({type: "POST", async: false,
			url: url,
			data: {"content": value},
			success: function(data){
				if(data && data.hasSenWord == true){
					rntFlag = false;
				}
			},dataType: 'json'
		});
		return rntFlag;
	};
	
	function bindSensitive(__$kmssValidation, rtfNameArr){
		kmsSense(__$kmssValidation, rtfNameArr);
	}
	
	exports.bindSensitive = bindSensitive;
});