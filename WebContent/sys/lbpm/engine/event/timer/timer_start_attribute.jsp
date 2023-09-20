<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript" src="datepicker.js"></script>
<body>
<script type="text/javascript">
<!--
/**
 * 功能：页面初始化（标准接口）
 *
 * @param config 当前修改的事件参数配置信息，从节点属性窗口传递。
 * @param eventType 当前修改的事件完整参数配置信息。
 *    格式为：{id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, eventConfig: 事件参数配置,
 *	             listenerId: 监听器类型, listenerName: 监听器显示名, listenerConfig: 监听器参数配置}
 * @param nodeObject 当前配置的节点在流程图中的JS对象
 * @param context 参数配置需要的相关上下文信息。
 *    格式为：{selectedId: 当前选中的事件类型, nodeEvents: 当前节点能选择的事件类型集, modelName: 当前模块的modelName}
 */
function initValue(config, eventType, nodeObject, context){
	var status = getStatus();
	if (config != null) {
		// 定时器事件配置参数
		var eventConfig = $.parseJSON(config);
		// 触发时间
		$("input[name='timeDate']").val(eventConfig.timeDate);
		// 重复类型
		$("#repeatType").val(eventConfig.repeatType);
		// 重复相关配置
		if (eventConfig.repeatType != "never") {
			var rateType = eventConfig.rateType;
			// 重复频率
			$("#rateType").val(rateType);
			// 间隔时间
			$("input[name='interval']").val(eventConfig.interval);
			// 重复时间
			if (eventConfig.repeatTime) {
				var repeatTimeToUse = $("input[name='repeat_time_" + rateType + "']");
				if (repeatTimeToUse.length > 0) {
					// 设置一周重复时间
					if (rateType == "week") {
						var repeatTimeArray = eventConfig.repeatTime.split(",");
						repeatTimeToUse.each(function(index) {
							var current = $(this);
							if ($.inArray(current.val(), repeatTimeArray) > -1) {
								current.prop("checked", true);
							}
						});
					} else {
						// 设置一个月或一年的重复时间
						$("#repeat_time_" + rateType).setDateCalendar(eventConfig.repeatTime);
					}
				}
			}
		}
		// 结束重复
		if (eventConfig.end) initRadioValue("end", eventConfig.end);
		// 结束条件
		var key = "end_" + (eventConfig.end || "times");
		if (eventConfig[key]) {
			$("input[name='" + key + "']").val(eventConfig[key]);
		}
		// 根据重复类型呈现不同的配置
		changeRepeatType();
	} else {
		$("tr[id^='repeat_'],#end_repeat").hide();
		// 初始化重复类型值
		$("#repeatType").val("never");
	}
	
	if (status == "edit") bindTextLinkEvent();
};

function initRadioValue(fieldName, value, changeFunc) {
	$("input[name='" + fieldName + "']").each(function(index) {
		var current = $(this);
		if (current.val() == value) {
			current.prop("checked", true);
			if (changeFunc) changeFunc(this);
		}
	});
};

// 单选的文本被点击时，也等同于选中单选的联动事件绑定
function bindTextLinkEvent() {
	$("label > input:radio").each(function(index) {
		var input = $(this), attrName = input.attr("name"), label = input.parent();
		if (attrName == "end") {
			label.click(function(event) {
				if (event.target != input[0]) input.prop("checked", true);
				changeEndCondition(input[0]);
			});
		} else {
			label.click(function(event) {
				if (event.target != input[0]) input.prop("checked", true);
			});
		}
	});
};

function changeRateType(event) {
	var rateType = $(event || "#rateType").val();
	// 呈现相应的间隔时间描述
	$("span[id^='interval_']").hide();
	$("#interval_" + rateType).show();
	// 需要先判断重复时间配置是否存在，若存在则显示重复时间整行，比如：每日就没有重复时间配置
	if ($("#repeat_time_" + rateType).length == 0) {
		$("#repeat_time").hide();
	} else {
		$("div[id^=repeat_time_]").hide();
		$("#repeat_time,#repeat_time_" + rateType).show();
	}
};

