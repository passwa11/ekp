<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
	
	require(['dojo/ready','dijit/registry','dojo/query','dojo/dom-style','dojo/dom-class','dojo/_base/lang',
	         'mui/dialog/Tip','dojo/request','mui/util','mui/i18n/i18n!hr-staff:hrStaffPersonExperience','dojo/dom-attr',
	         'dojo/ready','dojo/topic'],function(ready,registry,query,domStyle,domClass,lang,Tip,req,util,msg,domAttr,ready,topic){
		//校验对象
		var validorObj=null;
		ready(function(){
			
			validorObj=registry.byId('baseInfoForm');
			var compareDateMsg = "${ lfn:message('hr-staff:hrStaffPersonExperience.compareDate.error1') }";
			
			switch('${JsParam.type}'){
				case 'contract':{
					compareDateMsg = "${ lfn:message('hr-staff:hrStaffPersonExperience.compareDate.error2') }";
					break;
				}
				case 'education':{
					compareDateMsg = "${ lfn:message('hr-staff:hrStaffPersonExperience.compareDate.error3') }";
					break;
				}
				case 'qualification':{
					compareDateMsg = "${ lfn:message('hr-staff:hrStaffPersonExperience.compareDate.error4') }";
					break;
				}
			}

			// 日期区间校验
			validorObj._validation.addValidator('compareDate', compareDateMsg, function(v, e, o) {
				var fdBeginDate = $('[name="fdBeginDate"]');
				var fdEndDate = $('[name="fdEndDate"]');
				var result = true;
				if (fdBeginDate.val() && fdEndDate.val()) {
					var start = Com_GetDate(fdBeginDate.val());
					var end = Com_GetDate(fdEndDate.val());
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});
			// 任职时间区间校验
			validorObj._validation.addValidator('compareEntranceDate', compareDateMsg, function(v, e, o) {
				var fdBeginDate = $('[name="fdEntranceBeginDate"]');
				var fdEndDate = $('[name="fdEntranceEndDate"]');
				var result = true;
				if (fdBeginDate.val() && fdEndDate.val()) {
					var start = Com_GetDate(fdBeginDate.val());
					var end = Com_GetDate(fdEndDate.val());
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});

            // 勾选长期有效校验
            validorObj._validation.addValidator('checkLongterm', "${ lfn:message("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.error") }", function(v, e, o) {
                var result = true;
                var longtermContract = $('[name="fdIsLongtermContract"]').val();
                if(v){
                    if(longtermContract == 'true'){
                        result = false;
                    }
                }
                return result;
            });
            // 长期有效勾选清空到期时间
            window.cancelEndDate = function(value) {
                if (value == 'true') {
                    registry.byId('fdEndDate')._setValueAttr("");
                }
            }
		});
	});
</script>