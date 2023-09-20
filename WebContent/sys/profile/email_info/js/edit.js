define(function(require, exports, module) {

	var $ = require('lui/jquery');
	var lang = require('lang!sys-profile');
	var lang_xform = require('lang!sys-xform');
	var env = require('lui/util/env');
	var ui_lang = require('lang!sys-ui');
	
	var _hrValidation = $KMSSValidation(document.forms['sysSenderEmailInfoForm']);
	
	window.commitMethods = function(commitType, saveDraft) {

		var formObj = document.sysSenderEmailInfoForm;
		var docStatus = document.getElementsByName("docStatus")[0];
		
		if (saveDraft == "true") {
			docStatus.value = "10";
		} else {
			docStatus.value = "30";
		}
		
		if ('save' == commitType) {
			Com_Submit(formObj, commitType, 'fdId');
		} else {
			Com_Submit(formObj, commitType);
		}
	}
	
	var validators = {
		'checkNameUnique' : {
			error : lang['sys.email.info.repeatDocSubject'],
			test : function(v, e, o) {
				v = $.trim(v);
				v = encodeURI(v);
				var flag;
				var fdId = $('input[name="fdId"]').val();
				var docCategoryId = $('input[name="docCategoryId"]').val();

				$.ajax({
					type : "post",
					async : false,
					url : env.fn.formatUrl("/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=checkNameUnique&docSubject=")
						+ v +"&fdId="+fdId,
						dataType : "json",
						cache : false,
						success : function(data) {
							flag = data.flag;
						}
					});

				return flag;
			}
		},
		'pwRequired' : {
			error : "{name} " + lang_xform['sysFormMain.relevance.notNull'],
			test : function(v, e, o) {
				v = $.trim(v);
				v = encodeURI(v);
				var flag = false;
				if(v.length>0){
					flag = true;
				}
				return flag;
			}
		}
	};
	
	_hrValidation.addValidators(validators);
	
})