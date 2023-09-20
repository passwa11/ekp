<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<body>
<script type="text/javascript">
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
var _eventsToUseInSelect=[];
function initValue(config, eventType, nodeObject, context){
	// 定时器事件配置参数
	var eventConfig = (config == null) ? null : $.parseJSON(config);
	// 获取触发条件的相关事件类型集
	var eventsToUse = loadConditions(eventConfig, context);
	// 初始化事件列表
	if (context.nodeEvents != null) {
		var trigger = $("#event");
		// 输出到下拉框列表中
		$.each(context.nodeEvents, function(i, val){
			if (val.type != context.selectedId && $.inArray(val.type, eventsToUse) > -1) {
				if ($.inArray( val.type, _eventsToUseInSelect) == -1) {
					// 添加选项
					trigger.append('<option value="' + val.type + '">' + val.typeName + '</option>');
					// 初始化选中状态
					if(eventConfig != null && eventConfig.event == val.type) {
						trigger.val(val.type);
					}
					_eventsToUseInSelect.push(val.type);
				}
			}
		});
	}
	
	if (eventConfig != null) {
		// 触发规则
		$("#rule").val(eventConfig.rule || "1");
		// 触发条件相关信息
		$("input[name^='rule']").each(function(index){
			var attrName = $(this).attr("name");
			$(this).val(eventConfig[attrName] || "0");
		});
		// 重复类型
		$("#repeatType").val(eventConfig.repeatType);
		// 重复次数 
		$("input[name='end_times']").val(eventConfig.end_times || "1");
		// 重复间隔
		$("input[name^='interval']").each(function(index){
			var attrName = $(this).attr("name");
			$(this).val(eventConfig[attrName] || "0");
		});
		// 重复次数与间隔配置的隐藏情况
		changeRepeatType();
	}
};

/**
 * 功能：初始化触发条件列表
 *
 * @return 有触发条件的相关事件类型集
 */
function loadConditions(eventConfig, context) {
	// 记录有触发条件的事件类型集
	var eventsToUse = [];
	
	// 同步获取触发条件列表 
	$.ajaxSettings.async = false; // 同步
	$.getJSON(Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_name=com.landray.kmss.sys.lbpm.engine.event.timer.EventConditionProvider",
		{modelName: context.modelName, d: new Date().getTime()},
		function(json) {
			var conditions = $("#condition"), cache={}, cacheByEvent={};
			// 输出到下拉框列表中
			$.each(json, function(i, condition){
				var events = [];
				if (condition.eventTypes) {
					events = condition.eventTypes.split(",");
				}
				
				if (eventConfig != null) {
					if (events.length == 0 || events[0] == "*" || $.inArray(eventConfig.event, events) > -1) {
						// 添加选项
						conditions.append('<option value="' + condition.type + '">' + condition.name + '</option>');
						// 初始化选中状态
						if(eventConfig.condition && eventConfig.condition == condition.type) {
							conditions.val(condition.type);
						}
					}
				}
				
				// 按适应事件类型来缓存之...
				$.each(context.nodeEvents || [], function(index, nodeEvent) {
					var conditionBy = cacheByEvent[nodeEvent.type] || [];
					if (events.length == 0 || events[0] == "*" || $.inArray(nodeEvent.type, events) > -1) {
						conditionBy.push(condition);
						// 记录有触发条件的事件
						if ($.inArray(nodeEvent.type, eventsToUse) == -1) {
							eventsToUse.push(nodeEvent.type);
						}
					}
					// 按事件类型分类记录缓存...
					cacheByEvent[nodeEvent.type] = conditionBy;
				});
				
				// 记录缓存...
				cache[condition.type] = condition;
			});
			// 缓存之...
			$.data(conditions[0], "conditions", cache);
			$.data(conditions[0], "conditionsBy", cacheByEvent);
 	});
	$.ajaxSettings.async = true;
	
	return eventsToUse;
};

// 触发事件变更动作
function changeTriggerEvent(event) {
	var conditions = $("#condition"), cache = $.data(conditions[0], "conditionsBy");
	if (cache) {
		// 输出到触发条件列表中
		conditions.empty();
		conditions.append('<option value="">' + '<kmss:message key="page.firstOption" />' + '</option>');
		
		$.each(cache[$(event || "#event").val()] || [], function(i, condition){
			conditions.append('<option value="' + condition.type + '">' + condition.name + '</option>');
		});
	}
};

