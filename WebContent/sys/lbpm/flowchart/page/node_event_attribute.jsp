<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>

<style>
.btn_txt {
	margin: 0px 2px;
	color: #2574ad;
	border-bottom: 1px solid transparent;
}

.btn_txt:hover {
	text-decoration: underline;
}
</style>

<table id="TABLE_LBPM_NODE_EVENT" class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width="24%"><kmss:message key="FlowChartObject.Lang.Event.eventName" bundle="sys-lbpm-engine" /></td>
		<td class="td_normal_title" width="26%"><kmss:message key="FlowChartObject.Lang.Event.eventType" bundle="sys-lbpm-engine" /></td>
		<td class="td_normal_title" width="28%"><kmss:message key="FlowChartObject.Lang.Event.eventListener" bundle="sys-lbpm-engine" /></td>
		<td class="td_normal_title" width="22%">
			<a class="btn_txt" href="javascript:void(0);" onclick="AttributeObject.addNodeEvent();"><kmss:message key="FlowChartObject.Lang.Event.eventAdd" bundle="sys-lbpm-engine" /></a>
			<span style="display:none"><kmss:message key="FlowChartObject.Lang.Event.eventOperate" bundle="sys-lbpm-engine" /></span>
		</td>
	</tr>
</table>

<script>
var dialogObject=window.dialogArguments?window.dialogArguments:(opener==null?parent.Com_Parameter.Dialog:opener.Com_Parameter.Dialog);
// 初始化
AttributeObject.Init.AllModeFuns.push(function() {
	var nodeData = AttributeObject.NodeData;
	if(nodeData && nodeData.events) {
		var cache_config = {};
		$.each(nodeData.events, function(i, event) {
			// event: {id: 唯一标识, name: 事件配置名, type: 事件类型, eventConfig: 事件参数配置信息, listenerId: 监听器标识, listenerConfig: 监听器参数配置信息}
			if (event.id && event.type && event.listenerId) {
				// 获得当前事件相关扩展信息
				var _event = AttributeObject.getNodeEventType(AttributeObject.NodeObject.Type, event.type);
				if (_event) {
					// 获取相应配置的监听器扩展信息
					var _listener = AttributeObject.getNodeEventListener(_event, event.listenerId);
					if (_listener) {
						var _config = {id: event.id, name: event.name, typeName: _event.typeName, listenerName: _listener.listenerName};
						AttributeObject._addNodeEvent(_config);
						// 补充其他信息
						_config.type = _event.type;
						_config.eventConfig = event.eventConfig;
						_config.listenerId = _listener.listenerId;
						_config.listenerConfig = event.listenerConfig;
						// 缓存之...
						cache_config[event.id] = _config;
					}
				}
			}
		});
		$.data($("#TABLE_LBPM_NODE_EVENT")[0], "configs", cache_config);
	}
});

