<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>

<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|doclist.js");
</script>
<script src="../js/attribute.js"></script>
</head>
<body onload="AttributeObject.initDocument();">
<br>
<script>
//当前节点对象
AttributeObject.NodeObject = dialogObject.Node;
//当前节点配置信息对象
AttributeObject.NodeData = AttributeObject.NodeObject.Data;
AttributeObject.STATUS_RUNNING = dialogObject.Window.STATUS_RUNNING;
AttributeObject.isEdit = function() {
	var isEdit = "${JsParam.isEdit}";
	if(isEdit){
		return isEdit=="true";
	}else{
		return FlowChartObject.IsEdit && AttributeObject.NodeObject.Status != AttributeObject.STATUS_RUNNING;
	}
};

// 当前选择事件类型
AttributeObject.CurrEventType = dialogObject.CurrEventType;
// 当前节点下已经配置所有事件类型
AttributeObject.HavedEventTypes = dialogObject.HavedEventTypes;

// 按事件类型，初始化事件数据
AttributeObject.loadEvents = function() {
	var cacheElement = $("#main")[0], events = $.data(cacheElement, "events") || {}, _nodeType = AttributeObject.NodeObject.Type;
	if (!events[_nodeType]) {
		var eventsForType = {}, _nodeEvents = AttributeObject.loadNodeEvents(_nodeType);
		$.each(_nodeEvents || [], function(i, event) {
			eventsForType[event.type] = event;
		});
		events[_nodeType] = eventsForType;
		
		$.data(cacheElement, "events", events);
	}
	return events[_nodeType];
};

//初始化事件数据
AttributeObject.loadNodeEvents = function(nodeType) {
	if(!Data_XMLCatche.LbpmNodeEvents) {
		Data_XMLCatche.LbpmNodeEvents = [];
	}
	
	var nodeEvent = Data_XMLCatche.LbpmNodeEvents[nodeType];
	if(!nodeEvent) {
		$.ajaxSettings.async = false; // 同步
		$.getJSON(Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_name=com.landray.kmss.sys.lbpm.engine.manager.event.EventDataProvider",
			{modelName: FlowChartObject.ModelName, nodeType: nodeType, d: new Date().getTime()},
			function(json) {
				nodeEvent = Data_XMLCatche.LbpmNodeEvents[nodeType] = json;
	 	});
		$.ajaxSettings.async = true;
	}
	return nodeEvent;
};

// 初始化事件
AttributeObject.Init.AllModeFuns.push(function() {
	document.title = '<kmss:message key="FlowChartObject.Lang.Event.eventConfigTitle" bundle="sys-lbpm-engine" />';
	$("#eventName").val(AttributeObject.CurrEventType ? AttributeObject.CurrEventType.name : "");
	
	var nodeEvents = AttributeObject.loadEvents();
	// 初始化事件类型列表，同时返回当前选中的事件类型
	var _currentEvent = loadConfig("event", nodeEvents || {}, "type", "typeName", "typeJsp", "typeDescription");
	if (_currentEvent != null) {
		// 初始化监听器类型列表
		loadConfig("listener", _currentEvent.listeners || [], "listenerId", "listenerName", "listenerJsp", "listenerDescription");
	}
});

/**
 * 功能：根据配置初始化相应类型的选项和参数配置页面
 * @param type 类型（event/listener）
 * @param configList 配置列表信息，从父窗口传递的相关信息
 * @param idAttr 配置的标识属性名
 * @param nameAttr 配置的显示属性名
 * @param jspAttr 配置的相应参数配置页面属性名
 * @param descriptionAttr 配置的说明属性名
 */
function loadConfig(type, configList, idAttr, nameAttr, jspAttr, descriptionAttr) {
	var selected = null, field = $("#" + type);
	// 输出到下拉框列表中
	$.each(configList, function(i, config) {
		field.append('<option value="' + config[idAttr] + '">' + config[nameAttr] + '</option>');
		// 初始化选中状态
		if(AttributeObject.CurrEventType && AttributeObject.CurrEventType[idAttr] == config[idAttr]) {
			selected = config;
			// 初始化选中状态
			field.val(config[idAttr]);
			if(config[descriptionAttr]){
				$("#" + type + "Description").text(config[descriptionAttr]);
				$("#tr_"+type+"_description").show();
			}
		}
	});
	
	// 加载相应参数配置页面
	if (selected != null && selected[jspAttr]) {
		loadJsp(type, selected[jspAttr], selected[idAttr], selected[nameAttr]);
	}
	
	return selected;
};

/**
 * 功能：加载事件或监听器配置页面
 * @param type 类型（event/listener）
 * @param jsp 加载JSP路径
 * @param showId 配置对应元素的标识，即事件或监听器标识
 * @param showName 配置标题名，即事件或监听器显示名
 */
