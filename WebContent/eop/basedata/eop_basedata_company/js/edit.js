define(function(require, exports) {
	var langM = require('lang!eop-basedata');
	var langUI = require('lang!sys-ui');
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langUI);
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");

	var params = {
	}

	var init = function(iparams) {
		params = $.extend(params, iparams);
		initValidation();
	}

	var initValidation = function() {
		$KMSSValidation().addValidator("lrIdenValidator", lang["py.identity.invalid"], checkPersonIdentity)
	}

	var checkPersonIdentity = function(v, e, o) {
		var rtn = !v || /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(v);
		return rtn;
	}

	var onContactorChoosed = function(rtnData) {
		var contactorInfoUrl = params.ctxPath + "/eop/basedata/eop_basedata_company/eopBasedataCompany.do";
		contactorInfoUrl = Com_SetUrlParameter(contactorInfoUrl, "method", "getContactorInfo");
		contactorInfoUrl = Com_SetUrlParameter(contactorInfoUrl, "contactorId", rtnData[0]);
		$.ajax({
			url: contactorInfoUrl,
			dataType: "json",
			success: function(rtn) {
				if (rtn.success) {
					$("[name='contactorMobileNo']").val(rtn["mobile_no"]);
					$("[name='contactorEmail']").val(rtn["email"]);
				}
			}
		});
	}

	exports.init = init;
	exports.onContactorChoosed = onContactorChoosed;
})