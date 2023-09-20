<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|doclist.js");
</script>
</head>
<body onload="">
<br>

<script>
	var dialogObject = null;
	if(top && top.Com_Parameter.Dialog){
		dialogObject = top.Com_Parameter.Dialog;
	}
	// 节点+节点名称
	var selectNodes = [];
	// 节点
	var selectNodeIds = [];
	// 事件类型
	var eventType = dialogObject.eventType;
	// 事件名称
	var eventName = dialogObject.eventName;

	// 流程模板id
	var fdProcessTemplateId = dialogObject.fdProcessTemplateId;

	// 所有事件对象
	var eventsObject = dialogObject.eventsObject;

	var FlowChartObject = flowChartObject = dialogObject.flowChartObject;

	for(var l=0;l<dialogObject.selectNodes.length;l++){
		selectNodeIds.push(dialogObject.selectNodes[l].nodeId);
		selectNodes.push(dialogObject.selectNodes[l].nodeId+"."+dialogObject.selectNodes[l].nodeName);
	}
	// 当前事件对象
	var _event = {};

	var _eventObject = {};


	$(function(){
		$("#DIV_EditButtons").show();
		$("#nodeName").text(selectNodes.join(";"));
		$("#event").append('<option value="' + eventType + '">' + eventName + '</option>');
		$("#event").val(eventType);
		// 加载侦听器
		loadListenerByEvent(eventType);
	});
	
	function loadListenerByEvent(currentEventType) {

		$.each(eventsObject || [], function(i, event) {
			_eventObject[event.type] = event;
			if(event && event.type === eventType){
				_event = event;
			}
		});
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

	}

	/**
	 * 功能：加载事件或监听器配置页面
	 * @param type 类型（event/listener）
	 * @param jsp 加载JSP路径
	 * @param showId 配置对应元素的标识，即事件或监听器标识
	 * @param showName 配置标题名，即事件或监听器显示名
	 */
	function loadJsp(type, jsp, showId, showName) {
		var _jsp = Com_SetUrlParameter(jsp, "method", "edit");
		// 加载页面...
		$("#if_" + type).attr("src", _jsp).load(function() {
			var ifType = document.getElementById("if_" + type);
			// 相应类型初始化
			if(ifType && ifType.contentWindow.initValue) {
				var nodeEvents = _eventObject;
				var nodeObject = flowChartObject.Nodes.GetNodeById(selectNodes[0]);
				// 组装上下文信息，提供给加载页面初始化使用
				var context = {selectedId: showId, nodeEvents: nodeEvents, modelName: flowChartObject.ModelName};
				if(eventType) {
					// 从节点属性选择的事件配置中加载当前配置页面，此时需把已选择的配置信息传递给配置页面
					ifType.contentWindow.initValue(null, eventType, nodeObject, context);
				} else {
					ifType.contentWindow.initValue(null, null, nodeObject, context);
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
	

	// 选择事件侦听器
	function changeListener(current) {
		if ($.trim($(current).val()).length == 0) {
			clearJsp("listener");
			clearDescription("listener");
			return;
		}

		var _currentListenerType = $(current).val();
		// 当前选择的事件类型对象的侦听器
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
	function returnIframeInformation(type, outConfig,currentHavedEventTypes) {
		var ifType = document.getElementById("if_" + type);
		if (ifType) {
			if (ifType.contentWindow.checkValue) {
				// 配置校验
				if(!ifType.contentWindow.checkValue(outConfig, currentHavedEventTypes)) {
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
	function submitData() {
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

		// _config: {id: 唯一标识, name: 事件配置名, type: 事件类型, eventConfig: 事件参数配置信息, listenerId: 监听器标识, listenerConfig: 监听器参数配置信息}
		var _config = {}, _event = $("#event"), listenerType = $("#listener");

		_config.id = generateID();
		_config.name = $("#eventName").val();
		_config.type = _event.val();
		_config.typeName = _event.find("option:selected").text();
		_config.listenerId = listenerType.val();
		_config.listenerName = listenerType.find("option:selected").text();
		if(selectNodeIds){
			var node = flowChartObject.Nodes.GetNodeById(selectNodeIds[0]).Data;
			// 获取事件类型参数配置信息
			if (!returnIframeInformation("event", _config,node.events || [])) return false;
			// 获取监听器类型参数配置信息
			if (!returnIframeInformation("listener", _config,node.events || [])) return false;
		}

		// 更新flowchartObject对象里面的事件，往节点里面插入事件以及配置
		$.each(selectNodeIds,function(i,node){
			var nodeData = flowChartObject.Nodes.GetNodeById(node).Data;

			var events = [];
			if(nodeData && _config){
				_config.XMLNODENAME = "event";
				if(nodeData.events){
					nodeData.events.push(_config);
				}else{
					// 节点未配置事件的需要另外增加events的key
					events.push(_config);
					nodeData["events"] = events;
				}
			}
		});
		var listenerName = listenerType.find("option:selected").text();
		var eventTypeName = _event.find("option:selected").text();
		var _eventName = $("#eventName").val();
		var data = {"modelName":flowChartObject.TemplateModelName,
			"fdEventType":eventTypeName,"fdNodesName":selectNodes.join(";"),"fdEventName":_eventName,"fdEventListener":listenerName,"fdOperationContent":"<bean:message key='lbpmEventsLog.addEvent' bundle='sys-lbpmservice-support'/>"};
		$.ajax({
			url:Com_Parameter.ContextPath+'sys/lbpmservice/support/lbpm_events_log/lbpmEventsLog.do?method=save&fdProcessTemplateId='+fdProcessTemplateId,
			type:'POST',
			data:data,
			dataType: 'json',
			error: function(data){
				if(console){
					console.log(data);
				}
			},
			success: function(data){
				if($dialog){
					$dialog.hide(true);
				}
			}
		});

	};

	// 生成Id
	function generateID() {
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
				<span id="nodeName" style="width:95%"/>

				</span>
			</td>
		</tr>
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
				<select id="event" disabled>
				</select>
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
				<select id="listener" onchange="changeListener(this);">
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
		<input name="btnOK" type="button" class="btnopt" onclick="submitData();" value="  <kmss:message key="FlowChartObject.Lang.OK" bundle="sys-lbpm-engine" />  " />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="btnCancel" type="button" class="btnopt" onclick="Com_CloseWindow();" value="  <kmss:message key="FlowChartObject.Lang.Cancel" bundle="sys-lbpm-engine" />  " />
	</div>
	<br>
	<br>
</center>
</body>
</html>