function loadJsp(type, jsp, showId, showName) {
	var _jsp = Com_SetUrlParameter(jsp, "method", AttributeObject.isEdit() ? "edit" : "view");
	// 加载页面...
	$("#if_" + type).attr("src", _jsp).load(function() {
		var ifType = document.getElementById("if_" + type);
		// 相应类型初始化
		if(ifType && ifType.contentWindow.initValue) {
			var nodeEvents = AttributeObject.loadEvents();
			// 组装上下文信息，提供给加载页面初始化使用
			var context = {selectedId: showId, nodeEvents: nodeEvents, modelName: FlowChartObject.ModelName};
			if(AttributeObject.CurrEventType) {
				// 从节点属性选择的事件配置中加载当前配置页面，此时需把已选择的配置信息传递给配置页面
				ifType.contentWindow.initValue(AttributeObject.CurrEventType[type + "Config"], AttributeObject.CurrEventType, AttributeObject.NodeObject, context);
			} else {
				ifType.contentWindow.initValue(null, null, AttributeObject.NodeObject, context);
			}
		}
	});
	$("#tr_" + type).show();
	
	$("#tr_" + type + "_title :first-child").text(showName + ' <kmss:message bundle="sys-lbpm-engine" key="FlowChartObject.Lang.Event.configTitle" />');
	$("#tr_" + type + "_title").show();
};

// 功能：清除配置页面
function clearJsp(type) {
	$("#if_" + type).attr("src", "");
	$("#tr_" + type).hide();
	$("#tr_" + type + "_title").hide();
};

function clearDescription(type) {
	$("#" + type + "Description").text('');
	$("#tr_" + type + "_description").hide();
}

AttributeObject.Init.ViewModeFuns.push(function() {
	$("#DIV_ReadButtons").show();
	$("#if_event").load(function() {
		var ifEvent = document.getElementById("if_event");
		if(ifEvent && ifEvent.contentWindow.initView) {
			ifEvent.contentWindow.initView();
		}
		AttributeObject.Utils.disabledOperation(window.frames["if_event"].document || window.frames["if_event"].contentWindow.document);
	});
	$("#if_listener").load(function() {
		var ifListener = document.getElementById("if_listener");
		if(ifListener && ifListener.contentWindow.initView) {
			ifListener.contentWindow.initView();
		}
		AttributeObject.Utils.disabledOperation(window.frames["if_listener"].document || window.frames["if_listener"].contentWindow.document);
	});
});

AttributeObject.Init.EditModeFuns.push(function() {
	$("#DIV_EditButtons").show();
});

// 选择事件类型
AttributeObject.changeEventType = function(current) {
	if ($.trim($(current).val()).length == 0) {
		clearJsp("event");
		clearJsp("listener");
		clearDescription("event");
		clearDescription("listener");
		return;
	}
	
	var _currentEventType = $(current).val(), _event = AttributeObject.loadEvents()[_currentEventType];
	// 显示相应的事件说明
	$("#eventDescription").text(_event.typeDescription);
	if(_event.typeDescription){
		$("#tr_event_description").show();
	} else {
		$("#tr_event_description").hide();
	}
	// 更新当前事件类型下的事件监听列表
	var _listener = $("#listener");
	_listener.empty();
	_listener.append('<option value="">' + '<kmss:message key="page.firstOption" />' + '</option>');
	// 输出到下拉框列表中
	$.each(_event.listeners || [], function(i, val) {
		_listener.append('<option value="' + val.listenerId + '">' + val.listenerName + '</option>');
	});
	clearDescription("listener");
	// 加载事件类型的配置页面
	if(_event.typeJsp) {
		loadJsp("event", _event.typeJsp, _event.type, _event.typeName);
	} else {
		clearJsp("event");
	}
	// 清除事件侦听器的配置页面
	clearJsp("listener");
};

// 选择事件侦听器
AttributeObject.changeListener = function(current) {
	if ($.trim($(current).val()).length == 0) {
		clearJsp("listener");
		clearDescription("listener");
		return;
	}
	
	var _currentEventType = $("#event").val(), _currentListenerType = $(current).val();
	// 当前选择的事件类型对象
	var _event = AttributeObject.loadEvents()[_currentEventType];
	
	$.each(_event.listeners || [], function(i, listener) {
		if (listener.listenerId == _currentListenerType) {
			// 加载事件侦听器的配置页面
			if (listener.listenerJsp) {
				loadJsp("listener", listener.listenerJsp, listener.listenerId, listener.listenerName);
				// 显示相应的侦听器功能说明
				$("#listenerDescription").text(listener.listenerDescription);
				if(listener.listenerDescription){
					$("#tr_listener_description").show();
				} else {
					$("#tr_listener_description").hide();
				}
			} else {
				clearJsp("listener");
			}
			
			return false;
		}
	});
};

