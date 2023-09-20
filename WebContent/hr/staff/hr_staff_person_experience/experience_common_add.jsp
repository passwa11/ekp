<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:button text="${lfn:message('button.save')}" onclick="_submit();" height="35" width="120" ></ui:button>

<script type="text/javascript">
	// 表单校验
	var _validation = $KMSSValidation();

	seajs.use( [ 'lui/jquery', 'lui/dialog', 'hr/staff/resource/js/dateUtil' ], function($, dialog, dateUtil) {
		// 表单序列化成JSON对象
			$.fn.serializeObject = function() {
				var o = {};
				var a = this.serializeArray();
				$.each(a, function() {
					if (o[this.name] !== undefined) {
						if (!o[this.name].push) {
							o[this.name] = [ o[this.name] ];
						}
						o[this.name].push(this.value || '');
					} else {
						o[this.name] = this.value || '';
					}
				});
				return o;
			};

			// 确认提交
			window._submit = function() {
				if ($KMSSValidation().validate()) {
					window.$dialog.hide($("form").serializeObject());
				}
			};

			// 取消
			window._cancel = function() {
				window.$dialog.hide();
			};

			var compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error1") }';
			switch('${JsParam.type}'){
			case 'contract':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error2") }';
				break;
			}
			case 'education':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error3") }';
				break;
			}
			case 'qualification':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error4") }';
				break;
			}
			}

			// 日期区间校验
			_validation.addValidator('compareDate', compareDateMsg, function(v, e, o) {
				var fdBeginDate = $('[name="fdBeginDate"]');
				var fdEndDate = $('[name="fdEndDate"]');
				var result = true;
				if (fdBeginDate.val() && fdEndDate.val()) {
					var start = dateUtil.parseDate(fdBeginDate.val());
					var end = dateUtil.parseDate(fdEndDate.val());
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});
			// 任职记录日期区间校验
			_validation.addValidator('compareEntranceDate', compareDateMsg, function(v, e, o) {
				var fdBeginDate = $('[name="fdEntranceBeginDate"]');
				var fdEndDate = $('[name="fdEntranceEndDate"]');
				var result = true;
				if (fdBeginDate.val() && fdEndDate.val()) {
					var start = dateUtil.parseDate(fdBeginDate.val());
					var end = dateUtil.parseDate(fdEndDate.val());
					if (start.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});
			//任职记录结束时间必须大于当前时间
			_validation.addValidator('entranceEndDate', "结束时间必须大于当前时间", function(v, e, o) {
				var fdEndDate = $('[name="fdEntranceEndDate"]');
				var currDate = new Date();
				var result = true;
				if (fdEndDate.val()) {
					var end = dateUtil.parseDate(fdEndDate.val());
					if (currDate.getTime() > end.getTime()) {
						result = false;
					}
				}
				return result;
			});
		});
</script>