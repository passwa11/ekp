<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>

<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());

%>
<script language="JavaScript">
	Com_IncludeFile("dialog.js|formula.js|data.js");
	// 流程图页面Window对象
	function LBPM_Template_GetFlowChartWindow(prefix) {
		var _fdType = $("input[name='"+prefix+"fdType']:checked",parent.document);
		if(_fdType.val() == "4") {
			// 自由流默认流程图
			return parent.document.getElementById(prefix+"WF_IFrame_Default").contentWindow;
		} else {
			return parent.document.getElementById(prefix+"WF_IFrame").contentWindow;
		}
	}
	// 业务模块前缀
	var prefix = '${JsParam.prefix}';
	// 流程的对象
	var flowChartObject;
	if(parent && parent.document.getElementById(prefix+"WF_IFrame").contentWindow){
		var chartWindow = LBPM_Template_GetFlowChartWindow(prefix);
		flowChartObject = chartWindow.FlowChartObject;
	}

	var eventsType = {};

</script>
<style>
	.eventsSettingClass .lui_widget_btn .lui_widget_btn_txt {
		vertical-align: inherit;
	}
</style>
<div style="padding: 10px" class="eventsSettingClass">
	<span name="wf_name" class="inputsgl" style="width:150px" title="<kmss:message key='lbpmEventsLog.fdEventType' bundle='sys-lbpmservice-support' />">
		<kmss:message key="lbpmEventsLog.fdEventType" bundle="sys-lbpmservice-support" />
	 </span>
	<xform:select className="eventClass" onValueChange="findByEventAndNode();" property="eventsType" showStatus="edit" showPleaseSelect="true" style="width:200px">
	</xform:select>
	<span name="wf_name" class="inputsgl" style="width:150px" title="<kmss:message key='lbpmEventsLog.nodeType' bundle='sys-lbpmservice-support' />">
		<kmss:message key="lbpmEventsLog.nodeType" bundle="sys-lbpmservice-support" />
	</span>
	<input name="nodeNames" class="inputsgl" style="width:300px" disabled>
	<input type="hidden" name="nodeIds">
	<a href="javacript:void(0)" onclick="selectNode('nodeIds', 'nodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
	<ui:button text="查找" id="getEventsData" onclick="findByEventAndNode()"></ui:button>

	<ui:button text="设置" onclick="addNodeEvent()"></ui:button>

	<script>
		Com_IncludeFile("base64.js");
	</script>
	<xform:xtext property="${param.name}" showStatus="noShow"></xform:xtext>
	<div data-lui-type="sys/lbpmservice/support/lbpm_template/batchEvent/eventSettingDataView!EventSettingDataView" style="display:none;margin-top: 10px">
		<script type="text/config">
 		{
			subject : "${param.subject}",
			columns : {},
			_source : "${param._source}",
			_key : "${param._key}"
		}
 	</script>
		<div data-lui-type="lui/view/render!Template" style="display:none;">
			<script type="text/config">
 		{
			src : '/sys/lbpmservice/support/lbpm_template/batchEvent/eventSettingDataTable.html#'
		}
 		</script>
		</div>
	</div>