function changeRepeatType(event) {
	var repeatType = $(event || "#repeatType").val();
	if (repeatType == "never") {
		$("tr[id^='repeat_'],#end_repeat").hide();
	} else if (repeatType == "times") {
		$("tr[id^='repeat_'],tr[id^='end_repeat_'],#end_repeat_times_option").hide();
		$("#end_repeat,#end_repeat_times,#end_repeat_times_config").show();
	} else if (repeatType == "custom") {
		$("#repeat_rate,#end_repeat,#end_repeat_times_option,tr[id^='end_repeat_']").show();
		changeRateType();
		changeEndCondition();
	} else {
		$("tr[id^='repeat_']").hide();
		$("#end_repeat,#end_repeat_times_option,tr[id^='end_repeat_']").show();
		changeEndCondition();
	}
};

function changeEndCondition(input) {
	var end = $(input || "input:radio[name='end']:checked").val();
	// 隐藏非当前选中的结束条件的配置
	$("input[name='end']").each(function(index) {
		var endValue = $(this).val(), configId = "end_repeat_" + endValue + "_config";
		if (endValue == end) {
			$("#" + configId).show();
		} else {
			$("#" + configId).hide();
		}
	});
};

// 获取当前编辑状态
function getStatus() {
	var reg = new RegExp("(^|&)method=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    return (r != null) ? unescape(r[2]) : "edit";
};

/**
 * 功能：校验通用方法
 * 
 * @param jField 需校验字段的jquery对象
 * @param hint 校验不通过时的提示信息
 * @param validateFunc 校验条件函数，参数只接收需校验字段的jquery对象。此参数没有返回校验合法。
 */
function validate(jField, hint, validateFunc) {
	if (validateFunc && !validateFunc(jField)) {
		alert(hint);
		jField.focus();
		return false;
	}
	return true;
};

function validateNotNull(jField, hint) {
	return validate(jField, hint, function(field) {
		return !!field.val();
	});
};

function validateNumber(jField, hint) {
	return validate(jField, hint, function(field) {
		return /^\d+(\.\d+)?$/.test(field.val());
	});
};

function isPositiveInteger(jField, hint) {
	return validate(jField, hint, function(field) {
		return /^\d+(\.\d+)?$/.test(field.val()) && parseInt(field.val()) > 0;
	});
};

/**
 * 功能：页面提交时校验方法（标准接口）
 *
 * @param eventType 当前事件和监听器配置的基本信息。
 *    格式为：{id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, listenerId: 监听器类型, listenerName: 监听器显示名}
 * @param havedEventTypes 当前节点已经配置的事件和监听器配置的信息集。
 *	  格式为：id（唯一标识）: {id: 唯一标识, name: 事件配置显示名, type: 事件类型, typeName: 事件显示名, listenerId: 监听器类型, listenerName: 监听器显示名}
 */
function checkValue(eventType, havedEventTypes) {
	// 触发时间
	if (!validateNotNull($("input[name='timeDate']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.date.trigger" />')) return false;
	// 触发时间不能早于当前时间
	var check = validate($("input[name='timeDate']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.timeDate" />', function(field) {
		var triggerDate = new Date((field.val()).replace(/-/g,"/")), current = new Date();
		return (triggerDate >= current);
	});
	if (!check) return false;
	// 重复类型
	var repeatType = $("#repeatType").val();
	if (repeatType == "never") {
		return true;
	}
	
	// 自定义重复条件 
	if (repeatType == "custom") {
		// 间隔时间
		if (!isPositiveInteger($("input[name='interval']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.repeat.interval" />')) return false;
		// 重复时间 
		var rateType = $("#rateType").val(), repeatTimeToUse = $("input[name='repeat_time_" + rateType + "']");
		if (repeatTimeToUse.length > 0) {
			if (rateType == "week") {
				if (!checkRepeatTimeOnWeek(repeatTimeToUse)) {
					alert('<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.repeat.rate.time" />');
					repeatTimeToUse.focus();
					return false;
				}
			} else {
				if (!validateNotNull(repeatTimeToUse, '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.repeat.rate.time" />')) return false;
			}
		}
	}
	
	// 结束条件
	return checkEndCondition();
};

function checkRepeatTimeOnWeek(jqRepeatTime) {
	var check = false;
	jqRepeatTime.each(function(index) {
		if ($(this).prop("checked")) {
			check = true;
			return false;
		}
	});
	return check;
};

function checkEndCondition() {
	var check = true;
	// 校验重复结束条件 
	$("input[name='end']").each(function(index) {
		var endOption = $(this);
		if (!!endOption.prop("checked")) {
			var value = endOption.val();
			if (value == "times") {
				check = isPositiveInteger($("input[name='end_times']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.repeat.end.times" />');
			} else if (value == "timeDate") {
				check = validateNotNull($("input[name='end_timeDate']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.repeat.end.date" />');
				// 校验触发时间不能大于结束时间
				check = check && validate($("input[name='timeDate']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.endTime"/>', function(field) {
					return (checkCompareDate(field.val(), $("input[name='end_timeDate']").val()) < 1);
				});
			}
			return false;
		}
	});
	return check;
};

function checkCompareDate(dateOne, dateTwo) { 
	var _dateOne = dateOne.replace(/-/g,"\/"), _dateTwo = dateTwo.replace(/-/g,"\/");
	var dateForOne = Date.parse(_dateOne), dateForTwo = Date.parse(_dateTwo);
	if (dateForOne > dateForTwo) {
		return 1;
	} else if (dateForOne == dateForTwo) {
		return 0;
	} else {
		return -1;
	}
};

/**
 * 功能：页面提交时返回值（标准接口）
 */
function returnValue() {
	// 触发时间
	var result = '"timeDate":"' + $("input[name='timeDate']").val() + '"';
	// 重复类型 
	var repeatType = $("#repeatType").val();
	result += ',"repeatType":"' + repeatType + '"';
	// 自定义...
	if (repeatType == "custom") {
		var rateType = $("#rateType").val();
		// 重复频率
		result += ',"rateType":"' + rateType + '"';
		// 间隔时间
		result += ',"interval":"' + $("input[name='interval']").val() + '"';
		// 重复时间 
		var repeatTimeToUse = $("input[name='repeat_time_" + rateType + "']");
		if (repeatTimeToUse.length > 0) {
			// 一周重复时间设置
			if (rateType == "week") {
				var repeatTimeArray = [];
				repeatTimeToUse.each(function(index) {
					var current = $(this);
					if (current.prop("checked")) {
						repeatTimeArray.push(current.val());
					}
				});
				// 输出重复时间
				result += ',"repeatTime":"' + repeatTimeArray.join(",") + '"';
			} else {
				// 输出一个月或一年的重复时间
				result += ',"repeatTime":"' + repeatTimeToUse.val() + '"';
			}
		}
	}
	// 结束条件 
	if (repeatType != "never") {
		$("input[name='end']").each(function(index) {
			var endOption = $(this), value = endOption.val();
			if (!!endOption.prop("checked")) {
				result += ',"end":"' + value + '"';
				if (value != "always") result += ',"end_' + value + '":"' + $("input[name='end_" + value + "']").val() + '"';
				return false;
			}
		});
	}
	
	return '{' + result + '}';
};
//-->
</script>
<center>
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr>
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.date.trigger"/>
		</td>
		<td colspan="3">
			<c:if test="${param.method == 'edit'}">
			<xform:datetime property="timeDate" showStatus="edit" />
			<span class="txtstrong">*</span>
			</c:if>
			<c:if test="${param.method != 'edit'}">
			<xform:datetime property="timeDate" showStatus="readOnly" />
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat"/>
		</td>
		<td colspan="3">
			<select id="repeatType" onchange="changeRepeatType(this);">
				<option value="never" selected><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.never"/></option>
				<option value="day"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.day"/></option>
				<option value="week"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.week"/></option>
				<option value="twoWeek"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.twoWeek"/></option>
				<option value="month"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.month"/></option>
				<option value="year"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.year"/></option>
				<option value="custom"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.custom"/></option>
			</select>
		</td>
	</tr>
	<tr id="repeat_rate" style="display:none">
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.rate"/>
		</td>
		<td width="25%">
			<select id="rateType" onchange="changeRateType(this);">
				<option value="day" selected><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.day"/></option>
				<option value="week"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.week"/></option>
				<option value="month"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.month"/></option>
				<option value="year"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.year"/></option>
			</select>
		</td>
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.interval"/>
		</td>
		<td>
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.interval.every"/>
			<input name="interval" class="inputsgl" value="1" size="3" style="text-align:center">
			<span id="interval_day"><bean:message key="resource.period.type.day.name"/></span>
			<span id="interval_week" style="display:none"><bean:message key="resource.period.type.week.name"/></span>
			<span id="interval_month" style="display:none"><bean:message key="resource.period.type.month.name"/></span>
			<span id="interval_year" style="display:none"><bean:message key="resource.period.type.year.name"/></span>
		</td>
	</tr>
	<tr id="repeat_time" style="display:none">
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.rate.time"/>
		</td>
		<td colspan="3">
			<div id="repeat_time_week" style="display:none">
				<span><label><input type="checkbox" name="repeat_time_week" value="SUN"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.sunday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="MON"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.monday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="TUE"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.tuesday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="WED"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.wednesday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="THU"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.thursday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="FRI"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.friday"/></label></span>&nbsp;&nbsp;
				<span><label><input type="checkbox" name="repeat_time_week" value="SAT"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.week.saturday"/></label></span>
			</div>
			<div id="repeat_time_month" style="display:none">
				<input name="repeat_time_month" value="" style="display:none">
			</div>
			<div id="repeat_time_year" style="display:none">
				<input name="repeat_time_year" value="" style="display:none">
			</div>
			<script type="text/javascript">
				$("#repeat_time_month").drawMonthCalendar($("input[name='repeat_time_month']")[0], getStatus());
				// 月份的语言描述
				var monthLangs = {
						'Jan': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Jan"/>',
						'Feb': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Feb"/>',
						'Mar': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Mar"/>',
						'Apr': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Apr"/>',
						'May': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.May"/>',
						'Jun': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Jun"/>',
						'Jul': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Jul"/>',
						'Aug': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Aug"/>',
						'Sept': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Sept"/>',
						'Oct': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Oct"/>',
						'Nov': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Nov"/>',
						'Dec': '<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.month.Dec"/>'
				};
				$("#repeat_time_year").drawYearCalendar($("input[name='repeat_time_year']")[0], monthLangs, getStatus());
			</script>
		</td>
	</tr>
	<tr id="end_repeat" style="display:none">
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end"/>
		</td>
		<td colspan="3">
			<table class="tb_noborder" width="98%">
				<tr id="end_repeat_always" height="25">
					<td>
						<span id="end_repeat_always_option">
							<label>
								<input name="end" type="radio" value="always" checked>
								<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end.always"/>
							</label>
						</span>
					</td>
				</tr>
				<tr id="end_repeat_times" height="25">
					<td>
						<span id="end_repeat_times_option">
							<label>
								<input name="end" type="radio" value="times">
								<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end.times"/>
							</label>
						</span>
						<span id="end_repeat_times_config">
							<input name="end_times" class="inputsgl" value="1" size="3" style="text-align:center">
							<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end.config.times"/>
						</span>
					</td>
				</tr>
				<tr id="end_repeat_timeDate" height="25">
					<td>
						<span id="end_repeat_timeDate_option">
							<label>
								<input name="end" type="radio" value="timeDate">
								<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end.date"/>
							</label>
						</span>
						<span id="end_repeat_timeDate_config">
							<c:if test="${param.method == 'edit'}">
							<xform:datetime property="end_timeDate" showStatus="edit" />
							</c:if>
							<c:if test="${param.method != 'edit'}">
							<xform:datetime property="end_timeDate" showStatus="readOnly" />
							</c:if>
						</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>