function changeRepeatType(event) {
	var repeatType = $(event || "#repeatType").val();
	if (repeatType == "never") {
		$("#end_repeat").hide();
		$("#end_repeat_interval").hide();
	} else {
		$("#end_repeat").show();
		$("#end_repeat_interval").show();
	}
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

function validateRule() {
	var check = true, zeroInterval = true, hint = '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.event.rule" />';
	// 触发条件相关信息的校验，只能为正整数
	$("input[name^='rule']").each(function(index) {
		var currentField = $(this);
		// 若为空，则置为默认值0 
		if ($.trim(currentField.val()).length == 0) {
			currentField.val("0");
		}
		
		if (!validateNumber(currentField, hint)) {
			check = false;
			return false;
		}
		
		if (parseInt(currentField.val()) > 0) {
			zeroInterval = false;
		}
	});
	
	// 超出类型，则时间间隔必须大于0
	if (check && $("#rule").val() == "1" && zeroInterval) {
		return validate($("input[name='ruleMinute']"), hint, function(jField) {
			return false;
		});
	}
	
	return check;
};

function validateInterval() {
	var check = true, zeroInterval = true, hint = '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.repeat.interval" />';
	// 触发条件相关信息的校验，只能为正整数
	$("input[name^='interval']").each(function(index) {
		var currentField = $(this);
		// 若为空，则置为默认值0 
		if ($.trim(currentField.val()).length == 0) {
			currentField.val("0");
		}
		
		if (!validateNumber(currentField, hint)) {
			check = false;
			return false;
		}
		
		if (parseInt(currentField.val()) > 0) {
			zeroInterval = false;
		}
	});
	
	// 重复间隔必须大于0
	if (check && zeroInterval) {
		return validate($("input[name='intervalMinute']"), hint, function(jField) {
			return false;
		});
	}
	
	return check;
}

/**
 * 功能：页面提交时校验方法（标准接口）
 */
function checkValue() {
	if (!validateNotNull($("#event"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.event.trigger" />')) return false;
	if (!validateNotNull($("#condition"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.notNull" argKey0="lbpm.event.timer.event.rule" />')) return false;
	// 触发条件配置间隔时间不能为0
	if (!validateRule()) return false;
	// 按次数重复...
	if ($("#repeatType").val() == "times") {
		//return isPositiveInteger($("input[name='end_times']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.repeat.end.times" />');
		if (isPositiveInteger($("input[name='end_times']"), '<kmss:message bundle="sys-lbpm-engine" key="lbpm.event.timer.validate.positive" argKey0="lbpm.event.timer.repeat.end.times" />') &&
				validateInterval()) {
			return true;
		} else {
			return false;
		}
	}
	return true;
};

/**
 * 功能：页面提交时返回值（标准接口）
 */
function returnValue() {
	var result = '"event":"' + $("#event").val() + '"';
	// 触发规则
	result += ',"rule":"' + $("#rule").val() + '"';
	// 触发条件配置信息
	$("input[name^='rule']").each(function(index){
		result += ',"' + $(this).attr("name") + '":"' + $(this).val() + '"';
	});
	// 触发条件标准
	var _condition = $("#condition").val() || "";
	if (_condition != "") {
		result += ',"condition":"' + _condition + '"';
	}
	// 重复类型
	var repeatType = $("#repeatType").val();
	result += ',"repeatType":"' + repeatType + '"';
	// 按次数重复...
	if (repeatType == "times") {
		result += ',"end_times":"' + $("input[name='end_times']").val() + '"';
		// 间隔时间配置信息
		$("input[name^='interval']").each(function(index){
			result += ',"' + $(this).attr("name") + '":"' + $(this).val() + '"';
		});
	}
	
	return '{' + result + '}';
};
</script>
<center>
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.event.trigger"/>
		</td>
		<td>
			<select id="event" onchange="changeTriggerEvent(this);">
				<option value=""><bean:message key="page.firstOption" /></option>
			</select>
			<span class="txtstrong">*</span>
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.event.startCount"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.event.rule"/>
		</td>
		<td>
			<select id="rule">
				<option value="1"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.event.rule.exceed"/></option>
			</select>
			<input name="ruleDay" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.day" bundle="sys-lbpm-engine" />
			<input name="ruleHour" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.hour" bundle="sys-lbpm-engine" />
			<input name="ruleMinute" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.minute" bundle="sys-lbpm-engine" />
			<select id="condition">
				<option value=""><bean:message key="page.firstOption" /></option>
			</select>
			<span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat"/>
		</td>
		<td>
			<select id="repeatType" onchange="changeRepeatType(this);">
				<option value="never" selected><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.never"/></option>
				<option value="times"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.times"/></option>
			</select>
		</td>
	</tr>
	<tr id="end_repeat" style="display:none">
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end"/>
		</td>
		<td>
			<input name="end_times" class="inputsgl" value="1" size="3" style="text-align:center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.end.config.times"/>
		</td>
	</tr>
	<tr id="end_repeat_interval" style="display:none">
		<td class="td_normal_title" width="15%" align="center">
			<bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.repeat.interval"/>
		</td>
		<td>
			<input name="intervalDay" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.day" bundle="sys-lbpm-engine" />
			<input name="intervalHour" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.hour" bundle="sys-lbpm-engine" />
			<input name="intervalMinute" class="inputsgl" value="0" size="3" style="text-align:center">
			<kmss:message key="FlowChartObject.Lang.minute" bundle="sys-lbpm-engine" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" colspan="2"><bean:message bundle="sys-lbpm-engine" key="lbpm.event.timer.event.help"/></td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>