</div>
<script>
	// 根据模块从扩展点获取所有事件类型
	$(function(){

		var nodeEvent = [];
		$.ajaxSettings.async = false; // 同步
		$.getJSON(Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_name=com.landray.kmss.sys.lbpm.engine.manager.event.EventDataProvider",
				{modelName: flowChartObject.ModelName, nodeType: "", isSelectAllEvent: "true", d: new Date().getTime()},
				function(json) {
					nodeEvent = eventsType = json;
				});
		$.getJSON(Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_name=com.landray.kmss.sys.lbpm.engine.manager.event.EventDataProvider",
				{modelName: flowChartObject.ModelName, nodeType: "", isSelectAllEventOnNode: "true", d: new Date().getTime()},
				function(json) {
					eventsTypeOnNode = json;
				});
		$.ajaxSettings.async = true;

		// 加载事件类型下拉框
		var eventType = $("[name='eventsType']");
		for(var i=0;i<nodeEvent.length;i++){
			eventType.append('<option value="' + nodeEvent[i].type + '">' + nodeEvent[i].typeName + '</option>');
		}
	});

	function selectNode(idField, nameField){
		var FlowChartObject = flowChartObject;
		var data = new KMSSData();
		for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
			var node = FlowChartObject.Nodes.all[i];
			// 过滤组节点
			if(node.Data.groupNodeId != null || node.Data.startNodeId != null)
				continue;
			var nodDesc = FlowChartObject.Nodes.nodeDesc(node);
			data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
		}
		var dialog = new KMSSDialog(true, true);
		dialog.winTitle = FlowChartObject.Lang.dialogTitle;
		dialog.AddDefaultOption(data);
		dialog.BindingField(idField, nameField, ";");
		dialog.Show();
	}

	// 批量设置事件
	function addNodeEvent() {

		// 判断是否选择了事件类型，未选择不给设置
		var eventType = $("[name='eventsType']").val();
		var eventName = $("[name='eventsType']").find("option:checked").text();
		if(!eventType){
			alert("<bean:message key="lbpmEventsLog.needSelectEventType" bundle="sys-lbpmservice-support"/>");
			return;
		}

		// 获取勾选的数据
		var list_events = $("[name='list_events']:checked");
		if(!(list_events.length>0)){
			alert("<bean:message key="lbpmEventsLog.needCheckNodeData" bundle="sys-lbpmservice-support"/>");
			return;
		}
		var selectNodes = [];
		var selectNodeIds = [];

		// 过滤重复勾选的节点
		for(var l=0;l<list_events.length;l++){
			var jsonObject = JSON.parse($(list_events[l]).val());
			if($.inArray(jsonObject.nodeId,selectNodeIds) == -1){
				selectNodeIds.push(jsonObject.nodeId);
				selectNodes.push(jsonObject);
			}
		}
		var param = {};
		param.selectNodes = selectNodes;
		param.eventType = eventType;
		param.eventName = eventName;
		param.eventsObject = eventsType;
		param.flowChartObject = flowChartObject;
		param.fdProcessTemplateId = '${JsParam.fdProcessTemplateId}';
		top.Com_Parameter.Dialog = param;

		// 打开事件配置面板
		seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
			var url = "/sys/lbpmservice/support/lbpm_template/lbpmTemplate_batchEvent_select.jsp";
			dialog.iframe(url,'<bean:message key="lbpmEventsLog.eventsSetting" bundle="sys-lbpmservice-support"/>',function (data){
				if(data){
					findByEventAndNode();
					topic.publish("list.refresh");
				}
			},{width:700,height : 500});
		});
	}

	function findByEventAndNode(){
		// 选择的事件类型
		var eventType = $("[name='eventsType']").val();
		// 选择的节点Id
		var nodeIds = $("[name='nodeIds']").val();
		// 选择的节点name
		var nodeNames = $("[name='nodeNames']").val();
		var nodeIdArray = [];
		var nodeNameArray = [];
		var allNodes = flowChartObject.Nodes.all;

		if(nodeIds){
			nodeIdArray =  nodeIds.split(";");
		}
		if(nodeNames){
			nodeNameArray =  nodeNames.split(";");
		}
		// 如果选择了节点就取选择的，未选择则取当前流程图配置的节点
		if(nodeIdArray.length == 0 && nodeNameArray.length == 0){
			for(var n=0;n<allNodes.length;n++){
				if(allNodes[n].Data.groupNodeId != null || allNodes[n].Data.startNodeId != null){
					continue;
				}
				nodeIdArray.push(allNodes[n].Data.id);
				nodeNameArray.push(allNodes[n].Data.id+"."+allNodes[n].Data.name);

			}
		}

		// 获取节点里面所有事件,key:节点名,value:节点事件数组
		var events = {};
		for(var i=0;i<nodeIdArray.length;i++){
			var nodeType =  flowChartObject.Nodes.GetNodeById(nodeIdArray[i]).Type;
			var isPass = false;

			if(!eventType){
				isPass = true;
			}else{
				// 根据已选择的事件类型过滤符合的节点
				for(var key in eventsTypeOnNode){
					if(key === nodeType){
						if(eventType && $.inArray(eventType,eventsTypeOnNode[key]) > -1){
							isPass = true;
							break;
						}
					}
				}
			}
			// 不符合的节点就不添加
			if(!isPass){
				continue;
			}

			// 根据节点进行事件类型过滤
			var allEventsOnNode = flowChartObject.Nodes.GetNodeById(nodeIdArray[i]).Data.events;
			var eventsOnNode = [];
			// 选择了事件则进行事件过滤，未选择则只进行节点过滤
			if(eventType && allEventsOnNode && allEventsOnNode.length>0){
				for(var e=0;e<allEventsOnNode.length;e++){
					if(allEventsOnNode[e].type === eventType){
						eventsOnNode.push(allEventsOnNode[e]);
					}
				}
				eventsOnNode = eventsOnNode.length>0 ? eventsOnNode :[{"id":"","type":"","typeName":"","name":"","listenerId":"","listenerName":""}];
				events[nodeNameArray[i]] = eventsOnNode;
			}else{
				allEventsOnNode = (allEventsOnNode && allEventsOnNode.length>0)  ? allEventsOnNode:[{"id":"","type":"","typeName":"","name":"","listenerId":"","listenerName":""}];
				events[nodeNameArray[i]] = allEventsOnNode;
			}

		}

		// 发布列表刷新事件
		seajs.use(['lui/topic'],function (topic){
			topic.publish('eventsRefresh',events);
		});
	}

	function deleteEventOnNode(id,eventType,listenerId,nodeId,nodename){
		if(!eventType){
			alert("<bean:message key="lbpmEventsLog.noDeletableEvents" bundle="sys-lbpmservice-support"/>");
			return;
		}
		seajs.use(['lui/dialog','lui/topic'],function (dialog,topic){
			dialog.confirm('<bean:message key="lbpmEventsLog.comfireDeleteEvent" bundle="sys-lbpmservice-support"/>',function(value){
				if(value){
					var events = flowChartObject.Nodes.GetNodeById(nodeId).Data.events;
					if(events){
						var listenerName = "";
						var eventTypeName = "";
						var eventName = "";
						var nodeName = nodeId+"."+nodename;
						for(var i=0;i<events.length;i++){
							if(events[i].id === id && events[i].type === eventType && events[i].listenerId === listenerId){
								listenerName = events[i].listenerName;
								eventTypeName = events[i].typeName;
								eventName = events[i].name;
								// 删除当前行的节点监听器配置
								events.splice(i,1);
								break;
							}
						}

						var data = {"modelName":flowChartObject.TemplateModelName,
							"fdEventType":eventTypeName,"fdNodesName":nodeName,"fdEventName":eventName,"fdEventListener":listenerName,"fdOperationContent":"<bean:message key='lbpmEventsLog.deleteEvent' bundle='sys-lbpmservice-support'/>"};
						$.ajax({
							url:Com_Parameter.ContextPath+'sys/lbpmservice/support/lbpm_events_log/lbpmEventsLog.do?method=save&fdProcessTemplateId=${JsParam.fdProcessTemplateId}',
							type:'POST',
							data:data,
							dataType: 'json',
							error: function(data){
								if(console){
									console.log(data);
								}
							},
							success: function(data){
								alert("成功删除节点："+nodeId+",事件类型："+eventTypeName+",侦听器类型："+listenerName+"的配置");
								// 刷新列表
								findByEventAndNode(prefix);
								topic.publish("list.refresh");

							}
						});

					}

				}
			});
		});

	}
	
</script>