// 初始化事件数据
AttributeObject.loadNodeEvents = function(nodeType) {
	if(!Data_XMLCatche.LbpmNodeEvents) {
		Data_XMLCatche.LbpmNodeEvents = [];
	}
	
	var nodeEvent = Data_XMLCatche.LbpmNodeEvents[nodeType];
	if (!nodeEvent) {
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

// 根据类型获取事件类型
AttributeObject.getNodeEventType = function(nodeType, checkEventType) {
	var result = null, nodeEvent = AttributeObject.loadNodeEvents(nodeType);
	$.each(nodeEvent || {}, function(i, event) {
		if (event.type == checkEventType) {
			result = event;
			return false;
		}
	});
	return result;
};

// 获取事件类型下的侦听器
AttributeObject.getNodeEventListener = function(event, listenerId) {
	var result = null, listeners = (event && event.listeners) ? event.listeners : [];
	$.each(listeners, function(i, listener) {
		if (listener.listenerId == listenerId) {
			result = listener;
			return false;
		}
	});
	return result;
};

AttributeObject.Init.ViewModeFuns.push(function() {
	$("#TABLE_LBPM_NODE_EVENT span:first").show();
});

// 添加动态行
AttributeObject._addNodeEvent = function(config) {
	var tr = $("#TABLE_LBPM_NODE_EVENT")[0].insertRow(-1);
	$(tr).attr("id", config.id);
	
	$(tr.insertCell(-1)).text(config.name);
	$(tr.insertCell(-1)).text(config.typeName);
	$(tr.insertCell(-1)).text(config.listenerName);
	
	// 更新操作...
	var opUpdate = AttributeObject._addOperation('<kmss:message key="FlowChartObject.Lang.Event.eventView" bundle="sys-lbpm-engine" />');
	if (AttributeObject && AttributeObject.isEdit()) {
		opUpdate = AttributeObject._addOperation('<kmss:message key="FlowChartObject.Lang.Event.eventEdit" bundle="sys-lbpm-engine" />');
	}
	opUpdate.attr("viewdisplay", "true");
	opUpdate.click(function() {
		AttributeObject.updateNodeEvent(config.id);
	});
	// 删除操作...
	var opDelete = AttributeObject._addOperation('<kmss:message key="FlowChartObject.Lang.Event.eventDelete" bundle="sys-lbpm-engine" />');
	opDelete.click(function() {
		AttributeObject.deleteNodeEvent(config.id);
	});
	// 上移操作...
	var opUp = AttributeObject._addOperation('<kmss:message key="FlowChartObject.Lang.Event.eventUp" bundle="sys-lbpm-engine" />');
	opUp.click(function() {
		AttributeObject.moveNodeEvent(config.id, true);
	});
	// 下移操作...
	var opDown = AttributeObject._addOperation('<kmss:message key="FlowChartObject.Lang.Event.eventDown" bundle="sys-lbpm-engine" />');
	opDown.click(function() {
		AttributeObject.moveNodeEvent(config.id, false);
	});
	
	$(tr.insertCell(-1)).attr("style", "word-break:normal").append(opUpdate[0], opDelete[0], opUp[0], opDown[0]);
};

AttributeObject._addOperation = function(operationName) {
	var opElement = $(document.createElement("a"));
	opElement.attr("href", "javascript:void(0);");
	opElement.text(operationName);
	opElement.append("&nbsp;");
	return opElement;
};

// 添加
AttributeObject.addNodeEvent = function() {
	var cacheElement = $("#TABLE_LBPM_NODE_EVENT")[0], configs = $.data(cacheElement, "configs") || {};
	
	var eventDialog = $.extend({}, dialogObject);
	eventDialog.CurrEventType = null;
	eventDialog.DocumentOnNode = window.document;
	eventDialog.HavedEventTypes = configs;
	eventDialog.AfterShow = function(result) {
		if(result != null) {
			// 追加一行...
			AttributeObject._addNodeEvent(result);
			// 更新配置缓存
			configs[result.id] = $.extend({}, result);
			$.data(cacheElement, "configs", configs);
		}
	};
	AttributeObject.Utils.PopupWindow(Com_Parameter.ContextPath + "sys/lbpm/flowchart/page/node_event_select.jsp", 680, 580, eventDialog);
};

// 查看、更新
AttributeObject.updateNodeEvent = function(id) {
	var cacheElement = $("#TABLE_LBPM_NODE_EVENT")[0];
	// 获取当前的配置信息
	var configs = $.data(cacheElement, "configs"), _config = configs[id];

	var eventDialog = $.extend({}, dialogObject);
	eventDialog.CurrEventType = _config;
	eventDialog.DocumentOnNode = window.document;
	eventDialog.HavedEventTypes = configs;
	eventDialog.AfterShow = function(result) {
		if(result != null) {
			// 更新当前行数据
			$("#" + id + " td:eq(0)").text(result.name);
			$("#" + id + " td:eq(1)").text(result.typeName);
			$("#" + id + " td:eq(2)").text(result.listenerName);
			// 更新缓存
			configs[_config.id] = $.extend(_config, result);
			$.data(cacheElement, "configs", configs);
		}
	};
	var url = Com_Parameter.ContextPath + "sys/lbpm/flowchart/page/node_event_select.jsp";
	if(FlowChartObject.isFreeFlow){
		url+="?isEdit="+AttributeObject.isEdit();
	}
	AttributeObject.Utils.PopupWindow(url, 680, 580, eventDialog);
};

AttributeObject.deleteNodeEvent = function(id) {
	var cacheElement = $("#TABLE_LBPM_NODE_EVENT")[0];
	// 删除当前的配置信息
	var configs = $.data(cacheElement, "configs");
	configs[id] = undefined;
	delete configs[id];
	$.data(cacheElement, "configs", configs);
	
	$("#" + id).remove();
};

AttributeObject.moveNodeEvent = function(id, isUp) {
	var currentTR = $("#" + id);
	if (isUp) {
		var prevTR = currentTR.prev();
		// 不能与标题行进行切换顺序
		if (prevTR.length > 0 && typeof(prevTR.attr("id")) != "undefined") {
			prevTR.insertAfter(currentTR);
		}
	} else {
		var nextTR = currentTR.next();
		if (nextTR.length > 0) {
			nextTR.insertBefore(currentTR);
		} 
	}
};

// 提交数据
AttributeObject.AppendDataFuns.push(function(nodeData) {
	var events = [], configs = $.data($("#TABLE_LBPM_NODE_EVENT")[0], "configs") || {};
	// 遍历动态表格，以保障顺序
	$("#TABLE_LBPM_NODE_EVENT tr:not(:first)").each(function() {
		var id = $(this).attr("id"), config = configs[id];
		if (config) {
			var event = {XMLNODENAME: "event"};
			// event: {id: 唯一标识, name: 事件配置名, type: 事件类型, eventConfig: 事件参数配置信息, listenerId: 监听器标识, listenerConfig: 监听器参数配置信息}
			for (attr in config) {
				// 暂时把name开放出来，批量设置事件需要用到
				//if (attr != "typeName" && attr != "listenerName") {
				event[attr] = config[attr];
				//}
			}
			events.push(event);
		}
	});
	if(events.length > 0){
		nodeData.events = events;
	} else {
		nodeData.events = null;
	}
});
</script>