function returnIframeInformation(type, outConfig) {
	var ifType = document.getElementById("if_" + type);
	if (ifType) {
		if (ifType.contentWindow.checkValue) {
			// 配置校验
			if(!ifType.contentWindow.checkValue(outConfig, AttributeObject.HavedEventTypes)) {
				return false;
			}
		}
		// 监听器配置可以在组装返回值前，根据事件参数配置信息做相应的拼装
		if (ifType.contentWindow.beforeReturnValue) {
			ifType.contentWindow.beforeReturnValue(outConfig.type, outConfig["eventConfig"]);
		}
		// 配置返回值
		if (ifType.contentWindow.returnValue) { 
			outConfig[type + "Config"] = ifType.contentWindow.returnValue();
		}
	}
	return true;
};

// 提交数据
AttributeObject.SubmitFuns.push(function() {
	function validateValue(fieldId, hint) {
		var field = $("#" + fieldId);
		if (!field.val()) {
			alert(hint);
			field.focus();
			return false;
		}
		return true;
	};
	
	if (!validateValue('eventName', '<kmss:message bundle="sys-lbpm-engine" key="lbpm.eventName.notNull" />')) return false;
	if (!validateValue('event', '<kmss:message bundle="sys-lbpm-engine" key="lbpm.eventType.select" />')) return false;
	if (!validateValue('listener', '<kmss:message bundle="sys-lbpm-engine" key="lbpm.listenerType.select" />')) return false;
	
	var _config = {}, eventType = $("#event"), listenerType = $("#listener");
	_config.id = AttributeObject.CurrEventType ? AttributeObject.CurrEventType.id : AttributeObject.Utils.generateID();
	_config.name = $("#eventName").val();
	_config.type = eventType.val();
	_config.typeName = eventType.find("option:selected").text();
	_config.listenerId = listenerType.val();
	_config.listenerName = listenerType.find("option:selected").text();
	// 获取事件类型参数配置信息
	if (!returnIframeInformation("event", _config)) return false;
	// 获取监听器类型参数配置信息
	if (!returnIframeInformation("listener", _config)) return false;
	
	returnValue = _config;
	window.close();
});

// 生成Id
AttributeObject.Utils.generateID = function() {
	return parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
};
</script>
<p class="txttitle"><bean:message bundle="sys-lbpm-engine" key="lbpm.eventConfig"/></p>
<center>
	<table id="main" class="tb_normal" width="95%">
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-lbpm-engine" key="lbpm.eventName"/>
			</td>
			<td width=75%>
				<input id="eventName" name="eventName" class="inputsgl" style="width:95%" />
				<span class="txtstrong">*</span>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-lbpm-engine" key="lbpm.eventType"/>
			</td>
			<td width=75%>
				<select id="event" onchange="AttributeObject.changeEventType(this);">
					<option value=""><bean:message key="page.firstOption" /></option>
				</select>
				<span class="txtstrong">*</span>
			</td>
		</tr>
		<tr id="tr_event_description" style="display:none">
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-lbpm-engine" key="lbpm.eventDescription"/>
			</td>
			<td id="eventDescription" width=75%>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-lbpm-engine" key="lbpm.listenerType"/>
			</td>
			<td width=75%>
				<select id="listener" onchange="AttributeObject.changeListener(this);">
					<option value=""><bean:message key="page.firstOption" /></option>
				</select>
				<span class="txtstrong">*</span>
			</td>
		</tr>
		<tr id="tr_listener_description" style="display:none">
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-lbpm-engine" key="lbpm.listenerDescription"/>
			</td>
			<td id="listenerDescription" width=75%>
			</td>
		</tr>
		<tr id="tr_event_title" style="display:none">
			<td class="td_normal_title" colspan="2"></td>
		</tr>
		<tr id="tr_event" style="display:none">
			<td colspan="2" height="190px" style="padding: 0px">
				<iframe id="if_event" src="" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe>
			</td>
		</tr>
		<tr id="tr_listener_title" style="display:none">
			<td class="td_normal_title" colspan="2"></td>
		</tr>
		<tr id="tr_listener" style="display:none">
			<td colspan="2" height="210px" style="padding: 0px">
				<iframe id="if_listener" src="" frameborder="0" scrolling="yes" width="100%" height="100%"></iframe>
			</td>
		</tr>
	</table>
	<br />
	<div id="DIV_EditButtons" style="display:none">
		<input name="btnOK" type="button" class="btnopt" onclick="if(AttributeObject.submitDocument())window.close();" value="  <kmss:message key="FlowChartObject.Lang.OK" bundle="sys-lbpm-engine" />  " />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="btnCancel" type="button" class="btnopt" onclick="window.close();" value="  <kmss:message key="FlowChartObject.Lang.Cancel" bundle="sys-lbpm-engine" />  " />
	</div>
	<div id="DIV_ReadButtons" style="display:none">
		<input name="btnClose" type="button" class="btnopt" onclick="window.close();" value="  <kmss:message key="FlowChartObject.Lang.Close" bundle="sys-lbpm-engine" />  " />
	</div>
	<br>
	<br>
</center>
</body>
</html>