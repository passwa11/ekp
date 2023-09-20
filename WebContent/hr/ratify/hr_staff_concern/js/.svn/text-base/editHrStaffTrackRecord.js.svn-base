define(function(require, exports, module) {

	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	var lang = require('lang!hr-ratify');
	var env = require('lui/util/env');
	var ui_lang = require('lang!sys-ui');

	var _klValidation = $KMSSValidation(document.forms['hrStaffTrackRecordForm']);

	var validators = {
		'compareEnd' : {
			error : lang["hrRatify.timeError"],
			test : function(v, e, o) {
				//当前控件时间为空，则默认是永久
				if(!v){
					return true;
				}
				//当前选中控件的时间.一般是结束时间
				var currentDate = Com_GetDate(v.replace(/-/g, "/"));
				//对比的开始时间
				var fdEntranceBeginDate = document.getElementsByName("fdEntranceBeginDate")[0].value;
				var beginDate = Com_GetDate(fdEntranceBeginDate.replace(/-/g, "/"));
				//开始时间比结束时间大 则不成立
				if(beginDate.getTime() > currentDate.getTime()){
					return false;
				}
				return true;
			}
		}
	};

	_klValidation.addValidators(validators);

})